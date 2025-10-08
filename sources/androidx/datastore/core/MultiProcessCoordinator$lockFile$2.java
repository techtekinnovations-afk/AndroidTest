package androidx.datastore.core;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Ljava/io/File;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: MultiProcessCoordinator.android.kt */
final class MultiProcessCoordinator$lockFile$2 extends Lambda implements Function0<File> {
    final /* synthetic */ MultiProcessCoordinator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MultiProcessCoordinator$lockFile$2(MultiProcessCoordinator multiProcessCoordinator) {
        super(0);
        this.this$0 = multiProcessCoordinator;
    }

    public final File invoke() {
        File lockFile = this.this$0.fileWithSuffix(this.this$0.LOCK_SUFFIX);
        this.this$0.createIfNotExists(lockFile);
        return lockFile;
    }
}
