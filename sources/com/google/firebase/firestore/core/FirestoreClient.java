package com.google.firebase.firestore.core;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.AggregateField;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.LoadBundleTask;
import com.google.firebase.firestore.TransactionOptions;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.bundle.BundleReader;
import com.google.firebase.firestore.bundle.BundleSerializer;
import com.google.firebase.firestore.bundle.NamedQuery;
import com.google.firebase.firestore.core.ComponentProvider;
import com.google.firebase.firestore.core.EventManager;
import com.google.firebase.firestore.local.IndexBackfiller;
import com.google.firebase.firestore.local.LocalStore;
import com.google.firebase.firestore.local.Persistence;
import com.google.firebase.firestore.local.QueryResult;
import com.google.firebase.firestore.local.Scheduler;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldIndex;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.remote.GrpcMetadataProvider;
import com.google.firebase.firestore.remote.RemoteSerializer;
import com.google.firebase.firestore.remote.RemoteStore;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Function;
import com.google.firebase.firestore.util.Logger;
import com.google.firestore.v1.Value;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class FirestoreClient {
    private static final String LOG_TAG = "FirestoreClient";
    private static final int MAX_CONCURRENT_LIMBO_RESOLUTIONS = 100;
    private final CredentialsProvider<String> appCheckProvider;
    private final AsyncQueue asyncQueue;
    private final CredentialsProvider<User> authProvider;
    private final BundleSerializer bundleSerializer;
    private final DatabaseInfo databaseInfo;
    private EventManager eventManager;
    private Scheduler gcScheduler;
    private Scheduler indexBackfillScheduler;
    private LocalStore localStore;
    private Persistence persistence;
    private RemoteStore remoteStore;
    private SyncEngine syncEngine;

    public FirestoreClient(Context context, DatabaseInfo databaseInfo2, CredentialsProvider<User> authProvider2, CredentialsProvider<String> appCheckProvider2, AsyncQueue asyncQueue2, GrpcMetadataProvider metadataProvider, ComponentProvider componentProvider) {
        this.databaseInfo = databaseInfo2;
        this.authProvider = authProvider2;
        this.appCheckProvider = appCheckProvider2;
        this.asyncQueue = asyncQueue2;
        this.bundleSerializer = new BundleSerializer(new RemoteSerializer(databaseInfo2.getDatabaseId()));
        TaskCompletionSource<User> firstUser = new TaskCompletionSource<>();
        AtomicBoolean initialized = new AtomicBoolean(false);
        asyncQueue2.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda10(this, firstUser, context, componentProvider, metadataProvider));
        authProvider2.setChangeListener(new FirestoreClient$$ExternalSyntheticLambda12(this, initialized, firstUser, asyncQueue2));
        appCheckProvider2.setChangeListener(new FirestoreClient$$ExternalSyntheticLambda13());
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1823lambda$new$0$comgooglefirebasefirestorecoreFirestoreClient(TaskCompletionSource firstUser, Context context, ComponentProvider componentProvider, GrpcMetadataProvider metadataProvider) {
        try {
            initialize(context, (User) Tasks.await(firstUser.getTask()), componentProvider, metadataProvider);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$2$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1825lambda$new$2$comgooglefirebasefirestorecoreFirestoreClient(AtomicBoolean initialized, TaskCompletionSource firstUser, AsyncQueue asyncQueue2, User user) {
        if (initialized.compareAndSet(false, true)) {
            Assert.hardAssert(true ^ firstUser.getTask().isComplete(), "Already fulfilled first user task", new Object[0]);
            firstUser.setResult(user);
            return;
        }
        asyncQueue2.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda6(this, user));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1824lambda$new$1$comgooglefirebasefirestorecoreFirestoreClient(User user) {
        Assert.hardAssert(this.syncEngine != null, "SyncEngine not yet initialized", new Object[0]);
        Logger.debug(LOG_TAG, "Credential changed. Current user: %s", user.getUid());
        this.syncEngine.handleCredentialChange(user);
    }

    static /* synthetic */ void lambda$new$3(String appCheckToken) {
    }

    public Task<Void> disableNetwork() {
        verifyNotTerminated();
        return this.asyncQueue.enqueue((Runnable) new FirestoreClient$$ExternalSyntheticLambda14(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$disableNetwork$4$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1816lambda$disableNetwork$4$comgooglefirebasefirestorecoreFirestoreClient() {
        this.remoteStore.disableNetwork();
    }

    public Task<Void> enableNetwork() {
        verifyNotTerminated();
        return this.asyncQueue.enqueue((Runnable) new FirestoreClient$$ExternalSyntheticLambda8(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$enableNetwork$5$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1817lambda$enableNetwork$5$comgooglefirebasefirestorecoreFirestoreClient() {
        this.remoteStore.enableNetwork();
    }

    public Task<Void> terminate() {
        this.authProvider.removeChangeListener();
        this.appCheckProvider.removeChangeListener();
        return this.asyncQueue.enqueueAndInitiateShutdown(new FirestoreClient$$ExternalSyntheticLambda9(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$terminate$6$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1830lambda$terminate$6$comgooglefirebasefirestorecoreFirestoreClient() {
        this.remoteStore.shutdown();
        this.persistence.shutdown();
        if (this.gcScheduler != null) {
            this.gcScheduler.stop();
        }
        if (this.indexBackfillScheduler != null) {
            this.indexBackfillScheduler.stop();
        }
    }

    public boolean isTerminated() {
        return this.asyncQueue.isShuttingDown();
    }

    public QueryListener listen(Query query, EventManager.ListenOptions options, EventListener<ViewSnapshot> listener) {
        verifyNotTerminated();
        QueryListener queryListener = new QueryListener(query, options, listener);
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda15(this, queryListener));
        return queryListener;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$listen$7$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1821lambda$listen$7$comgooglefirebasefirestorecoreFirestoreClient(QueryListener queryListener) {
        this.eventManager.addQueryListener(queryListener);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$stopListening$8$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1829lambda$stopListening$8$comgooglefirebasefirestorecoreFirestoreClient(QueryListener listener) {
        this.eventManager.removeQueryListener(listener);
    }

    public void stopListening(QueryListener listener) {
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda19(this, listener));
    }

    public Task<Document> getDocumentFromLocalCache(DocumentKey docKey) {
        verifyNotTerminated();
        return this.asyncQueue.enqueue(new FirestoreClient$$ExternalSyntheticLambda2(this, docKey)).continueWith(new FirestoreClient$$ExternalSyntheticLambda3());
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getDocumentFromLocalCache$9$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ Document m1818lambda$getDocumentFromLocalCache$9$comgooglefirebasefirestorecoreFirestoreClient(DocumentKey docKey) throws Exception {
        return this.localStore.readDocument(docKey);
    }

    static /* synthetic */ Document lambda$getDocumentFromLocalCache$10(Task result) throws Exception {
        Document document = (Document) result.getResult();
        if (document.isFoundDocument()) {
            return document;
        }
        if (document.isNoDocument()) {
            return null;
        }
        throw new FirebaseFirestoreException("Failed to get document from cache. (However, this document may exist on the server. Run again without setting source to CACHE to attempt to retrieve the document from the server.)", FirebaseFirestoreException.Code.UNAVAILABLE);
    }

    public Task<ViewSnapshot> getDocumentsFromLocalCache(Query query) {
        verifyNotTerminated();
        return this.asyncQueue.enqueue(new FirestoreClient$$ExternalSyntheticLambda0(this, query));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getDocumentsFromLocalCache$11$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ ViewSnapshot m1819lambda$getDocumentsFromLocalCache$11$comgooglefirebasefirestorecoreFirestoreClient(Query query) throws Exception {
        QueryResult queryResult = this.localStore.executeQuery(query, true);
        View view = new View(query, queryResult.getRemoteKeys());
        return view.applyChanges(view.computeDocChanges(queryResult.getDocuments())).getSnapshot();
    }

    public Task<Void> write(List<Mutation> mutations) {
        verifyNotTerminated();
        TaskCompletionSource<Void> source = new TaskCompletionSource<>();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda11(this, mutations, source));
        return source.getTask();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$write$12$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1833lambda$write$12$comgooglefirebasefirestorecoreFirestoreClient(List mutations, TaskCompletionSource source) {
        this.syncEngine.writeMutations(mutations, source);
    }

    public <TResult> Task<TResult> transaction(TransactionOptions options, Function<Transaction, Task<TResult>> updateFunction) {
        verifyNotTerminated();
        return AsyncQueue.callTask(this.asyncQueue.getExecutor(), new FirestoreClient$$ExternalSyntheticLambda23(this, options, updateFunction));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$transaction$13$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ Task m1831lambda$transaction$13$comgooglefirebasefirestorecoreFirestoreClient(TransactionOptions options, Function updateFunction) throws Exception {
        return this.syncEngine.transaction(this.asyncQueue, options, updateFunction);
    }

    public Task<Map<String, Value>> runAggregateQuery(Query query, List<AggregateField> aggregateFields) {
        verifyNotTerminated();
        TaskCompletionSource<Map<String, Value>> result = new TaskCompletionSource<>();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda16(this, query, aggregateFields, result));
        return result.getTask();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$runAggregateQuery$16$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1827lambda$runAggregateQuery$16$comgooglefirebasefirestorecoreFirestoreClient(Query query, List aggregateFields, TaskCompletionSource result) {
        this.syncEngine.runAggregateQuery(query, aggregateFields).addOnSuccessListener(new FirestoreClient$$ExternalSyntheticLambda20(result)).addOnFailureListener(new FirestoreClient$$ExternalSyntheticLambda21(result));
    }

    public Task<Void> waitForPendingWrites() {
        verifyNotTerminated();
        TaskCompletionSource<Void> source = new TaskCompletionSource<>();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda5(this, source));
        return source.getTask();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$waitForPendingWrites$17$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1832lambda$waitForPendingWrites$17$comgooglefirebasefirestorecoreFirestoreClient(TaskCompletionSource source) {
        this.syncEngine.registerPendingWritesTask(source);
    }

    private void initialize(Context context, User user, ComponentProvider provider, GrpcMetadataProvider metadataProvider) {
        Logger.debug(LOG_TAG, "Initializing. user=%s", user.getUid());
        provider.initialize(new ComponentProvider.Configuration(context, this.asyncQueue, this.databaseInfo, user, 100, this.authProvider, this.appCheckProvider, metadataProvider));
        this.persistence = provider.getPersistence();
        this.gcScheduler = provider.getGarbageCollectionScheduler();
        this.localStore = provider.getLocalStore();
        this.remoteStore = provider.getRemoteStore();
        this.syncEngine = provider.getSyncEngine();
        this.eventManager = provider.getEventManager();
        IndexBackfiller indexBackfiller = provider.getIndexBackfiller();
        if (this.gcScheduler != null) {
            this.gcScheduler.start();
        }
        if (indexBackfiller != null) {
            this.indexBackfillScheduler = indexBackfiller.getScheduler();
            this.indexBackfillScheduler.start();
        }
    }

    public void addSnapshotsInSyncListener(EventListener<Void> listener) {
        verifyNotTerminated();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda4(this, listener));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addSnapshotsInSyncListener$18$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1813lambda$addSnapshotsInSyncListener$18$comgooglefirebasefirestorecoreFirestoreClient(EventListener listener) {
        this.eventManager.addSnapshotsInSyncListener(listener);
    }

    public void loadBundle(InputStream bundleData, LoadBundleTask resultTask) {
        verifyNotTerminated();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda1(this, new BundleReader(this.bundleSerializer, bundleData), resultTask));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$loadBundle$19$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1822lambda$loadBundle$19$comgooglefirebasefirestorecoreFirestoreClient(BundleReader bundleReader, LoadBundleTask resultTask) {
        this.syncEngine.loadBundle(bundleReader, resultTask);
    }

    public Task<Query> getNamedQuery(String queryName) {
        verifyNotTerminated();
        TaskCompletionSource<Query> completionSource = new TaskCompletionSource<>();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda18(this, queryName, completionSource));
        return completionSource.getTask();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getNamedQuery$20$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1820lambda$getNamedQuery$20$comgooglefirebasefirestorecoreFirestoreClient(String queryName, TaskCompletionSource completionSource) {
        NamedQuery namedQuery = this.localStore.getNamedQuery(queryName);
        if (namedQuery != null) {
            Target target = namedQuery.getBundledQuery().getTarget();
            completionSource.setResult(new Query(target.getPath(), target.getCollectionGroup(), target.getFilters(), target.getOrderBy(), target.getLimit(), namedQuery.getBundledQuery().getLimitType(), target.getStartAt(), target.getEndAt()));
            return;
        }
        completionSource.setResult(null);
    }

    public Task<Void> configureFieldIndexes(List<FieldIndex> fieldIndices) {
        verifyNotTerminated();
        return this.asyncQueue.enqueue((Runnable) new FirestoreClient$$ExternalSyntheticLambda7(this, fieldIndices));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$configureFieldIndexes$21$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1814lambda$configureFieldIndexes$21$comgooglefirebasefirestorecoreFirestoreClient(List fieldIndices) {
        this.localStore.configureFieldIndexes(fieldIndices);
    }

    public void setIndexAutoCreationEnabled(boolean isEnabled) {
        verifyNotTerminated();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda22(this, isEnabled));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setIndexAutoCreationEnabled$22$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1828lambda$setIndexAutoCreationEnabled$22$comgooglefirebasefirestorecoreFirestoreClient(boolean isEnabled) {
        this.localStore.setIndexAutoCreationEnabled(isEnabled);
    }

    public void deleteAllFieldIndexes() {
        verifyNotTerminated();
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda24(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$deleteAllFieldIndexes$23$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1815lambda$deleteAllFieldIndexes$23$comgooglefirebasefirestorecoreFirestoreClient() {
        this.localStore.deleteAllFieldIndexes();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$removeSnapshotsInSyncListener$24$com-google-firebase-firestore-core-FirestoreClient  reason: not valid java name */
    public /* synthetic */ void m1826lambda$removeSnapshotsInSyncListener$24$comgooglefirebasefirestorecoreFirestoreClient(EventListener listener) {
        this.eventManager.removeSnapshotsInSyncListener(listener);
    }

    public void removeSnapshotsInSyncListener(EventListener<Void> listener) {
        this.asyncQueue.enqueueAndForget(new FirestoreClient$$ExternalSyntheticLambda17(this, listener));
    }

    private void verifyNotTerminated() {
        if (isTerminated()) {
            throw new IllegalStateException("The client has already been terminated");
        }
    }
}
