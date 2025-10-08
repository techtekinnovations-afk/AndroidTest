package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Internal;
import java.lang.reflect.Field;

@CheckReturnValue
final class FieldInfo implements Comparable<FieldInfo> {
    private final Field cachedSizeField;
    private final boolean enforceUtf8;
    private final Internal.EnumVerifier enumVerifier;
    private final Field field;
    private final int fieldNumber;
    private final Object mapDefaultEntry;
    private final Class<?> messageClass;
    private final OneofInfo oneof;
    private final Class<?> oneofStoredType;
    private final Field presenceField;
    private final int presenceMask;
    private final boolean required;
    private final FieldType type;

    public static FieldInfo forField(Field field2, int fieldNumber2, FieldType fieldType, boolean enforceUtf82) {
        FieldType fieldType2 = fieldType;
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        Internal.checkNotNull(fieldType2, "fieldType");
        if (fieldType2 != FieldType.MESSAGE_LIST && fieldType2 != FieldType.GROUP_LIST) {
            return new FieldInfo(field2, fieldNumber2, fieldType2, (Class<?>) null, (Field) null, 0, false, enforceUtf82, (OneofInfo) null, (Class<?>) null, (Object) null, (Internal.EnumVerifier) null, (Field) null);
        }
        throw new IllegalStateException("Shouldn't be called for repeated message fields.");
    }

    public static FieldInfo forPackedField(Field field2, int fieldNumber2, FieldType fieldType, Field cachedSizeField2) {
        FieldType fieldType2 = fieldType;
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        Internal.checkNotNull(fieldType2, "fieldType");
        if (fieldType2 != FieldType.MESSAGE_LIST && fieldType2 != FieldType.GROUP_LIST) {
            return new FieldInfo(field2, fieldNumber2, fieldType2, (Class<?>) null, (Field) null, 0, false, false, (OneofInfo) null, (Class<?>) null, (Object) null, (Internal.EnumVerifier) null, cachedSizeField2);
        }
        throw new IllegalStateException("Shouldn't be called for repeated message fields.");
    }

    public static FieldInfo forRepeatedMessageField(Field field2, int fieldNumber2, FieldType fieldType, Class<?> messageClass2) {
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        FieldType fieldType2 = fieldType;
        Internal.checkNotNull(fieldType2, "fieldType");
        Class<?> cls = messageClass2;
        Internal.checkNotNull(cls, "messageClass");
        return new FieldInfo(field2, fieldNumber2, fieldType2, cls, (Field) null, 0, false, false, (OneofInfo) null, (Class<?>) null, (Object) null, (Internal.EnumVerifier) null, (Field) null);
    }

    public static FieldInfo forFieldWithEnumVerifier(Field field2, int fieldNumber2, FieldType fieldType, Internal.EnumVerifier enumVerifier2) {
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        return new FieldInfo(field2, fieldNumber2, fieldType, (Class<?>) null, (Field) null, 0, false, false, (OneofInfo) null, (Class<?>) null, (Object) null, enumVerifier2, (Field) null);
    }

    public static FieldInfo forPackedFieldWithEnumVerifier(Field field2, int fieldNumber2, FieldType fieldType, Internal.EnumVerifier enumVerifier2, Field cachedSizeField2) {
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        return new FieldInfo(field2, fieldNumber2, fieldType, (Class<?>) null, (Field) null, 0, false, false, (OneofInfo) null, (Class<?>) null, (Object) null, enumVerifier2, cachedSizeField2);
    }

