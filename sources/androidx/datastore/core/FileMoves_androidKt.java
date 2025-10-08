package androidx.datastore.core;

import android.os.Build;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0000¨\u0006\u0004"}, d2 = {"atomicMoveTo", "", "Ljava/io/File;", "toFile", "datastore-core_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: FileMoves.android.kt */
public final class FileMoves_androidKt {
    public static final boolean atomicMoveTo(File $this$atomicMoveTo, File toFile) {
        Intrinsics.checkNotNullParameter($this$atomicMoveTo, "<this>");
        Intrinsics.checkNotNullParameter(toFile, "toFile");
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.INSTANCE.move($this$atomicMoveTo, toFile);
        }
        return $this$atomicMoveTo.renameTo(toFile);
    }
}
