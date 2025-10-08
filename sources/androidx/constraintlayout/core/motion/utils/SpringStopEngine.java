package androidx.constraintlayout.core.motion.utils;

public class SpringStopEngine implements StopEngine {
    private static final double UNSET = Double.MAX_VALUE;
    private int mBoundaryMode = 0;
    double mDamping = 0.5d;
    private boolean mInitialized = false;
    private float mLastTime;
    private double mLastVelocity;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;
    private float mV;

    public String debug(String desc, float time) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public void log(String str) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        System.out.println((".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName() + "() ") + str);
    }

    public void springConfig(float currentPos, float target, float currentVelocity, float mass, float stiffness, float damping, float stopThreshold, int boundaryMode) {
        this.mTargetPos = (double) target;
        this.mDamping = (double) damping;
        this.mInitialized = false;
        this.mPos = currentPos;
        this.mLastVelocity = (double) currentVelocity;
        this.mStiffness = (double) stiffness;
        this.mMass = mass;
        this.mStopThreshold = stopThreshold;
        this.mBoundaryMode = boundaryMode;
        this.mLastTime = 0.0f;
    }

    public float getVelocity(float time) {
        return this.mV;
    }

    public float getInterpolation(float time) {
        compute((double) (time - this.mLastTime));
        this.mLastTime = time;
        if (isStopped()) {
            this.mPos = (float) this.mTargetPos;
        }
        return this.mPos;
    }

    public float getAcceleration() {
        double k = this.mStiffness;
        double c = this.mDamping;
        return ((float) (((-k) * (((double) this.mPos) - this.mTargetPos)) - (((double) this.mV) * c))) / this.mMass;
    }

    public float getVelocity() {
        return 0.0f;
    }

    public boolean isStopped() {
        double x = ((double) this.mPos) - this.mTargetPos;
        double k = this.mStiffness;
        double v = (double) this.mV;
        return Math.sqrt((((v * v) * ((double) this.mMass)) + ((k * x) * x)) / k) <= ((double) this.mStopThreshold);
    }

    private void compute(double dt) {
        if (dt > 0.0d) {
            double k = this.mStiffness;
            double c = this.mDamping;
            int overSample = (int) ((9.0d / ((Math.sqrt(this.mStiffness / ((double) this.mMass)) * dt) * 4.0d)) + 1.0d);
            double dt2 = dt / ((double) overSample);
            int i = 0;
            while (i < overSample) {
                double avgV = ((double) this.mV) + ((((((-k) * (((double) this.mPos) - this.mTargetPos)) - (((double) this.mV) * c)) / ((double) this.mMass)) * dt2) / 2.0d);
                double k2 = k;
                double avgX = (((double) this.mPos) + ((dt2 * avgV) / 2.0d)) - this.mTargetPos;
                double c2 = c;
                double d = avgX;
                double dv = ((((-avgX) * k2) - (avgV * c2)) / ((double) this.mMass)) * dt2;
                double avgV2 = ((double) this.mV) + (dv / 2.0d);
                this.mV += (float) dv;
                double d2 = dv;
                this.mPos += (float) (avgV2 * dt2);
                if (this.mBoundaryMode > 0) {
                    if (this.mPos < 0.0f && (this.mBoundaryMode & 1) == 1) {
                        this.mPos = -this.mPos;
                        this.mV = -this.mV;
                    }
                    if (this.mPos > 1.0f && (this.mBoundaryMode & 2) == 2) {
                        this.mPos = 2.0f - this.mPos;
                        this.mV = -this.mV;
                    }
                }
                i++;
                c = c2;
                k = k2;
            }
        }
    }
}
