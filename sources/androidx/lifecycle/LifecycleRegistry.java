package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000 42\u00020\u0001:\u000245B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001cH\u0016J\u0010\u0010$\u001a\u00020\"2\u0006\u0010\u0014\u001a\u00020\u0003H\u0002J\u0010\u0010%\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u001cH\u0002J\u0010\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020(H\u0003J\u0010\u0010)\u001a\u00020\"2\u0006\u0010\u0014\u001a\u00020\u0003H\u0002J\u0010\u0010*\u001a\u00020\"2\u0006\u0010+\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020\"2\u0006\u0010\n\u001a\u00020\u000bH\u0017J\u0010\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020\u000bH\u0002J\b\u00100\u001a\u00020\"H\u0002J\u0010\u00101\u001a\u00020\"2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u00102\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001cH\u0016J\b\u00103\u001a\u00020\"H\u0002R\u000e\u0010\b\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R$\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b8V@VX\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00068BX\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\t8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001bX\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\u001fj\b\u0012\u0004\u0012\u00020\u000b` X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u000e¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Landroidx/lifecycle/LifecycleRegistry;", "Landroidx/lifecycle/Lifecycle;", "provider", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;)V", "enforceMainThread", "", "(Landroidx/lifecycle/LifecycleOwner;Z)V", "addingObserverCounter", "", "state", "Landroidx/lifecycle/Lifecycle$State;", "currentState", "getCurrentState", "()Landroidx/lifecycle/Lifecycle$State;", "setCurrentState", "(Landroidx/lifecycle/Lifecycle$State;)V", "handlingEvent", "isSynced", "()Z", "lifecycleOwner", "Ljava/lang/ref/WeakReference;", "newEventOccurred", "observerCount", "getObserverCount", "()I", "observerMap", "Landroidx/arch/core/internal/FastSafeIterableMap;", "Landroidx/lifecycle/LifecycleObserver;", "Landroidx/lifecycle/LifecycleRegistry$ObserverWithState;", "parentStates", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "addObserver", "", "observer", "backwardPass", "calculateTargetState", "enforceMainThreadIfNeeded", "methodName", "", "forwardPass", "handleLifecycleEvent", "event", "Landroidx/lifecycle/Lifecycle$Event;", "markState", "moveToState", "next", "popParentState", "pushParentState", "removeObserver", "sync", "Companion", "ObserverWithState", "lifecycle-runtime_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: LifecycleRegistry.kt */
public class LifecycleRegistry extends Lifecycle {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private int addingObserverCounter;
    private final boolean enforceMainThread;
    private boolean handlingEvent;
    private final WeakReference<LifecycleOwner> lifecycleOwner;
    private boolean newEventOccurred;
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> observerMap;
    private ArrayList<Lifecycle.State> parentStates;
    private Lifecycle.State state;

    public /* synthetic */ LifecycleRegistry(LifecycleOwner lifecycleOwner2, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(lifecycleOwner2, z);
    }

    @JvmStatic
    public static final LifecycleRegistry createUnsafe(LifecycleOwner lifecycleOwner2) {
        return Companion.createUnsafe(lifecycleOwner2);
    }

    @JvmStatic
    public static final Lifecycle.State min$lifecycle_runtime_release(Lifecycle.State state2, Lifecycle.State state3) {
        return Companion.min$lifecycle_runtime_release(state2, state3);
    }

    private LifecycleRegistry(LifecycleOwner provider, boolean enforceMainThread2) {
        this.enforceMainThread = enforceMainThread2;
        this.observerMap = new FastSafeIterableMap<>();
        this.state = Lifecycle.State.INITIALIZED;
        this.parentStates = new ArrayList<>();
        this.lifecycleOwner = new WeakReference<>(provider);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LifecycleRegistry(LifecycleOwner provider) {
        this(provider, true);
        Intrinsics.checkNotNullParameter(provider, "provider");
    }

    @Deprecated(message = "Override [currentState].")
    public void markState(Lifecycle.State state2) {
        Intrinsics.checkNotNullParameter(state2, "state");
        enforceMainThreadIfNeeded("markState");
        setCurrentState(state2);
    }

    public Lifecycle.State getCurrentState() {
        return this.state;
    }

    public void setCurrentState(Lifecycle.State state2) {
        Intrinsics.checkNotNullParameter(state2, "state");
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state2);
    }

