package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
public abstract class GmsClient<T extends IInterface> extends BaseGmsClient<T> implements Api.Client, zaj {
    private static volatile Executor zaa;
    private final ClientSettings zab;
    private final Set zac;
    private final Account zad;

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected GmsClient(Context context, Handler handler, int gCoreServiceId, ClientSettings clientSettings) {
        super(context, handler, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), gCoreServiceId, (BaseGmsClient.BaseConnectionCallbacks) null, (BaseGmsClient.BaseOnConnectionFailedListener) null);
        this.zab = (ClientSettings) Preconditions.checkNotNull(clientSettings);
        this.zad = clientSettings.getAccount();
        this.zac = zaa(clientSettings.getAllRequestedScopes());
    }

    private final Set zaa(Set set) {
        Set<Scope> validateScopes = validateScopes(set);
        for (Scope contains : validateScopes) {
            if (!set.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        return validateScopes;
    }

    public final Account getAccount() {
        return this.zad;
    }

    /* access modifiers changed from: protected */
    public final Executor getBindServiceExecutor() {
        return null;
    }

    /* access modifiers changed from: protected */
    public final ClientSettings getClientSettings() {
        return this.zab;
    }

    public Feature[] getRequiredFeatures() {
        return new Feature[0];
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> getScopes() {
        return this.zac;
    }

    public Set<Scope> getScopesForConnectionlessNonSignIn() {
        return requiresSignIn() ? this.zac : Collections.emptySet();
    }

    /* access modifiers changed from: protected */
    public Set<Scope> validateScopes(Set<Scope> set) {
        return set;
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    protected GmsClient(Context context, Looper looper, int gCoreServiceId, ClientSettings clientSettings) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), gCoreServiceId, clientSettings, (ConnectionCallbacks) null, (OnConnectionFailedListener) null);
    }

    @Deprecated
    protected GmsClient(Context context, Looper looper, int gCoreServiceId, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectedListener, GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
        this(context, looper, gCoreServiceId, clientSettings, (ConnectionCallbacks) connectedListener, (OnConnectionFailedListener) connectionFailedListener);
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    protected GmsClient(Context context, Looper looper, int gCoreServiceId, ClientSettings clientSettings, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), gCoreServiceId, clientSettings, (ConnectionCallbacks) Preconditions.checkNotNull(connectedListener), (OnConnectionFailedListener) Preconditions.checkNotNull(connectionFailedListener));
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected GmsClient(android.content.Context r10, android.os.Looper r11, com.google.android.gms.common.internal.GmsClientSupervisor r12, com.google.android.gms.common.GoogleApiAvailability r13, int r14, com.google.android.gms.common.internal.ClientSettings r15, com.google.android.gms.common.api.internal.ConnectionCallbacks r16, com.google.android.gms.common.api.internal.OnConnectionFailedListener r17) {
        /*
            r9 = this;
            r0 = r16
            r1 = r17
            r2 = 0
            if (r0 != 0) goto L_0x0009
            r6 = r2
            goto L_0x000f
        L_0x0009:
            com.google.android.gms.common.internal.zah r3 = new com.google.android.gms.common.internal.zah
            r3.<init>(r0)
            r6 = r3
        L_0x000f:
            if (r1 != 0) goto L_0x0013
            r7 = r2
            goto L_0x0019
        L_0x0013:
            com.google.android.gms.common.internal.zai r2 = new com.google.android.gms.common.internal.zai
            r2.<init>(r1)
            r7 = r2
        L_0x0019:
            java.lang.String r8 = r15.zac()
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r9.zab = r15
            android.accounts.Account r2 = r15.getAccount()
            r9.zad = r2
            java.util.Set r1 = r15.getAllRequestedScopes()
            java.util.Set r1 = r9.zaa(r1)
            r9.zac = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.GmsClient.<init>(android.content.Context, android.os.Looper, com.google.android.gms.common.internal.GmsClientSupervisor, com.google.android.gms.common.GoogleApiAvailability, int, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.common.api.internal.ConnectionCallbacks, com.google.android.gms.common.api.internal.OnConnectionFailedListener):void");
    }
}
