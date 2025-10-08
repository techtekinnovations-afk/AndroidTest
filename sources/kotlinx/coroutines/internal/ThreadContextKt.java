package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ThreadContextElement;

@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0000\u001a\u001c\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0000\u001a\u001a\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004H\u0000\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\u0002\n\u0000\"$\u0010\u0002\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u0004¢\u0006\u0002\n\u0000\",\u0010\u0006\u001a \u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0007\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00070\u0003X\u0004¢\u0006\u0002\n\u0000\" \u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"NO_THREAD_ELEMENTS", "Lkotlinx/coroutines/internal/Symbol;", "countAll", "Lkotlin/Function2;", "", "Lkotlin/coroutines/CoroutineContext$Element;", "findOne", "Lkotlinx/coroutines/ThreadContextElement;", "updateState", "Lkotlinx/coroutines/internal/ThreadState;", "threadContextElements", "context", "Lkotlin/coroutines/CoroutineContext;", "updateThreadContext", "countOrElement", "restoreThreadContext", "", "oldState", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: ThreadContext.kt */
public final class ThreadContextKt {
    public static final Symbol NO_THREAD_ELEMENTS = new Symbol("NO_THREAD_ELEMENTS");
    private static final Function2<Object, CoroutineContext.Element, Object> countAll = new ThreadContextKt$$ExternalSyntheticLambda0();
    private static final Function2<ThreadContextElement<?>, CoroutineContext.Element, ThreadContextElement<?>> findOne = new ThreadContextKt$$ExternalSyntheticLambda1();
    private static final Function2<ThreadState, CoroutineContext.Element, ThreadState> updateState = new ThreadContextKt$$ExternalSyntheticLambda2();

    /* access modifiers changed from: private */
    public static final Object countAll$lambda$0(Object countOrElement, CoroutineContext.Element element) {
        if (!(element instanceof ThreadContextElement)) {
            return countOrElement;
        }
        Integer num = countOrElement instanceof Integer ? (Integer) countOrElement : null;
        int inCount = num != null ? num.intValue() : 1;
        return inCount == 0 ? element : Integer.valueOf(inCount + 1);
    }

    /* access modifiers changed from: private */
    public static final ThreadContextElement<?> findOne$lambda$1(ThreadContextElement<?> found, CoroutineContext.Element element) {
        if (found != null) {
            return found;
        }
        if (element instanceof ThreadContextElement) {
            return (ThreadContextElement) element;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static final ThreadState updateState$lambda$2(ThreadState state, CoroutineContext.Element element) {
        if (element instanceof ThreadContextElement) {
            state.append((ThreadContextElement) element, ((ThreadContextElement) element).updateThreadContext(state.context));
        }
        return state;
    }

    public static final Object threadContextElements(CoroutineContext context) {
        Object fold = context.fold(0, countAll);
        Intrinsics.checkNotNull(fold);
        return fold;
    }

    public static final Object updateThreadContext(CoroutineContext context, Object countOrElement) {
        Object countOrElement2 = countOrElement == null ? threadContextElements(context) : countOrElement;
        if (countOrElement2 == 0) {
            return NO_THREAD_ELEMENTS;
        }
        if (countOrElement2 instanceof Integer) {
            return context.fold(new ThreadState(context, ((Number) countOrElement2).intValue()), updateState);
        }
        Intrinsics.checkNotNull(countOrElement2, "null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
        return ((ThreadContextElement) countOrElement2).updateThreadContext(context);
    }

    public static final void restoreThreadContext(CoroutineContext context, Object oldState) {
        if (oldState != NO_THREAD_ELEMENTS) {
            if (oldState instanceof ThreadState) {
                ((ThreadState) oldState).restore(context);
                return;
            }
            Object fold = context.fold(null, findOne);
            Intrinsics.checkNotNull(fold, "null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
            ((ThreadContextElement) fold).restoreThreadContext(context, oldState);
        }
    }
}
