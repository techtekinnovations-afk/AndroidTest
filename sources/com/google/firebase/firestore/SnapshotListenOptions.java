package com.google.firebase.firestore;

import android.app.Activity;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Preconditions;
import java.util.concurrent.Executor;

public final class SnapshotListenOptions {
    private final Activity activity;
    private final Executor executor;
    private final MetadataChanges metadataChanges;
    private final ListenSource source;

    private SnapshotListenOptions(Builder builder) {
        this.metadataChanges = builder.metadataChanges;
        this.source = builder.source;
        this.executor = builder.executor;
        this.activity = builder.activity;
    }

    public MetadataChanges getMetadataChanges() {
        return this.metadataChanges;
    }

    public ListenSource getSource() {
        return this.source;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public Activity activity = null;
        /* access modifiers changed from: private */
        public Executor executor = Executors.DEFAULT_CALLBACK_EXECUTOR;
        /* access modifiers changed from: private */
        public MetadataChanges metadataChanges = MetadataChanges.EXCLUDE;
        /* access modifiers changed from: private */
        public ListenSource source = ListenSource.DEFAULT;

        public Builder setMetadataChanges(MetadataChanges metadataChanges2) {
            Preconditions.checkNotNull(metadataChanges2, "metadataChanges must not be null.");
            this.metadataChanges = metadataChanges2;
            return this;
        }

        public Builder setSource(ListenSource source2) {
            Preconditions.checkNotNull(source2, "listen source must not be null.");
            this.source = source2;
            return this;
        }

        public Builder setExecutor(Executor executor2) {
            Preconditions.checkNotNull(executor2, "executor must not be null.");
            this.executor = executor2;
            return this;
        }

        public Builder setActivity(Activity activity2) {
            Preconditions.checkNotNull(activity2, "activity must not be null.");
            this.activity = activity2;
            return this;
        }

        public SnapshotListenOptions build() {
            return new SnapshotListenOptions(this);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SnapshotListenOptions that = (SnapshotListenOptions) o;
        if (this.metadataChanges != that.metadataChanges || this.source != that.source || !this.executor.equals(that.executor) || !this.activity.equals(that.activity)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.metadataChanges.hashCode() * 31) + this.source.hashCode()) * 31) + this.executor.hashCode()) * 31) + (this.activity != null ? this.activity.hashCode() : 0);
    }

    public String toString() {
        return "SnapshotListenOptions{metadataChanges=" + this.metadataChanges + ", source=" + this.source + ", executor=" + this.executor + ", activity=" + this.activity + '}';
    }
}
