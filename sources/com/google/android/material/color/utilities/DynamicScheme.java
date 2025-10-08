package com.google.android.material.color.utilities;

public class DynamicScheme {
    public final double contrastLevel;
    public final TonalPalette errorPalette = TonalPalette.fromHueAndChroma(25.0d, 84.0d);
    public final boolean isDark;
    public final TonalPalette neutralPalette;
    public final TonalPalette neutralVariantPalette;
    public final TonalPalette primaryPalette;
    public final TonalPalette secondaryPalette;
    public final int sourceColorArgb;
    public final Hct sourceColorHct;
    public final TonalPalette tertiaryPalette;
    public final Variant variant;

    public DynamicScheme(Hct sourceColorHct2, Variant variant2, boolean isDark2, double contrastLevel2, TonalPalette primaryPalette2, TonalPalette secondaryPalette2, TonalPalette tertiaryPalette2, TonalPalette neutralPalette2, TonalPalette neutralVariantPalette2) {
        this.sourceColorArgb = sourceColorHct2.toInt();
        this.sourceColorHct = sourceColorHct2;
        this.variant = variant2;
        this.isDark = isDark2;
        this.contrastLevel = contrastLevel2;
        this.primaryPalette = primaryPalette2;
        this.secondaryPalette = secondaryPalette2;
        this.tertiaryPalette = tertiaryPalette2;
        this.neutralPalette = neutralPalette2;
        this.neutralVariantPalette = neutralVariantPalette2;
    }

    public static double getRotatedHue(Hct sourceColorHct2, double[] hues, double[] rotations) {
        double sourceHue = sourceColorHct2.getHue();
        if (rotations.length == 1) {
            return MathUtils.sanitizeDegreesDouble(rotations[0] + sourceHue);
        }
        int size = hues.length;
        for (int i = 0; i <= size - 2; i++) {
            double thisHue = hues[i];
            double nextHue = hues[i + 1];
            if (thisHue < sourceHue && sourceHue < nextHue) {
                return MathUtils.sanitizeDegreesDouble(rotations[i] + sourceHue);
            }
        }
        return sourceHue;
    }

    public Hct getHct(DynamicColor dynamicColor) {
        return dynamicColor.getHct(this);
    }

    public int getArgb(DynamicColor dynamicColor) {
        return dynamicColor.getArgb(this);
    }

    public int getPrimaryPaletteKeyColor() {
        return getArgb(new MaterialDynamicColors().primaryPaletteKeyColor());
    }

    public int getSecondaryPaletteKeyColor() {
        return getArgb(new MaterialDynamicColors().secondaryPaletteKeyColor());
    }

    public int getTertiaryPaletteKeyColor() {
        return getArgb(new MaterialDynamicColors().tertiaryPaletteKeyColor());
    }

    public int getNeutralPaletteKeyColor() {
        return getArgb(new MaterialDynamicColors().neutralPaletteKeyColor());
    }

    public int getNeutralVariantPaletteKeyColor() {
        return getArgb(new MaterialDynamicColors().neutralVariantPaletteKeyColor());
    }

    public int getBackground() {
        return getArgb(new MaterialDynamicColors().background());
    }

    public int getOnBackground() {
        return getArgb(new MaterialDynamicColors().onBackground());
    }

    public int getSurface() {
        return getArgb(new MaterialDynamicColors().surface());
    }

    public int getSurfaceDim() {
        return getArgb(new MaterialDynamicColors().surfaceDim());
    }

    public int getSurfaceBright() {
        return getArgb(new MaterialDynamicColors().surfaceBright());
    }

    public int getSurfaceContainerLowest() {
        return getArgb(new MaterialDynamicColors().surfaceContainerLowest());
    }

    public int getSurfaceContainerLow() {
        return getArgb(new MaterialDynamicColors().surfaceContainerLow());
    }

    public int getSurfaceContainer() {
        return getArgb(new MaterialDynamicColors().surfaceContainer());
    }

    public int getSurfaceContainerHigh() {
        return getArgb(new MaterialDynamicColors().surfaceContainerHigh());
    }

    public int getSurfaceContainerHighest() {
        return getArgb(new MaterialDynamicColors().surfaceContainerHighest());
    }

    public int getOnSurface() {
        return getArgb(new MaterialDynamicColors().onSurface());
    }

    public int getSurfaceVariant() {
        return getArgb(new MaterialDynamicColors().surfaceVariant());
    }

    public int getOnSurfaceVariant() {
        return getArgb(new MaterialDynamicColors().onSurfaceVariant());
    }

    public int getInverseSurface() {
        return getArgb(new MaterialDynamicColors().inverseSurface());
    }

    public int getInverseOnSurface() {
        return getArgb(new MaterialDynamicColors().inverseOnSurface());
    }

    public int getOutline() {
        return getArgb(new MaterialDynamicColors().outline());
    }

