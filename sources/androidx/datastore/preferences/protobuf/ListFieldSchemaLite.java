package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Internal;
import java.util.List;

final class ListFieldSchemaLite implements ListFieldSchema {
    ListFieldSchemaLite() {
    }

    public <L> List<L> mutableListAt(Object message, long offset) {
        Internal.ProtobufList<L> list = getProtobufList(message, offset);
        if (list.isModifiable()) {
            return list;
        }
        int size = list.size();
        Internal.ProtobufList<L> list2 = list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
        UnsafeUtil.putObject(message, offset, (Object) list2);
        return list2;
    }

    public void makeImmutableListAt(Object message, long offset) {
        getProtobufList(message, offset).makeImmutable();
    }

    public <E> void mergeListsAt(Object msg, Object otherMsg, long offset) {
        Internal.ProtobufList<E> mine = getProtobufList(msg, offset);
        Internal.ProtobufList<E> other = getProtobufList(otherMsg, offset);
        int size = mine.size();
        int otherSize = other.size();
        if (size > 0 && otherSize > 0) {
            if (!mine.isModifiable()) {
                mine = mine.mutableCopyWithCapacity(size + otherSize);
            }
            mine.addAll(other);
        }
        UnsafeUtil.putObject(msg, offset, (Object) size > 0 ? mine : other);
    }

    static <E> Internal.ProtobufList<E> getProtobufList(Object message, long offset) {
        return (Internal.ProtobufList) UnsafeUtil.getObject(message, offset);
    }
}
