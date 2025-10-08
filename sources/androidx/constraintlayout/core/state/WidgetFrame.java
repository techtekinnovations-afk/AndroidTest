package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLNumber;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.state.Transition;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.core.os.EnvironmentCompat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class WidgetFrame {
    public static float phone_orientation = Float.NaN;
    public float alpha = Float.NaN;
    public int bottom = 0;
    public float interpolatedPos = Float.NaN;
    public int left = 0;
    private final HashMap<String, CustomVariable> mCustom = new HashMap<>();
    TypedBundle mMotionProperties;
    public String name = null;
    public float pivotX = Float.NaN;
    public float pivotY = Float.NaN;
    public int right = 0;
    public float rotationX = Float.NaN;
    public float rotationY = Float.NaN;
    public float rotationZ = Float.NaN;
    public float scaleX = Float.NaN;
    public float scaleY = Float.NaN;
    public int top = 0;
    public float translationX = Float.NaN;
    public float translationY = Float.NaN;
    public float translationZ = Float.NaN;
    public int visibility = 0;
    public ConstraintWidget widget = null;

    public int width() {
        return Math.max(0, this.right - this.left);
    }

    public int height() {
        return Math.max(0, this.bottom - this.top);
    }

    public WidgetFrame() {
    }

    public WidgetFrame(ConstraintWidget widget2) {
        this.widget = widget2;
    }

    public WidgetFrame(WidgetFrame frame) {
        this.widget = frame.widget;
        this.left = frame.left;
        this.top = frame.top;
        this.right = frame.right;
        this.bottom = frame.bottom;
        updateAttributes(frame);
    }

    public void updateAttributes(WidgetFrame frame) {
        if (frame != null) {
            this.pivotX = frame.pivotX;
            this.pivotY = frame.pivotY;
            this.rotationX = frame.rotationX;
            this.rotationY = frame.rotationY;
            this.rotationZ = frame.rotationZ;
            this.translationX = frame.translationX;
            this.translationY = frame.translationY;
            this.translationZ = frame.translationZ;
            this.scaleX = frame.scaleX;
            this.scaleY = frame.scaleY;
            this.alpha = frame.alpha;
            this.visibility = frame.visibility;
            setMotionAttributes(frame.mMotionProperties);
            this.mCustom.clear();
            for (CustomVariable c : frame.mCustom.values()) {
                this.mCustom.put(c.getName(), c.copy());
            }
        }
    }

    public boolean isDefaultTransform() {
        return Float.isNaN(this.rotationX) && Float.isNaN(this.rotationY) && Float.isNaN(this.rotationZ) && Float.isNaN(this.translationX) && Float.isNaN(this.translationY) && Float.isNaN(this.translationZ) && Float.isNaN(this.scaleX) && Float.isNaN(this.scaleY) && Float.isNaN(this.alpha);
    }

    public static void interpolate(int parentWidth, int parentHeight, WidgetFrame frame, WidgetFrame start, WidgetFrame end, Transition transition, float progress) {
        int startY;
        float startAlpha;
        float endAlpha;
        int endHeight;
        float startAlpha2;
        float startAlpha3;
        float endAlpha2;
        int endY;
        int endX;
        int interpolateStartFrame;
        int startY2;
        int startX;
        Iterator<String> it;
        int startY3;
        int i = parentWidth;
        int i2 = parentHeight;
        WidgetFrame widgetFrame = frame;
        WidgetFrame widgetFrame2 = start;
        WidgetFrame widgetFrame3 = end;
        Transition transition2 = transition;
        float f = progress;
        int frameNumber = (int) (f * 100.0f);
        int startX2 = widgetFrame2.left;
        int startY4 = widgetFrame2.top;
        int endX2 = widgetFrame3.left;
        int endY2 = widgetFrame3.top;
        int startWidth = widgetFrame2.right - widgetFrame2.left;
        int startHeight = widgetFrame2.bottom - widgetFrame2.top;
        int endWidth = widgetFrame3.right - widgetFrame3.left;
        int endHeight2 = widgetFrame3.bottom - widgetFrame3.top;
        float progressPosition = progress;
        int startX3 = startX2;
        float startAlpha4 = widgetFrame2.alpha;
        float endAlpha3 = widgetFrame3.alpha;
        int startY5 = startY4;
        if (widgetFrame2.visibility == 8) {
            int startX4 = startX3 - ((int) (((float) endWidth) / 2.0f));
            startY = startY5 - ((int) (((float) endHeight2) / 2.0f));
            startWidth = endWidth;
            startHeight = endHeight2;
            if (Float.isNaN(startAlpha4)) {
                startX3 = startX4;
                startAlpha = 0.0f;
            } else {
                startX3 = startX4;
                startAlpha = startAlpha4;
            }
        } else {
            startAlpha = startAlpha4;
            startY = startY5;
        }
        int endHeight3 = endHeight2;
        float startAlpha5 = startAlpha;
        if (widgetFrame3.visibility == 8) {
            endX2 -= (int) (((float) startWidth) / 2.0f);
            endY2 -= (int) (((float) startHeight) / 2.0f);
            endWidth = startWidth;
            endHeight = startHeight;
            if (Float.isNaN(endAlpha3)) {
                endAlpha = 0.0f;
            } else {
                endAlpha = endAlpha3;
            }
        } else {
            endHeight = endHeight3;
            endAlpha = endAlpha3;
        }
        if (Float.isNaN(startAlpha5) == 0 || Float.isNaN(endAlpha)) {
            startAlpha2 = startAlpha5;
        } else {
            startAlpha2 = 1.0f;
        }
        if (!Float.isNaN(startAlpha2) && Float.isNaN(endAlpha)) {
            endAlpha = 1.0f;
        }
        float endAlpha4 = endAlpha;
        int startY6 = startY;
        if (widgetFrame2.visibility == 4) {
            startAlpha3 = 0.0f;
        } else {
            startAlpha3 = startAlpha2;
        }
        int endY3 = endX2;
        if (widgetFrame3.visibility == 4) {
            endAlpha2 = 0.0f;
        } else {
            endAlpha2 = endAlpha4;
        }
        if (widgetFrame.widget == null || !transition2.hasPositionKeyframes()) {
            interpolateStartFrame = startX3;
            endX = endY3;
            endY = endY2;
            startY2 = startY6;
        } else {
            Transition.KeyPosition firstPosition = transition2.findPreviousPosition(widgetFrame.widget.stringId, frameNumber);
            int endY4 = endY2;
            Transition.KeyPosition lastPosition = transition2.findNextPosition(widgetFrame.widget.stringId, frameNumber);
            if (firstPosition == lastPosition) {
                lastPosition = null;
            }
            int interpolateEndFrame = 100;
            if (firstPosition != null) {
                startX3 = (int) (((float) i) * firstPosition.mX);
                startY6 = (int) (((float) i2) * firstPosition.mY);
                startY3 = firstPosition.mFrame;
            } else {
                startY3 = 0;
            }
            if (lastPosition != null) {
                int i3 = frameNumber;
                float f2 = lastPosition.mX;
                interpolateEndFrame = lastPosition.mFrame;
                endY4 = (int) (((float) i2) * lastPosition.mY);
                endY3 = (int) (((float) i) * f2);
            }
            progressPosition = ((f * 100.0f) - ((float) startY3)) / ((float) (interpolateEndFrame - startY3));
            interpolateStartFrame = startX3;
            endX = endY3;
            endY = endY4;
            startY2 = startY6;
        }
        widgetFrame.widget = widgetFrame2.widget;
        widgetFrame.left = (int) (((float) interpolateStartFrame) + (((float) (endX - interpolateStartFrame)) * progressPosition));
        widgetFrame.top = (int) (((float) startY2) + (((float) (endY - startY2)) * progressPosition));
        int width = (int) (((1.0f - f) * ((float) startWidth)) + (((float) endWidth) * f));
        int i4 = startY2;
        int height = (int) (((1.0f - f) * ((float) startHeight)) + (((float) endHeight) * f));
        widgetFrame.right = widgetFrame.left + width;
        widgetFrame.bottom = widgetFrame.top + height;
        int i5 = height;
        int i6 = width;
        widgetFrame.pivotX = interpolate(widgetFrame2.pivotX, widgetFrame3.pivotX, 0.5f, f);
        widgetFrame.pivotY = interpolate(widgetFrame2.pivotY, widgetFrame3.pivotY, 0.5f, f);
        widgetFrame.rotationX = interpolate(widgetFrame2.rotationX, widgetFrame3.rotationX, 0.0f, f);
        widgetFrame.rotationY = interpolate(widgetFrame2.rotationY, widgetFrame3.rotationY, 0.0f, f);
        widgetFrame.rotationZ = interpolate(widgetFrame2.rotationZ, widgetFrame3.rotationZ, 0.0f, f);
        widgetFrame.scaleX = interpolate(widgetFrame2.scaleX, widgetFrame3.scaleX, 1.0f, f);
        widgetFrame.scaleY = interpolate(widgetFrame2.scaleY, widgetFrame3.scaleY, 1.0f, f);
        widgetFrame.translationX = interpolate(widgetFrame2.translationX, widgetFrame3.translationX, 0.0f, f);
        widgetFrame.translationY = interpolate(widgetFrame2.translationY, widgetFrame3.translationY, 0.0f, f);
        widgetFrame.translationZ = interpolate(widgetFrame2.translationZ, widgetFrame3.translationZ, 0.0f, f);
        widgetFrame.alpha = interpolate(startAlpha3, endAlpha2, 1.0f, f);
        Set<String> keys = widgetFrame3.mCustom.keySet();
        widgetFrame.mCustom.clear();
        Iterator<String> it2 = keys.iterator();
        while (it2.hasNext()) {
            String key = it2.next();
            Set<String> keys2 = keys;
            if (widgetFrame2.mCustom.containsKey(key)) {
                CustomVariable startVariable = widgetFrame2.mCustom.get(key);
                it = it2;
                CustomVariable endVariable = widgetFrame3.mCustom.get(key);
                CustomVariable interpolated = new CustomVariable(startVariable);
                widgetFrame.mCustom.put(key, interpolated);
                if (startVariable.numberOfInterpolatedValues() == 1) {
                    startX = interpolateStartFrame;
                    interpolated.setValue((Object) Float.valueOf(interpolate(startVariable.getValueToInterpolate(), endVariable.getValueToInterpolate(), 0.0f, f)));
                } else {
                    startX = interpolateStartFrame;
                    int n = startVariable.numberOfInterpolatedValues();
                    float[] startValues = new float[n];
                    float[] endValues = new float[n];
                    startVariable.getValuesToInterpolate(startValues);
                    endVariable.getValuesToInterpolate(endValues);
                    CustomVariable customVariable = startVariable;
                    int i7 = 0;
                    while (i7 < n) {
                        int i8 = i7;
                        startValues[i8] = interpolate(startValues[i8], endValues[i8], 0.0f, f);
                        interpolated.setValue(startValues);
                        i7 = i8 + 1;
                        endVariable = endVariable;
                        n = n;
                    }
                    int i9 = i7;
                    CustomVariable customVariable2 = endVariable;
                    int i10 = n;
                }
            } else {
                it = it2;
                startX = interpolateStartFrame;
            }
            widgetFrame = frame;
            widgetFrame2 = start;
            widgetFrame3 = end;
            keys = keys2;
            it2 = it;
            interpolateStartFrame = startX;
        }
    }

    private static float interpolate(float start, float end, float defaultValue, float progress) {
        boolean isStartUnset = Float.isNaN(start);
        boolean isEndUnset = Float.isNaN(end);
        if (isStartUnset && isEndUnset) {
            return Float.NaN;
        }
        if (isStartUnset) {
            start = defaultValue;
        }
        if (isEndUnset) {
            end = defaultValue;
        }
        return ((end - start) * progress) + start;
    }

    public float centerX() {
        return ((float) this.left) + (((float) (this.right - this.left)) / 2.0f);
    }

    public float centerY() {
        return ((float) this.top) + (((float) (this.bottom - this.top)) / 2.0f);
    }

    public WidgetFrame update() {
        if (this.widget != null) {
            this.left = this.widget.getLeft();
            this.top = this.widget.getTop();
            this.right = this.widget.getRight();
            this.bottom = this.widget.getBottom();
            updateAttributes(this.widget.frame);
        }
        return this;
    }

    public WidgetFrame update(ConstraintWidget widget2) {
        if (widget2 == null) {
            return this;
        }
        this.widget = widget2;
        update();
        return this;
    }

    public boolean containsCustom(String name2) {
        return this.mCustom.containsKey(name2);
    }

    public void addCustomColor(String name2, int color) {
        setCustomAttribute(name2, (int) TypedValues.Custom.TYPE_COLOR, color);
    }

    public int getCustomColor(String name2) {
        if (this.mCustom.containsKey(name2)) {
            return this.mCustom.get(name2).getColorValue();
        }
        return -21880;
    }

    public void addCustomFloat(String name2, float value) {
        setCustomAttribute(name2, (int) TypedValues.Custom.TYPE_FLOAT, value);
    }

    public float getCustomFloat(String name2) {
        if (this.mCustom.containsKey(name2)) {
            return this.mCustom.get(name2).getFloatValue();
        }
        return Float.NaN;
    }

    public void setCustomAttribute(String name2, int type, float value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setFloatValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public void setCustomAttribute(String name2, int type, int value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setIntValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public void setCustomAttribute(String name2, int type, boolean value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setBooleanValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public void setCustomAttribute(String name2, int type, String value) {
        if (this.mCustom.containsKey(name2)) {
            this.mCustom.get(name2).setStringValue(value);
        } else {
            this.mCustom.put(name2, new CustomVariable(name2, type, value));
        }
    }

    public CustomVariable getCustomAttribute(String name2) {
        return this.mCustom.get(name2);
    }

    public Set<String> getCustomAttributeNames() {
        return this.mCustom.keySet();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setValue(java.lang.String r4, androidx.constraintlayout.core.parser.CLElement r5) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 1
            r2 = 0
            switch(r0) {
                case -1881940865: goto L_0x00c6;
                case -1383228885: goto L_0x00bb;
                case -1349088399: goto L_0x00b0;
                case -1249320806: goto L_0x00a6;
                case -1249320805: goto L_0x009c;
                case -1249320804: goto L_0x0092;
                case -1225497657: goto L_0x0088;
                case -1225497656: goto L_0x007e;
                case -1225497655: goto L_0x0074;
                case -987906986: goto L_0x006a;
                case -987906985: goto L_0x005f;
                case -908189618: goto L_0x0053;
                case -908189617: goto L_0x0047;
                case 115029: goto L_0x003b;
                case 3317767: goto L_0x002f;
                case 92909918: goto L_0x0023;
                case 108511772: goto L_0x0017;
                case 642850769: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x00d1
        L_0x000b:
            java.lang.String r0 = "interpolatedPos"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 11
            goto L_0x00d2
        L_0x0017:
            java.lang.String r0 = "right"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 15
            goto L_0x00d2
        L_0x0023:
            java.lang.String r0 = "alpha"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 10
            goto L_0x00d2
        L_0x002f:
            java.lang.String r0 = "left"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 14
            goto L_0x00d2
        L_0x003b:
            java.lang.String r0 = "top"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 13
            goto L_0x00d2
        L_0x0047:
            java.lang.String r0 = "scaleY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 9
            goto L_0x00d2
        L_0x0053:
            java.lang.String r0 = "scaleX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 8
            goto L_0x00d2
        L_0x005f:
            java.lang.String r0 = "pivotY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = r1
            goto L_0x00d2
        L_0x006a:
            java.lang.String r0 = "pivotX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = r2
            goto L_0x00d2
        L_0x0074:
            java.lang.String r0 = "translationZ"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 7
            goto L_0x00d2
        L_0x007e:
            java.lang.String r0 = "translationY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 6
            goto L_0x00d2
        L_0x0088:
            java.lang.String r0 = "translationX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 5
            goto L_0x00d2
        L_0x0092:
            java.lang.String r0 = "rotationZ"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 4
            goto L_0x00d2
        L_0x009c:
            java.lang.String r0 = "rotationY"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 3
            goto L_0x00d2
        L_0x00a6:
            java.lang.String r0 = "rotationX"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 2
            goto L_0x00d2
        L_0x00b0:
            java.lang.String r0 = "custom"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 17
            goto L_0x00d2
        L_0x00bb:
            java.lang.String r0 = "bottom"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 16
            goto L_0x00d2
        L_0x00c6:
            java.lang.String r0 = "phone_orientation"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0009
            r0 = 12
            goto L_0x00d2
        L_0x00d1:
            r0 = -1
        L_0x00d2:
            switch(r0) {
                case 0: goto L_0x014d;
                case 1: goto L_0x0146;
                case 2: goto L_0x013f;
                case 3: goto L_0x0138;
                case 4: goto L_0x0131;
                case 5: goto L_0x012a;
                case 6: goto L_0x0123;
                case 7: goto L_0x011c;
                case 8: goto L_0x0115;
                case 9: goto L_0x010e;
                case 10: goto L_0x0107;
                case 11: goto L_0x0100;
                case 12: goto L_0x00f9;
                case 13: goto L_0x00f2;
                case 14: goto L_0x00eb;
                case 15: goto L_0x00e3;
                case 16: goto L_0x00db;
                case 17: goto L_0x00d6;
                default: goto L_0x00d5;
            }
        L_0x00d5:
            return r2
        L_0x00d6:
            r3.parseCustom(r5)
            goto L_0x0154
        L_0x00db:
            int r0 = r5.getInt()
            r3.bottom = r0
            goto L_0x0154
        L_0x00e3:
            int r0 = r5.getInt()
            r3.right = r0
            goto L_0x0154
        L_0x00eb:
            int r0 = r5.getInt()
            r3.left = r0
            goto L_0x0154
        L_0x00f2:
            int r0 = r5.getInt()
            r3.top = r0
            goto L_0x0154
        L_0x00f9:
            float r0 = r5.getFloat()
            phone_orientation = r0
            goto L_0x0154
        L_0x0100:
            float r0 = r5.getFloat()
            r3.interpolatedPos = r0
            goto L_0x0154
        L_0x0107:
            float r0 = r5.getFloat()
            r3.alpha = r0
            goto L_0x0154
        L_0x010e:
            float r0 = r5.getFloat()
            r3.scaleY = r0
            goto L_0x0154
        L_0x0115:
            float r0 = r5.getFloat()
            r3.scaleX = r0
            goto L_0x0154
        L_0x011c:
            float r0 = r5.getFloat()
            r3.translationZ = r0
            goto L_0x0154
        L_0x0123:
            float r0 = r5.getFloat()
            r3.translationY = r0
            goto L_0x0154
        L_0x012a:
            float r0 = r5.getFloat()
            r3.translationX = r0
            goto L_0x0154
        L_0x0131:
            float r0 = r5.getFloat()
            r3.rotationZ = r0
            goto L_0x0154
        L_0x0138:
            float r0 = r5.getFloat()
            r3.rotationY = r0
            goto L_0x0154
        L_0x013f:
            float r0 = r5.getFloat()
            r3.rotationX = r0
            goto L_0x0154
        L_0x0146:
            float r0 = r5.getFloat()
            r3.pivotY = r0
            goto L_0x0154
        L_0x014d:
            float r0 = r5.getFloat()
            r3.pivotX = r0
        L_0x0154:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.WidgetFrame.setValue(java.lang.String, androidx.constraintlayout.core.parser.CLElement):boolean");
    }

    public String getId() {
        if (this.widget == null) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return this.widget.stringId;
    }

    /* access modifiers changed from: package-private */
    public void parseCustom(CLElement custom) throws CLParsingException {
        CLObject obj = (CLObject) custom;
        int n = obj.size();
        for (int i = 0; i < n; i++) {
            CLElement v = ((CLKey) obj.get(i)).getValue();
            String vStr = v.content();
            if (vStr.matches("#[0-9a-fA-F]+")) {
                setCustomAttribute(this.name, (int) TypedValues.Custom.TYPE_COLOR, Integer.parseInt(vStr.substring(1), 16));
            } else if (v instanceof CLNumber) {
                setCustomAttribute(this.name, (int) TypedValues.Custom.TYPE_FLOAT, v.getFloat());
            } else {
                setCustomAttribute(this.name, (int) TypedValues.Custom.TYPE_STRING, vStr);
            }
        }
    }

    public StringBuilder serialize(StringBuilder ret) {
        return serialize(ret, false);
    }

    public StringBuilder serialize(StringBuilder ret, boolean sendPhoneOrientation) {
        ret.append("{\n");
        add(ret, "left", this.left);
        add(ret, "top", this.top);
        add(ret, "right", this.right);
        add(ret, "bottom", this.bottom);
        add(ret, "pivotX", this.pivotX);
        add(ret, "pivotY", this.pivotY);
        add(ret, "rotationX", this.rotationX);
        add(ret, "rotationY", this.rotationY);
        add(ret, "rotationZ", this.rotationZ);
        add(ret, "translationX", this.translationX);
        add(ret, "translationY", this.translationY);
        add(ret, "translationZ", this.translationZ);
        add(ret, "scaleX", this.scaleX);
        add(ret, "scaleY", this.scaleY);
        add(ret, "alpha", this.alpha);
        add(ret, "visibility", this.visibility);
        add(ret, "interpolatedPos", this.interpolatedPos);
        if (this.widget != null) {
            for (ConstraintAnchor.Type side : ConstraintAnchor.Type.values()) {
                serializeAnchor(ret, side);
            }
        }
        if (sendPhoneOrientation) {
            add(ret, "phone_orientation", phone_orientation);
        }
        if (sendPhoneOrientation) {
            add(ret, "phone_orientation", phone_orientation);
        }
        if (this.mCustom.size() != 0) {
            ret.append("custom : {\n");
            for (String s : this.mCustom.keySet()) {
                CustomVariable value = this.mCustom.get(s);
                ret.append(s);
                ret.append(": ");
                switch (value.getType()) {
                    case 900:
                        ret.append(value.getIntegerValue());
                        ret.append(",\n");
                        break;
                    case TypedValues.Custom.TYPE_FLOAT:
                    case TypedValues.Custom.TYPE_DIMENSION:
                        ret.append(value.getFloatValue());
                        ret.append(",\n");
                        break;
                    case TypedValues.Custom.TYPE_COLOR:
                        ret.append("'");
                        ret.append(CustomVariable.colorString(value.getIntegerValue()));
                        ret.append("',\n");
                        break;
                    case TypedValues.Custom.TYPE_STRING:
                        ret.append("'");
                        ret.append(value.getStringValue());
                        ret.append("',\n");
                        break;
                    case TypedValues.Custom.TYPE_BOOLEAN:
                        ret.append("'");
                        ret.append(value.getBooleanValue());
                        ret.append("',\n");
                        break;
                }
            }
            ret.append("}\n");
        }
        ret.append("}\n");
        return ret;
    }

    private void serializeAnchor(StringBuilder ret, ConstraintAnchor.Type type) {
        ConstraintAnchor anchor = this.widget.getAnchor(type);
        if (anchor != null && anchor.mTarget != null) {
            ret.append("Anchor");
            ret.append(type.name());
            ret.append(": ['");
            String str = anchor.mTarget.getOwner().stringId;
            ret.append(str == null ? "#PARENT" : str);
            ret.append("', '");
            ret.append(anchor.mTarget.getType().name());
            ret.append("', '");
            ret.append(anchor.mMargin);
            ret.append("'],\n");
        }
    }

    private static void add(StringBuilder s, String title, int value) {
        s.append(title);
        s.append(": ");
        s.append(value);
        s.append(",\n");
    }

    private static void add(StringBuilder s, String title, float value) {
        if (!Float.isNaN(value)) {
            s.append(title);
            s.append(": ");
            s.append(value);
            s.append(",\n");
        }
    }

    /* access modifiers changed from: package-private */
    public void printCustomAttributes() {
        String ss;
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String ss2 = (".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName()) + " " + (hashCode() % 1000);
        if (this.widget != null) {
            ss = ss2 + "/" + (this.widget.hashCode() % 1000) + " ";
        } else {
            ss = ss2 + "/NULL ";
        }
        if (this.mCustom != null) {
            for (String key : this.mCustom.keySet()) {
                System.out.println(ss + this.mCustom.get(key).toString());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void logv(String str) {
        String ss;
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String ss2 = (".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName()) + " " + (hashCode() % 1000);
        if (this.widget != null) {
            ss = ss2 + "/" + (this.widget.hashCode() % 1000);
        } else {
            ss = ss2 + "/NULL";
        }
        System.out.println(ss + " " + str);
    }

    public void setCustomValue(CustomAttribute valueAt, float[] mTempValues) {
    }

    /* access modifiers changed from: package-private */
    public void setMotionAttributes(TypedBundle motionProperties) {
        this.mMotionProperties = motionProperties;
    }

    public TypedBundle getMotionProperties() {
        return this.mMotionProperties;
    }
}
