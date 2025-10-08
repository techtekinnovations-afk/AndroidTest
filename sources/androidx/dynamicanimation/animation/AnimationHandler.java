package androidx.dynamicanimation.animation;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Choreographer;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;

public class AnimationHandler {
    private static final ThreadLocal<AnimationHandler> sAnimatorHandler = new ThreadLocal<>();
    final ArrayList<AnimationFrameCallback> mAnimationCallbacks = new ArrayList<>();
    private final AnimationCallbackDispatcher mCallbackDispatcher = new AnimationCallbackDispatcher();
    long mCurrentFrameTime = 0;
    private final SimpleArrayMap<AnimationFrameCallback, Long> mDelayedCallbackStartTime = new SimpleArrayMap<>();
    public float mDurationScale = 1.0f;
    public DurationScaleChangeListener mDurationScaleChangeListener;
    private boolean mListDirty = false;
    /* access modifiers changed from: private */
    public final Runnable mRunnable = new AnimationHandler$$ExternalSyntheticLambda0(this);
    /* access modifiers changed from: private */
    public FrameCallbackScheduler mScheduler;

    interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    public interface DurationScaleChangeListener {
        boolean register();

        boolean unregister();
    }

    private class AnimationCallbackDispatcher {
        private AnimationCallbackDispatcher() {
        }

        /* access modifiers changed from: package-private */
        public void dispatchAnimationFrame() {
            AnimationHandler.this.mCurrentFrameTime = SystemClock.uptimeMillis();
            AnimationHandler.this.doAnimationFrame(AnimationHandler.this.mCurrentFrameTime);
            if (AnimationHandler.this.mAnimationCallbacks.size() > 0) {
                AnimationHandler.this.mScheduler.postFrameCallback(AnimationHandler.this.mRunnable);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$androidx-dynamicanimation-animation-AnimationHandler  reason: not valid java name */
    public /* synthetic */ void m2016lambda$new$0$androidxdynamicanimationanimationAnimationHandler() {
        this.mCallbackDispatcher.dispatchAnimationFrame();
    }

    static AnimationHandler getInstance() {
        if (sAnimatorHandler.get() == null) {
            sAnimatorHandler.set(new AnimationHandler(new FrameCallbackScheduler16()));
        }
        return sAnimatorHandler.get();
    }

    public AnimationHandler(FrameCallbackScheduler scheduler) {
        this.mScheduler = scheduler;
    }

    /* access modifiers changed from: package-private */
    public void addAnimationFrameCallback(AnimationFrameCallback callback, long delay) {
        if (this.mAnimationCallbacks.size() == 0) {
            this.mScheduler.postFrameCallback(this.mRunnable);
            if (Build.VERSION.SDK_INT >= 33) {
                this.mDurationScale = ValueAnimator.getDurationScale();
                if (this.mDurationScaleChangeListener == null) {
                    this.mDurationScaleChangeListener = new DurationScaleChangeListener33();
                }
                this.mDurationScaleChangeListener.register();
            }
        }
        if (!this.mAnimationCallbacks.contains(callback)) {
            this.mAnimationCallbacks.add(callback);
        }
        if (delay > 0) {
            this.mDelayedCallbackStartTime.put(callback, Long.valueOf(SystemClock.uptimeMillis() + delay));
        }
    }

    /* access modifiers changed from: package-private */
    public void removeCallback(AnimationFrameCallback callback) {
        this.mDelayedCallbackStartTime.remove(callback);
        int id = this.mAnimationCallbacks.indexOf(callback);
        if (id >= 0) {
            this.mAnimationCallbacks.set(id, (Object) null);
            this.mListDirty = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void doAnimationFrame(long frameTime) {
        long currentTime = SystemClock.uptimeMillis();
        for (int i = 0; i < this.mAnimationCallbacks.size(); i++) {
            AnimationFrameCallback callback = this.mAnimationCallbacks.get(i);
            if (callback != null && isCallbackDue(callback, currentTime)) {
                callback.doAnimationFrame(frameTime);
            }
        }
        cleanUpList();
    }

    /* access modifiers changed from: package-private */
    public boolean isCurrentThread() {
        return this.mScheduler.isCurrentThread();
    }

    private boolean isCallbackDue(AnimationFrameCallback callback, long currentTime) {
        Long startTime = this.mDelayedCallbackStartTime.get(callback);
        if (startTime == null) {
            return true;
        }
        if (startTime.longValue() >= currentTime) {
            return false;
        }
        this.mDelayedCallbackStartTime.remove(callback);
        return true;
    }

    private void cleanUpList() {
        if (this.mListDirty) {
            for (int i = this.mAnimationCallbacks.size() - 1; i >= 0; i--) {
                if (this.mAnimationCallbacks.get(i) == null) {
                    this.mAnimationCallbacks.remove(i);
                }
            }
            if (this.mAnimationCallbacks.size() == 0 && Build.VERSION.SDK_INT >= 33) {
                this.mDurationScaleChangeListener.unregister();
            }
            this.mListDirty = false;
        }
    }

    /* access modifiers changed from: package-private */
    public FrameCallbackScheduler getScheduler() {
        return this.mScheduler;
    }

    static final class FrameCallbackScheduler16 implements FrameCallbackScheduler {
        private final Choreographer mChoreographer = Choreographer.getInstance();
        private final Looper mLooper = Looper.myLooper();

        FrameCallbackScheduler16() {
        }

        public void postFrameCallback(Runnable frameCallback) {
            this.mChoreographer.postFrameCallback(new AnimationHandler$FrameCallbackScheduler16$$ExternalSyntheticLambda0(frameCallback));
        }

        public boolean isCurrentThread() {
            return Thread.currentThread() == this.mLooper.getThread();
        }
    }

    public float getDurationScale() {
        return this.mDurationScale;
    }

    public class DurationScaleChangeListener33 implements DurationScaleChangeListener {
        ValueAnimator.DurationScaleChangeListener mListener;

        public DurationScaleChangeListener33() {
        }

        public boolean register() {
            if (this.mListener != null) {
                return true;
            }
            this.mListener = new AnimationHandler$DurationScaleChangeListener33$$ExternalSyntheticLambda0(this);
            return ValueAnimator.registerDurationScaleChangeListener(this.mListener);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$register$0$androidx-dynamicanimation-animation-AnimationHandler$DurationScaleChangeListener33  reason: not valid java name */
        public /* synthetic */ void m2017lambda$register$0$androidxdynamicanimationanimationAnimationHandler$DurationScaleChangeListener33(float scale) {
            AnimationHandler.this.mDurationScale = scale;
        }

        public boolean unregister() {
            boolean unregistered = ValueAnimator.unregisterDurationScaleChangeListener(this.mListener);
            this.mListener = null;
            return unregistered;
        }
    }
}
