package kotlinx.coroutines.debug.internal;

import _COROUTINE.ArtificialStackFrames;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0006\bÁ\u0002\u0018\u00002\u00020\u0001:\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010&\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020%\u0018\u00010$H\u0002J\r\u0010*\u001a\u00020%H\u0000¢\u0006\u0002\b+J\r\u0010,\u001a\u00020%H\u0000¢\u0006\u0002\b-J\b\u0010.\u001a\u00020%H\u0002J\b\u0010/\u001a\u00020%H\u0002J\u0015\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0000¢\u0006\u0002\b4J9\u00105\u001a\u00020%*\u0002032\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020)072\n\u00108\u001a\u00060:j\u0002`92\u0006\u0010;\u001a\u000201H\u0002¢\u0006\u0002\u0010<J9\u0010B\u001a\b\u0012\u0004\u0012\u0002HD0C\"\b\b\u0000\u0010D*\u00020\u00012\u001e\b\u0004\u0010E\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u0002HD0FH\bJ\u0011\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00010I¢\u0006\u0002\u0010JJ\u000e\u0010K\u001a\u0002012\u0006\u0010L\u001a\u00020MJ\f\u0010N\u001a\u000201*\u00020\u0001H\u0002J\f\u0010O\u001a\b\u0012\u0004\u0012\u00020M0CJ\f\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0CJ\u0010\u0010R\u001a\u00020%2\u0006\u0010S\u001a\u00020TH\u0001J\u0010\u0010U\u001a\u00020\r*\u0006\u0012\u0002\b\u00030\fH\u0002J\u0010\u0010V\u001a\u00020%2\u0006\u0010S\u001a\u00020TH\u0002J\u001e\u0010W\u001a\u00020%2\u0006\u0010S\u001a\u00020T2\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00050CH\u0002J\"\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00050C2\u0006\u0010L\u001a\u00020M2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00050CJ.\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00050C2\u0006\u0010\\\u001a\u0002012\b\u0010]\u001a\u0004\u0018\u00010\t2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00050CH\u0002J=\u0010^\u001a\u000e\u0012\u0004\u0012\u00020`\u0012\u0004\u0012\u00020`0_2\u0006\u0010a\u001a\u00020`2\f\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00050I2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00050CH\u0002¢\u0006\u0002\u0010cJ1\u0010d\u001a\u00020`2\u0006\u0010e\u001a\u00020`2\f\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00050I2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00050CH\u0002¢\u0006\u0002\u0010fJ\u0019\u0010g\u001a\u00020%2\n\u0010h\u001a\u0006\u0012\u0002\b\u00030iH\u0000¢\u0006\u0002\bjJ\u0019\u0010k\u001a\u00020%2\n\u0010h\u001a\u0006\u0012\u0002\b\u00030iH\u0000¢\u0006\u0002\blJ\u001c\u0010m\u001a\u00020%2\n\u0010h\u001a\u0006\u0012\u0002\b\u00030i2\u0006\u0010\\\u001a\u000201H\u0002J\u0018\u0010n\u001a\u00020%2\u0006\u0010h\u001a\u00020(2\u0006\u0010\\\u001a\u000201H\u0002J\u000f\u0010o\u001a\u0004\u0018\u00010(*\u00020(H\u0010J(\u0010m\u001a\u00020%2\n\u0010p\u001a\u0006\u0012\u0002\b\u00030\f2\n\u0010h\u001a\u0006\u0012\u0002\b\u00030i2\u0006\u0010\\\u001a\u000201H\u0002J\u0016\u0010p\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f*\u0006\u0012\u0002\b\u00030iH\u0002J\u0013\u0010p\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f*\u00020(H\u0010J'\u0010q\u001a\b\u0012\u0004\u0012\u0002Hr0i\"\u0004\b\u0000\u0010r2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Hr0iH\u0000¢\u0006\u0002\btJ\u0012\u0010u\u001a\u00020v*\b\u0012\u0004\u0012\u00020\u00050CH\u0002J,\u0010w\u001a\b\u0012\u0004\u0012\u0002Hr0i\"\u0004\b\u0000\u0010r2\f\u0010s\u001a\b\u0012\u0004\u0012\u0002Hr0i2\b\u0010h\u001a\u0004\u0018\u00010vH\u0002J\u0014\u0010x\u001a\u00020%2\n\u0010p\u001a\u0006\u0012\u0002\b\u00030\fH\u0002J%\u0010y\u001a\b\u0012\u0004\u0012\u00020\u00050C\"\b\b\u0000\u0010r*\u00020z2\u0006\u0010{\u001a\u0002HrH\u0002¢\u0006\u0002\u0010|R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\r0\u000bX\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u000f8BX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\t\u0010\u0012\u001a\u00020\u0013X\u0004R\u0011\u0010\u0014\u001a\u00020\r8G¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\t\u0010\u0017\u001a\u00020\u0018X\u0004R\u001a\u0010\u0019\u001a\u00020\rX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0016\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\rX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u001cR\u001a\u0010 \u001a\u00020\rX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0016\"\u0004\b\"\u0010\u001cR\u001c\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020%\u0018\u00010$X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010'\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020)0\u000bX\u0004¢\u0006\u0002\n\u0000R\u001e\u0010=\u001a\u000201*\u0002038BX\u0004¢\u0006\f\u0012\u0004\b>\u0010?\u001a\u0004\b@\u0010AR\u0018\u0010}\u001a\u00020\r*\u00020\u00058BX\u0004¢\u0006\u0006\u001a\u0004\b}\u0010~¨\u0006\u0001"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl;", "", "<init>", "()V", "ARTIFICIAL_FRAME", "Ljava/lang/StackTraceElement;", "dateFormat", "Ljava/text/SimpleDateFormat;", "weakRefCleanerThread", "Ljava/lang/Thread;", "capturedCoroutinesMap", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "", "capturedCoroutines", "", "getCapturedCoroutines", "()Ljava/util/Set;", "installations", "Lkotlinx/atomicfu/AtomicInt;", "isInstalled", "isInstalled$kotlinx_coroutines_debug", "()Z", "sequenceNumber", "Lkotlinx/atomicfu/AtomicLong;", "sanitizeStackTraces", "getSanitizeStackTraces$kotlinx_coroutines_core", "setSanitizeStackTraces$kotlinx_coroutines_core", "(Z)V", "enableCreationStackTraces", "getEnableCreationStackTraces$kotlinx_coroutines_core", "setEnableCreationStackTraces$kotlinx_coroutines_core", "ignoreCoroutinesWithEmptyContext", "getIgnoreCoroutinesWithEmptyContext", "setIgnoreCoroutinesWithEmptyContext", "dynamicAttach", "Lkotlin/Function1;", "", "getDynamicAttach", "callerInfoCache", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "install", "install$kotlinx_coroutines_core", "uninstall", "uninstall$kotlinx_coroutines_core", "startWeakRefCleanerThread", "stopWeakRefCleanerThread", "hierarchyToString", "", "job", "Lkotlinx/coroutines/Job;", "hierarchyToString$kotlinx_coroutines_core", "build", "map", "", "builder", "Lkotlin/text/StringBuilder;", "Ljava/lang/StringBuilder;", "indent", "(Lkotlinx/coroutines/Job;Ljava/util/Map;Ljava/lang/StringBuilder;Ljava/lang/String;)V", "debugString", "getDebugString$annotations", "(Lkotlinx/coroutines/Job;)V", "getDebugString", "(Lkotlinx/coroutines/Job;)Ljava/lang/String;", "dumpCoroutinesInfoImpl", "", "R", "create", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext;", "dumpCoroutinesInfoAsJsonAndReferences", "", "()[Ljava/lang/Object;", "enhanceStackTraceWithThreadDumpAsJson", "info", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "toStringRepr", "dumpCoroutinesInfo", "dumpDebuggerInfo", "Lkotlinx/coroutines/debug/internal/DebuggerInfo;", "dumpCoroutines", "out", "Ljava/io/PrintStream;", "isFinished", "dumpCoroutinesSynchronized", "printStackTrace", "frames", "enhanceStackTraceWithThreadDump", "coroutineTrace", "enhanceStackTraceWithThreadDumpImpl", "state", "thread", "findContinuationStartIndex", "Lkotlin/Pair;", "", "indexOfResumeWith", "actualTrace", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)Lkotlin/Pair;", "findIndexOfFrame", "frameIndex", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)I", "probeCoroutineResumed", "frame", "Lkotlin/coroutines/Continuation;", "probeCoroutineResumed$kotlinx_coroutines_core", "probeCoroutineSuspended", "probeCoroutineSuspended$kotlinx_coroutines_core", "updateState", "updateRunningState", "realCaller", "owner", "probeCoroutineCreated", "T", "completion", "probeCoroutineCreated$kotlinx_coroutines_core", "toStackTraceFrame", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "createOwner", "probeCoroutineCompleted", "sanitizeStackTrace", "", "throwable", "(Ljava/lang/Throwable;)Ljava/util/List;", "isInternalMethod", "(Ljava/lang/StackTraceElement;)Z", "CoroutineOwner", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: DebugProbesImpl.kt */
public final class DebugProbesImpl {
    private static final StackTraceElement ARTIFICIAL_FRAME = new ArtificialStackFrames().coroutineCreation();
    public static final DebugProbesImpl INSTANCE = new DebugProbesImpl();
    private static final ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> callerInfoCache = new ConcurrentWeakMap<>(true);
    private static final ConcurrentWeakMap<CoroutineOwner<?>, Boolean> capturedCoroutinesMap = new ConcurrentWeakMap<>(false, 1, (DefaultConstructorMarker) null);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /* access modifiers changed from: private */
    public static final /* synthetic */ DebugProbesImpl$DebugProbesImpl$VolatileWrapper$atomicfu$private debugProbesImpl$VolatileWrapper$atomicfu$private = new DebugProbesImpl$DebugProbesImpl$VolatileWrapper$atomicfu$private((DefaultConstructorMarker) null);
    private static final Function1<Boolean, Unit> dynamicAttach = INSTANCE.getDynamicAttach();
    private static boolean enableCreationStackTraces;
    private static boolean ignoreCoroutinesWithEmptyContext = true;
    private static boolean sanitizeStackTraces = true;
    private static Thread weakRefCleanerThread;

    private static /* synthetic */ void getDebugString$annotations(Job job) {
    }

    private DebugProbesImpl() {
    }

    private final Set<CoroutineOwner<?>> getCapturedCoroutines() {
        return capturedCoroutinesMap.keySet();
    }

    public final boolean isInstalled$kotlinx_coroutines_debug() {
        return DebugProbesImpl$DebugProbesImpl$VolatileWrapper$atomicfu$private.installations$volatile$FU.get(debugProbesImpl$VolatileWrapper$atomicfu$private) > 0;
    }

    public final boolean getSanitizeStackTraces$kotlinx_coroutines_core() {
        return sanitizeStackTraces;
    }

    public final void setSanitizeStackTraces$kotlinx_coroutines_core(boolean z) {
        sanitizeStackTraces = z;
    }

    public final boolean getEnableCreationStackTraces$kotlinx_coroutines_core() {
        return enableCreationStackTraces;
    }

    public final void setEnableCreationStackTraces$kotlinx_coroutines_core(boolean z) {
        enableCreationStackTraces = z;
    }

    public final boolean getIgnoreCoroutinesWithEmptyContext() {
        return ignoreCoroutinesWithEmptyContext;
    }

    public final void setIgnoreCoroutinesWithEmptyContext(boolean z) {
        ignoreCoroutinesWithEmptyContext = z;
    }

    private final Function1<Boolean, Unit> getDynamicAttach() {
        Object obj;
        try {
            Result.Companion companion = Result.Companion;
            DebugProbesImpl debugProbesImpl = this;
            Object newInstance = Class.forName("kotlinx.coroutines.debug.internal.ByteBuddyDynamicAttach").getConstructors()[0].newInstance(new Object[0]);
            Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Function1<kotlin.Boolean, kotlin.Unit>");
            obj = Result.m6constructorimpl((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(newInstance, 1));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m6constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m12isFailureimpl(obj)) {
            obj = null;
        }
        return (Function1) obj;
    }

    public final void install$kotlinx_coroutines_core() {
        Function1<Boolean, Unit> function1;
        if (DebugProbesImpl$DebugProbesImpl$VolatileWrapper$atomicfu$private.installations$volatile$FU.incrementAndGet(debugProbesImpl$VolatileWrapper$atomicfu$private) <= 1) {
            startWeakRefCleanerThread();
            if (!AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core() && (function1 = dynamicAttach) != null) {
                function1.invoke(true);
            }
        }
    }

    public final void uninstall$kotlinx_coroutines_core() {
        Function1<Boolean, Unit> function1;
        if (!isInstalled$kotlinx_coroutines_debug()) {
            throw new IllegalStateException("Agent was not installed".toString());
        } else if (DebugProbesImpl$DebugProbesImpl$VolatileWrapper$atomicfu$private.installations$volatile$FU.decrementAndGet(debugProbesImpl$VolatileWrapper$atomicfu$private) == 0) {
            stopWeakRefCleanerThread();
            capturedCoroutinesMap.clear();
            callerInfoCache.clear();
            if (!AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core() && (function1 = dynamicAttach) != null) {
                function1.invoke(false);
            }
        }
    }

    private final void startWeakRefCleanerThread() {
        weakRefCleanerThread = ThreadsKt.thread$default(false, true, (ClassLoader) null, "Coroutines Debugger Cleaner", 0, new DebugProbesImpl$$ExternalSyntheticLambda1(), 21, (Object) null);
    }

    /* access modifiers changed from: private */
    public static final Unit startWeakRefCleanerThread$lambda$2() {
        callerInfoCache.runWeakRefQueueCleaningLoopUntilInterrupted();
        return Unit.INSTANCE;
    }

    private final void stopWeakRefCleanerThread() {
        Thread thread = weakRefCleanerThread;
        if (thread != null) {
            weakRefCleanerThread = null;
            thread.interrupt();
            thread.join();
        }
    }

    public final String hierarchyToString$kotlinx_coroutines_core(Job job) {
        if (isInstalled$kotlinx_coroutines_debug()) {
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : getCapturedCoroutines()) {
                if (((CoroutineOwner) element$iv$iv).delegate.getContext().get(Job.Key) != null) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            Iterable $this$associateBy$iv = (List) destination$iv$iv;
            Map jobToStack = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16));
            for (Object element$iv$iv2 : $this$associateBy$iv) {
                jobToStack.put(JobKt.getJob(((CoroutineOwner) element$iv$iv2).delegate.getContext()), ((CoroutineOwner) element$iv$iv2).info);
            }
            StringBuilder $this$hierarchyToString_u24lambda_u247 = new StringBuilder();
            INSTANCE.build(job, jobToStack, $this$hierarchyToString_u24lambda_u247, "");
            String sb = $this$hierarchyToString_u24lambda_u247.toString();
            Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
            return sb;
        }
        throw new IllegalStateException("Debug probes are not installed".toString());
    }

    private final void build(Job $this$build, Map<Job, DebugCoroutineInfoImpl> map, StringBuilder builder, String indent) {
        String newIndent;
        DebugCoroutineInfoImpl info = map.get($this$build);
        if (info != null) {
            builder.append(indent + getDebugString($this$build) + ", continuation is " + info.getState$kotlinx_coroutines_core() + " at line " + ((StackTraceElement) CollectionsKt.firstOrNull(info.lastObservedStackTrace$kotlinx_coroutines_core())) + 10);
            newIndent = indent + 9;
        } else if (!($this$build instanceof ScopeCoroutine)) {
            builder.append(indent + getDebugString($this$build) + 10);
            newIndent = indent + 9;
        } else {
            newIndent = indent;
        }
        for (Job child : $this$build.getChildren()) {
            build(child, map, builder, newIndent);
        }
    }

    private final String getDebugString(Job $this$debugString) {
        return $this$debugString instanceof JobSupport ? ((JobSupport) $this$debugString).toDebugString() : $this$debugString.toString();
    }

    private final <R> List<R> dumpCoroutinesInfoImpl(Function2<? super CoroutineOwner<?>, ? super CoroutineContext, ? extends R> create) {
        if (isInstalled$kotlinx_coroutines_debug()) {
            return SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.sortedWith(CollectionsKt.asSequence(getCapturedCoroutines()), new DebugProbesImpl$dumpCoroutinesInfoImpl$$inlined$sortedBy$1()), new DebugProbesImpl$dumpCoroutinesInfoImpl$3(create)));
        }
        throw new IllegalStateException("Debug probes are not installed".toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0038, code lost:
        r8 = r8.getName();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object[] dumpCoroutinesInfoAsJsonAndReferences() {
        /*
            r15 = this;
            java.util.List r0 = r15.dumpCoroutinesInfo()
            int r1 = r0.size()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r1)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>(r1)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>(r1)
            java.util.Iterator r5 = r0.iterator()
        L_0x001b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00d2
            java.lang.Object r6 = r5.next()
            kotlinx.coroutines.debug.internal.DebugCoroutineInfo r6 = (kotlinx.coroutines.debug.internal.DebugCoroutineInfo) r6
            kotlin.coroutines.CoroutineContext r7 = r6.getContext()
            kotlinx.coroutines.CoroutineName$Key r8 = kotlinx.coroutines.CoroutineName.Key
            kotlin.coroutines.CoroutineContext$Key r8 = (kotlin.coroutines.CoroutineContext.Key) r8
            kotlin.coroutines.CoroutineContext$Element r8 = r7.get(r8)
            kotlinx.coroutines.CoroutineName r8 = (kotlinx.coroutines.CoroutineName) r8
            r9 = 0
            if (r8 == 0) goto L_0x0043
            java.lang.String r8 = r8.getName()
            if (r8 == 0) goto L_0x0043
            java.lang.String r8 = r15.toStringRepr(r8)
            goto L_0x0044
        L_0x0043:
            r8 = r9
        L_0x0044:
            kotlinx.coroutines.CoroutineDispatcher$Key r10 = kotlinx.coroutines.CoroutineDispatcher.Key
            kotlin.coroutines.CoroutineContext$Key r10 = (kotlin.coroutines.CoroutineContext.Key) r10
            kotlin.coroutines.CoroutineContext$Element r10 = r7.get(r10)
            kotlinx.coroutines.CoroutineDispatcher r10 = (kotlinx.coroutines.CoroutineDispatcher) r10
            if (r10 == 0) goto L_0x0055
            java.lang.String r10 = r15.toStringRepr(r10)
            goto L_0x0056
        L_0x0055:
            r10 = r9
        L_0x0056:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "\n                {\n                    \"name\": "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r8)
            java.lang.String r12 = ",\n                    \"id\": "
            java.lang.StringBuilder r11 = r11.append(r12)
            kotlinx.coroutines.CoroutineId$Key r12 = kotlinx.coroutines.CoroutineId.Key
            kotlin.coroutines.CoroutineContext$Key r12 = (kotlin.coroutines.CoroutineContext.Key) r12
            kotlin.coroutines.CoroutineContext$Element r12 = r7.get(r12)
            kotlinx.coroutines.CoroutineId r12 = (kotlinx.coroutines.CoroutineId) r12
            if (r12 == 0) goto L_0x0082
            long r12 = r12.getId()
            java.lang.Long r9 = java.lang.Long.valueOf(r12)
        L_0x0082:
            java.lang.StringBuilder r9 = r11.append(r9)
            java.lang.String r11 = ",\n                    \"dispatcher\": "
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r11 = ",\n                    \"sequenceNumber\": "
            java.lang.StringBuilder r9 = r9.append(r11)
            long r11 = r6.getSequenceNumber()
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r11 = ",\n                    \"state\": \""
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r11 = r6.getState()
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r11 = "\"\n                } \n                "
            java.lang.StringBuilder r9 = r9.append(r11)
            java.lang.String r9 = r9.toString()
            java.lang.String r9 = kotlin.text.StringsKt.trimIndent(r9)
            r4.add(r9)
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r9 = r6.getLastObservedFrame()
            r3.add(r9)
            java.lang.Thread r9 = r6.getLastObservedThread()
            r2.add(r9)
            goto L_0x001b
        L_0x00d2:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r6 = 91
            java.lang.StringBuilder r5 = r5.append(r6)
            r6 = r4
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            r13 = 63
            r14 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            java.lang.String r6 = kotlin.collections.CollectionsKt.joinToString$default(r6, r7, r8, r9, r10, r11, r12, r13, r14)
            java.lang.StringBuilder r5 = r5.append(r6)
            r6 = 93
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = r2
            java.util.Collection r6 = (java.util.Collection) r6
            r7 = 0
            r8 = r6
            r9 = 0
            java.lang.Thread[] r10 = new java.lang.Thread[r9]
            java.lang.Object[] r6 = r8.toArray(r10)
            r7 = r3
            java.util.Collection r7 = (java.util.Collection) r7
            r8 = 0
            r10 = r7
            kotlin.coroutines.jvm.internal.CoroutineStackFrame[] r11 = new kotlin.coroutines.jvm.internal.CoroutineStackFrame[r9]
            java.lang.Object[] r7 = r10.toArray(r11)
            r8 = r0
            java.util.Collection r8 = (java.util.Collection) r8
            r10 = 0
            r11 = r8
            kotlinx.coroutines.debug.internal.DebugCoroutineInfo[] r9 = new kotlinx.coroutines.debug.internal.DebugCoroutineInfo[r9]
            java.lang.Object[] r8 = r11.toArray(r9)
            java.lang.Object[] r5 = new java.lang.Object[]{r5, r6, r7, r8}
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutinesInfoAsJsonAndReferences():java.lang.Object[]");
    }

    public final String enhanceStackTraceWithThreadDumpAsJson(DebugCoroutineInfo info) {
        List stackTraceElements = enhanceStackTraceWithThreadDump(info, info.lastObservedStackTrace());
        List stackTraceElementsInfoAsJson = new ArrayList();
        for (StackTraceElement element : stackTraceElements) {
            StringBuilder append = new StringBuilder().append("\n                {\n                    \"declaringClass\": \"").append(element.getClassName()).append("\",\n                    \"methodName\": \"").append(element.getMethodName()).append("\",\n                    \"fileName\": ");
            String fileName = element.getFileName();
            stackTraceElementsInfoAsJson.add(StringsKt.trimIndent(append.append(fileName != null ? toStringRepr(fileName) : null).append(",\n                    \"lineNumber\": ").append(element.getLineNumber()).append("\n                }\n                ").toString()));
        }
        return '[' + CollectionsKt.joinToString$default(stackTraceElementsInfoAsJson, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 63, (Object) null) + ']';
    }

    private final String toStringRepr(Object $this$toStringRepr) {
        return DebugProbesImplKt.repr($this$toStringRepr.toString());
    }

    public final List<DebugCoroutineInfo> dumpCoroutinesInfo() {
        if (isInstalled$kotlinx_coroutines_debug()) {
            return SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.sortedWith(CollectionsKt.asSequence(getCapturedCoroutines()), new DebugProbesImpl$dumpCoroutinesInfoImpl$$inlined$sortedBy$1()), new DebugProbesImpl$dumpCoroutinesInfo$$inlined$dumpCoroutinesInfoImpl$1()));
        }
        throw new IllegalStateException("Debug probes are not installed".toString());
    }

    public final List<DebuggerInfo> dumpDebuggerInfo() {
        if (isInstalled$kotlinx_coroutines_debug()) {
            return SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.sortedWith(CollectionsKt.asSequence(getCapturedCoroutines()), new DebugProbesImpl$dumpCoroutinesInfoImpl$$inlined$sortedBy$1()), new DebugProbesImpl$dumpDebuggerInfo$$inlined$dumpCoroutinesInfoImpl$1()));
        }
        throw new IllegalStateException("Debug probes are not installed".toString());
    }

    public final void dumpCoroutines(PrintStream out) {
        synchronized (out) {
            INSTANCE.dumpCoroutinesSynchronized(out);
            Unit unit = Unit.INSTANCE;
        }
    }

    /* access modifiers changed from: private */
    public final boolean isFinished(CoroutineOwner<?> $this$isFinished) {
        Job job;
        CoroutineContext context = $this$isFinished.info.getContext();
        if (context == null || (job = (Job) context.get(Job.Key)) == null || !job.isCompleted()) {
            return false;
        }
        capturedCoroutinesMap.remove($this$isFinished);
        return true;
    }

    private final void dumpCoroutinesSynchronized(PrintStream out) {
        String state;
        if (isInstalled$kotlinx_coroutines_debug()) {
            out.print("Coroutines dump " + dateFormat.format(Long.valueOf(System.currentTimeMillis())));
            for (CoroutineOwner owner : SequencesKt.sortedWith(SequencesKt.filter(CollectionsKt.asSequence(getCapturedCoroutines()), new DebugProbesImpl$$ExternalSyntheticLambda0()), new DebugProbesImpl$dumpCoroutinesSynchronized$$inlined$sortedBy$1())) {
                DebugCoroutineInfoImpl info = owner.info;
                List observedStackTrace = info.lastObservedStackTrace$kotlinx_coroutines_core();
                List enhancedStackTrace = INSTANCE.enhanceStackTraceWithThreadDumpImpl(info.getState$kotlinx_coroutines_core(), info.lastObservedThread, observedStackTrace);
                if (!Intrinsics.areEqual((Object) info.getState$kotlinx_coroutines_core(), (Object) DebugCoroutineInfoImplKt.RUNNING) || enhancedStackTrace != observedStackTrace) {
                    state = info.getState$kotlinx_coroutines_core();
                } else {
                    state = info.getState$kotlinx_coroutines_core() + " (Last suspension stacktrace, not an actual stacktrace)";
                }
                out.print("\n\nCoroutine " + owner.delegate + ", state: " + state);
                if (observedStackTrace.isEmpty()) {
                    out.print("\n\tat " + ARTIFICIAL_FRAME);
                    INSTANCE.printStackTrace(out, info.getCreationStackTrace());
                } else {
                    INSTANCE.printStackTrace(out, enhancedStackTrace);
                }
            }
            return;
        }
        throw new IllegalStateException("Debug probes are not installed".toString());
    }

    /* access modifiers changed from: private */
    public static final boolean dumpCoroutinesSynchronized$lambda$14(CoroutineOwner it) {
        return !INSTANCE.isFinished(it);
    }

    private final void printStackTrace(PrintStream out, List<StackTraceElement> frames) {
        for (StackTraceElement frame : frames) {
            out.print("\n\tat " + frame);
        }
    }

    public final List<StackTraceElement> enhanceStackTraceWithThreadDump(DebugCoroutineInfo info, List<StackTraceElement> coroutineTrace) {
        return enhanceStackTraceWithThreadDumpImpl(info.getState(), info.getLastObservedThread(), coroutineTrace);
    }

    private final List<StackTraceElement> enhanceStackTraceWithThreadDumpImpl(String state, Thread thread, List<StackTraceElement> coroutineTrace) {
        Object obj;
        if (!Intrinsics.areEqual((Object) state, (Object) DebugCoroutineInfoImplKt.RUNNING) || thread == null) {
            return coroutineTrace;
        }
        try {
            Result.Companion companion = Result.Companion;
            DebugProbesImpl debugProbesImpl = this;
            obj = Result.m6constructorimpl(thread.getStackTrace());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m6constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m12isFailureimpl(obj)) {
            obj = null;
        }
        StackTraceElement[] actualTrace = (StackTraceElement[]) obj;
        if (actualTrace == null) {
            return coroutineTrace;
        }
        StackTraceElement[] stackTraceElementArr = actualTrace;
        int index$iv = 0;
        int length = stackTraceElementArr.length;
        while (true) {
            if (index$iv >= length) {
                index$iv = -1;
                break;
            }
            StackTraceElement it = stackTraceElementArr[index$iv];
            if (Intrinsics.areEqual((Object) it.getClassName(), (Object) "kotlin.coroutines.jvm.internal.BaseContinuationImpl") && Intrinsics.areEqual((Object) it.getMethodName(), (Object) "resumeWith") && Intrinsics.areEqual((Object) it.getFileName(), (Object) "ContinuationImpl.kt")) {
                break;
            }
            index$iv++;
        }
        Pair<Integer, Integer> findContinuationStartIndex = findContinuationStartIndex(index$iv, actualTrace, coroutineTrace);
        int continuationStartFrame = findContinuationStartIndex.component1().intValue();
        int delta = findContinuationStartIndex.component2().intValue();
        if (continuationStartFrame == -1) {
            return coroutineTrace;
        }
        ArrayList result = new ArrayList((((coroutineTrace.size() + index$iv) - continuationStartFrame) - 1) - delta);
        int i = index$iv - delta;
        for (int index = 0; index < i; index++) {
            result.add(actualTrace[index]);
        }
        int size = coroutineTrace.size();
        for (int index2 = continuationStartFrame + 1; index2 < size; index2++) {
            result.add(coroutineTrace.get(index2));
        }
        return result;
    }

    private final Pair<Integer, Integer> findContinuationStartIndex(int indexOfResumeWith, StackTraceElement[] actualTrace, List<StackTraceElement> coroutineTrace) {
        for (int i = 0; i < 3; i++) {
            int it = i;
            int result = INSTANCE.findIndexOfFrame((indexOfResumeWith - 1) - it, actualTrace, coroutineTrace);
            if (result != -1) {
                return TuplesKt.to(Integer.valueOf(result), Integer.valueOf(it));
            }
        }
        return TuplesKt.to(-1, 0);
    }

    private final int findIndexOfFrame(int frameIndex, StackTraceElement[] actualTrace, List<StackTraceElement> coroutineTrace) {
        StackTraceElement continuationFrame = (StackTraceElement) ArraysKt.getOrNull((T[]) actualTrace, frameIndex);
        if (continuationFrame == null) {
            return -1;
        }
        int index$iv = 0;
        for (StackTraceElement it : coroutineTrace) {
            if (Intrinsics.areEqual((Object) it.getFileName(), (Object) continuationFrame.getFileName()) && Intrinsics.areEqual((Object) it.getClassName(), (Object) continuationFrame.getClassName()) && Intrinsics.areEqual((Object) it.getMethodName(), (Object) continuationFrame.getMethodName())) {
                return index$iv;
            }
            index$iv++;
        }
        return -1;
    }

    public final void probeCoroutineResumed$kotlinx_coroutines_core(Continuation<?> frame) {
        updateState(frame, DebugCoroutineInfoImplKt.RUNNING);
    }

    public final void probeCoroutineSuspended$kotlinx_coroutines_core(Continuation<?> frame) {
        updateState(frame, DebugCoroutineInfoImplKt.SUSPENDED);
    }

    private final void updateState(Continuation<?> frame, String state) {
        if (isInstalled$kotlinx_coroutines_debug()) {
            if (ignoreCoroutinesWithEmptyContext && frame.getContext() == EmptyCoroutineContext.INSTANCE) {
                return;
            }
            if (Intrinsics.areEqual((Object) state, (Object) DebugCoroutineInfoImplKt.RUNNING)) {
                CoroutineStackFrame stackFrame = frame instanceof CoroutineStackFrame ? (CoroutineStackFrame) frame : null;
                if (stackFrame != null) {
                    updateRunningState(stackFrame, state);
                    return;
                }
                return;
            }
            CoroutineOwner owner = owner(frame);
            if (owner != null) {
                updateState(owner, frame, state);
            }
        }
    }

    private final void updateRunningState(CoroutineStackFrame frame, String state) {
        boolean shouldBeMatchedWithProbeSuspended;
        DebugCoroutineInfoImpl info;
        DebugCoroutineInfoImpl debugCoroutineInfoImpl;
        if (isInstalled$kotlinx_coroutines_debug()) {
            DebugCoroutineInfoImpl cached = callerInfoCache.remove(frame);
            if (cached != null) {
                info = cached;
                shouldBeMatchedWithProbeSuspended = false;
            } else {
                CoroutineOwner<?> owner = owner(frame);
                if (owner != null && (debugCoroutineInfoImpl = owner.info) != null) {
                    info = debugCoroutineInfoImpl;
                    shouldBeMatchedWithProbeSuspended = true;
                    CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = info.getLastObservedFrame$kotlinx_coroutines_core();
                    CoroutineStackFrame realCaller = lastObservedFrame$kotlinx_coroutines_core != null ? realCaller(lastObservedFrame$kotlinx_coroutines_core) : null;
                    if (realCaller != null) {
                        callerInfoCache.remove(realCaller);
                    }
                } else {
                    return;
                }
            }
            Intrinsics.checkNotNull(frame, "null cannot be cast to non-null type kotlin.coroutines.Continuation<*>");
            info.updateState$kotlinx_coroutines_core(state, (Continuation) frame, shouldBeMatchedWithProbeSuspended);
            CoroutineStackFrame caller = realCaller(frame);
            if (caller != null) {
                callerInfoCache.put(caller, info);
            }
        }
    }

    private final CoroutineStackFrame realCaller(CoroutineStackFrame $this$realCaller) {
        while (true) {
            CoroutineStackFrame caller = $this$realCaller.getCallerFrame();
            if (caller == null) {
                return null;
            }
            if (caller.getStackTraceElement() != null) {
                return caller;
            }
            $this$realCaller = caller;
        }
    }

    private final void updateState(CoroutineOwner<?> owner, Continuation<?> frame, String state) {
        if (isInstalled$kotlinx_coroutines_debug()) {
            owner.info.updateState$kotlinx_coroutines_core(state, frame, true);
        }
    }

    private final CoroutineOwner<?> owner(Continuation<?> $this$owner) {
        CoroutineStackFrame coroutineStackFrame = $this$owner instanceof CoroutineStackFrame ? (CoroutineStackFrame) $this$owner : null;
        if (coroutineStackFrame != null) {
            return owner(coroutineStackFrame);
        }
        return null;
    }

    private final CoroutineOwner<?> owner(CoroutineStackFrame $this$owner) {
        while (!($this$owner instanceof CoroutineOwner)) {
            CoroutineStackFrame callerFrame = $this$owner.getCallerFrame();
            if (callerFrame == null) {
                return null;
            }
            $this$owner = callerFrame;
        }
        return (CoroutineOwner) $this$owner;
    }

    public final <T> Continuation<T> probeCoroutineCreated$kotlinx_coroutines_core(Continuation<? super T> completion) {
        StackTraceFrame frame;
        if (!isInstalled$kotlinx_coroutines_debug()) {
            return completion;
        }
        if ((ignoreCoroutinesWithEmptyContext && completion.getContext() == EmptyCoroutineContext.INSTANCE) || owner((Continuation<?>) completion) != null) {
            return completion;
        }
        if (enableCreationStackTraces) {
            frame = toStackTraceFrame(sanitizeStackTrace(new Exception()));
        } else {
            frame = null;
        }
        return createOwner(completion, frame);
    }

    private final StackTraceFrame toStackTraceFrame(List<StackTraceElement> $this$toStackTraceFrame) {
        List $this$foldRight$iv = $this$toStackTraceFrame;
        Object accumulator$iv = null;
        if (!$this$foldRight$iv.isEmpty()) {
            ListIterator iterator$iv = $this$foldRight$iv.listIterator($this$foldRight$iv.size());
            while (iterator$iv.hasPrevious()) {
                accumulator$iv = new StackTraceFrame((CoroutineStackFrame) accumulator$iv, iterator$iv.previous());
            }
        }
        return new StackTraceFrame((CoroutineStackFrame) accumulator$iv, ARTIFICIAL_FRAME);
    }

    private final <T> Continuation<T> createOwner(Continuation<? super T> completion, StackTraceFrame frame) {
        if (!isInstalled$kotlinx_coroutines_debug()) {
            return completion;
        }
        CoroutineOwner owner = new CoroutineOwner(completion, new DebugCoroutineInfoImpl(completion.getContext(), frame, DebugProbesImpl$DebugProbesImpl$VolatileWrapper$atomicfu$private.sequenceNumber$volatile$FU.incrementAndGet(debugProbesImpl$VolatileWrapper$atomicfu$private)));
        capturedCoroutinesMap.put(owner, true);
        if (!isInstalled$kotlinx_coroutines_debug()) {
            capturedCoroutinesMap.clear();
        }
        return owner;
    }

    /* access modifiers changed from: private */
    public final void probeCoroutineCompleted(CoroutineOwner<?> owner) {
        CoroutineStackFrame caller;
        capturedCoroutinesMap.remove(owner);
        CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = owner.info.getLastObservedFrame$kotlinx_coroutines_core();
        if (lastObservedFrame$kotlinx_coroutines_core != null && (caller = realCaller(lastObservedFrame$kotlinx_coroutines_core)) != null) {
            callerInfoCache.remove(caller);
        }
    }

    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B\u001f\b\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001b\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0016¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0000X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\t\u0010\u0019\u001a\u00020\u001aX\u0005¨\u0006\u001b"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "T", "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "delegate", "info", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "<init>", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;)V", "frame", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "getFrame", "()Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "toString", "", "context", "Lkotlin/coroutines/CoroutineContext;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: DebugProbesImpl.kt */
    public static final class CoroutineOwner<T> implements Continuation<T>, CoroutineStackFrame {
        public final Continuation<T> delegate;
        public final DebugCoroutineInfoImpl info;

        public CoroutineContext getContext() {
            return this.delegate.getContext();
        }

        public CoroutineOwner(Continuation<? super T> delegate2, DebugCoroutineInfoImpl info2) {
            this.delegate = delegate2;
            this.info = info2;
        }

        private final StackTraceFrame getFrame() {
            return this.info.getCreationStackBottom$kotlinx_coroutines_core();
        }

        public CoroutineStackFrame getCallerFrame() {
            StackTraceFrame frame = getFrame();
            if (frame != null) {
                return frame.getCallerFrame();
            }
            return null;
        }

        public StackTraceElement getStackTraceElement() {
            StackTraceFrame frame = getFrame();
            if (frame != null) {
                return frame.getStackTraceElement();
            }
            return null;
        }

        public void resumeWith(Object result) {
            DebugProbesImpl.INSTANCE.probeCoroutineCompleted(this);
            this.delegate.resumeWith(result);
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    private final <T extends Throwable> List<StackTraceElement> sanitizeStackTrace(T throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        StackTraceElement[] stackTraceElementArr = stackTrace;
        int i = -1;
        int length = stackTraceElementArr.length - 1;
        if (length >= 0) {
            while (true) {
                int index$iv = length;
                length--;
                if (!Intrinsics.areEqual((Object) stackTraceElementArr[index$iv].getClassName(), (Object) "kotlin.coroutines.jvm.internal.DebugProbesKt")) {
                    if (length < 0) {
                        break;
                    }
                } else {
                    i = index$iv;
                    break;
                }
            }
        }
        int traceStart = i + 1;
        if (!sanitizeStackTraces) {
            int i2 = size - traceStart;
            ArrayList arrayList = new ArrayList(i2);
            for (int it = 0; it < i2; it++) {
                arrayList.add(stackTrace[it + traceStart]);
            }
            return arrayList;
        }
        ArrayList result = new ArrayList((size - traceStart) + 1);
        int i3 = traceStart;
        while (i3 < size) {
            if (isInternalMethod(stackTrace[i3])) {
                result.add(stackTrace[i3]);
                int j = i3 + 1;
                while (j < size && isInternalMethod(stackTrace[j])) {
                    j++;
                }
                int k = j - 1;
                while (k > i3 && stackTrace[k].getFileName() == null) {
                    k--;
                }
                if (k > i3 && k < j - 1) {
                    result.add(stackTrace[k]);
                }
                result.add(stackTrace[j - 1]);
                i3 = j;
            } else {
                result.add(stackTrace[i3]);
                i3++;
            }
        }
        return result;
    }

    private final boolean isInternalMethod(StackTraceElement $this$isInternalMethod) {
        return StringsKt.startsWith$default($this$isInternalMethod.getClassName(), "kotlinx.coroutines", false, 2, (Object) null);
    }
}
