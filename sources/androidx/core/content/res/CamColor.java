package androidx.core.content.res;

import androidx.core.graphics.ColorUtils;

public class CamColor {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;
    private final float mJ;
    private final float mJstar;
    private final float mM;
    private final float mQ;
    private final float mS;

    /* access modifiers changed from: package-private */
    public float getHue() {
        return this.mHue;
    }

    /* access modifiers changed from: package-private */
    public float getChroma() {
        return this.mChroma;
    }

    /* access modifiers changed from: package-private */
    public float getJ() {
        return this.mJ;
    }

    /* access modifiers changed from: package-private */
    public float getQ() {
        return this.mQ;
    }

    /* access modifiers changed from: package-private */
    public float getM() {
        return this.mM;
    }

    /* access modifiers changed from: package-private */
    public float getS() {
        return this.mS;
    }

    /* access modifiers changed from: package-private */
    public float getJStar() {
        return this.mJstar;
    }

    /* access modifiers changed from: package-private */
    public float getAStar() {
        return this.mAstar;
    }

    /* access modifiers changed from: package-private */
    public float getBStar() {
        return this.mBstar;
    }

    CamColor(float hue, float chroma, float j, float q, float m, float s, float jStar, float aStar, float bStar) {
        this.mHue = hue;
        this.mChroma = chroma;
        this.mJ = j;
        this.mQ = q;
        this.mM = m;
        this.mS = s;
        this.mJstar = jStar;
        this.mAstar = aStar;
        this.mBstar = bStar;
    }

    public static int toColor(float hue, float chroma, float lStar) {
        return toColor(hue, chroma, lStar, ViewingConditions.DEFAULT);
    }

    static CamColor fromColor(int color) {
        float[] outCamColor = new float[7];
        float[] outM3HCT = new float[3];
        fromColorInViewingConditions(color, ViewingConditions.DEFAULT, outCamColor, outM3HCT);
        return new CamColor(outM3HCT[0], outM3HCT[1], outCamColor[0], outCamColor[1], outCamColor[2], outCamColor[3], outCamColor[4], outCamColor[5], outCamColor[6]);
    }

    public static void getM3HCTfromColor(int color, float[] outM3HCT) {
        fromColorInViewingConditions(color, ViewingConditions.DEFAULT, (float[]) null, outM3HCT);
        outM3HCT[2] = CamUtils.lStarFromInt(color);
    }

