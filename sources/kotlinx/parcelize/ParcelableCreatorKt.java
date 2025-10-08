package kotlinx.parcelize;

import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\b¨\u0006\u0004"}, d2 = {"parcelableCreator", "Landroid/os/Parcelable$Creator;", "T", "Landroid/os/Parcelable;", "parcelize-runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* compiled from: parcelableCreator.kt */
public final class ParcelableCreatorKt {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: android.os.Parcelable$Creator<T>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ <T extends android.os.Parcelable> android.os.Parcelable.Creator<T> parcelableCreator() {
        /*
            r0 = 0
            r1 = 4
            java.lang.String r2 = "T"
            kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r1, r2)
            java.lang.Class<android.os.Parcelable> r3 = android.os.Parcelable.class
            r4 = r3
            java.lang.Class r4 = (java.lang.Class) r4
            java.lang.String r4 = "CREATOR"
            java.lang.reflect.Field r3 = r3.getDeclaredField(r4)
            r4 = 0
            java.lang.Object r3 = r3.get(r4)
            boolean r5 = r3 instanceof android.os.Parcelable.Creator
            if (r5 == 0) goto L_0x001e
            r4 = r3
            android.os.Parcelable$Creator r4 = (android.os.Parcelable.Creator) r4
        L_0x001e:
            if (r4 == 0) goto L_0x0024
            r1 = r4
            android.os.Parcelable$Creator r1 = (android.os.Parcelable.Creator) r1
            return r4
        L_0x0024:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Could not access CREATOR field in class "
            java.lang.StringBuilder r4 = r4.append(r5)
            kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r1, r2)
            java.lang.Class<android.os.Parcelable> r1 = android.os.Parcelable.class
            kotlin.reflect.KClass r1 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r1)
            java.lang.String r1 = r1.getSimpleName()
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r1 = r1.toString()
            r3.<init>(r1)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.parcelize.ParcelableCreatorKt.parcelableCreator():android.os.Parcelable$Creator");
    }
}
