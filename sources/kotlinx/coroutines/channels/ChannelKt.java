package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.ChannelResult;

@Metadata(d1 = {"\u00008\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aR\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022#\u0010\u0003\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u0002H\u00010\u0004H\b\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\t\u0010\n\u001aV\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0002\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022!\u0010\f\u001a\u001d\u0012\u0013\u0012\u0011H\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u000e0\u0004H\b\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u000f\u0010\n\u001aX\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0002\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022#\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000e0\u0004H\b\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0010\u0010\n\u001aX\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0002\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022#\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000e0\u0004H\b\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0012\u0010\n\u001a>\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0014\"\u0004\b\u0000\u0010\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0016\b\u0002\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0004\u001a\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0014\"\u0004\b\u0000\u0010\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007¨\u0006\u001b"}, d2 = {"getOrElse", "T", "Lkotlinx/coroutines/channels/ChannelResult;", "onFailure", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "exception", "getOrElse-WpGqRn0", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "onSuccess", "action", "value", "", "onSuccess-WpGqRn0", "onFailure-WpGqRn0", "onClosed", "onClosed-WpGqRn0", "Channel", "Lkotlinx/coroutines/channels/Channel;", "E", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "onUndeliveredElement", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Channel.kt */
public final class ChannelKt {
    /* renamed from: getOrElse-WpGqRn0  reason: not valid java name */
    public static final <T> T m1536getOrElseWpGqRn0(Object $this$getOrElse_u2dWpGqRn0, Function1<? super Throwable, ? extends T> onFailure) {
        return $this$getOrElse_u2dWpGqRn0 instanceof ChannelResult.Failed ? onFailure.invoke(ChannelResult.m1544exceptionOrNullimpl($this$getOrElse_u2dWpGqRn0)) : $this$getOrElse_u2dWpGqRn0;
    }

    /* renamed from: onSuccess-WpGqRn0  reason: not valid java name */
    public static final <T> Object m1539onSuccessWpGqRn0(Object $this$onSuccess_u2dWpGqRn0, Function1<? super T, Unit> action) {
        if (!($this$onSuccess_u2dWpGqRn0 instanceof ChannelResult.Failed)) {
            action.invoke($this$onSuccess_u2dWpGqRn0);
        }
        return $this$onSuccess_u2dWpGqRn0;
    }

    /* renamed from: onFailure-WpGqRn0  reason: not valid java name */
    public static final <T> Object m1538onFailureWpGqRn0(Object $this$onFailure_u2dWpGqRn0, Function1<? super Throwable, Unit> action) {
        if ($this$onFailure_u2dWpGqRn0 instanceof ChannelResult.Failed) {
            action.invoke(ChannelResult.m1544exceptionOrNullimpl($this$onFailure_u2dWpGqRn0));
        }
        return $this$onFailure_u2dWpGqRn0;
    }

    /* renamed from: onClosed-WpGqRn0  reason: not valid java name */
    public static final <T> Object m1537onClosedWpGqRn0(Object $this$onClosed_u2dWpGqRn0, Function1<? super Throwable, Unit> action) {
        if ($this$onClosed_u2dWpGqRn0 instanceof ChannelResult.Closed) {
            action.invoke(ChannelResult.m1544exceptionOrNullimpl($this$onClosed_u2dWpGqRn0));
        }
        return $this$onClosed_u2dWpGqRn0;
    }

    public static /* synthetic */ Channel Channel$default(int i, BufferOverflow bufferOverflow, Function1 function1, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        if ((i2 & 2) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        if ((i2 & 4) != 0) {
            function1 = null;
        }
        return Channel(i, bufferOverflow, function1);
    }

    public static final <E> Channel<E> Channel(int capacity, BufferOverflow onBufferOverflow, Function1<? super E, Unit> onUndeliveredElement) {
        BufferedChannel bufferedChannel;
        BufferedChannel bufferedChannel2;
        BufferedChannel bufferedChannel3;
        boolean z = false;
        switch (capacity) {
            case -2:
                if (onBufferOverflow == BufferOverflow.SUSPEND) {
                    bufferedChannel = new BufferedChannel(Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core(), onUndeliveredElement);
                } else {
                    bufferedChannel = new ConflatedBufferedChannel(1, onBufferOverflow, onUndeliveredElement);
                }
                return bufferedChannel;
            case -1:
                if (onBufferOverflow == BufferOverflow.SUSPEND) {
                    z = true;
                }
                if (z) {
                    return new ConflatedBufferedChannel<>(1, BufferOverflow.DROP_OLDEST, onUndeliveredElement);
                }
                throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
            case 0:
                if (onBufferOverflow == BufferOverflow.SUSPEND) {
                    bufferedChannel2 = new BufferedChannel(0, onUndeliveredElement);
                } else {
                    bufferedChannel2 = new ConflatedBufferedChannel(1, onBufferOverflow, onUndeliveredElement);
                }
                return bufferedChannel2;
            case Integer.MAX_VALUE:
                return new BufferedChannel<>(Integer.MAX_VALUE, onUndeliveredElement);
            default:
                if (onBufferOverflow == BufferOverflow.SUSPEND) {
                    bufferedChannel3 = new BufferedChannel(capacity, onUndeliveredElement);
                } else {
                    bufferedChannel3 = new ConflatedBufferedChannel(capacity, onBufferOverflow, onUndeliveredElement);
                }
                return bufferedChannel3;
        }
    }

    public static /* synthetic */ Channel Channel$default(int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return Channel$default(i, (BufferOverflow) null, (Function1) null, 6, (Object) null);
    }
}
