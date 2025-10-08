package kotlinx.coroutines.internal;

import _COROUTINE.ArtificialStackFrames;
import _COROUTINE.CoroutineDebuggingKt;
import java.util.ArrayDeque;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.DebugKt;

@Metadata(d1 = {"\u0000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\u001a\u001f\u0010\t\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\nH\u0000¢\u0006\u0002\u0010\r\u001a\u001b\u0010\u000e\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b*\u0002H\nH\u0002¢\u0006\u0002\u0010\r\u001a,\u0010\t\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\n2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\b¢\u0006\u0002\u0010\u0011\u001a+\u0010\u0012\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\n2\n\u0010\u000f\u001a\u00060\u0014j\u0002`\u0013H\u0002¢\u0006\u0002\u0010\u0015\u001a9\u0010\u0016\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\u0017\u001a\u0002H\n2\u0006\u0010\u0018\u001a\u0002H\n2\u0010\u0010\u0019\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u001b0\u001aH\u0002¢\u0006\u0002\u0010\u001c\u001a1\u0010\u001d\u001a\u0018\u0012\u0004\u0012\u0002H\n\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0004j\u0002`\u001b0\u001f0\u001e\"\b\b\u0000\u0010\n*\u00020\u000b*\u0002H\nH\u0002¢\u0006\u0002\u0010 \u001a1\u0010!\u001a\u00020\"2\u0010\u0010#\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u001b0\u001f2\u0010\u0010\u0018\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u001b0\u001aH\u0002¢\u0006\u0002\u0010$\u001a\u0016\u0010%\u001a\u00020&2\u0006\u0010\f\u001a\u00020\u000bHH¢\u0006\u0002\u0010'\u001a \u0010(\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\nH\b¢\u0006\u0002\u0010\r\u001a\u001f\u0010)\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\nH\u0001¢\u0006\u0002\u0010\r\u001a#\u0010*\u001a\f\u0012\b\u0012\u00060\u0004j\u0002`\u001b0\u001a2\n\u0010\u000f\u001a\u00060\u0014j\u0002`\u0013H\u0002¢\u0006\u0002\u0010+\u001a\u0015\u0010,\u001a\u00020-*\u00060\u0004j\u0002`\u001bH\u0000¢\u0006\u0002\u0010.\u001a#\u0010/\u001a\u000200*\f\u0012\b\u0012\u00060\u0004j\u0002`\u001b0\u001f2\u0006\u00101\u001a\u00020\u0001H\u0002¢\u0006\u0002\u00102\u001a!\u00103\u001a\u00020-*\u00060\u0004j\u0002`\u001b2\n\u00104\u001a\u00060\u0004j\u0002`\u001bH\u0002¢\u0006\u0002\u00105\u001a\u0014\u00108\u001a\u00020\"*\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000bH\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00010\u0001X\u0004¢\u0006\u0004\n\u0002\u0010\u0007\"\u0018\u0010\b\u001a\n \u0006*\u0004\u0018\u00010\u00010\u0001X\u0004¢\u0006\u0004\n\u0002\u0010\u0007*\f\b\u0000\u00106\"\u00020\u00142\u00020\u0014*\f\b\u0000\u00107\"\u00020\u00042\u00020\u0004¨\u00069"}, d2 = {"baseContinuationImplClass", "", "stackTraceRecoveryClass", "ARTIFICIAL_FRAME", "Ljava/lang/StackTraceElement;", "baseContinuationImplClassName", "kotlin.jvm.PlatformType", "Ljava/lang/String;", "stackTraceRecoveryClassName", "recoverStackTrace", "E", "", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "sanitizeStackTrace", "continuation", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;", "recoverFromStackFrame", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;", "createFinalException", "cause", "result", "resultStackTrace", "Ljava/util/ArrayDeque;", "Lkotlinx/coroutines/internal/StackTraceElement;", "(Ljava/lang/Throwable;Ljava/lang/Throwable;Ljava/util/ArrayDeque;)Ljava/lang/Throwable;", "causeAndStacktrace", "Lkotlin/Pair;", "", "(Ljava/lang/Throwable;)Lkotlin/Pair;", "mergeRecoveredTraces", "", "recoveredStacktrace", "([Ljava/lang/StackTraceElement;Ljava/util/ArrayDeque;)V", "recoverAndThrow", "", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unwrap", "unwrapImpl", "createStackTrace", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/util/ArrayDeque;", "isArtificial", "", "(Ljava/lang/StackTraceElement;)Z", "firstFrameIndex", "", "methodName", "([Ljava/lang/StackTraceElement;Ljava/lang/String;)I", "elementWiseEquals", "e", "(Ljava/lang/StackTraceElement;Ljava/lang/StackTraceElement;)Z", "CoroutineStackFrame", "StackTraceElement", "initCause", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: StackTraceRecovery.kt */
public final class StackTraceRecoveryKt {
    private static final StackTraceElement ARTIFICIAL_FRAME = new ArtificialStackFrames().coroutineBoundary();
    private static final String baseContinuationImplClass = "kotlin.coroutines.jvm.internal.BaseContinuationImpl";
    private static final String baseContinuationImplClassName;
    private static final String stackTraceRecoveryClass = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
    private static final String stackTraceRecoveryClassName;

    public static /* synthetic */ void CoroutineStackFrame$annotations() {
    }

    public static /* synthetic */ void StackTraceElement$annotations() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v15, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.String r0 = "kotlinx.coroutines.internal.StackTraceRecoveryKt"
            java.lang.String r1 = "kotlin.coroutines.jvm.internal.BaseContinuationImpl"
            _COROUTINE.ArtificialStackFrames r2 = new _COROUTINE.ArtificialStackFrames
            r2.<init>()
            java.lang.StackTraceElement r2 = r2.coroutineBoundary()
            ARTIFICIAL_FRAME = r2
            kotlin.Result$Companion r2 = kotlin.Result.Companion     // Catch:{ all -> 0x0020 }
            r2 = 0
            java.lang.Class r3 = java.lang.Class.forName(r1)     // Catch:{ all -> 0x0020 }
            java.lang.String r3 = r3.getCanonicalName()     // Catch:{ all -> 0x0020 }
            java.lang.Object r2 = kotlin.Result.m6constructorimpl(r3)     // Catch:{ all -> 0x0020 }
            goto L_0x002b
        L_0x0020:
            r2 = move-exception
            kotlin.Result$Companion r3 = kotlin.Result.Companion
            java.lang.Object r2 = kotlin.ResultKt.createFailure(r2)
            java.lang.Object r2 = kotlin.Result.m6constructorimpl(r2)
        L_0x002b:
            java.lang.Throwable r3 = kotlin.Result.m9exceptionOrNullimpl(r2)
            if (r3 != 0) goto L_0x0033
            r1 = r2
            goto L_0x0035
        L_0x0033:
            r2 = 0
        L_0x0035:
            java.lang.String r1 = (java.lang.String) r1
            baseContinuationImplClassName = r1
            kotlin.Result$Companion r1 = kotlin.Result.Companion     // Catch:{ all -> 0x004a }
            r1 = 0
            java.lang.Class r2 = java.lang.Class.forName(r0)     // Catch:{ all -> 0x004a }
            java.lang.String r2 = r2.getCanonicalName()     // Catch:{ all -> 0x004a }
            java.lang.Object r1 = kotlin.Result.m6constructorimpl(r2)     // Catch:{ all -> 0x004a }
            goto L_0x0055
        L_0x004a:
            r1 = move-exception
            kotlin.Result$Companion r2 = kotlin.Result.Companion
            java.lang.Object r1 = kotlin.ResultKt.createFailure(r1)
            java.lang.Object r1 = kotlin.Result.m6constructorimpl(r1)
        L_0x0055:
            java.lang.Throwable r2 = kotlin.Result.m9exceptionOrNullimpl(r1)
            if (r2 != 0) goto L_0x005d
            r0 = r1
            goto L_0x005f
        L_0x005d:
            r1 = 0
        L_0x005f:
            java.lang.String r0 = (java.lang.String) r0
            stackTraceRecoveryClassName = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.StackTraceRecoveryKt.<clinit>():void");
    }

    public static final <E extends Throwable> E recoverStackTrace(E exception) {
        Throwable copy;
        if (DebugKt.getRECOVER_STACK_TRACES() && (copy = ExceptionsConstructorKt.tryCopyException(exception)) != null) {
            return sanitizeStackTrace(copy);
        }
        return exception;
    }

    private static final <E extends Throwable> E sanitizeStackTrace(E $this$sanitizeStackTrace) {
        int index$iv;
        StackTraceElement stackTraceElement;
        StackTraceElement[] stackTrace = $this$sanitizeStackTrace.getStackTrace();
        int size = stackTrace.length;
        StackTraceElement[] stackTraceElementArr = stackTrace;
        int length = stackTraceElementArr.length - 1;
        if (length >= 0) {
            while (true) {
                index$iv = length;
                length--;
                if (!Intrinsics.areEqual((Object) stackTraceRecoveryClassName, (Object) stackTraceElementArr[index$iv].getClassName())) {
                    if (length < 0) {
                        break;
                    }
                } else {
                    break;
                }
            }
            index$iv = -1;
        } else {
            index$iv = -1;
        }
        int startIndex = index$iv + 1;
        int endIndex = firstFrameIndex(stackTrace, baseContinuationImplClassName);
        int i = (size - index$iv) - (endIndex == -1 ? 0 : size - endIndex);
        StackTraceElement[] trace = new StackTraceElement[i];
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == 0) {
                stackTraceElement = ARTIFICIAL_FRAME;
            } else {
                stackTraceElement = stackTrace[(startIndex + i2) - 1];
            }
            trace[i2] = stackTraceElement;
        }
        $this$sanitizeStackTrace.setStackTrace(trace);
        return $this$sanitizeStackTrace;
    }

    public static final <E extends Throwable> E recoverStackTrace(E exception, Continuation<?> continuation) {
        if (!DebugKt.getRECOVER_STACK_TRACES() || !(continuation instanceof CoroutineStackFrame)) {
            return exception;
        }
        return recoverFromStackFrame(exception, (CoroutineStackFrame) continuation);
    }

    /* access modifiers changed from: private */
    public static final <E extends Throwable> E recoverFromStackFrame(E exception, CoroutineStackFrame continuation) {
        Pair causeAndStacktrace = causeAndStacktrace(exception);
        Throwable cause = (Throwable) causeAndStacktrace.component1();
        StackTraceElement[] recoveredStacktrace = (StackTraceElement[]) causeAndStacktrace.component2();
        Throwable newException = ExceptionsConstructorKt.tryCopyException(cause);
        if (newException == null) {
            return exception;
        }
        ArrayDeque stacktrace = createStackTrace(continuation);
        if (stacktrace.isEmpty()) {
            return exception;
        }
        if (cause != exception) {
            mergeRecoveredTraces(recoveredStacktrace, stacktrace);
        }
        return createFinalException(cause, newException, stacktrace);
    }

    private static final <E extends Throwable> E createFinalException(E cause, E result, ArrayDeque<StackTraceElement> resultStackTrace) {
        resultStackTrace.addFirst(ARTIFICIAL_FRAME);
        StackTraceElement[] causeTrace = cause.getStackTrace();
        int size = firstFrameIndex(causeTrace, baseContinuationImplClassName);
        int i = 0;
        if (size == -1) {
            result.setStackTrace((StackTraceElement[]) resultStackTrace.toArray(new StackTraceElement[0]));
            return result;
        }
        StackTraceElement[] mergedStackTrace = new StackTraceElement[(resultStackTrace.size() + size)];
        for (int i2 = 0; i2 < size; i2++) {
            mergedStackTrace[i2] = causeTrace[i2];
        }
        for (StackTraceElement element : resultStackTrace) {
            int index = i;
            i++;
            mergedStackTrace[size + index] = element;
        }
        result.setStackTrace(mergedStackTrace);
        return result;
    }

    private static final <E extends Throwable> Pair<E, StackTraceElement[]> causeAndStacktrace(E $this$causeAndStacktrace) {
        boolean z;
        Throwable cause = $this$causeAndStacktrace.getCause();
        if (cause == null || !Intrinsics.areEqual((Object) cause.getClass(), (Object) $this$causeAndStacktrace.getClass())) {
            return TuplesKt.to($this$causeAndStacktrace, new StackTraceElement[0]);
        }
        StackTraceElement[] currentTrace = $this$causeAndStacktrace.getStackTrace();
        StackTraceElement[] stackTraceElementArr = currentTrace;
        int length = stackTraceElementArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (isArtificial(stackTraceElementArr[i])) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            return TuplesKt.to(cause, currentTrace);
        }
        return TuplesKt.to($this$causeAndStacktrace, new StackTraceElement[0]);
    }

    private static final void mergeRecoveredTraces(StackTraceElement[] recoveredStacktrace, ArrayDeque<StackTraceElement> result) {
        StackTraceElement[] stackTraceElementArr = recoveredStacktrace;
        int index$iv = 0;
        int length = stackTraceElementArr.length;
        while (true) {
            if (index$iv >= length) {
                index$iv = -1;
                break;
            } else if (isArtificial(stackTraceElementArr[index$iv])) {
                break;
            } else {
                index$iv++;
            }
        }
        int startIndex = index$iv + 1;
        int i = recoveredStacktrace.length - 1;
        if (startIndex <= i) {
            while (true) {
                if (elementWiseEquals(recoveredStacktrace[i], result.getLast())) {
                    result.removeLast();
                }
                result.addFirst(recoveredStacktrace[i]);
                if (i != startIndex) {
                    i--;
                } else {
                    return;
                }
            }
        }
    }

    public static final Object recoverAndThrow(Throwable exception, Continuation<?> $completion) {
        if (DebugKt.getRECOVER_STACK_TRACES()) {
            Continuation<?> continuation = $completion;
            if (!(continuation instanceof CoroutineStackFrame)) {
                throw exception;
            }
            throw recoverFromStackFrame(exception, (CoroutineStackFrame) continuation);
        }
        throw exception;
    }

    private static final Object recoverAndThrow$$forInline(Throwable exception, Continuation<?> $completion) {
        if (DebugKt.getRECOVER_STACK_TRACES()) {
            Continuation it = $completion;
            if (!(it instanceof CoroutineStackFrame)) {
                throw exception;
            }
            throw recoverFromStackFrame(exception, (CoroutineStackFrame) it);
        }
        throw exception;
    }

    public static final <E extends Throwable> E unwrap(E exception) {
        return !DebugKt.getRECOVER_STACK_TRACES() ? exception : unwrapImpl(exception);
    }

    public static final <E extends Throwable> E unwrapImpl(E exception) {
        Throwable cause = exception.getCause();
        if (cause == null || !Intrinsics.areEqual((Object) cause.getClass(), (Object) exception.getClass())) {
            return exception;
        }
        StackTraceElement[] stackTrace = exception.getStackTrace();
        int length = stackTrace.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (isArtificial(stackTrace[i])) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            return cause;
        }
        return exception;
    }

    private static final ArrayDeque<StackTraceElement> createStackTrace(CoroutineStackFrame continuation) {
        CoroutineStackFrame callerFrame;
        ArrayDeque stack = new ArrayDeque();
        StackTraceElement it = continuation.getStackTraceElement();
        if (it != null) {
            stack.add(it);
        }
        CoroutineStackFrame last = continuation;
        while (true) {
            CoroutineStackFrame coroutineStackFrame = last instanceof CoroutineStackFrame ? last : null;
            if (coroutineStackFrame == null || (callerFrame = coroutineStackFrame.getCallerFrame()) == null) {
                return stack;
            }
            last = callerFrame;
            StackTraceElement it2 = last.getStackTraceElement();
            if (it2 != null) {
                stack.add(it2);
            }
        }
        return stack;
    }

    public static final boolean isArtificial(StackTraceElement $this$isArtificial) {
        return StringsKt.startsWith$default($this$isArtificial.getClassName(), CoroutineDebuggingKt.getARTIFICIAL_FRAME_PACKAGE_NAME(), false, 2, (Object) null);
    }

    private static final int firstFrameIndex(StackTraceElement[] $this$firstFrameIndex, String methodName) {
        StackTraceElement[] stackTraceElementArr = $this$firstFrameIndex;
        int length = stackTraceElementArr.length;
        for (int index$iv = 0; index$iv < length; index$iv++) {
            if (Intrinsics.areEqual((Object) methodName, (Object) stackTraceElementArr[index$iv].getClassName())) {
                return index$iv;
            }
        }
        return -1;
    }

    private static final boolean elementWiseEquals(StackTraceElement $this$elementWiseEquals, StackTraceElement e) {
        return $this$elementWiseEquals.getLineNumber() == e.getLineNumber() && Intrinsics.areEqual((Object) $this$elementWiseEquals.getMethodName(), (Object) e.getMethodName()) && Intrinsics.areEqual((Object) $this$elementWiseEquals.getFileName(), (Object) e.getFileName()) && Intrinsics.areEqual((Object) $this$elementWiseEquals.getClassName(), (Object) e.getClassName());
    }

    public static final void initCause(Throwable $this$initCause, Throwable cause) {
        $this$initCause.initCause(cause);
    }
}
