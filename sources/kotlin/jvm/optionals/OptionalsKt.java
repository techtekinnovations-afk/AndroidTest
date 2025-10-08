package kotlin.jvm.optionals;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\"\n\u0000\u001a$\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0004H\u0007\u001a,\u0010\u0005\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\f\u0012\b\b\u0001\u0012\u0004\b\u0002H\u00020\u00042\u0006\u0010\u0006\u001a\u0002H\u0002H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001aC\u0010\b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\f\u0012\b\b\u0001\u0012\u0004\b\u0002H\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\bø\u0001\u0001ø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\n\u001a#\u0010\u000b\u001a\u0004\u0018\u0001H\u0002\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0007¢\u0006\u0002\u0010\f\u001a;\u0010\r\u001a\u0002H\u000e\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0010\b\u0001\u0010\u000e*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u000f*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0010\u001a\u0002H\u000eH\u0007¢\u0006\u0002\u0010\u0011\u001a$\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0013\"\b\b\u0000\u0010\u0002*\u00020\u0003*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0004H\u0007\u001a$\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\b\b\u0000\u0010\u0002*\u00020\u0003*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0004H\u0007\u0002\u000b\n\u0002\b9\n\u0005\b20\u0001¨\u0006\u0016"}, d2 = {"asSequence", "Lkotlin/sequences/Sequence;", "T", "", "Ljava/util/Optional;", "getOrDefault", "defaultValue", "(Ljava/util/Optional;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Ljava/util/Optional;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrNull", "(Ljava/util/Optional;)Ljava/lang/Object;", "toCollection", "C", "", "destination", "(Ljava/util/Optional;Ljava/util/Collection;)Ljava/util/Collection;", "toList", "", "toSet", "", "kotlin-stdlib-jdk8"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* compiled from: Optionals.kt */
public final class OptionalsKt {
    public static final <T> T getOrNull(Optional<T> $this$getOrNull) {
        Intrinsics.checkNotNullParameter($this$getOrNull, "<this>");
        return $this$getOrNull.orElse((Object) null);
    }

    public static final <T> T getOrDefault(Optional<? extends T> $this$getOrDefault, T defaultValue) {
        Intrinsics.checkNotNullParameter($this$getOrDefault, "<this>");
        return $this$getOrDefault.isPresent() ? $this$getOrDefault.get() : defaultValue;
    }

    public static final <T> T getOrElse(Optional<? extends T> $this$getOrElse, Function0<? extends T> defaultValue) {
        Intrinsics.checkNotNullParameter($this$getOrElse, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return $this$getOrElse.isPresent() ? $this$getOrElse.get() : defaultValue.invoke();
    }

    public static final <T, C extends Collection<? super T>> C toCollection(Optional<T> $this$toCollection, C destination) {
        Intrinsics.checkNotNullParameter($this$toCollection, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        if ($this$toCollection.isPresent()) {
            T t = $this$toCollection.get();
            Intrinsics.checkNotNullExpressionValue(t, "get(...)");
            destination.add(t);
        }
        return destination;
    }

    public static final <T> List<T> toList(Optional<? extends T> $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "<this>");
        return $this$toList.isPresent() ? CollectionsKt.listOf($this$toList.get()) : CollectionsKt.emptyList();
    }

    public static final <T> Set<T> toSet(Optional<? extends T> $this$toSet) {
        Intrinsics.checkNotNullParameter($this$toSet, "<this>");
        return $this$toSet.isPresent() ? SetsKt.setOf($this$toSet.get()) : SetsKt.emptySet();
    }

    public static final <T> Sequence<T> asSequence(Optional<? extends T> $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
        return $this$asSequence.isPresent() ? SequencesKt.sequenceOf($this$asSequence.get()) : SequencesKt.emptySequence();
    }
}
