package com.google.protobuf;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.ByteString;
import com.google.protobuf.Internal;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import com.google.protobuf.Writer;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import sun.misc.Unsafe;

@CheckReturnValue
final class MessageSchema<T> implements Schema<T> {
    private static final int CHECK_INITIALIZED_BIT = 1024;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int HAS_HAS_BIT = 4096;
    private static final int INTS_PER_FIELD = 3;
    private static final int LEGACY_ENUM_IS_CLOSED_BIT = 2048;
    private static final int LEGACY_ENUM_IS_CLOSED_MASK = Integer.MIN_VALUE;
    private static final int NO_PRESENCE_SENTINEL = 1048575;
    private static final int OFFSET_BITS = 20;
    private static final int OFFSET_MASK = 1048575;
    static final int ONEOF_TYPE_OFFSET = 51;
    private static final int REQUIRED_BIT = 256;
    private static final int REQUIRED_MASK = 268435456;
    private static final Unsafe UNSAFE = UnsafeUtil.getUnsafe();
    private static final int UTF8_CHECK_BIT = 512;
    private final int[] buffer;
    private final int checkInitializedCount;
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final int[] intArray;
    private final ListFieldSchema listFieldSchema;
    private final boolean lite;
    private final MapFieldSchema mapFieldSchema;
    private final int maxFieldNumber;
    private final int minFieldNumber;
    private final NewInstanceSchema newInstanceSchema;
    private final Object[] objects;
    private final int repeatedFieldOffsetStart;
    private final ProtoSyntax syntax;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final boolean useCachedSizeField;

    private MessageSchema(int[] buffer2, Object[] objects2, int minFieldNumber2, int maxFieldNumber2, MessageLite defaultInstance2, ProtoSyntax syntax2, boolean useCachedSizeField2, int[] intArray2, int checkInitialized, int mapFieldPositions, NewInstanceSchema newInstanceSchema2, ListFieldSchema listFieldSchema2, UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MapFieldSchema mapFieldSchema2) {
        MessageLite messageLite = defaultInstance2;
        ExtensionSchema<?> extensionSchema3 = extensionSchema2;
        this.buffer = buffer2;
        this.objects = objects2;
        this.minFieldNumber = minFieldNumber2;
        this.maxFieldNumber = maxFieldNumber2;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.syntax = syntax2;
        this.hasExtensions = extensionSchema3 != null && extensionSchema3.hasExtensions(messageLite);
        this.useCachedSizeField = useCachedSizeField2;
        this.intArray = intArray2;
        this.checkInitializedCount = checkInitialized;
        this.repeatedFieldOffsetStart = mapFieldPositions;
        this.newInstanceSchema = newInstanceSchema2;
        this.listFieldSchema = listFieldSchema2;
        this.unknownFieldSchema = unknownFieldSchema2;
        this.extensionSchema = extensionSchema3;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema2;
    }

