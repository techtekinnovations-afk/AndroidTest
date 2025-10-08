package androidx.datastore.preferences.core;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import okio.Path;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Ljava/io/File;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: PreferenceDataStoreFactory.jvmAndroid.kt */
final class PreferenceDataStoreFactory$createWithPath$1 extends Lambda implements Function0<File> {
    final /* synthetic */ Function0<Path> $produceFile;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PreferenceDataStoreFactory$createWithPath$1(Function0<Path> function0) {
        super(0);
        this.$produceFile = function0;
    }

    public final File invoke() {
        return this.$produceFile.invoke().toFile();
    }
}
