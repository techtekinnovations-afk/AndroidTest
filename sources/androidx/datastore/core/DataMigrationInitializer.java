package androidx.datastore.core;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u0000 \u0004*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u0004B\u0005¢\u0006\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Landroidx/datastore/core/DataMigrationInitializer;", "T", "", "()V", "Companion", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: DataMigrationInitializer.kt */
public final class DataMigrationInitializer<T> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JV\u0010\u0003\u001a3\b\u0001\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u0002H\u00060\u0005¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0004\"\u0004\b\u0001\u0010\u00062\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\u000e0\r¢\u0006\u0002\u0010\u000fJ6\u0010\u0010\u001a\u00020\u000b\"\u0004\b\u0001\u0010\u00062\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\u000e0\r2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005H@¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"Landroidx/datastore/core/DataMigrationInitializer$Companion;", "", "()V", "getInitializer", "Lkotlin/Function2;", "Landroidx/datastore/core/InitializerApi;", "T", "Lkotlin/ParameterName;", "name", "api", "Lkotlin/coroutines/Continuation;", "", "migrations", "", "Landroidx/datastore/core/DataMigration;", "(Ljava/util/List;)Lkotlin/jvm/functions/Function2;", "runMigrations", "(Ljava/util/List;Landroidx/datastore/core/InitializerApi;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: DataMigrationInitializer.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <T> Function2<InitializerApi<T>, Continuation<? super Unit>, Object> getInitializer(List<? extends DataMigration<T>> migrations) {
            Intrinsics.checkNotNullParameter(migrations, "migrations");
            return new DataMigrationInitializer$Companion$getInitializer$1(migrations, (Continuation<? super DataMigrationInitializer$Companion$getInitializer$1>) null);
        }

        /* access modifiers changed from: private */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Region traversal failed: Recursive call in traverseIterativeStepInternal method
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x003c  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0044  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        public final <T> java.lang.Object runMigrations(java.util.List<? extends androidx.datastore.core.DataMigration<T>> r8, androidx.datastore.core.InitializerApi<T> r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
            /*
                r7 = this;
                boolean r0 = r10 instanceof androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$1
                if (r0 == 0) goto L_0x0014
                r0 = r10
                androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$1 r0 = (androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0014
                int r10 = r0.label
                int r10 = r10 - r2
                r0.label = r10
                goto L_0x0019
            L_0x0014:
                androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$1 r0 = new androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$1
                r0.<init>(r7, r10)
            L_0x0019:
                java.lang.Object r10 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                switch(r2) {
                    case 0: goto L_0x0044;
                    case 1: goto L_0x003c;
                    case 2: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L_0x002c:
                r8 = 0
                r9 = 0
                java.lang.Object r2 = r0.L$1
                java.util.Iterator r2 = (java.util.Iterator) r2
                java.lang.Object r3 = r0.L$0
                kotlin.jvm.internal.Ref$ObjectRef r3 = (kotlin.jvm.internal.Ref.ObjectRef) r3
                kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x003a }
                goto L_0x008f
            L_0x003a:
                r4 = move-exception
                goto L_0x0094
            L_0x003c:
                java.lang.Object r8 = r0.L$0
                java.util.List r8 = (java.util.List) r8
                kotlin.ResultKt.throwOnFailure(r10)
                goto L_0x0063
            L_0x0044:
                kotlin.ResultKt.throwOnFailure(r10)
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                java.util.List r2 = (java.util.List) r2
                androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2 r3 = new androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2
                r4 = 0
                r3.<init>(r8, r2, r4)
                kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
                r0.L$0 = r2
                r4 = 1
                r0.label = r4
                java.lang.Object r8 = r9.updateData(r3, r0)
                if (r8 != r1) goto L_0x0062
                return r1
            L_0x0062:
                r8 = r2
            L_0x0063:
                kotlin.jvm.internal.Ref$ObjectRef r9 = new kotlin.jvm.internal.Ref$ObjectRef
                r9.<init>()
                java.lang.Iterable r8 = (java.lang.Iterable) r8
                r2 = 0
                java.util.Iterator r3 = r8.iterator()
                r8 = r2
                r2 = r3
                r3 = r9
            L_0x0072:
                boolean r9 = r2.hasNext()
                if (r9 == 0) goto L_0x00a7
                java.lang.Object r9 = r2.next()
                kotlin.jvm.functions.Function1 r9 = (kotlin.jvm.functions.Function1) r9
                r4 = 0
                r0.L$0 = r3     // Catch:{ all -> 0x0090 }
                r0.L$1 = r2     // Catch:{ all -> 0x0090 }
                r5 = 2
                r0.label = r5     // Catch:{ all -> 0x0090 }
                java.lang.Object r5 = r9.invoke(r0)     // Catch:{ all -> 0x0090 }
                if (r5 != r1) goto L_0x008e
                return r1
            L_0x008e:
                r9 = r4
            L_0x008f:
                goto L_0x00a5
            L_0x0090:
                r9 = move-exception
                r6 = r4
                r4 = r9
                r9 = r6
            L_0x0094:
                T r5 = r3.element
                if (r5 != 0) goto L_0x009b
                r3.element = r4
                goto L_0x00a5
            L_0x009b:
                T r5 = r3.element
                kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
                java.lang.Throwable r5 = (java.lang.Throwable) r5
                kotlin.ExceptionsKt.addSuppressed(r5, r4)
            L_0x00a5:
                goto L_0x0072
            L_0x00a7:
                T r8 = r3.element
                java.lang.Throwable r8 = (java.lang.Throwable) r8
                if (r8 != 0) goto L_0x00b1
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L_0x00b1:
                r9 = 0
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataMigrationInitializer.Companion.runMigrations(java.util.List, androidx.datastore.core.InitializerApi, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }
}
