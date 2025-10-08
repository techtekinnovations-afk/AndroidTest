package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlinx.coroutines.CopyableThrowable;

@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u001a!\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\u00042\u0006\u0010\t\u001a\u0002H\bH\u0000¢\u0006\u0002\u0010\n\u001a7\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003j\u0002`\f\"\b\b\u0000\u0010\b*\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\b0\u000eH\u0002¢\u0006\u0002\u0010\u000f\u001a3\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003j\u0002`\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003H\u0002¢\u0006\u0002\u0010\u0012\u001a\u0018\u0010\u0013\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0014\u001a\u00020\u0001H\u0002\u001a\u001b\u0010\u0015\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u000e2\b\b\u0002\u0010\u0016\u001a\u00020\u0001H\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0002\"\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00032\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¨\u0006\u0017"}, d2 = {"throwableFields", "", "Ctor", "Lkotlin/Function1;", "", "ctorCache", "Lkotlinx/coroutines/internal/CtorCache;", "tryCopyException", "E", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "createConstructor", "Lkotlinx/coroutines/internal/Ctor;", "clz", "Ljava/lang/Class;", "(Ljava/lang/Class;)Lkotlin/jvm/functions/Function1;", "safeCtor", "block", "(Lkotlin/jvm/functions/Function1;)Lkotlin/jvm/functions/Function1;", "fieldsCountOrDefault", "defaultValue", "fieldsCount", "accumulator", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: ExceptionsConstructor.kt */
public final class ExceptionsConstructorKt {
    private static final CtorCache ctorCache;
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);

    static {
        CtorCache ctorCache2;
        try {
            if (FastServiceLoaderKt.getANDROID_DETECTED()) {
                ctorCache2 = WeakMapCtorCache.INSTANCE;
            } else {
                ctorCache2 = ClassValueCtorCache.INSTANCE;
            }
        } catch (Throwable th) {
            ctorCache2 = WeakMapCtorCache.INSTANCE;
        }
        ctorCache = ctorCache2;
    }

    public static final <E extends Throwable> E tryCopyException(E exception) {
        E e;
        if (!(exception instanceof CopyableThrowable)) {
            return (Throwable) ctorCache.get(exception.getClass()).invoke(exception);
        }
        try {
            Result.Companion companion = Result.Companion;
            e = Result.m6constructorimpl(((CopyableThrowable) exception).createCopy());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            e = Result.m6constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m12isFailureimpl(e)) {
            e = null;
        }
        return (Throwable) e;
    }

    /* access modifiers changed from: private */
    public static final <E extends Throwable> Function1<Throwable, Throwable> createConstructor(Class<E> clz) {
        Function1<Throwable, Throwable> function1;
        int i;
        Pair<A, B> pair;
        Function1 nullResult = ExceptionsConstructorKt$createConstructor$nullResult$1.INSTANCE;
        int i2 = 0;
        Class<E> cls = clz;
        if (throwableFields != fieldsCountOrDefault(cls, 0)) {
            return nullResult;
        }
        Constructor[] constructors = cls.getConstructors();
        Collection destination$iv$iv = new ArrayList(constructors.length);
        Constructor[] constructorArr = constructors;
        int length = constructorArr.length;
        int i3 = 0;
        while (true) {
            Object maxElem$iv = null;
            if (i3 < length) {
                Constructor constructor = constructorArr[i3];
                Class[] p = constructor.getParameterTypes();
                switch (p.length) {
                    case 0:
                        i = i2;
                        pair = TuplesKt.to(safeCtor(new ExceptionsConstructorKt$$ExternalSyntheticLambda4(constructor)), Integer.valueOf(i));
                        break;
                    case 1:
                        i = i2;
                        Class cls2 = p[i];
                        if (!Intrinsics.areEqual((Object) cls2, (Object) String.class)) {
                            if (!Intrinsics.areEqual((Object) cls2, (Object) Throwable.class)) {
                                pair = TuplesKt.to(null, -1);
                                break;
                            } else {
                                pair = TuplesKt.to(safeCtor(new ExceptionsConstructorKt$$ExternalSyntheticLambda3(constructor)), 1);
                                break;
                            }
                        } else {
                            pair = TuplesKt.to(safeCtor(new ExceptionsConstructorKt$$ExternalSyntheticLambda2(constructor)), 2);
                            break;
                        }
                    case 2:
                        i = i2;
                        if (Intrinsics.areEqual((Object) p[i2], (Object) String.class) && Intrinsics.areEqual((Object) p[1], (Object) Throwable.class)) {
                            pair = TuplesKt.to(safeCtor(new ExceptionsConstructorKt$$ExternalSyntheticLambda1(constructor)), 3);
                            break;
                        } else {
                            pair = TuplesKt.to(null, -1);
                            break;
                        }
                    default:
                        i = i2;
                        pair = TuplesKt.to(null, -1);
                        break;
                }
                destination$iv$iv.add(pair);
                i3++;
                i2 = i;
            } else {
                Iterator iterator$iv = ((List) destination$iv$iv).iterator();
                if (iterator$iv.hasNext()) {
                    maxElem$iv = iterator$iv.next();
                    if (iterator$iv.hasNext()) {
                        int maxValue$iv = ((Number) ((Pair) maxElem$iv).getSecond()).intValue();
                        do {
                            Object e$iv = iterator$iv.next();
                            int v$iv = ((Number) ((Pair) e$iv).getSecond()).intValue();
                            if (maxValue$iv < v$iv) {
                                maxValue$iv = v$iv;
                                maxElem$iv = e$iv;
                            }
                        } while (iterator$iv.hasNext());
                    }
                }
                Pair pair2 = (Pair) maxElem$iv;
                if (pair2 == null || (function1 = (Function1) pair2.getFirst()) == null) {
                    return nullResult;
                }
                return function1;
            }
        }
    }

    /* access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$1(Constructor $constructor, Throwable e) {
        Object newInstance = $constructor.newInstance(new Object[]{e.getMessage(), e});
        Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Throwable");
        return (Throwable) newInstance;
    }

    /* access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$3(Constructor $constructor, Throwable e) {
        Object newInstance = $constructor.newInstance(new Object[]{e.getMessage()});
        Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Throwable");
        Throwable it = (Throwable) newInstance;
        it.initCause(e);
        return it;
    }

    /* access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$4(Constructor $constructor, Throwable e) {
        Object newInstance = $constructor.newInstance(new Object[]{e});
        Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Throwable");
        return (Throwable) newInstance;
    }

    /* access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$6(Constructor $constructor, Throwable e) {
        Object newInstance = $constructor.newInstance(new Object[0]);
        Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Throwable");
        Throwable it = (Throwable) newInstance;
        it.initCause(e);
        return it;
    }

    private static final Function1<Throwable, Throwable> safeCtor(Function1<? super Throwable, ? extends Throwable> block) {
        return new ExceptionsConstructorKt$$ExternalSyntheticLambda0(block);
    }

    /* access modifiers changed from: private */
    public static final Throwable safeCtor$lambda$9(Function1 $block, Throwable e) {
        Object obj;
        Throwable th = null;
        try {
            Result.Companion companion = Result.Companion;
            Throwable result = (Throwable) $block.invoke(e);
            if (!Intrinsics.areEqual((Object) e.getMessage(), (Object) result.getMessage()) && !Intrinsics.areEqual((Object) result.getMessage(), (Object) e.toString())) {
                result = null;
            }
            obj = Result.m6constructorimpl(result);
        } catch (Throwable th2) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m6constructorimpl(ResultKt.createFailure(th2));
        }
        if (!Result.m12isFailureimpl(obj)) {
            th = obj;
        }
        return th;
    }

    private static final int fieldsCountOrDefault(Class<?> $this$fieldsCountOrDefault, int defaultValue) {
        Integer num;
        KClass<?> kotlinClass = JvmClassMappingKt.getKotlinClass($this$fieldsCountOrDefault);
        try {
            Result.Companion companion = Result.Companion;
            num = Result.m6constructorimpl(Integer.valueOf(fieldsCount$default($this$fieldsCountOrDefault, 0, 1, (Object) null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            num = Result.m6constructorimpl(ResultKt.createFailure(th));
        }
        Integer valueOf = Integer.valueOf(defaultValue);
        if (Result.m12isFailureimpl(num)) {
            num = valueOf;
        }
        return ((Number) num).intValue();
    }

    static /* synthetic */ int fieldsCount$default(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return fieldsCount(cls, i);
    }

    private static final int fieldsCount(Class<?> $this$fieldsCount, int accumulator) {
        while (true) {
            int count$iv = 0;
            for (Field it : $this$fieldsCount.getDeclaredFields()) {
                if (!Modifier.isStatic(it.getModifiers())) {
                    count$iv++;
                }
            }
            int totalFields = accumulator + count$iv;
            Class<?> superClass = $this$fieldsCount.getSuperclass();
            if (superClass == null) {
                return totalFields;
            }
            $this$fieldsCount = superClass;
            accumulator = totalFields;
        }
    }
}
