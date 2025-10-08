package io.grpc.okhttp;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.Attributes;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.InternalStatus;
import io.grpc.Metadata;
import io.grpc.ServerStreamTracer;
import io.grpc.Status;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.KeepAliveEnforcer;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.MaxConnectionIdleManager;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.SerializingExecutor;
import io.grpc.internal.ServerTransport;
import io.grpc.internal.ServerTransportListener;
import io.grpc.internal.TransportTracer;
import io.grpc.okhttp.ExceptionHandlingFrameWriter;
import io.grpc.okhttp.OkHttpFrameLogger;
import io.grpc.okhttp.OutboundFlowController;
import io.grpc.okhttp.internal.framed.ErrorCode;
import io.grpc.okhttp.internal.framed.FrameReader;
import io.grpc.okhttp.internal.framed.Header;
import io.grpc.okhttp.internal.framed.Http2;
import io.grpc.okhttp.internal.framed.Settings;
import io.grpc.okhttp.internal.framed.Variant;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.ByteString;

final class OkHttpServerTransport implements ServerTransport, ExceptionHandlingFrameWriter.TransportExceptionHandler, OutboundFlowController.Transport {
    /* access modifiers changed from: private */
    public static final ByteString AUTHORITY = ByteString.encodeUtf8(":authority");
    /* access modifiers changed from: private */
    public static final ByteString CONNECTION = ByteString.encodeUtf8("connection");
    /* access modifiers changed from: private */
    public static final ByteString CONNECT_METHOD = ByteString.encodeUtf8("CONNECT");
    /* access modifiers changed from: private */
    public static final ByteString CONTENT_LENGTH = ByteString.encodeUtf8("content-length");
    /* access modifiers changed from: private */
    public static final ByteString CONTENT_TYPE = ByteString.encodeUtf8("content-type");
    private static final int GRACEFUL_SHUTDOWN_PING = 4369;
    private static final long GRACEFUL_SHUTDOWN_PING_TIMEOUT_NANOS = TimeUnit.SECONDS.toNanos(1);
    /* access modifiers changed from: private */
    public static final ByteString HOST = ByteString.encodeUtf8("host");
    /* access modifiers changed from: private */
    public static final ByteString HTTP_METHOD = ByteString.encodeUtf8(":method");
    private static final int KEEPALIVE_PING = 57005;
    /* access modifiers changed from: private */
    public static final ByteString PATH = ByteString.encodeUtf8(":path");
    /* access modifiers changed from: private */
    public static final ByteString POST_METHOD = ByteString.encodeUtf8(GrpcUtil.HTTP_METHOD);
    /* access modifiers changed from: private */
    public static final ByteString SCHEME = ByteString.encodeUtf8(":scheme");
    /* access modifiers changed from: private */
    public static final ByteString TE = ByteString.encodeUtf8("te");
    /* access modifiers changed from: private */
    public static final ByteString TE_TRAILERS = ByteString.encodeUtf8(GrpcUtil.TE_TRAILERS);
    /* access modifiers changed from: private */
    public static final Logger log = Logger.getLogger(OkHttpServerTransport.class.getName());
    private boolean abruptShutdown;
    /* access modifiers changed from: private */
    public Attributes attributes;
    /* access modifiers changed from: private */
    public final Config config;
    private ScheduledFuture<?> forcefulCloseTimer;
    /* access modifiers changed from: private */
    public ExceptionHandlingFrameWriter frameWriter;
    /* access modifiers changed from: private */
    public Status goAwayStatus;
    /* access modifiers changed from: private */
    public int goAwayStreamId = Integer.MAX_VALUE;
    private boolean gracefulShutdown;
    private Long gracefulShutdownPeriod = null;
    private boolean handshakeShutdown;
    /* access modifiers changed from: private */
    public final KeepAliveEnforcer keepAliveEnforcer;
    /* access modifiers changed from: private */
    public KeepAliveManager keepAliveManager;
    /* access modifiers changed from: private */
    public int lastStreamId;
    /* access modifiers changed from: private */
    public ServerTransportListener listener;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    private final InternalLogId logId;
    private ScheduledFuture<?> maxConnectionAgeMonitor;
    /* access modifiers changed from: private */
    public MaxConnectionIdleManager maxConnectionIdleManager;
    /* access modifiers changed from: private */
    public OutboundFlowController outboundFlow;
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture<?> secondGoawayTimer;
    private InternalChannelz.Security securityInfo;
    /* access modifiers changed from: private */
    public Socket socket;
    /* access modifiers changed from: private */
    public final Map<Integer, StreamState> streams = new TreeMap();
    /* access modifiers changed from: private */
    public final TransportTracer tracer;
    private Executor transportExecutor;
    private final Variant variant = new Http2();

    interface StreamState {
        OutboundFlowController.StreamState getOutboundFlowState();

        boolean hasReceivedEndOfStream();

        void inboundDataReceived(Buffer buffer, int i, int i2, boolean z);

        void inboundRstReceived(Status status);

        int inboundWindowAvailable();

        void transportReportStatus(Status status);
    }

    public OkHttpServerTransport(Config config2, Socket bareSocket) {
        this.config = (Config) Preconditions.checkNotNull(config2, "config");
        this.socket = (Socket) Preconditions.checkNotNull(bareSocket, "bareSocket");
        this.tracer = config2.transportTracerFactory.create();
        this.tracer.setFlowControlWindowReader(new OkHttpServerTransport$$ExternalSyntheticLambda4(this));
        this.logId = InternalLogId.allocate(getClass(), this.socket.getRemoteSocketAddress().toString());
        this.transportExecutor = config2.transportExecutorPool.getObject();
        this.scheduledExecutorService = config2.scheduledExecutorServicePool.getObject();
        this.keepAliveEnforcer = new KeepAliveEnforcer(config2.permitKeepAliveWithoutCalls, config2.permitKeepAliveTimeInNanos, TimeUnit.NANOSECONDS);
    }

