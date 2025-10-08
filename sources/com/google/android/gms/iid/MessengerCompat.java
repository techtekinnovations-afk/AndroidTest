package com.google.android.gms.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ReflectedParcelable;

public class MessengerCompat implements ReflectedParcelable {
    public static final Parcelable.Creator<MessengerCompat> CREATOR = new zzq();
    private Messenger zzad;
    private zzl zzcd;

    public MessengerCompat(IBinder iBinder) {
        this.zzad = new Messenger(iBinder);
    }

    public final void send(Message message) throws RemoteException {
        if (this.zzad != null) {
            this.zzad.send(message);
        } else {
            this.zzcd.send(message);
        }
    }

    private final IBinder getBinder() {
        return this.zzad != null ? this.zzad.getBinder() : this.zzcd.asBinder();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return getBinder().equals(((MessengerCompat) obj).getBinder());
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzad != null) {
            parcel.writeStrongBinder(this.zzad.getBinder());
        } else {
            parcel.writeStrongBinder(this.zzcd.asBinder());
        }
    }
}
