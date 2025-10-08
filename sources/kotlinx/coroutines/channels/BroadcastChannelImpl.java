package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u000256B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0016J\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0002J\u0016\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\u0006\u0010\u0018\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u001e\u0010\u001e\u001a\u00020\u00152\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0011H\u0014J\u0012\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\u0017\u0010'\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0010¢\u0006\u0002\b(J\b\u00103\u001a\u000204H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00060\fj\u0002`\u000bX\u0004¢\u0006\u0004\n\u0002\u0010\rR\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u000fX\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u000e¢\u0006\u0002\n\u0000R \u0010!\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030 \u0012\u0006\u0012\u0004\u0018\u00010\u00110\"X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\u00020$8VX\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0017\u0010+\u001a\u00028\u00008F¢\u0006\f\u0012\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0019\u00100\u001a\u0004\u0018\u00018\u00008F¢\u0006\f\u0012\u0004\b1\u0010-\u001a\u0004\b2\u0010/¨\u00067"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastChannelImpl;", "E", "Lkotlinx/coroutines/channels/BufferedChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "capacity", "", "<init>", "(I)V", "getCapacity", "()I", "lock", "Lkotlinx/coroutines/internal/ReentrantLock;", "Ljava/util/concurrent/locks/ReentrantLock;", "Ljava/util/concurrent/locks/ReentrantLock;", "subscribers", "", "lastConflatedElement", "", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "removeSubscriber", "", "s", "send", "element", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "trySend", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "registerSelectForSend", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "onSendInternalResult", "Ljava/util/HashMap;", "close", "", "cause", "", "cancelImpl", "cancelImpl$kotlinx_coroutines_core", "isClosedForSend", "()Z", "value", "getValue$annotations", "()V", "getValue", "()Ljava/lang/Object;", "valueOrNull", "getValueOrNull$annotations", "getValueOrNull", "toString", "", "SubscriberBuffered", "SubscriberConflated", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: BroadcastChannel.kt */
public final class BroadcastChannelImpl<E> extends BufferedChannel<E> implements BroadcastChannel<E> {
    private final int capacity;
    private Object lastConflatedElement;
    /* access modifiers changed from: private */
    public final ReentrantLock lock;
    /* access modifiers changed from: private */
    public final HashMap<SelectInstance<?>, Object> onSendInternalResult;
    private List<? extends BufferedChannel<E>> subscribers;

    public static /* synthetic */ void getValue$annotations() {
    }

    public static /* synthetic */ void getValueOrNull$annotations() {
    }

