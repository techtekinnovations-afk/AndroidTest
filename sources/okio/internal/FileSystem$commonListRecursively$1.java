package okio.internal;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;
import okio.FileSystem;
import okio.Path;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Lokio/Path;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "okio.internal.-FileSystem$commonListRecursively$1", f = "FileSystem.kt", i = {0, 0}, l = {96}, m = "invokeSuspend", n = {"$this$sequence", "stack"}, s = {"L$0", "L$1"})
/* renamed from: okio.internal.-FileSystem$commonListRecursively$1  reason: invalid class name */
/* compiled from: FileSystem.kt */
final class FileSystem$commonListRecursively$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Path $dir;
    final /* synthetic */ boolean $followSymlinks;
    final /* synthetic */ FileSystem $this_commonListRecursively;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FileSystem$commonListRecursively$1(Path path, FileSystem fileSystem, boolean z, Continuation<? super FileSystem$commonListRecursively$1> continuation) {
        super(2, continuation);
        this.$dir = path;
        this.$this_commonListRecursively = fileSystem;
        this.$followSymlinks = z;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FileSystem$commonListRecursively$1 r0 = new FileSystem$commonListRecursively$1(this.$dir, this.$this_commonListRecursively, this.$followSymlinks, continuation);
        r0.L$0 = obj;
        return r0;
    }

    public final Object invoke(SequenceScope<? super Path> sequenceScope, Continuation<? super Unit> continuation) {
        return ((FileSystem$commonListRecursively$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        ArrayDeque stack;
        SequenceScope $this$sequence;
        Iterator<Path> it;
        FileSystem$commonListRecursively$1 r1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                r1 = this;
                ArrayDeque stack2 = new ArrayDeque();
                stack2.addLast(r1.$dir);
                stack = stack2;
                $this$sequence = (SequenceScope) r1.L$0;
                it = r1.$this_commonListRecursively.list(r1.$dir).iterator();
                break;
            case 1:
                r1 = this;
                it = (Iterator) r1.L$2;
                ResultKt.throwOnFailure($result);
                stack = (ArrayDeque) r1.L$1;
                $this$sequence = (SequenceScope) r1.L$0;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (it.hasNext()) {
            FileSystem fileSystem = r1.$this_commonListRecursively;
            r1.L$0 = $this$sequence;
            r1.L$1 = stack;
            r1.L$2 = it;
            r1.label = 1;
            if (FileSystem.collectRecursively($this$sequence, fileSystem, stack, it.next(), r1.$followSymlinks, false, r1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
