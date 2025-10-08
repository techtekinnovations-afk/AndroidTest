package okio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004:\u0001\u0015B\u001f\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0011\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u000eH\u0002R\u001e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006X\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", "", "trie", "", "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", "", "getSize", "()I", "getTrie$okio", "()[I", "get", "index", "Companion", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: Options.kt */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    @JvmStatic
    public static final Options of(ByteString... byteStringArr) {
        return Companion.of(byteStringArr);
    }

    public final /* bridge */ boolean contains(Object element) {
        if (!(element instanceof ByteString)) {
            return false;
        }
        return contains((ByteString) element);
    }

    public /* bridge */ boolean contains(ByteString element) {
        return super.contains(element);
    }

    public final /* bridge */ int indexOf(Object element) {
        if (!(element instanceof ByteString)) {
            return -1;
        }
        return indexOf((ByteString) element);
    }

    public /* bridge */ int indexOf(ByteString element) {
        return super.indexOf(element);
    }

    public final /* bridge */ int lastIndexOf(Object element) {
        if (!(element instanceof ByteString)) {
            return -1;
        }
        return lastIndexOf((ByteString) element);
    }

    public /* bridge */ int lastIndexOf(ByteString element) {
        return super.lastIndexOf(element);
    }

    public final ByteString[] getByteStrings$okio() {
        return this.byteStrings;
    }

    public final int[] getTrie$okio() {
        return this.trie;
    }

    private Options(ByteString[] byteStrings2, int[] trie2) {
        this.byteStrings = byteStrings2;
        this.trie = trie2;
    }

    public int getSize() {
        return this.byteStrings.length;
    }

    public ByteString get(int index) {
        return this.byteStrings[index];
    }

    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J!\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u0016\"\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lokio/Options$Companion;", "", "()V", "intCount", "", "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", "", "nodeOffset", "node", "byteStringOffset", "", "byteStrings", "", "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", "", "([Lokio/ByteString;)Lokio/Options;", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: Options.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Options of(ByteString... byteStrings) {
            ByteString[] byteStringArr = byteStrings;
            Intrinsics.checkNotNullParameter(byteStringArr, "byteStrings");
            if (byteStringArr.length == 0) {
                return new Options(new ByteString[0], new int[]{0, -1}, (DefaultConstructorMarker) null);
            }
            List list = ArraysKt.toMutableList((T[]) byteStringArr);
            CollectionsKt.sort(list);
            ByteString[] byteStringArr2 = byteStrings;
            Collection destination$iv$iv = new ArrayList(byteStringArr2.length);
            for (ByteString byteString : byteStringArr2) {
                destination$iv$iv.add(-1);
            }
            Integer[] numArr = (Integer[]) ((List) destination$iv$iv).toArray(new Integer[0]);
            List indexes = CollectionsKt.mutableListOf(Arrays.copyOf(numArr, numArr.length));
            ByteString[] byteStringArr3 = byteStrings;
            int index$iv = 0;
            int length = byteStringArr3.length;
            int i = 0;
            while (i < length) {
                int index$iv2 = index$iv + 1;
                int callerIndex = index$iv;
                List list2 = list;
                list = list2;
                indexes.set(CollectionsKt.binarySearch$default(list2, (Comparable) byteStringArr3[i], 0, 0, 6, (Object) null), Integer.valueOf(callerIndex));
                i++;
                index$iv = index$iv2;
            }
            if (((ByteString) list.get(0)).size() > 0) {
                for (int a = 0; a < list.size(); a++) {
                    ByteString prefix = (ByteString) list.get(a);
                    int b = a + 1;
                    while (b < list.size()) {
                        ByteString byteString2 = (ByteString) list.get(b);
                        if (!byteString2.startsWith(prefix)) {
                            continue;
                            break;
                        }
                        if (!(byteString2.size() != prefix.size())) {
                            throw new IllegalArgumentException(("duplicate option: " + byteString2).toString());
                        } else if (((Number) indexes.get(b)).intValue() > ((Number) indexes.get(a)).intValue()) {
                            list.remove(b);
                            indexes.remove(b);
                        } else {
                            b++;
                        }
                    }
                }
                Buffer trieBytes = new Buffer();
                buildTrieRecursive$default(this, 0, trieBytes, 0, list, 0, 0, indexes, 53, (Object) null);
                List list3 = list;
                int[] trie = new int[((int) getIntCount(trieBytes))];
                int i2 = 0;
                while (!trieBytes.exhausted()) {
                    trie[i2] = trieBytes.readInt();
                    i2++;
                }
                Object[] copyOf = Arrays.copyOf(byteStringArr, byteStringArr.length);
                Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
                return new Options((ByteString[]) copyOf, trie, (DefaultConstructorMarker) null);
            }
            throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            long j2;
            int i5;
            int i6;
            int i7;
            if ((i4 & 1) != 0) {
                j2 = 0;
            } else {
                j2 = j;
            }
            if ((i4 & 4) != 0) {
                i5 = 0;
            } else {
                i5 = i;
            }
            if ((i4 & 16) != 0) {
                i6 = 0;
            } else {
                i6 = i2;
            }
            if ((i4 & 32) != 0) {
                i7 = list.size();
            } else {
                i7 = i3;
            }
            companion.buildTrieRecursive(j2, buffer, i5, list, i6, i7, list2);
        }

        private final void buildTrieRecursive(long nodeOffset, Buffer node, int byteStringOffset, List<? extends ByteString> byteStrings, int fromIndex, int toIndex, List<Integer> indexes) {
            ByteString from;
            int fromIndex2;
            int fromIndex3;
            int prefixIndex;
            Buffer childNodes;
            long childNodesOffset;
            int prefixIndex2;
            Companion companion = this;
            Buffer buffer = node;
            int prefixIndex3 = byteStringOffset;
            List<? extends ByteString> list = byteStrings;
            int i = toIndex;
            List<Integer> list2 = indexes;
            if (fromIndex < i) {
                int i2 = fromIndex;
                while (i2 < i) {
                    if (((ByteString) list.get(i2)).size() >= prefixIndex3) {
                        i2++;
                    } else {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                }
                int fromIndex4 = fromIndex;
                ByteString from2 = (ByteString) list.get(fromIndex4);
                ByteString to = (ByteString) list.get(i - 1);
                if (prefixIndex3 == from2.size()) {
                    int prefixIndex4 = list2.get(fromIndex4).intValue();
                    int fromIndex5 = fromIndex4 + 1;
                    fromIndex2 = fromIndex5;
                    from = (ByteString) list.get(fromIndex5);
                    fromIndex3 = prefixIndex4;
                } else {
                    fromIndex2 = fromIndex4;
                    from = from2;
                    fromIndex3 = -1;
                }
                if (from.getByte(prefixIndex3) != to.getByte(prefixIndex3)) {
                    int selectChoiceCount = 1;
                    for (int i3 = fromIndex2 + 1; i3 < i; i3++) {
                        if (((ByteString) list.get(i3 - 1)).getByte(prefixIndex3) != ((ByteString) list.get(i3)).getByte(prefixIndex3)) {
                            selectChoiceCount++;
                        }
                    }
                    long childNodesOffset2 = nodeOffset + companion.getIntCount(buffer) + ((long) 2) + ((long) (selectChoiceCount * 2));
                    buffer.writeInt(selectChoiceCount);
                    buffer.writeInt(fromIndex3);
                    for (int i4 = fromIndex2; i4 < i; i4++) {
                        int rangeByte = ((ByteString) list.get(i4)).getByte(prefixIndex3);
                        if (i4 == fromIndex2 || rangeByte != ((ByteString) list.get(i4 - 1)).getByte(prefixIndex3)) {
                            buffer.writeInt(255 & rangeByte);
                        }
                    }
                    Buffer childNodes2 = new Buffer();
                    int rangeStart = fromIndex2;
                    while (rangeStart < i) {
                        byte rangeByte2 = ((ByteString) list.get(rangeStart)).getByte(prefixIndex3);
                        int rangeEnd = toIndex;
                        int selectChoiceCount2 = selectChoiceCount;
                        int selectChoiceCount3 = rangeStart + 1;
                        while (true) {
                            if (selectChoiceCount3 >= i) {
                                int i5 = selectChoiceCount3;
                                break;
                            }
                            int i6 = selectChoiceCount3;
                            if (rangeByte2 != ((ByteString) list.get(selectChoiceCount3)).getByte(prefixIndex3)) {
                                rangeEnd = i6;
                                break;
                            }
                            selectChoiceCount3 = i6 + 1;
                        }
                        if (rangeStart + 1 == rangeEnd) {
                            prefixIndex2 = fromIndex3;
                            if (prefixIndex3 + 1 == ((ByteString) list.get(rangeStart)).size()) {
                                buffer.writeInt(list2.get(rangeStart).intValue());
                                childNodes = childNodes2;
                                childNodesOffset = childNodesOffset2;
                                prefixIndex = prefixIndex2;
                                byte b = rangeByte2;
                                rangeStart = rangeEnd;
                                childNodesOffset2 = childNodesOffset;
                                childNodes2 = childNodes;
                                fromIndex3 = prefixIndex;
                                selectChoiceCount = selectChoiceCount2;
                                prefixIndex3 = byteStringOffset;
                            }
                        } else {
                            prefixIndex2 = fromIndex3;
                        }
                        buffer.writeInt(((int) (childNodesOffset2 + companion.getIntCount(childNodes2))) * -1);
                        byte rangeByte3 = rangeByte2;
                        int i7 = prefixIndex3 + 1;
                        childNodes = childNodes2;
                        childNodesOffset = childNodesOffset2;
                        prefixIndex = prefixIndex2;
                        byte b2 = rangeByte3;
                        companion = this;
                        companion.buildTrieRecursive(childNodesOffset, childNodes, i7, list, rangeStart, rangeEnd, list2);
                        rangeStart = rangeEnd;
                        childNodesOffset2 = childNodesOffset;
                        childNodes2 = childNodes;
                        fromIndex3 = prefixIndex;
                        selectChoiceCount = selectChoiceCount2;
                        prefixIndex3 = byteStringOffset;
                    }
                    int i8 = fromIndex3;
                    long j = childNodesOffset2;
                    buffer.writeAll(childNodes2);
                    int rangeStart2 = fromIndex2;
                    return;
                }
                int prefixIndex5 = fromIndex3;
                int scanByteCount = 0;
                int i9 = byteStringOffset;
                int min = Math.min(from.size(), to.size());
                while (i9 < min && from.getByte(i9) == to.getByte(i9)) {
                    scanByteCount++;
                    i9++;
                }
                long childNodesOffset3 = nodeOffset + companion.getIntCount(buffer) + ((long) 2) + ((long) scanByteCount) + 1;
                buffer.writeInt(-scanByteCount);
                buffer.writeInt(prefixIndex5);
                int i10 = byteStringOffset + scanByteCount;
                for (int i11 = byteStringOffset; i11 < i10; i11++) {
                    buffer.writeInt((int) from.getByte(i11) & 255);
                }
                if (fromIndex2 + 1 == i) {
                    if (byteStringOffset + scanByteCount == ((ByteString) list.get(fromIndex2)).size()) {
                        buffer.writeInt(list2.get(fromIndex2).intValue());
                        int i12 = fromIndex2;
                        return;
                    }
                    throw new IllegalStateException("Check failed.".toString());
                }
                Buffer childNodes3 = new Buffer();
                buffer.writeInt(((int) (companion.getIntCount(childNodes3) + childNodesOffset3)) * -1);
                int scanByteCount2 = scanByteCount;
                Buffer childNodes4 = childNodes3;
                int i13 = i;
                int i14 = scanByteCount2;
                companion.buildTrieRecursive(childNodesOffset3, childNodes4, byteStringOffset + scanByteCount2, list, fromIndex2, i13, list2);
                buffer.writeAll(childNodes4);
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        private final long getIntCount(Buffer $this$intCount) {
            return $this$intCount.size() / ((long) 4);
        }
    }
}
