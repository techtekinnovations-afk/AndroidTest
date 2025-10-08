package org.checkerframework.checker.i18nformatter.qual;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public enum I18nConversionCategory {
    UNUSED((String) null, (int) null),
    GENERAL((String) null, (int) null),
    DATE(new Class[]{Date.class, Number.class}, new String[]{"date", "time"}),
    NUMBER(new Class[]{Number.class}, new String[]{"number", "choice"});
    
    private static final I18nConversionCategory[] conversionCategoriesForIntersect = null;
    private static final I18nConversionCategory[] namedCategories = null;
    public final String[] strings;
    public final Class<?>[] types;

    static {
        namedCategories = new I18nConversionCategory[]{DATE, NUMBER};
        conversionCategoriesForIntersect = new I18nConversionCategory[]{DATE, NUMBER};
    }

    private I18nConversionCategory(Class<?>[] types2, String[] strings2) {
        this.types = types2;
        this.strings = strings2;
    }

    public static I18nConversionCategory stringToI18nConversionCategory(String string) {
        String string2 = string.toLowerCase();
        for (I18nConversionCategory v : namedCategories) {
            for (String s : v.strings) {
                if (s.equals(string2)) {
                    return v;
                }
            }
        }
        throw new IllegalArgumentException("Invalid format type " + string2);
    }

    private static <E> Set<E> arrayToSet(E[] a) {
        return new HashSet(Arrays.asList(a));
    }

    public static boolean isSubsetOf(I18nConversionCategory a, I18nConversionCategory b) {
        return intersect(a, b) == a;
    }

    public static I18nConversionCategory intersect(I18nConversionCategory a, I18nConversionCategory b) {
        if (a == UNUSED) {
            return b;
        }
        if (b == UNUSED) {
            return a;
        }
        if (a == GENERAL) {
            return b;
        }
        if (b == GENERAL) {
            return a;
        }
        Set<Class<?>> as = arrayToSet(a.types);
        as.retainAll(arrayToSet(b.types));
        for (I18nConversionCategory v : conversionCategoriesForIntersect) {
            if (arrayToSet(v.types).equals(as)) {
                return v;
            }
        }
        throw new RuntimeException();
    }

    public static I18nConversionCategory union(I18nConversionCategory a, I18nConversionCategory b) {
        if (a == UNUSED || b == UNUSED) {
            return UNUSED;
        }
        if (a == GENERAL || b == GENERAL) {
            return GENERAL;
        }
        if (a == DATE || b == DATE) {
            return DATE;
        }
        return NUMBER;
    }

    public boolean isAssignableFrom(Class<?> argType) {
        if (this.types == null || argType == Void.TYPE) {
            return true;
        }
        for (Class<?> c : this.types) {
            if (c.isAssignableFrom(argType)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(name());
        if (this.types == null) {
            sb.append(" conversion category (all types)");
        } else {
            StringJoiner sj = new StringJoiner(", ", " conversion category (one of: ", ")");
            for (Class<?> cls : this.types) {
                sj.add(cls.getCanonicalName());
            }
            sb.append(sj);
        }
        return sb.toString();
    }
}
