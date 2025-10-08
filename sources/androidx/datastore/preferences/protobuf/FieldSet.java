package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.LazyField;
import androidx.datastore.preferences.protobuf.MessageLite;
import androidx.datastore.preferences.protobuf.WireFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class FieldSet<T extends FieldDescriptorLite<T>> {
    private static final FieldSet<?> DEFAULT_INSTANCE = new FieldSet<>(true);
    /* access modifiers changed from: private */
    public final SmallSortedMap<T, Object> fields;
    /* access modifiers changed from: private */
    public boolean hasLazyField;
    private boolean isImmutable;

    public interface FieldDescriptorLite<T extends FieldDescriptorLite<T>> extends Comparable<T> {
        Internal.EnumLiteMap<?> getEnumType();

        WireFormat.JavaType getLiteJavaType();

        WireFormat.FieldType getLiteType();

        int getNumber();

        MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite);

        boolean isPacked();

        boolean isRepeated();
    }

    private FieldSet() {
        this.fields = SmallSortedMap.newFieldMap();
    }

    private FieldSet(boolean dummy) {
        this(SmallSortedMap.newFieldMap());
        makeImmutable();
    }

    private FieldSet(SmallSortedMap<T, Object> fields2) {
        this.fields = fields2;
        makeImmutable();
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> newFieldSet() {
        return new FieldSet<>();
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> emptySet() {
        return DEFAULT_INSTANCE;
    }

    public static <T extends FieldDescriptorLite<T>> Builder<T> newBuilder() {
        return new Builder<>();
    }

    /* access modifiers changed from: package-private */
    public boolean isEmpty() {
        return this.fields.isEmpty();
    }

    public void makeImmutable() {
        if (!this.isImmutable) {
            int n = this.fields.getNumArrayEntries();
            for (int i = 0; i < n; i++) {
                Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
                if (entry.getValue() instanceof GeneratedMessageLite) {
                    ((GeneratedMessageLite) entry.getValue()).makeImmutable();
                }
            }
            this.fields.makeImmutable();
            this.isImmutable = true;
        }
    }

    public boolean isImmutable() {
        return this.isImmutable;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldSet)) {
            return false;
        }
        return this.fields.equals(((FieldSet) o).fields);
    }

    public int hashCode() {
        return this.fields.hashCode();
    }

    public FieldSet<T> clone() {
        FieldSet<T> clone = newFieldSet();
        int n = this.fields.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
            clone.setField((FieldDescriptorLite) entry.getKey(), entry.getValue());
        }
        for (Map.Entry<T, Object> entry2 : this.fields.getOverflowEntries()) {
            clone.setField((FieldDescriptorLite) entry2.getKey(), entry2.getValue());
        }
        clone.hasLazyField = this.hasLazyField;
        return clone;
    }

    public void clear() {
        this.fields.clear();
        this.hasLazyField = false;
    }

    public Map<T, Object> getAllFields() {
        if (!this.hasLazyField) {
            return this.fields.isImmutable() ? this.fields : Collections.unmodifiableMap(this.fields);
        }
        SmallSortedMap<T, Object> result = cloneAllFieldsMap(this.fields, false, true);
        if (this.fields.isImmutable()) {
            result.makeImmutable();
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static <T extends FieldDescriptorLite<T>> SmallSortedMap<T, Object> cloneAllFieldsMap(SmallSortedMap<T, Object> fields2, boolean copyList, boolean resolveLazyFields) {
        SmallSortedMap<T, Object> result = SmallSortedMap.newFieldMap();
        int n = fields2.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            cloneFieldEntry(result, fields2.getArrayEntryAt(i), copyList, resolveLazyFields);
        }
        for (Map.Entry<T, Object> entry : fields2.getOverflowEntries()) {
            cloneFieldEntry(result, entry, copyList, resolveLazyFields);
        }
        return result;
    }

    private static <T extends FieldDescriptorLite<T>> void cloneFieldEntry(Map<T, Object> map, Map.Entry<T, Object> entry, boolean copyList, boolean resolveLazyFields) {
        T key = (FieldDescriptorLite) entry.getKey();
        Object value = entry.getValue();
        if (resolveLazyFields && (value instanceof LazyField)) {
            map.put(key, ((LazyField) value).getValue());
        } else if (!copyList || !(value instanceof List)) {
            map.put(key, value);
        } else {
            map.put(key, new ArrayList((List) value));
        }
    }

    public Iterator<Map.Entry<T, Object>> iterator() {
        if (isEmpty()) {
            return Collections.emptyIterator();
        }
        if (this.hasLazyField) {
            return new LazyField.LazyIterator(this.fields.entrySet().iterator());
        }
        return this.fields.entrySet().iterator();
    }

    /* access modifiers changed from: package-private */
    public Iterator<Map.Entry<T, Object>> descendingIterator() {
        if (isEmpty()) {
            return Collections.emptyIterator();
        }
        if (this.hasLazyField) {
            return new LazyField.LazyIterator(this.fields.descendingEntrySet().iterator());
        }
        return this.fields.descendingEntrySet().iterator();
    }

    public boolean hasField(T descriptor) {
        if (!descriptor.isRepeated()) {
            return this.fields.get(descriptor) != null;
        }
        throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
    }

    public Object getField(T descriptor) {
        Object o = this.fields.get(descriptor);
        if (o instanceof LazyField) {
            return ((LazyField) o).getValue();
        }
        return o;
    }

    public void setField(T descriptor, Object value) {
        if (!descriptor.isRepeated()) {
            verifyType(descriptor, value);
        } else if (value instanceof List) {
            List<Object> newList = new ArrayList<>();
            newList.addAll((List) value);
            for (Object element : newList) {
                verifyType(descriptor, element);
            }
            value = newList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (value instanceof LazyField) {
            this.hasLazyField = true;
        }
        this.fields.put(descriptor, value);
    }

    public void clearField(T descriptor) {
        this.fields.remove(descriptor);
        if (this.fields.isEmpty()) {
            this.hasLazyField = false;
        }
    }

    public int getRepeatedFieldCount(T descriptor) {
        if (descriptor.isRepeated()) {
            Object value = getField(descriptor);
            if (value == null) {
                return 0;
            }
            return ((List) value).size();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public Object getRepeatedField(T descriptor, int index) {
        if (descriptor.isRepeated()) {
            Object value = getField(descriptor);
            if (value != null) {
                return ((List) value).get(index);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public void setRepeatedField(T descriptor, int index, Object value) {
        if (descriptor.isRepeated()) {
            Object list = getField(descriptor);
            if (list != null) {
                verifyType(descriptor, value);
                ((List) list).set(index, value);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public void addRepeatedField(T descriptor, Object value) {
        List<Object> list;
        if (descriptor.isRepeated()) {
            verifyType(descriptor, value);
            Object existingValue = getField(descriptor);
            if (existingValue == null) {
                list = new ArrayList<>();
                this.fields.put(descriptor, list);
            } else {
                list = (List) existingValue;
            }
            list.add(value);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    private void verifyType(T descriptor, Object value) {
        if (!isValidType(descriptor.getLiteType(), value)) {
            throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new Object[]{Integer.valueOf(descriptor.getNumber()), descriptor.getLiteType().getJavaType(), value.getClass().getName()}));
        }
    }

    /* access modifiers changed from: private */
    public static boolean isValidType(WireFormat.FieldType type, Object value) {
        Internal.checkNotNull(value);
        switch (type.getJavaType()) {
            case INT:
                return value instanceof Integer;
            case LONG:
                return value instanceof Long;
            case FLOAT:
                return value instanceof Float;
            case DOUBLE:
                return value instanceof Double;
            case BOOLEAN:
                return value instanceof Boolean;
            case STRING:
                return value instanceof String;
            case BYTE_STRING:
                if ((value instanceof ByteString) || (value instanceof byte[])) {
                    return true;
                }
                return false;
            case ENUM:
                if ((value instanceof Integer) || (value instanceof Internal.EnumLite)) {
                    return true;
                }
                return false;
            case MESSAGE:
                if ((value instanceof MessageLite) || (value instanceof LazyField)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public boolean isInitialized() {
        int n = this.fields.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            if (!isInitialized(this.fields.getArrayEntryAt(i))) {
                return false;
            }
        }
        for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
            if (!isInitialized(entry)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static <T extends FieldDescriptorLite<T>> boolean isInitialized(Map.Entry<T, Object> entry) {
        T descriptor = (FieldDescriptorLite) entry.getKey();
        if (descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        if (!descriptor.isRepeated()) {
            return isMessageFieldValueInitialized(entry.getValue());
        }
        List<?> list = (List) entry.getValue();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            if (!isMessageFieldValueInitialized(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isMessageFieldValueInitialized(Object value) {
        if (value instanceof MessageLiteOrBuilder) {
            return ((MessageLiteOrBuilder) value).isInitialized();
        }
        if (value instanceof LazyField) {
            return true;
        }
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }

    static int getWireFormatForFieldType(WireFormat.FieldType type, boolean isPacked) {
        if (isPacked) {
            return 2;
        }
        return type.getWireType();
    }

    public void mergeFrom(FieldSet<T> other) {
        int n = other.fields.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            mergeFromField(other.fields.getArrayEntryAt(i));
        }
        for (Map.Entry<T, Object> entry : other.fields.getOverflowEntries()) {
            mergeFromField(entry);
        }
    }

    /* access modifiers changed from: private */
    public static Object cloneIfMutable(Object value) {
        if (!(value instanceof byte[])) {
            return value;
        }
        byte[] bytes = (byte[]) value;
        byte[] copy = new byte[bytes.length];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        return copy;
    }

    private void mergeFromField(Map.Entry<T, Object> entry) {
        T descriptor = (FieldDescriptorLite) entry.getKey();
        Object otherValue = entry.getValue();
        boolean isLazyField = otherValue instanceof LazyField;
        if (descriptor.isRepeated()) {
            if (!isLazyField) {
                Object value = getField(descriptor);
                if (value == null) {
                    value = new ArrayList();
                }
                for (Object element : (List) otherValue) {
                    ((List) value).add(cloneIfMutable(element));
                }
                this.fields.put(descriptor, value);
                return;
            }
            throw new IllegalStateException("Lazy fields can not be repeated");
        } else if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            Object value2 = getField(descriptor);
            if (value2 == null) {
                this.fields.put(descriptor, cloneIfMutable(otherValue));
                if (isLazyField) {
                    this.hasLazyField = true;
                    return;
                }
                return;
            }
            if (otherValue instanceof LazyField) {
                otherValue = ((LazyField) otherValue).getValue();
            }
            this.fields.put(descriptor, descriptor.internalMergeFrom(((MessageLite) value2).toBuilder(), (MessageLite) otherValue).build());
        } else if (!isLazyField) {
            this.fields.put(descriptor, cloneIfMutable(otherValue));
        } else {
            throw new IllegalStateException("Lazy fields must be message-valued");
        }
    }

    public static Object readPrimitiveField(CodedInputStream input, WireFormat.FieldType type, boolean checkUtf8) throws IOException {
        if (checkUtf8) {
            return WireFormat.readPrimitiveField(input, type, WireFormat.Utf8Validation.STRICT);
        }
        return WireFormat.readPrimitiveField(input, type, WireFormat.Utf8Validation.LOOSE);
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        int n = this.fields.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
            writeField((FieldDescriptorLite) entry.getKey(), entry.getValue(), output);
        }
        for (Map.Entry<T, Object> entry2 : this.fields.getOverflowEntries()) {
            writeField((FieldDescriptorLite) entry2.getKey(), entry2.getValue(), output);
        }
    }

    public void writeMessageSetTo(CodedOutputStream output) throws IOException {
        int n = this.fields.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            writeMessageSetTo(this.fields.getArrayEntryAt(i), output);
        }
        for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
            writeMessageSetTo(entry, output);
        }
    }

    private void writeMessageSetTo(Map.Entry<T, Object> entry, CodedOutputStream output) throws IOException {
        T descriptor = (FieldDescriptorLite) entry.getKey();
        if (descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE || descriptor.isRepeated() || descriptor.isPacked()) {
            writeField(descriptor, entry.getValue(), output);
            return;
        }
        Object value = entry.getValue();
        if (value instanceof LazyField) {
            output.writeRawMessageSetExtension(((FieldDescriptorLite) entry.getKey()).getNumber(), ((LazyField) value).toByteString());
            return;
        }
        output.writeMessageSetExtension(((FieldDescriptorLite) entry.getKey()).getNumber(), (MessageLite) value);
    }

    static void writeElement(CodedOutputStream output, WireFormat.FieldType type, int number, Object value) throws IOException {
        if (type == WireFormat.FieldType.GROUP) {
            output.writeGroup(number, (MessageLite) value);
            return;
        }
        output.writeTag(number, getWireFormatForFieldType(type, false));
        writeElementNoTag(output, type, value);
    }

    static void writeElementNoTag(CodedOutputStream output, WireFormat.FieldType type, Object value) throws IOException {
        switch (type) {
            case DOUBLE:
                output.writeDoubleNoTag(((Double) value).doubleValue());
                return;
            case FLOAT:
                output.writeFloatNoTag(((Float) value).floatValue());
                return;
            case INT64:
                output.writeInt64NoTag(((Long) value).longValue());
                return;
            case UINT64:
                output.writeUInt64NoTag(((Long) value).longValue());
                return;
            case INT32:
                output.writeInt32NoTag(((Integer) value).intValue());
                return;
            case FIXED64:
                output.writeFixed64NoTag(((Long) value).longValue());
                return;
            case FIXED32:
                output.writeFixed32NoTag(((Integer) value).intValue());
                return;
            case BOOL:
                output.writeBoolNoTag(((Boolean) value).booleanValue());
                return;
            case GROUP:
                output.writeGroupNoTag((MessageLite) value);
                return;
            case MESSAGE:
                output.writeMessageNoTag((MessageLite) value);
                return;
            case STRING:
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString) value);
                    return;
                } else {
                    output.writeStringNoTag((String) value);
                    return;
                }
            case BYTES:
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString) value);
                    return;
                } else {
                    output.writeByteArrayNoTag((byte[]) value);
                    return;
                }
            case UINT32:
                output.writeUInt32NoTag(((Integer) value).intValue());
                return;
            case SFIXED32:
                output.writeSFixed32NoTag(((Integer) value).intValue());
                return;
            case SFIXED64:
                output.writeSFixed64NoTag(((Long) value).longValue());
                return;
            case SINT32:
                output.writeSInt32NoTag(((Integer) value).intValue());
                return;
            case SINT64:
                output.writeSInt64NoTag(((Long) value).longValue());
                return;
            case ENUM:
                if (value instanceof Internal.EnumLite) {
                    output.writeEnumNoTag(((Internal.EnumLite) value).getNumber());
                    return;
                } else {
                    output.writeEnumNoTag(((Integer) value).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public static void writeField(FieldDescriptorLite<?> descriptor, Object value, CodedOutputStream output) throws IOException {
        WireFormat.FieldType type = descriptor.getLiteType();
        int number = descriptor.getNumber();
        if (descriptor.isRepeated()) {
            List<?> valueList = (List) value;
            int valueListSize = valueList.size();
            if (!descriptor.isPacked()) {
                for (int i = 0; i < valueListSize; i++) {
                    writeElement(output, type, number, valueList.get(i));
                }
            } else if (!valueList.isEmpty()) {
                output.writeTag(number, 2);
                int dataSize = 0;
                for (int i2 = 0; i2 < valueListSize; i2++) {
                    dataSize += computeElementSizeNoTag(type, valueList.get(i2));
                }
                output.writeUInt32NoTag(dataSize);
                for (int i3 = 0; i3 < valueListSize; i3++) {
                    writeElementNoTag(output, type, valueList.get(i3));
                }
            }
        } else if (value instanceof LazyField) {
            writeElement(output, type, number, ((LazyField) value).getValue());
        } else {
            writeElement(output, type, number, value);
        }
    }

    public int getSerializedSize() {
        int size = 0;
        int n = this.fields.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
            size += computeFieldSize((FieldDescriptorLite) entry.getKey(), entry.getValue());
        }
        for (Map.Entry<T, Object> entry2 : this.fields.getOverflowEntries()) {
            size += computeFieldSize((FieldDescriptorLite) entry2.getKey(), entry2.getValue());
        }
        return size;
    }

    public int getMessageSetSerializedSize() {
        int size = 0;
        int n = this.fields.getNumArrayEntries();
        for (int i = 0; i < n; i++) {
            size += getMessageSetSerializedSize(this.fields.getArrayEntryAt(i));
        }
        for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
            size += getMessageSetSerializedSize(entry);
        }
        return size;
    }

    private int getMessageSetSerializedSize(Map.Entry<T, Object> entry) {
        T descriptor = (FieldDescriptorLite) entry.getKey();
        Object value = entry.getValue();
        if (descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE || descriptor.isRepeated() || descriptor.isPacked()) {
            return computeFieldSize(descriptor, value);
        }
        if (value instanceof LazyField) {
            return CodedOutputStream.computeLazyFieldMessageSetExtensionSize(((FieldDescriptorLite) entry.getKey()).getNumber(), (LazyField) value);
        }
        return CodedOutputStream.computeMessageSetExtensionSize(((FieldDescriptorLite) entry.getKey()).getNumber(), (MessageLite) value);
    }

    static int computeElementSize(WireFormat.FieldType type, int number, Object value) {
        int tagSize = CodedOutputStream.computeTagSize(number);
        if (type == WireFormat.FieldType.GROUP) {
            tagSize *= 2;
        }
        return computeElementSizeNoTag(type, value) + tagSize;
    }

    static int computeElementSizeNoTag(WireFormat.FieldType type, Object value) {
        switch (type) {
            case DOUBLE:
                return CodedOutputStream.computeDoubleSizeNoTag(((Double) value).doubleValue());
            case FLOAT:
                return CodedOutputStream.computeFloatSizeNoTag(((Float) value).floatValue());
            case INT64:
                return CodedOutputStream.computeInt64SizeNoTag(((Long) value).longValue());
            case UINT64:
                return CodedOutputStream.computeUInt64SizeNoTag(((Long) value).longValue());
            case INT32:
                return CodedOutputStream.computeInt32SizeNoTag(((Integer) value).intValue());
            case FIXED64:
                return CodedOutputStream.computeFixed64SizeNoTag(((Long) value).longValue());
            case FIXED32:
                return CodedOutputStream.computeFixed32SizeNoTag(((Integer) value).intValue());
            case BOOL:
                return CodedOutputStream.computeBoolSizeNoTag(((Boolean) value).booleanValue());
            case GROUP:
                return CodedOutputStream.computeGroupSizeNoTag((MessageLite) value);
            case MESSAGE:
                if (value instanceof LazyField) {
                    return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField) value);
                }
                return CodedOutputStream.computeMessageSizeNoTag((MessageLite) value);
            case STRING:
                if (value instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString) value);
                }
                return CodedOutputStream.computeStringSizeNoTag((String) value);
            case BYTES:
                if (value instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString) value);
                }
                return CodedOutputStream.computeByteArraySizeNoTag((byte[]) value);
            case UINT32:
                return CodedOutputStream.computeUInt32SizeNoTag(((Integer) value).intValue());
            case SFIXED32:
                return CodedOutputStream.computeSFixed32SizeNoTag(((Integer) value).intValue());
            case SFIXED64:
                return CodedOutputStream.computeSFixed64SizeNoTag(((Long) value).longValue());
            case SINT32:
                return CodedOutputStream.computeSInt32SizeNoTag(((Integer) value).intValue());
            case SINT64:
                return CodedOutputStream.computeSInt64SizeNoTag(((Long) value).longValue());
            case ENUM:
                if (value instanceof Internal.EnumLite) {
                    return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite) value).getNumber());
                }
                return CodedOutputStream.computeEnumSizeNoTag(((Integer) value).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int computeFieldSize(FieldDescriptorLite<?> descriptor, Object value) {
        WireFormat.FieldType type = descriptor.getLiteType();
        int number = descriptor.getNumber();
        if (!descriptor.isRepeated()) {
            return computeElementSize(type, number, value);
        }
        List<?> valueList = (List) value;
        int valueListSize = valueList.size();
        if (!descriptor.isPacked()) {
            int size = 0;
            for (int i = 0; i < valueListSize; i++) {
                size += computeElementSize(type, number, valueList.get(i));
            }
            return size;
        } else if (valueList.isEmpty()) {
            return 0;
        } else {
            int dataSize = 0;
            for (int i2 = 0; i2 < valueListSize; i2++) {
                dataSize += computeElementSizeNoTag(type, valueList.get(i2));
            }
            return CodedOutputStream.computeTagSize(number) + dataSize + CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        }
    }

    static final class Builder<T extends FieldDescriptorLite<T>> {
        private SmallSortedMap<T, Object> fields;
        private boolean hasLazyField;
        private boolean hasNestedBuilders;
        private boolean isMutable;

        private Builder() {
            this(SmallSortedMap.newFieldMap());
        }

        private Builder(SmallSortedMap<T, Object> fields2) {
            this.fields = fields2;
            this.isMutable = true;
        }

        public FieldSet<T> build() {
            return buildImpl(false);
        }

        public FieldSet<T> buildPartial() {
            return buildImpl(true);
        }

        private FieldSet<T> buildImpl(boolean partial) {
            if (this.fields.isEmpty()) {
                return FieldSet.emptySet();
            }
            this.isMutable = false;
            SmallSortedMap<T, Object> fieldsForBuild = this.fields;
            if (this.hasNestedBuilders) {
                fieldsForBuild = FieldSet.cloneAllFieldsMap(this.fields, false, false);
                replaceBuilders(fieldsForBuild, partial);
            }
            FieldSet<T> fieldSet = new FieldSet<>(fieldsForBuild);
            boolean unused = fieldSet.hasLazyField = this.hasLazyField;
            return fieldSet;
        }

        private static <T extends FieldDescriptorLite<T>> void replaceBuilders(SmallSortedMap<T, Object> fieldMap, boolean partial) {
            int n = fieldMap.getNumArrayEntries();
            for (int i = 0; i < n; i++) {
                replaceBuilders(fieldMap.getArrayEntryAt(i), partial);
            }
            for (Map.Entry<T, Object> entry : fieldMap.getOverflowEntries()) {
                replaceBuilders(entry, partial);
            }
        }

        private static <T extends FieldDescriptorLite<T>> void replaceBuilders(Map.Entry<T, Object> entry, boolean partial) {
            entry.setValue(replaceBuilders((FieldDescriptorLite) entry.getKey(), entry.getValue(), partial));
        }

        private static <T extends FieldDescriptorLite<T>> Object replaceBuilders(T descriptor, Object value, boolean partial) {
            if (value == null || descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE) {
                return value;
            }
            if (!descriptor.isRepeated()) {
                return replaceBuilder(value, partial);
            }
            if (value instanceof List) {
                List<Object> list = (List) value;
                for (int i = 0; i < list.size(); i++) {
                    Object oldElement = list.get(i);
                    Object newElement = replaceBuilder(oldElement, partial);
                    if (newElement != oldElement) {
                        if (list == value) {
                            list = new ArrayList<>(list);
                        }
                        list.set(i, newElement);
                    }
                }
                return list;
            }
            throw new IllegalStateException("Repeated field should contains a List but actually contains type: " + value.getClass());
        }

        private static Object replaceBuilder(Object value, boolean partial) {
            if (!(value instanceof MessageLite.Builder)) {
                return value;
            }
            MessageLite.Builder builder = (MessageLite.Builder) value;
            if (partial) {
                return builder.buildPartial();
            }
            return builder.build();
        }

        public static <T extends FieldDescriptorLite<T>> Builder<T> fromFieldSet(FieldSet<T> fieldSet) {
            Builder<T> builder = new Builder<>(FieldSet.cloneAllFieldsMap(fieldSet.fields, true, false));
            builder.hasLazyField = fieldSet.hasLazyField;
            return builder;
        }

        public Map<T, Object> getAllFields() {
            if (!this.hasLazyField) {
                return this.fields.isImmutable() ? this.fields : Collections.unmodifiableMap(this.fields);
            }
            SmallSortedMap<T, Object> result = FieldSet.cloneAllFieldsMap(this.fields, false, true);
            if (this.fields.isImmutable()) {
                result.makeImmutable();
            } else {
                replaceBuilders(result, true);
            }
            return result;
        }

        public boolean hasField(T descriptor) {
            if (!descriptor.isRepeated()) {
                return this.fields.get(descriptor) != null;
            }
            throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
        }

        public Object getField(T descriptor) {
            return replaceBuilders(descriptor, getFieldAllowBuilders(descriptor), true);
        }

        /* access modifiers changed from: package-private */
        public Object getFieldAllowBuilders(T descriptor) {
            Object o = this.fields.get(descriptor);
            if (o instanceof LazyField) {
                return ((LazyField) o).getValue();
            }
            return o;
        }

        private void ensureIsMutable() {
            if (!this.isMutable) {
                this.fields = FieldSet.cloneAllFieldsMap(this.fields, true, false);
                this.isMutable = true;
            }
        }

        public void setField(T descriptor, Object value) {
            ensureIsMutable();
            boolean z = false;
            if (!descriptor.isRepeated()) {
                verifyType(descriptor, value);
            } else if (value instanceof List) {
                List<Object> newList = new ArrayList<>((List) value);
                int newListSize = newList.size();
                for (int i = 0; i < newListSize; i++) {
                    Object element = newList.get(i);
                    verifyType(descriptor, element);
                    this.hasNestedBuilders = this.hasNestedBuilders || (element instanceof MessageLite.Builder);
                }
                value = newList;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            if (value instanceof LazyField) {
                this.hasLazyField = true;
            }
            if (this.hasNestedBuilders || (value instanceof MessageLite.Builder)) {
                z = true;
            }
            this.hasNestedBuilders = z;
            this.fields.put(descriptor, value);
        }

        public void clearField(T descriptor) {
            ensureIsMutable();
            this.fields.remove(descriptor);
            if (this.fields.isEmpty()) {
                this.hasLazyField = false;
            }
        }

        public int getRepeatedFieldCount(T descriptor) {
            if (descriptor.isRepeated()) {
                Object value = getFieldAllowBuilders(descriptor);
                if (value == null) {
                    return 0;
                }
                return ((List) value).size();
            }
            throw new IllegalArgumentException("getRepeatedFieldCount() can only be called on repeated fields.");
        }

        public Object getRepeatedField(T descriptor, int index) {
            if (this.hasNestedBuilders) {
                ensureIsMutable();
            }
            return replaceBuilder(getRepeatedFieldAllowBuilders(descriptor, index), true);
        }

        /* access modifiers changed from: package-private */
        public Object getRepeatedFieldAllowBuilders(T descriptor, int index) {
            if (descriptor.isRepeated()) {
                Object value = getFieldAllowBuilders(descriptor);
                if (value != null) {
                    return ((List) value).get(index);
                }
                throw new IndexOutOfBoundsException();
            }
            throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }

        public void setRepeatedField(T descriptor, int index, Object value) {
            ensureIsMutable();
            if (descriptor.isRepeated()) {
                this.hasNestedBuilders = this.hasNestedBuilders || (value instanceof MessageLite.Builder);
                Object list = getFieldAllowBuilders(descriptor);
                if (list != null) {
                    verifyType(descriptor, value);
                    ((List) list).set(index, value);
                    return;
                }
                throw new IndexOutOfBoundsException();
            }
            throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }

        public void addRepeatedField(T descriptor, Object value) {
            List<Object> list;
            ensureIsMutable();
            if (descriptor.isRepeated()) {
                this.hasNestedBuilders = this.hasNestedBuilders || (value instanceof MessageLite.Builder);
                verifyType(descriptor, value);
                Object existingValue = getFieldAllowBuilders(descriptor);
                if (existingValue == null) {
                    list = new ArrayList<>();
                    this.fields.put(descriptor, list);
                } else {
                    list = (List) existingValue;
                }
                list.add(value);
                return;
            }
            throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
        }

        private void verifyType(T descriptor, Object value) {
            if (FieldSet.isValidType(descriptor.getLiteType(), value)) {
                return;
            }
            if (descriptor.getLiteType().getJavaType() != WireFormat.JavaType.MESSAGE || !(value instanceof MessageLite.Builder)) {
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new Object[]{Integer.valueOf(descriptor.getNumber()), descriptor.getLiteType().getJavaType(), value.getClass().getName()}));
            }
        }

        public boolean isInitialized() {
            int n = this.fields.getNumArrayEntries();
            for (int i = 0; i < n; i++) {
                if (!FieldSet.isInitialized(this.fields.getArrayEntryAt(i))) {
                    return false;
                }
            }
            for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
                if (!FieldSet.isInitialized(entry)) {
                    return false;
                }
            }
            return true;
        }

        public void mergeFrom(FieldSet<T> other) {
            ensureIsMutable();
            int n = other.fields.getNumArrayEntries();
            for (int i = 0; i < n; i++) {
                mergeFromField(other.fields.getArrayEntryAt(i));
            }
            for (Map.Entry<T, Object> entry : other.fields.getOverflowEntries()) {
                mergeFromField(entry);
            }
        }

        private void mergeFromField(Map.Entry<T, Object> entry) {
            T descriptor = (FieldDescriptorLite) entry.getKey();
            Object otherValue = entry.getValue();
            boolean isLazyField = otherValue instanceof LazyField;
            if (descriptor.isRepeated()) {
                if (!isLazyField) {
                    List<Object> value = (List) getFieldAllowBuilders(descriptor);
                    List<?> otherList = (List) otherValue;
                    int otherListSize = otherList.size();
                    if (value == null) {
                        value = new ArrayList<>(otherListSize);
                        this.fields.put(descriptor, value);
                    }
                    for (int i = 0; i < otherListSize; i++) {
                        value.add(FieldSet.cloneIfMutable(otherList.get(i)));
                    }
                    return;
                }
                throw new IllegalStateException("Lazy fields can not be repeated");
            } else if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
                Object value2 = getFieldAllowBuilders(descriptor);
                if (value2 == null) {
                    this.fields.put(descriptor, FieldSet.cloneIfMutable(otherValue));
                    if (isLazyField) {
                        this.hasLazyField = true;
                        return;
                    }
                    return;
                }
                if (otherValue instanceof LazyField) {
                    otherValue = ((LazyField) otherValue).getValue();
                }
                if (value2 instanceof MessageLite.Builder) {
                    descriptor.internalMergeFrom((MessageLite.Builder) value2, (MessageLite) otherValue);
                    return;
                }
                this.fields.put(descriptor, descriptor.internalMergeFrom(((MessageLite) value2).toBuilder(), (MessageLite) otherValue).build());
            } else if (!isLazyField) {
                this.fields.put(descriptor, FieldSet.cloneIfMutable(otherValue));
            } else {
                throw new IllegalStateException("Lazy fields must be message-valued");
            }
        }
    }
}
