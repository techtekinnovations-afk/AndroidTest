package kotlinx.coroutines.channels;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.channels.ReceiveChannel;

@Metadata(d1 = {"\u0000¶\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001aC\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001d\u0010\u0004\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0006\u0012\u0004\u0012\u0002H\u00010\u0005¢\u0006\u0002\b\u0007H\b¢\u0006\u0002\u0010\b\u001a2\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\n0\u0005HH¢\u0006\u0002\u0010\f\u001aJ\u0010\r\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\n0\u0005j\u0002`\u000e2\u001a\u0010\u0013\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0014\"\u0006\u0012\u0002\b\u00030\u0006H\u0001¢\u0006\u0002\u0010\u0015\u001a&\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H@¢\u0006\u0002\u0010\u0019\u001a(\u0010\u001a\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H@¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u001b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a \u0010\u001d\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a&\u0010\u001e\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010\u001f\u001a\u0002H\u0002H@¢\u0006\u0002\u0010 \u001a\u001e\u0010!\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a&\u0010\"\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010\u001f\u001a\u0002H\u0002H@¢\u0006\u0002\u0010 \u001a \u0010#\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a\u001e\u0010$\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a \u0010%\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a0\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010'\u001a\u00020\u00182\b\b\u0002\u0010(\u001a\u00020)H\u0007\u001aQ\u0010*\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2\"\u0010+\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0007¢\u0006\u0002\u00100\u001aQ\u00101\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2\"\u0010+\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0001¢\u0006\u0002\u00100\u001af\u00102\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)27\u0010+\u001a3\b\u0001\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0-\u0012\u0006\u0012\u0004\u0018\u00010/03H\u0007¢\u0006\u0002\u00104\u001aQ\u00105\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2\"\u0010+\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0007¢\u0006\u0002\u00100\u001a$\u00106\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\b\b\u0000\u0010\u0002*\u00020/*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0006H\u0001\u001a>\u00107\u001a\u0002H8\"\b\b\u0000\u0010\u0002*\u00020/\"\u0010\b\u0001\u00108*\n\u0012\u0006\b\u0000\u0012\u0002H\u000209*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00062\u0006\u0010:\u001a\u0002H8H@¢\u0006\u0002\u0010;\u001a<\u00107\u001a\u0002H8\"\b\b\u0000\u0010\u0002*\u00020/\"\u000e\b\u0001\u00108*\b\u0012\u0004\u0012\u0002H\u00020<*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00062\u0006\u0010:\u001a\u0002H8H@¢\u0006\u0002\u0010=\u001a0\u0010>\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010'\u001a\u00020\u00182\b\b\u0002\u0010(\u001a\u00020)H\u0007\u001aQ\u0010?\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2\"\u0010+\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0007¢\u0006\u0002\u00100\u001a6\u0010@\u001a\u0002H8\"\u0004\b\u0000\u0010\u0002\"\u000e\b\u0001\u00108*\b\u0012\u0004\u0012\u0002H\u00020<*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010:\u001a\u0002H8H@¢\u0006\u0002\u0010=\u001a8\u0010A\u001a\u0002H8\"\u0004\b\u0000\u0010\u0002\"\u0010\b\u0001\u00108*\n\u0012\u0006\b\u0000\u0012\u0002H\u000209*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010:\u001a\u0002H8H@¢\u0006\u0002\u0010;\u001a<\u0010B\u001a\u000e\u0012\u0004\u0012\u0002HD\u0012\u0004\u0012\u0002HE0C\"\u0004\b\u0000\u0010D\"\u0004\b\u0001\u0010E*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002HD\u0012\u0004\u0012\u0002HE0F0\u0006H@¢\u0006\u0002\u0010\u001c\u001aR\u0010B\u001a\u0002HG\"\u0004\b\u0000\u0010D\"\u0004\b\u0001\u0010E\"\u0018\b\u0002\u0010G*\u0012\u0012\u0006\b\u0000\u0012\u0002HD\u0012\u0006\b\u0000\u0012\u0002HE0H*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002HD\u0012\u0004\u0012\u0002HE0F0\u00062\u0006\u0010:\u001a\u0002HGH@¢\u0006\u0002\u0010I\u001a$\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020K\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a$\u0010L\u001a\b\u0012\u0004\u0012\u0002H\u00020M\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a]\u0010N\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2(\u0010O\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00060-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0007¢\u0006\u0002\u00100\u001aW\u0010P\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2\"\u0010O\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0001¢\u0006\u0002\u00100\u001al\u0010Q\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)27\u0010O\u001a3\b\u0001\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010-\u0012\u0006\u0012\u0004\u0018\u00010/03H\u0001¢\u0006\u0002\u00104\u001ar\u0010R\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0001*\u00020/*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)29\u0010O\u001a5\b\u0001\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u0002H\u0002\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00010-\u0012\u0006\u0012\u0004\u0018\u00010/03H\u0007¢\u0006\u0002\u00104\u001a]\u0010S\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0001*\u00020/*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2$\u0010O\u001a \b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00010-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0007¢\u0006\u0002\u00100\u001a.\u0010T\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020U0\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)H\u0007\u001a\u001e\u0010V\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0007\u001aW\u0010W\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010D*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010(\u001a\u00020)2\"\u0010X\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002HD0-\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0001¢\u0006\u0002\u00100\u001a$\u0010Y\u001a\b\u0012\u0004\u0012\u0002H\u00020Z\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a\u001e\u0010[\u001a\u00020.\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a\u001e\u0010\\\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a<\u0010]\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u001a\u0010^\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020`j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`_H@¢\u0006\u0002\u0010a\u001a<\u0010b\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u001a\u0010^\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020`j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`_H@¢\u0006\u0002\u0010a\u001a\u001e\u0010c\u001a\u00020.\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0002\u0010\u001c\u001a$\u0010d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\b\b\u0000\u0010\u0002*\u00020/*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0006H\u0007\u001a?\u0010e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010F0\u0006\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00062\f\u0010f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006H\u0004\u001az\u0010e\u001a\b\u0012\u0004\u0012\u0002HE0\u0006\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0001\"\u0004\b\u0002\u0010E*\b\u0012\u0004\u0012\u0002H\u00020\u00062\f\u0010f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00062\b\b\u0002\u0010(\u001a\u00020)26\u0010O\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(g\u0012\u0013\u0012\u0011H\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(h\u0012\u0004\u0012\u0002HE0,H\u0001\u001a6\u0010i\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\n0\u0005j\u0002`\u000e*\u0006\u0012\u0002\b\u00030\u0006H\u0001¢\u0006\u0002\u0010j¨\u0006k"}, d2 = {"consume", "R", "E", "Lkotlinx/coroutines/channels/BroadcastChannel;", "block", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "", "action", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "consumesAll", "Lkotlinx/coroutines/CompletionHandler;", "", "Lkotlin/ParameterName;", "name", "cause", "channels", "", "([Lkotlinx/coroutines/channels/ReceiveChannel;)Lkotlin/jvm/functions/Function1;", "elementAt", "index", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "elementAtOrNull", "first", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "firstOrNull", "indexOf", "element", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "last", "lastIndexOf", "lastOrNull", "single", "singleOrNull", "drop", "n", "context", "Lkotlin/coroutines/CoroutineContext;", "dropWhile", "predicate", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "filter", "filterIndexed", "Lkotlin/Function3;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/channels/ReceiveChannel;", "filterNot", "filterNotNull", "filterNotNullTo", "C", "", "destination", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/SendChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "take", "takeWhile", "toChannel", "toCollection", "toMap", "", "K", "V", "Lkotlin/Pair;", "M", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toMutableList", "", "toSet", "", "flatMap", "transform", "map", "mapIndexed", "mapIndexedNotNull", "mapNotNull", "withIndex", "Lkotlin/collections/IndexedValue;", "distinct", "distinctBy", "selector", "toMutableSet", "", "any", "count", "maxWith", "comparator", "Lkotlin/Comparator;", "Ljava/util/Comparator;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Comparator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "minWith", "none", "requireNoNulls", "zip", "other", "a", "b", "consumes", "(Lkotlinx/coroutines/channels/ReceiveChannel;)Lkotlin/jvm/functions/Function1;", "kotlinx-coroutines-core"}, k = 5, mv = {2, 0, 0}, xi = 48, xs = "kotlinx/coroutines/channels/ChannelsKt")
/* compiled from: Deprecated.kt */
final /* synthetic */ class ChannelsKt__DeprecatedKt {
    @Deprecated(level = DeprecationLevel.ERROR, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
    public static final <E, R> R consume(BroadcastChannel<E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        ReceiveChannel channel = $this$consume.openSubscription();
        try {
            return block.invoke(channel);
        } finally {
            ReceiveChannel.DefaultImpls.cancel$default(channel, (CancellationException) null, 1, (Object) null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.L$0 = r8;
        r0.L$1 = r7;
        r0.L$2 = r6;
        r0.label = 1;
        r9 = r6.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006c, code lost:
        if (r9 != r2) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006e, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006f, code lost:
        r10 = r2;
        r2 = r1;
        r1 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r3;
        r3 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007d, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007f, code lost:
        r9.invoke(r7.next());
        r1 = r2;
        r2 = r3;
        r3 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008d, code lost:
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0090, code lost:
        kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default(r8, (java.util.concurrent.CancellationException) null, 1, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0097, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0098, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0099, code lost:
        r7 = r2;
        r2 = r1;
        r1 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.ERROR, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.BroadcastChannel<E> r11, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r12, kotlin.coroutines.Continuation<? super kotlin.Unit> r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$consumeEach$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$consumeEach$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$consumeEach$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$consumeEach$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$consumeEach$1
            r0.<init>(r13)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            switch(r3) {
                case 0: goto L_0x004a;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002e:
            r11 = 0
            r12 = 0
            r3 = 0
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r0.L$0
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0047 }
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r2
            r2 = r1
            goto L_0x0077
        L_0x0047:
            r2 = move-exception
            goto L_0x00a1
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r6 = 0
            kotlinx.coroutines.channels.ReceiveChannel r7 = r11.openSubscription()
            r11 = r7
            r8 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r11.iterator()     // Catch:{ all -> 0x009e }
            r11 = r3
            r3 = r8
            r8 = r12
            r12 = r6
            r6 = r9
        L_0x0060:
            r0.L$0 = r8     // Catch:{ all -> 0x0047 }
            r0.L$1 = r7     // Catch:{ all -> 0x0047 }
            r0.L$2 = r6     // Catch:{ all -> 0x0047 }
            r0.label = r4     // Catch:{ all -> 0x0047 }
            java.lang.Object r9 = r6.hasNext(r0)     // Catch:{ all -> 0x0047 }
            if (r9 != r2) goto L_0x006f
            return r2
        L_0x006f:
            r10 = r2
            r2 = r1
            r1 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r10
        L_0x0077:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x0098 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x0098 }
            if (r1 == 0) goto L_0x008d
            java.lang.Object r1 = r7.next()     // Catch:{ all -> 0x0098 }
            r9.invoke(r1)     // Catch:{ all -> 0x0098 }
            r1 = r2
            r2 = r3
            r3 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            goto L_0x0060
        L_0x008d:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0098 }
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r8, (java.util.concurrent.CancellationException) r5, (int) r4, (java.lang.Object) r5)
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        L_0x0098:
            r1 = move-exception
            r7 = r2
            r2 = r1
            r1 = r7
            r7 = r8
            goto L_0x00a1
        L_0x009e:
            r2 = move-exception
            r11 = r3
            r12 = r6
        L_0x00a1:
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r7, (java.util.concurrent.CancellationException) r5, (int) r4, (java.lang.Object) r5)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.consumeEach(kotlinx.coroutines.channels.BroadcastChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: finally extract failed */
    @Deprecated(level = DeprecationLevel.ERROR, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
    private static final <E> Object consumeEach$$forInline(BroadcastChannel<E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        ReceiveChannel<E> $this$consumeEach_u24lambda_u240 = $this$consumeEach.openSubscription();
        try {
            ChannelIterator it = $this$consumeEach_u24lambda_u240.iterator();
            while (((Boolean) it.hasNext((Continuation<? super Boolean>) null)).booleanValue()) {
                action.invoke(it.next());
            }
            Unit unit = Unit.INSTANCE;
            ReceiveChannel.DefaultImpls.cancel$default((ReceiveChannel) $this$consumeEach_u24lambda_u240, (CancellationException) null, 1, (Object) null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            ReceiveChannel.DefaultImpls.cancel$default((ReceiveChannel) $this$consumeEach_u24lambda_u240, (CancellationException) null, 1, (Object) null);
            throw th;
        }
    }

    public static final Function1<Throwable, Unit> consumesAll(ReceiveChannel<?>... channels) {
        return new ChannelsKt__DeprecatedKt$$ExternalSyntheticLambda1(channels);
    }

    /* access modifiers changed from: private */
    public static final Unit consumesAll$lambda$2$ChannelsKt__DeprecatedKt(ReceiveChannel[] $channels, Throwable cause) {
        Throwable exception = null;
        for (ReceiveChannel channel : $channels) {
            try {
                ChannelsKt.cancelConsumed(channel, cause);
            } catch (Throwable e) {
                if (exception == null) {
                    exception = e;
                } else {
                    ExceptionsKt.addSuppressed(exception, e);
                }
            }
        }
        if (exception == null) {
            return Unit.INSTANCE;
        }
        throw exception;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0062, code lost:
        r0.L$0 = r8;
        r0.L$1 = r7;
        r0.I$0 = r6;
        r0.I$1 = r3;
        r0.label = 1;
        r10 = r7.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0071, code lost:
        if (r10 != r2) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0073, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0074, code lost:
        r12 = r2;
        r2 = r1;
        r1 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r3;
        r3 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0083, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0085, code lost:
        r1 = r8.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0089, code lost:
        r11 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008b, code lost:
        if (r7 != r6) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008d, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0091, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0092, code lost:
        r1 = r2;
        r2 = r3;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009a, code lost:
        r8 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b6, code lost:
        throw new java.lang.IndexOutOfBoundsException("ReceiveChannel doesn't contain element at index " + r7 + '.');
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b7, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b8, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ba, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00bb, code lost:
        r8 = r9;
        r9 = r10;
        r1 = r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object elementAt(kotlinx.coroutines.channels.ReceiveChannel r13, int r14, kotlin.coroutines.Continuation r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAt$1
            r0.<init>(r15)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 46
            java.lang.String r5 = "ReceiveChannel doesn't contain element at index "
            switch(r3) {
                case 0: goto L_0x004d;
                case 1: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x0030:
            r13 = 0
            r14 = 0
            int r3 = r0.I$1
            int r6 = r0.I$0
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            r9 = 0
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x004a }
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r2
            r2 = r1
            goto L_0x007d
        L_0x004a:
            r14 = move-exception
            goto L_0x00dc
        L_0x004d:
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r13
            r13 = 0
            r9 = 0
            r3 = r8
            r6 = 0
            if (r14 < 0) goto L_0x00c1
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r3.iterator()     // Catch:{ all -> 0x00bf }
            r3 = r6
            r6 = r14
            r14 = r3
            r3 = r7
            r7 = r10
        L_0x0062:
            r0.L$0 = r8     // Catch:{ all -> 0x00bf }
            r0.L$1 = r7     // Catch:{ all -> 0x00bf }
            r0.I$0 = r6     // Catch:{ all -> 0x00bf }
            r0.I$1 = r3     // Catch:{ all -> 0x00bf }
            r10 = 1
            r0.label = r10     // Catch:{ all -> 0x00bf }
            java.lang.Object r10 = r7.hasNext(r0)     // Catch:{ all -> 0x00bf }
            if (r10 != r2) goto L_0x0074
            return r2
        L_0x0074:
            r12 = r2
            r2 = r1
            r1 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r12
        L_0x007d:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00ba }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00ba }
            if (r1 == 0) goto L_0x009a
            java.lang.Object r1 = r8.next()     // Catch:{ all -> 0x00ba }
            int r11 = r6 + 1
            if (r7 != r6) goto L_0x0092
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r10)
            return r1
        L_0x0092:
            r1 = r2
            r2 = r3
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r3 = r11
            goto L_0x0062
        L_0x009a:
            r8 = r9
            r9 = r10
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException     // Catch:{ all -> 0x00b7 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b7 }
            r3.<init>()     // Catch:{ all -> 0x00b7 }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x00b7 }
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ all -> 0x00b7 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00b7 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00b7 }
            r1.<init>(r3)     // Catch:{ all -> 0x00b7 }
            throw r1     // Catch:{ all -> 0x00b7 }
        L_0x00b7:
            r14 = move-exception
            r1 = r2
            goto L_0x00dc
        L_0x00ba:
            r14 = move-exception
            r8 = r9
            r9 = r10
            r1 = r2
            goto L_0x00dc
        L_0x00bf:
            r14 = move-exception
            goto L_0x00dc
        L_0x00c1:
            java.lang.IndexOutOfBoundsException r2 = new java.lang.IndexOutOfBoundsException     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            r3.<init>()     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r3 = r3.append(r14)     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00bf }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00bf }
            r2.<init>(r3)     // Catch:{ all -> 0x00bf }
            throw r2     // Catch:{ all -> 0x00bf }
        L_0x00dc:
            r2 = r14
            throw r14     // Catch:{ all -> 0x00df }
        L_0x00df:
            r14 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r2)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.elementAt(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r0.L$0 = r7;
        r0.L$1 = r9;
        r0.I$0 = r6;
        r0.I$1 = r3;
        r0.label = 1;
        r8 = r9.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0074, code lost:
        if (r8 != r2) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0076, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0077, code lost:
        r11 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r3;
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0085, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0087, code lost:
        r1 = r9.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008b, code lost:
        r10 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008d, code lost:
        if (r7 != r5) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008f, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0093, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0094, code lost:
        r5 = r6;
        r1 = r2;
        r2 = r3;
        r6 = r7;
        r7 = r8;
        r3 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009b, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009f, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a0, code lost:
        r12 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a1, code lost:
        r7 = r8;
        r4 = r6;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a5, code lost:
        r12 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a6, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel r12, int r13, kotlin.coroutines.Continuation r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$elementAtOrNull$1
            r0.<init>(r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0049;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002d:
            r12 = 0
            r13 = 0
            int r3 = r0.I$1
            int r5 = r0.I$0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0046 }
            r9 = r6
            r8 = r7
            r6 = r4
            r7 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x007f
        L_0x0046:
            r12 = move-exception
            goto L_0x00ac
        L_0x0049:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r5 = r12
            r6 = 0
            if (r13 >= 0) goto L_0x0059
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r4)
            return r4
        L_0x0059:
            r7 = r12
            r12 = r4
            r8 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r5.iterator()     // Catch:{ all -> 0x00a8 }
            r5 = r12
            r12 = r6
            r6 = r13
            r13 = r3
            r3 = r8
        L_0x0065:
            r0.L$0 = r7     // Catch:{ all -> 0x00a5 }
            r0.L$1 = r9     // Catch:{ all -> 0x00a5 }
            r0.I$0 = r6     // Catch:{ all -> 0x00a5 }
            r0.I$1 = r3     // Catch:{ all -> 0x00a5 }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x00a5 }
            java.lang.Object r8 = r9.hasNext(r0)     // Catch:{ all -> 0x00a5 }
            if (r8 != r2) goto L_0x0077
            return r2
        L_0x0077:
            r11 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r11
        L_0x007f:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00a0 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00a0 }
            if (r1 == 0) goto L_0x009b
            java.lang.Object r1 = r9.next()     // Catch:{ all -> 0x00a0 }
            int r10 = r5 + 1
            if (r7 != r5) goto L_0x0094
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r6)
            return r1
        L_0x0094:
            r5 = r6
            r1 = r2
            r2 = r3
            r6 = r7
            r7 = r8
            r3 = r10
            goto L_0x0065
        L_0x009b:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r6)
            return r4
        L_0x00a0:
            r12 = move-exception
            r7 = r8
            r4 = r6
            r1 = r2
            goto L_0x00ac
        L_0x00a5:
            r12 = move-exception
            r4 = r5
            goto L_0x00ac
        L_0x00a8:
            r13 = move-exception
            r4 = r12
            r12 = r13
            r13 = r3
        L_0x00ac:
            r2 = r12
            throw r12     // Catch:{ all -> 0x00af }
        L_0x00af:
            r12 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r2)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0061, code lost:
        if (((java.lang.Boolean) r7).booleanValue() == false) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0063, code lost:
        r6 = r3.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0067, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006a, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0072, code lost:
        throw new java.util.NoSuchElementException("ReceiveChannel is empty.");
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object first(kotlinx.coroutines.channels.ReceiveChannel r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$first$1
            r0.<init>(r9)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003e;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002c:
            r8 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            r5 = 0
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x003c }
            r7 = r1
            goto L_0x005b
        L_0x003c:
            r2 = move-exception
            goto L_0x0074
        L_0x003e:
            kotlin.ResultKt.throwOnFailure(r1)
            r4 = r8
            r8 = 0
            r5 = 0
            r3 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r3.iterator()     // Catch:{ all -> 0x0073 }
            r3 = r7
            r0.L$0 = r4     // Catch:{ all -> 0x0073 }
            r0.L$1 = r3     // Catch:{ all -> 0x0073 }
            r7 = 1
            r0.label = r7     // Catch:{ all -> 0x0073 }
            java.lang.Object r7 = r3.hasNext(r0)     // Catch:{ all -> 0x0073 }
            if (r7 != r2) goto L_0x005a
            return r2
        L_0x005a:
            r2 = r6
        L_0x005b:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x003c }
            boolean r6 = r7.booleanValue()     // Catch:{ all -> 0x003c }
            if (r6 == 0) goto L_0x006b
            java.lang.Object r6 = r3.next()     // Catch:{ all -> 0x003c }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5)
            return r6
        L_0x006b:
            java.util.NoSuchElementException r6 = new java.util.NoSuchElementException     // Catch:{ all -> 0x003c }
            java.lang.String r7 = "ReceiveChannel is empty."
            r6.<init>(r7)     // Catch:{ all -> 0x003c }
            throw r6     // Catch:{ all -> 0x003c }
        L_0x0073:
            r2 = move-exception
        L_0x0074:
            r3 = r2
            throw r2     // Catch:{ all -> 0x0077 }
        L_0x0077:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.first(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0062, code lost:
        if (((java.lang.Boolean) r6).booleanValue() != false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0065, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0068, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r4 = r8.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006e, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006f, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object firstOrNull(kotlinx.coroutines.channels.ReceiveChannel r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$firstOrNull$1
            r0.<init>(r10)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002d:
            r9 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x003e }
            r6 = r1
            r8 = r3
            r3 = r4
            goto L_0x005c
        L_0x003e:
            r2 = move-exception
        L_0x003f:
            goto L_0x0073
        L_0x0040:
            kotlin.ResultKt.throwOnFailure(r1)
            r5 = r9
            r9 = 0
            r3 = 0
            r6 = r5
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch:{ all -> 0x0071 }
            r0.L$0 = r5     // Catch:{ all -> 0x0071 }
            r0.L$1 = r8     // Catch:{ all -> 0x0071 }
            r6 = 1
            r0.label = r6     // Catch:{ all -> 0x0071 }
            java.lang.Object r6 = r8.hasNext(r0)     // Catch:{ all -> 0x0071 }
            if (r6 != r2) goto L_0x005b
            return r2
        L_0x005b:
            r2 = r7
        L_0x005c:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x006e }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x006e }
            if (r6 != 0) goto L_0x0069
        L_0x0065:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r3)
            return r4
        L_0x0069:
            java.lang.Object r4 = r8.next()     // Catch:{ all -> 0x006e }
            goto L_0x0065
        L_0x006e:
            r2 = move-exception
            r4 = r3
            goto L_0x003f
        L_0x0071:
            r2 = move-exception
            r4 = r3
        L_0x0073:
            r3 = r2
            throw r2     // Catch:{ all -> 0x0076 }
        L_0x0076:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.firstOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.L$0 = r11;
        r2.L$1 = r10;
        r2.L$2 = r9;
        r2.L$3 = r8;
        r2.label = 1;
        r13 = r8.hasNext(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007e, code lost:
        if (r13 != r0) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0080, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0081, code lost:
        r17 = r4;
        r4 = r3;
        r3 = r13;
        r13 = r12;
        r12 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0094, code lost:
        if (((java.lang.Boolean) r3).booleanValue() == false) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a0, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r12, r9.next()) == false) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a2, code lost:
        r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r11.element);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a8, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ab, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r11.element++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b1, code lost:
        r3 = r4;
        r4 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
        r10 = r11;
        r11 = r12;
        r12 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00bc, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bd, code lost:
        r3 = r4;
        r4 = r6;
        r6 = r7;
        r9 = r10;
        r12 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c3, code lost:
        r9 = r10;
        r12 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c8, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d3, code lost:
        return kotlin.coroutines.jvm.internal.Boxing.boxInt(-1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d4, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d5, code lost:
        r3 = r4;
        r4 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d9, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00da, code lost:
        r9 = r10;
        r12 = r13;
        r3 = r4;
        r4 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e0, code lost:
        r0 = th;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object indexOf(kotlinx.coroutines.channels.ReceiveChannel r18, java.lang.Object r19, kotlin.coroutines.Continuation r20) {
        /*
            r1 = r20
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$indexOf$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            switch(r4) {
                case 0: goto L_0x0052;
                case 1: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0030:
            r4 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r9 = r2.L$2
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r10 = r2.L$1
            kotlin.jvm.internal.Ref$IntRef r10 = (kotlin.jvm.internal.Ref.IntRef) r10
            java.lang.Object r11 = r2.L$0
            r12 = 0
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x004f }
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r3
            goto L_0x008e
        L_0x004f:
            r0 = move-exception
            goto L_0x00e5
        L_0x0052:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = r18
            r6 = r19
            kotlin.jvm.internal.Ref$IntRef r7 = new kotlin.jvm.internal.Ref$IntRef
            r7.<init>()
            r8 = 0
            r9 = r4
            r4 = 0
            r12 = 0
            r10 = r9
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r13 = r10.iterator()     // Catch:{ all -> 0x00e2 }
            r10 = r7
            r7 = r11
            r11 = r6
            r6 = r4
            r4 = r8
            r8 = r13
        L_0x0070:
            r2.L$0 = r11     // Catch:{ all -> 0x00e0 }
            r2.L$1 = r10     // Catch:{ all -> 0x00e0 }
            r2.L$2 = r9     // Catch:{ all -> 0x00e0 }
            r2.L$3 = r8     // Catch:{ all -> 0x00e0 }
            r2.label = r5     // Catch:{ all -> 0x00e0 }
            java.lang.Object r13 = r8.hasNext(r2)     // Catch:{ all -> 0x00e0 }
            if (r13 != r0) goto L_0x0081
            return r0
        L_0x0081:
            r17 = r4
            r4 = r3
            r3 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r17
        L_0x008e:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00d9 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00d9 }
            if (r3 == 0) goto L_0x00c3
            java.lang.Object r3 = r9.next()     // Catch:{ all -> 0x00d9 }
            r14 = r3
            r15 = 0
            boolean r16 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r12, (java.lang.Object) r14)     // Catch:{ all -> 0x00d9 }
            if (r16 == 0) goto L_0x00ac
            int r0 = r11.element     // Catch:{ all -> 0x00d9 }
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)     // Catch:{ all -> 0x00d9 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r13)
            return r0
        L_0x00ac:
            int r3 = r11.element     // Catch:{ all -> 0x00bc }
            int r3 = r3 + r5
            r11.element = r3     // Catch:{ all -> 0x00bc }
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            goto L_0x0070
        L_0x00bc:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r6 = r7
            r9 = r10
            r12 = r13
            goto L_0x00e5
        L_0x00c3:
            r9 = r10
            r12 = r13
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00d4 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r12)
            r0 = -1
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x00d4:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r6 = r7
            goto L_0x00e5
        L_0x00d9:
            r0 = move-exception
            r9 = r10
            r12 = r13
            r3 = r4
            r4 = r6
            r6 = r7
            goto L_0x00e5
        L_0x00e0:
            r0 = move-exception
            goto L_0x00e5
        L_0x00e2:
            r0 = move-exception
            r6 = r4
            r4 = r8
        L_0x00e5:
            r5 = r0
            throw r0     // Catch:{ all -> 0x00e8 }
        L_0x00e8:
            r0 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.indexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007c, code lost:
        if (((java.lang.Boolean) r3).booleanValue() == false) goto L_0x00bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007e, code lost:
        r9 = r4;
        r4 = r5.next();
        r3 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0085, code lost:
        r0.L$0 = r6;
        r0.L$1 = r5;
        r0.L$2 = r4;
        r0.label = 2;
        r8 = r5.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0092, code lost:
        if (r8 != r2) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0094, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0095, code lost:
        r9 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a4, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00aa, code lost:
        r5 = r4;
        r4 = r6.next();
        r1 = r2;
        r2 = r3;
        r3 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b3, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b7, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b8, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b9, code lost:
        r6 = r7;
        r7 = r8;
        r9 = r2;
        r2 = r1;
        r1 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c6, code lost:
        throw new java.util.NoSuchElementException("ReceiveChannel is empty.");
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object last(kotlinx.coroutines.channels.ReceiveChannel r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$last$1
            r0.<init>(r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x005b;
                case 1: goto L_0x0048;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002c:
            r10 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            r7 = 0
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0045 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x009e
        L_0x0045:
            r2 = move-exception
            goto L_0x00c8
        L_0x0048:
            r10 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r7 = 0
            java.lang.Object r5 = r0.L$0
            r6 = r5
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x00c7 }
            r5 = r4
            r4 = r3
            r3 = r1
            goto L_0x0076
        L_0x005b:
            kotlin.ResultKt.throwOnFailure(r1)
            r6 = r10
            r10 = 0
            r7 = 0
            r3 = r6
            r4 = 0
            kotlinx.coroutines.channels.ChannelIterator r5 = r3.iterator()     // Catch:{ all -> 0x00c7 }
            r0.L$0 = r6     // Catch:{ all -> 0x00c7 }
            r0.L$1 = r5     // Catch:{ all -> 0x00c7 }
            r3 = 1
            r0.label = r3     // Catch:{ all -> 0x00c7 }
            java.lang.Object r3 = r5.hasNext(r0)     // Catch:{ all -> 0x00c7 }
            if (r3 != r2) goto L_0x0076
            return r2
        L_0x0076:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00c7 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00c7 }
            if (r3 == 0) goto L_0x00bf
            java.lang.Object r3 = r5.next()     // Catch:{ all -> 0x00c7 }
            r9 = r4
            r4 = r3
            r3 = r9
        L_0x0085:
            r0.L$0 = r6     // Catch:{ all -> 0x00c7 }
            r0.L$1 = r5     // Catch:{ all -> 0x00c7 }
            r0.L$2 = r4     // Catch:{ all -> 0x00c7 }
            r8 = 2
            r0.label = r8     // Catch:{ all -> 0x00c7 }
            java.lang.Object r8 = r5.hasNext(r0)     // Catch:{ all -> 0x00c7 }
            if (r8 != r2) goto L_0x0095
            return r2
        L_0x0095:
            r9 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r9
        L_0x009e:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00b8 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00b8 }
            if (r1 == 0) goto L_0x00b3
            java.lang.Object r1 = r6.next()     // Catch:{ all -> 0x00b8 }
            r5 = r4
            r4 = r1
            r1 = r2
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x0085
        L_0x00b3:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r8)
            return r5
        L_0x00b8:
            r1 = move-exception
            r6 = r7
            r7 = r8
            r9 = r2
            r2 = r1
            r1 = r9
            goto L_0x00c8
        L_0x00bf:
            java.util.NoSuchElementException r2 = new java.util.NoSuchElementException     // Catch:{ all -> 0x00c7 }
            java.lang.String r3 = "ReceiveChannel is empty."
            r2.<init>(r3)     // Catch:{ all -> 0x00c7 }
            throw r2     // Catch:{ all -> 0x00c7 }
        L_0x00c7:
            r2 = move-exception
        L_0x00c8:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00cb }
        L_0x00cb:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.last(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.L$0 = r13;
        r2.L$1 = r12;
        r2.L$2 = r11;
        r2.L$3 = r10;
        r2.L$4 = r8;
        r2.label = 1;
        r14 = r8.hasNext(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x008f, code lost:
        if (r14 != r0) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0091, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0092, code lost:
        r17 = r4;
        r4 = r3;
        r3 = r14;
        r14 = r13;
        r13 = r12;
        r12 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a6, code lost:
        if (((java.lang.Boolean) r3).booleanValue() == false) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b1, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r14, r9.next()) == false) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b3, code lost:
        r13.element = r12.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b7, code lost:
        r12.element++;
        r3 = r4;
        r4 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
        r10 = r11;
        r11 = r12;
        r12 = r13;
        r13 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c8, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00cb, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d7, code lost:
        return kotlin.coroutines.jvm.internal.Boxing.boxInt(r13.element);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d8, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d9, code lost:
        r3 = r4;
        r4 = r6;
        r6 = r7;
        r9 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00df, code lost:
        r0 = th;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel r18, java.lang.Object r19, kotlin.coroutines.Continuation r20) {
        /*
            r1 = r20
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastIndexOf$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            switch(r4) {
                case 0: goto L_0x0057;
                case 1: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0030:
            r4 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r2.L$4
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            r9 = 0
            java.lang.Object r10 = r2.L$3
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r2.L$2
            kotlin.jvm.internal.Ref$IntRef r11 = (kotlin.jvm.internal.Ref.IntRef) r11
            java.lang.Object r12 = r2.L$1
            kotlin.jvm.internal.Ref$IntRef r12 = (kotlin.jvm.internal.Ref.IntRef) r12
            java.lang.Object r13 = r2.L$0
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0054 }
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r3
            goto L_0x00a0
        L_0x0054:
            r0 = move-exception
            goto L_0x00e5
        L_0x0057:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = r18
            r6 = r19
            kotlin.jvm.internal.Ref$IntRef r7 = new kotlin.jvm.internal.Ref$IntRef
            r7.<init>()
            r8 = -1
            r7.element = r8
            kotlin.jvm.internal.Ref$IntRef r8 = new kotlin.jvm.internal.Ref$IntRef
            r8.<init>()
            r9 = 0
            r10 = r4
            r4 = 0
            r11 = 0
            r12 = r10
            r13 = 0
            kotlinx.coroutines.channels.ChannelIterator r14 = r12.iterator()     // Catch:{ all -> 0x00e1 }
            r12 = r7
            r7 = r13
            r13 = r6
            r6 = r4
            r4 = r9
            r9 = r11
            r11 = r8
            r8 = r14
        L_0x007f:
            r2.L$0 = r13     // Catch:{ all -> 0x00df }
            r2.L$1 = r12     // Catch:{ all -> 0x00df }
            r2.L$2 = r11     // Catch:{ all -> 0x00df }
            r2.L$3 = r10     // Catch:{ all -> 0x00df }
            r2.L$4 = r8     // Catch:{ all -> 0x00df }
            r2.label = r5     // Catch:{ all -> 0x00df }
            java.lang.Object r14 = r8.hasNext(r2)     // Catch:{ all -> 0x00df }
            if (r14 != r0) goto L_0x0092
            return r0
        L_0x0092:
            r17 = r4
            r4 = r3
            r3 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r17
        L_0x00a0:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00d8 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00d8 }
            if (r3 == 0) goto L_0x00c8
            java.lang.Object r3 = r9.next()     // Catch:{ all -> 0x00d8 }
            r15 = 0
            boolean r16 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r14, (java.lang.Object) r3)     // Catch:{ all -> 0x00d8 }
            if (r16 == 0) goto L_0x00b7
            int r3 = r12.element     // Catch:{ all -> 0x00d8 }
            r13.element = r3     // Catch:{ all -> 0x00d8 }
        L_0x00b7:
            int r3 = r12.element     // Catch:{ all -> 0x00d8 }
            int r3 = r3 + r5
            r12.element = r3     // Catch:{ all -> 0x00d8 }
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            goto L_0x007f
        L_0x00c8:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00d8 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10)
            int r0 = r13.element
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x00d8:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r6 = r7
            r9 = r10
            r10 = r11
            goto L_0x00e5
        L_0x00df:
            r0 = move-exception
            goto L_0x00e5
        L_0x00e1:
            r0 = move-exception
            r6 = r4
            r4 = r9
            r9 = r11
        L_0x00e5:
            r5 = r0
            throw r0     // Catch:{ all -> 0x00e8 }
        L_0x00e8:
            r0 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007f, code lost:
        if (((java.lang.Boolean) r8).booleanValue() != false) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0081, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0085, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0086, code lost:
        r4 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r9 = r5;
        r5 = r5.next();
        r10 = r6;
        r6 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008f, code lost:
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.L$2 = r5;
        r0.label = 2;
        r8 = r6.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009c, code lost:
        if (r8 != r2) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009e, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009f, code lost:
        r9 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ae, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b4, code lost:
        r6 = r5;
        r5 = r7.next();
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00bd, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c1, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c2, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c3, code lost:
        r7 = r8;
        r1 = r2;
        r3 = r4;
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c8, code lost:
        r10 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ca, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00cb, code lost:
        r4 = r10;
        r10 = r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object lastOrNull(kotlinx.coroutines.channels.ReceiveChannel r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$lastOrNull$1
            r0.<init>(r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x005d;
                case 1: goto L_0x0047;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002d:
            r10 = 0
            r3 = 0
            java.lang.Object r5 = r0.L$2
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0045 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x00a8
        L_0x0045:
            r10 = move-exception
            goto L_0x005b
        L_0x0047:
            r3 = 0
            r10 = 0
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0059 }
            r8 = r1
            r7 = r6
            r6 = r10
            r10 = r4
            goto L_0x0079
        L_0x0059:
            r10 = move-exception
            r7 = r6
        L_0x005b:
            goto L_0x00d1
        L_0x005d:
            kotlin.ResultKt.throwOnFailure(r1)
            r7 = r10
            r3 = 0
            r10 = 0
            r5 = r7
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r5.iterator()     // Catch:{ all -> 0x00ce }
            r5 = r8
            r0.L$0 = r7     // Catch:{ all -> 0x00ce }
            r0.L$1 = r5     // Catch:{ all -> 0x00ce }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x00ce }
            java.lang.Object r8 = r5.hasNext(r0)     // Catch:{ all -> 0x00ce }
            if (r8 != r2) goto L_0x0079
            return r2
        L_0x0079:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch:{ all -> 0x00ca }
            boolean r8 = r8.booleanValue()     // Catch:{ all -> 0x00ca }
            if (r8 != 0) goto L_0x0086
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            return r4
        L_0x0086:
            r4 = r10
            java.lang.Object r10 = r5.next()     // Catch:{ all -> 0x00c8 }
            r9 = r5
            r5 = r10
            r10 = r6
            r6 = r9
        L_0x008f:
            r0.L$0 = r7     // Catch:{ all -> 0x00c8 }
            r0.L$1 = r6     // Catch:{ all -> 0x00c8 }
            r0.L$2 = r5     // Catch:{ all -> 0x00c8 }
            r8 = 2
            r0.label = r8     // Catch:{ all -> 0x00c8 }
            java.lang.Object r8 = r6.hasNext(r0)     // Catch:{ all -> 0x00c8 }
            if (r8 != r2) goto L_0x009f
            return r2
        L_0x009f:
            r9 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r9
        L_0x00a8:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00c2 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00c2 }
            if (r1 == 0) goto L_0x00bd
            java.lang.Object r1 = r7.next()     // Catch:{ all -> 0x00c2 }
            r6 = r5
            r5 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            goto L_0x008f
        L_0x00bd:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r5)
            return r6
        L_0x00c2:
            r10 = move-exception
            r7 = r8
            r1 = r2
            r3 = r4
            r4 = r5
            goto L_0x00d1
        L_0x00c8:
            r10 = move-exception
            goto L_0x00d1
        L_0x00ca:
            r2 = move-exception
            r4 = r10
            r10 = r2
            goto L_0x005b
        L_0x00ce:
            r2 = move-exception
            r4 = r10
            r10 = r2
        L_0x00d1:
            r2 = r10
            throw r10     // Catch:{ all -> 0x00d4 }
        L_0x00d4:
            r10 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r2)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.lastOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0076, code lost:
        if (((java.lang.Boolean) r4).booleanValue() == false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0078, code lost:
        r4 = r7.next();
        r0.L$0 = r6;
        r0.L$1 = r4;
        r0.label = 2;
        r8 = r7.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0087, code lost:
        if (r8 != r2) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0089, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008a, code lost:
        r2 = r3;
        r3 = r4;
        r4 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0093, code lost:
        if (((java.lang.Boolean) r8).booleanValue() != false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0095, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0099, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a1, code lost:
        throw new java.lang.IllegalArgumentException("ReceiveChannel has more than one element.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a9, code lost:
        throw new java.util.NoSuchElementException("ReceiveChannel is empty.");
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object single(kotlinx.coroutines.channels.ReceiveChannel r10, kotlin.coroutines.Continuation r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$single$1
            r0.<init>(r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0051;
                case 1: goto L_0x003d;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002c:
            r10 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$1
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            r5 = 0
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x003a }
            r8 = r1
            goto L_0x008d
        L_0x003a:
            r2 = move-exception
            goto L_0x00ab
        L_0x003d:
            r10 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x004e }
            r7 = r4
            r4 = r1
            goto L_0x0070
        L_0x004e:
            r2 = move-exception
            r4 = r6
            goto L_0x00ab
        L_0x0051:
            kotlin.ResultKt.throwOnFailure(r1)
            r4 = r10
            r10 = 0
            r5 = 0
            r3 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r3.iterator()     // Catch:{ all -> 0x00aa }
            r0.L$0 = r4     // Catch:{ all -> 0x00aa }
            r0.L$1 = r7     // Catch:{ all -> 0x00aa }
            r3 = 1
            r0.label = r3     // Catch:{ all -> 0x00aa }
            java.lang.Object r3 = r7.hasNext(r0)     // Catch:{ all -> 0x00aa }
            if (r3 != r2) goto L_0x006c
            return r2
        L_0x006c:
            r9 = r4
            r4 = r3
            r3 = r6
            r6 = r9
        L_0x0070:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x004e }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x004e }
            if (r4 == 0) goto L_0x00a2
            java.lang.Object r4 = r7.next()     // Catch:{ all -> 0x004e }
            r0.L$0 = r6     // Catch:{ all -> 0x004e }
            r0.L$1 = r4     // Catch:{ all -> 0x004e }
            r8 = 2
            r0.label = r8     // Catch:{ all -> 0x004e }
            java.lang.Object r8 = r7.hasNext(r0)     // Catch:{ all -> 0x004e }
            if (r8 != r2) goto L_0x008a
            return r2
        L_0x008a:
            r2 = r3
            r3 = r4
            r4 = r6
        L_0x008d:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch:{ all -> 0x003a }
            boolean r6 = r8.booleanValue()     // Catch:{ all -> 0x003a }
            if (r6 != 0) goto L_0x009a
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r5)
            return r3
        L_0x009a:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x003a }
            java.lang.String r7 = "ReceiveChannel has more than one element."
            r6.<init>(r7)     // Catch:{ all -> 0x003a }
            throw r6     // Catch:{ all -> 0x003a }
        L_0x00a2:
            java.util.NoSuchElementException r2 = new java.util.NoSuchElementException     // Catch:{ all -> 0x004e }
            java.lang.String r4 = "ReceiveChannel is empty."
            r2.<init>(r4)     // Catch:{ all -> 0x004e }
            throw r2     // Catch:{ all -> 0x004e }
        L_0x00aa:
            r2 = move-exception
        L_0x00ab:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00ae }
        L_0x00ae:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.single(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0081, code lost:
        if (((java.lang.Boolean) r6).booleanValue() != false) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0083, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0087, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r6 = r8.next();
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.label = 2;
        r9 = r8.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0097, code lost:
        if (r9 != r2) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0099, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009a, code lost:
        r2 = r3;
        r3 = r6;
        r6 = r5;
        r5 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a4, code lost:
        if (((java.lang.Boolean) r9).booleanValue() == false) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a6, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00aa, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ab, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00af, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b0, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b1, code lost:
        r4 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b3, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b4, code lost:
        r2 = r3;
        r4 = r5;
        r5 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b8, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b9, code lost:
        r4 = r5;
        r2 = r3;
        r5 = r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object singleOrNull(kotlinx.coroutines.channels.ReceiveChannel r11, kotlin.coroutines.Continuation r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$singleOrNull$1
            r0.<init>(r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x005b;
                case 1: goto L_0x003f;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x002d:
            r11 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$1
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x003c }
            r9 = r1
            r6 = r4
            goto L_0x009e
        L_0x003c:
            r11 = move-exception
            goto L_0x00c2
        L_0x003f:
            r11 = 0
            r3 = 0
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0054 }
            r7 = r3
            r3 = r11
            r11 = r7
            r8 = r5
            r7 = r6
            r6 = r1
            r5 = r4
            goto L_0x007b
        L_0x0054:
            r2 = move-exception
            r5 = r6
            r10 = r2
            r2 = r11
            r11 = r10
            goto L_0x00c2
        L_0x005b:
            kotlin.ResultKt.throwOnFailure(r1)
            r5 = r11
            r11 = 0
            r3 = 0
            r6 = r5
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch:{ all -> 0x00bd }
            r0.L$0 = r5     // Catch:{ all -> 0x00bd }
            r0.L$1 = r8     // Catch:{ all -> 0x00bd }
            r6 = 1
            r0.label = r6     // Catch:{ all -> 0x00bd }
            java.lang.Object r6 = r8.hasNext(r0)     // Catch:{ all -> 0x00bd }
            if (r6 != r2) goto L_0x0076
            return r2
        L_0x0076:
            r10 = r3
            r3 = r11
            r11 = r7
            r7 = r5
            r5 = r10
        L_0x007b:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x00b8 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x00b8 }
            if (r6 != 0) goto L_0x0088
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            return r4
        L_0x0088:
            java.lang.Object r6 = r8.next()     // Catch:{ all -> 0x00b3 }
            r0.L$0 = r7     // Catch:{ all -> 0x00b3 }
            r0.L$1 = r6     // Catch:{ all -> 0x00b3 }
            r9 = 2
            r0.label = r9     // Catch:{ all -> 0x00b3 }
            java.lang.Object r9 = r8.hasNext(r0)     // Catch:{ all -> 0x00b3 }
            if (r9 != r2) goto L_0x009a
            return r2
        L_0x009a:
            r2 = r3
            r3 = r6
            r6 = r5
            r5 = r7
        L_0x009e:
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ all -> 0x00b0 }
            boolean r7 = r9.booleanValue()     // Catch:{ all -> 0x00b0 }
            if (r7 == 0) goto L_0x00ab
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r6)
            return r4
        L_0x00ab:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r6)
            return r3
        L_0x00b0:
            r11 = move-exception
            r4 = r6
            goto L_0x00c2
        L_0x00b3:
            r11 = move-exception
            r2 = r3
            r4 = r5
            r5 = r7
            goto L_0x00c2
        L_0x00b8:
            r11 = move-exception
            r4 = r5
            r2 = r3
            r5 = r7
            goto L_0x00c2
        L_0x00bd:
            r2 = move-exception
            r4 = r2
            r2 = r11
            r11 = r4
            r4 = r3
        L_0x00c2:
            r3 = r11
            throw r11     // Catch:{ all -> 0x00c5 }
        L_0x00c5:
            r11 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r3)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.singleOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel drop$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$drop$1(i, receiveChannel, (Continuation<? super ChannelsKt__DeprecatedKt$drop$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel dropWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$dropWhile$1(receiveChannel, function2, (Continuation<? super ChannelsKt__DeprecatedKt$dropWhile$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filter$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filter(receiveChannel, coroutineContext, function2);
    }

    public static final <E> ReceiveChannel<E> filter(ReceiveChannel<? extends E> $this$filter, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$filter), new ChannelsKt__DeprecatedKt$filter$1($this$filter, predicate, (Continuation<? super ChannelsKt__DeprecatedKt$filter$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filterIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$filterIndexed$1(receiveChannel, function3, (Continuation<? super ChannelsKt__DeprecatedKt$filterIndexed$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filterNot$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filter(receiveChannel, coroutineContext, new ChannelsKt__DeprecatedKt$filterNot$1(function2, (Continuation<? super ChannelsKt__DeprecatedKt$filterNot$1>) null));
    }

    public static final <E> ReceiveChannel<E> filterNotNull(ReceiveChannel<? extends E> $this$filterNotNull) {
        ReceiveChannel<E> filter$default = filter$default($this$filterNotNull, (CoroutineContext) null, new ChannelsKt__DeprecatedKt$filterNotNull$1((Continuation<? super ChannelsKt__DeprecatedKt$filterNotNull$1>) null), 1, (Object) null);
        Intrinsics.checkNotNull(filter$default, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveChannel<E of kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.filterNotNull>");
        return filter$default;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.L$2 = r4;
        r0.label = 1;
        r8 = r4.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006c, code lost:
        if (r8 != r2) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006e, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006f, code lost:
        r10 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007e, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0080, code lost:
        r1 = r5.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0085, code lost:
        if (r1 == null) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0087, code lost:
        r8.add(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008a, code lost:
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0093, code lost:
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0096, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009c, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009d, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009e, code lost:
        r5 = r2;
        r2 = r1;
        r1 = r5;
        r5 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel r11, java.util.Collection r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$1
            r0.<init>(r13)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004a;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002c:
            r11 = 0
            r12 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            java.util.Collection r7 = (java.util.Collection) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0047 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x0078
        L_0x0047:
            r2 = move-exception
            goto L_0x00a7
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r6 = r11
            r11 = 0
            r5 = 0
            r4 = r6
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch:{ all -> 0x00a4 }
            r4 = r12
            r12 = r11
            r11 = r3
            r3 = r7
            r7 = r4
            r4 = r8
        L_0x005f:
            r0.L$0 = r7     // Catch:{ all -> 0x0047 }
            r0.L$1 = r6     // Catch:{ all -> 0x0047 }
            r0.L$2 = r4     // Catch:{ all -> 0x0047 }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x0047 }
            java.lang.Object r8 = r4.hasNext(r0)     // Catch:{ all -> 0x0047 }
            if (r8 != r2) goto L_0x006f
            return r2
        L_0x006f:
            r10 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r10
        L_0x0078:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x009d }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x009d }
            if (r1 == 0) goto L_0x0093
            java.lang.Object r1 = r5.next()     // Catch:{ all -> 0x009d }
            r9 = 0
            if (r1 == 0) goto L_0x008a
            r8.add(r1)     // Catch:{ all -> 0x009d }
        L_0x008a:
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x005f
        L_0x0093:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x009d }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            return r8
        L_0x009d:
            r1 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            r5 = r6
            r6 = r7
            goto L_0x00a7
        L_0x00a4:
            r2 = move-exception
            r12 = r11
            r11 = r3
        L_0x00a7:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00aa }
        L_0x00aa:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0093 A[Catch:{ all -> 0x00c9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00bf A[Catch:{ all -> 0x00c9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel r12, kotlinx.coroutines.channels.SendChannel r13, kotlin.coroutines.Continuation r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNullTo$3
            r0.<init>(r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x005e;
                case 1: goto L_0x0042;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            r12 = 0
            r13 = 0
            r3 = 0
            r4 = 0
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r6 = 0
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r8 = (kotlinx.coroutines.channels.SendChannel) r8
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x005b }
            goto L_0x00af
        L_0x0042:
            r12 = 0
            r13 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r6 = 0
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            r7 = r5
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x005b }
            r8 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x008b
        L_0x005b:
            r2 = move-exception
            goto L_0x00d9
        L_0x005e:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r7 = r12
            r12 = 0
            r6 = 0
            r4 = r7
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch:{ all -> 0x00d6 }
            r11 = r14
            r14 = r12
            r12 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
        L_0x0074:
            r1.L$0 = r13     // Catch:{ all -> 0x00ce }
            r1.L$1 = r7     // Catch:{ all -> 0x00ce }
            r1.L$2 = r8     // Catch:{ all -> 0x00ce }
            r4 = 1
            r1.label = r4     // Catch:{ all -> 0x00ce }
            java.lang.Object r4 = r8.hasNext(r1)     // Catch:{ all -> 0x00ce }
            if (r4 != r3) goto L_0x0084
            return r3
        L_0x0084:
            r11 = r8
            r8 = r13
            r13 = r14
            r14 = r0
            r0 = r1
            r1 = r4
            r4 = r11
        L_0x008b:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00c9 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00c9 }
            if (r1 == 0) goto L_0x00bf
            java.lang.Object r1 = r4.next()     // Catch:{ all -> 0x00c9 }
            r9 = 0
            if (r1 == 0) goto L_0x00b9
            r0.L$0 = r8     // Catch:{ all -> 0x00c9 }
            r0.L$1 = r7     // Catch:{ all -> 0x00c9 }
            r0.L$2 = r4     // Catch:{ all -> 0x00c9 }
            r10 = 2
            r0.label = r10     // Catch:{ all -> 0x00c9 }
            java.lang.Object r10 = r8.send(r1, r0)     // Catch:{ all -> 0x00c9 }
            if (r10 != r3) goto L_0x00aa
            return r3
        L_0x00aa:
            r1 = r2
            r2 = r3
            r3 = r5
            r5 = r4
            r4 = r9
        L_0x00af:
            r11 = r14
            r14 = r13
            r13 = r8
            r8 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x00be
        L_0x00b9:
            r1 = r0
            r0 = r14
            r14 = r13
            r13 = r8
            r8 = r4
        L_0x00be:
            goto L_0x0074
        L_0x00bf:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00c9 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            return r8
        L_0x00c9:
            r1 = move-exception
            r11 = r2
            r2 = r1
            r1 = r11
            goto L_0x00d9
        L_0x00ce:
            r13 = move-exception
            r11 = r2
            r2 = r13
            r13 = r14
            r14 = r0
            r0 = r1
            r1 = r11
            goto L_0x00d9
        L_0x00d6:
            r2 = move-exception
            r13 = r12
            r12 = r3
        L_0x00d9:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00dc }
        L_0x00dc:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel take$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$take$1(i, receiveChannel, (Continuation<? super ChannelsKt__DeprecatedKt$take$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel takeWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$takeWhile$1(receiveChannel, function2, (Continuation<? super ChannelsKt__DeprecatedKt$takeWhile$1>) null), 6, (Object) null);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Incorrect type for immutable var: ssa=C, code=kotlinx.coroutines.channels.SendChannel, for r13v0, types: [C] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x009c A[Catch:{ all -> 0x00c3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object toChannel(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r12, kotlinx.coroutines.channels.SendChannel r13, kotlin.coroutines.Continuation<? super C> r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toChannel$1
            r0.<init>(r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0067;
                case 1: goto L_0x004b;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            r12 = 0
            r13 = 0
            r3 = 0
            r4 = 0
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r6 = 0
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r8 = (kotlinx.coroutines.channels.SendChannel) r8
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0064 }
            r11 = r14
            r14 = r13
            r13 = r8
            r8 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x00b7
        L_0x004b:
            r12 = 0
            r13 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r6 = 0
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            r7 = r5
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0064 }
            r8 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x0094
        L_0x0064:
            r2 = move-exception
            goto L_0x00d3
        L_0x0067:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r7 = r12
            r12 = 0
            r6 = 0
            r4 = r7
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch:{ all -> 0x00d0 }
            r11 = r14
            r14 = r12
            r12 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
        L_0x007d:
            r1.L$0 = r13     // Catch:{ all -> 0x00c8 }
            r1.L$1 = r7     // Catch:{ all -> 0x00c8 }
            r1.L$2 = r8     // Catch:{ all -> 0x00c8 }
            r4 = 1
            r1.label = r4     // Catch:{ all -> 0x00c8 }
            java.lang.Object r4 = r8.hasNext(r1)     // Catch:{ all -> 0x00c8 }
            if (r4 != r3) goto L_0x008d
            return r3
        L_0x008d:
            r11 = r8
            r8 = r13
            r13 = r14
            r14 = r0
            r0 = r1
            r1 = r4
            r4 = r11
        L_0x0094:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00c3 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00c3 }
            if (r1 == 0) goto L_0x00b9
            java.lang.Object r1 = r4.next()     // Catch:{ all -> 0x00c3 }
            r9 = 0
            r0.L$0 = r8     // Catch:{ all -> 0x00c3 }
            r0.L$1 = r7     // Catch:{ all -> 0x00c3 }
            r0.L$2 = r4     // Catch:{ all -> 0x00c3 }
            r10 = 2
            r0.label = r10     // Catch:{ all -> 0x00c3 }
            java.lang.Object r10 = r8.send(r1, r0)     // Catch:{ all -> 0x00c3 }
            if (r10 != r3) goto L_0x00b1
            return r3
        L_0x00b1:
            r1 = r0
            r0 = r14
            r14 = r13
            r13 = r8
            r8 = r4
            r4 = r9
        L_0x00b7:
            goto L_0x007d
        L_0x00b9:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00c3 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            return r8
        L_0x00c3:
            r1 = move-exception
            r11 = r2
            r2 = r1
            r1 = r11
            goto L_0x00d3
        L_0x00c8:
            r13 = move-exception
            r11 = r2
            r2 = r13
            r13 = r14
            r14 = r0
            r0 = r1
            r1 = r11
            goto L_0x00d3
        L_0x00d0:
            r2 = move-exception
            r13 = r12
            r12 = r3
        L_0x00d3:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00d6 }
        L_0x00d6:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toChannel(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.L$2 = r4;
        r0.label = 1;
        r8 = r4.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006c, code lost:
        if (r8 != r2) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006e, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006f, code lost:
        r11 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007e, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0080, code lost:
        r8.add(r5.next());
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0092, code lost:
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0095, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009b, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009c, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009d, code lost:
        r5 = r2;
        r2 = r1;
        r1 = r5;
        r5 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object toCollection(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r12, C r13, kotlin.coroutines.Continuation<? super C> r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toCollection$1
            r0.<init>(r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004a;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            r12 = 0
            r13 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            java.util.Collection r7 = (java.util.Collection) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0047 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x0078
        L_0x0047:
            r2 = move-exception
            goto L_0x00a6
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r6 = r12
            r12 = 0
            r5 = 0
            r4 = r6
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch:{ all -> 0x00a3 }
            r4 = r13
            r13 = r12
            r12 = r3
            r3 = r7
            r7 = r4
            r4 = r8
        L_0x005f:
            r0.L$0 = r7     // Catch:{ all -> 0x0047 }
            r0.L$1 = r6     // Catch:{ all -> 0x0047 }
            r0.L$2 = r4     // Catch:{ all -> 0x0047 }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x0047 }
            java.lang.Object r8 = r4.hasNext(r0)     // Catch:{ all -> 0x0047 }
            if (r8 != r2) goto L_0x006f
            return r2
        L_0x006f:
            r11 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r11
        L_0x0078:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x009c }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x009c }
            if (r1 == 0) goto L_0x0092
            java.lang.Object r1 = r5.next()     // Catch:{ all -> 0x009c }
            r9 = r1
            r10 = 0
            r8.add(r9)     // Catch:{ all -> 0x009c }
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x005f
        L_0x0092:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x009c }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            return r8
        L_0x009c:
            r1 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            r5 = r6
            r6 = r7
            goto L_0x00a6
        L_0x00a3:
            r2 = move-exception
            r13 = r12
            r12 = r3
        L_0x00a6:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00a9 }
        L_0x00a9:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toCollection(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.L$0 = r10;
        r2.L$1 = r9;
        r2.L$2 = r7;
        r2.label = 1;
        r11 = r7.hasNext(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0074, code lost:
        if (r11 != r0) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0076, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0077, code lost:
        r16 = r4;
        r4 = r3;
        r3 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0089, code lost:
        if (((java.lang.Boolean) r3).booleanValue() == false) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008b, code lost:
        r12 = (kotlin.Pair) r8.next();
        r11.put(r12.getFirst(), r12.getSecond());
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a8, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ab, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b1, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b3, code lost:
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r8 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b9, code lost:
        r0 = th;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <K, V, M extends java.util.Map<? super K, ? super V>> java.lang.Object toMap(kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>> r17, M r18, kotlin.coroutines.Continuation<? super M> r19) {
        /*
            r1 = r19
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$toMap$2
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            switch(r4) {
                case 0: goto L_0x004e;
                case 1: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x002f:
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.Object r7 = r2.L$2
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            r8 = 0
            java.lang.Object r9 = r2.L$1
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r10 = r2.L$0
            java.util.Map r10 = (java.util.Map) r10
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x004b }
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x0083
        L_0x004b:
            r0 = move-exception
            goto L_0x00be
        L_0x004e:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = r17
            r5 = r18
            r6 = 0
            r9 = r4
            r4 = 0
            r8 = 0
            r7 = r9
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r7.iterator()     // Catch:{ all -> 0x00bb }
            r7 = r5
            r5 = r4
            r4 = r6
            r6 = r10
            r10 = r7
            r7 = r11
        L_0x0067:
            r2.L$0 = r10     // Catch:{ all -> 0x00b9 }
            r2.L$1 = r9     // Catch:{ all -> 0x00b9 }
            r2.L$2 = r7     // Catch:{ all -> 0x00b9 }
            r11 = 1
            r2.label = r11     // Catch:{ all -> 0x00b9 }
            java.lang.Object r11 = r7.hasNext(r2)     // Catch:{ all -> 0x00b9 }
            if (r11 != r0) goto L_0x0077
            return r0
        L_0x0077:
            r16 = r4
            r4 = r3
            r3 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r16
        L_0x0083:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00b2 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00b2 }
            if (r3 == 0) goto L_0x00a8
            java.lang.Object r3 = r8.next()     // Catch:{ all -> 0x00b2 }
            r12 = r3
            kotlin.Pair r12 = (kotlin.Pair) r12     // Catch:{ all -> 0x00b2 }
            r13 = 0
            java.lang.Object r14 = r12.getFirst()     // Catch:{ all -> 0x00b2 }
            java.lang.Object r15 = r12.getSecond()     // Catch:{ all -> 0x00b2 }
            r11.put(r14, r15)     // Catch:{ all -> 0x00b2 }
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            goto L_0x0067
        L_0x00a8:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00b2 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9)
            return r11
        L_0x00b2:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r8 = r9
            r9 = r10
            goto L_0x00be
        L_0x00b9:
            r0 = move-exception
            goto L_0x00be
        L_0x00bb:
            r0 = move-exception
            r5 = r4
            r4 = r6
        L_0x00be:
            r6 = r0
            throw r0     // Catch:{ all -> 0x00c1 }
        L_0x00c1:
            r0 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.toMap(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel flatMap$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$flatMap$1(receiveChannel, function2, (Continuation<? super ChannelsKt__DeprecatedKt$flatMap$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel map$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.map(receiveChannel, coroutineContext, function2);
    }

    public static final <E, R> ReceiveChannel<R> map(ReceiveChannel<? extends E> $this$map, CoroutineContext context, Function2<? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$map), new ChannelsKt__DeprecatedKt$map$1($this$map, transform, (Continuation<? super ChannelsKt__DeprecatedKt$map$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel mapIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3);
    }

    public static final <E, R> ReceiveChannel<R> mapIndexed(ReceiveChannel<? extends E> $this$mapIndexed, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$mapIndexed), new ChannelsKt__DeprecatedKt$mapIndexed$1($this$mapIndexed, transform, (Continuation<? super ChannelsKt__DeprecatedKt$mapIndexed$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel mapIndexedNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filterNotNull(ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3));
    }

    public static /* synthetic */ ReceiveChannel mapNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filterNotNull(ChannelsKt.map(receiveChannel, coroutineContext, function2));
    }

    public static /* synthetic */ ReceiveChannel withIndex$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ProduceKt.produce$default(GlobalScope.INSTANCE, coroutineContext, 0, (CoroutineStart) null, ChannelsKt.consumes(receiveChannel), new ChannelsKt__DeprecatedKt$withIndex$1(receiveChannel, (Continuation<? super ChannelsKt__DeprecatedKt$withIndex$1>) null), 6, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel distinctBy$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.distinctBy(receiveChannel, coroutineContext, function2);
    }

    public static final <E, K> ReceiveChannel<E> distinctBy(ReceiveChannel<? extends E> $this$distinctBy, CoroutineContext context, Function2<? super E, ? super Continuation<? super K>, ? extends Object> selector) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumes($this$distinctBy), new ChannelsKt__DeprecatedKt$distinctBy$1($this$distinctBy, selector, (Continuation<? super ChannelsKt__DeprecatedKt$distinctBy$1>) null), 6, (Object) null);
    }

    public static final <E> Object toMutableSet(ReceiveChannel<? extends E> $this$toMutableSet, Continuation<? super Set<E>> $completion) {
        return ChannelsKt.toCollection($this$toMutableSet, new LinkedHashSet(), $completion);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0056, code lost:
        return r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object any(kotlinx.coroutines.channels.ReceiveChannel r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$any$1
            r0.<init>(r10)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003a;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002c:
            r9 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            r4 = 0
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0038 }
            r7 = r1
            goto L_0x0053
        L_0x0038:
            r2 = move-exception
            goto L_0x0058
        L_0x003a:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r9
            r9 = 0
            r4 = 0
            r5 = r3
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch:{ all -> 0x0057 }
            r0.L$0 = r3     // Catch:{ all -> 0x0057 }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x0057 }
            java.lang.Object r7 = r7.hasNext(r0)     // Catch:{ all -> 0x0057 }
            if (r7 != r2) goto L_0x0053
            return r2
        L_0x0053:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4)
            return r7
        L_0x0057:
            r2 = move-exception
        L_0x0058:
            r4 = r2
            throw r2     // Catch:{ all -> 0x005b }
        L_0x005b:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.any(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.L$0 = r9;
        r0.L$1 = r8;
        r0.L$2 = r6;
        r0.label = 1;
        r10 = r6.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0072, code lost:
        if (r10 != r2) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0074, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0075, code lost:
        r13 = r2;
        r2 = r1;
        r1 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r3;
        r3 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0085, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0087, code lost:
        r1 = r7.next();
        r10.element++;
        r1 = r2;
        r2 = r3;
        r3 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009a, code lost:
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009d, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a9, code lost:
        return kotlin.coroutines.jvm.internal.Boxing.boxInt(r10.element);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00aa, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ab, code lost:
        r3 = r2;
        r2 = r1;
        r1 = r3;
        r3 = r5;
        r7 = r8;
        r8 = r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object count(kotlinx.coroutines.channels.ReceiveChannel r14, kotlin.coroutines.Continuation r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$count$1
            r0.<init>(r15)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x004c;
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
            r3 = 0
            r5 = 0
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            r7 = 0
            java.lang.Object r8 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r9 = r0.L$0
            kotlin.jvm.internal.Ref$IntRef r9 = (kotlin.jvm.internal.Ref.IntRef) r9
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0049 }
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x007f
        L_0x0049:
            r2 = move-exception
            goto L_0x00b5
        L_0x004c:
            kotlin.ResultKt.throwOnFailure(r1)
            kotlin.jvm.internal.Ref$IntRef r3 = new kotlin.jvm.internal.Ref$IntRef
            r3.<init>()
            r5 = 0
            r8 = r14
            r14 = 0
            r7 = 0
            r6 = r8
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r6.iterator()     // Catch:{ all -> 0x00b2 }
            r6 = r3
            r3 = r14
            r14 = r5
            r5 = r9
            r9 = r6
            r6 = r10
        L_0x0066:
            r0.L$0 = r9     // Catch:{ all -> 0x0049 }
            r0.L$1 = r8     // Catch:{ all -> 0x0049 }
            r0.L$2 = r6     // Catch:{ all -> 0x0049 }
            r0.label = r4     // Catch:{ all -> 0x0049 }
            java.lang.Object r10 = r6.hasNext(r0)     // Catch:{ all -> 0x0049 }
            if (r10 != r2) goto L_0x0075
            return r2
        L_0x0075:
            r13 = r2
            r2 = r1
            r1 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r13
        L_0x007f:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00aa }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00aa }
            if (r1 == 0) goto L_0x009a
            java.lang.Object r1 = r7.next()     // Catch:{ all -> 0x00aa }
            r11 = 0
            int r12 = r10.element     // Catch:{ all -> 0x00aa }
            int r12 = r12 + r4
            r10.element = r12     // Catch:{ all -> 0x00aa }
            r1 = r2
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            goto L_0x0066
        L_0x009a:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00aa }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r8)
            int r14 = r10.element
            java.lang.Integer r14 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r14)
            return r14
        L_0x00aa:
            r1 = move-exception
            r3 = r2
            r2 = r1
            r1 = r3
            r3 = r5
            r7 = r8
            r8 = r9
            goto L_0x00b5
        L_0x00b2:
            r2 = move-exception
            r3 = r14
            r14 = r5
        L_0x00b5:
            r4 = r2
            throw r2     // Catch:{ all -> 0x00b8 }
        L_0x00b8:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.count(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008f, code lost:
        if (((java.lang.Boolean) r5).booleanValue() != false) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0091, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0094, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r3 = r8.next();
        r5 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009b, code lost:
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.L$2 = r5;
        r0.L$3 = r3;
        r0.label = 2;
        r8 = r5.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00aa, code lost:
        if (r8 != r2) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ac, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ad, code lost:
        r10 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00bc, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00be, code lost:
        r1 = r6.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c6, code lost:
        if (r8.compare(r4, r1) >= 0) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c8, code lost:
        r4 = r5;
        r5 = r3;
        r3 = r1;
        r1 = r2;
        r2 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d1, code lost:
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d9, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00dd, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00de, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00df, code lost:
        r6 = r7;
        r4 = r5;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e3, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e5, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e6, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object maxWith(kotlinx.coroutines.channels.ReceiveChannel r11, java.util.Comparator r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$maxWith$1
            r0.<init>(r13)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0067;
                case 1: goto L_0x004c;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            r12 = 0
            java.lang.Object r3 = r0.L$3
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            java.util.Comparator r7 = (java.util.Comparator) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0049 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x00b6
        L_0x0049:
            r11 = move-exception
            goto L_0x00ec
        L_0x004c:
            r12 = 0
            r11 = 0
            java.lang.Object r3 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r0.L$0
            java.util.Comparator r6 = (java.util.Comparator) r6
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0063 }
            r8 = r3
            r3 = r4
            r7 = r6
            r6 = r5
            r5 = r1
            goto L_0x0089
        L_0x0063:
            r11 = move-exception
            r6 = r5
            goto L_0x00ec
        L_0x0067:
            kotlin.ResultKt.throwOnFailure(r1)
            r6 = r12
            r12 = 0
            r3 = 0
            r5 = r11
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r5.iterator()     // Catch:{ all -> 0x00e8 }
            r0.L$0 = r6     // Catch:{ all -> 0x00e8 }
            r0.L$1 = r11     // Catch:{ all -> 0x00e8 }
            r0.L$2 = r8     // Catch:{ all -> 0x00e8 }
            r5 = 1
            r0.label = r5     // Catch:{ all -> 0x00e8 }
            java.lang.Object r5 = r8.hasNext(r0)     // Catch:{ all -> 0x00e8 }
            if (r5 != r2) goto L_0x0085
            return r2
        L_0x0085:
            r10 = r6
            r6 = r11
            r11 = r7
            r7 = r10
        L_0x0089:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ all -> 0x00e5 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x00e5 }
            if (r5 != 0) goto L_0x0095
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            return r4
        L_0x0095:
            r4 = r3
            java.lang.Object r3 = r8.next()     // Catch:{ all -> 0x00e3 }
            r5 = r8
        L_0x009b:
            r0.L$0 = r7     // Catch:{ all -> 0x00e3 }
            r0.L$1 = r6     // Catch:{ all -> 0x00e3 }
            r0.L$2 = r5     // Catch:{ all -> 0x00e3 }
            r0.L$3 = r3     // Catch:{ all -> 0x00e3 }
            r8 = 2
            r0.label = r8     // Catch:{ all -> 0x00e3 }
            java.lang.Object r8 = r5.hasNext(r0)     // Catch:{ all -> 0x00e3 }
            if (r8 != r2) goto L_0x00ad
            return r2
        L_0x00ad:
            r10 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r10
        L_0x00b6:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00de }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00de }
            if (r1 == 0) goto L_0x00d9
            java.lang.Object r1 = r6.next()     // Catch:{ all -> 0x00de }
            int r9 = r8.compare(r4, r1)     // Catch:{ all -> 0x00de }
            if (r9 >= 0) goto L_0x00d1
            r4 = r5
            r5 = r3
            r3 = r1
            r1 = r2
            r2 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x009b
        L_0x00d1:
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x009b
        L_0x00d9:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            return r4
        L_0x00de:
            r11 = move-exception
            r6 = r7
            r4 = r5
            r1 = r2
            goto L_0x00ec
        L_0x00e3:
            r11 = move-exception
            goto L_0x00ec
        L_0x00e5:
            r11 = move-exception
            r4 = r3
            goto L_0x00ec
        L_0x00e8:
            r2 = move-exception
            r6 = r11
            r11 = r2
            r4 = r3
        L_0x00ec:
            r2 = r11
            throw r11     // Catch:{ all -> 0x00ef }
        L_0x00ef:
            r11 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r2)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.maxWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008f, code lost:
        if (((java.lang.Boolean) r5).booleanValue() != false) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0091, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0094, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r3 = r8.next();
        r5 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009b, code lost:
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.L$2 = r5;
        r0.L$3 = r3;
        r0.label = 2;
        r8 = r5.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00aa, code lost:
        if (r8 != r2) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ac, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ad, code lost:
        r10 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00bc, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00be, code lost:
        r1 = r6.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c6, code lost:
        if (r8.compare(r4, r1) <= 0) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c8, code lost:
        r4 = r5;
        r5 = r3;
        r3 = r1;
        r1 = r2;
        r2 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d1, code lost:
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d9, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00dd, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00de, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00df, code lost:
        r6 = r7;
        r4 = r5;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e3, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e5, code lost:
        r11 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e6, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object minWith(kotlinx.coroutines.channels.ReceiveChannel r11, java.util.Comparator r12, kotlin.coroutines.Continuation r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$minWith$1
            r0.<init>(r13)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0067;
                case 1: goto L_0x004c;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            r12 = 0
            java.lang.Object r3 = r0.L$3
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            java.util.Comparator r7 = (java.util.Comparator) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0049 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x00b6
        L_0x0049:
            r11 = move-exception
            goto L_0x00ec
        L_0x004c:
            r12 = 0
            r11 = 0
            java.lang.Object r3 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r6 = r0.L$0
            java.util.Comparator r6 = (java.util.Comparator) r6
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0063 }
            r8 = r3
            r3 = r4
            r7 = r6
            r6 = r5
            r5 = r1
            goto L_0x0089
        L_0x0063:
            r11 = move-exception
            r6 = r5
            goto L_0x00ec
        L_0x0067:
            kotlin.ResultKt.throwOnFailure(r1)
            r6 = r12
            r12 = 0
            r3 = 0
            r5 = r11
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r5.iterator()     // Catch:{ all -> 0x00e8 }
            r0.L$0 = r6     // Catch:{ all -> 0x00e8 }
            r0.L$1 = r11     // Catch:{ all -> 0x00e8 }
            r0.L$2 = r8     // Catch:{ all -> 0x00e8 }
            r5 = 1
            r0.label = r5     // Catch:{ all -> 0x00e8 }
            java.lang.Object r5 = r8.hasNext(r0)     // Catch:{ all -> 0x00e8 }
            if (r5 != r2) goto L_0x0085
            return r2
        L_0x0085:
            r10 = r6
            r6 = r11
            r11 = r7
            r7 = r10
        L_0x0089:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ all -> 0x00e5 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x00e5 }
            if (r5 != 0) goto L_0x0095
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            return r4
        L_0x0095:
            r4 = r3
            java.lang.Object r3 = r8.next()     // Catch:{ all -> 0x00e3 }
            r5 = r8
        L_0x009b:
            r0.L$0 = r7     // Catch:{ all -> 0x00e3 }
            r0.L$1 = r6     // Catch:{ all -> 0x00e3 }
            r0.L$2 = r5     // Catch:{ all -> 0x00e3 }
            r0.L$3 = r3     // Catch:{ all -> 0x00e3 }
            r8 = 2
            r0.label = r8     // Catch:{ all -> 0x00e3 }
            java.lang.Object r8 = r5.hasNext(r0)     // Catch:{ all -> 0x00e3 }
            if (r8 != r2) goto L_0x00ad
            return r2
        L_0x00ad:
            r10 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r10
        L_0x00b6:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00de }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00de }
            if (r1 == 0) goto L_0x00d9
            java.lang.Object r1 = r6.next()     // Catch:{ all -> 0x00de }
            int r9 = r8.compare(r4, r1)     // Catch:{ all -> 0x00de }
            if (r9 <= 0) goto L_0x00d1
            r4 = r5
            r5 = r3
            r3 = r1
            r1 = r2
            r2 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x009b
        L_0x00d1:
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x009b
        L_0x00d9:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            return r4
        L_0x00de:
            r11 = move-exception
            r6 = r7
            r4 = r5
            r1 = r2
            goto L_0x00ec
        L_0x00e3:
            r11 = move-exception
            goto L_0x00ec
        L_0x00e5:
            r11 = move-exception
            r4 = r3
            goto L_0x00ec
        L_0x00e8:
            r2 = move-exception
            r6 = r11
            r11 = r2
            r4 = r3
        L_0x00ec:
            r2 = r11
            throw r11     // Catch:{ all -> 0x00ef }
        L_0x00ef:
            r11 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r2)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.minWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        if (((java.lang.Boolean) r8).booleanValue() != false) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005d, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005e, code lost:
        r4 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0062, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0065, code lost:
        return r4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object none(kotlinx.coroutines.channels.ReceiveChannel r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$none$1
            r0.<init>(r10)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002d:
            r9 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            r5 = 0
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0039 }
            r8 = r1
            goto L_0x0054
        L_0x0039:
            r2 = move-exception
            goto L_0x0067
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r9
            r9 = 0
            r5 = 0
            r6 = r3
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch:{ all -> 0x0066 }
            r0.L$0 = r3     // Catch:{ all -> 0x0066 }
            r0.label = r4     // Catch:{ all -> 0x0066 }
            java.lang.Object r8 = r8.hasNext(r0)     // Catch:{ all -> 0x0066 }
            if (r8 != r2) goto L_0x0053
            return r2
        L_0x0053:
            r2 = r7
        L_0x0054:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch:{ all -> 0x0039 }
            boolean r6 = r8.booleanValue()     // Catch:{ all -> 0x0039 }
            if (r6 != 0) goto L_0x005d
            goto L_0x005e
        L_0x005d:
            r4 = 0
        L_0x005e:
            java.lang.Boolean r4 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)     // Catch:{ all -> 0x0039 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r5)
            return r4
        L_0x0066:
            r2 = move-exception
        L_0x0067:
            r4 = r2
            throw r2     // Catch:{ all -> 0x006a }
        L_0x006a:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt.none(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public static final Pair zip$lambda$23$ChannelsKt__DeprecatedKt(Object t1, Object t2) {
        return TuplesKt.to(t1, t2);
    }

    public static /* synthetic */ ReceiveChannel zip$default(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.zip(receiveChannel, receiveChannel2, coroutineContext, function2);
    }

    public static final <E, R, V> ReceiveChannel<V> zip(ReceiveChannel<? extends E> $this$zip, ReceiveChannel<? extends R> other, CoroutineContext context, Function2<? super E, ? super R, ? extends V> transform) {
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, (CoroutineStart) null, ChannelsKt.consumesAll($this$zip, other), new ChannelsKt__DeprecatedKt$zip$2(other, $this$zip, transform, (Continuation<? super ChannelsKt__DeprecatedKt$zip$2>) null), 6, (Object) null);
    }

    public static final Function1<Throwable, Unit> consumes(ReceiveChannel<?> $this$consumes) {
        return new ChannelsKt__DeprecatedKt$$ExternalSyntheticLambda0($this$consumes);
    }

    /* access modifiers changed from: private */
    public static final Unit consumes$lambda$24$ChannelsKt__DeprecatedKt(ReceiveChannel $this_consumes, Throwable cause) {
        ChannelsKt.cancelConsumed($this_consumes, cause);
        return Unit.INSTANCE;
    }
}
