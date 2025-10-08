package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Semaphore.kt */
/* synthetic */ class SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1 extends FunctionReferenceImpl implements Function2<Long, SemaphoreSegment, SemaphoreSegment> {
    public static final SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1 INSTANCE = new SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1();

    SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1() {
        super(2, SemaphoreKt.class, "createSegment", "createSegment(JLkotlinx/coroutines/sync/SemaphoreSegment;)Lkotlinx/coroutines/sync/SemaphoreSegment;", 1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
        return invoke(((Number) p1).longValue(), (SemaphoreSegment) p2);
    }

    public final SemaphoreSegment invoke(long p0, SemaphoreSegment p1) {
        return SemaphoreKt.createSegment(p0, p1);
    }
}
