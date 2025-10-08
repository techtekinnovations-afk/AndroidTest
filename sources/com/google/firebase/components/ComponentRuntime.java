package com.google.firebase.components;

import android.util.Log;
import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.google.firebase.dynamicloading.ComponentLoader;
import com.google.firebase.events.Publisher;
import com.google.firebase.events.Subscriber;
import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public class ComponentRuntime implements ComponentContainer, ComponentLoader {
    private static final Provider<Set<Object>> EMPTY_PROVIDER = new ComponentRuntime$$ExternalSyntheticLambda1();
    private final ComponentRegistrarProcessor componentRegistrarProcessor;
    private final Map<Component<?>, Provider<?>> components;
    private final AtomicReference<Boolean> eagerComponentsInitializedWith;
    private final EventBus eventBus;
    private final Map<Qualified<?>, Provider<?>> lazyInstanceMap;
    private final Map<Qualified<?>, LazySet<?>> lazySetMap;
    private Set<String> processedCoroutineDispatcherInterfaces;
    private final List<Provider<ComponentRegistrar>> unprocessedRegistrarProviders;

    @Deprecated
    public ComponentRuntime(Executor defaultEventExecutor, Iterable<ComponentRegistrar> registrars, Component<?>... additionalComponents) {
        this(defaultEventExecutor, toProviders(registrars), Arrays.asList(additionalComponents), ComponentRegistrarProcessor.NOOP);
    }

    public static Builder builder(Executor defaultEventExecutor) {
        return new Builder(defaultEventExecutor);
    }

    private ComponentRuntime(Executor defaultEventExecutor, Iterable<Provider<ComponentRegistrar>> registrars, Collection<Component<?>> additionalComponents, ComponentRegistrarProcessor componentRegistrarProcessor2) {
        this.components = new HashMap();
        this.lazyInstanceMap = new HashMap();
        this.lazySetMap = new HashMap();
        this.processedCoroutineDispatcherInterfaces = new HashSet();
        this.eagerComponentsInitializedWith = new AtomicReference<>();
        this.eventBus = new EventBus(defaultEventExecutor);
        this.componentRegistrarProcessor = componentRegistrarProcessor2;
        List<Component<?>> componentsToAdd = new ArrayList<>();
        componentsToAdd.add(Component.of(this.eventBus, EventBus.class, (Class<? super T>[]) new Class[]{Subscriber.class, Publisher.class}));
        componentsToAdd.add(Component.of(this, ComponentLoader.class, (Class<? super T>[]) new Class[0]));
        for (Component<?> additionalComponent : additionalComponents) {
            if (additionalComponent != null) {
                componentsToAdd.add(additionalComponent);
            }
        }
        this.unprocessedRegistrarProviders = iterableToList(registrars);
        discoverComponents(componentsToAdd);
    }

    private void discoverComponents(List<Component<?>> componentsToAdd) {
        List<Runnable> runAfterDiscovery = new ArrayList<>();
        synchronized (this) {
            Iterator<Provider<ComponentRegistrar>> iterator = this.unprocessedRegistrarProviders.iterator();
            while (iterator.hasNext()) {
                try {
                    ComponentRegistrar registrar = iterator.next().get();
                    if (registrar != null) {
                        componentsToAdd.addAll(this.componentRegistrarProcessor.processRegistrar(registrar));
                        iterator.remove();
                    }
                } catch (InvalidRegistrarException ex) {
                    iterator.remove();
                    Log.w("ComponentDiscovery", "Invalid component registrar.", ex);
                }
            }
            Iterator<Component<?>> it = componentsToAdd.iterator();
            while (it.hasNext()) {
                Object[] array = it.next().getProvidedInterfaces().toArray();
                int length = array.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Object anInterface = array[i];
                    if (anInterface.toString().contains("kotlinx.coroutines.CoroutineDispatcher")) {
                        if (this.processedCoroutineDispatcherInterfaces.contains(anInterface.toString())) {
                            it.remove();
                            break;
                        }
                        this.processedCoroutineDispatcherInterfaces.add(anInterface.toString());
                    }
                    i++;
                }
            }
            if (this.components.isEmpty()) {
                CycleDetector.detect(componentsToAdd);
            } else {
                ArrayList<Component<?>> allComponents = new ArrayList<>(this.components.keySet());
                allComponents.addAll(componentsToAdd);
                CycleDetector.detect(allComponents);
            }
            for (Component<?> component : componentsToAdd) {
                this.components.put(component, new Lazy<>(new ComponentRuntime$$ExternalSyntheticLambda2(this, component)));
            }
            runAfterDiscovery.addAll(processInstanceComponents(componentsToAdd));
            runAfterDiscovery.addAll(processSetComponents());
            processDependencies();
        }
        for (Runnable runnable : runAfterDiscovery) {
            runnable.run();
        }
        maybeInitializeEagerComponents();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$discoverComponents$0$com-google-firebase-components-ComponentRuntime  reason: not valid java name */
    public /* synthetic */ Object m1767lambda$discoverComponents$0$comgooglefirebasecomponentsComponentRuntime(Component component) {
        return component.getFactory().create(new RestrictedComponentContainer(component, this));
    }

    private void maybeInitializeEagerComponents() {
        Boolean isDefaultApp = this.eagerComponentsInitializedWith.get();
        if (isDefaultApp != null) {
            doInitializeEagerComponents(this.components, isDefaultApp.booleanValue());
        }
    }

    private static Iterable<Provider<ComponentRegistrar>> toProviders(Iterable<ComponentRegistrar> registrars) {
        List<Provider<ComponentRegistrar>> result = new ArrayList<>();
        for (ComponentRegistrar registrar : registrars) {
            result.add(new ComponentRuntime$$ExternalSyntheticLambda0(registrar));
        }
        return result;
    }

    static /* synthetic */ ComponentRegistrar lambda$toProviders$1(ComponentRegistrar registrar) {
        return registrar;
    }

    private static <T> List<T> iterableToList(Iterable<T> iterable) {
        ArrayList<T> result = new ArrayList<>();
        for (T item : iterable) {
            result.add(item);
        }
        return result;
    }

    private List<Runnable> processInstanceComponents(List<Component<?>> componentsToProcess) {
        ArrayList<Runnable> runnables = new ArrayList<>();
        for (Component<?> component : componentsToProcess) {
            if (component.isValue()) {
                Provider<Object> provider = this.components.get(component);
                for (Qualified<?> anInterface : component.getProvidedInterfaces()) {
                    if (!this.lazyInstanceMap.containsKey(anInterface)) {
                        this.lazyInstanceMap.put(anInterface, provider);
                    } else {
                        runnables.add(new ComponentRuntime$$ExternalSyntheticLambda3((OptionalProvider) this.lazyInstanceMap.get(anInterface), provider));
                    }
                }
            }
        }
        return runnables;
    }

    private List<Runnable> processSetComponents() {
        ArrayList<Runnable> runnables = new ArrayList<>();
        Map<Qualified<?>, Set<Provider<?>>> setIndex = new HashMap<>();
        for (Map.Entry<Component<?>, Provider<?>> entry : this.components.entrySet()) {
            Component<?> component = entry.getKey();
            if (!component.isValue()) {
                Provider<?> provider = entry.getValue();
                for (Qualified<?> anInterface : component.getProvidedInterfaces()) {
                    if (!setIndex.containsKey(anInterface)) {
                        setIndex.put(anInterface, new HashSet());
                    }
                    setIndex.get(anInterface).add(provider);
                }
            }
        }
        for (Map.Entry<Qualified<?>, Set<Provider<?>>> entry2 : setIndex.entrySet()) {
            if (!this.lazySetMap.containsKey(entry2.getKey())) {
                this.lazySetMap.put(entry2.getKey(), LazySet.fromCollection(entry2.getValue()));
            } else {
                LazySet<Object> existingSet = this.lazySetMap.get(entry2.getKey());
                for (Provider<Object> provider2 : entry2.getValue()) {
                    runnables.add(new ComponentRuntime$$ExternalSyntheticLambda4(existingSet, provider2));
                }
            }
        }
        return runnables;
    }

    public synchronized <T> Provider<T> getProvider(Qualified<T> anInterface) {
        Preconditions.checkNotNull(anInterface, "Null interface requested.");
        return this.lazyInstanceMap.get(anInterface);
    }

    public <T> Deferred<T> getDeferred(Qualified<T> anInterface) {
        Provider<T> provider = getProvider(anInterface);
        if (provider == null) {
            return OptionalProvider.empty();
        }
        if (provider instanceof OptionalProvider) {
            return (OptionalProvider) provider;
        }
        return OptionalProvider.of(provider);
    }

    public synchronized <T> Provider<Set<T>> setOfProvider(Qualified<T> anInterface) {
        LazySet<?> provider = this.lazySetMap.get(anInterface);
        if (provider != null) {
            return provider;
        }
        return EMPTY_PROVIDER;
    }

    public void initializeEagerComponents(boolean isDefaultApp) {
        HashMap<Component<?>, Provider<?>> componentsCopy;
        if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.eagerComponentsInitializedWith, (Object) null, Boolean.valueOf(isDefaultApp))) {
            synchronized (this) {
                componentsCopy = new HashMap<>(this.components);
            }
            doInitializeEagerComponents(componentsCopy, isDefaultApp);
        }
    }

    private void doInitializeEagerComponents(Map<Component<?>, Provider<?>> componentsToInitialize, boolean isDefaultApp) {
        for (Map.Entry<Component<?>, Provider<?>> entry : componentsToInitialize.entrySet()) {
            Component<?> component = entry.getKey();
            Provider<?> provider = entry.getValue();
            if (component.isAlwaysEager() || (component.isEagerInDefaultApp() && isDefaultApp)) {
                provider.get();
            }
        }
        this.eventBus.enablePublishingAndFlushPending();
    }

    public void discoverComponents() {
        synchronized (this) {
            if (!this.unprocessedRegistrarProviders.isEmpty()) {
                discoverComponents(new ArrayList());
            }
        }
    }

    public void initializeAllComponentsForTests() {
        for (Provider<?> component : this.components.values()) {
            component.get();
        }
    }

    private void processDependencies() {
        for (Component<?> component : this.components.keySet()) {
            Iterator<Dependency> it = component.getDependencies().iterator();
            while (true) {
                if (it.hasNext()) {
                    Dependency dependency = it.next();
                    if (dependency.isSet() && !this.lazySetMap.containsKey(dependency.getInterface())) {
                        this.lazySetMap.put(dependency.getInterface(), LazySet.fromCollection(Collections.emptySet()));
                    } else if (this.lazyInstanceMap.containsKey(dependency.getInterface())) {
                        continue;
                    } else if (dependency.isRequired()) {
                        throw new MissingDependencyException(String.format("Unsatisfied dependency for component %s: %s", new Object[]{component, dependency.getInterface()}));
                    } else if (!dependency.isSet()) {
                        this.lazyInstanceMap.put(dependency.getInterface(), OptionalProvider.empty());
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Collection<Component<?>> getAllComponentsForTest() {
        return this.components.keySet();
    }

    public static final class Builder {
        private final List<Component<?>> additionalComponents = new ArrayList();
        private ComponentRegistrarProcessor componentRegistrarProcessor = ComponentRegistrarProcessor.NOOP;
        private final Executor defaultExecutor;
        private final List<Provider<ComponentRegistrar>> lazyRegistrars = new ArrayList();

        Builder(Executor defaultExecutor2) {
            this.defaultExecutor = defaultExecutor2;
        }

        public Builder addLazyComponentRegistrars(Collection<Provider<ComponentRegistrar>> registrars) {
            this.lazyRegistrars.addAll(registrars);
            return this;
        }

        static /* synthetic */ ComponentRegistrar lambda$addComponentRegistrar$0(ComponentRegistrar registrar) {
            return registrar;
        }

        public Builder addComponentRegistrar(ComponentRegistrar registrar) {
            this.lazyRegistrars.add(new ComponentRuntime$Builder$$ExternalSyntheticLambda0(registrar));
            return this;
        }

        public Builder addComponent(Component<?> component) {
            this.additionalComponents.add(component);
            return this;
        }

        public Builder setProcessor(ComponentRegistrarProcessor processor) {
            this.componentRegistrarProcessor = processor;
            return this;
        }

        public ComponentRuntime build() {
            return new ComponentRuntime(this.defaultExecutor, this.lazyRegistrars, this.additionalComponents, this.componentRegistrarProcessor);
        }
    }
}
