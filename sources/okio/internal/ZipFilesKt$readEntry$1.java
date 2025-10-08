package okio.internal;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import okio.BufferedSource;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "headerId", "", "dataSize", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: ZipFiles.kt */
final class ZipFilesKt$readEntry$1 extends Lambda implements Function2<Integer, Long, Unit> {
    final /* synthetic */ Ref.LongRef $compressedSize;
    final /* synthetic */ Ref.BooleanRef $hasZip64Extra;
    final /* synthetic */ Ref.LongRef $offset;
    final /* synthetic */ long $requiredZip64ExtraSize;
    final /* synthetic */ Ref.LongRef $size;
    final /* synthetic */ BufferedSource $this_readEntry;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ZipFilesKt$readEntry$1(Ref.BooleanRef booleanRef, long j, Ref.LongRef longRef, BufferedSource bufferedSource, Ref.LongRef longRef2, Ref.LongRef longRef3) {
        super(2);
        this.$hasZip64Extra = booleanRef;
        this.$requiredZip64ExtraSize = j;
        this.$size = longRef;
        this.$this_readEntry = bufferedSource;
        this.$compressedSize = longRef2;
        this.$offset = longRef3;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
        invoke(((Number) p1).intValue(), ((Number) p2).longValue());
        return Unit.INSTANCE;
    }

    public final void invoke(int headerId, long dataSize) {
        if (headerId != 1) {
            return;
        }
        if (!this.$hasZip64Extra.element) {
            this.$hasZip64Extra.element = true;
            if (dataSize >= this.$requiredZip64ExtraSize) {
                this.$size.element = this.$size.element == 4294967295L ? this.$this_readEntry.readLongLe() : this.$size.element;
                long j = 0;
                this.$compressedSize.element = this.$compressedSize.element == 4294967295L ? this.$this_readEntry.readLongLe() : 0;
                Ref.LongRef longRef = this.$offset;
                if (this.$offset.element == 4294967295L) {
                    j = this.$this_readEntry.readLongLe();
                }
                longRef.element = j;
                return;
            }
            throw new IOException("bad zip: zip64 extra too short");
        }
        throw new IOException("bad zip: zip64 extra repeated");
    }
}
