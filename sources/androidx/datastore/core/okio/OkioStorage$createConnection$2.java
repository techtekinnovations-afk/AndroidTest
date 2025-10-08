package androidx.datastore.core.okio;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "T", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: OkioStorage.kt */
final class OkioStorage$createConnection$2 extends Lambda implements Function0<Unit> {
    final /* synthetic */ OkioStorage<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    OkioStorage$createConnection$2(OkioStorage<T> okioStorage) {
        super(0);
        this.this$0 = okioStorage;
    }

    public final void invoke() {
        Synchronizer this_$iv = OkioStorage.Companion.getActiveFilesLock();
        OkioStorage<T> okioStorage = this.this$0;
        synchronized (this_$iv) {
            OkioStorage.Companion.getActiveFiles$datastore_core_okio().remove(okioStorage.getCanonicalPath().toString());
            Unit unit = Unit.INSTANCE;
        }
    }
}
