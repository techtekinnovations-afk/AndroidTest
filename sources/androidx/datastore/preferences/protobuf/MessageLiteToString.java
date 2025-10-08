package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import com.google.firebase.firestore.model.Values;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import kotlin.text.Typography;

final class MessageLiteToString {
    private static final String BUILDER_LIST_SUFFIX = "OrBuilderList";
    private static final String BYTES_SUFFIX = "Bytes";
    private static final char[] INDENT_BUFFER = new char[80];
    private static final String LIST_SUFFIX = "List";
    private static final String MAP_SUFFIX = "Map";

    static {
        Arrays.fill(INDENT_BUFFER, ' ');
    }

    private MessageLiteToString() {
    }

    static String toString(MessageLite messageLite, String commentString) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("# ").append(commentString);
        reflectivePrintWithIndent(messageLite, buffer, 0);
        return buffer.toString();
    }

    private static void reflectivePrintWithIndent(MessageLite messageLite, StringBuilder buffer, int indent) {
        int i;
        Set<String> setters;
        boolean hasValue;
        Method mapMethod;
        Method listMethod;
        MessageLite messageLite2 = messageLite;
        StringBuilder sb = buffer;
        int i2 = indent;
        Set<String> hashSet = new HashSet<>();
        Map<String, Method> hazzers = new HashMap<>();
        Map<String, Method> getters = new TreeMap<>();
        Method[] declaredMethods = messageLite2.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i3 = 0;
        while (true) {
            i = 3;
            if (i3 >= length) {
                break;
            }
            Method method = declaredMethods[i3];
            if (!Modifier.isStatic(method.getModifiers()) && method.getName().length() >= 3) {
                if (method.getName().startsWith("set")) {
                    hashSet.add(method.getName());
                } else if (Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0) {
                    if (method.getName().startsWith("has")) {
                        hazzers.put(method.getName(), method);
                    } else if (method.getName().startsWith("get")) {
                        getters.put(method.getName(), method);
                    }
                }
            }
            i3++;
        }
        for (Map.Entry<String, Method> getter : getters.entrySet()) {
            String suffix = getter.getKey().substring(i);
            if (suffix.endsWith(LIST_SUFFIX) && !suffix.endsWith(BUILDER_LIST_SUFFIX) && !suffix.equals(LIST_SUFFIX) && (listMethod = getter.getValue()) != null && listMethod.getReturnType().equals(List.class)) {
                printField(sb, i2, suffix.substring(0, suffix.length() - LIST_SUFFIX.length()), GeneratedMessageLite.invokeOrDie(listMethod, messageLite2, new Object[0]));
                i = 3;
            } else if (suffix.endsWith(MAP_SUFFIX) && !suffix.equals(MAP_SUFFIX) && (mapMethod = getter.getValue()) != null && mapMethod.getReturnType().equals(Map.class) && !mapMethod.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(mapMethod.getModifiers())) {
                printField(sb, i2, suffix.substring(0, suffix.length() - MAP_SUFFIX.length()), GeneratedMessageLite.invokeOrDie(mapMethod, messageLite2, new Object[0]));
                i = 3;
            } else if (!hashSet.contains("set" + suffix)) {
                i = 3;
            } else if (!suffix.endsWith(BYTES_SUFFIX) || !getters.containsKey("get" + suffix.substring(0, suffix.length() - BYTES_SUFFIX.length()))) {
                Method getMethod = getter.getValue();
                Method hasMethod = hazzers.get("has" + suffix);
                if (getMethod != null) {
                    Object value = GeneratedMessageLite.invokeOrDie(getMethod, messageLite2, new Object[0]);
                    if (hasMethod != null) {
                        setters = hashSet;
                        hasValue = ((Boolean) GeneratedMessageLite.invokeOrDie(hasMethod, messageLite2, new Object[0])).booleanValue();
                    } else if (!isDefaultValue(value)) {
                        setters = hashSet;
                        hasValue = true;
                    } else {
                        setters = hashSet;
                        hasValue = false;
                    }
                    if (hasValue) {
                        printField(sb, i2, suffix, value);
                        hashSet = setters;
                        i = 3;
                    } else {
                        hashSet = setters;
                        i = 3;
                    }
                } else {
                    Set<String> setters2 = hashSet;
                    i = 3;
                }
            } else {
                i = 3;
            }
        }
        Set<String> setters3 = hashSet;
        if (messageLite2 instanceof GeneratedMessageLite.ExtendableMessage) {
            Iterator<Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object>> iter = ((GeneratedMessageLite.ExtendableMessage) messageLite2).extensions.iterator();
            while (iter.hasNext()) {
                Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object> entry = iter.next();
                printField(sb, i2, "[" + entry.getKey().getNumber() + "]", entry.getValue());
            }
        }
        if (((GeneratedMessageLite) messageLite2).unknownFields != null) {
            ((GeneratedMessageLite) messageLite2).unknownFields.printWithIndent(sb, i2);
        }
    }

    private static boolean isDefaultValue(Object o) {
        if (o instanceof Boolean) {
            return !((Boolean) o).booleanValue();
        }
        if (o instanceof Integer) {
            if (((Integer) o).intValue() == 0) {
                return true;
            }
            return false;
        } else if (o instanceof Float) {
            if (Float.floatToRawIntBits(((Float) o).floatValue()) == 0) {
                return true;
            }
            return false;
        } else if (o instanceof Double) {
            if (Double.doubleToRawLongBits(((Double) o).doubleValue()) == 0) {
                return true;
            }
            return false;
        } else if (o instanceof String) {
            return o.equals("");
        } else {
            if (o instanceof ByteString) {
                return o.equals(ByteString.EMPTY);
            }
            if (o instanceof MessageLite) {
                if (o == ((MessageLite) o).getDefaultInstanceForType()) {
                    return true;
                }
                return false;
            } else if (!(o instanceof Enum)) {
                return false;
            } else {
                if (((Enum) o).ordinal() == 0) {
                    return true;
                }
                return false;
            }
        }
    }

    static void printField(StringBuilder buffer, int indent, String name, Object object) {
        if (object instanceof List) {
            for (Object entry : (List) object) {
                printField(buffer, indent, name, entry);
            }
        } else if (object instanceof Map) {
            for (Map.Entry<?, ?> entry2 : ((Map) object).entrySet()) {
                printField(buffer, indent, name, entry2);
            }
        } else {
            buffer.append(10);
            indent(indent, buffer);
            buffer.append(pascalCaseToSnakeCase(name));
            if (object instanceof String) {
                buffer.append(": \"").append(TextFormatEscaper.escapeText((String) object)).append(Typography.quote);
            } else if (object instanceof ByteString) {
                buffer.append(": \"").append(TextFormatEscaper.escapeBytes((ByteString) object)).append(Typography.quote);
            } else if (object instanceof GeneratedMessageLite) {
                buffer.append(" {");
                reflectivePrintWithIndent((GeneratedMessageLite) object, buffer, indent + 2);
                buffer.append("\n");
                indent(indent, buffer);
                buffer.append("}");
            } else if (object instanceof Map.Entry) {
                buffer.append(" {");
                Map.Entry<?, ?> entry3 = (Map.Entry) object;
                printField(buffer, indent + 2, "key", entry3.getKey());
                printField(buffer, indent + 2, Values.VECTOR_MAP_VECTORS_KEY, entry3.getValue());
                buffer.append("\n");
                indent(indent, buffer);
                buffer.append("}");
            } else {
                buffer.append(": ").append(object);
            }
        }
    }

    private static void indent(int indent, StringBuilder buffer) {
        while (indent > 0) {
            int partialIndent = indent;
            if (partialIndent > INDENT_BUFFER.length) {
                partialIndent = INDENT_BUFFER.length;
            }
            buffer.append(INDENT_BUFFER, 0, partialIndent);
            indent -= partialIndent;
        }
    }

    private static String pascalCaseToSnakeCase(String pascalCase) {
        if (pascalCase.isEmpty()) {
            return pascalCase;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(Character.toLowerCase(pascalCase.charAt(0)));
        for (int i = 1; i < pascalCase.length(); i++) {
            char ch = pascalCase.charAt(i);
            if (Character.isUpperCase(ch)) {
                builder.append("_");
            }
            builder.append(Character.toLowerCase(ch));
        }
        return builder.toString();
    }
}
