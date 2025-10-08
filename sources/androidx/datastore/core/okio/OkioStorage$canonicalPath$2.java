package androidx.datastore.core.okio;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import okio.Path;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lokio/Path;", "T", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: OkioStorage.kt */
final class OkioStorage$canonicalPath$2 extends Lambda implements Function0<Path> {
    final /* synthetic */ OkioStorage<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    OkioStorage$canonicalPath$2(OkioStorage<T> okioStorage) {
        super(0);
        this.this$0 = okioStorage;
    }

    public final Path invoke() {
        Path path = (Path) this.this$0.producePath.invoke();
        boolean isAbsolute = path.isAbsolute();
        OkioStorage<T> okioStorage = this.this$0;
        if (isAbsolute) {
            return path.normalized();
        }
        throw new IllegalStateException(("OkioStorage requires absolute paths, but did not get an absolute path from producePath = " + okioStorage.producePath + ", instead got " + path).toString());
    }
}
