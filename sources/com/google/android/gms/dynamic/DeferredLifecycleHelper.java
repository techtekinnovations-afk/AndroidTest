package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zac;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.LinkedList;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
public abstract class DeferredLifecycleHelper<T extends LifecycleDelegate> {
    /* access modifiers changed from: private */
    public LifecycleDelegate zaa;
    /* access modifiers changed from: private */
    public Bundle zab;
    /* access modifiers changed from: private */
    public LinkedList zac;
    private final OnDelegateCreatedListener zad = new zaa(this);

    public static void showGooglePlayUnavailableMessage(FrameLayout parent) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        Context context = parent.getContext();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(context);
        String zad2 = zac.zad(context, isGooglePlayServicesAvailable);
        String zac2 = zac.zac(context, isGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        parent.addView(linearLayout);
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(zad2);
        linearLayout.addView(textView);
        Intent errorResolutionIntent = instance.getErrorResolutionIntent(context, isGooglePlayServicesAvailable, (String) null);
        if (errorResolutionIntent != null) {
            Button button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(zac2);
            linearLayout.addView(button);
            button.setOnClickListener(new zae(context, errorResolutionIntent));
        }
    }

    private final void zae(int i) {
        while (!this.zac.isEmpty() && ((zah) this.zac.getLast()).zaa() >= i) {
            this.zac.removeLast();
        }
    }

    private final void zaf(Bundle bundle, zah zah) {
        LifecycleDelegate lifecycleDelegate = this.zaa;
        if (lifecycleDelegate != null) {
            zah.zab(lifecycleDelegate);
            return;
        }
        if (this.zac == null) {
            this.zac = new LinkedList();
        }
        this.zac.add(zah);
        if (bundle != null) {
            Bundle bundle2 = this.zab;
            if (bundle2 == null) {
                this.zab = (Bundle) bundle.clone();
            } else {
                bundle2.putAll(bundle);
            }
        }
        createDelegate(this.zad);
    }

    /* access modifiers changed from: protected */
    public abstract void createDelegate(OnDelegateCreatedListener<T> onDelegateCreatedListener);

    public T getDelegate() {
        return this.zaa;
    }

    /* access modifiers changed from: protected */
    public void handleGooglePlayUnavailable(FrameLayout parent) {
        showGooglePlayUnavailableMessage(parent);
    }

    public void onCreate(Bundle savedInstanceState) {
        zaf(savedInstanceState, new zac(this, savedInstanceState));
    }

    /* Debug info: failed to restart local var, previous not found, register: 6 */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        Bundle savedInstanceState2 = savedInstanceState;
        zaf(savedInstanceState2, new zad(this, frameLayout, inflater, container, savedInstanceState2));
        if (this.zaa == null) {
            handleGooglePlayUnavailable(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        LifecycleDelegate lifecycleDelegate = this.zaa;
        if (lifecycleDelegate != null) {
            lifecycleDelegate.onDestroy();
        } else {
            zae(1);
        }
    }

    public void onDestroyView() {
        LifecycleDelegate lifecycleDelegate = this.zaa;
        if (lifecycleDelegate != null) {
            lifecycleDelegate.onDestroyView();
        } else {
            zae(2);
        }
    }

    public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
        zaf(savedInstanceState, new zab(this, activity, attrs, savedInstanceState));
    }

    public void onLowMemory() {
        LifecycleDelegate lifecycleDelegate = this.zaa;
        if (lifecycleDelegate != null) {
            lifecycleDelegate.onLowMemory();
        }
    }

    public void onPause() {
        LifecycleDelegate lifecycleDelegate = this.zaa;
        if (lifecycleDelegate != null) {
            lifecycleDelegate.onPause();
        } else {
            zae(5);
        }
    }

    public void onResume() {
        zaf((Bundle) null, new zag(this));
    }

    public void onSaveInstanceState(Bundle outState) {
        LifecycleDelegate lifecycleDelegate = this.zaa;
        if (lifecycleDelegate != null) {
            lifecycleDelegate.onSaveInstanceState(outState);
            return;
        }
        Bundle bundle = this.zab;
        if (bundle != null) {
            outState.putAll(bundle);
        }
    }

    public void onStart() {
        zaf((Bundle) null, new zaf(this));
    }

    public void onStop() {
        LifecycleDelegate lifecycleDelegate = this.zaa;
        if (lifecycleDelegate != null) {
            lifecycleDelegate.onStop();
        } else {
            zae(4);
        }
    }
}
