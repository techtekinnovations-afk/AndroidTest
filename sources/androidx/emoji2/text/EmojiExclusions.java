package androidx.emoji2.text;

import android.os.Build;
import java.util.Collections;
import java.util.Set;

class EmojiExclusions {
    private EmojiExclusions() {
    }

    static Set<int[]> getEmojiExclusions() {
        if (Build.VERSION.SDK_INT >= 34) {
            return EmojiExclusions_Api34.getExclusions();
        }
        return EmojiExclusions_Reflections.getExclusions();
    }

    private static class EmojiExclusions_Api34 {
        private EmojiExclusions_Api34() {
        }

        static Set<int[]> getExclusions() {
            return EmojiExclusions_Reflections.getExclusions();
        }
    }

    private static class EmojiExclusions_Reflections {
        private EmojiExclusions_Reflections() {
        }

        static Set<int[]> getExclusions() {
            try {
                Object result = Class.forName("android.text.EmojiConsistency").getMethod("getEmojiConsistencySet", new Class[0]).invoke((Object) null, new Object[0]);
                if (result == null) {
                    return Collections.emptySet();
                }
                Set<?> resultList = (Set) result;
                for (Object item : resultList) {
                    if (!(item instanceof int[])) {
                        return Collections.emptySet();
                    }
                }
                return resultList;
            } catch (Throwable th) {
                return Collections.emptySet();
            }
        }
    }
}
