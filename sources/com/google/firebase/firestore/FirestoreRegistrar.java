package com.google.firebase.firestore;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.firestore.remote.FirebaseClientGrpcMetadataProvider;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.util.Arrays;
import java.util.List;

public class FirestoreRegistrar implements ComponentRegistrar {
    private static final String LIBRARY_NAME = "fire-fst";

    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(FirestoreMultiDbComponent.class).name(LIBRARY_NAME).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.required((Class<?>) Context.class)).add(Dependency.optionalProvider((Class<?>) HeartBeatInfo.class)).add(Dependency.optionalProvider((Class<?>) UserAgentPublisher.class)).add(Dependency.deferred((Class<?>) InternalAuthProvider.class)).add(Dependency.deferred((Class<?>) InteropAppCheckTokenProvider.class)).add(Dependency.optional(FirebaseOptions.class)).factory(new FirestoreRegistrar$$ExternalSyntheticLambda0()).build(), LibraryVersionComponent.create(LIBRARY_NAME, BuildConfig.VERSION_NAME)});
    }

    static /* synthetic */ FirestoreMultiDbComponent lambda$getComponents$0(ComponentContainer c) {
        return new FirestoreMultiDbComponent((Context) c.get(Context.class), (FirebaseApp) c.get(FirebaseApp.class), c.getDeferred(InternalAuthProvider.class), c.getDeferred(InteropAppCheckTokenProvider.class), new FirebaseClientGrpcMetadataProvider(c.getProvider(UserAgentPublisher.class), c.getProvider(HeartBeatInfo.class), (FirebaseOptions) c.get(FirebaseOptions.class)));
    }
}
