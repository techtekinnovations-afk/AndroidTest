package com.google.android.material.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;

public class ScaledDrawableWrapper extends DrawableWrapperCompat {
    private boolean mutated;
    private ScaledDrawableWrapperState state;

    public ScaledDrawableWrapper(Drawable drawable, int width, int height) {
        super(drawable);
        this.state = new ScaledDrawableWrapperState(getConstantStateFrom(drawable), width, height);
    }

    private Drawable.ConstantState getConstantStateFrom(Drawable drawable) {
        if (drawable != null) {
            return drawable.getConstantState();
        }
        return null;
    }

    public int getIntrinsicWidth() {
        return this.state.width;
    }

    public int getIntrinsicHeight() {
        return this.state.height;
    }

    public void setDrawable(Drawable drawable) {
        super.setDrawable(drawable);
        if (this.state != null) {
            Drawable.ConstantState unused = this.state.wrappedDrawableState = getConstantStateFrom(drawable);
            this.mutated = false;
        }
    }

    public Drawable.ConstantState getConstantState() {
        if (this.state.canConstantState()) {
            return this.state;
        }
        return null;
    }

    public Drawable mutate() {
        if (!this.mutated && super.mutate() == this) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                drawable.mutate();
            }
            this.state = new ScaledDrawableWrapperState(getConstantStateFrom(drawable), this.state.width, this.state.height);
            this.mutated = true;
        }
        return this;
    }

    private static final class ScaledDrawableWrapperState extends Drawable.ConstantState {
        /* access modifiers changed from: private */
        public final int height;
        /* access modifiers changed from: private */
        public final int width;
        /* access modifiers changed from: private */
        public Drawable.ConstantState wrappedDrawableState;

        ScaledDrawableWrapperState(Drawable.ConstantState wrappedDrawableState2, int width2, int height2) {
            this.wrappedDrawableState = wrappedDrawableState2;
            this.width = width2;
            this.height = height2;
        }

        public Drawable newDrawable() {
            return new ScaledDrawableWrapper(this.wrappedDrawableState.newDrawable(), this.width, this.height);
        }

        public Drawable newDrawable(Resources res) {
            return new ScaledDrawableWrapper(this.wrappedDrawableState.newDrawable(res), this.width, this.height);
        }

        public Drawable newDrawable(Resources res, Resources.Theme theme) {
            return new ScaledDrawableWrapper(this.wrappedDrawableState.newDrawable(res, theme), this.width, this.height);
        }

        public int getChangingConfigurations() {
            if (this.wrappedDrawableState != null) {
                return this.wrappedDrawableState.getChangingConfigurations();
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public boolean canConstantState() {
            return this.wrappedDrawableState != null;
        }
    }
}
