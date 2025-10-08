package androidx.lifecycle;

import androidx.arch.core.util.Function;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001aB\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u001c\u0010\u0005\u001a\u0018\u0012\t\u0012\u0007H\u0002¢\u0006\u0002\b\u0007\u0012\t\u0012\u0007H\u0004¢\u0006\u0002\b\u00070\u0006H\u0007\u001a8\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00040\tH\u0007\u001aJ\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00012$\u0010\u0005\u001a \u0012\t\u0012\u0007H\u0002¢\u0006\u0002\b\u0007\u0012\u0011\u0012\u000f\u0012\u0004\u0012\u0002H\u0004\u0018\u00010\u0001¢\u0006\u0002\b\u00070\u0006H\u0007\u001a>\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00010\tH\u0007¨\u0006\f"}, d2 = {"distinctUntilChanged", "Landroidx/lifecycle/LiveData;", "X", "map", "Y", "transform", "Lkotlin/Function1;", "Lkotlin/jvm/JvmSuppressWildcards;", "mapFunction", "Landroidx/arch/core/util/Function;", "switchMap", "switchMapFunction", "lifecycle-livedata_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: Transformations.kt */
public final class Transformations {
    public static final <X, Y> LiveData<Y> map(LiveData<X> $this$map, Function1<X, Y> transform) {
        Intrinsics.checkNotNullParameter($this$map, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        MediatorLiveData result = new MediatorLiveData();
        result.addSource($this$map, new Transformations$sam$androidx_lifecycle_Observer$0(new Transformations$map$1(result, transform)));
        return result;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Use kotlin functions, instead of outdated arch core Functions")
    public static final /* synthetic */ LiveData map(LiveData $this$map, Function mapFunction) {
        Intrinsics.checkNotNullParameter($this$map, "<this>");
        Intrinsics.checkNotNullParameter(mapFunction, "mapFunction");
        MediatorLiveData result = new MediatorLiveData();
        result.addSource($this$map, new Transformations$sam$androidx_lifecycle_Observer$0(new Transformations$map$2(result, mapFunction)));
        return result;
    }

    public static final <X, Y> LiveData<Y> switchMap(LiveData<X> $this$switchMap, Function1<X, LiveData<Y>> transform) {
        Intrinsics.checkNotNullParameter($this$switchMap, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        MediatorLiveData result = new MediatorLiveData();
        result.addSource($this$switchMap, new Transformations$switchMap$1(transform, result));
        return result;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Use kotlin functions, instead of outdated arch core Functions")
    public static final /* synthetic */ LiveData switchMap(LiveData $this$switchMap, Function switchMapFunction) {
        Intrinsics.checkNotNullParameter($this$switchMap, "<this>");
        Intrinsics.checkNotNullParameter(switchMapFunction, "switchMapFunction");
        MediatorLiveData result = new MediatorLiveData();
        result.addSource($this$switchMap, new Transformations$switchMap$2(switchMapFunction, result));
        return result;
    }

    public static final <X> LiveData<X> distinctUntilChanged(LiveData<X> $this$distinctUntilChanged) {
        Intrinsics.checkNotNullParameter($this$distinctUntilChanged, "<this>");
        MediatorLiveData outputLiveData = new MediatorLiveData();
        Ref.BooleanRef firstTime = new Ref.BooleanRef();
        firstTime.element = true;
        if ($this$distinctUntilChanged.isInitialized()) {
            outputLiveData.setValue($this$distinctUntilChanged.getValue());
            firstTime.element = false;
        }
        outputLiveData.addSource($this$distinctUntilChanged, new Transformations$sam$androidx_lifecycle_Observer$0(new Transformations$distinctUntilChanged$1(outputLiveData, firstTime)));
        return outputLiveData;
    }
}
