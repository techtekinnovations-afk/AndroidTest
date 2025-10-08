package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class KeyFrames {
    private static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
    private static final String CUSTOM_METHOD = "CustomMethod";
    private static final String TAG = "KeyFrames";
    public static final int UNSET = -1;
    static HashMap<String, Constructor<? extends Key>> sKeyMakers = new HashMap<>();
    private HashMap<Integer, ArrayList<Key>> mFramesMap = new HashMap<>();

    static {
        try {
            sKeyMakers.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
            sKeyMakers.put(TypedValues.PositionType.NAME, KeyPosition.class.getConstructor(new Class[0]));
            sKeyMakers.put(TypedValues.CycleType.NAME, KeyCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put(TypedValues.TriggerType.NAME, KeyTrigger.class.getConstructor(new Class[0]));
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "unable to load", e);
        }
    }

    public void addKey(Key key) {
        if (!this.mFramesMap.containsKey(Integer.valueOf(key.mTargetId))) {
            this.mFramesMap.put(Integer.valueOf(key.mTargetId), new ArrayList());
        }
        ArrayList<Key> frames = this.mFramesMap.get(Integer.valueOf(key.mTargetId));
        if (frames != null) {
            frames.add(key);
        }
    }

    public KeyFrames() {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public KeyFrames(android.content.Context r9, org.xmlpull.v1.XmlPullParser r10) {
        /*
            r8 = this;
            java.lang.String r0 = "Error parsing XML resource"
            java.lang.String r1 = "KeyFrames"
            r8.<init>()
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r8.mFramesMap = r2
            r2 = 0
            r3 = 0
            int r4 = r10.getEventType()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
        L_0x0014:
            r5 = 1
            if (r4 == r5) goto L_0x00ed
            switch(r4) {
                case 0: goto L_0x00e5;
                case 1: goto L_0x001a;
                case 2: goto L_0x0029;
                case 3: goto L_0x001c;
                default: goto L_0x001a;
            }     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
        L_0x001a:
            goto L_0x00e6
        L_0x001c:
            java.lang.String r5 = "KeyFrameSet"
            java.lang.String r6 = r10.getName()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            boolean r5 = r5.equals(r6)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x00e6
            return
        L_0x0029:
            java.lang.String r6 = r10.getName()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r2 = r6
            java.util.HashMap<java.lang.String, java.lang.reflect.Constructor<? extends androidx.constraintlayout.motion.widget.Key>> r6 = sKeyMakers     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            boolean r6 = r6.containsKey(r2)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r6 == 0) goto L_0x00bd
            int r6 = r2.hashCode()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            switch(r6) {
                case -300573030: goto L_0x0065;
                case -298435811: goto L_0x005b;
                case 540053991: goto L_0x0051;
                case 1153397896: goto L_0x0048;
                case 1308496505: goto L_0x003e;
                default: goto L_0x003d;
            }     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
        L_0x003d:
            goto L_0x006f
        L_0x003e:
            java.lang.String r5 = "KeyTrigger"
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x003d
            r5 = 4
            goto L_0x0070
        L_0x0048:
            java.lang.String r6 = "KeyPosition"
            boolean r6 = r2.equals(r6)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r6 == 0) goto L_0x003d
            goto L_0x0070
        L_0x0051:
            java.lang.String r5 = "KeyCycle"
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x003d
            r5 = 2
            goto L_0x0070
        L_0x005b:
            java.lang.String r5 = "KeyAttribute"
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x003d
            r5 = 0
            goto L_0x0070
        L_0x0065:
            java.lang.String r5 = "KeyTimeCycle"
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x003d
            r5 = 3
            goto L_0x0070
        L_0x006f:
            r5 = -1
        L_0x0070:
            switch(r5) {
                case 0: goto L_0x008e;
                case 1: goto L_0x0088;
                case 2: goto L_0x0082;
                case 3: goto L_0x007c;
                case 4: goto L_0x0076;
                default: goto L_0x0073;
            }     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
        L_0x0073:
            java.lang.NullPointerException r5 = new java.lang.NullPointerException     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            goto L_0x00a0
        L_0x0076:
            androidx.constraintlayout.motion.widget.KeyTrigger r5 = new androidx.constraintlayout.motion.widget.KeyTrigger     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r5.<init>()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            goto L_0x0094
        L_0x007c:
            androidx.constraintlayout.motion.widget.KeyTimeCycle r5 = new androidx.constraintlayout.motion.widget.KeyTimeCycle     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r5.<init>()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            goto L_0x0094
        L_0x0082:
            androidx.constraintlayout.motion.widget.KeyCycle r5 = new androidx.constraintlayout.motion.widget.KeyCycle     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r5.<init>()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            goto L_0x0094
        L_0x0088:
            androidx.constraintlayout.motion.widget.KeyPosition r5 = new androidx.constraintlayout.motion.widget.KeyPosition     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r5.<init>()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            goto L_0x0094
        L_0x008e:
            androidx.constraintlayout.motion.widget.KeyAttributes r5 = new androidx.constraintlayout.motion.widget.KeyAttributes     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r5.<init>()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
        L_0x0094:
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r10)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r5.load(r9, r3)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r8.addKey(r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r3 = r5
            goto L_0x00e6
        L_0x00a0:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r6.<init>()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            java.lang.String r7 = "Key "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            java.lang.String r7 = " not found"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            java.lang.String r6 = r6.toString()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r5.<init>(r6)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            throw r5     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
        L_0x00bd:
            java.lang.String r5 = "CustomAttribute"
            boolean r5 = r2.equalsIgnoreCase(r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x00d1
            if (r3 == 0) goto L_0x00e6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r5 = r3.mCustomConstraints     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x00e6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r5 = r3.mCustomConstraints     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            androidx.constraintlayout.widget.ConstraintAttribute.parse(r9, r10, r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            goto L_0x00e6
        L_0x00d1:
            java.lang.String r5 = "CustomMethod"
            boolean r5 = r2.equalsIgnoreCase(r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x00e6
            if (r3 == 0) goto L_0x00e6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r5 = r3.mCustomConstraints     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            if (r5 == 0) goto L_0x00e6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r5 = r3.mCustomConstraints     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            androidx.constraintlayout.widget.ConstraintAttribute.parse(r9, r10, r5)     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            goto L_0x00e6
        L_0x00e5:
        L_0x00e6:
            int r5 = r10.next()     // Catch:{ XmlPullParserException -> 0x00f3, IOException -> 0x00ee }
            r4 = r5
            goto L_0x0014
        L_0x00ed:
            goto L_0x00f7
        L_0x00ee:
            r3 = move-exception
            android.util.Log.e(r1, r0, r3)
            goto L_0x00f8
        L_0x00f3:
            r3 = move-exception
            android.util.Log.e(r1, r0, r3)
        L_0x00f7:
        L_0x00f8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyFrames.<init>(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public void addAllFrames(MotionController motionController) {
        ArrayList<Key> list = this.mFramesMap.get(-1);
        if (list != null) {
            motionController.addKeys(list);
        }
    }

    public void addFrames(MotionController motionController) {
        ArrayList<Key> list = this.mFramesMap.get(Integer.valueOf(motionController.mId));
        if (list != null) {
            motionController.addKeys(list);
        }
        ArrayList<Key> list2 = this.mFramesMap.get(-1);
        if (list2 != null) {
            Iterator<Key> it = list2.iterator();
            while (it.hasNext()) {
                Key key = it.next();
                if (key.matches(((ConstraintLayout.LayoutParams) motionController.mView.getLayoutParams()).constraintTag)) {
                    motionController.addKey(key);
                }
            }
        }
    }

    static String name(int viewId, Context context) {
        return context.getResources().getResourceEntryName(viewId);
    }

    public Set<Integer> getKeys() {
        return this.mFramesMap.keySet();
    }

    public ArrayList<Key> getKeyFramesForView(int id) {
        return this.mFramesMap.get(Integer.valueOf(id));
    }
}
