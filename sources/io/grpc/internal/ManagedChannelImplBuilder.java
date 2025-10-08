package io.grpc.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.Attributes;
import io.grpc.BinaryLog;
import io.grpc.CallCredentials;
import io.grpc.ChannelCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.ClientTransportFilter;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalChannelz;
import io.grpc.InternalGlobalInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.NameResolverRegistry;
import io.grpc.ProxyDetector;
import io.grpc.internal.ExponentialBackoffPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class ManagedChannelImplBuilder extends ManagedChannelBuilder<ManagedChannelImplBuilder> {
    private static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();
    private static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    private static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    private static final long DEFAULT_PER_RPC_BUFFER_LIMIT_IN_BYTES = 1048576;
    private static final long DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES = 16777216;
    private static final String DIRECT_ADDRESS_SCHEME = "directaddress";
    private static final Method GET_CLIENT_INTERCEPTOR_METHOD;
    static final long IDLE_MODE_DEFAULT_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(IDLE_MODE_MAX_TIMEOUT_DAYS);
    static final long IDLE_MODE_MAX_TIMEOUT_DAYS = 30;
    static final long IDLE_MODE_MIN_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(1);
    private static final Logger log = Logger.getLogger(ManagedChannelImplBuilder.class.getName());
    private boolean authorityCheckerDisabled;
    @Nullable
    String authorityOverride;
    @Nullable
    BinaryLog binlog;
    @Nullable
    final CallCredentials callCredentials;
    private final ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider;
    @Nullable
    final ChannelCredentials channelCredentials;
    InternalChannelz channelz;
    private final ClientTransportFactoryBuilder clientTransportFactoryBuilder;
    CompressorRegistry compressorRegistry;
    DecompressorRegistry decompressorRegistry;
    String defaultLbPolicy;
    @Nullable
    Map<String, ?> defaultServiceConfig;
    @Nullable
    private final SocketAddress directServerAddress;
    ObjectPool<? extends Executor> executorPool;
    boolean fullStreamDecompression;
    long idleTimeoutMillis;
    private final List<ClientInterceptor> interceptors;
    boolean lookUpServiceConfig;
    int maxHedgedAttempts;
    int maxRetryAttempts;
    int maxTraceEvents;
    NameResolverRegistry nameResolverRegistry;
    ObjectPool<? extends Executor> offloadExecutorPool;
    long perRpcBufferLimit;
    @Nullable
    ProxyDetector proxyDetector;
    private boolean recordFinishedRpcs;
    private boolean recordRealTimeMetrics;
    private boolean recordRetryMetrics;
    private boolean recordStartedRpcs;
    long retryBufferSize;
    boolean retryEnabled;
    private boolean statsEnabled;
    final String target;
    private boolean tracingEnabled;
    final List<ClientTransportFilter> transportFilters;
    @Nullable
    String userAgent;

    public interface ChannelBuilderDefaultPortProvider {
        int getDefaultPort();
    }

    public interface ClientTransportFactoryBuilder {
        ClientTransportFactory buildClientTransportFactory();
    }

    static {
        Method getClientInterceptorMethod = null;
        try {
            getClientInterceptorMethod = Class.forName("io.grpc.census.InternalCensusStatsAccessor").getDeclaredMethod("getClientInterceptor", new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE});
        } catch (ClassNotFoundException e) {
            log.log(Level.FINE, "Unable to apply census stats", e);
        } catch (NoSuchMethodException e2) {
            log.log(Level.FINE, "Unable to apply census stats", e2);
        }
        GET_CLIENT_INTERCEPTOR_METHOD = getClientInterceptorMethod;
    }

    public static ManagedChannelBuilder<?> forAddress(String name, int port) {
        throw new UnsupportedOperationException("ClientTransportFactoryBuilder is required, use a constructor");
    }

    public static ManagedChannelBuilder<?> forTarget(String target2) {
        throw new UnsupportedOperationException("ClientTransportFactoryBuilder is required, use a constructor");
    }

    public static class UnsupportedClientTransportFactoryBuilder implements ClientTransportFactoryBuilder {
        public ClientTransportFactory buildClientTransportFactory() {
            throw new UnsupportedOperationException();
        }
    }

    public static final class FixedPortProvider implements ChannelBuilderDefaultPortProvider {
        private final int port;

        public FixedPortProvider(int port2) {
            this.port = port2;
        }

        public int getDefaultPort() {
            return this.port;
        }
    }

    private static final class ManagedChannelDefaultPortProvider implements ChannelBuilderDefaultPortProvider {
        private ManagedChannelDefaultPortProvider() {
        }

        public int getDefaultPort() {
            return GrpcUtil.DEFAULT_PORT_SSL;
        }
    }

    public ManagedChannelImplBuilder(String target2, ClientTransportFactoryBuilder clientTransportFactoryBuilder2, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider2) {
        this(target2, (ChannelCredentials) null, (CallCredentials) null, clientTransportFactoryBuilder2, channelBuilderDefaultPortProvider2);
    }

    public ManagedChannelImplBuilder(String target2, @Nullable ChannelCredentials channelCreds, @Nullable CallCredentials callCreds, ClientTransportFactoryBuilder clientTransportFactoryBuilder2, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider2) {
        this.executorPool = DEFAULT_EXECUTOR_POOL;
        this.offloadExecutorPool = DEFAULT_EXECUTOR_POOL;
        this.interceptors = new ArrayList();
        this.nameResolverRegistry = NameResolverRegistry.getDefaultRegistry();
        this.transportFilters = new ArrayList();
        this.defaultLbPolicy = GrpcUtil.DEFAULT_LB_POLICY;
        this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        this.idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
        this.maxRetryAttempts = 5;
        this.maxHedgedAttempts = 5;
        this.retryBufferSize = DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES;
        this.perRpcBufferLimit = DEFAULT_PER_RPC_BUFFER_LIMIT_IN_BYTES;
        this.retryEnabled = true;
        this.channelz = InternalChannelz.instance();
        this.lookUpServiceConfig = true;
        this.statsEnabled = true;
        this.recordStartedRpcs = true;
        this.recordFinishedRpcs = true;
        this.recordRealTimeMetrics = false;
        this.recordRetryMetrics = true;
        this.tracingEnabled = true;
        this.target = (String) Preconditions.checkNotNull(target2, TypedValues.AttributesType.S_TARGET);
        this.channelCredentials = channelCreds;
        this.callCredentials = callCreds;
        this.clientTransportFactoryBuilder = (ClientTransportFactoryBuilder) Preconditions.checkNotNull(clientTransportFactoryBuilder2, "clientTransportFactoryBuilder");
        this.directServerAddress = null;
        if (channelBuilderDefaultPortProvider2 != null) {
            this.channelBuilderDefaultPortProvider = channelBuilderDefaultPortProvider2;
        } else {
            this.channelBuilderDefaultPortProvider = new ManagedChannelDefaultPortProvider();
        }
    }

    static String makeTargetStringForDirectAddress(SocketAddress address) {
        try {
            return new URI(DIRECT_ADDRESS_SCHEME, "", "/" + address, (String) null).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ManagedChannelImplBuilder(SocketAddress directServerAddress2, String authority, ClientTransportFactoryBuilder clientTransportFactoryBuilder2, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider2) {
        this(directServerAddress2, authority, (ChannelCredentials) null, (CallCredentials) null, clientTransportFactoryBuilder2, channelBuilderDefaultPortProvider2);
    }

    public ManagedChannelImplBuilder(SocketAddress directServerAddress2, String authority, @Nullable ChannelCredentials channelCreds, @Nullable CallCredentials callCreds, ClientTransportFactoryBuilder clientTransportFactoryBuilder2, @Nullable ChannelBuilderDefaultPortProvider channelBuilderDefaultPortProvider2) {
        this.executorPool = DEFAULT_EXECUTOR_POOL;
        this.offloadExecutorPool = DEFAULT_EXECUTOR_POOL;
        this.interceptors = new ArrayList();
        this.nameResolverRegistry = NameResolverRegistry.getDefaultRegistry();
        this.transportFilters = new ArrayList();
        this.defaultLbPolicy = GrpcUtil.DEFAULT_LB_POLICY;
        this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        this.idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
        this.maxRetryAttempts = 5;
        this.maxHedgedAttempts = 5;
        this.retryBufferSize = DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES;
        this.perRpcBufferLimit = DEFAULT_PER_RPC_BUFFER_LIMIT_IN_BYTES;
        this.retryEnabled = true;
        this.channelz = InternalChannelz.instance();
        this.lookUpServiceConfig = true;
        this.statsEnabled = true;
        this.recordStartedRpcs = true;
        this.recordFinishedRpcs = true;
        this.recordRealTimeMetrics = false;
        this.recordRetryMetrics = true;
        this.tracingEnabled = true;
        this.target = makeTargetStringForDirectAddress(directServerAddress2);
        this.channelCredentials = channelCreds;
        this.callCredentials = callCreds;
        this.clientTransportFactoryBuilder = (ClientTransportFactoryBuilder) Preconditions.checkNotNull(clientTransportFactoryBuilder2, "clientTransportFactoryBuilder");
        this.directServerAddress = directServerAddress2;
        NameResolverRegistry reg = new NameResolverRegistry();
        reg.register(new DirectAddressNameResolverProvider(directServerAddress2, authority));
        this.nameResolverRegistry = reg;
        if (channelBuilderDefaultPortProvider2 != null) {
            this.channelBuilderDefaultPortProvider = channelBuilderDefaultPortProvider2;
        } else {
            this.channelBuilderDefaultPortProvider = new ManagedChannelDefaultPortProvider();
        }
    }

    public ManagedChannelImplBuilder directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    public ManagedChannelImplBuilder executor(Executor executor) {
        if (executor != null) {
            this.executorPool = new FixedObjectPool(executor);
        } else {
            this.executorPool = DEFAULT_EXECUTOR_POOL;
        }
        return this;
    }

    public ManagedChannelImplBuilder offloadExecutor(Executor executor) {
        if (executor != null) {
            this.offloadExecutorPool = new FixedObjectPool(executor);
        } else {
            this.offloadExecutorPool = DEFAULT_EXECUTOR_POOL;
        }
        return this;
    }

    public ManagedChannelImplBuilder intercept(List<ClientInterceptor> interceptors2) {
        this.interceptors.addAll(interceptors2);
        return this;
    }

    public ManagedChannelImplBuilder intercept(ClientInterceptor... interceptors2) {
        return intercept(Arrays.asList(interceptors2));
    }

    public ManagedChannelImplBuilder addTransportFilter(ClientTransportFilter hook) {
        this.transportFilters.add((ClientTransportFilter) Preconditions.checkNotNull(hook, "transport filter"));
        return this;
    }

    @Deprecated
    public ManagedChannelImplBuilder nameResolverFactory(NameResolver.Factory resolverFactory) {
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of NameResolverFactory", (Object) this.directServerAddress);
        if (resolverFactory != null) {
            NameResolverRegistry reg = new NameResolverRegistry();
            if (resolverFactory instanceof NameResolverProvider) {
                reg.register((NameResolverProvider) resolverFactory);
            } else {
                reg.register(new NameResolverFactoryToProviderFacade(resolverFactory));
            }
            this.nameResolverRegistry = reg;
        } else {
            this.nameResolverRegistry = NameResolverRegistry.getDefaultRegistry();
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public ManagedChannelImplBuilder nameResolverRegistry(NameResolverRegistry resolverRegistry) {
        this.nameResolverRegistry = resolverRegistry;
        return this;
    }

    public ManagedChannelImplBuilder defaultLoadBalancingPolicy(String policy) {
        boolean z = true;
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of load-balancing policy", (Object) this.directServerAddress);
        if (policy == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "policy cannot be null");
        this.defaultLbPolicy = policy;
        return this;
    }

    public ManagedChannelImplBuilder decompressorRegistry(DecompressorRegistry registry) {
        if (registry != null) {
            this.decompressorRegistry = registry;
        } else {
            this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        }
        return this;
    }

    public ManagedChannelImplBuilder compressorRegistry(CompressorRegistry registry) {
        if (registry != null) {
            this.compressorRegistry = registry;
        } else {
            this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        }
        return this;
    }

    public ManagedChannelImplBuilder userAgent(@Nullable String userAgent2) {
        this.userAgent = userAgent2;
        return this;
    }

    public ManagedChannelImplBuilder overrideAuthority(String authority) {
        this.authorityOverride = checkAuthority(authority);
        return this;
    }

    public ManagedChannelImplBuilder idleTimeout(long value, TimeUnit unit) {
        Preconditions.checkArgument(value > 0, "idle timeout is %s, but must be positive", value);
        if (unit.toDays(value) >= IDLE_MODE_MAX_TIMEOUT_DAYS) {
            this.idleTimeoutMillis = -1;
        } else {
            this.idleTimeoutMillis = Math.max(unit.toMillis(value), IDLE_MODE_MIN_TIMEOUT_MILLIS);
        }
        return this;
    }

    public ManagedChannelImplBuilder maxRetryAttempts(int maxRetryAttempts2) {
        this.maxRetryAttempts = maxRetryAttempts2;
        return this;
    }

    public ManagedChannelImplBuilder maxHedgedAttempts(int maxHedgedAttempts2) {
        this.maxHedgedAttempts = maxHedgedAttempts2;
        return this;
    }

    public ManagedChannelImplBuilder retryBufferSize(long bytes) {
        Preconditions.checkArgument(bytes > 0, "retry buffer size must be positive");
        this.retryBufferSize = bytes;
        return this;
    }

    public ManagedChannelImplBuilder perRpcBufferLimit(long bytes) {
        Preconditions.checkArgument(bytes > 0, "per RPC buffer limit must be positive");
        this.perRpcBufferLimit = bytes;
        return this;
    }

    public ManagedChannelImplBuilder disableRetry() {
        this.retryEnabled = false;
        return this;
    }

    public ManagedChannelImplBuilder enableRetry() {
        this.retryEnabled = true;
        return this;
    }

    public ManagedChannelImplBuilder setBinaryLog(BinaryLog binlog2) {
        this.binlog = binlog2;
        return this;
    }

    public ManagedChannelImplBuilder maxTraceEvents(int maxTraceEvents2) {
        Preconditions.checkArgument(maxTraceEvents2 >= 0, "maxTraceEvents must be non-negative");
        this.maxTraceEvents = maxTraceEvents2;
        return this;
    }

    public ManagedChannelImplBuilder proxyDetector(@Nullable ProxyDetector proxyDetector2) {
        this.proxyDetector = proxyDetector2;
        return this;
    }

    public ManagedChannelImplBuilder defaultServiceConfig(@Nullable Map<String, ?> serviceConfig) {
        this.defaultServiceConfig = checkMapEntryTypes(serviceConfig);
        return this;
    }

    @Nullable
    private static Map<String, ?> checkMapEntryTypes(@Nullable Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        Map<String, Object> parsedMap = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Preconditions.checkArgument(entry.getKey() instanceof String, "The key of the entry '%s' is not of String type", (Object) entry);
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                parsedMap.put(key, (Object) null);
            } else if (value instanceof Map) {
                parsedMap.put(key, checkMapEntryTypes((Map) value));
            } else if (value instanceof List) {
                parsedMap.put(key, checkListEntryTypes((List) value));
            } else if (value instanceof String) {
                parsedMap.put(key, value);
            } else if (value instanceof Double) {
                parsedMap.put(key, value);
            } else if (value instanceof Boolean) {
                parsedMap.put(key, value);
            } else {
                throw new IllegalArgumentException("The value of the map entry '" + entry + "' is of type '" + value.getClass() + "', which is not supported");
            }
        }
        return Collections.unmodifiableMap(parsedMap);
    }

    private static List<?> checkListEntryTypes(List<?> list) {
        List<Object> parsedList = new ArrayList<>(list.size());
        for (Object value : list) {
            if (value == null) {
                parsedList.add((Object) null);
            } else if (value instanceof Map) {
                parsedList.add(checkMapEntryTypes((Map) value));
            } else if (value instanceof List) {
                parsedList.add(checkListEntryTypes((List) value));
            } else if (value instanceof String) {
                parsedList.add(value);
            } else if (value instanceof Double) {
                parsedList.add(value);
            } else if (value instanceof Boolean) {
                parsedList.add(value);
            } else {
                throw new IllegalArgumentException("The entry '" + value + "' is of type '" + value.getClass() + "', which is not supported");
            }
        }
        return Collections.unmodifiableList(parsedList);
    }

    public ManagedChannelImplBuilder disableServiceConfigLookUp() {
        this.lookUpServiceConfig = false;
        return this;
    }

    public void setStatsEnabled(boolean value) {
        this.statsEnabled = value;
    }

    public void setStatsRecordStartedRpcs(boolean value) {
        this.recordStartedRpcs = value;
    }

    public void setStatsRecordFinishedRpcs(boolean value) {
        this.recordFinishedRpcs = value;
    }

    public void setStatsRecordRealTimeMetrics(boolean value) {
        this.recordRealTimeMetrics = value;
    }

    public void setStatsRecordRetryMetrics(boolean value) {
        this.recordRetryMetrics = value;
    }

    public void setTracingEnabled(boolean value) {
        this.tracingEnabled = value;
    }

    /* access modifiers changed from: package-private */
    public String checkAuthority(String authority) {
        if (this.authorityCheckerDisabled) {
            return authority;
        }
        return GrpcUtil.checkAuthority(authority);
    }

    public ManagedChannelImplBuilder disableCheckAuthority() {
        this.authorityCheckerDisabled = true;
        return this;
    }

    public ManagedChannelImplBuilder enableCheckAuthority() {
        this.authorityCheckerDisabled = false;
        return this;
    }

    public ManagedChannel build() {
        return new ManagedChannelOrphanWrapper(new ManagedChannelImpl(this, this.clientTransportFactoryBuilder.buildClientTransportFactory(), new ExponentialBackoffPolicy.Provider(), SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR), GrpcUtil.STOPWATCH_SUPPLIER, getEffectiveInterceptors(), TimeProvider.SYSTEM_TIME_PROVIDER));
    }

    /* access modifiers changed from: package-private */
    public List<ClientInterceptor> getEffectiveInterceptors() {
        List<ClientInterceptor> effectiveInterceptors = new ArrayList<>(this.interceptors);
        boolean isGlobalInterceptorsSet = false;
        List<ClientInterceptor> globalClientInterceptors = InternalGlobalInterceptors.getClientInterceptors();
        if (globalClientInterceptors != null) {
            effectiveInterceptors.addAll(globalClientInterceptors);
            isGlobalInterceptorsSet = true;
        }
        if (!isGlobalInterceptorsSet && this.statsEnabled) {
            ClientInterceptor statsInterceptor = null;
            if (GET_CLIENT_INTERCEPTOR_METHOD != null) {
                try {
                    statsInterceptor = (ClientInterceptor) GET_CLIENT_INTERCEPTOR_METHOD.invoke((Object) null, new Object[]{Boolean.valueOf(this.recordStartedRpcs), Boolean.valueOf(this.recordFinishedRpcs), Boolean.valueOf(this.recordRealTimeMetrics), Boolean.valueOf(this.recordRetryMetrics)});
                } catch (IllegalAccessException e) {
                    log.log(Level.FINE, "Unable to apply census stats", e);
                } catch (InvocationTargetException e2) {
                    log.log(Level.FINE, "Unable to apply census stats", e2);
                }
            }
            if (statsInterceptor != null) {
                effectiveInterceptors.add(0, statsInterceptor);
            }
        }
        if (!isGlobalInterceptorsSet && this.tracingEnabled) {
            ClientInterceptor tracingInterceptor = null;
            try {
                tracingInterceptor = (ClientInterceptor) Class.forName("io.grpc.census.InternalCensusTracingAccessor").getDeclaredMethod("getClientInterceptor", new Class[0]).invoke((Object) null, new Object[0]);
            } catch (ClassNotFoundException e3) {
                log.log(Level.FINE, "Unable to apply census stats", e3);
            } catch (NoSuchMethodException e4) {
                log.log(Level.FINE, "Unable to apply census stats", e4);
            } catch (IllegalAccessException e5) {
                log.log(Level.FINE, "Unable to apply census stats", e5);
            } catch (InvocationTargetException e6) {
                log.log(Level.FINE, "Unable to apply census stats", e6);
            }
            if (tracingInterceptor != null) {
                effectiveInterceptors.add(0, tracingInterceptor);
            }
        }
        return effectiveInterceptors;
    }

    /* access modifiers changed from: package-private */
    public int getDefaultPort() {
        return this.channelBuilderDefaultPortProvider.getDefaultPort();
    }

    private static class DirectAddressNameResolverProvider extends NameResolverProvider {
        final SocketAddress address;
        final String authority;
        final Collection<Class<? extends SocketAddress>> producedSocketAddressTypes;

        DirectAddressNameResolverProvider(SocketAddress address2, String authority2) {
            this.address = address2;
            this.authority = authority2;
            this.producedSocketAddressTypes = Collections.singleton(address2.getClass());
        }

        public NameResolver newNameResolver(URI notUsedUri, NameResolver.Args args) {
            return new NameResolver() {
                public String getServiceAuthority() {
                    return DirectAddressNameResolverProvider.this.authority;
                }

                public void start(NameResolver.Listener2 listener) {
                    listener.onResult(NameResolver.ResolutionResult.newBuilder().setAddresses(Collections.singletonList(new EquivalentAddressGroup(DirectAddressNameResolverProvider.this.address))).setAttributes(Attributes.EMPTY).build());
                }

                public void shutdown() {
                }
            };
        }

        public String getDefaultScheme() {
            return ManagedChannelImplBuilder.DIRECT_ADDRESS_SCHEME;
        }

        /* access modifiers changed from: protected */
        public boolean isAvailable() {
            return true;
        }

        /* access modifiers changed from: protected */
        public int priority() {
            return 5;
        }

        public Collection<Class<? extends SocketAddress>> getProducedSocketAddressTypes() {
            return this.producedSocketAddressTypes;
        }
    }

    public ObjectPool<? extends Executor> getOffloadExecutorPool() {
        return this.offloadExecutorPool;
    }
}
