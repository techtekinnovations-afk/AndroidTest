package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.selects.SelectClause0;
import kotlinx.coroutines.selects.SelectClause0Impl;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause1Impl;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1 = {"\u0000ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\f\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\n¹\u0001º\u0001»\u0001¼\u0001½\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u0004J\u001f\u0010\u001f\u001a\u00020 2\u0014\u0010!\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0012\u0004\u0012\u00020\u001b0\"H\bJ\u001c\u0010'\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001c\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u000eH\u0002J \u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010\u001c\u001a\u00020(2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020+0-H\u0002J\u001e\u0010.\u001a\u00020\u001b2\u0006\u0010/\u001a\u00020+2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020+0-H\u0002J\u001a\u00100\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u000eH\u0002J\u001a\u00103\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u000eH\u0002J\u0018\u00104\u001a\u00020\u001b2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020+H\u0002J\u0010\u00108\u001a\u00020\u00052\u0006\u00107\u001a\u00020+H\u0002J\u0016\u00109\u001a\u00020\u001b*\u0002062\b\u00107\u001a\u0004\u0018\u00010+H\u0002J/\u0010:\u001a\u00020\u001b2\u0006\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u00010+2\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00050\"H\bJ\u0006\u0010=\u001a\u00020\u0005J\u0012\u0010>\u001a\u00020?2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eH\u0002J\b\u0010@\u001a\u00020\u001bH\u0014J\u000f\u0010A\u001a\u00060Cj\u0002`B¢\u0006\u0002\u0010DJ!\u0010E\u001a\u00060Cj\u0002`B*\u00020+2\n\b\u0002\u0010F\u001a\u0004\u0018\u00010GH\u0004¢\u0006\u0002\u0010HJ4\u0010N\u001a\u00020O2'\u0010P\u001a#\u0012\u0015\u0012\u0013\u0018\u00010+¢\u0006\f\bR\u0012\b\bS\u0012\u0004\b\b(7\u0012\u0004\u0012\u00020\u001b0\"j\u0002`Q¢\u0006\u0002\u0010TJD\u0010N\u001a\u00020O2\u0006\u0010U\u001a\u00020\u00052\u0006\u0010V\u001a\u00020\u00052'\u0010P\u001a#\u0012\u0015\u0012\u0013\u0018\u00010+¢\u0006\f\bR\u0012\b\bS\u0012\u0004\b\b(7\u0012\u0004\u0012\u00020\u001b0\"j\u0002`Q¢\u0006\u0002\u0010WJ\u001d\u0010X\u001a\u00020O2\u0006\u0010V\u001a\u00020\u00052\u0006\u0010Y\u001a\u00020<H\u0000¢\u0006\u0002\bZJ+\u0010[\u001a\u00020\u00052\u0006\u0010Y\u001a\u00020<2\u0018\u0010\\\u001a\u0014\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u00050]H\bJ\u0010\u0010^\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020_H\u0002J\u0010\u0010`\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020<H\u0002J\u000e\u0010a\u001a\u00020\u001bH@¢\u0006\u0002\u0010bJ\b\u0010c\u001a\u00020\u0005H\u0002J\u000e\u0010d\u001a\u00020\u001bH@¢\u0006\u0002\u0010bJ\u001e\u0010k\u001a\u00020\u001b2\n\u0010l\u001a\u0006\u0012\u0002\b\u00030m2\b\u0010n\u001a\u0004\u0018\u00010\u000eH\u0002J\u0015\u0010o\u001a\u00020\u001b2\u0006\u0010Y\u001a\u00020<H\u0000¢\u0006\u0002\bpJ\u001d\u0010s\u001a\u00020\u001b2\u000e\u00107\u001a\n\u0018\u00010Cj\u0004\u0018\u0001`BH\u0016¢\u0006\u0002\u0010tJ\b\u0010u\u001a\u00020GH\u0014J\u0012\u0010s\u001a\u00020\u00052\b\u00107\u001a\u0004\u0018\u00010+H\u0017J\u0010\u0010v\u001a\u00020\u001b2\u0006\u00107\u001a\u00020+H\u0016J\u000e\u0010w\u001a\u00020\u001b2\u0006\u0010x\u001a\u00020\u0003J\u0010\u0010y\u001a\u00020\u00052\u0006\u00107\u001a\u00020+H\u0016J\u0010\u0010z\u001a\u00020\u00052\b\u00107\u001a\u0004\u0018\u00010+J\u0017\u0010{\u001a\u00020\u00052\b\u00107\u001a\u0004\u0018\u00010\u000eH\u0000¢\u0006\u0002\b|J\u0014\u0010}\u001a\u0004\u0018\u00010\u000e2\b\u00107\u001a\u0004\u0018\u00010\u000eH\u0002J'\u0010~\u001a\u000202\n\b\u0002\u0010F\u001a\u0004\u0018\u00010G2\n\b\u0002\u00107\u001a\u0004\u0018\u00010+H\b¢\u0006\u0003\b\u0001J\u0012\u0010\u0001\u001a\u00060Cj\u0002`BH\u0016¢\u0006\u0002\u0010DJ\u0013\u0010\u0001\u001a\u00020+2\b\u00107\u001a\u0004\u0018\u00010\u000eH\u0002J\u0015\u0010\u0001\u001a\u0004\u0018\u00010\u000e2\b\u00107\u001a\u0004\u0018\u00010\u000eH\u0002J\u0013\u0010\u0001\u001a\u0004\u0018\u0001062\u0006\u0010\u001c\u001a\u000201H\u0002J\u0019\u0010\u0001\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u0002012\u0006\u0010/\u001a\u00020+H\u0002J\u0019\u0010\u0001\u001a\u00020\u00052\b\u0010)\u001a\u0004\u0018\u00010\u000eH\u0000¢\u0006\u0003\b\u0001J\u001b\u0010\u0001\u001a\u0004\u0018\u00010\u000e2\b\u0010)\u001a\u0004\u0018\u00010\u000eH\u0000¢\u0006\u0003\b\u0001J\u001f\u0010\u0001\u001a\u0004\u0018\u00010\u000e2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000e2\b\u0010)\u001a\u0004\u0018\u00010\u000eH\u0002J\u001d\u0010\u0001\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001c\u001a\u0002012\b\u0010)\u001a\u0004\u0018\u00010\u000eH\u0002J&\u0010\u0001\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020(2\b\u0010\u0001\u001a\u00030\u00012\b\u0010)\u001a\u0004\u0018\u00010\u000eH\u0010J%\u0010\u0001\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020(2\b\u0010\u0001\u001a\u00030\u00012\b\u0010)\u001a\u0004\u0018\u00010\u000eH\u0002J\u0011\u0010\u0001\u001a\u0005\u0018\u00010\u0001*\u00030\u0001H\u0002J\u0010\u0010\u0001\u001a\u00020\u00102\u0007\u0010\u0001\u001a\u00020\u0002J\u0018\u0010\u0001\u001a\u00020\u001b2\u0007\u0010\u0001\u001a\u00020+H\u0010¢\u0006\u0003\b\u0001J\u0012\u0010U\u001a\u00020\u001b2\b\u00107\u001a\u0004\u0018\u00010+H\u0014J\u0012\u0010¡\u0001\u001a\u00020\u00052\u0007\u0010\u0001\u001a\u00020+H\u0014J\u0013\u0010¢\u0001\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eH\u0014J\u0013\u0010£\u0001\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eH\u0014J\t\u0010¤\u0001\u001a\u00020GH\u0016J\t\u0010¥\u0001\u001a\u00020GH\u0007J\u000f\u0010¦\u0001\u001a\u00020GH\u0010¢\u0006\u0003\b§\u0001J\u0013\u0010¨\u0001\u001a\u00020G2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eH\u0002J\t\u0010¬\u0001\u001a\u0004\u0018\u00010+J\u0011\u0010­\u0001\u001a\u0004\u0018\u00010\u000eH\u0000¢\u0006\u0003\b®\u0001J\u0011\u0010¯\u0001\u001a\u0004\u0018\u00010\u000eH@¢\u0006\u0002\u0010bJ\u0011\u0010°\u0001\u001a\u0004\u0018\u00010\u000eH@¢\u0006\u0002\u0010bJ\u001f\u0010¶\u0001\u001a\u00020\u001b2\n\u0010l\u001a\u0006\u0012\u0002\b\u00030m2\b\u0010n\u001a\u0004\u0018\u00010\u000eH\u0002J \u0010·\u0001\u001a\u0004\u0018\u00010\u000e2\b\u0010n\u001a\u0004\u0018\u00010\u000e2\t\u0010¸\u0001\u001a\u0004\u0018\u00010\u000eH\u0002R\u0015\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rX\u0004R\u0011\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\rX\u0004R(\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00108@@@X\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u00018VX\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u000e8@X\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010#\u001a\u00020\u00058VX\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b%\u0010$R\u0011\u0010&\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b&\u0010$R\u0016\u0010I\u001a\u0004\u0018\u00010+8DX\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010KR\u0014\u0010L\u001a\u00020\u00058DX\u0004¢\u0006\u0006\u001a\u0004\bM\u0010$R\u0017\u0010e\u001a\u00020f8F¢\u0006\f\u0012\u0004\bg\u0010h\u001a\u0004\bi\u0010jR\u0014\u0010q\u001a\u00020\u00058PX\u0004¢\u0006\u0006\u001a\u0004\br\u0010$R\u001f\u0010\u0001\u001a\u0004\u0018\u00010+*\u0004\u0018\u00010\u000e8BX\u0004¢\u0006\b\u001a\u0006\b\u0001\u0010\u0001R\u001b\u0010\u0001\u001a\t\u0012\u0004\u0012\u00020\u00010\u00018F¢\u0006\b\u001a\u0006\b\u0001\u0010\u0001R\u0016\u0010\u0001\u001a\u00020\u00058TX\u0004¢\u0006\u0007\u001a\u0005\b\u0001\u0010$R\u0016\u0010\u0001\u001a\u00020\u00058PX\u0004¢\u0006\u0007\u001a\u0005\b \u0001\u0010$R\u001b\u0010©\u0001\u001a\u00020\u0005*\u0002018BX\u0004¢\u0006\b\u001a\u0006\b©\u0001\u0010ª\u0001R\u0013\u0010«\u0001\u001a\u00020\u00058F¢\u0006\u0007\u001a\u0005\b«\u0001\u0010$R#\u0010±\u0001\u001a\u0007\u0012\u0002\b\u00030²\u00018DX\u0004¢\u0006\u000f\u0012\u0005\b³\u0001\u0010h\u001a\u0006\b´\u0001\u0010µ\u0001¨\u0006¾\u0001"}, d2 = {"Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/ChildJob;", "Lkotlinx/coroutines/ParentJob;", "active", "", "<init>", "(Z)V", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "_state", "Lkotlinx/atomicfu/AtomicRef;", "", "_parentHandle", "Lkotlinx/coroutines/ChildHandle;", "value", "parentHandle", "getParentHandle$kotlinx_coroutines_core", "()Lkotlinx/coroutines/ChildHandle;", "setParentHandle$kotlinx_coroutines_core", "(Lkotlinx/coroutines/ChildHandle;)V", "parent", "getParent", "()Lkotlinx/coroutines/Job;", "initParentJob", "", "state", "getState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "loopOnState", "", "block", "Lkotlin/Function1;", "isActive", "()Z", "isCompleted", "isCancelled", "finalizeFinishingState", "Lkotlinx/coroutines/JobSupport$Finishing;", "proposedUpdate", "getFinalRootCause", "", "exceptions", "", "addSuppressedExceptions", "rootCause", "tryFinalizeSimpleState", "Lkotlinx/coroutines/Incomplete;", "update", "completeStateFinalization", "notifyCancelling", "list", "Lkotlinx/coroutines/NodeList;", "cause", "cancelParent", "notifyCompletion", "notifyHandlers", "predicate", "Lkotlinx/coroutines/JobNode;", "start", "startInternal", "", "onStart", "getCancellationException", "Lkotlinx/coroutines/CancellationException;", "Ljava/util/concurrent/CancellationException;", "()Ljava/util/concurrent/CancellationException;", "toCancellationException", "message", "", "(Ljava/lang/Throwable;Ljava/lang/String;)Ljava/util/concurrent/CancellationException;", "completionCause", "getCompletionCause", "()Ljava/lang/Throwable;", "completionCauseHandled", "getCompletionCauseHandled", "invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "handler", "Lkotlinx/coroutines/CompletionHandler;", "Lkotlin/ParameterName;", "name", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "onCancelling", "invokeImmediately", "(ZZLkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "invokeOnCompletionInternal", "node", "invokeOnCompletionInternal$kotlinx_coroutines_core", "tryPutNodeIntoList", "tryAdd", "Lkotlin/Function2;", "promoteEmptyToNodeList", "Lkotlinx/coroutines/Empty;", "promoteSingleToNodeList", "join", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinInternal", "joinSuspend", "onJoin", "Lkotlinx/coroutines/selects/SelectClause0;", "getOnJoin$annotations", "()V", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "registerSelectForOnJoin", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "ignoredParam", "removeNode", "removeNode$kotlinx_coroutines_core", "onCancelComplete", "getOnCancelComplete$kotlinx_coroutines_core", "cancel", "(Ljava/util/concurrent/CancellationException;)V", "cancellationExceptionMessage", "cancelInternal", "parentCancelled", "parentJob", "childCancelled", "cancelCoroutine", "cancelImpl", "cancelImpl$kotlinx_coroutines_core", "cancelMakeCompleting", "defaultCancellationException", "Lkotlinx/coroutines/JobCancellationException;", "defaultCancellationException$kotlinx_coroutines_core", "getChildJobCancellationCause", "createCauseException", "makeCancelling", "getOrPromoteCancellingList", "tryMakeCancelling", "makeCompleting", "makeCompleting$kotlinx_coroutines_core", "makeCompletingOnce", "makeCompletingOnce$kotlinx_coroutines_core", "tryMakeCompleting", "tryMakeCompletingSlowPath", "exceptionOrNull", "getExceptionOrNull", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "tryWaitForChild", "child", "Lkotlinx/coroutines/ChildHandleNode;", "continueCompleting", "lastChild", "nextChild", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "children", "Lkotlin/sequences/Sequence;", "getChildren", "()Lkotlin/sequences/Sequence;", "attachChild", "handleOnCompletionException", "exception", "handleOnCompletionException$kotlinx_coroutines_core", "isScopedCoroutine", "handlesException", "getHandlesException$kotlinx_coroutines_core", "handleJobException", "onCompletionInternal", "afterCompletion", "toString", "toDebugString", "nameString", "nameString$kotlinx_coroutines_core", "stateString", "isCancelling", "(Lkotlinx/coroutines/Incomplete;)Z", "isCompletedExceptionally", "getCompletionExceptionOrNull", "getCompletedInternal", "getCompletedInternal$kotlinx_coroutines_core", "awaitInternal", "awaitSuspend", "onAwaitInternal", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnAwaitInternal$annotations", "getOnAwaitInternal", "()Lkotlinx/coroutines/selects/SelectClause1;", "onAwaitInternalRegFunc", "onAwaitInternalProcessResFunc", "result", "SelectOnJoinCompletionHandler", "Finishing", "ChildCompletion", "AwaitContinuation", "SelectOnAwaitCompletionHandler", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
/* compiled from: JobSupport.kt */
public class JobSupport implements Job, ChildJob, ParentJob {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _parentHandle$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _state$volatile$FU;
    private volatile /* synthetic */ Object _parentHandle$volatile;
    private volatile /* synthetic */ Object _state$volatile;

    static {
        Class<JobSupport> cls = JobSupport.class;
        _state$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_state$volatile");
        _parentHandle$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_parentHandle$volatile");
    }

    protected static /* synthetic */ void getOnAwaitInternal$annotations() {
    }

    public static /* synthetic */ void getOnJoin$annotations() {
    }

    private final /* synthetic */ Object get_parentHandle$volatile() {
        return this._parentHandle$volatile;
    }

    private final /* synthetic */ Object get_state$volatile() {
        return this._state$volatile;
    }

    private final /* synthetic */ void set_parentHandle$volatile(Object obj) {
        this._parentHandle$volatile = obj;
    }

    private final /* synthetic */ void set_state$volatile(Object obj) {
        this._state$volatile = obj;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        cancel((CancellationException) null);
    }

    public <R> R fold(R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
        return Job.DefaultImpls.fold(this, initial, operation);
    }

    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        return Job.DefaultImpls.get(this, key);
    }

    public CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        return Job.DefaultImpls.minusKey(this, key);
    }

    public CoroutineContext plus(CoroutineContext context) {
        return Job.DefaultImpls.plus((Job) this, context);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public Job plus(Job other) {
        return Job.DefaultImpls.plus((Job) this, other);
    }

    public JobSupport(boolean active) {
        this._state$volatile = active ? JobSupportKt.EMPTY_ACTIVE : JobSupportKt.EMPTY_NEW;
    }

    public final CoroutineContext.Key<?> getKey() {
        return Job.Key;
    }

    public final ChildHandle getParentHandle$kotlinx_coroutines_core() {
        return (ChildHandle) _parentHandle$volatile$FU.get(this);
    }

    public final void setParentHandle$kotlinx_coroutines_core(ChildHandle value) {
        _parentHandle$volatile$FU.set(this, value);
    }

    public Job getParent() {
        ChildHandle parentHandle$kotlinx_coroutines_core = getParentHandle$kotlinx_coroutines_core();
        if (parentHandle$kotlinx_coroutines_core != null) {
            return parentHandle$kotlinx_coroutines_core.getParent();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final void initParentJob(Job parent) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getParentHandle$kotlinx_coroutines_core() == null)) {
                throw new AssertionError();
            }
        }
        if (parent == null) {
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
            return;
        }
        parent.start();
        ChildHandle handle = parent.attachChild(this);
        setParentHandle$kotlinx_coroutines_core(handle);
        if (isCompleted()) {
            handle.dispose();
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
        }
    }

    public final Object getState$kotlinx_coroutines_core() {
        return _state$volatile$FU.get(this);
    }

    private final Void loopOnState(Function1<Object, Unit> block) {
        while (true) {
            block.invoke(getState$kotlinx_coroutines_core());
        }
    }

    public boolean isActive() {
        Object state = getState$kotlinx_coroutines_core();
        return (state instanceof Incomplete) && ((Incomplete) state).isActive();
    }

    public final boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof Incomplete);
    }

    public final boolean isCancelled() {
        Object state = getState$kotlinx_coroutines_core();
        return (state instanceof CompletedExceptionally) || ((state instanceof Finishing) && ((Finishing) state).isCancelling());
    }

    private final Object finalizeFinishingState(Finishing state, Object proposedUpdate) {
        boolean wasCancelling;
        Throwable finalException;
        boolean handled = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((getState$kotlinx_coroutines_core() == state ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED() && state.isSealed()) {
            throw new AssertionError();
        } else if (!DebugKt.getASSERTIONS_ENABLED() || state.isCompleting() != 0) {
            CompletedExceptionally completedExceptionally = proposedUpdate instanceof CompletedExceptionally ? (CompletedExceptionally) proposedUpdate : null;
            Throwable proposedException = completedExceptionally != null ? completedExceptionally.cause : null;
            synchronized (state) {
                wasCancelling = state.isCancelling();
                List exceptions = state.sealLocked(proposedException);
                finalException = getFinalRootCause(state, exceptions);
                if (finalException != null) {
                    addSuppressedExceptions(finalException, exceptions);
                }
            }
            Object finalState = (finalException == null || finalException == proposedException) ? proposedUpdate : new CompletedExceptionally(finalException, false, 2, (DefaultConstructorMarker) null);
            if (finalException != null) {
                if (!cancelParent(finalException) && !handleJobException(finalException)) {
                    handled = false;
                }
                if (handled) {
                    Intrinsics.checkNotNull(finalState, "null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
                    ((CompletedExceptionally) finalState).makeHandled();
                }
            }
            if (!wasCancelling) {
                onCancelling(finalException);
            }
            onCompletionInternal(finalState);
            boolean casSuccess = AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, JobSupportKt.boxIncomplete(finalState));
            if (!DebugKt.getASSERTIONS_ENABLED() || casSuccess) {
                completeStateFinalization(state, finalState);
                return finalState;
            }
            throw new AssertionError();
        } else {
            throw new AssertionError();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Throwable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Throwable getFinalRootCause(kotlinx.coroutines.JobSupport.Finishing r12, java.util.List<? extends java.lang.Throwable> r13) {
        /*
            r11 = this;
            boolean r0 = r13.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x0022
            boolean r0 = r12.isCancelling()
            if (r0 == 0) goto L_0x0021
            r0 = 0
            r1 = 0
            r2 = 0
            kotlinx.coroutines.JobCancellationException r3 = new kotlinx.coroutines.JobCancellationException
            java.lang.String r4 = r11.cancellationExceptionMessage()
            r5 = r11
            kotlinx.coroutines.Job r5 = (kotlinx.coroutines.Job) r5
            r3.<init>(r4, r1, r5)
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            return r3
        L_0x0021:
            return r1
        L_0x0022:
            r0 = r13
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r2 = 0
            java.util.Iterator r3 = r0.iterator()
        L_0x002a:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x003d
            java.lang.Object r4 = r3.next()
            r5 = r4
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r6 = 0
            boolean r7 = r5 instanceof java.util.concurrent.CancellationException
            if (r7 != 0) goto L_0x002a
            goto L_0x003e
        L_0x003d:
            r4 = r1
        L_0x003e:
            r0 = r4
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            if (r0 == 0) goto L_0x0044
            return r0
        L_0x0044:
            r2 = 0
            java.lang.Object r3 = r13.get(r2)
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            boolean r4 = r3 instanceof kotlinx.coroutines.TimeoutCancellationException
            if (r4 == 0) goto L_0x0078
            r4 = r13
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            r5 = 0
            java.util.Iterator r6 = r4.iterator()
        L_0x0057:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0072
            java.lang.Object r7 = r6.next()
            r8 = r7
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r9 = 0
            if (r8 == r3) goto L_0x006d
            boolean r10 = r8 instanceof kotlinx.coroutines.TimeoutCancellationException
            if (r10 == 0) goto L_0x006d
            r10 = 1
            goto L_0x006e
        L_0x006d:
            r10 = r2
        L_0x006e:
            if (r10 == 0) goto L_0x0057
            r1 = r7
            goto L_0x0073
        L_0x0072:
        L_0x0073:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            if (r1 == 0) goto L_0x0078
            return r1
        L_0x0078:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.getFinalRootCause(kotlinx.coroutines.JobSupport$Finishing, java.util.List):java.lang.Throwable");
    }

    private final void addSuppressedExceptions(Throwable rootCause, List<? extends Throwable> exceptions) {
        if (exceptions.size() > 1) {
            Set seenExceptions = Collections.newSetFromMap(new IdentityHashMap(exceptions.size()));
            Throwable unwrappedCause = !DebugKt.getRECOVER_STACK_TRACES() ? rootCause : StackTraceRecoveryKt.unwrapImpl(rootCause);
            for (Throwable exception : exceptions) {
                Throwable unwrapped = !DebugKt.getRECOVER_STACK_TRACES() ? exception : StackTraceRecoveryKt.unwrapImpl(exception);
                if (unwrapped != rootCause && unwrapped != unwrappedCause && !(unwrapped instanceof CancellationException) && seenExceptions.add(unwrapped)) {
                    ExceptionsKt.addSuppressed(rootCause, unwrapped);
                }
            }
        }
    }

    private final boolean tryFinalizeSimpleState(Incomplete state, Object update) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((((state instanceof Empty) || (state instanceof JobNode)) ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED() && (update instanceof CompletedExceptionally)) {
            throw new AssertionError();
        } else if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, JobSupportKt.boxIncomplete(update))) {
            return false;
        } else {
            onCancelling((Throwable) null);
            onCompletionInternal(update);
            completeStateFinalization(state, update);
            return true;
        }
    }

    private final void completeStateFinalization(Incomplete state, Object update) {
        ChildHandle it = getParentHandle$kotlinx_coroutines_core();
        if (it != null) {
            it.dispose();
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
        }
        Throwable cause = null;
        CompletedExceptionally completedExceptionally = update instanceof CompletedExceptionally ? (CompletedExceptionally) update : null;
        if (completedExceptionally != null) {
            cause = completedExceptionally.cause;
        }
        if (state instanceof JobNode) {
            try {
                ((JobNode) state).invoke(cause);
            } catch (Throwable ex) {
                handleOnCompletionException$kotlinx_coroutines_core(new CompletionHandlerException("Exception in completion handler " + state + " for " + this, ex));
            }
        } else {
            NodeList list = state.getList();
            if (list != null) {
                notifyCompletion(list, cause);
            }
        }
    }

    private final void notifyCancelling(NodeList list, Throwable cause) {
        NodeList nodeList = list;
        Throwable th = cause;
        onCancelling(th);
        nodeList.close(4);
        LockFreeLinkedListHead this_$iv$iv = nodeList;
        Object next = this_$iv$iv.getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode");
        LockFreeLinkedListNode cur$iv$iv = (LockFreeLinkedListNode) next;
        Object exception$iv = null;
        while (!Intrinsics.areEqual((Object) cur$iv$iv, (Object) this_$iv$iv)) {
            LockFreeLinkedListNode node$iv = cur$iv$iv;
            if ((node$iv instanceof JobNode) && ((JobNode) node$iv).getOnCancelling()) {
                try {
                    ((JobNode) node$iv).invoke(th);
                } catch (Throwable ex$iv) {
                    Throwable $this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv = (Throwable) exception$iv;
                    if ($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv != null) {
                        ExceptionsKt.addSuppressed($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv, ex$iv);
                        if ($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv != null) {
                        }
                    }
                    exception$iv = new CompletionHandlerException("Exception in completion handler " + node$iv + " for " + this, ex$iv);
                    Unit unit = Unit.INSTANCE;
                }
            }
            cur$iv$iv = cur$iv$iv.getNextNode();
            NodeList nodeList2 = list;
        }
        Throwable it$iv = (Throwable) exception$iv;
        if (it$iv != null) {
            handleOnCompletionException$kotlinx_coroutines_core(it$iv);
        }
        cancelParent(th);
    }

    private final boolean cancelParent(Throwable cause) {
        if (isScopedCoroutine()) {
            return true;
        }
        boolean isCancellation = cause instanceof CancellationException;
        ChildHandle parent = getParentHandle$kotlinx_coroutines_core();
        if (parent == null || parent == NonDisposableHandle.INSTANCE) {
            return isCancellation;
        }
        if (parent.childCancelled(cause) || isCancellation) {
            return true;
        }
        return false;
    }

    private final void notifyCompletion(NodeList $this$notifyCompletion, Throwable cause) {
        NodeList nodeList = $this$notifyCompletion;
        nodeList.close(1);
        LockFreeLinkedListHead this_$iv$iv = nodeList;
        Object next = this_$iv$iv.getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode");
        Object exception$iv = null;
        for (LockFreeLinkedListNode cur$iv$iv = (LockFreeLinkedListNode) next; !Intrinsics.areEqual((Object) cur$iv$iv, (Object) this_$iv$iv); cur$iv$iv = cur$iv$iv.getNextNode()) {
            LockFreeLinkedListNode node$iv = cur$iv$iv;
            if (node$iv instanceof JobNode) {
                JobNode jobNode = (JobNode) node$iv;
                try {
                    try {
                        ((JobNode) node$iv).invoke(cause);
                    } catch (Throwable th) {
                        ex$iv = th;
                    }
                } catch (Throwable th2) {
                    ex$iv = th2;
                    Throwable th3 = cause;
                    Throwable $this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv = (Throwable) exception$iv;
                    if ($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv != null) {
                        ExceptionsKt.addSuppressed($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv, ex$iv);
                        if ($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413$iv != null) {
                        }
                    }
                    exception$iv = new CompletionHandlerException("Exception in completion handler " + node$iv + " for " + this, ex$iv);
                    Unit unit = Unit.INSTANCE;
                }
            } else {
                Throwable th4 = cause;
            }
        }
        Throwable th5 = cause;
        Throwable it$iv = (Throwable) exception$iv;
        if (it$iv != null) {
            handleOnCompletionException$kotlinx_coroutines_core(it$iv);
        }
    }

    private final void notifyHandlers(NodeList list, Throwable cause, Function1<? super JobNode, Boolean> predicate) {
        LockFreeLinkedListHead this_$iv = list;
        Object next = this_$iv.getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode");
        Object exception = null;
        for (LockFreeLinkedListNode cur$iv = (LockFreeLinkedListNode) next; !Intrinsics.areEqual((Object) cur$iv, (Object) this_$iv); cur$iv = cur$iv.getNextNode()) {
            LockFreeLinkedListNode node = cur$iv;
            if (!(node instanceof JobNode)) {
                Throwable th = cause;
                Function1<? super JobNode, Boolean> function1 = predicate;
            } else if (predicate.invoke(node).booleanValue()) {
                try {
                    try {
                        ((JobNode) node).invoke(cause);
                    } catch (Throwable th2) {
                        ex = th2;
                    }
                } catch (Throwable th3) {
                    ex = th3;
                    Throwable th4 = cause;
                    Throwable $this$notifyHandlers_u24lambda_u2415_u24lambda_u2413 = (Throwable) exception;
                    if ($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413 != null) {
                        ExceptionsKt.addSuppressed($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413, ex);
                        if ($this$notifyHandlers_u24lambda_u2415_u24lambda_u2413 != null) {
                        }
                    }
                    exception = new CompletionHandlerException("Exception in completion handler " + node + " for " + this, ex);
                    Unit unit = Unit.INSTANCE;
                }
            } else {
                Throwable th5 = cause;
            }
        }
        Throwable th6 = cause;
        Function1<? super JobNode, Boolean> function12 = predicate;
        Throwable it = (Throwable) exception;
        if (it != null) {
            handleOnCompletionException$kotlinx_coroutines_core(it);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0002 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean start() {
        /*
            r5 = this;
            r0 = r5
            r1 = 0
        L_0x0002:
            java.lang.Object r2 = r0.getState$kotlinx_coroutines_core()
            r3 = 0
            int r4 = r5.startInternal(r2)
            switch(r4) {
                case 0: goto L_0x0013;
                case 1: goto L_0x0011;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0002
        L_0x0011:
            r4 = 1
            return r4
        L_0x0013:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.start():boolean");
    }

    private final int startInternal(Object state) {
        if (state instanceof Empty) {
            if (((Empty) state).isActive()) {
                return 0;
            }
            if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, JobSupportKt.EMPTY_ACTIVE)) {
                return -1;
            }
            onStart();
            return 1;
        } else if (!(state instanceof InactiveNodeList)) {
            return 0;
        } else {
            if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, ((InactiveNodeList) state).getList())) {
                return -1;
            }
            onStart();
            return 1;
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public final CancellationException getCancellationException() {
        CancellationException cancellationException;
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof Finishing) {
            Throwable rootCause = ((Finishing) state).getRootCause();
            if (rootCause != null && (cancellationException = toCancellationException(rootCause, DebugStringsKt.getClassSimpleName(this) + " is cancelling")) != null) {
                return cancellationException;
            }
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof Incomplete) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof CompletedExceptionally) {
            return toCancellationException$default(this, ((CompletedExceptionally) state).cause, (String) null, 1, (Object) null);
        } else {
            return new JobCancellationException(DebugStringsKt.getClassSimpleName(this) + " has completed normally", (Throwable) null, this);
        }
    }

    public static /* synthetic */ CancellationException toCancellationException$default(JobSupport jobSupport, Throwable th, String str, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                str = null;
            }
            return jobSupport.toCancellationException(th, str);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toCancellationException");
    }

    /* access modifiers changed from: protected */
    public final CancellationException toCancellationException(Throwable $this$toCancellationException, String message) {
        CancellationException cancellationException = $this$toCancellationException instanceof CancellationException ? (CancellationException) $this$toCancellationException : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        return new JobCancellationException(message == null ? cancellationExceptionMessage() : message, $this$toCancellationException, this);
    }

    /* access modifiers changed from: protected */
    public final Throwable getCompletionCause() {
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof Finishing) {
            Throwable rootCause = ((Finishing) state).getRootCause();
            if (rootCause != null) {
                return rootCause;
            }
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof Incomplete) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof CompletedExceptionally) {
            return ((CompletedExceptionally) state).cause;
        } else {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean getCompletionCauseHandled() {
        Object it = getState$kotlinx_coroutines_core();
        return (it instanceof CompletedExceptionally) && ((CompletedExceptionally) it).getHandled();
    }

    public final DisposableHandle invokeOnCompletion(Function1<? super Throwable, Unit> handler) {
        return invokeOnCompletionInternal$kotlinx_coroutines_core(true, new InvokeOnCompletion(handler));
    }

    public final DisposableHandle invokeOnCompletion(boolean onCancelling, boolean invokeImmediately, Function1<? super Throwable, Unit> handler) {
        JobNode jobNode;
        if (onCancelling) {
            jobNode = new InvokeOnCancelling(handler);
        } else {
            jobNode = new InvokeOnCompletion(handler);
        }
        return invokeOnCompletionInternal$kotlinx_coroutines_core(invokeImmediately, jobNode);
    }

    public final DisposableHandle invokeOnCompletionInternal$kotlinx_coroutines_core(boolean invokeImmediately, JobNode node) {
        boolean added;
        boolean z;
        JobNode jobNode = node;
        jobNode.setJob(this);
        while (true) {
            Object state$iv = getState$kotlinx_coroutines_core();
            added = true;
            if (!(state$iv instanceof Empty)) {
                if (!(state$iv instanceof Incomplete)) {
                    added = false;
                    break;
                }
                NodeList list$iv = ((Incomplete) state$iv).getList();
                if (list$iv == null) {
                    Intrinsics.checkNotNull(state$iv, "null cannot be cast to non-null type kotlinx.coroutines.JobNode");
                    promoteSingleToNodeList((JobNode) state$iv);
                } else {
                    Incomplete state = (Incomplete) state$iv;
                    NodeList list = list$iv;
                    if (jobNode.getOnCancelling()) {
                        Finishing finishing = state instanceof Finishing ? (Finishing) state : null;
                        Throwable rootCause = finishing != null ? finishing.getRootCause() : null;
                        if (rootCause == null) {
                            z = list.addLast(jobNode, 5);
                        } else {
                            if (invokeImmediately) {
                                jobNode.invoke(rootCause);
                            }
                            return NonDisposableHandle.INSTANCE;
                        }
                    } else {
                        z = list.addLast(jobNode, 1);
                    }
                    if (z) {
                        break;
                    }
                }
            } else if (!((Empty) state$iv).isActive()) {
                promoteEmptyToNodeList((Empty) state$iv);
            } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state$iv, jobNode)) {
                break;
            }
        }
        if (added) {
            return jobNode;
        }
        if (invokeImmediately) {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            CompletedExceptionally completedExceptionally = state$kotlinx_coroutines_core instanceof CompletedExceptionally ? (CompletedExceptionally) state$kotlinx_coroutines_core : null;
            jobNode.invoke(completedExceptionally != null ? completedExceptionally.cause : null);
        }
        return NonDisposableHandle.INSTANCE;
    }

    private final boolean tryPutNodeIntoList(JobNode node, Function2<? super Incomplete, ? super NodeList, Boolean> tryAdd) {
        while (true) {
            Object state = getState$kotlinx_coroutines_core();
            if (state instanceof Empty) {
                if (!((Empty) state).isActive()) {
                    promoteEmptyToNodeList((Empty) state);
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, node)) {
                    return true;
                }
            } else if (!(state instanceof Incomplete)) {
                return false;
            } else {
                NodeList list = ((Incomplete) state).getList();
                if (list == null) {
                    Intrinsics.checkNotNull(state, "null cannot be cast to non-null type kotlinx.coroutines.JobNode");
                    promoteSingleToNodeList((JobNode) state);
                } else if (tryAdd.invoke(state, list).booleanValue()) {
                    return true;
                }
            }
        }
    }

    private final void promoteEmptyToNodeList(Empty state) {
        NodeList list = new NodeList();
        AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, state.isActive() ? list : new InactiveNodeList(list));
    }

    private final void promoteSingleToNodeList(JobNode state) {
        state.addOneIfEmpty(new NodeList());
        AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, state.getNextNode());
    }

    public final Object join(Continuation<? super Unit> $completion) {
        if (!joinInternal()) {
            JobKt.ensureActive($completion.getContext());
            return Unit.INSTANCE;
        }
        Object joinSuspend = joinSuspend($completion);
        return joinSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? joinSuspend : Unit.INSTANCE;
    }

    private final boolean joinInternal() {
        Object state;
        do {
            state = getState$kotlinx_coroutines_core();
            if (!(state instanceof Incomplete)) {
                return false;
            }
        } while (startInternal(state) < 0);
        return true;
    }

    /* access modifiers changed from: private */
    public final Object joinSuspend(Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        CancellableContinuationKt.disposeOnCancellation(cont, JobKt__JobKt.invokeOnCompletion$default(this, false, new ResumeOnCompletion(cont), 1, (Object) null));
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return result;
        }
        return Unit.INSTANCE;
    }

    public final SelectClause0 getOnJoin() {
        JobSupport$onJoin$1 jobSupport$onJoin$1 = JobSupport$onJoin$1.INSTANCE;
        Intrinsics.checkNotNull(jobSupport$onJoin$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        return new SelectClause0Impl(this, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(jobSupport$onJoin$1, 3), (Function3) null, 4, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: private */
    public final void registerSelectForOnJoin(SelectInstance<?> select, Object ignoredParam) {
        if (!joinInternal()) {
            select.selectInRegistrationPhase(Unit.INSTANCE);
        } else {
            select.disposeOnCompletion(JobKt__JobKt.invokeOnCompletion$default(this, false, new SelectOnJoinCompletionHandler(select), 1, (Object) null));
        }
    }

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/JobSupport$SelectOnJoinCompletionHandler;", "Lkotlinx/coroutines/JobNode;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "<init>", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/selects/SelectInstance;)V", "onCancelling", "", "getOnCancelling", "()Z", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    private final class SelectOnJoinCompletionHandler extends JobNode {
        private final SelectInstance<?> select;

        public SelectOnJoinCompletionHandler(SelectInstance<?> select2) {
            this.select = select2;
        }

        public boolean getOnCancelling() {
            return false;
        }

        public void invoke(Throwable cause) {
            this.select.trySelect(JobSupport.this, Unit.INSTANCE);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0021 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000d A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void removeNode$kotlinx_coroutines_core(kotlinx.coroutines.JobNode r7) {
        /*
            r6 = this;
            r0 = r6
            r1 = 0
        L_0x0002:
            java.lang.Object r2 = r0.getState$kotlinx_coroutines_core()
            r3 = 0
            boolean r4 = r2 instanceof kotlinx.coroutines.JobNode
            if (r4 == 0) goto L_0x0021
            if (r2 == r7) goto L_0x0010
            return
        L_0x0010:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = _state$volatile$FU
            kotlinx.coroutines.Empty r5 = kotlinx.coroutines.JobSupportKt.EMPTY_ACTIVE
            boolean r4 = androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(r4, r6, r2, r5)
            if (r4 == 0) goto L_0x001f
            return
        L_0x001f:
            goto L_0x0002
        L_0x0021:
            boolean r4 = r2 instanceof kotlinx.coroutines.Incomplete
            if (r4 == 0) goto L_0x0032
            r4 = r2
            kotlinx.coroutines.Incomplete r4 = (kotlinx.coroutines.Incomplete) r4
            kotlinx.coroutines.NodeList r4 = r4.getList()
            if (r4 == 0) goto L_0x0031
            r7.remove()
        L_0x0031:
            return
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.removeNode$kotlinx_coroutines_core(kotlinx.coroutines.JobNode):void");
    }

    public boolean getOnCancelComplete$kotlinx_coroutines_core() {
        return false;
    }

    public void cancel(CancellationException cause) {
        CancellationException cancellationException;
        if (cause == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
        } else {
            cancellationException = cause;
        }
        cancelInternal(cancellationException);
    }

    /* access modifiers changed from: protected */
    public String cancellationExceptionMessage() {
        return "Job was cancelled";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Added since 1.2.0 for binary compatibility with versions <= 1.1.x")
    public /* synthetic */ boolean cancel(Throwable cause) {
        CancellationException cancellationException;
        if (cause == null || (cancellationException = toCancellationException$default(this, cause, (String) null, 1, (Object) null)) == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
        }
        cancelInternal(cancellationException);
        return true;
    }

    public void cancelInternal(Throwable cause) {
        cancelImpl$kotlinx_coroutines_core(cause);
    }

    public final void parentCancelled(ParentJob parentJob) {
        cancelImpl$kotlinx_coroutines_core(parentJob);
    }

    public boolean childCancelled(Throwable cause) {
        if (cause instanceof CancellationException) {
            return true;
        }
        if (!cancelImpl$kotlinx_coroutines_core(cause) || !getHandlesException$kotlinx_coroutines_core()) {
            return false;
        }
        return true;
    }

    public final boolean cancelCoroutine(Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause);
    }

    public final boolean cancelImpl$kotlinx_coroutines_core(Object cause) {
        Object finalState = JobSupportKt.COMPLETING_ALREADY;
        if (getOnCancelComplete$kotlinx_coroutines_core() && (finalState = cancelMakeCompleting(cause)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        if (finalState == JobSupportKt.COMPLETING_ALREADY) {
            finalState = makeCancelling(cause);
        }
        if (finalState == JobSupportKt.COMPLETING_ALREADY || finalState == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        if (finalState == JobSupportKt.TOO_LATE_TO_CANCEL) {
            return false;
        }
        afterCompletion(finalState);
        return true;
    }

    private final Object cancelMakeCompleting(Object cause) {
        Object finalState;
        do {
            Object state = getState$kotlinx_coroutines_core();
            if (!(state instanceof Incomplete) || ((state instanceof Finishing) && ((Finishing) state).isCompleting())) {
                return JobSupportKt.COMPLETING_ALREADY;
            }
            finalState = tryMakeCompleting(state, new CompletedExceptionally(createCauseException(cause), false, 2, (DefaultConstructorMarker) null));
        } while (finalState == JobSupportKt.COMPLETING_RETRY);
        return finalState;
    }

    public static /* synthetic */ JobCancellationException defaultCancellationException$kotlinx_coroutines_core$default(JobSupport $this, String message, Throwable cause, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                message = null;
            }
            if ((i & 2) != 0) {
                cause = null;
            }
            return new JobCancellationException(message == null ? $this.cancellationExceptionMessage() : message, cause, $this);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: defaultCancellationException");
    }

    public final JobCancellationException defaultCancellationException$kotlinx_coroutines_core(String message, Throwable cause) {
        return new JobCancellationException(message == null ? cancellationExceptionMessage() : message, cause, this);
    }

    public CancellationException getChildJobCancellationCause() {
        Throwable rootCause;
        Object state = getState$kotlinx_coroutines_core();
        CancellationException cancellationException = null;
        if (state instanceof Finishing) {
            rootCause = ((Finishing) state).getRootCause();
        } else if (state instanceof CompletedExceptionally) {
            rootCause = ((CompletedExceptionally) state).cause;
        } else if (!(state instanceof Incomplete)) {
            rootCause = null;
        } else {
            throw new IllegalStateException(("Cannot be cancelling child in this state: " + state).toString());
        }
        if (rootCause instanceof CancellationException) {
            cancellationException = (CancellationException) rootCause;
        }
        return cancellationException == null ? new JobCancellationException("Parent job is " + stateString(state), rootCause, this) : cancellationException;
    }

    private final Throwable createCauseException(Object cause) {
        if (cause == null ? true : cause instanceof Throwable) {
            Throwable th = (Throwable) cause;
            if (th == null) {
                return new JobCancellationException(cancellationExceptionMessage(), (Throwable) null, this);
            }
            return th;
        }
        Intrinsics.checkNotNull(cause, "null cannot be cast to non-null type kotlinx.coroutines.ParentJob");
        return ((ParentJob) cause).getChildJobCancellationCause();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005c, code lost:
        if (r7 == null) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005e, code lost:
        notifyCancelling(((kotlinx.coroutines.JobSupport.Finishing) r5).getList(), r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x006e, code lost:
        return kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object makeCancelling(java.lang.Object r18) {
        /*
            r17 = this;
            r1 = r17
            r0 = 0
            r2 = r17
            r3 = 0
            r4 = r0
        L_0x0007:
            java.lang.Object r5 = r2.getState$kotlinx_coroutines_core()
            r6 = 0
            boolean r0 = r5 instanceof kotlinx.coroutines.JobSupport.Finishing
            r7 = 0
            r8 = 0
            if (r0 == 0) goto L_0x0075
            r9 = r5
            r10 = 0
            r11 = 0
            monitor-enter(r9)
            r0 = 0
            r12 = r5
            kotlinx.coroutines.JobSupport$Finishing r12 = (kotlinx.coroutines.JobSupport.Finishing) r12     // Catch:{ all -> 0x0072 }
            boolean r12 = r12.isSealed()     // Catch:{ all -> 0x0072 }
            if (r12 == 0) goto L_0x0028
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.JobSupportKt.TOO_LATE_TO_CANCEL     // Catch:{ all -> 0x0072 }
            monitor-exit(r9)
            return r7
        L_0x0028:
            r12 = r5
            kotlinx.coroutines.JobSupport$Finishing r12 = (kotlinx.coroutines.JobSupport.Finishing) r12     // Catch:{ all -> 0x0072 }
            boolean r12 = r12.isCancelling()     // Catch:{ all -> 0x0072 }
            if (r18 != 0) goto L_0x0033
            if (r12 != 0) goto L_0x004a
        L_0x0033:
            if (r4 != 0) goto L_0x0042
            java.lang.Throwable r13 = r17.createCauseException(r18)     // Catch:{ all -> 0x0072 }
            r14 = r13
            r15 = 0
            r4 = r14
            r16 = r13
            r13 = r4
            r4 = r16
            goto L_0x0043
        L_0x0042:
            r13 = r4
        L_0x0043:
            r14 = r5
            kotlinx.coroutines.JobSupport$Finishing r14 = (kotlinx.coroutines.JobSupport.Finishing) r14     // Catch:{ all -> 0x006f }
            r14.addExceptionLocked(r4)     // Catch:{ all -> 0x006f }
            r4 = r13
        L_0x004a:
            r13 = r5
            kotlinx.coroutines.JobSupport$Finishing r13 = (kotlinx.coroutines.JobSupport.Finishing) r13     // Catch:{ all -> 0x0072 }
            java.lang.Throwable r13 = r13.getRootCause()     // Catch:{ all -> 0x0072 }
            r14 = r13
            r15 = 0
            if (r12 != 0) goto L_0x0056
            r8 = 1
        L_0x0056:
            if (r8 == 0) goto L_0x0059
            r7 = r13
        L_0x0059:
            monitor-exit(r9)
            if (r7 == 0) goto L_0x006a
            r0 = r7
            r8 = 0
            r9 = r5
            kotlinx.coroutines.JobSupport$Finishing r9 = (kotlinx.coroutines.JobSupport.Finishing) r9
            kotlinx.coroutines.NodeList r9 = r9.getList()
            r1.notifyCancelling(r9, r0)
        L_0x006a:
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            return r0
        L_0x006f:
            r0 = move-exception
            r4 = r13
            goto L_0x0073
        L_0x0072:
            r0 = move-exception
        L_0x0073:
            monitor-exit(r9)
            throw r0
        L_0x0075:
            boolean r0 = r5 instanceof kotlinx.coroutines.Incomplete
            if (r0 == 0) goto L_0x00d4
            if (r4 != 0) goto L_0x0083
            java.lang.Throwable r0 = r17.createCauseException(r18)
            r9 = r0
            r10 = 0
            r4 = r0
            goto L_0x0084
        L_0x0083:
            r9 = r4
        L_0x0084:
            r0 = r5
            kotlinx.coroutines.Incomplete r0 = (kotlinx.coroutines.Incomplete) r0
            boolean r0 = r0.isActive()
            if (r0 == 0) goto L_0x009c
            r0 = r5
            kotlinx.coroutines.Incomplete r0 = (kotlinx.coroutines.Incomplete) r0
            boolean r0 = r1.tryMakeCancelling(r0, r4)
            if (r0 == 0) goto L_0x009b
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            return r0
        L_0x009b:
            goto L_0x00b4
        L_0x009c:
            kotlinx.coroutines.CompletedExceptionally r0 = new kotlinx.coroutines.CompletedExceptionally
            r10 = 2
            r0.<init>(r4, r8, r10, r7)
            java.lang.Object r0 = r1.tryMakeCompleting(r5, r0)
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY
            if (r0 == r7) goto L_0x00b7
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY
            if (r0 == r7) goto L_0x00b4
            return r0
        L_0x00b4:
            r4 = r9
            goto L_0x0007
        L_0x00b7:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r10 = "Cannot happen in "
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.StringBuilder r8 = r8.append(r5)
            java.lang.String r8 = r8.toString()
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x00d4:
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.JobSupportKt.TOO_LATE_TO_CANCEL
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.makeCancelling(java.lang.Object):java.lang.Object");
    }

    private final NodeList getOrPromoteCancellingList(Incomplete state) {
        NodeList list = state.getList();
        if (list != null) {
            return list;
        }
        if (state instanceof Empty) {
            return new NodeList();
        }
        if (state instanceof JobNode) {
            promoteSingleToNodeList((JobNode) state);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + state).toString());
    }

    private final boolean tryMakeCancelling(Incomplete state, Throwable rootCause) {
        if (DebugKt.getASSERTIONS_ENABLED() && (state instanceof Finishing)) {
            throw new AssertionError();
        } else if (!DebugKt.getASSERTIONS_ENABLED() || state.isActive() != 0) {
            NodeList list = getOrPromoteCancellingList(state);
            if (list == null) {
                return false;
            }
            if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, new Finishing(list, false, rootCause))) {
                return false;
            }
            notifyCancelling(list, rootCause);
            return true;
        } else {
            throw new AssertionError();
        }
    }

    public final boolean makeCompleting$kotlinx_coroutines_core(Object proposedUpdate) {
        Object finalState;
        do {
            finalState = tryMakeCompleting(getState$kotlinx_coroutines_core(), proposedUpdate);
            if (finalState == JobSupportKt.COMPLETING_ALREADY) {
                return false;
            }
            if (finalState == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                return true;
            }
        } while (finalState == JobSupportKt.COMPLETING_RETRY);
        afterCompletion(finalState);
        return true;
    }

    public final Object makeCompletingOnce$kotlinx_coroutines_core(Object proposedUpdate) {
        Object finalState;
        do {
            finalState = tryMakeCompleting(getState$kotlinx_coroutines_core(), proposedUpdate);
            if (finalState == JobSupportKt.COMPLETING_ALREADY) {
                throw new IllegalStateException("Job " + this + " is already complete or completing, but is being completed with " + proposedUpdate, getExceptionOrNull(proposedUpdate));
            }
        } while (finalState == JobSupportKt.COMPLETING_RETRY);
        return finalState;
    }

    private final Object tryMakeCompleting(Object state, Object proposedUpdate) {
        if (!(state instanceof Incomplete)) {
            return JobSupportKt.COMPLETING_ALREADY;
        }
        if ((!(state instanceof Empty) && !(state instanceof JobNode)) || (state instanceof ChildHandleNode) || (proposedUpdate instanceof CompletedExceptionally)) {
            return tryMakeCompletingSlowPath((Incomplete) state, proposedUpdate);
        }
        if (tryFinalizeSimpleState((Incomplete) state, proposedUpdate)) {
            return proposedUpdate;
        }
        return JobSupportKt.COMPLETING_RETRY;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x008b, code lost:
        r2 = (java.lang.Throwable) r4.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0090, code lost:
        if (r2 == null) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0092, code lost:
        notifyCancelling(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0096, code lost:
        r2 = nextChild(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x009d, code lost:
        if (r2 == null) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00a3, code lost:
        if (tryWaitForChild(r1, r2, r15) == false) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00a7, code lost:
        return kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00a8, code lost:
        r0.close(2);
        r3 = nextChild(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00b3, code lost:
        if (r3 == null) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b9, code lost:
        if (tryWaitForChild(r1, r3, r15) == false) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00bd, code lost:
        return kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c2, code lost:
        return finalizeFinishingState(r1, r15);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object tryMakeCompletingSlowPath(kotlinx.coroutines.Incomplete r14, java.lang.Object r15) {
        /*
            r13 = this;
            kotlinx.coroutines.NodeList r0 = r13.getOrPromoteCancellingList(r14)
            if (r0 != 0) goto L_0x000b
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY
            return r0
        L_0x000b:
            boolean r1 = r14 instanceof kotlinx.coroutines.JobSupport.Finishing
            r2 = 0
            if (r1 == 0) goto L_0x0014
            r1 = r14
            kotlinx.coroutines.JobSupport$Finishing r1 = (kotlinx.coroutines.JobSupport.Finishing) r1
            goto L_0x0015
        L_0x0014:
            r1 = r2
        L_0x0015:
            r3 = 0
            if (r1 != 0) goto L_0x001d
            kotlinx.coroutines.JobSupport$Finishing r1 = new kotlinx.coroutines.JobSupport$Finishing
            r1.<init>(r0, r3, r2)
        L_0x001d:
            kotlin.jvm.internal.Ref$ObjectRef r4 = new kotlin.jvm.internal.Ref$ObjectRef
            r4.<init>()
            r5 = 0
            r6 = 0
            monitor-enter(r1)
            r7 = 0
            boolean r8 = r1.isCompleting()     // Catch:{ all -> 0x00c3 }
            if (r8 == 0) goto L_0x0032
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.JobSupportKt.COMPLETING_ALREADY     // Catch:{ all -> 0x00c3 }
            monitor-exit(r1)
            return r2
        L_0x0032:
            r8 = 1
            r1.setCompleting(r8)     // Catch:{ all -> 0x00c3 }
            if (r1 == r14) goto L_0x0048
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r9 = _state$volatile$FU     // Catch:{ all -> 0x00c3 }
            boolean r9 = androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(r9, r13, r14, r1)     // Catch:{ all -> 0x00c3 }
            if (r9 != 0) goto L_0x0048
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.JobSupportKt.COMPLETING_RETRY     // Catch:{ all -> 0x00c3 }
            monitor-exit(r1)
            return r2
        L_0x0048:
            boolean r9 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()     // Catch:{ all -> 0x00c3 }
            if (r9 == 0) goto L_0x005c
            r9 = 0
            boolean r10 = r1.isSealed()     // Catch:{ all -> 0x00c3 }
            if (r10 != 0) goto L_0x0056
            goto L_0x005c
        L_0x0056:
            java.lang.AssertionError r2 = new java.lang.AssertionError     // Catch:{ all -> 0x00c3 }
            r2.<init>()     // Catch:{ all -> 0x00c3 }
            throw r2     // Catch:{ all -> 0x00c3 }
        L_0x005c:
            boolean r9 = r1.isCancelling()     // Catch:{ all -> 0x00c3 }
            boolean r10 = r15 instanceof kotlinx.coroutines.CompletedExceptionally     // Catch:{ all -> 0x00c3 }
            if (r10 == 0) goto L_0x0068
            r10 = r15
            kotlinx.coroutines.CompletedExceptionally r10 = (kotlinx.coroutines.CompletedExceptionally) r10     // Catch:{ all -> 0x00c3 }
            goto L_0x0069
        L_0x0068:
            r10 = r2
        L_0x0069:
            if (r10 == 0) goto L_0x0071
            r11 = 0
            java.lang.Throwable r12 = r10.cause     // Catch:{ all -> 0x00c3 }
            r1.addExceptionLocked(r12)     // Catch:{ all -> 0x00c3 }
        L_0x0071:
            java.lang.Throwable r10 = r1.getRootCause()     // Catch:{ all -> 0x00c3 }
            r11 = r10
            r12 = 0
            if (r9 != 0) goto L_0x007a
            r3 = r8
        L_0x007a:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x00c3 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00c3 }
            if (r3 == 0) goto L_0x0085
            r2 = r10
        L_0x0085:
            r4.element = r2     // Catch:{ all -> 0x00c3 }
            kotlin.Unit r2 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00c3 }
            monitor-exit(r1)
            T r2 = r4.element
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            if (r2 == 0) goto L_0x0096
            r3 = 0
            r13.notifyCancelling(r0, r2)
        L_0x0096:
            r2 = r0
            kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r2
            kotlinx.coroutines.ChildHandleNode r2 = r13.nextChild(r2)
            if (r2 == 0) goto L_0x00a8
            boolean r3 = r13.tryWaitForChild(r1, r2, r15)
            if (r3 == 0) goto L_0x00a8
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN
            return r3
        L_0x00a8:
            r3 = 2
            r0.close(r3)
            r3 = r0
            kotlinx.coroutines.internal.LockFreeLinkedListNode r3 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r3
            kotlinx.coroutines.ChildHandleNode r3 = r13.nextChild(r3)
            if (r3 == 0) goto L_0x00be
            boolean r5 = r13.tryWaitForChild(r1, r3, r15)
            if (r5 == 0) goto L_0x00be
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.JobSupportKt.COMPLETING_WAITING_CHILDREN
            return r5
        L_0x00be:
            java.lang.Object r5 = r13.finalizeFinishingState(r1, r15)
            return r5
        L_0x00c3:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.tryMakeCompletingSlowPath(kotlinx.coroutines.Incomplete, java.lang.Object):java.lang.Object");
    }

    private final Throwable getExceptionOrNull(Object $this$exceptionOrNull) {
        CompletedExceptionally completedExceptionally = $this$exceptionOrNull instanceof CompletedExceptionally ? (CompletedExceptionally) $this$exceptionOrNull : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    private final boolean tryWaitForChild(Finishing state, ChildHandleNode child, Object proposedUpdate) {
        while (JobKt.invokeOnCompletion(child.childJob, false, new ChildCompletion(this, state, child, proposedUpdate)) == NonDisposableHandle.INSTANCE) {
            ChildHandleNode nextChild = nextChild(child);
            if (nextChild == null) {
                return false;
            }
            child = nextChild;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final void continueCompleting(Finishing state, ChildHandleNode lastChild, Object proposedUpdate) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getState$kotlinx_coroutines_core() == state)) {
                throw new AssertionError();
            }
        }
        ChildHandleNode waitChild = nextChild(lastChild);
        if (waitChild == null || !tryWaitForChild(state, waitChild, proposedUpdate)) {
            state.getList().close(2);
            ChildHandleNode waitChildAgain = nextChild(lastChild);
            if (waitChildAgain == null || !tryWaitForChild(state, waitChildAgain, proposedUpdate)) {
                afterCompletion(finalizeFinishingState(state, proposedUpdate));
            }
        }
    }

    private final ChildHandleNode nextChild(LockFreeLinkedListNode $this$nextChild) {
        LockFreeLinkedListNode cur = $this$nextChild;
        while (cur.isRemoved()) {
            cur = cur.getPrevNode();
        }
        while (true) {
            cur = cur.getNextNode();
            if (!cur.isRemoved()) {
                if (cur instanceof ChildHandleNode) {
                    return (ChildHandleNode) cur;
                }
                if (cur instanceof NodeList) {
                    return null;
                }
            }
        }
    }

    public final Sequence<Job> getChildren() {
        return SequencesKt.sequence(new JobSupport$children$1(this, (Continuation<? super JobSupport$children$1>) null));
    }

    public final ChildHandle attachChild(ChildJob child) {
        boolean added;
        Throwable rootCause;
        ChildHandleNode node = new ChildHandleNode(child);
        node.setJob(this);
        while (true) {
            Object state$iv = getState$kotlinx_coroutines_core();
            if (!(state$iv instanceof Empty)) {
                if (!(state$iv instanceof Incomplete)) {
                    added = false;
                    break;
                }
                NodeList list$iv = ((Incomplete) state$iv).getList();
                if (list$iv == null) {
                    Intrinsics.checkNotNull(state$iv, "null cannot be cast to non-null type kotlinx.coroutines.JobNode");
                    promoteSingleToNodeList((JobNode) state$iv);
                } else {
                    Incomplete incomplete = (Incomplete) state$iv;
                    NodeList list = list$iv;
                    if (!list.addLast(node, 7)) {
                        boolean addedBeforeCompletion = list.addLast(node, 3);
                        Object latestState = getState$kotlinx_coroutines_core();
                        if (latestState instanceof Finishing) {
                            rootCause = ((Finishing) latestState).getRootCause();
                        } else if (!DebugKt.getASSERTIONS_ENABLED() || !(latestState instanceof Incomplete)) {
                            CompletedExceptionally completedExceptionally = latestState instanceof CompletedExceptionally ? (CompletedExceptionally) latestState : null;
                            rootCause = completedExceptionally != null ? completedExceptionally.cause : null;
                        } else {
                            throw new AssertionError();
                        }
                        node.invoke(rootCause);
                        if (!addedBeforeCompletion) {
                            return NonDisposableHandle.INSTANCE;
                        }
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if (!(rootCause != null)) {
                                throw new AssertionError();
                            }
                        }
                    }
                    added = true;
                }
            } else if (!((Empty) state$iv).isActive()) {
                promoteEmptyToNodeList((Empty) state$iv);
            } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state$iv, node)) {
                added = true;
                break;
            }
        }
        if (added) {
            return node;
        }
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        CompletedExceptionally completedExceptionally2 = state$kotlinx_coroutines_core instanceof CompletedExceptionally ? (CompletedExceptionally) state$kotlinx_coroutines_core : null;
        node.invoke(completedExceptionally2 != null ? completedExceptionally2.cause : null);
        return NonDisposableHandle.INSTANCE;
    }

    public void handleOnCompletionException$kotlinx_coroutines_core(Throwable exception) {
        throw exception;
    }

    /* access modifiers changed from: protected */
    public void onCancelling(Throwable cause) {
    }

    /* access modifiers changed from: protected */
    public boolean isScopedCoroutine() {
        return false;
    }

    public boolean getHandlesException$kotlinx_coroutines_core() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean handleJobException(Throwable exception) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onCompletionInternal(Object state) {
    }

    /* access modifiers changed from: protected */
    public void afterCompletion(Object state) {
    }

    public String toString() {
        return toDebugString() + '@' + DebugStringsKt.getHexAddress(this);
    }

    public final String toDebugString() {
        return nameString$kotlinx_coroutines_core() + '{' + stateString(getState$kotlinx_coroutines_core()) + '}';
    }

    public String nameString$kotlinx_coroutines_core() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    private final String stateString(Object state) {
        if (state instanceof Finishing) {
            if (((Finishing) state).isCancelling()) {
                return "Cancelling";
            }
            if (((Finishing) state).isCompleting()) {
                return "Completing";
            }
            return "Active";
        } else if (state instanceof Incomplete) {
            if (((Incomplete) state).isActive()) {
                return "Active";
            }
            return "New";
        } else if (state instanceof CompletedExceptionally) {
            return "Cancelled";
        } else {
            return "Completed";
        }
    }

    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00060\u0002j\u0002`\u00012\u00020\u0003B!\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020\t0$2\b\u0010%\u001a\u0004\u0018\u00010\tJ\u000e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\tJ\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0*H\u0002J\b\u0010+\u001a\u00020,H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\t\u0010\u000e\u001a\u00020\u000fX\u0004R$\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00078F@FX\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0015X\u0004R(\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\t8F@FX\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0015X\u0004R(\u0010\u001b\u001a\u0004\u0018\u00010\u00022\b\u0010\u0010\u001a\u0004\u0018\u00010\u00028B@BX\u000e¢\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b \u0010\u0011R\u0011\u0010!\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b!\u0010\u0011R\u0014\u0010\"\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u0011¨\u0006-"}, d2 = {"Lkotlinx/coroutines/JobSupport$Finishing;", "Lkotlinx/coroutines/internal/SynchronizedObject;", "", "Lkotlinx/coroutines/Incomplete;", "list", "Lkotlinx/coroutines/NodeList;", "isCompleting", "", "rootCause", "", "<init>", "(Lkotlinx/coroutines/NodeList;ZLjava/lang/Throwable;)V", "getList", "()Lkotlinx/coroutines/NodeList;", "_isCompleting", "Lkotlinx/atomicfu/AtomicBoolean;", "value", "()Z", "setCompleting", "(Z)V", "_rootCause", "Lkotlinx/atomicfu/AtomicRef;", "getRootCause", "()Ljava/lang/Throwable;", "setRootCause", "(Ljava/lang/Throwable;)V", "_exceptionsHolder", "exceptionsHolder", "getExceptionsHolder", "()Ljava/lang/Object;", "setExceptionsHolder", "(Ljava/lang/Object;)V", "isSealed", "isCancelling", "isActive", "sealLocked", "", "proposedException", "addExceptionLocked", "", "exception", "allocateList", "Ljava/util/ArrayList;", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    private static final class Finishing implements Incomplete {
        /* access modifiers changed from: private */
        public static final /* synthetic */ AtomicReferenceFieldUpdater _exceptionsHolder$volatile$FU;
        /* access modifiers changed from: private */
        public static final /* synthetic */ AtomicIntegerFieldUpdater _isCompleting$volatile$FU;
        /* access modifiers changed from: private */
        public static final /* synthetic */ AtomicReferenceFieldUpdater _rootCause$volatile$FU;
        private volatile /* synthetic */ Object _exceptionsHolder$volatile;
        private volatile /* synthetic */ int _isCompleting$volatile;
        private volatile /* synthetic */ Object _rootCause$volatile;
        private final NodeList list;

        static {
            Class<Finishing> cls = Finishing.class;
            _isCompleting$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_isCompleting$volatile");
            _rootCause$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_rootCause$volatile");
            _exceptionsHolder$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_exceptionsHolder$volatile");
        }

        private final /* synthetic */ Object get_exceptionsHolder$volatile() {
            return this._exceptionsHolder$volatile;
        }

        private final /* synthetic */ int get_isCompleting$volatile() {
            return this._isCompleting$volatile;
        }

        private final /* synthetic */ Object get_rootCause$volatile() {
            return this._rootCause$volatile;
        }

        private final /* synthetic */ void set_exceptionsHolder$volatile(Object obj) {
            this._exceptionsHolder$volatile = obj;
        }

        private final /* synthetic */ void set_isCompleting$volatile(int i) {
            this._isCompleting$volatile = i;
        }

        private final /* synthetic */ void set_rootCause$volatile(Object obj) {
            this._rootCause$volatile = obj;
        }

        public NodeList getList() {
            return this.list;
        }

        public Finishing(NodeList list2, boolean isCompleting, Throwable rootCause) {
            this.list = list2;
            this._isCompleting$volatile = isCompleting;
            this._rootCause$volatile = rootCause;
        }

        public final boolean isCompleting() {
            return _isCompleting$volatile$FU.get(this) != 0;
        }

        public final void setCompleting(boolean value) {
            _isCompleting$volatile$FU.set(this, value);
        }

        public final Throwable getRootCause() {
            return (Throwable) _rootCause$volatile$FU.get(this);
        }

        public final void setRootCause(Throwable value) {
            _rootCause$volatile$FU.set(this, value);
        }

        private final Object getExceptionsHolder() {
            return _exceptionsHolder$volatile$FU.get(this);
        }

        private final void setExceptionsHolder(Object value) {
            _exceptionsHolder$volatile$FU.set(this, value);
        }

        public final boolean isSealed() {
            return getExceptionsHolder() == JobSupportKt.SEALED;
        }

        public final boolean isCancelling() {
            return getRootCause() != null;
        }

        public boolean isActive() {
            return getRootCause() == null;
        }

        public final List<Throwable> sealLocked(Throwable proposedException) {
            ArrayList it;
            Object eh = getExceptionsHolder();
            if (eh == null) {
                it = allocateList();
            } else if (eh instanceof Throwable) {
                it = allocateList();
                it.add(eh);
            } else if (eh instanceof ArrayList) {
                it = (ArrayList) eh;
            } else {
                throw new IllegalStateException(("State is " + eh).toString());
            }
            Throwable rootCause = getRootCause();
            if (rootCause != null) {
                it.add(0, rootCause);
            }
            if (proposedException != null && !Intrinsics.areEqual((Object) proposedException, (Object) rootCause)) {
                it.add(proposedException);
            }
            setExceptionsHolder(JobSupportKt.SEALED);
            return it;
        }

        public final void addExceptionLocked(Throwable exception) {
            Throwable rootCause = getRootCause();
            if (rootCause == null) {
                setRootCause(exception);
            } else if (exception != rootCause) {
                Object eh = getExceptionsHolder();
                if (eh == null) {
                    setExceptionsHolder(exception);
                } else if (eh instanceof Throwable) {
                    if (exception != eh) {
                        ArrayList allocateList = allocateList();
                        ArrayList $this$addExceptionLocked_u24lambda_u242 = allocateList;
                        $this$addExceptionLocked_u24lambda_u242.add(eh);
                        $this$addExceptionLocked_u24lambda_u242.add(exception);
                        setExceptionsHolder(allocateList);
                    }
                } else if (eh instanceof ArrayList) {
                    ((ArrayList) eh).add(exception);
                } else {
                    throw new IllegalStateException(("State is " + eh).toString());
                }
            }
        }

        private final ArrayList<Throwable> allocateList() {
            return new ArrayList<>(4);
        }

        public String toString() {
            return "Finishing[cancelling=" + isCancelling() + ", completing=" + isCompleting() + ", rootCause=" + getRootCause() + ", exceptions=" + getExceptionsHolder() + ", list=" + getList() + ']';
        }
    }

    private final boolean isCancelling(Incomplete $this$isCancelling) {
        return ($this$isCancelling instanceof Finishing) && ((Finishing) $this$isCancelling).isCancelling();
    }

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0004\b\n\u0010\u000bJ\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0014"}, d2 = {"Lkotlinx/coroutines/JobSupport$ChildCompletion;", "Lkotlinx/coroutines/JobNode;", "parent", "Lkotlinx/coroutines/JobSupport;", "state", "Lkotlinx/coroutines/JobSupport$Finishing;", "child", "Lkotlinx/coroutines/ChildHandleNode;", "proposedUpdate", "", "<init>", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "onCancelling", "", "getOnCancelling", "()Z", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    private static final class ChildCompletion extends JobNode {
        private final ChildHandleNode child;
        private final JobSupport parent;
        private final Object proposedUpdate;
        private final Finishing state;

        public ChildCompletion(JobSupport parent2, Finishing state2, ChildHandleNode child2, Object proposedUpdate2) {
            this.parent = parent2;
            this.state = state2;
            this.child = child2;
            this.proposedUpdate = proposedUpdate2;
        }

        public boolean getOnCancelling() {
            return false;
        }

        public void invoke(Throwable cause) {
            this.parent.continueCompleting(this.state, this.child, this.proposedUpdate);
        }
    }

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lkotlinx/coroutines/JobSupport$AwaitContinuation;", "T", "Lkotlinx/coroutines/CancellableContinuationImpl;", "delegate", "Lkotlin/coroutines/Continuation;", "job", "Lkotlinx/coroutines/JobSupport;", "<init>", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/JobSupport;)V", "getContinuationCancellationCause", "", "parent", "Lkotlinx/coroutines/Job;", "nameString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    private static final class AwaitContinuation<T> extends CancellableContinuationImpl<T> {
        private final JobSupport job;

        public AwaitContinuation(Continuation<? super T> delegate, JobSupport job2) {
            super(delegate, 1);
            this.job = job2;
        }

        public Throwable getContinuationCancellationCause(Job parent) {
            Throwable it;
            Object state = this.job.getState$kotlinx_coroutines_core();
            if ((state instanceof Finishing) && (it = ((Finishing) state).getRootCause()) != null) {
                return it;
            }
            if (state instanceof CompletedExceptionally) {
                return ((CompletedExceptionally) state).cause;
            }
            return parent.getCancellationException();
        }

        /* access modifiers changed from: protected */
        public String nameString() {
            return "AwaitContinuation";
        }
    }

    public final boolean isCompletedExceptionally() {
        return getState$kotlinx_coroutines_core() instanceof CompletedExceptionally;
    }

    public final Throwable getCompletionExceptionOrNull() {
        Object state = getState$kotlinx_coroutines_core();
        if (!(state instanceof Incomplete)) {
            return getExceptionOrNull(state);
        }
        throw new IllegalStateException("This job has not completed yet".toString());
    }

    public final Object getCompletedInternal$kotlinx_coroutines_core() {
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof Incomplete) {
            throw new IllegalStateException("This job has not completed yet".toString());
        } else if (!(state instanceof CompletedExceptionally)) {
            return JobSupportKt.unboxState(state);
        } else {
            throw ((CompletedExceptionally) state).cause;
        }
    }

    /* access modifiers changed from: protected */
    public final Object awaitInternal(Continuation<Object> $completion) {
        Object state;
        do {
            state = getState$kotlinx_coroutines_core();
            if (!(state instanceof Incomplete)) {
                if (!(state instanceof CompletedExceptionally)) {
                    return JobSupportKt.unboxState(state);
                }
                Throwable exception$iv = ((CompletedExceptionally) state).cause;
                if (DebugKt.getRECOVER_STACK_TRACES()) {
                    Continuation<Object> continuation = $completion;
                    if (!(continuation instanceof CoroutineStackFrame)) {
                        throw exception$iv;
                    }
                    throw StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) continuation);
                }
                throw exception$iv;
            }
        } while (startInternal(state) < 0);
        return awaitSuspend($completion);
    }

    /* access modifiers changed from: private */
    public final Object awaitSuspend(Continuation<Object> $completion) {
        AwaitContinuation cont = new AwaitContinuation(IntrinsicsKt.intercepted($completion), this);
        cont.initCancellability();
        CancellableContinuationKt.disposeOnCancellation(cont, JobKt__JobKt.invokeOnCompletion$default(this, false, new ResumeAwaitOnCompletion(cont), 1, (Object) null));
        Object result = cont.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public final SelectClause1<?> getOnAwaitInternal() {
        JobSupport$onAwaitInternal$1 jobSupport$onAwaitInternal$1 = JobSupport$onAwaitInternal$1.INSTANCE;
        Intrinsics.checkNotNull(jobSupport$onAwaitInternal$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        JobSupport$onAwaitInternal$2 jobSupport$onAwaitInternal$2 = JobSupport$onAwaitInternal$2.INSTANCE;
        Intrinsics.checkNotNull(jobSupport$onAwaitInternal$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(jobSupport$onAwaitInternal$1, 3);
        return new SelectClause1Impl<>(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(jobSupport$onAwaitInternal$2, 3), (Function3) null, 8, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: private */
    public final void onAwaitInternalRegFunc(SelectInstance<?> select, Object ignoredParam) {
        Object state;
        do {
            state = getState$kotlinx_coroutines_core();
            if (!(state instanceof Incomplete)) {
                select.selectInRegistrationPhase(state instanceof CompletedExceptionally ? state : JobSupportKt.unboxState(state));
                return;
            }
        } while (startInternal(state) < 0);
        select.disposeOnCompletion(JobKt__JobKt.invokeOnCompletion$default(this, false, new SelectOnAwaitCompletionHandler(select), 1, (Object) null));
    }

    /* access modifiers changed from: private */
    public final Object onAwaitInternalProcessResFunc(Object ignoredParam, Object result) {
        if (!(result instanceof CompletedExceptionally)) {
            return result;
        }
        throw ((CompletedExceptionally) result).cause;
    }

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/JobSupport$SelectOnAwaitCompletionHandler;", "Lkotlinx/coroutines/JobNode;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "<init>", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/selects/SelectInstance;)V", "onCancelling", "", "getOnCancelling", "()Z", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: JobSupport.kt */
    private final class SelectOnAwaitCompletionHandler extends JobNode {
        private final SelectInstance<?> select;

        public SelectOnAwaitCompletionHandler(SelectInstance<?> select2) {
            this.select = select2;
        }

        public boolean getOnCancelling() {
            return false;
        }

        public void invoke(Throwable cause) {
            Object state = JobSupport.this.getState$kotlinx_coroutines_core();
            this.select.trySelect(JobSupport.this, state instanceof CompletedExceptionally ? state : JobSupportKt.unboxState(state));
        }
    }
}
