package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.gcm.Task;

public class PeriodicTask extends Task {
    public static final Parcelable.Creator<PeriodicTask> CREATOR = new zzk();
    protected long mFlexInSeconds;
    protected long mIntervalInSeconds;

    private PeriodicTask(Builder builder) {
        super((Task.Builder) builder);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = builder.zzao;
        this.mFlexInSeconds = Math.min(builder.zzap, this.mIntervalInSeconds);
    }

    public static class Builder extends Task.Builder {
        /* access modifiers changed from: private */
        public long zzao = -1;
        /* access modifiers changed from: private */
        public long zzap = -1;

        public Builder() {
            this.isPersisted = true;
        }

        public Builder setPeriod(long j) {
            this.zzao = j;
            return this;
        }

        public Builder setFlex(long j) {
            this.zzap = j;
            return this;
        }

        public Builder setService(Class<? extends GcmTaskService> cls) {
            this.gcmTaskService = cls.getName();
            return this;
        }

        public Builder setRequiredNetwork(int i) {
            this.requiredNetworkState = i;
            return this;
        }

        public Builder setRequiresCharging(boolean z) {
            this.requiresCharging = z;
            return this;
        }

        public Builder setTag(String str) {
            this.tag = str;
            return this;
        }

        public Builder setPersisted(boolean z) {
            this.isPersisted = z;
            return this;
        }

        public Builder setUpdateCurrent(boolean z) {
            this.updateCurrent = z;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.extras = bundle;
            return this;
        }

        public PeriodicTask build() {
            checkConditions();
            return new PeriodicTask(this, (zzk) null);
        }

        /* access modifiers changed from: protected */
        public void checkConditions() {
            super.checkConditions();
            if (this.zzao == -1) {
                throw new IllegalArgumentException("Must call setPeriod(long) to establish an execution interval for this periodic task.");
            } else if (this.zzao <= 0) {
                throw new IllegalArgumentException(new StringBuilder(66).append("Period set cannot be less than or equal to 0: ").append(this.zzao).toString());
            } else if (this.zzap == -1) {
                this.zzap = (long) (((float) this.zzao) * 0.1f);
            } else if (this.zzap > this.zzao) {
                this.zzap = this.zzao;
            }
        }
    }

    @Deprecated
    private PeriodicTask(Parcel parcel) {
        super(parcel);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = parcel.readLong();
        this.mFlexInSeconds = Math.min(parcel.readLong(), this.mIntervalInSeconds);
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putLong(TypedValues.CycleType.S_WAVE_PERIOD, this.mIntervalInSeconds);
        bundle.putLong("period_flex", this.mFlexInSeconds);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeLong(this.mIntervalInSeconds);
        parcel.writeLong(this.mFlexInSeconds);
    }

    public long getPeriod() {
        return this.mIntervalInSeconds;
    }

    public long getFlex() {
        return this.mFlexInSeconds;
    }

    public String toString() {
        String obj = super.toString();
        long period = getPeriod();
        return new StringBuilder(String.valueOf(obj).length() + 54).append(obj).append(" period=").append(period).append(" flex=").append(getFlex()).toString();
    }

    /* synthetic */ PeriodicTask(Parcel parcel, zzk zzk) {
        this(parcel);
    }

    /* synthetic */ PeriodicTask(Builder builder, zzk zzk) {
        this(builder);
    }
}
