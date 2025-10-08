package io.grpc.util;

import io.grpc.BindableService;
import io.grpc.HandlerRegistry;
import io.grpc.MethodDescriptor;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;

public final class MutableHandlerRegistry extends HandlerRegistry {
    private final ConcurrentMap<String, ServerServiceDefinition> services = new ConcurrentHashMap();

    @Nullable
    public ServerServiceDefinition addService(ServerServiceDefinition service) {
        return (ServerServiceDefinition) this.services.put(service.getServiceDescriptor().getName(), service);
    }

    @Nullable
    public ServerServiceDefinition addService(BindableService bindableService) {
        return addService(bindableService.bindService());
    }

    public boolean removeService(ServerServiceDefinition service) {
        return this.services.remove(service.getServiceDescriptor().getName(), service);
    }

    public List<ServerServiceDefinition> getServices() {
        return Collections.unmodifiableList(new ArrayList(this.services.values()));
    }

    @Nullable
    public ServerMethodDefinition<?, ?> lookupMethod(String methodName, @Nullable String authority) {
        ServerServiceDefinition service;
        String serviceName = MethodDescriptor.extractFullServiceName(methodName);
        if (serviceName == null || (service = (ServerServiceDefinition) this.services.get(serviceName)) == null) {
            return null;
        }
        return service.getMethod(methodName);
    }
}
