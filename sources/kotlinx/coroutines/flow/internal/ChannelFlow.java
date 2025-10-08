package kotlinx.coroutines.flow.internal;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u0016\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0017H\u0016J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u00172\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J&\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH$J\u001c\u0010\u001a\u001a\u00020\u000f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\rH¤@¢\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010\u001b\u001a\u00020\u001fH\u0016J\u001c\u0010 \u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\"H@¢\u0006\u0002\u0010#J\n\u0010$\u001a\u0004\u0018\u00010%H\u0014J\b\u0010&\u001a\u00020%H\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0004¢\u0006\u0002\n\u0000R6\u0010\u000b\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f8@X\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00068@X\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006'"}, d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlow;", "T", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "<init>", "(Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "collectToFun", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "getCollectToFun$kotlinx_coroutines_core", "()Lkotlin/jvm/functions/Function2;", "produceCapacity", "getProduceCapacity$kotlinx_coroutines_core", "()I", "dropChannelOperators", "Lkotlinx/coroutines/flow/Flow;", "fuse", "create", "collectTo", "scope", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "collect", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "additionalToStringProps", "", "toString", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public abstract class ChannelFlow<T> implements FusibleFlow<T> {
    public final int capacity;
    public final CoroutineContext context;
    public final BufferOverflow onBufferOverflow;

    public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return collect$suspendImpl(this, flowCollector, continuation);
    }

    /* access modifiers changed from: protected */
    public abstract Object collectTo(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation);

    /* access modifiers changed from: protected */
    public abstract ChannelFlow<T> create(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow);

    public ChannelFlow(CoroutineContext context2, int capacity2, BufferOverflow onBufferOverflow2) {
        this.context = context2;
        this.capacity = capacity2;
        this.onBufferOverflow = onBufferOverflow2;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(this.capacity != -1)) {
                throw new AssertionError();
            }
        }
    }

    public final Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> getCollectToFun$kotlinx_coroutines_core() {
        return new ChannelFlow$collectToFun$1(this, (Continuation<? super ChannelFlow$collectToFun$1>) null);
    }

    public final int getProduceCapacity$kotlinx_coroutines_core() {
        if (this.capacity == -3) {
            return -2;
        }
        return this.capacity;
    }

    public Flow<T> dropChannelOperators() {
        return null;
    }

    public Flow<T> fuse(CoroutineContext context2, int capacity2, BufferOverflow onBufferOverflow2) {
        BufferOverflow newOverflow;
        int newCapacity;
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((capacity2 != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        CoroutineContext newContext = context2.plus(this.context);
        if (onBufferOverflow2 != BufferOverflow.SUSPEND) {
            newCapacity = capacity2;
            newOverflow = onBufferOverflow2;
        } else {
            if (this.capacity != -3) {
                if (capacity2 == -3) {
                    newCapacity = this.capacity;
                } else if (this.capacity != -2) {
                    if (capacity2 == -2) {
                        newCapacity = this.capacity;
                    } else {
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if ((this.capacity >= 0 ? 1 : 0) == 0) {
                                throw new AssertionError();
                            }
                        }
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if (capacity2 < 0) {
                                z = false;
                            }
                            if (!z) {
                                throw new AssertionError();
                            }
                        }
                        newCapacity = this.capacity + capacity2;
                        if (newCapacity < 0) {
                            newCapacity = Integer.MAX_VALUE;
                        }
                    }
                }
                newOverflow = this.onBufferOverflow;
            }
            newCapacity = capacity2;
            newOverflow = this.onBufferOverflow;
        }
        if (Intrinsics.areEqual((Object) newContext, (Object) this.context) && newCapacity == this.capacity && newOverflow == this.onBufferOverflow) {
            return this;
        }
        return create(newContext, newCapacity, newOverflow);
    }

    public ReceiveChannel<T> produceImpl(CoroutineScope scope) {
        return ProduceKt.produce$default(scope, this.context, getProduceCapacity$kotlinx_coroutines_core(), this.onBufferOverflow, CoroutineStart.ATOMIC, (Function1) null, getCollectToFun$kotlinx_coroutines_core(), 16, (Object) null);
    }

    static /* synthetic */ <T> Object collect$suspendImpl(ChannelFlow<T> $this, FlowCollector<? super T> collector, Continuation<? super Unit> $completion) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new ChannelFlow$collect$2(collector, $this, (Continuation<? super ChannelFlow$collect$2>) null), $completion);
        return coroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutineScope : Unit.INSTANCE;
    }

    /* access modifiers changed from: protected */
    public String additionalToStringProps() {
        return null;
    }

    public String toString() {
        ArrayList props = new ArrayList(4);
        String it = additionalToStringProps();
        if (it != null) {
            props.add(it);
        }
        if (this.context != EmptyCoroutineContext.INSTANCE) {
            props.add("context=" + this.context);
        }
        if (this.capacity != -3) {
            props.add("capacity=" + this.capacity);
        }
        if (this.onBufferOverflow != BufferOverflow.SUSPEND) {
            props.add("onBufferOverflow=" + this.onBufferOverflow);
        }
        return DebugStringsKt.getClassSimpleName(this) + '[' + CollectionsKt.joinToString$default(props, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) + ']';
    }
}
