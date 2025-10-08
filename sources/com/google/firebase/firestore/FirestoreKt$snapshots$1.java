package com.google.firebase.firestore;

import com.google.firebase.firestore.util.Executors;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/google/firebase/firestore/DocumentSnapshot;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "com.google.firebase.firestore.FirestoreKt$snapshots$1", f = "Firestore.kt", i = {}, l = {243}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: Firestore.kt */
final class FirestoreKt$snapshots$1 extends SuspendLambda implements Function2<ProducerScope<? super DocumentSnapshot>, Continuation<? super Unit>, Object> {
    final /* synthetic */ MetadataChanges $metadataChanges;
    final /* synthetic */ DocumentReference $this_snapshots;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FirestoreKt$snapshots$1(DocumentReference documentReference, MetadataChanges metadataChanges, Continuation<? super FirestoreKt$snapshots$1> continuation) {
        super(2, continuation);
        this.$this_snapshots = documentReference;
        this.$metadataChanges = metadataChanges;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FirestoreKt$snapshots$1 firestoreKt$snapshots$1 = new FirestoreKt$snapshots$1(this.$this_snapshots, this.$metadataChanges, continuation);
        firestoreKt$snapshots$1.L$0 = obj;
        return firestoreKt$snapshots$1;
    }

    public final Object invoke(ProducerScope<? super DocumentSnapshot> producerScope, Continuation<? super Unit> continuation) {
        return ((FirestoreKt$snapshots$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ProducerScope $this$callbackFlow = (ProducerScope) this.L$0;
                ListenerRegistration registration = this.$this_snapshots.addSnapshotListener(Executors.BACKGROUND_EXECUTOR, this.$metadataChanges, (EventListener<DocumentSnapshot>) new FirestoreKt$snapshots$1$$ExternalSyntheticLambda0($this$callbackFlow));
                Intrinsics.checkNotNullExpressionValue(registration, "addSnapshotListener(...)");
                this.label = 1;
                if (ProduceKt.awaitClose($this$callbackFlow, new FirestoreKt$snapshots$1$$ExternalSyntheticLambda1(registration), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public static final void invokeSuspend$lambda$0(ProducerScope $$this$callbackFlow, DocumentSnapshot snapshot, FirebaseFirestoreException exception) {
        if (exception != null) {
            CoroutineScopeKt.cancel($$this$callbackFlow, "Error getting DocumentReference snapshot", exception);
        } else if (snapshot != null) {
            ChannelsKt.trySendBlocking($$this$callbackFlow, snapshot);
        }
    }

    /* access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$1(ListenerRegistration $registration) {
        $registration.remove();
        return Unit.INSTANCE;
    }
}
