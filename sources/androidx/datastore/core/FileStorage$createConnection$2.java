package androidx.datastore.core;

import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "T", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: FileStorage.kt */
final class FileStorage$createConnection$2 extends Lambda implements Function0<Unit> {
    final /* synthetic */ File $file;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FileStorage$createConnection$2(File file) {
        super(0);
        this.$file = file;
    }

    public final void invoke() {
        Object activeFilesLock$datastore_core_release = FileStorage.Companion.getActiveFilesLock$datastore_core_release();
        File file = this.$file;
        synchronized (activeFilesLock$datastore_core_release) {
            FileStorage.Companion.getActiveFiles$datastore_core_release().remove(file.getAbsolutePath());
            Unit unit = Unit.INSTANCE;
        }
    }
}
