package androidx.datastore.core;

import androidx.datastore.core.MulticastFileObserver;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MulticastFileObserver$Companion$$ExternalSyntheticLambda0 implements DisposableHandle {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Function1 f$1;

    public /* synthetic */ MulticastFileObserver$Companion$$ExternalSyntheticLambda0(String str, Function1 function1) {
        this.f$0 = str;
        this.f$1 = function1;
    }

    public final void dispose() {
        MulticastFileObserver.Companion.observe$lambda$4(this.f$0, this.f$1);
    }
}
