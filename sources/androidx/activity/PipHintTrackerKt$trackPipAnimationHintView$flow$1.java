package androidx.activity;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Landroid/graphics/Rect;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "androidx.activity.PipHintTrackerKt$trackPipAnimationHintView$flow$1", f = "PipHintTracker.kt", i = {}, l = {86}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: PipHintTracker.kt */
final class PipHintTrackerKt$trackPipAnimationHintView$flow$1 extends SuspendLambda implements Function2<ProducerScope<? super Rect>, Continuation<? super Unit>, Object> {
    final /* synthetic */ View $view;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PipHintTrackerKt$trackPipAnimationHintView$flow$1(View view, Continuation<? super PipHintTrackerKt$trackPipAnimationHintView$flow$1> continuation) {
        super(2, continuation);
        this.$view = view;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        PipHintTrackerKt$trackPipAnimationHintView$flow$1 pipHintTrackerKt$trackPipAnimationHintView$flow$1 = new PipHintTrackerKt$trackPipAnimationHintView$flow$1(this.$view, continuation);
        pipHintTrackerKt$trackPipAnimationHintView$flow$1.L$0 = obj;
        return pipHintTrackerKt$trackPipAnimationHintView$flow$1;
    }

    public final Object invoke(ProducerScope<? super Rect> producerScope, Continuation<? super Unit> continuation) {
        return ((PipHintTrackerKt$trackPipAnimationHintView$flow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ProducerScope $this$callbackFlow = (ProducerScope) this.L$0;
                View.OnLayoutChangeListener layoutChangeListener = new PipHintTrackerKt$trackPipAnimationHintView$flow$1$$ExternalSyntheticLambda0($this$callbackFlow);
                ViewTreeObserver.OnScrollChangedListener scrollChangeListener = new PipHintTrackerKt$trackPipAnimationHintView$flow$1$$ExternalSyntheticLambda1($this$callbackFlow, this.$view);
                PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1 attachStateChangeListener = new PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1($this$callbackFlow, this.$view, scrollChangeListener, layoutChangeListener);
                if (this.$view.isAttachedToWindow()) {
                    $this$callbackFlow.m1565trySendJP2dKIU(PipHintTrackerKt.trackPipAnimationHintView$positionInWindow(this.$view));
                    this.$view.getViewTreeObserver().addOnScrollChangedListener(scrollChangeListener);
                    this.$view.addOnLayoutChangeListener(layoutChangeListener);
                }
                this.$view.addOnAttachStateChangeListener(attachStateChangeListener);
                this.label = 1;
                if (ProduceKt.awaitClose($this$callbackFlow, new PipHintTrackerKt$trackPipAnimationHintView$flow$1$$ExternalSyntheticLambda2(this.$view, scrollChangeListener, layoutChangeListener, attachStateChangeListener), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public static final void invokeSuspend$lambda$0(ProducerScope $$this$callbackFlow, View v, int l, int t, int r, int b, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (l != oldLeft || r != oldRight || t != oldTop || b != oldBottom) {
            Intrinsics.checkNotNull(v);
            $$this$callbackFlow.m1565trySendJP2dKIU(PipHintTrackerKt.trackPipAnimationHintView$positionInWindow(v));
        }
    }

    /* access modifiers changed from: private */
    public static final void invokeSuspend$lambda$1(ProducerScope $$this$callbackFlow, View $view2) {
        $$this$callbackFlow.m1565trySendJP2dKIU(PipHintTrackerKt.trackPipAnimationHintView$positionInWindow($view2));
    }

    /* access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$2(View $view2, ViewTreeObserver.OnScrollChangedListener $scrollChangeListener, View.OnLayoutChangeListener $layoutChangeListener, PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1 $attachStateChangeListener) {
        $view2.getViewTreeObserver().removeOnScrollChangedListener($scrollChangeListener);
        $view2.removeOnLayoutChangeListener($layoutChangeListener);
        $view2.removeOnAttachStateChangeListener($attachStateChangeListener);
        return Unit.INSTANCE;
    }
}
