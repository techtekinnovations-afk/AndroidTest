package androidx.datastore.core;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a9\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0001*\u0002H\u00022\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u0005H\bø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0007"}, d2 = {"use", "R", "T", "Landroidx/datastore/core/Closeable;", "block", "Lkotlin/Function1;", "(Landroidx/datastore/core/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "datastore-core_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: Closeable.kt */
public final class CloseableKt {
    public static final <T extends Closeable, R> R use(T $this$use, Function1<? super T, ? extends R> block) {
        Intrinsics.checkNotNullParameter($this$use, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        Throwable thrown = null;
        try {
            R invoke = block.invoke($this$use);
            try {
                $this$use.close();
            } catch (Throwable t) {
                thrown = t;
            }
            if (thrown == null) {
                return invoke;
            }
            throw thrown;
        } catch (Throwable t2) {
            ExceptionsKt.addSuppressed(thrown, t2);
        }
        throw thrown;
    }
}