    public static FieldInfo forExplicitPresenceField(Field field2, int fieldNumber2, FieldType fieldType, Field presenceField2, int presenceMask2, boolean enforceUtf82, Internal.EnumVerifier enumVerifier2) {
        int i;
        Field field3 = presenceField2;
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        FieldType fieldType2 = fieldType;
        Internal.checkNotNull(fieldType2, "fieldType");
        Internal.checkNotNull(field3, "presenceField");
        if (field3 == null) {
            i = presenceMask2;
        } else if (isExactlyOneBitSet(presenceMask2)) {
            i = presenceMask2;
        } else {
            throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + presenceMask2);
        }
        return new FieldInfo(field2, fieldNumber2, fieldType2, (Class<?>) null, field3, i, false, enforceUtf82, (OneofInfo) null, (Class<?>) null, (Object) null, enumVerifier2, (Field) null);
    }

    public static FieldInfo forOneofMemberField(int fieldNumber2, FieldType fieldType, OneofInfo oneof2, Class<?> oneofStoredType2, boolean enforceUtf82, Internal.EnumVerifier enumVerifier2) {
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(fieldType, "fieldType");
        OneofInfo oneofInfo = oneof2;
        Internal.checkNotNull(oneofInfo, "oneof");
        Class<?> cls = oneofStoredType2;
        Internal.checkNotNull(cls, "oneofStoredType");
        if (fieldType.isScalar()) {
            return new FieldInfo((Field) null, fieldNumber2, fieldType, (Class<?>) null, (Field) null, 0, false, enforceUtf82, oneofInfo, cls, (Object) null, enumVerifier2, (Field) null);
        }
        throw new IllegalArgumentException("Oneof is only supported for scalar fields. Field " + fieldNumber2 + " is of type " + fieldType);
    }

    private static void checkFieldNumber(int fieldNumber2) {
        if (fieldNumber2 <= 0) {
            throw new IllegalArgumentException("fieldNumber must be positive: " + fieldNumber2);
        }
    }

    public static FieldInfo forLegacyRequiredField(Field field2, int fieldNumber2, FieldType fieldType, Field presenceField2, int presenceMask2, boolean enforceUtf82, Internal.EnumVerifier enumVerifier2) {
        int i;
        Field field3 = presenceField2;
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        FieldType fieldType2 = fieldType;
        Internal.checkNotNull(fieldType2, "fieldType");
        Internal.checkNotNull(field3, "presenceField");
        if (field3 == null) {
            i = presenceMask2;
        } else if (isExactlyOneBitSet(presenceMask2)) {
            i = presenceMask2;
        } else {
            throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + presenceMask2);
        }
        return new FieldInfo(field2, fieldNumber2, fieldType2, (Class<?>) null, field3, i, true, enforceUtf82, (OneofInfo) null, (Class<?>) null, (Object) null, enumVerifier2, (Field) null);
    }

    public static FieldInfo forMapField(Field field2, int fieldNumber2, Object mapDefaultEntry2, Internal.EnumVerifier enumVerifier2) {
        Object obj = mapDefaultEntry2;
        Internal.checkNotNull(obj, "mapDefaultEntry");
        checkFieldNumber(fieldNumber2);
        Internal.checkNotNull(field2, "field");
        return new FieldInfo(field2, fieldNumber2, FieldType.MAP, (Class<?>) null, (Field) null, 0, false, true, (OneofInfo) null, (Class<?>) null, obj, enumVerifier2, (Field) null);
    }

    private FieldInfo(Field field2, int fieldNumber2, FieldType type2, Class<?> messageClass2, Field presenceField2, int presenceMask2, boolean required2, boolean enforceUtf82, OneofInfo oneof2, Class<?> oneofStoredType2, Object mapDefaultEntry2, Internal.EnumVerifier enumVerifier2, Field cachedSizeField2) {
        this.field = field2;
        this.type = type2;
        this.messageClass = messageClass2;
        this.fieldNumber = fieldNumber2;
        this.presenceField = presenceField2;
        this.presenceMask = presenceMask2;
        this.required = required2;
        this.enforceUtf8 = enforceUtf82;
        this.oneof = oneof2;
        this.oneofStoredType = oneofStoredType2;
        this.mapDefaultEntry = mapDefaultEntry2;
        this.enumVerifier = enumVerifier2;
        this.cachedSizeField = cachedSizeField2;
    }

    public int getFieldNumber() {
        return this.fieldNumber;
    }

    public Field getField() {
        return this.field;
    }

    public FieldType getType() {
        return this.type;
    }

    public OneofInfo getOneof() {
        return this.oneof;
    }

    public Class<?> getOneofStoredType() {
        return this.oneofStoredType;
    }

    public Internal.EnumVerifier getEnumVerifier() {
        return this.enumVerifier;
    }

    public int compareTo(FieldInfo o) {
        return this.fieldNumber - o.fieldNumber;
    }

    public Class<?> getListElementType() {
        return this.messageClass;
    }

    public Field getPresenceField() {
        return this.presenceField;
    }

    public Object getMapDefaultEntry() {
        return this.mapDefaultEntry;
    }

    public int getPresenceMask() {
        return this.presenceMask;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isEnforceUtf8() {
        return this.enforceUtf8;
    }

    public Field getCachedSizeField() {
        return this.cachedSizeField;
    }

    public Class<?> getMessageFieldClass() {
        switch (this.type) {
            case MESSAGE:
            case GROUP:
                return this.field != null ? this.field.getType() : this.oneofStoredType;
            case MESSAGE_LIST:
            case GROUP_LIST:
                return this.messageClass;
            default:
                return null;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Field cachedSizeField;
        private boolean enforceUtf8;
        private Internal.EnumVerifier enumVerifier;
        private Field field;
        private int fieldNumber;
        private Object mapDefaultEntry;
        private OneofInfo oneof;
        private Class<?> oneofStoredType;
        private Field presenceField;
        private int presenceMask;
        private boolean required;
        private FieldType type;

        private Builder() {
        }

        public Builder withField(Field field2) {
            if (this.oneof == null) {
                this.field = field2;
                return this;
            }
            throw new IllegalStateException("Cannot set field when building a oneof.");
        }

        public Builder withType(FieldType type2) {
            this.type = type2;
            return this;
        }

        public Builder withFieldNumber(int fieldNumber2) {
            this.fieldNumber = fieldNumber2;
            return this;
        }

        public Builder withPresence(Field presenceField2, int presenceMask2) {
            this.presenceField = (Field) Internal.checkNotNull(presenceField2, "presenceField");
            this.presenceMask = presenceMask2;
            return this;
        }

        public Builder withOneof(OneofInfo oneof2, Class<?> oneofStoredType2) {
            if (this.field == null && this.presenceField == null) {
                this.oneof = oneof2;
                this.oneofStoredType = oneofStoredType2;
                return this;
            }
            throw new IllegalStateException("Cannot set oneof when field or presenceField have been provided");
        }

        public Builder withRequired(boolean required2) {
            this.required = required2;
            return this;
        }

        public Builder withMapDefaultEntry(Object mapDefaultEntry2) {
            this.mapDefaultEntry = mapDefaultEntry2;
            return this;
        }

        public Builder withEnforceUtf8(boolean enforceUtf82) {
            this.enforceUtf8 = enforceUtf82;
            return this;
        }

        public Builder withEnumVerifier(Internal.EnumVerifier enumVerifier2) {
            this.enumVerifier = enumVerifier2;
            return this;
        }

        public Builder withCachedSizeField(Field cachedSizeField2) {
            this.cachedSizeField = cachedSizeField2;
            return this;
        }

        public FieldInfo build() {
            if (this.oneof != null) {
                return FieldInfo.forOneofMemberField(this.fieldNumber, this.type, this.oneof, this.oneofStoredType, this.enforceUtf8, this.enumVerifier);
            }
            if (this.mapDefaultEntry != null) {
                return FieldInfo.forMapField(this.field, this.fieldNumber, this.mapDefaultEntry, this.enumVerifier);
            }
            if (this.presenceField != null) {
                if (this.required) {
                    return FieldInfo.forLegacyRequiredField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
                }
                return FieldInfo.forExplicitPresenceField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
            } else if (this.enumVerifier != null) {
                if (this.cachedSizeField == null) {
                    return FieldInfo.forFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier);
                }
                return FieldInfo.forPackedFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier, this.cachedSizeField);
            } else if (this.cachedSizeField == null) {
                return FieldInfo.forField(this.field, this.fieldNumber, this.type, this.enforceUtf8);
            } else {
                return FieldInfo.forPackedField(this.field, this.fieldNumber, this.type, this.cachedSizeField);
            }
        }
    }

    private static boolean isExactlyOneBitSet(int value) {
        return value != 0 && ((value + -1) & value) == 0;
    }
}
