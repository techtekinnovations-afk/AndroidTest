package kotlinx.coroutines.channels;

import com.google.common.util.concurrent.Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00000\u0002B7\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00028\u0000H\u0000¢\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001b\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\tH\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\tH\u0000¢\u0006\u0004\b\u001f\u0010\u001dJ\u0015\u0010 \u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0000¢\u0006\u0002\b!J\u001a\u0010\"\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010\u0011H\u0002J\u0017\u0010$\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0017\u001a\u00020\tH\u0000¢\u0006\u0002\b%J\u001f\u0010&\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010\u0011H\u0000¢\u0006\u0002\b'J)\u0010(\u001a\u00020)2\u0006\u0010\u0017\u001a\u00020\t2\b\u0010*\u001a\u0004\u0018\u00010\u00112\b\u0010+\u001a\u0004\u0018\u00010\u0011H\u0000¢\u0006\u0002\b,J!\u0010-\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0017\u001a\u00020\t2\b\u0010.\u001a\u0004\u0018\u00010\u0011H\u0000¢\u0006\u0002\b/J\"\u00100\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\b\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u000204H\u0016J\u0016\u00105\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\u0006\u00106\u001a\u00020)R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007X\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00078F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0010X\u0004R\u0014\u0010\u0012\u001a\u00020\t8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u00067"}, d2 = {"Lkotlinx/coroutines/channels/ChannelSegment;", "E", "Lkotlinx/coroutines/internal/Segment;", "id", "", "prev", "channel", "Lkotlinx/coroutines/channels/BufferedChannel;", "pointers", "", "<init>", "(JLkotlinx/coroutines/channels/ChannelSegment;Lkotlinx/coroutines/channels/BufferedChannel;I)V", "_channel", "getChannel", "()Lkotlinx/coroutines/channels/BufferedChannel;", "data", "Lkotlinx/atomicfu/AtomicArray;", "", "numberOfSlots", "getNumberOfSlots", "()I", "storeElement", "", "index", "element", "storeElement$kotlinx_coroutines_core", "(ILjava/lang/Object;)V", "getElement", "getElement$kotlinx_coroutines_core", "(I)Ljava/lang/Object;", "retrieveElement", "retrieveElement$kotlinx_coroutines_core", "cleanElement", "cleanElement$kotlinx_coroutines_core", "setElementLazy", "value", "getState", "getState$kotlinx_coroutines_core", "setState", "setState$kotlinx_coroutines_core", "casState", "", "from", "to", "casState$kotlinx_coroutines_core", "getAndSetState", "update", "getAndSetState$kotlinx_coroutines_core", "onCancellation", "cause", "", "context", "Lkotlin/coroutines/CoroutineContext;", "onCancelledRequest", "receiver", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: BufferedChannel.kt */
public final class ChannelSegment<E> extends Segment<ChannelSegment<E>> {
    private final BufferedChannel<E> _channel;
    private final /* synthetic */ AtomicReferenceArray data = new AtomicReferenceArray(BufferedChannelKt.SEGMENT_SIZE * 2);

    private final /* synthetic */ AtomicReferenceArray getData() {
        return this.data;
    }

    public ChannelSegment(long id, ChannelSegment<E> prev, BufferedChannel<E> channel, int pointers) {
        super(id, prev, pointers);
        this._channel = channel;
    }

    public final BufferedChannel<E> getChannel() {
        BufferedChannel<E> bufferedChannel = this._channel;
        Intrinsics.checkNotNull(bufferedChannel);
        return bufferedChannel;
    }

    public int getNumberOfSlots() {
        return BufferedChannelKt.SEGMENT_SIZE;
    }

    public final void storeElement$kotlinx_coroutines_core(int index, E element) {
        setElementLazy(index, element);
    }

    public final E getElement$kotlinx_coroutines_core(int index) {
        return getData().get(index * 2);
    }

    public final E retrieveElement$kotlinx_coroutines_core(int index) {
        E element$kotlinx_coroutines_core = getElement$kotlinx_coroutines_core(index);
        E e = element$kotlinx_coroutines_core;
        cleanElement$kotlinx_coroutines_core(index);
        return element$kotlinx_coroutines_core;
    }

    public final void cleanElement$kotlinx_coroutines_core(int index) {
        setElementLazy(index, (Object) null);
    }

    private final void setElementLazy(int index, Object value) {
        getData().set(index * 2, value);
    }

    public final Object getState$kotlinx_coroutines_core(int index) {
        return getData().get((index * 2) + 1);
    }

    public final void setState$kotlinx_coroutines_core(int index, Object value) {
        getData().set((index * 2) + 1, value);
    }

    public final boolean casState$kotlinx_coroutines_core(int index, Object from, Object to) {
        return Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(getData(), (index * 2) + 1, from, to);
    }

    public final Object getAndSetState$kotlinx_coroutines_core(int index, Object update) {
        return getData().getAndSet((index * 2) + 1, update);
    }

    public void onCancellation(int index, Throwable cause, CoroutineContext context) {
        Function1<E, Unit> function1;
        Function1<E, Unit> function12;
        boolean z = true;
        boolean isSender = index >= BufferedChannelKt.SEGMENT_SIZE;
        int index2 = isSender ? index - BufferedChannelKt.SEGMENT_SIZE : index;
        Object element = getElement$kotlinx_coroutines_core(index2);
        while (true) {
            Object cur = getState$kotlinx_coroutines_core(index2);
            if ((cur instanceof Waiter) || (cur instanceof WaiterEB)) {
                if (casState$kotlinx_coroutines_core(index2, cur, isSender ? BufferedChannelKt.INTERRUPTED_SEND : BufferedChannelKt.INTERRUPTED_RCV)) {
                    cleanElement$kotlinx_coroutines_core(index2);
                    if (isSender) {
                        z = false;
                    }
                    onCancelledRequest(index2, z);
                    if (isSender && (function1 = getChannel().onUndeliveredElement) != null) {
                        OnUndeliveredElementKt.callUndeliveredElement(function1, element, context);
                        return;
                    }
                    return;
                }
            } else if (cur == BufferedChannelKt.INTERRUPTED_SEND || cur == BufferedChannelKt.INTERRUPTED_RCV) {
                cleanElement$kotlinx_coroutines_core(index2);
            } else if (!(cur == BufferedChannelKt.RESUMING_BY_EB || cur == BufferedChannelKt.RESUMING_BY_RCV)) {
                if (cur != BufferedChannelKt.DONE_RCV && cur != BufferedChannelKt.BUFFERED && cur != BufferedChannelKt.getCHANNEL_CLOSED()) {
                    throw new IllegalStateException(("unexpected state: " + cur).toString());
                }
                return;
            }
        }
        cleanElement$kotlinx_coroutines_core(index2);
        if (isSender && (function12 = getChannel().onUndeliveredElement) != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function12, element, context);
        }
    }

    public final void onCancelledRequest(int index, boolean receiver) {
        if (receiver) {
            getChannel().waitExpandBufferCompletion$kotlinx_coroutines_core((this.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) + ((long) index));
        }
        onSlotCleaned();
    }
}
