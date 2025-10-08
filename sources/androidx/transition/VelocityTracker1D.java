package androidx.transition;

import java.util.Arrays;

class VelocityTracker1D {
    private static final int ASSUME_POINTER_MOVE_STOPPED_MILLIS = 40;
    private static final int HISTORY_SIZE = 20;
    private static final int HORIZON_MILLIS = 100;
    private float[] mDataSamples = new float[20];
    private int mIndex = 0;
    private long[] mTimeSamples = new long[20];

    VelocityTracker1D() {
        Arrays.fill(this.mTimeSamples, Long.MIN_VALUE);
    }

    public void addDataPoint(long timeMillis, float data) {
        this.mIndex = (this.mIndex + 1) % 20;
        this.mTimeSamples[this.mIndex] = timeMillis;
        this.mDataSamples[this.mIndex] = data;
    }

    public void resetTracking() {
        this.mIndex = 0;
        Arrays.fill(this.mTimeSamples, Long.MIN_VALUE);
        Arrays.fill(this.mDataSamples, 0.0f);
    }

    /* access modifiers changed from: package-private */
    public float calculateVelocity() {
        char c;
        int sampleCount;
        int sampleCount2 = 0;
        int index = this.mIndex;
        float f = 0.0f;
        if (index == 0 && this.mTimeSamples[index] == Long.MIN_VALUE) {
            return 0.0f;
        }
        long newestTime = this.mTimeSamples[index];
        long previousTime = newestTime;
        do {
            long sampleTime = this.mTimeSamples[index];
            c = 20;
            if (sampleTime != Long.MIN_VALUE) {
                float age = (float) (newestTime - sampleTime);
                float delta = (float) Math.abs(sampleTime - previousTime);
                previousTime = sampleTime;
                if (age > 100.0f || delta > 40.0f) {
                    break;
                }
                index = (index == 0 ? 20 : index) - 1;
                sampleCount2++;
            } else {
                break;
            }
        } while (sampleCount2 < 20);
        if (sampleCount2 < 2) {
            return 0.0f;
        }
        float f2 = 1000.0f;
        if (sampleCount2 == 2) {
            int prevIndex = this.mIndex == 0 ? 19 : this.mIndex - 1;
            float timeDiff = (float) (this.mTimeSamples[this.mIndex] - this.mTimeSamples[prevIndex]);
            if (timeDiff == 0.0f) {
                return 0.0f;
            }
            return ((this.mDataSamples[this.mIndex] - this.mDataSamples[prevIndex]) / timeDiff) * 1000.0f;
        }
        float work = 0.0f;
        int startIndex = (((this.mIndex - sampleCount2) + 20) + 1) % 20;
        int endIndex = ((this.mIndex + 1) + 20) % 20;
        long previousTime2 = this.mTimeSamples[startIndex];
        float previousData = this.mDataSamples[startIndex];
        int i = (startIndex + 1) % 20;
        while (i != endIndex) {
            long time = this.mTimeSamples[i];
            float f3 = f2;
            long timeDelta = time - previousTime2;
            char c2 = c;
            if (((float) timeDelta) == f) {
                sampleCount = sampleCount2;
            } else {
                float data = this.mDataSamples[i];
                float vCurr = (data - previousData) / ((float) timeDelta);
                work += (vCurr - kineticEnergyToVelocity(work)) * Math.abs(vCurr);
                sampleCount = sampleCount2;
                if (i == startIndex + 1) {
                    work *= 0.5f;
                }
                previousTime2 = time;
                previousData = data;
            }
            i = (i + 1) % 20;
            f2 = f3;
            c = c2;
            sampleCount2 = sampleCount;
            f = 0.0f;
        }
        return kineticEnergyToVelocity(work) * f2;
    }

    private float kineticEnergyToVelocity(float kineticEnergy) {
        return (float) (((double) Math.signum(kineticEnergy)) * Math.sqrt((double) (Math.abs(kineticEnergy) * 2.0f)));
    }
}
