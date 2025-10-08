package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Visibility extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String[] sTransitionProperties = {PROPNAME_VISIBILITY, PROPNAME_PARENT};
    private int mMode = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    private static class VisibilityInfo {
        ViewGroup mEndParent;
        int mEndVisibility;
        boolean mFadeIn;
        ViewGroup mStartParent;
        int mStartVisibility;
        boolean mVisibilityChange;

        VisibilityInfo() {
        }
    }

    public Visibility() {
    }

    public Visibility(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.VISIBILITY_TRANSITION);
        int mode = TypedArrayUtils.getNamedInt(a, (XmlResourceParser) attrs, "transitionVisibilityMode", 0, 0);
        a.recycle();
        if (mode != 0) {
            setMode(mode);
        }
    }

    public void setMode(int mode) {
        if ((mode & -4) == 0) {
            this.mMode = mode;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    public int getMode() {
        return this.mMode;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
        int[] loc = new int[2];
        transitionValues.view.getLocationOnScreen(loc);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, loc);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public boolean isVisible(TransitionValues values) {
        if (values == null) {
            return false;
        }
        int visibility = ((Integer) values.values.get(PROPNAME_VISIBILITY)).intValue();
        View parent = (View) values.values.get(PROPNAME_PARENT);
        if (visibility != 0 || parent == null) {
            return false;
        }
        return true;
    }

    private VisibilityInfo getVisibilityChangeInfo(TransitionValues startValues, TransitionValues endValues) {
        VisibilityInfo visInfo = new VisibilityInfo();
        visInfo.mVisibilityChange = false;
        visInfo.mFadeIn = false;
        if (startValues == null || !startValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visInfo.mStartVisibility = -1;
            visInfo.mStartParent = null;
        } else {
            visInfo.mStartVisibility = ((Integer) startValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visInfo.mStartParent = (ViewGroup) startValues.values.get(PROPNAME_PARENT);
        }
        if (endValues == null || !endValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visInfo.mEndVisibility = -1;
            visInfo.mEndParent = null;
        } else {
            visInfo.mEndVisibility = ((Integer) endValues.values.get(PROPNAME_VISIBILITY)).intValue();
            visInfo.mEndParent = (ViewGroup) endValues.values.get(PROPNAME_PARENT);
        }
        if (startValues == null || endValues == null) {
            if (startValues == null && visInfo.mEndVisibility == 0) {
                visInfo.mFadeIn = true;
                visInfo.mVisibilityChange = true;
            } else if (endValues == null && visInfo.mStartVisibility == 0) {
                visInfo.mFadeIn = false;
                visInfo.mVisibilityChange = true;
            }
        } else if (visInfo.mStartVisibility == visInfo.mEndVisibility && visInfo.mStartParent == visInfo.mEndParent) {
            return visInfo;
        } else {
            if (visInfo.mStartVisibility != visInfo.mEndVisibility) {
                if (visInfo.mStartVisibility == 0) {
                    visInfo.mFadeIn = false;
                    visInfo.mVisibilityChange = true;
                } else if (visInfo.mEndVisibility == 0) {
                    visInfo.mFadeIn = true;
                    visInfo.mVisibilityChange = true;
                }
            } else if (visInfo.mEndParent == null) {
                visInfo.mFadeIn = false;
                visInfo.mVisibilityChange = true;
            } else if (visInfo.mStartParent == null) {
                visInfo.mFadeIn = true;
                visInfo.mVisibilityChange = true;
            }
        }
        return visInfo;
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        VisibilityInfo visInfo = getVisibilityChangeInfo(startValues, endValues);
        if (!visInfo.mVisibilityChange) {
            TransitionValues transitionValues = startValues;
            TransitionValues transitionValues2 = endValues;
            return null;
        } else if (visInfo.mStartParent == null && visInfo.mEndParent == null) {
            ViewGroup viewGroup = sceneRoot;
            TransitionValues transitionValues3 = startValues;
            TransitionValues transitionValues4 = endValues;
            return null;
        } else if (visInfo.mFadeIn) {
            ViewGroup sceneRoot2 = sceneRoot;
            TransitionValues startValues2 = startValues;
            TransitionValues endValues2 = endValues;
            Animator onAppear = onAppear(sceneRoot2, startValues2, visInfo.mStartVisibility, endValues2, visInfo.mEndVisibility);
            ViewGroup viewGroup2 = sceneRoot2;
            TransitionValues transitionValues5 = startValues2;
            TransitionValues transitionValues6 = endValues2;
            return onAppear;
        } else {
            return onDisappear(sceneRoot, startValues, visInfo.mStartVisibility, endValues, visInfo.mEndVisibility);
        }
    }

    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        if ((this.mMode & 1) != 1 || endValues == null) {
            return null;
        }
        if (startValues == null) {
            View endParent = (View) endValues.view.getParent();
            if (getVisibilityChangeInfo(getMatchedTransitionValues(endParent, false), getTransitionValues(endParent, false)).mVisibilityChange) {
                return null;
            }
        }
        return onAppear(sceneRoot, endValues.view, startValues, endValues);
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return null;
    }

    /* JADX WARNING: type inference failed for: r17v0 */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r17v2 */
    /* JADX WARNING: type inference failed for: r17v4 */
    /* JADX WARNING: type inference failed for: r17v5 */
    /* JADX WARNING: type inference failed for: r17v6 */
    /* JADX WARNING: type inference failed for: r17v7 */
    /* JADX WARNING: type inference failed for: r17v8 */
    /* JADX WARNING: type inference failed for: r17v9 */
    /* JADX WARNING: type inference failed for: r17v10 */
    /* JADX WARNING: type inference failed for: r17v11 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.animation.Animator onDisappear(android.view.ViewGroup r21, androidx.transition.TransitionValues r22, int r23, androidx.transition.TransitionValues r24, int r25) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = r24
            r4 = r25
            int r5 = r0.mMode
            r6 = 2
            r5 = r5 & r6
            r7 = 0
            if (r5 == r6) goto L_0x0012
            return r7
        L_0x0012:
            if (r2 != 0) goto L_0x0015
            return r7
        L_0x0015:
            android.view.View r5 = r2.view
            if (r3 == 0) goto L_0x001c
            android.view.View r8 = r3.view
            goto L_0x001d
        L_0x001c:
            r8 = r7
        L_0x001d:
            r9 = 0
            r10 = 0
            r11 = 0
            int r12 = androidx.transition.R.id.save_overlay_view
            java.lang.Object r12 = r5.getTag(r12)
            android.view.View r12 = (android.view.View) r12
            r13 = 1
            if (r12 == 0) goto L_0x0033
            r9 = r12
            r11 = 1
            r16 = r7
            r17 = r13
            goto L_0x00ac
        L_0x0033:
            r14 = 0
            if (r8 == 0) goto L_0x0048
            android.view.ViewParent r15 = r8.getParent()
            if (r15 != 0) goto L_0x003d
            goto L_0x0048
        L_0x003d:
            r15 = 4
            if (r4 != r15) goto L_0x0042
            r10 = r8
            goto L_0x004d
        L_0x0042:
            if (r5 != r8) goto L_0x0046
            r10 = r8
            goto L_0x004d
        L_0x0046:
            r14 = 1
            goto L_0x004d
        L_0x0048:
            if (r8 == 0) goto L_0x004c
            r9 = r8
            goto L_0x004d
        L_0x004c:
            r14 = 1
        L_0x004d:
            if (r14 == 0) goto L_0x00a8
            android.view.ViewParent r15 = r5.getParent()
            if (r15 != 0) goto L_0x005b
            r9 = r5
            r16 = r7
            r17 = r13
            goto L_0x00ac
        L_0x005b:
            android.view.ViewParent r15 = r5.getParent()
            boolean r15 = r15 instanceof android.view.View
            if (r15 == 0) goto L_0x00a3
            android.view.ViewParent r15 = r5.getParent()
            android.view.View r15 = (android.view.View) r15
            r16 = r7
            androidx.transition.TransitionValues r7 = r0.getTransitionValues(r15, r13)
            androidx.transition.TransitionValues r6 = r0.getMatchedTransitionValues(r15, r13)
            r17 = r13
            androidx.transition.Visibility$VisibilityInfo r13 = r0.getVisibilityChangeInfo(r7, r6)
            r18 = r6
            boolean r6 = r13.mVisibilityChange
            if (r6 != 0) goto L_0x0085
            android.view.View r9 = androidx.transition.TransitionUtils.copyViewImage(r1, r5, r15)
            goto L_0x00ac
        L_0x0085:
            int r6 = r15.getId()
            android.view.ViewParent r19 = r15.getParent()
            if (r19 != 0) goto L_0x00a0
            r19 = r7
            r7 = -1
            if (r6 == r7) goto L_0x00ac
            android.view.View r7 = r1.findViewById(r6)
            if (r7 == 0) goto L_0x00ac
            boolean r7 = r0.mCanRemoveViews
            if (r7 == 0) goto L_0x00ac
            r9 = r5
            goto L_0x00ac
        L_0x00a0:
            r19 = r7
            goto L_0x00ac
        L_0x00a3:
            r16 = r7
            r17 = r13
            goto L_0x00ac
        L_0x00a8:
            r16 = r7
            r17 = r13
        L_0x00ac:
            r6 = 0
            if (r9 == 0) goto L_0x010e
            if (r11 != 0) goto L_0x00e6
            java.util.Map<java.lang.String, java.lang.Object> r7 = r2.values
            java.lang.String r13 = "android:visibility:screenLocation"
            java.lang.Object r7 = r7.get(r13)
            int[] r7 = (int[]) r7
            r13 = r7[r6]
            r14 = r7[r17]
            r15 = 2
            int[] r15 = new int[r15]
            r1.getLocationOnScreen(r15)
            r6 = r15[r6]
            int r6 = r13 - r6
            int r16 = r9.getLeft()
            int r6 = r6 - r16
            r9.offsetLeftAndRight(r6)
            r6 = r15[r17]
            int r6 = r14 - r6
            int r16 = r9.getTop()
            int r6 = r6 - r16
            r9.offsetTopAndBottom(r6)
            android.view.ViewGroupOverlay r6 = r1.getOverlay()
            r6.add(r9)
        L_0x00e6:
            android.animation.Animator r6 = r0.onDisappear(r1, r9, r2, r3)
            if (r11 != 0) goto L_0x010d
            if (r6 != 0) goto L_0x00f6
            android.view.ViewGroupOverlay r7 = r1.getOverlay()
            r7.remove(r9)
            goto L_0x010d
        L_0x00f6:
            int r7 = androidx.transition.R.id.save_overlay_view
            r5.setTag(r7, r9)
            androidx.transition.Visibility$OverlayListener r7 = new androidx.transition.Visibility$OverlayListener
            r7.<init>(r1, r9, r5)
            r6.addListener(r7)
            r6.addPauseListener(r7)
            androidx.transition.Transition r13 = r0.getRootTransition()
            r13.addListener(r7)
        L_0x010d:
            return r6
        L_0x010e:
            if (r10 == 0) goto L_0x0133
            int r7 = r10.getVisibility()
            androidx.transition.ViewUtils.setTransitionVisibility(r10, r6)
            android.animation.Animator r6 = r0.onDisappear(r1, r10, r2, r3)
            if (r6 == 0) goto L_0x012f
            androidx.transition.Visibility$DisappearListener r13 = new androidx.transition.Visibility$DisappearListener
            r14 = r17
            r13.<init>(r10, r4, r14)
            r6.addListener(r13)
            androidx.transition.Transition r14 = r0.getRootTransition()
            r14.addListener(r13)
            goto L_0x0132
        L_0x012f:
            androidx.transition.ViewUtils.setTransitionVisibility(r10, r7)
        L_0x0132:
            return r6
        L_0x0133:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.Visibility.onDisappear(android.view.ViewGroup, androidx.transition.TransitionValues, int, androidx.transition.TransitionValues, int):android.animation.Animator");
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return null;
    }

    public boolean isTransitionRequired(TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null && endValues == null) {
            return false;
        }
        if (startValues != null && endValues != null && endValues.values.containsKey(PROPNAME_VISIBILITY) != startValues.values.containsKey(PROPNAME_VISIBILITY)) {
            return false;
        }
        VisibilityInfo changeInfo = getVisibilityChangeInfo(startValues, endValues);
        if (!changeInfo.mVisibilityChange) {
            return false;
        }
        if (changeInfo.mStartVisibility == 0 || changeInfo.mEndVisibility == 0) {
            return true;
        }
        return false;
    }

    private static class DisappearListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final View mView;

        DisappearListener(View view, int finalVisibility, boolean suppressLayout) {
            this.mView = view;
            this.mFinalVisibility = finalVisibility;
            this.mParent = (ViewGroup) view.getParent();
            this.mSuppressLayout = suppressLayout;
            suppressLayout(true);
        }

        public void onAnimationCancel(Animator animation) {
            this.mCanceled = true;
        }

        public void onAnimationRepeat(Animator animation) {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            hideViewWhenNotCanceled();
        }

        public void onAnimationStart(Animator animation, boolean isReverse) {
            if (isReverse) {
                ViewUtils.setTransitionVisibility(this.mView, 0);
                if (this.mParent != null) {
                    this.mParent.invalidate();
                }
            }
        }

        public void onAnimationEnd(Animator animation, boolean isReverse) {
            if (!isReverse) {
                hideViewWhenNotCanceled();
            }
        }

        public void onTransitionStart(Transition transition) {
        }

        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
            suppressLayout(false);
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
            }
        }

        public void onTransitionResume(Transition transition) {
            suppressLayout(true);
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, 0);
            }
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                if (this.mParent != null) {
                    this.mParent.invalidate();
                }
            }
            suppressLayout(false);
        }

        private void suppressLayout(boolean suppress) {
            if (this.mSuppressLayout && this.mLayoutSuppressed != suppress && this.mParent != null) {
                this.mLayoutSuppressed = suppress;
                ViewGroupUtils.suppressLayout(this.mParent, suppress);
            }
        }
    }

    private class OverlayListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        private boolean mHasOverlay = true;
        private final ViewGroup mOverlayHost;
        private final View mOverlayView;
        private final View mStartView;

        OverlayListener(ViewGroup overlayHost, View overlayView, View startView) {
            this.mOverlayHost = overlayHost;
            this.mOverlayView = overlayView;
            this.mStartView = startView;
        }

        public void onAnimationPause(Animator animation) {
            this.mOverlayHost.getOverlay().remove(this.mOverlayView);
        }

        public void onAnimationResume(Animator animation) {
            if (this.mOverlayView.getParent() == null) {
                this.mOverlayHost.getOverlay().add(this.mOverlayView);
            } else {
                Visibility.this.cancel();
            }
        }

        public void onAnimationStart(Animator animation, boolean isReverse) {
            if (isReverse) {
                this.mStartView.setTag(R.id.save_overlay_view, this.mOverlayView);
                this.mOverlayHost.getOverlay().add(this.mOverlayView);
                this.mHasOverlay = true;
            }
        }

        public void onAnimationEnd(Animator animation) {
            removeFromOverlay();
        }

        public void onAnimationEnd(Animator animation, boolean isReverse) {
            if (!isReverse) {
                removeFromOverlay();
            }
        }

        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
        }

        public void onTransitionStart(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }

        public void onTransitionCancel(Transition transition) {
            if (this.mHasOverlay) {
                removeFromOverlay();
            }
        }

        private void removeFromOverlay() {
            this.mStartView.setTag(R.id.save_overlay_view, (Object) null);
            this.mOverlayHost.getOverlay().remove(this.mOverlayView);
            this.mHasOverlay = false;
        }
    }
}