    static void fromColorInViewingConditions(int color, ViewingConditions viewingConditions, float[] outCamColor, float[] outM3HCT) {
        float f;
        CamUtils.xyzFromInt(color, outM3HCT);
        float[] xyz = outM3HCT;
        float[][] matrix = CamUtils.XYZ_TO_CAM16RGB;
        float rT = (xyz[0] * matrix[0][0]) + (xyz[1] * matrix[0][1]) + (xyz[2] * matrix[0][2]);
        float gT = (xyz[0] * matrix[1][0]) + (xyz[1] * matrix[1][1]) + (xyz[2] * matrix[1][2]);
        float bT = (xyz[0] * matrix[2][0]) + (xyz[1] * matrix[2][1]) + (xyz[2] * matrix[2][2]);
        float rD = viewingConditions.getRgbD()[0] * rT;
        float gD = viewingConditions.getRgbD()[1] * gT;
        float bD = viewingConditions.getRgbD()[2] * bT;
        float f2 = rT;
        float rAF = (float) Math.pow(((double) (viewingConditions.getFl() * Math.abs(rD))) / 100.0d, 0.42d);
        float gAF = (float) Math.pow(((double) (viewingConditions.getFl() * Math.abs(gD))) / 100.0d, 0.42d);
        float bAF = (float) Math.pow(((double) (viewingConditions.getFl() * Math.abs(bD))) / 100.0d, 0.42d);
        float rA = ((Math.signum(rD) * 400.0f) * rAF) / (rAF + 27.13f);
        float gA = ((Math.signum(gD) * 400.0f) * gAF) / (gAF + 27.13f);
        float bA = ((Math.signum(bD) * 400.0f) * bAF) / (27.13f + bAF);
        float f3 = bAF;
        float rA2 = rA;
        float a = ((float) (((((double) rA) * 11.0d) + (((double) gA) * -12.0d)) + ((double) bA))) / 11.0f;
        float[] fArr = xyz;
        float b = ((float) (((double) (rA2 + gA)) - (((double) bA) * 2.0d))) / 9.0f;
        float[][] fArr2 = matrix;
        float u = (((rA2 * 20.0f) + (gA * 20.0f)) + (21.0f * bA)) / 20.0f;
        float p2 = (((40.0f * rA2) + (gA * 20.0f)) + bA) / 20.0f;
        float atan2 = (float) Math.atan2((double) b, (double) a);
        float atanDegrees = (atan2 * 180.0f) / 3.1415927f;
        if (atanDegrees < 0.0f) {
            f = atanDegrees + 360.0f;
        } else {
            f = atanDegrees >= 360.0f ? atanDegrees - 360.0f : atanDegrees;
        }
        float hue = f;
        float hueRadians = (3.1415927f * hue) / 180.0f;
        float ac = viewingConditions.getNbb() * p2;
        float a2 = a;
        float b2 = b;
        float f4 = atan2;
        float f5 = ac;
        float j = ((float) Math.pow((double) (ac / viewingConditions.getAw()), (double) (viewingConditions.getC() * viewingConditions.getZ()))) * 100.0f;
        float f6 = atanDegrees;
        float q = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt((double) (j / 100.0f))) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float hue2 = hue;
        float huePrime = ((double) hue2) < 20.14d ? hue2 + 360.0f : hue2;
        float hue3 = hue2;
        float q2 = q;
        float eHue = ((float) (Math.cos(((((double) huePrime) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f;
        float f7 = eHue;
        float t = ((((3846.1538f * eHue) * viewingConditions.getNc()) * viewingConditions.getNcb()) * ((float) Math.sqrt((double) ((a2 * a2) + (b2 * b2))))) / (u + 0.305f);
        float f8 = huePrime;
        float q3 = q2;
        float f9 = gA;
        float f10 = gAF;
        float f11 = t;
        float alpha = ((float) Math.pow(1.64d - Math.pow(0.29d, (double) viewingConditions.getN()), 0.73d)) * ((float) Math.pow((double) t, 0.9d));
        float c = ((float) Math.sqrt(((double) j) / 100.0d)) * alpha;
        float m = viewingConditions.getFlRoot() * c;
        float s = ((float) Math.sqrt((double) ((viewingConditions.getC() * alpha) / (viewingConditions.getAw() + 4.0f)))) * 50.0f;
        float jstar = (1.7f * j) / ((0.007f * j) + 1.0f);
        float j2 = j;
        float mstar = ((float) Math.log((double) ((0.0228f * m) + 1.0f))) * 43.85965f;
        float astar = ((float) Math.cos((double) hueRadians)) * mstar;
        float bstar = ((float) Math.sin((double) hueRadians)) * mstar;
        outM3HCT[0] = hue3;
        outM3HCT[1] = c;
        if (outCamColor != null) {
            outCamColor[0] = j2;
            outCamColor[1] = q3;
            outCamColor[2] = m;
            outCamColor[3] = s;
            outCamColor[4] = jstar;
            outCamColor[5] = astar;
            outCamColor[6] = bstar;
        }
    }

    private static CamColor fromJch(float j, float c, float h) {
        return fromJchInFrame(j, c, h, ViewingConditions.DEFAULT);
    }

    private static CamColor fromJchInFrame(float j, float c, float h, ViewingConditions viewingConditions) {
        float q = (4.0f / viewingConditions.getC()) * ((float) Math.sqrt(((double) j) / 100.0d)) * (viewingConditions.getAw() + 4.0f) * viewingConditions.getFlRoot();
        float m = c * viewingConditions.getFlRoot();
        float s = ((float) Math.sqrt((double) ((viewingConditions.getC() * (c / ((float) Math.sqrt(((double) j) / 100.0d)))) / (viewingConditions.getAw() + 4.0f)))) * 50.0f;
        float hueRadians = (3.1415927f * h) / 180.0f;
        float jstar = (1.7f * j) / ((0.007f * j) + 1.0f);
        float mstar = ((float) Math.log((((double) m) * 0.0228d) + 1.0d)) * 43.85965f;
        return new CamColor(h, c, j, q, m, s, jstar, mstar * ((float) Math.cos((double) hueRadians)), mstar * ((float) Math.sin((double) hueRadians)));
    }

    /* access modifiers changed from: package-private */
    public float distance(CamColor other) {
        float dJ = getJStar() - other.getJStar();
        float dA = getAStar() - other.getAStar();
        float dB = getBStar() - other.getBStar();
        return (float) (Math.pow(Math.sqrt((double) ((dJ * dJ) + (dA * dA) + (dB * dB))), 0.63d) * 1.41d);
    }

    /* access modifiers changed from: package-private */
    public int viewedInSrgb() {
        return viewed(ViewingConditions.DEFAULT);
    }

    /* access modifiers changed from: package-private */
    public int viewed(ViewingConditions viewingConditions) {
        float alpha;
        if (((double) getChroma()) == 0.0d || ((double) getJ()) == 0.0d) {
            alpha = 0.0f;
        } else {
            alpha = getChroma() / ((float) Math.sqrt(((double) getJ()) / 100.0d));
        }
        float t = (float) Math.pow(((double) alpha) / Math.pow(1.64d - Math.pow(0.29d, (double) viewingConditions.getN()), 0.73d), 1.1111111111111112d);
        float hRad = (getHue() * 3.1415927f) / 180.0f;
        float ac = viewingConditions.getAw() * ((float) Math.pow(((double) getJ()) / 100.0d, (1.0d / ((double) viewingConditions.getC())) / ((double) viewingConditions.getZ())));
        float p1 = 3846.1538f * ((float) (Math.cos(((double) hRad) + 2.0d) + 3.8d)) * 0.25f * viewingConditions.getNc() * viewingConditions.getNcb();
        float p2 = ac / viewingConditions.getNbb();
        float hSin = (float) Math.sin((double) hRad);
        float hCos = (float) Math.cos((double) hRad);
        float gamma = (((0.305f + p2) * 23.0f) * t) / (((23.0f * p1) + ((11.0f * t) * hCos)) + ((108.0f * t) * hSin));
        float a = gamma * hCos;
        float b = gamma * hSin;
        float rA = (((p2 * 460.0f) + (451.0f * a)) + (288.0f * b)) / 1403.0f;
        float gA = (((p2 * 460.0f) - (891.0f * a)) - (261.0f * b)) / 1403.0f;
        float bA = (((460.0f * p2) - (220.0f * a)) - (6300.0f * b)) / 1403.0f;
        float f = alpha;
        float f2 = t;
        float rCBase = (float) Math.max(0.0d, (((double) Math.abs(rA)) * 27.13d) / (400.0d - ((double) Math.abs(rA))));
        float signum = Math.signum(rA) * (100.0f / viewingConditions.getFl());
        float f3 = rCBase;
        float pow = (float) Math.pow((double) rCBase, 2.380952380952381d);
        float p12 = p1;
        float rC = pow * signum;
        float gCBase = (float) Math.max(0.0d, (((double) Math.abs(gA)) * 27.13d) / (400.0d - ((double) Math.abs(gA))));
        float f4 = gCBase;
        float gC = Math.signum(gA) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((double) gCBase, 2.380952380952381d));
        float bCBase = (float) Math.max(0.0d, (((double) Math.abs(bA)) * 27.13d) / (400.0d - ((double) Math.abs(bA))));
        float f5 = bCBase;
        float bC = Math.signum(bA) * (100.0f / viewingConditions.getFl()) * ((float) Math.pow((double) bCBase, 2.380952380952381d));
        float rF = rC / viewingConditions.getRgbD()[0];
        float gF = gC / viewingConditions.getRgbD()[1];
        float bF = bC / viewingConditions.getRgbD()[2];
        float[][] matrix = CamUtils.CAM16RGB_TO_XYZ;
        float x = (matrix[0][0] * rF) + (matrix[0][1] * gF) + (matrix[0][2] * bF);
        float gF2 = gF;
        float f6 = bC;
        float f7 = rF;
        float f8 = p12;
        float f9 = x;
        return ColorUtils.XYZToColor((double) x, (double) ((matrix[1][0] * rF) + (matrix[1][1] * gF) + (matrix[1][2] * bF)), (double) ((matrix[2][0] * rF) + (matrix[2][1] * gF2) + (matrix[2][2] * bF)));
    }

    static int toColor(float hue, float chroma, float lstar, ViewingConditions viewingConditions) {
        if (((double) chroma) < 1.0d || ((double) Math.round(lstar)) <= 0.0d || ((double) Math.round(lstar)) >= 100.0d) {
            return CamUtils.intFromLStar(lstar);
        }
        float hue2 = 0.0f;
        if (hue >= 0.0f) {
            hue2 = Math.min(360.0f, hue);
        }
        float high = chroma;
        float mid = chroma;
        float low = 0.0f;
        boolean isFirstLoop = true;
        CamColor answer = null;
        while (Math.abs(low - high) >= CHROMA_SEARCH_ENDPOINT) {
            CamColor possibleAnswer = findCamByJ(hue2, mid, lstar);
            if (!isFirstLoop) {
                if (possibleAnswer == null) {
                    high = mid;
                } else {
                    answer = possibleAnswer;
                    low = mid;
                }
                mid = low + ((high - low) / 2.0f);
            } else if (possibleAnswer != null) {
                return possibleAnswer.viewed(viewingConditions);
            } else {
                isFirstLoop = false;
                mid = low + ((high - low) / 2.0f);
            }
        }
        if (answer == null) {
            return CamUtils.intFromLStar(lstar);
        }
        return answer.viewed(viewingConditions);
    }

    private static CamColor findCamByJ(float hue, float chroma, float lstar) {
        float low = 0.0f;
        float high = 100.0f;
        float bestdL = 1000.0f;
        float bestdE = 1000.0f;
        CamColor bestCam = null;
        while (Math.abs(low - high) > LIGHTNESS_SEARCH_ENDPOINT) {
            float mid = low + ((high - low) / 2.0f);
            int clipped = fromJch(mid, chroma, hue).viewedInSrgb();
            float clippedLstar = CamUtils.lStarFromInt(clipped);
            float dL = Math.abs(lstar - clippedLstar);
            if (dL < 0.2f) {
                CamColor camClipped = fromColor(clipped);
                float dE = camClipped.distance(fromJch(camClipped.getJ(), camClipped.getChroma(), hue));
                if (dE <= 1.0f) {
                    bestdL = dL;
                    bestdE = dE;
                    bestCam = camClipped;
                }
            }
            if (bestdL == 0.0f && bestdE == 0.0f) {
                break;
            } else if (clippedLstar < lstar) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return bestCam;
    }
}
