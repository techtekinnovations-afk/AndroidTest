package kotlinx.coroutines.channels;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B;\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\"\b\u0002\u0010\u0007\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\b¢\u0006\u0004\b\u000b\u0010\fJ\u0016\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00028\u0000H@¢\u0006\u0004\b\u0014\u0010\u0012J\u001d\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u00162\u0006\u0010\u0011\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J%\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\n0\u00162\u0006\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u000eH\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ%\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\u00162\u0006\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u000eH\u0002¢\u0006\u0004\b\u001e\u0010\u001cJ\u001e\u0010\u001f\u001a\u00020\n2\n\u0010 \u001a\u0006\u0012\u0002\b\u00030!2\b\u0010\u0011\u001a\u0004\u0018\u00010\"H\u0014J\r\u0010#\u001a\u00020\u000eH\u0010¢\u0006\u0002\b$R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8TX\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000f¨\u0006%"}, d2 = {"Lkotlinx/coroutines/channels/ConflatedBufferedChannel;", "E", "Lkotlinx/coroutines/channels/BufferedChannel;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "onUndeliveredElement", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "Lkotlin/Function1;", "", "<init>", "(ILkotlinx/coroutines/channels/BufferOverflow;Lkotlin/jvm/functions/Function1;)V", "isConflatedDropOldest", "", "()Z", "send", "element", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendBroadcast", "sendBroadcast$kotlinx_coroutines_core", "trySend", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySendImpl", "isSendOp", "trySendImpl-Mj0NB7M", "(Ljava/lang/Object;Z)Ljava/lang/Object;", "trySendDropLatest", "trySendDropLatest-Mj0NB7M", "registerSelectForSend", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "", "shouldSendSuspend", "shouldSendSuspend$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: ConflatedBufferedChannel.kt */
public class ConflatedBufferedChannel<E> extends BufferedChannel<E> {
    private final int capacity;
    private final BufferOverflow onBufferOverflow;

    public Object send(E e, Continuation<? super Unit> continuation) {
        return send$suspendImpl(this, e, continuation);
    }

    public Object sendBroadcast$kotlinx_coroutines_core(E e, Continuation<? super Boolean> continuation) {
        return sendBroadcast$suspendImpl(this, e, continuation);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ConflatedBufferedChannel(int i, BufferOverflow bufferOverflow, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, bufferOverflow, (i2 & 4) != 0 ? null : function1);
    }

    public ConflatedBufferedChannel(int capacity2, BufferOverflow onBufferOverflow2, Function1<? super E, Unit> onUndeliveredElement) {
        super(capacity2, onUndeliveredElement);
        this.capacity = capacity2;
        this.onBufferOverflow = onBufferOverflow2;
        boolean z = false;
        if (this.onBufferOverflow != BufferOverflow.SUSPEND) {
            if (!(this.capacity >= 1 ? true : z)) {
                throw new IllegalArgumentException(("Buffered channel capacity must be at least 1, but " + this.capacity + " was specified").toString());
            }
            return;
        }
        throw new IllegalArgumentException(("This implementation does not support suspension for senders, use " + Reflection.getOrCreateKotlinClass(BufferedChannel.class).getSimpleName() + " instead").toString());
    }

    /* access modifiers changed from: protected */
    public boolean isConflatedDropOldest() {
        return this.onBufferOverflow == BufferOverflow.DROP_OLDEST;
    }

    static /* synthetic */ <E> Object send$suspendImpl(ConflatedBufferedChannel<E> $this, E element, Continuation<? super Unit> $completion) {
        UndeliveredElementException it;
        Object $this$onClosed_u2dWpGqRn0$iv = $this.m1560trySendImplMj0NB7M(element, true);
        if (!($this$onClosed_u2dWpGqRn0$iv instanceof ChannelResult.Closed)) {
            return Unit.INSTANCE;
        }
        Throwable r2 = ChannelResult.m1544exceptionOrNullimpl($this$onClosed_u2dWpGqRn0$iv);
        Function1 function1 = $this.onUndeliveredElement;
        if (function1 == null || (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
            throw $this.getSendException();
        }
        ExceptionsKt.addSuppressed(it, $this.getSendException());
        throw it;
    }

    static /* synthetic */ <E> Object sendBroadcast$suspendImpl(ConflatedBufferedChannel<E> $this, E element, Continuation<? super Boolean> $completion) {
        Object $this$onSuccess_u2dWpGqRn0$iv = $this.m1560trySendImplMj0NB7M(element, true);
        if ($this$onSuccess_u2dWpGqRn0$iv instanceof ChannelResult.Failed) {
            return Boxing.boxBoolean(false);
        }
        Unit unit = (Unit) $this$onSuccess_u2dWpGqRn0$iv;
        return Boxing.boxBoolean(true);
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public Object m1561trySendJP2dKIU(E element) {
        return m1560trySendImplMj0NB7M(element, false);
    }

    /* renamed from: trySendImpl-Mj0NB7M  reason: not valid java name */
    private final Object m1560trySendImplMj0NB7M(E element, boolean isSendOp) {
        if (this.onBufferOverflow == BufferOverflow.DROP_LATEST) {
            return m1559trySendDropLatestMj0NB7M(element, isSendOp);
        }
        return m1531trySendDropOldestJP2dKIU(element);
    }

    /* renamed from: trySendDropLatest-Mj0NB7M  reason: not valid java name */
    private final Object m1559trySendDropLatestMj0NB7M(E element, boolean isSendOp) {
        Function1 function1;
        UndeliveredElementException it;
        Object result = super.m1530trySendJP2dKIU(element);
        if (ChannelResult.m1550isSuccessimpl(result) || ChannelResult.m1548isClosedimpl(result)) {
            return result;
        }
        if (!isSendOp || (function1 = this.onUndeliveredElement) == null || (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
            return ChannelResult.Companion.m1555successJP2dKIU(Unit.INSTANCE);
        }
        throw it;
    }

    /* access modifiers changed from: protected */
    public void registerSelectForSend(SelectInstance<?> select, Object element) {
        Object $this$onSuccess_u2dWpGqRn0$iv = m1561trySendJP2dKIU(element);
        if (!($this$onSuccess_u2dWpGqRn0$iv instanceof ChannelResult.Failed)) {
            Unit unit = (Unit) $this$onSuccess_u2dWpGqRn0$iv;
            select.selectInRegistrationPhase(Unit.INSTANCE);
        } else if ($this$onSuccess_u2dWpGqRn0$iv instanceof ChannelResult.Closed) {
            Throwable r4 = ChannelResult.m1544exceptionOrNullimpl($this$onSuccess_u2dWpGqRn0$iv);
            select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
        } else {
            throw new IllegalStateException("unreachable".toString());
        }
    }

    public boolean shouldSendSuspend$kotlinx_coroutines_core() {
        return false;
    }
}
