package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: OnTimeout.kt */
/* synthetic */ class OnTimeout$selectClause$1 extends FunctionReferenceImpl implements Function3<OnTimeout, SelectInstance<?>, Object, Unit> {
    public static final OnTimeout$selectClause$1 INSTANCE = new OnTimeout$selectClause$1();

    OnTimeout$selectClause$1() {
        super(3, OnTimeout.class, "register", "register(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2, Object p3) {
        invoke((OnTimeout) p1, (SelectInstance<?>) (SelectInstance) p2, p3);
        return Unit.INSTANCE;
    }

    public final void invoke(OnTimeout p0, SelectInstance<?> p1, Object p2) {
        p0.register(p1, p2);
    }
}
