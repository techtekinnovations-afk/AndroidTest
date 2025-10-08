package androidx.core.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

class FontRequestWorker {
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = RequestExecutor.createDefaultExecutor("fonts-androidx", 10, 10000);
    static final Object LOCK = new Object();
    static final SimpleArrayMap<String, ArrayList<Consumer<TypefaceResult>>> PENDING_REPLIES = new SimpleArrayMap<>();
    static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);

    private FontRequestWorker() {
    }

    static void resetTypefaceCache() {
        sTypefaceCache.evictAll();
    }

    static Typeface requestFontSync(final Context context, final FontRequest request, CallbackWithHandler callback, final int style, int timeoutInMillis) {
        final String id = createCacheId(request, style);
        Typeface cached = sTypefaceCache.get(id);
        if (cached != null) {
            callback.onTypefaceResult(new TypefaceResult(cached));
            return cached;
        } else if (timeoutInMillis == -1) {
            TypefaceResult typefaceResult = getFontSync(id, context, request, style);
            callback.onTypefaceResult(typefaceResult);
            return typefaceResult.mTypeface;
        } else {
            try {
                TypefaceResult typefaceResult2 = (TypefaceResult) RequestExecutor.submit(DEFAULT_EXECUTOR_SERVICE, new Callable<TypefaceResult>() {
                    public TypefaceResult call() {
                        return FontRequestWorker.getFontSync(id, context, request, style);
                    }
                }, timeoutInMillis);
                callback.onTypefaceResult(typefaceResult2);
                return typefaceResult2.mTypeface;
            } catch (InterruptedException e) {
                callback.onTypefaceResult(new TypefaceResult(-3));
                return null;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003e, code lost:
        r3 = new androidx.core.provider.FontRequestWorker.AnonymousClass3();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0043, code lost:
        if (r10 != null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
        r4 = DEFAULT_EXECUTOR_SERVICE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0048, code lost:
        r4 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0049, code lost:
        androidx.core.provider.RequestExecutor.execute(r4, r3, new androidx.core.provider.FontRequestWorker.AnonymousClass4());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0051, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.graphics.Typeface requestFontAsync(final android.content.Context r7, final androidx.core.provider.FontRequest r8, final int r9, java.util.concurrent.Executor r10, final androidx.core.provider.CallbackWithHandler r11) {
        /*
            java.lang.String r0 = createCacheId(r8, r9)
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r1 = sTypefaceCache
            java.lang.Object r1 = r1.get(r0)
            android.graphics.Typeface r1 = (android.graphics.Typeface) r1
            if (r1 == 0) goto L_0x0017
            androidx.core.provider.FontRequestWorker$TypefaceResult r2 = new androidx.core.provider.FontRequestWorker$TypefaceResult
            r2.<init>((android.graphics.Typeface) r1)
            r11.onTypefaceResult(r2)
            return r1
        L_0x0017:
            androidx.core.provider.FontRequestWorker$2 r2 = new androidx.core.provider.FontRequestWorker$2
            r2.<init>()
            java.lang.Object r3 = LOCK
            monitor-enter(r3)
            androidx.collection.SimpleArrayMap<java.lang.String, java.util.ArrayList<androidx.core.util.Consumer<androidx.core.provider.FontRequestWorker$TypefaceResult>>> r4 = PENDING_REPLIES     // Catch:{ all -> 0x0052 }
            java.lang.Object r4 = r4.get(r0)     // Catch:{ all -> 0x0052 }
            java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch:{ all -> 0x0052 }
            r5 = 0
            if (r4 == 0) goto L_0x002f
            r4.add(r2)     // Catch:{ all -> 0x0052 }
            monitor-exit(r3)     // Catch:{ all -> 0x0052 }
            return r5
        L_0x002f:
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x0052 }
            r6.<init>()     // Catch:{ all -> 0x0052 }
            r6.add(r2)     // Catch:{ all -> 0x0052 }
            androidx.collection.SimpleArrayMap<java.lang.String, java.util.ArrayList<androidx.core.util.Consumer<androidx.core.provider.FontRequestWorker$TypefaceResult>>> r4 = PENDING_REPLIES     // Catch:{ all -> 0x0052 }
            r4.put(r0, r6)     // Catch:{ all -> 0x0052 }
            monitor-exit(r3)     // Catch:{ all -> 0x0052 }
            androidx.core.provider.FontRequestWorker$3 r3 = new androidx.core.provider.FontRequestWorker$3
            r3.<init>(r0, r7, r8, r9)
            if (r10 != 0) goto L_0x0048
            java.util.concurrent.ExecutorService r4 = DEFAULT_EXECUTOR_SERVICE
            goto L_0x0049
        L_0x0048:
            r4 = r10
        L_0x0049:
            androidx.core.provider.FontRequestWorker$4 r6 = new androidx.core.provider.FontRequestWorker$4
            r6.<init>(r0)
            androidx.core.provider.RequestExecutor.execute(r4, r3, r6)
            return r5
        L_0x0052:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0052 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontRequestWorker.requestFontAsync(android.content.Context, androidx.core.provider.FontRequest, int, java.util.concurrent.Executor, androidx.core.provider.CallbackWithHandler):android.graphics.Typeface");
    }

    private static String createCacheId(FontRequest request, int style) {
        return request.getId() + "-" + style;
    }

    static TypefaceResult getFontSync(String cacheId, Context context, FontRequest request, int style) {
        Typeface cached = sTypefaceCache.get(cacheId);
        if (cached != null) {
            return new TypefaceResult(cached);
        }
        try {
            FontsContractCompat.FontFamilyResult result = FontProvider.getFontFamilyResult(context, request, (CancellationSignal) null);
            int fontFamilyResultStatus = getFontFamilyResultStatus(result);
            if (fontFamilyResultStatus != 0) {
                return new TypefaceResult(fontFamilyResultStatus);
            }
            Typeface typeface = TypefaceCompat.createFromFontInfo(context, (CancellationSignal) null, result.getFonts(), style);
            if (typeface == null) {
                return new TypefaceResult(-3);
            }
            sTypefaceCache.put(cacheId, typeface);
            return new TypefaceResult(typeface);
        } catch (PackageManager.NameNotFoundException e) {
            return new TypefaceResult(-1);
        }
    }

    private static int getFontFamilyResultStatus(FontsContractCompat.FontFamilyResult fontFamilyResult) {
        if (fontFamilyResult.getStatusCode() != 0) {
            switch (fontFamilyResult.getStatusCode()) {
                case 1:
                    return -2;
                default:
                    return -3;
            }
        } else {
            FontsContractCompat.FontInfo[] fonts = fontFamilyResult.getFonts();
            if (fonts == null || fonts.length == 0) {
                return 1;
            }
            int length = fonts.length;
            int i = 0;
            while (i < length) {
                int resultCode = fonts[i].getResultCode();
                if (resultCode == 0) {
                    i++;
                } else if (resultCode < 0) {
                    return -3;
                } else {
                    return resultCode;
                }
            }
            return 0;
        }
    }

    static final class TypefaceResult {
        final int mResult;
        final Typeface mTypeface;

        TypefaceResult(int result) {
            this.mTypeface = null;
            this.mResult = result;
        }

        TypefaceResult(Typeface typeface) {
            this.mTypeface = typeface;
            this.mResult = 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isSuccess() {
            return this.mResult == 0;
        }
    }
}
