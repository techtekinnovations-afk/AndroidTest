package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewParent;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R;

public class MotionTelltales extends MockView {
    private static final String TAG = "MotionTelltales";
    Matrix mInvertMatrix = new Matrix();
    MotionLayout mMotionLayout;
    private Paint mPaintTelltales = new Paint();
    int mTailColor = -65281;
    float mTailScale = 0.25f;
    float[] mVelocity = new float[2];
    int mVelocityMode = 0;

    public MotionTelltales(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public MotionTelltales(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MotionTelltales(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MotionTelltales);
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.MotionTelltales_telltales_tailColor) {
                    this.mTailColor = a.getColor(attr, this.mTailColor);
                } else if (attr == R.styleable.MotionTelltales_telltales_velocityMode) {
                    this.mVelocityMode = a.getInt(attr, this.mVelocityMode);
                } else if (attr == R.styleable.MotionTelltales_telltales_tailScale) {
                    this.mTailScale = a.getFloat(attr, this.mTailScale);
                }
            }
            a.recycle();
        }
        this.mPaintTelltales.setColor(this.mTailColor);
        this.mPaintTelltales.setStrokeWidth(5.0f);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setText(CharSequence text) {
        this.mText = text.toString();
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        postInvalidate();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getMatrix().invert(this.mInvertMatrix);
        if (this.mMotionLayout == null) {
            ViewParent vp = getParent();
            if (vp instanceof MotionLayout) {
                this.mMotionLayout = (MotionLayout) vp;
                return;
            }
            return;
        }
        int width = getWidth();
        int height = getHeight();
        float[] f = {0.1f, 0.25f, 0.5f, 0.75f, 0.9f};
        for (float py : f) {
            for (float px : f) {
                this.mMotionLayout.getViewVelocity(this, px, py, this.mVelocity, this.mVelocityMode);
                this.mInvertMatrix.mapVectors(this.mVelocity);
                float sx = ((float) width) * px;
                float sy = ((float) height) * py;
                float ex = sx - (this.mVelocity[0] * this.mTailScale);
                float ey = sy - (this.mVelocity[1] * this.mTailScale);
                this.mInvertMatrix.mapVectors(this.mVelocity);
                canvas.drawLine(sx, sy, ex, ey, this.mPaintTelltales);
            }
        }
    }
}
