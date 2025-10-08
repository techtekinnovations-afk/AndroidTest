package com.google.firebase.logger;

import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u000f\b&\u0018\u0000 (2\u00020\u0001:\u0004%&'(B!\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ9\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00032\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0018\"\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007¢\u0006\u0002\u0010\u001bJ\u001c\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007J9\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00032\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0018\"\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007¢\u0006\u0002\u0010\u001bJ\u001c\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007J9\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00032\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0018\"\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007¢\u0006\u0002\u0010\u001bJ\u001c\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007J9\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00032\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0018\"\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007¢\u0006\u0002\u0010\u001bJ\u001c\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007J9\u0010 \u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00032\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0018\"\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007¢\u0006\u0002\u0010\u001bJ\u001c\u0010 \u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0007J;\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00032\u0012\b\u0002\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002¢\u0006\u0002\u0010#J9\u0010$\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00032\u0010\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH&¢\u0006\u0002\u0010#R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0007X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006)"}, d2 = {"Lcom/google/firebase/logger/Logger;", "", "tag", "", "enabled", "", "minLevel", "Lcom/google/firebase/logger/Logger$Level;", "<init>", "(Ljava/lang/String;ZLcom/google/firebase/logger/Logger$Level;)V", "getTag", "()Ljava/lang/String;", "getEnabled", "()Z", "setEnabled", "(Z)V", "getMinLevel", "()Lcom/google/firebase/logger/Logger$Level;", "setMinLevel", "(Lcom/google/firebase/logger/Logger$Level;)V", "verbose", "", "format", "args", "", "throwable", "", "(Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)I", "msg", "debug", "info", "warn", "error", "logIfAble", "level", "(Lcom/google/firebase/logger/Logger$Level;Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)I", "log", "AndroidLogger", "FakeLogger", "Level", "Companion", "com.google.firebase-firebase-common"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Logger.kt */
public abstract class Logger {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final ConcurrentHashMap<String, Logger> loggers = new ConcurrentHashMap<>();
    private boolean enabled;
    private Level minLevel;
    private final String tag;

    public /* synthetic */ Logger(String str, boolean z, Level level, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, level);
    }

    public abstract int log(Level level, String str, Object[] objArr, Throwable th);

    private Logger(String tag2, boolean enabled2, Level minLevel2) {
        this.tag = tag2;
        this.enabled = enabled2;
        this.minLevel = minLevel2;
    }

    public final String getTag() {
        return this.tag;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final Level getMinLevel() {
        return this.minLevel;
    }

    public final void setMinLevel(Level level) {
        Intrinsics.checkNotNullParameter(level, "<set-?>");
        this.minLevel = level;
    }

    public static /* synthetic */ int verbose$default(Logger logger, String str, Object[] objArr, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                th = null;
            }
            return logger.verbose(str, objArr, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: verbose");
    }

    public final int verbose(String format, Object... args) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return verbose$default(this, format, args, (Throwable) null, 4, (Object) null);
    }

    public final int verbose(String format, Object[] args, Throwable throwable) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return logIfAble(Level.VERBOSE, format, args, throwable);
    }

    public static /* synthetic */ int verbose$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                th = null;
            }
            return logger.verbose(str, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: verbose");
    }

    public final int verbose(String msg) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return verbose$default(this, msg, (Throwable) null, 2, (Object) null);
    }

    public final int verbose(String msg, Throwable throwable) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return logIfAble$default(this, Level.VERBOSE, msg, (Object[]) null, throwable, 4, (Object) null);
    }

    public static /* synthetic */ int debug$default(Logger logger, String str, Object[] objArr, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                th = null;
            }
            return logger.debug(str, objArr, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: debug");
    }

    public final int debug(String format, Object... args) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return debug$default(this, format, args, (Throwable) null, 4, (Object) null);
    }

    public final int debug(String format, Object[] args, Throwable throwable) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return logIfAble(Level.DEBUG, format, args, throwable);
    }

    public static /* synthetic */ int debug$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                th = null;
            }
            return logger.debug(str, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: debug");
    }

    public final int debug(String msg) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return debug$default(this, msg, (Throwable) null, 2, (Object) null);
    }

    public final int debug(String msg, Throwable throwable) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return logIfAble$default(this, Level.DEBUG, msg, (Object[]) null, throwable, 4, (Object) null);
    }

    public static /* synthetic */ int info$default(Logger logger, String str, Object[] objArr, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                th = null;
            }
            return logger.info(str, objArr, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: info");
    }

    public final int info(String format, Object... args) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return info$default(this, format, args, (Throwable) null, 4, (Object) null);
    }

    public final int info(String format, Object[] args, Throwable throwable) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return logIfAble(Level.INFO, format, args, throwable);
    }

    public static /* synthetic */ int info$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                th = null;
            }
            return logger.info(str, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: info");
    }

    public final int info(String msg) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return info$default(this, msg, (Throwable) null, 2, (Object) null);
    }

    public final int info(String msg, Throwable throwable) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return logIfAble$default(this, Level.INFO, msg, (Object[]) null, throwable, 4, (Object) null);
    }

    public static /* synthetic */ int warn$default(Logger logger, String str, Object[] objArr, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                th = null;
            }
            return logger.warn(str, objArr, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: warn");
    }

    public final int warn(String format, Object... args) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return warn$default(this, format, args, (Throwable) null, 4, (Object) null);
    }

    public final int warn(String format, Object[] args, Throwable throwable) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return logIfAble(Level.WARN, format, args, throwable);
    }

    public static /* synthetic */ int warn$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                th = null;
            }
            return logger.warn(str, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: warn");
    }

    public final int warn(String msg) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return warn$default(this, msg, (Throwable) null, 2, (Object) null);
    }

    public final int warn(String msg, Throwable throwable) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return logIfAble$default(this, Level.WARN, msg, (Object[]) null, throwable, 4, (Object) null);
    }

    public static /* synthetic */ int error$default(Logger logger, String str, Object[] objArr, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                th = null;
            }
            return logger.error(str, objArr, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: error");
    }

    public final int error(String format, Object... args) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return error$default(this, format, args, (Throwable) null, 4, (Object) null);
    }

    public final int error(String format, Object[] args, Throwable throwable) {
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(args, "args");
        return logIfAble(Level.ERROR, format, args, throwable);
    }

    public static /* synthetic */ int error$default(Logger logger, String str, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                th = null;
            }
            return logger.error(str, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: error");
    }

    public final int error(String msg) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return error$default(this, msg, (Throwable) null, 2, (Object) null);
    }

    public final int error(String msg, Throwable throwable) {
        Intrinsics.checkNotNullParameter(msg, NotificationCompat.CATEGORY_MESSAGE);
        return logIfAble$default(this, Level.ERROR, msg, (Object[]) null, throwable, 4, (Object) null);
    }

    static /* synthetic */ int logIfAble$default(Logger logger, Level level, String str, Object[] objArr, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                objArr = new Object[0];
            }
            return logger.logIfAble(level, str, objArr, th);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logIfAble");
    }

    private final int logIfAble(Level level, String format, Object[] args, Throwable throwable) {
        if (!this.enabled || (this.minLevel.getPriority$com_google_firebase_firebase_common() > level.getPriority$com_google_firebase_firebase_common() && !Log.isLoggable(this.tag, level.getPriority$com_google_firebase_firebase_common()))) {
            return 0;
        }
        return log(level, format, args, throwable);
    }

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ9\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00032\u0010\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00100\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"Lcom/google/firebase/logger/Logger$AndroidLogger;", "Lcom/google/firebase/logger/Logger;", "tag", "", "enabled", "", "minLevel", "Lcom/google/firebase/logger/Logger$Level;", "<init>", "(Ljava/lang/String;ZLcom/google/firebase/logger/Logger$Level;)V", "log", "", "level", "format", "args", "", "", "throwable", "", "(Lcom/google/firebase/logger/Logger$Level;Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)I", "com.google.firebase-firebase-common"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Logger.kt */
    private static final class AndroidLogger extends Logger {

        @Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
        /* compiled from: Logger.kt */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Level.values().length];
                try {
                    iArr[Level.VERBOSE.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[Level.DEBUG.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[Level.INFO.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[Level.WARN.ordinal()] = 4;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[Level.ERROR.ordinal()] = 5;
                } catch (NoSuchFieldError e5) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public AndroidLogger(String tag, boolean enabled, Level minLevel) {
            super(tag, enabled, minLevel, (DefaultConstructorMarker) null);
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(minLevel, "minLevel");
        }

        public int log(Level level, String format, Object[] args, Throwable throwable) {
            String msg;
            Intrinsics.checkNotNullParameter(level, "level");
            Intrinsics.checkNotNullParameter(format, "format");
            Intrinsics.checkNotNullParameter(args, "args");
            if (args.length == 0) {
                msg = format;
            } else {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                Object[] copyOf = Arrays.copyOf(args, args.length);
                msg = String.format(format, Arrays.copyOf(copyOf, copyOf.length));
                Intrinsics.checkNotNullExpressionValue(msg, "format(...)");
            }
            switch (WhenMappings.$EnumSwitchMapping$0[level.ordinal()]) {
                case 1:
                    if (throwable == null) {
                        return Log.v(getTag(), msg);
                    }
                    Throwable th = throwable;
                    return Log.v(getTag(), msg, throwable);
                case 2:
                    if (throwable == null) {
                        return Log.d(getTag(), msg);
                    }
                    Throwable th2 = throwable;
                    return Log.d(getTag(), msg, throwable);
                case 3:
                    if (throwable == null) {
                        return Log.i(getTag(), msg);
                    }
                    Throwable th3 = throwable;
                    return Log.i(getTag(), msg, throwable);
                case 4:
                    if (throwable == null) {
                        return Log.w(getTag(), msg);
                    }
                    Throwable th4 = throwable;
                    return Log.w(getTag(), msg, throwable);
                case 5:
                    if (throwable == null) {
                        return Log.e(getTag(), msg);
                    }
                    Throwable th5 = throwable;
                    return Log.e(getTag(), msg, throwable);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
    }

    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B!\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ9\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00032\u0010\u0010\u0010\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00120\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0003H\u0007J\u001c\u0010\u001a\u001a\u00020\u00052\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\u001cH\u0007J9\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00032\u0010\u0010\u0010\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00120\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002¢\u0006\u0002\u0010\u001eR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bX\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/google/firebase/logger/Logger$FakeLogger;", "Lcom/google/firebase/logger/Logger;", "tag", "", "enabled", "", "minLevel", "Lcom/google/firebase/logger/Logger$Level;", "<init>", "(Ljava/lang/String;ZLcom/google/firebase/logger/Logger$Level;)V", "record", "", "log", "", "level", "format", "args", "", "", "throwable", "", "(Lcom/google/firebase/logger/Logger$Level;Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)I", "clearLogMessages", "", "hasLogMessage", "message", "hasLogMessageThat", "predicate", "Lkotlin/Function1;", "toLogMessage", "(Lcom/google/firebase/logger/Logger$Level;Ljava/lang/String;[Ljava/lang/Object;Ljava/lang/Throwable;)Ljava/lang/String;", "com.google.firebase-firebase-common"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Logger.kt */
    public static final class FakeLogger extends Logger {
        private final List<String> record = new ArrayList();

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public FakeLogger(String tag, boolean enabled, Level minLevel) {
            super(tag, enabled, minLevel, (DefaultConstructorMarker) null);
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(minLevel, "minLevel");
        }

        public int log(Level level, String format, Object[] args, Throwable throwable) {
            Intrinsics.checkNotNullParameter(level, "level");
            Intrinsics.checkNotNullParameter(format, "format");
            Intrinsics.checkNotNullParameter(args, "args");
            String logMessage = toLogMessage(level, format, args, throwable);
            System.out.println("Log: " + logMessage);
            this.record.add(logMessage);
            return logMessage.length();
        }

        public final void clearLogMessages() {
            this.record.clear();
        }

        public final boolean hasLogMessage(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            Iterable<String> $this$any$iv = this.record;
            if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                return false;
            }
            for (String it : $this$any$iv) {
                if (StringsKt.contains$default((CharSequence) it, (CharSequence) message, false, 2, (Object) null)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean hasLogMessageThat(Function1<? super String, Boolean> predicate) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Iterable<Object> $this$any$iv = this.record;
            if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                return false;
            }
            for (Object element$iv : $this$any$iv) {
                if (predicate.invoke(element$iv).booleanValue()) {
                    return true;
                }
            }
            return false;
        }

        private final String toLogMessage(Level level, String format, Object[] args, Throwable throwable) {
            String msg;
            if (args.length == 0) {
                msg = format;
            } else {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                Object[] copyOf = Arrays.copyOf(args, args.length);
                msg = String.format(format, Arrays.copyOf(copyOf, copyOf.length));
                Intrinsics.checkNotNullExpressionValue(msg, "format(...)");
            }
            if (throwable != null) {
                Throwable th = throwable;
                String str = level + ' ' + msg + ' ' + Log.getStackTraceString(throwable);
                if (str != null) {
                    return str;
                }
            }
            return level + ' ' + msg;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lcom/google/firebase/logger/Logger$Level;", "", "priority", "", "<init>", "(Ljava/lang/String;II)V", "getPriority$com_google_firebase_firebase_common", "()I", "VERBOSE", "DEBUG", "INFO", "WARN", "ERROR", "com.google.firebase-firebase-common"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Logger.kt */
    public enum Level {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6);
        
        private final int priority;

        private Level(int priority2) {
            this.priority = priority2;
        }

        public final int getPriority$com_google_firebase_firebase_common() {
            return this.priority;
        }

        static {
            $ENTRIES = EnumEntriesKt.enumEntries((E[]) (Enum[]) $VALUES);
        }

        public static EnumEntries<Level> getEntries() {
            return $ENTRIES;
        }
    }

    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007J$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/google/firebase/logger/Logger$Companion;", "", "<init>", "()V", "loggers", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/google/firebase/logger/Logger;", "getLogger", "tag", "enabled", "", "minLevel", "Lcom/google/firebase/logger/Logger$Level;", "setupFakeLogger", "Lcom/google/firebase/logger/Logger$FakeLogger;", "com.google.firebase-firebase-common"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Logger.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ Logger getLogger$default(Companion companion, String str, boolean z, Level level, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            if ((i & 4) != 0) {
                level = Level.INFO;
            }
            return companion.getLogger(str, z, level);
        }

        @JvmStatic
        public final Logger getLogger(String tag, boolean enabled, Level minLevel) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(minLevel, "minLevel");
            ConcurrentMap $this$getOrPut$iv = Logger.loggers;
            Object obj = $this$getOrPut$iv.get(tag);
            if (obj == null) {
                Object default$iv = (Logger) new AndroidLogger(tag, enabled, minLevel);
                Object putIfAbsent = $this$getOrPut$iv.putIfAbsent(tag, default$iv);
                obj = putIfAbsent == null ? default$iv : putIfAbsent;
            }
            Intrinsics.checkNotNullExpressionValue(obj, "getOrPut(...)");
            return (Logger) obj;
        }

        public static /* synthetic */ FakeLogger setupFakeLogger$default(Companion companion, String str, boolean z, Level level, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            if ((i & 4) != 0) {
                level = Level.DEBUG;
            }
            return companion.setupFakeLogger(str, z, level);
        }

        @JvmStatic
        public final FakeLogger setupFakeLogger(String tag, boolean enabled, Level minLevel) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            Intrinsics.checkNotNullParameter(minLevel, "minLevel");
            FakeLogger fakeLogger = new FakeLogger(tag, enabled, minLevel);
            Logger.loggers.put(tag, fakeLogger);
            return fakeLogger;
        }
    }

    @JvmStatic
    public static final Logger getLogger(String tag2, boolean enabled2, Level minLevel2) {
        return Companion.getLogger(tag2, enabled2, minLevel2);
    }

    @JvmStatic
    public static final FakeLogger setupFakeLogger(String tag2, boolean enabled2, Level minLevel2) {
        return Companion.setupFakeLogger(tag2, enabled2, minLevel2);
    }
}
