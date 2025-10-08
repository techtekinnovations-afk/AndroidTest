package androidx.datastore.core;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "Landroidx/datastore/core/InterProcessCoordinator;", "T", "it", "Ljava/io/File;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: MultiProcessDataStoreFactory.android.kt */
final class MultiProcessDataStoreFactory$create$1 extends Lambda implements Function1<File, InterProcessCoordinator> {
    final /* synthetic */ CoroutineScope $scope;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MultiProcessDataStoreFactory$create$1(CoroutineScope coroutineScope) {
        super(1);
        this.$scope = coroutineScope;
    }

    public final InterProcessCoordinator invoke(File it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return new MultiProcessCoordinator(this.$scope.getCoroutineContext(), it);
    }
}
