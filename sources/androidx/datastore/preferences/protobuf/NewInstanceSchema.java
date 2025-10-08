package androidx.datastore.preferences.protobuf;

@CheckReturnValue
interface NewInstanceSchema {
    Object newInstance(Object obj);
}
