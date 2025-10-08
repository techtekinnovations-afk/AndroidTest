package com.google.android.material.shape;

public class RoundedCornerTreatment extends CornerTreatment {
    float radius = -1.0f;

    public RoundedCornerTreatment() {
    }

    @Deprecated
    public RoundedCornerTreatment(float radius2) {
        this.radius = radius2;
    }

    public void getCornerPath(ShapePath shapePath, float angle, float interpolation, float radius2) {
        float radius3 = radius2 * interpolation;
        shapePath.reset(0.0f, radius3, 180.0f, 180.0f - angle);
        shapePath.addArc(0.0f, 0.0f, radius3 * 2.0f, radius3 * 2.0f, 180.0f, angle);
    }
}
