package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J\u001d\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014J\u0014\u0010\u0015\u001a\u00020\u000f2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J(\u0010\u0016\u001a\u0004\u0018\u0001H\u0017\"\b\b\u0001\u0010\u0017*\u00020\u00182\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00170\nH\u0002¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0010\u0010\u0003\u001a\u00028\u0000X\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u001c"}, d2 = {"Lkotlinx/coroutines/internal/ThreadLocalElement;", "T", "Lkotlinx/coroutines/ThreadContextElement;", "value", "threadLocal", "Ljava/lang/ThreadLocal;", "<init>", "(Ljava/lang/Object;Ljava/lang/ThreadLocal;)V", "Ljava/lang/Object;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "updateThreadContext", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/Object;", "restoreThreadContext", "", "oldState", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "minusKey", "get", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: ThreadContext.kt */
public final class ThreadLocalElement<T> implements ThreadContextElement<T> {
    private final CoroutineContext.Key<?> key = new ThreadLocalKey(this.threadLocal);
    private final ThreadLocal<T> threadLocal;
    private final T value;

    public ThreadLocalElement(T value2, ThreadLocal<T> threadLocal2) {
        this.value = value2;
        this.threadLocal = threadLocal2;
    }

    public <R> R fold(R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
        return ThreadContextElement.DefaultImpls.fold(this, initial, operation);
    }

    public CoroutineContext plus(CoroutineContext context) {
        return ThreadContextElement.DefaultImpls.plus(this, context);
    }

    public CoroutineContext.Key<?> getKey() {
        return this.key;
    }

    public T updateThreadContext(CoroutineContext context) {
        Object oldState = this.threadLocal.get();
        this.threadLocal.set(this.value);
        return oldState;
    }

    public void restoreThreadContext(CoroutineContext context, T oldState) {
        this.threadLocal.set(oldState);
    }

    public CoroutineContext minusKey(CoroutineContext.Key<?> key2) {
        return Intrinsics.areEqual((Object) getKey(), (Object) key2) ? EmptyCoroutineContext.INSTANCE : this;
    }

    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key2) {
        if (!Intrinsics.areEqual((Object) getKey(), (Object) key2)) {
            return null;
        }
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type E of kotlinx.coroutines.internal.ThreadLocalElement.get");
        return this;
    }

    public String toString() {
        return "ThreadLocal(value=" + this.value + ", threadLocal = " + this.threadLocal + ')';
    }
}
