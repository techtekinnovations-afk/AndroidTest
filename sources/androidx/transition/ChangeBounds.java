package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import java.util.Map;

public class ChangeBounds extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight") {
        public void set(View view, PointF bottomRight) {
            ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(bottomRight.x), Math.round(bottomRight.y));
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight") {
        public void set(ViewBounds viewBounds, PointF bottomRight) {
            viewBounds.setBottomRight(bottomRight);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static final Property<View, PointF> POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position") {
        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            ViewUtils.setLeftTopRightBottom(view, left, top, view.getWidth() + left, view.getHeight() + top);
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft") {
        public void set(View view, PointF topLeft) {
            ViewUtils.setLeftTopRightBottom(view, Math.round(topLeft.x), Math.round(topLeft.y), view.getRight(), view.getBottom());
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft") {
        public void set(ViewBounds viewBounds, PointF topLeft) {
            viewBounds.setTopLeft(topLeft);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static final RectEvaluator sRectEvaluator = new RectEvaluator();
    private static final String[] sTransitionProperties = {PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    private boolean mResizeClip = false;

    public ChangeBounds() {
    }

    public ChangeBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.CHANGE_BOUNDS);
        boolean resizeClip = TypedArrayUtils.getNamedBoolean(a, (XmlResourceParser) attrs, "resizeClip", 0, false);
        a.recycle();
        setResizeClip(resizeClip);
    }

    public boolean isSeekingSupported() {
        return true;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean resizeClip) {
        this.mResizeClip = resizeClip;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (view.isLaidOut() || view.getWidth() != 0 || view.getHeight() != 0) {
            values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            values.values.put(PROPNAME_PARENT, values.view.getParent());
            if (this.mResizeClip) {
                values.values.put(PROPNAME_CLIP, view.getClipBounds());
            }
        }
    }

    public void captureStartValues(TransitionValues transitionValues) {
        Rect clipSize;
        captureValues(transitionValues);
        if (this.mResizeClip && (clipSize = (Rect) transitionValues.view.getTag(R.id.transition_clip)) != null) {
            transitionValues.values.put(PROPNAME_CLIP, clipSize);
        }
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        boolean z;
        ObjectAnimator anim;
        int endBottom;
        int startBottom;
        Rect startClip;
        Rect endClip;
        Rect endClip2;
        int startWidth;
        int startHeight;
        TransitionValues transitionValues = startValues;
        TransitionValues transitionValues2 = endValues;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        Map<String, Object> startParentVals = transitionValues.values;
        Map<String, Object> endParentVals = transitionValues2.values;
        ViewGroup startParent = (ViewGroup) startParentVals.get(PROPNAME_PARENT);
        ViewGroup endParent = (ViewGroup) endParentVals.get(PROPNAME_PARENT);
        if (startParent == null) {
            Map<String, Object> map = endParentVals;
            ViewGroup viewGroup = endParent;
            ViewGroup viewGroup2 = startParent;
            return null;
        } else if (endParent == null) {
            Map<String, Object> map2 = startParentVals;
            Map<String, Object> map3 = endParentVals;
            ViewGroup viewGroup3 = endParent;
            ViewGroup viewGroup4 = startParent;
            return null;
        } else {
            View view = transitionValues2.view;
            Rect startBounds = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
            Rect endBounds = (Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
            int startLeft = startBounds.left;
            int endLeft = endBounds.left;
            int startTop = startBounds.top;
            int endTop = endBounds.top;
            int startRight = startBounds.right;
            int endRight = endBounds.right;
            Map<String, Object> map4 = startParentVals;
            int startBottom2 = startBounds.bottom;
            Map<String, Object> map5 = endParentVals;
            int endBottom2 = endBounds.bottom;
            ViewGroup viewGroup5 = endParent;
            int startWidth2 = startRight - startLeft;
            ViewGroup viewGroup6 = startParent;
            Rect startBounds2 = startBounds;
            int endWidth = endRight - endLeft;
            Rect endBounds2 = endBounds;
            int startHeight2 = startBottom2 - startTop;
            Rect startClip2 = (Rect) transitionValues.values.get(PROPNAME_CLIP);
            int endHeight = endBottom2 - endTop;
            Rect endClip3 = (Rect) transitionValues2.values.get(PROPNAME_CLIP);
            int numChanges = 0;
            if (!((startWidth2 == 0 || startHeight2 == 0) && (endWidth == 0 || endHeight == 0))) {
                if (!(startLeft == endLeft && startTop == endTop)) {
                    numChanges = 0 + 1;
                }
                if (!(startRight == endRight && startBottom2 == endBottom2)) {
                    numChanges++;
                }
            }
            if ((startClip2 != null && !startClip2.equals(endClip3)) || (startClip2 == null && endClip3 != null)) {
                numChanges++;
            }
            if (numChanges <= 0) {
                return null;
            }
            Rect endClip4 = endClip3;
            if (!this.mResizeClip) {
                ViewUtils.setLeftTopRightBottom(view, startLeft, startTop, startRight, startBottom2);
                z = true;
                if (numChanges == 2) {
                    if (startWidth2 == endWidth) {
                        int endHeight2 = endHeight;
                        int startHeight3 = startHeight2;
                        if (startHeight3 == endHeight2) {
                            Rect rect = startClip2;
                            int i = numChanges;
                            int i2 = endHeight2;
                            int startWidth3 = startWidth2;
                            int i3 = endBottom2;
                            int i4 = endWidth;
                            int i5 = startRight;
                            Rect rect2 = startBounds2;
                            Rect rect3 = endBounds2;
                            int i6 = startHeight3;
                            int i7 = startWidth3;
                            int endWidth2 = endRight;
                            int startWidth4 = startBottom2;
                            int i8 = endLeft;
                            int startBottom3 = endTop;
                            anim = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                            Rect rect4 = endClip4;
                        } else {
                            startHeight = startHeight3;
                            int i9 = endHeight2;
                            startWidth = startWidth2;
                            Rect rect5 = startClip2;
                            int i10 = numChanges;
                        }
                    } else {
                        startWidth = startWidth2;
                        int i11 = endHeight;
                        startHeight = startHeight2;
                        Rect rect6 = startClip2;
                        int startHeight4 = numChanges;
                    }
                    final ViewBounds viewBounds = new ViewBounds(view);
                    int i12 = endWidth;
                    Path topLeftPath = getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop);
                    Path path = topLeftPath;
                    ObjectAnimator topLeftAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, TOP_LEFT_PROPERTY, topLeftPath);
                    ObjectAnimator bottomRightAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, BOTTOM_RIGHT_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom2, (float) endRight, (float) endBottom2));
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(new Animator[]{topLeftAnimator, bottomRightAnimator});
                    set.addListener(new AnimatorListenerAdapter() {
                        private final ViewBounds mViewBounds = viewBounds;
                    });
                    int i13 = endRight;
                    anim = set;
                    int i14 = endBottom2;
                    int i15 = startRight;
                    Rect rect7 = startBounds2;
                    Rect rect8 = endBounds2;
                    Rect rect9 = endClip4;
                    int i16 = startHeight;
                    int i17 = startWidth;
                    int startWidth5 = startBottom2;
                    int i18 = endLeft;
                    int startBottom4 = endTop;
                } else {
                    int startWidth6 = startWidth2;
                    int i19 = endWidth;
                    int i20 = endHeight;
                    int startHeight5 = startHeight2;
                    Rect rect10 = startClip2;
                    int startHeight6 = numChanges;
                    if (startLeft == endLeft && startTop == endTop) {
                        int i21 = endRight;
                        int i22 = endBottom2;
                        int i23 = startRight;
                        Rect rect11 = startBounds2;
                        Rect rect12 = endBounds2;
                        int i24 = startHeight5;
                        int i25 = startWidth6;
                        anim = ObjectAnimatorUtils.ofPointF(view, BOTTOM_RIGHT_ONLY_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom2, (float) endRight, (float) endBottom2));
                        int startWidth7 = startBottom2;
                        int i26 = endLeft;
                        int startBottom5 = endTop;
                        Rect rect13 = endClip4;
                    } else {
                        int i27 = endRight;
                        int i28 = endBottom2;
                        int i29 = startRight;
                        Rect rect14 = startBounds2;
                        Rect rect15 = endBounds2;
                        int i30 = startHeight5;
                        int i31 = startWidth6;
                        anim = ObjectAnimatorUtils.ofPointF(view, TOP_LEFT_ONLY_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                        int startWidth8 = startBottom2;
                        int i32 = endLeft;
                        int startBottom6 = endTop;
                        Rect rect16 = endClip4;
                    }
                }
            } else {
                int endHeight3 = endHeight;
                int startHeight7 = startHeight2;
                z = true;
                Rect startClip3 = startClip2;
                int startHeight8 = numChanges;
                int startWidth9 = startWidth2;
                int endWidth3 = endWidth;
                int maxWidth = Math.max(startWidth9, endWidth3);
                int startHeight9 = startHeight7;
                int endHeight4 = endHeight3;
                int endRight2 = endRight;
                ViewUtils.setLeftTopRightBottom(view, startLeft, startTop, startLeft + maxWidth, startTop + Math.max(startHeight9, endHeight4));
                ObjectAnimator positionAnimator = null;
                if (startLeft == endLeft && startTop == endTop) {
                    startBottom = startBottom2;
                    endBottom = endBottom2;
                    int i33 = maxWidth;
                } else {
                    startBottom = startBottom2;
                    endBottom = endBottom2;
                    int i34 = maxWidth;
                    positionAnimator = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft, (float) startTop, (float) endLeft, (float) endTop));
                }
                boolean startClipIsNull = startClip3 == null;
                if (startClipIsNull) {
                    startClip = new Rect(0, 0, startWidth9, startHeight9);
                } else {
                    startClip = startClip3;
                }
                int endClipIsNull = endClip4 == null;
                if (endClipIsNull != 0) {
                    int i35 = startWidth9;
                    endClip = new Rect(0, 0, endWidth3, endHeight4);
                } else {
                    endClip = endClip4;
                }
                ObjectAnimator clipAnimator = null;
                if (!startClip.equals(endClip)) {
                    view.setClipBounds(startClip);
                    int endWidth4 = endWidth3;
                    clipAnimator = ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, new Object[]{startClip, endClip});
                    int endTop2 = endTop;
                    endClip2 = endClip;
                    int endRight3 = endRight2;
                    int endRight4 = endHeight4;
                    Rect rect17 = endBounds2;
                    int endLeft2 = endLeft;
                    boolean startClipIsNull2 = startClipIsNull;
                    int endBottom3 = endBottom;
                    int endBottom4 = endWidth4;
                    int endWidth5 = startRight;
                    boolean endClipIsNull2 = endClipIsNull;
                    int i36 = endWidth5;
                    int i37 = endTop2;
                    int i38 = endRight3;
                    Rect rect18 = startBounds2;
                    ClipListener listener = new ClipListener(view, startClip, startClipIsNull2, endClip2, endClipIsNull2, startLeft, startTop, endWidth5, startBottom, endLeft2, endTop2, endRight3, endBottom3);
                    clipAnimator.addListener(listener);
                    addListener(listener);
                } else {
                    int endBottom5 = startRight;
                    int startRight2 = endClipIsNull;
                    int i39 = endBottom5;
                    int i40 = endRight2;
                    int endRight5 = endHeight4;
                    int endHeight5 = i40;
                    Rect rect19 = startBounds2;
                    int i41 = endBottom;
                    int endBottom6 = endWidth3;
                    Rect rect20 = endBounds2;
                    int i42 = endLeft;
                    boolean endLeft3 = startClipIsNull;
                    int i43 = endTop;
                    endClip2 = endClip;
                }
                anim = TransitionUtils.mergeAnimators(positionAnimator, clipAnimator);
                Rect rect21 = startClip;
                Rect rect22 = endClip2;
            }
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) view.getParent();
                ViewGroupUtils.suppressLayout(parent, z);
                getRootTransition().addListener(new SuppressLayoutListener(parent));
            }
            return anim;
        }
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private final View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        /* access modifiers changed from: package-private */
        public void setTopLeft(PointF topLeft) {
            this.mLeft = Math.round(topLeft.x);
            this.mTop = Math.round(topLeft.y);
            this.mTopLeftCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        /* access modifiers changed from: package-private */
        public void setBottomRight(PointF bottomRight) {
            this.mRight = Math.round(bottomRight.x);
            this.mBottom = Math.round(bottomRight.y);
            this.mBottomRightCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }
    }

    private static class ClipListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        private final int mEndBottom;
        private final Rect mEndClip;
        private final boolean mEndClipIsNull;
        private final int mEndLeft;
        private final int mEndRight;
        private final int mEndTop;
        private boolean mIsCanceled;
        private final int mStartBottom;
        private final Rect mStartClip;
        private final boolean mStartClipIsNull;
        private final int mStartLeft;
        private final int mStartRight;
        private final int mStartTop;
        private final View mView;

        ClipListener(View view, Rect startClip, boolean startClipIsNull, Rect endClip, boolean endClipIsNull, int startLeft, int startTop, int startRight, int startBottom, int endLeft, int endTop, int endRight, int endBottom) {
            this.mView = view;
            this.mStartClip = startClip;
            this.mStartClipIsNull = startClipIsNull;
            this.mEndClip = endClip;
            this.mEndClipIsNull = endClipIsNull;
            this.mStartLeft = startLeft;
            this.mStartTop = startTop;
            this.mStartRight = startRight;
            this.mStartBottom = startBottom;
            this.mEndLeft = endLeft;
            this.mEndTop = endTop;
            this.mEndRight = endRight;
            this.mEndBottom = endBottom;
        }

        public void onAnimationStart(Animator animation) {
            onAnimationStart(animation, false);
        }

        public void onAnimationEnd(Animator animation) {
            onAnimationEnd(animation, false);
        }

        public void onAnimationStart(Animator animation, boolean isReverse) {
            int maxWidth = Math.max(this.mStartRight - this.mStartLeft, this.mEndRight - this.mEndLeft);
            int maxHeight = Math.max(this.mStartBottom - this.mStartTop, this.mEndBottom - this.mEndTop);
            int left = isReverse ? this.mEndLeft : this.mStartLeft;
            int top = isReverse ? this.mEndTop : this.mStartTop;
            ViewUtils.setLeftTopRightBottom(this.mView, left, top, left + maxWidth, top + maxHeight);
            this.mView.setClipBounds(isReverse ? this.mEndClip : this.mStartClip);
        }

        public void onAnimationEnd(Animator animation, boolean isReverse) {
            if (!this.mIsCanceled) {
                Rect clip = null;
                if (isReverse) {
                    if (!this.mStartClipIsNull) {
                        clip = this.mStartClip;
                    }
                } else if (!this.mEndClipIsNull) {
                    clip = this.mEndClip;
                }
                this.mView.setClipBounds(clip);
                if (isReverse) {
                    ViewUtils.setLeftTopRightBottom(this.mView, this.mStartLeft, this.mStartTop, this.mStartRight, this.mStartBottom);
                } else {
                    ViewUtils.setLeftTopRightBottom(this.mView, this.mEndLeft, this.mEndTop, this.mEndRight, this.mEndBottom);
                }
            }
        }

        public void onTransitionCancel(Transition transition) {
            this.mIsCanceled = true;
        }

        public void onTransitionPause(Transition transition) {
            this.mView.setTag(R.id.transition_clip, this.mView.getClipBounds());
            this.mView.setClipBounds(this.mEndClipIsNull ? null : this.mEndClip);
        }

        public void onTransitionResume(Transition transition) {
            this.mView.setTag(R.id.transition_clip, (Object) null);
            this.mView.setClipBounds((Rect) this.mView.getTag(R.id.transition_clip));
        }

        public void onTransitionStart(Transition transition) {
        }

        public void onTransitionEnd(Transition transition) {
        }
    }

    private static class SuppressLayoutListener extends TransitionListenerAdapter {
        boolean mCanceled = false;
        final ViewGroup mParent;

        SuppressLayoutListener(ViewGroup parent) {
            this.mParent = parent;
        }

        public void onTransitionCancel(Transition transition) {
            ViewGroupUtils.suppressLayout(this.mParent, false);
            this.mCanceled = true;
        }

        public void onTransitionEnd(Transition transition) {
            if (!this.mCanceled) {
                ViewGroupUtils.suppressLayout(this.mParent, false);
            }
            transition.removeListener(this);
        }

        public void onTransitionPause(Transition transition) {
            ViewGroupUtils.suppressLayout(this.mParent, false);
        }

        public void onTransitionResume(Transition transition) {
            ViewGroupUtils.suppressLayout(this.mParent, true);
        }
    }
}
