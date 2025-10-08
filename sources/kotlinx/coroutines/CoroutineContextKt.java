package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0007\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0001H\u0002\u001a \u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0006H\u0002\u001a4\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\u0003\u001a\u00020\u00012\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\f0\u0010H\b¢\u0006\u0002\u0010\u0011\u001a8\u0010\u0012\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\f0\u0010H\b¢\u0006\u0002\u0010\u0015\u001a(\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0017*\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0003\u001a\u00020\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\u000eH\u0000\u001a\u0013\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0017*\u00020\u001aH\u0010\"\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u00018@X\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u000e\u0010\u001f\u001a\u00020\u001cXT¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"newCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlinx/coroutines/CoroutineScope;", "context", "addedContext", "hasCopyableElements", "", "foldCopies", "originalContext", "appendContext", "isNewCoroutine", "withCoroutineContext", "T", "countOrElement", "", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withContinuationContext", "continuation", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "updateUndispatchedCompletion", "Lkotlinx/coroutines/UndispatchedCoroutine;", "oldValue", "undispatchedCompletion", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "coroutineName", "", "getCoroutineName", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/String;", "DEBUG_THREAD_NAME_SEPARATOR", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: CoroutineContext.kt */
public final class CoroutineContextKt {
    private static final String DEBUG_THREAD_NAME_SEPARATOR = " @";

    public static final CoroutineContext newCoroutineContext(CoroutineScope $this$newCoroutineContext, CoroutineContext context) {
        CoroutineContext combined = foldCopies($this$newCoroutineContext.getCoroutineContext(), context, true);
        CoroutineContext debug = DebugKt.getDEBUG() ? combined.plus(new CoroutineId(DebugKt.getCOROUTINE_ID().incrementAndGet())) : combined;
        return (combined == Dispatchers.getDefault() || combined.get(ContinuationInterceptor.Key) != null) ? debug : debug.plus(Dispatchers.getDefault());
    }

    public static final CoroutineContext newCoroutineContext(CoroutineContext $this$newCoroutineContext, CoroutineContext addedContext) {
        if (!hasCopyableElements(addedContext)) {
            return $this$newCoroutineContext.plus(addedContext);
        }
        return foldCopies($this$newCoroutineContext, addedContext, false);
    }

    private static final boolean hasCopyableElements(CoroutineContext $this$hasCopyableElements) {
        return ((Boolean) $this$hasCopyableElements.fold(false, new CoroutineContextKt$$ExternalSyntheticLambda0())).booleanValue();
    }

    /* access modifiers changed from: private */
    public static final boolean hasCopyableElements$lambda$0(boolean result, CoroutineContext.Element it) {
        return result || (it instanceof CopyableThreadContextElement);
    }

    private static final CoroutineContext foldCopies(CoroutineContext originalContext, CoroutineContext appendContext, boolean isNewCoroutine) {
        boolean hasElementsLeft = hasCopyableElements(originalContext);
        boolean hasElementsRight = hasCopyableElements(appendContext);
        if (!hasElementsLeft && !hasElementsRight) {
            return originalContext.plus(appendContext);
        }
        Ref.ObjectRef leftoverContext = new Ref.ObjectRef();
        leftoverContext.element = appendContext;
        CoroutineContext folded = (CoroutineContext) originalContext.fold(EmptyCoroutineContext.INSTANCE, new CoroutineContextKt$$ExternalSyntheticLambda1(leftoverContext, isNewCoroutine));
        if (hasElementsRight) {
            leftoverContext.element = ((CoroutineContext) leftoverContext.element).fold(EmptyCoroutineContext.INSTANCE, new CoroutineContextKt$$ExternalSyntheticLambda2());
        }
        return folded.plus((CoroutineContext) leftoverContext.element);
    }

    /* access modifiers changed from: private */
    public static final CoroutineContext foldCopies$lambda$1(Ref.ObjectRef $leftoverContext, boolean $isNewCoroutine, CoroutineContext result, CoroutineContext.Element element) {
        if (!(element instanceof CopyableThreadContextElement)) {
            return result.plus(element);
        }
        CoroutineContext.Element newElement = ((CoroutineContext) $leftoverContext.element).get(element.getKey());
        if (newElement == null) {
            CopyableThreadContextElement copyableThreadContextElement = (CopyableThreadContextElement) element;
            if ($isNewCoroutine) {
                copyableThreadContextElement = copyableThreadContextElement.copyForChild();
            }
            return result.plus(copyableThreadContextElement);
        }
        $leftoverContext.element = ((CoroutineContext) $leftoverContext.element).minusKey(element.getKey());
        return result.plus(((CopyableThreadContextElement) element).mergeForChild(newElement));
    }

    /* access modifiers changed from: private */
    public static final CoroutineContext foldCopies$lambda$2(CoroutineContext result, CoroutineContext.Element element) {
        if (element instanceof CopyableThreadContextElement) {
            return result.plus(((CopyableThreadContextElement) element).copyForChild());
        }
        return result.plus(element);
    }

    public static final <T> T withCoroutineContext(CoroutineContext context, Object countOrElement, Function0<? extends T> block) {
        Object oldValue = ThreadContextKt.updateThreadContext(context, countOrElement);
        try {
            return block.invoke();
        } finally {
            ThreadContextKt.restoreThreadContext(context, oldValue);
        }
    }

    public static final <T> T withContinuationContext(Continuation<?> continuation, Object countOrElement, Function0<? extends T> block) {
        UndispatchedCoroutine undispatchedCompletion;
        CoroutineContext context = continuation.getContext();
        Object oldValue = ThreadContextKt.updateThreadContext(context, countOrElement);
        if (oldValue != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCompletion = updateUndispatchedCompletion(continuation, context, oldValue);
        } else {
            undispatchedCompletion = null;
        }
        try {
            return block.invoke();
        } finally {
            if (undispatchedCompletion == null || undispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, oldValue);
            }
        }
    }

    public static final UndispatchedCoroutine<?> updateUndispatchedCompletion(Continuation<?> $this$updateUndispatchedCompletion, CoroutineContext context, Object oldValue) {
        if (!($this$updateUndispatchedCompletion instanceof CoroutineStackFrame)) {
            return null;
        }
        if (!(context.get(UndispatchedMarker.INSTANCE) != null)) {
            return null;
        }
        UndispatchedCoroutine completion = undispatchedCompletion((CoroutineStackFrame) $this$updateUndispatchedCompletion);
        if (completion != null) {
            completion.saveThreadContext(context, oldValue);
        }
        return completion;
    }

    public static final UndispatchedCoroutine<?> undispatchedCompletion(CoroutineStackFrame $this$undispatchedCompletion) {
        CoroutineStackFrame completion;
        while (!($this$undispatchedCompletion instanceof DispatchedCoroutine) && (completion = $this$undispatchedCompletion.getCallerFrame()) != null) {
            if (completion instanceof UndispatchedCoroutine) {
                return (UndispatchedCoroutine) completion;
            }
            $this$undispatchedCompletion = completion;
        }
        return null;
    }

    public static final String getCoroutineName(CoroutineContext $this$coroutineName) {
        CoroutineId coroutineId;
        String coroutineName;
        if (!DebugKt.getDEBUG() || (coroutineId = (CoroutineId) $this$coroutineName.get(CoroutineId.Key)) == null) {
            return null;
        }
        CoroutineName coroutineName2 = (CoroutineName) $this$coroutineName.get(CoroutineName.Key);
        if (coroutineName2 == null || (coroutineName = coroutineName2.getName()) == null) {
            coroutineName = "coroutine";
        }
        return coroutineName + '#' + coroutineId.getId();
    }
}
