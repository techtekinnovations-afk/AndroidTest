package kotlinx.coroutines.channels;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.google.common.primitives.Longs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt;
import kotlin.time.DurationKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause1Impl;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectClause2Impl;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.TrySelectDetailedResult;

@Metadata(d1 = {"\u0000Ö\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b1\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0004ï\u0001ð\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\"\b\u0002\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0004\b\t\u0010\nJ\u0016\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u0000H@¢\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u0000H@¢\u0006\u0002\u0010#J4\u0010%\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u0011H@¢\u0006\u0002\u0010)J\"\u0010*\u001a\u00020\b*\u00020+2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u0004H\u0002J#\u0010,\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\b0.H\u0002¢\u0006\u0002\u0010/J\u001d\u00100\u001a\b\u0012\u0004\u0012\u00020\b012\u0006\u0010\"\u001a\u00028\u0000H\u0016¢\u0006\u0004\b2\u00103J\u0018\u00104\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00028\u0000H@¢\u0006\u0004\b5\u0010#Jê\u0001\u00106\u001a\u0002H7\"\u0004\b\u0001\u001072\u0006\u0010\"\u001a\u00028\u00002\b\u00108\u001a\u0004\u0018\u0001092\f\u0010:\u001a\b\u0012\u0004\u0012\u0002H70;2<\u0010<\u001a8\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0004\u0012\u0002H70=2\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70;2h\b\u0002\u0010C\u001ab\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b((\u0012\u0004\u0012\u0002H70DH\b¢\u0006\u0002\u0010EJ\u001d\u0010F\u001a\b\u0012\u0004\u0012\u00020\b012\u0006\u0010\"\u001a\u00028\u0000H\u0004¢\u0006\u0004\bG\u00103JX\u0010H\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u00112\u0006\u00108\u001a\u00020+2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\b0;2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\b0;H\b¢\u0006\u0002\u0010IJE\u0010J\u001a\u00020\u00042\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u0001092\u0006\u0010K\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010LJE\u0010M\u001a\u00020\u00042\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010(\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u0001092\u0006\u0010K\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010LJ\u0010\u0010N\u001a\u00020\u001a2\u0006\u0010O\u001a\u00020\u0011H\u0003J\u0010\u0010P\u001a\u00020\u001a2\u0006\u0010Q\u001a\u00020\u0011H\u0002J\r\u0010N\u001a\u00020\u001aH\u0010¢\u0006\u0002\bRJ\u0019\u0010S\u001a\u00020\u001a*\u0002092\u0006\u0010\"\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010TJ\b\u0010U\u001a\u00020\bH\u0014J\b\u0010V\u001a\u00020\bH\u0014J\u000e\u0010W\u001a\u00028\u0000H@¢\u0006\u0002\u0010XJ;\u0010Y\u001a\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u0011H@¢\u0006\u0002\u0010[J\"\u0010\\\u001a\u00020\b*\u00020+2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u0004H\u0002J\u0016\u0010]\u001a\u00020\b2\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.H\u0002J\u0016\u0010^\u001a\b\u0012\u0004\u0012\u00028\u000001H@¢\u0006\u0004\b_\u0010XJ4\u0010`\u001a\b\u0012\u0004\u0012\u00028\u0000012\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u0011H@¢\u0006\u0004\ba\u0010[J\u001c\u0010b\u001a\u00020\b2\u0012\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000010.H\u0002J\u0015\u0010c\u001a\b\u0012\u0004\u0012\u00028\u000001H\u0016¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\b2\u0006\u0010g\u001a\u00020\u0011H\u0004J÷\u0001\u0010h\u001a\u0002H7\"\u0004\b\u0001\u001072\b\u00108\u001a\u0004\u0018\u0001092!\u0010i\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H70\u00072Q\u0010<\u001aM\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(Z\u0012\u0004\u0012\u0002H70j2\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70;2S\b\u0002\u0010C\u001aM\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u001e¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(@\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(A\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(Z\u0012\u0004\u0012\u0002H70jH\b¢\u0006\u0002\u0010kJ`\u0010l\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u00112\u0006\u00108\u001a\u00020+2!\u0010i\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b>\u0012\b\b?\u0012\u0004\b\b(\"\u0012\u0004\u0012\u00020\b0\u00072\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\b0;H\bJ2\u0010m\u001a\u0004\u0018\u0001092\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u000109H\u0002J2\u0010n\u001a\u0004\u0018\u0001092\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010Z\u001a\u00020\u00112\b\u00108\u001a\u0004\u0018\u000109H\u0002J\"\u0010o\u001a\u00020\u001a*\u0002092\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u0004H\u0002J\b\u0010p\u001a\u00020\bH\u0002J&\u0010q\u001a\u00020\u001a2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010r\u001a\u00020\u0011H\u0002J&\u0010s\u001a\u00020\u001a2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010r\u001a\u00020\u0011H\u0002J\u0012\u0010t\u001a\u00020\b2\b\b\u0002\u0010u\u001a\u00020\u0011H\u0002J\u0015\u0010v\u001a\u00020\b2\u0006\u0010w\u001a\u00020\u0011H\u0000¢\u0006\u0002\bxJ \u0010\u001a\u00020\b2\f\u0010\u0001\u001a\u0007\u0012\u0002\b\u00030\u00012\b\u0010\"\u001a\u0004\u0018\u000109H\u0014J%\u0010\u0001\u001a\u00020\b2\u0006\u0010\"\u001a\u00028\u00002\f\u0010\u0001\u001a\u0007\u0012\u0002\b\u00030\u0001H\u0002¢\u0006\u0003\u0010\u0001J!\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u000109H\u0002J\"\u0010\u0001\u001a\u00020\b2\f\u0010\u0001\u001a\u0007\u0012\u0002\b\u00030\u00012\t\u0010\u0001\u001a\u0004\u0018\u000109H\u0002J\u0017\u0010\u0001\u001a\u00020\b2\f\u0010\u0001\u001a\u0007\u0012\u0002\b\u00030\u0001H\u0002J!\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u000109H\u0002J!\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u000109H\u0002J!\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u0001092\t\u0010\u0001\u001a\u0004\u0018\u000109H\u0002J\u0011\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000 \u0001H\u0002J\t\u0010ª\u0001\u001a\u00020\bH\u0014J\u0015\u0010«\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u0001H\u0016J\u0013\u0010­\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u0001J\u0007\u0010­\u0001\u001a\u00020\bJ \u0010­\u0001\u001a\u00020\b2\u0011\u0010¬\u0001\u001a\f\u0018\u00010¯\u0001j\u0005\u0018\u0001`®\u0001¢\u0006\u0003\u0010°\u0001J\u001b\u0010±\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u0001H\u0010¢\u0006\u0003\b²\u0001J\u001e\u0010³\u0001\u001a\u00020\u001a2\n\u0010¬\u0001\u001a\u0005\u0018\u00010\u00012\u0007\u0010­\u0001\u001a\u00020\u001aH\u0014J\t\u0010´\u0001\u001a\u00020\bH\u0002J1\u0010µ\u0001\u001a\u00020\b2&\u0010¶\u0001\u001a!\u0012\u0017\u0012\u0015\u0018\u00010\u0001¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(¬\u0001\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\t\u0010·\u0001\u001a\u00020\bH\u0002J\t\u0010¸\u0001\u001a\u00020\bH\u0002J\t\u0010¹\u0001\u001a\u00020\bH\u0002J\t\u0010º\u0001\u001a\u00020\bH\u0002J\u0018\u0010¼\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0007\u0010½\u0001\u001a\u00020\u0011H\u0002J\u0012\u0010¾\u0001\u001a\u00020\b2\u0007\u0010½\u0001\u001a\u00020\u0011H\u0002J\u000f\u0010¿\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J\u0018\u0010À\u0001\u001a\u00020\u00112\r\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J\u0018\u0010Â\u0001\u001a\u00020\b2\r\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J \u0010Ã\u0001\u001a\u00020\b2\r\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\r\u0010Ä\u0001\u001a\u00020\b*\u00020+H\u0002J\r\u0010Å\u0001\u001a\u00020\b*\u00020+H\u0002J\u0016\u0010Æ\u0001\u001a\u00020\b*\u00020+2\u0007\u0010Ç\u0001\u001a\u00020\u001aH\u0002J\u001b\u0010Ï\u0001\u001a\u00020\u001a2\u0007\u0010Ð\u0001\u001a\u00020\u00112\u0007\u0010Ì\u0001\u001a\u00020\u001aH\u0002J\u000f\u0010Ó\u0001\u001a\u00020\u001aH\u0000¢\u0006\u0003\bÔ\u0001J'\u0010Õ\u0001\u001a\u00020\u001a2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010w\u001a\u00020\u0011H\u0002J)\u0010Ö\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001e2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J)\u0010Ù\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001e2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J2\u0010Ú\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001e2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001e2\u0007\u0010Û\u0001\u001a\u00020\u0011H\u0002J!\u0010Ü\u0001\u001a\u00020\b2\u0007\u0010×\u0001\u001a\u00020\u00112\r\u0010Ø\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0002J\u0012\u0010Ý\u0001\u001a\u00020\b2\u0007\u0010Þ\u0001\u001a\u00020\u0011H\u0002J\u0012\u0010ß\u0001\u001a\u00020\b2\u0007\u0010Þ\u0001\u001a\u00020\u0011H\u0002J\n\u0010à\u0001\u001a\u00030á\u0001H\u0016J\u0010\u0010â\u0001\u001a\u00030á\u0001H\u0000¢\u0006\u0003\bã\u0001J\u0007\u0010ä\u0001\u001a\u00020\bJJ\u0010å\u0001\u001a#\u0012\u0005\u0012\u00030\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000001\u0012\u0005\u0012\u00030\u0001\u0012\u0004\u0012\u00020\b0æ\u0001*\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00028\u0000`\u0006H\u0002¢\u0006\u0003\u0010ç\u0001J4\u0010è\u0001\u001a\u00020\b2\b\u0010¬\u0001\u001a\u00030\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u0000012\b\u0010é\u0001\u001a\u00030\u0001H\u0002¢\u0006\u0006\bê\u0001\u0010ë\u0001JM\u0010ì\u0001\u001a\u001e\u0012\u0005\u0012\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u000109\u0012\u0005\u0012\u00030\u0001\u0012\u0004\u0012\u00020\b0j*\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00028\u0000`\u00062\u0006\u0010\"\u001a\u00028\u0000H\u0002¢\u0006\u0003\u0010í\u0001JD\u0010ì\u0001\u001a\u001d\u0012\u0005\u0012\u00030\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u0005\u0012\u00030\u0001\u0012\u0004\u0012\u00020\b0æ\u0001*\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00028\u0000`\u0006H\u0002¢\u0006\u0003\u0010ç\u0001J+\u0010î\u0001\u001a\u00020\b2\b\u0010¬\u0001\u001a\u00030\u00012\u0006\u0010\"\u001a\u00028\u00002\b\u0010é\u0001\u001a\u00030\u0001H\u0002¢\u0006\u0003\u0010ë\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R,\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u00068\u0000X\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\t\u0010\f\u001a\u00020\rX\u0004R\t\u0010\u000e\u001a\u00020\rX\u0004R\t\u0010\u000f\u001a\u00020\rX\u0004R\u0014\u0010\u0010\u001a\u00020\u00118@X\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00118@X\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u00118BX\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013R\t\u0010\u0018\u001a\u00020\rX\u0004R\u0014\u0010\u0019\u001a\u00020\u001a8BX\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u0015\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e0\u001dX\u0004R\u0015\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e0\u001dX\u0004R\u0015\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e0\u001dX\u0004R,\u0010y\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00000z8VX\u0004¢\u0006\f\u0012\u0004\b{\u0010|\u001a\u0004\b}\u0010~R%\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u00018VX\u0004¢\u0006\u000f\u0012\u0005\b\u0001\u0010|\u001a\u0006\b\u0001\u0010\u0001R+\u0010\u0001\u001a\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000010\u00018VX\u0004¢\u0006\u000f\u0012\u0005\b\u0001\u0010|\u001a\u0006\b\u0001\u0010\u0001R'\u0010\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u00018VX\u0004¢\u0006\u000f\u0012\u0005\b\u0001\u0010|\u001a\u0006\b\u0001\u0010\u0001R\u0001\u0010\u0001\u001ax\u0012\u0019\u0012\u0017\u0012\u0002\b\u00030\u0001¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(\u0001\u0012\u0016\u0012\u0014\u0018\u000109¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(\u0001\u0012\u0016\u0012\u0014\u0018\u000109¢\u0006\r\b>\u0012\t\b?\u0012\u0005\b\b(\u0001\u0012 \u0012\u001e\u0012\u0005\u0012\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u000109\u0012\u0005\u0012\u00030\u0001\u0012\u0004\u0012\u00020\b0j\u0018\u00010jj\u0005\u0018\u0001`\u0001X\u0004¢\u0006\f\n\u0003\u0010\u0001\u0012\u0005\b\u0001\u0010|R\u0012\u0010¡\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001090\u001dX\u0004R\u001a\u0010¢\u0001\u001a\u0005\u0018\u00010\u00018DX\u0004¢\u0006\b\u001a\u0006\b£\u0001\u0010¤\u0001R\u0018\u0010¥\u0001\u001a\u00030\u00018DX\u0004¢\u0006\b\u001a\u0006\b¦\u0001\u0010¤\u0001R\u0018\u0010§\u0001\u001a\u00030\u00018BX\u0004¢\u0006\b\u001a\u0006\b¨\u0001\u0010¤\u0001R\u0012\u0010©\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001090\u001dX\u0004R\u0016\u0010»\u0001\u001a\u00020\u001a8TX\u0004¢\u0006\u0007\u001a\u0005\b»\u0001\u0010\u001bR\u001d\u0010È\u0001\u001a\u00020\u001a8VX\u0004¢\u0006\u000e\u0012\u0005\bÉ\u0001\u0010|\u001a\u0005\bÈ\u0001\u0010\u001bR\u001b\u0010Ê\u0001\u001a\u00020\u001a*\u00020\u00118BX\u0004¢\u0006\b\u001a\u0006\bÊ\u0001\u0010Ë\u0001R\u001d\u0010Ì\u0001\u001a\u00020\u001a8VX\u0004¢\u0006\u000e\u0012\u0005\bÍ\u0001\u0010|\u001a\u0005\bÌ\u0001\u0010\u001bR\u001b\u0010Î\u0001\u001a\u00020\u001a*\u00020\u00118BX\u0004¢\u0006\b\u001a\u0006\bÎ\u0001\u0010Ë\u0001R\u001d\u0010Ñ\u0001\u001a\u00020\u001a8VX\u0004¢\u0006\u000e\u0012\u0005\bÒ\u0001\u0010|\u001a\u0005\bÑ\u0001\u0010\u001b¨\u0006ñ\u0001"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel;", "E", "Lkotlinx/coroutines/channels/Channel;", "capacity", "", "onUndeliveredElement", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "Lkotlin/Function1;", "", "<init>", "(ILkotlin/jvm/functions/Function1;)V", "Lkotlin/jvm/functions/Function1;", "sendersAndCloseStatus", "Lkotlinx/atomicfu/AtomicLong;", "receivers", "bufferEnd", "sendersCounter", "", "getSendersCounter$kotlinx_coroutines_core", "()J", "receiversCounter", "getReceiversCounter$kotlinx_coroutines_core", "bufferEndCounter", "getBufferEndCounter", "completedExpandBuffersAndPauseFlag", "isRendezvousOrUnlimited", "", "()Z", "sendSegment", "Lkotlinx/atomicfu/AtomicRef;", "Lkotlinx/coroutines/channels/ChannelSegment;", "receiveSegment", "bufferEndSegment", "send", "element", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onClosedSend", "sendOnNoWaiterSuspend", "segment", "index", "s", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "prepareSenderForSuspension", "Lkotlinx/coroutines/Waiter;", "onClosedSendOnNoWaiterSuspend", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "trySend", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "sendBroadcast", "sendBroadcast$kotlinx_coroutines_core", "sendImpl", "R", "waiter", "", "onRendezvousOrBuffered", "Lkotlin/Function0;", "onSuspend", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "segm", "i", "onClosed", "onNoWaiterSuspend", "Lkotlin/Function4;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "trySendDropOldest", "trySendDropOldest-JP2dKIU", "sendImplOnNoWaiter", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlinx/coroutines/Waiter;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "updateCellSend", "closed", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLjava/lang/Object;Z)I", "updateCellSendSlow", "shouldSendSuspend", "curSendersAndCloseStatus", "bufferOrRendezvousSend", "curSenders", "shouldSendSuspend$kotlinx_coroutines_core", "tryResumeReceiver", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "onReceiveEnqueued", "onReceiveDequeued", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveOnNoWaiterSuspend", "r", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "prepareReceiverForSuspension", "onClosedReceiveOnNoWaiterSuspend", "receiveCatching", "receiveCatching-JP2dKIU", "receiveCatchingOnNoWaiterSuspend", "receiveCatchingOnNoWaiterSuspend-GKJJFZk", "onClosedReceiveCatchingOnNoWaiterSuspend", "tryReceive", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "dropFirstElementUntilTheSpecifiedCellIsInTheBuffer", "globalCellIndex", "receiveImpl", "onElementRetrieved", "Lkotlin/Function3;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "receiveImplOnNoWaiter", "updateCellReceive", "updateCellReceiveSlow", "tryResumeSender", "expandBuffer", "updateCellExpandBuffer", "b", "updateCellExpandBufferSlow", "incCompletedExpandBufferAttempts", "nAttempts", "waitExpandBufferCompletion", "globalIndex", "waitExpandBufferCompletion$kotlinx_coroutines_core", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend$annotations", "()V", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "registerSelectForSend", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "onClosedSelectOnSend", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)V", "processResultSelectSend", "ignoredParam", "selectResult", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive$annotations", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "getOnReceiveCatching$annotations", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull$annotations", "getOnReceiveOrNull", "registerSelectForReceive", "onClosedSelectOnReceive", "processResultSelectReceive", "processResultSelectReceiveOrNull", "processResultSelectReceiveCatching", "onUndeliveredElementReceiveCancellationConstructor", "Lkotlinx/coroutines/selects/OnCancellationConstructor;", "param", "internalResult", "", "Lkotlin/coroutines/CoroutineContext;", "getOnUndeliveredElementReceiveCancellationConstructor$annotations", "Lkotlin/jvm/functions/Function3;", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "_closeCause", "closeCause", "getCloseCause", "()Ljava/lang/Throwable;", "sendException", "getSendException", "receiveException", "getReceiveException", "closeHandler", "onClosedIdempotent", "close", "cause", "cancel", "Lkotlinx/coroutines/CancellationException;", "Ljava/util/concurrent/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelImpl", "cancelImpl$kotlinx_coroutines_core", "closeOrCancelImpl", "invokeCloseHandler", "invokeOnClose", "handler", "markClosed", "markCancelled", "markCancellationStarted", "completeCloseOrCancel", "isConflatedDropOldest", "completeClose", "sendersCur", "completeCancel", "closeLinkedList", "markAllEmptyCellsAsClosed", "lastSegment", "removeUnprocessedElements", "cancelSuspendedReceiveRequests", "resumeReceiverOnClosedChannel", "resumeSenderOnCancelledChannel", "resumeWaiterOnClosedChannel", "receiver", "isClosedForSend", "isClosedForSend$annotations", "isClosedForSend0", "(J)Z", "isClosedForReceive", "isClosedForReceive$annotations", "isClosedForReceive0", "isClosed", "sendersAndCloseStatusCur", "isEmpty", "isEmpty$annotations", "hasElements", "hasElements$kotlinx_coroutines_core", "isCellNonEmpty", "findSegmentSend", "id", "startFrom", "findSegmentReceive", "findSegmentBufferEnd", "currentBufferEndCounter", "moveSegmentBufferEndToSpecifiedOrLast", "updateSendersCounterIfLower", "value", "updateReceiversCounterIfLower", "toString", "", "toStringDebug", "toStringDebug$kotlinx_coroutines_core", "checkSegmentStructureInvariants", "bindCancellationFunResult", "Lkotlin/reflect/KFunction3;", "(Lkotlin/jvm/functions/Function1;)Lkotlin/reflect/KFunction;", "onCancellationChannelResultImplDoNotCall", "context", "onCancellationChannelResultImplDoNotCall-5_sEAP8", "(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", "bindCancellationFun", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;)Lkotlin/jvm/functions/Function3;", "onCancellationImplDoNotCall", "SendBroadcast", "BufferedChannelIterator", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: BufferedChannel.kt */
public class BufferedChannel<E> implements Channel<E> {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _closeCause$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater bufferEnd$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater bufferEndSegment$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater closeHandler$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater completedExpandBuffersAndPauseFlag$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater receiveSegment$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater receivers$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater sendSegment$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater sendersAndCloseStatus$volatile$FU;
    private volatile /* synthetic */ Object _closeCause$volatile;
    private volatile /* synthetic */ long bufferEnd$volatile;
    private volatile /* synthetic */ Object bufferEndSegment$volatile;
    private final int capacity;
    private volatile /* synthetic */ Object closeHandler$volatile;
    private volatile /* synthetic */ long completedExpandBuffersAndPauseFlag$volatile;
    public final Function1<E, Unit> onUndeliveredElement;
    private final Function3<SelectInstance<?>, Object, Object, Function3<Throwable, Object, CoroutineContext, Unit>> onUndeliveredElementReceiveCancellationConstructor;
    private volatile /* synthetic */ Object receiveSegment$volatile;
    private volatile /* synthetic */ long receivers$volatile;
    private volatile /* synthetic */ Object sendSegment$volatile;
    private volatile /* synthetic */ long sendersAndCloseStatus$volatile;

    static {
        Class<BufferedChannel> cls = BufferedChannel.class;
        sendersAndCloseStatus$volatile$FU = AtomicLongFieldUpdater.newUpdater(cls, "sendersAndCloseStatus$volatile");
        receivers$volatile$FU = AtomicLongFieldUpdater.newUpdater(cls, "receivers$volatile");
        bufferEnd$volatile$FU = AtomicLongFieldUpdater.newUpdater(cls, "bufferEnd$volatile");
        completedExpandBuffersAndPauseFlag$volatile$FU = AtomicLongFieldUpdater.newUpdater(cls, "completedExpandBuffersAndPauseFlag$volatile");
        sendSegment$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "sendSegment$volatile");
        receiveSegment$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "receiveSegment$volatile");
        bufferEndSegment$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "bufferEndSegment$volatile");
        _closeCause$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_closeCause$volatile");
        closeHandler$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "closeHandler$volatile");
    }

    private final /* synthetic */ Object getAndUpdate$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, ? extends Object> function1) {
        Object obj2;
        do {
            obj2 = atomicReferenceFieldUpdater.get(obj);
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceFieldUpdater, obj, obj2, function1.invoke(obj2)));
        return obj2;
    }

    private final /* synthetic */ long getBufferEnd$volatile() {
        return this.bufferEnd$volatile;
    }

    private final /* synthetic */ Object getBufferEndSegment$volatile() {
        return this.bufferEndSegment$volatile;
    }

    private final /* synthetic */ Object getCloseHandler$volatile() {
        return this.closeHandler$volatile;
    }

    private final /* synthetic */ long getCompletedExpandBuffersAndPauseFlag$volatile() {
        return this.completedExpandBuffersAndPauseFlag$volatile;
    }

    public static /* synthetic */ void getOnReceive$annotations() {
    }

    public static /* synthetic */ void getOnReceiveCatching$annotations() {
    }

    public static /* synthetic */ void getOnReceiveOrNull$annotations() {
    }

    public static /* synthetic */ void getOnSend$annotations() {
    }

    private static /* synthetic */ void getOnUndeliveredElementReceiveCancellationConstructor$annotations() {
    }

    private final /* synthetic */ Object getReceiveSegment$volatile() {
        return this.receiveSegment$volatile;
    }

    private final /* synthetic */ long getReceivers$volatile() {
        return this.receivers$volatile;
    }

    private final /* synthetic */ Object getSendSegment$volatile() {
        return this.sendSegment$volatile;
    }

    private final /* synthetic */ long getSendersAndCloseStatus$volatile() {
        return this.sendersAndCloseStatus$volatile;
    }

    private final /* synthetic */ Object get_closeCause$volatile() {
        return this._closeCause$volatile;
    }

    public static /* synthetic */ void isClosedForReceive$annotations() {
    }

    public static /* synthetic */ void isClosedForSend$annotations() {
    }

    public static /* synthetic */ void isEmpty$annotations() {
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Unit> function1) {
        while (true) {
            function1.invoke(Long.valueOf(atomicLongFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void setBufferEnd$volatile(long j) {
        this.bufferEnd$volatile = j;
    }

    private final /* synthetic */ void setBufferEndSegment$volatile(Object obj) {
        this.bufferEndSegment$volatile = obj;
    }

    private final /* synthetic */ void setCloseHandler$volatile(Object obj) {
        this.closeHandler$volatile = obj;
    }

    private final /* synthetic */ void setCompletedExpandBuffersAndPauseFlag$volatile(long j) {
        this.completedExpandBuffersAndPauseFlag$volatile = j;
    }

    private final /* synthetic */ void setReceiveSegment$volatile(Object obj) {
        this.receiveSegment$volatile = obj;
    }

    private final /* synthetic */ void setReceivers$volatile(long j) {
        this.receivers$volatile = j;
    }

    private final /* synthetic */ void setSendSegment$volatile(Object obj) {
        this.sendSegment$volatile = obj;
    }

    private final /* synthetic */ void setSendersAndCloseStatus$volatile(long j) {
        this.sendersAndCloseStatus$volatile = j;
    }

    private final /* synthetic */ void set_closeCause$volatile(Object obj) {
        this._closeCause$volatile = obj;
    }

    private final /* synthetic */ void update$atomicfu(Object obj, AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Long> function1) {
        while (true) {
            long j = atomicLongFieldUpdater.get(obj);
            Object obj2 = obj;
            AtomicLongFieldUpdater atomicLongFieldUpdater2 = atomicLongFieldUpdater;
            if (!atomicLongFieldUpdater2.compareAndSet(obj2, j, function1.invoke(Long.valueOf(j)).longValue())) {
                atomicLongFieldUpdater = atomicLongFieldUpdater2;
                obj = obj2;
            } else {
                return;
            }
        }
    }

    public Object receive(Continuation<? super E> continuation) {
        return receive$suspendImpl(this, continuation);
    }

    /* renamed from: receiveCatching-JP2dKIU  reason: not valid java name */
    public Object m1528receiveCatchingJP2dKIU(Continuation<? super ChannelResult<? extends E>> continuation) {
        return m1526receiveCatchingJP2dKIU$suspendImpl(this, continuation);
    }

    public Object send(E e, Continuation<? super Unit> continuation) {
        return send$suspendImpl(this, e, continuation);
    }

    public Object sendBroadcast$kotlinx_coroutines_core(E e, Continuation<? super Boolean> continuation) {
        return sendBroadcast$suspendImpl(this, e, continuation);
    }

    public BufferedChannel(int capacity2, Function1<? super E, Unit> onUndeliveredElement2) {
        ChannelSegment channelSegment;
        BufferedChannel$$ExternalSyntheticLambda1 bufferedChannel$$ExternalSyntheticLambda1;
        this.capacity = capacity2;
        this.onUndeliveredElement = onUndeliveredElement2;
        if (this.capacity >= 0) {
            this.bufferEnd$volatile = BufferedChannelKt.initialBufferEnd(this.capacity);
            this.completedExpandBuffersAndPauseFlag$volatile = getBufferEndCounter();
            ChannelSegment firstSegment = new ChannelSegment(0, (ChannelSegment) null, this, 3);
            this.sendSegment$volatile = firstSegment;
            this.receiveSegment$volatile = firstSegment;
            if (isRendezvousOrUnlimited()) {
                channelSegment = BufferedChannelKt.NULL_SEGMENT;
                Intrinsics.checkNotNull(channelSegment, "null cannot be cast to non-null type kotlinx.coroutines.channels.ChannelSegment<E of kotlinx.coroutines.channels.BufferedChannel>");
            } else {
                channelSegment = firstSegment;
            }
            this.bufferEndSegment$volatile = channelSegment;
            if (this.onUndeliveredElement != null) {
                bufferedChannel$$ExternalSyntheticLambda1 = new BufferedChannel$$ExternalSyntheticLambda1(this);
            } else {
                bufferedChannel$$ExternalSyntheticLambda1 = null;
            }
            this.onUndeliveredElementReceiveCancellationConstructor = bufferedChannel$$ExternalSyntheticLambda1;
            this._closeCause$volatile = BufferedChannelKt.NO_CLOSE_CAUSE;
            return;
        }
        throw new IllegalArgumentException(("Invalid channel capacity: " + this.capacity + ", should be >=0").toString());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BufferedChannel(int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : function1);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E element) {
        return Channel.DefaultImpls.offer(this, element);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = {}))
    public E poll() {
        return Channel.DefaultImpls.poll(this);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public Object receiveOrNull(Continuation<? super E> $completion) {
        return Channel.DefaultImpls.receiveOrNull(this, $completion);
    }

    public final long getSendersCounter$kotlinx_coroutines_core() {
        return sendersAndCloseStatus$volatile$FU.get(this) & 1152921504606846975L;
    }

    public final long getReceiversCounter$kotlinx_coroutines_core() {
        return receivers$volatile$FU.get(this);
    }

    private final long getBufferEndCounter() {
        return bufferEnd$volatile$FU.get(this);
    }

    private final boolean isRendezvousOrUnlimited() {
        long it = getBufferEndCounter();
        return it == 0 || it == Long.MAX_VALUE;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00cf, code lost:
        r0 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ <E> java.lang.Object send$suspendImpl(kotlinx.coroutines.channels.BufferedChannel<E> r21, E r22, kotlin.coroutines.Continuation<? super kotlin.Unit> r23) {
        /*
            r0 = 0
            r7 = r0
            r1 = r21
            r0 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = sendSegment$volatile$FU
            java.lang.Object r2 = r2.get(r1)
            kotlinx.coroutines.channels.ChannelSegment r2 = (kotlinx.coroutines.channels.ChannelSegment) r2
        L_0x0011:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r3 = sendersAndCloseStatus$volatile$FU
            long r9 = r3.getAndIncrement(r1)
            r3 = r9
            r5 = 0
            r11 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r5 = r3 & r11
            boolean r8 = r1.isClosedForSend0(r9)
            int r3 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r3 = (long) r3
            long r11 = r5 / r3
            int r3 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r3 = (long) r3
            long r3 = r5 % r3
            int r3 = (int) r3
            long r13 = r2.id
            int r4 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r4 == 0) goto L_0x0051
            kotlinx.coroutines.channels.ChannelSegment r4 = r1.findSegmentSend(r11, r2)
            if (r4 != 0) goto L_0x0050
            if (r8 == 0) goto L_0x004f
            r4 = 0
            java.lang.Object r13 = r21.onClosedSend(r22, r23)
            java.lang.Object r14 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r13 != r14) goto L_0x004c
            return r13
        L_0x004c:
            goto L_0x00cc
        L_0x004f:
            goto L_0x0011
        L_0x0050:
            r2 = r4
        L_0x0051:
            r4 = r22
            int r13 = r1.updateCellSend(r2, r3, r4, r5, r7, r8)
            switch(r13) {
                case 0: goto L_0x00c4;
                case 1: goto L_0x00bf;
                case 2: goto L_0x0099;
                case 3: goto L_0x007e;
                case 4: goto L_0x0065;
                case 5: goto L_0x005e;
                default: goto L_0x005a;
            }
        L_0x005a:
            r20 = r0
            goto L_0x00cf
        L_0x005e:
            r2.cleanPrev()
            r20 = r0
            goto L_0x00cf
        L_0x0065:
            long r13 = r1.getReceiversCounter$kotlinx_coroutines_core()
            int r4 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r4 >= 0) goto L_0x0070
            r2.cleanPrev()
        L_0x0070:
            r4 = 0
            java.lang.Object r13 = r21.onClosedSend(r22, r23)
            java.lang.Object r14 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r13 != r14) goto L_0x007c
            return r13
        L_0x007c:
            goto L_0x00cc
        L_0x007e:
            r4 = r2
            r15 = r3
            r14 = r4
            r17 = r5
            r16 = r22
            r4 = 0
            r13 = r21
            r19 = r23
            r20 = r0
            java.lang.Object r0 = r13.sendOnNoWaiterSuspend(r14, r15, r16, r17, r19)
            java.lang.Object r13 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r13) goto L_0x0097
            return r0
        L_0x0097:
            goto L_0x00cc
        L_0x0099:
            r20 = r0
            if (r8 == 0) goto L_0x00ae
            r2.onSlotCleaned()
            r0 = 0
            java.lang.Object r4 = r21.onClosedSend(r22, r23)
            java.lang.Object r13 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r4 != r13) goto L_0x00ac
            return r4
        L_0x00ac:
            goto L_0x00cc
        L_0x00ae:
            r0 = 0
            boolean r4 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r4 != 0) goto L_0x00b7
            goto L_0x00cc
        L_0x00b7:
            r4 = 0
            java.lang.AssertionError r4 = new java.lang.AssertionError
            r4.<init>()
            throw r4
        L_0x00bf:
            r20 = r0
            r0 = 0
            goto L_0x00cc
        L_0x00c4:
            r20 = r0
            r2.cleanPrev()
            r0 = 0
        L_0x00cc:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x00cf:
            r0 = r20
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.send$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final Object onClosedSend(E element, Continuation<? super Unit> $completion) {
        Throwable th;
        UndeliveredElementException it;
        Throwable th2;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation continuation = cancellable$iv;
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
            Continuation $this$resumeWithStackTrace$iv = continuation;
            Throwable exception$iv = getSendException();
            Result.Companion companion = Result.Companion;
            if (!DebugKt.getRECOVER_STACK_TRACES() || !($this$resumeWithStackTrace$iv instanceof CoroutineStackFrame)) {
                th = exception$iv;
            } else {
                th = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) $this$resumeWithStackTrace$iv);
            }
            $this$resumeWithStackTrace$iv.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(th)));
        } else {
            ExceptionsKt.addSuppressed(it, getSendException());
            Continuation $this$resumeWithStackTrace$iv2 = continuation;
            Result.Companion companion2 = Result.Companion;
            if (!DebugKt.getRECOVER_STACK_TRACES() || !($this$resumeWithStackTrace$iv2 instanceof CoroutineStackFrame)) {
                th2 = it;
            } else {
                th2 = StackTraceRecoveryKt.recoverFromStackFrame(it, (CoroutineStackFrame) $this$resumeWithStackTrace$iv2);
            }
            $this$resumeWithStackTrace$iv2.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(th2)));
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x012c, code lost:
        r0 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x018f, code lost:
        r0 = r13.getResult();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x019a, code lost:
        if (r0 != kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()) goto L_0x019f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x019c, code lost:
        kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r33);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01a3, code lost:
        if (r0 != kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()) goto L_0x01a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01a5, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01a9, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment<E> r28, int r29, E r30, long r31, kotlin.coroutines.Continuation<? super kotlin.Unit> r33) {
        /*
            r27 = this;
            r1 = r27
            r10 = 0
            r11 = r33
            r12 = 0
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r11)
            kotlinx.coroutines.CancellableContinuationImpl r13 = kotlinx.coroutines.CancellableContinuationKt.getOrCreateCancellableContinuation(r0)
            r0 = r13
            r14 = 0
            r2 = r27
            r15 = 0
            r8 = r0
            kotlinx.coroutines.Waiter r8 = (kotlinx.coroutines.Waiter) r8     // Catch:{ all -> 0x01b4 }
            r9 = 0
            r3 = r28
            r4 = r29
            r5 = r30
            r6 = r31
            int r8 = r2.updateCellSend(r3, r4, r5, r6, r8, r9)     // Catch:{ all -> 0x01b2 }
            java.lang.String r16 = "unexpected"
            switch(r8) {
                case 0: goto L_0x0175;
                case 1: goto L_0x015e;
                case 2: goto L_0x014a;
                case 3: goto L_0x0029;
                case 4: goto L_0x0130;
                case 5: goto L_0x0033;
                default: goto L_0x0029;
            }
        L_0x0029:
            r3 = r28
            r4 = r29
            r18 = r0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x01b2 }
            goto L_0x01aa
        L_0x0033:
            r28.cleanPrev()     // Catch:{ all -> 0x0158 }
            r17 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = sendSegment$volatile$FU     // Catch:{ all -> 0x0158 }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x0158 }
            kotlinx.coroutines.channels.ChannelSegment r3 = (kotlinx.coroutines.channels.ChannelSegment) r3     // Catch:{ all -> 0x0158 }
        L_0x0045:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r4 = sendersAndCloseStatus$volatile$FU     // Catch:{ all -> 0x0158 }
            long r6 = r4.getAndIncrement(r2)     // Catch:{ all -> 0x0158 }
            r8 = r6
            r4 = 0
            r18 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r8 = r8 & r18
            boolean r4 = r2.isClosedForSend0(r6)     // Catch:{ all -> 0x0158 }
            r18 = r0
            int r0 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE     // Catch:{ all -> 0x0158 }
            r19 = r6
            long r6 = (long) r0     // Catch:{ all -> 0x0158 }
            long r6 = r8 / r6
            int r0 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE     // Catch:{ all -> 0x0158 }
            r21 = r8
            long r8 = (long) r0     // Catch:{ all -> 0x0158 }
            long r8 = r21 % r8
            int r0 = (int) r8     // Catch:{ all -> 0x0158 }
            long r8 = r3.id     // Catch:{ all -> 0x0158 }
            int r8 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x008c
            kotlinx.coroutines.channels.ChannelSegment r8 = r2.findSegmentSend(r6, r3)     // Catch:{ all -> 0x0158 }
            if (r8 != 0) goto L_0x008b
            if (r4 == 0) goto L_0x0088
            r8 = 0
            r9 = r18
            kotlinx.coroutines.CancellableContinuation r9 = (kotlinx.coroutines.CancellableContinuation) r9     // Catch:{ all -> 0x0158 }
            r1.onClosedSendOnNoWaiterSuspend(r5, r9)     // Catch:{ all -> 0x0158 }
            r3 = r28
            r4 = r29
            goto L_0x018f
        L_0x0088:
            r0 = r18
            goto L_0x0045
        L_0x008b:
            r3 = r8
        L_0x008c:
            r8 = r18
            kotlinx.coroutines.Waiter r8 = (kotlinx.coroutines.Waiter) r8     // Catch:{ all -> 0x0158 }
            r25 = r21
            r21 = r6
            r6 = r25
            r9 = r4
            r4 = r0
            int r0 = r2.updateCellSend(r3, r4, r5, r6, r8, r9)     // Catch:{ all -> 0x0158 }
            switch(r0) {
                case 0: goto L_0x0112;
                case 1: goto L_0x00fa;
                case 2: goto L_0x00cc;
                case 3: goto L_0x00bf;
                case 4: goto L_0x00a6;
                case 5: goto L_0x00a1;
                default: goto L_0x009f;
            }     // Catch:{ all -> 0x0158 }
        L_0x009f:
            goto L_0x012c
        L_0x00a1:
            r3.cleanPrev()     // Catch:{ all -> 0x0158 }
            goto L_0x012c
        L_0x00a6:
            long r23 = r2.getReceiversCounter$kotlinx_coroutines_core()     // Catch:{ all -> 0x0158 }
            int r0 = (r6 > r23 ? 1 : (r6 == r23 ? 0 : -1))
            if (r0 >= 0) goto L_0x00b1
            r3.cleanPrev()     // Catch:{ all -> 0x0158 }
        L_0x00b1:
            r0 = 0
            r8 = r18
            kotlinx.coroutines.CancellableContinuation r8 = (kotlinx.coroutines.CancellableContinuation) r8     // Catch:{ all -> 0x0158 }
            r1.onClosedSendOnNoWaiterSuspend(r5, r8)     // Catch:{ all -> 0x0158 }
            r3 = r28
            r4 = r29
            goto L_0x018f
        L_0x00bf:
            r0 = 0
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0158 }
            r23 = r0
            java.lang.String r0 = r16.toString()     // Catch:{ all -> 0x0158 }
            r8.<init>(r0)     // Catch:{ all -> 0x0158 }
            throw r8     // Catch:{ all -> 0x0158 }
        L_0x00cc:
            if (r9 == 0) goto L_0x00df
            r3.onSlotCleaned()     // Catch:{ all -> 0x0158 }
            r0 = 0
            r8 = r18
            kotlinx.coroutines.CancellableContinuation r8 = (kotlinx.coroutines.CancellableContinuation) r8     // Catch:{ all -> 0x0158 }
            r1.onClosedSendOnNoWaiterSuspend(r5, r8)     // Catch:{ all -> 0x0158 }
            r3 = r28
            r4 = r29
            goto L_0x018f
        L_0x00df:
            r0 = r18
            kotlinx.coroutines.Waiter r0 = (kotlinx.coroutines.Waiter) r0     // Catch:{ all -> 0x0158 }
            boolean r0 = r0 instanceof kotlinx.coroutines.Waiter     // Catch:{ all -> 0x0158 }
            if (r0 == 0) goto L_0x00ec
            r0 = r18
            kotlinx.coroutines.Waiter r0 = (kotlinx.coroutines.Waiter) r0     // Catch:{ all -> 0x0158 }
            goto L_0x00ed
        L_0x00ec:
            r0 = 0
        L_0x00ed:
            if (r0 == 0) goto L_0x00f2
            r2.prepareSenderForSuspension(r0, r3, r4)     // Catch:{ all -> 0x0158 }
        L_0x00f2:
            r0 = 0
            r3 = r28
            r4 = r29
            goto L_0x018f
        L_0x00fa:
            r0 = 0
            r8 = r18
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8     // Catch:{ all -> 0x0158 }
            kotlin.Result$Companion r16 = kotlin.Result.Companion     // Catch:{ all -> 0x0158 }
            kotlin.Unit r16 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0158 }
            r23 = r0
            java.lang.Object r0 = kotlin.Result.m6constructorimpl(r16)     // Catch:{ all -> 0x0158 }
            r8.resumeWith(r0)     // Catch:{ all -> 0x0158 }
            r3 = r28
            r4 = r29
            goto L_0x018f
        L_0x0112:
            r3.cleanPrev()     // Catch:{ all -> 0x0158 }
            r0 = 0
            r8 = r18
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8     // Catch:{ all -> 0x0158 }
            kotlin.Result$Companion r16 = kotlin.Result.Companion     // Catch:{ all -> 0x0158 }
            kotlin.Unit r16 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0158 }
            r23 = r0
            java.lang.Object r0 = kotlin.Result.m6constructorimpl(r16)     // Catch:{ all -> 0x0158 }
            r8.resumeWith(r0)     // Catch:{ all -> 0x0158 }
            r3 = r28
            r4 = r29
            goto L_0x018f
        L_0x012c:
            r0 = r18
            goto L_0x0045
        L_0x0130:
            r18 = r0
            long r3 = r2.getReceiversCounter$kotlinx_coroutines_core()     // Catch:{ all -> 0x0158 }
            int r0 = (r31 > r3 ? 1 : (r31 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x013d
            r28.cleanPrev()     // Catch:{ all -> 0x0158 }
        L_0x013d:
            r0 = 0
            r3 = r18
            kotlinx.coroutines.CancellableContinuation r3 = (kotlinx.coroutines.CancellableContinuation) r3     // Catch:{ all -> 0x0158 }
            r1.onClosedSendOnNoWaiterSuspend(r5, r3)     // Catch:{ all -> 0x0158 }
            r3 = r28
            r4 = r29
            goto L_0x018f
        L_0x014a:
            r18 = r0
            r0 = r18
            kotlinx.coroutines.Waiter r0 = (kotlinx.coroutines.Waiter) r0     // Catch:{ all -> 0x0158 }
            r3 = r28
            r4 = r29
            r2.prepareSenderForSuspension(r0, r3, r4)     // Catch:{ all -> 0x01b2 }
            goto L_0x018f
        L_0x0158:
            r0 = move-exception
            r3 = r28
            r4 = r29
            goto L_0x01bb
        L_0x015e:
            r3 = r28
            r4 = r29
            r18 = r0
            r0 = 0
            r6 = r18
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6     // Catch:{ all -> 0x01b2 }
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch:{ all -> 0x01b2 }
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01b2 }
            java.lang.Object r7 = kotlin.Result.m6constructorimpl(r7)     // Catch:{ all -> 0x01b2 }
            r6.resumeWith(r7)     // Catch:{ all -> 0x01b2 }
            goto L_0x018f
        L_0x0175:
            r3 = r28
            r4 = r29
            r18 = r0
            r3.cleanPrev()     // Catch:{ all -> 0x01b2 }
            r0 = 0
            r6 = r18
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6     // Catch:{ all -> 0x01b2 }
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch:{ all -> 0x01b2 }
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01b2 }
            java.lang.Object r7 = kotlin.Result.m6constructorimpl(r7)     // Catch:{ all -> 0x01b2 }
            r6.resumeWith(r7)     // Catch:{ all -> 0x01b2 }
        L_0x018f:
            java.lang.Object r0 = r13.getResult()
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r2) goto L_0x019f
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r33)
        L_0x019f:
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r2) goto L_0x01a6
            return r0
        L_0x01a6:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x01aa:
            java.lang.String r6 = r16.toString()     // Catch:{ all -> 0x01b2 }
            r0.<init>(r6)     // Catch:{ all -> 0x01b2 }
            throw r0     // Catch:{ all -> 0x01b2 }
        L_0x01b2:
            r0 = move-exception
            goto L_0x01bb
        L_0x01b4:
            r0 = move-exception
            r3 = r28
            r4 = r29
            r5 = r30
        L_0x01bb:
            r13.releaseClaimedReusableContinuation$kotlinx_coroutines_core()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment, int, java.lang.Object, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final void prepareSenderForSuspension(Waiter $this$prepareSenderForSuspension, ChannelSegment<E> segment, int index) {
        $this$prepareSenderForSuspension.invokeOnCancellation(segment, BufferedChannelKt.SEGMENT_SIZE + index);
    }

    /* access modifiers changed from: private */
    public final void onClosedSendOnNoWaiterSuspend(E element, CancellableContinuation<? super Unit> cont) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, cont.getContext());
        }
        Continuation continuation = cont;
        Throwable exception$iv = getSendException();
        if (DebugKt.getRECOVER_STACK_TRACES() && (cont instanceof CoroutineStackFrame)) {
            exception$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) cont);
        }
        Result.Companion companion = Result.Companion;
        continuation.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(exception$iv)));
    }

    /* renamed from: trySend-JP2dKIU  reason: not valid java name */
    public Object m1530trySendJP2dKIU(E element) {
        ChannelSegment segment$iv;
        if (shouldSendSuspend(sendersAndCloseStatus$volatile$FU.get(this))) {
            return ChannelResult.Companion.m1554failurePtdJZtk();
        }
        Object waiter$iv = BufferedChannelKt.INTERRUPTED_SEND;
        ChannelSegment segment$iv2 = (ChannelSegment) sendSegment$volatile$FU.get(this);
        while (true) {
            long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
            long s$iv = 1152921504606846975L & sendersAndCloseStatusCur$iv;
            boolean closed$iv = isClosedForSend0(sendersAndCloseStatusCur$iv);
            long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv2.id != id$iv) {
                segment$iv = findSegmentSend(id$iv, segment$iv2);
                if (segment$iv != null) {
                } else if (closed$iv) {
                    return ChannelResult.Companion.m1553closedJP2dKIU(getSendException());
                }
            } else {
                segment$iv = segment$iv2;
            }
            switch (updateCellSend(segment$iv, i$iv, element, s$iv, waiter$iv, closed$iv)) {
                case 0:
                    segment$iv.cleanPrev();
                    return ChannelResult.Companion.m1555successJP2dKIU(Unit.INSTANCE);
                case 1:
                    return ChannelResult.Companion.m1555successJP2dKIU(Unit.INSTANCE);
                case 2:
                    if (closed$iv) {
                        segment$iv.onSlotCleaned();
                        return ChannelResult.Companion.m1553closedJP2dKIU(getSendException());
                    }
                    Waiter waiter = waiter$iv instanceof Waiter ? (Waiter) waiter$iv : null;
                    if (waiter != null) {
                        prepareSenderForSuspension(waiter, segment$iv, i$iv);
                    }
                    segment$iv.onSlotCleaned();
                    return ChannelResult.Companion.m1554failurePtdJZtk();
                case 3:
                    throw new IllegalStateException("unexpected".toString());
                case 4:
                    if (s$iv < getReceiversCounter$kotlinx_coroutines_core()) {
                        segment$iv.cleanPrev();
                    }
                    return ChannelResult.Companion.m1553closedJP2dKIU(getSendException());
                case 5:
                    segment$iv.cleanPrev();
                    break;
            }
            segment$iv2 = segment$iv;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0122, code lost:
        r11 = r17;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ <E> java.lang.Object sendBroadcast$suspendImpl(kotlinx.coroutines.channels.BufferedChannel<E> r24, E r25, kotlin.coroutines.Continuation<? super java.lang.Boolean> r26) {
        /*
            r0 = r24
            r8 = 0
            r9 = r26
            r10 = 0
            kotlinx.coroutines.CancellableContinuationImpl r1 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r9)
            r11 = 1
            r1.<init>(r2, r11)
            r12 = r1
            r12.initCancellability()
            r13 = r12
            kotlinx.coroutines.CancellableContinuation r13 = (kotlinx.coroutines.CancellableContinuation) r13
            r14 = 0
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r1 = r0.onUndeliveredElement
            r15 = 0
            if (r1 != 0) goto L_0x001f
            r1 = r11
            goto L_0x0020
        L_0x001f:
            r1 = r15
        L_0x0020:
            if (r1 == 0) goto L_0x0126
            kotlinx.coroutines.channels.BufferedChannel$SendBroadcast r6 = new kotlinx.coroutines.channels.BufferedChannel$SendBroadcast
            r6.<init>(r13)
            r16 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = sendSegment$volatile$FU
            java.lang.Object r1 = r1.get(r0)
            kotlinx.coroutines.channels.ChannelSegment r1 = (kotlinx.coroutines.channels.ChannelSegment) r1
        L_0x0036:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r2 = sendersAndCloseStatus$volatile$FU
            long r2 = r2.getAndIncrement(r0)
            r4 = r2
            r7 = 0
            r17 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r4 = r4 & r17
            boolean r7 = r0.isClosedForSend0(r2)
            r17 = r11
            int r11 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            r18 = r2
            long r2 = (long) r11
            long r2 = r4 / r2
            int r11 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            r20 = r4
            long r4 = (long) r11
            long r4 = r20 % r4
            int r4 = (int) r4
            r11 = r4
            long r4 = r1.id
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0083
            kotlinx.coroutines.channels.ChannelSegment r4 = r0.findSegmentSend(r2, r1)
            if (r4 != 0) goto L_0x0082
            if (r7 == 0) goto L_0x007f
            r4 = 0
            r5 = r13
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            kotlin.Result$Companion r17 = kotlin.Result.Companion
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r15)
            java.lang.Object r15 = kotlin.Result.m6constructorimpl(r15)
            r5.resumeWith(r15)
            goto L_0x0111
        L_0x007f:
            r11 = r17
            goto L_0x0036
        L_0x0082:
            r1 = r4
        L_0x0083:
            r4 = r20
            r20 = r2
            r2 = r11
            r3 = r25
            int r11 = r0.updateCellSend(r1, r2, r3, r4, r6, r7)
            switch(r11) {
                case 0: goto L_0x00fc;
                case 1: goto L_0x00ea;
                case 2: goto L_0x00c2;
                case 3: goto L_0x00b5;
                case 4: goto L_0x0098;
                case 5: goto L_0x0093;
                default: goto L_0x0091;
            }
        L_0x0091:
            goto L_0x0122
        L_0x0093:
            r1.cleanPrev()
            goto L_0x0122
        L_0x0098:
            long r22 = r0.getReceiversCounter$kotlinx_coroutines_core()
            int r3 = (r4 > r22 ? 1 : (r4 == r22 ? 0 : -1))
            if (r3 >= 0) goto L_0x00a3
            r1.cleanPrev()
        L_0x00a3:
            r3 = 0
            r11 = r13
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            kotlin.Result$Companion r17 = kotlin.Result.Companion
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r15)
            java.lang.Object r15 = kotlin.Result.m6constructorimpl(r15)
            r11.resumeWith(r15)
            goto L_0x0111
        L_0x00b5:
            r3 = 0
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r15 = "unexpected"
            java.lang.String r15 = r15.toString()
            r11.<init>(r15)
            throw r11
        L_0x00c2:
            if (r7 == 0) goto L_0x00d9
            r1.onSlotCleaned()
            r3 = 0
            r11 = r13
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            kotlin.Result$Companion r17 = kotlin.Result.Companion
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r15)
            java.lang.Object r15 = kotlin.Result.m6constructorimpl(r15)
            r11.resumeWith(r15)
            goto L_0x0111
        L_0x00d9:
            boolean r3 = r6 instanceof kotlinx.coroutines.Waiter
            if (r3 == 0) goto L_0x00e1
            r3 = r6
            kotlinx.coroutines.Waiter r3 = (kotlinx.coroutines.Waiter) r3
            goto L_0x00e2
        L_0x00e1:
            r3 = 0
        L_0x00e2:
            if (r3 == 0) goto L_0x00e7
            r0.prepareSenderForSuspension(r3, r1, r2)
        L_0x00e7:
            r3 = 0
            goto L_0x0111
        L_0x00ea:
            r3 = 0
            r11 = r13
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            kotlin.Result$Companion r15 = kotlin.Result.Companion
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r17)
            java.lang.Object r15 = kotlin.Result.m6constructorimpl(r15)
            r11.resumeWith(r15)
            goto L_0x0111
        L_0x00fc:
            r1.cleanPrev()
            r3 = 0
            r11 = r13
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            kotlin.Result$Companion r15 = kotlin.Result.Companion
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r17)
            java.lang.Object r15 = kotlin.Result.m6constructorimpl(r15)
            r11.resumeWith(r15)
        L_0x0111:
            java.lang.Object r1 = r12.getResult()
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r1 != r2) goto L_0x0120
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r26)
        L_0x0120:
            return r1
        L_0x0122:
            r11 = r17
            goto L_0x0036
        L_0x0126:
            r1 = 0
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "the `onUndeliveredElement` feature is unsupported for `sendBroadcast(e)`"
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.sendBroadcast$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\r\u001a\u00020\u000eH\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$SendBroadcast;", "Lkotlinx/coroutines/Waiter;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "<init>", "(Lkotlinx/coroutines/CancellableContinuation;)V", "getCont", "()Lkotlinx/coroutines/CancellableContinuation;", "invokeOnCancellation", "", "segment", "Lkotlinx/coroutines/internal/Segment;", "index", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: BufferedChannel.kt */
    private static final class SendBroadcast implements Waiter {
        private final /* synthetic */ CancellableContinuationImpl<Boolean> $$delegate_0;
        private final CancellableContinuation<Boolean> cont;

        public void invokeOnCancellation(Segment<?> segment, int i) {
            this.$$delegate_0.invokeOnCancellation(segment, i);
        }

        public SendBroadcast(CancellableContinuation<? super Boolean> cont2) {
            Intrinsics.checkNotNull(cont2, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuationImpl<kotlin.Boolean>");
            this.$$delegate_0 = (CancellableContinuationImpl) cont2;
            this.cont = cont2;
        }

        public final CancellableContinuation<Boolean> getCont() {
            return this.cont;
        }
    }

    static /* synthetic */ Object sendImpl$default(BufferedChannel $this, Object element, Object waiter, Function0 onRendezvousOrBuffered, Function2 onSuspend, Function0 onClosed, Function4 onNoWaiterSuspend, int i, Object obj) {
        Function4 onNoWaiterSuspend2;
        BufferedChannel bufferedChannel = $this;
        if (obj == null) {
            if ((i & 32) != 0) {
                onNoWaiterSuspend2 = BufferedChannel$sendImpl$1.INSTANCE;
            } else {
                onNoWaiterSuspend2 = onNoWaiterSuspend;
            }
            ChannelSegment segment = (ChannelSegment) sendSegment$volatile$FU.get(bufferedChannel);
            while (true) {
                long sendersAndCloseStatusCur = sendersAndCloseStatus$volatile$FU.getAndIncrement(bufferedChannel);
                long s = sendersAndCloseStatusCur & 1152921504606846975L;
                boolean closed = bufferedChannel.isClosedForSend0(sendersAndCloseStatusCur);
                long id = s / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i2 = (int) (s % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (segment.id != id) {
                    ChannelSegment access$findSegmentSend = bufferedChannel.findSegmentSend(id, segment);
                    if (access$findSegmentSend != null) {
                        segment = access$findSegmentSend;
                    } else if (closed) {
                        return onClosed.invoke();
                    }
                }
                Object obj2 = waiter;
                switch (bufferedChannel.updateCellSend(segment, i2, element, s, obj2, closed)) {
                    case 0:
                        Object obj3 = element;
                        Function2 function2 = onSuspend;
                        segment.cleanPrev();
                        return onRendezvousOrBuffered.invoke();
                    case 1:
                        Object obj4 = element;
                        Function2 function22 = onSuspend;
                        return onRendezvousOrBuffered.invoke();
                    case 2:
                        Object obj5 = element;
                        if (closed) {
                            segment.onSlotCleaned();
                            return onClosed.invoke();
                        }
                        Waiter waiter2 = obj2 instanceof Waiter ? (Waiter) obj2 : null;
                        if (waiter2 != null) {
                            bufferedChannel.prepareSenderForSuspension(waiter2, segment, i2);
                        }
                        return onSuspend.invoke(segment, Integer.valueOf(i2));
                    case 3:
                        return onNoWaiterSuspend2.invoke(segment, Integer.valueOf(i2), element, Long.valueOf(s));
                    case 4:
                        if (s < bufferedChannel.getReceiversCounter$kotlinx_coroutines_core()) {
                            segment.cleanPrev();
                        }
                        return onClosed.invoke();
                    case 5:
                        segment.cleanPrev();
                        Object obj6 = element;
                        Function2 function23 = onSuspend;
                        break;
                    default:
                        Object obj7 = element;
                        Function2 function24 = onSuspend;
                        break;
                }
            }
        } else {
            Object obj8 = element;
            Object obj9 = waiter;
            Function2 function25 = onSuspend;
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendImpl");
        }
    }

    private final <R> R sendImpl(E element, Object waiter, Function0<? extends R> onRendezvousOrBuffered, Function2<? super ChannelSegment<E>, ? super Integer, ? extends R> onSuspend, Function0<? extends R> onClosed, Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> onNoWaiterSuspend) {
        ChannelSegment segment = (ChannelSegment) sendSegment$volatile$FU.get(this);
        while (true) {
            long sendersAndCloseStatusCur = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
            long s = sendersAndCloseStatusCur & 1152921504606846975L;
            boolean closed = isClosedForSend0(sendersAndCloseStatusCur);
            long id = s / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (s % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id != id) {
                ChannelSegment access$findSegmentSend = findSegmentSend(id, segment);
                if (access$findSegmentSend != null) {
                    segment = access$findSegmentSend;
                } else if (closed) {
                    return onClosed.invoke();
                }
            }
            Object obj = waiter;
            switch (updateCellSend(segment, i, element, s, obj, closed)) {
                case 0:
                    E e = element;
                    Function2<? super ChannelSegment<E>, ? super Integer, ? extends R> function2 = onSuspend;
                    Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> function4 = onNoWaiterSuspend;
                    segment.cleanPrev();
                    return onRendezvousOrBuffered.invoke();
                case 1:
                    E e2 = element;
                    Function2<? super ChannelSegment<E>, ? super Integer, ? extends R> function22 = onSuspend;
                    Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> function42 = onNoWaiterSuspend;
                    return onRendezvousOrBuffered.invoke();
                case 2:
                    E e3 = element;
                    Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> function43 = onNoWaiterSuspend;
                    if (closed) {
                        segment.onSlotCleaned();
                        return onClosed.invoke();
                    }
                    Waiter waiter2 = obj instanceof Waiter ? (Waiter) obj : null;
                    if (waiter2 != null) {
                        prepareSenderForSuspension(waiter2, segment, i);
                    }
                    return onSuspend.invoke(segment, Integer.valueOf(i));
                case 3:
                    return onNoWaiterSuspend.invoke(segment, Integer.valueOf(i), element, Long.valueOf(s));
                case 4:
                    if (s < getReceiversCounter$kotlinx_coroutines_core()) {
                        segment.cleanPrev();
                    }
                    return onClosed.invoke();
                case 5:
                    segment.cleanPrev();
                    E e4 = element;
                    Function2<? super ChannelSegment<E>, ? super Integer, ? extends R> function23 = onSuspend;
                    Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> function44 = onNoWaiterSuspend;
                    break;
                default:
                    E e5 = element;
                    Function2<? super ChannelSegment<E>, ? super Integer, ? extends R> function24 = onSuspend;
                    Function4<? super ChannelSegment<E>, ? super Integer, ? super E, ? super Long, ? extends R> function45 = onNoWaiterSuspend;
                    break;
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00be, code lost:
        return kotlinx.coroutines.channels.ChannelResult.Companion.m1555successJP2dKIU(kotlin.Unit.INSTANCE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c7, code lost:
        r1 = r15;
     */
    /* renamed from: trySendDropOldest-JP2dKIU  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object m1531trySendDropOldestJP2dKIU(E r20) {
        /*
            r19 = this;
            r0 = r19
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            r8 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = sendSegment$volatile$FU
            java.lang.Object r1 = r1.get(r0)
            kotlinx.coroutines.channels.ChannelSegment r1 = (kotlinx.coroutines.channels.ChannelSegment) r1
        L_0x0012:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r2 = sendersAndCloseStatus$volatile$FU
            long r9 = r2.getAndIncrement(r0)
            r2 = r9
            r4 = 0
            r11 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r4 = r2 & r11
            boolean r7 = r0.isClosedForSend0(r9)
            int r2 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r2 = (long) r2
            long r11 = r4 / r2
            int r2 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r2 = (long) r2
            long r2 = r4 % r2
            int r2 = (int) r2
            long r13 = r1.id
            int r3 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r3 == 0) goto L_0x004f
            kotlinx.coroutines.channels.ChannelSegment r3 = r0.findSegmentSend(r11, r1)
            if (r3 != 0) goto L_0x004e
            if (r7 == 0) goto L_0x004d
            r3 = 0
        L_0x0042:
            kotlinx.coroutines.channels.ChannelResult$Companion r13 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Throwable r14 = r0.getSendException()
            java.lang.Object r13 = r13.m1553closedJP2dKIU(r14)
            return r13
        L_0x004d:
            goto L_0x0012
        L_0x004e:
            r1 = r3
        L_0x004f:
            r3 = r20
            int r13 = r0.updateCellSend(r1, r2, r3, r4, r6, r7)
            switch(r13) {
                case 0: goto L_0x00bf;
                case 1: goto L_0x00b2;
                case 2: goto L_0x007d;
                case 3: goto L_0x0070;
                case 4: goto L_0x0063;
                case 5: goto L_0x005d;
                default: goto L_0x0058;
            }
        L_0x0058:
            r15 = r1
            r16 = r2
            goto L_0x00c7
        L_0x005d:
            r1.cleanPrev()
            r15 = r1
            goto L_0x00c7
        L_0x0063:
            long r13 = r0.getReceiversCounter$kotlinx_coroutines_core()
            int r3 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r3 >= 0) goto L_0x006e
            r1.cleanPrev()
        L_0x006e:
            r3 = 0
            goto L_0x0042
        L_0x0070:
            r3 = 0
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "unexpected"
            java.lang.String r14 = r14.toString()
            r13.<init>(r14)
            throw r13
        L_0x007d:
            if (r7 == 0) goto L_0x0084
            r1.onSlotCleaned()
            r3 = 0
            goto L_0x0042
        L_0x0084:
            boolean r3 = r6 instanceof kotlinx.coroutines.Waiter
            if (r3 == 0) goto L_0x008c
            r3 = r6
            kotlinx.coroutines.Waiter r3 = (kotlinx.coroutines.Waiter) r3
            goto L_0x008d
        L_0x008c:
            r3 = 0
        L_0x008d:
            if (r3 == 0) goto L_0x0092
            r0.prepareSenderForSuspension(r3, r1, r2)
        L_0x0092:
            r3 = r1
            r13 = r2
            r14 = 0
            r15 = r1
            r16 = r2
            long r1 = r3.id
            r17 = r1
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r1 = (long) r1
            long r1 = r1 * r17
            r17 = r1
            long r1 = (long) r13
            long r1 = r17 + r1
            r0.dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(r1)
            kotlinx.coroutines.channels.ChannelResult$Companion r1 = kotlinx.coroutines.channels.ChannelResult.Companion
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            java.lang.Object r1 = r1.m1555successJP2dKIU(r2)
            return r1
        L_0x00b2:
            r15 = r1
            r16 = r2
            r1 = 0
        L_0x00b6:
            kotlinx.coroutines.channels.ChannelResult$Companion r2 = kotlinx.coroutines.channels.ChannelResult.Companion
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            java.lang.Object r2 = r2.m1555successJP2dKIU(r3)
            return r2
        L_0x00bf:
            r15 = r1
            r16 = r2
            r15.cleanPrev()
            r1 = 0
            goto L_0x00b6
        L_0x00c7:
            r1 = r15
            goto L_0x0012
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m1531trySendDropOldestJP2dKIU(java.lang.Object):java.lang.Object");
    }

    private final void sendImplOnNoWaiter(ChannelSegment<E> segment, int index, E element, long s, Waiter waiter, Function0<Unit> onRendezvousOrBuffered, Function0<Unit> onClosed) {
        Unit unit;
        switch (updateCellSend(segment, index, element, s, waiter, false)) {
            case 0:
                int i = index;
                Waiter waiter2 = waiter;
                segment.cleanPrev();
                onRendezvousOrBuffered.invoke();
                return;
            case 1:
                ChannelSegment<E> channelSegment = segment;
                int i2 = index;
                Waiter waiter3 = waiter;
                onRendezvousOrBuffered.invoke();
                return;
            case 2:
                prepareSenderForSuspension(waiter, segment, index);
                return;
            case 4:
                Waiter waiter4 = waiter;
                if (s < getReceiversCounter$kotlinx_coroutines_core()) {
                    segment.cleanPrev();
                }
                onClosed.invoke();
                ChannelSegment<E> channelSegment2 = segment;
                int i3 = index;
                return;
            case 5:
                segment.cleanPrev();
                ChannelSegment segment$iv = (ChannelSegment) sendSegment$volatile$FU.get(this);
                while (true) {
                    long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
                    long s$iv = sendersAndCloseStatusCur$iv & 1152921504606846975L;
                    boolean closed$iv = isClosedForSend0(sendersAndCloseStatusCur$iv);
                    long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                    int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                    if (segment$iv.id != id$iv) {
                        ChannelSegment access$findSegmentSend = findSegmentSend(id$iv, segment$iv);
                        if (access$findSegmentSend != null) {
                            segment$iv = access$findSegmentSend;
                        } else if (closed$iv) {
                            unit = onClosed.invoke();
                            Waiter waiter5 = waiter;
                        }
                    }
                    int i$iv2 = i$iv;
                    Waiter waiter6 = waiter;
                    switch (updateCellSend(segment$iv, i$iv2, element, s$iv, waiter6, closed$iv)) {
                        case 0:
                            segment$iv.cleanPrev();
                            unit = onRendezvousOrBuffered.invoke();
                            break;
                        case 1:
                            unit = onRendezvousOrBuffered.invoke();
                            break;
                        case 2:
                            if (!closed$iv) {
                                Waiter waiter7 = waiter6 instanceof Waiter ? waiter6 : null;
                                if (waiter7 != null) {
                                    prepareSenderForSuspension(waiter7, segment$iv, i$iv2);
                                }
                                unit = Unit.INSTANCE;
                                break;
                            } else {
                                segment$iv.onSlotCleaned();
                                unit = onClosed.invoke();
                                break;
                            }
                        case 3:
                            throw new IllegalStateException("unexpected".toString());
                        case 4:
                            if (s$iv < getReceiversCounter$kotlinx_coroutines_core()) {
                                segment$iv.cleanPrev();
                            }
                            unit = onClosed.invoke();
                            break;
                        case 5:
                            segment$iv.cleanPrev();
                            continue;
                    }
                }
                Unit unit2 = unit;
                ChannelSegment<E> channelSegment3 = segment;
                int i4 = index;
                return;
            default:
                ChannelSegment<E> channelSegment4 = segment;
                int i5 = index;
                Waiter waiter8 = waiter;
                throw new IllegalStateException("unexpected".toString());
        }
    }

    /* access modifiers changed from: private */
    public final int updateCellSend(ChannelSegment<E> segment, int index, E element, long s, Object waiter, boolean closed) {
        segment.storeElement$kotlinx_coroutines_core(index, element);
        if (closed) {
            int updateCellSendSlow = updateCellSendSlow(segment, index, element, s, waiter, closed);
            boolean z = closed;
            Object obj = waiter;
            long j = s;
            E e = element;
            int i = index;
            ChannelSegment<E> channelSegment = segment;
            return updateCellSendSlow;
        }
        boolean closed2 = closed;
        Object waiter2 = waiter;
        long s2 = s;
        Object element2 = element;
        int index2 = index;
        ChannelSegment<E> segment2 = segment;
        Object state = segment2.getState$kotlinx_coroutines_core(index2);
        if (state == null) {
            if (bufferOrRendezvousSend(s2)) {
                if (segment2.casState$kotlinx_coroutines_core(index2, (Object) null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else if (waiter2 == null) {
                return 3;
            } else {
                if (segment2.casState$kotlinx_coroutines_core(index2, (Object) null, waiter2)) {
                    return 2;
                }
            }
        } else if (state instanceof Waiter) {
            segment2.cleanElement$kotlinx_coroutines_core(index2);
            if (tryResumeReceiver(state, element2)) {
                segment2.setState$kotlinx_coroutines_core(index2, BufferedChannelKt.DONE_RCV);
                onReceiveDequeued();
                return 0;
            }
            if (segment2.getAndSetState$kotlinx_coroutines_core(index2, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                segment2.onCancelledRequest(index2, true);
            }
            return 5;
        }
        return updateCellSendSlow(segment2, index2, element2, s2, waiter2, closed2);
    }

    private final int updateCellSendSlow(ChannelSegment<E> segment, int index, E element, long s, Object waiter, boolean closed) {
        while (true) {
            Object state = segment.getState$kotlinx_coroutines_core(index);
            if (state == null) {
                if (!bufferOrRendezvousSend(s) || closed) {
                    if (closed) {
                        if (segment.casState$kotlinx_coroutines_core(index, (Object) null, BufferedChannelKt.INTERRUPTED_SEND)) {
                            segment.onCancelledRequest(index, false);
                            return 4;
                        }
                    } else if (waiter == null) {
                        return 3;
                    } else {
                        if (segment.casState$kotlinx_coroutines_core(index, (Object) null, waiter)) {
                            return 2;
                        }
                    }
                } else if (segment.casState$kotlinx_coroutines_core(index, (Object) null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else if (state == BufferedChannelKt.IN_BUFFER) {
                if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else if (state == BufferedChannelKt.INTERRUPTED_RCV) {
                segment.cleanElement$kotlinx_coroutines_core(index);
                return 5;
            } else if (state == BufferedChannelKt.POISONED) {
                segment.cleanElement$kotlinx_coroutines_core(index);
                return 5;
            } else if (state == BufferedChannelKt.getCHANNEL_CLOSED()) {
                segment.cleanElement$kotlinx_coroutines_core(index);
                completeCloseOrCancel();
                return 4;
            } else {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if ((((state instanceof Waiter) || (state instanceof WaiterEB)) ? 1 : 0) == 0) {
                        throw new AssertionError();
                    }
                }
                segment.cleanElement$kotlinx_coroutines_core(index);
                if (tryResumeReceiver(state instanceof WaiterEB ? ((WaiterEB) state).waiter : state, element)) {
                    segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                    onReceiveDequeued();
                    return 0;
                }
                if (segment.getAndSetState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                    segment.onCancelledRequest(index, true);
                }
                return 5;
            }
        }
    }

    private final boolean shouldSendSuspend(long curSendersAndCloseStatus) {
        if (isClosedForSend0(curSendersAndCloseStatus)) {
            return false;
        }
        return !bufferOrRendezvousSend(curSendersAndCloseStatus & 1152921504606846975L);
    }

    private final boolean bufferOrRendezvousSend(long curSenders) {
        return curSenders < getBufferEndCounter() || curSenders < getReceiversCounter$kotlinx_coroutines_core() + ((long) this.capacity);
    }

    public boolean shouldSendSuspend$kotlinx_coroutines_core() {
        return shouldSendSuspend(sendersAndCloseStatus$volatile$FU.get(this));
    }

    private final boolean tryResumeReceiver(Object $this$tryResumeReceiver, E element) {
        if ($this$tryResumeReceiver instanceof SelectInstance) {
            return ((SelectInstance) $this$tryResumeReceiver).trySelect(this, element);
        }
        KFunction<Unit> kFunction = null;
        if ($this$tryResumeReceiver instanceof ReceiveCatching) {
            Intrinsics.checkNotNull($this$tryResumeReceiver, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveCatching<E of kotlinx.coroutines.channels.BufferedChannel>");
            ReceiveCatching receiveCatching = (ReceiveCatching) $this$tryResumeReceiver;
            CancellableContinuation cancellableContinuation = ((ReceiveCatching) $this$tryResumeReceiver).cont;
            ChannelResult r2 = ChannelResult.m1540boximpl(ChannelResult.Companion.m1555successJP2dKIU(element));
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            if (function1 != null) {
                kFunction = bindCancellationFunResult(function1);
            }
            return BufferedChannelKt.tryResume0(cancellableContinuation, r2, (Function3) kFunction);
        } else if ($this$tryResumeReceiver instanceof BufferedChannelIterator) {
            Intrinsics.checkNotNull($this$tryResumeReceiver, "null cannot be cast to non-null type kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator<E of kotlinx.coroutines.channels.BufferedChannel>");
            BufferedChannelIterator bufferedChannelIterator = (BufferedChannelIterator) $this$tryResumeReceiver;
            return ((BufferedChannelIterator) $this$tryResumeReceiver).tryResumeHasNext(element);
        } else if ($this$tryResumeReceiver instanceof CancellableContinuation) {
            Intrinsics.checkNotNull($this$tryResumeReceiver, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<E of kotlinx.coroutines.channels.BufferedChannel>");
            CancellableContinuation cancellableContinuation2 = (CancellableContinuation) $this$tryResumeReceiver;
            CancellableContinuation cancellableContinuation3 = (CancellableContinuation) $this$tryResumeReceiver;
            Function1<E, Unit> function12 = this.onUndeliveredElement;
            if (function12 != null) {
                kFunction = bindCancellationFun(function12);
            }
            return BufferedChannelKt.tryResume0(cancellableContinuation3, element, (Function3) kFunction);
        } else {
            throw new IllegalStateException(("Unexpected receiver type: " + $this$tryResumeReceiver).toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onReceiveEnqueued() {
    }

    /* access modifiers changed from: protected */
    public void onReceiveDequeued() {
    }

    static /* synthetic */ <E> Object receive$suspendImpl(BufferedChannel<E> $this, Continuation<? super E> $completion) {
        BufferedChannel this_$iv = $this;
        ChannelSegment segment$iv = (ChannelSegment) receiveSegment$volatile$FU.get(this_$iv);
        while (!this_$iv.isClosedForReceive()) {
            long r$iv = receivers$volatile$FU.getAndIncrement(this_$iv);
            long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv.id != id$iv) {
                ChannelSegment access$findSegmentReceive = this_$iv.findSegmentReceive(id$iv, segment$iv);
                if (access$findSegmentReceive == null) {
                    continue;
                } else {
                    segment$iv = access$findSegmentReceive;
                }
            }
            Object updCellResult$iv = this_$iv.updateCellReceive(segment$iv, i$iv, r$iv, (Object) null);
            if (updCellResult$iv == BufferedChannelKt.SUSPEND) {
                throw new IllegalStateException("unexpected".toString());
            } else if (updCellResult$iv == BufferedChannelKt.FAILED) {
                if (r$iv < this_$iv.getSendersCounter$kotlinx_coroutines_core()) {
                    segment$iv.cleanPrev();
                }
            } else if (updCellResult$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                return $this.receiveOnNoWaiterSuspend(segment$iv, i$iv, r$iv, $completion);
            } else {
                segment$iv.cleanPrev();
                return updCellResult$iv;
            }
        }
        throw StackTraceRecoveryKt.recoverStackTrace($this.getReceiveException());
    }

    /* JADX WARNING: type inference failed for: r5v9, types: [kotlinx.coroutines.Waiter] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object receiveOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment<E> r25, int r26, long r27, kotlin.coroutines.Continuation<? super E> r29) {
        /*
            r24 = this;
            r1 = r24
            r2 = 0
            r3 = r29
            r4 = 0
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r3)
            kotlinx.coroutines.CancellableContinuationImpl r5 = kotlinx.coroutines.CancellableContinuationKt.getOrCreateCancellableContinuation(r0)
            r0 = r5
            r6 = 0
            r7 = r24
            r13 = 0
            r12 = r0
            kotlinx.coroutines.Waiter r12 = (kotlinx.coroutines.Waiter) r12     // Catch:{ all -> 0x0175 }
            r8 = r25
            r9 = r26
            r10 = r27
            java.lang.Object r12 = r7.updateCellReceive(r8, r9, r10, r12)     // Catch:{ all -> 0x016a }
            r14 = r12
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND     // Catch:{ all -> 0x0175 }
            if (r14 != r8) goto L_0x004e
            r8 = r0
            kotlinx.coroutines.Waiter r8 = (kotlinx.coroutines.Waiter) r8     // Catch:{ all -> 0x003f }
            r15 = r25
            r9 = r26
            r7.prepareReceiverForSuspension(r8, r15, r9)     // Catch:{ all -> 0x003d }
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
            goto L_0x0154
        L_0x003d:
            r0 = move-exception
            goto L_0x0044
        L_0x003f:
            r0 = move-exception
            r15 = r25
            r9 = r26
        L_0x0044:
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
            goto L_0x0180
        L_0x004e:
            r15 = r25
            r9 = r26
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.FAILED     // Catch:{ all -> 0x0168 }
            r16 = 0
            if (r14 != r8) goto L_0x0136
            long r10 = r7.getSendersCounter$kotlinx_coroutines_core()     // Catch:{ all -> 0x0168 }
            int r8 = (r27 > r10 ? 1 : (r27 == r10 ? 0 : -1))
            if (r8 >= 0) goto L_0x0065
            r15.cleanPrev()     // Catch:{ all -> 0x003d }
        L_0x0065:
            r17 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r8 = receiveSegment$volatile$FU     // Catch:{ all -> 0x0168 }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ all -> 0x0168 }
            kotlinx.coroutines.channels.ChannelSegment r8 = (kotlinx.coroutines.channels.ChannelSegment) r8     // Catch:{ all -> 0x0168 }
        L_0x0074:
            boolean r10 = r7.isClosedForReceive()     // Catch:{ all -> 0x0168 }
            if (r10 == 0) goto L_0x008c
            r10 = 0
            r11 = r0
            kotlinx.coroutines.CancellableContinuation r11 = (kotlinx.coroutines.CancellableContinuation) r11     // Catch:{ all -> 0x003d }
            r1.onClosedReceiveOnNoWaiterSuspend(r11)     // Catch:{ all -> 0x003d }
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
            goto L_0x0154
        L_0x008c:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r10 = receivers$volatile$FU     // Catch:{ all -> 0x0168 }
            long r10 = r10.getAndIncrement(r7)     // Catch:{ all -> 0x0168 }
            int r12 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE     // Catch:{ all -> 0x0168 }
            r18 = r2
            r19 = r3
            long r2 = (long) r12
            long r2 = r10 / r2
            int r12 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE     // Catch:{ all -> 0x0130 }
            r20 = r4
            r21 = r5
            long r4 = (long) r12
            long r4 = r10 % r4
            int r4 = (int) r4     // Catch:{ all -> 0x0166 }
            r12 = r4
            long r4 = r8.id     // Catch:{ all -> 0x0166 }
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x00be
            kotlinx.coroutines.channels.ChannelSegment r4 = r7.findSegmentReceive(r2, r8)     // Catch:{ all -> 0x0166 }
            if (r4 != 0) goto L_0x00bd
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            goto L_0x0074
        L_0x00bd:
            r8 = r4
        L_0x00be:
            r9 = r12
            r12 = r0
            kotlinx.coroutines.Waiter r12 = (kotlinx.coroutines.Waiter) r12     // Catch:{ all -> 0x0166 }
            java.lang.Object r4 = r7.updateCellReceive(r8, r9, r10, r12)     // Catch:{ all -> 0x0166 }
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND     // Catch:{ all -> 0x0166 }
            if (r4 != r5) goto L_0x00e4
            r5 = r0
            kotlinx.coroutines.Waiter r5 = (kotlinx.coroutines.Waiter) r5     // Catch:{ all -> 0x0166 }
            boolean r5 = r5 instanceof kotlinx.coroutines.Waiter     // Catch:{ all -> 0x0166 }
            if (r5 == 0) goto L_0x00d8
            r16 = r0
            kotlinx.coroutines.Waiter r16 = (kotlinx.coroutines.Waiter) r16     // Catch:{ all -> 0x0166 }
        L_0x00d8:
            r5 = r16
            if (r5 == 0) goto L_0x00df
            r7.prepareReceiverForSuspension(r5, r8, r9)     // Catch:{ all -> 0x0166 }
        L_0x00df:
            r5 = 0
            r22 = r2
            goto L_0x011e
        L_0x00e4:
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.channels.BufferedChannelKt.FAILED     // Catch:{ all -> 0x0166 }
            if (r4 != r5) goto L_0x0101
            long r22 = r7.getSendersCounter$kotlinx_coroutines_core()     // Catch:{ all -> 0x0166 }
            int r5 = (r10 > r22 ? 1 : (r10 == r22 ? 0 : -1))
            if (r5 >= 0) goto L_0x00f5
            r8.cleanPrev()     // Catch:{ all -> 0x0166 }
        L_0x00f5:
            r9 = r26
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            goto L_0x0074
        L_0x0101:
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND_NO_WAITER     // Catch:{ all -> 0x0166 }
            if (r4 == r5) goto L_0x0121
            r8.cleanPrev()     // Catch:{ all -> 0x0166 }
            r5 = r4
            r12 = 0
            r22 = r2
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r2 = r1.onUndeliveredElement     // Catch:{ all -> 0x0166 }
            if (r2 == 0) goto L_0x0116
            kotlin.reflect.KFunction r16 = r1.bindCancellationFun(r2)     // Catch:{ all -> 0x0166 }
        L_0x0116:
            r2 = r16
            kotlin.jvm.functions.Function3 r2 = (kotlin.jvm.functions.Function3) r2     // Catch:{ all -> 0x0166 }
            r0.resume(r5, r2)     // Catch:{ all -> 0x0166 }
        L_0x011e:
            goto L_0x0154
        L_0x0121:
            r22 = r2
            r2 = 0
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0166 }
            java.lang.String r5 = "unexpected"
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0166 }
            r3.<init>(r5)     // Catch:{ all -> 0x0166 }
            throw r3     // Catch:{ all -> 0x0166 }
        L_0x0130:
            r0 = move-exception
            r20 = r4
            r21 = r5
            goto L_0x0180
        L_0x0136:
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
            r15.cleanPrev()     // Catch:{ all -> 0x0166 }
            r2 = r14
            r3 = 0
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r4 = r1.onUndeliveredElement     // Catch:{ all -> 0x0166 }
            if (r4 == 0) goto L_0x014b
            kotlin.reflect.KFunction r16 = r1.bindCancellationFun(r4)     // Catch:{ all -> 0x0166 }
        L_0x014b:
            r4 = r16
            kotlin.jvm.functions.Function3 r4 = (kotlin.jvm.functions.Function3) r4     // Catch:{ all -> 0x0166 }
            r0.resume(r2, r4)     // Catch:{ all -> 0x0166 }
        L_0x0154:
            java.lang.Object r0 = r21.getResult()
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r2) goto L_0x0164
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r29)
        L_0x0164:
            return r0
        L_0x0166:
            r0 = move-exception
            goto L_0x0180
        L_0x0168:
            r0 = move-exception
            goto L_0x0178
        L_0x016a:
            r0 = move-exception
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
            r15 = r8
            goto L_0x0180
        L_0x0175:
            r0 = move-exception
            r15 = r25
        L_0x0178:
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
        L_0x0180:
            r21.releaseClaimedReusableContinuation$kotlinx_coroutines_core()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.receiveOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment, int, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final void prepareReceiverForSuspension(Waiter $this$prepareReceiverForSuspension, ChannelSegment<E> segment, int index) {
        onReceiveEnqueued();
        $this$prepareReceiverForSuspension.invokeOnCancellation(segment, index);
    }

    /* access modifiers changed from: private */
    public final void onClosedReceiveOnNoWaiterSuspend(CancellableContinuation<? super E> cont) {
        Result.Companion companion = Result.Companion;
        cont.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(getReceiveException())));
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* renamed from: receiveCatching-JP2dKIU$suspendImpl  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ <E> java.lang.Object m1526receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel<E> r14, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = (kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = new kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            r0.<init>(r14, r15)
        L_0x0019:
            r6 = r0
            java.lang.Object r0 = r6.result
            java.lang.Object r7 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            switch(r1) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r0)
            throw r14
        L_0x002d:
            r14 = 0
            r1 = 0
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r0
            kotlinx.coroutines.channels.ChannelResult r2 = (kotlinx.coroutines.channels.ChannelResult) r2
            java.lang.Object r2 = r2.m1552unboximpl()
            goto L_0x00b7
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r0)
            r1 = r14
            r8 = r1
            r13 = 0
            r14 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = receiveSegment$volatile$FU
            java.lang.Object r2 = r2.get(r8)
            kotlinx.coroutines.channels.ChannelSegment r2 = (kotlinx.coroutines.channels.ChannelSegment) r2
        L_0x004c:
            boolean r3 = r8.isClosedForReceive()
            if (r3 == 0) goto L_0x0060
            r2 = 0
            kotlinx.coroutines.channels.ChannelResult$Companion r3 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Throwable r4 = r1.getCloseCause()
            java.lang.Object r1 = r3.m1553closedJP2dKIU(r4)
            goto L_0x00c8
        L_0x0060:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r3 = receivers$volatile$FU
            long r4 = r3.getAndIncrement(r8)
            int r3 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r9 = (long) r3
            long r9 = r4 / r9
            int r3 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r11 = (long) r3
            long r11 = r4 % r11
            int r3 = (int) r11
            long r11 = r2.id
            int r11 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r11 == 0) goto L_0x0083
            kotlinx.coroutines.channels.ChannelSegment r11 = r8.findSegmentReceive(r9, r2)
            if (r11 != 0) goto L_0x0080
            goto L_0x004c
        L_0x0080:
            r2 = r11
            r9 = r2
            goto L_0x0084
        L_0x0083:
            r9 = r2
        L_0x0084:
            r10 = r3
            r11 = r4
            java.lang.Object r2 = r8.updateCellReceive(r9, r10, r11, r13)
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND
            if (r2 == r10) goto L_0x00c9
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.FAILED
            if (r2 != r10) goto L_0x00a4
            long r2 = r8.getSendersCounter$kotlinx_coroutines_core()
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x00a2
            r9.cleanPrev()
        L_0x00a2:
            r2 = r9
            goto L_0x004c
        L_0x00a4:
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND_NO_WAITER
            if (r2 != r8) goto L_0x00ba
            r2 = r9
            r8 = 0
            r9 = 1
            r6.label = r9
            java.lang.Object r2 = r1.m1527receiveCatchingOnNoWaiterSuspendGKJJFZk(r2, r3, r4, r6)
            if (r2 != r7) goto L_0x00b6
            return r7
        L_0x00b6:
            r1 = r8
        L_0x00b7:
            r1 = r2
            goto L_0x00c6
        L_0x00ba:
            r9.cleanPrev()
            r1 = 0
            kotlinx.coroutines.channels.ChannelResult$Companion r3 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Object r3 = r3.m1555successJP2dKIU(r2)
            r1 = r3
        L_0x00c6:
        L_0x00c8:
            return r1
        L_0x00c9:
            r1 = 0
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "unexpected"
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m1526receiveCatchingJP2dKIU$suspendImpl(kotlinx.coroutines.channels.BufferedChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* renamed from: receiveCatchingOnNoWaiterSuspend-GKJJFZk  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object m1527receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment<E> r26, int r27, long r28, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r30) {
        /*
            r25 = this;
            r1 = r30
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.BufferedChannel$receiveCatchingOnNoWaiterSuspend$1
            if (r0 == 0) goto L_0x0018
            r0 = r1
            kotlinx.coroutines.channels.BufferedChannel$receiveCatchingOnNoWaiterSuspend$1 r0 = (kotlinx.coroutines.channels.BufferedChannel$receiveCatchingOnNoWaiterSuspend$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0018
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            r2 = r25
            goto L_0x001f
        L_0x0018:
            kotlinx.coroutines.channels.BufferedChannel$receiveCatchingOnNoWaiterSuspend$1 r0 = new kotlinx.coroutines.channels.BufferedChannel$receiveCatchingOnNoWaiterSuspend$1
            r2 = r25
            r0.<init>(r2, r1)
        L_0x001f:
            r3 = r0
            java.lang.Object r4 = r3.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r3.label
            switch(r5) {
                case 0: goto L_0x0046;
                case 1: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            r0 = 0
            long r5 = r3.J$0
            int r5 = r3.I$0
            java.lang.Object r5 = r3.L$1
            kotlinx.coroutines.channels.ChannelSegment r5 = (kotlinx.coroutines.channels.ChannelSegment) r5
            java.lang.Object r5 = r3.L$0
            kotlinx.coroutines.channels.BufferedChannel r5 = (kotlinx.coroutines.channels.BufferedChannel) r5
            kotlin.ResultKt.throwOnFailure(r4)
            r1 = r4
            goto L_0x01a2
        L_0x0046:
            kotlin.ResultKt.throwOnFailure(r4)
            r5 = r25
            r8 = r27
            r7 = r26
            r9 = r28
            r12 = 0
            r3.L$0 = r5
            r3.L$1 = r7
            r3.I$0 = r8
            r3.J$0 = r9
            r6 = 1
            r3.label = r6
            r6 = r3
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r13 = 0
            kotlin.coroutines.Continuation r11 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r6)
            kotlinx.coroutines.CancellableContinuationImpl r14 = kotlinx.coroutines.CancellableContinuationKt.getOrCreateCancellableContinuation(r11)
            r15 = r14
            r16 = 0
            kotlinx.coroutines.channels.ReceiveCatching r6 = new kotlinx.coroutines.channels.ReceiveCatching     // Catch:{ all -> 0x01aa }
            java.lang.String r11 = "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuationImpl<kotlinx.coroutines.channels.ChannelResult<E of kotlinx.coroutines.channels.BufferedChannel.receiveCatchingOnNoWaiterSuspend_GKJJFZk$lambda$38>>"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r15, r11)     // Catch:{ all -> 0x01aa }
            r6.<init>(r15)     // Catch:{ all -> 0x01aa }
            r17 = r6
            r18 = r5
            r24 = 0
            r11 = r17
            kotlinx.coroutines.Waiter r11 = (kotlinx.coroutines.Waiter) r11     // Catch:{ all -> 0x01aa }
            r6 = r18
            java.lang.Object r11 = r6.updateCellReceive(r7, r8, r9, r11)     // Catch:{ all -> 0x01aa }
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND     // Catch:{ all -> 0x01aa }
            if (r11 != r1) goto L_0x0097
            r1 = r17
            kotlinx.coroutines.Waiter r1 = (kotlinx.coroutines.Waiter) r1     // Catch:{ all -> 0x01aa }
            r6.prepareReceiverForSuspension(r1, r7, r8)     // Catch:{ all -> 0x01aa }
            goto L_0x018b
        L_0x0097:
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.channels.BufferedChannelKt.FAILED     // Catch:{ all -> 0x01aa }
            if (r11 != r1) goto L_0x016a
            long r18 = r6.getSendersCounter$kotlinx_coroutines_core()     // Catch:{ all -> 0x01aa }
            int r1 = (r9 > r18 ? 1 : (r9 == r18 ? 0 : -1))
            if (r1 >= 0) goto L_0x00a8
            r7.cleanPrev()     // Catch:{ all -> 0x01aa }
        L_0x00a8:
            r1 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r7 = receiveSegment$volatile$FU     // Catch:{ all -> 0x01aa }
            java.lang.Object r7 = r7.get(r6)     // Catch:{ all -> 0x01aa }
            kotlinx.coroutines.channels.ChannelSegment r7 = (kotlinx.coroutines.channels.ChannelSegment) r7     // Catch:{ all -> 0x01aa }
        L_0x00b6:
            boolean r9 = r6.isClosedForReceive()     // Catch:{ all -> 0x01aa }
            if (r9 == 0) goto L_0x00c6
            r6 = 0
            r7 = r15
            kotlinx.coroutines.CancellableContinuation r7 = (kotlinx.coroutines.CancellableContinuation) r7     // Catch:{ all -> 0x01aa }
            r5.onClosedReceiveCatchingOnNoWaiterSuspend(r7)     // Catch:{ all -> 0x01aa }
            goto L_0x018b
        L_0x00c6:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r9 = receivers$volatile$FU     // Catch:{ all -> 0x01aa }
            long r21 = r9.getAndIncrement(r6)     // Catch:{ all -> 0x01aa }
            int r9 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE     // Catch:{ all -> 0x01aa }
            long r9 = (long) r9     // Catch:{ all -> 0x01aa }
            long r9 = r21 / r9
            int r11 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE     // Catch:{ all -> 0x01aa }
            r27 = r9
            long r8 = (long) r11     // Catch:{ all -> 0x01aa }
            long r8 = r21 % r8
            int r8 = (int) r8     // Catch:{ all -> 0x01aa }
            long r9 = r7.id     // Catch:{ all -> 0x01aa }
            int r9 = (r9 > r27 ? 1 : (r9 == r27 ? 0 : -1))
            if (r9 == 0) goto L_0x00ee
            r9 = r27
            kotlinx.coroutines.channels.ChannelSegment r11 = r6.findSegmentReceive(r9, r7)     // Catch:{ all -> 0x01aa }
            if (r11 != 0) goto L_0x00ea
            goto L_0x00b6
        L_0x00ea:
            r7 = r11
            r19 = r7
            goto L_0x00f2
        L_0x00ee:
            r9 = r27
            r19 = r7
        L_0x00f2:
            r23 = r17
            kotlinx.coroutines.Waiter r23 = (kotlinx.coroutines.Waiter) r23     // Catch:{ all -> 0x01aa }
            r18 = r6
            r20 = r8
            java.lang.Object r6 = r18.updateCellReceive(r19, r20, r21, r23)     // Catch:{ all -> 0x01aa }
            r8 = r18
            r7 = r19
            r9 = r20
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND     // Catch:{ all -> 0x01aa }
            if (r6 != r10) goto L_0x0122
            r5 = r17
            kotlinx.coroutines.Waiter r5 = (kotlinx.coroutines.Waiter) r5     // Catch:{ all -> 0x01aa }
            boolean r5 = r5 instanceof kotlinx.coroutines.Waiter     // Catch:{ all -> 0x01aa }
            if (r5 == 0) goto L_0x0118
            r5 = r17
            kotlinx.coroutines.Waiter r5 = (kotlinx.coroutines.Waiter) r5     // Catch:{ all -> 0x01aa }
            goto L_0x0119
        L_0x0118:
            r5 = 0
        L_0x0119:
            if (r5 == 0) goto L_0x011f
            r8.prepareReceiverForSuspension(r5, r7, r9)     // Catch:{ all -> 0x01aa }
        L_0x011f:
            r5 = 0
            goto L_0x015a
        L_0x0122:
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.FAILED     // Catch:{ all -> 0x01aa }
            if (r6 != r10) goto L_0x0135
            long r9 = r8.getSendersCounter$kotlinx_coroutines_core()     // Catch:{ all -> 0x01aa }
            int r6 = (r21 > r9 ? 1 : (r21 == r9 ? 0 : -1))
            if (r6 >= 0) goto L_0x0133
            r7.cleanPrev()     // Catch:{ all -> 0x01aa }
        L_0x0133:
            r6 = r8
            goto L_0x00b6
        L_0x0135:
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.SUSPEND_NO_WAITER     // Catch:{ all -> 0x01aa }
            if (r6 == r8) goto L_0x015d
            r7.cleanPrev()     // Catch:{ all -> 0x01aa }
            r7 = 0
            kotlinx.coroutines.channels.ChannelResult$Companion r8 = kotlinx.coroutines.channels.ChannelResult.Companion     // Catch:{ all -> 0x01aa }
            java.lang.Object r8 = r8.m1555successJP2dKIU(r6)     // Catch:{ all -> 0x01aa }
            kotlinx.coroutines.channels.ChannelResult r8 = kotlinx.coroutines.channels.ChannelResult.m1540boximpl(r8)     // Catch:{ all -> 0x01aa }
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r9 = r5.onUndeliveredElement     // Catch:{ all -> 0x01aa }
            if (r9 == 0) goto L_0x0153
            kotlin.reflect.KFunction r9 = r5.bindCancellationFunResult(r9)     // Catch:{ all -> 0x01aa }
            goto L_0x0154
        L_0x0153:
            r9 = 0
        L_0x0154:
            kotlin.jvm.functions.Function3 r9 = (kotlin.jvm.functions.Function3) r9     // Catch:{ all -> 0x01aa }
            r15.resume(r8, r9)     // Catch:{ all -> 0x01aa }
        L_0x015a:
            goto L_0x018b
        L_0x015d:
            r0 = 0
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x01aa }
            java.lang.String r6 = "unexpected"
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x01aa }
            r5.<init>(r6)     // Catch:{ all -> 0x01aa }
            throw r5     // Catch:{ all -> 0x01aa }
        L_0x016a:
            r8 = r6
            r7.cleanPrev()     // Catch:{ all -> 0x01aa }
            r1 = 0
            kotlinx.coroutines.channels.ChannelResult$Companion r6 = kotlinx.coroutines.channels.ChannelResult.Companion     // Catch:{ all -> 0x01aa }
            java.lang.Object r6 = r6.m1555successJP2dKIU(r11)     // Catch:{ all -> 0x01aa }
            kotlinx.coroutines.channels.ChannelResult r6 = kotlinx.coroutines.channels.ChannelResult.m1540boximpl(r6)     // Catch:{ all -> 0x01aa }
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r7 = r5.onUndeliveredElement     // Catch:{ all -> 0x01aa }
            if (r7 == 0) goto L_0x0183
            kotlin.reflect.KFunction r8 = r5.bindCancellationFunResult(r7)     // Catch:{ all -> 0x01aa }
            goto L_0x0184
        L_0x0183:
            r8 = 0
        L_0x0184:
            kotlin.jvm.functions.Function3 r8 = (kotlin.jvm.functions.Function3) r8     // Catch:{ all -> 0x01aa }
            r15.resume(r6, r8)     // Catch:{ all -> 0x01aa }
        L_0x018b:
            java.lang.Object r1 = r14.getResult()
            java.lang.Object r5 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r1 != r5) goto L_0x019e
            r5 = r3
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r5)
        L_0x019e:
            if (r1 != r0) goto L_0x01a1
            return r0
        L_0x01a1:
            r0 = r12
        L_0x01a2:
            kotlinx.coroutines.channels.ChannelResult r1 = (kotlinx.coroutines.channels.ChannelResult) r1
            java.lang.Object r0 = r1.m1552unboximpl()
            return r0
        L_0x01aa:
            r0 = move-exception
            r14.releaseClaimedReusableContinuation$kotlinx_coroutines_core()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m1527receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment, int, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final void onClosedReceiveCatchingOnNoWaiterSuspend(CancellableContinuation<? super ChannelResult<? extends E>> cont) {
        Result.Companion companion = Result.Companion;
        cont.resumeWith(Result.m6constructorimpl(ChannelResult.m1540boximpl(ChannelResult.Companion.m1553closedJP2dKIU(getCloseCause()))));
    }

    /* renamed from: tryReceive-PtdJZtk  reason: not valid java name */
    public Object m1529tryReceivePtdJZtk() {
        long r = receivers$volatile$FU.get(this);
        long sendersAndCloseStatusCur = sendersAndCloseStatus$volatile$FU.get(this);
        if (isClosedForReceive0(sendersAndCloseStatusCur)) {
            return ChannelResult.Companion.m1553closedJP2dKIU(getCloseCause());
        }
        if (r >= (sendersAndCloseStatusCur & 1152921504606846975L)) {
            return ChannelResult.Companion.m1554failurePtdJZtk();
        }
        Symbol access$getINTERRUPTED_RCV$p = BufferedChannelKt.INTERRUPTED_RCV;
        ChannelSegment segment$iv = (ChannelSegment) receiveSegment$volatile$FU.get(this);
        while (!isClosedForReceive()) {
            long r$iv = receivers$volatile$FU.getAndIncrement(this);
            long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            long r$iv2 = r$iv;
            int i$iv = (int) (r$iv2 % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv.id != id$iv) {
                ChannelSegment access$findSegmentReceive = findSegmentReceive(id$iv, segment$iv);
                if (access$findSegmentReceive == null) {
                    continue;
                } else {
                    segment$iv = access$findSegmentReceive;
                }
            }
            long r$iv3 = r$iv2;
            Object updCellResult$iv = updateCellReceive(segment$iv, i$iv, r$iv3, access$getINTERRUPTED_RCV$p);
            long r$iv4 = r$iv3;
            if (updCellResult$iv == BufferedChannelKt.SUSPEND) {
                Waiter waiter = access$getINTERRUPTED_RCV$p instanceof Waiter ? (Waiter) access$getINTERRUPTED_RCV$p : null;
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, segment$iv, i$iv);
                }
                ChannelSegment channelSegment = segment$iv;
                int i = i$iv;
                waitExpandBufferCompletion$kotlinx_coroutines_core(r$iv4);
                segment$iv.onSlotCleaned();
                return ChannelResult.Companion.m1554failurePtdJZtk();
            }
            ChannelSegment segment$iv2 = segment$iv;
            int i2 = i$iv;
            if (updCellResult$iv == BufferedChannelKt.FAILED) {
                if (r$iv4 < getSendersCounter$kotlinx_coroutines_core()) {
                    segment$iv2.cleanPrev();
                }
                segment$iv = segment$iv2;
            } else if (updCellResult$iv != BufferedChannelKt.SUSPEND_NO_WAITER) {
                segment$iv2.cleanPrev();
                return ChannelResult.Companion.m1555successJP2dKIU(updCellResult$iv);
            } else {
                throw new IllegalStateException("unexpected".toString());
            }
        }
        return ChannelResult.Companion.m1553closedJP2dKIU(getCloseCause());
    }

    /* access modifiers changed from: protected */
    public final void dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(long globalCellIndex) {
        ChannelSegment segment;
        UndeliveredElementException it;
        if (!DebugKt.getASSERTIONS_ENABLED() || isConflatedDropOldest() != 0) {
            ChannelSegment segment2 = (ChannelSegment) receiveSegment$volatile$FU.get(this);
            while (true) {
                long r = receivers$volatile$FU.get(this);
                if (globalCellIndex >= Math.max(((long) this.capacity) + r, getBufferEndCounter())) {
                    if (receivers$volatile$FU.compareAndSet(this, r, 1 + r)) {
                        long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
                        int i = (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE));
                        if (segment2.id != id) {
                            segment = findSegmentReceive(id, segment2);
                            if (segment == null) {
                                continue;
                            }
                        } else {
                            segment = segment2;
                        }
                        long r2 = r;
                        Object updCellResult = updateCellReceive(segment, i, r2, (Object) null);
                        long r3 = r2;
                        if (updCellResult != BufferedChannelKt.FAILED) {
                            segment.cleanPrev();
                            Function1<E, Unit> function1 = this.onUndeliveredElement;
                            if (!(function1 == null || (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, updCellResult, (UndeliveredElementException) null, 2, (Object) null)) == null)) {
                                throw it;
                            }
                        } else if (r3 < getSendersCounter$kotlinx_coroutines_core()) {
                            segment.cleanPrev();
                        }
                        segment2 = segment;
                    }
                } else {
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    static /* synthetic */ Object receiveImpl$default(BufferedChannel $this, Object waiter, Function1 onElementRetrieved, Function3 onSuspend, Function0 onClosed, Function3 onNoWaiterSuspend, int i, Object obj) {
        Function3 onNoWaiterSuspend2;
        if (obj == null) {
            if ((i & 16) != 0) {
                onNoWaiterSuspend2 = BufferedChannel$receiveImpl$1.INSTANCE;
            } else {
                onNoWaiterSuspend2 = onNoWaiterSuspend;
            }
            ChannelSegment segment = (ChannelSegment) receiveSegment$volatile$FU.get($this);
            while (!$this.isClosedForReceive()) {
                long r = receivers$volatile$FU.getAndIncrement($this);
                long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i2 = (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (segment.id != id) {
                    ChannelSegment access$findSegmentReceive = $this.findSegmentReceive(id, segment);
                    if (access$findSegmentReceive == null) {
                        continue;
                    } else {
                        segment = access$findSegmentReceive;
                    }
                }
                Object updCellResult = $this.updateCellReceive(segment, i2, r, waiter);
                if (updCellResult == BufferedChannelKt.SUSPEND) {
                    Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                    if (waiter2 != null) {
                        $this.prepareReceiverForSuspension(waiter2, segment, i2);
                    }
                    Function1 function1 = onElementRetrieved;
                    return onSuspend.invoke(segment, Integer.valueOf(i2), Long.valueOf(r));
                }
                Function3 function3 = onSuspend;
                if (updCellResult == BufferedChannelKt.FAILED) {
                    if (r < $this.getSendersCounter$kotlinx_coroutines_core()) {
                        segment.cleanPrev();
                    }
                } else if (updCellResult == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    Function1 function12 = onElementRetrieved;
                    return onNoWaiterSuspend2.invoke(segment, Integer.valueOf(i2), Long.valueOf(r));
                } else {
                    segment.cleanPrev();
                    return onElementRetrieved.invoke(updCellResult);
                }
            }
            return onClosed.invoke();
        }
        Function1 function13 = onElementRetrieved;
        Function3 function32 = onSuspend;
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: receiveImpl");
    }

    private final <R> R receiveImpl(Object waiter, Function1<? super E, ? extends R> onElementRetrieved, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onSuspend, Function0<? extends R> onClosed, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onNoWaiterSuspend) {
        ChannelSegment segment = (ChannelSegment) receiveSegment$volatile$FU.get(this);
        while (!isClosedForReceive()) {
            long r = receivers$volatile$FU.getAndIncrement(this);
            long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i = (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id != id) {
                ChannelSegment access$findSegmentReceive = findSegmentReceive(id, segment);
                if (access$findSegmentReceive == null) {
                    continue;
                } else {
                    segment = access$findSegmentReceive;
                }
            }
            Object updCellResult = updateCellReceive(segment, i, r, waiter);
            if (updCellResult == BufferedChannelKt.SUSPEND) {
                Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                if (waiter2 != null) {
                    prepareReceiverForSuspension(waiter2, segment, i);
                }
                Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> function3 = onNoWaiterSuspend;
                Function1<? super E, ? extends R> function1 = onElementRetrieved;
                return onSuspend.invoke(segment, Integer.valueOf(i), Long.valueOf(r));
            }
            Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> function32 = onSuspend;
            if (updCellResult == BufferedChannelKt.FAILED) {
                if (r < getSendersCounter$kotlinx_coroutines_core()) {
                    segment.cleanPrev();
                }
            } else if (updCellResult == BufferedChannelKt.SUSPEND_NO_WAITER) {
                Function1<? super E, ? extends R> function12 = onElementRetrieved;
                return onNoWaiterSuspend.invoke(segment, Integer.valueOf(i), Long.valueOf(r));
            } else {
                Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> function33 = onNoWaiterSuspend;
                segment.cleanPrev();
                return onElementRetrieved.invoke(updCellResult);
            }
        }
        return onClosed.invoke();
    }

    private final void receiveImplOnNoWaiter(ChannelSegment<E> segment, int index, long r, Waiter waiter, Function1<? super E, Unit> onElementRetrieved, Function0<Unit> onClosed) {
        Waiter waiter2 = waiter;
        Function1<? super E, Unit> function1 = onElementRetrieved;
        Object updCellResult = updateCellReceive(segment, index, r, waiter);
        if (updCellResult == BufferedChannelKt.SUSPEND) {
            prepareReceiverForSuspension(waiter2, segment, index);
            return;
        }
        ChannelSegment<E> channelSegment = segment;
        int i = index;
        if (updCellResult == BufferedChannelKt.FAILED) {
            if (r < getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
            ChannelSegment segment$iv = (ChannelSegment) receiveSegment$volatile$FU.get(this);
            while (!isClosedForReceive()) {
                long r$iv = receivers$volatile$FU.getAndIncrement(this);
                long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                if (segment$iv.id != id$iv) {
                    ChannelSegment access$findSegmentReceive = findSegmentReceive(id$iv, segment$iv);
                    if (access$findSegmentReceive == null) {
                        continue;
                    } else {
                        segment$iv = access$findSegmentReceive;
                    }
                }
                Object updCellResult$iv = updateCellReceive(segment$iv, i$iv, r$iv, waiter2);
                if (updCellResult$iv == BufferedChannelKt.SUSPEND) {
                    Waiter waiter3 = waiter2 instanceof Waiter ? waiter2 : null;
                    if (waiter3 != null) {
                        prepareReceiverForSuspension(waiter3, segment$iv, i$iv);
                    }
                    Unit unit = Unit.INSTANCE;
                    return;
                } else if (updCellResult$iv == BufferedChannelKt.FAILED) {
                    if (r$iv < getSendersCounter$kotlinx_coroutines_core()) {
                        segment$iv.cleanPrev();
                    }
                } else if (updCellResult$iv != BufferedChannelKt.SUSPEND_NO_WAITER) {
                    segment$iv.cleanPrev();
                    function1.invoke(updCellResult$iv);
                    return;
                } else {
                    ChannelSegment channelSegment2 = segment$iv;
                    throw new IllegalStateException("unexpected".toString());
                }
            }
            onClosed.invoke();
            return;
        }
        channelSegment.cleanPrev();
        function1.invoke(updCellResult);
    }

    /* access modifiers changed from: private */
    public final Object updateCellReceive(ChannelSegment<E> segment, int index, long r, Object waiter) {
        Object state = segment.getState$kotlinx_coroutines_core(index);
        if (state == null) {
            if (r >= (sendersAndCloseStatus$volatile$FU.get(this) & 1152921504606846975L)) {
                if (waiter == null) {
                    return BufferedChannelKt.SUSPEND_NO_WAITER;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state, waiter)) {
                    expandBuffer();
                    return BufferedChannelKt.SUSPEND;
                }
            }
        } else if (state == BufferedChannelKt.BUFFERED && segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.DONE_RCV)) {
            expandBuffer();
            return segment.retrieveElement$kotlinx_coroutines_core(index);
        }
        return updateCellReceiveSlow(segment, index, r, waiter);
    }

    private final Object updateCellReceiveSlow(ChannelSegment<E> segment, int index, long r, Object waiter) {
        while (true) {
            Object state = segment.getState$kotlinx_coroutines_core(index);
            if (state == null || state == BufferedChannelKt.IN_BUFFER) {
                if (r < (sendersAndCloseStatus$volatile$FU.get(this) & 1152921504606846975L)) {
                    if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.POISONED)) {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                } else if (waiter == null) {
                    return BufferedChannelKt.SUSPEND_NO_WAITER;
                } else {
                    if (segment.casState$kotlinx_coroutines_core(index, state, waiter)) {
                        expandBuffer();
                        return BufferedChannelKt.SUSPEND;
                    }
                }
            } else if (state == BufferedChannelKt.BUFFERED) {
                if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.DONE_RCV)) {
                    expandBuffer();
                    return segment.retrieveElement$kotlinx_coroutines_core(index);
                }
            } else if (state == BufferedChannelKt.INTERRUPTED_SEND) {
                return BufferedChannelKt.FAILED;
            } else {
                if (state == BufferedChannelKt.POISONED) {
                    return BufferedChannelKt.FAILED;
                }
                if (state == BufferedChannelKt.getCHANNEL_CLOSED()) {
                    expandBuffer();
                    return BufferedChannelKt.FAILED;
                } else if (state != BufferedChannelKt.RESUMING_BY_EB && segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.RESUMING_BY_RCV)) {
                    boolean helpExpandBuffer = state instanceof WaiterEB;
                    if (tryResumeSender(state instanceof WaiterEB ? ((WaiterEB) state).waiter : state, segment, index)) {
                        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                        expandBuffer();
                        return segment.retrieveElement$kotlinx_coroutines_core(index);
                    }
                    segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                    segment.onCancelledRequest(index, false);
                    if (helpExpandBuffer) {
                        expandBuffer();
                    }
                    return BufferedChannelKt.FAILED;
                }
            }
        }
    }

    private final boolean tryResumeSender(Object $this$tryResumeSender, ChannelSegment<E> segment, int index) {
        if ($this$tryResumeSender instanceof CancellableContinuation) {
            Intrinsics.checkNotNull($this$tryResumeSender, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<kotlin.Unit>");
            CancellableContinuation cancellableContinuation = (CancellableContinuation) $this$tryResumeSender;
            return BufferedChannelKt.tryResume0$default((CancellableContinuation) $this$tryResumeSender, Unit.INSTANCE, (Function3) null, 2, (Object) null);
        } else if ($this$tryResumeSender instanceof SelectInstance) {
            Intrinsics.checkNotNull($this$tryResumeSender, "null cannot be cast to non-null type kotlinx.coroutines.selects.SelectImplementation<*>");
            SelectImplementation selectImplementation = (SelectImplementation) $this$tryResumeSender;
            TrySelectDetailedResult trySelectResult = ((SelectImplementation) $this$tryResumeSender).trySelectDetailed(this, Unit.INSTANCE);
            if (trySelectResult == TrySelectDetailedResult.REREGISTER) {
                segment.cleanElement$kotlinx_coroutines_core(index);
            }
            return trySelectResult == TrySelectDetailedResult.SUCCESSFUL;
        } else if ($this$tryResumeSender instanceof SendBroadcast) {
            return BufferedChannelKt.tryResume0$default(((SendBroadcast) $this$tryResumeSender).getCont(), true, (Function3) null, 2, (Object) null);
        } else {
            throw new IllegalStateException(("Unexpected waiter: " + $this$tryResumeSender).toString());
        }
    }

    private final void expandBuffer() {
        if (!isRendezvousOrUnlimited()) {
            ChannelSegment segment = (ChannelSegment) bufferEndSegment$volatile$FU.get(this);
            while (true) {
                long b = bufferEnd$volatile$FU.getAndIncrement(this);
                long id = b / ((long) BufferedChannelKt.SEGMENT_SIZE);
                if (getSendersCounter$kotlinx_coroutines_core() <= b) {
                    if (segment.id < id && segment.getNext() != null) {
                        moveSegmentBufferEndToSpecifiedOrLast(id, segment);
                    }
                    incCompletedExpandBufferAttempts$default(this, 0, 1, (Object) null);
                    return;
                }
                if (segment.id != id) {
                    ChannelSegment findSegmentBufferEnd = findSegmentBufferEnd(id, segment, b);
                    if (findSegmentBufferEnd == null) {
                        continue;
                    } else {
                        segment = findSegmentBufferEnd;
                    }
                }
                if (updateCellExpandBuffer(segment, (int) (b % ((long) BufferedChannelKt.SEGMENT_SIZE)), b)) {
                    incCompletedExpandBufferAttempts$default(this, 0, 1, (Object) null);
                    return;
                }
                incCompletedExpandBufferAttempts$default(this, 0, 1, (Object) null);
            }
        }
    }

    private final boolean updateCellExpandBuffer(ChannelSegment<E> segment, int index, long b) {
        Object state = segment.getState$kotlinx_coroutines_core(index);
        if (!(state instanceof Waiter) || b < receivers$volatile$FU.get(this) || !segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.RESUMING_BY_EB)) {
            return updateCellExpandBufferSlow(segment, index, b);
        }
        if (tryResumeSender(state, segment, index)) {
            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
            return true;
        }
        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
        segment.onCancelledRequest(index, false);
        return false;
    }

    private final boolean updateCellExpandBufferSlow(ChannelSegment<E> segment, int index, long b) {
        while (true) {
            Object state = segment.getState$kotlinx_coroutines_core(index);
            if (state instanceof Waiter) {
                if (b < receivers$volatile$FU.get(this)) {
                    if (segment.casState$kotlinx_coroutines_core(index, state, new WaiterEB((Waiter) state))) {
                        return true;
                    }
                } else if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.RESUMING_BY_EB)) {
                    if (tryResumeSender(state, segment, index)) {
                        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
                        return true;
                    }
                    segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                    segment.onCancelledRequest(index, false);
                    return false;
                }
            } else if (state == BufferedChannelKt.INTERRUPTED_SEND) {
                return false;
            } else {
                if (state == null) {
                    if (segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.IN_BUFFER)) {
                        return true;
                    }
                } else if (state == BufferedChannelKt.BUFFERED || state == BufferedChannelKt.POISONED || state == BufferedChannelKt.DONE_RCV || state == BufferedChannelKt.INTERRUPTED_RCV || state == BufferedChannelKt.getCHANNEL_CLOSED()) {
                    return true;
                } else {
                    if (state != BufferedChannelKt.RESUMING_BY_RCV) {
                        throw new IllegalStateException(("Unexpected cell state: " + state).toString());
                    }
                }
            }
        }
        return true;
    }

    static /* synthetic */ void incCompletedExpandBufferAttempts$default(BufferedChannel bufferedChannel, long j, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                j = 1;
            }
            bufferedChannel.incCompletedExpandBufferAttempts(j);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incCompletedExpandBufferAttempts");
    }

    private final void incCompletedExpandBufferAttempts(long nAttempts) {
        boolean z;
        if ((completedExpandBuffersAndPauseFlag$volatile$FU.addAndGet(this, nAttempts) & Longs.MAX_POWER_OF_TWO) != 0) {
            do {
                if ((completedExpandBuffersAndPauseFlag$volatile$FU.get(this) & Longs.MAX_POWER_OF_TWO) != 0) {
                    z = true;
                    continue;
                } else {
                    z = false;
                    continue;
                }
            } while (z);
        }
    }

    public final void waitExpandBufferCompletion$kotlinx_coroutines_core(long globalIndex) {
        BufferedChannel bufferedChannel;
        BufferedChannel bufferedChannel2 = this;
        if (!bufferedChannel2.isRendezvousOrUnlimited()) {
            while (bufferedChannel2.getBufferEndCounter() <= globalIndex) {
                bufferedChannel2 = this;
            }
            int access$getEXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS$p = BufferedChannelKt.EXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS;
            int i = 0;
            while (true) {
                long j = DurationKt.MAX_MILLIS;
                if (i < access$getEXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS$p) {
                    int i2 = i;
                    long b = bufferedChannel2.getBufferEndCounter();
                    if (b != (DurationKt.MAX_MILLIS & completedExpandBuffersAndPauseFlag$volatile$FU.get(bufferedChannel2)) || b != bufferedChannel2.getBufferEndCounter()) {
                        i++;
                    } else {
                        return;
                    }
                } else {
                    AtomicLongFieldUpdater handler$atomicfu$iv = completedExpandBuffersAndPauseFlag$volatile$FU;
                    while (true) {
                        long it = handler$atomicfu$iv.get(bufferedChannel);
                        if (handler$atomicfu$iv.compareAndSet(bufferedChannel, it, BufferedChannelKt.constructEBCompletedAndPauseFlag(it & DurationKt.MAX_MILLIS, true))) {
                            break;
                        }
                        bufferedChannel2 = this;
                    }
                    while (true) {
                        long b2 = bufferedChannel.getBufferEndCounter();
                        long ebCompletedAndBit = completedExpandBuffersAndPauseFlag$volatile$FU.get(bufferedChannel);
                        long ebCompleted = ebCompletedAndBit & j;
                        int pauseExpandBuffers = (Longs.MAX_POWER_OF_TWO & ebCompletedAndBit) != 0 ? 1 : 0;
                        if (b2 == ebCompleted && b2 == bufferedChannel.getBufferEndCounter()) {
                            break;
                        }
                        long j2 = j;
                        if (pauseExpandBuffers == 0) {
                            bufferedChannel = this;
                            completedExpandBuffersAndPauseFlag$volatile$FU.compareAndSet(bufferedChannel, ebCompletedAndBit, BufferedChannelKt.constructEBCompletedAndPauseFlag(ebCompleted, true));
                            j = j2;
                        } else {
                            bufferedChannel = this;
                            j = j2;
                        }
                    }
                    AtomicLongFieldUpdater handler$atomicfu$iv2 = completedExpandBuffersAndPauseFlag$volatile$FU;
                    while (true) {
                        long it2 = handler$atomicfu$iv2.get(bufferedChannel);
                        long j3 = j;
                        if (!handler$atomicfu$iv2.compareAndSet(bufferedChannel, it2, BufferedChannelKt.constructEBCompletedAndPauseFlag(it2 & j3, false))) {
                            bufferedChannel = this;
                            j = j3;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public SelectClause2<E, BufferedChannel<E>> getOnSend() {
        BufferedChannel$onSend$1 bufferedChannel$onSend$1 = BufferedChannel$onSend$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        BufferedChannel$onSend$2 bufferedChannel$onSend$2 = BufferedChannel$onSend$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$1, 3);
        return new SelectClause2Impl<>(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$2, 3), (Function3) null, 8, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: protected */
    public void registerSelectForSend(SelectInstance<?> select, Object element) {
        SelectInstance<?> selectInstance = select;
        Object obj = element;
        Object element$iv = element;
        ChannelSegment segment$iv = (ChannelSegment) sendSegment$volatile$FU.get(this);
        while (true) {
            long sendersAndCloseStatusCur$iv = sendersAndCloseStatus$volatile$FU.getAndIncrement(this);
            long s$iv = sendersAndCloseStatusCur$iv & 1152921504606846975L;
            boolean closed$iv = isClosedForSend0(sendersAndCloseStatusCur$iv);
            long id$iv = s$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (s$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv.id != id$iv) {
                ChannelSegment access$findSegmentSend = findSegmentSend(id$iv, segment$iv);
                if (access$findSegmentSend != null) {
                    segment$iv = access$findSegmentSend;
                } else if (closed$iv) {
                    onClosedSelectOnSend(obj, selectInstance);
                    return;
                }
            }
            switch (updateCellSend(segment$iv, i$iv, element$iv, s$iv, selectInstance, closed$iv)) {
                case 0:
                    segment$iv.cleanPrev();
                    selectInstance.selectInRegistrationPhase(Unit.INSTANCE);
                    return;
                case 1:
                    selectInstance.selectInRegistrationPhase(Unit.INSTANCE);
                    return;
                case 2:
                    if (closed$iv) {
                        segment$iv.onSlotCleaned();
                        onClosedSelectOnSend(obj, selectInstance);
                        return;
                    }
                    Waiter waiter = selectInstance instanceof Waiter ? (Waiter) selectInstance : null;
                    if (waiter != null) {
                        prepareSenderForSuspension(waiter, segment$iv, i$iv);
                    }
                    return;
                case 3:
                    Object obj2 = element$iv;
                    throw new IllegalStateException("unexpected".toString());
                case 4:
                    if (s$iv < getReceiversCounter$kotlinx_coroutines_core()) {
                        segment$iv.cleanPrev();
                    }
                    onClosedSelectOnSend(obj, selectInstance);
                    return;
                case 5:
                    segment$iv.cleanPrev();
                    break;
            }
            element$iv = element$iv;
        }
    }

    private final void onClosedSelectOnSend(E element, SelectInstance<?> select) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, select.getContext());
        }
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    /* access modifiers changed from: private */
    public final Object processResultSelectSend(Object ignoredParam, Object selectResult) {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return this;
        }
        throw getSendException();
    }

    public SelectClause1<E> getOnReceive() {
        BufferedChannel$onReceive$1 bufferedChannel$onReceive$1 = BufferedChannel$onReceive$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        BufferedChannel$onReceive$2 bufferedChannel$onReceive$2 = BufferedChannel$onReceive$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause1Impl<>(this, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$1, 3), (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    public SelectClause1<ChannelResult<E>> getOnReceiveCatching() {
        BufferedChannel$onReceiveCatching$1 bufferedChannel$onReceiveCatching$1 = BufferedChannel$onReceiveCatching$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        BufferedChannel$onReceiveCatching$2 bufferedChannel$onReceiveCatching$2 = BufferedChannel$onReceiveCatching$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause1Impl<>(this, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$1, 3), (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    public SelectClause1<E> getOnReceiveOrNull() {
        BufferedChannel$onReceiveOrNull$1 bufferedChannel$onReceiveOrNull$1 = BufferedChannel$onReceiveOrNull$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        BufferedChannel$onReceiveOrNull$2 bufferedChannel$onReceiveOrNull$2 = BufferedChannel$onReceiveOrNull$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause1Impl<>(this, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$1, 3), (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    /* access modifiers changed from: private */
    public final void registerSelectForReceive(SelectInstance<?> select, Object ignoredParam) {
        ChannelSegment segment$iv;
        ChannelSegment segment$iv2 = (ChannelSegment) receiveSegment$volatile$FU.get(this);
        while (!isClosedForReceive()) {
            long r$iv = receivers$volatile$FU.getAndIncrement(this);
            long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
            int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment$iv2.id != id$iv) {
                ChannelSegment segment$iv3 = findSegmentReceive(id$iv, segment$iv2);
                if (segment$iv3 == null) {
                    continue;
                } else {
                    segment$iv = segment$iv3;
                }
            } else {
                segment$iv = segment$iv2;
            }
            SelectInstance<?> selectInstance = select;
            Object updCellResult$iv = updateCellReceive(segment$iv, i$iv, r$iv, selectInstance);
            if (updCellResult$iv == BufferedChannelKt.SUSPEND) {
                Waiter waiter = selectInstance instanceof Waiter ? (Waiter) selectInstance : null;
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, segment$iv, i$iv);
                }
                return;
            } else if (updCellResult$iv == BufferedChannelKt.FAILED) {
                if (r$iv < getSendersCounter$kotlinx_coroutines_core()) {
                    segment$iv.cleanPrev();
                }
                segment$iv2 = segment$iv;
                select = selectInstance;
            } else if (updCellResult$iv != BufferedChannelKt.SUSPEND_NO_WAITER) {
                segment$iv.cleanPrev();
                selectInstance.selectInRegistrationPhase(updCellResult$iv);
                return;
            } else {
                throw new IllegalStateException("unexpected".toString());
            }
        }
        onClosedSelectOnReceive(select);
        SelectInstance<?> selectInstance2 = select;
    }

    private final void onClosedSelectOnReceive(SelectInstance<?> select) {
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    /* access modifiers changed from: private */
    public final Object processResultSelectReceive(Object ignoredParam, Object selectResult) {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return selectResult;
        }
        throw getReceiveException();
    }

    /* access modifiers changed from: private */
    public final Object processResultSelectReceiveOrNull(Object ignoredParam, Object selectResult) {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return selectResult;
        }
        if (getCloseCause() == null) {
            return null;
        }
        throw getReceiveException();
    }

    /* access modifiers changed from: private */
    public final Object processResultSelectReceiveCatching(Object ignoredParam, Object selectResult) {
        Object obj;
        if (selectResult == BufferedChannelKt.getCHANNEL_CLOSED()) {
            obj = ChannelResult.Companion.m1553closedJP2dKIU(getCloseCause());
        } else {
            obj = ChannelResult.Companion.m1555successJP2dKIU(selectResult);
        }
        return ChannelResult.m1540boximpl(obj);
    }

    /* access modifiers changed from: private */
    public static final Function3 onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56(BufferedChannel this$0, SelectInstance select, Object obj, Object element) {
        return new BufferedChannel$$ExternalSyntheticLambda0(element, this$0, select);
    }

    /* access modifiers changed from: private */
    public static final Unit onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56$lambda$55(Object $element, BufferedChannel this$0, SelectInstance $select, Throwable th, Object obj, CoroutineContext coroutineContext) {
        if ($element != BufferedChannelKt.getCHANNEL_CLOSED()) {
            OnUndeliveredElementKt.callUndeliveredElement(this$0.onUndeliveredElement, $element, $select.getContext());
        }
        return Unit.INSTANCE;
    }

    public ChannelIterator<E> iterator() {
        return new BufferedChannelIterator();
    }

    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000e\u0010\n\u001a\u00020\tHB¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\tH\u0002J,\u0010\r\u001a\u00020\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H@¢\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\u00162\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u00172\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\u000e\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001aJ\u0013\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00028\u0000¢\u0006\u0002\u0010\u001dJ\u0006\u0010\u001e\u001a\u00020\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;", "Lkotlinx/coroutines/channels/ChannelIterator;", "Lkotlinx/coroutines/Waiter;", "<init>", "(Lkotlinx/coroutines/channels/BufferedChannel;)V", "receiveResult", "", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "", "hasNext", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onClosedHasNext", "hasNextOnNoWaiterSuspend", "segment", "Lkotlinx/coroutines/channels/ChannelSegment;", "index", "", "r", "", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeOnCancellation", "", "Lkotlinx/coroutines/internal/Segment;", "onClosedHasNextNoWaiterSuspend", "next", "()Ljava/lang/Object;", "tryResumeHasNext", "element", "(Ljava/lang/Object;)Z", "tryResumeHasNextOnClosedChannel", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: BufferedChannel.kt */
    private final class BufferedChannelIterator implements ChannelIterator<E>, Waiter {
        /* access modifiers changed from: private */
        public CancellableContinuationImpl<? super Boolean> continuation;
        /* access modifiers changed from: private */
        public Object receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;

        public BufferedChannelIterator() {
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        public /* synthetic */ Object next(Continuation $completion) {
            return ChannelIterator.DefaultImpls.next(this, $completion);
        }

        public Object hasNext(Continuation<? super Boolean> $completion) {
            ChannelSegment segment$iv;
            boolean z = true;
            if (this.receiveResult == BufferedChannelKt.NO_RECEIVE_RESULT || this.receiveResult == BufferedChannelKt.getCHANNEL_CLOSED()) {
                BufferedChannel this_$iv = BufferedChannel.this;
                ChannelSegment segment$iv2 = (ChannelSegment) BufferedChannel.receiveSegment$volatile$FU.get(this_$iv);
                while (true) {
                    if (this_$iv.isClosedForReceive()) {
                        z = onClosedHasNext();
                        break;
                    }
                    long r$iv = BufferedChannel.receivers$volatile$FU.getAndIncrement(this_$iv);
                    long id$iv = r$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                    int i$iv = (int) (r$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                    if (segment$iv2.id != id$iv) {
                        segment$iv = this_$iv.findSegmentReceive(id$iv, segment$iv2);
                        if (segment$iv == null) {
                            continue;
                        }
                    } else {
                        segment$iv = segment$iv2;
                    }
                    int i$iv2 = i$iv;
                    Object updCellResult$iv = this_$iv.updateCellReceive(segment$iv, i$iv2, r$iv, (Object) null);
                    long r$iv2 = r$iv;
                    BufferedChannel this_$iv2 = this_$iv;
                    int i$iv3 = i$iv2;
                    ChannelSegment segment$iv3 = segment$iv;
                    if (updCellResult$iv == BufferedChannelKt.SUSPEND) {
                        throw new IllegalStateException("unreachable".toString());
                    } else if (updCellResult$iv == BufferedChannelKt.FAILED) {
                        if (r$iv2 < this_$iv2.getSendersCounter$kotlinx_coroutines_core()) {
                            segment$iv3.cleanPrev();
                        }
                        segment$iv2 = segment$iv3;
                        this_$iv = this_$iv2;
                    } else if (updCellResult$iv == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return hasNextOnNoWaiterSuspend(segment$iv3, i$iv3, r$iv2, $completion);
                    } else {
                        segment$iv3.cleanPrev();
                        this.receiveResult = updCellResult$iv;
                    }
                }
            }
            return Boxing.boxBoolean(z);
        }

        private final boolean onClosedHasNext() {
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable cause = BufferedChannel.this.getCloseCause();
            if (cause == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(cause);
        }

        /* access modifiers changed from: private */
        public final Object hasNextOnNoWaiterSuspend(ChannelSegment<E> segment, int index, long r, Continuation<? super Boolean> $completion) {
            BufferedChannel bufferedChannel = BufferedChannel.this;
            boolean z = false;
            Continuation<? super Boolean> continuation2 = $completion;
            CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation2));
            CancellableContinuationImpl cont = orCreateCancellableContinuation;
            try {
                this.continuation = cont;
                BufferedChannel this_$iv = bufferedChannel;
                Object updCellResult$iv = this_$iv.updateCellReceive(segment, index, r, this);
                if (updCellResult$iv == BufferedChannelKt.SUSPEND) {
                    try {
                    } catch (Throwable th) {
                        e$iv = th;
                        ChannelSegment<E> channelSegment = segment;
                        int i = index;
                        boolean z2 = z;
                        Continuation<? super Boolean> continuation3 = continuation2;
                        orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                        throw e$iv;
                    }
                    try {
                        this_$iv.prepareReceiverForSuspension(this, segment, index);
                        Continuation<? super Boolean> continuation4 = continuation2;
                    } catch (Throwable th2) {
                        e$iv = th2;
                        boolean z22 = z;
                        Continuation<? super Boolean> continuation32 = continuation2;
                        orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                        throw e$iv;
                    }
                } else {
                    ChannelSegment<E> channelSegment2 = segment;
                    int i2 = index;
                    if (updCellResult$iv == BufferedChannelKt.FAILED) {
                        if (r < this_$iv.getSendersCounter$kotlinx_coroutines_core()) {
                            channelSegment2.cleanPrev();
                        }
                        ChannelSegment segment$iv$iv = (ChannelSegment) BufferedChannel.receiveSegment$volatile$FU.get(this_$iv);
                        while (!this_$iv.isClosedForReceive()) {
                            long r$iv$iv = BufferedChannel.receivers$volatile$FU.getAndIncrement(this_$iv);
                            long id$iv$iv = r$iv$iv / ((long) BufferedChannelKt.SEGMENT_SIZE);
                            boolean z3 = z;
                            try {
                                Continuation<? super Boolean> continuation5 = continuation2;
                                int i$iv$iv = (int) (r$iv$iv % ((long) BufferedChannelKt.SEGMENT_SIZE));
                                if (segment$iv$iv.id != id$iv$iv) {
                                    ChannelSegment access$findSegmentReceive = this_$iv.findSegmentReceive(id$iv$iv, segment$iv$iv);
                                    if (access$findSegmentReceive == null) {
                                        z = z3;
                                        continuation2 = continuation5;
                                    } else {
                                        segment$iv$iv = access$findSegmentReceive;
                                    }
                                }
                                ChannelSegment segment$iv$iv2 = segment$iv$iv;
                                long r$iv$iv2 = r$iv$iv;
                                int i$iv$iv2 = i$iv$iv;
                                long r$iv$iv3 = id$iv$iv;
                                Object updCellResult$iv$iv = this_$iv.updateCellReceive(segment$iv$iv2, i$iv$iv2, r$iv$iv2, this);
                                if (updCellResult$iv$iv == BufferedChannelKt.SUSPEND) {
                                    Waiter waiter = this instanceof Waiter ? this : null;
                                    if (waiter != null) {
                                        this_$iv.prepareReceiverForSuspension(waiter, segment$iv$iv2, i$iv$iv2);
                                    }
                                    Object obj = updCellResult$iv$iv;
                                } else if (updCellResult$iv$iv == BufferedChannelKt.FAILED) {
                                    if (r$iv$iv2 < this_$iv.getSendersCounter$kotlinx_coroutines_core()) {
                                        segment$iv$iv2.cleanPrev();
                                    }
                                    int i$iv$iv3 = index;
                                    segment$iv$iv = segment$iv$iv2;
                                    z = z3;
                                    continuation2 = continuation5;
                                    ChannelSegment segment$iv$iv3 = segment;
                                } else if (updCellResult$iv$iv != BufferedChannelKt.SUSPEND_NO_WAITER) {
                                    segment$iv$iv2.cleanPrev();
                                    Object element = updCellResult$iv$iv;
                                    this.receiveResult = element;
                                    this.continuation = null;
                                    Boolean boxBoolean = Boxing.boxBoolean(true);
                                    Object obj2 = updCellResult$iv$iv;
                                    Function1<E, Unit> function1 = bufferedChannel.onUndeliveredElement;
                                    cont.resume(boxBoolean, function1 != null ? bufferedChannel.bindCancellationFun(function1, element) : null);
                                } else {
                                    Object obj3 = updCellResult$iv$iv;
                                    throw new IllegalStateException("unexpected".toString());
                                }
                            } catch (Throwable th3) {
                                e$iv = th3;
                                orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                                throw e$iv;
                            }
                        }
                        onClosedHasNextNoWaiterSuspend();
                        boolean z4 = z;
                        Continuation<? super Boolean> continuation6 = continuation2;
                    } else {
                        Continuation<? super Boolean> continuation7 = continuation2;
                        segment.cleanPrev();
                        Object element2 = updCellResult$iv;
                        this.receiveResult = element2;
                        this.continuation = null;
                        Boolean boxBoolean2 = Boxing.boxBoolean(true);
                        Function1<E, Unit> function12 = bufferedChannel.onUndeliveredElement;
                        cont.resume(boxBoolean2, function12 != null ? bufferedChannel.bindCancellationFun(function12, element2) : null);
                    }
                }
                Object result = orCreateCancellableContinuation.getResult();
                if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended($completion);
                }
                return result;
            } catch (Throwable th4) {
                e$iv = th4;
                boolean z5 = z;
                Continuation<? super Boolean> continuation8 = continuation2;
                orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                throw e$iv;
            }
        }

        public void invokeOnCancellation(Segment<?> segment, int index) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.invokeOnCancellation(segment, index);
            }
        }

        /* access modifiers changed from: private */
        public final void onClosedHasNextNoWaiterSuspend() {
            Throwable th;
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable cause = BufferedChannel.this.getCloseCause();
            if (cause == null) {
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m6constructorimpl(false));
                return;
            }
            Continuation continuation2 = cancellableContinuationImpl;
            if (!DebugKt.getRECOVER_STACK_TRACES() || !(cancellableContinuationImpl instanceof CoroutineStackFrame)) {
                th = cause;
            } else {
                th = StackTraceRecoveryKt.recoverFromStackFrame(cause, cancellableContinuationImpl);
            }
            Result.Companion companion2 = Result.Companion;
            continuation2.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(th)));
        }

        public E next() {
            Object result = this.receiveResult;
            if (result != BufferedChannelKt.NO_RECEIVE_RESULT) {
                this.receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;
                if (result != BufferedChannelKt.getCHANNEL_CLOSED()) {
                    return result;
                }
                throw StackTraceRecoveryKt.recoverStackTrace(BufferedChannel.this.getReceiveException());
            }
            throw new IllegalStateException("`hasNext()` has not been invoked".toString());
        }

        public final boolean tryResumeHasNext(E element) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            Function3 function3 = null;
            this.continuation = null;
            this.receiveResult = element;
            CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
            Function1<E, Unit> function1 = BufferedChannel.this.onUndeliveredElement;
            if (function1 != null) {
                function3 = BufferedChannel.this.bindCancellationFun(function1, element);
            }
            return BufferedChannelKt.tryResume0(cancellableContinuation, true, function3);
        }

        public final void tryResumeHasNextOnClosedChannel() {
            Throwable th;
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable cause = BufferedChannel.this.getCloseCause();
            if (cause == null) {
                Result.Companion companion = Result.Companion;
                cancellableContinuationImpl.resumeWith(Result.m6constructorimpl(false));
                return;
            }
            Continuation continuation2 = cancellableContinuationImpl;
            if (!DebugKt.getRECOVER_STACK_TRACES() || !(cancellableContinuationImpl instanceof CoroutineStackFrame)) {
                th = cause;
            } else {
                th = StackTraceRecoveryKt.recoverFromStackFrame(cause, cancellableContinuationImpl);
            }
            Result.Companion companion2 = Result.Companion;
            continuation2.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(th)));
        }
    }

    /* access modifiers changed from: protected */
    public final Throwable getCloseCause() {
        return (Throwable) _closeCause$volatile$FU.get(this);
    }

    /* access modifiers changed from: protected */
    public final Throwable getSendException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedSendChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : closeCause;
    }

    /* access modifiers changed from: private */
    public final Throwable getReceiveException() {
        Throwable closeCause = getCloseCause();
        return closeCause == null ? new ClosedReceiveChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : closeCause;
    }

    /* access modifiers changed from: protected */
    public void onClosedIdempotent() {
    }

    public boolean close(Throwable cause) {
        return closeOrCancelImpl(cause, false);
    }

    public final boolean cancel(Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause);
    }

    public final void cancel() {
        cancelImpl$kotlinx_coroutines_core((Throwable) null);
    }

    public final void cancel(CancellationException cause) {
        cancelImpl$kotlinx_coroutines_core(cause);
    }

    public boolean cancelImpl$kotlinx_coroutines_core(Throwable cause) {
        return closeOrCancelImpl(cause == null ? new CancellationException("Channel was cancelled") : cause, true);
    }

    /* access modifiers changed from: protected */
    public boolean closeOrCancelImpl(Throwable cause, boolean cancel) {
        if (cancel) {
            markCancellationStarted();
        }
        boolean closedByThisOperation = AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_closeCause$volatile$FU, this, BufferedChannelKt.NO_CLOSE_CAUSE, cause);
        if (cancel) {
            markCancelled();
        } else {
            markClosed();
        }
        completeCloseOrCancel();
        boolean it = closedByThisOperation;
        onClosedIdempotent();
        if (it) {
            invokeCloseHandler();
        }
        return closedByThisOperation;
    }

    private final void invokeCloseHandler() {
        Object closeHandler;
        Symbol symbol;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = closeHandler$volatile$FU;
        do {
            closeHandler = handler$atomicfu$iv.get(this);
            if (closeHandler == null) {
                symbol = BufferedChannelKt.CLOSE_HANDLER_CLOSED;
            } else {
                symbol = BufferedChannelKt.CLOSE_HANDLER_INVOKED;
            }
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, closeHandler, symbol));
        if (closeHandler != null) {
            Function1 function1 = (Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(closeHandler, 1);
            ((Function1) closeHandler).invoke(getCloseCause());
        }
    }

    public void invokeOnClose(Function1<? super Throwable, Unit> handler) {
        if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(closeHandler$volatile$FU, this, (Object) null, handler)) {
            AtomicReferenceFieldUpdater handler$atomicfu$iv = closeHandler$volatile$FU;
            do {
                Object cur = handler$atomicfu$iv.get(this);
                if (cur != BufferedChannelKt.CLOSE_HANDLER_CLOSED) {
                    if (cur == BufferedChannelKt.CLOSE_HANDLER_INVOKED) {
                        throw new IllegalStateException("Another handler was already registered and successfully invoked".toString());
                    }
                    throw new IllegalStateException(("Another handler is already registered: " + cur).toString());
                }
            } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(closeHandler$volatile$FU, this, BufferedChannelKt.CLOSE_HANDLER_CLOSED, BufferedChannelKt.CLOSE_HANDLER_INVOKED));
            handler.invoke(getCloseCause());
        }
    }

    private final void markClosed() {
        long j;
        long cur;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            j = handler$atomicfu$iv.get(this);
            long cur2 = j;
            switch ((int) (cur2 >> 60)) {
                case 0:
                    cur = BufferedChannelKt.constructSendersAndCloseStatus(cur2 & 1152921504606846975L, 2);
                    break;
                case 1:
                    cur = BufferedChannelKt.constructSendersAndCloseStatus(cur2 & 1152921504606846975L, 3);
                    break;
                default:
                    return;
            }
        } while (!handler$atomicfu$iv.compareAndSet(this, j, cur));
    }

    private final void markCancelled() {
        long cur;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
        } while (!handler$atomicfu$iv.compareAndSet(this, cur, BufferedChannelKt.constructSendersAndCloseStatus(cur & 1152921504606846975L, 3)));
    }

    private final void markCancellationStarted() {
        long j;
        long cur;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            j = handler$atomicfu$iv.get(this);
            cur = j;
            if (((int) (cur >> 60)) != 0 || handler$atomicfu$iv.compareAndSet(this, j, BufferedChannelKt.constructSendersAndCloseStatus(cur & 1152921504606846975L, 1))) {
            }
            j = handler$atomicfu$iv.get(this);
            cur = j;
            return;
        } while (handler$atomicfu$iv.compareAndSet(this, j, BufferedChannelKt.constructSendersAndCloseStatus(cur & 1152921504606846975L, 1)));
    }

    private final void completeCloseOrCancel() {
        isClosedForSend();
    }

    /* access modifiers changed from: protected */
    public boolean isConflatedDropOldest() {
        return false;
    }

    private final ChannelSegment<E> completeClose(long sendersCur) {
        ChannelSegment lastSegment = closeLinkedList();
        if (isConflatedDropOldest()) {
            long lastBufferedCellGlobalIndex = markAllEmptyCellsAsClosed(lastSegment);
            if (lastBufferedCellGlobalIndex != -1) {
                dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(lastBufferedCellGlobalIndex);
            }
        }
        cancelSuspendedReceiveRequests(lastSegment, sendersCur);
        return lastSegment;
    }

    private final void completeCancel(long sendersCur) {
        removeUnprocessedElements(completeClose(sendersCur));
    }

    private final ChannelSegment<E> closeLinkedList() {
        Object lastSegment = bufferEndSegment$volatile$FU.get(this);
        ChannelSegment it = (ChannelSegment) sendSegment$volatile$FU.get(this);
        if (it.id > ((ChannelSegment) lastSegment).id) {
            lastSegment = it;
        }
        ChannelSegment it2 = (ChannelSegment) receiveSegment$volatile$FU.get(this);
        if (it2.id > ((ChannelSegment) lastSegment).id) {
            lastSegment = it2;
        }
        return (ChannelSegment) ConcurrentLinkedListKt.close((ConcurrentLinkedListNode) lastSegment);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        r1 = (kotlinx.coroutines.channels.ChannelSegment) r0.getPrev();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
        if (r1 != null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004a, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment<E> r9) {
        /*
            r8 = this;
            r0 = r9
        L_0x0001:
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r1 = r1 + -1
        L_0x0006:
            r2 = -1
            r4 = -1
            if (r4 >= r1) goto L_0x0042
            long r4 = r0.id
            int r6 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r6 = (long) r6
            long r4 = r4 * r6
            long r6 = (long) r1
            long r4 = r4 + r6
            long r6 = r8.getReceiversCounter$kotlinx_coroutines_core()
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 >= 0) goto L_0x001c
            return r2
        L_0x001c:
            java.lang.Object r2 = r0.getState$kotlinx_coroutines_core(r1)
            if (r2 == 0) goto L_0x0031
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.IN_BUFFER
            if (r2 != r3) goto L_0x002b
            goto L_0x0031
        L_0x002b:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r2 != r3) goto L_0x0030
            return r4
        L_0x0030:
            goto L_0x003f
        L_0x0031:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r3 = r0.casState$kotlinx_coroutines_core(r1, r2, r3)
            if (r3 == 0) goto L_0x001c
            r0.onSlotCleaned()
        L_0x003f:
            int r1 = r1 + -1
            goto L_0x0006
        L_0x0042:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r1 = r0.getPrev()
            kotlinx.coroutines.channels.ChannelSegment r1 = (kotlinx.coroutines.channels.ChannelSegment) r1
            if (r1 != 0) goto L_0x004b
            return r2
        L_0x004b:
            r0 = r1
            goto L_0x0001
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment):long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ba, code lost:
        r5 = (kotlinx.coroutines.channels.ChannelSegment) r4.getPrev();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c0, code lost:
        if (r5 != null) goto L_0x00f6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment<E> r13) {
        /*
            r12 = this;
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r0 = r12.onUndeliveredElement
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.Object r2 = kotlinx.coroutines.internal.InlineList.m1588constructorimpl$default(r2, r3, r2)
            r4 = r13
        L_0x000a:
            int r5 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r5 = r5 - r3
        L_0x000e:
            r6 = -1
            if (r6 >= r5) goto L_0x00ba
            long r7 = r4.id
            int r9 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r9 = (long) r9
            long r7 = r7 * r9
            long r9 = (long) r5
            long r7 = r7 + r9
        L_0x0019:
            java.lang.Object r9 = r4.getState$kotlinx_coroutines_core(r5)
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.DONE_RCV
            if (r9 == r10) goto L_0x00c2
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r9 != r10) goto L_0x004d
            long r10 = r12.getReceiversCounter$kotlinx_coroutines_core()
            int r10 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r10 < 0) goto L_0x00c2
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r10 = r4.casState$kotlinx_coroutines_core(r5, r9, r10)
            if (r10 == 0) goto L_0x0019
            if (r0 == 0) goto L_0x0045
            java.lang.Object r6 = r4.getElement$kotlinx_coroutines_core(r5)
            kotlinx.coroutines.internal.UndeliveredElementException r1 = kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElementCatchingException(r0, r6, r1)
        L_0x0045:
            r4.cleanElement$kotlinx_coroutines_core(r5)
            r4.onSlotCleaned()
            goto L_0x00b6
        L_0x004d:
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.IN_BUFFER
            if (r9 == r10) goto L_0x00a8
            if (r9 != 0) goto L_0x0056
            goto L_0x00a8
        L_0x0056:
            boolean r10 = r9 instanceof kotlinx.coroutines.Waiter
            if (r10 != 0) goto L_0x0073
            boolean r10 = r9 instanceof kotlinx.coroutines.channels.WaiterEB
            if (r10 == 0) goto L_0x005f
            goto L_0x0073
        L_0x005f:
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.RESUMING_BY_EB
            if (r9 == r10) goto L_0x00c2
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.RESUMING_BY_RCV
            if (r9 != r10) goto L_0x006c
            goto L_0x00c2
        L_0x006c:
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.RESUMING_BY_EB
            if (r9 == r10) goto L_0x0019
            goto L_0x00b6
        L_0x0073:
            long r10 = r12.getReceiversCounter$kotlinx_coroutines_core()
            int r10 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r10 < 0) goto L_0x00c2
            boolean r10 = r9 instanceof kotlinx.coroutines.channels.WaiterEB
            if (r10 == 0) goto L_0x0085
            r10 = r9
            kotlinx.coroutines.channels.WaiterEB r10 = (kotlinx.coroutines.channels.WaiterEB) r10
            kotlinx.coroutines.Waiter r10 = r10.waiter
            goto L_0x0088
        L_0x0085:
            r10 = r9
            kotlinx.coroutines.Waiter r10 = (kotlinx.coroutines.Waiter) r10
        L_0x0088:
            kotlinx.coroutines.internal.Symbol r11 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r11 = r4.casState$kotlinx_coroutines_core(r5, r9, r11)
            if (r11 == 0) goto L_0x0019
            if (r0 == 0) goto L_0x009d
            java.lang.Object r6 = r4.getElement$kotlinx_coroutines_core(r5)
            kotlinx.coroutines.internal.UndeliveredElementException r1 = kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElementCatchingException(r0, r6, r1)
        L_0x009d:
            java.lang.Object r2 = kotlinx.coroutines.internal.InlineList.m1593plusFjFbRPM(r2, r10)
            r4.cleanElement$kotlinx_coroutines_core(r5)
            r4.onSlotCleaned()
            goto L_0x00b6
        L_0x00a8:
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r10 = r4.casState$kotlinx_coroutines_core(r5, r9, r10)
            if (r10 == 0) goto L_0x0019
            r4.onSlotCleaned()
        L_0x00b6:
            int r5 = r5 + -1
            goto L_0x000e
        L_0x00ba:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r5 = r4.getPrev()
            kotlinx.coroutines.channels.ChannelSegment r5 = (kotlinx.coroutines.channels.ChannelSegment) r5
            if (r5 != 0) goto L_0x00f6
        L_0x00c2:
            r5 = 0
            if (r2 == 0) goto L_0x00f0
            boolean r7 = r2 instanceof java.util.ArrayList
            if (r7 != 0) goto L_0x00d2
            r3 = r2
            kotlinx.coroutines.Waiter r3 = (kotlinx.coroutines.Waiter) r3
            r6 = 0
            r12.resumeSenderOnCancelledChannel(r3)
            goto L_0x00ef
        L_0x00d2:
            java.lang.String r7 = "null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2, r7)
            r7 = r2
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            int r8 = r7.size()
            int r8 = r8 - r3
        L_0x00df:
            if (r6 >= r8) goto L_0x00ef
            java.lang.Object r3 = r7.get(r8)
            kotlinx.coroutines.Waiter r3 = (kotlinx.coroutines.Waiter) r3
            r9 = 0
            r12.resumeSenderOnCancelledChannel(r3)
            int r8 = r8 + -1
            goto L_0x00df
        L_0x00ef:
        L_0x00f0:
            if (r1 != 0) goto L_0x00f3
            return
        L_0x00f3:
            r3 = r1
            r5 = 0
            throw r3
        L_0x00f6:
            r4 = r5
            goto L_0x000a
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment):void");
    }

    /* JADX WARNING: type inference failed for: r3v2, types: [kotlinx.coroutines.internal.ConcurrentLinkedListNode] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void cancelSuspendedReceiveRequests(kotlinx.coroutines.channels.ChannelSegment<E> r10, long r11) {
        /*
            r9 = this;
            r0 = 0
            r1 = 1
            java.lang.Object r0 = kotlinx.coroutines.internal.InlineList.m1588constructorimpl$default(r0, r1, r0)
            r2 = r10
        L_0x0007:
            r3 = -1
            if (r2 == 0) goto L_0x0075
            int r4 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r4 = r4 - r1
        L_0x000d:
            if (r3 >= r4) goto L_0x006d
            long r5 = r2.id
            int r7 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r7 = (long) r7
            long r5 = r5 * r7
            long r7 = (long) r4
            long r5 = r5 + r7
            int r5 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r5 < 0) goto L_0x0075
        L_0x001b:
            java.lang.Object r5 = r2.getState$kotlinx_coroutines_core(r4)
            if (r5 == 0) goto L_0x005c
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.IN_BUFFER
            if (r5 != r6) goto L_0x002a
            goto L_0x005c
        L_0x002a:
            boolean r6 = r5 instanceof kotlinx.coroutines.channels.WaiterEB
            if (r6 == 0) goto L_0x0045
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r6 = r2.casState$kotlinx_coroutines_core(r4, r5, r6)
            if (r6 == 0) goto L_0x001b
            r6 = r5
            kotlinx.coroutines.channels.WaiterEB r6 = (kotlinx.coroutines.channels.WaiterEB) r6
            kotlinx.coroutines.Waiter r6 = r6.waiter
            java.lang.Object r0 = kotlinx.coroutines.internal.InlineList.m1593plusFjFbRPM(r0, r6)
            r2.onCancelledRequest(r4, r1)
            goto L_0x006a
        L_0x0045:
            boolean r6 = r5 instanceof kotlinx.coroutines.Waiter
            if (r6 == 0) goto L_0x005b
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r6 = r2.casState$kotlinx_coroutines_core(r4, r5, r6)
            if (r6 == 0) goto L_0x001b
            java.lang.Object r0 = kotlinx.coroutines.internal.InlineList.m1593plusFjFbRPM(r0, r5)
            r2.onCancelledRequest(r4, r1)
            goto L_0x006a
        L_0x005b:
            goto L_0x006a
        L_0x005c:
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r6 = r2.casState$kotlinx_coroutines_core(r4, r5, r6)
            if (r6 == 0) goto L_0x001b
            r2.onSlotCleaned()
        L_0x006a:
            int r4 = r4 + -1
            goto L_0x000d
        L_0x006d:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r3 = r2.getPrev()
            r2 = r3
            kotlinx.coroutines.channels.ChannelSegment r2 = (kotlinx.coroutines.channels.ChannelSegment) r2
            goto L_0x0007
        L_0x0075:
            r4 = 0
            if (r0 == 0) goto L_0x00a3
            boolean r5 = r0 instanceof java.util.ArrayList
            if (r5 != 0) goto L_0x0085
            r1 = r0
            kotlinx.coroutines.Waiter r1 = (kotlinx.coroutines.Waiter) r1
            r3 = 0
            r9.resumeReceiverOnClosedChannel(r1)
            goto L_0x00a2
        L_0x0085:
            java.lang.String r5 = "null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0, r5)
            r5 = r0
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            int r6 = r5.size()
            int r6 = r6 - r1
        L_0x0092:
            if (r3 >= r6) goto L_0x00a2
            java.lang.Object r1 = r5.get(r6)
            kotlinx.coroutines.Waiter r1 = (kotlinx.coroutines.Waiter) r1
            r7 = 0
            r9.resumeReceiverOnClosedChannel(r1)
            int r6 = r6 + -1
            goto L_0x0092
        L_0x00a2:
        L_0x00a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.cancelSuspendedReceiveRequests(kotlinx.coroutines.channels.ChannelSegment, long):void");
    }

    private final void resumeReceiverOnClosedChannel(Waiter $this$resumeReceiverOnClosedChannel) {
        resumeWaiterOnClosedChannel($this$resumeReceiverOnClosedChannel, true);
    }

    private final void resumeSenderOnCancelledChannel(Waiter $this$resumeSenderOnCancelledChannel) {
        resumeWaiterOnClosedChannel($this$resumeSenderOnCancelledChannel, false);
    }

    private final void resumeWaiterOnClosedChannel(Waiter $this$resumeWaiterOnClosedChannel, boolean receiver) {
        if ($this$resumeWaiterOnClosedChannel instanceof SendBroadcast) {
            Result.Companion companion = Result.Companion;
            ((SendBroadcast) $this$resumeWaiterOnClosedChannel).getCont().resumeWith(Result.m6constructorimpl(false));
        } else if ($this$resumeWaiterOnClosedChannel instanceof CancellableContinuation) {
            Continuation continuation = (Continuation) $this$resumeWaiterOnClosedChannel;
            Result.Companion companion2 = Result.Companion;
            continuation.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(receiver ? getReceiveException() : getSendException())));
        } else if ($this$resumeWaiterOnClosedChannel instanceof ReceiveCatching) {
            Result.Companion companion3 = Result.Companion;
            ((ReceiveCatching) $this$resumeWaiterOnClosedChannel).cont.resumeWith(Result.m6constructorimpl(ChannelResult.m1540boximpl(ChannelResult.Companion.m1553closedJP2dKIU(getCloseCause()))));
        } else if ($this$resumeWaiterOnClosedChannel instanceof BufferedChannelIterator) {
            ((BufferedChannelIterator) $this$resumeWaiterOnClosedChannel).tryResumeHasNextOnClosedChannel();
        } else if ($this$resumeWaiterOnClosedChannel instanceof SelectInstance) {
            ((SelectInstance) $this$resumeWaiterOnClosedChannel).trySelect(this, BufferedChannelKt.getCHANNEL_CLOSED());
        } else {
            throw new IllegalStateException(("Unexpected waiter: " + $this$resumeWaiterOnClosedChannel).toString());
        }
    }

    public boolean isClosedForSend() {
        return isClosedForSend0(sendersAndCloseStatus$volatile$FU.get(this));
    }

    /* access modifiers changed from: private */
    public final boolean isClosedForSend0(long $this$isClosedForSend0) {
        return isClosed($this$isClosedForSend0, false);
    }

    public boolean isClosedForReceive() {
        return isClosedForReceive0(sendersAndCloseStatus$volatile$FU.get(this));
    }

    private final boolean isClosedForReceive0(long $this$isClosedForReceive0) {
        return isClosed($this$isClosedForReceive0, true);
    }

    private final boolean isClosed(long sendersAndCloseStatusCur, boolean isClosedForReceive) {
        switch ((int) (sendersAndCloseStatusCur >> 60)) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                completeClose(sendersAndCloseStatusCur & 1152921504606846975L);
                if (!isClosedForReceive || !hasElements$kotlinx_coroutines_core()) {
                    return true;
                }
                return false;
            case 3:
                completeCancel(sendersAndCloseStatusCur & 1152921504606846975L);
                return true;
            default:
                throw new IllegalStateException(("unexpected close status: " + ((int) (sendersAndCloseStatusCur >> 60))).toString());
        }
    }

    public boolean isEmpty() {
        if (!isClosedForReceive() && !hasElements$kotlinx_coroutines_core()) {
            return !isClosedForReceive();
        }
        return false;
    }

    public final boolean hasElements$kotlinx_coroutines_core() {
        while (true) {
            ChannelSegment segment = (ChannelSegment) receiveSegment$volatile$FU.get(this);
            long r = getReceiversCounter$kotlinx_coroutines_core();
            if (getSendersCounter$kotlinx_coroutines_core() <= r) {
                return false;
            }
            long id = r / ((long) BufferedChannelKt.SEGMENT_SIZE);
            if (segment.id != id) {
                ChannelSegment findSegmentReceive = findSegmentReceive(id, segment);
                if (findSegmentReceive != null) {
                    segment = findSegmentReceive;
                } else if (((ChannelSegment) receiveSegment$volatile$FU.get(this)).id < id) {
                    return false;
                }
            }
            segment.cleanPrev();
            if (isCellNonEmpty(segment, (int) (r % ((long) BufferedChannelKt.SEGMENT_SIZE)), r)) {
                return true;
            }
            receivers$volatile$FU.compareAndSet(this, r, 1 + r);
        }
    }

    private final boolean isCellNonEmpty(ChannelSegment<E> segment, int index, long globalIndex) {
        Object state;
        do {
            state = segment.getState$kotlinx_coroutines_core(index);
            if (state != null && state != BufferedChannelKt.IN_BUFFER) {
                if (state == BufferedChannelKt.BUFFERED) {
                    return true;
                }
                if (state == BufferedChannelKt.INTERRUPTED_SEND || state == BufferedChannelKt.getCHANNEL_CLOSED() || state == BufferedChannelKt.DONE_RCV || state == BufferedChannelKt.POISONED) {
                    return false;
                }
                if (state == BufferedChannelKt.RESUMING_BY_EB) {
                    return true;
                }
                if (state != BufferedChannelKt.RESUMING_BY_RCV && globalIndex == getReceiversCounter$kotlinx_coroutines_core()) {
                    return true;
                }
                return false;
            }
        } while (!segment.casState$kotlinx_coroutines_core(index, state, BufferedChannelKt.POISONED));
        expandBuffer();
        return false;
    }

    /* access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentSend(long id, ChannelSegment<E> startFrom) {
        Object s$iv;
        boolean z;
        Segment to$iv$iv;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = sendSegment$volatile$FU;
        Function2 createNewSegment$iv = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, createNewSegment$iv);
            z = false;
            if (SegmentOrClosed.m1602isClosedimpl(s$iv)) {
                break;
            }
            Segment to$iv$iv2 = SegmentOrClosed.m1600getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                if (cur$iv$iv.id >= to$iv$iv2.id) {
                    to$iv$iv = 1;
                    continue;
                    break;
                } else if (!to$iv$iv2.tryIncPointers$kotlinx_coroutines_core()) {
                    to$iv$iv = null;
                    continue;
                    break;
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv2)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    to$iv$iv = 1;
                    continue;
                } else if (to$iv$iv2.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv2.remove();
                }
            }
        } while (to$iv$iv == null);
        if (SegmentOrClosed.m1602isClosedimpl(s$iv)) {
            completeCloseOrCancel();
            if (startFrom.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getReceiversCounter$kotlinx_coroutines_core()) {
                return null;
            }
            startFrom.cleanPrev();
            return null;
        }
        ChannelSegment segment = (ChannelSegment) SegmentOrClosed.m1600getSegmentimpl(s$iv);
        if (segment.id > id) {
            updateSendersCounterIfLower(segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) >= getReceiversCounter$kotlinx_coroutines_core()) {
                return null;
            }
            segment.cleanPrev();
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (segment.id == id) {
                z = true;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
        return segment;
    }

    /* access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentReceive(long id, ChannelSegment<E> startFrom) {
        Object s$iv;
        Segment to$iv$iv;
        long j = id;
        ChannelSegment<E> channelSegment = startFrom;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = receiveSegment$volatile$FU;
        Function2 createNewSegment$iv = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(channelSegment, j, createNewSegment$iv);
            if (SegmentOrClosed.m1602isClosedimpl(s$iv)) {
                break;
            }
            Segment to$iv$iv2 = SegmentOrClosed.m1600getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                if (cur$iv$iv.id >= to$iv$iv2.id) {
                    to$iv$iv = 1;
                    continue;
                    break;
                } else if (!to$iv$iv2.tryIncPointers$kotlinx_coroutines_core()) {
                    to$iv$iv = null;
                    continue;
                    break;
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv2)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    to$iv$iv = 1;
                    continue;
                } else if (to$iv$iv2.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv2.remove();
                }
            }
        } while (to$iv$iv == null);
        if (SegmentOrClosed.m1602isClosedimpl(s$iv)) {
            completeCloseOrCancel();
            if (channelSegment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) < getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
            return null;
        }
        ChannelSegment segment = (ChannelSegment) SegmentOrClosed.m1600getSegmentimpl(s$iv);
        if (!isRendezvousOrUnlimited() && j <= getBufferEndCounter() / ((long) BufferedChannelKt.SEGMENT_SIZE)) {
            AtomicReferenceFieldUpdater handler$atomicfu$iv2 = bufferEndSegment$volatile$FU;
            while (true) {
                Segment cur$iv = (Segment) handler$atomicfu$iv2.get(this);
                if (cur$iv.id >= segment.id || !segment.tryIncPointers$kotlinx_coroutines_core()) {
                    break;
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv2, this, cur$iv, segment)) {
                    if (cur$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv.remove();
                    }
                } else if (segment.decPointers$kotlinx_coroutines_core()) {
                    segment.remove();
                }
            }
        }
        if (segment.id > j) {
            updateReceiversCounterIfLower(segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE));
            if (segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE) < getSendersCounter$kotlinx_coroutines_core()) {
                segment.cleanPrev();
            }
            return null;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(segment.id == j)) {
                throw new AssertionError();
            }
        }
        return segment;
    }

    private final ChannelSegment<E> findSegmentBufferEnd(long id, ChannelSegment<E> startFrom, long currentBufferEndCounter) {
        Object s$iv;
        boolean z;
        Segment to$iv$iv;
        long j = id;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = bufferEndSegment$volatile$FU;
        Function2 createNewSegment$iv = (Function2) BufferedChannelKt.createSegmentFunction();
        do {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(startFrom, j, createNewSegment$iv);
            z = false;
            if (SegmentOrClosed.m1602isClosedimpl(s$iv)) {
                break;
            }
            Segment to$iv$iv2 = SegmentOrClosed.m1600getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                if (cur$iv$iv.id >= to$iv$iv2.id) {
                    to$iv$iv = 1;
                    continue;
                    break;
                } else if (!to$iv$iv2.tryIncPointers$kotlinx_coroutines_core()) {
                    to$iv$iv = null;
                    continue;
                    break;
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv2)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    to$iv$iv = 1;
                    continue;
                } else if (to$iv$iv2.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv2.remove();
                }
            }
        } while (to$iv$iv == null);
        Object it = s$iv;
        if (SegmentOrClosed.m1602isClosedimpl(it)) {
            completeCloseOrCancel();
            moveSegmentBufferEndToSpecifiedOrLast(id, startFrom);
            incCompletedExpandBufferAttempts$default(this, 0, 1, (Object) null);
            Object obj = it;
            return null;
        }
        ChannelSegment segment = (ChannelSegment) SegmentOrClosed.m1600getSegmentimpl(it);
        if (segment.id > j) {
            Object obj2 = it;
            if (bufferEnd$volatile$FU.compareAndSet(this, currentBufferEndCounter + 1, segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE))) {
                incCompletedExpandBufferAttempts((segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) - currentBufferEndCounter);
                return null;
            }
            incCompletedExpandBufferAttempts$default(this, 0, 1, (Object) null);
            return null;
        }
        Object obj3 = it;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (segment.id == j) {
                z = true;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
        return segment;
    }

    private final void moveSegmentBufferEndToSpecifiedOrLast(long id, ChannelSegment<E> startFrom) {
        boolean z;
        ChannelSegment channelSegment;
        ChannelSegment channelSegment2;
        ChannelSegment segment = startFrom;
        while (segment.id < id && (channelSegment2 = (ChannelSegment) segment.getNext()) != null) {
            segment = channelSegment2;
        }
        while (true) {
            if (!segment.isRemoved() || (channelSegment = (ChannelSegment) segment.getNext()) == null) {
                AtomicReferenceFieldUpdater handler$atomicfu$iv = bufferEndSegment$volatile$FU;
                while (true) {
                    Segment cur$iv = (Segment) handler$atomicfu$iv.get(this);
                    z = true;
                    if (cur$iv.id >= segment.id) {
                        break;
                    } else if (!segment.tryIncPointers$kotlinx_coroutines_core()) {
                        z = false;
                        break;
                    } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv, segment)) {
                        if (cur$iv.decPointers$kotlinx_coroutines_core()) {
                            cur$iv.remove();
                        }
                    } else if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                }
                if (z) {
                    return;
                }
            } else {
                segment = channelSegment;
            }
        }
    }

    private final void updateSendersCounterIfLower(long value) {
        long cur;
        long curCounter;
        AtomicLongFieldUpdater handler$atomicfu$iv = sendersAndCloseStatus$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            curCounter = cur & 1152921504606846975L;
            if (curCounter < value) {
            } else {
                return;
            }
        } while (!sendersAndCloseStatus$volatile$FU.compareAndSet(this, cur, BufferedChannelKt.constructSendersAndCloseStatus(curCounter, (int) (cur >> 60))));
    }

    private final void updateReceiversCounterIfLower(long value) {
        AtomicLongFieldUpdater handler$atomicfu$iv = receivers$volatile$FU;
        while (true) {
            long cur = handler$atomicfu$iv.get(this);
            if (cur < value) {
                long value2 = value;
                if (!receivers$volatile$FU.compareAndSet(this, cur, value2)) {
                    value = value2;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public String toString() {
        String cellStateString;
        BufferedChannel bufferedChannel = this;
        StringBuilder sb = new StringBuilder();
        switch ((int) (sendersAndCloseStatus$volatile$FU.get(bufferedChannel) >> 60)) {
            case 2:
                sb.append("closed,");
                break;
            case 3:
                sb.append("cancelled,");
                break;
        }
        sb.append("capacity=" + bufferedChannel.capacity + ',');
        sb.append("data=[");
        boolean z = true;
        Object[] objArr = {receiveSegment$volatile$FU.get(bufferedChannel), sendSegment$volatile$FU.get(bufferedChannel), bufferEndSegment$volatile$FU.get(bufferedChannel)};
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : CollectionsKt.listOf(objArr)) {
            if ((((ChannelSegment) element$iv$iv) != BufferedChannelKt.NULL_SEGMENT ? 1 : null) != null) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterator iterator$iv = ((List) destination$iv$iv).iterator();
        if (iterator$iv.hasNext()) {
            Object minElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                long minValue$iv = ((ChannelSegment) minElem$iv).id;
                while (true) {
                    Object e$iv = iterator$iv.next();
                    long v$iv = ((ChannelSegment) e$iv).id;
                    if (minValue$iv > v$iv) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                    if (iterator$iv.hasNext()) {
                        bufferedChannel = this;
                    }
                }
            }
            long r = bufferedChannel.getReceiversCounter$kotlinx_coroutines_core();
            long s = bufferedChannel.getSendersCounter$kotlinx_coroutines_core();
            ChannelSegment segment = (ChannelSegment) minElem$iv;
            while (true) {
                int i = 0;
                int i2 = BufferedChannelKt.SEGMENT_SIZE;
                while (true) {
                    if (i < i2) {
                        boolean z2 = z;
                        int i3 = i2;
                        long globalCellIndex = (segment.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) + ((long) i);
                        if (globalCellIndex < s || globalCellIndex < r) {
                            Object cellState = segment.getState$kotlinx_coroutines_core(i);
                            Object element = segment.getElement$kotlinx_coroutines_core(i);
                            boolean z3 = z2;
                            if (cellState instanceof CancellableContinuation) {
                                if (globalCellIndex < r && globalCellIndex >= s) {
                                    cellStateString = "receive";
                                } else if (globalCellIndex >= s || globalCellIndex < r) {
                                    cellStateString = "cont";
                                } else {
                                    cellStateString = "send";
                                }
                            } else if (cellState instanceof SelectInstance) {
                                if (globalCellIndex < r && globalCellIndex >= s) {
                                    cellStateString = "onReceive";
                                } else if (globalCellIndex >= s || globalCellIndex < r) {
                                    cellStateString = "select";
                                } else {
                                    cellStateString = "onSend";
                                }
                            } else if (cellState instanceof ReceiveCatching) {
                                cellStateString = "receiveCatching";
                            } else if (cellState instanceof SendBroadcast) {
                                cellStateString = "sendBroadcast";
                            } else if (cellState instanceof WaiterEB) {
                                cellStateString = "EB(" + cellState + ')';
                            } else if (Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.RESUMING_BY_RCV) || Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.RESUMING_BY_EB)) {
                                cellStateString = "resuming_sender";
                            } else {
                                if (cellState != null && !Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.IN_BUFFER) && !Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.DONE_RCV) && !Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.POISONED) && !Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.INTERRUPTED_RCV) && !Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.INTERRUPTED_SEND) && !Intrinsics.areEqual(cellState, (Object) BufferedChannelKt.getCHANNEL_CLOSED())) {
                                    cellStateString = cellState.toString();
                                }
                                i++;
                                i2 = i3;
                                z = z3;
                            }
                            if (element != null) {
                                sb.append('(' + cellStateString + ',' + element + "),");
                            } else {
                                sb.append(cellStateString + ',');
                            }
                            i++;
                            i2 = i3;
                            z = z3;
                        } else {
                            boolean z4 = z2;
                        }
                    } else {
                        boolean z5 = z;
                        ChannelSegment channelSegment = (ChannelSegment) segment.getNext();
                        if (channelSegment != null) {
                            segment = channelSegment;
                            z = z5;
                        }
                    }
                }
            }
            if (StringsKt.last(sb) == ',') {
                Intrinsics.checkNotNullExpressionValue(sb.deleteCharAt(sb.length() - 1), "deleteCharAt(...)");
            }
            sb.append("]");
            return sb.toString();
        }
        throw new NoSuchElementException();
    }

    public final String toStringDebug$kotlinx_coroutines_core() {
        String cellStateString;
        StringBuilder sb = new StringBuilder();
        sb.append("S=" + getSendersCounter$kotlinx_coroutines_core() + ",R=" + getReceiversCounter$kotlinx_coroutines_core() + ",B=" + getBufferEndCounter() + ",B'=" + completedExpandBuffersAndPauseFlag$volatile$FU.get(this) + ",C=" + ((int) (sendersAndCloseStatus$volatile$FU.get(this) >> 60)) + ',');
        switch ((int) (sendersAndCloseStatus$volatile$FU.get(this) >> 60)) {
            case 1:
                sb.append("CANCELLATION_STARTED,");
                break;
            case 2:
                sb.append("CLOSED,");
                break;
            case 3:
                sb.append("CANCELLED,");
                break;
        }
        sb.append("SEND_SEGM=" + DebugStringsKt.getHexAddress(sendSegment$volatile$FU.get(this)) + ",RCV_SEGM=" + DebugStringsKt.getHexAddress(receiveSegment$volatile$FU.get(this)));
        if (!isRendezvousOrUnlimited()) {
            sb.append(",EB_SEGM=" + DebugStringsKt.getHexAddress(bufferEndSegment$volatile$FU.get(this)));
        }
        sb.append("  ");
        Object[] objArr = {receiveSegment$volatile$FU.get(this), sendSegment$volatile$FU.get(this), bufferEndSegment$volatile$FU.get(this)};
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : CollectionsKt.listOf(objArr)) {
            if ((((ChannelSegment) element$iv$iv) != BufferedChannelKt.NULL_SEGMENT ? 1 : null) != null) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterator iterator$iv = ((List) destination$iv$iv).iterator();
        if (iterator$iv.hasNext()) {
            Object minElem$iv = iterator$iv.next();
            if (!iterator$iv.hasNext()) {
                ChannelSegment channelSegment = (ChannelSegment) minElem$iv;
            } else {
                long minValue$iv = ((ChannelSegment) minElem$iv).id;
                do {
                    Object e$iv = iterator$iv.next();
                    long v$iv = ((ChannelSegment) e$iv).id;
                    if (minValue$iv > v$iv) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
            ChannelSegment channelSegment2 = (ChannelSegment) minElem$iv;
            while (true) {
                StringBuilder append = new StringBuilder().append(DebugStringsKt.getHexAddress(channelSegment2)).append("=[").append(channelSegment2.isRemoved() ? "*" : "").append(channelSegment2.id).append(",prev=");
                ChannelSegment channelSegment3 = (ChannelSegment) channelSegment2.getPrev();
                String str = null;
                sb.append(append.append(channelSegment3 != null ? DebugStringsKt.getHexAddress(channelSegment3) : null).append(',').toString());
                int i = BufferedChannelKt.SEGMENT_SIZE;
                for (int i2 = 0; i2 < i; i2++) {
                    int i3 = i2;
                    Object cellState = channelSegment2.getState$kotlinx_coroutines_core(i3);
                    Object element = channelSegment2.getElement$kotlinx_coroutines_core(i3);
                    if (cellState instanceof CancellableContinuation) {
                        cellStateString = "cont";
                    } else if (cellState instanceof SelectInstance) {
                        cellStateString = "select";
                    } else if (cellState instanceof ReceiveCatching) {
                        cellStateString = "receiveCatching";
                    } else if (cellState instanceof SendBroadcast) {
                        cellStateString = "send(broadcast)";
                    } else if (cellState instanceof WaiterEB) {
                        cellStateString = "EB(" + cellState + ')';
                    } else {
                        cellStateString = String.valueOf(cellState);
                    }
                    sb.append('[' + i3 + "]=(" + cellStateString + ',' + element + "),");
                }
                StringBuilder append2 = new StringBuilder().append("next=");
                ChannelSegment channelSegment4 = (ChannelSegment) channelSegment2.getNext();
                if (channelSegment4 != null) {
                    str = DebugStringsKt.getHexAddress(channelSegment4);
                }
                sb.append(append2.append(str).append("]  ").toString());
                ChannelSegment channelSegment5 = (ChannelSegment) channelSegment2.getNext();
                if (channelSegment5 == null) {
                    return sb.toString();
                }
                channelSegment2 = channelSegment5;
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /* JADX WARNING: type inference failed for: r4v8, types: [kotlinx.coroutines.internal.ConcurrentLinkedListNode, java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x021e A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0126  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void checkSegmentStructureInvariants() {
        /*
            r12 = this;
            boolean r0 = r12.isRendezvousOrUnlimited()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x003c
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = bufferEndSegment$volatile$FU
            java.lang.Object r0 = r0.get(r12)
            kotlinx.coroutines.channels.ChannelSegment r3 = kotlinx.coroutines.channels.BufferedChannelKt.NULL_SEGMENT
            if (r0 != r3) goto L_0x0018
            r0 = r2
            goto L_0x0019
        L_0x0018:
            r0 = r1
        L_0x0019:
            if (r0 == 0) goto L_0x001c
            goto L_0x005d
        L_0x001c:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "bufferEndSegment must be NULL_SEGMENT for rendezvous and unlimited channels; they do not manipulate it.\nChannel state: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x003c:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = receiveSegment$volatile$FU
            java.lang.Object r0 = r0.get(r12)
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
            long r3 = r0.id
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = bufferEndSegment$volatile$FU
            java.lang.Object r0 = r0.get(r12)
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
            long r5 = r0.id
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 > 0) goto L_0x005a
            r0 = r2
            goto L_0x005b
        L_0x005a:
            r0 = r1
        L_0x005b:
            if (r0 == 0) goto L_0x0265
        L_0x005d:
            r0 = 3
            kotlinx.coroutines.channels.ChannelSegment[] r0 = new kotlinx.coroutines.channels.ChannelSegment[r0]
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = receiveSegment$volatile$FU
            java.lang.Object r3 = r3.get(r12)
            r0[r1] = r3
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = sendSegment$volatile$FU
            java.lang.Object r3 = r3.get(r12)
            r0[r2] = r3
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = bufferEndSegment$volatile$FU
            java.lang.Object r3 = r3.get(r12)
            r4 = 2
            r0[r4] = r3
            java.util.List r0 = kotlin.collections.CollectionsKt.listOf(r0)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r3 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Collection r4 = (java.util.Collection) r4
            r5 = r0
            r6 = 0
            java.util.Iterator r7 = r5.iterator()
        L_0x0094:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x00b1
            java.lang.Object r8 = r7.next()
            r9 = r8
            kotlinx.coroutines.channels.ChannelSegment r9 = (kotlinx.coroutines.channels.ChannelSegment) r9
            r10 = 0
            kotlinx.coroutines.channels.ChannelSegment r11 = kotlinx.coroutines.channels.BufferedChannelKt.NULL_SEGMENT
            if (r9 == r11) goto L_0x00aa
            r9 = r2
            goto L_0x00ab
        L_0x00aa:
            r9 = r1
        L_0x00ab:
            if (r9 == 0) goto L_0x0094
            r4.add(r8)
            goto L_0x0094
        L_0x00b1:
            java.util.List r4 = (java.util.List) r4
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            r0 = 0
            java.util.Iterator r3 = r4.iterator()
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x025f
            java.lang.Object r5 = r3.next()
            boolean r6 = r3.hasNext()
            if (r6 != 0) goto L_0x00ce
            goto L_0x00ed
        L_0x00ce:
            r6 = r5
            kotlinx.coroutines.channels.ChannelSegment r6 = (kotlinx.coroutines.channels.ChannelSegment) r6
            r7 = 0
            long r6 = r6.id
        L_0x00d5:
            java.lang.Object r8 = r3.next()
            r9 = r8
            kotlinx.coroutines.channels.ChannelSegment r9 = (kotlinx.coroutines.channels.ChannelSegment) r9
            r10 = 0
            long r9 = r9.id
            int r11 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r11 <= 0) goto L_0x00e6
            r5 = r8
            r6 = r9
        L_0x00e6:
            boolean r8 = r3.hasNext()
            if (r8 != 0) goto L_0x00d5
        L_0x00ed:
            kotlinx.coroutines.channels.ChannelSegment r5 = (kotlinx.coroutines.channels.ChannelSegment) r5
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r0 = r5.getPrev()
            if (r0 != 0) goto L_0x00f8
            r0 = r2
            goto L_0x00f9
        L_0x00f8:
            r0 = r1
        L_0x00f9:
            if (r0 == 0) goto L_0x023f
            r0 = r5
        L_0x00fc:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r3 = r0.getNext()
            if (r3 == 0) goto L_0x023e
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r3 = r0.getNext()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            kotlinx.coroutines.channels.ChannelSegment r3 = (kotlinx.coroutines.channels.ChannelSegment) r3
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r3 = r3.getPrev()
            if (r3 == 0) goto L_0x0123
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r3 = r0.getNext()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            kotlinx.coroutines.channels.ChannelSegment r3 = (kotlinx.coroutines.channels.ChannelSegment) r3
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r3 = r3.getPrev()
            if (r3 != r0) goto L_0x0121
            goto L_0x0123
        L_0x0121:
            r3 = r1
            goto L_0x0124
        L_0x0123:
            r3 = r2
        L_0x0124:
            if (r3 == 0) goto L_0x021e
            r3 = 0
            r4 = 0
            int r6 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
        L_0x012a:
            if (r4 >= r6) goto L_0x01cb
            java.lang.Object r7 = r0.getState$kotlinx_coroutines_core(r4)
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r7, (java.lang.Object) r8)
            if (r8 != 0) goto L_0x01c7
            boolean r8 = r7 instanceof kotlinx.coroutines.Waiter
            if (r8 != 0) goto L_0x01c7
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.INTERRUPTED_RCV
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r7, (java.lang.Object) r8)
            java.lang.String r9 = "Check failed."
            if (r8 != 0) goto L_0x01af
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.INTERRUPTED_SEND
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r7, (java.lang.Object) r8)
            if (r8 != 0) goto L_0x01af
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r7, (java.lang.Object) r8)
            if (r8 == 0) goto L_0x015d
            goto L_0x01af
        L_0x015d:
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.POISONED
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r7, (java.lang.Object) r8)
            if (r8 != 0) goto L_0x0199
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.DONE_RCV
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r7, (java.lang.Object) r8)
            if (r8 == 0) goto L_0x0172
            goto L_0x0199
        L_0x0172:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "Unexpected segment cell state: "
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r6 = ".\nChannel state: "
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.StringBuilder r2 = r2.append(r12)
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0199:
            java.lang.Object r8 = r0.getElement$kotlinx_coroutines_core(r4)
            if (r8 != 0) goto L_0x01a1
            r8 = r2
            goto L_0x01a2
        L_0x01a1:
            r8 = r1
        L_0x01a2:
            if (r8 == 0) goto L_0x01a5
            goto L_0x01c7
        L_0x01a5:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = r9.toString()
            r1.<init>(r2)
            throw r1
        L_0x01af:
            java.lang.Object r8 = r0.getElement$kotlinx_coroutines_core(r4)
            if (r8 != 0) goto L_0x01b7
            r8 = r2
            goto L_0x01b8
        L_0x01b7:
            r8 = r1
        L_0x01b8:
            if (r8 == 0) goto L_0x01bd
            int r3 = r3 + 1
            goto L_0x01c7
        L_0x01bd:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = r9.toString()
            r1.<init>(r2)
            throw r1
        L_0x01c7:
            int r4 = r4 + 1
            goto L_0x012a
        L_0x01cb:
            int r4 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            if (r3 != r4) goto L_0x0212
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = receiveSegment$volatile$FU
            java.lang.Object r4 = r4.get(r12)
            if (r0 == r4) goto L_0x01f0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = sendSegment$volatile$FU
            java.lang.Object r4 = r4.get(r12)
            if (r0 == r4) goto L_0x01f0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = bufferEndSegment$volatile$FU
            java.lang.Object r4 = r4.get(r12)
            if (r0 != r4) goto L_0x01ee
            goto L_0x01f0
        L_0x01ee:
            r4 = r1
            goto L_0x01f1
        L_0x01f0:
            r4 = r2
        L_0x01f1:
            if (r4 == 0) goto L_0x01f4
            goto L_0x0212
        L_0x01f4:
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "Logically removed segment is reachable.\nChannel state: "
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.StringBuilder r2 = r2.append(r12)
            java.lang.String r1 = r2.toString()
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r1 = r1.toString()
            r2.<init>(r1)
            throw r2
        L_0x0212:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r4 = r0.getNext()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            r0 = r4
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
            goto L_0x00fc
        L_0x021e:
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "The `segment.next.prev === segment` invariant is violated.\nChannel state: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r12)
            java.lang.String r2 = r2.toString()
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x023e:
            return
        L_0x023f:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "All processed segments should be unreachable from the data structure, but the `prev` link of the leftmost segment is non-null.\nChannel state: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x025f:
            java.util.NoSuchElementException r1 = new java.util.NoSuchElementException
            r1.<init>()
            throw r1
        L_0x0265:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "bufferEndSegment should not have lower id than receiveSegment.\nChannel state: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.checkSegmentStructureInvariants():void");
    }

    /* access modifiers changed from: private */
    public final KFunction<Unit> bindCancellationFunResult(Function1<? super E, Unit> $this$bindCancellationFunResult) {
        return new BufferedChannel$bindCancellationFunResult$1(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: onCancellationChannelResultImplDoNotCall-5_sEAP8  reason: not valid java name */
    public final void m1525onCancellationChannelResultImplDoNotCall5_sEAP8(Throwable cause, Object element, CoroutineContext context) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        Intrinsics.checkNotNull(function1);
        Object r1 = ChannelResult.m1545getOrNullimpl(element);
        Intrinsics.checkNotNull(r1);
        OnUndeliveredElementKt.callUndeliveredElement(function1, r1, context);
    }

    /* access modifiers changed from: private */
    public final Function3<Throwable, Object, CoroutineContext, Unit> bindCancellationFun(Function1<? super E, Unit> $this$bindCancellationFun, E element) {
        return new BufferedChannel$$ExternalSyntheticLambda2($this$bindCancellationFun, element);
    }

    /* access modifiers changed from: private */
    public static final Unit bindCancellationFun$lambda$89(Function1 $this_bindCancellationFun, Object $element, Throwable th, Object obj, CoroutineContext context) {
        OnUndeliveredElementKt.callUndeliveredElement($this_bindCancellationFun, $element, context);
        return Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final KFunction<Unit> bindCancellationFun(Function1<? super E, Unit> $this$bindCancellationFun) {
        return new BufferedChannel$bindCancellationFun$2(this);
    }

    /* access modifiers changed from: private */
    public final void onCancellationImplDoNotCall(Throwable cause, E element, CoroutineContext context) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        Intrinsics.checkNotNull(function1);
        OnUndeliveredElementKt.callUndeliveredElement(function1, element, context);
    }
}
