package com.google.android.material.animation;

import android.graphics.drawable.Drawable;
import android.util.Property;

public class DrawableAlphaProperty extends Property<Drawable, Integer> {
    public static final Property<Drawable, Integer> DRAWABLE_ALPHA_COMPAT = new DrawableAlphaProperty();

    private DrawableAlphaProperty() {
        super(Integer.class, "drawableAlphaCompat");
    }

    public Integer get(Drawable object) {
        return Integer.valueOf(object.getAlpha());
    }

    public void set(Drawable object, Integer value) {
        object.setAlpha(value.intValue());
    }
}
