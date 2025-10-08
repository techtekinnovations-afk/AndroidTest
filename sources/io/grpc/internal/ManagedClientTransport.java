package io.grpc.internal;

import io.grpc.Attributes;
import io.grpc.Status;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public interface ManagedClientTransport extends ClientTransport {
    void shutdown(Status status);

    void shutdownNow(Status status);

    @CheckReturnValue
    @Nullable
    Runnable start(Listener listener);

    public interface Listener {
        void transportInUse(boolean z);

        void transportReady();

        void transportShutdown(Status status);

        void transportTerminated();

        Attributes filterTransport(Attributes attributes) {
            return attributes;
        }
    }
}
