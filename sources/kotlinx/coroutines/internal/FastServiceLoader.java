package kotlinx.coroutines.internal;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.jar.JarFile;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0000¢\u0006\u0002\b\tJ!\u0010\n\u001a\u0004\u0018\u00010\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\r\u001a\u00020\u0005H\bJ*\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0007\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J/\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0007\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0000¢\u0006\u0002\b\u0014J1\u0010\u0015\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f2\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\fH\u0002¢\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J,\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u0002H\u001c0\u001fH\b¢\u0006\u0002\u0010 J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u0006\u0010\"\u001a\u00020#H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lkotlinx/coroutines/internal/FastServiceLoader;", "", "<init>", "()V", "PREFIX", "", "loadMainDispatcherFactory", "", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "loadMainDispatcherFactory$kotlinx_coroutines_core", "createInstanceOf", "baseClass", "Ljava/lang/Class;", "serviceClass", "load", "S", "service", "loader", "Ljava/lang/ClassLoader;", "loadProviders", "loadProviders$kotlinx_coroutines_core", "getProviderInstance", "name", "(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Object;", "parse", "url", "Ljava/net/URL;", "use", "R", "Ljava/util/jar/JarFile;", "block", "Lkotlin/Function1;", "(Ljava/util/jar/JarFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "parseFile", "r", "Ljava/io/BufferedReader;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: FastServiceLoader.kt */
public final class FastServiceLoader {
    public static final FastServiceLoader INSTANCE = new FastServiceLoader();
    private static final String PREFIX = "META-INF/services/";

    private FastServiceLoader() {
    }

