package io.grpc.internal;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import java.net.URI;

public class NameResolverFactoryToProviderFacade extends NameResolverProvider {
    private NameResolver.Factory factory;

    NameResolverFactoryToProviderFacade(NameResolver.Factory factory2) {
        this.factory = factory2;
    }

    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        return this.factory.newNameResolver(targetUri, args);
    }

    public String getDefaultScheme() {
        return this.factory.getDefaultScheme();
    }

    /* access modifiers changed from: protected */
    public boolean isAvailable() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int priority() {
        return 5;
    }
}
