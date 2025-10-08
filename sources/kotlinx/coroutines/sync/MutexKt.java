package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u001a?\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0005*\u00020\u00012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\tHH\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\n\"\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u000fXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u000fXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u000fXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u000fXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u000fXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u000fXT¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Mutex", "Lkotlinx/coroutines/sync/Mutex;", "locked", "", "withLock", "T", "owner", "", "action", "Lkotlin/Function0;", "(Lkotlinx/coroutines/sync/Mutex;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "NO_OWNER", "Lkotlinx/coroutines/internal/Symbol;", "ON_LOCK_ALREADY_LOCKED_BY_OWNER", "TRY_LOCK_SUCCESS", "", "TRY_LOCK_FAILED", "TRY_LOCK_ALREADY_LOCKED_BY_OWNER", "HOLDS_LOCK_UNLOCKED", "HOLDS_LOCK_YES", "HOLDS_LOCK_ANOTHER_OWNER", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Mutex.kt */
public final class MutexKt {
    private static final int HOLDS_LOCK_ANOTHER_OWNER = 2;
    private static final int HOLDS_LOCK_UNLOCKED = 0;
    private static final int HOLDS_LOCK_YES = 1;
    /* access modifiers changed from: private */
    public static final Symbol NO_OWNER = new Symbol("NO_OWNER");
    /* access modifiers changed from: private */
    public static final Symbol ON_LOCK_ALREADY_LOCKED_BY_OWNER = new Symbol("ALREADY_LOCKED_BY_OWNER");
    private static final int TRY_LOCK_ALREADY_LOCKED_BY_OWNER = 2;
    private static final int TRY_LOCK_FAILED = 1;
    private static final int TRY_LOCK_SUCCESS = 0;

    public static /* synthetic */ Mutex Mutex$default(boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return Mutex(z);
    }

    public static final Mutex Mutex(boolean locked) {
        return new MutexImpl(locked);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object withLock(kotlinx.coroutines.sync.Mutex r5, java.lang.Object r6, kotlin.jvm.functions.Function0<? extends T> r7, kotlin.coroutines.Continuation<? super T> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.sync.MutexKt$withLock$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.sync.MutexKt$withLock$1 r0 = (kotlinx.coroutines.sync.MutexKt$withLock$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.sync.MutexKt$withLock$1 r0 = new kotlinx.coroutines.sync.MutexKt$withLock$1
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002c:
            r5 = 0
            java.lang.Object r6 = r0.L$2
            kotlin.jvm.functions.Function0 r6 = (kotlin.jvm.functions.Function0) r6
            java.lang.Object r7 = r0.L$1
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.sync.Mutex r2 = (kotlinx.coroutines.sync.Mutex) r2
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0054
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r0.L$0 = r5
            r0.L$1 = r6
            r0.L$2 = r7
            r4 = 1
            r0.label = r4
            java.lang.Object r4 = r5.lock(r6, r0)
            if (r4 != r2) goto L_0x004f
            return r2
        L_0x004f:
            r2 = r7
            r7 = r6
            r6 = r2
            r2 = r5
            r5 = r3
        L_0x0054:
            java.lang.Object r3 = r6.invoke()     // Catch:{ all -> 0x005e }
            r2.unlock(r7)
            return r3
        L_0x005e:
            r6 = move-exception
            r2.unlock(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.MutexKt.withLock(kotlinx.coroutines.sync.Mutex, java.lang.Object, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object withLock$default(Mutex $this$withLock_u24default, Object owner, Function0 action, Continuation $completion, int i, Object obj) {
        if ((i & 1) != 0) {
            owner = null;
        }
        $this$withLock_u24default.lock(owner, $completion);
        try {
            return action.invoke();
        } finally {
            $this$withLock_u24default.unlock(owner);
        }
    }

    private static final <T> Object withLock$$forInline(Mutex $this$withLock, Object owner, Function0<? extends T> action, Continuation<? super T> $completion) {
        $this$withLock.lock(owner, $completion);
        try {
            return action.invoke();
        } finally {
            $this$withLock.unlock(owner);
        }
    }
}
