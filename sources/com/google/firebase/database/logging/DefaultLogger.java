package com.google.firebase.database.logging;

import com.google.firebase.database.logging.Logger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultLogger implements Logger {
    private final Set<String> enabledComponents;
    private final Logger.Level minLevel;

    public DefaultLogger(Logger.Level level, List<String> enabledComponents2) {
        if (enabledComponents2 != null) {
            this.enabledComponents = new HashSet(enabledComponents2);
        } else {
            this.enabledComponents = null;
        }
        this.minLevel = level;
    }

    public Logger.Level getLogLevel() {
        return this.minLevel;
    }

    public void onLogMessage(Logger.Level level, String tag, String message, long msTimestamp) {
        if (shouldLog(level, tag)) {
            String toLog = buildLogMessage(level, tag, message, msTimestamp);
            long j = msTimestamp;
            String str = message;
            String tag2 = tag;
            Logger.Level level2 = level;
            switch (level2) {
                case ERROR:
                    error(tag2, toLog);
                    return;
                case WARN:
                    warn(tag2, toLog);
                    return;
                case INFO:
                    info(tag2, toLog);
                    return;
                case DEBUG:
                    debug(tag2, toLog);
                    return;
                default:
                    throw new RuntimeException("Should not reach here!");
            }
        } else {
            String str2 = message;
            String message2 = tag;
            Logger.Level level3 = level;
        }
    }

    /* access modifiers changed from: protected */
    public String buildLogMessage(Logger.Level level, String tag, String message, long msTimestamp) {
        return new Date(msTimestamp).toString() + " [" + level + "] " + tag + ": " + message;
    }

    /* access modifiers changed from: protected */
    public void error(String tag, String toLog) {
        System.err.println(toLog);
    }

    /* access modifiers changed from: protected */
    public void warn(String tag, String toLog) {
        System.out.println(toLog);
    }

    /* access modifiers changed from: protected */
    public void info(String tag, String toLog) {
        System.out.println(toLog);
    }

    /* access modifiers changed from: protected */
    public void debug(String tag, String toLog) {
        System.out.println(toLog);
    }

    /* access modifiers changed from: protected */
    public boolean shouldLog(Logger.Level level, String tag) {
        return level.ordinal() >= this.minLevel.ordinal() && (this.enabledComponents == null || level.ordinal() > Logger.Level.DEBUG.ordinal() || this.enabledComponents.contains(tag));
    }
}
