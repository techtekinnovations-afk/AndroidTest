package androidx.activity.result;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007B\u0011\b\u0010\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\u0006\u0010\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\b\u0010\u0015\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Landroidx/activity/result/ActivityResult;", "Landroid/os/Parcelable;", "resultCode", "", "data", "Landroid/content/Intent;", "<init>", "(ILandroid/content/Intent;)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "getResultCode", "()I", "getData", "()Landroid/content/Intent;", "toString", "", "writeToParcel", "", "dest", "flags", "describeContents", "Companion", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: ActivityResult.kt */
public final class ActivityResult implements Parcelable {
    public static final Parcelable.Creator<ActivityResult> CREATOR = new ActivityResult$Companion$CREATOR$1();
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final Intent data;
    private final int resultCode;

    public ActivityResult(int resultCode2, Intent data2) {
        this.resultCode = resultCode2;
        this.data = data2;
    }

    public final int getResultCode() {
        return this.resultCode;
    }

    public final Intent getData() {
        return this.data;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ActivityResult(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt() == 0 ? null : (Intent) Intent.CREATOR.createFromParcel(parcel));
        Intrinsics.checkNotNullParameter(parcel, "parcel");
    }

    public String toString() {
        return "ActivityResult{resultCode=" + Companion.resultCodeToString(this.resultCode) + ", data=" + this.data + '}';
    }

    public void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeInt(this.resultCode);
        dest.writeInt(this.data == null ? 0 : 1);
        Intent intent = this.data;
        if (intent != null) {
            intent.writeToParcel(dest, flags);
        }
    }

    public int describeContents() {
        return 0;
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007R\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\u0003¨\u0006\f"}, d2 = {"Landroidx/activity/result/ActivityResult$Companion;", "", "<init>", "()V", "resultCodeToString", "", "resultCode", "", "CREATOR", "Landroid/os/Parcelable$Creator;", "Landroidx/activity/result/ActivityResult;", "getCREATOR$annotations", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: ActivityResult.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getCREATOR$annotations() {
        }

        private Companion() {
        }

        @JvmStatic
        public final String resultCodeToString(int resultCode) {
            switch (resultCode) {
                case -1:
                    return "RESULT_OK";
                case 0:
                    return "RESULT_CANCELED";
                default:
                    return String.valueOf(resultCode);
            }
        }
    }

    @JvmStatic
    public static final String resultCodeToString(int resultCode2) {
        return Companion.resultCodeToString(resultCode2);
    }
}