    static <T> MessageSchema<T> newSchema(Class<T> cls, MessageInfo messageInfo, NewInstanceSchema newInstanceSchema2, ListFieldSchema listFieldSchema2, UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MapFieldSchema mapFieldSchema2) {
        if (messageInfo instanceof RawMessageInfo) {
            NewInstanceSchema newInstanceSchema3 = newInstanceSchema2;
            ListFieldSchema listFieldSchema3 = listFieldSchema2;
            UnknownFieldSchema<?, ?> unknownFieldSchema3 = unknownFieldSchema2;
            ExtensionSchema<?> extensionSchema3 = extensionSchema2;
            MapFieldSchema mapFieldSchema3 = mapFieldSchema2;
            MessageSchema<T> newSchemaForRawMessageInfo = newSchemaForRawMessageInfo((RawMessageInfo) messageInfo, newInstanceSchema3, listFieldSchema3, unknownFieldSchema3, extensionSchema3, mapFieldSchema3);
            NewInstanceSchema newInstanceSchema4 = newInstanceSchema3;
            ListFieldSchema listFieldSchema4 = listFieldSchema3;
            UnknownFieldSchema<?, ?> unknownFieldSchema4 = unknownFieldSchema3;
            ExtensionSchema<?> extensionSchema4 = extensionSchema3;
            MapFieldSchema mapFieldSchema4 = mapFieldSchema3;
            return newSchemaForRawMessageInfo;
        }
        return newSchemaForMessageInfo((StructuralMessageInfo) messageInfo, newInstanceSchema2, listFieldSchema2, unknownFieldSchema2, extensionSchema2, mapFieldSchema2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r28v0, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v1, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v0, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v1, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v2, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v3, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v10, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v32, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v34, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v45, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v21, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v50, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v51, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v54, resolved type: java.lang.reflect.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: java.lang.reflect.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v15, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v16, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v14, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v15, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: int[]} */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x02ce  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x02d2  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x02f0  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x02f4  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> com.google.protobuf.MessageSchema<T> newSchemaForRawMessageInfo(com.google.protobuf.RawMessageInfo r41, com.google.protobuf.NewInstanceSchema r42, com.google.protobuf.ListFieldSchema r43, com.google.protobuf.UnknownFieldSchema<?, ?> r44, com.google.protobuf.ExtensionSchema<?> r45, com.google.protobuf.MapFieldSchema r46) {
        /*
            java.lang.String r0 = r41.getStringInfo()
            int r1 = r0.length()
            r2 = 0
            int r3 = r2 + 1
            char r2 = r0.charAt(r2)
            r4 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r4) goto L_0x002e
            r5 = r2 & 8191(0x1fff, float:1.1478E-41)
            r6 = 13
        L_0x0018:
            int r7 = r3 + 1
            char r3 = r0.charAt(r3)
            r2 = r3
            if (r3 < r4) goto L_0x0029
            r3 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r6
            r5 = r5 | r3
            int r6 = r6 + 13
            r3 = r7
            goto L_0x0018
        L_0x0029:
            int r3 = r2 << r6
            r2 = r5 | r3
            r3 = r7
        L_0x002e:
            r5 = r2
            int r6 = r3 + 1
            char r2 = r0.charAt(r3)
            if (r2 < r4) goto L_0x0051
            r3 = r2 & 8191(0x1fff, float:1.1478E-41)
            r7 = 13
        L_0x003b:
            int r8 = r6 + 1
            char r6 = r0.charAt(r6)
            r2 = r6
            if (r6 < r4) goto L_0x004c
            r6 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r6 = r6 << r7
            r3 = r3 | r6
            int r7 = r7 + 13
            r6 = r8
            goto L_0x003b
        L_0x004c:
            int r6 = r2 << r7
            r2 = r3 | r6
            r6 = r8
        L_0x0051:
            r3 = r2
            if (r3 != 0) goto L_0x006e
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            int[] r15 = EMPTY_INT_ARRAY
            r16 = 0
            r25 = r11
            r26 = r12
            r27 = r13
            r18 = r14
            r12 = r9
            r13 = r10
            r17 = r15
            goto L_0x01a0
        L_0x006e:
            int r7 = r6 + 1
            char r2 = r0.charAt(r6)
            if (r2 < r4) goto L_0x0090
            r6 = r2 & 8191(0x1fff, float:1.1478E-41)
            r8 = 13
        L_0x007a:
            int r9 = r7 + 1
            char r7 = r0.charAt(r7)
            r2 = r7
            if (r7 < r4) goto L_0x008b
            r7 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r7 = r7 << r8
            r6 = r6 | r7
            int r8 = r8 + 13
            r7 = r9
            goto L_0x007a
        L_0x008b:
            int r7 = r2 << r8
            r2 = r6 | r7
            r7 = r9
        L_0x0090:
            r6 = r2
            int r8 = r7 + 1
            char r2 = r0.charAt(r7)
            if (r2 < r4) goto L_0x00b3
            r7 = r2 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x009d:
            int r10 = r8 + 1
            char r8 = r0.charAt(r8)
            r2 = r8
            if (r8 < r4) goto L_0x00ae
            r8 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r8 = r8 << r9
            r7 = r7 | r8
            int r9 = r9 + 13
            r8 = r10
            goto L_0x009d
        L_0x00ae:
            int r8 = r2 << r9
            r2 = r7 | r8
            r8 = r10
        L_0x00b3:
            r7 = r2
            int r9 = r8 + 1
            char r2 = r0.charAt(r8)
            if (r2 < r4) goto L_0x00d6
            r8 = r2 & 8191(0x1fff, float:1.1478E-41)
            r10 = 13
        L_0x00c0:
            int r11 = r9 + 1
            char r9 = r0.charAt(r9)
            r2 = r9
            if (r9 < r4) goto L_0x00d1
            r9 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r10
            r8 = r8 | r9
            int r10 = r10 + 13
            r9 = r11
            goto L_0x00c0
        L_0x00d1:
            int r9 = r2 << r10
            r2 = r8 | r9
            r9 = r11
        L_0x00d6:
            r8 = r2
            int r10 = r9 + 1
            char r2 = r0.charAt(r9)
            if (r2 < r4) goto L_0x00f9
            r9 = r2 & 8191(0x1fff, float:1.1478E-41)
            r11 = 13
        L_0x00e3:
            int r12 = r10 + 1
            char r10 = r0.charAt(r10)
            r2 = r10
            if (r10 < r4) goto L_0x00f4
            r10 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r11
            r9 = r9 | r10
            int r11 = r11 + 13
            r10 = r12
            goto L_0x00e3
        L_0x00f4:
            int r10 = r2 << r11
            r2 = r9 | r10
            r10 = r12
        L_0x00f9:
            r9 = r2
            int r11 = r10 + 1
            char r2 = r0.charAt(r10)
            if (r2 < r4) goto L_0x011c
            r10 = r2 & 8191(0x1fff, float:1.1478E-41)
            r12 = 13
        L_0x0106:
            int r13 = r11 + 1
            char r11 = r0.charAt(r11)
            r2 = r11
            if (r11 < r4) goto L_0x0117
            r11 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r11 = r11 << r12
            r10 = r10 | r11
            int r12 = r12 + 13
            r11 = r13
            goto L_0x0106
        L_0x0117:
            int r11 = r2 << r12
            r2 = r10 | r11
            r11 = r13
        L_0x011c:
            r10 = r2
            int r12 = r11 + 1
            char r2 = r0.charAt(r11)
            if (r2 < r4) goto L_0x013f
            r11 = r2 & 8191(0x1fff, float:1.1478E-41)
            r13 = 13
        L_0x0129:
            int r14 = r12 + 1
            char r12 = r0.charAt(r12)
            r2 = r12
            if (r12 < r4) goto L_0x013a
            r12 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r12 = r12 << r13
            r11 = r11 | r12
            int r13 = r13 + 13
            r12 = r14
            goto L_0x0129
        L_0x013a:
            int r12 = r2 << r13
            r2 = r11 | r12
            r12 = r14
        L_0x013f:
            r11 = r2
            int r13 = r12 + 1
            char r2 = r0.charAt(r12)
            if (r2 < r4) goto L_0x0162
            r12 = r2 & 8191(0x1fff, float:1.1478E-41)
            r14 = 13
        L_0x014c:
            int r15 = r13 + 1
            char r13 = r0.charAt(r13)
            r2 = r13
            if (r13 < r4) goto L_0x015d
            r13 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r13 = r13 << r14
            r12 = r12 | r13
            int r14 = r14 + 13
            r13 = r15
            goto L_0x014c
        L_0x015d:
            int r13 = r2 << r14
            r2 = r12 | r13
            r13 = r15
        L_0x0162:
            r12 = r2
            int r14 = r13 + 1
            char r2 = r0.charAt(r13)
            if (r2 < r4) goto L_0x0187
            r13 = r2 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x016f:
            int r16 = r14 + 1
            char r14 = r0.charAt(r14)
            r2 = r14
            if (r14 < r4) goto L_0x0181
            r14 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r14 = r14 << r15
            r13 = r13 | r14
            int r15 = r15 + 13
            r14 = r16
            goto L_0x016f
        L_0x0181:
            int r14 = r2 << r15
            r2 = r13 | r14
            r14 = r16
        L_0x0187:
            r13 = r2
            int r15 = r13 + r11
            int r15 = r15 + r12
            int[] r15 = new int[r15]
            int r16 = r6 * 2
            int r16 = r16 + r7
            r25 = r10
            r26 = r11
            r27 = r12
            r18 = r13
            r12 = r8
            r13 = r9
            r8 = r7
            r7 = r6
            r6 = r14
            r17 = r15
        L_0x01a0:
            sun.misc.Unsafe r9 = UNSAFE
            java.lang.Object[] r28 = r41.getObjects()
            r10 = 0
            com.google.protobuf.MessageLite r11 = r41.getDefaultInstance()
            java.lang.Class r11 = r11.getClass()
            int r14 = r25 * 3
            int[] r14 = new int[r14]
            int r15 = r25 * 2
            java.lang.Object[] r15 = new java.lang.Object[r15]
            r19 = r18
            int r20 = r18 + r26
            r21 = 0
            r29 = r10
            r30 = r16
            r31 = r19
            r32 = r20
            r33 = r21
        L_0x01c7:
            if (r6 >= r1) goto L_0x048a
            int r10 = r6 + 1
            char r2 = r0.charAt(r6)
            if (r2 < r4) goto L_0x01ee
            r6 = r2 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
        L_0x01d5:
            int r19 = r10 + 1
            char r10 = r0.charAt(r10)
            r2 = r10
            if (r10 < r4) goto L_0x01e8
            r10 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r16
            r6 = r6 | r10
            int r16 = r16 + 13
            r10 = r19
            goto L_0x01d5
        L_0x01e8:
            int r10 = r2 << r16
            r2 = r6 | r10
            r10 = r19
        L_0x01ee:
            r6 = r2
            int r16 = r10 + 1
            char r2 = r0.charAt(r10)
            if (r2 < r4) goto L_0x0220
            r10 = r2 & 8191(0x1fff, float:1.1478E-41)
            r19 = 13
            r4 = r16
            r16 = r19
        L_0x01ff:
            int r20 = r4 + 1
            char r4 = r0.charAt(r4)
            r2 = r4
            r34 = r1
            r1 = 55296(0xd800, float:7.7486E-41)
            if (r4 < r1) goto L_0x0219
            r1 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r1 = r1 << r16
            r10 = r10 | r1
            int r16 = r16 + 13
            r4 = r20
            r1 = r34
            goto L_0x01ff
        L_0x0219:
            int r1 = r2 << r16
            r2 = r10 | r1
            r1 = r20
            goto L_0x0224
        L_0x0220:
            r34 = r1
            r1 = r16
        L_0x0224:
            r4 = r2
            r10 = r4 & 255(0xff, float:3.57E-43)
            r16 = r2
            r2 = r4 & 1024(0x400, float:1.435E-42)
            if (r2 == 0) goto L_0x0233
            int r2 = r29 + 1
            r17[r29] = r33
            r29 = r2
        L_0x0233:
            r23 = 0
            r24 = 1
            r2 = 51
            if (r10 < r2) goto L_0x030f
            int r2 = r1 + 1
            char r1 = r0.charAt(r1)
            r22 = r2
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r1 < r2) goto L_0x0276
            r2 = r1 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
            r40 = r16
            r16 = r2
            r2 = r22
            r22 = r40
        L_0x0254:
            int r36 = r2 + 1
            char r2 = r0.charAt(r2)
            r1 = r2
            r37 = r3
            r3 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r3) goto L_0x026f
            r2 = r1 & 8191(0x1fff, float:1.1478E-41)
            int r2 = r2 << r22
            r16 = r16 | r2
            int r22 = r22 + 13
            r2 = r36
            r3 = r37
            goto L_0x0254
        L_0x026f:
            int r2 = r1 << r22
            r1 = r16 | r2
            r2 = r36
            goto L_0x027a
        L_0x0276:
            r37 = r3
            r2 = r22
        L_0x027a:
            r3 = r1
            r16 = r1
            int r1 = r10 + -51
            r22 = r2
            r2 = 9
            if (r1 == r2) goto L_0x02b4
            r2 = 17
            if (r1 != r2) goto L_0x028c
            r20 = r1
            goto L_0x02b6
        L_0x028c:
            r2 = 12
            if (r1 != r2) goto L_0x02b1
            com.google.protobuf.ProtoSyntax r2 = r41.getSyntax()
            r20 = r1
            com.google.protobuf.ProtoSyntax r1 = com.google.protobuf.ProtoSyntax.PROTO2
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x02a2
            r1 = r4 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L_0x02c4
        L_0x02a2:
            int r1 = r33 / 3
            int r1 = r1 * 2
            int r1 = r1 + 1
            int r2 = r30 + 1
            r21 = r28[r30]
            r15[r1] = r21
            r30 = r2
            goto L_0x02c4
        L_0x02b1:
            r20 = r1
            goto L_0x02c4
        L_0x02b4:
            r20 = r1
        L_0x02b6:
            int r1 = r33 / 3
            int r1 = r1 * 2
            int r1 = r1 + 1
            int r2 = r30 + 1
            r21 = r28[r30]
            r15[r1] = r21
            r30 = r2
        L_0x02c4:
            int r1 = r3 * 2
            r2 = r28[r1]
            r21 = r1
            boolean r1 = r2 instanceof java.lang.reflect.Field
            if (r1 == 0) goto L_0x02d2
            r1 = r2
            java.lang.reflect.Field r1 = (java.lang.reflect.Field) r1
            goto L_0x02db
        L_0x02d2:
            r1 = r2
            java.lang.String r1 = (java.lang.String) r1
            java.lang.reflect.Field r1 = reflectField(r11, r1)
            r28[r21] = r1
        L_0x02db:
            r24 = r2
            r35 = r3
            long r2 = r9.objectFieldOffset(r1)
            int r2 = (int) r2
            int r3 = r21 + 1
            r21 = r1
            r1 = r28[r3]
            r24 = r2
            boolean r2 = r1 instanceof java.lang.reflect.Field
            if (r2 == 0) goto L_0x02f4
            r2 = r1
            java.lang.reflect.Field r2 = (java.lang.reflect.Field) r2
            goto L_0x02fd
        L_0x02f4:
            r2 = r1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.reflect.Field r2 = reflectField(r11, r2)
            r28[r3] = r2
        L_0x02fd:
            r36 = r5
            r38 = r6
            long r5 = r9.objectFieldOffset(r2)
            int r5 = (int) r5
            r1 = 0
            r35 = r0
            r6 = r22
            r2 = r24
            goto L_0x0443
        L_0x030f:
            r37 = r3
            r36 = r5
            r38 = r6
            int r2 = r30 + 1
            r3 = r28[r30]
            java.lang.String r3 = (java.lang.String) r3
            java.lang.reflect.Field r3 = reflectField(r11, r3)
            r5 = 49
            r6 = 9
            if (r10 == r6) goto L_0x0393
            r6 = 17
            if (r10 != r6) goto L_0x032b
            goto L_0x0393
        L_0x032b:
            r6 = 27
            if (r10 == r6) goto L_0x0385
            if (r10 != r5) goto L_0x0332
            goto L_0x0385
        L_0x0332:
            r6 = 12
            if (r10 == r6) goto L_0x036b
            r6 = 30
            if (r10 == r6) goto L_0x036b
            r6 = 44
            if (r10 != r6) goto L_0x033f
            goto L_0x036b
        L_0x033f:
            r6 = 50
            if (r10 != r6) goto L_0x039f
            int r6 = r31 + 1
            r17[r31] = r33
            int r20 = r33 / 3
            int r20 = r20 * 2
            int r21 = r2 + 1
            r2 = r28[r2]
            r15[r20] = r2
            r2 = r4 & 2048(0x800, float:2.87E-42)
            if (r2 == 0) goto L_0x0366
            int r2 = r33 / 3
            int r2 = r2 * 2
            int r2 = r2 + 1
            int r20 = r21 + 1
            r21 = r28[r21]
            r15[r2] = r21
            r31 = r6
            r2 = r20
            goto L_0x039f
        L_0x0366:
            r31 = r6
            r2 = r21
            goto L_0x039f
        L_0x036b:
            com.google.protobuf.ProtoSyntax r6 = r41.getSyntax()
            com.google.protobuf.ProtoSyntax r5 = com.google.protobuf.ProtoSyntax.PROTO2
            if (r6 == r5) goto L_0x0377
            r5 = r4 & 2048(0x800, float:2.87E-42)
            if (r5 == 0) goto L_0x039f
        L_0x0377:
            int r5 = r33 / 3
            int r5 = r5 * 2
            int r5 = r5 + 1
            int r6 = r2 + 1
            r2 = r28[r2]
            r15[r5] = r2
            r2 = r6
            goto L_0x039f
        L_0x0385:
            int r5 = r33 / 3
            int r5 = r5 * 2
            int r5 = r5 + 1
            int r6 = r2 + 1
            r2 = r28[r2]
            r15[r5] = r2
            r2 = r6
            goto L_0x039f
        L_0x0393:
            int r5 = r33 / 3
            int r5 = r5 * 2
            int r5 = r5 + 1
            java.lang.Class r6 = r3.getType()
            r15[r5] = r6
        L_0x039f:
            long r5 = r9.objectFieldOffset(r3)
            int r5 = (int) r5
            r6 = r4 & 4096(0x1000, float:5.74E-42)
            if (r6 == 0) goto L_0x03a9
            goto L_0x03ab
        L_0x03a9:
            r24 = r23
        L_0x03ab:
            if (r24 == 0) goto L_0x0415
            r6 = 17
            if (r10 > r6) goto L_0x0415
            int r6 = r1 + 1
            char r1 = r0.charAt(r1)
            r21 = r2
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r1 < r2) goto L_0x03e4
            r2 = r1 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
        L_0x03c2:
            int r22 = r6 + 1
            char r6 = r0.charAt(r6)
            r1 = r6
            r35 = r0
            r0 = 55296(0xd800, float:7.7486E-41)
            if (r6 < r0) goto L_0x03dc
            r6 = r1 & 8191(0x1fff, float:1.1478E-41)
            int r6 = r6 << r16
            r2 = r2 | r6
            int r16 = r16 + 13
            r6 = r22
            r0 = r35
            goto L_0x03c2
        L_0x03dc:
            int r6 = r1 << r16
            r1 = r2 | r6
            r2 = r1
            r1 = r22
            goto L_0x03e9
        L_0x03e4:
            r35 = r0
            r0 = r2
            r2 = r1
            r1 = r6
        L_0x03e9:
            r6 = r2
            int r16 = r7 * 2
            int r19 = r6 / 32
            int r16 = r16 + r19
            r0 = r28[r16]
            r22 = r1
            boolean r1 = r0 instanceof java.lang.reflect.Field
            if (r1 == 0) goto L_0x03fc
            r1 = r0
            java.lang.reflect.Field r1 = (java.lang.reflect.Field) r1
            goto L_0x0405
        L_0x03fc:
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1
            java.lang.reflect.Field r1 = reflectField(r11, r1)
            r28[r16] = r1
        L_0x0405:
            r39 = r2
            r30 = r3
            long r2 = r9.objectFieldOffset(r1)
            int r2 = (int) r2
            int r6 = r6 % 32
            r0 = r2
            r1 = r6
            r2 = r39
            goto L_0x0424
        L_0x0415:
            r35 = r0
            r21 = r2
            r30 = r3
            r0 = 1048575(0xfffff, float:1.469367E-39)
            r2 = 0
            r22 = r1
            r1 = r2
            r2 = r16
        L_0x0424:
            r3 = 18
            if (r10 < r3) goto L_0x043b
            r3 = 49
            if (r10 > r3) goto L_0x043b
            int r3 = r32 + 1
            r17[r32] = r5
            r16 = r2
            r32 = r3
            r2 = r5
            r30 = r21
            r6 = r22
            r5 = r0
            goto L_0x0443
        L_0x043b:
            r16 = r2
            r2 = r5
            r30 = r21
            r6 = r22
            r5 = r0
        L_0x0443:
            int r0 = r33 + 1
            r14[r33] = r38
            int r3 = r0 + 1
            r20 = r0
            r0 = r4 & 512(0x200, float:7.175E-43)
            if (r0 == 0) goto L_0x0452
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x0454
        L_0x0452:
            r0 = r23
        L_0x0454:
            r21 = r0
            r0 = r4 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L_0x045d
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x045f
        L_0x045d:
            r0 = r23
        L_0x045f:
            r0 = r21 | r0
            r21 = r0
            r0 = r4 & 2048(0x800, float:2.87E-42)
            if (r0 == 0) goto L_0x046a
            r23 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x046b
        L_0x046a:
        L_0x046b:
            r0 = r21 | r23
            int r21 = r10 << 20
            r0 = r0 | r21
            r0 = r0 | r2
            r14[r20] = r0
            int r33 = r3 + 1
            int r0 = r1 << 20
            r0 = r0 | r5
            r14[r3] = r0
            r2 = r16
            r1 = r34
            r0 = r35
            r5 = r36
            r3 = r37
            r4 = 55296(0xd800, float:7.7486E-41)
            goto L_0x01c7
        L_0x048a:
            r35 = r0
            r34 = r1
            r0 = r9
            com.google.protobuf.MessageSchema r9 = new com.google.protobuf.MessageSchema
            r10 = r14
            com.google.protobuf.MessageLite r14 = r41.getDefaultInstance()
            r1 = r11
            r11 = r15
            com.google.protobuf.ProtoSyntax r15 = r41.getSyntax()
            r16 = 0
            int r19 = r18 + r26
            r20 = r42
            r21 = r43
            r22 = r44
            r23 = r45
            r24 = r46
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.newSchemaForRawMessageInfo(com.google.protobuf.RawMessageInfo, com.google.protobuf.NewInstanceSchema, com.google.protobuf.ListFieldSchema, com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, com.google.protobuf.MapFieldSchema):com.google.protobuf.MessageSchema");
    }

    private static Field reflectField(Class<?> messageClass, String fieldName) {
        try {
            return messageClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Field[] fields = messageClass.getDeclaredFields();
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + fieldName + " for " + messageClass.getName() + " not found. Known fields are " + Arrays.toString(fields));
        }
    }

    static <T> MessageSchema<T> newSchemaForMessageInfo(StructuralMessageInfo messageInfo, NewInstanceSchema newInstanceSchema2, ListFieldSchema listFieldSchema2, UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MapFieldSchema mapFieldSchema2) {
        int maxFieldNumber2;
        int minFieldNumber2;
        int[] mapFieldPositions;
        int[] repeatedFieldOffsets;
        int[] checkInitialized;
        FieldInfo[] fis = messageInfo.getFields();
        if (fis.length == 0) {
            minFieldNumber2 = 0;
            maxFieldNumber2 = 0;
        } else {
            minFieldNumber2 = fis[0].getFieldNumber();
            maxFieldNumber2 = fis[fis.length - 1].getFieldNumber();
        }
        int numEntries = fis.length;
        int[] buffer2 = new int[(numEntries * 3)];
        Object[] objects2 = new Object[(numEntries * 2)];
        int mapFieldCount = 0;
        int repeatedFieldCount = 0;
        for (FieldInfo fi : fis) {
            if (fi.getType() == FieldType.MAP) {
                mapFieldCount++;
            } else if (fi.getType().id() >= 18 && fi.getType().id() <= 49) {
                repeatedFieldCount++;
            }
        }
        int[] repeatedFieldOffsets2 = null;
        int[] mapFieldPositions2 = mapFieldCount > 0 ? new int[mapFieldCount] : null;
        if (repeatedFieldCount > 0) {
            repeatedFieldOffsets2 = new int[repeatedFieldCount];
        }
        int mapFieldCount2 = 0;
        int[] checkInitialized2 = messageInfo.getCheckInitialized();
        if (checkInitialized2 == null) {
            checkInitialized2 = EMPTY_INT_ARRAY;
        }
        int repeatedFieldCount2 = 0;
        int checkInitializedIndex = 0;
        int fieldIndex = 0;
        int bufferIndex = 0;
        while (fieldIndex < fis.length) {
            FieldInfo fi2 = fis[fieldIndex];
            int fieldNumber = fi2.getFieldNumber();
            storeFieldData(fi2, buffer2, bufferIndex, objects2);
            if (checkInitializedIndex < checkInitialized2.length && checkInitialized2[checkInitializedIndex] == fieldNumber) {
                checkInitialized2[checkInitializedIndex] = bufferIndex;
                checkInitializedIndex++;
            }
            FieldInfo[] fis2 = fis;
            if (fi2.getType() == FieldType.MAP) {
                mapFieldPositions2[mapFieldCount2] = bufferIndex;
                mapFieldCount2++;
                checkInitialized = checkInitialized2;
            } else if (fi2.getType().id() < 18 || fi2.getType().id() > 49) {
                checkInitialized = checkInitialized2;
            } else {
                checkInitialized = checkInitialized2;
                repeatedFieldOffsets2[repeatedFieldCount2] = (int) UnsafeUtil.objectFieldOffset(fi2.getField());
                repeatedFieldCount2++;
            }
            fieldIndex++;
            bufferIndex += 3;
            checkInitialized2 = checkInitialized;
            fis = fis2;
        }
        int[] checkInitialized3 = checkInitialized2;
        if (mapFieldPositions2 == null) {
            mapFieldPositions = EMPTY_INT_ARRAY;
        } else {
            mapFieldPositions = mapFieldPositions2;
        }
        if (repeatedFieldOffsets2 == null) {
            repeatedFieldOffsets = EMPTY_INT_ARRAY;
        } else {
            repeatedFieldOffsets = repeatedFieldOffsets2;
        }
        int[] checkInitialized4 = checkInitialized3;
        int[] combined = new int[(checkInitialized4.length + mapFieldPositions.length + repeatedFieldOffsets.length)];
        System.arraycopy(checkInitialized4, 0, combined, 0, checkInitialized4.length);
        System.arraycopy(mapFieldPositions, 0, combined, checkInitialized4.length, mapFieldPositions.length);
        System.arraycopy(repeatedFieldOffsets, 0, combined, checkInitialized4.length + mapFieldPositions.length, repeatedFieldOffsets.length);
        int i = numEntries;
        int i2 = fieldIndex;
        int[] iArr = checkInitialized4;
        int i3 = checkInitializedIndex;
        return new MessageSchema<>(buffer2, objects2, minFieldNumber2, maxFieldNumber2, messageInfo.getDefaultInstance(), messageInfo.getSyntax(), true, combined, checkInitialized4.length, checkInitialized4.length + mapFieldPositions.length, newInstanceSchema2, listFieldSchema2, unknownFieldSchema2, extensionSchema2, mapFieldSchema2);
    }

    private static void storeFieldData(FieldInfo fi, int[] buffer2, int bufferIndex, Object[] objects2) {
        int presenceFieldOffset;
        int typeId;
        int fieldOffset;
        int typeId2;
        int presenceFieldOffset2;
        OneofInfo oneof = fi.getOneof();
        if (oneof != null) {
            typeId2 = fi.getType().id() + 51;
            fieldOffset = (int) UnsafeUtil.objectFieldOffset(oneof.getValueField());
            typeId = (int) UnsafeUtil.objectFieldOffset(oneof.getCaseField());
            presenceFieldOffset = 0;
        } else {
            FieldType type = fi.getType();
            fieldOffset = (int) UnsafeUtil.objectFieldOffset(fi.getField());
            int typeId3 = type.id();
            if (!type.isList() && !type.isMap()) {
                Field presenceField = fi.getPresenceField();
                if (presenceField == null) {
                    presenceFieldOffset2 = 1048575;
                } else {
                    presenceFieldOffset2 = (int) UnsafeUtil.objectFieldOffset(presenceField);
                }
                presenceFieldOffset = Integer.numberOfTrailingZeros(fi.getPresenceMask());
                typeId2 = typeId3;
                typeId = presenceFieldOffset2;
            } else if (fi.getCachedSizeField() == null) {
                typeId2 = typeId3;
                typeId = 0;
                presenceFieldOffset = 0;
            } else {
                typeId2 = typeId3;
                typeId = (int) UnsafeUtil.objectFieldOffset(fi.getCachedSizeField());
                presenceFieldOffset = 0;
            }
        }
        buffer2[bufferIndex] = fi.getFieldNumber();
        int i = bufferIndex + 1;
        int i2 = 0;
        int i3 = fi.isEnforceUtf8() ? ENFORCE_UTF8_MASK : 0;
        if (fi.isRequired()) {
            i2 = REQUIRED_MASK;
        }
        buffer2[i] = i3 | i2 | (typeId2 << 20) | fieldOffset;
        buffer2[bufferIndex + 2] = (presenceFieldOffset << 20) | typeId;
        Object messageFieldClass = fi.getMessageFieldClass();
        if (fi.getMapDefaultEntry() != null) {
            objects2[(bufferIndex / 3) * 2] = fi.getMapDefaultEntry();
            if (messageFieldClass != null) {
                objects2[((bufferIndex / 3) * 2) + 1] = messageFieldClass;
            } else if (fi.getEnumVerifier() != null) {
                objects2[((bufferIndex / 3) * 2) + 1] = fi.getEnumVerifier();
            }
        } else if (messageFieldClass != null) {
            objects2[((bufferIndex / 3) * 2) + 1] = messageFieldClass;
        } else if (fi.getEnumVerifier() != null) {
            objects2[((bufferIndex / 3) * 2) + 1] = fi.getEnumVerifier();
        }
    }

    public T newInstance() {
        return this.newInstanceSchema.newInstance(this.defaultInstance);
    }

    public boolean equals(T message, T other) {
        int bufferLength = this.buffer.length;
        for (int pos = 0; pos < bufferLength; pos += 3) {
            if (!equals(message, other, pos)) {
                return false;
            }
        }
        if (!this.unknownFieldSchema.getFromMessage(message).equals(this.unknownFieldSchema.getFromMessage(other))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(message).equals(this.extensionSchema.getExtensions(other));
        }
        return true;
    }

    private boolean equals(T message, T other, int pos) {
        int typeAndOffset = typeAndOffsetAt(pos);
        long offset = offset(typeAndOffset);
        switch (type(typeAndOffset)) {
            case 0:
                if (!arePresentForEquals(message, other, pos) || Double.doubleToLongBits(UnsafeUtil.getDouble((Object) message, offset)) != Double.doubleToLongBits(UnsafeUtil.getDouble((Object) other, offset))) {
                    return false;
                }
                return true;
            case 1:
                if (!arePresentForEquals(message, other, pos) || Float.floatToIntBits(UnsafeUtil.getFloat((Object) message, offset)) != Float.floatToIntBits(UnsafeUtil.getFloat((Object) other, offset))) {
                    return false;
                }
                return true;
            case 2:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong((Object) message, offset) != UnsafeUtil.getLong((Object) other, offset)) {
                    return false;
                }
                return true;
            case 3:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong((Object) message, offset) != UnsafeUtil.getLong((Object) other, offset)) {
                    return false;
                }
                return true;
            case 4:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt((Object) message, offset) != UnsafeUtil.getInt((Object) other, offset)) {
                    return false;
                }
                return true;
            case 5:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong((Object) message, offset) != UnsafeUtil.getLong((Object) other, offset)) {
                    return false;
                }
                return true;
            case 6:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt((Object) message, offset) != UnsafeUtil.getInt((Object) other, offset)) {
                    return false;
                }
                return true;
            case 7:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getBoolean((Object) message, offset) != UnsafeUtil.getBoolean((Object) other, offset)) {
                    return false;
                }
                return true;
            case 8:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject((Object) message, offset), UnsafeUtil.getObject((Object) other, offset))) {
                    return false;
                }
                return true;
            case 9:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject((Object) message, offset), UnsafeUtil.getObject((Object) other, offset))) {
                    return false;
                }
                return true;
            case 10:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject((Object) message, offset), UnsafeUtil.getObject((Object) other, offset))) {
                    return false;
                }
                return true;
            case 11:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt((Object) message, offset) != UnsafeUtil.getInt((Object) other, offset)) {
                    return false;
                }
                return true;
            case 12:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt((Object) message, offset) != UnsafeUtil.getInt((Object) other, offset)) {
                    return false;
                }
                return true;
            case 13:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt((Object) message, offset) != UnsafeUtil.getInt((Object) other, offset)) {
                    return false;
                }
                return true;
            case 14:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong((Object) message, offset) != UnsafeUtil.getLong((Object) other, offset)) {
                    return false;
                }
                return true;
            case 15:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getInt((Object) message, offset) != UnsafeUtil.getInt((Object) other, offset)) {
                    return false;
                }
                return true;
            case 16:
                if (!arePresentForEquals(message, other, pos) || UnsafeUtil.getLong((Object) message, offset) != UnsafeUtil.getLong((Object) other, offset)) {
                    return false;
                }
                return true;
            case 17:
                if (!arePresentForEquals(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject((Object) message, offset), UnsafeUtil.getObject((Object) other, offset))) {
                    return false;
                }
                return true;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE:
            case 49:
                return SchemaUtil.safeEquals(UnsafeUtil.getObject((Object) message, offset), UnsafeUtil.getObject((Object) other, offset));
            case 50:
                return SchemaUtil.safeEquals(UnsafeUtil.getObject((Object) message, offset), UnsafeUtil.getObject((Object) other, offset));
            case 51:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF:
            case 53:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case LockFreeTaskQueueCore.CLOSED_SHIFT:
            case 62:
            case HtmlCompat.FROM_HTML_MODE_COMPACT:
            case 64:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HEIGHT:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT:
            case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL:
            case 68:
                if (!isOneofCaseEqual(message, other, pos) || !SchemaUtil.safeEquals(UnsafeUtil.getObject((Object) message, offset), UnsafeUtil.getObject((Object) other, offset))) {
                    return false;
                }
                return true;
            default:
                return true;
        }
    }

    public int hashCode(T message) {
        int hashCode = 0;
        int bufferLength = this.buffer.length;
        for (int pos = 0; pos < bufferLength; pos += 3) {
            int typeAndOffset = typeAndOffsetAt(pos);
            int entryNumber = numberAt(pos);
            long offset = offset(typeAndOffset);
            switch (type(typeAndOffset)) {
                case 0:
                    hashCode = (hashCode * 53) + Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.getDouble((Object) message, offset)));
                    break;
                case 1:
                    hashCode = (hashCode * 53) + Float.floatToIntBits(UnsafeUtil.getFloat((Object) message, offset));
                    break;
                case 2:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong((Object) message, offset));
                    break;
                case 3:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong((Object) message, offset));
                    break;
                case 4:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt((Object) message, offset);
                    break;
                case 5:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong((Object) message, offset));
                    break;
                case 6:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt((Object) message, offset);
                    break;
                case 7:
                    hashCode = (hashCode * 53) + Internal.hashBoolean(UnsafeUtil.getBoolean((Object) message, offset));
                    break;
                case 8:
                    hashCode = (hashCode * 53) + ((String) UnsafeUtil.getObject((Object) message, offset)).hashCode();
                    break;
                case 9:
                    int protoHash = 37;
                    Object submessage = UnsafeUtil.getObject((Object) message, offset);
                    if (submessage != null) {
                        protoHash = submessage.hashCode();
                    }
                    hashCode = (hashCode * 53) + protoHash;
                    break;
                case 10:
                    hashCode = (hashCode * 53) + UnsafeUtil.getObject((Object) message, offset).hashCode();
                    break;
                case 11:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt((Object) message, offset);
                    break;
                case 12:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt((Object) message, offset);
                    break;
                case 13:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt((Object) message, offset);
                    break;
                case 14:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong((Object) message, offset));
                    break;
                case 15:
                    hashCode = (hashCode * 53) + UnsafeUtil.getInt((Object) message, offset);
                    break;
                case 16:
                    hashCode = (hashCode * 53) + Internal.hashLong(UnsafeUtil.getLong((Object) message, offset));
                    break;
                case 17:
                    int protoHash2 = 37;
                    Object submessage2 = UnsafeUtil.getObject((Object) message, offset);
                    if (submessage2 != null) {
                        protoHash2 = submessage2.hashCode();
                    }
                    hashCode = (hashCode * 53) + protoHash2;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE:
                case 49:
                    hashCode = (hashCode * 53) + UnsafeUtil.getObject((Object) message, offset).hashCode();
                    break;
                case 50:
                    hashCode = (hashCode * 53) + UnsafeUtil.getObject((Object) message, offset).hashCode();
                    break;
                case 51:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(message, offset)));
                        break;
                    }
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Float.floatToIntBits(oneofFloatAt(message, offset));
                        break;
                    }
                case 53:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 55:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 56:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 57:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 58:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashBoolean(oneofBooleanAt(message, offset));
                        break;
                    }
                case 59:
                    if (!isOneofPresent(message, entryNumber, pos)) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + ((String) UnsafeUtil.getObject((Object) message, offset)).hashCode();
                        break;
                    }
                case 60:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + UnsafeUtil.getObject((Object) message, offset).hashCode();
                        break;
                    }
                case LockFreeTaskQueueCore.CLOSED_SHIFT:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + UnsafeUtil.getObject((Object) message, offset).hashCode();
                        break;
                    }
                case 62:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case HtmlCompat.FROM_HTML_MODE_COMPACT:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case 64:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HEIGHT:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT:
                    if (isOneofPresent(message, entryNumber, pos) == 0) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + oneofIntAt(message, offset);
                        break;
                    }
                case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL:
                    if (!isOneofPresent(message, entryNumber, pos)) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + Internal.hashLong(oneofLongAt(message, offset));
                        break;
                    }
                case 68:
                    if (!isOneofPresent(message, entryNumber, pos)) {
                        break;
                    } else {
                        hashCode = (hashCode * 53) + UnsafeUtil.getObject((Object) message, offset).hashCode();
                        break;
                    }
            }
        }
        int hashCode2 = (hashCode * 53) + this.unknownFieldSchema.getFromMessage(message).hashCode();
        if (this.hasExtensions != 0) {
            return (hashCode2 * 53) + this.extensionSchema.getExtensions(message).hashCode();
        }
        return hashCode2;
    }

    public void mergeFrom(T message, T other) {
        checkMutable(message);
        if (other != null) {
            for (int i = 0; i < this.buffer.length; i += 3) {
                mergeSingleField(message, other, i);
            }
            SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
            if (this.hasExtensions) {
                SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private void mergeSingleField(T message, T other, int pos) {
        int typeAndOffset = typeAndOffsetAt(pos);
        long offset = offset(typeAndOffset);
        int number = numberAt(pos);
        switch (type(typeAndOffset)) {
            case 0:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putDouble((Object) message, offset, UnsafeUtil.getDouble((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 1:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putFloat((Object) message, offset, UnsafeUtil.getFloat((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 2:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong((Object) message, offset, UnsafeUtil.getLong((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 3:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong((Object) message, offset, UnsafeUtil.getLong((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 4:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt((Object) message, offset, UnsafeUtil.getInt((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 5:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong((Object) message, offset, UnsafeUtil.getLong((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 6:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt((Object) message, offset, UnsafeUtil.getInt((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 7:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putBoolean((Object) message, offset, UnsafeUtil.getBoolean((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 8:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject((Object) message, offset, UnsafeUtil.getObject((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 9:
                mergeMessage(message, other, pos);
                return;
            case 10:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject((Object) message, offset, UnsafeUtil.getObject((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 11:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt((Object) message, offset, UnsafeUtil.getInt((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 12:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt((Object) message, offset, UnsafeUtil.getInt((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 13:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt((Object) message, offset, UnsafeUtil.getInt((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 14:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong((Object) message, offset, UnsafeUtil.getLong((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 15:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt((Object) message, offset, UnsafeUtil.getInt((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 16:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong((Object) message, offset, UnsafeUtil.getLong((Object) other, offset));
                    setFieldPresent(message, pos);
                    return;
                }
                return;
            case 17:
                mergeMessage(message, other, pos);
                return;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE:
            case 49:
                this.listFieldSchema.mergeListsAt(message, other, offset);
                return;
            case 50:
                SchemaUtil.mergeMap(this.mapFieldSchema, message, other, offset);
                return;
            case 51:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF:
            case 53:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                if (isOneofPresent(other, number, pos)) {
                    UnsafeUtil.putObject((Object) message, offset, UnsafeUtil.getObject((Object) other, offset));
                    setOneofPresent(message, number, pos);
                    return;
                }
                return;
            case 60:
                mergeOneofMessage(message, other, pos);
                return;
            case LockFreeTaskQueueCore.CLOSED_SHIFT:
            case 62:
            case HtmlCompat.FROM_HTML_MODE_COMPACT:
            case 64:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HEIGHT:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT:
            case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL:
                if (isOneofPresent(other, number, pos)) {
                    UnsafeUtil.putObject((Object) message, offset, UnsafeUtil.getObject((Object) other, offset));
                    setOneofPresent(message, number, pos);
                    return;
                }
                return;
            case 68:
                mergeOneofMessage(message, other, pos);
                return;
            default:
                return;
        }
    }

    private void mergeMessage(T targetParent, T sourceParent, int pos) {
        if (isFieldPresent(sourceParent, pos)) {
            long offset = offset(typeAndOffsetAt(pos));
            Object source = UNSAFE.getObject(sourceParent, offset);
            if (source != null) {
                Schema fieldSchema = getMessageFieldSchema(pos);
                if (!isFieldPresent(targetParent, pos)) {
                    if (!isMutable(source)) {
                        UNSAFE.putObject(targetParent, offset, source);
                    } else {
                        Object copyOfSource = fieldSchema.newInstance();
                        fieldSchema.mergeFrom(copyOfSource, source);
                        UNSAFE.putObject(targetParent, offset, copyOfSource);
                    }
                    setFieldPresent(targetParent, pos);
                    return;
                }
                Object target = UNSAFE.getObject(targetParent, offset);
                if (!isMutable(target)) {
                    Object newInstance = fieldSchema.newInstance();
                    fieldSchema.mergeFrom(newInstance, target);
                    UNSAFE.putObject(targetParent, offset, newInstance);
                    target = newInstance;
                }
                fieldSchema.mergeFrom(target, source);
                return;
            }
            throw new IllegalStateException("Source subfield " + numberAt(pos) + " is present but null: " + sourceParent);
        }
    }

    private void mergeOneofMessage(T targetParent, T sourceParent, int pos) {
        int number = numberAt(pos);
        if (isOneofPresent(sourceParent, number, pos)) {
            long offset = offset(typeAndOffsetAt(pos));
            Object source = UNSAFE.getObject(sourceParent, offset);
            if (source != null) {
                Schema fieldSchema = getMessageFieldSchema(pos);
                if (!isOneofPresent(targetParent, number, pos)) {
                    if (!isMutable(source)) {
                        UNSAFE.putObject(targetParent, offset, source);
                    } else {
                        Object copyOfSource = fieldSchema.newInstance();
                        fieldSchema.mergeFrom(copyOfSource, source);
                        UNSAFE.putObject(targetParent, offset, copyOfSource);
                    }
                    setOneofPresent(targetParent, number, pos);
                    return;
                }
                Object target = UNSAFE.getObject(targetParent, offset);
                if (!isMutable(target)) {
                    Object newInstance = fieldSchema.newInstance();
                    fieldSchema.mergeFrom(newInstance, target);
                    UNSAFE.putObject(targetParent, offset, newInstance);
                    target = newInstance;
                }
                fieldSchema.mergeFrom(target, source);
                return;
            }
            throw new IllegalStateException("Source subfield " + numberAt(pos) + " is present but null: " + sourceParent);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getSerializedSize(T r21) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = 0
            sun.misc.Unsafe r6 = UNSAFE
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r4 = 0
            r5 = 0
            r7 = r2
            r2 = r5
        L_0x000e:
            int[] r5 = r0.buffer
            int r5 = r5.length
            if (r2 >= r5) goto L_0x070d
            int r8 = r0.typeAndOffsetAt(r2)
            int r9 = type(r8)
            int r10 = r0.numberAt(r2)
            r5 = 0
            int[] r11 = r0.buffer
            int r12 = r2 + 2
            r11 = r11[r12]
            r12 = 1048575(0xfffff, float:1.469367E-39)
            r13 = r11 & r12
            r14 = 17
            r16 = 1
            if (r9 > r14) goto L_0x004a
            if (r13 == r3) goto L_0x0043
            r3 = r13
            if (r3 != r12) goto L_0x003a
            r12 = r4
            r14 = r5
            r4 = 0
            goto L_0x0041
        L_0x003a:
            r12 = r4
            r14 = r5
            long r4 = (long) r3
            int r4 = r6.getInt(r1, r4)
        L_0x0041:
            goto L_0x0045
        L_0x0043:
            r12 = r4
            r14 = r5
        L_0x0045:
            int r5 = r11 >>> 20
            int r5 = r16 << r5
            goto L_0x004c
        L_0x004a:
            r12 = r4
            r14 = r5
        L_0x004c:
            r14 = r7
            r17 = r8
            long r7 = offset(r17)
            com.google.protobuf.FieldType r12 = com.google.protobuf.FieldType.DOUBLE_LIST_PACKED
            int r12 = r12.id()
            if (r9 < r12) goto L_0x0065
            com.google.protobuf.FieldType r12 = com.google.protobuf.FieldType.SINT64_LIST_PACKED
            int r12 = r12.id()
            if (r9 > r12) goto L_0x0065
            r12 = r13
            goto L_0x0066
        L_0x0065:
            r12 = 0
        L_0x0066:
            r15 = 0
            r18 = r13
            r19 = r14
            r13 = 0
            switch(r9) {
                case 0: goto L_0x06f3;
                case 1: goto L_0x06da;
                case 2: goto L_0x06c1;
                case 3: goto L_0x06a8;
                case 4: goto L_0x068b;
                case 5: goto L_0x066e;
                case 6: goto L_0x0650;
                case 7: goto L_0x0634;
                case 8: goto L_0x060a;
                case 9: goto L_0x05f1;
                case 10: goto L_0x05d4;
                case 11: goto L_0x05b9;
                case 12: goto L_0x059c;
                case 13: goto L_0x057e;
                case 14: goto L_0x0563;
                case 15: goto L_0x0548;
                case 16: goto L_0x052f;
                case 17: goto L_0x0515;
                case 18: goto L_0x0505;
                case 19: goto L_0x04f5;
                case 20: goto L_0x04e5;
                case 21: goto L_0x04d5;
                case 22: goto L_0x04c5;
                case 23: goto L_0x04b5;
                case 24: goto L_0x04a5;
                case 25: goto L_0x0494;
                case 26: goto L_0x0484;
                case 27: goto L_0x0470;
                case 28: goto L_0x0460;
                case 29: goto L_0x0450;
                case 30: goto L_0x0440;
                case 31: goto L_0x0430;
                case 32: goto L_0x0420;
                case 33: goto L_0x0410;
                case 34: goto L_0x03ff;
                case 35: goto L_0x03da;
                case 36: goto L_0x03b5;
                case 37: goto L_0x0390;
                case 38: goto L_0x036b;
                case 39: goto L_0x0346;
                case 40: goto L_0x0321;
                case 41: goto L_0x02fc;
                case 42: goto L_0x02d7;
                case 43: goto L_0x02b2;
                case 44: goto L_0x028d;
                case 45: goto L_0x0268;
                case 46: goto L_0x0243;
                case 47: goto L_0x021e;
                case 48: goto L_0x01f9;
                case 49: goto L_0x01e5;
                case 50: goto L_0x01d2;
                case 51: goto L_0x01c1;
                case 52: goto L_0x01b2;
                case 53: goto L_0x019f;
                case 54: goto L_0x018c;
                case 55: goto L_0x0179;
                case 56: goto L_0x016a;
                case 57: goto L_0x015a;
                case 58: goto L_0x0149;
                case 59: goto L_0x0125;
                case 60: goto L_0x010e;
                case 61: goto L_0x00f8;
                case 62: goto L_0x00e5;
                case 63: goto L_0x00d2;
                case 64: goto L_0x00c2;
                case 65: goto L_0x00b3;
                case 66: goto L_0x00a0;
                case 67: goto L_0x008d;
                case 68: goto L_0x0073;
                default: goto L_0x0071;
            }
        L_0x0071:
            goto L_0x0707
        L_0x0073:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            java.lang.Object r13 = r6.getObject(r1, r7)
            com.google.protobuf.MessageLite r13 = (com.google.protobuf.MessageLite) r13
            com.google.protobuf.Schema r14 = r0.getMessageFieldSchema(r2)
            int r13 = com.google.protobuf.CodedOutputStream.computeGroupSize(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x008d:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            long r13 = oneofLongAt(r1, r7)
            int r13 = com.google.protobuf.CodedOutputStream.computeSInt64Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x00a0:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            int r13 = oneofIntAt(r1, r7)
            int r13 = com.google.protobuf.CodedOutputStream.computeSInt32Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x00b3:
            boolean r15 = r0.isOneofPresent(r1, r10, r2)
            if (r15 == 0) goto L_0x0707
            int r13 = com.google.protobuf.CodedOutputStream.computeSFixed64Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x00c2:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            r13 = 0
            int r13 = com.google.protobuf.CodedOutputStream.computeSFixed32Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x00d2:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            int r13 = oneofIntAt(r1, r7)
            int r13 = com.google.protobuf.CodedOutputStream.computeEnumSize(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x00e5:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            int r13 = oneofIntAt(r1, r7)
            int r13 = com.google.protobuf.CodedOutputStream.computeUInt32Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x00f8:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            java.lang.Object r13 = r6.getObject(r1, r7)
            com.google.protobuf.ByteString r13 = (com.google.protobuf.ByteString) r13
            int r13 = com.google.protobuf.CodedOutputStream.computeBytesSize(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x010e:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            java.lang.Object r13 = r6.getObject(r1, r7)
            com.google.protobuf.Schema r14 = r0.getMessageFieldSchema(r2)
            int r14 = com.google.protobuf.SchemaUtil.computeSizeMessage(r10, r13, r14)
            int r13 = r19 + r14
            r7 = r13
            goto L_0x0709
        L_0x0125:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            java.lang.Object r13 = r6.getObject(r1, r7)
            boolean r14 = r13 instanceof com.google.protobuf.ByteString
            if (r14 == 0) goto L_0x013d
            r14 = r13
            com.google.protobuf.ByteString r14 = (com.google.protobuf.ByteString) r14
            int r14 = com.google.protobuf.CodedOutputStream.computeBytesSize(r10, r14)
            int r14 = r19 + r14
            goto L_0x0146
        L_0x013d:
            r14 = r13
            java.lang.String r14 = (java.lang.String) r14
            int r14 = com.google.protobuf.CodedOutputStream.computeStringSize(r10, r14)
            int r14 = r19 + r14
        L_0x0146:
            r7 = r14
            goto L_0x0709
        L_0x0149:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            r13 = r16
            int r13 = com.google.protobuf.CodedOutputStream.computeBoolSize(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x015a:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            r13 = 0
            int r13 = com.google.protobuf.CodedOutputStream.computeFixed32Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x016a:
            boolean r15 = r0.isOneofPresent(r1, r10, r2)
            if (r15 == 0) goto L_0x0707
            int r13 = com.google.protobuf.CodedOutputStream.computeFixed64Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0179:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            int r13 = oneofIntAt(r1, r7)
            int r13 = com.google.protobuf.CodedOutputStream.computeInt32Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x018c:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            long r13 = oneofLongAt(r1, r7)
            int r13 = com.google.protobuf.CodedOutputStream.computeUInt64Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x019f:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            long r13 = oneofLongAt(r1, r7)
            int r13 = com.google.protobuf.CodedOutputStream.computeInt64Size(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x01b2:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            int r13 = com.google.protobuf.CodedOutputStream.computeFloatSize(r10, r15)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x01c1:
            boolean r13 = r0.isOneofPresent(r1, r10, r2)
            if (r13 == 0) goto L_0x0707
            r13 = 0
            int r13 = com.google.protobuf.CodedOutputStream.computeDoubleSize(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x01d2:
            com.google.protobuf.MapFieldSchema r13 = r0.mapFieldSchema
            java.lang.Object r14 = r6.getObject(r1, r7)
            java.lang.Object r15 = r0.getMapFieldDefaultEntry(r2)
            int r13 = r13.getSerializedSize(r10, r14, r15)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x01e5:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            com.google.protobuf.Schema r14 = r0.getMessageFieldSchema(r2)
            int r13 = com.google.protobuf.SchemaUtil.computeSizeGroupList(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x01f9:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeSInt64ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x020e
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x020e:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x021e:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeSInt32ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x0233
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x0233:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x0243:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed64ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x0258
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x0258:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x0268:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed32ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x027d
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x027d:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x028d:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeEnumListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x02a2
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x02a2:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x02b2:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeUInt32ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x02c7
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x02c7:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x02d7:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeBoolListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x02ec
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x02ec:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x02fc:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed32ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x0311
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x0311:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x0321:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed64ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x0336
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x0336:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x0346:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeInt32ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x035b
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x035b:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x036b:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeUInt64ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x0380
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x0380:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x0390:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeInt64ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x03a5
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x03a5:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x03b5:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed32ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x03ca
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x03ca:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x03da:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed64ListNoTag(r13)
            if (r13 <= 0) goto L_0x0707
            boolean r14 = r0.useCachedSizeField
            if (r14 == 0) goto L_0x03ef
            long r14 = (long) r12
            r6.putInt(r1, r14, r13)
        L_0x03ef:
            int r14 = com.google.protobuf.CodedOutputStream.computeTagSize(r10)
            int r15 = com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(r13)
            int r14 = r14 + r15
            int r14 = r14 + r13
            int r14 = r19 + r14
            r7 = r14
            goto L_0x0709
        L_0x03ff:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            r14 = 0
            int r13 = com.google.protobuf.SchemaUtil.computeSizeSInt64List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0410:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeSInt32List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0420:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed64List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0430:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed32List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0440:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeEnumList(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0450:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeUInt32List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0460:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeByteStringList(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0470:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            com.google.protobuf.Schema r14 = r0.getMessageFieldSchema(r2)
            int r13 = com.google.protobuf.SchemaUtil.computeSizeMessageList(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0484:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeStringList(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0494:
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            r14 = 0
            int r13 = com.google.protobuf.SchemaUtil.computeSizeBoolList(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x04a5:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed32List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x04b5:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed64List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x04c5:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeInt32List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x04d5:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeUInt64List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x04e5:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeInt64List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x04f5:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed32List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0505:
            r14 = 0
            java.lang.Object r13 = r6.getObject(r1, r7)
            java.util.List r13 = (java.util.List) r13
            int r13 = com.google.protobuf.SchemaUtil.computeSizeFixed64List(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0515:
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x0707
            java.lang.Object r13 = r6.getObject(r1, r7)
            com.google.protobuf.MessageLite r13 = (com.google.protobuf.MessageLite) r13
            com.google.protobuf.Schema r14 = r0.getMessageFieldSchema(r2)
            int r13 = com.google.protobuf.CodedOutputStream.computeGroupSize(r10, r13, r14)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x052f:
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x0544
            long r13 = r6.getLong(r1, r7)
            int r0 = com.google.protobuf.CodedOutputStream.computeSInt64Size(r10, r13)
            int r0 = r19 + r0
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x0544:
            r0 = r20
            goto L_0x0707
        L_0x0548:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x055f
            int r0 = r6.getInt(r1, r7)
            int r0 = com.google.protobuf.CodedOutputStream.computeSInt32Size(r10, r0)
            int r0 = r19 + r0
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x055f:
            r0 = r20
            goto L_0x0707
        L_0x0563:
            r0 = r20
            boolean r15 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r15 == 0) goto L_0x0578
            int r0 = com.google.protobuf.CodedOutputStream.computeSFixed64Size(r10, r13)
            int r0 = r19 + r0
            r1 = r21
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x0578:
            r0 = r20
            r1 = r21
            goto L_0x0707
        L_0x057e:
            r0 = r20
            r1 = r21
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x0596
            r14 = 0
            int r0 = com.google.protobuf.CodedOutputStream.computeSFixed32Size(r10, r14)
            int r0 = r19 + r0
            r1 = r21
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x0596:
            r0 = r20
            r1 = r21
            goto L_0x0707
        L_0x059c:
            r0 = r20
            r1 = r21
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x05b5
            int r0 = r6.getInt(r1, r7)
            int r0 = com.google.protobuf.CodedOutputStream.computeEnumSize(r10, r0)
            int r0 = r19 + r0
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x05b5:
            r0 = r20
            goto L_0x0707
        L_0x05b9:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x05d0
            int r0 = r6.getInt(r1, r7)
            int r0 = com.google.protobuf.CodedOutputStream.computeUInt32Size(r10, r0)
            int r0 = r19 + r0
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x05d0:
            r0 = r20
            goto L_0x0707
        L_0x05d4:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x05ed
            java.lang.Object r0 = r6.getObject(r1, r7)
            com.google.protobuf.ByteString r0 = (com.google.protobuf.ByteString) r0
            int r13 = com.google.protobuf.CodedOutputStream.computeBytesSize(r10, r0)
            int r0 = r19 + r13
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x05ed:
            r0 = r20
            goto L_0x0707
        L_0x05f1:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x0707
            java.lang.Object r13 = r6.getObject(r1, r7)
            com.google.protobuf.Schema r14 = r0.getMessageFieldSchema(r2)
            int r14 = com.google.protobuf.SchemaUtil.computeSizeMessage(r10, r13, r14)
            int r13 = r19 + r14
            r7 = r13
            goto L_0x0709
        L_0x060a:
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x0630
            java.lang.Object r0 = r6.getObject(r1, r7)
            boolean r13 = r0 instanceof com.google.protobuf.ByteString
            if (r13 == 0) goto L_0x0622
            r13 = r0
            com.google.protobuf.ByteString r13 = (com.google.protobuf.ByteString) r13
            int r13 = com.google.protobuf.CodedOutputStream.computeBytesSize(r10, r13)
            int r13 = r19 + r13
            goto L_0x062b
        L_0x0622:
            r13 = r0
            java.lang.String r13 = (java.lang.String) r13
            int r13 = com.google.protobuf.CodedOutputStream.computeStringSize(r10, r13)
            int r13 = r19 + r13
        L_0x062b:
            r0 = r20
            r7 = r13
            goto L_0x0709
        L_0x0630:
            r0 = r20
            goto L_0x0707
        L_0x0634:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x064a
            r13 = 1
            int r0 = com.google.protobuf.CodedOutputStream.computeBoolSize(r10, r13)
            int r0 = r19 + r0
            r1 = r21
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x064a:
            r0 = r20
            r1 = r21
            goto L_0x0707
        L_0x0650:
            r0 = r20
            r1 = r21
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x0668
            r14 = 0
            int r0 = com.google.protobuf.CodedOutputStream.computeFixed32Size(r10, r14)
            int r0 = r19 + r0
            r1 = r21
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x0668:
            r0 = r20
            r1 = r21
            goto L_0x0707
        L_0x066e:
            r0 = r20
            r1 = r21
            boolean r15 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r15 == 0) goto L_0x0685
            int r0 = com.google.protobuf.CodedOutputStream.computeFixed64Size(r10, r13)
            int r0 = r19 + r0
            r1 = r21
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x0685:
            r0 = r20
            r1 = r21
            goto L_0x0707
        L_0x068b:
            r0 = r20
            r1 = r21
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x06a4
            int r0 = r6.getInt(r1, r7)
            int r0 = com.google.protobuf.CodedOutputStream.computeInt32Size(r10, r0)
            int r0 = r19 + r0
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x06a4:
            r0 = r20
            goto L_0x0707
        L_0x06a8:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x06be
            long r13 = r6.getLong(r1, r7)
            int r0 = com.google.protobuf.CodedOutputStream.computeUInt64Size(r10, r13)
            int r0 = r19 + r0
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x06be:
            r0 = r20
            goto L_0x0707
        L_0x06c1:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x06d7
            long r13 = r6.getLong(r1, r7)
            int r0 = com.google.protobuf.CodedOutputStream.computeInt64Size(r10, r13)
            int r0 = r19 + r0
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x06d7:
            r0 = r20
            goto L_0x0707
        L_0x06da:
            r0 = r20
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x06ee
            int r0 = com.google.protobuf.CodedOutputStream.computeFloatSize(r10, r15)
            int r0 = r19 + r0
            r1 = r21
            r7 = r0
            r0 = r20
            goto L_0x0709
        L_0x06ee:
            r0 = r20
            r1 = r21
            goto L_0x0707
        L_0x06f3:
            r0 = r20
            r1 = r21
            boolean r13 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r13 == 0) goto L_0x0707
            r13 = 0
            int r13 = com.google.protobuf.CodedOutputStream.computeDoubleSize(r10, r13)
            int r13 = r19 + r13
            r7 = r13
            goto L_0x0709
        L_0x0707:
            r7 = r19
        L_0x0709:
            int r2 = r2 + 3
            goto L_0x000e
        L_0x070d:
            r12 = r4
            r19 = r7
            com.google.protobuf.UnknownFieldSchema<?, ?> r2 = r0.unknownFieldSchema
            int r2 = r0.getUnknownFieldsSerializedSize(r2, r1)
            int r7 = r19 + r2
            boolean r2 = r0.hasExtensions
            if (r2 == 0) goto L_0x0727
            com.google.protobuf.ExtensionSchema<?> r2 = r0.extensionSchema
            com.google.protobuf.FieldSet r2 = r2.getExtensions(r1)
            int r2 = r2.getSerializedSize()
            int r7 = r7 + r2
        L_0x0727:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.getSerializedSize(java.lang.Object):int");
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> schema, T message) {
        return schema.getSerializedSize(schema.getFromMessage(message));
    }

    public void writeTo(T message, Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            writeFieldsInDescendingOrder(message, writer);
        } else {
            writeFieldsInAscendingOrder(message, writer);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:194:0x0719  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0032  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeFieldsInAscendingOrder(T r22, com.google.protobuf.Writer r23) throws java.io.IOException {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r6 = r23
            r2 = 0
            r3 = 0
            boolean r4 = r0.hasExtensions
            if (r4 == 0) goto L_0x0025
            com.google.protobuf.ExtensionSchema<?> r4 = r0.extensionSchema
            com.google.protobuf.FieldSet r4 = r4.getExtensions(r1)
            boolean r5 = r4.isEmpty()
            if (r5 != 0) goto L_0x0025
            java.util.Iterator r2 = r4.iterator()
            java.lang.Object r5 = r2.next()
            r3 = r5
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            r7 = r2
            goto L_0x0026
        L_0x0025:
            r7 = r2
        L_0x0026:
            r2 = 1048575(0xfffff, float:1.469367E-39)
            r4 = 0
            int[] r5 = r0.buffer
            int r8 = r5.length
            sun.misc.Unsafe r9 = UNSAFE
            r5 = 0
        L_0x0030:
            if (r5 >= r8) goto L_0x0712
            int r11 = r0.typeAndOffsetAt(r5)
            int r12 = r0.numberAt(r5)
            int r13 = type(r11)
            r14 = 0
            r15 = 17
            if (r13 > r15) goto L_0x0072
            int[] r15 = r0.buffer
            int r16 = r5 + 2
            r15 = r15[r16]
            r16 = 1
            r17 = 1048575(0xfffff, float:1.469367E-39)
            r10 = r15 & r17
            if (r10 == r2) goto L_0x0068
            r2 = r10
            r18 = r3
            r3 = r17
            if (r2 != r3) goto L_0x005d
            r17 = r2
            r2 = 0
            goto L_0x0064
        L_0x005d:
            r17 = r2
            long r2 = (long) r10
            int r2 = r9.getInt(r1, r2)
        L_0x0064:
            r4 = r2
            r2 = r17
            goto L_0x006a
        L_0x0068:
            r18 = r3
        L_0x006a:
            int r3 = r15 >>> 20
            int r14 = r16 << r3
            r3 = r2
            r10 = r18
            goto L_0x0079
        L_0x0072:
            r18 = r3
            r16 = 1
            r3 = r2
            r10 = r18
        L_0x0079:
            if (r10 == 0) goto L_0x0098
            com.google.protobuf.ExtensionSchema<?> r2 = r0.extensionSchema
            int r2 = r2.extensionNumber(r10)
            if (r2 > r12) goto L_0x0098
            com.google.protobuf.ExtensionSchema<?> r2 = r0.extensionSchema
            r2.serializeExtension(r6, r10)
            boolean r2 = r7.hasNext()
            if (r2 == 0) goto L_0x0095
            java.lang.Object r2 = r7.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            goto L_0x0096
        L_0x0095:
            r2 = 0
        L_0x0096:
            r10 = r2
            goto L_0x0079
        L_0x0098:
            r15 = r7
            r17 = r8
            long r7 = offset(r11)
            switch(r13) {
                case 0: goto L_0x06eb;
                case 1: goto L_0x06cb;
                case 2: goto L_0x06a9;
                case 3: goto L_0x0688;
                case 4: goto L_0x0666;
                case 5: goto L_0x0645;
                case 6: goto L_0x0626;
                case 7: goto L_0x0609;
                case 8: goto L_0x05ee;
                case 9: goto L_0x05cb;
                case 10: goto L_0x05aa;
                case 11: goto L_0x058b;
                case 12: goto L_0x056c;
                case 13: goto L_0x054a;
                case 14: goto L_0x0529;
                case 15: goto L_0x0507;
                case 16: goto L_0x04e6;
                case 17: goto L_0x04c0;
                case 18: goto L_0x04aa;
                case 19: goto L_0x0494;
                case 20: goto L_0x047e;
                case 21: goto L_0x0468;
                case 22: goto L_0x0452;
                case 23: goto L_0x043c;
                case 24: goto L_0x0426;
                case 25: goto L_0x0410;
                case 26: goto L_0x03fd;
                case 27: goto L_0x03e4;
                case 28: goto L_0x03d1;
                case 29: goto L_0x03bb;
                case 30: goto L_0x03a5;
                case 31: goto L_0x038f;
                case 32: goto L_0x0379;
                case 33: goto L_0x0363;
                case 34: goto L_0x034d;
                case 35: goto L_0x0336;
                case 36: goto L_0x031f;
                case 37: goto L_0x0308;
                case 38: goto L_0x02f1;
                case 39: goto L_0x02da;
                case 40: goto L_0x02c3;
                case 41: goto L_0x02ac;
                case 42: goto L_0x0295;
                case 43: goto L_0x027e;
                case 44: goto L_0x0267;
                case 45: goto L_0x0250;
                case 46: goto L_0x0239;
                case 47: goto L_0x0222;
                case 48: goto L_0x020b;
                case 49: goto L_0x01f2;
                case 50: goto L_0x01e7;
                case 51: goto L_0x01d6;
                case 52: goto L_0x01c5;
                case 53: goto L_0x01b4;
                case 54: goto L_0x01a3;
                case 55: goto L_0x0192;
                case 56: goto L_0x0181;
                case 57: goto L_0x0170;
                case 58: goto L_0x015f;
                case 59: goto L_0x014e;
                case 60: goto L_0x0139;
                case 61: goto L_0x0126;
                case 62: goto L_0x0115;
                case 63: goto L_0x0104;
                case 64: goto L_0x00f3;
                case 65: goto L_0x00e2;
                case 66: goto L_0x00d1;
                case 67: goto L_0x00c0;
                case 68: goto L_0x00a6;
                default: goto L_0x00a2;
            }
        L_0x00a2:
            r18 = r3
            goto L_0x0708
        L_0x00a6:
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x00bc
            java.lang.Object r2 = r9.getObject(r1, r7)
            r18 = r3
            com.google.protobuf.Schema r3 = r0.getMessageFieldSchema(r5)
            r6.writeGroup(r12, r2, r3)
            goto L_0x0708
        L_0x00bc:
            r18 = r3
            goto L_0x0708
        L_0x00c0:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            long r2 = oneofLongAt(r1, r7)
            r6.writeSInt64(r12, r2)
            goto L_0x0708
        L_0x00d1:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            int r2 = oneofIntAt(r1, r7)
            r6.writeSInt32(r12, r2)
            goto L_0x0708
        L_0x00e2:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            long r2 = oneofLongAt(r1, r7)
            r6.writeSFixed64(r12, r2)
            goto L_0x0708
        L_0x00f3:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            int r2 = oneofIntAt(r1, r7)
            r6.writeSFixed32(r12, r2)
            goto L_0x0708
        L_0x0104:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            int r2 = oneofIntAt(r1, r7)
            r6.writeEnum(r12, r2)
            goto L_0x0708
        L_0x0115:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            int r2 = oneofIntAt(r1, r7)
            r6.writeUInt32(r12, r2)
            goto L_0x0708
        L_0x0126:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            java.lang.Object r2 = r9.getObject(r1, r7)
            com.google.protobuf.ByteString r2 = (com.google.protobuf.ByteString) r2
            r6.writeBytes(r12, r2)
            goto L_0x0708
        L_0x0139:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            java.lang.Object r2 = r9.getObject(r1, r7)
            com.google.protobuf.Schema r3 = r0.getMessageFieldSchema(r5)
            r6.writeMessage(r12, r2, r3)
            goto L_0x0708
        L_0x014e:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            java.lang.Object r2 = r9.getObject(r1, r7)
            r0.writeString(r12, r2, r6)
            goto L_0x0708
        L_0x015f:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            boolean r2 = oneofBooleanAt(r1, r7)
            r6.writeBool(r12, r2)
            goto L_0x0708
        L_0x0170:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            int r2 = oneofIntAt(r1, r7)
            r6.writeFixed32(r12, r2)
            goto L_0x0708
        L_0x0181:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            long r2 = oneofLongAt(r1, r7)
            r6.writeFixed64(r12, r2)
            goto L_0x0708
        L_0x0192:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            int r2 = oneofIntAt(r1, r7)
            r6.writeInt32(r12, r2)
            goto L_0x0708
        L_0x01a3:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            long r2 = oneofLongAt(r1, r7)
            r6.writeUInt64(r12, r2)
            goto L_0x0708
        L_0x01b4:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            long r2 = oneofLongAt(r1, r7)
            r6.writeInt64(r12, r2)
            goto L_0x0708
        L_0x01c5:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            float r2 = oneofFloatAt(r1, r7)
            r6.writeFloat(r12, r2)
            goto L_0x0708
        L_0x01d6:
            r18 = r3
            boolean r2 = r0.isOneofPresent(r1, r12, r5)
            if (r2 == 0) goto L_0x0708
            double r2 = oneofDoubleAt(r1, r7)
            r6.writeDouble(r12, r2)
            goto L_0x0708
        L_0x01e7:
            r18 = r3
            java.lang.Object r2 = r9.getObject(r1, r7)
            r0.writeMapHelper(r6, r12, r2, r5)
            goto L_0x0708
        L_0x01f2:
            r18 = r3
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            r19 = r4
            com.google.protobuf.Schema r4 = r0.getMessageFieldSchema(r5)
            com.google.protobuf.SchemaUtil.writeGroupList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x020b:
            r18 = r3
            r19 = r4
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            r4 = r16
            com.google.protobuf.SchemaUtil.writeSInt64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0222:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeSInt32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0239:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeSFixed64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0250:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeSFixed32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0267:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeEnumList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x027e:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeUInt32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0295:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeBoolList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x02ac:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeFixed32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x02c3:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeFixed64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x02da:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeInt32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x02f1:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeUInt64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0308:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeInt64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x031f:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeFloatList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0336:
            r18 = r3
            r19 = r4
            r4 = r16
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeDoubleList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x034d:
            r18 = r3
            r19 = r4
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            r4 = 0
            com.google.protobuf.SchemaUtil.writeSInt64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0363:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeSInt32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0379:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeSFixed64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x038f:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeSFixed32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x03a5:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeEnumList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x03bb:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeUInt32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x03d1:
            r18 = r3
            r19 = r4
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeBytesList(r2, r3, r6)
            goto L_0x0708
        L_0x03e4:
            r18 = r3
            r19 = r4
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.Schema r4 = r0.getMessageFieldSchema(r5)
            com.google.protobuf.SchemaUtil.writeMessageList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x03fd:
            r18 = r3
            r19 = r4
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeStringList(r2, r3, r6)
            goto L_0x0708
        L_0x0410:
            r18 = r3
            r19 = r4
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            r4 = 0
            com.google.protobuf.SchemaUtil.writeBoolList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0426:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeFixed32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x043c:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeFixed64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0452:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeInt32List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0468:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeUInt64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x047e:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeInt64List(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x0494:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeFloatList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x04aa:
            r18 = r3
            r19 = r4
            r4 = 0
            int r2 = r0.numberAt(r5)
            java.lang.Object r3 = r9.getObject(r1, r7)
            java.util.List r3 = (java.util.List) r3
            com.google.protobuf.SchemaUtil.writeDoubleList(r2, r3, r6, r4)
            r4 = r19
            goto L_0x0708
        L_0x04c0:
            r18 = r3
            r19 = r4
            r2 = r5
            r5 = r14
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x04df
            java.lang.Object r14 = r9.getObject(r1, r7)
            com.google.protobuf.Schema r1 = r0.getMessageFieldSchema(r2)
            r6.writeGroup(r12, r14, r1)
            r1 = r22
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x04df:
            r1 = r22
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x04e6:
            r2 = r5
            r5 = r14
            r1 = r22
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x04ff
            r0 = r2
            r18 = r3
            long r2 = r9.getLong(r1, r7)
            r6.writeSInt64(r12, r2)
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x04ff:
            r0 = r2
            r18 = r3
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x0507:
            r18 = r3
            r0 = r5
            r5 = r14
            r2 = r0
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x0522
            int r0 = r9.getInt(r1, r7)
            r6.writeSInt32(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0522:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0529:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x0542
            r0 = r2
            r18 = r3
            long r2 = r9.getLong(r1, r7)
            r6.writeSFixed64(r12, r2)
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x0542:
            r0 = r2
            r18 = r3
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x054a:
            r18 = r3
            r0 = r5
            r5 = r14
            r2 = r0
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x0565
            int r0 = r9.getInt(r1, r7)
            r6.writeSFixed32(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0565:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x056c:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x0584
            int r0 = r9.getInt(r1, r7)
            r6.writeEnum(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0584:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x058b:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x05a3
            int r0 = r9.getInt(r1, r7)
            r6.writeUInt32(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x05a3:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x05aa:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x05c4
            java.lang.Object r0 = r9.getObject(r1, r7)
            com.google.protobuf.ByteString r0 = (com.google.protobuf.ByteString) r0
            r6.writeBytes(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x05c4:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x05cb:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x05e7
            java.lang.Object r14 = r9.getObject(r1, r7)
            com.google.protobuf.Schema r1 = r0.getMessageFieldSchema(r2)
            r6.writeMessage(r12, r14, r1)
            r1 = r22
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x05e7:
            r1 = r22
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x05ee:
            r2 = r5
            r5 = r14
            r1 = r22
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x0604
            java.lang.Object r14 = r9.getObject(r1, r7)
            r0.writeString(r12, r14, r6)
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0604:
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0609:
            r2 = r5
            r5 = r14
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x061f
            boolean r0 = booleanAt(r1, r7)
            r6.writeBool(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x061f:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0626:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x063e
            int r0 = r9.getInt(r1, r7)
            r6.writeFixed32(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x063e:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0645:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x065e
            r0 = r2
            r18 = r3
            long r2 = r9.getLong(r1, r7)
            r6.writeFixed64(r12, r2)
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x065e:
            r0 = r2
            r18 = r3
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x0666:
            r18 = r3
            r0 = r5
            r5 = r14
            r2 = r0
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x0681
            int r0 = r9.getInt(r1, r7)
            r6.writeInt32(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0681:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x0688:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x06a1
            r0 = r2
            r18 = r3
            long r2 = r9.getLong(r1, r7)
            r6.writeUInt64(r12, r2)
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x06a1:
            r0 = r2
            r18 = r3
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x06a9:
            r18 = r3
            r0 = r5
            r5 = r14
            r2 = r0
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x06c4
            r0 = r2
            r18 = r3
            long r2 = r9.getLong(r1, r7)
            r6.writeInt64(r12, r2)
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x06c4:
            r0 = r2
            r18 = r3
            r5 = r0
            r0 = r21
            goto L_0x0708
        L_0x06cb:
            r18 = r3
            r0 = r5
            r5 = r14
            r2 = r0
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r14 == 0) goto L_0x06e5
            float r0 = floatAt(r1, r7)
            r6.writeFloat(r12, r0)
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x06e5:
            r0 = r21
            r5 = r2
            r18 = r3
            goto L_0x0708
        L_0x06eb:
            r2 = r5
            r5 = r14
            r0 = r21
            boolean r14 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            r20 = r5
            r5 = r2
            r2 = r20
            if (r14 == 0) goto L_0x0705
            r14 = r2
            r18 = r3
            double r2 = doubleAt(r1, r7)
            r6.writeDouble(r12, r2)
            goto L_0x0708
        L_0x0705:
            r14 = r2
            r18 = r3
        L_0x0708:
            int r5 = r5 + 3
            r3 = r10
            r7 = r15
            r8 = r17
            r2 = r18
            goto L_0x0030
        L_0x0712:
            r18 = r3
            r15 = r7
            r17 = r8
        L_0x0717:
            if (r3 == 0) goto L_0x072e
            com.google.protobuf.ExtensionSchema<?> r5 = r0.extensionSchema
            r5.serializeExtension(r6, r3)
            boolean r5 = r15.hasNext()
            if (r5 == 0) goto L_0x072b
            java.lang.Object r5 = r15.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x072c
        L_0x072b:
            r5 = 0
        L_0x072c:
            r3 = r5
            goto L_0x0717
        L_0x072e:
            com.google.protobuf.UnknownFieldSchema<?, ?> r5 = r0.unknownFieldSchema
            r0.writeUnknownInMessageTo(r5, r1, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.writeFieldsInAscendingOrder(java.lang.Object, com.google.protobuf.Writer):void");
    }

    private void writeFieldsInDescendingOrder(T message, Writer writer) throws IOException {
        writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
        Iterator<Map.Entry<?, Object>> it = null;
        Map.Entry nextExtension = null;
        if (this.hasExtensions) {
            FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
            if (!extensions.isEmpty()) {
                it = extensions.descendingIterator();
                nextExtension = it.next();
            }
        }
        int pos = this.buffer.length;
        while (true) {
            pos -= 3;
            if (pos >= 0) {
                int typeAndOffset = typeAndOffsetAt(pos);
                int number = numberAt(pos);
                while (nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) > number) {
                    this.extensionSchema.serializeExtension(writer, nextExtension);
                    nextExtension = it.hasNext() ? it.next() : null;
                }
                switch (type(typeAndOffset)) {
                    case 0:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeDouble(number, doubleAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 1:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeFloat(number, floatAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 2:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeInt64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 3:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeUInt64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 4:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeInt32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 5:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeFixed64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 6:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeFixed32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 7:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeBool(number, booleanAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 8:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writeString(number, UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer);
                            break;
                        }
                    case 9:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeMessage(number, UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                    case 10:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeBytes(number, (ByteString) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)));
                            break;
                        }
                    case 11:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeUInt32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 12:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeEnum(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 13:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSFixed32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 14:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSFixed64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 15:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSInt32(number, intAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 16:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeSInt64(number, longAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 17:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            writer.writeGroup(number, UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                    case 18:
                        SchemaUtil.writeDoubleList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 19:
                        SchemaUtil.writeFloatList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 20:
                        SchemaUtil.writeInt64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 21:
                        SchemaUtil.writeUInt64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 22:
                        SchemaUtil.writeInt32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 23:
                        SchemaUtil.writeFixed64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 24:
                        SchemaUtil.writeFixed32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 25:
                        SchemaUtil.writeBoolList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 26:
                        SchemaUtil.writeStringList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer);
                        break;
                    case 27:
                        SchemaUtil.writeMessageList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, getMessageFieldSchema(pos));
                        break;
                    case 28:
                        SchemaUtil.writeBytesList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer);
                        break;
                    case 29:
                        SchemaUtil.writeUInt32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 30:
                        SchemaUtil.writeEnumList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 31:
                        SchemaUtil.writeSFixed32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 32:
                        SchemaUtil.writeSFixed64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 33:
                        SchemaUtil.writeSInt32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 34:
                        SchemaUtil.writeSInt64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, false);
                        break;
                    case 35:
                        SchemaUtil.writeDoubleList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 36:
                        SchemaUtil.writeFloatList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 37:
                        SchemaUtil.writeInt64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 38:
                        SchemaUtil.writeUInt64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 39:
                        SchemaUtil.writeInt32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 40:
                        SchemaUtil.writeFixed64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 41:
                        SchemaUtil.writeFixed32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 42:
                        SchemaUtil.writeBoolList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 43:
                        SchemaUtil.writeUInt32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 44:
                        SchemaUtil.writeEnumList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 45:
                        SchemaUtil.writeSFixed32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 46:
                        SchemaUtil.writeSFixed64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 47:
                        SchemaUtil.writeSInt32List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE:
                        SchemaUtil.writeSInt64List(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, true);
                        break;
                    case 49:
                        SchemaUtil.writeGroupList(numberAt(pos), (List) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer, getMessageFieldSchema(pos));
                        break;
                    case 50:
                        writeMapHelper(writer, number, UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), pos);
                        break;
                    case 51:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeDouble(number, oneofDoubleAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeFloat(number, oneofFloatAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 53:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeUInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 55:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 56:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 57:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 58:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeBool(number, oneofBooleanAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 59:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writeString(number, UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), writer);
                            break;
                        }
                    case 60:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeMessage(number, UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                    case LockFreeTaskQueueCore.CLOSED_SHIFT:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeBytes(number, (ByteString) UnsafeUtil.getObject((Object) message, offset(typeAndOffset)));
                            break;
                        }
                    case 62:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeUInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case HtmlCompat.FROM_HTML_MODE_COMPACT:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeEnum(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 64:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HEIGHT:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSInt32(number, oneofIntAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeSInt64(number, oneofLongAt(message, offset(typeAndOffset)));
                            break;
                        }
                    case 68:
                        if (!isOneofPresent(message, number, pos)) {
                            break;
                        } else {
                            writer.writeGroup(number, UnsafeUtil.getObject((Object) message, offset(typeAndOffset)), getMessageFieldSchema(pos));
                            break;
                        }
                }
            } else {
                while (nextExtension != null) {
                    this.extensionSchema.serializeExtension(writer, nextExtension);
                    nextExtension = it.hasNext() ? it.next() : null;
                }
                return;
            }
        }
    }

    private <K, V> void writeMapHelper(Writer writer, int number, Object mapField, int pos) throws IOException {
        if (mapField != null) {
            writer.writeMap(number, this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos)), this.mapFieldSchema.forMapData(mapField));
        }
    }

    private <UT, UB> void writeUnknownInMessageTo(UnknownFieldSchema<UT, UB> schema, T message, Writer writer) throws IOException {
        schema.writeTo(schema.getFromMessage(message), writer);
    }

    public void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (extensionRegistry != null) {
            checkMutable(message);
            mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
            return;
        }
        throw new NullPointerException();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v1, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v19, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v12, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v17, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v18, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v19, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v20, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v21, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v34, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v47, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v23, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v53, resolved type: com.google.protobuf.UnknownFieldSchema<UT, UB>} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0311, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0312, code lost:
        r2 = r20;
        r14 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0319, code lost:
        r2 = r20;
        r14 = r21;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0311 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:103:0x02f3] */
    /* JADX WARNING: Removed duplicated region for block: B:274:0x0891 A[Catch:{ all -> 0x08e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:282:0x08b5  */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x08fb A[LOOP:6: B:302:0x08f7->B:304:0x08fb, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x0910  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <UT, UB, ET extends com.google.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(com.google.protobuf.UnknownFieldSchema<UT, UB> r18, com.google.protobuf.ExtensionSchema<ET> r19, T r20, com.google.protobuf.Reader r21, com.google.protobuf.ExtensionRegistryLite r22) throws java.io.IOException {
        /*
            r17 = this;
            r1 = r17
            r7 = r22
            r0 = 0
            r2 = 0
            r5 = r0
            r8 = r2
        L_0x0008:
            int r2 = r21.getFieldNumber()     // Catch:{ all -> 0x08eb }
            r9 = r2
            int r3 = r1.positionForFieldNumber(r9)     // Catch:{ all -> 0x08eb }
            r10 = r3
            r11 = 0
            if (r10 >= 0) goto L_0x00ee
            r0 = 2147483647(0x7fffffff, float:NaN)
            if (r9 != r0) goto L_0x003f
            int r0 = r1.checkInitializedCount
            r4 = r5
        L_0x001d:
            int r2 = r1.repeatedFieldOffsetStart
            if (r0 >= r2) goto L_0x0034
            int[] r2 = r1.intArray
            r3 = r2[r0]
            r6 = r20
            r5 = r18
            r2 = r20
            java.lang.Object r4 = r1.filterMapUnknownEnumValues(r2, r3, r4, r5, r6)
            r12 = r1
            r6 = r5
            int r0 = r0 + 1
            goto L_0x001d
        L_0x0034:
            r6 = r18
            r2 = r20
            r12 = r1
            if (r4 == 0) goto L_0x003e
            r6.setBuilderToMessage(r2, r4)
        L_0x003e:
            return
        L_0x003f:
            r6 = r18
            r2 = r20
            r12 = r1
            boolean r0 = r12.hasExtensions     // Catch:{ all -> 0x00e1 }
            if (r0 != 0) goto L_0x004d
            r0 = 0
            r1 = r19
            r4 = r0
            goto L_0x0056
        L_0x004d:
            com.google.protobuf.MessageLite r0 = r12.defaultInstance     // Catch:{ all -> 0x00e1 }
            r1 = r19
            java.lang.Object r0 = r1.findExtensionByNumber(r7, r0, r9)     // Catch:{ all -> 0x00e1 }
            r4 = r0
        L_0x0056:
            if (r4 == 0) goto L_0x008c
            if (r8 != 0) goto L_0x0067
            com.google.protobuf.FieldSet r0 = r19.getMutableExtensions(r20)     // Catch:{ all -> 0x0061 }
            r8 = r0
            goto L_0x0067
        L_0x0061:
            r0 = move-exception
            r14 = r21
            r1 = r12
            goto L_0x08f3
        L_0x0067:
            r3 = r7
            r7 = r5
            r5 = r3
            r3 = r8
            r8 = r6
            r6 = r3
            r3 = r21
            java.lang.Object r0 = r1.parseExtension(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x007e }
            r15 = r4
            r14 = r5
            r13 = r7
            r5 = r8
            r7 = r3
            r5 = r0
            r8 = r6
            r1 = r12
            r7 = r14
            goto L_0x0008
        L_0x007e:
            r0 = move-exception
            r14 = r5
            r13 = r7
            r5 = r8
            r7 = r3
            r1 = r14
            r14 = r7
            r7 = r1
            r8 = r6
            r1 = r12
            r6 = r5
            r5 = r13
            goto L_0x08f3
        L_0x008c:
            r15 = r4
            r13 = r5
            r5 = r6
            r14 = r7
            r7 = r21
            boolean r0 = r5.shouldDiscardUnknownFields(r7)     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x00ab
            boolean r0 = r7.skipField()     // Catch:{ all -> 0x00a3 }
            if (r0 == 0) goto L_0x00bd
            r1 = r12
            r5 = r13
            r7 = r14
            goto L_0x0008
        L_0x00a3:
            r0 = move-exception
            r1 = r14
            r14 = r7
            r7 = r1
            r6 = r5
            r1 = r12
            goto L_0x08e9
        L_0x00ab:
            if (r13 != 0) goto L_0x00b2
            java.lang.Object r0 = r5.getBuilderFromMessage(r2)     // Catch:{ all -> 0x00a3 }
            r13 = r0
        L_0x00b2:
            boolean r0 = r5.mergeOneFieldFrom(r13, r7, r11)     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x00bd
            r1 = r12
            r5 = r13
            r7 = r14
            goto L_0x0008
        L_0x00bd:
            int r0 = r12.checkInitializedCount
            r4 = r13
        L_0x00c0:
            int r1 = r12.repeatedFieldOffsetStart
            if (r0 >= r1) goto L_0x00d4
            int[] r1 = r12.intArray
            r3 = r1[r0]
            r6 = r20
            r1 = r12
            java.lang.Object r4 = r1.filterMapUnknownEnumValues(r2, r3, r4, r5, r6)
            r12 = r5
            int r0 = r0 + 1
            r12 = r1
            goto L_0x00c0
        L_0x00d4:
            r1 = r12
            r12 = r5
            if (r4 == 0) goto L_0x00db
            r12.setBuilderToMessage(r2, r4)
        L_0x00db:
            return
        L_0x00dc:
            r0 = move-exception
            r1 = r12
            r12 = r5
            goto L_0x02e8
        L_0x00e1:
            r0 = move-exception
            r13 = r5
            r14 = r7
            r1 = r12
            r7 = r21
            r12 = r6
            r5 = r14
            r14 = r7
            r7 = r5
            r5 = r13
            goto L_0x08f3
        L_0x00ee:
            r12 = r18
            r2 = r20
            r13 = r5
            r14 = r7
            r7 = r21
            int r3 = r1.typeAndOffsetAt(r10)     // Catch:{ all -> 0x08e4 }
            r15 = r3
            int r0 = type(r15)     // Catch:{ InvalidWireTypeException -> 0x0885 }
            switch(r0) {
                case 0: goto L_0x0838;
                case 1: goto L_0x0824;
                case 2: goto L_0x0810;
                case 3: goto L_0x07fc;
                case 4: goto L_0x07e8;
                case 5: goto L_0x07d3;
                case 6: goto L_0x07be;
                case 7: goto L_0x07a9;
                case 8: goto L_0x079c;
                case 9: goto L_0x0784;
                case 10: goto L_0x076f;
                case 11: goto L_0x075a;
                case 12: goto L_0x072f;
                case 13: goto L_0x071a;
                case 14: goto L_0x0705;
                case 15: goto L_0x06f0;
                case 16: goto L_0x06db;
                case 17: goto L_0x06c3;
                case 18: goto L_0x06af;
                case 19: goto L_0x069b;
                case 20: goto L_0x0687;
                case 21: goto L_0x0673;
                case 22: goto L_0x065f;
                case 23: goto L_0x064b;
                case 24: goto L_0x0637;
                case 25: goto L_0x0623;
                case 26: goto L_0x0619;
                case 27: goto L_0x05f0;
                case 28: goto L_0x05cc;
                case 29: goto L_0x05b8;
                case 30: goto L_0x0569;
                case 31: goto L_0x053e;
                case 32: goto L_0x0524;
                case 33: goto L_0x050a;
                case 34: goto L_0x04f0;
                case 35: goto L_0x04d6;
                case 36: goto L_0x04bc;
                case 37: goto L_0x04a2;
                case 38: goto L_0x0488;
                case 39: goto L_0x046e;
                case 40: goto L_0x0454;
                case 41: goto L_0x043a;
                case 42: goto L_0x0420;
                case 43: goto L_0x0406;
                case 44: goto L_0x03b2;
                case 45: goto L_0x039e;
                case 46: goto L_0x038a;
                case 47: goto L_0x0376;
                case 48: goto L_0x0362;
                case 49: goto L_0x031f;
                case 50: goto L_0x02f3;
                case 51: goto L_0x02cd;
                case 52: goto L_0x02b3;
                case 53: goto L_0x0299;
                case 54: goto L_0x027f;
                case 55: goto L_0x0265;
                case 56: goto L_0x024b;
                case 57: goto L_0x0231;
                case 58: goto L_0x0217;
                case 59: goto L_0x020a;
                case 60: goto L_0x01f1;
                case 61: goto L_0x01dc;
                case 62: goto L_0x01c2;
                case 63: goto L_0x0190;
                case 64: goto L_0x0176;
                case 65: goto L_0x015c;
                case 66: goto L_0x0142;
                case 67: goto L_0x0128;
                case 68: goto L_0x010f;
                default: goto L_0x0102;
            }
        L_0x0102:
            r16 = r14
            r14 = r7
            r7 = r16
            if (r13 != 0) goto L_0x0856
            java.lang.Object r0 = r12.getBuilderFromMessage(r2)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x0854
        L_0x010f:
            java.lang.Object r0 = r1.mutableOneofMessageFieldForMerge(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.MessageLite r0 = (com.google.protobuf.MessageLite) r0     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.Schema r3 = r1.getMessageFieldSchema(r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r7.mergeGroupField(r0, r3, r14)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.storeOneofMessageField(r2, r9, r10, r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0128:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            long r5 = r7.readSInt64()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Long r0 = java.lang.Long.valueOf(r5)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0142:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            int r0 = r7.readSInt32()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x015c:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            long r5 = r7.readSFixed64()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Long r0 = java.lang.Long.valueOf(r5)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0176:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            int r0 = r7.readSFixed32()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0190:
            int r0 = r7.readEnum()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.Internal$EnumVerifier r3 = r1.getEnumFieldVerifier(r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            if (r3 == 0) goto L_0x01ad
            boolean r4 = r3.isInRange(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            if (r4 == 0) goto L_0x01a1
            goto L_0x01ad
        L_0x01a1:
            java.lang.Object r4 = com.google.protobuf.SchemaUtil.storeUnknownEnum(r2, r9, r0, r13, r12)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r5 = r4
            r6 = r14
            r14 = r7
            r7 = r6
            r6 = r12
            goto L_0x087c
        L_0x01ad:
            long r4 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r4, (java.lang.Object) r6)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x01c2:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            int r0 = r7.readUInt32()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x01dc:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.ByteString r0 = r7.readBytes()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x01f1:
            java.lang.Object r0 = r1.mutableOneofMessageFieldForMerge(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.MessageLite r0 = (com.google.protobuf.MessageLite) r0     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.Schema r3 = r1.getMessageFieldSchema(r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r7.mergeMessageField(r0, r3, r14)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.storeOneofMessageField(r2, r9, r10, r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x020a:
            r1.readString(r2, r15, r7)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0217:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            boolean r0 = r7.readBool()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0231:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            int r0 = r7.readFixed32()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x024b:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            long r5 = r7.readFixed64()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Long r0 = java.lang.Long.valueOf(r5)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0265:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            int r0 = r7.readInt32()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x027f:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            long r5 = r7.readUInt64()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Long r0 = java.lang.Long.valueOf(r5)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x0299:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            long r5 = r7.readInt64()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Long r0 = java.lang.Long.valueOf(r5)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x02b3:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            float r0 = r7.readFloat()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Float r0 = java.lang.Float.valueOf(r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x02cd:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            double r5 = r7.readDouble()     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            java.lang.Double r0 = java.lang.Double.valueOf(r5)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r1.setOneofPresent(r2, r9, r10)     // Catch:{ InvalidWireTypeException -> 0x02ed, all -> 0x02e7 }
            r16 = r14
            r14 = r7
            r7 = r16
            goto L_0x084c
        L_0x02e7:
            r0 = move-exception
        L_0x02e8:
            r5 = r14
            r14 = r7
            r7 = r5
            goto L_0x08e8
        L_0x02ed:
            r0 = move-exception
            r5 = r14
            r14 = r7
            r7 = r5
            goto L_0x0889
        L_0x02f3:
            java.lang.Object r4 = r1.getMapFieldDefaultEntry(r10)     // Catch:{ InvalidWireTypeException -> 0x0318, all -> 0x0311 }
            r6 = r7
            r3 = r10
            r5 = r14
            r1.mergeMap(r2, r3, r4, r5, r6)     // Catch:{ InvalidWireTypeException -> 0x0305, all -> 0x0311 }
            r2 = r20
            r14 = r21
            r7 = r22
            goto L_0x084c
        L_0x0305:
            r0 = move-exception
            r10 = r3
            r2 = r20
            r14 = r21
            r7 = r22
            r6 = r12
            r5 = r13
            goto L_0x088b
        L_0x0311:
            r0 = move-exception
            r2 = r20
            r14 = r21
            goto L_0x060f
        L_0x0318:
            r0 = move-exception
            r2 = r20
            r14 = r21
            goto L_0x0615
        L_0x031f:
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0356, all -> 0x034a }
            com.google.protobuf.Schema r6 = r1.getMessageFieldSchema(r10)     // Catch:{ InvalidWireTypeException -> 0x0356, all -> 0x034a }
            r2 = r20
            r5 = r21
            r7 = r22
            r1.readGroupList(r2, r3, r5, r6, r7)     // Catch:{ InvalidWireTypeException -> 0x0341, all -> 0x0338 }
            r7 = r1
            r14 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x0338:
            r0 = move-exception
            r7 = r1
            r14 = r5
            r6 = r12
            r5 = r13
            r7 = r22
            goto L_0x08f3
        L_0x0341:
            r0 = move-exception
            r7 = r1
            r14 = r5
            r6 = r12
            r5 = r13
            r7 = r22
            goto L_0x088b
        L_0x034a:
            r0 = move-exception
            r2 = r20
            r14 = r21
            r7 = r1
            r6 = r12
            r5 = r13
            r7 = r22
            goto L_0x08f3
        L_0x0356:
            r0 = move-exception
            r2 = r20
            r14 = r21
            r7 = r1
            r6 = r12
            r5 = r13
            r7 = r22
            goto L_0x088b
        L_0x0362:
            r14 = r7
            r7 = r1
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r14.readSInt64List(r0)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x0376:
            r14 = r7
            r7 = r1
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r14.readSInt32List(r0)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x038a:
            r14 = r7
            r7 = r1
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r14.readSFixed64List(r0)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x039e:
            r14 = r7
            r7 = r1
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r14.readSFixed32List(r0)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x03b2:
            r14 = r7
            r7 = r1
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x03fc, all -> 0x03f3 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x03fc, all -> 0x03f3 }
            java.util.List r3 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x03fc, all -> 0x03f3 }
            r14.readEnumList(r3)     // Catch:{ InvalidWireTypeException -> 0x03fc, all -> 0x03f3 }
            com.google.protobuf.Internal$EnumVerifier r4 = r7.getEnumFieldVerifier(r10)     // Catch:{ InvalidWireTypeException -> 0x03fc, all -> 0x03f3 }
            r1 = r2
            r2 = r9
            r6 = r12
            r5 = r13
            java.lang.Object r0 = com.google.protobuf.SchemaUtil.filterUnknownEnumList((java.lang.Object) r1, (int) r2, (java.util.List<java.lang.Integer>) r3, (com.google.protobuf.Internal.EnumVerifier) r4, r5, r6)     // Catch:{ InvalidWireTypeException -> 0x03e5, all -> 0x03dc }
            r16 = r2
            r2 = r1
            r1 = r16
            r5 = r0
            r6 = r18
            r9 = r1
            r1 = r7
            r7 = r22
            goto L_0x087c
        L_0x03dc:
            r0 = move-exception
            r2 = r1
            r6 = r18
            r1 = r7
            r7 = r22
            goto L_0x08f3
        L_0x03e5:
            r0 = move-exception
            r16 = r2
            r2 = r1
            r1 = r16
            r6 = r18
            r9 = r1
            r1 = r7
            r7 = r22
            goto L_0x088b
        L_0x03f3:
            r0 = move-exception
            r5 = r13
            r6 = r18
            r1 = r7
            r7 = r22
            goto L_0x08f3
        L_0x03fc:
            r0 = move-exception
            r1 = r9
            r5 = r13
            r6 = r18
            r1 = r7
            r7 = r22
            goto L_0x088b
        L_0x0406:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readUInt32List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x0420:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readBoolList(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x043a:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readFixed32List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x0454:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readFixed64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x046e:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readInt32List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x0488:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readUInt64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x04a2:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readInt64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x04bc:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readFloatList(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x04d6:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readDoubleList(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x04f0:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readSInt64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x050a:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readSInt32List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x0524:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readSFixed64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x053e:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r14.readSFixed32List(r0)     // Catch:{ InvalidWireTypeException -> 0x0560, all -> 0x0558 }
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x0558:
            r0 = move-exception
            r6 = r18
            r1 = r7
            r7 = r22
            goto L_0x08f3
        L_0x0560:
            r0 = move-exception
            r6 = r18
            r9 = r1
            r1 = r7
            r7 = r22
            goto L_0x088b
        L_0x0569:
            r14 = r7
            r5 = r13
            r7 = r1
            r1 = r9
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x05ad, all -> 0x05a3 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x05ad, all -> 0x05a3 }
            java.util.List r3 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x05ad, all -> 0x05a3 }
            r14.readEnumList(r3)     // Catch:{ InvalidWireTypeException -> 0x05ad, all -> 0x05a3 }
            com.google.protobuf.Internal$EnumVerifier r4 = r7.getEnumFieldVerifier(r10)     // Catch:{ InvalidWireTypeException -> 0x05ad, all -> 0x05a3 }
            r6 = r2
            r2 = r1
            r1 = r6
            r6 = r18
            java.lang.Object r0 = com.google.protobuf.SchemaUtil.filterUnknownEnumList((java.lang.Object) r1, (int) r2, (java.util.List<java.lang.Integer>) r3, (com.google.protobuf.Internal.EnumVerifier) r4, r5, r6)     // Catch:{ InvalidWireTypeException -> 0x0599, all -> 0x0593 }
            r9 = r2
            r13 = r5
            r12 = r6
            r2 = r1
            r5 = r0
            r1 = r7
            r6 = r12
            r7 = r22
            goto L_0x087c
        L_0x0593:
            r0 = move-exception
            r2 = r1
            r13 = r5
            r12 = r6
            r1 = r7
            goto L_0x05a9
        L_0x0599:
            r0 = move-exception
            r9 = r2
            r13 = r5
            r12 = r6
            r2 = r1
            r1 = r7
            r7 = r22
            goto L_0x088b
        L_0x05a3:
            r0 = move-exception
            r12 = r18
            r13 = r5
            r1 = r7
            r6 = r12
        L_0x05a9:
            r7 = r22
            goto L_0x08f3
        L_0x05ad:
            r0 = move-exception
            r12 = r18
            r9 = r1
            r13 = r5
            r1 = r7
            r6 = r12
            r7 = r22
            goto L_0x088b
        L_0x05b8:
            r14 = r7
            r7 = r1
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r14.readUInt32List(r0)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x05cc:
            r14 = r7
            r7 = r1
            com.google.protobuf.ListFieldSchema r0 = r7.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r14.readBytesList(r0)     // Catch:{ InvalidWireTypeException -> 0x05e8, all -> 0x05e0 }
            r1 = r7
            r7 = r22
            goto L_0x084c
        L_0x05e0:
            r0 = move-exception
            r1 = r7
            r6 = r12
            r5 = r13
            r7 = r22
            goto L_0x08f3
        L_0x05e8:
            r0 = move-exception
            r1 = r7
            r6 = r12
            r5 = r13
            r7 = r22
            goto L_0x088b
        L_0x05f0:
            r14 = r7
            r7 = r1
            com.google.protobuf.Schema r5 = r7.getMessageFieldSchema(r10)     // Catch:{ InvalidWireTypeException -> 0x0613, all -> 0x060d }
            r6 = r22
            r1 = r7
            r4 = r14
            r3 = r15
            r1.readMessageList(r2, r3, r4, r5, r6)     // Catch:{ InvalidWireTypeException -> 0x0605, all -> 0x0601 }
            r7 = r6
            goto L_0x084c
        L_0x0601:
            r0 = move-exception
            r14 = r4
            goto L_0x08e7
        L_0x0605:
            r0 = move-exception
            r15 = r3
            r14 = r4
            r7 = r6
            r6 = r12
            r5 = r13
            goto L_0x088b
        L_0x060d:
            r0 = move-exception
            r1 = r7
        L_0x060f:
            r7 = r22
            goto L_0x08e8
        L_0x0613:
            r0 = move-exception
            r1 = r7
        L_0x0615:
            r7 = r22
            goto L_0x0889
        L_0x0619:
            r16 = r14
            r14 = r7
            r7 = r16
            r1.readStringList(r2, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0623:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readBoolList(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0637:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readFixed32List(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x064b:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readFixed64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x065f:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readInt32List(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0673:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readUInt64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0687:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readInt64List(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x069b:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readFloatList(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x06af:
            r16 = r14
            r14 = r7
            r7 = r16
            com.google.protobuf.ListFieldSchema r0 = r1.listFieldSchema     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            java.util.List r0 = r0.mutableListAt(r2, r3)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.readDoubleList(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x06c3:
            r16 = r14
            r14 = r7
            r7 = r16
            java.lang.Object r0 = r1.mutableMessageFieldForMerge(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.MessageLite r0 = (com.google.protobuf.MessageLite) r0     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.Schema r3 = r1.getMessageFieldSchema(r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.mergeGroupField(r0, r3, r7)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.storeMessageField(r2, r10, r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x06db:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r5 = r14.readSInt64()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putLong((java.lang.Object) r2, (long) r3, (long) r5)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x06f0:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            int r0 = r14.readSInt32()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putInt((java.lang.Object) r2, (long) r3, (int) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0705:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r5 = r14.readSFixed64()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putLong((java.lang.Object) r2, (long) r3, (long) r5)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x071a:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            int r0 = r14.readSFixed32()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putInt((java.lang.Object) r2, (long) r3, (int) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x072f:
            r16 = r14
            r14 = r7
            r7 = r16
            int r0 = r14.readEnum()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.Internal$EnumVerifier r3 = r1.getEnumFieldVerifier(r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            if (r3 == 0) goto L_0x074e
            boolean r4 = r3.isInRange(r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            if (r4 == 0) goto L_0x0745
            goto L_0x074e
        L_0x0745:
            java.lang.Object r4 = com.google.protobuf.SchemaUtil.storeUnknownEnum(r2, r9, r0, r13, r12)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r5 = r4
            r6 = r12
            goto L_0x087c
        L_0x074e:
            long r4 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putInt((java.lang.Object) r2, (long) r4, (int) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x075a:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            int r0 = r14.readUInt32()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putInt((java.lang.Object) r2, (long) r3, (int) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x076f:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.ByteString r0 = r14.readBytes()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putObject((java.lang.Object) r2, (long) r3, (java.lang.Object) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0784:
            r16 = r14
            r14 = r7
            r7 = r16
            java.lang.Object r0 = r1.mutableMessageFieldForMerge(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.MessageLite r0 = (com.google.protobuf.MessageLite) r0     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.Schema r3 = r1.getMessageFieldSchema(r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r14.mergeMessageField(r0, r3, r7)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.storeMessageField(r2, r10, r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x079c:
            r16 = r14
            r14 = r7
            r7 = r16
            r1.readString(r2, r15, r14)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x07a9:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            boolean r0 = r14.readBool()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putBoolean((java.lang.Object) r2, (long) r3, (boolean) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x07be:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            int r0 = r14.readFixed32()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putInt((java.lang.Object) r2, (long) r3, (int) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x07d3:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r5 = r14.readFixed64()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putLong((java.lang.Object) r2, (long) r3, (long) r5)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x07e8:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            int r0 = r14.readInt32()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putInt((java.lang.Object) r2, (long) r3, (int) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x07fc:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r5 = r14.readUInt64()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putLong((java.lang.Object) r2, (long) r3, (long) r5)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0810:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            long r5 = r14.readInt64()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putLong((java.lang.Object) r2, (long) r3, (long) r5)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0824:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            float r0 = r14.readFloat()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putFloat((java.lang.Object) r2, (long) r3, (float) r0)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            goto L_0x084c
        L_0x0838:
            r16 = r14
            r14 = r7
            r7 = r16
            long r3 = offset(r15)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            double r5 = r14.readDouble()     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            com.google.protobuf.UnsafeUtil.putDouble((java.lang.Object) r2, (long) r3, (double) r5)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
            r1.setFieldPresent(r2, r10)     // Catch:{ InvalidWireTypeException -> 0x0852, all -> 0x084f }
        L_0x084c:
            r6 = r12
            r5 = r13
            goto L_0x087c
        L_0x084f:
            r0 = move-exception
            goto L_0x08e8
        L_0x0852:
            r0 = move-exception
            goto L_0x0889
        L_0x0854:
            r5 = r0
            goto L_0x0857
        L_0x0856:
            r5 = r13
        L_0x0857:
            boolean r0 = r12.mergeOneFieldFrom(r5, r14, r11)     // Catch:{ InvalidWireTypeException -> 0x0882, all -> 0x087e }
            if (r0 != 0) goto L_0x087b
            int r0 = r1.checkInitializedCount
            r4 = r5
        L_0x0860:
            int r3 = r1.repeatedFieldOffsetStart
            if (r0 >= r3) goto L_0x0874
            int[] r3 = r1.intArray
            r3 = r3[r0]
            r6 = r20
            r5 = r12
            java.lang.Object r4 = r1.filterMapUnknownEnumValues(r2, r3, r4, r5, r6)
            r6 = r5
            int r0 = r0 + 1
            r12 = r6
            goto L_0x0860
        L_0x0874:
            r6 = r12
            if (r4 == 0) goto L_0x087a
            r6.setBuilderToMessage(r2, r4)
        L_0x087a:
            return
        L_0x087b:
            r6 = r12
        L_0x087c:
            goto L_0x08e0
        L_0x087e:
            r0 = move-exception
            r6 = r12
            goto L_0x08f3
        L_0x0882:
            r0 = move-exception
            r6 = r12
            goto L_0x088b
        L_0x0885:
            r0 = move-exception
            r6 = r14
            r14 = r7
            r7 = r6
        L_0x0889:
            r6 = r12
            r5 = r13
        L_0x088b:
            boolean r3 = r6.shouldDiscardUnknownFields(r14)     // Catch:{ all -> 0x08e2 }
            if (r3 == 0) goto L_0x08b5
            boolean r3 = r14.skipField()     // Catch:{ all -> 0x08e2 }
            if (r3 != 0) goto L_0x08e0
            int r3 = r1.checkInitializedCount
            r11 = r3
            r4 = r5
        L_0x089b:
            int r3 = r1.repeatedFieldOffsetStart
            if (r11 >= r3) goto L_0x08af
            int[] r3 = r1.intArray
            r3 = r3[r11]
            r6 = r20
            r5 = r18
            java.lang.Object r4 = r1.filterMapUnknownEnumValues(r2, r3, r4, r5, r6)
            r6 = r5
            int r11 = r11 + 1
            goto L_0x089b
        L_0x08af:
            if (r4 == 0) goto L_0x08b4
            r6.setBuilderToMessage(r2, r4)
        L_0x08b4:
            return
        L_0x08b5:
            if (r5 != 0) goto L_0x08bc
            java.lang.Object r3 = r6.getBuilderFromMessage(r2)     // Catch:{ all -> 0x08e2 }
            r5 = r3
        L_0x08bc:
            boolean r3 = r6.mergeOneFieldFrom(r5, r14, r11)     // Catch:{ all -> 0x08e2 }
            if (r3 != 0) goto L_0x08e0
            int r3 = r1.checkInitializedCount
            r11 = r3
            r4 = r5
        L_0x08c6:
            int r3 = r1.repeatedFieldOffsetStart
            if (r11 >= r3) goto L_0x08da
            int[] r3 = r1.intArray
            r3 = r3[r11]
            r6 = r20
            r5 = r18
            java.lang.Object r4 = r1.filterMapUnknownEnumValues(r2, r3, r4, r5, r6)
            r6 = r5
            int r11 = r11 + 1
            goto L_0x08c6
        L_0x08da:
            if (r4 == 0) goto L_0x08df
            r6.setBuilderToMessage(r2, r4)
        L_0x08df:
            return
        L_0x08e0:
            goto L_0x0008
        L_0x08e2:
            r0 = move-exception
            goto L_0x08f3
        L_0x08e4:
            r0 = move-exception
            r6 = r14
            r14 = r7
        L_0x08e7:
            r7 = r6
        L_0x08e8:
            r6 = r12
        L_0x08e9:
            r5 = r13
            goto L_0x08f3
        L_0x08eb:
            r0 = move-exception
            r6 = r18
            r2 = r20
            r14 = r21
            r13 = r5
        L_0x08f3:
            int r3 = r1.checkInitializedCount
            r9 = r3
            r4 = r5
        L_0x08f7:
            int r3 = r1.repeatedFieldOffsetStart
            if (r9 >= r3) goto L_0x090d
            int[] r3 = r1.intArray
            r3 = r3[r9]
            r6 = r20
            r5 = r18
            java.lang.Object r4 = r1.filterMapUnknownEnumValues(r2, r3, r4, r5, r6)
            int r9 = r9 + 1
            r1 = r17
            r6 = r5
            goto L_0x08f7
        L_0x090d:
            r5 = r6
            if (r4 == 0) goto L_0x0913
            r5.setBuilderToMessage(r2, r4)
        L_0x0913:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.mergeFromHelper(com.google.protobuf.UnknownFieldSchema, com.google.protobuf.ExtensionSchema, java.lang.Object, com.google.protobuf.Reader, com.google.protobuf.ExtensionRegistryLite):void");
    }

    static UnknownFieldSetLite getMutableUnknownFields(Object message) {
        UnknownFieldSetLite unknownFields = ((GeneratedMessageLite) message).unknownFields;
        if (unknownFields != UnknownFieldSetLite.getDefaultInstance()) {
            return unknownFields;
        }
        UnknownFieldSetLite unknownFields2 = UnknownFieldSetLite.newInstance();
        ((GeneratedMessageLite) message).unknownFields = unknownFields2;
        return unknownFields2;
    }

    private int decodeMapEntryValue(byte[] data, int position, int limit, WireFormat.FieldType fieldType, Class<?> messageType, ArrayDecoders.Registers registers) throws IOException {
        switch (fieldType) {
            case BOOL:
                int position2 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return position2;
            case BYTES:
                return ArrayDecoders.decodeBytes(data, position, registers);
            case DOUBLE:
                registers.object1 = Double.valueOf(ArrayDecoders.decodeDouble(data, position));
                return position + 8;
            case FIXED32:
            case SFIXED32:
                registers.object1 = Integer.valueOf(ArrayDecoders.decodeFixed32(data, position));
                return position + 4;
            case FIXED64:
            case SFIXED64:
                registers.object1 = Long.valueOf(ArrayDecoders.decodeFixed64(data, position));
                return position + 8;
            case FLOAT:
                registers.object1 = Float.valueOf(ArrayDecoders.decodeFloat(data, position));
                return position + 4;
            case ENUM:
            case INT32:
            case UINT32:
                int position3 = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return position3;
            case INT64:
            case UINT64:
                int position4 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return position4;
            case MESSAGE:
                return ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor(messageType), data, position, limit, registers);
            case SINT32:
                int position5 = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return position5;
            case SINT64:
                int position6 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return position6;
            case STRING:
                return ArrayDecoders.decodeStringRequireUtf8(data, position, registers);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: byte} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <K, V> int decodeMapEntry(byte[] r16, int r17, int r18, com.google.protobuf.MapEntryLite.Metadata<K, V> r19, java.util.Map<K, V> r20, com.google.protobuf.ArrayDecoders.Registers r21) throws java.io.IOException {
        /*
            r15 = this;
            r1 = r16
            r7 = r19
            r6 = r21
            r0 = r17
            int r0 = com.google.protobuf.ArrayDecoders.decodeVarint32(r1, r0, r6)
            int r8 = r6.int1
            if (r8 < 0) goto L_0x008b
            int r2 = r18 - r0
            if (r8 > r2) goto L_0x008b
            int r9 = r0 + r8
            K r2 = r7.defaultKey
            V r3 = r7.defaultValue
            r10 = r2
            r11 = r3
        L_0x001c:
            if (r0 >= r9) goto L_0x007a
            int r2 = r0 + 1
            byte r0 = r1[r0]
            if (r0 >= 0) goto L_0x002c
            int r2 = com.google.protobuf.ArrayDecoders.decodeVarint32(r0, r1, r2, r6)
            int r0 = r6.int1
            r12 = r0
            goto L_0x002d
        L_0x002c:
            r12 = r0
        L_0x002d:
            int r13 = r12 >>> 3
            r14 = r12 & 7
            switch(r13) {
                case 1: goto L_0x0059;
                case 2: goto L_0x0037;
                default: goto L_0x0034;
            }
        L_0x0034:
            r3 = r18
            goto L_0x0075
        L_0x0037:
            com.google.protobuf.WireFormat$FieldType r0 = r7.valueType
            int r0 = r0.getWireType()
            if (r14 != r0) goto L_0x0054
            com.google.protobuf.WireFormat$FieldType r4 = r7.valueType
            V r0 = r7.defaultValue
            java.lang.Class r5 = r0.getClass()
            r0 = r15
            r3 = r18
            int r2 = r0.decodeMapEntryValue(r1, r2, r3, r4, r5, r6)
            java.lang.Object r11 = r6.object1
            r1 = r16
            r0 = r2
            goto L_0x001c
        L_0x0054:
            r1 = r16
            r3 = r18
            goto L_0x0075
        L_0x0059:
            com.google.protobuf.WireFormat$FieldType r0 = r7.keyType
            int r0 = r0.getWireType()
            if (r14 != r0) goto L_0x0071
            com.google.protobuf.WireFormat$FieldType r4 = r7.keyType
            r5 = 0
            r0 = r15
            r1 = r16
            r3 = r18
            int r2 = r0.decodeMapEntryValue(r1, r2, r3, r4, r5, r6)
            java.lang.Object r10 = r6.object1
            r0 = r2
            goto L_0x001c
        L_0x0071:
            r1 = r16
            r3 = r18
        L_0x0075:
            int r0 = com.google.protobuf.ArrayDecoders.skipField(r12, r1, r2, r3, r6)
            goto L_0x001c
        L_0x007a:
            r3 = r18
            if (r0 != r9) goto L_0x0084
            r2 = r20
            r2.put(r10, r11)
            return r9
        L_0x0084:
            r2 = r20
            com.google.protobuf.InvalidProtocolBufferException r4 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r4
        L_0x008b:
            r3 = r18
            r2 = r20
            com.google.protobuf.InvalidProtocolBufferException r4 = com.google.protobuf.InvalidProtocolBufferException.truncatedMessage()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.decodeMapEntry(byte[], int, int, com.google.protobuf.MapEntryLite$Metadata, java.util.Map, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    private int parseRepeatedField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int bufferPosition, long typeAndOffset, int fieldType, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
        Internal.ProtobufList<?> list;
        int position2;
        int i = wireType;
        int i2 = bufferPosition;
        long j = fieldOffset;
        Internal.ProtobufList<?> list2 = (Internal.ProtobufList) UNSAFE.getObject(message, j);
        if (!list2.isModifiable()) {
            int size = list2.size();
            Internal.ProtobufList<?> list3 = list2.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
            UNSAFE.putObject(message, j, list3);
            list = list3;
        } else {
            list = list2;
        }
        switch (fieldType) {
            case 18:
            case 35:
                byte[] bArr = data;
                int i3 = position;
                ArrayDecoders.Registers registers2 = registers;
                if (i == 2) {
                    return ArrayDecoders.decodePackedDoubleList(data, i3, list, registers2);
                }
                if (i == 1) {
                    return ArrayDecoders.decodeDoubleList(tag, bArr, i3, limit, list, registers2);
                }
                break;
            case 19:
            case 36:
                byte[] bArr2 = data;
                int i4 = position;
                ArrayDecoders.Registers registers3 = registers;
                if (i != 2) {
                    if (i != 5) {
                        byte[] bArr3 = bArr2;
                        int i5 = i4;
                        break;
                    } else {
                        return ArrayDecoders.decodeFloatList(tag, bArr2, i4, limit, list, registers3);
                    }
                } else {
                    return ArrayDecoders.decodePackedFloatList(data, i4, list, registers3);
                }
            case 20:
            case 21:
            case 37:
            case 38:
                byte[] bArr4 = data;
                int i6 = position;
                ArrayDecoders.Registers registers4 = registers;
                if (i != 2) {
                    if (i != 0) {
                        byte[] bArr5 = bArr4;
                        break;
                    } else {
                        byte[] bArr6 = bArr4;
                        return ArrayDecoders.decodeVarint64List(tag, bArr4, i6, limit, list, registers4);
                    }
                } else {
                    return ArrayDecoders.decodePackedVarint64List(data, i6, list, registers4);
                }
            case 22:
            case 29:
            case 39:
            case 43:
                byte[] bArr7 = data;
                int i7 = position;
                ArrayDecoders.Registers registers5 = registers;
                if (i == 2) {
                    return ArrayDecoders.decodePackedVarint32List(data, i7, list, registers5);
                }
                if (i == 0) {
                    return ArrayDecoders.decodeVarint32List(tag, bArr7, i7, limit, list, registers5);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                byte[] bArr8 = data;
                int i8 = position;
                ArrayDecoders.Registers registers6 = registers;
                if (i != 2) {
                    if (i != 1) {
                        int position3 = i8;
                        break;
                    } else {
                        return ArrayDecoders.decodeFixed64List(tag, bArr8, i8, limit, list, registers6);
                    }
                } else {
                    return ArrayDecoders.decodePackedFixed64List(data, i8, list, registers6);
                }
            case 24:
            case 31:
            case 41:
            case 45:
                byte[] bArr9 = data;
                int i9 = position;
                ArrayDecoders.Registers registers7 = registers;
                if (i != 2) {
                    if (i != 5) {
                        byte[] bArr10 = bArr9;
                        int i10 = i9;
                        break;
                    } else {
                        return ArrayDecoders.decodeFixed32List(tag, bArr9, i9, limit, list, registers7);
                    }
                } else {
                    return ArrayDecoders.decodePackedFixed32List(data, i9, list, registers7);
                }
            case 25:
            case 42:
                byte[] bArr11 = data;
                int i11 = position;
                ArrayDecoders.Registers registers8 = registers;
                if (i != 2) {
                    if (i != 0) {
                        byte[] bArr12 = bArr11;
                        break;
                    } else {
                        byte[] bArr13 = bArr11;
                        return ArrayDecoders.decodeBoolList(tag, bArr11, i11, limit, list, registers8);
                    }
                } else {
                    return ArrayDecoders.decodePackedBoolList(data, i11, list, registers8);
                }
            case 26:
                if (i != 2) {
                    byte[] bArr14 = data;
                    int i12 = position;
                    ArrayDecoders.Registers registers9 = registers;
                    break;
                } else if ((typeAndOffset & 536870912) == 0) {
                    return ArrayDecoders.decodeStringList(tag, data, position, limit, list, registers);
                } else {
                    return ArrayDecoders.decodeStringListRequireUtf8(tag, data, position, limit, list, registers);
                }
            case 27:
                if (i == 2) {
                    Internal.ProtobufList<?> list4 = list;
                    Internal.ProtobufList<?> protobufList = list4;
                    return ArrayDecoders.decodeMessageList(getMessageFieldSchema(i2), tag, data, position, limit, list4, registers);
                }
                break;
            case 28:
                if (i == 2) {
                    return ArrayDecoders.decodeBytesList(tag, data, position, limit, list, registers);
                }
                break;
            case 30:
            case 44:
                byte[] bArr15 = data;
                int i13 = position;
                ArrayDecoders.Registers registers10 = registers;
                if (i == 2) {
                    position2 = ArrayDecoders.decodePackedVarint32List(data, i13, list, registers10);
                } else if (i == 0) {
                    ArrayDecoders.Registers registers11 = registers10;
                    Internal.ProtobufList<?> list5 = list;
                    list = list5;
                    position2 = ArrayDecoders.decodeVarint32List(tag, bArr15, i13, limit, list5, registers11);
                }
                Internal.ProtobufList<?> list6 = list;
                SchemaUtil.filterUnknownEnumList((Object) message, number, (List<Integer>) list6, getEnumFieldVerifier(i2), null, this.unknownFieldSchema);
                Internal.ProtobufList<?> protobufList2 = list6;
                return position2;
            case 33:
            case 47:
                byte[] bArr16 = data;
                int i14 = position;
                ArrayDecoders.Registers registers12 = registers;
                if (i == 2) {
                    return ArrayDecoders.decodePackedSInt32List(data, i14, list, registers12);
                }
                if (i == 0) {
                    int i15 = i14;
                    ArrayDecoders.Registers registers13 = registers12;
                    byte[] bArr17 = bArr16;
                    Internal.ProtobufList<?> list7 = list;
                    int position4 = ArrayDecoders.decodeSInt32List(tag, bArr17, i15, limit, list7, registers13);
                    byte[] bArr18 = bArr17;
                    Internal.ProtobufList<?> protobufList3 = list7;
                    ArrayDecoders.Registers registers14 = registers13;
                    return position4;
                }
                break;
            case 34:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE:
                byte[] bArr19 = data;
                int i16 = position;
                ArrayDecoders.Registers registers15 = registers;
                if (i != 2) {
                    if (i != 0) {
                        byte[] bArr20 = bArr19;
                        int i17 = i16;
                        byte[] bArr21 = bArr20;
                        break;
                    } else {
                        ArrayDecoders.Registers registers16 = registers15;
                        Internal.ProtobufList<?> list8 = list;
                        int position5 = ArrayDecoders.decodeSInt64List(tag, bArr19, i16, limit, list8, registers16);
                        byte[] bArr22 = bArr19;
                        Internal.ProtobufList<?> protobufList4 = list8;
                        ArrayDecoders.Registers registers17 = registers16;
                        return position5;
                    }
                } else {
                    return ArrayDecoders.decodePackedSInt64List(data, i16, list, registers15);
                }
            case 49:
                if (i != 3) {
                    byte[] bArr23 = data;
                    int i18 = position;
                    ArrayDecoders.Registers registers18 = registers;
                    break;
                } else {
                    byte[] bArr24 = data;
                    ArrayDecoders.Registers registers19 = registers;
                    Internal.ProtobufList<?> list9 = list;
                    byte[] bArr25 = bArr24;
                    ArrayDecoders.Registers registers20 = registers19;
                    Internal.ProtobufList<?> protobufList5 = list9;
                    return ArrayDecoders.decodeGroupList(getMessageFieldSchema(i2), tag, bArr24, position, limit, list9, registers19);
                }
        }
        return position;
    }

    private <K, V> int parseMapField(T message, byte[] data, int position, int limit, int bufferPosition, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
        Object mapField;
        long j = fieldOffset;
        Unsafe unsafe = UNSAFE;
        Object mapDefaultEntry = getMapFieldDefaultEntry(bufferPosition);
        Object mapField2 = unsafe.getObject(message, j);
        if (this.mapFieldSchema.isImmutable(mapField2)) {
            Object oldMapField = mapField2;
            Object mapField3 = this.mapFieldSchema.newMapField(mapDefaultEntry);
            this.mapFieldSchema.mergeFrom(mapField3, oldMapField);
            unsafe.putObject(message, j, mapField3);
            mapField = mapField3;
        } else {
            mapField = mapField2;
        }
        return decodeMapEntry(data, position, limit, this.mapFieldSchema.forMapMetadata(mapDefaultEntry), this.mapFieldSchema.forMutableMapData(mapField), registers);
    }

    private int parseOneofField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int typeAndOffset, int fieldType, long fieldOffset, int bufferPosition, ArrayDecoders.Registers registers) throws IOException {
        int position2;
        T t = message;
        int i = tag;
        int i2 = number;
        int i3 = wireType;
        long j = fieldOffset;
        int i4 = bufferPosition;
        Unsafe unsafe = UNSAFE;
        long oneofCaseOffset = (long) (this.buffer[i4 + 2] & 1048575);
        switch (fieldType) {
            case 51:
                ArrayDecoders.Registers registers2 = registers;
                long oneofCaseOffset2 = oneofCaseOffset;
                byte[] bArr = data;
                int position3 = position;
                if (i3 != 1) {
                    return position3;
                }
                unsafe.putObject(t, j, Double.valueOf(ArrayDecoders.decodeDouble(data, position)));
                int position4 = position3 + 8;
                unsafe.putInt(t, oneofCaseOffset2, i2);
                return position4;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF:
                ArrayDecoders.Registers registers3 = registers;
                long oneofCaseOffset3 = oneofCaseOffset;
                byte[] bArr2 = data;
                int position5 = position;
                if (i3 != 5) {
                    return position5;
                }
                unsafe.putObject(t, j, Float.valueOf(ArrayDecoders.decodeFloat(data, position)));
                int position6 = position5 + 4;
                unsafe.putInt(t, oneofCaseOffset3, i2);
                return position6;
            case 53:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE:
                ArrayDecoders.Registers registers4 = registers;
                long oneofCaseOffset4 = oneofCaseOffset;
                byte[] bArr3 = data;
                int position7 = position;
                if (i3 != 0) {
                    return position7;
                }
                int position8 = ArrayDecoders.decodeVarint64(bArr3, position7, registers4);
                unsafe.putObject(t, j, Long.valueOf(registers4.long1));
                unsafe.putInt(t, oneofCaseOffset4, i2);
                return position8;
            case 55:
            case 62:
                ArrayDecoders.Registers registers5 = registers;
                long oneofCaseOffset5 = oneofCaseOffset;
                byte[] bArr4 = data;
                int position9 = position;
                if (i3 != 0) {
                    return position9;
                }
                int position10 = ArrayDecoders.decodeVarint32(bArr4, position9, registers5);
                unsafe.putObject(t, j, Integer.valueOf(registers5.int1));
                unsafe.putInt(t, oneofCaseOffset5, i2);
                return position10;
            case 56:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HEIGHT:
                ArrayDecoders.Registers registers6 = registers;
                long oneofCaseOffset6 = oneofCaseOffset;
                byte[] bArr5 = data;
                int position11 = position;
                if (i3 != 1) {
                    return position11;
                }
                unsafe.putObject(t, j, Long.valueOf(ArrayDecoders.decodeFixed64(data, position)));
                int position12 = position11 + 8;
                unsafe.putInt(t, oneofCaseOffset6, i2);
                return position12;
            case 57:
            case 64:
                ArrayDecoders.Registers registers7 = registers;
                long oneofCaseOffset7 = oneofCaseOffset;
                byte[] bArr6 = data;
                int position13 = position;
                if (i3 != 5) {
                    return position13;
                }
                unsafe.putObject(t, j, Integer.valueOf(ArrayDecoders.decodeFixed32(data, position)));
                int position14 = position13 + 4;
                unsafe.putInt(t, oneofCaseOffset7, i2);
                return position14;
            case 58:
                ArrayDecoders.Registers registers8 = registers;
                long oneofCaseOffset8 = oneofCaseOffset;
                byte[] bArr7 = data;
                int position15 = position;
                if (i3 != 0) {
                    return position15;
                }
                int position16 = ArrayDecoders.decodeVarint64(bArr7, position15, registers8);
                unsafe.putObject(t, j, Boolean.valueOf(registers8.long1 != 0));
                unsafe.putInt(t, oneofCaseOffset8, i2);
                return position16;
            case 59:
                ArrayDecoders.Registers registers9 = registers;
                long oneofCaseOffset9 = oneofCaseOffset;
                byte[] bArr8 = data;
                int position17 = position;
                if (i3 != 2) {
                    return position17;
                }
                int position18 = ArrayDecoders.decodeVarint32(bArr8, position17, registers9);
                int length = registers9.int1;
                if (length == 0) {
                    unsafe.putObject(t, j, "");
                } else if ((typeAndOffset & ENFORCE_UTF8_MASK) == 0 || Utf8.isValidUtf8(bArr8, position18, position18 + length)) {
                    unsafe.putObject(t, j, new String(bArr8, position18, length, Internal.UTF_8));
                    position18 += length;
                } else {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                unsafe.putInt(t, oneofCaseOffset9, i2);
                return position18;
            case 60:
                byte[] bArr9 = data;
                int position19 = position;
                ArrayDecoders.Registers registers10 = registers;
                if (i3 == 2) {
                    long oneofCaseOffset10 = oneofCaseOffset;
                    Object current = mutableOneofMessageFieldForMerge(t, i2, i4);
                    long j2 = oneofCaseOffset10;
                    int position20 = ArrayDecoders.mergeMessageField(current, getMessageFieldSchema(i4), bArr9, position19, limit, registers10);
                    Object obj = bArr9;
                    Object current2 = current;
                    Object current3 = obj;
                    storeOneofMessageField(t, i2, i4, current2);
                    return position20;
                }
                byte[] bArr10 = bArr9;
                return position19;
            case LockFreeTaskQueueCore.CLOSED_SHIFT:
                byte[] bArr11 = data;
                int position21 = position;
                if (i3 == 2) {
                    ArrayDecoders.Registers registers11 = registers;
                    int position22 = ArrayDecoders.decodeBytes(bArr11, position21, registers11);
                    unsafe.putObject(t, j, registers11.object1);
                    unsafe.putInt(t, oneofCaseOffset, i2);
                    long j3 = oneofCaseOffset;
                    byte[] bArr12 = bArr11;
                    return position22;
                }
                ArrayDecoders.Registers registers12 = registers;
                long j4 = oneofCaseOffset;
                byte[] bArr13 = bArr11;
                return position21;
            case HtmlCompat.FROM_HTML_MODE_COMPACT:
                byte[] bArr14 = data;
                int position23 = position;
                ArrayDecoders.Registers registers13 = registers;
                if (i3 == 0) {
                    int position24 = ArrayDecoders.decodeVarint32(bArr14, position23, registers13);
                    int enumValue = registers13.int1;
                    Internal.EnumVerifier enumVerifier = getEnumFieldVerifier(i4);
                    if (enumVerifier == null) {
                        position2 = position24;
                        Internal.EnumVerifier enumVerifier2 = enumVerifier;
                    } else if (enumVerifier.isInRange(enumValue)) {
                        position2 = position24;
                        Internal.EnumVerifier enumVerifier3 = enumVerifier;
                    } else {
                        position2 = position24;
                        Internal.EnumVerifier enumVerifier4 = enumVerifier;
                        getMutableUnknownFields(t).storeField(i, Long.valueOf((long) enumValue));
                        ArrayDecoders.Registers registers14 = registers;
                        long j5 = oneofCaseOffset;
                        byte[] bArr15 = bArr14;
                        return position2;
                    }
                    unsafe.putObject(t, j, Integer.valueOf(enumValue));
                    unsafe.putInt(t, oneofCaseOffset, i2);
                    ArrayDecoders.Registers registers142 = registers;
                    long j52 = oneofCaseOffset;
                    byte[] bArr152 = bArr14;
                    return position2;
                }
                ArrayDecoders.Registers registers15 = registers;
                long j6 = oneofCaseOffset;
                byte[] bArr16 = bArr14;
                return position23;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT:
                byte[] bArr17 = data;
                int position25 = position;
                ArrayDecoders.Registers registers16 = registers;
                if (i3 == 0) {
                    int position26 = ArrayDecoders.decodeVarint32(bArr17, position25, registers16);
                    unsafe.putObject(t, j, Integer.valueOf(CodedInputStream.decodeZigZag32(registers16.int1)));
                    unsafe.putInt(t, oneofCaseOffset, i2);
                    long j7 = oneofCaseOffset;
                    byte[] bArr18 = bArr17;
                    return position26;
                }
                long j8 = oneofCaseOffset;
                byte[] bArr19 = bArr17;
                return position25;
            case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL:
                byte[] bArr20 = data;
                int position27 = position;
                ArrayDecoders.Registers registers17 = registers;
                long oneofCaseOffset11 = oneofCaseOffset;
                if (i3 == 0) {
                    int position28 = ArrayDecoders.decodeVarint64(bArr20, position27, registers17);
                    unsafe.putObject(t, j, Long.valueOf(CodedInputStream.decodeZigZag64(registers17.long1)));
                    long oneofCaseOffset12 = oneofCaseOffset11;
                    unsafe.putInt(t, oneofCaseOffset12, i2);
                    int position29 = position28;
                    byte[] bArr21 = bArr20;
                    long j9 = oneofCaseOffset12;
                    return position29;
                }
                long j10 = oneofCaseOffset11;
                byte[] bArr22 = bArr20;
                return position27;
            case 68:
                if (i3 == 3) {
                    long oneofCaseOffset13 = oneofCaseOffset;
                    Object current4 = mutableOneofMessageFieldForMerge(t, i2, i4);
                    int endTag = (i & -8) | 4;
                    ArrayDecoders.Registers registers18 = registers;
                    long oneofCaseOffset14 = oneofCaseOffset13;
                    byte[] bArr23 = data;
                    int position30 = ArrayDecoders.mergeGroupField(current4, getMessageFieldSchema(i4), bArr23, position, limit, endTag, registers18);
                    int i5 = endTag;
                    ArrayDecoders.Registers registers19 = registers18;
                    storeOneofMessageField(t, i2, i4, current4);
                    Object current5 = bArr23;
                    long j11 = oneofCaseOffset14;
                    return position30;
                }
                ArrayDecoders.Registers registers20 = registers;
                long oneofCaseOffset15 = oneofCaseOffset;
                byte[] bArr24 = data;
                long j12 = oneofCaseOffset15;
                return position;
            default:
                ArrayDecoders.Registers registers21 = registers;
                long j13 = oneofCaseOffset;
                byte[] bArr25 = data;
                return position;
        }
    }

    private Schema getMessageFieldSchema(int pos) {
        int index = (pos / 3) * 2;
        Schema schema = (Schema) this.objects[index];
        if (schema != null) {
            return schema;
        }
        Schema schema2 = Protobuf.getInstance().schemaFor((Class) this.objects[index + 1]);
        this.objects[index] = schema2;
        return schema2;
    }

    private Object getMapFieldDefaultEntry(int pos) {
        return this.objects[(pos / 3) * 2];
    }

    private Internal.EnumVerifier getEnumFieldVerifier(int pos) {
        return (Internal.EnumVerifier) this.objects[((pos / 3) * 2) + 1];
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: com.google.protobuf.UnknownFieldSetLite} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v17, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v22, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v26, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v40, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v41, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v45, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v46, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v48, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v12, resolved type: byte} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int parseMessage(T r32, byte[] r33, int r34, int r35, int r36, com.google.protobuf.ArrayDecoders.Registers r37) throws java.io.IOException {
        /*
            r31 = this;
            r0 = r31
            r1 = r32
            r2 = r33
            r4 = r35
            r7 = r37
            checkMutable(r1)
            sun.misc.Unsafe r9 = UNSAFE
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r5 = 0
            r6 = 0
            r8 = -1
            r10 = 0
            r11 = r10
            r10 = r5
            r5 = r8
            r8 = r3
            r3 = r34
        L_0x001c:
            if (r3 >= r4) goto L_0x0647
            int r13 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x0030
            int r13 = com.google.protobuf.ArrayDecoders.decodeVarint32(r3, r2, r13, r7)
            int r3 = r7.int1
            r28 = r13
            r13 = r3
            r3 = r28
            goto L_0x0035
        L_0x0030:
            r28 = r13
            r13 = r3
            r3 = r28
        L_0x0035:
            int r14 = r13 >>> 3
            r6 = r13 & 7
            if (r14 <= r5) goto L_0x0046
            r34 = 1048575(0xfffff, float:1.469367E-39)
            int r12 = r11 / 3
            int r11 = r0.positionForFieldNumber(r14, r12)
            r12 = r11
            goto L_0x004e
        L_0x0046:
            r34 = 1048575(0xfffff, float:1.469367E-39)
            int r11 = r0.positionForFieldNumber(r14)
            r12 = r11
        L_0x004e:
            r16 = r14
            r5 = -1
            if (r12 != r5) goto L_0x0060
            r5 = 0
            r2 = r3
            r12 = r5
            r11 = r6
            r25 = r9
            r24 = r10
            r5 = r13
            r10 = r14
            r9 = r0
            goto L_0x05ec
        L_0x0060:
            int[] r5 = r0.buffer
            int r11 = r12 + 1
            r11 = r5[r11]
            int r5 = type(r11)
            long r17 = offset(r11)
            r2 = 17
            r19 = r3
            if (r5 > r2) goto L_0x04a3
            int[] r2 = r0.buffer
            int r20 = r12 + 2
            r20 = r2[r20]
            int r2 = r20 >>> 20
            r3 = 1
            int r22 = r3 << r2
            r2 = r20 & r34
            r23 = 0
            if (r2 == r8) goto L_0x00a3
            r3 = r34
            if (r8 == r3) goto L_0x008d
            long r3 = (long) r8
            r9.putInt(r1, r3, r10)
        L_0x008d:
            r3 = r2
            r4 = 1048575(0xfffff, float:1.469367E-39)
            if (r2 != r4) goto L_0x0098
            r34 = r5
            r4 = r23
            goto L_0x009f
        L_0x0098:
            r34 = r5
            long r4 = (long) r2
            int r4 = r9.getInt(r1, r4)
        L_0x009f:
            r10 = r3
            r24 = r4
            goto L_0x00a8
        L_0x00a3:
            r34 = r5
            r24 = r10
            r10 = r8
        L_0x00a8:
            r3 = 5
            switch(r34) {
                case 0: goto L_0x0468;
                case 1: goto L_0x043a;
                case 2: goto L_0x0404;
                case 3: goto L_0x0404;
                case 4: goto L_0x03d5;
                case 5: goto L_0x0394;
                case 6: goto L_0x0354;
                case 7: goto L_0x030a;
                case 8: goto L_0x02bc;
                case 9: goto L_0x025f;
                case 10: goto L_0x0225;
                case 11: goto L_0x03d5;
                case 12: goto L_0x01ac;
                case 13: goto L_0x0354;
                case 14: goto L_0x0394;
                case 15: goto L_0x016f;
                case 16: goto L_0x0124;
                case 17: goto L_0x00c2;
                default: goto L_0x00ac;
            }
        L_0x00ac:
            r8 = r33
            r15 = r11
            r25 = r14
            r11 = r1
            r14 = r9
            r9 = r6
            r28 = r17
            r17 = r34
            r34 = r10
            r10 = r19
            r19 = r2
            r1 = r28
            goto L_0x0497
        L_0x00c2:
            r3 = 3
            if (r6 != r3) goto L_0x0104
            r3 = r2
            java.lang.Object r2 = r0.mutableMessageFieldForMerge(r1, r12)
            int r4 = r14 << 3
            r4 = r4 | 4
            r5 = r3
            com.google.protobuf.Schema r3 = r0.getMessageFieldSchema(r12)
            r8 = r10
            r10 = r34
            r34 = r8
            r8 = r19
            r19 = r5
            r5 = r8
            r8 = r7
            r25 = r9
            r7 = r4
            r9 = r6
            r4 = r33
            r6 = r35
            int r3 = com.google.protobuf.ArrayDecoders.mergeGroupField(r2, r3, r4, r5, r6, r7, r8)
            r28 = r7
            r7 = r4
            r4 = r28
            r0.storeMessageField(r1, r12, r2)
            r5 = r24 | r22
            r4 = r35
            r10 = r5
            r2 = r7
            r7 = r8
            r11 = r12
            r6 = r13
            r5 = r16
            r9 = r25
            r8 = r34
            goto L_0x001c
        L_0x0104:
            r3 = r10
            r10 = r34
            r34 = r3
            r8 = r7
            r25 = r9
            r3 = r19
            r7 = r33
            r19 = r2
            r9 = r6
            r2 = r8
            r8 = r7
            r7 = r2
            r2 = r25
            r25 = r14
            r14 = r2
            r15 = r11
            r11 = r1
            r1 = r17
            r17 = r10
            r10 = r3
            goto L_0x0497
        L_0x0124:
            r3 = r10
            r10 = r34
            r34 = r3
            r8 = r7
            r25 = r9
            r3 = r19
            r7 = r33
            r19 = r2
            r9 = r6
            if (r9 != 0) goto L_0x015d
            int r21 = com.google.protobuf.ArrayDecoders.decodeVarint64(r7, r3, r8)
            long r2 = r8.long1
            long r5 = com.google.protobuf.CodedInputStream.decodeZigZag64(r2)
            r2 = r1
            r3 = r17
            r1 = r25
            r1.putLong(r2, r3, r5)
            r5 = r2
            r2 = r1
            r1 = r5
            r4 = r3
            r3 = r24 | r22
            r4 = r35
            r9 = r2
            r10 = r3
            r2 = r7
            r7 = r8
            r11 = r12
            r6 = r13
            r5 = r16
            r3 = r21
            r8 = r34
            goto L_0x001c
        L_0x015d:
            r4 = r17
            r2 = r25
            r15 = r8
            r8 = r7
            r7 = r15
            r17 = r10
            r15 = r11
            r25 = r14
            r11 = r1
            r14 = r2
            r10 = r3
            r1 = r4
            goto L_0x0497
        L_0x016f:
            r3 = r10
            r10 = r34
            r34 = r3
            r8 = r7
            r4 = r17
            r3 = r19
            r7 = r33
            r19 = r2
            r2 = r9
            r9 = r6
            if (r9 != 0) goto L_0x019e
            int r3 = com.google.protobuf.ArrayDecoders.decodeVarint32(r7, r3, r8)
            int r6 = r8.int1
            int r6 = com.google.protobuf.CodedInputStream.decodeZigZag32(r6)
            r2.putInt(r1, r4, r6)
            r6 = r24 | r22
            r4 = r35
            r9 = r2
            r10 = r6
            r2 = r7
            r7 = r8
            r11 = r12
            r6 = r13
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x019e:
            r15 = r8
            r8 = r7
            r7 = r15
            r17 = r10
            r15 = r11
            r25 = r14
            r11 = r1
            r14 = r2
            r10 = r3
            r1 = r4
            goto L_0x0497
        L_0x01ac:
            r3 = r10
            r10 = r34
            r34 = r3
            r8 = r7
            r4 = r17
            r3 = r19
            r7 = r33
            r19 = r2
            r2 = r9
            r9 = r6
            if (r9 != 0) goto L_0x0217
            int r3 = com.google.protobuf.ArrayDecoders.decodeVarint32(r7, r3, r8)
            int r6 = r8.int1
            r17 = r3
            com.google.protobuf.Internal$EnumVerifier r3 = r0.getEnumFieldVerifier(r12)
            boolean r18 = isLegacyEnumIsClosed(r11)
            if (r18 == 0) goto L_0x01fe
            if (r3 == 0) goto L_0x01fe
            boolean r18 = r3.isInRange(r6)
            if (r18 == 0) goto L_0x01dd
            r18 = r3
            r25 = r14
            goto L_0x0202
        L_0x01dd:
            r18 = r3
            com.google.protobuf.UnknownFieldSetLite r3 = getMutableUnknownFields(r1)
            r25 = r14
            long r14 = (long) r6
            java.lang.Long r14 = java.lang.Long.valueOf(r14)
            r3.storeField(r13, r14)
            r4 = r35
            r9 = r2
            r2 = r7
            r7 = r8
            r11 = r12
            r6 = r13
            r5 = r16
            r3 = r17
            r10 = r24
            r8 = r34
            goto L_0x001c
        L_0x01fe:
            r18 = r3
            r25 = r14
        L_0x0202:
            r2.putInt(r1, r4, r6)
            r3 = r24 | r22
            r4 = r35
            r9 = r2
            r10 = r3
            r2 = r7
            r7 = r8
            r11 = r12
            r6 = r13
            r5 = r16
            r3 = r17
            r8 = r34
            goto L_0x001c
        L_0x0217:
            r25 = r14
            r14 = r8
            r8 = r7
            r7 = r14
            r14 = r2
            r17 = r10
            r15 = r11
            r11 = r1
            r10 = r3
            r1 = r4
            goto L_0x0497
        L_0x0225:
            r3 = r10
            r10 = r34
            r34 = r3
            r8 = r7
            r25 = r14
            r4 = r17
            r3 = r19
            r7 = r33
            r19 = r2
            r2 = r9
            r9 = r6
            r6 = 2
            if (r9 != r6) goto L_0x0253
            int r3 = com.google.protobuf.ArrayDecoders.decodeBytes(r7, r3, r8)
            java.lang.Object r6 = r8.object1
            r2.putObject(r1, r4, r6)
            r6 = r24 | r22
            r4 = r35
            r9 = r2
            r10 = r6
            r2 = r7
            r7 = r8
            r11 = r12
            r6 = r13
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x0253:
            r14 = r8
            r8 = r7
            r7 = r14
            r14 = r2
            r17 = r10
            r15 = r11
            r11 = r1
            r10 = r3
            r1 = r4
            goto L_0x0497
        L_0x025f:
            r3 = r10
            r10 = r34
            r34 = r3
            r8 = r7
            r25 = r14
            r4 = r17
            r3 = r19
            r7 = r33
            r19 = r2
            r2 = r9
            r9 = r6
            r6 = 2
            if (r9 != r6) goto L_0x02a6
            r6 = r1
            java.lang.Object r1 = r0.mutableMessageFieldForMerge(r6, r12)
            r14 = r2
            com.google.protobuf.Schema r2 = r0.getMessageFieldSchema(r12)
            r17 = r10
            r15 = r11
            r10 = r4
            r5 = r35
            r4 = r3
            r3 = r7
            r7 = r6
            r6 = r8
            int r2 = com.google.protobuf.ArrayDecoders.mergeMessageField(r1, r2, r3, r4, r5, r6)
            r8 = r3
            r3 = r1
            r1 = r6
            r0.storeMessageField(r7, r12, r3)
            r4 = r24 | r22
            r3 = r7
            r7 = r1
            r1 = r3
            r3 = r2
            r10 = r4
            r2 = r8
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            r4 = r35
            goto L_0x001c
        L_0x02a6:
            r14 = r7
            r7 = r1
            r1 = r8
            r8 = r14
            r14 = r2
            r2 = r3
            r17 = r10
            r15 = r11
            r10 = r4
            r28 = r7
            r7 = r1
            r29 = r10
            r10 = r2
            r11 = r28
            r1 = r29
            goto L_0x0497
        L_0x02bc:
            r8 = r7
            r7 = r1
            r1 = r8
            r8 = r19
            r19 = r2
            r2 = r8
            r8 = r33
            r15 = r11
            r25 = r14
            r14 = r9
            r9 = r6
            r28 = r17
            r17 = r34
            r34 = r10
            r10 = r28
            r6 = 2
            if (r9 != r6) goto L_0x02fe
            boolean r3 = isEnforceUtf8(r15)
            if (r3 == 0) goto L_0x02e2
            int r2 = com.google.protobuf.ArrayDecoders.decodeStringRequireUtf8(r8, r2, r1)
            r3 = r2
            goto L_0x02e7
        L_0x02e2:
            int r2 = com.google.protobuf.ArrayDecoders.decodeString(r8, r2, r1)
            r3 = r2
        L_0x02e7:
            java.lang.Object r2 = r1.object1
            r14.putObject(r7, r10, r2)
            r2 = r24 | r22
            r4 = r7
            r7 = r1
            r1 = r4
            r4 = r35
            r10 = r2
            r2 = r8
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x02fe:
            r28 = r7
            r7 = r1
            r29 = r10
            r10 = r2
            r11 = r28
            r1 = r29
            goto L_0x0497
        L_0x030a:
            r8 = r7
            r7 = r1
            r1 = r8
            r8 = r19
            r19 = r2
            r2 = r8
            r8 = r33
            r15 = r11
            r25 = r14
            r14 = r9
            r9 = r6
            r28 = r17
            r17 = r34
            r34 = r10
            r10 = r28
            if (r9 != 0) goto L_0x0348
            int r3 = com.google.protobuf.ArrayDecoders.decodeVarint64(r8, r2, r1)
            long r4 = r1.long1
            r26 = 0
            int r2 = (r4 > r26 ? 1 : (r4 == r26 ? 0 : -1))
            if (r2 == 0) goto L_0x0331
            r2 = 1
            goto L_0x0333
        L_0x0331:
            r2 = r23
        L_0x0333:
            com.google.protobuf.UnsafeUtil.putBoolean((java.lang.Object) r7, (long) r10, (boolean) r2)
            r2 = r24 | r22
            r4 = r7
            r7 = r1
            r1 = r4
            r4 = r35
            r10 = r2
            r2 = r8
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x0348:
            r28 = r7
            r7 = r1
            r29 = r10
            r10 = r2
            r11 = r28
            r1 = r29
            goto L_0x0497
        L_0x0354:
            r8 = r7
            r7 = r1
            r1 = r8
            r8 = r19
            r19 = r2
            r2 = r8
            r8 = r33
            r15 = r11
            r25 = r14
            r14 = r9
            r9 = r6
            r28 = r17
            r17 = r34
            r34 = r10
            r10 = r28
            if (r9 != r3) goto L_0x0388
            int r3 = com.google.protobuf.ArrayDecoders.decodeFixed32(r8, r2)
            r14.putInt(r7, r10, r3)
            int r3 = r2 + 4
            r2 = r24 | r22
            r4 = r7
            r7 = r1
            r1 = r4
            r4 = r35
            r10 = r2
            r2 = r8
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x0388:
            r28 = r7
            r7 = r1
            r29 = r10
            r10 = r2
            r11 = r28
            r1 = r29
            goto L_0x0497
        L_0x0394:
            r8 = r7
            r7 = r1
            r1 = r8
            r8 = r19
            r19 = r2
            r2 = r8
            r8 = r33
            r15 = r11
            r25 = r14
            r14 = r9
            r9 = r6
            r28 = r17
            r17 = r34
            r34 = r10
            r10 = r28
            r3 = 1
            if (r9 != r3) goto L_0x03cc
            long r5 = com.google.protobuf.ArrayDecoders.decodeFixed64(r8, r2)
            r3 = r10
            r10 = r2
            r2 = r7
            r7 = r1
            r1 = r14
            r1.putLong(r2, r3, r5)
            r1 = r2
            int r2 = r10 + 8
            r10 = r24 | r22
            r4 = r35
            r3 = r2
            r2 = r8
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x03cc:
            r3 = r7
            r7 = r1
            r1 = r3
            r3 = r10
            r10 = r2
            r11 = r1
            r1 = r3
            goto L_0x0497
        L_0x03d5:
            r8 = r33
            r15 = r11
            r25 = r14
            r3 = r17
            r17 = r34
            r14 = r9
            r34 = r10
            r10 = r19
            r19 = r2
            r9 = r6
            if (r9 != 0) goto L_0x0400
            int r2 = com.google.protobuf.ArrayDecoders.decodeVarint32(r8, r10, r7)
            int r5 = r7.int1
            r14.putInt(r1, r3, r5)
            r10 = r24 | r22
            r4 = r35
            r3 = r2
            r2 = r8
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x0400:
            r11 = r1
            r1 = r3
            goto L_0x0497
        L_0x0404:
            r8 = r33
            r15 = r11
            r25 = r14
            r3 = r17
            r17 = r34
            r14 = r9
            r34 = r10
            r10 = r19
            r19 = r2
            r9 = r6
            if (r9 != 0) goto L_0x0436
            int r10 = com.google.protobuf.ArrayDecoders.decodeVarint64(r8, r10, r7)
            long r5 = r7.long1
            r2 = r1
            r1 = r14
            r1.putLong(r2, r3, r5)
            r11 = r2
            r1 = r3
            r3 = r24 | r22
            r1 = r10
            r10 = r3
            r3 = r1
            r4 = r35
            r2 = r8
            r1 = r11
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x0436:
            r11 = r1
            r1 = r3
            goto L_0x0497
        L_0x043a:
            r8 = r33
            r15 = r11
            r25 = r14
            r11 = r1
            r14 = r9
            r9 = r6
            r28 = r17
            r17 = r34
            r34 = r10
            r10 = r19
            r19 = r2
            r1 = r28
            if (r9 != r3) goto L_0x0497
            float r3 = com.google.protobuf.ArrayDecoders.decodeFloat(r8, r10)
            com.google.protobuf.UnsafeUtil.putFloat((java.lang.Object) r11, (long) r1, (float) r3)
            int r3 = r10 + 4
            r10 = r24 | r22
            r4 = r35
            r2 = r8
            r1 = r11
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x0468:
            r8 = r33
            r15 = r11
            r25 = r14
            r11 = r1
            r14 = r9
            r9 = r6
            r28 = r17
            r17 = r34
            r34 = r10
            r10 = r19
            r19 = r2
            r1 = r28
            r3 = 1
            if (r9 != r3) goto L_0x0497
            double r3 = com.google.protobuf.ArrayDecoders.decodeDouble(r8, r10)
            com.google.protobuf.UnsafeUtil.putDouble((java.lang.Object) r11, (long) r1, (double) r3)
            int r3 = r10 + 8
            r10 = r24 | r22
            r4 = r35
            r2 = r8
            r1 = r11
            r11 = r12
            r6 = r13
            r9 = r14
            r5 = r16
            r8 = r34
            goto L_0x001c
        L_0x0497:
            r8 = r34
            r11 = r9
            r2 = r10
            r5 = r13
            r10 = r25
            r9 = r0
            r25 = r14
            goto L_0x05ec
        L_0x04a3:
            r24 = r10
            r15 = r11
            r25 = r14
            r10 = r19
            r11 = r1
            r14 = r9
            r1 = r17
            r17 = r5
            r9 = r6
            r3 = 27
            r4 = r17
            if (r4 != r3) goto L_0x0518
            r6 = 2
            if (r9 != r6) goto L_0x0509
            java.lang.Object r3 = r14.getObject(r11, r1)
            com.google.protobuf.Internal$ProtobufList r3 = (com.google.protobuf.Internal.ProtobufList) r3
            boolean r5 = r3.isModifiable()
            if (r5 != 0) goto L_0x04db
            int r5 = r3.size()
            if (r5 != 0) goto L_0x04d0
            r6 = 10
            goto L_0x04d2
        L_0x04d0:
            int r6 = r5 * 2
        L_0x04d2:
            com.google.protobuf.Internal$ProtobufList r3 = r3.mutableCopyWithCapacity(r6)
            r14.putObject(r11, r1, r3)
            r6 = r3
            goto L_0x04dc
        L_0x04db:
            r6 = r3
        L_0x04dc:
            r2 = r1
            com.google.protobuf.Schema r1 = r0.getMessageFieldSchema(r12)
            r5 = r10
            r10 = r4
            r4 = r5
            r5 = r35
            r17 = r8
            r8 = r12
            r28 = r2
            r3 = r33
            r2 = r13
            r12 = r28
            int r1 = com.google.protobuf.ArrayDecoders.decodeMessageList(r1, r2, r3, r4, r5, r6, r7)
            r5 = r2
            r2 = r33
            r4 = r35
            r7 = r37
            r3 = r1
            r6 = r5
            r1 = r11
            r9 = r14
            r5 = r16
            r10 = r24
            r11 = r8
            r8 = r17
            goto L_0x001c
        L_0x0509:
            r17 = r8
            r3 = r10
            r8 = r12
            r5 = r13
            r12 = r1
            r10 = r4
            r11 = r9
            r10 = r25
            r9 = r5
            r25 = r14
            goto L_0x05a9
        L_0x0518:
            r17 = r8
            r3 = r10
            r8 = r12
            r5 = r13
            r12 = r1
            r10 = r4
            r1 = 49
            if (r10 > r1) goto L_0x0562
            r1 = r3
            r7 = r9
            r4 = r10
            long r9 = (long) r15
            r2 = r33
            r18 = r15
            r6 = r25
            r15 = r1
            r1 = r11
            r25 = r14
            r14 = r37
            r11 = r4
            r4 = r35
            int r3 = r0.parseRepeatedField(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            r9 = r5
            r10 = r6
            r1 = r12
            r12 = r11
            r11 = r7
            if (r3 == r15) goto L_0x0557
            r0 = r31
            r1 = r32
            r2 = r33
            r4 = r35
            r7 = r37
            r11 = r8
            r6 = r9
            r5 = r16
            r8 = r17
            r10 = r24
            r9 = r25
            goto L_0x001c
        L_0x0557:
            r7 = r37
            r2 = r3
            r12 = r8
            r5 = r9
            r8 = r17
            r9 = r31
            goto L_0x05ec
        L_0x0562:
            r11 = r9
            r1 = r12
            r18 = r15
            r9 = r5
            r12 = r10
            r10 = r25
            r25 = r14
            r0 = 50
            if (r12 != r0) goto L_0x05b3
            r6 = 2
            if (r11 != r6) goto L_0x05a9
            r13 = r3
            r0 = r31
            r4 = r35
            r6 = r1
            r5 = r8
            r1 = r32
            r2 = r33
            r8 = r37
            int r3 = r0.parseMapField(r1, r2, r3, r4, r5, r6, r8)
            r8 = r5
            r1 = r6
            if (r3 == r13) goto L_0x059e
            r0 = r31
            r1 = r32
            r2 = r33
            r4 = r35
            r7 = r37
            r11 = r8
            r6 = r9
            r5 = r16
            r8 = r17
            r10 = r24
            r9 = r25
            goto L_0x001c
        L_0x059e:
            r7 = r37
            r2 = r3
            r12 = r8
            r5 = r9
            r8 = r17
            r9 = r31
            goto L_0x05ec
        L_0x05a9:
            r7 = r37
            r2 = r3
            r12 = r8
            r5 = r9
            r8 = r17
            r9 = r31
            goto L_0x05ec
        L_0x05b3:
            r14 = r3
            r0 = r31
            r4 = r35
            r13 = r37
            r5 = r9
            r6 = r10
            r7 = r11
            r9 = r12
            r10 = r1
            r12 = r8
            r8 = r18
            r1 = r32
            r2 = r33
            int r3 = r0.parseOneofField(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            r15 = r8
            r4 = r9
            r1 = r10
            r8 = r12
            r9 = r0
            r10 = r6
            r11 = r7
            r7 = r13
            if (r3 == r14) goto L_0x05e8
            r1 = r32
            r2 = r33
            r4 = r35
            r6 = r5
            r11 = r8
            r0 = r9
            r5 = r16
            r8 = r17
            r10 = r24
            r9 = r25
            goto L_0x001c
        L_0x05e8:
            r2 = r3
            r12 = r8
            r8 = r17
        L_0x05ec:
            r15 = r36
            if (r5 != r15) goto L_0x05fd
            if (r15 == 0) goto L_0x05fd
            r7 = r32
            r13 = r35
            r6 = r2
            r10 = r8
            r8 = r5
            r11 = r24
            goto L_0x065b
        L_0x05fd:
            boolean r0 = r9.hasExtensions
            if (r0 == 0) goto L_0x0621
            com.google.protobuf.ExtensionRegistryLite r0 = r7.extensionRegistry
            com.google.protobuf.ExtensionRegistryLite r1 = com.google.protobuf.ExtensionRegistryLite.getEmptyRegistry()
            if (r0 == r1) goto L_0x061e
            r0 = r5
            com.google.protobuf.MessageLite r5 = r9.defaultInstance
            com.google.protobuf.UnknownFieldSchema<?, ?> r6 = r9.unknownFieldSchema
            r4 = r32
            r1 = r33
            r3 = r35
            int r2 = com.google.protobuf.ArrayDecoders.decodeExtensionOrUnknownField(r0, r1, r2, r3, r4, r5, r6, r7)
            r7 = r4
            r13 = r35
            r5 = r0
            r3 = r2
            goto L_0x0636
        L_0x061e:
            r7 = r32
            goto L_0x0623
        L_0x0621:
            r7 = r32
        L_0x0623:
            com.google.protobuf.UnknownFieldSetLite r4 = getMutableUnknownFields(r7)
            r1 = r33
            r3 = r35
            r0 = r5
            r5 = r37
            int r2 = com.google.protobuf.ArrayDecoders.decodeUnknownField(r0, r1, r2, r3, r4, r5)
            r13 = r3
            r5 = r0
            r3 = r2
        L_0x0636:
            r2 = r33
            r6 = r5
            r1 = r7
            r0 = r9
            r11 = r12
            r4 = r13
            r5 = r16
            r10 = r24
            r9 = r25
            r7 = r37
            goto L_0x001c
        L_0x0647:
            r15 = r36
            r7 = r1
            r13 = r4
            r17 = r8
            r25 = r9
            r24 = r10
            r9 = r0
            r16 = r5
            r8 = r6
            r12 = r11
            r10 = r17
            r6 = r3
            r11 = r24
        L_0x065b:
            r3 = 1048575(0xfffff, float:1.469367E-39)
            if (r10 == r3) goto L_0x0667
            long r0 = (long) r10
            r14 = r25
            r14.putInt(r7, r0, r11)
            goto L_0x0669
        L_0x0667:
            r14 = r25
        L_0x0669:
            r0 = 0
            int r1 = r9.checkInitializedCount
            r3 = r0
            r0 = r1
        L_0x066e:
            int r1 = r9.repeatedFieldOffsetStart
            if (r0 >= r1) goto L_0x0689
            int[] r1 = r9.intArray
            r2 = r1[r0]
            com.google.protobuf.UnknownFieldSchema<?, ?> r4 = r9.unknownFieldSchema
            r5 = r32
            r1 = r7
            r7 = r0
            r0 = r9
            java.lang.Object r2 = r0.filterMapUnknownEnumValues(r1, r2, r3, r4, r5)
            r3 = r2
            com.google.protobuf.UnknownFieldSetLite r3 = (com.google.protobuf.UnknownFieldSetLite) r3
            int r2 = r7 + 1
            r7 = r1
            r0 = r2
            goto L_0x066e
        L_0x0689:
            r1 = r7
            r7 = r0
            r0 = r9
            if (r3 == 0) goto L_0x0693
            com.google.protobuf.UnknownFieldSchema<?, ?> r2 = r0.unknownFieldSchema
            r2.setBuilderToMessage(r1, r3)
        L_0x0693:
            if (r15 != 0) goto L_0x069d
            if (r6 != r13) goto L_0x0698
            goto L_0x06a1
        L_0x0698:
            com.google.protobuf.InvalidProtocolBufferException r2 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r2
        L_0x069d:
            if (r6 > r13) goto L_0x06a2
            if (r8 != r15) goto L_0x06a2
        L_0x06a1:
            return r6
        L_0x06a2:
            com.google.protobuf.InvalidProtocolBufferException r2 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSchema.parseMessage(java.lang.Object, byte[], int, int, int, com.google.protobuf.ArrayDecoders$Registers):int");
    }

    private Object mutableMessageFieldForMerge(T message, int pos) {
        Schema fieldSchema = getMessageFieldSchema(pos);
        long offset = offset(typeAndOffsetAt(pos));
        if (!isFieldPresent(message, pos)) {
            return fieldSchema.newInstance();
        }
        Object current = UNSAFE.getObject(message, offset);
        if (isMutable(current)) {
            return current;
        }
        Object newMessage = fieldSchema.newInstance();
        if (current != null) {
            fieldSchema.mergeFrom(newMessage, current);
        }
        return newMessage;
    }

    private void storeMessageField(T message, int pos, Object field) {
        UNSAFE.putObject(message, offset(typeAndOffsetAt(pos)), field);
        setFieldPresent(message, pos);
    }

    private Object mutableOneofMessageFieldForMerge(T message, int fieldNumber, int pos) {
        Schema fieldSchema = getMessageFieldSchema(pos);
        if (!isOneofPresent(message, fieldNumber, pos)) {
            return fieldSchema.newInstance();
        }
        Object current = UNSAFE.getObject(message, offset(typeAndOffsetAt(pos)));
        if (isMutable(current)) {
            return current;
        }
        Object newMessage = fieldSchema.newInstance();
        if (current != null) {
            fieldSchema.mergeFrom(newMessage, current);
        }
        return newMessage;
    }

    private void storeOneofMessageField(T message, int fieldNumber, int pos, Object field) {
        UNSAFE.putObject(message, offset(typeAndOffsetAt(pos)), field);
        setOneofPresent(message, fieldNumber, pos);
    }

    public void mergeFrom(T message, byte[] data, int position, int limit, ArrayDecoders.Registers registers) throws IOException {
        parseMessage(message, data, position, limit, 0, registers);
    }

    public void makeImmutable(T message) {
        if (isMutable(message)) {
            if (message instanceof GeneratedMessageLite) {
                GeneratedMessageLite<?, ?> generatedMessage = message;
                generatedMessage.clearMemoizedSerializedSize();
                generatedMessage.clearMemoizedHashCode();
                generatedMessage.markImmutable();
            }
            int bufferLength = this.buffer.length;
            for (int pos = 0; pos < bufferLength; pos += 3) {
                int typeAndOffset = typeAndOffsetAt(pos);
                long offset = offset(typeAndOffset);
                switch (type(typeAndOffset)) {
                    case 9:
                    case 17:
                        if (!isFieldPresent(message, pos)) {
                            break;
                        } else {
                            getMessageFieldSchema(pos).makeImmutable(UNSAFE.getObject(message, offset));
                            break;
                        }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE:
                    case 49:
                        this.listFieldSchema.makeImmutableListAt(message, offset);
                        break;
                    case 50:
                        Object mapField = UNSAFE.getObject(message, offset);
                        if (mapField == null) {
                            break;
                        } else {
                            UNSAFE.putObject(message, offset, this.mapFieldSchema.toImmutable(mapField));
                            break;
                        }
                    case 60:
                    case 68:
                        if (!isOneofPresent(message, numberAt(pos), pos)) {
                            break;
                        } else {
                            getMessageFieldSchema(pos).makeImmutable(UNSAFE.getObject(message, offset));
                            break;
                        }
                }
            }
            this.unknownFieldSchema.makeImmutable(message);
            if (this.hasExtensions) {
                this.extensionSchema.makeImmutable(message);
            }
        }
    }

    private final <K, V> void mergeMap(Object message, int pos, Object mapDefaultEntry, ExtensionRegistryLite extensionRegistry, Reader reader) throws IOException {
        long offset = offset(typeAndOffsetAt(pos));
        Object mapField = UnsafeUtil.getObject(message, offset);
        if (mapField == null) {
            mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            UnsafeUtil.putObject(message, offset, mapField);
        } else if (this.mapFieldSchema.isImmutable(mapField)) {
            Object oldMapField = mapField;
            mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            this.mapFieldSchema.mergeFrom(mapField, oldMapField);
            UnsafeUtil.putObject(message, offset, mapField);
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(mapField), this.mapFieldSchema.forMapMetadata(mapDefaultEntry), extensionRegistry);
    }

    private <UT, UB> UB filterMapUnknownEnumValues(Object message, int pos, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema2, Object containerMessage) {
        Internal.EnumVerifier enumVerifier;
        int fieldNumber = numberAt(pos);
        Object mapField = UnsafeUtil.getObject(message, offset(typeAndOffsetAt(pos)));
        if (mapField == null || (enumVerifier = getEnumFieldVerifier(pos)) == null) {
            return unknownFields;
        }
        return filterUnknownEnumMap(pos, fieldNumber, this.mapFieldSchema.forMutableMapData(mapField), enumVerifier, unknownFields, unknownFieldSchema2, containerMessage);
    }

    private <K, V, UT, UB> UB filterUnknownEnumMap(int pos, int number, Map<K, V> mapData, Internal.EnumVerifier enumVerifier, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema2, Object containerMessage) {
        MapEntryLite.Metadata<?, ?> forMapMetadata = this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos));
        Iterator<Map.Entry<K, V>> it = mapData.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            if (!enumVerifier.isInRange(((Integer) entry.getValue()).intValue())) {
                if (unknownFields == null) {
                    unknownFields = unknownFieldSchema2.getBuilderFromMessage(containerMessage);
                }
                ByteString.CodedBuilder codedBuilder = ByteString.newCodedBuilder(MapEntryLite.computeSerializedSize(forMapMetadata, entry.getKey(), entry.getValue()));
                try {
                    MapEntryLite.writeTo(codedBuilder.getCodedOutput(), forMapMetadata, entry.getKey(), entry.getValue());
                    unknownFieldSchema2.addLengthDelimited(unknownFields, number, codedBuilder.build());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return unknownFields;
    }

    public final boolean isInitialized(T message) {
        int pos;
        int currentPresenceField;
        int currentPresenceFieldOffset;
        T t = message;
        int currentPresenceFieldOffset2 = 1048575;
        int currentPresenceFieldOffset3 = 0;
        int i = 0;
        while (i < this.checkInitializedCount) {
            int pos2 = this.intArray[i];
            int number = numberAt(pos2);
            int typeAndOffset = typeAndOffsetAt(pos2);
            int presenceMaskAndOffset = this.buffer[pos2 + 2];
            int presenceFieldOffset = presenceMaskAndOffset & 1048575;
            int presenceMask = 1 << (presenceMaskAndOffset >>> 20);
            if (presenceFieldOffset != currentPresenceFieldOffset2) {
                int currentPresenceFieldOffset4 = presenceFieldOffset;
                if (currentPresenceFieldOffset4 != 1048575) {
                    currentPresenceField = currentPresenceFieldOffset4;
                    currentPresenceFieldOffset = pos2;
                    pos = UNSAFE.getInt(t, (long) presenceFieldOffset);
                } else {
                    int i2 = currentPresenceFieldOffset3;
                    currentPresenceField = currentPresenceFieldOffset4;
                    currentPresenceFieldOffset = pos2;
                    pos = i2;
                }
            } else {
                int i3 = currentPresenceFieldOffset3;
                currentPresenceField = currentPresenceFieldOffset2;
                currentPresenceFieldOffset = pos2;
                pos = i3;
            }
            if (isRequired(typeAndOffset) && !isFieldPresent(t, currentPresenceFieldOffset, currentPresenceField, pos, presenceMask)) {
                return false;
            }
            switch (type(typeAndOffset)) {
                case 9:
                case 17:
                    if (isFieldPresent(t, currentPresenceFieldOffset, currentPresenceField, pos, presenceMask) && !isInitialized(t, typeAndOffset, getMessageFieldSchema(currentPresenceFieldOffset))) {
                        return false;
                    }
                case 27:
                case 49:
                    if (isListInitialized(t, typeAndOffset, currentPresenceFieldOffset)) {
                        break;
                    } else {
                        return false;
                    }
                case 50:
                    if (isMapInitialized(t, typeAndOffset, currentPresenceFieldOffset)) {
                        break;
                    } else {
                        return false;
                    }
                case 60:
                case 68:
                    if (isOneofPresent(t, number, currentPresenceFieldOffset) && !isInitialized(t, typeAndOffset, getMessageFieldSchema(currentPresenceFieldOffset))) {
                        return false;
                    }
            }
            i++;
            currentPresenceFieldOffset2 = currentPresenceField;
            currentPresenceFieldOffset3 = pos;
        }
        return this.hasExtensions == 0 || this.extensionSchema.getExtensions(t).isInitialized();
    }

    private static boolean isInitialized(Object message, int typeAndOffset, Schema schema) {
        return schema.isInitialized(UnsafeUtil.getObject(message, offset(typeAndOffset)));
    }

    private <N> boolean isListInitialized(Object message, int typeAndOffset, int pos) {
        List<N> list = (List) UnsafeUtil.getObject(message, offset(typeAndOffset));
        if (list.isEmpty()) {
            return true;
        }
        Schema schema = getMessageFieldSchema(pos);
        for (int i = 0; i < list.size(); i++) {
            if (!schema.isInitialized(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isMapInitialized(T message, int typeAndOffset, int pos) {
        Map<?, ?> map = this.mapFieldSchema.forMapData(UnsafeUtil.getObject((Object) message, offset(typeAndOffset)));
        if (map.isEmpty()) {
            return true;
        }
        if (this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos)).valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        Schema schema = null;
        for (Object nested : map.values()) {
            if (schema == null) {
                schema = Protobuf.getInstance().schemaFor(nested.getClass());
            }
            if (!schema.isInitialized(nested)) {
                return false;
            }
        }
        return true;
    }

    private void writeString(int fieldNumber, Object value, Writer writer) throws IOException {
        if (value instanceof String) {
            writer.writeString(fieldNumber, (String) value);
        } else {
            writer.writeBytes(fieldNumber, (ByteString) value);
        }
    }

    private void readString(Object message, int typeAndOffset, Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), (Object) reader.readStringRequireUtf8());
        } else if (this.lite) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), (Object) reader.readString());
        } else {
            UnsafeUtil.putObject(message, offset(typeAndOffset), (Object) reader.readBytes());
        }
    }

    private void readStringList(Object message, int typeAndOffset, Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            reader.readStringListRequireUtf8(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        } else {
            reader.readStringList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        }
    }

    private <E> void readMessageList(Object message, int typeAndOffset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        reader.readMessageList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)), schema, extensionRegistry);
    }

    private <E> void readGroupList(Object message, long offset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        reader.readGroupList(this.listFieldSchema.mutableListAt(message, offset), schema, extensionRegistry);
    }

    private int numberAt(int pos) {
        return this.buffer[pos];
    }

    private int typeAndOffsetAt(int pos) {
        return this.buffer[pos + 1];
    }

    private int presenceMaskAndOffsetAt(int pos) {
        return this.buffer[pos + 2];
    }

    private static int type(int value) {
        return (FIELD_TYPE_MASK & value) >>> 20;
    }

    private static boolean isRequired(int value) {
        return (REQUIRED_MASK & value) != 0;
    }

    private static boolean isEnforceUtf8(int value) {
        return (ENFORCE_UTF8_MASK & value) != 0;
    }

    private static boolean isLegacyEnumIsClosed(int value) {
        return (Integer.MIN_VALUE & value) != 0;
    }

    private static long offset(int value) {
        return (long) (1048575 & value);
    }

    private static boolean isMutable(Object message) {
        if (message == null) {
            return false;
        }
        if (message instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) message).isMutable();
        }
        return true;
    }

    private static void checkMutable(Object message) {
        if (!isMutable(message)) {
            throw new IllegalArgumentException("Mutating immutable message: " + message);
        }
    }

    private static <T> double doubleAt(T message, long offset) {
        return UnsafeUtil.getDouble((Object) message, offset);
    }

    private static <T> float floatAt(T message, long offset) {
        return UnsafeUtil.getFloat((Object) message, offset);
    }

    private static <T> int intAt(T message, long offset) {
        return UnsafeUtil.getInt((Object) message, offset);
    }

    private static <T> long longAt(T message, long offset) {
        return UnsafeUtil.getLong((Object) message, offset);
    }

    private static <T> boolean booleanAt(T message, long offset) {
        return UnsafeUtil.getBoolean((Object) message, offset);
    }

    private static <T> double oneofDoubleAt(T message, long offset) {
        return ((Double) UnsafeUtil.getObject((Object) message, offset)).doubleValue();
    }

    private static <T> float oneofFloatAt(T message, long offset) {
        return ((Float) UnsafeUtil.getObject((Object) message, offset)).floatValue();
    }

    private static <T> int oneofIntAt(T message, long offset) {
        return ((Integer) UnsafeUtil.getObject((Object) message, offset)).intValue();
    }

    private static <T> long oneofLongAt(T message, long offset) {
        return ((Long) UnsafeUtil.getObject((Object) message, offset)).longValue();
    }

    private static <T> boolean oneofBooleanAt(T message, long offset) {
        return ((Boolean) UnsafeUtil.getObject((Object) message, offset)).booleanValue();
    }

    private boolean arePresentForEquals(T message, T other, int pos) {
        return isFieldPresent(message, pos) == isFieldPresent(other, pos);
    }

    private boolean isFieldPresent(T message, int pos, int presenceFieldOffset, int presenceField, int presenceMask) {
        if (presenceFieldOffset == 1048575) {
            return isFieldPresent(message, pos);
        }
        return (presenceField & presenceMask) != 0;
    }

    private boolean isFieldPresent(T message, int pos) {
        int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
        if (((long) (presenceMaskAndOffset & 1048575)) == 1048575) {
            int typeAndOffset = typeAndOffsetAt(pos);
            long offset = offset(typeAndOffset);
            switch (type(typeAndOffset)) {
                case 0:
                    if (Double.doubleToRawLongBits(UnsafeUtil.getDouble((Object) message, offset)) != 0) {
                        return true;
                    }
                    return false;
                case 1:
                    if (Float.floatToRawIntBits(UnsafeUtil.getFloat((Object) message, offset)) != 0) {
                        return true;
                    }
                    return false;
                case 2:
                    if (UnsafeUtil.getLong((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 3:
                    if (UnsafeUtil.getLong((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 4:
                    if (UnsafeUtil.getInt((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 5:
                    if (UnsafeUtil.getLong((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 6:
                    if (UnsafeUtil.getInt((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 7:
                    return UnsafeUtil.getBoolean((Object) message, offset);
                case 8:
                    Object value = UnsafeUtil.getObject((Object) message, offset);
                    if (value instanceof String) {
                        return !((String) value).isEmpty();
                    }
                    if (value instanceof ByteString) {
                        return !ByteString.EMPTY.equals(value);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    if (UnsafeUtil.getObject((Object) message, offset) != null) {
                        return true;
                    }
                    return false;
                case 10:
                    return !ByteString.EMPTY.equals(UnsafeUtil.getObject((Object) message, offset));
                case 11:
                    if (UnsafeUtil.getInt((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 12:
                    if (UnsafeUtil.getInt((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 13:
                    if (UnsafeUtil.getInt((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 14:
                    if (UnsafeUtil.getLong((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 15:
                    if (UnsafeUtil.getInt((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 16:
                    if (UnsafeUtil.getLong((Object) message, offset) != 0) {
                        return true;
                    }
                    return false;
                case 17:
                    if (UnsafeUtil.getObject((Object) message, offset) != null) {
                        return true;
                    }
                    return false;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            if ((UnsafeUtil.getInt((Object) message, (long) (1048575 & presenceMaskAndOffset)) & (1 << (presenceMaskAndOffset >>> 20))) != 0) {
                return true;
            }
            return false;
        }
    }

    private void setFieldPresent(T message, int pos) {
        int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
        long presenceFieldOffset = (long) (1048575 & presenceMaskAndOffset);
        if (presenceFieldOffset != 1048575) {
            UnsafeUtil.putInt((Object) message, presenceFieldOffset, UnsafeUtil.getInt((Object) message, presenceFieldOffset) | (1 << (presenceMaskAndOffset >>> 20)));
        }
    }

    private boolean isOneofPresent(T message, int fieldNumber, int pos) {
        return UnsafeUtil.getInt((Object) message, (long) (1048575 & presenceMaskAndOffsetAt(pos))) == fieldNumber;
    }

    private boolean isOneofCaseEqual(T message, T other, int pos) {
        int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
        return UnsafeUtil.getInt((Object) message, (long) (presenceMaskAndOffset & 1048575)) == UnsafeUtil.getInt((Object) other, (long) (1048575 & presenceMaskAndOffset));
    }

    private void setOneofPresent(T message, int fieldNumber, int pos) {
        UnsafeUtil.putInt((Object) message, (long) (1048575 & presenceMaskAndOffsetAt(pos)), fieldNumber);
    }

    private int positionForFieldNumber(int number) {
        if (number < this.minFieldNumber || number > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(number, 0);
    }

    private int positionForFieldNumber(int number, int min) {
        if (number < this.minFieldNumber || number > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(number, min);
    }

    private int slowPositionForFieldNumber(int number, int min) {
        int max = (this.buffer.length / 3) - 1;
        while (min <= max) {
            int mid = (max + min) >>> 1;
            int pos = mid * 3;
            int midFieldNumber = numberAt(pos);
            if (number == midFieldNumber) {
                return pos;
            }
            if (number < midFieldNumber) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int getSchemaSize() {
        return this.buffer.length * 3;
    }
}