    public final List<MainDispatcherFactory> loadMainDispatcherFactory$kotlinx_coroutines_core() {
        MainDispatcherFactory mainFactory;
        Class clz = MainDispatcherFactory.class;
        if (!FastServiceLoaderKt.getANDROID_DETECTED()) {
            return load(clz, clz.getClassLoader());
        }
        try {
            ArrayList result = new ArrayList(2);
            MainDispatcherFactory $this$loadMainDispatcherFactory_u24lambda_u240 = null;
            try {
                mainFactory = clz.cast(Class.forName("kotlinx.coroutines.android.AndroidDispatcherFactory", true, clz.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            } catch (ClassNotFoundException e) {
                mainFactory = null;
            }
            if (mainFactory == null) {
                return load(clz, clz.getClassLoader());
            }
            result.add(mainFactory);
            try {
                $this$loadMainDispatcherFactory_u24lambda_u240 = clz.cast(Class.forName("kotlinx.coroutines.test.internal.TestMainDispatcherFactory", true, clz.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            } catch (ClassNotFoundException e2) {
            }
            if ($this$loadMainDispatcherFactory_u24lambda_u240 != null) {
                result.add($this$loadMainDispatcherFactory_u24lambda_u240);
            }
            return result;
        } catch (Throwable th) {
            return load(clz, clz.getClassLoader());
        }
    }

    private final MainDispatcherFactory createInstanceOf(Class<MainDispatcherFactory> baseClass, String serviceClass) {
        try {
            return baseClass.cast(Class.forName(serviceClass, true, baseClass.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private final <S> List<S> load(Class<S> service, ClassLoader loader) {
        try {
            return loadProviders$kotlinx_coroutines_core(service, loader);
        } catch (Throwable th) {
            return CollectionsKt.toList(ServiceLoader.load(service, loader));
        }
    }

    public final <S> List<S> loadProviders$kotlinx_coroutines_core(Class<S> service, ClassLoader loader) {
        ArrayList<T> $this$flatMap$iv = Collections.list(loader.getResources(PREFIX + service.getName()));
        Intrinsics.checkNotNullExpressionValue($this$flatMap$iv, "list(...)");
        Collection destination$iv$iv = new ArrayList();
        for (T it : $this$flatMap$iv) {
            CollectionsKt.addAll(destination$iv$iv, INSTANCE.parse(it));
        }
        Set providers = CollectionsKt.toSet((List) destination$iv$iv);
        if (!providers.isEmpty()) {
            Iterable<String> $this$map$iv = providers;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (String it2 : $this$map$iv) {
                destination$iv$iv2.add(INSTANCE.getProviderInstance(it2, loader, service));
            }
            return (List) destination$iv$iv2;
        }
        throw new IllegalArgumentException("No providers were loaded with FastServiceLoader".toString());
    }

    private final <S> S getProviderInstance(String name, ClassLoader loader, Class<S> service) {
        Class clazz = Class.forName(name, false, loader);
        if (service.isAssignableFrom(clazz)) {
            return service.cast(clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        throw new IllegalArgumentException(("Expected service of class " + service + ", but found " + clazz).toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005b, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r10, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005f, code lost:
        throw r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0091, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0092, code lost:
        kotlin.io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0095, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<java.lang.String> parse(java.net.URL r15) {
        /*
            r14 = this;
            java.lang.String r0 = r15.toString()
            java.lang.String r1 = "jar"
            r2 = 0
            r3 = 2
            r4 = 0
            boolean r1 = kotlin.text.StringsKt.startsWith$default(r0, r1, r2, r3, r4)
            if (r1 == 0) goto L_0x006f
            java.lang.String r1 = "jar:file:"
            java.lang.String r1 = kotlin.text.StringsKt.substringAfter$default((java.lang.String) r0, (java.lang.String) r1, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            r5 = 33
            java.lang.String r1 = kotlin.text.StringsKt.substringBefore$default((java.lang.String) r1, (char) r5, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            java.lang.String r5 = "!/"
            java.lang.String r3 = kotlin.text.StringsKt.substringAfter$default((java.lang.String) r0, (java.lang.String) r5, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            java.util.jar.JarFile r5 = new java.util.jar.JarFile
            r5.<init>(r1, r2)
            r2 = r14
            r6 = 0
            r7 = 0
            r8 = r5
            r9 = 0
            java.io.BufferedReader r10 = new java.io.BufferedReader     // Catch:{ all -> 0x0060 }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ all -> 0x0060 }
            java.util.zip.ZipEntry r12 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x0060 }
            r12.<init>(r3)     // Catch:{ all -> 0x0060 }
            java.io.InputStream r12 = r8.getInputStream(r12)     // Catch:{ all -> 0x0060 }
            java.lang.String r13 = "UTF-8"
            r11.<init>(r12, r13)     // Catch:{ all -> 0x0060 }
            java.io.Reader r11 = (java.io.Reader) r11     // Catch:{ all -> 0x0060 }
            r10.<init>(r11)     // Catch:{ all -> 0x0060 }
            java.io.Closeable r10 = (java.io.Closeable) r10     // Catch:{ all -> 0x0060 }
            r11 = r10
            java.io.BufferedReader r11 = (java.io.BufferedReader) r11     // Catch:{ all -> 0x0059 }
            r12 = 0
            kotlinx.coroutines.internal.FastServiceLoader r13 = INSTANCE     // Catch:{ all -> 0x0059 }
            java.util.List r13 = r13.parseFile(r11)     // Catch:{ all -> 0x0059 }
            kotlin.io.CloseableKt.closeFinally(r10, r4)     // Catch:{ all -> 0x0060 }
            r5.close()     // Catch:{ all -> 0x0057 }
            return r13
        L_0x0057:
            r2 = move-exception
            throw r2
        L_0x0059:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x005b }
        L_0x005b:
            r11 = move-exception
            kotlin.io.CloseableKt.closeFinally(r10, r4)     // Catch:{ all -> 0x0060 }
            throw r11     // Catch:{ all -> 0x0060 }
        L_0x0060:
            r4 = move-exception
            r7 = r4
            throw r4     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r4 = move-exception
            r5.close()     // Catch:{ all -> 0x0069 }
            throw r4
        L_0x0069:
            r4 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r7, r4)
            throw r7
        L_0x006f:
            java.io.BufferedReader r1 = new java.io.BufferedReader
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            java.io.InputStream r3 = r15.openStream()
            r2.<init>(r3)
            java.io.Reader r2 = (java.io.Reader) r2
            r1.<init>(r2)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = r1
            java.io.BufferedReader r2 = (java.io.BufferedReader) r2     // Catch:{ all -> 0x008f }
            r3 = 0
            kotlinx.coroutines.internal.FastServiceLoader r5 = INSTANCE     // Catch:{ all -> 0x008f }
            java.util.List r5 = r5.parseFile(r2)     // Catch:{ all -> 0x008f }
            kotlin.io.CloseableKt.closeFinally(r1, r4)
            return r5
        L_0x008f:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0091 }
        L_0x0091:
            r3 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.FastServiceLoader.parse(java.net.URL):java.util.List");
    }

    private final <R> R use(JarFile $this$use, Function1<? super JarFile, ? extends R> block) {
        Throwable cause;
        try {
            R invoke = block.invoke($this$use);
            $this$use.close();
            return invoke;
        } catch (Throwable e) {
            try {
                $this$use.close();
                throw e;
            } catch (Throwable closeException) {
                ExceptionsKt.addSuppressed(cause, closeException);
                throw cause;
            }
        }
    }

    private final List<String> parseFile(BufferedReader r) {
        CharSequence $this$all$iv;
        Set names = new LinkedHashSet();
        while (true) {
            String line = r.readLine();
            if (line == null) {
                return CollectionsKt.toList(names);
            }
            String serviceName = StringsKt.trim((CharSequence) StringsKt.substringBefore$default(line, "#", (String) null, 2, (Object) null)).toString();
            CharSequence $this$all$iv2 = serviceName;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= $this$all$iv2.length()) {
                    $this$all$iv = 1;
                    break;
                }
                char it = $this$all$iv2.charAt(i);
                if (((it == '.' || Character.isJavaIdentifierPart(it)) ? (char) 1 : 0) == 0) {
                    $this$all$iv = null;
                    break;
                }
                i++;
            }
            if ($this$all$iv != null) {
                if (serviceName.length() > 0) {
                    z = true;
                }
                if (z) {
                    names.add(serviceName);
                }
            } else {
                throw new IllegalArgumentException(("Illegal service provider class name: " + serviceName).toString());
            }
        }
    }
}
