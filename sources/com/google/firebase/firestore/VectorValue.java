package com.google.firebase.firestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VectorValue {
    private final double[] values;

    VectorValue(double[] values2) {
        this.values = values2 == null ? new double[0] : (double[]) values2.clone();
    }

    public double[] toArray() {
        return (double[]) this.values.clone();
    }

    /* access modifiers changed from: package-private */
    public List<Double> toList() {
        ArrayList<Double> result = new ArrayList<>(this.values.length);
        for (int i = 0; i < this.values.length; i++) {
            result.add(i, Double.valueOf(this.values[i]));
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.values, ((VectorValue) obj).values);
    }

    public int hashCode() {
        return Arrays.hashCode(this.values);
    }
}
