package com.google.firebase;

final class AutoValue_StartupTime extends StartupTime {
    private final long elapsedRealtime;
    private final long epochMillis;
    private final long uptimeMillis;

    AutoValue_StartupTime(long epochMillis2, long elapsedRealtime2, long uptimeMillis2) {
        this.epochMillis = epochMillis2;
        this.elapsedRealtime = elapsedRealtime2;
        this.uptimeMillis = uptimeMillis2;
    }

    public long getEpochMillis() {
        return this.epochMillis;
    }

    public long getElapsedRealtime() {
        return this.elapsedRealtime;
    }

    public long getUptimeMillis() {
        return this.uptimeMillis;
    }

    public String toString() {
        return "StartupTime{epochMillis=" + this.epochMillis + ", elapsedRealtime=" + this.elapsedRealtime + ", uptimeMillis=" + this.uptimeMillis + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StartupTime)) {
            return false;
        }
        StartupTime that = (StartupTime) o;
        if (this.epochMillis == that.getEpochMillis() && this.elapsedRealtime == that.getElapsedRealtime() && this.uptimeMillis == that.getUptimeMillis()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((1 * 1000003) ^ ((int) ((this.epochMillis >>> 32) ^ this.epochMillis))) * 1000003) ^ ((int) ((this.elapsedRealtime >>> 32) ^ this.elapsedRealtime))) * 1000003) ^ ((int) ((this.uptimeMillis >>> 32) ^ this.uptimeMillis));
    }
}
