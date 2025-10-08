package com.google.firebase.firestore;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.MemoryEagerGcSettings;
import com.google.firebase.firestore.MemoryLruGcSettings;
import com.google.firebase.firestore.PersistentCacheSettings;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

@Metadata(d1 = {"\u0000 \u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u001c\u0010\t\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\u000bH\b¢\u0006\u0002\u0010\f\u001a$\u0010\t\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\b¢\u0006\u0002\u0010\u000f\u001a$\u0010\u0010\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\bH\b¢\u0006\u0002\u0010\u0012\u001a,\u0010\u0010\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\b¢\u0006\u0002\u0010\u0013\u001a$\u0010\u0010\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\b¢\u0006\u0002\u0010\u0016\u001a,\u0010\u0010\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u000eH\b¢\u0006\u0002\u0010\u0017\u001a\u001e\u0010\t\u001a\u0002H\n\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u0018*\u00020\u0019H\b¢\u0006\u0002\u0010\u001a\u001a&\u0010\t\u001a\u0002H\n\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u0018*\u00020\u00192\u0006\u0010\r\u001a\u00020\u000eH\b¢\u0006\u0002\u0010\u001b\u001a\u001f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\n0\u001d\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u0018*\u00020\u001eH\b\u001a'\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\n0\u001d\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u0018*\u00020\u001e2\u0006\u0010\r\u001a\u00020\u000eH\b\u001a\u001f\u0010\u001f\u001a\u00020 2\u0017\u0010!\u001a\u0013\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\"¢\u0006\u0002\b%\u001a\u001f\u0010&\u001a\u00020'2\u0017\u0010!\u001a\u0013\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020$0\"¢\u0006\u0002\b%\u001a\u001f\u0010)\u001a\u00020*2\u0017\u0010!\u001a\u0013\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020$0\"¢\u0006\u0002\b%\u001a\u001f\u0010,\u001a\u00020-2\u0017\u0010!\u001a\u0013\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020$0\"¢\u0006\u0002\b%\u001a\u001f\u0010/\u001a\u0002002\u0017\u0010!\u001a\u0013\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020$0\"¢\u0006\u0002\b%\u001a\u001a\u00102\u001a\b\u0012\u0004\u0012\u00020\u000b03*\u0002042\b\b\u0002\u00105\u001a\u000206\u001a\u001a\u00102\u001a\b\u0012\u0004\u0012\u00020\u001e03*\u0002072\b\b\u0002\u00105\u001a\u000206\u001a/\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\u001d03\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u0018*\u0002072\b\b\u0002\u00105\u001a\u000206H\b\u001a+\u00108\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\n03\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u0018*\u0002042\b\b\u0002\u00105\u001a\u000206H\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u00069"}, d2 = {"firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "Lcom/google/firebase/Firebase;", "getFirestore", "(Lcom/google/firebase/Firebase;)Lcom/google/firebase/firestore/FirebaseFirestore;", "app", "Lcom/google/firebase/FirebaseApp;", "database", "", "toObject", "T", "Lcom/google/firebase/firestore/DocumentSnapshot;", "(Lcom/google/firebase/firestore/DocumentSnapshot;)Ljava/lang/Object;", "serverTimestampBehavior", "Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "getField", "field", "(Lcom/google/firebase/firestore/DocumentSnapshot;Ljava/lang/String;)Ljava/lang/Object;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Ljava/lang/String;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "fieldPath", "Lcom/google/firebase/firestore/FieldPath;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Lcom/google/firebase/firestore/FieldPath;)Ljava/lang/Object;", "(Lcom/google/firebase/firestore/DocumentSnapshot;Lcom/google/firebase/firestore/FieldPath;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "", "Lcom/google/firebase/firestore/QueryDocumentSnapshot;", "(Lcom/google/firebase/firestore/QueryDocumentSnapshot;)Ljava/lang/Object;", "(Lcom/google/firebase/firestore/QueryDocumentSnapshot;Lcom/google/firebase/firestore/DocumentSnapshot$ServerTimestampBehavior;)Ljava/lang/Object;", "toObjects", "", "Lcom/google/firebase/firestore/QuerySnapshot;", "firestoreSettings", "Lcom/google/firebase/firestore/FirebaseFirestoreSettings;", "init", "Lkotlin/Function1;", "Lcom/google/firebase/firestore/FirebaseFirestoreSettings$Builder;", "", "Lkotlin/ExtensionFunctionType;", "memoryCacheSettings", "Lcom/google/firebase/firestore/MemoryCacheSettings;", "Lcom/google/firebase/firestore/MemoryCacheSettings$Builder;", "memoryEagerGcSettings", "Lcom/google/firebase/firestore/MemoryEagerGcSettings;", "Lcom/google/firebase/firestore/MemoryEagerGcSettings$Builder;", "memoryLruGcSettings", "Lcom/google/firebase/firestore/MemoryLruGcSettings;", "Lcom/google/firebase/firestore/MemoryLruGcSettings$Builder;", "persistentCacheSettings", "Lcom/google/firebase/firestore/PersistentCacheSettings;", "Lcom/google/firebase/firestore/PersistentCacheSettings$Builder;", "snapshots", "Lkotlinx/coroutines/flow/Flow;", "Lcom/google/firebase/firestore/DocumentReference;", "metadataChanges", "Lcom/google/firebase/firestore/MetadataChanges;", "Lcom/google/firebase/firestore/Query;", "dataObjects", "com.google.firebase-firebase-firestore"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Firestore.kt */
public final class FirestoreKt {
    public static final FirebaseFirestore getFirestore(Firebase $this$firestore) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseFirestore firestore(Firebase $this$firestore, FirebaseApp app) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        FirebaseFirestore instance = FirebaseFirestore.getInstance(app);
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseFirestore firestore(Firebase $this$firestore, FirebaseApp app, String database) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(database, "database");
        FirebaseFirestore instance = FirebaseFirestore.getInstance(app, database);
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseFirestore firestore(Firebase $this$firestore, String database) {
        Intrinsics.checkNotNullParameter($this$firestore, "<this>");
        Intrinsics.checkNotNullParameter(database, "database");
        FirebaseFirestore instance = FirebaseFirestore.getInstance(database);
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final /* synthetic */ <T> T toObject(DocumentSnapshot $this$toObject) {
        Intrinsics.checkNotNullParameter($this$toObject, "<this>");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$toObject.toObject(cls);
    }

    public static final /* synthetic */ <T> T toObject(DocumentSnapshot $this$toObject, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter($this$toObject, "<this>");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$toObject.toObject(cls, serverTimestampBehavior);
    }

    public static final /* synthetic */ <T> T getField(DocumentSnapshot $this$getField, String field) {
        Intrinsics.checkNotNullParameter($this$getField, "<this>");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$getField.get(field, cls);
    }

    public static final /* synthetic */ <T> T getField(DocumentSnapshot $this$getField, String field, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter($this$getField, "<this>");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$getField.get(field, cls, serverTimestampBehavior);
    }

    public static final /* synthetic */ <T> T getField(DocumentSnapshot $this$getField, FieldPath fieldPath) {
        Intrinsics.checkNotNullParameter($this$getField, "<this>");
        Intrinsics.checkNotNullParameter(fieldPath, "fieldPath");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$getField.get(fieldPath, cls);
    }

    public static final /* synthetic */ <T> T getField(DocumentSnapshot $this$getField, FieldPath fieldPath, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter($this$getField, "<this>");
        Intrinsics.checkNotNullParameter(fieldPath, "fieldPath");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$getField.get(fieldPath, cls, serverTimestampBehavior);
    }

    public static final /* synthetic */ <T> T toObject(QueryDocumentSnapshot $this$toObject) {
        Intrinsics.checkNotNullParameter($this$toObject, "<this>");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$toObject.toObject(cls);
    }

    public static final /* synthetic */ <T> T toObject(QueryDocumentSnapshot $this$toObject, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter($this$toObject, "<this>");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        return $this$toObject.toObject(cls, serverTimestampBehavior);
    }

    public static final /* synthetic */ <T> List<T> toObjects(QuerySnapshot $this$toObjects) {
        Intrinsics.checkNotNullParameter($this$toObjects, "<this>");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        List<T> objects = $this$toObjects.toObjects(cls);
        Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
        List list = objects;
        return objects;
    }

    public static final /* synthetic */ <T> List<T> toObjects(QuerySnapshot $this$toObjects, DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior) {
        Intrinsics.checkNotNullParameter($this$toObjects, "<this>");
        Intrinsics.checkNotNullParameter(serverTimestampBehavior, "serverTimestampBehavior");
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        List<T> objects = $this$toObjects.toObjects(cls, serverTimestampBehavior);
        Intrinsics.checkNotNullExpressionValue(objects, "toObjects(...)");
        List list = objects;
        return objects;
    }

    public static final FirebaseFirestoreSettings firestoreSettings(Function1<? super FirebaseFirestoreSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirebaseFirestoreSettings.Builder builder = new FirebaseFirestoreSettings.Builder();
        init.invoke(builder);
        FirebaseFirestoreSettings build = builder.build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    public static final MemoryCacheSettings memoryCacheSettings(Function1<? super MemoryCacheSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        MemoryCacheSettings.Builder builder = MemoryCacheSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder(...)");
        init.invoke(builder);
        MemoryCacheSettings build = builder.build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    public static final MemoryEagerGcSettings memoryEagerGcSettings(Function1<? super MemoryEagerGcSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        MemoryEagerGcSettings.Builder builder = MemoryEagerGcSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder(...)");
        init.invoke(builder);
        MemoryEagerGcSettings build = builder.build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    public static final MemoryLruGcSettings memoryLruGcSettings(Function1<? super MemoryLruGcSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        MemoryLruGcSettings.Builder builder = MemoryLruGcSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder(...)");
        init.invoke(builder);
        MemoryLruGcSettings build = builder.build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    public static final PersistentCacheSettings persistentCacheSettings(Function1<? super PersistentCacheSettings.Builder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        PersistentCacheSettings.Builder builder = PersistentCacheSettings.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builder, "newBuilder(...)");
        init.invoke(builder);
        PersistentCacheSettings build = builder.build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }

    public static /* synthetic */ Flow snapshots$default(DocumentReference documentReference, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        return snapshots(documentReference, metadataChanges);
    }

    public static final Flow<DocumentSnapshot> snapshots(DocumentReference $this$snapshots, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$snapshots, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        return FlowKt.callbackFlow(new FirestoreKt$snapshots$1($this$snapshots, metadataChanges, (Continuation<? super FirestoreKt$snapshots$1>) null));
    }

    public static /* synthetic */ Flow snapshots$default(Query query, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        return snapshots(query, metadataChanges);
    }

    public static final Flow<QuerySnapshot> snapshots(Query $this$snapshots, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$snapshots, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        return FlowKt.callbackFlow(new FirestoreKt$snapshots$2($this$snapshots, metadataChanges, (Continuation<? super FirestoreKt$snapshots$2>) null));
    }

    public static /* synthetic */ Flow dataObjects$default(Query $this$dataObjects_u24default, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        Intrinsics.checkNotNullParameter($this$dataObjects_u24default, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$1(snapshots($this$dataObjects_u24default, metadataChanges));
    }

    public static final /* synthetic */ <T> Flow<List<T>> dataObjects(Query $this$dataObjects, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$dataObjects, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$1(snapshots($this$dataObjects, metadataChanges));
    }

    public static /* synthetic */ Flow dataObjects$default(DocumentReference $this$dataObjects_u24default, MetadataChanges metadataChanges, int i, Object obj) {
        if ((i & 1) != 0) {
            metadataChanges = MetadataChanges.EXCLUDE;
        }
        Intrinsics.checkNotNullParameter($this$dataObjects_u24default, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$2(snapshots($this$dataObjects_u24default, metadataChanges));
    }

    public static final /* synthetic */ <T> Flow<T> dataObjects(DocumentReference $this$dataObjects, MetadataChanges metadataChanges) {
        Intrinsics.checkNotNullParameter($this$dataObjects, "<this>");
        Intrinsics.checkNotNullParameter(metadataChanges, "metadataChanges");
        Intrinsics.needClassReification();
        return new FirestoreKt$dataObjects$$inlined$map$2(snapshots($this$dataObjects, metadataChanges));
    }
}
