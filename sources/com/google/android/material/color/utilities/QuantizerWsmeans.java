package com.google.android.material.color.utilities;

public final class QuantizerWsmeans {
    private static final int MAX_ITERATIONS = 10;
    private static final double MIN_MOVEMENT_DISTANCE = 3.0d;

    private QuantizerWsmeans() {
    }

    private static final class Distance implements Comparable<Distance> {
        double distance = -1.0d;
        int index = -1;

        Distance() {
        }

        public int compareTo(Distance other) {
            return Double.valueOf(this.distance).compareTo(Double.valueOf(other.distance));
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v15, resolved type: boolean} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.Integer, java.lang.Integer> quantize(int[] r34, int[] r35, int r36) {
        /*
            r0 = r34
            r1 = r35
            java.util.Random r2 = new java.util.Random
            r3 = 272008(0x42688, double:1.3439E-318)
            r2.<init>(r3)
            java.util.LinkedHashMap r3 = new java.util.LinkedHashMap
            r3.<init>()
            int r4 = r0.length
            double[][] r4 = new double[r4][]
            int r5 = r0.length
            int[] r5 = new int[r5]
            com.google.android.material.color.utilities.PointProviderLab r6 = new com.google.android.material.color.utilities.PointProviderLab
            r6.<init>()
            r7 = 0
            r8 = 0
        L_0x001e:
            int r9 = r0.length
            r10 = 1
            if (r8 >= r9) goto L_0x0059
            r9 = r0[r8]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r9)
            java.lang.Object r11 = r3.get(r11)
            java.lang.Integer r11 = (java.lang.Integer) r11
            if (r11 != 0) goto L_0x0046
            double[] r12 = r6.fromInt(r9)
            r4[r7] = r12
            r5[r7] = r9
            int r7 = r7 + 1
            java.lang.Integer r12 = java.lang.Integer.valueOf(r9)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r3.put(r12, r10)
            goto L_0x0056
        L_0x0046:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r9)
            int r13 = r11.intValue()
            int r13 = r13 + r10
            java.lang.Integer r10 = java.lang.Integer.valueOf(r13)
            r3.put(r12, r10)
        L_0x0056:
            int r8 = r8 + 1
            goto L_0x001e
        L_0x0059:
            int[] r8 = new int[r7]
            r9 = 0
        L_0x005c:
            if (r9 >= r7) goto L_0x0073
            r11 = r5[r9]
            java.lang.Integer r12 = java.lang.Integer.valueOf(r11)
            java.lang.Object r12 = r3.get(r12)
            java.lang.Integer r12 = (java.lang.Integer) r12
            int r12 = r12.intValue()
            r8[r9] = r12
            int r9 = r9 + 1
            goto L_0x005c
        L_0x0073:
            r9 = r36
            int r11 = java.lang.Math.min(r9, r7)
            int r12 = r1.length
            if (r12 == 0) goto L_0x0081
            int r12 = r1.length
            int r11 = java.lang.Math.min(r11, r12)
        L_0x0081:
            double[][] r12 = new double[r11][]
            r13 = 0
            r14 = 0
        L_0x0085:
            int r15 = r1.length
            if (r14 >= r15) goto L_0x0095
            r15 = r1[r14]
            double[] r15 = r6.fromInt(r15)
            r12[r14] = r15
            int r13 = r13 + 1
            int r14 = r14 + 1
            goto L_0x0085
        L_0x0095:
            int r14 = r11 - r13
            if (r14 <= 0) goto L_0x009f
            r15 = 0
        L_0x009a:
            if (r15 >= r14) goto L_0x009f
            int r15 = r15 + 1
            goto L_0x009a
        L_0x009f:
            int[] r15 = new int[r7]
            r16 = 0
            r17 = r10
            r10 = r16
        L_0x00a7:
            if (r10 >= r7) goto L_0x00b2
            int r16 = r2.nextInt(r11)
            r15[r10] = r16
            int r10 = r10 + 1
            goto L_0x00a7
        L_0x00b2:
            int[][] r10 = new int[r11][]
            r16 = 0
            r0 = r16
        L_0x00b8:
            if (r0 >= r11) goto L_0x00c3
            r16 = r0
            int[] r0 = new int[r11]
            r10[r16] = r0
            int r0 = r16 + 1
            goto L_0x00b8
        L_0x00c3:
            r16 = r0
            com.google.android.material.color.utilities.QuantizerWsmeans$Distance[][] r0 = new com.google.android.material.color.utilities.QuantizerWsmeans.Distance[r11][]
            r16 = 0
            r18 = r0
            r0 = r16
        L_0x00cd:
            if (r0 >= r11) goto L_0x00e7
            r16 = r0
            com.google.android.material.color.utilities.QuantizerWsmeans$Distance[] r0 = new com.google.android.material.color.utilities.QuantizerWsmeans.Distance[r11]
            r18[r16] = r0
            r0 = 0
        L_0x00d6:
            if (r0 >= r11) goto L_0x00e4
            r19 = r18[r16]
            com.google.android.material.color.utilities.QuantizerWsmeans$Distance r20 = new com.google.android.material.color.utilities.QuantizerWsmeans$Distance
            r20.<init>()
            r19[r0] = r20
            int r0 = r0 + 1
            goto L_0x00d6
        L_0x00e4:
            int r0 = r16 + 1
            goto L_0x00cd
        L_0x00e7:
            r16 = r0
            int[] r0 = new int[r11]
            r16 = 0
            r1 = r16
        L_0x00ef:
            r16 = r2
            r2 = 10
            if (r1 >= r2) goto L_0x027e
            r2 = 0
        L_0x00f6:
            if (r2 >= r11) goto L_0x0155
            int r19 = r2 + 1
            r20 = r1
            r1 = r19
        L_0x00fe:
            if (r1 >= r11) goto L_0x012f
            r19 = r3
            r3 = r12[r2]
            r21 = r4
            r4 = r12[r1]
            double r3 = r6.distance(r3, r4)
            r22 = r18[r1]
            r23 = r5
            r5 = r22[r2]
            r5.distance = r3
            r5 = r18[r1]
            r5 = r5[r2]
            r5.index = r2
            r5 = r18[r2]
            r5 = r5[r1]
            r5.distance = r3
            r5 = r18[r2]
            r5 = r5[r1]
            r5.index = r1
            int r1 = r1 + 1
            r3 = r19
            r4 = r21
            r5 = r23
            goto L_0x00fe
        L_0x012f:
            r19 = r3
            r21 = r4
            r23 = r5
            r1 = r18[r2]
            java.util.Arrays.sort(r1)
            r1 = 0
        L_0x013b:
            if (r1 >= r11) goto L_0x014a
            r3 = r10[r2]
            r4 = r18[r2]
            r4 = r4[r1]
            int r4 = r4.index
            r3[r1] = r4
            int r1 = r1 + 1
            goto L_0x013b
        L_0x014a:
            int r2 = r2 + 1
            r3 = r19
            r1 = r20
            r4 = r21
            r5 = r23
            goto L_0x00f6
        L_0x0155:
            r20 = r1
            r19 = r3
            r21 = r4
            r23 = r5
            r1 = 0
            r2 = 0
        L_0x015f:
            if (r2 >= r7) goto L_0x01ce
            r3 = r21[r2]
            r4 = r15[r2]
            r5 = r12[r4]
            double r24 = r6.distance(r3, r5)
            r26 = r24
            r22 = -1
            r28 = 0
            r29 = r1
            r1 = r22
            r22 = r2
            r2 = r28
        L_0x0179:
            if (r2 >= r11) goto L_0x01a5
            r28 = r18[r4]
            r30 = r2
            r2 = r28[r30]
            r28 = r4
            r31 = r5
            double r4 = r2.distance
            r32 = 4616189618054758400(0x4010000000000000, double:4.0)
            double r32 = r32 * r24
            int r2 = (r4 > r32 ? 1 : (r4 == r32 ? 0 : -1))
            if (r2 < 0) goto L_0x0190
            goto L_0x019e
        L_0x0190:
            r2 = r12[r30]
            double r4 = r6.distance(r3, r2)
            int r2 = (r4 > r26 ? 1 : (r4 == r26 ? 0 : -1))
            if (r2 >= 0) goto L_0x019e
            r26 = r4
            r1 = r30
        L_0x019e:
            int r2 = r30 + 1
            r4 = r28
            r5 = r31
            goto L_0x0179
        L_0x01a5:
            r30 = r2
            r28 = r4
            r31 = r5
            r2 = -1
            if (r1 == r2) goto L_0x01c9
            double r4 = java.lang.Math.sqrt(r26)
            double r32 = java.lang.Math.sqrt(r24)
            double r4 = r4 - r32
            double r4 = java.lang.Math.abs(r4)
            r32 = 4613937818241073152(0x4008000000000000, double:3.0)
            int r2 = (r4 > r32 ? 1 : (r4 == r32 ? 0 : -1))
            if (r2 <= 0) goto L_0x01c9
            int r2 = r29 + 1
            r15[r22] = r1
            r1 = r2
            goto L_0x01cb
        L_0x01c9:
            r1 = r29
        L_0x01cb:
            int r2 = r22 + 1
            goto L_0x015f
        L_0x01ce:
            r29 = r1
            r22 = r2
            if (r29 != 0) goto L_0x01da
            if (r20 == 0) goto L_0x01da
            r32 = r0
            goto L_0x0288
        L_0x01da:
            double[] r1 = new double[r11]
            double[] r2 = new double[r11]
            double[] r3 = new double[r11]
            r4 = 0
            java.util.Arrays.fill(r0, r4)
            r5 = 0
        L_0x01e5:
            r22 = 2
            if (r5 >= r7) goto L_0x0229
            r24 = r15[r5]
            r25 = r21[r5]
            r26 = r4
            r4 = r8[r5]
            r27 = r0[r24]
            int r27 = r27 + r4
            r0[r24] = r27
            r27 = r1[r24]
            r30 = r25[r26]
            r32 = r0
            r33 = r1
            double r0 = (double) r4
            double r30 = r30 * r0
            double r27 = r27 + r30
            r33[r24] = r27
            r0 = r2[r24]
            r27 = r25[r17]
            r30 = r0
            double r0 = (double) r4
            double r27 = r27 * r0
            double r0 = r30 + r27
            r2[r24] = r0
            r0 = r3[r24]
            r27 = r25[r22]
            r30 = r0
            double r0 = (double) r4
            double r27 = r27 * r0
            double r0 = r30 + r27
            r3[r24] = r0
            int r5 = r5 + 1
            r4 = r26
            r0 = r32
            r1 = r33
            goto L_0x01e5
        L_0x0229:
            r32 = r0
            r33 = r1
            r26 = r4
            r0 = 0
        L_0x0230:
            if (r0 >= r11) goto L_0x026c
            r1 = r32[r0]
            if (r1 != 0) goto L_0x0243
            r4 = 3
            double[] r4 = new double[r4]
            r4 = {0, 0, 0} // fill-array
            r12[r0] = r4
            r24 = r2
            r25 = r3
            goto L_0x0265
        L_0x0243:
            r4 = r33[r0]
            r24 = r2
            r25 = r3
            double r2 = (double) r1
            double r4 = r4 / r2
            r2 = r24[r0]
            r27 = r2
            double r2 = (double) r1
            double r2 = r27 / r2
            r27 = r25[r0]
            r30 = r2
            double r2 = (double) r1
            double r27 = r27 / r2
            r2 = r12[r0]
            r2[r26] = r4
            r2 = r12[r0]
            r2[r17] = r30
            r2 = r12[r0]
            r2[r22] = r27
        L_0x0265:
            int r0 = r0 + 1
            r2 = r24
            r3 = r25
            goto L_0x0230
        L_0x026c:
            r24 = r2
            r25 = r3
            int r1 = r20 + 1
            r2 = r16
            r3 = r19
            r4 = r21
            r5 = r23
            r0 = r32
            goto L_0x00ef
        L_0x027e:
            r32 = r0
            r20 = r1
            r19 = r3
            r21 = r4
            r23 = r5
        L_0x0288:
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            r1 = 0
        L_0x028e:
            if (r1 >= r11) goto L_0x02b4
            r2 = r32[r1]
            if (r2 != 0) goto L_0x0295
            goto L_0x02b1
        L_0x0295:
            r3 = r12[r1]
            int r3 = r6.toInt(r3)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            boolean r4 = r0.containsKey(r4)
            if (r4 == 0) goto L_0x02a6
            goto L_0x02b1
        L_0x02a6:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
            r0.put(r4, r5)
        L_0x02b1:
            int r1 = r1 + 1
            goto L_0x028e
        L_0x02b4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.color.utilities.QuantizerWsmeans.quantize(int[], int[], int):java.util.Map");
    }
}