    public void start(ServerTransportListener listener2) {
        this.listener = (ServerTransportListener) Preconditions.checkNotNull(listener2, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        SerializingExecutor serializingExecutor = new SerializingExecutor(this.transportExecutor);
        serializingExecutor.execute(new OkHttpServerTransport$$ExternalSyntheticLambda5(this, serializingExecutor));
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0149, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0156, code lost:
        r0 = th;
     */
    /* renamed from: startIo */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m1940lambda$start$0$iogrpcokhttpOkHttpServerTransport(io.grpc.internal.SerializingExecutor r24) {
        /*
            r23 = this;
            r1 = r23
            java.lang.Object r2 = r1.lock     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            monitor-enter(r2)     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            java.net.Socket r0 = r1.socket     // Catch:{ all -> 0x014b }
            r3 = 1
            r0.setTcpNoDelay(r3)     // Catch:{ all -> 0x014b }
            monitor-exit(r2)     // Catch:{ all -> 0x014b }
            io.grpc.okhttp.OkHttpServerTransport$Config r0 = r1.config     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            io.grpc.okhttp.HandshakerSocketFactory r0 = r0.handshakerSocketFactory     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            java.net.Socket r2 = r1.socket     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            io.grpc.Attributes r3 = io.grpc.Attributes.EMPTY     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            io.grpc.okhttp.HandshakerSocketFactory$HandshakeResult r0 = r0.handshake(r2, r3)     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            r2 = r0
            java.lang.Object r3 = r1.lock     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            monitor-enter(r3)     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            java.net.Socket r0 = r2.socket     // Catch:{ all -> 0x0144 }
            r1.socket = r0     // Catch:{ all -> 0x0144 }
            monitor-exit(r3)     // Catch:{ all -> 0x0144 }
            io.grpc.Attributes r0 = r2.attributes     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            r1.attributes = r0     // Catch:{ Error -> 0x015c, IOException -> 0x015a, RuntimeException -> 0x0158 }
            r3 = 10000(0x2710, float:1.4013E-41)
            r4 = r24
            io.grpc.okhttp.AsyncSink r0 = io.grpc.okhttp.AsyncSink.sink(r4, r1, r3)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r5 = r0
            java.net.Socket r0 = r1.socket     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            okio.Sink r0 = okio.Okio.sink((java.net.Socket) r0)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            java.net.Socket r6 = r1.socket     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r5.becomeConnected(r0, r6)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.internal.framed.Variant r0 = r1.variant     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            okio.BufferedSink r6 = okio.Okio.buffer((okio.Sink) r5)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r7 = 0
            io.grpc.okhttp.internal.framed.FrameWriter r0 = r0.newWriter(r6, r7)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.internal.framed.FrameWriter r0 = r5.limitControlFramesWriter(r0)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r6 = r0
            io.grpc.okhttp.OkHttpServerTransport$1 r0 = new io.grpc.okhttp.OkHttpServerTransport$1     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r0.<init>(r6)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r8 = r0
            java.lang.Object r9 = r1.lock     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            monitor-enter(r9)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.InternalChannelz$Security r0 = r2.securityInfo     // Catch:{ all -> 0x0141 }
            r1.securityInfo = r0     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r0 = new io.grpc.okhttp.ExceptionHandlingFrameWriter     // Catch:{ all -> 0x0141 }
            r0.<init>(r1, r8)     // Catch:{ all -> 0x0141 }
            r1.frameWriter = r0     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.OutboundFlowController r0 = new io.grpc.okhttp.OutboundFlowController     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r10 = r1.frameWriter     // Catch:{ all -> 0x0141 }
            r0.<init>(r1, r10)     // Catch:{ all -> 0x0141 }
            r1.outboundFlow = r0     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r0 = r1.frameWriter     // Catch:{ all -> 0x0141 }
            r0.connectionPreface()     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.internal.framed.Settings r0 = new io.grpc.okhttp.internal.framed.Settings     // Catch:{ all -> 0x0141 }
            r0.<init>()     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.OkHttpServerTransport$Config r10 = r1.config     // Catch:{ all -> 0x0141 }
            int r10 = r10.flowControlWindow     // Catch:{ all -> 0x0141 }
            r11 = 7
            io.grpc.okhttp.OkHttpSettingsUtil.set(r0, r11, r10)     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.OkHttpServerTransport$Config r10 = r1.config     // Catch:{ all -> 0x0141 }
            int r10 = r10.maxInboundMetadataSize     // Catch:{ all -> 0x0141 }
            r11 = 6
            io.grpc.okhttp.OkHttpSettingsUtil.set(r0, r11, r10)     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r10 = r1.frameWriter     // Catch:{ all -> 0x0141 }
            r10.settings(r0)     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.OkHttpServerTransport$Config r10 = r1.config     // Catch:{ all -> 0x0141 }
            int r10 = r10.flowControlWindow     // Catch:{ all -> 0x0141 }
            r11 = 65535(0xffff, float:9.1834E-41)
            if (r10 <= r11) goto L_0x0099
            io.grpc.okhttp.ExceptionHandlingFrameWriter r10 = r1.frameWriter     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.OkHttpServerTransport$Config r12 = r1.config     // Catch:{ all -> 0x0141 }
            int r12 = r12.flowControlWindow     // Catch:{ all -> 0x0141 }
            int r12 = r12 - r11
            long r11 = (long) r12     // Catch:{ all -> 0x0141 }
            r10.windowUpdate(r7, r11)     // Catch:{ all -> 0x0141 }
        L_0x0099:
            io.grpc.okhttp.ExceptionHandlingFrameWriter r10 = r1.frameWriter     // Catch:{ all -> 0x0141 }
            r10.flush()     // Catch:{ all -> 0x0141 }
            monitor-exit(r9)     // Catch:{ all -> 0x0141 }
            io.grpc.okhttp.OkHttpServerTransport$Config r0 = r1.config     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            long r9 = r0.keepAliveTimeNanos     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r11 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x00d1
            io.grpc.internal.KeepAliveManager r13 = new io.grpc.internal.KeepAliveManager     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.OkHttpServerTransport$KeepAlivePinger r14 = new io.grpc.okhttp.OkHttpServerTransport$KeepAlivePinger     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r0 = 0
            r14.<init>()     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            java.util.concurrent.ScheduledExecutorService r15 = r1.scheduledExecutorService     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.OkHttpServerTransport$Config r0 = r1.config     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            long r9 = r0.keepAliveTimeNanos     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.OkHttpServerTransport$Config r0 = r1.config     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r21 = r11
            long r11 = r0.keepAliveTimeoutNanos     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r20 = 1
            r16 = r9
            r18 = r11
            r13.<init>(r14, r15, r16, r18, r20)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r1.keepAliveManager = r13     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.internal.KeepAliveManager r0 = r1.keepAliveManager     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r0.onTransportStarted()     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            goto L_0x00d3
        L_0x00d1:
            r21 = r11
        L_0x00d3:
            io.grpc.okhttp.OkHttpServerTransport$Config r0 = r1.config     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            long r9 = r0.maxConnectionIdleNanos     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            int r0 = (r9 > r21 ? 1 : (r9 == r21 ? 0 : -1))
            if (r0 == 0) goto L_0x00f2
            io.grpc.internal.MaxConnectionIdleManager r0 = new io.grpc.internal.MaxConnectionIdleManager     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.OkHttpServerTransport$Config r9 = r1.config     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            long r9 = r9.maxConnectionIdleNanos     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r0.<init>(r9)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r1.maxConnectionIdleManager = r0     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.internal.MaxConnectionIdleManager r0 = r1.maxConnectionIdleManager     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda1 r9 = new io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda1     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r9.<init>(r1)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            java.util.concurrent.ScheduledExecutorService r10 = r1.scheduledExecutorService     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r0.start(r9, r10)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
        L_0x00f2:
            io.grpc.okhttp.OkHttpServerTransport$Config r0 = r1.config     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            long r9 = r0.maxConnectionAgeInNanos     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            int r0 = (r9 > r21 ? 1 : (r9 == r21 ? 0 : -1))
            if (r0 == 0) goto L_0x0126
            double r9 = java.lang.Math.random()     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r11 = 4596373779694328218(0x3fc999999999999a, double:0.2)
            double r9 = r9 * r11
            r11 = 4606281698874543309(0x3feccccccccccccd, double:0.9)
            double r9 = r9 + r11
            io.grpc.okhttp.OkHttpServerTransport$Config r0 = r1.config     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            long r11 = r0.maxConnectionAgeInNanos     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            double r11 = (double) r11     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            double r9 = r9 * r11
            long r9 = (long) r9     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            java.util.concurrent.ScheduledExecutorService r0 = r1.scheduledExecutorService     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.internal.LogExceptionRunnable r11 = new io.grpc.internal.LogExceptionRunnable     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda2 r12 = new io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda2     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r12.<init>(r1)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r11.<init>(r12)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            java.util.concurrent.TimeUnit r12 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            java.util.concurrent.ScheduledFuture r0 = r0.schedule(r11, r9, r12)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r1.maxConnectionAgeMonitor = r0     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
        L_0x0126:
            java.util.concurrent.Executor r0 = r1.transportExecutor     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.OkHttpServerTransport$FrameHandler r9 = new io.grpc.okhttp.OkHttpServerTransport$FrameHandler     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.internal.framed.Variant r10 = r1.variant     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            java.net.Socket r11 = r1.socket     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            okio.Source r11 = okio.Okio.source((java.net.Socket) r11)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            okio.BufferedSource r11 = okio.Okio.buffer((okio.Source) r11)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            io.grpc.okhttp.internal.framed.FrameReader r7 = r10.newReader(r11, r7)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r9.<init>(r7)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            r0.execute(r9)     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
            goto L_0x0179
        L_0x0141:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0141 }
            throw r0     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
        L_0x0144:
            r0 = move-exception
            r4 = r24
        L_0x0147:
            monitor-exit(r3)     // Catch:{ all -> 0x0149 }
            throw r0     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
        L_0x0149:
            r0 = move-exception
            goto L_0x0147
        L_0x014b:
            r0 = move-exception
            r4 = r24
        L_0x014e:
            monitor-exit(r2)     // Catch:{ all -> 0x0156 }
            throw r0     // Catch:{ Error -> 0x0154, IOException -> 0x0152, RuntimeException -> 0x0150 }
        L_0x0150:
            r0 = move-exception
            goto L_0x015f
        L_0x0152:
            r0 = move-exception
            goto L_0x015f
        L_0x0154:
            r0 = move-exception
            goto L_0x015f
        L_0x0156:
            r0 = move-exception
            goto L_0x014e
        L_0x0158:
            r0 = move-exception
            goto L_0x015d
        L_0x015a:
            r0 = move-exception
            goto L_0x015d
        L_0x015c:
            r0 = move-exception
        L_0x015d:
            r4 = r24
        L_0x015f:
            r2 = r0
            java.lang.Object r3 = r1.lock
            monitor-enter(r3)
            boolean r0 = r1.handshakeShutdown     // Catch:{ all -> 0x017a }
            if (r0 != 0) goto L_0x0170
            java.util.logging.Logger r0 = log     // Catch:{ all -> 0x017a }
            java.util.logging.Level r5 = java.util.logging.Level.INFO     // Catch:{ all -> 0x017a }
            java.lang.String r6 = "Socket failed to handshake"
            r0.log(r5, r6, r2)     // Catch:{ all -> 0x017a }
        L_0x0170:
            monitor-exit(r3)     // Catch:{ all -> 0x017a }
            java.net.Socket r0 = r1.socket
            io.grpc.internal.GrpcUtil.closeQuietly((java.io.Closeable) r0)
            r1.terminated()
        L_0x0179:
            return
        L_0x017a:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x017a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpServerTransport.m1940lambda$start$0$iogrpcokhttpOkHttpServerTransport(io.grpc.internal.SerializingExecutor):void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startIo$1$io-grpc-okhttp-OkHttpServerTransport  reason: not valid java name */
    public /* synthetic */ void m1941lambda$startIo$1$iogrpcokhttpOkHttpServerTransport() {
        shutdown(Long.valueOf(this.config.maxConnectionAgeGraceInNanos));
    }

    public void shutdown() {
        shutdown((Long) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0048, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void shutdown(@javax.annotation.Nullable java.lang.Long r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.lock
            monitor-enter(r0)
            boolean r1 = r6.gracefulShutdown     // Catch:{ all -> 0x004b }
            if (r1 != 0) goto L_0x0049
            boolean r1 = r6.abruptShutdown     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x000c
            goto L_0x0049
        L_0x000c:
            r1 = 1
            r6.gracefulShutdown = r1     // Catch:{ all -> 0x004b }
            r6.gracefulShutdownPeriod = r7     // Catch:{ all -> 0x004b }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r2 = r6.frameWriter     // Catch:{ all -> 0x004b }
            if (r2 != 0) goto L_0x001d
            r6.handshakeShutdown = r1     // Catch:{ all -> 0x004b }
            java.net.Socket r1 = r6.socket     // Catch:{ all -> 0x004b }
            io.grpc.internal.GrpcUtil.closeQuietly((java.io.Closeable) r1)     // Catch:{ all -> 0x004b }
            goto L_0x0047
        L_0x001d:
            java.util.concurrent.ScheduledExecutorService r1 = r6.scheduledExecutorService     // Catch:{ all -> 0x004b }
            io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda3 r2 = new io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda3     // Catch:{ all -> 0x004b }
            r2.<init>(r6)     // Catch:{ all -> 0x004b }
            long r3 = GRACEFUL_SHUTDOWN_PING_TIMEOUT_NANOS     // Catch:{ all -> 0x004b }
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ all -> 0x004b }
            java.util.concurrent.ScheduledFuture r1 = r1.schedule(r2, r3, r5)     // Catch:{ all -> 0x004b }
            r6.secondGoawayTimer = r1     // Catch:{ all -> 0x004b }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r1 = r6.frameWriter     // Catch:{ all -> 0x004b }
            io.grpc.okhttp.internal.framed.ErrorCode r2 = io.grpc.okhttp.internal.framed.ErrorCode.NO_ERROR     // Catch:{ all -> 0x004b }
            r3 = 0
            byte[] r4 = new byte[r3]     // Catch:{ all -> 0x004b }
            r5 = 2147483647(0x7fffffff, float:NaN)
            r1.goAway(r5, r2, r4)     // Catch:{ all -> 0x004b }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r1 = r6.frameWriter     // Catch:{ all -> 0x004b }
            r2 = 4369(0x1111, float:6.122E-42)
            r1.ping(r3, r3, r2)     // Catch:{ all -> 0x004b }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r1 = r6.frameWriter     // Catch:{ all -> 0x004b }
            r1.flush()     // Catch:{ all -> 0x004b }
        L_0x0047:
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            return
        L_0x0049:
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            return
        L_0x004b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpServerTransport.shutdown(java.lang.Long):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void triggerGracefulSecondGoaway() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.lock
            monitor-enter(r0)
            java.util.concurrent.ScheduledFuture<?> r1 = r6.secondGoawayTimer     // Catch:{ all -> 0x004f }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            return
        L_0x0009:
            java.util.concurrent.ScheduledFuture<?> r1 = r6.secondGoawayTimer     // Catch:{ all -> 0x004f }
            r2 = 0
            r1.cancel(r2)     // Catch:{ all -> 0x004f }
            r1 = 0
            r6.secondGoawayTimer = r1     // Catch:{ all -> 0x004f }
            io.grpc.okhttp.ExceptionHandlingFrameWriter r1 = r6.frameWriter     // Catch:{ all -> 0x004f }
            int r3 = r6.lastStreamId     // Catch:{ all -> 0x004f }
            io.grpc.okhttp.internal.framed.ErrorCode r4 = io.grpc.okhttp.internal.framed.ErrorCode.NO_ERROR     // Catch:{ all -> 0x004f }
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x004f }
            r1.goAway(r3, r4, r2)     // Catch:{ all -> 0x004f }
            int r1 = r6.lastStreamId     // Catch:{ all -> 0x004f }
            r6.goAwayStreamId = r1     // Catch:{ all -> 0x004f }
            java.util.Map<java.lang.Integer, io.grpc.okhttp.OkHttpServerTransport$StreamState> r1 = r6.streams     // Catch:{ all -> 0x004f }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x004f }
            if (r1 == 0) goto L_0x002f
            io.grpc.okhttp.ExceptionHandlingFrameWriter r1 = r6.frameWriter     // Catch:{ all -> 0x004f }
            r1.close()     // Catch:{ all -> 0x004f }
            goto L_0x0034
        L_0x002f:
            io.grpc.okhttp.ExceptionHandlingFrameWriter r1 = r6.frameWriter     // Catch:{ all -> 0x004f }
            r1.flush()     // Catch:{ all -> 0x004f }
        L_0x0034:
            java.lang.Long r1 = r6.gracefulShutdownPeriod     // Catch:{ all -> 0x004f }
            if (r1 == 0) goto L_0x004d
            java.util.concurrent.ScheduledExecutorService r1 = r6.scheduledExecutorService     // Catch:{ all -> 0x004f }
            io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda0 r2 = new io.grpc.okhttp.OkHttpServerTransport$$ExternalSyntheticLambda0     // Catch:{ all -> 0x004f }
            r2.<init>(r6)     // Catch:{ all -> 0x004f }
            java.lang.Long r3 = r6.gracefulShutdownPeriod     // Catch:{ all -> 0x004f }
            long r3 = r3.longValue()     // Catch:{ all -> 0x004f }
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ all -> 0x004f }
            java.util.concurrent.ScheduledFuture r1 = r1.schedule(r2, r3, r5)     // Catch:{ all -> 0x004f }
            r6.forcefulCloseTimer = r1     // Catch:{ all -> 0x004f }
        L_0x004d:
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            return
        L_0x004f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpServerTransport.triggerGracefulSecondGoaway():void");
    }

    public void shutdownNow(Status reason) {
        synchronized (this.lock) {
            if (this.frameWriter == null) {
                this.handshakeShutdown = true;
                GrpcUtil.closeQuietly((Closeable) this.socket);
                return;
            }
            abruptShutdown(ErrorCode.NO_ERROR, "", reason, true);
        }
    }

    public void onException(Throwable failureCause) {
        Preconditions.checkNotNull(failureCause, "failureCause");
        abruptShutdown(ErrorCode.INTERNAL_ERROR, "I/O failure", Status.UNAVAILABLE.withCause(failureCause), false);
    }

    /* access modifiers changed from: private */
    public void abruptShutdown(ErrorCode errorCode, String moreDetail, Status reason, boolean rstStreams) {
        synchronized (this.lock) {
            if (!this.abruptShutdown) {
                this.abruptShutdown = true;
                this.goAwayStatus = reason;
                if (this.secondGoawayTimer != null) {
                    this.secondGoawayTimer.cancel(false);
                    this.secondGoawayTimer = null;
                }
                for (Map.Entry<Integer, StreamState> entry : this.streams.entrySet()) {
                    if (rstStreams) {
                        this.frameWriter.rstStream(entry.getKey().intValue(), ErrorCode.CANCEL);
                    }
                    entry.getValue().transportReportStatus(reason);
                }
                this.streams.clear();
                this.frameWriter.goAway(this.lastStreamId, errorCode, moreDetail.getBytes(GrpcUtil.US_ASCII));
                this.goAwayStreamId = this.lastStreamId;
                this.frameWriter.close();
                this.forcefulCloseTimer = this.scheduledExecutorService.schedule(new OkHttpServerTransport$$ExternalSyntheticLambda0(this), 1, TimeUnit.SECONDS);
            }
        }
    }

    /* access modifiers changed from: private */
    public void triggerForcefulClose() {
        GrpcUtil.closeQuietly((Closeable) this.socket);
    }

    /* access modifiers changed from: private */
    public void terminated() {
        synchronized (this.lock) {
            if (this.forcefulCloseTimer != null) {
                this.forcefulCloseTimer.cancel(false);
                this.forcefulCloseTimer = null;
            }
        }
        if (this.keepAliveManager != null) {
            this.keepAliveManager.onTransportTermination();
        }
        if (this.maxConnectionIdleManager != null) {
            this.maxConnectionIdleManager.onTransportTermination();
        }
        if (this.maxConnectionAgeMonitor != null) {
            this.maxConnectionAgeMonitor.cancel(false);
        }
        this.transportExecutor = this.config.transportExecutorPool.returnObject(this.transportExecutor);
        this.scheduledExecutorService = this.config.scheduledExecutorServicePool.returnObject(this.scheduledExecutorService);
        this.listener.transportTerminated();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

    public ListenableFuture<InternalChannelz.SocketStats> getStats() {
        ListenableFuture<InternalChannelz.SocketStats> immediateFuture;
        synchronized (this.lock) {
            immediateFuture = Futures.immediateFuture(new InternalChannelz.SocketStats(this.tracer.getStats(), this.socket.getLocalSocketAddress(), this.socket.getRemoteSocketAddress(), Utils.getSocketOptions(this.socket), this.securityInfo));
        }
        return immediateFuture;
    }

    /* access modifiers changed from: private */
    public TransportTracer.FlowControlWindows readFlowControlWindow() {
        TransportTracer.FlowControlWindows flowControlWindows;
        synchronized (this.lock) {
            flowControlWindows = new TransportTracer.FlowControlWindows(this.outboundFlow == null ? -1 : (long) this.outboundFlow.windowUpdate((OutboundFlowController.StreamState) null, 0), (long) (((float) this.config.flowControlWindow) * 0.5f));
        }
        return flowControlWindows;
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    public OutboundFlowController.StreamState[] getActiveStreams() {
        OutboundFlowController.StreamState[] flowStreams;
        synchronized (this.lock) {
            flowStreams = new OutboundFlowController.StreamState[this.streams.size()];
            int i = 0;
            for (StreamState stream : this.streams.values()) {
                flowStreams[i] = stream.getOutboundFlowState();
                i++;
            }
        }
        return flowStreams;
    }

    /* access modifiers changed from: package-private */
    public void streamClosed(int streamId, boolean flush) {
        synchronized (this.lock) {
            this.streams.remove(Integer.valueOf(streamId));
            if (this.streams.isEmpty()) {
                this.keepAliveEnforcer.onTransportIdle();
                if (this.maxConnectionIdleManager != null) {
                    this.maxConnectionIdleManager.onTransportIdle();
                }
            }
            if (this.gracefulShutdown && this.streams.isEmpty()) {
                this.frameWriter.close();
            } else if (flush) {
                this.frameWriter.flush();
            }
        }
    }

    /* access modifiers changed from: private */
    public static String asciiString(ByteString value) {
        for (int i = 0; i < value.size(); i++) {
            if (value.getByte(i) < 0) {
                return value.string(GrpcUtil.US_ASCII);
            }
        }
        return value.utf8();
    }

    /* access modifiers changed from: private */
    public static int headerFind(List<Header> header, ByteString key, int startIndex) {
        for (int i = startIndex; i < header.size(); i++) {
            if (header.get(i).name.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public static boolean headerContains(List<Header> header, ByteString key) {
        return headerFind(header, key, 0) != -1;
    }

    /* access modifiers changed from: private */
    public static void headerRemove(List<Header> header, ByteString key) {
        int i = 0;
        while (true) {
            int headerFind = headerFind(header, key, i);
            i = headerFind;
            if (headerFind != -1) {
                header.remove(i);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static ByteString headerGetRequiredSingle(List<Header> header, ByteString key) {
        int i = headerFind(header, key, 0);
        if (i != -1 && headerFind(header, key, i + 1) == -1) {
            return header.get(i).value;
        }
        return null;
    }

    static final class Config {
        final int flowControlWindow;
        final HandshakerSocketFactory handshakerSocketFactory;
        final long keepAliveTimeNanos;
        final long keepAliveTimeoutNanos;
        final long maxConnectionAgeGraceInNanos;
        final long maxConnectionAgeInNanos;
        final long maxConnectionIdleNanos;
        final int maxInboundMessageSize;
        final int maxInboundMetadataSize;
        final long permitKeepAliveTimeInNanos;
        final boolean permitKeepAliveWithoutCalls;
        final ObjectPool<ScheduledExecutorService> scheduledExecutorServicePool;
        final List<? extends ServerStreamTracer.Factory> streamTracerFactories;
        final ObjectPool<Executor> transportExecutorPool;
        final TransportTracer.Factory transportTracerFactory;

        public Config(OkHttpServerBuilder builder, List<? extends ServerStreamTracer.Factory> streamTracerFactories2) {
            this.streamTracerFactories = (List) Preconditions.checkNotNull(streamTracerFactories2, "streamTracerFactories");
            this.transportExecutorPool = (ObjectPool) Preconditions.checkNotNull(builder.transportExecutorPool, "transportExecutorPool");
            this.scheduledExecutorServicePool = (ObjectPool) Preconditions.checkNotNull(builder.scheduledExecutorServicePool, "scheduledExecutorServicePool");
            this.transportTracerFactory = (TransportTracer.Factory) Preconditions.checkNotNull(builder.transportTracerFactory, "transportTracerFactory");
            this.handshakerSocketFactory = (HandshakerSocketFactory) Preconditions.checkNotNull(builder.handshakerSocketFactory, "handshakerSocketFactory");
            this.keepAliveTimeNanos = builder.keepAliveTimeNanos;
            this.keepAliveTimeoutNanos = builder.keepAliveTimeoutNanos;
            this.flowControlWindow = builder.flowControlWindow;
            this.maxInboundMessageSize = builder.maxInboundMessageSize;
            this.maxInboundMetadataSize = builder.maxInboundMetadataSize;
            this.maxConnectionIdleNanos = builder.maxConnectionIdleInNanos;
            this.permitKeepAliveWithoutCalls = builder.permitKeepAliveWithoutCalls;
            this.permitKeepAliveTimeInNanos = builder.permitKeepAliveTimeInNanos;
            this.maxConnectionAgeInNanos = builder.maxConnectionAgeInNanos;
            this.maxConnectionAgeGraceInNanos = builder.maxConnectionAgeGraceInNanos;
        }
    }

    class FrameHandler implements FrameReader.Handler, Runnable {
        private int connectionUnacknowledgedBytesRead;
        private final OkHttpFrameLogger frameLogger = new OkHttpFrameLogger(Level.FINE, (Class<?>) OkHttpServerTransport.class);
        private final FrameReader frameReader;
        private boolean receivedSettings;

        public FrameHandler(FrameReader frameReader2) {
            this.frameReader = frameReader2;
        }

        public void run() {
            Status status;
            String threadName = Thread.currentThread().getName();
            Thread.currentThread().setName("OkHttpServerTransport");
            try {
                this.frameReader.readConnectionPreface();
                if (!this.frameReader.nextFrame(this)) {
                    connectionError(ErrorCode.INTERNAL_ERROR, "Failed to read initial SETTINGS");
                    try {
                        GrpcUtil.exhaust(OkHttpServerTransport.this.socket.getInputStream());
                    } catch (IOException e) {
                    }
                    GrpcUtil.closeQuietly((Closeable) OkHttpServerTransport.this.socket);
                    OkHttpServerTransport.this.terminated();
                    Thread.currentThread().setName(threadName);
                } else if (!this.receivedSettings) {
                    connectionError(ErrorCode.PROTOCOL_ERROR, "First HTTP/2 frame must be SETTINGS. RFC7540 section 3.5");
                    try {
                        GrpcUtil.exhaust(OkHttpServerTransport.this.socket.getInputStream());
                    } catch (IOException e2) {
                    }
                    GrpcUtil.closeQuietly((Closeable) OkHttpServerTransport.this.socket);
                    OkHttpServerTransport.this.terminated();
                    Thread.currentThread().setName(threadName);
                } else {
                    while (this.frameReader.nextFrame(this)) {
                        if (OkHttpServerTransport.this.keepAliveManager != null) {
                            OkHttpServerTransport.this.keepAliveManager.onDataReceived();
                        }
                    }
                    synchronized (OkHttpServerTransport.this.lock) {
                        status = OkHttpServerTransport.this.goAwayStatus;
                    }
                    if (status == null) {
                        status = Status.UNAVAILABLE.withDescription("TCP connection closed or IOException");
                    }
                    OkHttpServerTransport.this.abruptShutdown(ErrorCode.INTERNAL_ERROR, "I/O failure", status, false);
                    try {
                        GrpcUtil.exhaust(OkHttpServerTransport.this.socket.getInputStream());
                    } catch (IOException e3) {
                    }
                }
            } catch (Throwable t) {
                try {
                    OkHttpServerTransport.log.log(Level.WARNING, "Error decoding HTTP/2 frames", t);
                    OkHttpServerTransport.this.abruptShutdown(ErrorCode.INTERNAL_ERROR, "Error in frame decoder", Status.INTERNAL.withDescription("Error decoding HTTP/2 frames").withCause(t), false);
                } finally {
                    try {
                        GrpcUtil.exhaust(OkHttpServerTransport.this.socket.getInputStream());
                    } catch (IOException e4) {
                    }
                    GrpcUtil.closeQuietly((Closeable) OkHttpServerTransport.this.socket);
                    OkHttpServerTransport.this.terminated();
                    Thread.currentThread().setName(threadName);
                }
            }
        }

        /* JADX WARNING: type inference failed for: r19v0 */
        /* JADX WARNING: Code restructure failed: missing block: B:101:0x01ca, code lost:
            if (r7.getByte(0) == 47) goto L_0x01d6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:102:0x01cc, code lost:
            r12 = r31;
            r11 = r33;
            r26 = r7;
            r29 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:103:0x01d6, code lost:
            r10 = io.grpc.okhttp.OkHttpServerTransport.access$2300(r7).substring(1);
            r18 = io.grpc.okhttp.OkHttpServerTransport.access$2500(r11, io.grpc.okhttp.OkHttpServerTransport.access$2400());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:104:0x01e6, code lost:
            if (r18 != null) goto L_0x01f8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:105:0x01e8, code lost:
            respondWithHttpError(r34, r33, 415, io.grpc.Status.Code.INTERNAL, "Content-Type is missing or duplicated");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:0x01f7, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:0x01f8, code lost:
            r8 = io.grpc.okhttp.OkHttpServerTransport.access$2300(r18);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:108:0x0200, code lost:
            if (io.grpc.internal.GrpcUtil.isGrpcContentType(r8) != false) goto L_0x0223;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:109:0x0202, code lost:
            respondWithHttpError(r34, r33, 415, io.grpc.Status.Code.INTERNAL, "Content-Type is not supported: " + r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:0x0222, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:0x022b, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$2600().equals(r15) != false) goto L_0x0257;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:113:0x022d, code lost:
            r3 = r33;
            r2 = r34;
            respondWithHttpError(r2, r3, 405, io.grpc.Status.Code.INTERNAL, "HTTP Method is not supported: " + io.grpc.okhttp.OkHttpServerTransport.access$2300(r15));
            r30 = r3;
            r3 = r2;
            r2 = r30;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:0x0256, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:0x0257, code lost:
            r1 = r31;
            r2 = r33;
            r3 = r34;
            r4 = io.grpc.okhttp.OkHttpServerTransport.access$2500(r11, io.grpc.okhttp.OkHttpServerTransport.access$2700());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x026d, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$2800().equals(r4) != false) goto L_0x0290;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x026f, code lost:
            r0 = io.grpc.Status.Code.INTERNAL;
            r6 = io.grpc.okhttp.OkHttpServerTransport.access$2300(io.grpc.okhttp.OkHttpServerTransport.access$2800());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x027b, code lost:
            if (r4 != null) goto L_0x0280;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x027d, code lost:
            r9 = "<missing>";
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x0280, code lost:
            r9 = io.grpc.okhttp.OkHttpServerTransport.access$2300(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x0284, code lost:
            respondWithGrpcError(r3, r2, r0, java.lang.String.format("Expected header TE: %s, but %s is received. Some intermediate proxy may not support trailers", new java.lang.Object[]{r6, r9}));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x028f, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x0290, code lost:
            io.grpc.okhttp.OkHttpServerTransport.access$1200(r11, io.grpc.okhttp.OkHttpServerTransport.access$2900());
            r5 = io.grpc.okhttp.Utils.convertHeaders(r11);
            r23 = io.grpc.internal.StatsTraceContext.newServerContext(io.grpc.okhttp.OkHttpServerTransport.access$1100(r1.this$0).streamTracerFactories, r10, r5);
            r25 = io.grpc.okhttp.OkHttpServerTransport.access$300(r1.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x02ad, code lost:
            monitor-enter(r25);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x02e2, code lost:
            r11 = r2;
            r28 = r4;
            r26 = r7;
            r27 = r8;
            r29 = r13;
            r4 = r23;
            r13 = r5;
            r0 = r19;
            r12 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
            r20 = new io.grpc.okhttp.OkHttpServerStream.TransportState(r1.this$0, r3, io.grpc.okhttp.OkHttpServerTransport.access$1100(r1.this$0).maxInboundMessageSize, r4, io.grpc.okhttp.OkHttpServerTransport.access$300(r1.this$0), io.grpc.okhttp.OkHttpServerTransport.access$3000(r1.this$0), io.grpc.okhttp.OkHttpServerTransport.access$3100(r1.this$0), io.grpc.okhttp.OkHttpServerTransport.access$1100(r1.this$0).flowControlWindow, io.grpc.okhttp.OkHttpServerTransport.access$3200(r1.this$0), r10);
            r21 = io.grpc.okhttp.OkHttpServerTransport.access$3300(r12.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:0x0309, code lost:
            if (r17 != null) goto L_0x030d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x030b, code lost:
            r0 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:132:0x030d, code lost:
            r0 = io.grpc.okhttp.OkHttpServerTransport.access$2300(r17);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x0319, code lost:
            r23 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
            r19 = new io.grpc.okhttp.OkHttpServerStream(r20, r21, r0, r23, io.grpc.okhttp.OkHttpServerTransport.access$3200(r12.this$0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x031e, code lost:
            r0 = r20;
            r1 = r19;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:0x032c, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$2000(r12.this$0).isEmpty() == false) goto L_0x0348;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x032e, code lost:
            io.grpc.okhttp.OkHttpServerTransport.access$000(r12.this$0).onTransportActive();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:0x033d, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$3400(r12.this$0) == null) goto L_0x0348;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:143:0x033f, code lost:
            io.grpc.okhttp.OkHttpServerTransport.access$3400(r12.this$0).onTransportActive();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:144:0x0348, code lost:
            io.grpc.okhttp.OkHttpServerTransport.access$2000(r12.this$0).put(java.lang.Integer.valueOf(r34), r0);
            io.grpc.okhttp.OkHttpServerTransport.access$3500(r12.this$0).streamCreated(r1, r10, r13);
            r0.onStreamAllocated();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:145:0x0361, code lost:
            if (r11 == false) goto L_0x036c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:146:0x0363, code lost:
            r0.inboundDataReceived(new okio.Buffer(), 0, 0, r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:147:0x036c, code lost:
            monitor-exit(r25);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:148:0x036d, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x036e, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:150:0x036f, code lost:
            r4 = r23;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x0372, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:152:0x0373, code lost:
            r12 = r1;
            r11 = r2;
            r28 = r4;
            r26 = r7;
            r27 = r8;
            r29 = r13;
            r4 = r23;
            r13 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:153:0x0380, code lost:
            monitor-exit(r25);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:154:0x0381, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:155:0x0382, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x0384, code lost:
            r12 = r31;
            r11 = r33;
            r26 = r7;
            r29 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x038c, code lost:
            r12.respondWithHttpError(r34, r11, 404, io.grpc.Status.Code.UNIMPLEMENTED, "Expected path to start with /: " + io.grpc.okhttp.OkHttpServerTransport.access$2300(r26));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:158:0x03ae, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0042, code lost:
            r14 = headerBlockSize(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x004e, code lost:
            if (r14 <= io.grpc.okhttp.OkHttpServerTransport.access$1100(r1.this$0).maxInboundMetadataSize) goto L_0x0074;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0050, code lost:
            respondWithHttpError(r2, r3, 431, io.grpc.Status.Code.RESOURCE_EXHAUSTED, java.lang.String.format(java.util.Locale.US, "Request metadata larger than %d: %d", new java.lang.Object[]{java.lang.Integer.valueOf(io.grpc.okhttp.OkHttpServerTransport.access$1100(r1.this$0).maxInboundMetadataSize), java.lang.Integer.valueOf(r14)}));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0073, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0074, code lost:
            io.grpc.okhttp.OkHttpServerTransport.access$1200(r11, okio.ByteString.EMPTY);
            r15 = null;
            r16 = null;
            r7 = null;
            r8 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0088, code lost:
            if (r11.size() <= 0) goto L_0x00ef;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0096, code lost:
            if (r11.get(0).name.getByte(0) != 58) goto L_0x00ef;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0098, code lost:
            r0 = r11.remove(0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a8, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$1300().equals(r0.name) == false) goto L_0x00b0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00aa, code lost:
            if (r15 != null) goto L_0x00b0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ac, code lost:
            r15 = r0.value;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ba, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$1400().equals(r0.name) == false) goto L_0x00c3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bc, code lost:
            if (r16 != null) goto L_0x00c3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00be, code lost:
            r16 = r0.value;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cd, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$1500().equals(r0.name) == false) goto L_0x00d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00cf, code lost:
            if (r7 != null) goto L_0x00d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00d1, code lost:
            r7 = r0.value;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00df, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$1600().equals(r0.name) == false) goto L_0x00e7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e1, code lost:
            if (r8 != null) goto L_0x00e7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00e3, code lost:
            r8 = r0.value;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e7, code lost:
            streamError(r2, io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR, "Unexpected pseudo header. RFC7540 section 8.1.2.1");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ee, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ef, code lost:
            r0 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f4, code lost:
            if (r0 >= r11.size()) goto L_0x010f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x0102, code lost:
            if (r11.get(r0).name.getByte(0) != 58) goto L_0x010c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0104, code lost:
            streamError(r2, io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR, "Pseudo header not before regular headers. RFC7540 section 8.1.2.1");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x010b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x010c, code lost:
            r0 = r0 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x0117, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$1700().equals(r15) != false) goto L_0x0129;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x0119, code lost:
            if (r13 == false) goto L_0x0129;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x011b, code lost:
            if (r15 == null) goto L_0x0121;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x011d, code lost:
            if (r16 == null) goto L_0x0121;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x011f, code lost:
            if (r7 != null) goto L_0x0129;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x0121, code lost:
            streamError(r2, io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR, "Missing required pseudo header. RFC7540 section 8.1.2.3");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x0128, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x0131, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$1900(r11, io.grpc.okhttp.OkHttpServerTransport.access$1800()) == false) goto L_0x013b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x0133, code lost:
            streamError(r2, io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR, "Connection-specific headers not permitted. RFC7540 section 8.1.2.2");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x013a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x013b, code lost:
            if (r13 != false) goto L_0x0185;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x013d, code lost:
            if (r33 == false) goto L_0x017d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:0x013f, code lost:
            r3 = io.grpc.okhttp.OkHttpServerTransport.access$300(r1.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:0x0145, code lost:
            monitor-enter(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
            r0 = (io.grpc.okhttp.OkHttpServerTransport.StreamState) io.grpc.okhttp.OkHttpServerTransport.access$2000(r1.this$0).get(java.lang.Integer.valueOf(r2));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x0156, code lost:
            if (r0 != null) goto L_0x0161;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:0x0158, code lost:
            streamError(r2, io.grpc.okhttp.internal.framed.ErrorCode.STREAM_CLOSED, "Received headers for closed stream");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x015f, code lost:
            monitor-exit(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:0x0160, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x0165, code lost:
            if (r0.hasReceivedEndOfStream() == false) goto L_0x0170;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x0167, code lost:
            streamError(r2, io.grpc.okhttp.internal.framed.ErrorCode.STREAM_CLOSED, "Received HEADERS for half-closed (remote) stream. RFC7540 section 5.1");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x016e, code lost:
            monitor-exit(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:0x016f, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x0170, code lost:
            r0.inboundDataReceived(new okio.Buffer(), 0, 0, true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x0178, code lost:
            monitor-exit(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:83:0x0179, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:87:0x017d, code lost:
            streamError(r2, io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR, "Headers disallowed in the middle of the stream. RFC7540 section 8.1");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:88:0x0184, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x0185, code lost:
            if (r8 != null) goto L_0x01b5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:90:0x0187, code lost:
            r0 = io.grpc.okhttp.OkHttpServerTransport.access$2200(r11, io.grpc.okhttp.OkHttpServerTransport.access$2100(), 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:91:0x0190, code lost:
            if (r0 == -1) goto L_0x01b5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:93:0x019c, code lost:
            if (io.grpc.okhttp.OkHttpServerTransport.access$2200(r11, io.grpc.okhttp.OkHttpServerTransport.access$2100(), r0 + 1) == -1) goto L_0x01aa;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x019e, code lost:
            respondWithHttpError(r2, r33, com.google.logging.type.LogSeverity.WARNING_VALUE, io.grpc.Status.Code.INTERNAL, "Multiple host headers disallowed. RFC7230 section 5.4");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:0x01a9, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:96:0x01aa, code lost:
            r17 = r11.get(r0).value;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:97:0x01b5, code lost:
            r17 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:0x01b7, code lost:
            io.grpc.okhttp.OkHttpServerTransport.access$1200(r11, io.grpc.okhttp.OkHttpServerTransport.access$2100());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x01c2, code lost:
            if (r7.size() == 0) goto L_0x0384;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void headers(boolean r32, boolean r33, int r34, int r35, java.util.List<io.grpc.okhttp.internal.framed.Header> r36, io.grpc.okhttp.internal.framed.HeadersMode r37) {
            /*
                r31 = this;
                r1 = r31
                r3 = r33
                r2 = r34
                r11 = r36
                io.grpc.okhttp.OkHttpFrameLogger r0 = r1.frameLogger
                io.grpc.okhttp.OkHttpFrameLogger$Direction r4 = io.grpc.okhttp.OkHttpFrameLogger.Direction.INBOUND
                r0.logHeaders(r4, r2, r11, r3)
                r0 = r2 & 1
                if (r0 != 0) goto L_0x001b
                io.grpc.okhttp.internal.framed.ErrorCode r0 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r4 = "Clients cannot open even numbered streams. RFC7540 section 5.1.1"
                r1.connectionError(r0, r4)
                return
            L_0x001b:
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this
                java.lang.Object r4 = r0.lock
                monitor-enter(r4)
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x03af }
                int r0 = r0.goAwayStreamId     // Catch:{ all -> 0x03af }
                if (r2 <= r0) goto L_0x002c
                monitor-exit(r4)     // Catch:{ all -> 0x03af }
                return
            L_0x002c:
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x03af }
                int r0 = r0.lastStreamId     // Catch:{ all -> 0x03af }
                r12 = 0
                r5 = 1
                if (r2 <= r0) goto L_0x0038
                r0 = r5
                goto L_0x0039
            L_0x0038:
                r0 = r12
            L_0x0039:
                r13 = r0
                if (r13 == 0) goto L_0x0041
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x03af }
                int unused = r0.lastStreamId = r2     // Catch:{ all -> 0x03af }
            L_0x0041:
                monitor-exit(r4)     // Catch:{ all -> 0x03af }
                int r14 = r1.headerBlockSize(r11)
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this
                io.grpc.okhttp.OkHttpServerTransport$Config r0 = r0.config
                int r0 = r0.maxInboundMetadataSize
                if (r14 <= r0) goto L_0x0074
                io.grpc.Status$Code r5 = io.grpc.Status.Code.RESOURCE_EXHAUSTED
                java.util.Locale r0 = java.util.Locale.US
                java.lang.String r4 = "Request metadata larger than %d: %d"
                io.grpc.okhttp.OkHttpServerTransport r6 = io.grpc.okhttp.OkHttpServerTransport.this
                io.grpc.okhttp.OkHttpServerTransport$Config r6 = r6.config
                int r6 = r6.maxInboundMetadataSize
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
                java.lang.Integer r7 = java.lang.Integer.valueOf(r14)
                java.lang.Object[] r6 = new java.lang.Object[]{r6, r7}
                java.lang.String r6 = java.lang.String.format(r0, r4, r6)
                r4 = 431(0x1af, float:6.04E-43)
                r1.respondWithHttpError(r2, r3, r4, r5, r6)
                return
            L_0x0074:
                okio.ByteString r0 = okio.ByteString.EMPTY
                io.grpc.okhttp.OkHttpServerTransport.headerRemove(r11, r0)
                r0 = 0
                r3 = 0
                r4 = 0
                r6 = 0
                r15 = r0
                r16 = r3
                r7 = r4
                r8 = r6
            L_0x0082:
                int r0 = r11.size()
                r3 = 58
                if (r0 <= 0) goto L_0x00ef
                java.lang.Object r0 = r11.get(r12)
                io.grpc.okhttp.internal.framed.Header r0 = (io.grpc.okhttp.internal.framed.Header) r0
                okio.ByteString r0 = r0.name
                byte r0 = r0.getByte(r12)
                if (r0 != r3) goto L_0x00ef
                java.lang.Object r0 = r11.remove(r12)
                io.grpc.okhttp.internal.framed.Header r0 = (io.grpc.okhttp.internal.framed.Header) r0
                okio.ByteString r3 = io.grpc.okhttp.OkHttpServerTransport.HTTP_METHOD
                okio.ByteString r4 = r0.name
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x00b0
                if (r15 != 0) goto L_0x00b0
                okio.ByteString r3 = r0.value
                r15 = r3
                goto L_0x00e6
            L_0x00b0:
                okio.ByteString r3 = io.grpc.okhttp.OkHttpServerTransport.SCHEME
                okio.ByteString r4 = r0.name
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x00c3
                if (r16 != 0) goto L_0x00c3
                okio.ByteString r3 = r0.value
                r16 = r3
                goto L_0x00e6
            L_0x00c3:
                okio.ByteString r3 = io.grpc.okhttp.OkHttpServerTransport.PATH
                okio.ByteString r4 = r0.name
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x00d5
                if (r7 != 0) goto L_0x00d5
                okio.ByteString r3 = r0.value
                r7 = r3
                goto L_0x00e6
            L_0x00d5:
                okio.ByteString r3 = io.grpc.okhttp.OkHttpServerTransport.AUTHORITY
                okio.ByteString r4 = r0.name
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x00e7
                if (r8 != 0) goto L_0x00e7
                okio.ByteString r3 = r0.value
                r8 = r3
            L_0x00e6:
                goto L_0x0082
            L_0x00e7:
                io.grpc.okhttp.internal.framed.ErrorCode r3 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r4 = "Unexpected pseudo header. RFC7540 section 8.1.2.1"
                r1.streamError(r2, r3, r4)
                return
            L_0x00ef:
                r0 = 0
            L_0x00f0:
                int r4 = r11.size()
                if (r0 >= r4) goto L_0x010f
                java.lang.Object r4 = r11.get(r0)
                io.grpc.okhttp.internal.framed.Header r4 = (io.grpc.okhttp.internal.framed.Header) r4
                okio.ByteString r4 = r4.name
                byte r4 = r4.getByte(r12)
                if (r4 != r3) goto L_0x010c
                io.grpc.okhttp.internal.framed.ErrorCode r3 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r4 = "Pseudo header not before regular headers. RFC7540 section 8.1.2.1"
                r1.streamError(r2, r3, r4)
                return
            L_0x010c:
                int r0 = r0 + 1
                goto L_0x00f0
            L_0x010f:
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.CONNECT_METHOD
                boolean r0 = r0.equals(r15)
                if (r0 != 0) goto L_0x0129
                if (r13 == 0) goto L_0x0129
                if (r15 == 0) goto L_0x0121
                if (r16 == 0) goto L_0x0121
                if (r7 != 0) goto L_0x0129
            L_0x0121:
                io.grpc.okhttp.internal.framed.ErrorCode r0 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r3 = "Missing required pseudo header. RFC7540 section 8.1.2.3"
                r1.streamError(r2, r0, r3)
                return
            L_0x0129:
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.CONNECTION
                boolean r0 = io.grpc.okhttp.OkHttpServerTransport.headerContains(r11, r0)
                if (r0 == 0) goto L_0x013b
                io.grpc.okhttp.internal.framed.ErrorCode r0 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r3 = "Connection-specific headers not permitted. RFC7540 section 8.1.2.2"
                r1.streamError(r2, r0, r3)
                return
            L_0x013b:
                if (r13 != 0) goto L_0x0185
                if (r33 == 0) goto L_0x017d
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this
                java.lang.Object r3 = r0.lock
                monitor-enter(r3)
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x017a }
                java.util.Map r0 = r0.streams     // Catch:{ all -> 0x017a }
                java.lang.Integer r4 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x017a }
                java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x017a }
                io.grpc.okhttp.OkHttpServerTransport$StreamState r0 = (io.grpc.okhttp.OkHttpServerTransport.StreamState) r0     // Catch:{ all -> 0x017a }
                if (r0 != 0) goto L_0x0161
                io.grpc.okhttp.internal.framed.ErrorCode r4 = io.grpc.okhttp.internal.framed.ErrorCode.STREAM_CLOSED     // Catch:{ all -> 0x017a }
                java.lang.String r5 = "Received headers for closed stream"
                r1.streamError(r2, r4, r5)     // Catch:{ all -> 0x017a }
                monitor-exit(r3)     // Catch:{ all -> 0x017a }
                return
            L_0x0161:
                boolean r4 = r0.hasReceivedEndOfStream()     // Catch:{ all -> 0x017a }
                if (r4 == 0) goto L_0x0170
                io.grpc.okhttp.internal.framed.ErrorCode r4 = io.grpc.okhttp.internal.framed.ErrorCode.STREAM_CLOSED     // Catch:{ all -> 0x017a }
                java.lang.String r5 = "Received HEADERS for half-closed (remote) stream. RFC7540 section 5.1"
                r1.streamError(r2, r4, r5)     // Catch:{ all -> 0x017a }
                monitor-exit(r3)     // Catch:{ all -> 0x017a }
                return
            L_0x0170:
                okio.Buffer r4 = new okio.Buffer     // Catch:{ all -> 0x017a }
                r4.<init>()     // Catch:{ all -> 0x017a }
                r0.inboundDataReceived(r4, r12, r12, r5)     // Catch:{ all -> 0x017a }
                monitor-exit(r3)     // Catch:{ all -> 0x017a }
                return
            L_0x017a:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x017a }
                throw r0
            L_0x017d:
                io.grpc.okhttp.internal.framed.ErrorCode r0 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r3 = "Headers disallowed in the middle of the stream. RFC7540 section 8.1"
                r1.streamError(r2, r0, r3)
                return
            L_0x0185:
                if (r8 != 0) goto L_0x01b5
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.HOST
                int r0 = io.grpc.okhttp.OkHttpServerTransport.headerFind(r11, r0, r12)
                r3 = -1
                if (r0 == r3) goto L_0x01b5
                okio.ByteString r4 = io.grpc.okhttp.OkHttpServerTransport.HOST
                int r6 = r0 + 1
                int r4 = io.grpc.okhttp.OkHttpServerTransport.headerFind(r11, r4, r6)
                if (r4 == r3) goto L_0x01aa
                io.grpc.Status$Code r5 = io.grpc.Status.Code.INTERNAL
                java.lang.String r6 = "Multiple host headers disallowed. RFC7230 section 5.4"
                r4 = 400(0x190, float:5.6E-43)
                r3 = r33
                r1.respondWithHttpError(r2, r3, r4, r5, r6)
                return
            L_0x01aa:
                java.lang.Object r1 = r11.get(r0)
                io.grpc.okhttp.internal.framed.Header r1 = (io.grpc.okhttp.internal.framed.Header) r1
                okio.ByteString r8 = r1.value
                r17 = r8
                goto L_0x01b7
            L_0x01b5:
                r17 = r8
            L_0x01b7:
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.HOST
                io.grpc.okhttp.OkHttpServerTransport.headerRemove(r11, r0)
                int r0 = r7.size()
                if (r0 == 0) goto L_0x0384
                byte r0 = r7.getByte(r12)
                r1 = 47
                if (r0 == r1) goto L_0x01d6
                r12 = r31
                r11 = r33
                r26 = r7
                r29 = r13
                goto L_0x038c
            L_0x01d6:
                java.lang.String r0 = io.grpc.okhttp.OkHttpServerTransport.asciiString(r7)
                java.lang.String r10 = r0.substring(r5)
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.CONTENT_TYPE
                okio.ByteString r18 = io.grpc.okhttp.OkHttpServerTransport.headerGetRequiredSingle(r11, r0)
                if (r18 != 0) goto L_0x01f8
                io.grpc.Status$Code r5 = io.grpc.Status.Code.INTERNAL
                java.lang.String r6 = "Content-Type is missing or duplicated"
                r4 = 415(0x19f, float:5.82E-43)
                r1 = r31
                r3 = r33
                r2 = r34
                r1.respondWithHttpError(r2, r3, r4, r5, r6)
                return
            L_0x01f8:
                java.lang.String r8 = io.grpc.okhttp.OkHttpServerTransport.asciiString(r18)
                boolean r0 = io.grpc.internal.GrpcUtil.isGrpcContentType(r8)
                if (r0 != 0) goto L_0x0223
                io.grpc.Status$Code r5 = io.grpc.Status.Code.INTERNAL
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Content-Type is not supported: "
                java.lang.StringBuilder r0 = r0.append(r1)
                java.lang.StringBuilder r0 = r0.append(r8)
                java.lang.String r6 = r0.toString()
                r4 = 415(0x19f, float:5.82E-43)
                r1 = r31
                r3 = r33
                r2 = r34
                r1.respondWithHttpError(r2, r3, r4, r5, r6)
                return
            L_0x0223:
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.POST_METHOD
                boolean r0 = r0.equals(r15)
                if (r0 != 0) goto L_0x0257
                io.grpc.Status$Code r5 = io.grpc.Status.Code.INTERNAL
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "HTTP Method is not supported: "
                java.lang.StringBuilder r0 = r0.append(r1)
                java.lang.String r1 = io.grpc.okhttp.OkHttpServerTransport.asciiString(r15)
                java.lang.StringBuilder r0 = r0.append(r1)
                java.lang.String r6 = r0.toString()
                r4 = 405(0x195, float:5.68E-43)
                r1 = r31
                r3 = r33
                r2 = r34
                r1.respondWithHttpError(r2, r3, r4, r5, r6)
                r30 = r3
                r3 = r2
                r2 = r30
                return
            L_0x0257:
                r1 = r31
                r2 = r33
                r3 = r34
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.TE
                okio.ByteString r4 = io.grpc.okhttp.OkHttpServerTransport.headerGetRequiredSingle(r11, r0)
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.TE_TRAILERS
                boolean r0 = r0.equals(r4)
                if (r0 != 0) goto L_0x0290
                io.grpc.Status$Code r0 = io.grpc.Status.Code.INTERNAL
                java.lang.String r5 = "Expected header TE: %s, but %s is received. Some intermediate proxy may not support trailers"
                okio.ByteString r6 = io.grpc.okhttp.OkHttpServerTransport.TE_TRAILERS
                java.lang.String r6 = io.grpc.okhttp.OkHttpServerTransport.asciiString(r6)
                if (r4 != 0) goto L_0x0280
                java.lang.String r9 = "<missing>"
                goto L_0x0284
            L_0x0280:
                java.lang.String r9 = io.grpc.okhttp.OkHttpServerTransport.asciiString(r4)
            L_0x0284:
                java.lang.Object[] r6 = new java.lang.Object[]{r6, r9}
                java.lang.String r5 = java.lang.String.format(r5, r6)
                r1.respondWithGrpcError(r3, r2, r0, r5)
                return
            L_0x0290:
                okio.ByteString r0 = io.grpc.okhttp.OkHttpServerTransport.CONTENT_LENGTH
                io.grpc.okhttp.OkHttpServerTransport.headerRemove(r11, r0)
                io.grpc.Metadata r5 = io.grpc.okhttp.Utils.convertHeaders(r11)
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this
                io.grpc.okhttp.OkHttpServerTransport$Config r0 = r0.config
                java.util.List<? extends io.grpc.ServerStreamTracer$Factory> r0 = r0.streamTracerFactories
                io.grpc.internal.StatsTraceContext r23 = io.grpc.internal.StatsTraceContext.newServerContext(r0, r10, r5)
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this
                java.lang.Object r25 = r0.lock
                monitor-enter(r25)
                io.grpc.okhttp.OkHttpServerStream$TransportState r0 = new io.grpc.okhttp.OkHttpServerStream$TransportState     // Catch:{ all -> 0x0372 }
                io.grpc.okhttp.OkHttpServerTransport r6 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0372 }
                io.grpc.okhttp.OkHttpServerTransport r9 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0372 }
                io.grpc.okhttp.OkHttpServerTransport$Config r9 = r9.config     // Catch:{ all -> 0x0372 }
                int r9 = r9.maxInboundMessageSize     // Catch:{ all -> 0x0372 }
                io.grpc.okhttp.OkHttpServerTransport r12 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0372 }
                java.lang.Object r12 = r12.lock     // Catch:{ all -> 0x0372 }
                r19 = r0
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0372 }
                io.grpc.okhttp.ExceptionHandlingFrameWriter r0 = r0.frameWriter     // Catch:{ all -> 0x0372 }
                r20 = r0
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0372 }
                io.grpc.okhttp.OutboundFlowController r0 = r0.outboundFlow     // Catch:{ all -> 0x0372 }
                r21 = r0
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0372 }
                io.grpc.okhttp.OkHttpServerTransport$Config r0 = r0.config     // Catch:{ all -> 0x0372 }
                int r0 = r0.flowControlWindow     // Catch:{ all -> 0x0372 }
                r22 = r0
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0372 }
                io.grpc.internal.TransportTracer r0 = r0.tracer     // Catch:{ all -> 0x0372 }
                r11 = r2
                r2 = r3
                r28 = r4
                r26 = r7
                r27 = r8
                r3 = r9
                r29 = r13
                r7 = r21
                r8 = r22
                r4 = r23
                r9 = r0
                r13 = r5
                r5 = r12
                r0 = r19
                r12 = r1
                r1 = r6
                r6 = r20
                r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0382 }
                r20 = r0
                io.grpc.okhttp.OkHttpServerStream r19 = new io.grpc.okhttp.OkHttpServerStream     // Catch:{ all -> 0x0382 }
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                io.grpc.Attributes r21 = r0.attributes     // Catch:{ all -> 0x0382 }
                if (r17 != 0) goto L_0x030d
                r0 = 0
                goto L_0x0311
            L_0x030d:
                java.lang.String r0 = io.grpc.okhttp.OkHttpServerTransport.asciiString(r17)     // Catch:{ all -> 0x0382 }
            L_0x0311:
                r22 = r0
                io.grpc.okhttp.OkHttpServerTransport r0 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                io.grpc.internal.TransportTracer r24 = r0.tracer     // Catch:{ all -> 0x0382 }
                r23 = r4
                r19.<init>(r20, r21, r22, r23, r24)     // Catch:{ all -> 0x036e }
                r0 = r20
                r1 = r19
                io.grpc.okhttp.OkHttpServerTransport r2 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                java.util.Map r2 = r2.streams     // Catch:{ all -> 0x0382 }
                boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0382 }
                if (r2 == 0) goto L_0x0348
                io.grpc.okhttp.OkHttpServerTransport r2 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                io.grpc.internal.KeepAliveEnforcer r2 = r2.keepAliveEnforcer     // Catch:{ all -> 0x0382 }
                r2.onTransportActive()     // Catch:{ all -> 0x0382 }
                io.grpc.okhttp.OkHttpServerTransport r2 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                io.grpc.internal.MaxConnectionIdleManager r2 = r2.maxConnectionIdleManager     // Catch:{ all -> 0x0382 }
                if (r2 == 0) goto L_0x0348
                io.grpc.okhttp.OkHttpServerTransport r2 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                io.grpc.internal.MaxConnectionIdleManager r2 = r2.maxConnectionIdleManager     // Catch:{ all -> 0x0382 }
                r2.onTransportActive()     // Catch:{ all -> 0x0382 }
            L_0x0348:
                io.grpc.okhttp.OkHttpServerTransport r2 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                java.util.Map r2 = r2.streams     // Catch:{ all -> 0x0382 }
                java.lang.Integer r3 = java.lang.Integer.valueOf(r34)     // Catch:{ all -> 0x0382 }
                r2.put(r3, r0)     // Catch:{ all -> 0x0382 }
                io.grpc.okhttp.OkHttpServerTransport r2 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x0382 }
                io.grpc.internal.ServerTransportListener r2 = r2.listener     // Catch:{ all -> 0x0382 }
                r2.streamCreated(r1, r10, r13)     // Catch:{ all -> 0x0382 }
                r0.onStreamAllocated()     // Catch:{ all -> 0x0382 }
                if (r11 == 0) goto L_0x036c
                okio.Buffer r2 = new okio.Buffer     // Catch:{ all -> 0x0382 }
                r2.<init>()     // Catch:{ all -> 0x0382 }
                r3 = 0
                r0.inboundDataReceived(r2, r3, r3, r11)     // Catch:{ all -> 0x0382 }
            L_0x036c:
                monitor-exit(r25)     // Catch:{ all -> 0x0382 }
                return
            L_0x036e:
                r0 = move-exception
                r4 = r23
                goto L_0x0380
            L_0x0372:
                r0 = move-exception
                r12 = r1
                r11 = r2
                r28 = r4
                r26 = r7
                r27 = r8
                r29 = r13
                r4 = r23
                r13 = r5
            L_0x0380:
                monitor-exit(r25)     // Catch:{ all -> 0x0382 }
                throw r0
            L_0x0382:
                r0 = move-exception
                goto L_0x0380
            L_0x0384:
                r12 = r31
                r11 = r33
                r26 = r7
                r29 = r13
            L_0x038c:
                io.grpc.Status$Code r5 = io.grpc.Status.Code.UNIMPLEMENTED
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Expected path to start with /: "
                java.lang.StringBuilder r0 = r0.append(r1)
                java.lang.String r1 = io.grpc.okhttp.OkHttpServerTransport.asciiString(r26)
                java.lang.StringBuilder r0 = r0.append(r1)
                java.lang.String r6 = r0.toString()
                r4 = 404(0x194, float:5.66E-43)
                r2 = r34
                r3 = r11
                r1 = r12
                r1.respondWithHttpError(r2, r3, r4, r5, r6)
                return
            L_0x03af:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x03af }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpServerTransport.FrameHandler.headers(boolean, boolean, int, int, java.util.List, io.grpc.okhttp.internal.framed.HeadersMode):void");
        }

        private int headerBlockSize(List<Header> headerBlock) {
            long size = 0;
            for (int i = 0; i < headerBlock.size(); i++) {
                Header header = headerBlock.get(i);
                size += (long) (header.name.size() + 32 + header.value.size());
            }
            return (int) Math.min(size, 2147483647L);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0087, code lost:
            r8.connectionUnacknowledgedBytesRead += r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x009d, code lost:
            if (((float) r8.connectionUnacknowledgedBytesRead) < (((float) io.grpc.okhttp.OkHttpServerTransport.access$1100(r8.this$0).flowControlWindow) * 0.5f)) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x009f, code lost:
            r9 = io.grpc.okhttp.OkHttpServerTransport.access$300(r8.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a5, code lost:
            monitor-enter(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            io.grpc.okhttp.OkHttpServerTransport.access$3000(r8.this$0).windowUpdate(0, (long) r8.connectionUnacknowledgedBytesRead);
            io.grpc.okhttp.OkHttpServerTransport.access$3000(r8.this$0).flush();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bc, code lost:
            monitor-exit(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00bd, code lost:
            r8.connectionUnacknowledgedBytesRead = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c0, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c3, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void data(boolean r9, int r10, okio.BufferedSource r11, int r12, int r13) throws java.io.IOException {
            /*
                r8 = this;
                io.grpc.okhttp.OkHttpFrameLogger r0 = r8.frameLogger
                io.grpc.okhttp.OkHttpFrameLogger$Direction r1 = io.grpc.okhttp.OkHttpFrameLogger.Direction.INBOUND
                okio.Buffer r3 = r11.getBuffer()
                r5 = r9
                r2 = r10
                r4 = r12
                r0.logData(r1, r2, r3, r4, r5)
                if (r2 != 0) goto L_0x0018
                io.grpc.okhttp.internal.framed.ErrorCode r9 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r10 = "Stream 0 is reserved for control messages. RFC7540 section 5.1.1"
                r8.connectionError(r9, r10)
                return
            L_0x0018:
                r9 = r2 & 1
                if (r9 != 0) goto L_0x0024
                io.grpc.okhttp.internal.framed.ErrorCode r9 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR
                java.lang.String r10 = "Clients cannot open even numbered streams. RFC7540 section 5.1.1"
                r8.connectionError(r9, r10)
                return
            L_0x0024:
                long r9 = (long) r4
                r11.require(r9)
                io.grpc.okhttp.OkHttpServerTransport r9 = io.grpc.okhttp.OkHttpServerTransport.this
                java.lang.Object r9 = r9.lock
                monitor-enter(r9)
                io.grpc.okhttp.OkHttpServerTransport r10 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x00c5 }
                java.util.Map r10 = r10.streams     // Catch:{ all -> 0x00c5 }
                java.lang.Integer r12 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x00c5 }
                java.lang.Object r10 = r10.get(r12)     // Catch:{ all -> 0x00c5 }
                io.grpc.okhttp.OkHttpServerTransport$StreamState r10 = (io.grpc.okhttp.OkHttpServerTransport.StreamState) r10     // Catch:{ all -> 0x00c5 }
                if (r10 != 0) goto L_0x004e
                long r0 = (long) r4     // Catch:{ all -> 0x00c5 }
                r11.skip(r0)     // Catch:{ all -> 0x00c5 }
                io.grpc.okhttp.internal.framed.ErrorCode r12 = io.grpc.okhttp.internal.framed.ErrorCode.STREAM_CLOSED     // Catch:{ all -> 0x00c5 }
                java.lang.String r0 = "Received data for closed stream"
                r8.streamError(r2, r12, r0)     // Catch:{ all -> 0x00c5 }
                monitor-exit(r9)     // Catch:{ all -> 0x00c5 }
                return
            L_0x004e:
                boolean r12 = r10.hasReceivedEndOfStream()     // Catch:{ all -> 0x00c5 }
                if (r12 == 0) goto L_0x0061
                long r0 = (long) r4     // Catch:{ all -> 0x00c5 }
                r11.skip(r0)     // Catch:{ all -> 0x00c5 }
                io.grpc.okhttp.internal.framed.ErrorCode r12 = io.grpc.okhttp.internal.framed.ErrorCode.STREAM_CLOSED     // Catch:{ all -> 0x00c5 }
                java.lang.String r0 = "Received DATA for half-closed (remote) stream. RFC7540 section 5.1"
                r8.streamError(r2, r12, r0)     // Catch:{ all -> 0x00c5 }
                monitor-exit(r9)     // Catch:{ all -> 0x00c5 }
                return
            L_0x0061:
                int r12 = r10.inboundWindowAvailable()     // Catch:{ all -> 0x00c5 }
                if (r12 >= r13) goto L_0x0074
                long r0 = (long) r4     // Catch:{ all -> 0x00c5 }
                r11.skip(r0)     // Catch:{ all -> 0x00c5 }
                io.grpc.okhttp.internal.framed.ErrorCode r12 = io.grpc.okhttp.internal.framed.ErrorCode.FLOW_CONTROL_ERROR     // Catch:{ all -> 0x00c5 }
                java.lang.String r0 = "Received DATA size exceeded window size. RFC7540 section 6.9"
                r8.streamError(r2, r12, r0)     // Catch:{ all -> 0x00c5 }
                monitor-exit(r9)     // Catch:{ all -> 0x00c5 }
                return
            L_0x0074:
                okio.Buffer r12 = new okio.Buffer     // Catch:{ all -> 0x00c5 }
                r12.<init>()     // Catch:{ all -> 0x00c5 }
                okio.Buffer r0 = r11.getBuffer()     // Catch:{ all -> 0x00c5 }
                long r6 = (long) r4     // Catch:{ all -> 0x00c5 }
                r12.write((okio.Buffer) r0, (long) r6)     // Catch:{ all -> 0x00c5 }
                int r0 = r13 - r4
                r10.inboundDataReceived(r12, r4, r0, r5)     // Catch:{ all -> 0x00c5 }
                monitor-exit(r9)     // Catch:{ all -> 0x00c5 }
                int r9 = r8.connectionUnacknowledgedBytesRead
                int r9 = r9 + r13
                r8.connectionUnacknowledgedBytesRead = r9
                int r9 = r8.connectionUnacknowledgedBytesRead
                float r9 = (float) r9
                io.grpc.okhttp.OkHttpServerTransport r10 = io.grpc.okhttp.OkHttpServerTransport.this
                io.grpc.okhttp.OkHttpServerTransport$Config r10 = r10.config
                int r10 = r10.flowControlWindow
                float r10 = (float) r10
                r12 = 1056964608(0x3f000000, float:0.5)
                float r10 = r10 * r12
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 < 0) goto L_0x00c4
                io.grpc.okhttp.OkHttpServerTransport r9 = io.grpc.okhttp.OkHttpServerTransport.this
                java.lang.Object r9 = r9.lock
                monitor-enter(r9)
                io.grpc.okhttp.OkHttpServerTransport r10 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x00c0 }
                io.grpc.okhttp.ExceptionHandlingFrameWriter r10 = r10.frameWriter     // Catch:{ all -> 0x00c0 }
                int r12 = r8.connectionUnacknowledgedBytesRead     // Catch:{ all -> 0x00c0 }
                long r0 = (long) r12     // Catch:{ all -> 0x00c0 }
                r12 = 0
                r10.windowUpdate(r12, r0)     // Catch:{ all -> 0x00c0 }
                io.grpc.okhttp.OkHttpServerTransport r10 = io.grpc.okhttp.OkHttpServerTransport.this     // Catch:{ all -> 0x00c0 }
                io.grpc.okhttp.ExceptionHandlingFrameWriter r10 = r10.frameWriter     // Catch:{ all -> 0x00c0 }
                r10.flush()     // Catch:{ all -> 0x00c0 }
                monitor-exit(r9)     // Catch:{ all -> 0x00c0 }
                r8.connectionUnacknowledgedBytesRead = r12
                goto L_0x00c4
            L_0x00c0:
                r0 = move-exception
                r10 = r0
                monitor-exit(r9)     // Catch:{ all -> 0x00c0 }
                throw r10
            L_0x00c4:
                return
            L_0x00c5:
                r0 = move-exception
                r10 = r0
                monitor-exit(r9)     // Catch:{ all -> 0x00c5 }
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpServerTransport.FrameHandler.data(boolean, int, okio.BufferedSource, int, int):void");
        }

        public void rstStream(int streamId, ErrorCode errorCode) {
            this.frameLogger.logRstStream(OkHttpFrameLogger.Direction.INBOUND, streamId, errorCode);
            if (!ErrorCode.NO_ERROR.equals(errorCode) && !ErrorCode.CANCEL.equals(errorCode) && !ErrorCode.STREAM_CLOSED.equals(errorCode)) {
                OkHttpServerTransport.log.log(Level.INFO, "Received RST_STREAM: " + errorCode);
            }
            Status status = GrpcUtil.Http2Error.statusForCode((long) errorCode.httpCode).withDescription("RST_STREAM");
            synchronized (OkHttpServerTransport.this.lock) {
                StreamState stream = (StreamState) OkHttpServerTransport.this.streams.get(Integer.valueOf(streamId));
                if (stream != null) {
                    stream.inboundRstReceived(status);
                    OkHttpServerTransport.this.streamClosed(streamId, false);
                }
            }
        }

        public void settings(boolean clearPrevious, Settings settings) {
            this.frameLogger.logSettings(OkHttpFrameLogger.Direction.INBOUND, settings);
            synchronized (OkHttpServerTransport.this.lock) {
                boolean outboundWindowSizeIncreased = false;
                if (OkHttpSettingsUtil.isSet(settings, 7)) {
                    outboundWindowSizeIncreased = OkHttpServerTransport.this.outboundFlow.initialOutboundWindowSize(OkHttpSettingsUtil.get(settings, 7));
                }
                OkHttpServerTransport.this.frameWriter.ackSettings(settings);
                OkHttpServerTransport.this.frameWriter.flush();
                if (!this.receivedSettings) {
                    this.receivedSettings = true;
                    Attributes unused = OkHttpServerTransport.this.attributes = OkHttpServerTransport.this.listener.transportReady(OkHttpServerTransport.this.attributes);
                }
                if (outboundWindowSizeIncreased) {
                    OkHttpServerTransport.this.outboundFlow.writeStreams();
                }
            }
        }

        public void ping(boolean ack, int payload1, int payload2) {
            if (!OkHttpServerTransport.this.keepAliveEnforcer.pingAcceptable()) {
                OkHttpServerTransport.this.abruptShutdown(ErrorCode.ENHANCE_YOUR_CALM, "too_many_pings", Status.RESOURCE_EXHAUSTED.withDescription("Too many pings from client"), false);
                return;
            }
            long payload = (((long) payload1) << 32) | (((long) payload2) & 4294967295L);
            if (!ack) {
                this.frameLogger.logPing(OkHttpFrameLogger.Direction.INBOUND, payload);
                synchronized (OkHttpServerTransport.this.lock) {
                    OkHttpServerTransport.this.frameWriter.ping(true, payload1, payload2);
                    OkHttpServerTransport.this.frameWriter.flush();
                }
                return;
            }
            this.frameLogger.logPingAck(OkHttpFrameLogger.Direction.INBOUND, payload);
            if (57005 != payload) {
                if (4369 == payload) {
                    OkHttpServerTransport.this.triggerGracefulSecondGoaway();
                } else {
                    OkHttpServerTransport.log.log(Level.INFO, "Received unexpected ping ack: " + payload);
                }
            }
        }

        public void ackSettings() {
        }

        public void goAway(int lastGoodStreamId, ErrorCode errorCode, ByteString debugData) {
            this.frameLogger.logGoAway(OkHttpFrameLogger.Direction.INBOUND, lastGoodStreamId, errorCode, debugData);
            Status status = GrpcUtil.Http2Error.statusForCode((long) errorCode.httpCode).withDescription(String.format("Received GOAWAY: %s '%s'", new Object[]{errorCode, debugData.utf8()}));
            if (!ErrorCode.NO_ERROR.equals(errorCode)) {
                OkHttpServerTransport.log.log(Level.WARNING, "Received GOAWAY: {0} {1}", new Object[]{errorCode, debugData.utf8()});
            }
            synchronized (OkHttpServerTransport.this.lock) {
                Status unused = OkHttpServerTransport.this.goAwayStatus = status;
            }
        }

        public void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) throws IOException {
            this.frameLogger.logPushPromise(OkHttpFrameLogger.Direction.INBOUND, streamId, promisedStreamId, requestHeaders);
            connectionError(ErrorCode.PROTOCOL_ERROR, "PUSH_PROMISE only allowed on peer-initiated streams. RFC7540 section 6.6");
        }

        public void windowUpdate(int streamId, long delta) {
            this.frameLogger.logWindowsUpdate(OkHttpFrameLogger.Direction.INBOUND, streamId, delta);
            synchronized (OkHttpServerTransport.this.lock) {
                if (streamId == 0) {
                    OkHttpServerTransport.this.outboundFlow.windowUpdate((OutboundFlowController.StreamState) null, (int) delta);
                } else {
                    StreamState stream = (StreamState) OkHttpServerTransport.this.streams.get(Integer.valueOf(streamId));
                    if (stream != null) {
                        OkHttpServerTransport.this.outboundFlow.windowUpdate(stream.getOutboundFlowState(), (int) delta);
                    }
                }
            }
        }

        public void priority(int streamId, int streamDependency, int weight, boolean exclusive) {
            this.frameLogger.logPriority(OkHttpFrameLogger.Direction.INBOUND, streamId, streamDependency, weight, exclusive);
        }

        public void alternateService(int streamId, String origin, ByteString protocol, String host, int port, long maxAge) {
        }

        private void connectionError(ErrorCode errorCode, String moreDetail) {
            OkHttpServerTransport.this.abruptShutdown(errorCode, moreDetail, GrpcUtil.Http2Error.statusForCode((long) errorCode.httpCode).withDescription(String.format("HTTP2 connection error: %s '%s'", new Object[]{errorCode, moreDetail})), false);
        }

        private void streamError(int streamId, ErrorCode errorCode, String reason) {
            if (errorCode == ErrorCode.PROTOCOL_ERROR) {
                OkHttpServerTransport.log.log(Level.FINE, "Responding with RST_STREAM {0}: {1}", new Object[]{errorCode, reason});
            }
            synchronized (OkHttpServerTransport.this.lock) {
                OkHttpServerTransport.this.frameWriter.rstStream(streamId, errorCode);
                OkHttpServerTransport.this.frameWriter.flush();
                StreamState stream = (StreamState) OkHttpServerTransport.this.streams.get(Integer.valueOf(streamId));
                if (stream != null) {
                    stream.transportReportStatus(Status.INTERNAL.withDescription(String.format("Responded with RST_STREAM %s: %s", new Object[]{errorCode, reason})));
                    OkHttpServerTransport.this.streamClosed(streamId, false);
                }
            }
        }

        private void respondWithHttpError(int streamId, boolean inFinished, int httpCode, Status.Code statusCode, String msg) {
            Metadata metadata = new Metadata();
            metadata.put(InternalStatus.CODE_KEY, statusCode.toStatus());
            metadata.put(InternalStatus.MESSAGE_KEY, msg);
            List<Header> headers = Headers.createHttpResponseHeaders(httpCode, "text/plain; charset=utf-8", metadata);
            Buffer data = new Buffer().writeUtf8(msg);
            synchronized (OkHttpServerTransport.this.lock) {
                Http2ErrorStreamState stream = new Http2ErrorStreamState(streamId, OkHttpServerTransport.this.lock, OkHttpServerTransport.this.outboundFlow, OkHttpServerTransport.this.config.flowControlWindow);
                if (OkHttpServerTransport.this.streams.isEmpty()) {
                    OkHttpServerTransport.this.keepAliveEnforcer.onTransportActive();
                    if (OkHttpServerTransport.this.maxConnectionIdleManager != null) {
                        OkHttpServerTransport.this.maxConnectionIdleManager.onTransportActive();
                    }
                }
                OkHttpServerTransport.this.streams.put(Integer.valueOf(streamId), stream);
                if (inFinished) {
                    stream.inboundDataReceived(new Buffer(), 0, 0, true);
                }
                OkHttpServerTransport.this.frameWriter.headers(streamId, headers);
                OkHttpServerTransport.this.outboundFlow.data(true, stream.getOutboundFlowState(), data, true);
                OkHttpServerTransport.this.outboundFlow.notifyWhenNoPendingData(stream.getOutboundFlowState(), new OkHttpServerTransport$FrameHandler$$ExternalSyntheticLambda0(this, stream));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: rstOkAtEndOfHttpError */
        public void m1942lambda$respondWithHttpError$0$iogrpcokhttpOkHttpServerTransport$FrameHandler(Http2ErrorStreamState stream) {
            synchronized (OkHttpServerTransport.this.lock) {
                if (!stream.hasReceivedEndOfStream()) {
                    OkHttpServerTransport.this.frameWriter.rstStream(stream.streamId, ErrorCode.NO_ERROR);
                }
                OkHttpServerTransport.this.streamClosed(stream.streamId, true);
            }
        }

        private void respondWithGrpcError(int streamId, boolean inFinished, Status.Code statusCode, String msg) {
            Metadata metadata = new Metadata();
            metadata.put(InternalStatus.CODE_KEY, statusCode.toStatus());
            metadata.put(InternalStatus.MESSAGE_KEY, msg);
            List<Header> headers = Headers.createResponseTrailers(metadata, false);
            synchronized (OkHttpServerTransport.this.lock) {
                OkHttpServerTransport.this.frameWriter.synReply(true, streamId, headers);
                if (!inFinished) {
                    OkHttpServerTransport.this.frameWriter.rstStream(streamId, ErrorCode.NO_ERROR);
                }
                OkHttpServerTransport.this.frameWriter.flush();
            }
        }
    }

    private final class KeepAlivePinger implements KeepAliveManager.KeepAlivePinger {
        private KeepAlivePinger() {
        }

        public void ping() {
            synchronized (OkHttpServerTransport.this.lock) {
                OkHttpServerTransport.this.frameWriter.ping(false, 0, OkHttpServerTransport.KEEPALIVE_PING);
                OkHttpServerTransport.this.frameWriter.flush();
            }
            OkHttpServerTransport.this.tracer.reportKeepAliveSent();
        }

        public void onPingTimeout() {
            synchronized (OkHttpServerTransport.this.lock) {
                Status unused = OkHttpServerTransport.this.goAwayStatus = Status.UNAVAILABLE.withDescription("Keepalive failed. Considering connection dead");
                GrpcUtil.closeQuietly((Closeable) OkHttpServerTransport.this.socket);
            }
        }
    }

    static class Http2ErrorStreamState implements StreamState, OutboundFlowController.Stream {
        private final Object lock;
        private final OutboundFlowController.StreamState outboundFlowState;
        private boolean receivedEndOfStream;
        /* access modifiers changed from: private */
        public final int streamId;
        private int window;

        Http2ErrorStreamState(int streamId2, Object lock2, OutboundFlowController outboundFlow, int initialWindowSize) {
            this.streamId = streamId2;
            this.lock = lock2;
            this.outboundFlowState = outboundFlow.createState(this, streamId2);
            this.window = initialWindowSize;
        }

        public void onSentBytes(int frameBytes) {
        }

        public void inboundDataReceived(Buffer frame, int dataLength, int paddingLength, boolean endOfStream) {
            synchronized (this.lock) {
                if (endOfStream) {
                    this.receivedEndOfStream = true;
                }
                this.window -= dataLength + paddingLength;
                try {
                    frame.skip(frame.size());
                } catch (IOException ex) {
                    throw new AssertionError(ex);
                }
            }
        }

        public boolean hasReceivedEndOfStream() {
            boolean z;
            synchronized (this.lock) {
                z = this.receivedEndOfStream;
            }
            return z;
        }

        public int inboundWindowAvailable() {
            int i;
            synchronized (this.lock) {
                i = this.window;
            }
            return i;
        }

        public void transportReportStatus(Status status) {
        }

        public void inboundRstReceived(Status status) {
        }

        public OutboundFlowController.StreamState getOutboundFlowState() {
            OutboundFlowController.StreamState streamState;
            synchronized (this.lock) {
                streamState = this.outboundFlowState;
            }
            return streamState;
        }
    }
}
