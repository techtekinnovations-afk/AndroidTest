package com.google.android.gms.common.moduleinstall;

import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
public final class ModuleInstallRequest {
    private final List zaa;
    private final InstallStatusListener zab;
    private final Executor zac;
    private final boolean zad;

    /* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
    public static class Builder {
        private final List zaa = new ArrayList();
        private boolean zab = true;
        private InstallStatusListener zac;
        private Executor zad;

        public Builder addApi(OptionalModuleApi api) {
            this.zaa.add(api);
            return this;
        }

        public ModuleInstallRequest build() {
            return new ModuleInstallRequest(this.zaa, this.zac, this.zad, this.zab, (zac) null);
        }

        public Builder setListener(InstallStatusListener listener) {
            return setListener(listener, (Executor) null);
        }

        public Builder setListener(InstallStatusListener installStatusListener, Executor executor) {
            this.zac = installStatusListener;
            this.zad = executor;
            return this;
        }
    }

    /* synthetic */ ModuleInstallRequest(List list, InstallStatusListener installStatusListener, Executor executor, boolean z, zac zac2) {
        Preconditions.checkNotNull(list, "APIs must not be null.");
        Preconditions.checkArgument(!list.isEmpty(), "APIs must not be empty.");
        if (executor != null) {
            Preconditions.checkNotNull(installStatusListener, "Listener must not be null when listener executor is set.");
        }
        this.zaa = list;
        this.zab = installStatusListener;
        this.zac = executor;
        this.zad = z;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<OptionalModuleApi> getApis() {
        return this.zaa;
    }

    public InstallStatusListener getListener() {
        return this.zab;
    }

    public Executor getListenerExecutor() {
        return this.zac;
    }

    public final boolean zaa() {
        return this.zad;
    }
}
