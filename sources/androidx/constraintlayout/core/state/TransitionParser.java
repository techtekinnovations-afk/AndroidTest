package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.parser.CLArray;
import androidx.constraintlayout.core.parser.CLContainer;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.state.Transition;

public class TransitionParser {
    @Deprecated
    public static void parse(CLObject json, Transition transition, CorePixelDp dpToPixel) throws CLParsingException {
        parse(json, transition);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parse(androidx.constraintlayout.core.parser.CLObject r11, androidx.constraintlayout.core.state.Transition r12) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            r12.resetProperties()
            java.lang.String r0 = "pathMotionArc"
            java.lang.String r0 = r11.getStringOrNull((java.lang.String) r0)
            androidx.constraintlayout.core.motion.utils.TypedBundle r1 = new androidx.constraintlayout.core.motion.utils.TypedBundle
            r1.<init>()
            r2 = 0
            if (r0 == 0) goto L_0x007b
            r2 = 1
            int r3 = r0.hashCode()
            r4 = 5
            r5 = 4
            r6 = 3
            r7 = 2
            r8 = 1
            r9 = 0
            switch(r3) {
                case -1857024520: goto L_0x0052;
                case -1007052250: goto L_0x0048;
                case 3145837: goto L_0x003e;
                case 3387192: goto L_0x0034;
                case 92611485: goto L_0x002a;
                case 93621297: goto L_0x0020;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x005c
        L_0x0020:
            java.lang.String r3 = "below"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x001f
            r3 = r5
            goto L_0x005d
        L_0x002a:
            java.lang.String r3 = "above"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x001f
            r3 = r4
            goto L_0x005d
        L_0x0034:
            java.lang.String r3 = "none"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x001f
            r3 = r9
            goto L_0x005d
        L_0x003e:
            java.lang.String r3 = "flip"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x001f
            r3 = r6
            goto L_0x005d
        L_0x0048:
            java.lang.String r3 = "startHorizontal"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x001f
            r3 = r7
            goto L_0x005d
        L_0x0052:
            java.lang.String r3 = "startVertical"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x001f
            r3 = r8
            goto L_0x005d
        L_0x005c:
            r3 = -1
        L_0x005d:
            r10 = 509(0x1fd, float:7.13E-43)
            switch(r3) {
                case 0: goto L_0x0077;
                case 1: goto L_0x0073;
                case 2: goto L_0x006f;
                case 3: goto L_0x006b;
                case 4: goto L_0x0067;
                case 5: goto L_0x0063;
                default: goto L_0x0062;
            }
        L_0x0062:
            goto L_0x007b
        L_0x0063:
            r1.add((int) r10, (int) r4)
            goto L_0x007b
        L_0x0067:
            r1.add((int) r10, (int) r5)
            goto L_0x007b
        L_0x006b:
            r1.add((int) r10, (int) r6)
            goto L_0x007b
        L_0x006f:
            r1.add((int) r10, (int) r7)
            goto L_0x007b
        L_0x0073:
            r1.add((int) r10, (int) r8)
            goto L_0x007b
        L_0x0077:
            r1.add((int) r10, (int) r9)
        L_0x007b:
            java.lang.String r3 = "interpolator"
            java.lang.String r3 = r11.getStringOrNull((java.lang.String) r3)
            if (r3 == 0) goto L_0x0089
            r2 = 1
            r4 = 705(0x2c1, float:9.88E-43)
            r1.add((int) r4, (java.lang.String) r3)
        L_0x0089:
            java.lang.String r4 = "staggered"
            float r4 = r11.getFloatOrNaN(r4)
            boolean r5 = java.lang.Float.isNaN(r4)
            if (r5 != 0) goto L_0x009b
            r2 = 1
            r5 = 706(0x2c2, float:9.9E-43)
            r1.add((int) r5, (float) r4)
        L_0x009b:
            if (r2 == 0) goto L_0x00a0
            r12.setTransitionProperties(r1)
        L_0x00a0:
            java.lang.String r5 = "onSwipe"
            androidx.constraintlayout.core.parser.CLObject r5 = r11.getObjectOrNull(r5)
            if (r5 == 0) goto L_0x00ab
            parseOnSwipe(r5, r12)
        L_0x00ab:
            parseKeyFrames(r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.TransitionParser.parse(androidx.constraintlayout.core.parser.CLObject, androidx.constraintlayout.core.state.Transition):void");
    }

    private static void parseOnSwipe(CLContainer onSwipe, Transition transition) {
        CLContainer cLContainer = onSwipe;
        String anchor = cLContainer.getStringOrNull("anchor");
        int side = map(cLContainer.getStringOrNull("side"), Transition.OnSwipe.SIDES);
        int direction = map(cLContainer.getStringOrNull("direction"), Transition.OnSwipe.DIRECTIONS);
        float scale = cLContainer.getFloatOrNaN("scale");
        float threshold = cLContainer.getFloatOrNaN("threshold");
        float maxVelocity = cLContainer.getFloatOrNaN("maxVelocity");
        float maxAccel = cLContainer.getFloatOrNaN("maxAccel");
        String limitBounds = cLContainer.getStringOrNull("limitBounds");
        int autoCompleteMode = map(cLContainer.getStringOrNull("mode"), Transition.OnSwipe.MODE);
        int touchUp = map(cLContainer.getStringOrNull("touchUp"), Transition.OnSwipe.TOUCH_UP);
        float springMass = cLContainer.getFloatOrNaN("springMass");
        float springStiffness = cLContainer.getFloatOrNaN("springStiffness");
        float springDamping = cLContainer.getFloatOrNaN("springDamping");
        float stopThreshold = cLContainer.getFloatOrNaN("stopThreshold");
        int springBoundary = map(cLContainer.getStringOrNull("springBoundary"), Transition.OnSwipe.BOUNDARY);
        String around = cLContainer.getStringOrNull("around");
        Transition.OnSwipe swipe = transition.createOnSwipe();
        swipe.setAnchorId(anchor);
        swipe.setAnchorSide(side);
        swipe.setDragDirection(direction);
        swipe.setDragScale(scale);
        swipe.setDragThreshold(threshold);
        swipe.setMaxVelocity(maxVelocity);
        swipe.setMaxAcceleration(maxAccel);
        swipe.setLimitBoundsTo(limitBounds);
        swipe.setAutoCompleteMode(autoCompleteMode);
        swipe.setOnTouchUp(touchUp);
        swipe.setSpringMass(springMass);
        swipe.setSpringStiffness(springStiffness);
        swipe.setSpringDamping(springDamping);
        String str = anchor;
        swipe.setSpringStopThreshold(stopThreshold);
        swipe.setSpringBoundary(springBoundary);
        swipe.setRotationCenterId(around);
    }

    private static int map(String val, String... types) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(val)) {
                return i;
            }
        }
        return 0;
    }

    private static void map(TypedBundle bundle, int type, String val, String... types) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(val)) {
                bundle.add(type, i);
            }
        }
    }

    public static void parseKeyFrames(CLObject transitionCLObject, Transition transition) throws CLParsingException {
        CLContainer keyframes = transitionCLObject.getObjectOrNull("KeyFrames");
        if (keyframes != null) {
            CLArray keyPositions = keyframes.getArrayOrNull("KeyPositions");
            if (keyPositions != null) {
                for (int i = 0; i < keyPositions.size(); i++) {
                    CLElement keyPosition = keyPositions.get(i);
                    if (keyPosition instanceof CLObject) {
                        parseKeyPosition((CLObject) keyPosition, transition);
                    }
                }
            }
            CLArray keyAttributes = keyframes.getArrayOrNull(TypedValues.AttributesType.NAME);
            if (keyAttributes != null) {
                for (int i2 = 0; i2 < keyAttributes.size(); i2++) {
                    CLElement keyAttribute = keyAttributes.get(i2);
                    if (keyAttribute instanceof CLObject) {
                        parseKeyAttribute((CLObject) keyAttribute, transition);
                    }
                }
            }
            CLArray keyCycles = keyframes.getArrayOrNull("KeyCycles");
            if (keyCycles != null) {
                for (int i3 = 0; i3 < keyCycles.size(); i3++) {
                    CLElement keyCycle = keyCycles.get(i3);
                    if (keyCycle instanceof CLObject) {
                        parseKeyCycle((CLObject) keyCycle, transition);
                    }
                }
            }
        }
    }

    private static void parseKeyPosition(CLObject keyPosition, Transition transition) throws CLParsingException {
        CLObject cLObject = keyPosition;
        TypedBundle bundle = new TypedBundle();
        CLArray targets = cLObject.getArray(TypedValues.AttributesType.S_TARGET);
        CLArray frames = cLObject.getArray("frames");
        CLArray percentX = cLObject.getArrayOrNull("percentX");
        CLArray percentY = cLObject.getArrayOrNull("percentY");
        CLArray percentWidth = cLObject.getArrayOrNull("percentWidth");
        CLArray percentHeight = cLObject.getArrayOrNull("percentHeight");
        String pathMotionArc = cLObject.getStringOrNull(TypedValues.TransitionType.S_PATH_MOTION_ARC);
        String transitionEasing = cLObject.getStringOrNull("transitionEasing");
        String curveFit = cLObject.getStringOrNull("curveFit");
        String type = cLObject.getStringOrNull("type");
        if (type == null) {
            type = "parentRelative";
        }
        if (percentX != null && frames.size() != percentX.size()) {
            return;
        }
        if (percentY == null || frames.size() == percentY.size()) {
            int i = 0;
            while (i < targets.size()) {
                String target = targets.getString(i);
                int pos_type = map(type, "deltaRelative", "pathRelative", "parentRelative");
                bundle.clear();
                bundle.add((int) TypedValues.PositionType.TYPE_POSITION_TYPE, pos_type);
                if (curveFit != null) {
                    map(bundle, TypedValues.PositionType.TYPE_CURVE_FIT, curveFit, "spline", "linear");
                }
                bundle.addIfNotNull(TypedValues.PositionType.TYPE_TRANSITION_EASING, transitionEasing);
                if (pathMotionArc != null) {
                    map(bundle, 509, pathMotionArc, "none", "startVertical", "startHorizontal", "flip", "below", "above");
                }
                int j = 0;
                while (j < frames.size()) {
                    bundle.add(100, frames.getInt(j));
                    set(bundle, TypedValues.PositionType.TYPE_PERCENT_X, percentX, j);
                    set(bundle, TypedValues.PositionType.TYPE_PERCENT_Y, percentY, j);
                    set(bundle, TypedValues.PositionType.TYPE_PERCENT_WIDTH, percentWidth, j);
                    set(bundle, TypedValues.PositionType.TYPE_PERCENT_HEIGHT, percentHeight, j);
                    transition.addKeyPosition(target, bundle);
                    j++;
                    targets = targets;
                }
                CLArray targets2 = targets;
                Transition transition2 = transition;
                i++;
                CLObject cLObject2 = keyPosition;
                targets = targets2;
            }
        }
    }

    private static void set(TypedBundle bundle, int type, CLArray array, int index) throws CLParsingException {
        if (array != null) {
            bundle.add(type, array.getFloat(index));
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: androidx.constraintlayout.core.motion.CustomVariable[][]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void parseKeyAttribute(androidx.constraintlayout.core.parser.CLObject r31, androidx.constraintlayout.core.state.Transition r32) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            r0 = r31
            r1 = r32
            java.lang.String r2 = "target"
            androidx.constraintlayout.core.parser.CLArray r2 = r0.getArrayOrNull(r2)
            if (r2 != 0) goto L_0x000d
            return
        L_0x000d:
            java.lang.String r3 = "frames"
            androidx.constraintlayout.core.parser.CLArray r3 = r0.getArrayOrNull(r3)
            if (r3 != 0) goto L_0x0016
            return
        L_0x0016:
            java.lang.String r4 = "transitionEasing"
            java.lang.String r4 = r0.getStringOrNull((java.lang.String) r4)
            r5 = 9
            java.lang.String[] r6 = new java.lang.String[r5]
            java.lang.String r7 = "scaleX"
            r8 = 0
            r6[r8] = r7
            java.lang.String r7 = "scaleY"
            r9 = 1
            r6[r9] = r7
            java.lang.String r7 = "translationX"
            r10 = 2
            r6[r10] = r7
            r7 = 3
            java.lang.String r11 = "translationY"
            r6[r7] = r11
            r7 = 4
            java.lang.String r11 = "translationZ"
            r6[r7] = r11
            r7 = 5
            java.lang.String r11 = "rotationX"
            r6[r7] = r11
            r7 = 6
            java.lang.String r11 = "rotationY"
            r6[r7] = r11
            r7 = 7
            java.lang.String r11 = "rotationZ"
            r6[r7] = r11
            r7 = 8
            java.lang.String r11 = "alpha"
            r6[r7] = r11
            int[] r7 = new int[r5]
            r7 = {311, 312, 304, 305, 306, 308, 309, 310, 303} // fill-array
            boolean[] r5 = new boolean[r5]
            r5 = {0, 0, 1, 1, 1, 0, 0, 0, 0} // fill-array
            int r11 = r3.size()
            androidx.constraintlayout.core.motion.utils.TypedBundle[] r11 = new androidx.constraintlayout.core.motion.utils.TypedBundle[r11]
            r12 = 0
            r13 = 0
        L_0x0060:
            int r14 = r3.size()
            if (r13 >= r14) goto L_0x0070
            androidx.constraintlayout.core.motion.utils.TypedBundle r14 = new androidx.constraintlayout.core.motion.utils.TypedBundle
            r14.<init>()
            r11[r13] = r14
            int r13 = r13 + 1
            goto L_0x0060
        L_0x0070:
            r13 = 0
        L_0x0071:
            int r14 = r6.length
            if (r13 >= r14) goto L_0x00f7
            r14 = r6[r13]
            r15 = r7[r13]
            boolean r16 = r5[r13]
            r17 = r9
            androidx.constraintlayout.core.parser.CLArray r9 = r0.getArrayOrNull(r14)
            if (r9 == 0) goto L_0x00af
            r18 = r8
            int r8 = r9.size()
            int r10 = r11.length
            if (r8 != r10) goto L_0x008e
            r19 = r5
            goto L_0x00b3
        L_0x008e:
            androidx.constraintlayout.core.parser.CLParsingException r8 = new androidx.constraintlayout.core.parser.CLParsingException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r19 = r5
            java.lang.String r5 = "incorrect size for "
            java.lang.StringBuilder r5 = r10.append(r5)
            java.lang.StringBuilder r5 = r5.append(r14)
            java.lang.String r10 = " array, not matching targets array!"
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.String r5 = r5.toString()
            r8.<init>(r5, r0)
            throw r8
        L_0x00af:
            r19 = r5
            r18 = r8
        L_0x00b3:
            if (r9 == 0) goto L_0x00ce
            r5 = 0
        L_0x00b6:
            int r8 = r11.length
            if (r5 >= r8) goto L_0x00cd
            float r8 = r9.getFloat((int) r5)
            if (r16 == 0) goto L_0x00c5
            androidx.constraintlayout.core.state.CorePixelDp r10 = r1.mToPixel
            float r8 = r10.toPixels(r8)
        L_0x00c5:
            r10 = r11[r5]
            r10.add((int) r15, (float) r8)
            int r5 = r5 + 1
            goto L_0x00b6
        L_0x00cd:
            goto L_0x00ec
        L_0x00ce:
            float r5 = r0.getFloatOrNaN(r14)
            boolean r8 = java.lang.Float.isNaN(r5)
            if (r8 != 0) goto L_0x00ec
            if (r16 == 0) goto L_0x00e0
            androidx.constraintlayout.core.state.CorePixelDp r8 = r1.mToPixel
            float r5 = r8.toPixels(r5)
        L_0x00e0:
            r8 = 0
        L_0x00e1:
            int r10 = r11.length
            if (r8 >= r10) goto L_0x00ec
            r10 = r11[r8]
            r10.add((int) r15, (float) r5)
            int r8 = r8 + 1
            goto L_0x00e1
        L_0x00ec:
            int r13 = r13 + 1
            r9 = r17
            r8 = r18
            r5 = r19
            r10 = 2
            goto L_0x0071
        L_0x00f7:
            r19 = r5
            r18 = r8
            r17 = r9
            java.lang.String r5 = "custom"
            androidx.constraintlayout.core.parser.CLElement r5 = r0.getOrNull((java.lang.String) r5)
            if (r5 == 0) goto L_0x0233
            boolean r8 = r5 instanceof androidx.constraintlayout.core.parser.CLObject
            if (r8 == 0) goto L_0x0233
            r8 = r5
            androidx.constraintlayout.core.parser.CLObject r8 = (androidx.constraintlayout.core.parser.CLObject) r8
            int r9 = r8.size()
            int r10 = r3.size()
            r13 = 2
            int[] r14 = new int[r13]
            r14[r17] = r9
            r14[r18] = r10
            java.lang.Class<androidx.constraintlayout.core.motion.CustomVariable> r10 = androidx.constraintlayout.core.motion.CustomVariable.class
            java.lang.Object r10 = java.lang.reflect.Array.newInstance(r10, r14)
            r12 = r10
            androidx.constraintlayout.core.motion.CustomVariable[][] r12 = (androidx.constraintlayout.core.motion.CustomVariable[][]) r12
            r10 = 0
        L_0x0125:
            if (r10 >= r9) goto L_0x022a
            androidx.constraintlayout.core.parser.CLElement r13 = r8.get((int) r10)
            androidx.constraintlayout.core.parser.CLKey r13 = (androidx.constraintlayout.core.parser.CLKey) r13
            java.lang.String r14 = r13.content()
            androidx.constraintlayout.core.parser.CLElement r15 = r13.getValue()
            boolean r15 = r15 instanceof androidx.constraintlayout.core.parser.CLArray
            r16 = r5
            r21 = -1
            if (r15 == 0) goto L_0x01c3
            androidx.constraintlayout.core.parser.CLElement r15 = r13.getValue()
            androidx.constraintlayout.core.parser.CLArray r15 = (androidx.constraintlayout.core.parser.CLArray) r15
            int r5 = r15.size()
            r24 = r6
            int r6 = r11.length
            if (r5 != r6) goto L_0x01bc
            if (r5 <= 0) goto L_0x01bc
            r25 = r5
            r6 = r18
            androidx.constraintlayout.core.parser.CLElement r5 = r15.get((int) r6)
            boolean r5 = r5 instanceof androidx.constraintlayout.core.parser.CLNumber
            if (r5 == 0) goto L_0x0183
            r5 = 0
        L_0x015b:
            int r6 = r11.length
            if (r5 >= r6) goto L_0x017c
            r6 = r12[r5]
            r20 = r6
            androidx.constraintlayout.core.motion.CustomVariable r6 = new androidx.constraintlayout.core.motion.CustomVariable
            androidx.constraintlayout.core.parser.CLElement r21 = r15.get((int) r5)
            r22 = r5
            float r5 = r21.getFloat()
            r26 = r7
            r7 = 901(0x385, float:1.263E-42)
            r6.<init>((java.lang.String) r14, (int) r7, (float) r5)
            r20[r10] = r6
            int r5 = r22 + 1
            r7 = r26
            goto L_0x015b
        L_0x017c:
            r22 = r5
            r26 = r7
            r28 = r8
            goto L_0x01c2
        L_0x0183:
            r26 = r7
            r5 = 0
        L_0x0186:
            int r6 = r11.length
            if (r5 >= r6) goto L_0x01b7
            androidx.constraintlayout.core.parser.CLElement r6 = r15.get((int) r5)
            java.lang.String r6 = r6.content()
            long r6 = androidx.constraintlayout.core.state.ConstraintSetParser.parseColorString(r6)
            int r23 = (r6 > r21 ? 1 : (r6 == r21 ? 0 : -1))
            if (r23 == 0) goto L_0x01ac
            r23 = r12[r5]
            r27 = r5
            androidx.constraintlayout.core.motion.CustomVariable r5 = new androidx.constraintlayout.core.motion.CustomVariable
            r28 = r8
            int r8 = (int) r6
            r29 = r6
            r6 = 902(0x386, float:1.264E-42)
            r5.<init>((java.lang.String) r14, (int) r6, (int) r8)
            r23[r10] = r5
            goto L_0x01b2
        L_0x01ac:
            r27 = r5
            r29 = r6
            r28 = r8
        L_0x01b2:
            int r5 = r27 + 1
            r8 = r28
            goto L_0x0186
        L_0x01b7:
            r27 = r5
            r28 = r8
            goto L_0x01c2
        L_0x01bc:
            r25 = r5
            r26 = r7
            r28 = r8
        L_0x01c2:
            goto L_0x021c
        L_0x01c3:
            r24 = r6
            r26 = r7
            r28 = r8
            androidx.constraintlayout.core.parser.CLElement r5 = r13.getValue()
            boolean r6 = r5 instanceof androidx.constraintlayout.core.parser.CLNumber
            if (r6 == 0) goto L_0x01ee
            float r6 = r5.getFloat()
            r7 = 0
        L_0x01d6:
            int r8 = r11.length
            if (r7 >= r8) goto L_0x01eb
            r8 = r12[r7]
            androidx.constraintlayout.core.motion.CustomVariable r15 = new androidx.constraintlayout.core.motion.CustomVariable
            r25 = r5
            r5 = 901(0x385, float:1.263E-42)
            r15.<init>((java.lang.String) r14, (int) r5, (float) r6)
            r8[r10] = r15
            int r7 = r7 + 1
            r5 = r25
            goto L_0x01d6
        L_0x01eb:
            r25 = r5
            goto L_0x021c
        L_0x01ee:
            r25 = r5
            java.lang.String r5 = r25.content()
            long r5 = androidx.constraintlayout.core.state.ConstraintSetParser.parseColorString(r5)
            int r7 = (r5 > r21 ? 1 : (r5 == r21 ? 0 : -1))
            if (r7 == 0) goto L_0x021a
            r7 = 0
        L_0x01fd:
            int r8 = r11.length
            if (r7 >= r8) goto L_0x0215
            r8 = r12[r7]
            androidx.constraintlayout.core.motion.CustomVariable r15 = new androidx.constraintlayout.core.motion.CustomVariable
            r21 = r7
            int r7 = (int) r5
            r22 = r5
            r5 = 902(0x386, float:1.264E-42)
            r15.<init>((java.lang.String) r14, (int) r5, (int) r7)
            r8[r10] = r15
            int r7 = r21 + 1
            r5 = r22
            goto L_0x01fd
        L_0x0215:
            r22 = r5
            r21 = r7
            goto L_0x021c
        L_0x021a:
            r22 = r5
        L_0x021c:
            int r10 = r10 + 1
            r5 = r16
            r6 = r24
            r7 = r26
            r8 = r28
            r18 = 0
            goto L_0x0125
        L_0x022a:
            r16 = r5
            r24 = r6
            r26 = r7
            r28 = r8
            goto L_0x0239
        L_0x0233:
            r16 = r5
            r24 = r6
            r26 = r7
        L_0x0239:
            java.lang.String r5 = "curveFit"
            java.lang.String r5 = r0.getStringOrNull((java.lang.String) r5)
            r6 = 0
        L_0x0240:
            int r7 = r2.size()
            if (r6 >= r7) goto L_0x028c
            r7 = 0
        L_0x0247:
            int r8 = r11.length
            if (r7 >= r8) goto L_0x0286
            java.lang.String r8 = r2.getString((int) r6)
            r9 = r11[r7]
            if (r5 == 0) goto L_0x0269
            r13 = 2
            java.lang.String[] r10 = new java.lang.String[r13]
            java.lang.String r14 = "spline"
            r18 = 0
            r10[r18] = r14
            java.lang.String r14 = "linear"
            r10[r17] = r14
            int r10 = map(r5, r10)
            r14 = 508(0x1fc, float:7.12E-43)
            r9.add((int) r14, (int) r10)
            goto L_0x026c
        L_0x0269:
            r13 = 2
            r18 = 0
        L_0x026c:
            r10 = 501(0x1f5, float:7.02E-43)
            r9.addIfNotNull(r10, r4)
            int r10 = r3.getInt((int) r7)
            r14 = 100
            r9.add((int) r14, (int) r10)
            if (r12 == 0) goto L_0x027f
            r14 = r12[r7]
            goto L_0x0280
        L_0x027f:
            r14 = 0
        L_0x0280:
            r1.addKeyAttribute(r8, r9, r14)
            int r7 = r7 + 1
            goto L_0x0247
        L_0x0286:
            r13 = 2
            r18 = 0
            int r6 = r6 + 1
            goto L_0x0240
        L_0x028c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.TransitionParser.parseKeyAttribute(androidx.constraintlayout.core.parser.CLObject, androidx.constraintlayout.core.state.Transition):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void parseKeyCycle(androidx.constraintlayout.core.parser.CLObject r19, androidx.constraintlayout.core.state.Transition r20) throws androidx.constraintlayout.core.parser.CLParsingException {
        /*
            r0 = r19
            r1 = r20
            java.lang.String r2 = "target"
            androidx.constraintlayout.core.parser.CLArray r2 = r0.getArray((java.lang.String) r2)
            java.lang.String r3 = "frames"
            androidx.constraintlayout.core.parser.CLArray r3 = r0.getArray((java.lang.String) r3)
            java.lang.String r4 = "transitionEasing"
            java.lang.String r4 = r0.getStringOrNull((java.lang.String) r4)
            r5 = 12
            java.lang.String[] r6 = new java.lang.String[r5]
            java.lang.String r7 = "scaleX"
            r8 = 0
            r6[r8] = r7
            java.lang.String r7 = "scaleY"
            r9 = 1
            r6[r9] = r7
            java.lang.String r7 = "translationX"
            r10 = 2
            r6[r10] = r7
            r7 = 3
            java.lang.String r11 = "translationY"
            r6[r7] = r11
            r7 = 4
            java.lang.String r11 = "translationZ"
            r6[r7] = r11
            r7 = 5
            java.lang.String r11 = "rotationX"
            r6[r7] = r11
            r7 = 6
            java.lang.String r11 = "rotationY"
            r6[r7] = r11
            r7 = 7
            java.lang.String r11 = "rotationZ"
            r6[r7] = r11
            r7 = 8
            java.lang.String r11 = "alpha"
            r6[r7] = r11
            r7 = 9
            java.lang.String r11 = "period"
            r6[r7] = r11
            r7 = 10
            java.lang.String r11 = "offset"
            r6[r7] = r11
            r7 = 11
            java.lang.String r11 = "phase"
            r6[r7] = r11
            int[] r7 = new int[r5]
            r7 = {311, 312, 304, 305, 306, 308, 309, 310, 403, 423, 424, 425} // fill-array
            int[] r5 = new int[r5]
            r5 = {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 2, 0} // fill-array
            int r11 = r3.size()
            androidx.constraintlayout.core.motion.utils.TypedBundle[] r11 = new androidx.constraintlayout.core.motion.utils.TypedBundle[r11]
            r12 = 0
        L_0x006b:
            int r13 = r11.length
            if (r12 >= r13) goto L_0x0078
            androidx.constraintlayout.core.motion.utils.TypedBundle r13 = new androidx.constraintlayout.core.motion.utils.TypedBundle
            r13.<init>()
            r11[r12] = r13
            int r12 = r12 + 1
            goto L_0x006b
        L_0x0078:
            r12 = 0
            r13 = 0
        L_0x007a:
            int r14 = r6.length
            if (r13 >= r14) goto L_0x008d
            r14 = r6[r13]
            boolean r14 = r0.has(r14)
            if (r14 == 0) goto L_0x008a
            r14 = r5[r13]
            if (r14 != r9) goto L_0x008a
            r12 = 1
        L_0x008a:
            int r13 = r13 + 1
            goto L_0x007a
        L_0x008d:
            r13 = 0
        L_0x008e:
            int r14 = r6.length
            if (r13 >= r14) goto L_0x011b
            r14 = r6[r13]
            r15 = r7[r13]
            r8 = r5[r13]
            androidx.constraintlayout.core.parser.CLArray r10 = r0.getArrayOrNull(r14)
            if (r10 == 0) goto L_0x00af
            int r9 = r10.size()
            r17 = r5
            int r5 = r11.length
            if (r9 != r5) goto L_0x00a7
            goto L_0x00b1
        L_0x00a7:
            androidx.constraintlayout.core.parser.CLParsingException r5 = new androidx.constraintlayout.core.parser.CLParsingException
            java.lang.String r9 = "incorrect size for $attrName array, not matching targets array!"
            r5.<init>(r9, r0)
            throw r5
        L_0x00af:
            r17 = r5
        L_0x00b1:
            if (r10 == 0) goto L_0x00df
            r5 = 0
        L_0x00b4:
            int r9 = r11.length
            if (r5 >= r9) goto L_0x00da
            float r9 = r10.getFloat((int) r5)
            r18 = r5
            r5 = 1
            if (r8 != r5) goto L_0x00c7
            androidx.constraintlayout.core.state.CorePixelDp r5 = r1.mToPixel
            float r9 = r5.toPixels(r9)
            goto L_0x00d2
        L_0x00c7:
            r5 = 2
            if (r8 != r5) goto L_0x00d2
            if (r12 == 0) goto L_0x00d2
            androidx.constraintlayout.core.state.CorePixelDp r5 = r1.mToPixel
            float r9 = r5.toPixels(r9)
        L_0x00d2:
            r5 = r11[r18]
            r5.add((int) r15, (float) r9)
            int r5 = r18 + 1
            goto L_0x00b4
        L_0x00da:
            r18 = r5
            r18 = r6
            goto L_0x0110
        L_0x00df:
            float r5 = r0.getFloatOrNaN(r14)
            boolean r9 = java.lang.Float.isNaN(r5)
            if (r9 != 0) goto L_0x010e
            r9 = 1
            if (r8 != r9) goto L_0x00f3
            androidx.constraintlayout.core.state.CorePixelDp r9 = r1.mToPixel
            float r5 = r9.toPixels(r5)
            goto L_0x00fe
        L_0x00f3:
            r9 = 2
            if (r8 != r9) goto L_0x00fe
            if (r12 == 0) goto L_0x00fe
            androidx.constraintlayout.core.state.CorePixelDp r9 = r1.mToPixel
            float r5 = r9.toPixels(r5)
        L_0x00fe:
            r9 = 0
        L_0x00ff:
            r18 = r6
            int r6 = r11.length
            if (r9 >= r6) goto L_0x0110
            r6 = r11[r9]
            r6.add((int) r15, (float) r5)
            int r9 = r9 + 1
            r6 = r18
            goto L_0x00ff
        L_0x010e:
            r18 = r6
        L_0x0110:
            int r13 = r13 + 1
            r5 = r17
            r6 = r18
            r8 = 0
            r9 = 1
            r10 = 2
            goto L_0x008e
        L_0x011b:
            r17 = r5
            r18 = r6
            java.lang.String r5 = "curveFit"
            java.lang.String r5 = r0.getStringOrNull((java.lang.String) r5)
            java.lang.String r6 = "easing"
            java.lang.String r6 = r0.getStringOrNull((java.lang.String) r6)
            java.lang.String r8 = "waveShape"
            java.lang.String r8 = r0.getStringOrNull((java.lang.String) r8)
            java.lang.String r9 = "customWave"
            java.lang.String r9 = r0.getStringOrNull((java.lang.String) r9)
            r10 = 0
        L_0x0138:
            int r13 = r2.size()
            if (r10 >= r13) goto L_0x01ba
            r13 = 0
        L_0x013f:
            int r14 = r11.length
            if (r13 >= r14) goto L_0x01b2
            java.lang.String r14 = r2.getString((int) r10)
            r15 = r11[r13]
            if (r5 == 0) goto L_0x0182
            int r16 = r5.hashCode()
            switch(r16) {
                case -1102672091: goto L_0x015c;
                case -895858735: goto L_0x0152;
                default: goto L_0x0151;
            }
        L_0x0151:
            goto L_0x0166
        L_0x0152:
            java.lang.String r0 = "spline"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0151
            r0 = 0
            goto L_0x0167
        L_0x015c:
            java.lang.String r0 = "linear"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0151
            r0 = 1
            goto L_0x0167
        L_0x0166:
            r0 = -1
        L_0x0167:
            r16 = r0
            r0 = 401(0x191, float:5.62E-43)
            switch(r16) {
                case 0: goto L_0x017a;
                case 1: goto L_0x0172;
                default: goto L_0x016e;
            }
        L_0x016e:
            r16 = r2
            r2 = 0
            goto L_0x0185
        L_0x0172:
            r16 = r2
            r2 = 1
            r15.add((int) r0, (int) r2)
            r2 = 0
            goto L_0x0185
        L_0x017a:
            r16 = r2
            r2 = 1
            r2 = 0
            r15.add((int) r0, (int) r2)
            goto L_0x0185
        L_0x0182:
            r16 = r2
            r2 = 0
        L_0x0185:
            r0 = 501(0x1f5, float:7.02E-43)
            r15.addIfNotNull(r0, r4)
            if (r6 == 0) goto L_0x0191
            r0 = 420(0x1a4, float:5.89E-43)
            r15.add((int) r0, (java.lang.String) r6)
        L_0x0191:
            if (r8 == 0) goto L_0x0198
            r0 = 421(0x1a5, float:5.9E-43)
            r15.add((int) r0, (java.lang.String) r8)
        L_0x0198:
            if (r9 == 0) goto L_0x019f
            r0 = 422(0x1a6, float:5.91E-43)
            r15.add((int) r0, (java.lang.String) r9)
        L_0x019f:
            int r0 = r3.getInt((int) r13)
            r2 = 100
            r15.add((int) r2, (int) r0)
            r1.addKeyCycle(r14, r15)
            int r13 = r13 + 1
            r0 = r19
            r2 = r16
            goto L_0x013f
        L_0x01b2:
            r16 = r2
            int r10 = r10 + 1
            r0 = r19
            goto L_0x0138
        L_0x01ba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.state.TransitionParser.parseKeyCycle(androidx.constraintlayout.core.parser.CLObject, androidx.constraintlayout.core.state.Transition):void");
    }
}
