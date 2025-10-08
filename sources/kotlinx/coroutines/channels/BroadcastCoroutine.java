package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005B%\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\u0012\u0010\u0015\u001a\u00020\n2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0007J\u001b\u0010\u0015\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0018\u00010\u0019j\u0004\u0018\u0001`\u0018¢\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0015\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0003H\u0014¢\u0006\u0002\u0010\u001eJ\u0018\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\nH\u0014J\u0012\u0010!\u001a\u00020\n2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J.\u0010\"\u001a\u00020\u00032#\u0010#\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0017¢\u0006\f\b%\u0012\b\b&\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00030$H\u0001J\u0011\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00028\u0000H\u0001J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00028\u00000*H\u0001J\u0011\u0010+\u001a\u00020\u00032\u0006\u0010(\u001a\u00028\u0000HAJ\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00030-2\u0006\u0010(\u001a\u00028\u0000H\u0001R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\n8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00128VX\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000b\u0010.\u001a\u00020\n8\u0016X\u0005R\u001b\u0010/\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001200X\u0005¨\u00061"}, d2 = {"Lkotlinx/coroutines/channels/BroadcastCoroutine;", "E", "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "_channel", "active", "", "<init>", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/BroadcastChannel;Z)V", "get_channel", "()Lkotlinx/coroutines/channels/BroadcastChannel;", "isActive", "()Z", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "getChannel", "()Lkotlinx/coroutines/channels/SendChannel;", "cancel", "cause", "", "Lkotlinx/coroutines/CancellationException;", "Ljava/util/concurrent/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "onCompleted", "value", "(Lkotlin/Unit;)V", "onCancelled", "handled", "close", "invokeOnClose", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "offer", "element", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "send", "trySend", "Lkotlinx/coroutines/channels/ChannelResult;", "isClosedForSend", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Broadcast.kt */
class BroadcastCoroutine<E> extends AbstractCoroutine<Unit> implements ProducerScope<E>, BroadcastChannel<E> {
    private final BroadcastChannel<E> _channel;

    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return this._channel.getOnSend();
    }

    public void invokeOnClose(Function1<? super Throwable, Unit> function1) {
        this._channel.invokeOnClose(function1);
    }

    public boolean isClosedForSend() {
        return this._channel.isClosedForSend();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E e) {
        return this._channel.offer(e);
    }

    public ReceiveChannel<E> openSubscription() {
        return this._channel.openSubscription();
    }

    public Object send(E e, Continuation<? super Unit> continuation) {
        return this._channel.send(e, continuation);
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public Object m1521trySendJP2dKIU(E e) {
        return this._channel.m1565trySendJP2dKIU(e);
    }

    /* access modifiers changed from: protected */
    public final BroadcastChannel<E> get_channel() {
        return this._channel;
    }

    public BroadcastCoroutine(CoroutineContext parentContext, BroadcastChannel<E> _channel2, boolean active) {
        super(parentContext, false, active);
        this._channel = _channel2;
        initParentJob((Job) parentContext.get(Job.Key));
    }

    public boolean isActive() {
        return super.isActive();
    }

    public SendChannel<E> getChannel() {
        return this;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public final /* synthetic */ boolean cancel(Throwable cause) {
        Throwable th;
        if (cause == null) {
            th = new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
        } else {
            th = cause;
        }
        cancelInternal(th);
        return true;
    }

    public final void cancel(CancellationException cause) {
        CancellationException cancellationException;
        if (cause == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
        } else {
            cancellationException = cause;
        }
        cancelInternal(cancellationException);
    }

    public void cancelInternal(Throwable cause) {
        CancellationException exception = JobSupport.toCancellationException$default(this, cause, (String) null, 1, (Object) null);
        this._channel.cancel(exception);
        cancelCoroutine(exception);
    }

    /* access modifiers changed from: protected */
    public void onCompleted(Unit value) {
        SendChannel.DefaultImpls.close$default(this._channel, (Throwable) null, 1, (Object) null);
    }

    /* access modifiers changed from: protected */
    public void onCancelled(Throwable cause, boolean handled) {
        if (!this._channel.close(cause) && !handled) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), cause);
        }
    }

    public boolean close(Throwable cause) {
        boolean result = this._channel.close(cause);
        start();
        return result;
    }
}
