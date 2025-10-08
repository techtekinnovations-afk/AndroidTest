package io.grpc.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ChannelCredentials;
import io.grpc.ChannelLogger;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ClientStreamTracer;
import io.grpc.ClientTransportFilter;
import io.grpc.CompressorRegistry;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.Context;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ForwardingChannelBuilder2;
import io.grpc.ForwardingClientCall;
import io.grpc.Grpc;
import io.grpc.InternalChannelz;
import io.grpc.InternalConfigSelector;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.InternalWithLogId;
import io.grpc.LoadBalancer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.NameResolverRegistry;
import io.grpc.ProxyDetector;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.AutoConfiguredLoadBalancerFactory;
import io.grpc.internal.BackoffPolicy;
import io.grpc.internal.CallTracer;
import io.grpc.internal.ClientCallImpl;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ExponentialBackoffPolicy;
import io.grpc.internal.InternalSubchannel;
import io.grpc.internal.ManagedChannelImplBuilder;
import io.grpc.internal.ManagedChannelServiceConfig;
import io.grpc.internal.ManagedClientTransport;
import io.grpc.internal.RetriableStream;
import io.grpc.internal.RetryingNameResolver;
import java.lang.Thread;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

final class ManagedChannelImpl extends ManagedChannel implements InternalInstrumented<InternalChannelz.ChannelStats> {
    /* access modifiers changed from: private */
    public static final ManagedChannelServiceConfig EMPTY_SERVICE_CONFIG = ManagedChannelServiceConfig.empty();
    static final long IDLE_TIMEOUT_MILLIS_DISABLE = -1;
    /* access modifiers changed from: private */
    public static final InternalConfigSelector INITIAL_PENDING_SELECTOR = new InternalConfigSelector() {
        public InternalConfigSelector.Result selectConfig(LoadBalancer.PickSubchannelArgs args) {
            throw new IllegalStateException("Resolution is pending");
        }
    };
    /* access modifiers changed from: private */
    public static final ClientCall<Object, Object> NOOP_CALL = new ClientCall<Object, Object>() {
        public void start(ClientCall.Listener<Object> listener, Metadata headers) {
        }

        public void request(int numMessages) {
        }

        public void cancel(String message, Throwable cause) {
        }

        public void halfClose() {
        }

        public void sendMessage(Object message) {
        }

        public boolean isReady() {
            return false;
        }
    };
    static final Status SHUTDOWN_NOW_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdownNow invoked");
    static final Status SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Channel shutdown invoked");
    static final long SUBCHANNEL_SHUTDOWN_DELAY_SECONDS = 5;
    static final Status SUBCHANNEL_SHUTDOWN_STATUS = Status.UNAVAILABLE.withDescription("Subchannel shutdown invoked");
    static final Pattern URI_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+.-]*:/.*");
    static final Logger logger = Logger.getLogger(ManagedChannelImpl.class.getName());
    /* access modifiers changed from: private */
    @Nullable
    public final String authorityOverride;
    /* access modifiers changed from: private */
    public final BackoffPolicy.Provider backoffPolicyProvider;
    /* access modifiers changed from: private */
    public final ExecutorHolder balancerRpcExecutorHolder;
    /* access modifiers changed from: private */
    public final ObjectPool<? extends Executor> balancerRpcExecutorPool;
    /* access modifiers changed from: private */
    public final CallTracer.Factory callTracerFactory;
    /* access modifiers changed from: private */
    public final long channelBufferLimit;
    /* access modifiers changed from: private */
    public final RetriableStream.ChannelBufferMeter channelBufferUsed = new RetriableStream.ChannelBufferMeter();
    /* access modifiers changed from: private */
    public final CallTracer channelCallTracer;
    /* access modifiers changed from: private */
    public final ChannelLogger channelLogger;
    /* access modifiers changed from: private */
    public final ConnectivityStateManager channelStateManager = new ConnectivityStateManager();
    /* access modifiers changed from: private */
    public final ChannelTracer channelTracer;
    /* access modifiers changed from: private */
    public final InternalChannelz channelz;
    /* access modifiers changed from: private */
    public final CompressorRegistry compressorRegistry;
    /* access modifiers changed from: private */
    public final DecompressorRegistry decompressorRegistry;
    /* access modifiers changed from: private */
    @Nullable
    public final ManagedChannelServiceConfig defaultServiceConfig;
    /* access modifiers changed from: private */
    public final DelayedClientTransport delayedTransport;
    private final ManagedClientTransport.Listener delayedTransportListener = new DelayedTransportListener();
    /* access modifiers changed from: private */
    public final Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    /* access modifiers changed from: private */
    public boolean fullStreamDecompression;
    private final long idleTimeoutMillis;
    private final Rescheduler idleTimer;
    final InUseStateAggregator<Object> inUseStateAggregator = new IdleModeStateAggregator();
    private final Channel interceptorChannel;
    /* access modifiers changed from: private */
    public ResolutionState lastResolutionState = ResolutionState.NO_RESOLUTION;
    /* access modifiers changed from: private */
    public ManagedChannelServiceConfig lastServiceConfig = EMPTY_SERVICE_CONFIG;
    /* access modifiers changed from: private */
    @Nullable
    public LbHelperImpl lbHelper;
    private final AutoConfiguredLoadBalancerFactory loadBalancerFactory;
    private final InternalLogId logId;
    /* access modifiers changed from: private */
    public final boolean lookUpServiceConfig;
    /* access modifiers changed from: private */
    public final int maxTraceEvents;
    /* access modifiers changed from: private */
    public NameResolver nameResolver;
    /* access modifiers changed from: private */
    public final NameResolver.Args nameResolverArgs;
    /* access modifiers changed from: private */
    public final NameResolverRegistry nameResolverRegistry;
    /* access modifiers changed from: private */
    public boolean nameResolverStarted;
    /* access modifiers changed from: private */
    public final ExecutorHolder offloadExecutorHolder;
    /* access modifiers changed from: private */
    public final Set<OobChannel> oobChannels = new HashSet(1, 0.75f);
    /* access modifiers changed from: private */
    public final ClientTransportFactory oobTransportFactory;
    /* access modifiers changed from: private */
    @Nullable
    public final ChannelCredentials originalChannelCreds;
    /* access modifiers changed from: private */
    public final ClientTransportFactory originalTransportFactory;
    private boolean panicMode;
    /* access modifiers changed from: private */
    @Nullable
    public Collection<RealChannel.PendingCall<?, ?>> pendingCalls;
    /* access modifiers changed from: private */
    public final Object pendingCallsInUseObject = new Object();
    /* access modifiers changed from: private */
    public final long perRpcBufferLimit;
    /* access modifiers changed from: private */
    public final RealChannel realChannel;
    /* access modifiers changed from: private */
    public final boolean retryEnabled;
    /* access modifiers changed from: private */
    public final RestrictedScheduledExecutor scheduledExecutor;
    /* access modifiers changed from: private */
    public boolean serviceConfigUpdated = false;
    /* access modifiers changed from: private */
    public final AtomicBoolean shutdown = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public boolean shutdownNowed;
    /* access modifiers changed from: private */
    public final Supplier<Stopwatch> stopwatchSupplier;
    /* access modifiers changed from: private */
    @Nullable
    public volatile LoadBalancer.SubchannelPicker subchannelPicker;
    /* access modifiers changed from: private */
    public final Set<InternalSubchannel> subchannels = new HashSet(16, 0.75f);
    final SynchronizationContext syncContext = new SynchronizationContext(new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread t, Throwable e) {
            ManagedChannelImpl.logger.log(Level.SEVERE, "[" + ManagedChannelImpl.this.getLogId() + "] Uncaught exception in the SynchronizationContext. Panic!", e);
            ManagedChannelImpl.this.panic(e);
        }
    });
    /* access modifiers changed from: private */
    public final String target;
    /* access modifiers changed from: private */
    public volatile boolean terminated;
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);
    /* access modifiers changed from: private */
    public boolean terminating;
    /* access modifiers changed from: private */
    public final Deadline.Ticker ticker = Deadline.getSystemTicker();
    /* access modifiers changed from: private */
    public final TimeProvider timeProvider;
    /* access modifiers changed from: private */
    public final ClientTransportFactory transportFactory;
    /* access modifiers changed from: private */
    public final List<ClientTransportFilter> transportFilters;
    /* access modifiers changed from: private */
    public final ChannelStreamProvider transportProvider = new ChannelStreamProvider();
    /* access modifiers changed from: private */
    public final UncommittedRetriableStreamsRegistry uncommittedRetriableStreamsRegistry = new UncommittedRetriableStreamsRegistry();
    /* access modifiers changed from: private */
    @Nullable
    public final String userAgent;

    enum ResolutionState {
        NO_RESOLUTION,
        SUCCESS,
        ERROR
    }

    /* access modifiers changed from: private */
    public void maybeShutdownNowSubchannels() {
        if (this.shutdownNowed) {
            for (InternalSubchannel subchannel : this.subchannels) {
                subchannel.shutdownNow(SHUTDOWN_NOW_STATUS);
            }
            for (OobChannel oobChannel : this.oobChannels) {
                oobChannel.getInternalSubchannel().shutdownNow(SHUTDOWN_NOW_STATUS);
            }
        }
    }

    public ListenableFuture<InternalChannelz.ChannelStats> getStats() {
        final SettableFuture<InternalChannelz.ChannelStats> ret = SettableFuture.create();
        this.syncContext.execute(new Runnable() {
            public void run() {
                InternalChannelz.ChannelStats.Builder builder = new InternalChannelz.ChannelStats.Builder();
                ManagedChannelImpl.this.channelCallTracer.updateBuilder(builder);
                ManagedChannelImpl.this.channelTracer.updateBuilder(builder);
                builder.setTarget(ManagedChannelImpl.this.target).setState(ManagedChannelImpl.this.channelStateManager.getState());
                List<InternalWithLogId> children = new ArrayList<>();
                children.addAll(ManagedChannelImpl.this.subchannels);
                children.addAll(ManagedChannelImpl.this.oobChannels);
                builder.setSubchannels(children);
                ret.set(builder.build());
            }
        });
        return ret;
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    private class IdleModeTimer implements Runnable {
        private IdleModeTimer() {
        }

        public void run() {
            if (ManagedChannelImpl.this.lbHelper != null) {
                ManagedChannelImpl.this.enterIdleMode();
            }
        }
    }

    /* access modifiers changed from: private */
    public void shutdownNameResolverAndLoadBalancer(boolean channelIsActive) {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (channelIsActive) {
            Preconditions.checkState(this.nameResolverStarted, "nameResolver is not started");
            Preconditions.checkState(this.lbHelper != null, "lbHelper is null");
        }
        if (this.nameResolver != null) {
            this.nameResolver.shutdown();
            this.nameResolverStarted = false;
            if (channelIsActive) {
                this.nameResolver = getNameResolver(this.target, this.authorityOverride, this.nameResolverRegistry, this.nameResolverArgs, this.transportFactory.getSupportedSocketAddressTypes());
            } else {
                this.nameResolver = null;
            }
        }
        if (this.lbHelper != null) {
            this.lbHelper.lb.shutdown();
            this.lbHelper = null;
        }
        this.subchannelPicker = null;
    }

    /* access modifiers changed from: package-private */
    public void exitIdleMode() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (!this.shutdown.get() && !this.panicMode) {
            if (this.inUseStateAggregator.isInUse()) {
                cancelIdleTimer(false);
            } else {
                rescheduleIdleTimer();
            }
            if (this.lbHelper == null) {
                this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Exiting idle mode");
                LbHelperImpl lbHelper2 = new LbHelperImpl();
                lbHelper2.lb = this.loadBalancerFactory.newLoadBalancer(lbHelper2);
                this.lbHelper = lbHelper2;
                this.nameResolver.start((NameResolver.Listener2) new NameResolverListener(lbHelper2, this.nameResolver));
                this.nameResolverStarted = true;
            }
        }
    }

    /* access modifiers changed from: private */
    public void enterIdleMode() {
        shutdownNameResolverAndLoadBalancer(true);
        this.delayedTransport.reprocess((LoadBalancer.SubchannelPicker) null);
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering IDLE state");
        this.channelStateManager.gotoState(ConnectivityState.IDLE);
        if (this.inUseStateAggregator.anyObjectInUse(this.pendingCallsInUseObject, this.delayedTransport)) {
            exitIdleMode();
        }
    }

    /* access modifiers changed from: private */
    public void cancelIdleTimer(boolean permanent) {
        this.idleTimer.cancel(permanent);
    }

    /* access modifiers changed from: private */
    public void rescheduleIdleTimer() {
        if (this.idleTimeoutMillis != -1) {
            this.idleTimer.reschedule(this.idleTimeoutMillis, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: private */
    public void refreshNameResolution() {
        this.syncContext.throwIfNotInThisSynchronizationContext();
        if (this.nameResolverStarted) {
            this.nameResolver.refresh();
        }
    }

    private final class ChannelStreamProvider implements ClientCallImpl.ClientStreamProvider {
        volatile RetriableStream.Throttle throttle;

        private ChannelStreamProvider() {
        }

        /* access modifiers changed from: private */
        public ClientTransport getTransport(LoadBalancer.PickSubchannelArgs args) {
            LoadBalancer.SubchannelPicker pickerCopy = ManagedChannelImpl.this.subchannelPicker;
            if (ManagedChannelImpl.this.shutdown.get()) {
                return ManagedChannelImpl.this.delayedTransport;
            }
            if (pickerCopy == null) {
                ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                    public void run() {
                        ManagedChannelImpl.this.exitIdleMode();
                    }
                });
                return ManagedChannelImpl.this.delayedTransport;
            }
            ClientTransport transport = GrpcUtil.getTransportFromPickResult(pickerCopy.pickSubchannel(args), args.getCallOptions().isWaitForReady());
            if (transport != null) {
                return transport;
            }
            return ManagedChannelImpl.this.delayedTransport;
        }

        public ClientStream newStream(MethodDescriptor<?, ?> method, CallOptions callOptions, Metadata headers, Context context) {
            if (!ManagedChannelImpl.this.retryEnabled) {
                ClientTransport transport = getTransport(new PickSubchannelArgsImpl(method, headers, callOptions));
                Context origContext = context.attach();
                try {
                    return transport.newStream(method, headers, callOptions, GrpcUtil.getClientStreamTracers(callOptions, headers, 0, false));
                } finally {
                    context.detach(origContext);
                }
            } else {
                ManagedChannelServiceConfig.MethodInfo methodInfo = (ManagedChannelServiceConfig.MethodInfo) callOptions.getOption(ManagedChannelServiceConfig.MethodInfo.KEY);
                HedgingPolicy hedgingPolicy = null;
                RetryPolicy retryPolicy = methodInfo == null ? null : methodInfo.retryPolicy;
                if (methodInfo != null) {
                    hedgingPolicy = methodInfo.hedgingPolicy;
                }
                return new RetriableStream<ReqT>(this, method, headers, callOptions, retryPolicy, hedgingPolicy, context) {
                    final /* synthetic */ ChannelStreamProvider this$1;
                    final /* synthetic */ CallOptions val$callOptions;
                    final /* synthetic */ Context val$context;
                    final /* synthetic */ Metadata val$headers;
                    final /* synthetic */ HedgingPolicy val$hedgingPolicy;
                    final /* synthetic */ MethodDescriptor val$method;
                    final /* synthetic */ RetryPolicy val$retryPolicy;

                    {
                        ChannelStreamProvider channelStreamProvider = this$1;
                        CallOptions callOptions = r20;
                        this.this$1 = channelStreamProvider;
                        MethodDescriptor methodDescriptor = r18;
                        this.val$method = methodDescriptor;
                        Metadata metadata = r19;
                        this.val$headers = metadata;
                        this.val$callOptions = callOptions;
                        RetryPolicy retryPolicy = r21;
                        this.val$retryPolicy = retryPolicy;
                        HedgingPolicy hedgingPolicy = r22;
                        this.val$hedgingPolicy = hedgingPolicy;
                        this.val$context = r23;
                        RetriableStream.ChannelBufferMeter access$1600 = ManagedChannelImpl.this.channelBufferUsed;
                        long access$1700 = ManagedChannelImpl.this.perRpcBufferLimit;
                        long access$1800 = ManagedChannelImpl.this.channelBufferLimit;
                        Executor access$1900 = ManagedChannelImpl.this.getCallExecutor(callOptions);
                    }

                    /* access modifiers changed from: package-private */
                    public Status prestart() {
                        return ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.add(this);
                    }

                    /* access modifiers changed from: package-private */
                    public void postCommit() {
                        ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.remove(this);
                    }

                    /* access modifiers changed from: package-private */
                    public ClientStream newSubstream(Metadata newHeaders, ClientStreamTracer.Factory factory, int previousAttempts, boolean isTransparentRetry) {
                        CallOptions newOptions = this.val$callOptions.withStreamTracerFactory(factory);
                        ClientStreamTracer[] tracers = GrpcUtil.getClientStreamTracers(newOptions, newHeaders, previousAttempts, isTransparentRetry);
                        ClientTransport transport = this.this$1.getTransport(new PickSubchannelArgsImpl(this.val$method, newHeaders, newOptions));
                        Context origContext = this.val$context.attach();
                        try {
                            return transport.newStream(this.val$method, newHeaders, newOptions, tracers);
                        } finally {
                            this.val$context.detach(origContext);
                        }
                    }
                };
            }
        }
    }

    ManagedChannelImpl(ManagedChannelImplBuilder builder, ClientTransportFactory clientTransportFactory, BackoffPolicy.Provider backoffPolicyProvider2, ObjectPool<? extends Executor> balancerRpcExecutorPool2, Supplier<Stopwatch> stopwatchSupplier2, List<ClientInterceptor> interceptors, TimeProvider timeProvider2) {
        ManagedChannelImplBuilder managedChannelImplBuilder = builder;
        ClientTransportFactory clientTransportFactory2 = clientTransportFactory;
        ObjectPool<? extends Executor> objectPool = balancerRpcExecutorPool2;
        final TimeProvider timeProvider3 = timeProvider2;
        this.target = (String) Preconditions.checkNotNull(managedChannelImplBuilder.target, TypedValues.AttributesType.S_TARGET);
        this.logId = InternalLogId.allocate("Channel", this.target);
        this.timeProvider = (TimeProvider) Preconditions.checkNotNull(timeProvider3, "timeProvider");
        this.executorPool = (ObjectPool) Preconditions.checkNotNull(managedChannelImplBuilder.executorPool, "executorPool");
        this.executor = (Executor) Preconditions.checkNotNull((Executor) this.executorPool.getObject(), "executor");
        this.originalChannelCreds = managedChannelImplBuilder.channelCredentials;
        this.originalTransportFactory = clientTransportFactory2;
        this.offloadExecutorHolder = new ExecutorHolder((ObjectPool) Preconditions.checkNotNull(managedChannelImplBuilder.offloadExecutorPool, "offloadExecutorPool"));
        this.transportFactory = new CallCredentialsApplyingTransportFactory(clientTransportFactory2, managedChannelImplBuilder.callCredentials, this.offloadExecutorHolder);
        this.oobTransportFactory = new CallCredentialsApplyingTransportFactory(clientTransportFactory2, (CallCredentials) null, this.offloadExecutorHolder);
        this.scheduledExecutor = new RestrictedScheduledExecutor(this.transportFactory.getScheduledExecutorService());
        this.maxTraceEvents = managedChannelImplBuilder.maxTraceEvents;
        this.channelTracer = new ChannelTracer(this.logId, managedChannelImplBuilder.maxTraceEvents, timeProvider3.currentTimeNanos(), "Channel for '" + this.target + "'");
        this.channelLogger = new ChannelLoggerImpl(this.channelTracer, timeProvider3);
        ProxyDetector proxyDetector = managedChannelImplBuilder.proxyDetector != null ? managedChannelImplBuilder.proxyDetector : GrpcUtil.DEFAULT_PROXY_DETECTOR;
        this.retryEnabled = managedChannelImplBuilder.retryEnabled;
        this.loadBalancerFactory = new AutoConfiguredLoadBalancerFactory(managedChannelImplBuilder.defaultLbPolicy);
        this.nameResolverRegistry = managedChannelImplBuilder.nameResolverRegistry;
        ScParser serviceConfigParser = new ScParser(this.retryEnabled, managedChannelImplBuilder.maxRetryAttempts, managedChannelImplBuilder.maxHedgedAttempts, this.loadBalancerFactory);
        this.authorityOverride = managedChannelImplBuilder.authorityOverride;
        this.nameResolverArgs = NameResolver.Args.newBuilder().setDefaultPort(managedChannelImplBuilder.getDefaultPort()).setProxyDetector(proxyDetector).setSynchronizationContext(this.syncContext).setScheduledExecutorService(this.scheduledExecutor).setServiceConfigParser(serviceConfigParser).setChannelLogger(this.channelLogger).setOffloadExecutor(this.offloadExecutorHolder).setOverrideAuthority(this.authorityOverride).build();
        this.nameResolver = getNameResolver(this.target, this.authorityOverride, this.nameResolverRegistry, this.nameResolverArgs, this.transportFactory.getSupportedSocketAddressTypes());
        this.balancerRpcExecutorPool = (ObjectPool) Preconditions.checkNotNull(objectPool, "balancerRpcExecutorPool");
        this.balancerRpcExecutorHolder = new ExecutorHolder(objectPool);
        this.delayedTransport = new DelayedClientTransport(this.executor, this.syncContext);
        this.delayedTransport.start(this.delayedTransportListener);
        this.backoffPolicyProvider = backoffPolicyProvider2;
        if (managedChannelImplBuilder.defaultServiceConfig != null) {
            NameResolver.ConfigOrError parsedDefaultServiceConfig = serviceConfigParser.parseServiceConfig(managedChannelImplBuilder.defaultServiceConfig);
            Preconditions.checkState(parsedDefaultServiceConfig.getError() == null, "Default config is invalid: %s", (Object) parsedDefaultServiceConfig.getError());
            this.defaultServiceConfig = (ManagedChannelServiceConfig) parsedDefaultServiceConfig.getConfig();
            this.lastServiceConfig = this.defaultServiceConfig;
        } else {
            this.defaultServiceConfig = null;
        }
        this.lookUpServiceConfig = managedChannelImplBuilder.lookUpServiceConfig;
        this.realChannel = new RealChannel(this.nameResolver.getServiceAuthority());
        Channel channel = this.realChannel;
        this.interceptorChannel = ClientInterceptors.intercept(managedChannelImplBuilder.binlog != null ? managedChannelImplBuilder.binlog.wrapChannel(channel) : channel, (List<? extends ClientInterceptor>) interceptors);
        this.transportFilters = new ArrayList(managedChannelImplBuilder.transportFilters);
        Supplier<Stopwatch> supplier = stopwatchSupplier2;
        this.stopwatchSupplier = (Supplier) Preconditions.checkNotNull(supplier, "stopwatchSupplier");
        ScParser scParser = serviceConfigParser;
        if (managedChannelImplBuilder.idleTimeoutMillis == -1) {
            this.idleTimeoutMillis = managedChannelImplBuilder.idleTimeoutMillis;
        } else {
            Preconditions.checkArgument(managedChannelImplBuilder.idleTimeoutMillis >= ManagedChannelImplBuilder.IDLE_MODE_MIN_TIMEOUT_MILLIS, "invalid idleTimeoutMillis %s", managedChannelImplBuilder.idleTimeoutMillis);
            this.idleTimeoutMillis = managedChannelImplBuilder.idleTimeoutMillis;
        }
        this.idleTimer = new Rescheduler(new IdleModeTimer(), this.syncContext, this.transportFactory.getScheduledExecutorService(), supplier.get());
        this.fullStreamDecompression = managedChannelImplBuilder.fullStreamDecompression;
        this.decompressorRegistry = (DecompressorRegistry) Preconditions.checkNotNull(managedChannelImplBuilder.decompressorRegistry, "decompressorRegistry");
        this.compressorRegistry = (CompressorRegistry) Preconditions.checkNotNull(managedChannelImplBuilder.compressorRegistry, "compressorRegistry");
        this.userAgent = managedChannelImplBuilder.userAgent;
        this.channelBufferLimit = managedChannelImplBuilder.retryBufferSize;
        this.perRpcBufferLimit = managedChannelImplBuilder.perRpcBufferLimit;
        this.callTracerFactory = new CallTracer.Factory() {
            public CallTracer create() {
                return new CallTracer(timeProvider3);
            }
        };
        this.channelCallTracer = this.callTracerFactory.create();
        this.channelz = (InternalChannelz) Preconditions.checkNotNull(managedChannelImplBuilder.channelz);
        this.channelz.addRootChannel(this);
        if (!this.lookUpServiceConfig) {
            if (this.defaultServiceConfig != null) {
                this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config look-up disabled, using default service config");
            }
            this.serviceConfigUpdated = true;
        }
    }

    private static NameResolver getNameResolver(String target2, NameResolverRegistry nameResolverRegistry2, NameResolver.Args nameResolverArgs2, Collection<Class<? extends SocketAddress>> channelTransportSocketAddressTypes) {
        NameResolverProvider provider = null;
        URI targetUri = null;
        StringBuilder uriSyntaxErrors = new StringBuilder();
        try {
            targetUri = new URI(target2);
        } catch (URISyntaxException e) {
            uriSyntaxErrors.append(e.getMessage());
        }
        if (targetUri != null) {
            provider = nameResolverRegistry2.getProviderForScheme(targetUri.getScheme());
        }
        String str = "";
        if (provider == null && !URI_PATTERN.matcher(target2).matches()) {
            try {
                targetUri = new URI(nameResolverRegistry2.getDefaultScheme(), str, "/" + target2, (String) null);
                provider = nameResolverRegistry2.getProviderForScheme(targetUri.getScheme());
            } catch (URISyntaxException e2) {
                throw new IllegalArgumentException(e2);
            }
        }
        if (provider == null) {
            if (uriSyntaxErrors.length() > 0) {
                str = " (" + uriSyntaxErrors + ")";
            }
            throw new IllegalArgumentException(String.format("Could not find a NameResolverProvider for %s%s", new Object[]{target2, str}));
        } else if (channelTransportSocketAddressTypes == null || channelTransportSocketAddressTypes.containsAll(provider.getProducedSocketAddressTypes())) {
            NameResolver resolver = provider.newNameResolver(targetUri, nameResolverArgs2);
            if (resolver != null) {
                return resolver;
            }
            if (uriSyntaxErrors.length() > 0) {
                str = " (" + uriSyntaxErrors + ")";
            }
            throw new IllegalArgumentException(String.format("cannot create a NameResolver for %s%s", new Object[]{target2, str}));
        } else {
            throw new IllegalArgumentException(String.format("Address types of NameResolver '%s' for '%s' not supported by transport", new Object[]{targetUri.getScheme(), target2}));
        }
    }

    static NameResolver getNameResolver(String target2, @Nullable final String overrideAuthority, NameResolverRegistry nameResolverRegistry2, NameResolver.Args nameResolverArgs2, Collection<Class<? extends SocketAddress>> channelTransportSocketAddressTypes) {
        NameResolver usedNameResolver = new RetryingNameResolver(getNameResolver(target2, nameResolverRegistry2, nameResolverArgs2, channelTransportSocketAddressTypes), new BackoffPolicyRetryScheduler(new ExponentialBackoffPolicy.Provider(), nameResolverArgs2.getScheduledExecutorService(), nameResolverArgs2.getSynchronizationContext()), nameResolverArgs2.getSynchronizationContext());
        if (overrideAuthority == null) {
            return usedNameResolver;
        }
        return new ForwardingNameResolver(usedNameResolver) {
            public String getServiceAuthority() {
                return overrideAuthority;
            }
        };
    }

    /* access modifiers changed from: package-private */
    public InternalConfigSelector getConfigSelector() {
        return (InternalConfigSelector) this.realChannel.configSelector.get();
    }

    public ManagedChannelImpl shutdown() {
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "shutdown() called");
        if (!this.shutdown.compareAndSet(false, true)) {
            return this;
        }
        this.syncContext.execute(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering SHUTDOWN state");
                ManagedChannelImpl.this.channelStateManager.gotoState(ConnectivityState.SHUTDOWN);
            }
        });
        this.realChannel.shutdown();
        this.syncContext.execute(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.cancelIdleTimer(true);
            }
        });
        return this;
    }

    public ManagedChannelImpl shutdownNow() {
        this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "shutdownNow() called");
        shutdown();
        this.realChannel.shutdownNow();
        this.syncContext.execute(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdownNowed) {
                    boolean unused = ManagedChannelImpl.this.shutdownNowed = true;
                    ManagedChannelImpl.this.maybeShutdownNowSubchannels();
                }
            }
        });
        return this;
    }

    /* access modifiers changed from: package-private */
    public void panic(final Throwable t) {
        if (!this.panicMode) {
            this.panicMode = true;
            cancelIdleTimer(true);
            shutdownNameResolverAndLoadBalancer(false);
            updateSubchannelPicker(new LoadBalancer.SubchannelPicker() {
                private final LoadBalancer.PickResult panicPickResult = LoadBalancer.PickResult.withDrop(Status.INTERNAL.withDescription("Panic! This is a bug!").withCause(t));

                public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
                    return this.panicPickResult;
                }

                public String toString() {
                    return MoreObjects.toStringHelper((Class<?>) AnonymousClass1PanicSubchannelPicker.class).add("panicPickResult", (Object) this.panicPickResult).toString();
                }
            });
            this.realChannel.updateConfigSelector((InternalConfigSelector) null);
            this.channelLogger.log(ChannelLogger.ChannelLogLevel.ERROR, "PANIC! Entering TRANSIENT_FAILURE");
            this.channelStateManager.gotoState(ConnectivityState.TRANSIENT_FAILURE);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInPanicMode() {
        return this.panicMode;
    }

    /* access modifiers changed from: private */
    public void updateSubchannelPicker(LoadBalancer.SubchannelPicker newPicker) {
        this.subchannelPicker = newPicker;
        this.delayedTransport.reprocess(newPicker);
    }

    public boolean isShutdown() {
        return this.shutdown.get();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.terminatedLatch.await(timeout, unit);
    }

    public boolean isTerminated() {
        return this.terminated;
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions) {
        return this.interceptorChannel.newCall(method, callOptions);
    }

    public String authority() {
        return this.interceptorChannel.authority();
    }

    /* access modifiers changed from: private */
    public Executor getCallExecutor(CallOptions callOptions) {
        Executor executor2 = callOptions.getExecutor();
        if (executor2 == null) {
            return this.executor;
        }
        return executor2;
    }

    private class RealChannel extends Channel {
        /* access modifiers changed from: private */
        public final String authority;
        private final Channel clientCallImplChannel;
        /* access modifiers changed from: private */
        public final AtomicReference<InternalConfigSelector> configSelector;

        private RealChannel(String authority2) {
            this.configSelector = new AtomicReference<>(ManagedChannelImpl.INITIAL_PENDING_SELECTOR);
            this.clientCallImplChannel = new Channel() {
                public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> method, CallOptions callOptions) {
                    return new ClientCallImpl(method, ManagedChannelImpl.this.getCallExecutor(callOptions), callOptions, ManagedChannelImpl.this.transportProvider, ManagedChannelImpl.this.terminated ? null : ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.channelCallTracer, (InternalConfigSelector) null).setFullStreamDecompression(ManagedChannelImpl.this.fullStreamDecompression).setDecompressorRegistry(ManagedChannelImpl.this.decompressorRegistry).setCompressorRegistry(ManagedChannelImpl.this.compressorRegistry);
                }

                public String authority() {
                    return RealChannel.this.authority;
                }
            };
            this.authority = (String) Preconditions.checkNotNull(authority2, "authority");
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> newCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions) {
            if (this.configSelector.get() != ManagedChannelImpl.INITIAL_PENDING_SELECTOR) {
                return newClientCall(method, callOptions);
            }
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    ManagedChannelImpl.this.exitIdleMode();
                }
            });
            if (this.configSelector.get() != ManagedChannelImpl.INITIAL_PENDING_SELECTOR) {
                return newClientCall(method, callOptions);
            }
            if (ManagedChannelImpl.this.shutdown.get()) {
                return new ClientCall<ReqT, RespT>() {
                    public void start(ClientCall.Listener<RespT> responseListener, Metadata headers) {
                        responseListener.onClose(ManagedChannelImpl.SHUTDOWN_STATUS, new Metadata());
                    }

                    public void request(int numMessages) {
                    }

                    public void cancel(@Nullable String message, @Nullable Throwable cause) {
                    }

                    public void halfClose() {
                    }

                    public void sendMessage(ReqT reqt) {
                    }
                };
            }
            final PendingCall<ReqT, RespT> pendingCall = new PendingCall<>(Context.current(), method, callOptions);
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    if (RealChannel.this.configSelector.get() == ManagedChannelImpl.INITIAL_PENDING_SELECTOR) {
                        if (ManagedChannelImpl.this.pendingCalls == null) {
                            Collection unused = ManagedChannelImpl.this.pendingCalls = new LinkedHashSet();
                            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.pendingCallsInUseObject, true);
                        }
                        ManagedChannelImpl.this.pendingCalls.add(pendingCall);
                        return;
                    }
                    pendingCall.reprocess();
                }
            });
            return pendingCall;
        }

        /* access modifiers changed from: package-private */
        public void updateConfigSelector(@Nullable InternalConfigSelector config) {
            InternalConfigSelector prevConfig = this.configSelector.get();
            this.configSelector.set(config);
            if (prevConfig == ManagedChannelImpl.INITIAL_PENDING_SELECTOR && ManagedChannelImpl.this.pendingCalls != null) {
                for (PendingCall<?, ?> pendingCall : ManagedChannelImpl.this.pendingCalls) {
                    pendingCall.reprocess();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void onConfigError() {
            if (this.configSelector.get() == ManagedChannelImpl.INITIAL_PENDING_SELECTOR) {
                updateConfigSelector((InternalConfigSelector) null);
            }
        }

        /* access modifiers changed from: package-private */
        public void shutdown() {
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    if (ManagedChannelImpl.this.pendingCalls == null) {
                        if (RealChannel.this.configSelector.get() == ManagedChannelImpl.INITIAL_PENDING_SELECTOR) {
                            RealChannel.this.configSelector.set((Object) null);
                        }
                        ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.onShutdown(ManagedChannelImpl.SHUTDOWN_STATUS);
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void shutdownNow() {
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    if (RealChannel.this.configSelector.get() == ManagedChannelImpl.INITIAL_PENDING_SELECTOR) {
                        RealChannel.this.configSelector.set((Object) null);
                    }
                    if (ManagedChannelImpl.this.pendingCalls != null) {
                        for (PendingCall<?, ?> pendingCall : ManagedChannelImpl.this.pendingCalls) {
                            pendingCall.cancel("Channel is forcefully shutdown", (Throwable) null);
                        }
                    }
                    ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.onShutdownNow(ManagedChannelImpl.SHUTDOWN_NOW_STATUS);
                }
            });
        }

        public String authority() {
            return this.authority;
        }

        private final class PendingCall<ReqT, RespT> extends DelayedClientCall<ReqT, RespT> {
            private final long callCreationTime;
            final CallOptions callOptions;
            final Context context;
            final MethodDescriptor<ReqT, RespT> method;

            PendingCall(Context context2, MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions2) {
                super(ManagedChannelImpl.this.getCallExecutor(callOptions2), ManagedChannelImpl.this.scheduledExecutor, callOptions2.getDeadline());
                this.context = context2;
                this.method = method2;
                this.callOptions = callOptions2;
                this.callCreationTime = ManagedChannelImpl.this.ticker.nanoTime();
            }

            /* JADX INFO: finally extract failed */
            /* access modifiers changed from: package-private */
            public void reprocess() {
                Context previous = this.context.attach();
                try {
                    ClientCall<ReqT, RespT> realCall = RealChannel.this.newClientCall(this.method, this.callOptions.withOption(ClientStreamTracer.NAME_RESOLUTION_DELAYED, Long.valueOf(ManagedChannelImpl.this.ticker.nanoTime() - this.callCreationTime)));
                    this.context.detach(previous);
                    final Runnable toRun = setCall(realCall);
                    if (toRun == null) {
                        ManagedChannelImpl.this.syncContext.execute(new PendingCallRemoval());
                    } else {
                        ManagedChannelImpl.this.getCallExecutor(this.callOptions).execute(new Runnable() {
                            public void run() {
                                toRun.run();
                                ManagedChannelImpl.this.syncContext.execute(new PendingCallRemoval());
                            }
                        });
                    }
                } catch (Throwable th) {
                    this.context.detach(previous);
                    throw th;
                }
            }

            /* access modifiers changed from: protected */
            public void callCancelled() {
                super.callCancelled();
                ManagedChannelImpl.this.syncContext.execute(new PendingCallRemoval());
            }

            final class PendingCallRemoval implements Runnable {
                PendingCallRemoval() {
                }

                public void run() {
                    if (ManagedChannelImpl.this.pendingCalls != null) {
                        ManagedChannelImpl.this.pendingCalls.remove(PendingCall.this);
                        if (ManagedChannelImpl.this.pendingCalls.isEmpty()) {
                            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.pendingCallsInUseObject, false);
                            Collection unused = ManagedChannelImpl.this.pendingCalls = null;
                            if (ManagedChannelImpl.this.shutdown.get()) {
                                ManagedChannelImpl.this.uncommittedRetriableStreamsRegistry.onShutdown(ManagedChannelImpl.SHUTDOWN_STATUS);
                            }
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public <ReqT, RespT> ClientCall<ReqT, RespT> newClientCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions) {
            InternalConfigSelector selector = this.configSelector.get();
            if (selector == null) {
                return this.clientCallImplChannel.newCall(method, callOptions);
            }
            if (!(selector instanceof ManagedChannelServiceConfig.ServiceConfigConvertedSelector)) {
                return new ConfigSelectingClientCall(selector, this.clientCallImplChannel, ManagedChannelImpl.this.executor, method, callOptions);
            }
            ManagedChannelServiceConfig.MethodInfo methodInfo = ((ManagedChannelServiceConfig.ServiceConfigConvertedSelector) selector).config.getMethodConfig(method);
            if (methodInfo != null) {
                callOptions = callOptions.withOption(ManagedChannelServiceConfig.MethodInfo.KEY, methodInfo);
            }
            return this.clientCallImplChannel.newCall(method, callOptions);
        }
    }

    static final class ConfigSelectingClientCall<ReqT, RespT> extends ForwardingClientCall<ReqT, RespT> {
        private final Executor callExecutor;
        private CallOptions callOptions;
        private final Channel channel;
        private final InternalConfigSelector configSelector;
        /* access modifiers changed from: private */
        public final Context context;
        private ClientCall<ReqT, RespT> delegate;
        private final MethodDescriptor<ReqT, RespT> method;

        ConfigSelectingClientCall(InternalConfigSelector configSelector2, Channel channel2, Executor channelExecutor, MethodDescriptor<ReqT, RespT> method2, CallOptions callOptions2) {
            this.configSelector = configSelector2;
            this.channel = channel2;
            this.method = method2;
            this.callExecutor = callOptions2.getExecutor() == null ? channelExecutor : callOptions2.getExecutor();
            this.callOptions = callOptions2.withExecutor(this.callExecutor);
            this.context = Context.current();
        }

        /* access modifiers changed from: protected */
        public ClientCall<ReqT, RespT> delegate() {
            return this.delegate;
        }

        public void start(ClientCall.Listener<RespT> observer, Metadata headers) {
            InternalConfigSelector.Result result = this.configSelector.selectConfig(new PickSubchannelArgsImpl(this.method, headers, this.callOptions));
            Status status = result.getStatus();
            if (!status.isOk()) {
                executeCloseObserverInContext(observer, GrpcUtil.replaceInappropriateControlPlaneStatus(status));
                this.delegate = ManagedChannelImpl.NOOP_CALL;
                return;
            }
            ClientInterceptor interceptor = result.getInterceptor();
            ManagedChannelServiceConfig.MethodInfo methodInfo = ((ManagedChannelServiceConfig) result.getConfig()).getMethodConfig(this.method);
            if (methodInfo != null) {
                this.callOptions = this.callOptions.withOption(ManagedChannelServiceConfig.MethodInfo.KEY, methodInfo);
            }
            if (interceptor != null) {
                this.delegate = interceptor.interceptCall(this.method, this.callOptions, this.channel);
            } else {
                this.delegate = this.channel.newCall(this.method, this.callOptions);
            }
            this.delegate.start(observer, headers);
        }

        private void executeCloseObserverInContext(final ClientCall.Listener<RespT> observer, final Status status) {
            this.callExecutor.execute(new ContextRunnable() {
                public void runInContext() {
                    observer.onClose(status, new Metadata());
                }
            });
        }

        public void cancel(@Nullable String message, @Nullable Throwable cause) {
            if (this.delegate != null) {
                this.delegate.cancel(message, cause);
            }
        }
    }

    /* access modifiers changed from: private */
    public void maybeTerminateChannel() {
        if (!this.terminated && this.shutdown.get() && this.subchannels.isEmpty() && this.oobChannels.isEmpty()) {
            this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Terminated");
            this.channelz.removeRootChannel(this);
            this.executorPool.returnObject(this.executor);
            this.balancerRpcExecutorHolder.release();
            this.offloadExecutorHolder.release();
            this.transportFactory.close();
            this.terminated = true;
            this.terminatedLatch.countDown();
        }
    }

    /* access modifiers changed from: private */
    public void handleInternalSubchannelState(ConnectivityStateInfo newState) {
        if (newState.getState() == ConnectivityState.TRANSIENT_FAILURE || newState.getState() == ConnectivityState.IDLE) {
            refreshNameResolution();
        }
    }

    public ConnectivityState getState(boolean requestConnection) {
        ConnectivityState savedChannelState = this.channelStateManager.getState();
        if (requestConnection && savedChannelState == ConnectivityState.IDLE) {
            this.syncContext.execute(new Runnable() {
                public void run() {
                    ManagedChannelImpl.this.exitIdleMode();
                    if (ManagedChannelImpl.this.subchannelPicker != null) {
                        ManagedChannelImpl.this.subchannelPicker.requestConnection();
                    }
                    if (ManagedChannelImpl.this.lbHelper != null) {
                        ManagedChannelImpl.this.lbHelper.lb.requestConnection();
                    }
                }
            });
        }
        return savedChannelState;
    }

    public void notifyWhenStateChanged(final ConnectivityState source, final Runnable callback) {
        this.syncContext.execute(new Runnable() {
            public void run() {
                ManagedChannelImpl.this.channelStateManager.notifyWhenStateChanged(callback, ManagedChannelImpl.this.executor, source);
            }
        });
    }

    public void resetConnectBackoff() {
        this.syncContext.execute(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdown.get()) {
                    if (ManagedChannelImpl.this.nameResolverStarted) {
                        ManagedChannelImpl.this.refreshNameResolution();
                    }
                    for (InternalSubchannel subchannel : ManagedChannelImpl.this.subchannels) {
                        subchannel.resetConnectBackoff();
                    }
                    for (OobChannel oobChannel : ManagedChannelImpl.this.oobChannels) {
                        oobChannel.resetConnectBackoff();
                    }
                }
            }
        });
    }

    public void enterIdle() {
        this.syncContext.execute(new Runnable() {
            public void run() {
                if (!ManagedChannelImpl.this.shutdown.get() && ManagedChannelImpl.this.lbHelper != null) {
                    ManagedChannelImpl.this.cancelIdleTimer(false);
                    ManagedChannelImpl.this.enterIdleMode();
                }
            }
        });
    }

    private final class UncommittedRetriableStreamsRegistry {
        final Object lock;
        Status shutdownStatus;
        Collection<ClientStream> uncommittedRetriableStreams;

        private UncommittedRetriableStreamsRegistry() {
            this.lock = new Object();
            this.uncommittedRetriableStreams = new HashSet();
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
            if (r0 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
            io.grpc.internal.ManagedChannelImpl.access$1400(r3.this$0).shutdown(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onShutdown(io.grpc.Status r4) {
            /*
                r3 = this;
                r0 = 0
                java.lang.Object r1 = r3.lock
                monitor-enter(r1)
                io.grpc.Status r2 = r3.shutdownStatus     // Catch:{ all -> 0x0022 }
                if (r2 == 0) goto L_0x000a
                monitor-exit(r1)     // Catch:{ all -> 0x0022 }
                return
            L_0x000a:
                r3.shutdownStatus = r4     // Catch:{ all -> 0x0022 }
                java.util.Collection<io.grpc.internal.ClientStream> r2 = r3.uncommittedRetriableStreams     // Catch:{ all -> 0x0022 }
                boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0022 }
                if (r2 == 0) goto L_0x0015
                r0 = 1
            L_0x0015:
                monitor-exit(r1)     // Catch:{ all -> 0x0022 }
                if (r0 == 0) goto L_0x0021
                io.grpc.internal.ManagedChannelImpl r1 = io.grpc.internal.ManagedChannelImpl.this
                io.grpc.internal.DelayedClientTransport r1 = r1.delayedTransport
                r1.shutdown(r4)
            L_0x0021:
                return
            L_0x0022:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0022 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.ManagedChannelImpl.UncommittedRetriableStreamsRegistry.onShutdown(io.grpc.Status):void");
        }

        /* access modifiers changed from: package-private */
        public void onShutdownNow(Status reason) {
            Collection<ClientStream> streams;
            onShutdown(reason);
            synchronized (this.lock) {
                streams = new ArrayList<>(this.uncommittedRetriableStreams);
            }
            for (ClientStream stream : streams) {
                stream.cancel(reason);
            }
            ManagedChannelImpl.this.delayedTransport.shutdownNow(reason);
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public Status add(RetriableStream<?> retriableStream) {
            synchronized (this.lock) {
                if (this.shutdownStatus != null) {
                    Status status = this.shutdownStatus;
                    return status;
                }
                this.uncommittedRetriableStreams.add(retriableStream);
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public void remove(RetriableStream<?> retriableStream) {
            Status shutdownStatusCopy = null;
            synchronized (this.lock) {
                this.uncommittedRetriableStreams.remove(retriableStream);
                if (this.uncommittedRetriableStreams.isEmpty()) {
                    shutdownStatusCopy = this.shutdownStatus;
                    this.uncommittedRetriableStreams = new HashSet();
                }
            }
            if (shutdownStatusCopy != null) {
                ManagedChannelImpl.this.delayedTransport.shutdown(shutdownStatusCopy);
            }
        }
    }

    private final class LbHelperImpl extends LoadBalancer.Helper {
        AutoConfiguredLoadBalancerFactory.AutoConfiguredLoadBalancer lb;

        private LbHelperImpl() {
        }

        public AbstractSubchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(!ManagedChannelImpl.this.terminating, "Channel is being terminated");
            return new SubchannelImpl(args);
        }

        public void updateBalancingState(final ConnectivityState newState, final LoadBalancer.SubchannelPicker newPicker) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkNotNull(newState, "newState");
            Preconditions.checkNotNull(newPicker, "newPicker");
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    if (LbHelperImpl.this == ManagedChannelImpl.this.lbHelper) {
                        ManagedChannelImpl.this.updateSubchannelPicker(newPicker);
                        if (newState != ConnectivityState.SHUTDOWN) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Entering {0} state with picker: {1}", newState, newPicker);
                            ManagedChannelImpl.this.channelStateManager.gotoState(newState);
                        }
                    }
                }
            });
        }

        public void refreshNameResolution() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    ManagedChannelImpl.this.refreshNameResolution();
                }
            });
        }

        public ManagedChannel createOobChannel(EquivalentAddressGroup addressGroup, String authority) {
            return createOobChannel((List<EquivalentAddressGroup>) Collections.singletonList(addressGroup), authority);
        }

        public ManagedChannel createOobChannel(List<EquivalentAddressGroup> addressGroup, String authority) {
            List<EquivalentAddressGroup> list = addressGroup;
            Preconditions.checkState(!ManagedChannelImpl.this.terminated, "Channel is terminated");
            long oobChannelCreationTime = ManagedChannelImpl.this.timeProvider.currentTimeNanos();
            InternalLogId oobLogId = InternalLogId.allocate("OobChannel", (String) null);
            String str = authority;
            InternalLogId subchannelLogId = InternalLogId.allocate("Subchannel-OOB", str);
            ChannelTracer channelTracer = new ChannelTracer(oobLogId, ManagedChannelImpl.this.maxTraceEvents, oobChannelCreationTime, "OobChannel for " + list);
            InternalLogId internalLogId = oobLogId;
            final OobChannel oobChannel = new OobChannel(str, ManagedChannelImpl.this.balancerRpcExecutorPool, ManagedChannelImpl.this.oobTransportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.syncContext, ManagedChannelImpl.this.callTracerFactory.create(), channelTracer, ManagedChannelImpl.this.channelz, ManagedChannelImpl.this.timeProvider);
            ManagedChannelImpl.this.channelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child OobChannel created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(oobChannelCreationTime).setChannelRef(oobChannel).build());
            InternalLogId subchannelLogId2 = subchannelLogId;
            ChannelTracer channelTracer2 = new ChannelTracer(subchannelLogId2, ManagedChannelImpl.this.maxTraceEvents, oobChannelCreationTime, "Subchannel for " + list);
            ChannelLogger subchannelLogger = new ChannelLoggerImpl(channelTracer2, ManagedChannelImpl.this.timeProvider);
            String access$5900 = ManagedChannelImpl.this.userAgent;
            BackoffPolicy.Provider access$6000 = ManagedChannelImpl.this.backoffPolicyProvider;
            ClientTransportFactory access$5400 = ManagedChannelImpl.this.oobTransportFactory;
            ScheduledExecutorService scheduledExecutorService = ManagedChannelImpl.this.oobTransportFactory.getScheduledExecutorService();
            Supplier access$6100 = ManagedChannelImpl.this.stopwatchSupplier;
            SynchronizationContext synchronizationContext = ManagedChannelImpl.this.syncContext;
            long oobChannelCreationTime2 = oobChannelCreationTime;
            ScheduledExecutorService scheduledExecutorService2 = scheduledExecutorService;
            AnonymousClass1ManagedOobChannelCallback r10 = new InternalSubchannel.Callback() {
                /* access modifiers changed from: package-private */
                public void onTerminated(InternalSubchannel is) {
                    ManagedChannelImpl.this.oobChannels.remove(oobChannel);
                    ManagedChannelImpl.this.channelz.removeSubchannel(is);
                    oobChannel.handleSubchannelTerminated();
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                /* access modifiers changed from: package-private */
                public void onStateChange(InternalSubchannel is, ConnectivityStateInfo newState) {
                    ManagedChannelImpl.this.handleInternalSubchannelState(newState);
                    oobChannel.handleSubchannelStateChange(newState);
                }
            };
            InternalChannelz access$5600 = ManagedChannelImpl.this.channelz;
            CallTracer create = ManagedChannelImpl.this.callTracerFactory.create();
            Supplier supplier = access$6100;
            InternalChannelz internalChannelz = access$5600;
            OobChannel oobChannel2 = oobChannel;
            ChannelTracer oobChannelTracer = channelTracer;
            InternalLogId subchannelLogId3 = subchannelLogId2;
            String str2 = access$5900;
            String str3 = authority;
            InternalSubchannel internalSubchannel = new InternalSubchannel(list, str3, str2, access$6000, access$5400, scheduledExecutorService2, supplier, synchronizationContext, r10, internalChannelz, create, channelTracer2, subchannelLogId3, subchannelLogger, ManagedChannelImpl.this.transportFilters);
            InternalSubchannel internalSubchannel2 = internalSubchannel;
            InternalLogId internalLogId2 = subchannelLogId3;
            oobChannelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child Subchannel created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(oobChannelCreationTime2).setSubchannelRef(internalSubchannel).build());
            final OobChannel oobChannel3 = oobChannel2;
            ManagedChannelImpl.this.channelz.addSubchannel(oobChannel3);
            ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
            oobChannel3.setSubchannel(internalSubchannel);
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    if (ManagedChannelImpl.this.terminating) {
                        oobChannel3.shutdown();
                    }
                    if (!ManagedChannelImpl.this.terminated) {
                        ManagedChannelImpl.this.oobChannels.add(oobChannel3);
                    }
                }
            });
            return oobChannel3;
        }

        @Deprecated
        public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(String target) {
            return createResolvingOobChannelBuilder(target, new DefaultChannelCreds()).overrideAuthority(getAuthority());
        }

        public ManagedChannelBuilder<?> createResolvingOobChannelBuilder(final String target, final ChannelCredentials channelCreds) {
            Preconditions.checkNotNull(channelCreds, "channelCreds");
            Preconditions.checkState(!ManagedChannelImpl.this.terminated, "Channel is terminated");
            return ((AnonymousClass1ResolvingOobChannelBuilder) ((AnonymousClass1ResolvingOobChannelBuilder) ((AnonymousClass1ResolvingOobChannelBuilder) ((AnonymousClass1ResolvingOobChannelBuilder) new ForwardingChannelBuilder2<AnonymousClass1ResolvingOobChannelBuilder>() {
                final ManagedChannelBuilder<?> delegate;

                {
                    final ClientTransportFactory transportFactory;
                    CallCredentials callCredentials;
                    if (channelCreds instanceof DefaultChannelCreds) {
                        transportFactory = ManagedChannelImpl.this.originalTransportFactory;
                        callCredentials = null;
                    } else {
                        ClientTransportFactory.SwapChannelCredentialsResult swapResult = ManagedChannelImpl.this.originalTransportFactory.swapChannelCredentials(channelCreds);
                        if (swapResult == null) {
                            this.delegate = Grpc.newChannelBuilder(target, channelCreds);
                            return;
                        }
                        ClientTransportFactory transportFactory2 = swapResult.transportFactory;
                        CallCredentials callCredentials2 = swapResult.callCredentials;
                        transportFactory = transportFactory2;
                        callCredentials = callCredentials2;
                    }
                    this.delegate = new ManagedChannelImplBuilder(target, channelCreds, callCredentials, new ManagedChannelImplBuilder.ClientTransportFactoryBuilder(LbHelperImpl.this) {
                        public ClientTransportFactory buildClientTransportFactory() {
                            return transportFactory;
                        }
                    }, new ManagedChannelImplBuilder.FixedPortProvider(ManagedChannelImpl.this.nameResolverArgs.getDefaultPort())).nameResolverRegistry(ManagedChannelImpl.this.nameResolverRegistry);
                }

                /* access modifiers changed from: protected */
                public ManagedChannelBuilder<?> delegate() {
                    return this.delegate;
                }
            }.executor(ManagedChannelImpl.this.executor)).offloadExecutor(ManagedChannelImpl.this.offloadExecutorHolder.getExecutor())).maxTraceEvents(ManagedChannelImpl.this.maxTraceEvents)).proxyDetector(ManagedChannelImpl.this.nameResolverArgs.getProxyDetector())).userAgent(ManagedChannelImpl.this.userAgent);
        }

        public ChannelCredentials getUnsafeChannelCredentials() {
            if (ManagedChannelImpl.this.originalChannelCreds == null) {
                return new DefaultChannelCreds();
            }
            return ManagedChannelImpl.this.originalChannelCreds;
        }

        public void updateOobChannelAddresses(ManagedChannel channel, EquivalentAddressGroup eag) {
            updateOobChannelAddresses(channel, (List<EquivalentAddressGroup>) Collections.singletonList(eag));
        }

        public void updateOobChannelAddresses(ManagedChannel channel, List<EquivalentAddressGroup> eag) {
            Preconditions.checkArgument(channel instanceof OobChannel, "channel must have been returned from createOobChannel");
            ((OobChannel) channel).updateAddresses(eag);
        }

        public String getAuthority() {
            return ManagedChannelImpl.this.authority();
        }

        public SynchronizationContext getSynchronizationContext() {
            return ManagedChannelImpl.this.syncContext;
        }

        public ScheduledExecutorService getScheduledExecutorService() {
            return ManagedChannelImpl.this.scheduledExecutor;
        }

        public ChannelLogger getChannelLogger() {
            return ManagedChannelImpl.this.channelLogger;
        }

        public NameResolver.Args getNameResolverArgs() {
            return ManagedChannelImpl.this.nameResolverArgs;
        }

        public NameResolverRegistry getNameResolverRegistry() {
            return ManagedChannelImpl.this.nameResolverRegistry;
        }

        final class DefaultChannelCreds extends ChannelCredentials {
            DefaultChannelCreds() {
            }

            public ChannelCredentials withoutBearerTokens() {
                return this;
            }
        }
    }

    final class NameResolverListener extends NameResolver.Listener2 {
        final LbHelperImpl helper;
        final NameResolver resolver;

        NameResolverListener(LbHelperImpl helperImpl, NameResolver resolver2) {
            this.helper = (LbHelperImpl) Preconditions.checkNotNull(helperImpl, "helperImpl");
            this.resolver = (NameResolver) Preconditions.checkNotNull(resolver2, "resolver");
        }

        public void onResult(final NameResolver.ResolutionResult resolutionResult) {
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    ManagedChannelServiceConfig validServiceConfig;
                    ManagedChannelServiceConfig effectiveServiceConfig;
                    ManagedChannelServiceConfig effectiveServiceConfig2;
                    if (ManagedChannelImpl.this.nameResolver == NameResolverListener.this.resolver) {
                        List<EquivalentAddressGroup> servers = resolutionResult.getAddresses();
                        ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "Resolved address: {0}, config={1}", servers, resolutionResult.getAttributes());
                        if (ManagedChannelImpl.this.lastResolutionState != ResolutionState.SUCCESS) {
                            ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Address resolved: {0}", servers);
                            ResolutionState unused = ManagedChannelImpl.this.lastResolutionState = ResolutionState.SUCCESS;
                        }
                        NameResolver.ConfigOrError configOrError = resolutionResult.getServiceConfig();
                        RetryingNameResolver.ResolutionResultListener resolutionResultListener = (RetryingNameResolver.ResolutionResultListener) resolutionResult.getAttributes().get(RetryingNameResolver.RESOLUTION_RESULT_LISTENER_KEY);
                        InternalConfigSelector resolvedConfigSelector = (InternalConfigSelector) resolutionResult.getAttributes().get(InternalConfigSelector.KEY);
                        if (configOrError == null || configOrError.getConfig() == null) {
                            validServiceConfig = null;
                        } else {
                            validServiceConfig = (ManagedChannelServiceConfig) configOrError.getConfig();
                        }
                        Status serviceConfigError = configOrError != null ? configOrError.getError() : null;
                        if (!ManagedChannelImpl.this.lookUpServiceConfig) {
                            if (validServiceConfig != null) {
                                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config from name resolver discarded by channel settings");
                            }
                            effectiveServiceConfig = ManagedChannelImpl.this.defaultServiceConfig == null ? ManagedChannelImpl.EMPTY_SERVICE_CONFIG : ManagedChannelImpl.this.defaultServiceConfig;
                            if (resolvedConfigSelector != null) {
                                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Config selector from name resolver discarded by channel settings");
                            }
                            ManagedChannelImpl.this.realChannel.updateConfigSelector(effectiveServiceConfig.getDefaultConfigSelector());
                        } else {
                            if (validServiceConfig != null) {
                                effectiveServiceConfig2 = validServiceConfig;
                                if (resolvedConfigSelector != null) {
                                    ManagedChannelImpl.this.realChannel.updateConfigSelector(resolvedConfigSelector);
                                    if (effectiveServiceConfig2.getDefaultConfigSelector() != null) {
                                        ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.DEBUG, "Method configs in service config will be discarded due to presence ofconfig-selector");
                                    }
                                } else {
                                    ManagedChannelImpl.this.realChannel.updateConfigSelector(effectiveServiceConfig2.getDefaultConfigSelector());
                                }
                            } else if (ManagedChannelImpl.this.defaultServiceConfig != null) {
                                effectiveServiceConfig2 = ManagedChannelImpl.this.defaultServiceConfig;
                                ManagedChannelImpl.this.realChannel.updateConfigSelector(effectiveServiceConfig2.getDefaultConfigSelector());
                                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Received no service config, using default service config");
                            } else if (serviceConfigError == null) {
                                ManagedChannelServiceConfig effectiveServiceConfig3 = ManagedChannelImpl.EMPTY_SERVICE_CONFIG;
                                ManagedChannelImpl.this.realChannel.updateConfigSelector((InternalConfigSelector) null);
                                effectiveServiceConfig2 = effectiveServiceConfig3;
                            } else if (!ManagedChannelImpl.this.serviceConfigUpdated) {
                                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Fallback to error due to invalid first service config without default config");
                                NameResolverListener.this.onError(configOrError.getError());
                                if (resolutionResultListener != null) {
                                    resolutionResultListener.resolutionAttempted(configOrError.getError());
                                    return;
                                }
                                return;
                            } else {
                                effectiveServiceConfig2 = ManagedChannelImpl.this.lastServiceConfig;
                            }
                            if (!effectiveServiceConfig.equals(ManagedChannelImpl.this.lastServiceConfig)) {
                                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.INFO, "Service config changed{0}", effectiveServiceConfig == ManagedChannelImpl.EMPTY_SERVICE_CONFIG ? " to empty" : "");
                                ManagedChannelServiceConfig unused2 = ManagedChannelImpl.this.lastServiceConfig = effectiveServiceConfig;
                                ManagedChannelImpl.this.transportProvider.throttle = effectiveServiceConfig.getRetryThrottling();
                            }
                            try {
                                boolean unused3 = ManagedChannelImpl.this.serviceConfigUpdated = true;
                            } catch (RuntimeException re) {
                                ManagedChannelImpl.logger.log(Level.WARNING, "[" + ManagedChannelImpl.this.getLogId() + "] Unexpected exception from parsing service config", re);
                            }
                        }
                        Attributes effectiveAttrs = resolutionResult.getAttributes();
                        if (NameResolverListener.this.helper == ManagedChannelImpl.this.lbHelper) {
                            Attributes.Builder attrBuilder = effectiveAttrs.toBuilder().discard(InternalConfigSelector.KEY);
                            Map<String, ?> healthCheckingConfig = effectiveServiceConfig.getHealthCheckingConfig();
                            if (healthCheckingConfig != null) {
                                attrBuilder.set(LoadBalancer.ATTR_HEALTH_CHECKING_CONFIG, healthCheckingConfig).build();
                            }
                            Status addressAcceptanceStatus = NameResolverListener.this.helper.lb.tryAcceptResolvedAddresses(LoadBalancer.ResolvedAddresses.newBuilder().setAddresses(servers).setAttributes(attrBuilder.build()).setLoadBalancingPolicyConfig(effectiveServiceConfig.getLoadBalancingConfig()).build());
                            if (resolutionResultListener != null) {
                                resolutionResultListener.resolutionAttempted(addressAcceptanceStatus);
                            }
                        }
                    }
                }
            });
        }

        public void onError(final Status error) {
            Preconditions.checkArgument(!error.isOk(), "the error status must not be OK");
            ManagedChannelImpl.this.syncContext.execute(new Runnable() {
                public void run() {
                    NameResolverListener.this.handleErrorInSyncContext(error);
                }
            });
        }

        /* access modifiers changed from: private */
        public void handleErrorInSyncContext(Status error) {
            ManagedChannelImpl.logger.log(Level.WARNING, "[{0}] Failed to resolve name. status={1}", new Object[]{ManagedChannelImpl.this.getLogId(), error});
            ManagedChannelImpl.this.realChannel.onConfigError();
            if (ManagedChannelImpl.this.lastResolutionState != ResolutionState.ERROR) {
                ManagedChannelImpl.this.channelLogger.log(ChannelLogger.ChannelLogLevel.WARNING, "Failed to resolve name: {0}", error);
                ResolutionState unused = ManagedChannelImpl.this.lastResolutionState = ResolutionState.ERROR;
            }
            if (this.helper == ManagedChannelImpl.this.lbHelper) {
                this.helper.lb.handleNameResolutionError(error);
            }
        }
    }

    private final class SubchannelImpl extends AbstractSubchannel {
        List<EquivalentAddressGroup> addressGroups;
        final LoadBalancer.CreateSubchannelArgs args;
        SynchronizationContext.ScheduledHandle delayedShutdownTask;
        boolean shutdown;
        boolean started;
        InternalSubchannel subchannel;
        final InternalLogId subchannelLogId;
        final ChannelLoggerImpl subchannelLogger;
        final ChannelTracer subchannelTracer;

        SubchannelImpl(LoadBalancer.CreateSubchannelArgs args2) {
            Preconditions.checkNotNull(args2, "args");
            this.addressGroups = args2.getAddresses();
            if (ManagedChannelImpl.this.authorityOverride != null) {
                args2 = args2.toBuilder().setAddresses(stripOverrideAuthorityAttributes(args2.getAddresses())).build();
            }
            this.args = args2;
            this.subchannelLogId = InternalLogId.allocate("Subchannel", ManagedChannelImpl.this.authority());
            this.subchannelTracer = new ChannelTracer(this.subchannelLogId, ManagedChannelImpl.this.maxTraceEvents, ManagedChannelImpl.this.timeProvider.currentTimeNanos(), "Subchannel for " + args2.getAddresses());
            this.subchannelLogger = new ChannelLoggerImpl(this.subchannelTracer, ManagedChannelImpl.this.timeProvider);
        }

        public void start(LoadBalancer.SubchannelStateListener listener) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(!this.started, "already started");
            Preconditions.checkState(!this.shutdown, "already shutdown");
            Preconditions.checkState(!ManagedChannelImpl.this.terminating, "Channel is being terminated");
            this.started = true;
            List<EquivalentAddressGroup> addresses = this.args.getAddresses();
            String authority = ManagedChannelImpl.this.authority();
            String access$5900 = ManagedChannelImpl.this.userAgent;
            BackoffPolicy.Provider access$6000 = ManagedChannelImpl.this.backoffPolicyProvider;
            ClientTransportFactory access$2000 = ManagedChannelImpl.this.transportFactory;
            ScheduledExecutorService scheduledExecutorService = ManagedChannelImpl.this.transportFactory.getScheduledExecutorService();
            Supplier access$6100 = ManagedChannelImpl.this.stopwatchSupplier;
            SynchronizationContext synchronizationContext = ManagedChannelImpl.this.syncContext;
            final LoadBalancer.SubchannelStateListener subchannelStateListener = listener;
            AnonymousClass1ManagedInternalSubchannelCallback r13 = new InternalSubchannel.Callback() {
                /* access modifiers changed from: package-private */
                public void onTerminated(InternalSubchannel is) {
                    ManagedChannelImpl.this.subchannels.remove(is);
                    ManagedChannelImpl.this.channelz.removeSubchannel(is);
                    ManagedChannelImpl.this.maybeTerminateChannel();
                }

                /* access modifiers changed from: package-private */
                public void onStateChange(InternalSubchannel is, ConnectivityStateInfo newState) {
                    Preconditions.checkState(subchannelStateListener != null, "listener is null");
                    subchannelStateListener.onSubchannelState(newState);
                }

                /* access modifiers changed from: package-private */
                public void onInUse(InternalSubchannel is) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(is, true);
                }

                /* access modifiers changed from: package-private */
                public void onNotInUse(InternalSubchannel is) {
                    ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(is, false);
                }
            };
            InternalChannelz access$5600 = ManagedChannelImpl.this.channelz;
            CallTracer create = ManagedChannelImpl.this.callTracerFactory.create();
            ChannelTracer channelTracer = this.subchannelTracer;
            InternalSubchannel internalSubchannel = new InternalSubchannel(addresses, authority, access$5900, access$6000, access$2000, scheduledExecutorService, access$6100, synchronizationContext, r13, access$5600, create, channelTracer, this.subchannelLogId, this.subchannelLogger, ManagedChannelImpl.this.transportFilters);
            ManagedChannelImpl.this.channelTracer.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription("Child Subchannel started").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(ManagedChannelImpl.this.timeProvider.currentTimeNanos()).setSubchannelRef(internalSubchannel).build());
            this.subchannel = internalSubchannel;
            ManagedChannelImpl.this.channelz.addSubchannel(internalSubchannel);
            ManagedChannelImpl.this.subchannels.add(internalSubchannel);
        }

        /* access modifiers changed from: package-private */
        public InternalInstrumented<InternalChannelz.ChannelStats> getInstrumentedInternalSubchannel() {
            Preconditions.checkState(this.started, "not started");
            return this.subchannel;
        }

        public void shutdown() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            if (this.subchannel == null) {
                this.shutdown = true;
                return;
            }
            if (!this.shutdown) {
                this.shutdown = true;
            } else if (ManagedChannelImpl.this.terminating && this.delayedShutdownTask != null) {
                this.delayedShutdownTask.cancel();
                this.delayedShutdownTask = null;
            } else {
                return;
            }
            if (!ManagedChannelImpl.this.terminating) {
                this.delayedShutdownTask = ManagedChannelImpl.this.syncContext.schedule(new LogExceptionRunnable(new Runnable() {
                    public void run() {
                        SubchannelImpl.this.subchannel.shutdown(ManagedChannelImpl.SUBCHANNEL_SHUTDOWN_STATUS);
                    }
                }), ManagedChannelImpl.SUBCHANNEL_SHUTDOWN_DELAY_SECONDS, TimeUnit.SECONDS, ManagedChannelImpl.this.transportFactory.getScheduledExecutorService());
            } else {
                this.subchannel.shutdown(ManagedChannelImpl.SHUTDOWN_STATUS);
            }
        }

        public void requestConnection() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(this.started, "not started");
            this.subchannel.obtainActiveTransport();
        }

        public List<EquivalentAddressGroup> getAllAddresses() {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            Preconditions.checkState(this.started, "not started");
            return this.addressGroups;
        }

        public Attributes getAttributes() {
            return this.args.getAttributes();
        }

        public String toString() {
            return this.subchannelLogId.toString();
        }

        public Channel asChannel() {
            Preconditions.checkState(this.started, "not started");
            return new SubchannelChannel(this.subchannel, ManagedChannelImpl.this.balancerRpcExecutorHolder.getExecutor(), ManagedChannelImpl.this.transportFactory.getScheduledExecutorService(), ManagedChannelImpl.this.callTracerFactory.create(), new AtomicReference((Object) null));
        }

        public Object getInternalSubchannel() {
            Preconditions.checkState(this.started, "Subchannel is not started");
            return this.subchannel;
        }

        public ChannelLogger getChannelLogger() {
            return this.subchannelLogger;
        }

        public void updateAddresses(List<EquivalentAddressGroup> addrs) {
            ManagedChannelImpl.this.syncContext.throwIfNotInThisSynchronizationContext();
            this.addressGroups = addrs;
            if (ManagedChannelImpl.this.authorityOverride != null) {
                addrs = stripOverrideAuthorityAttributes(addrs);
            }
            this.subchannel.updateAddresses(addrs);
        }

        private List<EquivalentAddressGroup> stripOverrideAuthorityAttributes(List<EquivalentAddressGroup> eags) {
            List<EquivalentAddressGroup> eagsWithoutOverrideAttr = new ArrayList<>();
            for (EquivalentAddressGroup eag : eags) {
                eagsWithoutOverrideAttr.add(new EquivalentAddressGroup(eag.getAddresses(), eag.getAttributes().toBuilder().discard(EquivalentAddressGroup.ATTR_AUTHORITY_OVERRIDE).build()));
            }
            return Collections.unmodifiableList(eagsWithoutOverrideAttr);
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add(TypedValues.AttributesType.S_TARGET, (Object) this.target).toString();
    }

    private final class DelayedTransportListener implements ManagedClientTransport.Listener {
        private DelayedTransportListener() {
        }

        public void transportShutdown(Status s) {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
        }

        public void transportReady() {
        }

        public Attributes filterTransport(Attributes attributes) {
            return attributes;
        }

        public void transportInUse(boolean inUse) {
            ManagedChannelImpl.this.inUseStateAggregator.updateObjectInUse(ManagedChannelImpl.this.delayedTransport, inUse);
        }

        public void transportTerminated() {
            Preconditions.checkState(ManagedChannelImpl.this.shutdown.get(), "Channel must have been shut down");
            boolean unused = ManagedChannelImpl.this.terminating = true;
            ManagedChannelImpl.this.shutdownNameResolverAndLoadBalancer(false);
            ManagedChannelImpl.this.maybeShutdownNowSubchannels();
            ManagedChannelImpl.this.maybeTerminateChannel();
        }
    }

    private final class IdleModeStateAggregator extends InUseStateAggregator<Object> {
        private IdleModeStateAggregator() {
        }

        /* access modifiers changed from: protected */
        public void handleInUse() {
            ManagedChannelImpl.this.exitIdleMode();
        }

        /* access modifiers changed from: protected */
        public void handleNotInUse() {
            if (!ManagedChannelImpl.this.shutdown.get()) {
                ManagedChannelImpl.this.rescheduleIdleTimer();
            }
        }
    }

    static final class ExecutorHolder implements Executor {
        private Executor executor;
        private final ObjectPool<? extends Executor> pool;

        ExecutorHolder(ObjectPool<? extends Executor> executorPool) {
            this.pool = (ObjectPool) Preconditions.checkNotNull(executorPool, "executorPool");
        }

        /* access modifiers changed from: package-private */
        public synchronized Executor getExecutor() {
            if (this.executor == null) {
                this.executor = (Executor) Preconditions.checkNotNull((Executor) this.pool.getObject(), "%s.getObject()", (Object) this.executor);
            }
            return this.executor;
        }

        /* access modifiers changed from: package-private */
        public synchronized void release() {
            if (this.executor != null) {
                this.executor = (Executor) this.pool.returnObject(this.executor);
            }
        }

        public void execute(Runnable command) {
            getExecutor().execute(command);
        }
    }

    private static final class RestrictedScheduledExecutor implements ScheduledExecutorService {
        final ScheduledExecutorService delegate;

        private RestrictedScheduledExecutor(ScheduledExecutorService delegate2) {
            this.delegate = (ScheduledExecutorService) Preconditions.checkNotNull(delegate2, "delegate");
        }

        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return this.delegate.schedule(callable, delay, unit);
        }

        public ScheduledFuture<?> schedule(Runnable cmd, long delay, TimeUnit unit) {
            return this.delegate.schedule(cmd, delay, unit);
        }

        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            return this.delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return this.delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return this.delegate.awaitTermination(timeout, unit);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return this.delegate.invokeAll(tasks);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return this.delegate.invokeAll(tasks, timeout, unit);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return this.delegate.invokeAny(tasks);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.delegate.invokeAny(tasks, timeout, unit);
        }

        public boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        public boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        public void shutdown() {
            throw new UnsupportedOperationException("Restricted: shutdown() is not allowed");
        }

        public List<Runnable> shutdownNow() {
            throw new UnsupportedOperationException("Restricted: shutdownNow() is not allowed");
        }

        public <T> Future<T> submit(Callable<T> task) {
            return this.delegate.submit(task);
        }

        public Future<?> submit(Runnable task) {
            return this.delegate.submit(task);
        }

        public <T> Future<T> submit(Runnable task, T result) {
            return this.delegate.submit(task, result);
        }

        public void execute(Runnable command) {
            this.delegate.execute(command);
        }
    }
}
