package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

@Metadata(d1 = {"\u0000`\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H@¢\u0006\u0002\u0010\u0005\u001ab\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e¢\u0006\u0002\b\u0011H\u0007¢\u0006\u0002\u0010\u0012\u001a\u0001\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\u00142-\b\u0002\u0010\u0015\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\u0018¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0017j\u0004\u0018\u0001`\u00162/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e¢\u0006\u0002\b\u0011H\u0007¢\u0006\u0002\u0010\u001c\u001a¥\u0001\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u0013\u001a\u00020\u00142-\b\u0002\u0010\u0015\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\u0018¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0017j\u0004\u0018\u0001`\u00162/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e¢\u0006\u0002\b\u0011H\u0000¢\u0006\u0002\u0010\u001f¨\u0006 "}, d2 = {"awaitClose", "", "Lkotlinx/coroutines/channels/ProducerScope;", "block", "Lkotlin/Function0;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produce", "Lkotlinx/coroutines/channels/ReceiveChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "start", "Lkotlinx/coroutines/CoroutineStart;", "onCompletion", "Lkotlinx/coroutines/CompletionHandler;", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Produce.kt */
public final class ProduceKt {
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlin.jvm.functions.Function0<kotlin.Unit>, code=kotlin.jvm.functions.Function0, for r11v0, types: [kotlin.jvm.functions.Function0<kotlin.Unit>, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object awaitClose(kotlinx.coroutines.channels.ProducerScope<?> r10, kotlin.jvm.functions.Function0 r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ProduceKt$awaitClose$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.ProduceKt$awaitClose$1 r0 = (kotlinx.coroutines.channels.ProduceKt$awaitClose$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ProduceKt$awaitClose$1 r0 = new kotlinx.coroutines.channels.ProduceKt$awaitClose$1
            r0.<init>(r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            r10 = 0
            java.lang.Object r11 = r0.L$1
            kotlin.jvm.functions.Function0 r11 = (kotlin.jvm.functions.Function0) r11
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0039 }
            goto L_0x008e
        L_0x0039:
            r10 = move-exception
            goto L_0x0096
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r1)
            kotlin.coroutines.CoroutineContext r3 = r0.getContext()
            kotlinx.coroutines.Job$Key r4 = kotlinx.coroutines.Job.Key
            kotlin.coroutines.CoroutineContext$Key r4 = (kotlin.coroutines.CoroutineContext.Key) r4
            kotlin.coroutines.CoroutineContext$Element r3 = r3.get(r4)
            r4 = 1
            if (r3 != r10) goto L_0x004f
            r3 = r4
            goto L_0x0050
        L_0x004f:
            r3 = 0
        L_0x0050:
            if (r3 == 0) goto L_0x009a
            r3 = 0
            r0.L$0 = r10     // Catch:{ all -> 0x0039 }
            r0.L$1 = r11     // Catch:{ all -> 0x0039 }
            r0.label = r4     // Catch:{ all -> 0x0039 }
            r5 = r0
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5     // Catch:{ all -> 0x0039 }
            r6 = 0
            kotlinx.coroutines.CancellableContinuationImpl r7 = new kotlinx.coroutines.CancellableContinuationImpl     // Catch:{ all -> 0x0039 }
            kotlin.coroutines.Continuation r8 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r5)     // Catch:{ all -> 0x0039 }
            r7.<init>(r8, r4)     // Catch:{ all -> 0x0039 }
            r7.initCancellability()     // Catch:{ all -> 0x0039 }
            r4 = r7
            kotlinx.coroutines.CancellableContinuation r4 = (kotlinx.coroutines.CancellableContinuation) r4     // Catch:{ all -> 0x0039 }
            r8 = 0
            kotlinx.coroutines.channels.ProduceKt$awaitClose$4$1 r9 = new kotlinx.coroutines.channels.ProduceKt$awaitClose$4$1     // Catch:{ all -> 0x0039 }
            r9.<init>(r4)     // Catch:{ all -> 0x0039 }
            kotlin.jvm.functions.Function1 r9 = (kotlin.jvm.functions.Function1) r9     // Catch:{ all -> 0x0039 }
            r10.invokeOnClose(r9)     // Catch:{ all -> 0x0039 }
            java.lang.Object r10 = r7.getResult()     // Catch:{ all -> 0x0039 }
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch:{ all -> 0x0039 }
            if (r10 != r4) goto L_0x008a
            r4 = r0
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4     // Catch:{ all -> 0x0039 }
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r4)     // Catch:{ all -> 0x0039 }
        L_0x008a:
            if (r10 != r2) goto L_0x008d
            return r2
        L_0x008d:
            r10 = r3
        L_0x008e:
            r11.invoke()
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L_0x0096:
            r11.invoke()
            throw r10
        L_0x009a:
            r10 = 0
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "awaitClose() can only be invoked from the producer context"
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ProduceKt.awaitClose(kotlinx.coroutines.channels.ProducerScope, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object awaitClose$default(ProducerScope producerScope, Function0 function0, Continuation continuation, int i, Object obj) {
        if ((i & 1) != 0) {
            new ProduceKt$$ExternalSyntheticLambda0
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0006: CONSTRUCTOR  (r1v2 ? I:kotlinx.coroutines.channels.ProduceKt$$ExternalSyntheticLambda0) =  call: kotlinx.coroutines.channels.ProduceKt$$ExternalSyntheticLambda0.<init>():void type: CONSTRUCTOR in method: kotlinx.coroutines.channels.ProduceKt.awaitClose$default(kotlinx.coroutines.channels.ProducerScope, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation, int, java.lang.Object):java.lang.Object, dex: classes5.dex
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.util.ArrayList.forEach(ArrayList.java:1259)
                	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:483)
                	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
                	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:485)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r1v2 ?
                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:189)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:620)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	... 34 more
                */
            /*
                r3 = r3 & 1
                if (r3 == 0) goto L_0x0009
                kotlinx.coroutines.channels.ProduceKt$$ExternalSyntheticLambda0 r1 = new kotlinx.coroutines.channels.ProduceKt$$ExternalSyntheticLambda0
                r1.<init>()
            L_0x0009:
                java.lang.Object r0 = awaitClose(r0, r1, r2)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ProduceKt.awaitClose$default(kotlinx.coroutines.channels.ProducerScope, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation, int, java.lang.Object):java.lang.Object");
        }

        /* access modifiers changed from: private */
        public static final Unit awaitClose$lambda$0() {
            return Unit.INSTANCE;
        }

        public static /* synthetic */ ReceiveChannel produce$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, int i, Function2 function2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                coroutineContext = EmptyCoroutineContext.INSTANCE;
            }
            if ((i2 & 2) != 0) {
                i = 0;
            }
            return produce(coroutineScope, coroutineContext, i, function2);
        }

        public static final <E> ReceiveChannel<E> produce(CoroutineScope $this$produce, CoroutineContext context, int capacity, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> block) {
            return produce($this$produce, context, capacity, BufferOverflow.SUSPEND, CoroutineStart.DEFAULT, (Function1<? super Throwable, Unit>) null, block);
        }

        public static /* synthetic */ ReceiveChannel produce$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, int i, CoroutineStart coroutineStart, Function1 function1, Function2 function2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                coroutineContext = EmptyCoroutineContext.INSTANCE;
            }
            if ((i2 & 2) != 0) {
                i = 0;
            }
            if ((i2 & 4) != 0) {
                coroutineStart = CoroutineStart.DEFAULT;
            }
            return produce(coroutineScope, coroutineContext, i, coroutineStart, (i2 & 8) != 0 ? null : function1, function2);
        }

        public static final <E> ReceiveChannel<E> produce(CoroutineScope $this$produce, CoroutineContext context, int capacity, CoroutineStart start, Function1<? super Throwable, Unit> onCompletion, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> block) {
            return produce($this$produce, context, capacity, BufferOverflow.SUSPEND, start, onCompletion, block);
        }

        public static /* synthetic */ ReceiveChannel produce$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow, CoroutineStart coroutineStart, Function1 function1, Function2 function2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                coroutineContext = EmptyCoroutineContext.INSTANCE;
            }
            if ((i2 & 2) != 0) {
                i = 0;
            }
            if ((i2 & 4) != 0) {
                bufferOverflow = BufferOverflow.SUSPEND;
            }
            if ((i2 & 8) != 0) {
                coroutineStart = CoroutineStart.DEFAULT;
            }
            Function2 function22 = function2;
            CoroutineStart coroutineStart2 = coroutineStart;
            return produce(coroutineScope, coroutineContext, i, bufferOverflow, coroutineStart2, (i2 & 16) != 0 ? null : function1, function22);
        }

        public static final <E> ReceiveChannel<E> produce(CoroutineScope $this$produce, CoroutineContext context, int capacity, BufferOverflow onBufferOverflow, CoroutineStart start, Function1<? super Throwable, Unit> onCompletion, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> block) {
            ProducerCoroutine coroutine = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext($this$produce, context), ChannelKt.Channel$default(capacity, onBufferOverflow, (Function1) null, 4, (Object) null));
            if (onCompletion != null) {
                coroutine.invokeOnCompletion(onCompletion);
            }
            coroutine.start(start, coroutine, block);
            return coroutine;
        }
    }