    public int getOutlineVariant() {
        return getArgb(new MaterialDynamicColors().outlineVariant());
    }

    public int getShadow() {
        return getArgb(new MaterialDynamicColors().shadow());
    }

    public int getScrim() {
        return getArgb(new MaterialDynamicColors().scrim());
    }

    public int getSurfaceTint() {
        return getArgb(new MaterialDynamicColors().surfaceTint());
    }

    public int getPrimary() {
        return getArgb(new MaterialDynamicColors().primary());
    }

    public int getOnPrimary() {
        return getArgb(new MaterialDynamicColors().onPrimary());
    }

    public int getPrimaryContainer() {
        return getArgb(new MaterialDynamicColors().primaryContainer());
    }

    public int getOnPrimaryContainer() {
        return getArgb(new MaterialDynamicColors().onPrimaryContainer());
    }

    public int getInversePrimary() {
        return getArgb(new MaterialDynamicColors().inversePrimary());
    }

    public int getSecondary() {
        return getArgb(new MaterialDynamicColors().secondary());
    }

    public int getOnSecondary() {
        return getArgb(new MaterialDynamicColors().onSecondary());
    }

    public int getSecondaryContainer() {
        return getArgb(new MaterialDynamicColors().secondaryContainer());
    }

    public int getOnSecondaryContainer() {
        return getArgb(new MaterialDynamicColors().onSecondaryContainer());
    }

    public int getTertiary() {
        return getArgb(new MaterialDynamicColors().tertiary());
    }

    public int getOnTertiary() {
        return getArgb(new MaterialDynamicColors().onTertiary());
    }

    public int getTertiaryContainer() {
        return getArgb(new MaterialDynamicColors().tertiaryContainer());
    }

    public int getOnTertiaryContainer() {
        return getArgb(new MaterialDynamicColors().onTertiaryContainer());
    }

    public int getError() {
        return getArgb(new MaterialDynamicColors().error());
    }

    public int getOnError() {
        return getArgb(new MaterialDynamicColors().onError());
    }

    public int getErrorContainer() {
        return getArgb(new MaterialDynamicColors().errorContainer());
    }

    public int getOnErrorContainer() {
        return getArgb(new MaterialDynamicColors().onErrorContainer());
    }

    public int getPrimaryFixed() {
        return getArgb(new MaterialDynamicColors().primaryFixed());
    }

    public int getPrimaryFixedDim() {
        return getArgb(new MaterialDynamicColors().primaryFixedDim());
    }

    public int getOnPrimaryFixed() {
        return getArgb(new MaterialDynamicColors().onPrimaryFixed());
    }

    public int getOnPrimaryFixedVariant() {
        return getArgb(new MaterialDynamicColors().onPrimaryFixedVariant());
    }

    public int getSecondaryFixed() {
        return getArgb(new MaterialDynamicColors().secondaryFixed());
    }

    public int getSecondaryFixedDim() {
        return getArgb(new MaterialDynamicColors().secondaryFixedDim());
    }

    public int getOnSecondaryFixed() {
        return getArgb(new MaterialDynamicColors().onSecondaryFixed());
    }

    public int getOnSecondaryFixedVariant() {
        return getArgb(new MaterialDynamicColors().onSecondaryFixedVariant());
    }

    public int getTertiaryFixed() {
        return getArgb(new MaterialDynamicColors().tertiaryFixed());
    }

    public int getTertiaryFixedDim() {
        return getArgb(new MaterialDynamicColors().tertiaryFixedDim());
    }

    public int getOnTertiaryFixed() {
        return getArgb(new MaterialDynamicColors().onTertiaryFixed());
    }

    public int getOnTertiaryFixedVariant() {
        return getArgb(new MaterialDynamicColors().onTertiaryFixedVariant());
    }

    public int getControlActivated() {
        return getArgb(new MaterialDynamicColors().controlActivated());
    }

    public int getControlNormal() {
        return getArgb(new MaterialDynamicColors().controlNormal());
    }

    public int getControlHighlight() {
        return getArgb(new MaterialDynamicColors().controlHighlight());
    }

    public int getTextPrimaryInverse() {
        return getArgb(new MaterialDynamicColors().textPrimaryInverse());
    }

    public int getTextSecondaryAndTertiaryInverse() {
        return getArgb(new MaterialDynamicColors().textSecondaryAndTertiaryInverse());
    }

    public int getTextPrimaryInverseDisableOnly() {
        return getArgb(new MaterialDynamicColors().textPrimaryInverseDisableOnly());
    }

    public int getTextSecondaryAndTertiaryInverseDisabled() {
        return getArgb(new MaterialDynamicColors().textSecondaryAndTertiaryInverseDisabled());
    }

    public int getTextHintInverse() {
        return getArgb(new MaterialDynamicColors().textHintInverse());
    }
}
