package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.LazyField;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@CheckReturnValue
final class MessageSetSchema<T> implements Schema<T> {
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;

    private MessageSetSchema(UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MessageLite defaultInstance2) {
        this.unknownFieldSchema = unknownFieldSchema2;
        this.hasExtensions = extensionSchema2.hasExtensions(defaultInstance2);
        this.extensionSchema = extensionSchema2;
        this.defaultInstance = defaultInstance2;
    }

    static <T> MessageSetSchema<T> newSchema(UnknownFieldSchema<?, ?> unknownFieldSchema2, ExtensionSchema<?> extensionSchema2, MessageLite defaultInstance2) {
        return new MessageSetSchema<>(unknownFieldSchema2, extensionSchema2, defaultInstance2);
    }

    public T newInstance() {
        if (this.defaultInstance instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) this.defaultInstance).newMutableInstance();
        }
        return this.defaultInstance.newBuilderForType().buildPartial();
    }

    public boolean equals(T message, T other) {
        if (!this.unknownFieldSchema.getFromMessage(message).equals(this.unknownFieldSchema.getFromMessage(other))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(message).equals(this.extensionSchema.getExtensions(other));
        }
        return true;
    }

    public int hashCode(T message) {
        int hashCode = this.unknownFieldSchema.getFromMessage(message).hashCode();
        if (this.hasExtensions) {
            return (hashCode * 53) + this.extensionSchema.getExtensions(message).hashCode();
        }
        return hashCode;
    }

    public void mergeFrom(T message, T other) {
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
        }
    }

    public void writeTo(T message, Writer writer) throws IOException {
        Iterator<Map.Entry<?, Object>> it = this.extensionSchema.getExtensions(message).iterator();
        while (it.hasNext()) {
            Map.Entry<?, ?> extension = it.next();
            FieldSet.FieldDescriptorLite<?> fd = (FieldSet.FieldDescriptorLite) extension.getKey();
            if (fd.getLiteJavaType() != WireFormat.JavaType.MESSAGE || fd.isRepeated() || fd.isPacked()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (extension instanceof LazyField.LazyEntry) {
                writer.writeMessageSetItem(fd.getNumber(), ((LazyField.LazyEntry) extension).getField().toByteString());
            } else {
                writer.writeMessageSetItem(fd.getNumber(), extension.getValue());
            }
        }
        writeUnknownFieldsHelper(this.unknownFieldSchema, message, writer);
    }

    private <UT, UB> void writeUnknownFieldsHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema2, T message, Writer writer) throws IOException {
        unknownFieldSchema2.writeAsMessageSetTo(unknownFieldSchema2.getFromMessage(message), writer);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: com.google.protobuf.GeneratedMessageLite$GeneratedExtension} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: com.google.protobuf.GeneratedMessageLite$GeneratedExtension} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mergeFrom(T r18, byte[] r19, int r20, int r21, com.google.protobuf.ArrayDecoders.Registers r22) throws java.io.IOException {
        /*
            r17 = this;
            r0 = r17
            r2 = r19
            r4 = r21
            r6 = r22
            r1 = r18
            com.google.protobuf.GeneratedMessageLite r1 = (com.google.protobuf.GeneratedMessageLite) r1
            com.google.protobuf.UnknownFieldSetLite r1 = r1.unknownFields
            com.google.protobuf.UnknownFieldSetLite r3 = com.google.protobuf.UnknownFieldSetLite.getDefaultInstance()
            if (r1 != r3) goto L_0x0020
            com.google.protobuf.UnknownFieldSetLite r1 = com.google.protobuf.UnknownFieldSetLite.newInstance()
            r3 = r18
            com.google.protobuf.GeneratedMessageLite r3 = (com.google.protobuf.GeneratedMessageLite) r3
            r3.unknownFields = r1
            r5 = r1
            goto L_0x0021
        L_0x0020:
            r5 = r1
        L_0x0021:
            r1 = r18
            com.google.protobuf.GeneratedMessageLite$ExtendableMessage r1 = (com.google.protobuf.GeneratedMessageLite.ExtendableMessage) r1
            com.google.protobuf.FieldSet r7 = r1.ensureExtensionsAreMutable()
            r1 = 0
            r3 = r1
            r1 = r20
        L_0x002d:
            if (r1 >= r4) goto L_0x0105
            r8 = r3
            int r3 = com.google.protobuf.ArrayDecoders.decodeVarint32(r2, r1, r6)
            int r1 = r6.int1
            int r9 = com.google.protobuf.WireFormat.MESSAGE_SET_ITEM_TAG
            r10 = 2
            if (r1 == r9) goto L_0x0081
            int r9 = com.google.protobuf.WireFormat.getTagWireType(r1)
            if (r9 != r10) goto L_0x007a
            com.google.protobuf.ExtensionSchema<?> r9 = r0.extensionSchema
            com.google.protobuf.ExtensionRegistryLite r10 = r6.extensionRegistry
            com.google.protobuf.MessageLite r11 = r0.defaultInstance
            int r12 = com.google.protobuf.WireFormat.getTagFieldNumber(r1)
            java.lang.Object r9 = r9.findExtensionByNumber(r10, r11, r12)
            r8 = r9
            com.google.protobuf.GeneratedMessageLite$GeneratedExtension r8 = (com.google.protobuf.GeneratedMessageLite.GeneratedExtension) r8
            if (r8 == 0) goto L_0x0072
            com.google.protobuf.Protobuf r9 = com.google.protobuf.Protobuf.getInstance()
            com.google.protobuf.MessageLite r10 = r8.getMessageDefaultInstance()
            java.lang.Class r10 = r10.getClass()
            com.google.protobuf.Schema r9 = r9.schemaFor(r10)
            int r3 = com.google.protobuf.ArrayDecoders.decodeMessageField(r9, r2, r3, r4, r6)
            com.google.protobuf.GeneratedMessageLite$ExtensionDescriptor r9 = r8.descriptor
            java.lang.Object r10 = r6.object1
            r7.setField(r9, r10)
            r1 = r3
            r3 = r8
            goto L_0x002d
        L_0x0072:
            int r3 = com.google.protobuf.ArrayDecoders.decodeUnknownField(r1, r2, r3, r4, r5, r6)
            r1 = r3
            r3 = r8
            goto L_0x002d
        L_0x007a:
            int r3 = com.google.protobuf.ArrayDecoders.skipField(r1, r2, r3, r4, r6)
            r1 = r3
            r3 = r8
            goto L_0x002d
        L_0x0081:
            r9 = 0
            r11 = 0
        L_0x0083:
            if (r3 >= r4) goto L_0x00f4
            int r3 = com.google.protobuf.ArrayDecoders.decodeVarint32(r2, r3, r6)
            int r12 = r6.int1
            int r13 = com.google.protobuf.WireFormat.getTagFieldNumber(r12)
            int r14 = com.google.protobuf.WireFormat.getTagWireType(r12)
            switch(r13) {
                case 2: goto L_0x00c9;
                case 3: goto L_0x0099;
                default: goto L_0x0096;
            }
        L_0x0096:
            r16 = r1
            goto L_0x00e6
        L_0x0099:
            if (r8 == 0) goto L_0x00b8
            com.google.protobuf.Protobuf r15 = com.google.protobuf.Protobuf.getInstance()
            com.google.protobuf.MessageLite r16 = r8.getMessageDefaultInstance()
            java.lang.Class r10 = r16.getClass()
            com.google.protobuf.Schema r10 = r15.schemaFor(r10)
            int r3 = com.google.protobuf.ArrayDecoders.decodeMessageField(r10, r2, r3, r4, r6)
            com.google.protobuf.GeneratedMessageLite$ExtensionDescriptor r10 = r8.descriptor
            java.lang.Object r15 = r6.object1
            r7.setField(r10, r15)
            r10 = 2
            goto L_0x0083
        L_0x00b8:
            r10 = 2
            if (r14 != r10) goto L_0x00c6
            int r3 = com.google.protobuf.ArrayDecoders.decodeBytes(r2, r3, r6)
            java.lang.Object r10 = r6.object1
            r11 = r10
            com.google.protobuf.ByteString r11 = (com.google.protobuf.ByteString) r11
            r10 = 2
            goto L_0x0083
        L_0x00c6:
            r16 = r1
            goto L_0x00e6
        L_0x00c9:
            if (r14 != 0) goto L_0x00e4
            int r3 = com.google.protobuf.ArrayDecoders.decodeVarint32(r2, r3, r6)
            int r9 = r6.int1
            com.google.protobuf.ExtensionSchema<?> r10 = r0.extensionSchema
            com.google.protobuf.ExtensionRegistryLite r15 = r6.extensionRegistry
            r16 = r1
            com.google.protobuf.MessageLite r1 = r0.defaultInstance
            java.lang.Object r1 = r10.findExtensionByNumber(r15, r1, r9)
            r8 = r1
            com.google.protobuf.GeneratedMessageLite$GeneratedExtension r8 = (com.google.protobuf.GeneratedMessageLite.GeneratedExtension) r8
            r1 = r16
            r10 = 2
            goto L_0x0083
        L_0x00e4:
            r16 = r1
        L_0x00e6:
            int r1 = com.google.protobuf.WireFormat.MESSAGE_SET_ITEM_END_TAG
            if (r12 != r1) goto L_0x00ec
            r1 = r3
            goto L_0x00f7
        L_0x00ec:
            int r3 = com.google.protobuf.ArrayDecoders.skipField(r12, r2, r3, r4, r6)
            r1 = r16
            r10 = 2
            goto L_0x0083
        L_0x00f4:
            r16 = r1
            r1 = r3
        L_0x00f7:
            if (r11 == 0) goto L_0x0102
            r10 = 2
            int r3 = com.google.protobuf.WireFormat.makeTag(r9, r10)
            r5.storeField(r3, r11)
        L_0x0102:
            r3 = r8
            goto L_0x002d
        L_0x0105:
            r8 = r3
            if (r1 != r4) goto L_0x0109
            return
        L_0x0109:
            com.google.protobuf.InvalidProtocolBufferException r3 = com.google.protobuf.InvalidProtocolBufferException.parseFailure()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MessageSetSchema.mergeFrom(java.lang.Object, byte[], int, int, com.google.protobuf.ArrayDecoders$Registers):void");
    }

    public void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema2, ExtensionSchema<ET> extensionSchema2, T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
        Throwable th;
        UnknownFieldSchema<UT, UB> unknownFieldSchema3;
        UB unknownFields = unknownFieldSchema2.getBuilderFromMessage(message);
        FieldSet<ET> extensions = extensionSchema2.getMutableExtensions(message);
        while (reader.getFieldNumber() != Integer.MAX_VALUE) {
            try {
                unknownFieldSchema3 = unknownFieldSchema2;
                ExtensionSchema<ET> extensionSchema3 = extensionSchema2;
                Reader reader2 = reader;
                ExtensionRegistryLite extensionRegistry2 = extensionRegistry;
                try {
                    if (parseMessageSetItemOrUnknownField(reader2, extensionRegistry2, extensionSchema3, extensions, unknownFieldSchema3, unknownFields)) {
                        reader = reader2;
                        extensionRegistry = extensionRegistry2;
                        extensionSchema2 = extensionSchema3;
                        unknownFieldSchema2 = unknownFieldSchema3;
                    } else {
                        unknownFieldSchema3.setBuilderToMessage(message, unknownFields);
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    unknownFieldSchema3.setBuilderToMessage(message, unknownFields);
                    throw th;
                }
            } catch (Throwable th3) {
                unknownFieldSchema3 = unknownFieldSchema2;
                ExtensionSchema<ET> extensionSchema4 = extensionSchema2;
                Reader reader3 = reader;
                ExtensionRegistryLite extensionRegistryLite = extensionRegistry;
                th = th3;
                unknownFieldSchema3.setBuilderToMessage(message, unknownFields);
                throw th;
            }
        }
        unknownFieldSchema2.setBuilderToMessage(message, unknownFields);
    }

    public void makeImmutable(T message) {
        this.unknownFieldSchema.makeImmutable(message);
        this.extensionSchema.makeImmutable(message);
    }

    private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> boolean parseMessageSetItemOrUnknownField(Reader reader, ExtensionRegistryLite extensionRegistry, ExtensionSchema<ET> extensionSchema2, FieldSet<ET> extensions, UnknownFieldSchema<UT, UB> unknownFieldSchema2, UB unknownFields) throws IOException {
        int startTag = reader.getTag();
        if (startTag == WireFormat.MESSAGE_SET_ITEM_TAG) {
            int typeId = 0;
            ByteString rawBytes = null;
            Object extension = null;
            while (reader.getFieldNumber() != Integer.MAX_VALUE) {
                int tag = reader.getTag();
                if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    typeId = reader.readUInt32();
                    extension = extensionSchema2.findExtensionByNumber(extensionRegistry, this.defaultInstance, typeId);
                } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (extension != null) {
                        extensionSchema2.parseLengthPrefixedMessageSetItem(reader, extension, extensionRegistry, extensions);
                    } else {
                        rawBytes = reader.readBytes();
                    }
                } else if (!reader.skipField()) {
                    break;
                }
            }
            if (reader.getTag() == WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                if (rawBytes != null) {
                    if (extension != null) {
                        extensionSchema2.parseMessageSetItem(rawBytes, extension, extensionRegistry, extensions);
                    } else {
                        unknownFieldSchema2.addLengthDelimited(unknownFields, typeId, rawBytes);
                    }
                }
                return true;
            }
            throw InvalidProtocolBufferException.invalidEndTag();
        } else if (WireFormat.getTagWireType(startTag) != 2) {
            return reader.skipField();
        } else {
            Object extension2 = extensionSchema2.findExtensionByNumber(extensionRegistry, this.defaultInstance, WireFormat.getTagFieldNumber(startTag));
            if (extension2 == null) {
                return unknownFieldSchema2.mergeOneFieldFrom(unknownFields, reader, 0);
            }
            extensionSchema2.parseLengthPrefixedMessageSetItem(reader, extension2, extensionRegistry, extensions);
            return true;
        }
    }

    public final boolean isInitialized(T message) {
        return this.extensionSchema.getExtensions(message).isInitialized();
    }

    public int getSerializedSize(T message) {
        int size = 0 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, message);
        if (this.hasExtensions) {
            return size + this.extensionSchema.getExtensions(message).getMessageSetSerializedSize();
        }
        return size;
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> schema, T message) {
        return schema.getSerializedSizeAsMessageSet(schema.getFromMessage(message));
    }
}
