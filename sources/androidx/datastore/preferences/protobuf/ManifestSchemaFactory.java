package androidx.datastore.preferences.protobuf;

@CheckReturnValue
final class ManifestSchemaFactory implements SchemaFactory {
    private static final MessageInfoFactory EMPTY_FACTORY = new MessageInfoFactory() {
        public boolean isSupported(Class<?> cls) {
            return false;
        }

        public MessageInfo messageInfoFor(Class<?> cls) {
            throw new IllegalStateException("This should never be called.");
        }
    };
    private final MessageInfoFactory messageInfoFactory;

    public ManifestSchemaFactory() {
        this(getDefaultMessageInfoFactory());
    }

    private ManifestSchemaFactory(MessageInfoFactory messageInfoFactory2) {
        this.messageInfoFactory = (MessageInfoFactory) Internal.checkNotNull(messageInfoFactory2, "messageInfoFactory");
    }

    public <T> Schema<T> createSchema(Class<T> messageType) {
        SchemaUtil.requireGeneratedMessage(messageType);
        MessageInfo messageInfo = this.messageInfoFactory.messageInfoFor(messageType);
        if (!messageInfo.isMessageSetWireFormat()) {
            return newSchema(messageType, messageInfo);
        }
        if (useLiteRuntime(messageType)) {
            return MessageSetSchema.newSchema(SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.lite(), messageInfo.getDefaultInstance());
        }
        return MessageSetSchema.newSchema(SchemaUtil.unknownFieldSetFullSchema(), ExtensionSchemas.full(), messageInfo.getDefaultInstance());
    }

    private static <T> Schema<T> newSchema(Class<T> messageType, MessageInfo messageInfo) {
        ExtensionSchema<?> extensionSchema = null;
        if (useLiteRuntime(messageType)) {
            NewInstanceSchema lite = NewInstanceSchemas.lite();
            ListFieldSchema lite2 = ListFieldSchemas.lite();
            UnknownFieldSchema<?, ?> unknownFieldSetLiteSchema = SchemaUtil.unknownFieldSetLiteSchema();
            if (allowExtensions(messageInfo)) {
                extensionSchema = ExtensionSchemas.lite();
            }
            Class<T> messageType2 = messageType;
            MessageInfo messageInfo2 = messageInfo;
            MessageSchema<?> newSchema = MessageSchema.newSchema(messageType2, messageInfo2, lite, lite2, unknownFieldSetLiteSchema, extensionSchema, MapFieldSchemas.lite());
            Class<T> cls = messageType2;
            MessageInfo messageInfo3 = messageInfo2;
            return newSchema;
        }
        Class<T> messageType3 = messageType;
        NewInstanceSchema full = NewInstanceSchemas.full();
        ExtensionSchema<?> extensionSchema2 = null;
        MessageInfo messageInfo4 = messageInfo;
        ListFieldSchema full2 = ListFieldSchemas.full();
        UnknownFieldSchema<?, ?> unknownFieldSetFullSchema = SchemaUtil.unknownFieldSetFullSchema();
        if (allowExtensions(messageInfo4)) {
            extensionSchema2 = ExtensionSchemas.full();
        }
        return MessageSchema.newSchema(messageType3, messageInfo4, full, full2, unknownFieldSetFullSchema, extensionSchema2, MapFieldSchemas.full());
    }

    private static boolean allowExtensions(MessageInfo messageInfo) {
        switch (messageInfo.getSyntax()) {
            case PROTO3:
                return false;
            default:
                return true;
        }
    }

    private static MessageInfoFactory getDefaultMessageInfoFactory() {
        return new CompositeMessageInfoFactory(GeneratedMessageInfoFactory.getInstance(), getDescriptorMessageInfoFactory());
    }

    private static class CompositeMessageInfoFactory implements MessageInfoFactory {
        private MessageInfoFactory[] factories;

        CompositeMessageInfoFactory(MessageInfoFactory... factories2) {
            this.factories = factories2;
        }

        public boolean isSupported(Class<?> clazz) {
            for (MessageInfoFactory factory : this.factories) {
                if (factory.isSupported(clazz)) {
                    return true;
                }
            }
            return false;
        }

        public MessageInfo messageInfoFor(Class<?> clazz) {
            for (MessageInfoFactory factory : this.factories) {
                if (factory.isSupported(clazz)) {
                    return factory.messageInfoFor(clazz);
                }
            }
            throw new UnsupportedOperationException("No factory is available for message type: " + clazz.getName());
        }
    }

    private static MessageInfoFactory getDescriptorMessageInfoFactory() {
        if (Protobuf.assumeLiteRuntime) {
            return EMPTY_FACTORY;
        }
        try {
            return (MessageInfoFactory) Class.forName("androidx.datastore.preferences.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            return EMPTY_FACTORY;
        }
    }

    private static boolean useLiteRuntime(Class<?> messageType) {
        return Protobuf.assumeLiteRuntime || GeneratedMessageLite.class.isAssignableFrom(messageType);
    }
}
