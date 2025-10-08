package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.ReflectionAccessFilterHelper;
import com.google.gson.internal.reflect.ReflectionHelper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final List<ReflectionAccessFilter> reflectionFilters;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor2, FieldNamingStrategy fieldNamingPolicy2, Excluder excluder2, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory2, List<ReflectionAccessFilter> reflectionFilters2) {
        this.constructorConstructor = constructorConstructor2;
        this.fieldNamingPolicy = fieldNamingPolicy2;
        this.excluder = excluder2;
        this.jsonAdapterFactory = jsonAdapterFactory2;
        this.reflectionFilters = reflectionFilters2;
    }

    private boolean includeField(Field f, boolean serialize) {
        return !this.excluder.excludeClass(f.getType(), serialize) && !this.excluder.excludeField(f, serialize);
    }

    private List<String> getFieldNames(Field f) {
        SerializedName annotation = (SerializedName) f.getAnnotation(SerializedName.class);
        if (annotation == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(f));
        }
        String name = annotation.value();
        String[] alternates = annotation.alternate();
        if (alternates.length == 0) {
            return Collections.singletonList(name);
        }
        List<String> fieldNames = new ArrayList<>(alternates.length + 1);
        fieldNames.add(name);
        Collections.addAll(fieldNames, alternates);
        return fieldNames;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();
        if (!Object.class.isAssignableFrom(raw)) {
            return null;
        }
        ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, raw);
        if (filterResult != ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
            boolean blockInaccessible = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
            if (ReflectionHelper.isRecord(raw)) {
                return new RecordAdapter<>(raw, getBoundFields(gson, type, raw, blockInaccessible, true), blockInaccessible);
            }
            TypeToken<T> type2 = type;
            return new FieldReflectionAdapter(this.constructorConstructor.get(type2), getBoundFields(gson, type2, raw, blockInaccessible, false));
        }
        TypeToken<T> typeToken = type;
        throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + raw + ". Register a TypeAdapter for this type or adjust the access filter.");
    }

    /* access modifiers changed from: private */
    public static <M extends AccessibleObject & Member> void checkAccessible(Object object, M member) {
        if (!ReflectionAccessFilterHelper.canAccess(member, Modifier.isStatic(((Member) member).getModifiers()) ? null : object)) {
            throw new JsonIOException(ReflectionHelper.getAccessibleObjectDescription(member, true) + " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type.");
        }
    }

    private BoundField createBoundField(Gson context, Field field, Method accessor, String name, TypeToken<?> fieldType, boolean serialize, boolean deserialize, boolean blockInaccessible) {
        final Gson gson = context;
        final TypeToken<?> typeToken = fieldType;
        final boolean isPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        int modifiers = field.getModifiers();
        final boolean isStaticFinalField = Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
        Field field2 = field;
        JsonAdapter annotation = (JsonAdapter) field2.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> mapped = null;
        if (annotation != null) {
            mapped = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, annotation);
        }
        final boolean jsonAdapterPresent = mapped != null;
        if (mapped == null) {
            mapped = gson.getAdapter(typeToken);
        }
        final TypeAdapter<?> typeAdapter = mapped;
        final Method method = accessor;
        final boolean z = blockInaccessible;
        return new BoundField(name, field2, serialize, deserialize) {
            /* access modifiers changed from: package-private */
            public void write(JsonWriter writer, Object source) throws IOException, IllegalAccessException {
                Object fieldValue;
                TypeAdapter<Object> t;
                if (this.serialized) {
                    if (z) {
                        if (method == null) {
                            ReflectiveTypeAdapterFactory.checkAccessible(source, this.field);
                        } else {
                            ReflectiveTypeAdapterFactory.checkAccessible(source, method);
                        }
                    }
                    if (method != null) {
                        try {
                            fieldValue = method.invoke(source, new Object[0]);
                        } catch (InvocationTargetException e) {
                            throw new JsonIOException("Accessor " + ReflectionHelper.getAccessibleObjectDescription(method, false) + " threw exception", e.getCause());
                        }
                    } else {
                        fieldValue = this.field.get(source);
                    }
                    if (fieldValue != source) {
                        writer.name(this.name);
                        if (jsonAdapterPresent) {
                            t = typeAdapter;
                        } else {
                            t = new TypeAdapterRuntimeTypeWrapper<>(gson, typeAdapter, typeToken.getType());
                        }
                        t.write(writer, fieldValue);
                    }
                }
            }

            /* access modifiers changed from: package-private */
            public void readIntoArray(JsonReader reader, int index, Object[] target) throws IOException, JsonParseException {
                Object fieldValue = typeAdapter.read(reader);
                if (fieldValue != null || !isPrimitive) {
                    target[index] = fieldValue;
                    return;
                }
                throw new JsonParseException("null is not allowed as value for record component '" + this.fieldName + "' of primitive type; at path " + reader.getPath());
            }

            /* access modifiers changed from: package-private */
            public void readIntoField(JsonReader reader, Object target) throws IOException, IllegalAccessException {
                Object fieldValue = typeAdapter.read(reader);
                if (fieldValue != null || !isPrimitive) {
                    if (z) {
                        ReflectiveTypeAdapterFactory.checkAccessible(target, this.field);
                    } else if (isStaticFinalField) {
                        throw new JsonIOException("Cannot set value of 'static final' " + ReflectionHelper.getAccessibleObjectDescription(this.field, false));
                    }
                    this.field.set(target, fieldValue);
                }
            }
        };
    }

    private Map<String, BoundField> getBoundFields(Gson context, TypeToken<?> type, Class<?> raw, boolean blockInaccessible, boolean isRecord) {
        boolean blockInaccessible2;
        TypeToken<?> type2;
        int i;
        int i2;
        Class<?> originalRaw;
        boolean deserialize;
        ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory = this;
        Map<String, BoundField> result = new LinkedHashMap<>();
        if (raw.isInterface()) {
            return result;
        }
        Class<?> originalRaw2 = raw;
        TypeToken<?> type3 = type;
        Class<? super Object> cls = raw;
        boolean blockInaccessible3 = blockInaccessible;
        while (cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            boolean z = true;
            boolean z2 = false;
            if (cls == originalRaw2 || fields.length <= 0) {
                blockInaccessible2 = blockInaccessible3;
            } else {
                ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(reflectiveTypeAdapterFactory.reflectionFilters, cls);
                if (filterResult != ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
                    blockInaccessible2 = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
                } else {
                    throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + cls + " (supertype of " + originalRaw2 + "). Register a TypeAdapter for this type or adjust the access filter.");
                }
            }
            int length = fields.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = i3;
                Field field = fields[i4];
                boolean serialize = reflectiveTypeAdapterFactory.includeField(field, z);
                boolean deserialize2 = reflectiveTypeAdapterFactory.includeField(field, z2);
                if (serialize || deserialize2) {
                    Method accessor = null;
                    if (!isRecord) {
                        deserialize = deserialize2;
                    } else if (Modifier.isStatic(field.getModifiers())) {
                        deserialize = false;
                    } else {
                        accessor = ReflectionHelper.getAccessor(cls, field);
                        if (!blockInaccessible2) {
                            ReflectionHelper.makeAccessible(accessor);
                        }
                        if (accessor.getAnnotation(SerializedName.class) == null || field.getAnnotation(SerializedName.class) != null) {
                            deserialize = deserialize2;
                        } else {
                            throw new JsonIOException("@SerializedName on " + ReflectionHelper.getAccessibleObjectDescription(accessor, z2) + " is not supported");
                        }
                    }
                    if (!blockInaccessible2 && accessor == null) {
                        ReflectionHelper.makeAccessible(field);
                    }
                    Type fieldType = C$Gson$Types.resolve(type3.getType(), cls, field.getGenericType());
                    List<String> fieldNames = reflectiveTypeAdapterFactory.getFieldNames(field);
                    int size = fieldNames.size();
                    originalRaw = originalRaw2;
                    BoundField previous = null;
                    type2 = type3;
                    int i5 = 0;
                    while (i5 < size) {
                        String name = fieldNames.get(i5);
                        if (i5 != 0) {
                            serialize = false;
                        }
                        List<String> fieldNames2 = fieldNames;
                        int i6 = i4;
                        Method accessor2 = accessor;
                        boolean serialize2 = serialize;
                        String name2 = name;
                        int i7 = length;
                        BoundField replaced = result.put(name2, reflectiveTypeAdapterFactory.createBoundField(context, field, accessor2, name2, TypeToken.get(fieldType), serialize2, deserialize, blockInaccessible2));
                        if (previous == null) {
                            previous = replaced;
                        }
                        i5++;
                        reflectiveTypeAdapterFactory = this;
                        serialize = serialize2;
                        length = i7;
                        fieldNames = fieldNames2;
                        accessor = accessor2;
                        i4 = i6;
                    }
                    i2 = length;
                    i = i4;
                    List<String> list = fieldNames;
                    Method method = accessor;
                    if (previous != null) {
                        throw new IllegalArgumentException("Class " + originalRaw.getName() + " declares multiple JSON fields named '" + previous.name + "'; conflict is caused by fields " + ReflectionHelper.fieldToString(previous.field) + " and " + ReflectionHelper.fieldToString(field));
                    }
                } else {
                    i2 = length;
                    i = i4;
                    originalRaw = originalRaw2;
                    type2 = type3;
                }
                i3 = i + 1;
                reflectiveTypeAdapterFactory = this;
                type3 = type2;
                originalRaw2 = originalRaw;
                length = i2;
                z = true;
                z2 = false;
            }
            type3 = TypeToken.get(C$Gson$Types.resolve(type3.getType(), cls, cls.getGenericSuperclass()));
            cls = type3.getRawType();
            reflectiveTypeAdapterFactory = this;
            blockInaccessible3 = blockInaccessible2;
        }
        return result;
    }

    static abstract class BoundField {
        final boolean deserialized;
        final Field field;
        final String fieldName;
        final String name;
        final boolean serialized;

        /* access modifiers changed from: package-private */
        public abstract void readIntoArray(JsonReader jsonReader, int i, Object[] objArr) throws IOException, JsonParseException;

        /* access modifiers changed from: package-private */
        public abstract void readIntoField(JsonReader jsonReader, Object obj) throws IOException, IllegalAccessException;

        /* access modifiers changed from: package-private */
        public abstract void write(JsonWriter jsonWriter, Object obj) throws IOException, IllegalAccessException;

        protected BoundField(String name2, Field field2, boolean serialized2, boolean deserialized2) {
            this.name = name2;
            this.field = field2;
            this.fieldName = field2.getName();
            this.serialized = serialized2;
            this.deserialized = deserialized2;
        }
    }

    public static abstract class Adapter<T, A> extends TypeAdapter<T> {
        final Map<String, BoundField> boundFields;

        /* access modifiers changed from: package-private */
        public abstract A createAccumulator();

        /* access modifiers changed from: package-private */
        public abstract T finalize(A a);

        /* access modifiers changed from: package-private */
        public abstract void readField(A a, JsonReader jsonReader, BoundField boundField) throws IllegalAccessException, IOException;

        Adapter(Map<String, BoundField> boundFields2) {
            this.boundFields = boundFields2;
        }

        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            try {
                for (BoundField boundField : this.boundFields.values()) {
                    boundField.write(out, value);
                }
                out.endObject();
            } catch (IllegalAccessException e) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            }
        }

        public T read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            A accumulator = createAccumulator();
            try {
                in.beginObject();
                while (in.hasNext()) {
                    BoundField field = this.boundFields.get(in.nextName());
                    if (field != null) {
                        if (field.deserialized) {
                            readField(accumulator, in, field);
                        }
                    }
                    in.skipValue();
                }
                in.endObject();
                return finalize(accumulator);
            } catch (IllegalStateException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            }
        }
    }

    private static final class FieldReflectionAdapter<T> extends Adapter<T, T> {
        private final ObjectConstructor<T> constructor;

        FieldReflectionAdapter(ObjectConstructor<T> constructor2, Map<String, BoundField> boundFields) {
            super(boundFields);
            this.constructor = constructor2;
        }

        /* access modifiers changed from: package-private */
        public T createAccumulator() {
            return this.constructor.construct();
        }

        /* access modifiers changed from: package-private */
        public void readField(T accumulator, JsonReader in, BoundField field) throws IllegalAccessException, IOException {
            field.readIntoField(in, accumulator);
        }

        /* access modifiers changed from: package-private */
        public T finalize(T accumulator) {
            return accumulator;
        }
    }

    private static final class RecordAdapter<T> extends Adapter<T, Object[]> {
        static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = primitiveDefaults();
        private final Map<String, Integer> componentIndices = new HashMap();
        private final Constructor<T> constructor;
        private final Object[] constructorArgsDefaults;

        RecordAdapter(Class<T> raw, Map<String, BoundField> boundFields, boolean blockInaccessible) {
            super(boundFields);
            this.constructor = ReflectionHelper.getCanonicalRecordConstructor(raw);
            if (blockInaccessible) {
                ReflectiveTypeAdapterFactory.checkAccessible((Object) null, this.constructor);
            } else {
                ReflectionHelper.makeAccessible(this.constructor);
            }
            String[] componentNames = ReflectionHelper.getRecordComponentNames(raw);
            for (int i = 0; i < componentNames.length; i++) {
                this.componentIndices.put(componentNames[i], Integer.valueOf(i));
            }
            Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            this.constructorArgsDefaults = new Object[parameterTypes.length];
            for (int i2 = 0; i2 < parameterTypes.length; i2++) {
                this.constructorArgsDefaults[i2] = PRIMITIVE_DEFAULTS.get(parameterTypes[i2]);
            }
        }

        private static Map<Class<?>, Object> primitiveDefaults() {
            Map<Class<?>, Object> zeroes = new HashMap<>();
            zeroes.put(Byte.TYPE, (byte) 0);
            zeroes.put(Short.TYPE, (short) 0);
            zeroes.put(Integer.TYPE, 0);
            zeroes.put(Long.TYPE, 0L);
            zeroes.put(Float.TYPE, Float.valueOf(0.0f));
            zeroes.put(Double.TYPE, Double.valueOf(0.0d));
            zeroes.put(Character.TYPE, 0);
            zeroes.put(Boolean.TYPE, false);
            return zeroes;
        }

        /* access modifiers changed from: package-private */
        public Object[] createAccumulator() {
            return (Object[]) this.constructorArgsDefaults.clone();
        }

        /* access modifiers changed from: package-private */
        public void readField(Object[] accumulator, JsonReader in, BoundField field) throws IOException {
            Integer componentIndex = this.componentIndices.get(field.fieldName);
            if (componentIndex != null) {
                field.readIntoArray(in, componentIndex.intValue(), accumulator);
                return;
            }
            throw new IllegalStateException("Could not find the index in the constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' for field with name '" + field.fieldName + "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters.");
        }

        /* access modifiers changed from: package-private */
        public T finalize(Object[] accumulator) {
            try {
                return this.constructor.newInstance(accumulator);
            } catch (IllegalAccessException e) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            } catch (IllegalArgumentException | InstantiationException e2) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(accumulator), e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(accumulator), e3.getCause());
            }
        }
    }
}
