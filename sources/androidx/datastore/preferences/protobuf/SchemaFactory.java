package androidx.datastore.preferences.protobuf;

@CheckReturnValue
interface SchemaFactory {
    <T> Schema<T> createSchema(Class<T> cls);
}
