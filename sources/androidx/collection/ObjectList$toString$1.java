package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "E", "element", "invoke", "(Ljava/lang/Object;)Ljava/lang/CharSequence;"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: ObjectList.kt */
final class ObjectList$toString$1 extends Lambda implements Function1<E, CharSequence> {
    final /* synthetic */ ObjectList<E> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ObjectList$toString$1(ObjectList<E> objectList) {
        super(1);
        this.this$0 = objectList;
    }

    public final CharSequence invoke(E element) {
        if (element == this.this$0) {
            return "(this)";
        }
        return String.valueOf(element);
    }
}
