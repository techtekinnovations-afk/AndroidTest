package androidx.datastore.core;

import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0000\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Landroidx/datastore/core/SharedCounter;", "", "mappedAddress", "", "(J)V", "getValue", "", "incrementAndGetValue", "Factory", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: SharedCounter.android.kt */
public final class SharedCounter {
    public static final Factory Factory = new Factory((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final NativeSharedCounter nativeSharedCounter = new NativeSharedCounter();
    private final long mappedAddress;

    public /* synthetic */ SharedCounter(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    private SharedCounter(long mappedAddress2) {
        this.mappedAddress = mappedAddress2;
    }

    public final int getValue() {
        return nativeSharedCounter.nativeGetCounterValue(this.mappedAddress);
    }

    public final int incrementAndGetValue() {
        return nativeSharedCounter.nativeIncrementAndGetCounterValue(this.mappedAddress);
    }

    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0000¢\u0006\u0002\b\fJ\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Landroidx/datastore/core/SharedCounter$Factory;", "", "()V", "nativeSharedCounter", "Landroidx/datastore/core/NativeSharedCounter;", "getNativeSharedCounter$datastore_core_release", "()Landroidx/datastore/core/NativeSharedCounter;", "create", "Landroidx/datastore/core/SharedCounter;", "produceFile", "Lkotlin/Function0;", "Ljava/io/File;", "create$datastore_core_release", "createCounterFromFd", "pfd", "Landroid/os/ParcelFileDescriptor;", "loadLib", "", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: SharedCounter.android.kt */
    public static final class Factory {
        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Factory() {
        }

        public final NativeSharedCounter getNativeSharedCounter$datastore_core_release() {
            return SharedCounter.nativeSharedCounter;
        }

        public final void loadLib() {
            System.loadLibrary("datastore_shared_counter");
        }

        private final SharedCounter createCounterFromFd(ParcelFileDescriptor pfd) {
            int nativeFd = pfd.getFd();
            if (getNativeSharedCounter$datastore_core_release().nativeTruncateFile(nativeFd) == 0) {
                long address = getNativeSharedCounter$datastore_core_release().nativeCreateSharedCounter(nativeFd);
                if (address >= 0) {
                    return new SharedCounter(address, (DefaultConstructorMarker) null);
                }
                throw new IOException("Failed to mmap counter file");
            }
            throw new IOException("Failed to truncate counter file");
        }

        public final SharedCounter create$datastore_core_release(Function0<? extends File> produceFile) {
            Intrinsics.checkNotNullParameter(produceFile, "produceFile");
            ParcelFileDescriptor pfd = null;
            try {
                pfd = ParcelFileDescriptor.open((File) produceFile.invoke(), 939524096);
                return createCounterFromFd(pfd);
            } finally {
                if (pfd != null) {
                    pfd.close();
                }
            }
        }
    }
}
