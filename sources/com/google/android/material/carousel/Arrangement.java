package com.google.android.material.carousel;

import androidx.core.math.MathUtils;

public final class Arrangement {
    private static final float MEDIUM_ITEM_FLEX_PERCENTAGE = 0.1f;
    final float cost;
    final int largeCount;
    float largeSize;
    int mediumCount;
    float mediumSize;
    final int priority;
    int smallCount;
    float smallSize;

    public Arrangement(int priority2, float targetSmallSize, float minSmallSize, float maxSmallSize, int smallCount2, float targetMediumSize, int mediumCount2, float targetLargeSize, int largeCount2, float availableSpace) {
        this.priority = priority2;
        this.smallSize = MathUtils.clamp(targetSmallSize, minSmallSize, maxSmallSize);
        this.smallCount = smallCount2;
        this.mediumSize = targetMediumSize;
        this.mediumCount = mediumCount2;
        this.largeSize = targetLargeSize;
        this.largeCount = largeCount2;
        fit(availableSpace, minSmallSize, maxSmallSize, targetLargeSize);
        this.cost = cost(targetLargeSize);
    }

    public String toString() {
        return "Arrangement [priority=" + this.priority + ", smallCount=" + this.smallCount + ", smallSize=" + this.smallSize + ", mediumCount=" + this.mediumCount + ", mediumSize=" + this.mediumSize + ", largeCount=" + this.largeCount + ", largeSize=" + this.largeSize + ", cost=" + this.cost + "]";
    }

    private float getSpace() {
        return (this.largeSize * ((float) this.largeCount)) + (this.mediumSize * ((float) this.mediumCount)) + (this.smallSize * ((float) this.smallCount));
    }

    private void fit(float availableSpace, float minSmallSize, float maxSmallSize, float targetLargeSize) {
        float delta = availableSpace - getSpace();
        if (this.smallCount > 0 && delta > 0.0f) {
            this.smallSize += Math.min(delta / ((float) this.smallCount), maxSmallSize - this.smallSize);
        } else if (this.smallCount > 0 && delta < 0.0f) {
            this.smallSize += Math.max(delta / ((float) this.smallCount), minSmallSize - this.smallSize);
        }
        this.smallSize = this.smallCount > 0 ? this.smallSize : 0.0f;
        this.largeSize = calculateLargeSize(availableSpace, this.smallCount, this.smallSize, this.mediumCount, this.largeCount);
        this.mediumSize = (this.largeSize + this.smallSize) / 2.0f;
        if (this.mediumCount > 0 && this.largeSize != targetLargeSize) {
            float targetAdjustment = (targetLargeSize - this.largeSize) * ((float) this.largeCount);
            float distribute = Math.min(Math.abs(targetAdjustment), this.mediumSize * 0.1f * ((float) this.mediumCount));
            if (targetAdjustment > 0.0f) {
                this.mediumSize -= distribute / ((float) this.mediumCount);
                this.largeSize += distribute / ((float) this.largeCount);
                return;
            }
            this.mediumSize += distribute / ((float) this.mediumCount);
            this.largeSize -= distribute / ((float) this.largeCount);
        }
    }

    private float calculateLargeSize(float availableSpace, int smallCount2, float smallSize2, int mediumCount2, int largeCount2) {
        return (availableSpace - ((((float) smallCount2) + (((float) mediumCount2) / 2.0f)) * (smallCount2 > 0 ? smallSize2 : 0.0f))) / (((float) largeCount2) + (((float) mediumCount2) / 2.0f));
    }

    private boolean isValid() {
        if (this.largeCount <= 0 || this.smallCount <= 0 || this.mediumCount <= 0) {
            if (this.largeCount <= 0 || this.smallCount <= 0) {
                return true;
            }
            if (this.largeSize > this.smallSize) {
                return true;
            }
            return false;
        } else if (this.largeSize <= this.mediumSize || this.mediumSize <= this.smallSize) {
            return false;
        } else {
            return true;
        }
    }

    private float cost(float targetLargeSize) {
        if (!isValid()) {
            return Float.MAX_VALUE;
        }
        return Math.abs(targetLargeSize - this.largeSize) * ((float) this.priority);
    }

    public static Arrangement findLowestCostArrangement(float availableSpace, float targetSmallSize, float minSmallSize, float maxSmallSize, int[] smallCounts, float targetMediumSize, int[] mediumCounts, float targetLargeSize, int[] largeCounts) {
        int[] iArr = smallCounts;
        int[] iArr2 = mediumCounts;
        Arrangement lowestCostArrangement = null;
        int priority2 = 1;
        for (int largeCount2 : largeCounts) {
            int priority3 = iArr2.length;
            int priority4 = 0;
            while (priority4 < priority3) {
                int mediumCount2 = iArr2[priority4];
                int length = iArr.length;
                int i = 0;
                while (i < length) {
                    int i2 = priority4;
                    int priority5 = priority2;
                    int priority6 = i2;
                    int i3 = length;
                    int i4 = i;
                    int i5 = priority3;
                    Arrangement arrangement = new Arrangement(priority5, targetSmallSize, minSmallSize, maxSmallSize, iArr[i], targetMediumSize, mediumCount2, targetLargeSize, largeCount2, availableSpace);
                    if (lowestCostArrangement == null || arrangement.cost < lowestCostArrangement.cost) {
                        lowestCostArrangement = arrangement;
                        if (lowestCostArrangement.cost == 0.0f) {
                            return lowestCostArrangement;
                        }
                    }
                    int priority7 = priority5 + 1;
                    i = i4 + 1;
                    priority4 = priority6;
                    priority2 = priority7;
                    priority3 = i5;
                    length = i3;
                }
                int i6 = priority4;
                int priority8 = priority2;
                int priority9 = i6;
                int i7 = priority3;
                int i8 = priority8;
                priority4 = priority9 + 1;
                priority2 = i8;
                priority3 = i7;
            }
        }
        return lowestCostArrangement;
    }

    /* access modifiers changed from: package-private */
    public int getItemCount() {
        return this.smallCount + this.mediumCount + this.largeCount;
    }
}
