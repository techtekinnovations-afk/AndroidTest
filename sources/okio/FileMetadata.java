package okio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KClasses;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\u0018\b\u0002\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00010\r¢\u0006\u0002\u0010\u000fJu\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0018\b\u0002\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00010\r¢\u0006\u0002\u0010\u001cJ'\u0010\u001d\u001a\u0004\u0018\u0001H\u001e\"\b\b\u0000\u0010\u001e*\u00020\u00012\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u001e0\u000e¢\u0006\u0002\u0010 J\b\u0010!\u001a\u00020\"H\u0016R\u0015\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R!\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0015R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0016\u0010\u0011R\u0015\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0017\u0010\u0011R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0018\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006#"}, d2 = {"Lokio/FileMetadata;", "", "isRegularFile", "", "isDirectory", "symlinkTarget", "Lokio/Path;", "size", "", "createdAtMillis", "lastModifiedAtMillis", "lastAccessedAtMillis", "extras", "", "Lkotlin/reflect/KClass;", "(ZZLokio/Path;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/Map;)V", "getCreatedAtMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getExtras", "()Ljava/util/Map;", "()Z", "getLastAccessedAtMillis", "getLastModifiedAtMillis", "getSize", "getSymlinkTarget", "()Lokio/Path;", "copy", "(ZZLokio/Path;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/Map;)Lokio/FileMetadata;", "extra", "T", "type", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "toString", "", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: FileMetadata.kt */
public final class FileMetadata {
    private final Long createdAtMillis;
    private final Map<KClass<?>, Object> extras;
    private final boolean isDirectory;
    private final boolean isRegularFile;
    private final Long lastAccessedAtMillis;
    private final Long lastModifiedAtMillis;
    private final Long size;
    private final Path symlinkTarget;

    public FileMetadata() {
        this(false, false, (Path) null, (Long) null, (Long) null, (Long) null, (Long) null, (Map) null, 255, (DefaultConstructorMarker) null);
    }

