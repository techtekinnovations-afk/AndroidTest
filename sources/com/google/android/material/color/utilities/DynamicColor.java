package com.google.android.material.color.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public final class DynamicColor {
    public final Function<DynamicScheme, DynamicColor> background;
    public final ContrastCurve contrastCurve;
    private final HashMap<DynamicScheme, Hct> hctCache = new HashMap<>();
    public final boolean isBackground;
    public final String name;
    public final Function<DynamicScheme, Double> opacity;
    public final Function<DynamicScheme, TonalPalette> palette;
    public final Function<DynamicScheme, DynamicColor> secondBackground;
    public final Function<DynamicScheme, Double> tone;
    public final Function<DynamicScheme, ToneDeltaPair> toneDeltaPair;

    public DynamicColor(String name2, Function<DynamicScheme, TonalPalette> palette2, Function<DynamicScheme, Double> tone2, boolean isBackground2, Function<DynamicScheme, DynamicColor> background2, Function<DynamicScheme, DynamicColor> secondBackground2, ContrastCurve contrastCurve2, Function<DynamicScheme, ToneDeltaPair> toneDeltaPair2) {
        this.name = name2;
        this.palette = palette2;
        this.tone = tone2;
        this.isBackground = isBackground2;
        this.background = background2;
        this.secondBackground = secondBackground2;
        this.contrastCurve = contrastCurve2;
        this.toneDeltaPair = toneDeltaPair2;
        this.opacity = null;
    }

    public DynamicColor(String name2, Function<DynamicScheme, TonalPalette> palette2, Function<DynamicScheme, Double> tone2, boolean isBackground2, Function<DynamicScheme, DynamicColor> background2, Function<DynamicScheme, DynamicColor> secondBackground2, ContrastCurve contrastCurve2, Function<DynamicScheme, ToneDeltaPair> toneDeltaPair2, Function<DynamicScheme, Double> opacity2) {
        this.name = name2;
        this.palette = palette2;
        this.tone = tone2;
        this.isBackground = isBackground2;
        this.background = background2;
        this.secondBackground = secondBackground2;
        this.contrastCurve = contrastCurve2;
        this.toneDeltaPair = toneDeltaPair2;
        this.opacity = opacity2;
    }

    public static DynamicColor fromPalette(String name2, Function<DynamicScheme, TonalPalette> palette2, Function<DynamicScheme, Double> tone2) {
        return new DynamicColor(name2, palette2, tone2, false, (Function<DynamicScheme, DynamicColor>) null, (Function<DynamicScheme, DynamicColor>) null, (ContrastCurve) null, (Function<DynamicScheme, ToneDeltaPair>) null);
    }

    public static DynamicColor fromPalette(String name2, Function<DynamicScheme, TonalPalette> palette2, Function<DynamicScheme, Double> tone2, boolean isBackground2) {
        return new DynamicColor(name2, palette2, tone2, isBackground2, (Function<DynamicScheme, DynamicColor>) null, (Function<DynamicScheme, DynamicColor>) null, (ContrastCurve) null, (Function<DynamicScheme, ToneDeltaPair>) null);
    }

    public static DynamicColor fromArgb(String name2, int argb) {
        return fromPalette(name2, new DynamicColor$$ExternalSyntheticLambda0(TonalPalette.fromInt(argb)), new DynamicColor$$ExternalSyntheticLambda1(Hct.fromInt(argb)));
    }

    static /* synthetic */ TonalPalette lambda$fromArgb$0(TonalPalette palette2, DynamicScheme s) {
        return palette2;
    }

    public int getArgb(DynamicScheme scheme) {
        int argb = getHct(scheme).toInt();
        if (this.opacity == null) {
            return argb;
        }
        return (16777215 & argb) | (MathUtils.clampInt(0, 255, (int) Math.round(255.0d * this.opacity.apply(scheme).doubleValue())) << 24);
    }

    public Hct getHct(DynamicScheme scheme) {
        Hct cachedAnswer = this.hctCache.get(scheme);
        if (cachedAnswer != null) {
            return cachedAnswer;
        }
        Hct answer = this.palette.apply(scheme).getHct(getTone(scheme));
        if (this.hctCache.size() > 4) {
            this.hctCache.clear();
        }
        this.hctCache.put(scheme, answer);
        return answer;
    }

    public double getTone(DynamicScheme scheme) {
        ArrayList<Double> availables;
        double nTone;
        double fTone;
        double nContrast;
        DynamicScheme dynamicScheme = scheme;
        boolean decreasingContrast = dynamicScheme.contrastLevel < 0.0d;
        if (this.toneDeltaPair != null) {
            ToneDeltaPair toneDeltaPair2 = this.toneDeltaPair.apply(dynamicScheme);
            DynamicColor roleA = toneDeltaPair2.getRoleA();
            DynamicColor roleB = toneDeltaPair2.getRoleB();
            double delta = toneDeltaPair2.getDelta();
            TonePolarity polarity = toneDeltaPair2.getPolarity();
            boolean stayTogether = toneDeltaPair2.getStayTogether();
            double bgTone = this.background.apply(dynamicScheme).getTone(dynamicScheme);
            boolean aIsNearer = polarity == TonePolarity.NEARER || (polarity == TonePolarity.LIGHTER && !dynamicScheme.isDark) || (polarity == TonePolarity.DARKER && dynamicScheme.isDark);
            DynamicColor nearer = aIsNearer ? roleA : roleB;
            DynamicColor farther = aIsNearer ? roleB : roleA;
            double expansionDir = -1.0d;
            boolean amNearer = this.name.equals(nearer.name);
            if (dynamicScheme.isDark) {
                expansionDir = 1.0d;
            }
            boolean decreasingContrast2 = decreasingContrast;
            boolean z = aIsNearer;
            double nContrast2 = nearer.contrastCurve.get(dynamicScheme.contrastLevel);
            TonePolarity tonePolarity = polarity;
            boolean stayTogether2 = stayTogether;
            double fContrast = farther.contrastCurve.get(dynamicScheme.contrastLevel);
            DynamicColor dynamicColor = nearer;
            ToneDeltaPair toneDeltaPair3 = toneDeltaPair2;
            double nInitialTone = nearer.tone.apply(dynamicScheme).doubleValue();
            if (Contrast.ratioOfTones(bgTone, nInitialTone) >= nContrast2) {
                nTone = nInitialTone;
            } else {
                nTone = foregroundTone(bgTone, nContrast2);
            }
            double d = nInitialTone;
            double fInitialTone = farther.tone.apply(dynamicScheme).doubleValue();
            if (Contrast.ratioOfTones(bgTone, fInitialTone) >= fContrast) {
                fTone = fInitialTone;
            } else {
                fTone = foregroundTone(bgTone, fContrast);
            }
            if (decreasingContrast2) {
                nTone = foregroundTone(bgTone, nContrast2);
                fTone = foregroundTone(bgTone, fContrast);
            }
            if ((fTone - nTone) * expansionDir < delta) {
                double fTone2 = MathUtils.clampDouble(0.0d, 100.0d, nTone + (delta * expansionDir));
                if ((fTone2 - nTone) * expansionDir < delta) {
                    nTone = MathUtils.clampDouble(0.0d, 100.0d, fTone2 - (delta * expansionDir));
                    double d2 = fTone2;
                    double fTone3 = nContrast2;
                    nContrast = d2;
                } else {
                    double d3 = fTone2;
                    double fTone4 = nContrast2;
                    nContrast = d3;
                }
            } else {
                double d4 = fTone;
                double fTone5 = nContrast2;
                nContrast = d4;
            }
            if (50.0d > nTone || nTone >= 60.0d) {
                if (50.0d <= nContrast && nContrast < 60.0d) {
                    if (stayTogether2) {
                        if (expansionDir > 0.0d) {
                            nTone = 60.0d;
                            nContrast = Math.max(nContrast, 60.0d + (delta * expansionDir));
                        } else {
                            nTone = 49.0d;
                            nContrast = Math.min(nContrast, 49.0d + (delta * expansionDir));
                        }
                    } else if (expansionDir > 0.0d) {
                        nContrast = 60.0d;
                    } else {
                        nContrast = 49.0d;
                    }
                }
            } else if (expansionDir > 0.0d) {
                nTone = 60.0d;
                double d5 = fContrast;
                nContrast = Math.max(nContrast, 60.0d + (delta * expansionDir));
            } else {
                nTone = 49.0d;
                nContrast = Math.min(nContrast, 49.0d + (delta * expansionDir));
            }
            return amNearer ? nTone : nContrast;
        }
        boolean decreasingContrast3 = decreasingContrast;
        double answer = this.tone.apply(dynamicScheme).doubleValue();
        if (this.background == null) {
            return answer;
        }
        double bgTone2 = this.background.apply(dynamicScheme).getTone(dynamicScheme);
        double desiredRatio = this.contrastCurve.get(dynamicScheme.contrastLevel);
        if (Contrast.ratioOfTones(bgTone2, answer) < desiredRatio) {
            answer = foregroundTone(bgTone2, desiredRatio);
        }
        if (decreasingContrast3) {
            answer = foregroundTone(bgTone2, desiredRatio);
        }
        if (this.isBackground && 50.0d <= answer && answer < 60.0d) {
            if (Contrast.ratioOfTones(49.0d, bgTone2) >= desiredRatio) {
                answer = 49.0d;
            } else {
                answer = 60.0d;
            }
        }
        if (this.secondBackground == null) {
            return answer;
        }
        double bgTone1 = this.background.apply(dynamicScheme).getTone(dynamicScheme);
        double bgTone22 = this.secondBackground.apply(dynamicScheme).getTone(dynamicScheme);
        double d6 = bgTone2;
        double upper = Math.max(bgTone1, bgTone22);
        double lower = Math.min(bgTone1, bgTone22);
        if (Contrast.ratioOfTones(upper, answer) >= desiredRatio && Contrast.ratioOfTones(lower, answer) >= desiredRatio) {
            return answer;
        }
        double lightOption = Contrast.lighter(upper, desiredRatio);
        double darkOption = Contrast.darker(lower, desiredRatio);
        ArrayList<Double> availables2 = new ArrayList<>();
        if (lightOption != -1.0d) {
            availables = availables2;
            availables.add(Double.valueOf(lightOption));
        } else {
            availables = availables2;
        }
        if (darkOption != -1.0d) {
            availables.add(Double.valueOf(darkOption));
        }
        if (!(tonePrefersLightForeground(bgTone1) || tonePrefersLightForeground(bgTone22))) {
            double d7 = lower;
            if (availables.size() == 1) {
                return availables.get(0).doubleValue();
            }
            if (darkOption == -1.0d) {
                return 0.0d;
            }
            return darkOption;
        } else if (lightOption == -1.0d) {
            return 100.0d;
        } else {
            return lightOption;
        }
    }

    public static double foregroundTone(double bgTone, double ratio) {
        double d = bgTone;
        double lighterTone = Contrast.lighterUnsafe(bgTone, ratio);
        double darkerTone = Contrast.darkerUnsafe(bgTone, ratio);
        double lighterRatio = Contrast.ratioOfTones(lighterTone, d);
        double darkerRatio = Contrast.ratioOfTones(darkerTone, d);
        if (!tonePrefersLightForeground(d)) {
            return (darkerRatio >= ratio || darkerRatio >= lighterRatio) ? darkerTone : lighterTone;
        }
        boolean negligibleDifference = Math.abs(lighterRatio - darkerRatio) < 0.1d && lighterRatio < ratio && darkerRatio < ratio;
        if (lighterRatio >= ratio || lighterRatio >= darkerRatio || negligibleDifference) {
            return lighterTone;
        }
        return darkerTone;
    }

    public static double enableLightForeground(double tone2) {
        if (!tonePrefersLightForeground(tone2) || toneAllowsLightForeground(tone2)) {
            return tone2;
        }
        return 49.0d;
    }

    public static boolean tonePrefersLightForeground(double tone2) {
        return Math.round(tone2) < 60;
    }

    public static boolean toneAllowsLightForeground(double tone2) {
        return Math.round(tone2) <= 49;
    }
}
