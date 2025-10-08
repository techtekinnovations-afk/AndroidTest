package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.util.Xml;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import org.xmlpull.v1.XmlPullParser;

public class TextAppearance {
    private static final String TAG = "TextAppearance";
    private static final int TYPEFACE_MONOSPACE = 3;
    private static final int TYPEFACE_SANS = 1;
    private static final int TYPEFACE_SERIF = 2;
    /* access modifiers changed from: private */
    public Typeface font;
    public final String fontFamily;
    private final int fontFamilyResourceId;
    /* access modifiers changed from: private */
    public boolean fontResolved = false;
    public String fontVariationSettings;
    public final boolean hasLetterSpacing;
    public final float letterSpacing;
    public final ColorStateList shadowColor;
    public final float shadowDx;
    public final float shadowDy;
    public final float shadowRadius;
    private boolean systemFontLoadAttempted = false;
    public final boolean textAllCaps;
    private ColorStateList textColor;
    public final ColorStateList textColorHint;
    public final ColorStateList textColorLink;
    private float textSize;
    public final int textStyle;
    public final int typeface;

    public TextAppearance(Context context, int id) {
        TypedArray a = context.obtainStyledAttributes(id, R.styleable.TextAppearance);
        setTextSize(a.getDimension(R.styleable.TextAppearance_android_textSize, 0.0f));
        setTextColor(MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColor));
        this.textColorHint = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColorHint);
        this.textColorLink = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_textColorLink);
        this.textStyle = a.getInt(R.styleable.TextAppearance_android_textStyle, 0);
        this.typeface = a.getInt(R.styleable.TextAppearance_android_typeface, 1);
        int fontFamilyIndex = MaterialResources.getIndexWithValue(a, R.styleable.TextAppearance_fontFamily, R.styleable.TextAppearance_android_fontFamily);
        this.fontFamilyResourceId = a.getResourceId(fontFamilyIndex, 0);
        this.fontFamily = a.getString(fontFamilyIndex);
        this.textAllCaps = a.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
        this.shadowColor = MaterialResources.getColorStateList(context, a, R.styleable.TextAppearance_android_shadowColor);
        this.shadowDx = a.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.shadowDy = a.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.shadowRadius = a.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        a.recycle();
        TypedArray a2 = context.obtainStyledAttributes(id, com.google.android.material.R.styleable.MaterialTextAppearance);
        this.hasLetterSpacing = a2.hasValue(com.google.android.material.R.styleable.MaterialTextAppearance_android_letterSpacing);
        this.letterSpacing = a2.getFloat(com.google.android.material.R.styleable.MaterialTextAppearance_android_letterSpacing, 0.0f);
        if (Build.VERSION.SDK_INT >= 26) {
            this.fontVariationSettings = a2.getString(MaterialResources.getIndexWithValue(a2, com.google.android.material.R.styleable.MaterialTextAppearance_fontVariationSettings, com.google.android.material.R.styleable.MaterialTextAppearance_android_fontVariationSettings));
        }
        a2.recycle();
    }

    public Typeface getFont(Context context) {
        if (this.fontResolved) {
            return this.font;
        }
        if (!context.isRestricted()) {
            try {
                this.font = ResourcesCompat.getFont(context, this.fontFamilyResourceId);
                if (this.font != null) {
                    this.font = Typeface.create(this.font, this.textStyle);
                }
            } catch (Resources.NotFoundException | UnsupportedOperationException e) {
            } catch (Exception e2) {
                Log.d(TAG, "Error loading font " + this.fontFamily, e2);
            }
        }
        createFallbackFont();
        this.fontResolved = true;
        return this.font;
    }

    public void getFontAsync(Context context, final TextAppearanceFontCallback callback) {
        if (!maybeLoadFontSynchronously(context)) {
            createFallbackFont();
        }
        if (this.fontFamilyResourceId == 0) {
            this.fontResolved = true;
        }
        if (this.fontResolved) {
            callback.onFontRetrieved(this.font, true);
            return;
        }
        try {
            ResourcesCompat.getFont(context, this.fontFamilyResourceId, new ResourcesCompat.FontCallback() {
                public void onFontRetrieved(Typeface typeface) {
                    Typeface unused = TextAppearance.this.font = Typeface.create(typeface, TextAppearance.this.textStyle);
                    boolean unused2 = TextAppearance.this.fontResolved = true;
                    callback.onFontRetrieved(TextAppearance.this.font, false);
                }

                public void onFontRetrievalFailed(int reason) {
                    boolean unused = TextAppearance.this.fontResolved = true;
                    callback.onFontRetrievalFailed(reason);
                }
            }, (Handler) null);
        } catch (Resources.NotFoundException e) {
            this.fontResolved = true;
            callback.onFontRetrievalFailed(1);
        } catch (Exception e2) {
            Log.d(TAG, "Error loading font " + this.fontFamily, e2);
            this.fontResolved = true;
            callback.onFontRetrievalFailed(-3);
        }
    }

    public void getFontAsync(final Context context, final TextPaint textPaint, final TextAppearanceFontCallback callback) {
        updateTextPaintMeasureState(context, textPaint, getFallbackFont());
        getFontAsync(context, new TextAppearanceFontCallback() {
            public void onFontRetrieved(Typeface typeface, boolean fontResolvedSynchronously) {
                TextAppearance.this.updateTextPaintMeasureState(context, textPaint, typeface);
                callback.onFontRetrieved(typeface, fontResolvedSynchronously);
            }

            public void onFontRetrievalFailed(int i) {
                callback.onFontRetrievalFailed(i);
            }
        });
    }

    public Typeface getFallbackFont() {
        createFallbackFont();
        return this.font;
    }

    private void createFallbackFont() {
        if (this.font == null && this.fontFamily != null) {
            this.font = Typeface.create(this.fontFamily, this.textStyle);
        }
        if (this.font == null) {
            switch (this.typeface) {
                case 1:
                    this.font = Typeface.SANS_SERIF;
                    break;
                case 2:
                    this.font = Typeface.SERIF;
                    break;
                case 3:
                    this.font = Typeface.MONOSPACE;
                    break;
                default:
                    this.font = Typeface.DEFAULT;
                    break;
            }
            this.font = Typeface.create(this.font, this.textStyle);
        }
    }

    public void updateDrawState(Context context, TextPaint textPaint, TextAppearanceFontCallback callback) {
        int i;
        int i2;
        updateMeasureState(context, textPaint, callback);
        if (this.textColor != null) {
            i = this.textColor.getColorForState(textPaint.drawableState, this.textColor.getDefaultColor());
        } else {
            i = ViewCompat.MEASURED_STATE_MASK;
        }
        textPaint.setColor(i);
        float f = this.shadowRadius;
        float f2 = this.shadowDx;
        float f3 = this.shadowDy;
        if (this.shadowColor != null) {
            i2 = this.shadowColor.getColorForState(textPaint.drawableState, this.shadowColor.getDefaultColor());
        } else {
            i2 = 0;
        }
        textPaint.setShadowLayer(f, f2, f3, i2);
    }

    public void updateMeasureState(Context context, TextPaint textPaint, TextAppearanceFontCallback callback) {
        if (!maybeLoadFontSynchronously(context) || !this.fontResolved || this.font == null) {
            getFontAsync(context, textPaint, callback);
        } else {
            updateTextPaintMeasureState(context, textPaint, this.font);
        }
    }

    public void updateTextPaintMeasureState(Context context, TextPaint textPaint, Typeface typeface2) {
        Typeface boldTypeface = TypefaceUtils.maybeCopyWithFontWeightAdjustment(context, typeface2);
        if (boldTypeface != null) {
            typeface2 = boldTypeface;
        }
        textPaint.setTypeface(typeface2);
        int fake = this.textStyle & (~typeface2.getStyle());
        textPaint.setFakeBoldText((fake & 1) != 0);
        textPaint.setTextSkewX((fake & 2) != 0 ? -0.25f : 0.0f);
        textPaint.setTextSize(this.textSize);
        if (Build.VERSION.SDK_INT >= 26) {
            textPaint.setFontVariationSettings(this.fontVariationSettings);
        }
        if (this.hasLetterSpacing) {
            textPaint.setLetterSpacing(this.letterSpacing);
        }
    }

    public ColorStateList getTextColor() {
        return this.textColor;
    }

    public void setTextColor(ColorStateList textColor2) {
        this.textColor = textColor2;
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float textSize2) {
        this.textSize = textSize2;
    }

    public String getFontVariationSettings() {
        return this.fontVariationSettings;
    }

    public void setFontVariationSettings(String fontVariationSettings2) {
        this.fontVariationSettings = fontVariationSettings2;
    }

    private boolean maybeLoadFontSynchronously(Context context) {
        if (TextAppearanceConfig.shouldLoadFontSynchronously()) {
            getFont(context);
            return true;
        } else if (this.fontResolved) {
            return true;
        } else {
            if (this.fontFamilyResourceId == 0) {
                return false;
            }
            Typeface cachedFont = ResourcesCompat.getCachedFont(context, this.fontFamilyResourceId);
            if (cachedFont != null) {
                this.font = cachedFont;
                this.fontResolved = true;
                return true;
            }
            Typeface systemFont = getSystemTypeface(context);
            if (systemFont == null) {
                return false;
            }
            this.font = systemFont;
            this.fontResolved = true;
            return true;
        }
    }

    private Typeface getSystemTypeface(Context context) {
        Typeface regularSystemTypeface;
        if (this.systemFontLoadAttempted) {
            return null;
        }
        this.systemFontLoadAttempted = true;
        String systemFontFamily = readFontProviderSystemFontFamily(context, this.fontFamilyResourceId);
        if (systemFontFamily == null || (regularSystemTypeface = Typeface.create(systemFontFamily, 0)) == Typeface.DEFAULT) {
            return null;
        }
        return Typeface.create(regularSystemTypeface, this.textStyle);
    }

    private static String readFontProviderSystemFontFamily(Context context, int fontFamilyResourceId2) {
        Resources resources = context.getResources();
        if (fontFamilyResourceId2 == 0 || !resources.getResourceTypeName(fontFamilyResourceId2).equals("font")) {
            return null;
        }
        try {
            XmlPullParser xpp = resources.getXml(fontFamilyResourceId2);
            while (xpp.getEventType() != 1) {
                if (xpp.getEventType() != 2 || !xpp.getName().equals("font-family")) {
                    xpp.next();
                } else {
                    TypedArray a = resources.obtainAttributes(Xml.asAttributeSet(xpp), androidx.core.R.styleable.FontFamily);
                    String systemFontFamily = a.getString(androidx.core.R.styleable.FontFamily_fontProviderSystemFontFamily);
                    a.recycle();
                    return systemFontFamily;
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }
}
