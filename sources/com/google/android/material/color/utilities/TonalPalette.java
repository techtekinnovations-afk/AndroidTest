package com.google.android.material.color.utilities;

import java.util.HashMap;
import java.util.Map;

public final class TonalPalette {
    Map<Integer, Integer> cache = new HashMap();
    double chroma;
    double hue;
    Hct keyColor;

    public static TonalPalette fromInt(int argb) {
        return fromHct(Hct.fromInt(argb));
    }

    public static TonalPalette fromHct(Hct hct) {
        return new TonalPalette(hct.getHue(), hct.getChroma(), hct);
    }

    public static TonalPalette fromHueAndChroma(double hue2, double chroma2) {
        return new TonalPalette(hue2, chroma2, new KeyColor(hue2, chroma2).create());
    }

    private TonalPalette(double hue2, double chroma2, Hct keyColor2) {
        this.hue = hue2;
        this.chroma = chroma2;
        this.keyColor = keyColor2;
    }

    public int tone(int tone) {
        Integer color = this.cache.get(Integer.valueOf(tone));
        if (color == null) {
            color = Integer.valueOf(Hct.from(this.hue, this.chroma, (double) tone).toInt());
            this.cache.put(Integer.valueOf(tone), color);
        }
        return color.intValue();
    }

    public Hct getHct(double tone) {
        return Hct.from(this.hue, this.chroma, tone);
    }

    public double getChroma() {
        return this.chroma;
    }

    public double getHue() {
        return this.hue;
    }

    public Hct getKeyColor() {
        return this.keyColor;
    }

    private static final class KeyColor {
        private static final double MAX_CHROMA_VALUE = 200.0d;
        private final Map<Integer, Double> chromaCache = new HashMap();
        private final double hue;
        private final double requestedChroma;

        public KeyColor(double hue2, double requestedChroma2) {
            this.hue = hue2;
            this.requestedChroma = requestedChroma2;
        }

        public Hct create() {
            int lowerTone = 0;
            int upperTone = 100;
            while (lowerTone < upperTone) {
                int midTone = (lowerTone + upperTone) / 2;
                boolean sufficientChroma = false;
                boolean isAscending = maxChroma(midTone) < maxChroma(midTone + 1);
                if (maxChroma(midTone) >= this.requestedChroma - 0.01d) {
                    sufficientChroma = true;
                }
                if (sufficientChroma) {
                    if (Math.abs(lowerTone - 50) < Math.abs(upperTone - 50)) {
                        upperTone = midTone;
                    } else if (lowerTone == midTone) {
                        return Hct.from(this.hue, this.requestedChroma, (double) lowerTone);
                    } else {
                        lowerTone = midTone;
                    }
                } else if (isAscending) {
                    lowerTone = midTone + 1;
                } else {
                    upperTone = midTone;
                }
            }
            return Hct.from(this.hue, this.requestedChroma, (double) lowerTone);
        }

        private double maxChroma(int tone) {
            Double newChroma;
            if (this.chromaCache.get(Integer.valueOf(tone)) == null && (newChroma = Double.valueOf(Hct.from(this.hue, MAX_CHROMA_VALUE, (double) tone).getChroma())) != null) {
                this.chromaCache.put(Integer.valueOf(tone), newChroma);
            }
            return this.chromaCache.get(Integer.valueOf(tone)).doubleValue();
        }
    }
}
