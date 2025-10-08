package okio.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Path;

@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0006\n\u0002\u0010\u0005\n\u0000\u001a\u0015\u0010\u0014\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000eH\b\u001a\u0017\u0010\u0016\u001a\u00020\u0017*\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0018H\b\u001a\r\u0010\u0019\u001a\u00020\r*\u00020\u000eH\b\u001a\r\u0010\u001a\u001a\u00020\u0017*\u00020\u000eH\b\u001a\r\u0010\u001b\u001a\u00020\u0017*\u00020\u000eH\b\u001a\r\u0010\u001c\u001a\u00020\u0017*\u00020\u000eH\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u000eH\b\u001a\r\u0010\u001f\u001a\u00020\u0001*\u00020\u000eH\b\u001a\r\u0010 \u001a\u00020\u000e*\u00020\u000eH\b\u001a\u000f\u0010!\u001a\u0004\u0018\u00010\u000e*\u00020\u000eH\b\u001a\u0015\u0010\"\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000eH\b\u001a\u001d\u0010#\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u0017H\b\u001a\u001d\u0010#\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010$\u001a\u00020&2\u0006\u0010%\u001a\u00020\u0017H\b\u001a\u001d\u0010#\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0017H\b\u001a\u001c\u0010#\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u0017H\u0000\u001a\u000f\u0010'\u001a\u0004\u0018\u00010\u000e*\u00020\u000eH\b\u001a\u0013\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001e0)*\u00020\u000eH\b\u001a\u0013\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010)*\u00020\u000eH\b\u001a\u0014\u0010+\u001a\u00020\u000e*\u00020\u001e2\u0006\u0010%\u001a\u00020\u0017H\u0000\u001a\r\u0010,\u001a\u00020\u001e*\u00020\u000eH\b\u001a\u0014\u0010-\u001a\u0004\u0018\u00010.*\u00020\u000eH\b¢\u0006\u0002\u0010/\u001a\f\u00100\u001a\u00020\u0017*\u00020\u000eH\u0002\u001a\f\u00101\u001a\u00020\r*\u00020\u000eH\u0002\u001a\u0014\u00102\u001a\u00020\u0017*\u00020&2\u0006\u0010\u0011\u001a\u00020\u0001H\u0002\u001a\u0014\u00103\u001a\u00020\u000e*\u00020&2\u0006\u0010%\u001a\u00020\u0017H\u0000\u001a\f\u00104\u001a\u00020\u0001*\u000205H\u0002\u001a\f\u00104\u001a\u00020\u0001*\u00020\u001eH\u0002\"\u0016\u0010\u0000\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0003\"\u0016\u0010\u0006\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0003\"\u0016\u0010\b\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\t\u0010\u0003\"\u0016\u0010\n\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\u0003\"\u0018\u0010\f\u001a\u00020\r*\u00020\u000e8BX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0001*\u00020\u000e8BX\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u00066"}, d2 = {"ANY_SLASH", "Lokio/ByteString;", "getANY_SLASH$annotations", "()V", "BACKSLASH", "getBACKSLASH$annotations", "DOT", "getDOT$annotations", "DOT_DOT", "getDOT_DOT$annotations", "SLASH", "getSLASH$annotations", "indexOfLastSlash", "", "Lokio/Path;", "getIndexOfLastSlash", "(Lokio/Path;)I", "slash", "getSlash", "(Lokio/Path;)Lokio/ByteString;", "commonCompareTo", "other", "commonEquals", "", "", "commonHashCode", "commonIsAbsolute", "commonIsRelative", "commonIsRoot", "commonName", "", "commonNameBytes", "commonNormalized", "commonParent", "commonRelativeTo", "commonResolve", "child", "normalize", "Lokio/Buffer;", "commonRoot", "commonSegments", "", "commonSegmentsBytes", "commonToPath", "commonToString", "commonVolumeLetter", "", "(Lokio/Path;)Ljava/lang/Character;", "lastSegmentIsDotDot", "rootLength", "startsWithVolumeLetterAndColon", "toPath", "toSlash", "", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* renamed from: okio.internal.-Path  reason: invalid class name */
/* compiled from: Path.kt */
public final class Path {
    private static final ByteString ANY_SLASH = ByteString.Companion.encodeUtf8("/\\");
    /* access modifiers changed from: private */
    public static final ByteString BACKSLASH = ByteString.Companion.encodeUtf8("\\");
    /* access modifiers changed from: private */
    public static final ByteString DOT = ByteString.Companion.encodeUtf8(".");
    /* access modifiers changed from: private */
    public static final ByteString DOT_DOT = ByteString.Companion.encodeUtf8("..");
    /* access modifiers changed from: private */
    public static final ByteString SLASH = ByteString.Companion.encodeUtf8("/");

    private static /* synthetic */ void getANY_SLASH$annotations() {
    }

    private static /* synthetic */ void getBACKSLASH$annotations() {
    }

    private static /* synthetic */ void getDOT$annotations() {
    }

    private static /* synthetic */ void getDOT_DOT$annotations() {
    }

    private static /* synthetic */ void getSLASH$annotations() {
    }

    public static final okio.Path commonRoot(okio.Path $this$commonRoot) {
        Intrinsics.checkNotNullParameter($this$commonRoot, "<this>");
        int rootLength = rootLength($this$commonRoot);
        if (rootLength == -1) {
            return null;
        }
        return new okio.Path($this$commonRoot.getBytes$okio().substring(0, rootLength));
    }

    public static final List<String> commonSegments(okio.Path $this$commonSegments) {
        Intrinsics.checkNotNullParameter($this$commonSegments, "<this>");
        okio.Path $this$commonSegmentsBytes$iv = $this$commonSegments;
        List result$iv = new ArrayList();
        int segmentStart$iv = rootLength($this$commonSegmentsBytes$iv);
        if (segmentStart$iv == -1) {
            segmentStart$iv = 0;
        } else if (segmentStart$iv < $this$commonSegmentsBytes$iv.getBytes$okio().size() && $this$commonSegmentsBytes$iv.getBytes$okio().getByte(segmentStart$iv) == 92) {
            segmentStart$iv++;
        }
        int size = $this$commonSegmentsBytes$iv.getBytes$okio().size();
        for (int i$iv = segmentStart$iv; i$iv < size; i$iv++) {
            if ($this$commonSegmentsBytes$iv.getBytes$okio().getByte(i$iv) == 47 || $this$commonSegmentsBytes$iv.getBytes$okio().getByte(i$iv) == 92) {
                result$iv.add($this$commonSegmentsBytes$iv.getBytes$okio().substring(segmentStart$iv, i$iv));
                segmentStart$iv = i$iv + 1;
            }
        }
        if (segmentStart$iv < $this$commonSegmentsBytes$iv.getBytes$okio().size()) {
            result$iv.add($this$commonSegmentsBytes$iv.getBytes$okio().substring(segmentStart$iv, $this$commonSegmentsBytes$iv.getBytes$okio().size()));
        }
        Iterable<ByteString> $this$map$iv = result$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (ByteString it : $this$map$iv) {
            destination$iv$iv.add(it.utf8());
        }
        return (List) destination$iv$iv;
    }

    public static final List<ByteString> commonSegmentsBytes(okio.Path $this$commonSegmentsBytes) {
        Intrinsics.checkNotNullParameter($this$commonSegmentsBytes, "<this>");
        List<ByteString> arrayList = new ArrayList<>();
        int segmentStart = rootLength($this$commonSegmentsBytes);
        if (segmentStart == -1) {
            segmentStart = 0;
        } else if (segmentStart < $this$commonSegmentsBytes.getBytes$okio().size() && $this$commonSegmentsBytes.getBytes$okio().getByte(segmentStart) == 92) {
            segmentStart++;
        }
        int size = $this$commonSegmentsBytes.getBytes$okio().size();
        for (int i = segmentStart; i < size; i++) {
            if ($this$commonSegmentsBytes.getBytes$okio().getByte(i) == 47 || $this$commonSegmentsBytes.getBytes$okio().getByte(i) == 92) {
                arrayList.add($this$commonSegmentsBytes.getBytes$okio().substring(segmentStart, i));
                segmentStart = i + 1;
            }
        }
        if (segmentStart < $this$commonSegmentsBytes.getBytes$okio().size()) {
            arrayList.add($this$commonSegmentsBytes.getBytes$okio().substring(segmentStart, $this$commonSegmentsBytes.getBytes$okio().size()));
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public static final int rootLength(okio.Path $this$rootLength) {
        if ($this$rootLength.getBytes$okio().size() == 0) {
            return -1;
        }
        boolean z = false;
        if ($this$rootLength.getBytes$okio().getByte(0) == 47) {
            return 1;
        }
        if ($this$rootLength.getBytes$okio().getByte(0) == 92) {
            if ($this$rootLength.getBytes$okio().size() <= 2 || $this$rootLength.getBytes$okio().getByte(1) != 92) {
                return 1;
            }
            int uncRootEnd = $this$rootLength.getBytes$okio().indexOf(BACKSLASH, 2);
            if (uncRootEnd == -1) {
                return $this$rootLength.getBytes$okio().size();
            }
            return uncRootEnd;
        } else if ($this$rootLength.getBytes$okio().size() <= 2 || $this$rootLength.getBytes$okio().getByte(1) != 58 || $this$rootLength.getBytes$okio().getByte(2) != 92) {
            return -1;
        } else {
            char c = (char) $this$rootLength.getBytes$okio().getByte(0);
            if ('a' <= c && c < '{') {
                return 3;
            }
            if ('A' <= c && c < '[') {
                z = true;
            }
            if (!z) {
                return -1;
            }
            return 3;
        }
    }

    public static final boolean commonIsAbsolute(okio.Path $this$commonIsAbsolute) {
        Intrinsics.checkNotNullParameter($this$commonIsAbsolute, "<this>");
        return rootLength($this$commonIsAbsolute) != -1;
    }

    public static final boolean commonIsRelative(okio.Path $this$commonIsRelative) {
        Intrinsics.checkNotNullParameter($this$commonIsRelative, "<this>");
        return rootLength($this$commonIsRelative) == -1;
    }

    public static final Character commonVolumeLetter(okio.Path $this$commonVolumeLetter) {
        Intrinsics.checkNotNullParameter($this$commonVolumeLetter, "<this>");
        boolean z = false;
        if (ByteString.indexOf$default($this$commonVolumeLetter.getBytes$okio(), SLASH, 0, 2, (Object) null) != -1 || $this$commonVolumeLetter.getBytes$okio().size() < 2 || $this$commonVolumeLetter.getBytes$okio().getByte(1) != 58) {
            return null;
        }
        char c = (char) $this$commonVolumeLetter.getBytes$okio().getByte(0);
        if (!('a' <= c && c < '{')) {
            if ('A' <= c && c < '[') {
                z = true;
            }
            if (!z) {
                return null;
            }
        }
        return Character.valueOf(c);
    }

    /* access modifiers changed from: private */
    public static final int getIndexOfLastSlash(okio.Path $this$indexOfLastSlash) {
        int lastSlash = ByteString.lastIndexOf$default($this$indexOfLastSlash.getBytes$okio(), SLASH, 0, 2, (Object) null);
        if (lastSlash != -1) {
            return lastSlash;
        }
        return ByteString.lastIndexOf$default($this$indexOfLastSlash.getBytes$okio(), BACKSLASH, 0, 2, (Object) null);
    }

    public static final ByteString commonNameBytes(okio.Path $this$commonNameBytes) {
        Intrinsics.checkNotNullParameter($this$commonNameBytes, "<this>");
        int lastSlash = getIndexOfLastSlash($this$commonNameBytes);
        if (lastSlash != -1) {
            return ByteString.substring$default($this$commonNameBytes.getBytes$okio(), lastSlash + 1, 0, 2, (Object) null);
        }
        if ($this$commonNameBytes.volumeLetter() == null || $this$commonNameBytes.getBytes$okio().size() != 2) {
            return $this$commonNameBytes.getBytes$okio();
        }
        return ByteString.EMPTY;
    }

    public static final String commonName(okio.Path $this$commonName) {
        Intrinsics.checkNotNullParameter($this$commonName, "<this>");
        return $this$commonName.nameBytes().utf8();
    }

    public static final okio.Path commonParent(okio.Path $this$commonParent) {
        Intrinsics.checkNotNullParameter($this$commonParent, "<this>");
        if (Intrinsics.areEqual((Object) $this$commonParent.getBytes$okio(), (Object) DOT) || Intrinsics.areEqual((Object) $this$commonParent.getBytes$okio(), (Object) SLASH) || Intrinsics.areEqual((Object) $this$commonParent.getBytes$okio(), (Object) BACKSLASH) || lastSegmentIsDotDot($this$commonParent)) {
            return null;
        }
        int lastSlash = getIndexOfLastSlash($this$commonParent);
        if (lastSlash != 2 || $this$commonParent.volumeLetter() == null) {
            if (lastSlash == 1 && $this$commonParent.getBytes$okio().startsWith(BACKSLASH)) {
                return null;
            }
            if (lastSlash != -1 || $this$commonParent.volumeLetter() == null) {
                if (lastSlash == -1) {
                    return new okio.Path(DOT);
                }
                if (lastSlash == 0) {
                    return new okio.Path(ByteString.substring$default($this$commonParent.getBytes$okio(), 0, 1, 1, (Object) null));
                }
                return new okio.Path(ByteString.substring$default($this$commonParent.getBytes$okio(), 0, lastSlash, 1, (Object) null));
            } else if ($this$commonParent.getBytes$okio().size() == 2) {
                return null;
            } else {
                return new okio.Path(ByteString.substring$default($this$commonParent.getBytes$okio(), 0, 2, 1, (Object) null));
            }
        } else if ($this$commonParent.getBytes$okio().size() == 3) {
            return null;
        } else {
            return new okio.Path(ByteString.substring$default($this$commonParent.getBytes$okio(), 0, 3, 1, (Object) null));
        }
    }

    /* access modifiers changed from: private */
    public static final boolean lastSegmentIsDotDot(okio.Path $this$lastSegmentIsDotDot) {
        if (!$this$lastSegmentIsDotDot.getBytes$okio().endsWith(DOT_DOT) || ($this$lastSegmentIsDotDot.getBytes$okio().size() != 2 && !$this$lastSegmentIsDotDot.getBytes$okio().rangeEquals($this$lastSegmentIsDotDot.getBytes$okio().size() - 3, SLASH, 0, 1) && !$this$lastSegmentIsDotDot.getBytes$okio().rangeEquals($this$lastSegmentIsDotDot.getBytes$okio().size() - 3, BACKSLASH, 0, 1))) {
            return false;
        }
        return true;
    }

    public static final boolean commonIsRoot(okio.Path $this$commonIsRoot) {
        Intrinsics.checkNotNullParameter($this$commonIsRoot, "<this>");
        return rootLength($this$commonIsRoot) == $this$commonIsRoot.getBytes$okio().size();
    }

    public static final okio.Path commonResolve(okio.Path $this$commonResolve, String child, boolean normalize) {
        Intrinsics.checkNotNullParameter($this$commonResolve, "<this>");
        Intrinsics.checkNotNullParameter(child, "child");
        return commonResolve($this$commonResolve, toPath(new Buffer().writeUtf8(child), false), normalize);
    }

    public static final okio.Path commonResolve(okio.Path $this$commonResolve, ByteString child, boolean normalize) {
        Intrinsics.checkNotNullParameter($this$commonResolve, "<this>");
        Intrinsics.checkNotNullParameter(child, "child");
        return commonResolve($this$commonResolve, toPath(new Buffer().write(child), false), normalize);
    }

    public static final okio.Path commonResolve(okio.Path $this$commonResolve, Buffer child, boolean normalize) {
        Intrinsics.checkNotNullParameter($this$commonResolve, "<this>");
        Intrinsics.checkNotNullParameter(child, "child");
        return commonResolve($this$commonResolve, toPath(child, false), normalize);
    }

    public static final okio.Path commonResolve(okio.Path $this$commonResolve, okio.Path child, boolean normalize) {
        Intrinsics.checkNotNullParameter($this$commonResolve, "<this>");
        Intrinsics.checkNotNullParameter(child, "child");
        if (child.isAbsolute() || child.volumeLetter() != null) {
            return child;
        }
        ByteString slash = getSlash($this$commonResolve);
        if (slash == null && (slash = getSlash(child)) == null) {
            slash = toSlash(okio.Path.DIRECTORY_SEPARATOR);
        }
        Buffer buffer = new Buffer();
        buffer.write($this$commonResolve.getBytes$okio());
        if (buffer.size() > 0) {
            buffer.write(slash);
        }
        buffer.write(child.getBytes$okio());
        return toPath(buffer, normalize);
    }

    public static final okio.Path commonRelativeTo(okio.Path $this$commonRelativeTo, okio.Path other) {
        Intrinsics.checkNotNullParameter($this$commonRelativeTo, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (Intrinsics.areEqual((Object) $this$commonRelativeTo.getRoot(), (Object) other.getRoot())) {
            List thisSegments = $this$commonRelativeTo.getSegmentsBytes();
            List otherSegments = other.getSegmentsBytes();
            int firstNewSegmentIndex = 0;
            int minSegmentsSize = Math.min(thisSegments.size(), otherSegments.size());
            while (firstNewSegmentIndex < minSegmentsSize && Intrinsics.areEqual((Object) thisSegments.get(firstNewSegmentIndex), (Object) otherSegments.get(firstNewSegmentIndex))) {
                firstNewSegmentIndex++;
            }
            boolean z = true;
            if (firstNewSegmentIndex == minSegmentsSize && $this$commonRelativeTo.getBytes$okio().size() == other.getBytes$okio().size()) {
                return Path.Companion.get$default(okio.Path.Companion, ".", false, 1, (Object) null);
            }
            if (otherSegments.subList(firstNewSegmentIndex, otherSegments.size()).indexOf(DOT_DOT) != -1) {
                z = false;
            }
            if (z) {
                Buffer buffer = new Buffer();
                ByteString slash = getSlash(other);
                if (slash == null && (slash = getSlash($this$commonRelativeTo)) == null) {
                    slash = toSlash(okio.Path.DIRECTORY_SEPARATOR);
                }
                int size = otherSegments.size();
                for (int i = firstNewSegmentIndex; i < size; i++) {
                    buffer.write(DOT_DOT);
                    buffer.write(slash);
                }
                int size2 = thisSegments.size();
                for (int i2 = firstNewSegmentIndex; i2 < size2; i2++) {
                    buffer.write(thisSegments.get(i2));
                    buffer.write(slash);
                }
                return toPath(buffer, false);
            }
            throw new IllegalArgumentException(("Impossible relative path to resolve: " + $this$commonRelativeTo + " and " + other).toString());
        }
        throw new IllegalArgumentException(("Paths of different roots cannot be relative to each other: " + $this$commonRelativeTo + " and " + other).toString());
    }

    public static final okio.Path commonNormalized(okio.Path $this$commonNormalized) {
        Intrinsics.checkNotNullParameter($this$commonNormalized, "<this>");
        return okio.Path.Companion.get($this$commonNormalized.toString(), true);
    }

    /* access modifiers changed from: private */
    public static final ByteString getSlash(okio.Path $this$slash) {
        if (ByteString.indexOf$default($this$slash.getBytes$okio(), SLASH, 0, 2, (Object) null) != -1) {
            return SLASH;
        }
        if (ByteString.indexOf$default($this$slash.getBytes$okio(), BACKSLASH, 0, 2, (Object) null) != -1) {
            return BACKSLASH;
        }
        return null;
    }

    public static final int commonCompareTo(okio.Path $this$commonCompareTo, okio.Path other) {
        Intrinsics.checkNotNullParameter($this$commonCompareTo, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return $this$commonCompareTo.getBytes$okio().compareTo(other.getBytes$okio());
    }

    public static final boolean commonEquals(okio.Path $this$commonEquals, Object other) {
        Intrinsics.checkNotNullParameter($this$commonEquals, "<this>");
        return (other instanceof okio.Path) && Intrinsics.areEqual((Object) ((okio.Path) other).getBytes$okio(), (Object) $this$commonEquals.getBytes$okio());
    }

    public static final int commonHashCode(okio.Path $this$commonHashCode) {
        Intrinsics.checkNotNullParameter($this$commonHashCode, "<this>");
        return $this$commonHashCode.getBytes$okio().hashCode();
    }

    public static final String commonToString(okio.Path $this$commonToString) {
        Intrinsics.checkNotNullParameter($this$commonToString, "<this>");
        return $this$commonToString.getBytes$okio().utf8();
    }

    public static final okio.Path commonToPath(String $this$commonToPath, boolean normalize) {
        Intrinsics.checkNotNullParameter($this$commonToPath, "<this>");
        return toPath(new Buffer().writeUtf8($this$commonToPath), normalize);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00cf, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(kotlin.collections.CollectionsKt.last(r11), (java.lang.Object) DOT_DOT) != false) goto L_0x00e8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final okio.Path toPath(okio.Buffer r18, boolean r19) {
        /*
            r0 = r18
            java.lang.String r1 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r1 = 0
            okio.Buffer r2 = new okio.Buffer
            r2.<init>()
            r3 = 0
        L_0x000e:
            okio.ByteString r4 = SLASH
            r5 = 0
            boolean r4 = r0.rangeEquals(r5, r4)
            if (r4 != 0) goto L_0x013a
            okio.ByteString r4 = BACKSLASH
            boolean r4 = r0.rangeEquals(r5, r4)
            if (r4 == 0) goto L_0x0022
            goto L_0x013a
        L_0x0022:
            r4 = 2
            r7 = 0
            r8 = 1
            if (r3 < r4) goto L_0x0031
            okio.ByteString r4 = BACKSLASH
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1, (java.lang.Object) r4)
            if (r4 == 0) goto L_0x0031
            r4 = r8
            goto L_0x0032
        L_0x0031:
            r4 = r7
        L_0x0032:
            r9 = -1
            if (r4 == 0) goto L_0x0040
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            r2.write((okio.ByteString) r1)
            r2.write((okio.ByteString) r1)
            goto L_0x007c
        L_0x0040:
            if (r3 <= 0) goto L_0x0049
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            r2.write((okio.ByteString) r1)
            goto L_0x007c
        L_0x0049:
            okio.ByteString r11 = ANY_SLASH
            long r11 = r0.indexOfElement(r11)
            if (r1 != 0) goto L_0x0065
            int r13 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r13 != 0) goto L_0x005c
            java.lang.String r13 = okio.Path.DIRECTORY_SEPARATOR
            okio.ByteString r13 = toSlash((java.lang.String) r13)
            goto L_0x0066
        L_0x005c:
            byte r13 = r0.getByte(r11)
            okio.ByteString r13 = toSlash((byte) r13)
            goto L_0x0066
        L_0x0065:
            r13 = r1
        L_0x0066:
            r1 = r13
            boolean r13 = startsWithVolumeLetterAndColon(r0, r1)
            if (r13 == 0) goto L_0x007c
            r13 = 2
            int r15 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r15 != 0) goto L_0x0079
            r13 = 3
            r2.write((okio.Buffer) r0, (long) r13)
            goto L_0x007c
        L_0x0079:
            r2.write((okio.Buffer) r0, (long) r13)
        L_0x007c:
            long r11 = r2.size()
            int r11 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r11 <= 0) goto L_0x0085
            r7 = r8
        L_0x0085:
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.List r11 = (java.util.List) r11
        L_0x008c:
            boolean r12 = r0.exhausted()
            if (r12 != 0) goto L_0x0109
            okio.ByteString r12 = ANY_SLASH
            long r12 = r0.indexOfElement(r12)
            r14 = 0
            int r15 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r15 != 0) goto L_0x00a2
            okio.ByteString r14 = r0.readByteString()
            goto L_0x00a9
        L_0x00a2:
            okio.ByteString r14 = r0.readByteString(r12)
            r0.readByte()
        L_0x00a9:
            okio.ByteString r15 = DOT_DOT
            boolean r15 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r14, (java.lang.Object) r15)
            if (r15 == 0) goto L_0x00ee
            if (r7 == 0) goto L_0x00b9
            boolean r15 = r11.isEmpty()
            if (r15 != 0) goto L_0x008c
        L_0x00b9:
            if (r19 == 0) goto L_0x00e6
            if (r7 != 0) goto L_0x00d2
            boolean r15 = r11.isEmpty()
            if (r15 != 0) goto L_0x00e6
            java.lang.Object r15 = kotlin.collections.CollectionsKt.last(r11)
            r16 = r5
            okio.ByteString r5 = DOT_DOT
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r15, (java.lang.Object) r5)
            if (r5 == 0) goto L_0x00d4
            goto L_0x00e8
        L_0x00d2:
            r16 = r5
        L_0x00d4:
            if (r4 == 0) goto L_0x00e0
            int r5 = r11.size()
            if (r5 == r8) goto L_0x00dd
            goto L_0x00e0
        L_0x00dd:
            r5 = r16
            goto L_0x008c
        L_0x00e0:
            kotlin.collections.CollectionsKt.removeLastOrNull(r11)
            r5 = r16
            goto L_0x008c
        L_0x00e6:
            r16 = r5
        L_0x00e8:
            r11.add(r14)
            r5 = r16
            goto L_0x008c
        L_0x00ee:
            r16 = r5
            okio.ByteString r5 = DOT
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r14, (java.lang.Object) r5)
            if (r5 != 0) goto L_0x0106
            okio.ByteString r5 = okio.ByteString.EMPTY
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r14, (java.lang.Object) r5)
            if (r5 != 0) goto L_0x0106
            r11.add(r14)
            r5 = r16
            goto L_0x008c
        L_0x0106:
            r5 = r16
            goto L_0x008c
        L_0x0109:
            r16 = r5
            r5 = 0
            int r6 = r11.size()
        L_0x0110:
            if (r5 >= r6) goto L_0x0123
            if (r5 <= 0) goto L_0x0117
            r2.write((okio.ByteString) r1)
        L_0x0117:
            java.lang.Object r8 = r11.get(r5)
            okio.ByteString r8 = (okio.ByteString) r8
            r2.write((okio.ByteString) r8)
            int r5 = r5 + 1
            goto L_0x0110
        L_0x0123:
            long r5 = r2.size()
            int r5 = (r5 > r16 ? 1 : (r5 == r16 ? 0 : -1))
            if (r5 != 0) goto L_0x0130
            okio.ByteString r5 = DOT
            r2.write((okio.ByteString) r5)
        L_0x0130:
            okio.Path r5 = new okio.Path
            okio.ByteString r6 = r2.readByteString()
            r5.<init>(r6)
            return r5
        L_0x013a:
            byte r4 = r0.readByte()
            if (r1 != 0) goto L_0x0145
            okio.ByteString r5 = toSlash((byte) r4)
            goto L_0x0146
        L_0x0145:
            r5 = r1
        L_0x0146:
            r1 = r5
            int r3 = r3 + 1
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.Path.toPath(okio.Buffer, boolean):okio.Path");
    }

    /* access modifiers changed from: private */
    public static final ByteString toSlash(String $this$toSlash) {
        if (Intrinsics.areEqual((Object) $this$toSlash, (Object) "/")) {
            return SLASH;
        }
        if (Intrinsics.areEqual((Object) $this$toSlash, (Object) "\\")) {
            return BACKSLASH;
        }
        throw new IllegalArgumentException("not a directory separator: " + $this$toSlash);
    }

    private static final ByteString toSlash(byte $this$toSlash) {
        switch ($this$toSlash) {
            case 47:
                return SLASH;
            case 92:
                return BACKSLASH;
            default:
                throw new IllegalArgumentException("not a directory separator: " + $this$toSlash);
        }
    }

    private static final boolean startsWithVolumeLetterAndColon(Buffer $this$startsWithVolumeLetterAndColon, ByteString slash) {
        if (!Intrinsics.areEqual((Object) slash, (Object) BACKSLASH) || $this$startsWithVolumeLetterAndColon.size() < 2 || $this$startsWithVolumeLetterAndColon.getByte(1) != 58) {
            return false;
        }
        char b = (char) $this$startsWithVolumeLetterAndColon.getByte(0);
        if (!('a' <= b && b < '{')) {
            if (!('A' <= b && b < '[')) {
                return false;
            }
        }
        return true;
    }
}
