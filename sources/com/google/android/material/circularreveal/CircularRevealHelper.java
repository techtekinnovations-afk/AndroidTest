package com.google.android.material.circularreveal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.math.MathUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CircularRevealHelper {
    public static final int BITMAP_SHADER = 0;
    public static final int CLIP_PATH = 1;
    private static final boolean DEBUG = false;
    public static final int REVEAL_ANIMATOR = 2;
    public static final int STRATEGY = 2;
    private boolean buildingCircularRevealCache;
    private Paint debugPaint;
    private final Delegate delegate;
    private boolean hasCircularRevealCache;
    private Drawable overlayDrawable;
    private CircularRevealWidget.RevealInfo revealInfo;
    private final Paint revealPaint = new Paint(7);
    private final Path revealPath = new Path();
    private final Paint scrimPaint = new Paint(1);
    private final View view;

    public interface Delegate {
        void actualDraw(Canvas canvas);

        boolean actualIsOpaque();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Strategy {
    }

    public CircularRevealHelper(Delegate delegate2) {
        this.delegate = delegate2;
        this.view = (View) delegate2;
        this.view.setWillNotDraw(false);
        this.scrimPaint.setColor(0);
    }

    public void buildCircularRevealCache() {
    }

    public void destroyCircularRevealCache() {
    }

    public void setRevealInfo(CircularRevealWidget.RevealInfo revealInfo2) {
        if (revealInfo2 == null) {
            this.revealInfo = null;
        } else {
            if (this.revealInfo == null) {
                this.revealInfo = new CircularRevealWidget.RevealInfo(revealInfo2);
            } else {
                this.revealInfo.set(revealInfo2);
            }
            if (MathUtils.geq(revealInfo2.radius, getDistanceToFurthestCorner(revealInfo2), 1.0E-4f)) {
                this.revealInfo.radius = Float.MAX_VALUE;
            }
        }
        invalidateRevealInfo();
    }

    public CircularRevealWidget.RevealInfo getRevealInfo() {
        if (this.revealInfo == null) {
            return null;
        }
        CircularRevealWidget.RevealInfo revealInfo2 = new CircularRevealWidget.RevealInfo(this.revealInfo);
        if (revealInfo2.isInvalid()) {
            revealInfo2.radius = getDistanceToFurthestCorner(revealInfo2);
        }
        return revealInfo2;
    }

    public void setCircularRevealScrimColor(int color) {
        this.scrimPaint.setColor(color);
        this.view.invalidate();
    }

    public int getCircularRevealScrimColor() {
        return this.scrimPaint.getColor();
    }

    public Drawable getCircularRevealOverlayDrawable() {
        return this.overlayDrawable;
    }

    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.overlayDrawable = drawable;
        this.view.invalidate();
    }

    private void invalidateRevealInfo() {
        this.view.invalidate();
    }

    private float getDistanceToFurthestCorner(CircularRevealWidget.RevealInfo revealInfo2) {
        return MathUtils.distanceToFurthestCorner(revealInfo2.centerX, revealInfo2.centerY, 0.0f, 0.0f, (float) this.view.getWidth(), (float) this.view.getHeight());
    }

    public void draw(Canvas canvas) {
        Canvas canvas2;
        if (shouldDrawCircularReveal()) {
            switch (2) {
                case 0:
                    canvas2 = canvas;
                    canvas2.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.revealPaint);
                    if (shouldDrawScrim()) {
                        canvas2.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.scrimPaint);
                        break;
                    }
                    break;
                case 1:
                    canvas2 = canvas;
                    int count = canvas2.save();
                    canvas2.clipPath(this.revealPath);
                    this.delegate.actualDraw(canvas2);
                    if (shouldDrawScrim()) {
                        canvas2.drawRect(0.0f, 0.0f, (float) this.view.getWidth(), (float) this.view.getHeight(), this.scrimPaint);
                    }
                    canvas2.restoreToCount(count);
                    break;
                case 2:
                    this.delegate.actualDraw(canvas);
                    if (!shouldDrawScrim()) {
                        canvas2 = canvas;
                        break;
                    } else {
                        Canvas canvas3 = canvas;
                        canvas3.drawRect(0.0f, 0.0f, (float) this.view.getWidth(), (float) this.view.getHeight(), this.scrimPaint);
                        canvas2 = canvas3;
                        break;
                    }
                default:
                    Canvas canvas4 = canvas;
                    throw new IllegalStateException("Unsupported strategy 2");
            }
        } else {
            canvas2 = canvas;
            this.delegate.actualDraw(canvas2);
            if (shouldDrawScrim()) {
                canvas2.drawRect(0.0f, 0.0f, (float) this.view.getWidth(), (float) this.view.getHeight(), this.scrimPaint);
            }
        }
        drawOverlayDrawable(canvas2);
    }

    private void drawOverlayDrawable(Canvas canvas) {
        if (shouldDrawOverlayDrawable()) {
            Rect bounds = this.overlayDrawable.getBounds();
            float translationX = this.revealInfo.centerX - (((float) bounds.width()) / 2.0f);
            float translationY = this.revealInfo.centerY - (((float) bounds.height()) / 2.0f);
            canvas.translate(translationX, translationY);
            this.overlayDrawable.draw(canvas);
            canvas.translate(-translationX, -translationY);
        }
    }

    public boolean isOpaque() {
        return this.delegate.actualIsOpaque() && !shouldDrawCircularReveal();
    }

    private boolean shouldDrawCircularReveal() {
        if (!(this.revealInfo == null || this.revealInfo.isInvalid())) {
            return true;
        }
        return false;
    }

    private boolean shouldDrawScrim() {
        return !this.buildingCircularRevealCache && Color.alpha(this.scrimPaint.getColor()) != 0;
    }

    private boolean shouldDrawOverlayDrawable() {
        return (this.buildingCircularRevealCache || this.overlayDrawable == null || this.revealInfo == null) ? false : true;
    }

    private void drawDebugMode(Canvas canvas) {
        this.delegate.actualDraw(canvas);
        if (shouldDrawScrim()) {
            canvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius, this.scrimPaint);
        }
        if (shouldDrawCircularReveal()) {
            drawDebugCircle(canvas, ViewCompat.MEASURED_STATE_MASK, 10.0f);
            drawDebugCircle(canvas, SupportMenu.CATEGORY_MASK, 5.0f);
        }
        drawOverlayDrawable(canvas);
    }

    private void drawDebugCircle(Canvas canvas, int color, float width) {
        this.debugPaint.setColor(color);
        this.debugPaint.setStrokeWidth(width);
        canvas.drawCircle(this.revealInfo.centerX, this.revealInfo.centerY, this.revealInfo.radius - (width / 2.0f), this.debugPaint);
    }
}