    public FileMetadata(boolean isRegularFile2, boolean isDirectory2, Path symlinkTarget2, Long size2, Long createdAtMillis2, Long lastModifiedAtMillis2, Long lastAccessedAtMillis2, Map<KClass<?>, ? extends Object> extras2) {
        Intrinsics.checkNotNullParameter(extras2, "extras");
        this.isRegularFile = isRegularFile2;
        this.isDirectory = isDirectory2;
        this.symlinkTarget = symlinkTarget2;
        this.size = size2;
        this.createdAtMillis = createdAtMillis2;
        this.lastModifiedAtMillis = lastModifiedAtMillis2;
        this.lastAccessedAtMillis = lastAccessedAtMillis2;
        this.extras = MapsKt.toMap(extras2);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ FileMetadata(boolean r2, boolean r3, okio.Path r4, java.lang.Long r5, java.lang.Long r6, java.lang.Long r7, java.lang.Long r8, java.util.Map r9, int r10, kotlin.jvm.internal.DefaultConstructorMarker r11) {
        /*
            r1 = this;
            r11 = r10 & 1
            r0 = 0
            if (r11 == 0) goto L_0x0006
            r2 = r0
        L_0x0006:
            r11 = r10 & 2
            if (r11 == 0) goto L_0x000b
            r3 = r0
        L_0x000b:
            r11 = r10 & 4
            r0 = 0
            if (r11 == 0) goto L_0x0011
            r4 = r0
        L_0x0011:
            r11 = r10 & 8
            if (r11 == 0) goto L_0x0016
            r5 = r0
        L_0x0016:
            r11 = r10 & 16
            if (r11 == 0) goto L_0x001b
            r6 = r0
        L_0x001b:
            r11 = r10 & 32
            if (r11 == 0) goto L_0x0020
            r7 = r0
        L_0x0020:
            r11 = r10 & 64
            if (r11 == 0) goto L_0x0025
            r8 = r0
        L_0x0025:
            r10 = r10 & 128(0x80, float:1.794E-43)
            if (r10 == 0) goto L_0x002f
            java.util.Map r9 = kotlin.collections.MapsKt.emptyMap()
            r10 = r9
            goto L_0x0030
        L_0x002f:
            r10 = r9
        L_0x0030:
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.FileMetadata.<init>(boolean, boolean, okio.Path, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Map, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean isRegularFile() {
        return this.isRegularFile;
    }

    public final boolean isDirectory() {
        return this.isDirectory;
    }

    public final Path getSymlinkTarget() {
        return this.symlinkTarget;
    }

    public final Long getSize() {
        return this.size;
    }

    public final Long getCreatedAtMillis() {
        return this.createdAtMillis;
    }

    public final Long getLastModifiedAtMillis() {
        return this.lastModifiedAtMillis;
    }

    public final Long getLastAccessedAtMillis() {
        return this.lastAccessedAtMillis;
    }

    public final Map<KClass<?>, Object> getExtras() {
        return this.extras;
    }

    public final <T> T extra(KClass<? extends T> type) {
        Intrinsics.checkNotNullParameter(type, "type");
        Object value = this.extras.get(type);
        if (value == null) {
            return null;
        }
        return KClasses.cast(type, value);
    }

    public static /* synthetic */ FileMetadata copy$default(FileMetadata fileMetadata, boolean z, boolean z2, Path path, Long l, Long l2, Long l3, Long l4, Map map, int i, Object obj) {
        Map<KClass<?>, Object> map2;
        if ((i & 1) != 0) {
            z = fileMetadata.isRegularFile;
        }
        if ((i & 2) != 0) {
            z2 = fileMetadata.isDirectory;
        }
        if ((i & 4) != 0) {
            path = fileMetadata.symlinkTarget;
        }
        if ((i & 8) != 0) {
            l = fileMetadata.size;
        }
        if ((i & 16) != 0) {
            l2 = fileMetadata.createdAtMillis;
        }
        if ((i & 32) != 0) {
            l3 = fileMetadata.lastModifiedAtMillis;
        }
        if ((i & 64) != 0) {
            l4 = fileMetadata.lastAccessedAtMillis;
        }
        if ((i & 128) != 0) {
            map2 = fileMetadata.extras;
        } else {
            map2 = map;
        }
        Long l5 = l3;
        Long l6 = l4;
        Long l7 = l;
        Long l8 = l2;
        return fileMetadata.copy(z, z2, path, l7, l8, l5, l6, map2);
    }

    public final FileMetadata copy(boolean isRegularFile2, boolean isDirectory2, Path symlinkTarget2, Long size2, Long createdAtMillis2, Long lastModifiedAtMillis2, Long lastAccessedAtMillis2, Map<KClass<?>, ? extends Object> extras2) {
        Map<KClass<?>, ? extends Object> map = extras2;
        Intrinsics.checkNotNullParameter(map, "extras");
        return new FileMetadata(isRegularFile2, isDirectory2, symlinkTarget2, size2, createdAtMillis2, lastModifiedAtMillis2, lastAccessedAtMillis2, map);
    }

    public String toString() {
        List fields = new ArrayList();
        if (this.isRegularFile) {
            fields.add("isRegularFile");
        }
        if (this.isDirectory) {
            fields.add("isDirectory");
        }
        if (this.size != null) {
            fields.add("byteCount=" + this.size);
        }
        if (this.createdAtMillis != null) {
            fields.add("createdAt=" + this.createdAtMillis);
        }
        if (this.lastModifiedAtMillis != null) {
            fields.add("lastModifiedAt=" + this.lastModifiedAtMillis);
        }
        if (this.lastAccessedAtMillis != null) {
            fields.add("lastAccessedAt=" + this.lastAccessedAtMillis);
        }
        if (!this.extras.isEmpty()) {
            fields.add("extras=" + this.extras);
        }
        return CollectionsKt.joinToString$default(fields, ", ", "FileMetadata(", ")", 0, (CharSequence) null, (Function1) null, 56, (Object) null);
    }
}
