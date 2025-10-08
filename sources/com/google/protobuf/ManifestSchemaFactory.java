package com.google.protobuf;

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
        if (GeneratedMessageLite.class.isAssignableFrom(messageType)) {
            return MessageSetSchema.newSchema(SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.lite(), messageInfo.getDefaultInstance());
        }
        return MessageSetSchema.newSchema(SchemaUtil.unknownFieldSetFullSchema(), ExtensionSchemas.full(), messageInfo.getDefaultInstance());
    }

    private static <T> Schema<T> newSchema(Class<T> messageType, MessageInfo messageInfo) {
        if (!GeneratedMessageLite.class.isAssignableFrom(messageType)) {
            Class<T> messageType2 = messageType;
            MessageInfo messageInfo2 = messageInfo;
            if (allowExtensions(messageInfo2)) {
                return MessageSchema.newSchema(messageType2, messageInfo2, NewInstanceSchemas.full(), ListFieldSchema.full(), SchemaUtil.unknownFieldSetFullSchema(), ExtensionSchemas.full(), MapFieldSchemas.full());
            }
            return MessageSchema.newSchema(messageType2, messageInfo2, NewInstanceSchemas.full(), ListFieldSchema.full(), SchemaUtil.unknownFieldSetFullSchema(), (ExtensionSchema<?>) null, MapFieldSchemas.full());
        } else if (!allowExtensions(messageInfo)) {
            return MessageSchema.newSchema(messageType, messageInfo, NewInstanceSchemas.lite(), ListFieldSchema.lite(), SchemaUtil.unknownFieldSetLiteSchema(), (ExtensionSchema<?>) null, MapFieldSchemas.lite());
        } else {
            Class<T> messageType3 = messageType;
            MessageInfo messageInfo3 = messageInfo;
            MessageSchema<?> newSchema = MessageSchema.newSchema(messageType3, messageInfo3, NewInstanceSchemas.lite(), ListFieldSchema.lite(), SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.lite(), MapFieldSchemas.lite());
            Class<T> cls = messageType3;
            MessageInfo messageInfo4 = messageInfo3;
            return newSchema;
        }
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
        try {
            return (MessageInfoFactory) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            return EMPTY_FACTORY;
        }
    }
}
