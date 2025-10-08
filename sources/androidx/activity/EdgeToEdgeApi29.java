package androidx.activity;

import android.view.View;
import android.view.Window;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0013\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J8\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0017¨\u0006\u0010"}, d2 = {"Landroidx/activity/EdgeToEdgeApi29;", "Landroidx/activity/EdgeToEdgeApi28;", "<init>", "()V", "setUp", "", "statusBarStyle", "Landroidx/activity/SystemBarStyle;", "navigationBarStyle", "window", "Landroid/view/Window;", "view", "Landroid/view/View;", "statusBarIsDark", "", "navigationBarIsDark", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: EdgeToEdge.kt */
class EdgeToEdgeApi29 extends EdgeToEdgeApi28 {
    public void setUp(SystemBarStyle statusBarStyle, SystemBarStyle navigationBarStyle, Window window, View view, boolean statusBarIsDark, boolean navigationBarIsDark) {
        Intrinsics.checkNotNullParameter(statusBarStyle, "statusBarStyle");
        Intrinsics.checkNotNullParameter(navigationBarStyle, "navigationBarStyle");
        Intrinsics.checkNotNullParameter(window, "window");
        Intrinsics.checkNotNullParameter(view, "view");
        boolean z = false;
        WindowCompat.setDecorFitsSystemWindows(window, false);
        window.setStatusBarColor(statusBarStyle.getScrimWithEnforcedContrast$activity_release(statusBarIsDark));
        window.setNavigationBarColor(navigationBarStyle.getScrimWithEnforcedContrast$activity_release(navigationBarIsDark));
        window.setStatusBarContrastEnforced(false);
        if (navigationBarStyle.getNightMode$activity_release() == 0) {
            z = true;
        }
        window.setNavigationBarContrastEnforced(z);
        WindowInsetsControllerCompat $this$setUp_u24lambda_u240 = new WindowInsetsControllerCompat(window, view);
        $this$setUp_u24lambda_u240.setAppearanceLightStatusBars(!statusBarIsDark);
        $this$setUp_u24lambda_u240.setAppearanceLightNavigationBars(!navigationBarIsDark);
    }
}
