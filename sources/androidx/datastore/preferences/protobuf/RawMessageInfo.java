package androidx.datastore.preferences.protobuf;

@CheckReturnValue
final class RawMessageInfo implements MessageInfo {
    private static final int IS_EDITION_BIT = 4;
    private static final int IS_PROTO2_BIT = 1;
    private final MessageLite defaultInstance;
    private final int flags;
    private final String info;
    private final Object[] objects;

    RawMessageInfo(MessageLite defaultInstance2, String info2, Object[] objects2) {
        this.defaultInstance = defaultInstance2;
        this.info = info2;
        this.objects = objects2;
        int position = 0 + 1;
        int position2 = info2.charAt(0);
        if (position2 < 55296) {
            this.flags = position2;
            return;
        }
        int result = position2 & 8191;
        int shift = 13;
        while (true) {
            int position3 = position + 1;
            int position4 = info2.charAt(position);
            int value = position4;
            if (position4 >= 55296) {
                result |= (value & 8191) << shift;
                shift += 13;
                position = position3;
            } else {
                this.flags = (value << shift) | result;
                int i = position3;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public String getStringInfo() {
        return this.info;
    }

    /* access modifiers changed from: package-private */
    public Object[] getObjects() {
        return this.objects;
    }

    public MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }

    public ProtoSyntax getSyntax() {
        if ((this.flags & 1) != 0) {
            return ProtoSyntax.PROTO2;
        }
        if ((this.flags & 4) == 4) {
            return ProtoSyntax.EDITIONS;
        }
        return ProtoSyntax.PROTO3;
    }

    public boolean isMessageSetWireFormat() {
        return (this.flags & 2) == 2;
    }
}
