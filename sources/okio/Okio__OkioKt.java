package okio;

import java.io.Closeable;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0001\u001a\n\u0010\u0003\u001a\u00020\u0005*\u00020\u0006\u001aA\u0010\u0007\u001a\u0002H\b\"\u0010\b\u0000\u0010\t*\n\u0018\u00010\nj\u0004\u0018\u0001`\u000b\"\u0004\b\u0001\u0010\b*\u0002H\t2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\rH\bø\u0001\u0000¢\u0006\u0002\u0010\u000e\u0002\u0007\n\u0005\b20\u0001¨\u0006\u000f"}, d2 = {"blackholeSink", "Lokio/Sink;", "blackhole", "buffer", "Lokio/BufferedSink;", "Lokio/BufferedSource;", "Lokio/Source;", "use", "R", "T", "Ljava/io/Closeable;", "Lokio/Closeable;", "block", "Lkotlin/Function1;", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "okio"}, k = 5, mv = {1, 8, 0}, xi = 48, xs = "okio/Okio")
/* compiled from: Okio.kt */
final /* synthetic */ class Okio__OkioKt {
    public static final BufferedSource buffer(Source $this$buffer) {
        Intrinsics.checkNotNullParameter($this$buffer, "<this>");
        return new RealBufferedSource($this$buffer);
    }

    public static final BufferedSink buffer(Sink $this$buffer) {
        Intrinsics.checkNotNullParameter($this$buffer, "<this>");
        return new RealBufferedSink($this$buffer);
    }

    public static final Sink blackhole() {
        return new BlackholeSink();
    }

    public static final <T extends Closeable, R> R use(T $this$use, Function1<? super T, ? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Object result = null;
        Throwable thrown = null;
        try {
            result = block.invoke($this$use);
            if ($this$use != null) {
                try {
                    $this$use.close();
                } catch (Throwable t) {
                    thrown = t;
                }
            }
        } catch (Throwable t2) {
            ExceptionsKt.addSuppressed(thrown, t2);
        }
        if (thrown == null) {
            Intrinsics.checkNotNull(result);
            return result;
        }
        throw thrown;
    }
}