    public void handleLifecycleEvent(Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(event, NotificationCompat.CATEGORY_EVENT);
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    private final void moveToState(Lifecycle.State next) {
        if (this.state != next) {
            if ((this.state == Lifecycle.State.INITIALIZED && next == Lifecycle.State.DESTROYED) ? false : true) {
                this.state = next;
                if (this.handlingEvent || this.addingObserverCounter != 0) {
                    this.newEventOccurred = true;
                    return;
                }
                this.handlingEvent = true;
                sync();
                this.handlingEvent = false;
                if (this.state == Lifecycle.State.DESTROYED) {
                    this.observerMap = new FastSafeIterableMap<>();
                    return;
                }
                return;
            }
            throw new IllegalStateException(("no event down from " + this.state + " in component " + this.lifecycleOwner.get()).toString());
        }
    }

    private final boolean isSynced() {
        if (this.observerMap.size() == 0) {
            return true;
        }
        Map.Entry<LifecycleObserver, ObserverWithState> eldest = this.observerMap.eldest();
        Intrinsics.checkNotNull(eldest);
        Lifecycle.State eldestObserverState = eldest.getValue().getState();
        Map.Entry<LifecycleObserver, ObserverWithState> newest = this.observerMap.newest();
        Intrinsics.checkNotNull(newest);
        Lifecycle.State newestObserverState = newest.getValue().getState();
        if (eldestObserverState == newestObserverState && this.state == newestObserverState) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0009, code lost:
        r2 = r0.getValue();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final androidx.lifecycle.Lifecycle.State calculateTargetState(androidx.lifecycle.LifecycleObserver r7) {
        /*
            r6 = this;
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r0 = r6.observerMap
            java.util.Map$Entry r0 = r0.ceil(r7)
            r1 = 0
            if (r0 == 0) goto L_0x0016
            java.lang.Object r2 = r0.getValue()
            androidx.lifecycle.LifecycleRegistry$ObserverWithState r2 = (androidx.lifecycle.LifecycleRegistry.ObserverWithState) r2
            if (r2 == 0) goto L_0x0016
            androidx.lifecycle.Lifecycle$State r2 = r2.getState()
            goto L_0x0017
        L_0x0016:
            r2 = r1
        L_0x0017:
            java.util.ArrayList<androidx.lifecycle.Lifecycle$State> r3 = r6.parentStates
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L_0x0031
            java.util.ArrayList<androidx.lifecycle.Lifecycle$State> r1 = r6.parentStates
            java.util.ArrayList<androidx.lifecycle.Lifecycle$State> r3 = r6.parentStates
            int r3 = r3.size()
            int r3 = r3 + -1
            java.lang.Object r1 = r1.get(r3)
            androidx.lifecycle.Lifecycle$State r1 = (androidx.lifecycle.Lifecycle.State) r1
        L_0x0031:
            androidx.lifecycle.LifecycleRegistry$Companion r3 = Companion
            androidx.lifecycle.LifecycleRegistry$Companion r4 = Companion
            androidx.lifecycle.Lifecycle$State r5 = r6.state
            androidx.lifecycle.Lifecycle$State r4 = r4.min$lifecycle_runtime_release(r5, r2)
            androidx.lifecycle.Lifecycle$State r3 = r3.min$lifecycle_runtime_release(r4, r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.LifecycleRegistry.calculateTargetState(androidx.lifecycle.LifecycleObserver):androidx.lifecycle.Lifecycle$State");
    }

    public void addObserver(LifecycleObserver observer) {
        LifecycleOwner lifecycleOwner2;
        Intrinsics.checkNotNullParameter(observer, "observer");
        enforceMainThreadIfNeeded("addObserver");
        ObserverWithState statefulObserver = new ObserverWithState(observer, this.state == Lifecycle.State.DESTROYED ? Lifecycle.State.DESTROYED : Lifecycle.State.INITIALIZED);
        if (this.observerMap.putIfAbsent(observer, statefulObserver) == null && (lifecycleOwner2 = (LifecycleOwner) this.lifecycleOwner.get()) != null) {
            boolean isReentrance = this.addingObserverCounter != 0 || this.handlingEvent;
            Lifecycle.State targetState = calculateTargetState(observer);
            this.addingObserverCounter++;
            while (statefulObserver.getState().compareTo(targetState) < 0 && this.observerMap.contains(observer)) {
                pushParentState(statefulObserver.getState());
                Lifecycle.Event event = Lifecycle.Event.Companion.upFrom(statefulObserver.getState());
                if (event != null) {
                    statefulObserver.dispatchEvent(lifecycleOwner2, event);
                    popParentState();
                    targetState = calculateTargetState(observer);
                } else {
                    throw new IllegalStateException("no event up from " + statefulObserver.getState());
                }
            }
            if (!isReentrance) {
                sync();
            }
            this.addingObserverCounter--;
        }
    }

    private final void popParentState() {
        this.parentStates.remove(this.parentStates.size() - 1);
    }

    private final void pushParentState(Lifecycle.State state2) {
        this.parentStates.add(state2);
    }

    public void removeObserver(LifecycleObserver observer) {
        Intrinsics.checkNotNullParameter(observer, "observer");
        enforceMainThreadIfNeeded("removeObserver");
        this.observerMap.remove(observer);
    }

    public int getObserverCount() {
        enforceMainThreadIfNeeded("getObserverCount");
        return this.observerMap.size();
    }

    private final void forwardPass(LifecycleOwner lifecycleOwner2) {
        SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions = this.observerMap.iteratorWithAdditions();
        Intrinsics.checkNotNullExpressionValue(iteratorWithAdditions, "observerMap.iteratorWithAdditions()");
        Iterator ascendingIterator = iteratorWithAdditions;
        while (ascendingIterator.hasNext() && !this.newEventOccurred) {
            Map.Entry entry = (Map.Entry) ascendingIterator.next();
            LifecycleObserver key = (LifecycleObserver) entry.getKey();
            ObserverWithState observer = (ObserverWithState) entry.getValue();
            while (observer.getState().compareTo(this.state) < 0 && !this.newEventOccurred && this.observerMap.contains(key)) {
                pushParentState(observer.getState());
                Lifecycle.Event event = Lifecycle.Event.Companion.upFrom(observer.getState());
                if (event != null) {
                    observer.dispatchEvent(lifecycleOwner2, event);
                    popParentState();
                } else {
                    throw new IllegalStateException("no event up from " + observer.getState());
                }
            }
        }
    }

    private final void backwardPass(LifecycleOwner lifecycleOwner2) {
        Iterator descendingIterator = this.observerMap.descendingIterator();
        Intrinsics.checkNotNullExpressionValue(descendingIterator, "observerMap.descendingIterator()");
        while (descendingIterator.hasNext() && !this.newEventOccurred) {
            Map.Entry next = descendingIterator.next();
            Intrinsics.checkNotNullExpressionValue(next, "next()");
            LifecycleObserver key = (LifecycleObserver) next.getKey();
            ObserverWithState observer = (ObserverWithState) next.getValue();
            while (observer.getState().compareTo(this.state) > 0 && !this.newEventOccurred && this.observerMap.contains(key)) {
                Lifecycle.Event event = Lifecycle.Event.Companion.downFrom(observer.getState());
                if (event != null) {
                    pushParentState(event.getTargetState());
                    observer.dispatchEvent(lifecycleOwner2, event);
                    popParentState();
                } else {
                    throw new IllegalStateException("no event down from " + observer.getState());
                }
            }
        }
    }

    private final void sync() {
        LifecycleOwner lifecycleOwner2 = (LifecycleOwner) this.lifecycleOwner.get();
        if (lifecycleOwner2 != null) {
            while (!isSynced()) {
                this.newEventOccurred = false;
                Lifecycle.State state2 = this.state;
                Map.Entry<LifecycleObserver, ObserverWithState> eldest = this.observerMap.eldest();
                Intrinsics.checkNotNull(eldest);
                if (state2.compareTo(eldest.getValue().getState()) < 0) {
                    backwardPass(lifecycleOwner2);
                }
                Map.Entry newest = this.observerMap.newest();
                if (!this.newEventOccurred && newest != null && this.state.compareTo(newest.getValue().getState()) > 0) {
                    forwardPass(lifecycleOwner2);
                }
            }
            this.newEventOccurred = false;
            return;
        }
        throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is already garbage collected. It is too late to change lifecycle state.");
    }

    private final void enforceMainThreadIfNeeded(String methodName) {
        if (this.enforceMainThread && !ArchTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException(("Method " + methodName + " must be called on the main thread").toString());
        }
    }

    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017R\u001a\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0018"}, d2 = {"Landroidx/lifecycle/LifecycleRegistry$ObserverWithState;", "", "observer", "Landroidx/lifecycle/LifecycleObserver;", "initialState", "Landroidx/lifecycle/Lifecycle$State;", "(Landroidx/lifecycle/LifecycleObserver;Landroidx/lifecycle/Lifecycle$State;)V", "lifecycleObserver", "Landroidx/lifecycle/LifecycleEventObserver;", "getLifecycleObserver", "()Landroidx/lifecycle/LifecycleEventObserver;", "setLifecycleObserver", "(Landroidx/lifecycle/LifecycleEventObserver;)V", "state", "getState", "()Landroidx/lifecycle/Lifecycle$State;", "setState", "(Landroidx/lifecycle/Lifecycle$State;)V", "dispatchEvent", "", "owner", "Landroidx/lifecycle/LifecycleOwner;", "event", "Landroidx/lifecycle/Lifecycle$Event;", "lifecycle-runtime_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: LifecycleRegistry.kt */
    public static final class ObserverWithState {
        private LifecycleEventObserver lifecycleObserver;
        private Lifecycle.State state;

        public ObserverWithState(LifecycleObserver observer, Lifecycle.State initialState) {
            Intrinsics.checkNotNullParameter(initialState, "initialState");
            Intrinsics.checkNotNull(observer);
            this.lifecycleObserver = Lifecycling.lifecycleEventObserver(observer);
            this.state = initialState;
        }

        public final Lifecycle.State getState() {
            return this.state;
        }

        public final void setState(Lifecycle.State state2) {
            Intrinsics.checkNotNullParameter(state2, "<set-?>");
            this.state = state2;
        }

        public final LifecycleEventObserver getLifecycleObserver() {
            return this.lifecycleObserver;
        }

        public final void setLifecycleObserver(LifecycleEventObserver lifecycleEventObserver) {
            Intrinsics.checkNotNullParameter(lifecycleEventObserver, "<set-?>");
            this.lifecycleObserver = lifecycleEventObserver;
        }

        public final void dispatchEvent(LifecycleOwner owner, Lifecycle.Event event) {
            Intrinsics.checkNotNullParameter(event, NotificationCompat.CATEGORY_EVENT);
            Lifecycle.State newState = event.getTargetState();
            this.state = LifecycleRegistry.Companion.min$lifecycle_runtime_release(this.state, newState);
            LifecycleEventObserver lifecycleEventObserver = this.lifecycleObserver;
            Intrinsics.checkNotNull(owner);
            lifecycleEventObserver.onStateChanged(owner, event);
            this.state = newState;
        }
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001f\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\bH\u0001¢\u0006\u0002\b\u000b¨\u0006\f"}, d2 = {"Landroidx/lifecycle/LifecycleRegistry$Companion;", "", "()V", "createUnsafe", "Landroidx/lifecycle/LifecycleRegistry;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "min", "Landroidx/lifecycle/Lifecycle$State;", "state1", "state2", "min$lifecycle_runtime_release", "lifecycle-runtime_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: LifecycleRegistry.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final LifecycleRegistry createUnsafe(LifecycleOwner owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            return new LifecycleRegistry(owner, false, (DefaultConstructorMarker) null);
        }

        @JvmStatic
        public final Lifecycle.State min$lifecycle_runtime_release(Lifecycle.State state1, Lifecycle.State state2) {
            Intrinsics.checkNotNullParameter(state1, "state1");
            return (state2 == null || state2.compareTo(state1) >= 0) ? state1 : state2;
        }
    }
}