    public final int getCapacity() {
        return this.capacity;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BroadcastChannelImpl(int capacity2) {
        super(0, (Function1) null);
        boolean z = false;
        this.capacity = capacity2;
        if ((this.capacity >= 1 || this.capacity == -1) ? true : z) {
            this.lock = new ReentrantLock();
            this.subscribers = CollectionsKt.emptyList();
            this.lastConflatedElement = BroadcastChannelKt.NO_ELEMENT;
            this.onSendInternalResult = new HashMap<>();
            return;
        }
        throw new IllegalArgumentException(("BroadcastChannel capacity must be positive or Channel.CONFLATED, but " + this.capacity + " was specified").toString());
    }

    public ReceiveChannel<E> openSubscription() {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            BufferedChannel s = this.capacity == -1 ? new SubscriberConflated() : new SubscriberBuffered();
            if (!isClosedForSend() || this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT) {
                if (this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT) {
                    s.m1530trySendJP2dKIU(getValue());
                }
                this.subscribers = CollectionsKt.plus(this.subscribers, s);
                lock2.unlock();
                return s;
            }
            s.close(getCloseCause());
            return s;
        } finally {
            lock2.unlock();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: kotlinx.coroutines.channels.BufferedChannel} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void removeSubscriber(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r15) {
        /*
            r14 = this;
            java.util.concurrent.locks.ReentrantLock r0 = r14.lock
            r1 = 0
            r2 = r0
            java.util.concurrent.locks.Lock r2 = (java.util.concurrent.locks.Lock) r2
            r2.lock()
            r3 = 0
            java.util.List<? extends kotlinx.coroutines.channels.BufferedChannel<E>> r4 = r14.subscribers     // Catch:{ all -> 0x0042 }
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch:{ all -> 0x0042 }
            r5 = 0
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x0042 }
            r6.<init>()     // Catch:{ all -> 0x0042 }
            java.util.Collection r6 = (java.util.Collection) r6     // Catch:{ all -> 0x0042 }
            r7 = r4
            r8 = 0
            java.util.Iterator r9 = r7.iterator()     // Catch:{ all -> 0x0042 }
        L_0x001c:
            boolean r10 = r9.hasNext()     // Catch:{ all -> 0x0042 }
            if (r10 == 0) goto L_0x0035
            java.lang.Object r10 = r9.next()     // Catch:{ all -> 0x0042 }
            r11 = r10
            kotlinx.coroutines.channels.BufferedChannel r11 = (kotlinx.coroutines.channels.BufferedChannel) r11     // Catch:{ all -> 0x0042 }
            r12 = 0
            if (r11 == r15) goto L_0x002e
            r13 = 1
            goto L_0x002f
        L_0x002e:
            r13 = 0
        L_0x002f:
            if (r13 == 0) goto L_0x001c
            r6.add(r10)     // Catch:{ all -> 0x0042 }
            goto L_0x001c
        L_0x0035:
            java.util.List r6 = (java.util.List) r6     // Catch:{ all -> 0x0042 }
            r14.subscribers = r6     // Catch:{ all -> 0x0042 }
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0042 }
            r2.unlock()
            return
        L_0x0042:
            r3 = move-exception
            r2.unlock()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BroadcastChannelImpl.removeSubscriber(kotlinx.coroutines.channels.ReceiveChannel):void");
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object send(E r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.BroadcastChannelImpl$send$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.BroadcastChannelImpl$send$1 r0 = (kotlinx.coroutines.channels.BroadcastChannelImpl$send$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.BroadcastChannelImpl$send$1 r0 = new kotlinx.coroutines.channels.BroadcastChannelImpl$send$1
            r0.<init>(r10, r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003f;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x002c:
            r11 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r0.L$1
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.BroadcastChannelImpl r6 = (kotlinx.coroutines.channels.BroadcastChannelImpl) r6
            kotlin.ResultKt.throwOnFailure(r1)
            r7 = r3
            r3 = r2
            r2 = r1
            goto L_0x008e
        L_0x003f:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r10
            java.util.concurrent.locks.ReentrantLock r4 = r3.lock
            r5 = 0
            r6 = r4
            java.util.concurrent.locks.Lock r6 = (java.util.concurrent.locks.Lock) r6
            r6.lock()
            r4 = 0
            boolean r7 = r3.isClosedForSend()     // Catch:{ all -> 0x00af }
            if (r7 != 0) goto L_0x00aa
            int r7 = r3.capacity     // Catch:{ all -> 0x00af }
            r8 = -1
            if (r7 != r8) goto L_0x005a
            r3.lastConflatedElement = r11     // Catch:{ all -> 0x00af }
        L_0x005a:
            java.util.List<? extends kotlinx.coroutines.channels.BufferedChannel<E>> r7 = r3.subscribers     // Catch:{ all -> 0x00af }
            r6.unlock()
            r4 = r7
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            r5 = 0
            java.util.Iterator r6 = r4.iterator()
            r4 = r5
            r5 = r11
            r11 = r4
            r4 = r6
            r6 = r3
        L_0x006d:
            boolean r3 = r4.hasNext()
            if (r3 == 0) goto L_0x00a6
            java.lang.Object r3 = r4.next()
            kotlinx.coroutines.channels.BufferedChannel r3 = (kotlinx.coroutines.channels.BufferedChannel) r3
            r7 = 0
            r0.L$0 = r6
            r0.L$1 = r5
            r0.L$2 = r4
            r8 = 1
            r0.label = r8
            java.lang.Object r3 = r3.sendBroadcast$kotlinx_coroutines_core(r5, r0)
            if (r3 != r2) goto L_0x008a
            return r2
        L_0x008a:
            r9 = r2
            r2 = r1
            r1 = r3
            r3 = r9
        L_0x008e:
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x00a2
            boolean r8 = r6.isClosedForSend()
            if (r8 != 0) goto L_0x009d
            goto L_0x00a2
        L_0x009d:
            java.lang.Throwable r3 = r6.getSendException()
            throw r3
        L_0x00a2:
            r1 = r2
            r2 = r3
            goto L_0x006d
        L_0x00a6:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L_0x00aa:
            java.lang.Throwable r2 = r3.getSendException()     // Catch:{ all -> 0x00af }
            throw r2     // Catch:{ all -> 0x00af }
        L_0x00af:
            r11 = move-exception
            r6.unlock()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BroadcastChannelImpl.send(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public Object m1520trySendJP2dKIU(E element) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (isClosedForSend()) {
                return super.m1530trySendJP2dKIU(element);
            }
            Iterable $this$any$iv = this.subscribers;
            boolean shouldSuspend = false;
            if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                Iterator it = $this$any$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((BufferedChannel) it.next()).shouldSendSuspend$kotlinx_coroutines_core()) {
                            shouldSuspend = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (shouldSuspend) {
                Object r4 = ChannelResult.Companion.m1554failurePtdJZtk();
                lock2.unlock();
                return r4;
            }
            if (this.capacity == -1) {
                this.lastConflatedElement = element;
            }
            for (BufferedChannel it2 : this.subscribers) {
                it2.m1530trySendJP2dKIU(element);
            }
            Object r42 = ChannelResult.Companion.m1555successJP2dKIU(Unit.INSTANCE);
            lock2.unlock();
            return r42;
        } finally {
            lock2.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void registerSelectForSend(SelectInstance<?> select, Object element) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            Object result = this.onSendInternalResult.remove(select);
            if (result != null) {
                select.selectInRegistrationPhase(result);
                return;
            }
            Unit unit = Unit.INSTANCE;
            lock2.unlock();
            Job unused = BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(select.getContext()), (CoroutineContext) null, CoroutineStart.UNDISPATCHED, new BroadcastChannelImpl$registerSelectForSend$2(this, element, select, (Continuation<? super BroadcastChannelImpl$registerSelectForSend$2>) null), 1, (Object) null);
        } finally {
            lock2.unlock();
        }
    }

    public boolean close(Throwable cause) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            for (BufferedChannel it : this.subscribers) {
                it.close(cause);
            }
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : this.subscribers) {
                if (((BufferedChannel) element$iv$iv).hasElements$kotlinx_coroutines_core()) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            this.subscribers = (List) destination$iv$iv;
            return super.close(cause);
        } finally {
            lock2.unlock();
        }
    }

    public boolean cancelImpl$kotlinx_coroutines_core(Throwable cause) {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            for (BufferedChannel it : this.subscribers) {
                it.cancelImpl$kotlinx_coroutines_core(cause);
            }
            this.lastConflatedElement = BroadcastChannelKt.NO_ELEMENT;
            return super.cancelImpl$kotlinx_coroutines_core(cause);
        } finally {
            lock2.unlock();
        }
    }

    public boolean isClosedForSend() {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            return super.isClosedForSend();
        } finally {
            lock2.unlock();
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\b"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastChannelImpl$SubscriberBuffered;", "Lkotlinx/coroutines/channels/BufferedChannel;", "<init>", "(Lkotlinx/coroutines/channels/BroadcastChannelImpl;)V", "cancelImpl", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: BroadcastChannel.kt */
    private final class SubscriberBuffered extends BufferedChannel<E> {
        public SubscriberBuffered() {
            super(BroadcastChannelImpl.this.getCapacity(), (Function1) null, 2, (DefaultConstructorMarker) null);
        }

        /* renamed from: cancelImpl */
        public boolean cancelImpl$kotlinx_coroutines_core(Throwable cause) {
            ReentrantLock $this$withLock$iv = BroadcastChannelImpl.this.lock;
            BroadcastChannelImpl<E> broadcastChannelImpl = BroadcastChannelImpl.this;
            Lock lock = $this$withLock$iv;
            lock.lock();
            try {
                broadcastChannelImpl.removeSubscriber(this);
                return super.cancelImpl$kotlinx_coroutines_core(cause);
            } finally {
                lock.unlock();
            }
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\b"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastChannelImpl$SubscriberConflated;", "Lkotlinx/coroutines/channels/ConflatedBufferedChannel;", "<init>", "(Lkotlinx/coroutines/channels/BroadcastChannelImpl;)V", "cancelImpl", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: BroadcastChannel.kt */
    private final class SubscriberConflated extends ConflatedBufferedChannel<E> {
        public SubscriberConflated() {
            super(1, BufferOverflow.DROP_OLDEST, (Function1) null, 4, (DefaultConstructorMarker) null);
        }

        /* renamed from: cancelImpl */
        public boolean cancelImpl$kotlinx_coroutines_core(Throwable cause) {
            BroadcastChannelImpl.this.removeSubscriber(this);
            return super.cancelImpl$kotlinx_coroutines_core(cause);
        }
    }

    public final E getValue() {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (isClosedForSend()) {
                Throwable closeCause = getCloseCause();
                if (closeCause == null) {
                    closeCause = new IllegalStateException("This broadcast channel is closed");
                }
                throw closeCause;
            } else if (this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT) {
                return this.lastConflatedElement;
            } else {
                throw new IllegalStateException("No value".toString());
            }
        } finally {
            lock2.unlock();
        }
    }

    public final E getValueOrNull() {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            E e = null;
            if (!isClosedForReceive()) {
                if (this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT) {
                    e = this.lastConflatedElement;
                }
            }
            return e;
        } finally {
            lock2.unlock();
        }
    }

    public String toString() {
        return (this.lastConflatedElement != BroadcastChannelKt.NO_ELEMENT ? "CONFLATED_ELEMENT=" + this.lastConflatedElement + "; " : "") + "BROADCAST=<" + super.toString() + ">; SUBSCRIBERS=" + CollectionsKt.joinToString$default(this.subscribers, ";", "<", ">", 0, (CharSequence) null, (Function1) null, 56, (Object) null);
    }
}
