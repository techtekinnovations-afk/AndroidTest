package androidx.activity;

import android.window.BackEvent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\bÁ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J.\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005¨\u0006\u000f"}, d2 = {"Landroidx/activity/Api36Impl;", "", "<init>", "()V", "createOnBackEvent", "Landroid/window/BackEvent;", "touchX", "", "touchY", "progress", "swipeEdge", "", "frameTimeMillis", "", "backEvent", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: BackEventCompat.kt */
public final class Api36Impl {
    public static final Api36Impl INSTANCE = new Api36Impl();

    private Api36Impl() {
    }

    public final BackEvent createOnBackEvent(float touchX, float touchY, float progress, int swipeEdge, long frameTimeMillis) {
        return new BackEvent(touchX, touchY, progress, swipeEdge, frameTimeMillis);
    }

    public final long frameTimeMillis(BackEvent backEvent) {
        Intrinsics.checkNotNullParameter(backEvent, "backEvent");
        return backEvent.getFrameTimeMillis();
    }
}
