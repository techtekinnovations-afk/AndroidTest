package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MessageLite;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CheckReturnValue
final class ExtensionSchemaLite extends ExtensionSchema<GeneratedMessageLite.ExtensionDescriptor> {
    ExtensionSchemaLite() {
    }

    /* access modifiers changed from: package-private */
    public boolean hasExtensions(MessageLite prototype) {
        return prototype instanceof GeneratedMessageLite.ExtendableMessage;
    }

    /* access modifiers changed from: package-private */
    public FieldSet<GeneratedMessageLite.ExtensionDescriptor> getExtensions(Object message) {
        return ((GeneratedMessageLite.ExtendableMessage) message).extensions;
    }

    /* access modifiers changed from: package-private */
    public void setExtensions(Object message, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) {
        ((GeneratedMessageLite.ExtendableMessage) message).extensions = extensions;
    }

    /* access modifiers changed from: package-private */
    public FieldSet<GeneratedMessageLite.ExtensionDescriptor> getMutableExtensions(Object message) {
        return ((GeneratedMessageLite.ExtendableMessage) message).ensureExtensionsAreMutable();
    }

    /* access modifiers changed from: package-private */
    public void makeImmutable(Object message) {
        getExtensions(message).makeImmutable();
    }

    /* access modifiers changed from: package-private */
    public <UT, UB> UB parseExtension(Object containerMessage, Reader reader, Object extensionObject, ExtensionRegistryLite extensionRegistry, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema) throws IOException {
        UB unknownFields2;
        ArrayList arrayList;
        GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension) extensionObject;
        int fieldNumber = extension.getNumber();
        if (!extension.descriptor.isRepeated() || !extension.descriptor.isPacked()) {
            Object containerMessage2 = containerMessage;
            UB unknownFields3 = unknownFields;
            UnknownFieldSchema<UT, UB> unknownFieldSchema2 = unknownFieldSchema;
            Object value = null;
            if (extension.getLiteType() != WireFormat.FieldType.ENUM) {
                switch (extension.getLiteType()) {
                    case DOUBLE:
                        value = Double.valueOf(reader.readDouble());
                        break;
                    case FLOAT:
                        value = Float.valueOf(reader.readFloat());
                        break;
                    case INT64:
                        value = Long.valueOf(reader.readInt64());
                        break;
                    case UINT64:
                        value = Long.valueOf(reader.readUInt64());
                        break;
                    case INT32:
                        value = Integer.valueOf(reader.readInt32());
                        break;
                    case FIXED64:
                        value = Long.valueOf(reader.readFixed64());
                        break;
                    case FIXED32:
                        value = Integer.valueOf(reader.readFixed32());
                        break;
                    case BOOL:
                        value = Boolean.valueOf(reader.readBool());
                        break;
                    case UINT32:
                        value = Integer.valueOf(reader.readUInt32());
                        break;
                    case SFIXED32:
                        value = Integer.valueOf(reader.readSFixed32());
                        break;
                    case SFIXED64:
                        value = Long.valueOf(reader.readSFixed64());
                        break;
                    case SINT32:
                        value = Integer.valueOf(reader.readSInt32());
                        break;
                    case SINT64:
                        value = Long.valueOf(reader.readSInt64());
                        break;
                    case ENUM:
                        throw new IllegalStateException("Shouldn't reach here.");
                    case BYTES:
                        value = reader.readBytes();
                        break;
                    case STRING:
                        value = reader.readString();
                        break;
                    case GROUP:
                        if (!extension.isRepeated()) {
                            Object oldValue = extensions.getField(extension.descriptor);
                            if (oldValue instanceof GeneratedMessageLite) {
                                Schema extSchema = Protobuf.getInstance().schemaFor(oldValue);
                                if (!((GeneratedMessageLite) oldValue).isMutable()) {
                                    Object newValue = extSchema.newInstance();
                                    extSchema.mergeFrom(newValue, oldValue);
                                    extensions.setField(extension.descriptor, newValue);
                                    oldValue = newValue;
                                }
                                reader.mergeGroupField(oldValue, extSchema, extensionRegistry);
                                return unknownFields3;
                            }
                        }
                        value = reader.readGroup(extension.getMessageDefaultInstance().getClass(), extensionRegistry);
                        break;
                    case MESSAGE:
                        if (!extension.isRepeated()) {
                            Object oldValue2 = extensions.getField(extension.descriptor);
                            if (oldValue2 instanceof GeneratedMessageLite) {
                                Schema extSchema2 = Protobuf.getInstance().schemaFor(oldValue2);
                                if (!((GeneratedMessageLite) oldValue2).isMutable()) {
                                    Object newValue2 = extSchema2.newInstance();
                                    extSchema2.mergeFrom(newValue2, oldValue2);
                                    extensions.setField(extension.descriptor, newValue2);
                                    oldValue2 = newValue2;
                                }
                                reader.mergeMessageField(oldValue2, extSchema2, extensionRegistry);
                                return unknownFields3;
                            }
                        }
                        value = reader.readMessage(extension.getMessageDefaultInstance().getClass(), extensionRegistry);
                        break;
                }
            } else {
                int number = reader.readInt32();
                if (extension.descriptor.getEnumType().findValueByNumber(number) == null) {
                    return SchemaUtil.storeUnknownEnum(containerMessage2, fieldNumber, number, unknownFields3, unknownFieldSchema2);
                }
                value = Integer.valueOf(number);
            }
            if (extension.isRepeated()) {
                extensions.addRepeatedField(extension.descriptor, value);
            } else {
                switch (extension.getLiteType()) {
                    case GROUP:
                    case MESSAGE:
                        Object oldValue3 = extensions.getField(extension.descriptor);
                        if (oldValue3 != null) {
                            value = Internal.mergeMessage(oldValue3, value);
                            break;
                        }
                        break;
                }
                extensions.setField(extension.descriptor, value);
            }
            return unknownFields3;
        }
        switch (extension.getLiteType()) {
            case DOUBLE:
                UB unknownFields4 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema3 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readDoubleList(arrayList);
                ArrayList arrayList2 = arrayList;
                unknownFields2 = unknownFields4;
                break;
            case FLOAT:
                UB unknownFields5 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema4 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readFloatList(arrayList);
                ArrayList arrayList3 = arrayList;
                unknownFields2 = unknownFields5;
                break;
            case INT64:
                UB unknownFields6 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema5 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readInt64List(arrayList);
                ArrayList arrayList4 = arrayList;
                unknownFields2 = unknownFields6;
                break;
            case UINT64:
                UB unknownFields7 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema6 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readUInt64List(arrayList);
                ArrayList arrayList5 = arrayList;
                unknownFields2 = unknownFields7;
                break;
            case INT32:
                UB unknownFields8 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema7 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readInt32List(arrayList);
                ArrayList arrayList6 = arrayList;
                unknownFields2 = unknownFields8;
                break;
            case FIXED64:
                UB unknownFields9 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema8 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readFixed64List(arrayList);
                ArrayList arrayList7 = arrayList;
                unknownFields2 = unknownFields9;
                break;
            case FIXED32:
                UB unknownFields10 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema9 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readFixed32List(arrayList);
                ArrayList arrayList8 = arrayList;
                unknownFields2 = unknownFields10;
                break;
            case BOOL:
                UB unknownFields11 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema10 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readBoolList(arrayList);
                ArrayList arrayList9 = arrayList;
                unknownFields2 = unknownFields11;
                break;
            case UINT32:
                UB unknownFields12 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema11 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readUInt32List(arrayList);
                ArrayList arrayList10 = arrayList;
                unknownFields2 = unknownFields12;
                break;
            case SFIXED32:
                UB unknownFields13 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema12 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readSFixed32List(arrayList);
                ArrayList arrayList11 = arrayList;
                unknownFields2 = unknownFields13;
                break;
            case SFIXED64:
                UB unknownFields14 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema13 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readSFixed64List(arrayList);
                ArrayList arrayList12 = arrayList;
                unknownFields2 = unknownFields14;
                break;
            case SINT32:
                UB unknownFields15 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema14 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readSInt32List(arrayList);
                ArrayList arrayList13 = arrayList;
                unknownFields2 = unknownFields15;
                break;
            case SINT64:
                UB unknownFields16 = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema15 = unknownFieldSchema;
                arrayList = new ArrayList();
                reader.readSInt64List(arrayList);
                ArrayList arrayList14 = arrayList;
                unknownFields2 = unknownFields16;
                break;
            case ENUM:
                ArrayList arrayList15 = new ArrayList();
                reader.readEnumList(arrayList15);
                unknownFields2 = SchemaUtil.filterUnknownEnumList(containerMessage, fieldNumber, (List<Integer>) arrayList15, extension.descriptor.getEnumType(), unknownFields, unknownFieldSchema);
                arrayList = arrayList15;
                break;
            default:
                Object obj = containerMessage;
                UB ub = unknownFields;
                UnknownFieldSchema<UT, UB> unknownFieldSchema16 = unknownFieldSchema;
                throw new IllegalStateException("Type cannot be packed: " + extension.descriptor.getLiteType());
        }
        extensions.setField(extension.descriptor, arrayList);
        return unknownFields2;
    }

    /* access modifiers changed from: package-private */
    public int extensionNumber(Map.Entry<?, ?> extension) {
        return ((GeneratedMessageLite.ExtensionDescriptor) extension.getKey()).getNumber();
    }

    /* access modifiers changed from: package-private */
    public void serializeExtension(Writer writer, Map.Entry<?, ?> extension) throws IOException {
        GeneratedMessageLite.ExtensionDescriptor descriptor = (GeneratedMessageLite.ExtensionDescriptor) extension.getKey();
        if (descriptor.isRepeated()) {
            switch (descriptor.getLiteType()) {
                case DOUBLE:
                    SchemaUtil.writeDoubleList(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case FLOAT:
                    SchemaUtil.writeFloatList(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case INT64:
                    SchemaUtil.writeInt64List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case UINT64:
                    SchemaUtil.writeUInt64List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case INT32:
                    SchemaUtil.writeInt32List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case FIXED64:
                    SchemaUtil.writeFixed64List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case FIXED32:
                    SchemaUtil.writeFixed32List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case BOOL:
                    SchemaUtil.writeBoolList(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case UINT32:
                    SchemaUtil.writeUInt32List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case SFIXED32:
                    SchemaUtil.writeSFixed32List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case SFIXED64:
                    SchemaUtil.writeSFixed64List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case SINT32:
                    SchemaUtil.writeSInt32List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case SINT64:
                    SchemaUtil.writeSInt64List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case ENUM:
                    SchemaUtil.writeInt32List(descriptor.getNumber(), (List) extension.getValue(), writer, descriptor.isPacked());
                    return;
                case BYTES:
                    SchemaUtil.writeBytesList(descriptor.getNumber(), (List) extension.getValue(), writer);
                    return;
                case STRING:
                    SchemaUtil.writeStringList(descriptor.getNumber(), (List) extension.getValue(), writer);
                    return;
                case GROUP:
                    List<?> data = (List) extension.getValue();
                    if (data != null && !data.isEmpty()) {
                        SchemaUtil.writeGroupList(descriptor.getNumber(), (List) extension.getValue(), writer, Protobuf.getInstance().schemaFor(data.get(0).getClass()));
                        return;
                    }
                    return;
                case MESSAGE:
                    List<?> data2 = (List) extension.getValue();
                    if (data2 != null && !data2.isEmpty()) {
                        SchemaUtil.writeMessageList(descriptor.getNumber(), (List) extension.getValue(), writer, Protobuf.getInstance().schemaFor(data2.get(0).getClass()));
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else {
            switch (descriptor.getLiteType()) {
                case DOUBLE:
                    writer.writeDouble(descriptor.getNumber(), ((Double) extension.getValue()).doubleValue());
                    return;
                case FLOAT:
                    writer.writeFloat(descriptor.getNumber(), ((Float) extension.getValue()).floatValue());
                    return;
                case INT64:
                    writer.writeInt64(descriptor.getNumber(), ((Long) extension.getValue()).longValue());
                    return;
                case UINT64:
                    writer.writeUInt64(descriptor.getNumber(), ((Long) extension.getValue()).longValue());
                    return;
                case INT32:
                    writer.writeInt32(descriptor.getNumber(), ((Integer) extension.getValue()).intValue());
                    return;
                case FIXED64:
                    writer.writeFixed64(descriptor.getNumber(), ((Long) extension.getValue()).longValue());
                    return;
                case FIXED32:
                    writer.writeFixed32(descriptor.getNumber(), ((Integer) extension.getValue()).intValue());
                    return;
                case BOOL:
                    writer.writeBool(descriptor.getNumber(), ((Boolean) extension.getValue()).booleanValue());
                    return;
                case UINT32:
                    writer.writeUInt32(descriptor.getNumber(), ((Integer) extension.getValue()).intValue());
                    return;
                case SFIXED32:
                    writer.writeSFixed32(descriptor.getNumber(), ((Integer) extension.getValue()).intValue());
                    return;
                case SFIXED64:
                    writer.writeSFixed64(descriptor.getNumber(), ((Long) extension.getValue()).longValue());
                    return;
                case SINT32:
                    writer.writeSInt32(descriptor.getNumber(), ((Integer) extension.getValue()).intValue());
                    return;
                case SINT64:
                    writer.writeSInt64(descriptor.getNumber(), ((Long) extension.getValue()).longValue());
                    return;
                case ENUM:
                    writer.writeInt32(descriptor.getNumber(), ((Integer) extension.getValue()).intValue());
                    return;
                case BYTES:
                    writer.writeBytes(descriptor.getNumber(), (ByteString) extension.getValue());
                    return;
                case STRING:
                    writer.writeString(descriptor.getNumber(), (String) extension.getValue());
                    return;
                case GROUP:
                    writer.writeGroup(descriptor.getNumber(), extension.getValue(), Protobuf.getInstance().schemaFor(extension.getValue().getClass()));
                    return;
                case MESSAGE:
                    writer.writeMessage(descriptor.getNumber(), extension.getValue(), Protobuf.getInstance().schemaFor(extension.getValue().getClass()));
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Object findExtensionByNumber(ExtensionRegistryLite extensionRegistry, MessageLite defaultInstance, int number) {
        return extensionRegistry.findLiteExtensionByNumber(defaultInstance, number);
    }

    /* access modifiers changed from: package-private */
    public void parseLengthPrefixedMessageSetItem(Reader reader, Object extensionObject, ExtensionRegistryLite extensionRegistry, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) throws IOException {
        GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension) extensionObject;
        extensions.setField(extension.descriptor, reader.readMessage(extension.getMessageDefaultInstance().getClass(), extensionRegistry));
    }

    /* access modifiers changed from: package-private */
    public void parseMessageSetItem(ByteString data, Object extensionObject, ExtensionRegistryLite extensionRegistry, FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions) throws IOException {
        GeneratedMessageLite.GeneratedExtension<?, ?> extension = (GeneratedMessageLite.GeneratedExtension) extensionObject;
        MessageLite.Builder builder = extension.getMessageDefaultInstance().newBuilderForType();
        CodedInputStream input = data.newCodedInput();
        builder.mergeFrom(input, extensionRegistry);
        extensions.setField(extension.descriptor, builder.buildPartial());
        input.checkLastTagWas(0);
    }
}
