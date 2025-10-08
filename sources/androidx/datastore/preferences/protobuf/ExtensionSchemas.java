package androidx.datastore.preferences.protobuf;

@CheckReturnValue
final class ExtensionSchemas {
    private static final ExtensionSchema<?> FULL_SCHEMA = loadSchemaForFullRuntime();
    private static final ExtensionSchema<?> LITE_SCHEMA = new ExtensionSchemaLite();

    private static ExtensionSchema<?> loadSchemaForFullRuntime() {
        if (Protobuf.assumeLiteRuntime) {
            return null;
        }
        try {
            return (ExtensionSchema) Class.forName("androidx.datastore.preferences.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static ExtensionSchema<?> lite() {
        return LITE_SCHEMA;
    }

    static ExtensionSchema<?> full() {
        if (FULL_SCHEMA != null) {
            return FULL_SCHEMA;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }

    private ExtensionSchemas() {
    }
}
