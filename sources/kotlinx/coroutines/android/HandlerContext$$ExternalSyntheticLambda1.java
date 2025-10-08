package kotlinx.coroutines.android;

import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HandlerContext$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ HandlerContext f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ HandlerContext$$ExternalSyntheticLambda1(HandlerContext handlerContext, Runnable runnable) {
        this.f$0 = handlerContext;
        this.f$1 = runnable;
    }

    public final Object invoke(Object obj) {
        return HandlerContext.scheduleResumeAfterDelay$lambda$2(this.f$0, this.f$1, (Throwable) obj);
    }
}
