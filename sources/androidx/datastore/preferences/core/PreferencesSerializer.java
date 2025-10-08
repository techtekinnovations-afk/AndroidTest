package androidx.datastore.preferences.core;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.okio.OkioSerializer;
import androidx.datastore.preferences.PreferencesMapCompat;
import androidx.datastore.preferences.PreferencesProto;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.protobuf.ByteString;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import com.google.firebase.firestore.model.Values;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.BufferedSink;
import okio.BufferedSource;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u0011H\u0002J\u0016\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H@¢\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H@¢\u0006\u0002\u0010\u001aR\u0014\u0010\u0004\u001a\u00020\u00028VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Landroidx/datastore/preferences/core/PreferencesSerializer;", "Landroidx/datastore/core/okio/OkioSerializer;", "Landroidx/datastore/preferences/core/Preferences;", "()V", "defaultValue", "getDefaultValue", "()Landroidx/datastore/preferences/core/Preferences;", "fileExtension", "", "addProtoEntryToPreferences", "", "name", "value", "Landroidx/datastore/preferences/PreferencesProto$Value;", "mutablePreferences", "Landroidx/datastore/preferences/core/MutablePreferences;", "getValueProto", "", "readFrom", "source", "Lokio/BufferedSource;", "(Lokio/BufferedSource;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeTo", "t", "sink", "Lokio/BufferedSink;", "(Landroidx/datastore/preferences/core/Preferences;Lokio/BufferedSink;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-preferences-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: PreferencesSerializer.jvmAndroid.kt */
public final class PreferencesSerializer implements OkioSerializer<Preferences> {
    public static final PreferencesSerializer INSTANCE = new PreferencesSerializer();
    public static final String fileExtension = "preferences_pb";

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* compiled from: PreferencesSerializer.jvmAndroid.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PreferencesProto.Value.ValueCase.values().length];
            try {
                iArr[PreferencesProto.Value.ValueCase.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.INTEGER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.LONG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.STRING_SET.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.BYTES.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[PreferencesProto.Value.ValueCase.VALUE_NOT_SET.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private PreferencesSerializer() {
    }

    public Preferences getDefaultValue() {
        return PreferencesFactory.createEmpty();
    }

    public Object readFrom(BufferedSource source, Continuation<? super Preferences> $completion) throws IOException, CorruptionException {
        PreferencesProto.PreferenceMap preferencesProto = PreferencesMapCompat.Companion.readFrom(source.inputStream());
        MutablePreferences mutablePreferences = PreferencesFactory.createMutable(new Preferences.Pair[0]);
        Map $this$forEach$iv = preferencesProto.getPreferencesMap();
        Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "preferencesProto.preferencesMap");
        for (Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            String name = (String) element$iv.getKey();
            PreferencesProto.Value value = (PreferencesProto.Value) element$iv.getValue();
            PreferencesSerializer preferencesSerializer = INSTANCE;
            Intrinsics.checkNotNullExpressionValue(name, "name");
            Intrinsics.checkNotNullExpressionValue(value, Values.VECTOR_MAP_VECTORS_KEY);
            preferencesSerializer.addProtoEntryToPreferences(name, value, mutablePreferences);
        }
        return mutablePreferences.toPreferences();
    }

    public Object writeTo(Preferences t, BufferedSink sink, Continuation<? super Unit> $completion) throws IOException, CorruptionException {
        Map preferences = t.asMap();
        PreferencesProto.PreferenceMap.Builder protoBuilder = PreferencesProto.PreferenceMap.newBuilder();
        for (Map.Entry next : preferences.entrySet()) {
            protoBuilder.putPreferences(((Preferences.Key) next.getKey()).getName(), getValueProto(next.getValue()));
        }
        ((PreferencesProto.PreferenceMap) protoBuilder.build()).writeTo(sink.outputStream());
        return Unit.INSTANCE;
    }

    private final PreferencesProto.Value getValueProto(Object value) {
        if (value instanceof Boolean) {
            GeneratedMessageLite build = PreferencesProto.Value.newBuilder().setBoolean(((Boolean) value).booleanValue()).build();
            Intrinsics.checkNotNullExpressionValue(build, "newBuilder().setBoolean(value).build()");
            return (PreferencesProto.Value) build;
        } else if (value instanceof Float) {
            GeneratedMessageLite build2 = PreferencesProto.Value.newBuilder().setFloat(((Number) value).floatValue()).build();
            Intrinsics.checkNotNullExpressionValue(build2, "newBuilder().setFloat(value).build()");
            return (PreferencesProto.Value) build2;
        } else if (value instanceof Double) {
            GeneratedMessageLite build3 = PreferencesProto.Value.newBuilder().setDouble(((Number) value).doubleValue()).build();
            Intrinsics.checkNotNullExpressionValue(build3, "newBuilder().setDouble(value).build()");
            return (PreferencesProto.Value) build3;
        } else if (value instanceof Integer) {
            GeneratedMessageLite build4 = PreferencesProto.Value.newBuilder().setInteger(((Number) value).intValue()).build();
            Intrinsics.checkNotNullExpressionValue(build4, "newBuilder().setInteger(value).build()");
            return (PreferencesProto.Value) build4;
        } else if (value instanceof Long) {
            GeneratedMessageLite build5 = PreferencesProto.Value.newBuilder().setLong(((Number) value).longValue()).build();
            Intrinsics.checkNotNullExpressionValue(build5, "newBuilder().setLong(value).build()");
            return (PreferencesProto.Value) build5;
        } else if (value instanceof String) {
            GeneratedMessageLite build6 = PreferencesProto.Value.newBuilder().setString((String) value).build();
            Intrinsics.checkNotNullExpressionValue(build6, "newBuilder().setString(value).build()");
            return (PreferencesProto.Value) build6;
        } else if (value instanceof Set) {
            PreferencesProto.Value.Builder newBuilder = PreferencesProto.Value.newBuilder();
            PreferencesProto.StringSet.Builder newBuilder2 = PreferencesProto.StringSet.newBuilder();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.collections.Set<kotlin.String>");
            GeneratedMessageLite build7 = newBuilder.setStringSet(newBuilder2.addAllStrings((Set) value)).build();
            Intrinsics.checkNotNullExpressionValue(build7, "newBuilder()\n           …                 .build()");
            return (PreferencesProto.Value) build7;
        } else if (value instanceof byte[]) {
            GeneratedMessageLite build8 = PreferencesProto.Value.newBuilder().setBytes(ByteString.copyFrom((byte[]) value)).build();
            Intrinsics.checkNotNullExpressionValue(build8, "newBuilder().setBytes(By….copyFrom(value)).build()");
            return (PreferencesProto.Value) build8;
        } else {
            throw new IllegalStateException("PreferencesSerializer does not support type: " + value.getClass().getName());
        }
    }

    private final void addProtoEntryToPreferences(String name, PreferencesProto.Value value, MutablePreferences mutablePreferences) {
        PreferencesProto.Value.ValueCase valueCase = value.getValueCase();
        switch (valueCase == null ? -1 : WhenMappings.$EnumSwitchMapping$0[valueCase.ordinal()]) {
            case -1:
                throw new CorruptionException("Value case is null.", (Throwable) null, 2, (DefaultConstructorMarker) null);
            case 1:
                mutablePreferences.set(PreferencesKeys.booleanKey(name), Boolean.valueOf(value.getBoolean()));
                return;
            case 2:
                mutablePreferences.set(PreferencesKeys.floatKey(name), Float.valueOf(value.getFloat()));
                return;
            case 3:
                mutablePreferences.set(PreferencesKeys.doubleKey(name), Double.valueOf(value.getDouble()));
                return;
            case 4:
                mutablePreferences.set(PreferencesKeys.intKey(name), Integer.valueOf(value.getInteger()));
                return;
            case 5:
                mutablePreferences.set(PreferencesKeys.longKey(name), Long.valueOf(value.getLong()));
                return;
            case 6:
                Preferences.Key<String> stringKey = PreferencesKeys.stringKey(name);
                String string = value.getString();
                Intrinsics.checkNotNullExpressionValue(string, "value.string");
                mutablePreferences.set(stringKey, string);
                return;
            case 7:
                Preferences.Key<Set<String>> stringSetKey = PreferencesKeys.stringSetKey(name);
                List<String> stringsList = value.getStringSet().getStringsList();
                Intrinsics.checkNotNullExpressionValue(stringsList, "value.stringSet.stringsList");
                mutablePreferences.set(stringSetKey, CollectionsKt.toSet(stringsList));
                return;
            case 8:
                Preferences.Key<byte[]> byteArrayKey = PreferencesKeys.byteArrayKey(name);
                byte[] byteArray = value.getBytes().toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "value.bytes.toByteArray()");
                mutablePreferences.set(byteArrayKey, byteArray);
                return;
            case 9:
                throw new CorruptionException("Value not set.", (Throwable) null, 2, (DefaultConstructorMarker) null);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
