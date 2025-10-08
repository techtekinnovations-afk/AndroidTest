package androidx.datastore.core;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0003\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u0001H@"}, d2 = {"<anonymous>", "T", "startingData"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2", f = "DataMigrationInitializer.kt", i = {0, 0}, l = {44, 46}, m = "invokeSuspend", n = {"migration", "data"}, s = {"L$2", "L$3"})
/* compiled from: DataMigrationInitializer.kt */
final class DataMigrationInitializer$Companion$runMigrations$2 extends SuspendLambda implements Function2<T, Continuation<? super T>, Object> {
    final /* synthetic */ List<Function1<Continuation<? super Unit>, Object>> $cleanUps;
    final /* synthetic */ List<DataMigration<T>> $migrations;
    /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataMigrationInitializer$Companion$runMigrations$2(List<? extends DataMigration<T>> list, List<Function1<Continuation<? super Unit>, Object>> list2, Continuation<? super DataMigrationInitializer$Companion$runMigrations$2> continuation) {
        super(2, continuation);
        this.$migrations = list;
        this.$cleanUps = list2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DataMigrationInitializer$Companion$runMigrations$2 dataMigrationInitializer$Companion$runMigrations$2 = new DataMigrationInitializer$Companion$runMigrations$2(this.$migrations, this.$cleanUps, continuation);
        dataMigrationInitializer$Companion$runMigrations$2.L$0 = obj;
        return dataMigrationInitializer$Companion$runMigrations$2;
    }

    public final Object invoke(T t, Continuation<? super T> continuation) {
        return ((DataMigrationInitializer$Companion$runMigrations$2) create(t, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: androidx.datastore.core.DataMigration} */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0070, code lost:
        if (r8 != r0) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0072, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0073, code lost:
        r10 = r0;
        r0 = r12;
        r12 = r8;
        r8 = r4;
        r4 = r2;
        r2 = r1;
        r1 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0080, code lost:
        if (((java.lang.Boolean) r12).booleanValue() == false) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0082, code lost:
        r8.add(new androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2$1$1(r7, (kotlin.coroutines.Continuation<? super androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2$1$1>) null));
        r2.L$0 = r8;
        r2.L$1 = r6;
        r2.L$2 = null;
        r2.L$3 = null;
        r2.label = 2;
        r12 = r7.migrate(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x009a, code lost:
        if (r12 != r1) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x009c, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x009d, code lost:
        r4 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x009e, code lost:
        r6 = r4;
        r4 = r12;
        r12 = r0;
        r0 = r1;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a4, code lost:
        r12 = r0;
        r0 = r1;
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a7, code lost:
        r2 = r4;
        r4 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ac, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0057, code lost:
        if (r6.hasNext() == false) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0059, code lost:
        r7 = r6.next();
        r1.L$0 = r4;
        r1.L$1 = r6;
        r1.L$2 = r7;
        r1.L$3 = r2;
        r1.label = 1;
        r8 = r7.shouldMigrate(r2, r1);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            switch(r1) {
                case 0: goto L_0x0041;
                case 1: goto L_0x0026;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L_0x0011:
            r1 = r11
            r2 = 0
            r3 = 0
            java.lang.Object r4 = r1.L$1
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r1.L$0
            java.util.List r5 = (java.util.List) r5
            kotlin.ResultKt.throwOnFailure(r12)
            r8 = r5
            r5 = r2
            r2 = r1
            r1 = r0
            r0 = r12
            goto L_0x009e
        L_0x0026:
            r1 = r11
            r2 = 0
            r3 = 0
            java.lang.Object r4 = r1.L$3
            java.lang.Object r5 = r1.L$2
            androidx.datastore.core.DataMigration r5 = (androidx.datastore.core.DataMigration) r5
            java.lang.Object r6 = r1.L$1
            java.util.Iterator r6 = (java.util.Iterator) r6
            java.lang.Object r7 = r1.L$0
            java.util.List r7 = (java.util.List) r7
            kotlin.ResultKt.throwOnFailure(r12)
            r8 = r7
            r7 = r5
            r5 = r2
            r2 = r1
            r1 = r0
            r0 = r12
            goto L_0x007a
        L_0x0041:
            kotlin.ResultKt.throwOnFailure(r12)
            r1 = r11
            java.lang.Object r2 = r1.L$0
            java.util.List<androidx.datastore.core.DataMigration<T>> r3 = r1.$migrations
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            java.util.List<kotlin.jvm.functions.Function1<kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object>> r4 = r1.$cleanUps
            r5 = 0
            java.util.Iterator r6 = r3.iterator()
        L_0x0053:
            boolean r3 = r6.hasNext()
            if (r3 == 0) goto L_0x00ab
            java.lang.Object r3 = r6.next()
            r7 = r3
            androidx.datastore.core.DataMigration r7 = (androidx.datastore.core.DataMigration) r7
            r3 = 0
            r1.L$0 = r4
            r1.L$1 = r6
            r1.L$2 = r7
            r1.L$3 = r2
            r8 = 1
            r1.label = r8
            java.lang.Object r8 = r7.shouldMigrate(r2, r1)
            if (r8 != r0) goto L_0x0073
            return r0
        L_0x0073:
            r10 = r0
            r0 = r12
            r12 = r8
            r8 = r4
            r4 = r2
            r2 = r1
            r1 = r10
        L_0x007a:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x00a4
            androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2$1$1 r12 = new androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2$1$1
            r9 = 0
            r12.<init>(r7, r9)
            r8.add(r12)
            r2.L$0 = r8
            r2.L$1 = r6
            r2.L$2 = r9
            r2.L$3 = r9
            r12 = 2
            r2.label = r12
            java.lang.Object r12 = r7.migrate(r4, r2)
            if (r12 != r1) goto L_0x009d
            return r1
        L_0x009d:
            r4 = r6
        L_0x009e:
            r6 = r4
            r4 = r12
            r12 = r0
            r0 = r1
            r1 = r2
            goto L_0x00a7
        L_0x00a4:
            r12 = r0
            r0 = r1
            r1 = r2
        L_0x00a7:
            r2 = r4
            r4 = r8
            goto L_0x0053
        L_0x00ab:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
