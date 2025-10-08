package androidx.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.window.OnBackInvokedDispatcher;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u001b\b\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0003\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001bH\u0015J\b\u0010\u001f\u001a\u00020\u001dH\u0015J\b\u0010 \u001a\u00020\u001dH\u0015J\b\u0010'\u001a\u00020\u001dH\u0017J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\bH\u0016J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020+H\u0016J\u001a\u0010(\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u001a\u0010.\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\b\u0010/\u001a\u00020\u001dH\u0017R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\f8BX\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010!\u001a\u00020\"¢\u0006\u000e\n\u0000\u0012\u0004\b#\u0010$\u001a\u0004\b%\u0010&¨\u00060"}, d2 = {"Landroidx/activity/ComponentDialog;", "Landroid/app/Dialog;", "Landroidx/lifecycle/LifecycleOwner;", "Landroidx/activity/OnBackPressedDispatcherOwner;", "Landroidx/savedstate/SavedStateRegistryOwner;", "context", "Landroid/content/Context;", "themeResId", "", "<init>", "(Landroid/content/Context;I)V", "_lifecycleRegistry", "Landroidx/lifecycle/LifecycleRegistry;", "lifecycleRegistry", "getLifecycleRegistry", "()Landroidx/lifecycle/LifecycleRegistry;", "savedStateRegistryController", "Landroidx/savedstate/SavedStateRegistryController;", "savedStateRegistry", "Landroidx/savedstate/SavedStateRegistry;", "getSavedStateRegistry", "()Landroidx/savedstate/SavedStateRegistry;", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "getLifecycle", "()Landroidx/lifecycle/Lifecycle;", "onSaveInstanceState", "Landroid/os/Bundle;", "onCreate", "", "savedInstanceState", "onStart", "onStop", "onBackPressedDispatcher", "Landroidx/activity/OnBackPressedDispatcher;", "getOnBackPressedDispatcher$annotations", "()V", "getOnBackPressedDispatcher", "()Landroidx/activity/OnBackPressedDispatcher;", "onBackPressed", "setContentView", "layoutResID", "view", "Landroid/view/View;", "params", "Landroid/view/ViewGroup$LayoutParams;", "addContentView", "initializeViewTreeOwners", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: ComponentDialog.kt */
public class ComponentDialog extends Dialog implements LifecycleOwner, OnBackPressedDispatcherOwner, SavedStateRegistryOwner {
    private LifecycleRegistry _lifecycleRegistry;
    private final OnBackPressedDispatcher onBackPressedDispatcher;
    private final SavedStateRegistryController savedStateRegistryController;

    public static /* synthetic */ void getOnBackPressedDispatcher$annotations() {
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ComponentDialog(Context context, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? 0 : i);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ComponentDialog(Context context) {
        this(context, 0, 2, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ComponentDialog(Context context, int themeResId) {
        super(context, themeResId);
        Intrinsics.checkNotNullParameter(context, "context");
        this.savedStateRegistryController = SavedStateRegistryController.Companion.create(this);
        this.onBackPressedDispatcher = new OnBackPressedDispatcher(new ComponentDialog$$ExternalSyntheticLambda0(this));
    }

    private final LifecycleRegistry getLifecycleRegistry() {
        LifecycleRegistry lifecycleRegistry = this._lifecycleRegistry;
        if (lifecycleRegistry != null) {
            return lifecycleRegistry;
        }
        LifecycleRegistry it = new LifecycleRegistry(this);
        this._lifecycleRegistry = it;
        return it;
    }

    public SavedStateRegistry getSavedStateRegistry() {
        return this.savedStateRegistryController.getSavedStateRegistry();
    }

    public Lifecycle getLifecycle() {
        return getLifecycleRegistry();
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = super.onSaveInstanceState();
        Intrinsics.checkNotNullExpressionValue(bundle, "onSaveInstanceState(...)");
        this.savedStateRegistryController.performSave(bundle);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackPressedDispatcher onBackPressedDispatcher2 = this.onBackPressedDispatcher;
            OnBackInvokedDispatcher onBackInvokedDispatcher = getOnBackInvokedDispatcher();
            Intrinsics.checkNotNullExpressionValue(onBackInvokedDispatcher, "getOnBackInvokedDispatcher(...)");
            onBackPressedDispatcher2.setOnBackInvokedDispatcher(onBackInvokedDispatcher);
        }
        this.savedStateRegistryController.performRestore(savedInstanceState);
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        this._lifecycleRegistry = null;
        super.onStop();
    }

    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
        return this.onBackPressedDispatcher;
    }

    /* access modifiers changed from: private */
    public static final void onBackPressedDispatcher$lambda$1(ComponentDialog this$0) {
        super.onBackPressed();
    }

    public void onBackPressed() {
        this.onBackPressedDispatcher.onBackPressed();
    }

    public void setContentView(int layoutResID) {
        initializeViewTreeOwners();
        super.setContentView(layoutResID);
    }

    public void setContentView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        initializeViewTreeOwners();
        super.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        Intrinsics.checkNotNullParameter(view, "view");
        initializeViewTreeOwners();
        super.setContentView(view, params);
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        Intrinsics.checkNotNullParameter(view, "view");
        initializeViewTreeOwners();
        super.addContentView(view, params);
    }

    public void initializeViewTreeOwners() {
        Window window = getWindow();
        Intrinsics.checkNotNull(window);
        View decorView = window.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "getDecorView(...)");
        ViewTreeLifecycleOwner.set(decorView, this);
        Window window2 = getWindow();
        Intrinsics.checkNotNull(window2);
        View decorView2 = window2.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView2, "getDecorView(...)");
        ViewTreeOnBackPressedDispatcherOwner.set(decorView2, this);
        Window window3 = getWindow();
        Intrinsics.checkNotNull(window3);
        View decorView3 = window3.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView3, "getDecorView(...)");
        ViewTreeSavedStateRegistryOwner.set(decorView3, this);
    }
}
