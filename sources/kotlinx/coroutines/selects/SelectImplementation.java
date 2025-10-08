package kotlinx.coroutines.selects;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.selects.SelectBuilder;

@Metadata(d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0011\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004:\u0001IB\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\u001b\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u001cJ\u000e\u0010\u001d\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u001cJ0\u0010\u001e\u001a\u00020\u001f*\u00020 2\u001c\u0010!\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#\u0012\u0006\u0012\u0004\u0018\u00010\r0\"H\u0002¢\u0006\u0002\u0010$JB\u0010\u001e\u001a\u00020\u001f\"\u0004\b\u0001\u0010%*\b\u0012\u0004\u0012\u0002H%0&2\"\u0010!\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H%\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#\u0012\u0006\u0012\u0004\u0018\u00010\r0'H\u0002¢\u0006\u0002\u0010(JV\u0010\u001e\u001a\u00020\u001f\"\u0004\b\u0001\u0010)\"\u0004\b\u0002\u0010%*\u000e\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u0002H%0*2\u0006\u0010+\u001a\u0002H)2\"\u0010!\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H%\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#\u0012\u0006\u0012\u0004\u0018\u00010\r0'H\u0002¢\u0006\u0002\u0010,J \u0010-\u001a\u00020\u001f*\f0\u0016R\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010.\u001a\u00020\u000fH\u0001J\u0010\u0010/\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\rH\u0002J\u0010\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u000203H\u0016J\u001c\u00104\u001a\u00020\u001f2\n\u00105\u001a\u0006\u0012\u0002\b\u0003062\u0006\u00107\u001a\u00020\u0019H\u0016J\u0012\u00108\u001a\u00020\u001f2\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0016J\u000e\u00109\u001a\u00020\u001fH@¢\u0006\u0002\u0010\u001cJ\u0010\u0010:\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\rH\u0002J\u001a\u0010;\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\r2\b\u0010<\u001a\u0004\u0018\u00010\rH\u0016J\u0018\u0010=\u001a\u00020>2\u0006\u00100\u001a\u00020\r2\b\u0010<\u001a\u0004\u0018\u00010\rJ\u001a\u0010?\u001a\u00020\u00192\u0006\u00100\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0002J\u001c\u0010@\u001a\u000e\u0018\u00010\u0016R\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u00100\u001a\u00020\rH\u0002J\u000e\u0010A\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u001cJ*\u0010B\u001a\u00028\u00002\u0010\u0010C\u001a\f0\u0016R\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010\u001a\u001a\u0004\u0018\u00010\rH@¢\u0006\u0002\u0010DJ\u001a\u0010E\u001a\u00020\u001f2\u0010\u0010F\u001a\f0\u0016R\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0002J\u0012\u0010\u001e\u001a\u00020\u001f2\b\u0010G\u001a\u0004\u0018\u00010HH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0004R\u0014\u0010\u000e\u001a\u00020\u000f8BX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8BX\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\u000f8BX\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R \u0010\u0014\u001a\u0014\u0012\u000e\u0012\f0\u0016R\b\u0012\u0004\u0012\u00028\u00000\u0000\u0018\u00010\u0015X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\rX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\rX\u000e¢\u0006\u0002\n\u0000¨\u0006J"}, d2 = {"Lkotlinx/coroutines/selects/SelectImplementation;", "R", "Lkotlinx/coroutines/CancelHandler;", "Lkotlinx/coroutines/selects/SelectBuilder;", "Lkotlinx/coroutines/selects/SelectInstanceInternal;", "context", "Lkotlin/coroutines/CoroutineContext;", "<init>", "(Lkotlin/coroutines/CoroutineContext;)V", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "state", "Lkotlinx/atomicfu/AtomicRef;", "", "inRegistrationPhase", "", "getInRegistrationPhase", "()Z", "isSelected", "isCancelled", "clauses", "", "Lkotlinx/coroutines/selects/SelectImplementation$ClauseData;", "disposableHandleOrSegment", "indexInSegment", "", "internalResult", "doSelect", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "doSelectSuspend", "invoke", "", "Lkotlinx/coroutines/selects/SelectClause0;", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "register", "reregister", "checkClauseObject", "clauseObject", "disposeOnCompletion", "disposableHandle", "Lkotlinx/coroutines/DisposableHandle;", "invokeOnCancellation", "segment", "Lkotlinx/coroutines/internal/Segment;", "index", "selectInRegistrationPhase", "waitUntilSelected", "reregisterClause", "trySelect", "result", "trySelectDetailed", "Lkotlinx/coroutines/selects/TrySelectDetailedResult;", "trySelectInternal", "findClause", "complete", "processResultAndInvokeBlockRecoveringException", "clause", "(Lkotlinx/coroutines/selects/SelectImplementation$ClauseData;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cleanup", "selectedClause", "cause", "", "ClauseData", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Select.kt */
public class SelectImplementation<R> implements CancelHandler, SelectBuilder<R>, SelectInstanceInternal<R> {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater state$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(SelectImplementation.class, Object.class, "state$volatile");
    private List<SelectImplementation<R>.ClauseData> clauses = new ArrayList(2);
    private final CoroutineContext context;
    private Object disposableHandleOrSegment;
    private int indexInSegment = -1;
    /* access modifiers changed from: private */
    public Object internalResult = SelectKt.NO_RESULT;
    private volatile /* synthetic */ Object state$volatile = SelectKt.STATE_REG;

    private final /* synthetic */ Object getState$volatile() {
        return this.state$volatile;
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void setState$volatile(Object obj) {
        this.state$volatile = obj;
    }

    private final /* synthetic */ void update$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, ? extends Object> function1) {
        Object obj2;
        do {
            obj2 = atomicReferenceFieldUpdater.get(obj);
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, obj, obj2, function1.invoke(obj2)));
    }

    public Object doSelect(Continuation<? super R> continuation) {
        return doSelect$suspendImpl(this, continuation);
    }

    public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> $this$invoke, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        SelectBuilder.DefaultImpls.invoke(this, $this$invoke, block);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Replaced with the same extension function", replaceWith = @ReplaceWith(expression = "onTimeout", imports = {"kotlinx.coroutines.selects.onTimeout"}))
    public void onTimeout(long timeMillis, Function1<? super Continuation<? super R>, ? extends Object> block) {
        SelectBuilder.DefaultImpls.onTimeout(this, timeMillis, block);
    }

    public SelectImplementation(CoroutineContext context2) {
        this.context = context2;
    }

    public CoroutineContext getContext() {
        return this.context;
    }

    /* access modifiers changed from: private */
    public final boolean getInRegistrationPhase() {
        Object it = state$volatile$FU.get(this);
        return it == SelectKt.STATE_REG || (it instanceof List);
    }

    private final boolean isSelected() {
        return state$volatile$FU.get(this) instanceof ClauseData;
    }

    /* access modifiers changed from: private */
    public final boolean isCancelled() {
        return state$volatile$FU.get(this) == SelectKt.STATE_CANCELLED;
    }

    static /* synthetic */ <R> Object doSelect$suspendImpl(SelectImplementation<R> $this, Continuation<? super R> $completion) {
        if ($this.isSelected()) {
            return $this.complete($completion);
        }
        return $this.doSelectSuspend($completion);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0055 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object doSelectSuspend(kotlin.coroutines.Continuation<? super R> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof kotlinx.coroutines.selects.SelectImplementation$doSelectSuspend$1
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.selects.SelectImplementation$doSelectSuspend$1 r0 = (kotlinx.coroutines.selects.SelectImplementation$doSelectSuspend$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.selects.SelectImplementation$doSelectSuspend$1 r0 = new kotlinx.coroutines.selects.SelectImplementation$doSelectSuspend$1
            r0.<init>(r5, r6)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0039;
                case 1: goto L_0x0031;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r1
            goto L_0x0056
        L_0x0031:
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.selects.SelectImplementation r3 = (kotlinx.coroutines.selects.SelectImplementation) r3
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0049
        L_0x0039:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r5
            r0.L$0 = r3
            r4 = 1
            r0.label = r4
            java.lang.Object r4 = r3.waitUntilSelected(r0)
            if (r4 != r2) goto L_0x0049
            return r2
        L_0x0049:
            r4 = 0
            r0.L$0 = r4
            r4 = 2
            r0.label = r4
            java.lang.Object r3 = r3.complete(r0)
            if (r3 != r2) goto L_0x0056
            return r2
        L_0x0056:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.SelectImplementation.doSelectSuspend(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public void invoke(SelectClause0 $this$invoke, Function1<? super Continuation<? super R>, ? extends Object> block) {
        register$default(this, new ClauseData($this$invoke.getClauseObject(), $this$invoke.getRegFunc(), $this$invoke.getProcessResFunc(), SelectKt.getPARAM_CLAUSE_0(), block, $this$invoke.getOnCancellationConstructor()), false, 1, (Object) null);
    }

    public <Q> void invoke(SelectClause1<? extends Q> $this$invoke, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        register$default(this, new ClauseData($this$invoke.getClauseObject(), $this$invoke.getRegFunc(), $this$invoke.getProcessResFunc(), (Object) null, block, $this$invoke.getOnCancellationConstructor()), false, 1, (Object) null);
    }

    public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> $this$invoke, P param, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        register$default(this, new ClauseData($this$invoke.getClauseObject(), $this$invoke.getRegFunc(), $this$invoke.getProcessResFunc(), param, block, $this$invoke.getOnCancellationConstructor()), false, 1, (Object) null);
    }

    public static /* synthetic */ void register$default(SelectImplementation selectImplementation, ClauseData clauseData, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                z = false;
            }
            selectImplementation.register(clauseData, z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: register");
    }

    public final void register(SelectImplementation<R>.ClauseData $this$register, boolean reregister) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(state$volatile$FU.get(this) != SelectKt.STATE_CANCELLED)) {
                throw new AssertionError();
            }
        }
        if (!(state$volatile$FU.get(this) instanceof ClauseData)) {
            if (!reregister) {
                checkClauseObject($this$register.clauseObject);
            }
            if ($this$register.tryRegisterAsWaiter(this)) {
                if (!reregister) {
                    List<SelectImplementation<R>.ClauseData> list = this.clauses;
                    Intrinsics.checkNotNull(list);
                    list.add($this$register);
                }
                $this$register.disposableHandleOrSegment = this.disposableHandleOrSegment;
                $this$register.indexInSegment = this.indexInSegment;
                this.disposableHandleOrSegment = null;
                this.indexInSegment = -1;
                return;
            }
            state$volatile$FU.set(this, $this$register);
        }
    }

    private final void checkClauseObject(Object clauseObject) {
        ClauseData it;
        List<SelectImplementation<R>.ClauseData> list = this.clauses;
        Intrinsics.checkNotNull(list);
        Iterable $this$none$iv = list;
        boolean z = true;
        if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
            Iterator it2 = $this$none$iv.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (((ClauseData) it2.next()).clauseObject == clauseObject) {
                        it = 1;
                        continue;
                    } else {
                        it = null;
                        continue;
                    }
                    if (it != null) {
                        z = false;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (!z) {
            throw new IllegalStateException(("Cannot use select clauses on the same object: " + clauseObject).toString());
        }
    }

    public void disposeOnCompletion(DisposableHandle disposableHandle) {
        this.disposableHandleOrSegment = disposableHandle;
    }

    public void invokeOnCancellation(Segment<?> segment, int index) {
        this.disposableHandleOrSegment = segment;
        this.indexInSegment = index;
    }

    public void selectInRegistrationPhase(Object internalResult2) {
        this.internalResult = internalResult2;
    }

    /* access modifiers changed from: private */
    public final Object waitUntilSelected(Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = state$volatile$FU;
        while (true) {
            Object curState = handler$atomicfu$iv.get(this);
            if (curState == SelectKt.STATE_REG) {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(state$volatile$FU, this, curState, cont)) {
                    CancellableContinuationKt.invokeOnCancellation(cont, this);
                    break;
                }
            } else if (curState instanceof List) {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(state$volatile$FU, this, curState, SelectKt.STATE_REG)) {
                    List list = (List) curState;
                    for (Object element$iv : (Iterable) curState) {
                        reregisterClause(element$iv);
                    }
                }
            } else if (curState instanceof ClauseData) {
                cont.resume(Unit.INSTANCE, ((ClauseData) curState).createOnCancellationAction(this, this.internalResult));
            } else {
                throw new IllegalStateException(("unexpected state: " + curState).toString());
            }
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final void reregisterClause(Object clauseObject) {
        ClauseData clause = findClause(clauseObject);
        Intrinsics.checkNotNull(clause);
        clause.disposableHandleOrSegment = null;
        clause.indexInSegment = -1;
        register(clause, true);
    }

    public boolean trySelect(Object clauseObject, Object result) {
        return trySelectInternal(clauseObject, result) == 0;
    }

    public final TrySelectDetailedResult trySelectDetailed(Object clauseObject, Object result) {
        return SelectKt.TrySelectDetailedResult(trySelectInternal(clauseObject, result));
    }

    private final int trySelectInternal(Object clauseObject, Object internalResult2) {
        while (true) {
            Object curState = state$volatile$FU.get(this);
            if (curState instanceof CancellableContinuation) {
                ClauseData clause = findClause(clauseObject);
                if (clause == null) {
                    continue;
                } else {
                    Function3 onCancellation = clause.createOnCancellationAction(this, internalResult2);
                    if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(state$volatile$FU, this, curState, clause)) {
                        this.internalResult = internalResult2;
                        if (SelectKt.tryResume((CancellableContinuation) curState, onCancellation)) {
                            return 0;
                        }
                        this.internalResult = SelectKt.NO_RESULT;
                        return 2;
                    }
                }
            } else if (Intrinsics.areEqual(curState, (Object) SelectKt.STATE_COMPLETED) || (curState instanceof ClauseData)) {
                return 3;
            } else {
                if (Intrinsics.areEqual(curState, (Object) SelectKt.STATE_CANCELLED)) {
                    return 2;
                }
                if (Intrinsics.areEqual(curState, (Object) SelectKt.STATE_REG)) {
                    if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(state$volatile$FU, this, curState, CollectionsKt.listOf(clauseObject))) {
                        return 1;
                    }
                } else if (!(curState instanceof List)) {
                    throw new IllegalStateException(("Unexpected state: " + curState).toString());
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(state$volatile$FU, this, curState, CollectionsKt.plus((Collection) curState, clauseObject))) {
                    return 1;
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: kotlinx.coroutines.selects.SelectImplementation<R>$ClauseData} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: kotlinx.coroutines.selects.SelectImplementation<R>$ClauseData} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: kotlinx.coroutines.selects.SelectImplementation$ClauseData} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: kotlinx.coroutines.selects.SelectImplementation<R>$ClauseData} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlinx.coroutines.selects.SelectImplementation<R>.ClauseData findClause(java.lang.Object r8) {
        /*
            r7 = this;
            java.util.List<kotlinx.coroutines.selects.SelectImplementation<R>$ClauseData> r0 = r7.clauses
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r2 = r0
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.Iterator r2 = r2.iterator()
        L_0x000d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0025
            java.lang.Object r3 = r2.next()
            r4 = r3
            kotlinx.coroutines.selects.SelectImplementation$ClauseData r4 = (kotlinx.coroutines.selects.SelectImplementation.ClauseData) r4
            r5 = 0
            java.lang.Object r6 = r4.clauseObject
            if (r6 != r8) goto L_0x0021
            r6 = 1
            goto L_0x0022
        L_0x0021:
            r6 = 0
        L_0x0022:
            if (r6 == 0) goto L_0x000d
            r1 = r3
        L_0x0025:
            kotlinx.coroutines.selects.SelectImplementation$ClauseData r1 = (kotlinx.coroutines.selects.SelectImplementation.ClauseData) r1
            if (r1 == 0) goto L_0x002a
            return r1
        L_0x002a:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Clause with object "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r8)
            java.lang.String r3 = " is not found"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.SelectImplementation.findClause(java.lang.Object):kotlinx.coroutines.selects.SelectImplementation$ClauseData");
    }

    /* access modifiers changed from: private */
    public final Object complete(Continuation<? super R> $completion) {
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected() != 0) {
            Object obj = state$volatile$FU.get(this);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.selects.SelectImplementation.ClauseData<R of kotlinx.coroutines.selects.SelectImplementation>");
            ClauseData selectedClause = (ClauseData) obj;
            Object internalResult2 = this.internalResult;
            cleanup(selectedClause);
            if (!DebugKt.getRECOVER_STACK_TRACES()) {
                return selectedClause.invokeBlock(selectedClause.processResult(internalResult2), $completion);
            }
            return processResultAndInvokeBlockRecoveringException(selectedClause, internalResult2, $completion);
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c A[SYNTHETIC, Splitter:B:10:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object processResultAndInvokeBlockRecoveringException(kotlinx.coroutines.selects.SelectImplementation<R>.ClauseData r6, java.lang.Object r7, kotlin.coroutines.Continuation<? super R> r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.selects.SelectImplementation$processResultAndInvokeBlockRecoveringException$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.selects.SelectImplementation$processResultAndInvokeBlockRecoveringException$1 r0 = (kotlinx.coroutines.selects.SelectImplementation$processResultAndInvokeBlockRecoveringException$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.selects.SelectImplementation$processResultAndInvokeBlockRecoveringException$1 r0 = new kotlinx.coroutines.selects.SelectImplementation$processResultAndInvokeBlockRecoveringException$1
            r0.<init>(r5, r8)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0033;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0031 }
            r7 = r1
            goto L_0x0045
        L_0x0031:
            r6 = move-exception
            goto L_0x0047
        L_0x0033:
            kotlin.ResultKt.throwOnFailure(r1)
            java.lang.Object r3 = r6.processResult(r7)     // Catch:{ all -> 0x0031 }
            r7 = 1
            r0.label = r7     // Catch:{ all -> 0x0031 }
            java.lang.Object r7 = r6.invokeBlock(r3, r0)     // Catch:{ all -> 0x0031 }
            if (r7 != r2) goto L_0x0045
            return r2
        L_0x0045:
            return r7
        L_0x0047:
            r7 = 0
            boolean r2 = kotlinx.coroutines.DebugKt.getRECOVER_STACK_TRACES()
            if (r2 == 0) goto L_0x005d
            r2 = r0
            r3 = 0
            boolean r4 = r2 instanceof kotlin.coroutines.jvm.internal.CoroutineStackFrame
            if (r4 != 0) goto L_0x0055
            throw r6
        L_0x0055:
            r4 = r2
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r4 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r4
            java.lang.Throwable r4 = kotlinx.coroutines.internal.StackTraceRecoveryKt.recoverFromStackFrame(r6, r4)
            throw r4
        L_0x005d:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.SelectImplementation.processResultAndInvokeBlockRecoveringException(kotlinx.coroutines.selects.SelectImplementation$ClauseData, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: kotlinx.coroutines.selects.SelectImplementation<R>$ClauseData} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void cleanup(kotlinx.coroutines.selects.SelectImplementation<R>.ClauseData r8) {
        /*
            r7 = this;
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x001c
            r0 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = state$volatile$FU
            java.lang.Object r1 = r1.get(r7)
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1, (java.lang.Object) r8)
            if (r0 == 0) goto L_0x0016
            goto L_0x001c
        L_0x0016:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001c:
            java.util.List<kotlinx.coroutines.selects.SelectImplementation<R>$ClauseData> r0 = r7.clauses
            if (r0 != 0) goto L_0x0021
            return
        L_0x0021:
            r1 = r0
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            r2 = 0
            java.util.Iterator r3 = r1.iterator()
        L_0x0029:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x003f
            java.lang.Object r4 = r3.next()
            r5 = r4
            kotlinx.coroutines.selects.SelectImplementation$ClauseData r5 = (kotlinx.coroutines.selects.SelectImplementation.ClauseData) r5
            r6 = 0
            if (r5 == r8) goto L_0x003c
            r5.dispose()
        L_0x003c:
            goto L_0x0029
        L_0x003f:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = state$volatile$FU
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.selects.SelectKt.STATE_COMPLETED
            r1.set(r7, r2)
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.selects.SelectKt.NO_RESULT
            r7.internalResult = r1
            r1 = 0
            r7.clauses = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.SelectImplementation.cleanup(kotlinx.coroutines.selects.SelectImplementation$ClauseData):void");
    }

    public void invoke(Throwable cause) {
        Object cur;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = state$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            if (cur == SelectKt.STATE_COMPLETED) {
                return;
            }
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur, SelectKt.STATE_CANCELLED));
        List<ClauseData> clauses2 = this.clauses;
        if (clauses2 != null) {
            for (ClauseData it : clauses2) {
                it.dispose();
            }
            this.internalResult = SelectKt.NO_RESULT;
            this.clauses = null;
        }
    }

    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\b\b\u0004\u0018\u00002\u00020\u0001BÆ\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012U\u0010\u0003\u001aQ\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0002\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\t\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\u0004\u0012U\u0010\f\u001aQ\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0002\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0005j\u0002`\r\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u000f\u001a\u00020\u0001\u0012u\u0010\u0010\u001aq\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\t\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0012\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000b0\u0005\u0018\u00010\u0005j\u0004\u0018\u0001`\u0011¢\u0006\u0004\b\u0015\u0010\u0016J\u0014\u0010\u001b\u001a\u00020\u001c2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u001dJ\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00012\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001J\u0018\u0010 \u001a\u00028\u00002\b\u0010!\u001a\u0004\u0018\u00010\u0001H@¢\u0006\u0002\u0010\"J\u0006\u0010#\u001a\u00020\u000bJ8\u0010$\u001a\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00052\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001R\u0010\u0010\u0002\u001a\u00020\u00018\u0006X\u0004¢\u0006\u0002\n\u0000R_\u0010\u0003\u001aQ\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0002\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\t\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\u0004X\u0004¢\u0006\u0004\n\u0002\u0010\u0017R_\u0010\f\u001aQ\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0002\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0005j\u0002`\rX\u0004¢\u0006\u0004\n\u0002\u0010\u0017R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0001X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000R\u0001\u0010\u0010\u001aq\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\t\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0012\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000b0\u0005\u0018\u00010\u0005j\u0004\u0018\u0001`\u00118\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\u0017R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00018\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00020\u001a8\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lkotlinx/coroutines/selects/SelectImplementation$ClauseData;", "", "clauseObject", "regFunc", "Lkotlinx/coroutines/selects/RegistrationFunction;", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "param", "", "processResFunc", "Lkotlinx/coroutines/selects/ProcessResultFunction;", "clauseResult", "block", "onCancellationConstructor", "Lkotlinx/coroutines/selects/OnCancellationConstructor;", "internalResult", "", "Lkotlin/coroutines/CoroutineContext;", "<init>", "(Lkotlinx/coroutines/selects/SelectImplementation;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)V", "Lkotlin/jvm/functions/Function3;", "disposableHandleOrSegment", "indexInSegment", "", "tryRegisterAsWaiter", "", "Lkotlinx/coroutines/selects/SelectImplementation;", "processResult", "result", "invokeBlock", "argument", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispose", "createOnCancellationAction", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Select.kt */
    public final class ClauseData {
        private final Object block;
        public final Object clauseObject;
        public Object disposableHandleOrSegment;
        public int indexInSegment = -1;
        public final Function3<SelectInstance<?>, Object, Object, Function3<Throwable, Object, CoroutineContext, Unit>> onCancellationConstructor;
        private final Object param;
        private final Function3<Object, Object, Object, Object> processResFunc;
        private final Function3<Object, SelectInstance<?>, Object, Unit> regFunc;

        public ClauseData(Object clauseObject2, Function3<Object, ? super SelectInstance<?>, Object, Unit> regFunc2, Function3<Object, Object, Object, ? extends Object> processResFunc2, Object param2, Object block2, Function3<? super SelectInstance<?>, Object, Object, ? extends Function3<? super Throwable, Object, ? super CoroutineContext, Unit>> onCancellationConstructor2) {
            this.clauseObject = clauseObject2;
            this.regFunc = regFunc2;
            this.processResFunc = processResFunc2;
            this.param = param2;
            this.block = block2;
            this.onCancellationConstructor = onCancellationConstructor2;
        }

        public final boolean tryRegisterAsWaiter(SelectImplementation<R> select) {
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (((select.getInRegistrationPhase() || select.isCancelled()) ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if ((select.internalResult == SelectKt.NO_RESULT ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            this.regFunc.invoke(this.clauseObject, select, this.param);
            if (select.internalResult == SelectKt.NO_RESULT) {
                return true;
            }
            return false;
        }

        public final Object processResult(Object result) {
            return this.processResFunc.invoke(this.clauseObject, this.param, result);
        }

        public final Object invokeBlock(Object argument, Continuation<? super R> $completion) {
            Object block2 = this.block;
            if (this.param == SelectKt.getPARAM_CLAUSE_0()) {
                Intrinsics.checkNotNull(block2, "null cannot be cast to non-null type kotlin.coroutines.SuspendFunction0<R of kotlinx.coroutines.selects.SelectImplementation>");
                Function1 function1 = (Function1) block2;
                return ((Function1) block2).invoke($completion);
            }
            Intrinsics.checkNotNull(block2, "null cannot be cast to non-null type kotlin.coroutines.SuspendFunction1<kotlin.Any?, R of kotlinx.coroutines.selects.SelectImplementation>");
            Function2 function2 = (Function2) block2;
            return ((Function2) block2).invoke(argument, $completion);
        }

        public final void dispose() {
            Object $this$dispose_u24lambda_u242 = this.disposableHandleOrSegment;
            SelectImplementation<R> selectImplementation = SelectImplementation.this;
            DisposableHandle disposableHandle = null;
            if ($this$dispose_u24lambda_u242 instanceof Segment) {
                ((Segment) $this$dispose_u24lambda_u242).onCancellation(this.indexInSegment, (Throwable) null, selectImplementation.getContext());
                return;
            }
            if ($this$dispose_u24lambda_u242 instanceof DisposableHandle) {
                disposableHandle = (DisposableHandle) $this$dispose_u24lambda_u242;
            }
            if (disposableHandle != null) {
                disposableHandle.dispose();
            }
        }

        public final Function3<Throwable, Object, CoroutineContext, Unit> createOnCancellationAction(SelectInstance<?> select, Object internalResult) {
            Function3<SelectInstance<?>, Object, Object, Function3<Throwable, Object, CoroutineContext, Unit>> function3 = this.onCancellationConstructor;
            if (function3 != null) {
                return function3.invoke(select, this.param, internalResult);
            }
            return null;
        }
    }
}
