package com.google.android.material.color.utilities;

import androidx.core.view.ViewCompat;

public class ColorUtils {
    static final double[][] SRGB_TO_XYZ = {new double[]{0.41233895d, 0.35762064d, 0.18051042d}, new double[]{0.2126d, 0.7152d, 0.0722d}, new double[]{0.01932141d, 0.11916382d, 0.95034478d}};
    static final double[] WHITE_POINT_D65 = {95.047d, 100.0d, 108.883d};
    static final double[][] XYZ_TO_SRGB = {new double[]{3.2413774792388685d, -1.5376652402851851d, -0.49885366846268053d}, new double[]{-0.9691452513005321d, 1.8758853451067872d, 0.04156585616912061d}, new double[]{0.05562093689691305d, -0.20395524564742123d, 1.0571799111220335d}};

    private ColorUtils() {
    }

    public static int argbFromRgb(int red, int green, int blue) {
        return ((red & 255) << 16) | ViewCompat.MEASURED_STATE_MASK | ((green & 255) << 8) | (blue & 255);
    }

    public static int argbFromLinrgb(double[] linrgb) {
        return argbFromRgb(delinearized(linrgb[0]), delinearized(linrgb[1]), delinearized(linrgb[2]));
    }

    public static int alphaFromArgb(int argb) {
        return (argb >> 24) & 255;
    }

    public static int redFromArgb(int argb) {
        return (argb >> 16) & 255;
    }

    public static int greenFromArgb(int argb) {
        return (argb >> 8) & 255;
    }

    public static int blueFromArgb(int argb) {
        return argb & 255;
    }

    public static boolean isOpaque(int argb) {
        return alphaFromArgb(argb) >= 255;
    }

    public static int argbFromXyz(double x, double y, double z) {
        double[][] matrix = XYZ_TO_SRGB;
        return argbFromRgb(delinearized((matrix[0][0] * x) + (matrix[0][1] * y) + (matrix[0][2] * z)), delinearized((matrix[1][0] * x) + (matrix[1][1] * y) + (matrix[1][2] * z)), delinearized((matrix[2][0] * x) + (matrix[2][1] * y) + (matrix[2][2] * z)));
    }

    public static double[] xyzFromArgb(int argb) {
        return MathUtils.matrixMultiply(new double[]{linearized(redFromArgb(argb)), linearized(greenFromArgb(argb)), linearized(blueFromArgb(argb))}, SRGB_TO_XYZ);
    }

    public static int argbFromLab(double l, double a, double b) {
        double[] whitePoint = WHITE_POINT_D65;
        double fy = (l + 16.0d) / 116.0d;
        return argbFromXyz(labInvf((a / 500.0d) + fy) * whitePoint[0], labInvf(fy) * whitePoint[1], labInvf(fy - (b / 200.0d)) * whitePoint[2]);
    }

    public static double[] labFromArgb(int argb) {
        double linearR = linearized(redFromArgb(argb));
        double linearG = linearized(greenFromArgb(argb));
        double linearB = linearized(blueFromArgb(argb));
        double[][] matrix = SRGB_TO_XYZ;
        double x = (matrix[0][0] * linearR) + (matrix[0][1] * linearG) + (matrix[0][2] * linearB);
        double y = (matrix[1][0] * linearR) + (matrix[1][1] * linearG) + (matrix[1][2] * linearB);
        double z = (matrix[2][0] * linearR) + (matrix[2][1] * linearG) + (matrix[2][2] * linearB);
        double[] whitePoint = WHITE_POINT_D65;
        double fx = labF(x / whitePoint[0]);
        double fy = labF(y / whitePoint[1]);
        return new double[]{(116.0d * fy) - 16.0d, (fx - fy) * 500.0d, (fy - labF(z / whitePoint[2])) * 200.0d};
    }

    public static int argbFromLstar(double lstar) {
        int component = delinearized(yFromLstar(lstar));
        return argbFromRgb(component, component, component);
    }

    public static double lstarFromArgb(int argb) {
        return (labF(xyzFromArgb(argb)[1] / 100.0d) * 116.0d) - 16.0d;
    }

    public static double yFromLstar(double lstar) {
        return labInvf((16.0d + lstar) / 116.0d) * 100.0d;
    }

    public static double lstarFromY(double y) {
        return (labF(y / 100.0d) * 116.0d) - 16.0d;
    }

    public static double linearized(int rgbComponent) {
        double normalized = ((double) rgbComponent) / 255.0d;
        if (normalized <= 0.040449936d) {
            return (normalized / 12.92d) * 100.0d;
        }
        return Math.pow((0.055d + normalized) / 1.055d, 2.4d) * 100.0d;
    }

    public static int delinearized(double rgbComponent) {
        double delinearized;
        double normalized = rgbComponent / 100.0d;
        if (normalized <= 0.0031308d) {
            delinearized = 12.92d * normalized;
        } else {
            delinearized = (Math.pow(normalized, 0.4166666666666667d) * 1.055d) - 0.055d;
        }
        return MathUtils.clampInt(0, 255, (int) Math.round(255.0d * delinearized));
    }

    public static double[] whitePointD65() {
        return WHITE_POINT_D65;
    }

    static double labF(double t) {
        if (t > 0.008856451679035631d) {
            return Math.pow(t, 0.3333333333333333d);
        }
        return ((903.2962962962963d * t) + 16.0d) / 116.0d;
    }

    static double labInvf(double ft) {
        double ft3 = ft * ft * ft;
        if (ft3 > 0.008856451679035631d) {
            return ft3;
        }
        return ((116.0d * ft) - 16.0d) / 903.2962962962963d;
    }
}
