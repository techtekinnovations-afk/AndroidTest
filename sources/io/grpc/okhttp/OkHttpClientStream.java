package io.grpc.okhttp;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import io.grpc.Attributes;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.AbstractClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.Http2ClientStreamTransportState;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.WritableBuffer;
import io.grpc.okhttp.OutboundFlowController;
import io.grpc.okhttp.internal.framed.ErrorCode;
import io.grpc.okhttp.internal.framed.Header;
import io.perfmark.PerfMark;
import io.perfmark.Tag;
import io.perfmark.TaskCloseable;
import java.util.List;
import okio.Buffer;

class OkHttpClientStream extends AbstractClientStream {
    public static final int ABSENT_ID = -1;
    /* access modifiers changed from: private */
    public static final Buffer EMPTY_BUFFER = new Buffer();
    private final Attributes attributes;
    /* access modifiers changed from: private */
    public String authority;
    /* access modifiers changed from: private */
    public final MethodDescriptor<?, ?> method;
    private final Sink sink;
    /* access modifiers changed from: private */
    public final TransportState state;
    /* access modifiers changed from: private */
    public final StatsTraceContext statsTraceCtx;
    /* access modifiers changed from: private */
    public boolean useGet;
    /* access modifiers changed from: private */
    public final String userAgent;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    OkHttpClientStream(io.grpc.MethodDescriptor<?, ?> r13, io.grpc.Metadata r14, io.grpc.okhttp.ExceptionHandlingFrameWriter r15, io.grpc.okhttp.OkHttpClientTransport r16, io.grpc.okhttp.OutboundFlowController r17, java.lang.Object r18, int r19, int r20, java.lang.String r21, java.lang.String r22, io.grpc.internal.StatsTraceContext r23, io.grpc.internal.TransportTracer r24, io.grpc.CallOptions r25, boolean r26) {
        /*
            r12 = this;
            io.grpc.okhttp.OkHttpWritableBufferAllocator r1 = new io.grpc.okhttp.OkHttpWritableBufferAllocator
            r1.<init>()
            r7 = 0
            if (r26 == 0) goto L_0x0011
            boolean r0 = r13.isSafe()
            if (r0 == 0) goto L_0x0011
            r0 = 1
            r6 = r0
            goto L_0x0012
        L_0x0011:
            r6 = r7
        L_0x0012:
            r0 = r12
            r4 = r14
            r2 = r23
            r3 = r24
            r5 = r25
            r0.<init>(r1, r2, r3, r4, r5, r6)
            io.grpc.okhttp.OkHttpClientStream$Sink r0 = new io.grpc.okhttp.OkHttpClientStream$Sink
            r0.<init>()
            r12.sink = r0
            r12.useGet = r7
            java.lang.String r0 = "statsTraceCtx"
            java.lang.Object r0 = com.google.common.base.Preconditions.checkNotNull(r2, r0)
            io.grpc.internal.StatsTraceContext r0 = (io.grpc.internal.StatsTraceContext) r0
            r12.statsTraceCtx = r0
            r12.method = r13
            r10 = r21
            r12.authority = r10
            r11 = r22
            r12.userAgent = r11
            io.grpc.Attributes r0 = r16.getAttributes()
            r12.attributes = r0
            io.grpc.okhttp.OkHttpClientStream$TransportState r0 = new io.grpc.okhttp.OkHttpClientStream$TransportState
            java.lang.String r9 = r13.getFullMethodName()
            r1 = r12
            r5 = r15
            r7 = r16
            r6 = r17
            r4 = r18
            r8 = r20
            r3 = r2
            r2 = r19
            r0.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            r12.state = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpClientStream.<init>(io.grpc.MethodDescriptor, io.grpc.Metadata, io.grpc.okhttp.ExceptionHandlingFrameWriter, io.grpc.okhttp.OkHttpClientTransport, io.grpc.okhttp.OutboundFlowController, java.lang.Object, int, int, java.lang.String, java.lang.String, io.grpc.internal.StatsTraceContext, io.grpc.internal.TransportTracer, io.grpc.CallOptions, boolean):void");
    }

    /* access modifiers changed from: protected */
    public TransportState transportState() {
        return this.state;
    }

    /* access modifiers changed from: protected */
    public Sink abstractClientStreamSink() {
        return this.sink;
    }

    public MethodDescriptor.MethodType getType() {
        return this.method.getType();
    }

    /* access modifiers changed from: package-private */
    public boolean useGet() {
        return this.useGet;
    }

    public void setAuthority(String authority2) {
        this.authority = (String) Preconditions.checkNotNull(authority2, "authority");
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    class Sink implements AbstractClientStream.Sink {
        Sink() {
        }

        public void writeHeaders(Metadata metadata, byte[] payload) {
            TaskCloseable ignore = PerfMark.traceTask("OkHttpClientStream$Sink.writeHeaders");
            try {
                String defaultPath = "/" + OkHttpClientStream.this.method.getFullMethodName();
                if (payload != null) {
                    boolean unused = OkHttpClientStream.this.useGet = true;
                    defaultPath = defaultPath + "?" + BaseEncoding.base64().encode(payload);
                }
                synchronized (OkHttpClientStream.this.state.lock) {
                    OkHttpClientStream.this.state.streamReady(metadata, defaultPath);
                }
                if (ignore != null) {
                    ignore.close();
                }
            } catch (Throwable th) {
                if (ignore != null) {
                    try {
                        ignore.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void writeFrame(WritableBuffer frame, boolean endOfStream, boolean flush, int numMessages) {
            Buffer buffer;
            TaskCloseable ignore = PerfMark.traceTask("OkHttpClientStream$Sink.writeFrame");
            if (frame == null) {
                try {
                    buffer = OkHttpClientStream.EMPTY_BUFFER;
                } catch (Throwable th) {
                    if (ignore != null) {
                        try {
                            ignore.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } else {
                buffer = ((OkHttpWritableBuffer) frame).buffer();
                int size = (int) buffer.size();
                if (size > 0) {
                    OkHttpClientStream.this.onSendingBytes(size);
                }
            }
            synchronized (OkHttpClientStream.this.state.lock) {
                OkHttpClientStream.this.state.sendBuffer(buffer, endOfStream, flush);
                OkHttpClientStream.this.getTransportTracer().reportMessageSent(numMessages);
            }
            if (ignore != null) {
                ignore.close();
            }
        }

        public void cancel(Status reason) {
            TaskCloseable ignore = PerfMark.traceTask("OkHttpClientStream$Sink.cancel");
            try {
                synchronized (OkHttpClientStream.this.state.lock) {
                    OkHttpClientStream.this.state.cancel(reason, true, (Metadata) null);
                }
                if (ignore != null) {
                    ignore.close();
                }
            } catch (Throwable th) {
                if (ignore != null) {
                    try {
                        ignore.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    class TransportState extends Http2ClientStreamTransportState implements OutboundFlowController.Stream {
        private boolean canStart = true;
        private boolean cancelSent = false;
        private boolean flushPendingData = false;
        private final ExceptionHandlingFrameWriter frameWriter;
        private int id = -1;
        private final int initialWindowSize;
        /* access modifiers changed from: private */
        public final Object lock;
        private final OutboundFlowController outboundFlow;
        private OutboundFlowController.StreamState outboundFlowState;
        private Buffer pendingData = new Buffer();
        private boolean pendingDataHasEndOfStream = false;
        private int processedWindow;
        private List<Header> requestHeaders;
        private final Tag tag;
        private final OkHttpClientTransport transport;
        private int window;

        public TransportState(int maxMessageSize, StatsTraceContext statsTraceCtx, Object lock2, ExceptionHandlingFrameWriter frameWriter2, OutboundFlowController outboundFlow2, OkHttpClientTransport transport2, int initialWindowSize2, String methodName) {
            super(maxMessageSize, statsTraceCtx, OkHttpClientStream.this.getTransportTracer());
            this.lock = Preconditions.checkNotNull(lock2, "lock");
            this.frameWriter = frameWriter2;
            this.outboundFlow = outboundFlow2;
            this.transport = transport2;
            this.window = initialWindowSize2;
            this.processedWindow = initialWindowSize2;
            this.initialWindowSize = initialWindowSize2;
            this.tag = PerfMark.createTag(methodName);
        }

        public void start(int streamId) {
            Preconditions.checkState(this.id == -1, "the stream has been started with id %s", streamId);
            this.id = streamId;
            this.outboundFlowState = this.outboundFlow.createState(this, streamId);
            OkHttpClientStream.this.state.onStreamAllocated();
            if (this.canStart) {
                this.frameWriter.synStream(OkHttpClientStream.this.useGet, false, this.id, 0, this.requestHeaders);
                OkHttpClientStream.this.statsTraceCtx.clientOutboundHeaders();
                this.requestHeaders = null;
                if (this.pendingData.size() > 0) {
                    this.outboundFlow.data(this.pendingDataHasEndOfStream, this.outboundFlowState, this.pendingData, this.flushPendingData);
                }
                this.canStart = false;
            }
        }

        /* access modifiers changed from: protected */
        public void onStreamAllocated() {
            super.onStreamAllocated();
            getTransportTracer().reportLocalStreamStarted();
        }

        /* access modifiers changed from: protected */
        public void http2ProcessingFailed(Status status, boolean stopDelivery, Metadata trailers) {
            cancel(status, stopDelivery, trailers);
        }

        public void deframeFailed(Throwable cause) {
            http2ProcessingFailed(Status.fromThrowable(cause), true, new Metadata());
        }

        public void bytesRead(int processedBytes) {
            this.processedWindow -= processedBytes;
            if (((float) this.processedWindow) <= ((float) this.initialWindowSize) * 0.5f) {
                int delta = this.initialWindowSize - this.processedWindow;
                this.window += delta;
                this.processedWindow += delta;
                this.frameWriter.windowUpdate(id(), (long) delta);
            }
        }

        public void deframerClosed(boolean hasPartialMessage) {
            onEndOfStream();
            super.deframerClosed(hasPartialMessage);
        }

        public void runOnTransportThread(Runnable r) {
            synchronized (this.lock) {
                r.run();
            }
        }

        public void transportHeadersReceived(List<Header> headers, boolean endOfStream) {
            if (endOfStream) {
                transportTrailersReceived(Utils.convertTrailers(headers));
            } else {
                transportHeadersReceived(Utils.convertHeaders(headers));
            }
        }

        public void transportDataReceived(Buffer frame, boolean endOfStream, int paddingLen) {
            this.window -= ((int) frame.size()) + paddingLen;
            this.processedWindow -= paddingLen;
            if (this.window < 0) {
                this.frameWriter.rstStream(id(), ErrorCode.FLOW_CONTROL_ERROR);
                this.transport.finishStream(id(), Status.INTERNAL.withDescription("Received data size exceeded our receiving window size"), ClientStreamListener.RpcProgress.PROCESSED, false, (ErrorCode) null, (Metadata) null);
                return;
            }
            super.transportDataReceived(new OkHttpReadableBuffer(frame), endOfStream);
        }

        private void onEndOfStream() {
            if (!isOutboundClosed()) {
                this.transport.finishStream(id(), (Status) null, ClientStreamListener.RpcProgress.PROCESSED, false, ErrorCode.CANCEL, (Metadata) null);
            } else {
                this.transport.finishStream(id(), (Status) null, ClientStreamListener.RpcProgress.PROCESSED, false, (ErrorCode) null, (Metadata) null);
            }
        }

        /* access modifiers changed from: private */
        public void cancel(Status reason, boolean stopDelivery, Metadata trailers) {
            if (!this.cancelSent) {
                this.cancelSent = true;
                if (this.canStart) {
                    this.transport.removePendingStream(OkHttpClientStream.this);
                    this.requestHeaders = null;
                    this.pendingData.clear();
                    this.canStart = false;
                    transportReportStatus(reason, true, trailers != null ? trailers : new Metadata());
                    Status status = reason;
                    boolean z = stopDelivery;
                    Metadata metadata = trailers;
                    return;
                }
                this.transport.finishStream(id(), reason, ClientStreamListener.RpcProgress.PROCESSED, stopDelivery, ErrorCode.CANCEL, trailers);
            }
        }

        /* access modifiers changed from: private */
        public void sendBuffer(Buffer buffer, boolean endOfStream, boolean flush) {
            if (!this.cancelSent) {
                if (this.canStart) {
                    this.pendingData.write(buffer, (long) ((int) buffer.size()));
                    this.pendingDataHasEndOfStream |= endOfStream;
                    this.flushPendingData |= flush;
                    return;
                }
                Preconditions.checkState(id() != -1, "streamId should be set");
                this.outboundFlow.data(endOfStream, this.outboundFlowState, buffer, flush);
            }
        }

        /* access modifiers changed from: private */
        public void streamReady(Metadata metadata, String path) {
            this.requestHeaders = Headers.createRequestHeaders(metadata, path, OkHttpClientStream.this.authority, OkHttpClientStream.this.userAgent, OkHttpClientStream.this.useGet, this.transport.isUsingPlaintext());
            this.transport.streamReadyToStart(OkHttpClientStream.this);
        }

        /* access modifiers changed from: package-private */
        public Tag tag() {
            return this.tag;
        }

        /* access modifiers changed from: package-private */
        public int id() {
            return this.id;
        }

        /* access modifiers changed from: package-private */
        public OutboundFlowController.StreamState getOutboundFlowState() {
            OutboundFlowController.StreamState streamState;
            synchronized (this.lock) {
                streamState = this.outboundFlowState;
            }
            return streamState;
        }
    }
}
