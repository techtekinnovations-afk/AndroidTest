package androidx.activity;

import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0007\u001a\u00020\u0003H\u0016J\b\u0010\b\u001a\u00020\u0003H\u0016¨\u0006\t"}, d2 = {"androidx/activity/OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1", "Landroid/window/OnBackAnimationCallback;", "onBackStarted", "", "backEvent", "Landroid/window/BackEvent;", "onBackProgressed", "onBackInvoked", "onBackCancelled", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: OnBackPressedDispatcher.kt */
public final class OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1 implements OnBackAnimationCallback {
    final /* synthetic */ Function0<Unit> $onBackCancelled;
    final /* synthetic */ Function0<Unit> $onBackInvoked;
    final /* synthetic */ Function1<BackEventCompat, Unit> $onBackProgressed;
    final /* synthetic */ Function1<BackEventCompat, Unit> $onBackStarted;

    OnBackPressedDispatcher$Api34Impl$createOnBackAnimationCallback$1(Function1<? super BackEventCompat, Unit> $onBackStarted2, Function1<? super BackEventCompat, Unit> $onBackProgressed2, Function0<Unit> $onBackInvoked2, Function0<Unit> $onBackCancelled2) {
        this.$onBackStarted = $onBackStarted2;
        this.$onBackProgressed = $onBackProgressed2;
        this.$onBackInvoked = $onBackInvoked2;
        this.$onBackCancelled = $onBackCancelled2;
    }

    public void onBackStarted(BackEvent backEvent) {
        Intrinsics.checkNotNullParameter(backEvent, "backEvent");
        this.$onBackStarted.invoke(new BackEventCompat(backEvent));
    }

    public void onBackProgressed(BackEvent backEvent) {
        Intrinsics.checkNotNullParameter(backEvent, "backEvent");
        this.$onBackProgressed.invoke(new BackEventCompat(backEvent));
    }

    public void onBackInvoked() {
        this.$onBackInvoked.invoke();
    }

    public void onBackCancelled() {
        this.$onBackCancelled.invoke();
    }
}
