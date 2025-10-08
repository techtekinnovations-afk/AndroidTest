package androidx.core.provider;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class FontProvider {
    private static final Comparator<byte[]> sByteArrayComparator = new FontProvider$$ExternalSyntheticLambda0();

    private FontProvider() {
    }

    static FontsContractCompat.FontFamilyResult getFontFamilyResult(Context context, FontRequest request, CancellationSignal cancellationSignal) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return FontsContractCompat.FontFamilyResult.create(1, (FontsContractCompat.FontInfo[]) null);
        }
        return FontsContractCompat.FontFamilyResult.create(0, query(context, request, providerInfo.authority, cancellationSignal));
    }

    static ProviderInfo getProvider(PackageManager packageManager, FontRequest request, Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (info.packageName.equals(request.getProviderPackage())) {
            List<byte[]> signatures = convertToByteArrayList(packageManager.getPackageInfo(info.packageName, 64).signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0122  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static androidx.core.provider.FontsContractCompat.FontInfo[] query(android.content.Context r22, androidx.core.provider.FontRequest r23, java.lang.String r24, android.os.CancellationSignal r25) {
        /*
            r1 = r24
            java.lang.String r0 = "result_code"
            java.lang.String r2 = "font_italic"
            java.lang.String r3 = "font_weight"
            java.lang.String r4 = "font_ttc_index"
            java.lang.String r5 = "file_id"
            java.lang.String r6 = "_id"
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            android.net.Uri$Builder r8 = new android.net.Uri$Builder
            r8.<init>()
            java.lang.String r9 = "content"
            android.net.Uri$Builder r8 = r8.scheme(r9)
            android.net.Uri$Builder r8 = r8.authority(r1)
            android.net.Uri r11 = r8.build()
            android.net.Uri$Builder r8 = new android.net.Uri$Builder
            r8.<init>()
            android.net.Uri$Builder r8 = r8.scheme(r9)
            android.net.Uri$Builder r8 = r8.authority(r1)
            java.lang.String r9 = "file"
            android.net.Uri$Builder r8 = r8.appendPath(r9)
            android.net.Uri r8 = r8.build()
            r9 = 0
            r10 = r22
            androidx.core.provider.FontProvider$ContentQueryWrapper r12 = androidx.core.provider.FontProvider.ContentQueryWrapper.make(r10, r11)
            r13 = 7
            java.lang.String[] r13 = new java.lang.String[r13]     // Catch:{ all -> 0x011e }
            r14 = 0
            r13[r14] = r6     // Catch:{ all -> 0x011e }
            r15 = 1
            r13[r15] = r5     // Catch:{ all -> 0x011e }
            r16 = 2
            r13[r16] = r4     // Catch:{ all -> 0x011e }
            java.lang.String r16 = "font_variation_settings"
            r17 = 3
            r13[r17] = r16     // Catch:{ all -> 0x011e }
            r16 = 4
            r13[r16] = r3     // Catch:{ all -> 0x011e }
            r16 = 5
            r13[r16] = r2     // Catch:{ all -> 0x011e }
            r16 = 6
            r13[r16] = r0     // Catch:{ all -> 0x011e }
            r10 = r12
            r12 = r13
            java.lang.String r13 = "query = ?"
            r16 = r14
            java.lang.String[] r14 = new java.lang.String[r15]     // Catch:{ all -> 0x011c }
            java.lang.String r17 = r23.getQuery()     // Catch:{ all -> 0x011c }
            r14[r16] = r17     // Catch:{ all -> 0x011c }
            r17 = r15
            r15 = 0
            r16 = r25
            r1 = r17
            android.database.Cursor r13 = r10.query(r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x011c }
            r9 = r13
            if (r9 == 0) goto L_0x0109
            int r13 = r9.getCount()     // Catch:{ all -> 0x011c }
            if (r13 <= 0) goto L_0x0109
            int r0 = r9.getColumnIndex(r0)     // Catch:{ all -> 0x011c }
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ all -> 0x011c }
            r13.<init>()     // Catch:{ all -> 0x011c }
            r7 = r13
            int r6 = r9.getColumnIndex(r6)     // Catch:{ all -> 0x011c }
            int r5 = r9.getColumnIndex(r5)     // Catch:{ all -> 0x011c }
            int r4 = r9.getColumnIndex(r4)     // Catch:{ all -> 0x011c }
            int r3 = r9.getColumnIndex(r3)     // Catch:{ all -> 0x011c }
            int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x011c }
        L_0x00a3:
            boolean r13 = r9.moveToNext()     // Catch:{ all -> 0x011c }
            if (r13 == 0) goto L_0x0105
            r13 = -1
            if (r0 == r13) goto L_0x00b1
            int r14 = r9.getInt(r0)     // Catch:{ all -> 0x011c }
            goto L_0x00b2
        L_0x00b1:
            r14 = 0
        L_0x00b2:
            if (r4 == r13) goto L_0x00ba
            int r15 = r9.getInt(r4)     // Catch:{ all -> 0x011c }
            goto L_0x00bb
        L_0x00ba:
            r15 = 0
        L_0x00bb:
            if (r5 != r13) goto L_0x00ce
            long r18 = r9.getLong(r6)     // Catch:{ all -> 0x011c }
            r20 = r18
            r18 = r2
            r1 = r20
            android.net.Uri r19 = android.content.ContentUris.withAppendedId(r11, r1)     // Catch:{ all -> 0x011c }
            r1 = r19
            goto L_0x00da
        L_0x00ce:
            r18 = r2
            long r1 = r9.getLong(r5)     // Catch:{ all -> 0x011c }
            android.net.Uri r19 = android.content.ContentUris.withAppendedId(r8, r1)     // Catch:{ all -> 0x011c }
            r1 = r19
        L_0x00da:
            if (r3 == r13) goto L_0x00e1
            int r2 = r9.getInt(r3)     // Catch:{ all -> 0x011c }
            goto L_0x00e3
        L_0x00e1:
            r2 = 400(0x190, float:5.6E-43)
        L_0x00e3:
            r19 = r0
            r0 = r18
            if (r0 == r13) goto L_0x00f4
            int r13 = r9.getInt(r0)     // Catch:{ all -> 0x011c }
            r18 = r0
            r0 = 1
            if (r13 != r0) goto L_0x00f7
            r13 = r0
            goto L_0x00f8
        L_0x00f4:
            r18 = r0
            r0 = 1
        L_0x00f7:
            r13 = 0
        L_0x00f8:
            androidx.core.provider.FontsContractCompat$FontInfo r0 = androidx.core.provider.FontsContractCompat.FontInfo.create(r1, r15, r2, r13, r14)     // Catch:{ all -> 0x011c }
            r7.add(r0)     // Catch:{ all -> 0x011c }
            r2 = r18
            r0 = r19
            r1 = 1
            goto L_0x00a3
        L_0x0105:
            r19 = r0
            r18 = r2
        L_0x0109:
            if (r9 == 0) goto L_0x010e
            r9.close()
        L_0x010e:
            r10.close()
            r0 = 0
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = new androidx.core.provider.FontsContractCompat.FontInfo[r0]
            java.lang.Object[] r0 = r7.toArray(r0)
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = (androidx.core.provider.FontsContractCompat.FontInfo[]) r0
            return r0
        L_0x011c:
            r0 = move-exception
            goto L_0x0120
        L_0x011e:
            r0 = move-exception
            r10 = r12
        L_0x0120:
            if (r9 == 0) goto L_0x0125
            r9.close()
        L_0x0125:
            r10.close()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontProvider.query(android.content.Context, androidx.core.provider.FontRequest, java.lang.String, android.os.CancellationSignal):androidx.core.provider.FontsContractCompat$FontInfo[]");
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, request.getCertificatesArrayResId());
    }

    static /* synthetic */ int lambda$static$0(byte[] l, byte[] r) {
        if (l.length != r.length) {
            return l.length - r.length;
        }
        for (int i = 0; i < l.length; i++) {
            if (l[i] != r[i]) {
                return l[i] - r[i];
            }
        }
        return 0;
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shaList = new ArrayList<>();
        for (Signature signature : signatures) {
            shaList.add(signature.toByteArray());
        }
        return shaList;
    }

    private interface ContentQueryWrapper {
        void close();

        Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal);

        static ContentQueryWrapper make(Context context, Uri uri) {
            return new ContentQueryWrapperApi24Impl(context, uri);
        }
    }

    private static class ContentQueryWrapperApi16Impl implements ContentQueryWrapper {
        private final ContentProviderClient mClient;

        ContentQueryWrapperApi16Impl(Context context, Uri uri) {
            this.mClient = context.getContentResolver().acquireUnstableContentProviderClient(uri);
        }

        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
            RemoteException e;
            if (this.mClient == null) {
                return null;
            }
            try {
                try {
                    return this.mClient.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
                } catch (RemoteException e2) {
                    e = e2;
                    Log.w("FontsProvider", "Unable to query the content provider", e);
                    return null;
                }
            } catch (RemoteException e3) {
                Uri uri2 = uri;
                String[] strArr = projection;
                String str = selection;
                String[] strArr2 = selectionArgs;
                String str2 = sortOrder;
                CancellationSignal cancellationSignal2 = cancellationSignal;
                e = e3;
                Log.w("FontsProvider", "Unable to query the content provider", e);
                return null;
            }
        }

        public void close() {
            if (this.mClient != null) {
                this.mClient.release();
            }
        }
    }

    private static class ContentQueryWrapperApi24Impl implements ContentQueryWrapper {
        private final ContentProviderClient mClient;

        ContentQueryWrapperApi24Impl(Context context, Uri uri) {
            this.mClient = context.getContentResolver().acquireUnstableContentProviderClient(uri);
        }

        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
            RemoteException e;
            if (this.mClient == null) {
                return null;
            }
            try {
                try {
                    return this.mClient.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
                } catch (RemoteException e2) {
                    e = e2;
                    Log.w("FontsProvider", "Unable to query the content provider", e);
                    return null;
                }
            } catch (RemoteException e3) {
                Uri uri2 = uri;
                String[] strArr = projection;
                String str = selection;
                String[] strArr2 = selectionArgs;
                String str2 = sortOrder;
                CancellationSignal cancellationSignal2 = cancellationSignal;
                e = e3;
                Log.w("FontsProvider", "Unable to query the content provider", e);
                return null;
            }
        }

        public void close() {
            if (this.mClient != null) {
                this.mClient.close();
            }
        }
    }
}
