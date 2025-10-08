package androidx.datastore;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import okio.Path;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lokio/Path;", "T", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: DataStoreDelegate.android.kt */
final class DataStoreSingletonDelegate$getValue$1$1 extends Lambda implements Function0<Path> {
    final /* synthetic */ Context $applicationContext;
    final /* synthetic */ DataStoreSingletonDelegate<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreSingletonDelegate$getValue$1$1(Context context, DataStoreSingletonDelegate<T> dataStoreSingletonDelegate) {
        super(0);
        this.$applicationContext = context;
        this.this$0 = dataStoreSingletonDelegate;
    }

    public final Path invoke() {
        Path.Companion companion = Path.Companion;
        Context context = this.$applicationContext;
        Intrinsics.checkNotNullExpressionValue(context, "applicationContext");
        String absolutePath = DataStoreFile.dataStoreFile(context, this.this$0.fileName).getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "applicationContext.dataS…le(fileName).absolutePath");
        return Path.Companion.get$default(companion, absolutePath, false, 1, (Object) null);
    }
}
