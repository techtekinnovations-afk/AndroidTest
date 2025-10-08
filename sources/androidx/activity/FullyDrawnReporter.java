package androidx.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\u0006J\u0014\u0010\u0017\u001a\u00020\u00062\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u0014\u0010\u0019\u001a\u00020\u00062\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\b\u0010\u001a\u001a\u00020\u0006H\u0007J\b\u0010\u001b\u001a\u00020\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\u000b8\u0002@\u0002X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\r8\u0002@\u0002X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u00020\r8\u0002@\u0002X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00128\u0002X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Landroidx/activity/FullyDrawnReporter;", "", "executor", "Ljava/util/concurrent/Executor;", "reportFullyDrawn", "Lkotlin/Function0;", "", "<init>", "(Ljava/util/concurrent/Executor;Lkotlin/jvm/functions/Function0;)V", "lock", "reporterCount", "", "reportPosted", "", "reportedFullyDrawn", "isFullyDrawnReported", "()Z", "onReportCallbacks", "", "reportRunnable", "Ljava/lang/Runnable;", "addReporter", "removeReporter", "addOnReportDrawnListener", "callback", "removeOnReportDrawnListener", "fullyDrawnReported", "postWhenReportersAreDone", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: FullyDrawnReporter.kt */
public final class FullyDrawnReporter {
    private final Executor executor;
    private final Object lock = new Object();
    private final List<Function0<Unit>> onReportCallbacks = new ArrayList();
    private final Function0<Unit> reportFullyDrawn;
    private boolean reportPosted;
    private final Runnable reportRunnable = new FullyDrawnReporter$$ExternalSyntheticLambda0(this);
    private boolean reportedFullyDrawn;
    private int reporterCount;

    public FullyDrawnReporter(Executor executor2, Function0<Unit> reportFullyDrawn2) {
        Intrinsics.checkNotNullParameter(executor2, "executor");
        Intrinsics.checkNotNullParameter(reportFullyDrawn2, "reportFullyDrawn");
        this.executor = executor2;
        this.reportFullyDrawn = reportFullyDrawn2;
    }

    public final boolean isFullyDrawnReported() {
        boolean z;
        synchronized (this.lock) {
            z = this.reportedFullyDrawn;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public static final void reportRunnable$lambda$2(FullyDrawnReporter this$0) {
        synchronized (this$0.lock) {
            this$0.reportPosted = false;
            if (this$0.reporterCount == 0 && !this$0.reportedFullyDrawn) {
                this$0.reportFullyDrawn.invoke();
                this$0.fullyDrawnReported();
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void addReporter() {
        synchronized (this.lock) {
            if (!this.reportedFullyDrawn) {
                this.reporterCount++;
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void removeReporter() {
        synchronized (this.lock) {
            if (!this.reportedFullyDrawn && this.reporterCount > 0) {
                this.reporterCount--;
                postWhenReportersAreDone();
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void addOnReportDrawnListener(Function0<Unit> callback) {
        boolean callImmediately;
        Intrinsics.checkNotNullParameter(callback, "callback");
        synchronized (this.lock) {
            if (this.reportedFullyDrawn) {
                callImmediately = true;
            } else {
                this.onReportCallbacks.add(callback);
                callImmediately = false;
            }
        }
        if (callImmediately) {
            callback.invoke();
        }
    }

    public final void removeOnReportDrawnListener(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        synchronized (this.lock) {
            this.onReportCallbacks.remove(callback);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void fullyDrawnReported() {
        synchronized (this.lock) {
            this.reportedFullyDrawn = true;
            for (Function0 it : this.onReportCallbacks) {
                it.invoke();
            }
            this.onReportCallbacks.clear();
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void postWhenReportersAreDone() {
        if (!this.reportPosted && this.reporterCount == 0) {
            this.reportPosted = true;
            this.executor.execute(this.reportRunnable);
        }
    }
}
