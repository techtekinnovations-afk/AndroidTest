package com.google.firebase.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\"\u0014\u0010\u0005\u001a\u00020\u0001XT¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"nextAlphanumericString", "", "Lkotlin/random/Random;", "length", "", "ALPHANUMERIC_ALPHABET", "getALPHANUMERIC_ALPHABET$annotations", "()V", "com.google.firebase-firebase-common"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: RandomUtil.kt */
public final class RandomUtilKt {
    private static final String ALPHANUMERIC_ALPHABET = "23456789abcdefghjkmnpqrstvwxyz";

    private static /* synthetic */ void getALPHANUMERIC_ALPHABET$annotations() {
    }

    public static final String nextAlphanumericString(Random $this$nextAlphanumericString, int length) {
        Intrinsics.checkNotNullParameter($this$nextAlphanumericString, "<this>");
        if (length >= 0) {
            Iterable $this$map$iv = RangesKt.until(0, length);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int nextInt = ((IntIterator) it).nextInt();
                destination$iv$iv.add(Character.valueOf(StringsKt.random(ALPHANUMERIC_ALPHABET, $this$nextAlphanumericString)));
            }
            return CollectionsKt.joinToString$default((List) destination$iv$iv, "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
        }
        throw new IllegalArgumentException(("invalid length: " + length).toString());
    }
}
