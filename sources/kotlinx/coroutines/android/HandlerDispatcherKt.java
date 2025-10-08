package kotlinx.coroutines.android;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.Dispatchers;

@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0007¢\u0006\u0002\b\u0005\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0001\u001a\u000e\u0010\u0011\u001a\u00020\u0007H@¢\u0006\u0002\u0010\u0012\u001a\u000e\u0010\u0013\u001a\u00020\u0007H@¢\u0006\u0002\u0010\u0012\u001a\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0017H\u0002\u001a\u001e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0017H\u0002\"\u000e\u0010\u0006\u001a\u00020\u0007XT¢\u0006\u0002\n\u0000\"\u0018\u0010\f\u001a\u0004\u0018\u00010\u00018\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\r\u0010\u000e\"\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"asCoroutineDispatcher", "Lkotlinx/coroutines/android/HandlerDispatcher;", "Landroid/os/Handler;", "name", "", "from", "MAX_DELAY", "", "asHandler", "Landroid/os/Looper;", "async", "", "Main", "getMain$annotations", "()V", "choreographer", "Landroid/view/Choreographer;", "awaitFrame", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitFrameSlowPath", "updateChoreographerAndPostFrameCallback", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "postFrameCallback", "kotlinx-coroutines-android"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: HandlerDispatcher.kt */
public final class HandlerDispatcherKt {
    private static final long MAX_DELAY = 4611686018427387903L;
    public static final HandlerDispatcher Main;
    private static volatile Choreographer choreographer;

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Use Dispatchers.Main instead")
    public static /* synthetic */ void getMain$annotations() {
    }

    public static /* synthetic */ HandlerDispatcher from$default(Handler handler, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return from(handler, str);
    }

    public static final HandlerDispatcher from(Handler $this$asCoroutineDispatcher) {
        return from$default($this$asCoroutineDispatcher, (String) null, 1, (Object) null);
    }

    public static final HandlerDispatcher from(Handler $this$asCoroutineDispatcher, String name) {
        return new HandlerContext($this$asCoroutineDispatcher, name);
    }

    public static final Handler asHandler(Looper $this$asHandler, boolean async) {
        if (!async) {
            return new Handler($this$asHandler);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            Object invoke = Handler.class.getDeclaredMethod("createAsync", new Class[]{Looper.class}).invoke((Object) null, new Object[]{$this$asHandler});
            Intrinsics.checkNotNull(invoke, "null cannot be cast to non-null type android.os.Handler");
            return (Handler) invoke;
        }
        try {
            return Handler.class.getDeclaredConstructor(new Class[]{Looper.class, Handler.Callback.class, Boolean.TYPE}).newInstance(new Object[]{$this$asHandler, null, true});
        } catch (NoSuchMethodException e) {
            return new Handler($this$asHandler);
        }
    }

    static {
        Object obj;
        HandlerDispatcher handlerDispatcher = null;
        try {
            Result.Companion companion = Result.Companion;
            obj = Result.m6constructorimpl(new HandlerContext(asHandler(Looper.getMainLooper(), true), (String) null, 2, (DefaultConstructorMarker) null));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m6constructorimpl(ResultKt.createFailure(th));
        }
        if (!Result.m12isFailureimpl(obj)) {
            handlerDispatcher = obj;
        }
        Main = handlerDispatcher;
    }

    public static final Object awaitFrame(Continuation<? super Long> $completion) {
        Choreographer choreographer2 = choreographer;
        if (choreographer2 == null) {
            return awaitFrameSlowPath($completion);
        }
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        postFrameCallback(choreographer2, cancellable$iv);
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static final Object awaitFrameSlowPath(Continuation<? super Long> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            updateChoreographerAndPostFrameCallback(cont);
        } else {
            Dispatchers.getMain().dispatch(cont.getContext(), new HandlerDispatcherKt$awaitFrameSlowPath$lambda$3$$inlined$Runnable$1(cont));
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static final void updateChoreographerAndPostFrameCallback(CancellableContinuation<? super Long> cont) {
        Choreographer choreographer2 = choreographer;
        if (choreographer2 == null) {
            choreographer2 = Choreographer.getInstance();
            Intrinsics.checkNotNull(choreographer2);
            choreographer = choreographer2;
        }
        postFrameCallback(choreographer2, cont);
    }

    /* access modifiers changed from: private */
    public static final void postFrameCallback(Choreographer choreographer2, CancellableContinuation<? super Long> cont) {
        choreographer2.postFrameCallback(new HandlerDispatcherKt$$ExternalSyntheticLambda0(cont));
    }

    /* access modifiers changed from: private */
    public static final void postFrameCallback$lambda$6(CancellableContinuation $cont, long nanos) {
        $cont.resumeUndispatched(Dispatchers.getMain(), Long.valueOf(nanos));
    }
}
