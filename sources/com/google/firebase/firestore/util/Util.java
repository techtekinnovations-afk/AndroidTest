package com.google.firebase.firestore.util;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.cloud.datastore.core.number.NumberComparisonHelper;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;

public class Util {
    private static final String AUTO_ID_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int AUTO_ID_LENGTH = 20;
    private static final Continuation<Void, Void> VOID_ERROR_TRANSFORMER = new Util$$ExternalSyntheticLambda1();
    private static final Random rand = new SecureRandom();

    public static String autoId() {
        StringBuilder builder = new StringBuilder();
        int maxRandom = AUTO_ID_ALPHABET.length();
        for (int i = 0; i < 20; i++) {
            builder.append(AUTO_ID_ALPHABET.charAt(rand.nextInt(maxRandom)));
        }
        return builder.toString();
    }

    public static int compareBooleans(boolean b1, boolean b2) {
        if (b1 == b2) {
            return 0;
        }
        if (b1) {
            return 1;
        }
        return -1;
    }

    public static int compareUtf8Strings(String left, String right) {
        if (left == right) {
            return 0;
        }
        int length = Math.min(left.length(), right.length());
        int i = 0;
        while (i < length) {
            char leftChar = left.charAt(i);
            char rightChar = right.charAt(i);
            if (leftChar == rightChar) {
                i++;
            } else if (Character.isSurrogate(leftChar) == Character.isSurrogate(rightChar)) {
                return Character.compare(leftChar, rightChar);
            } else {
                return Character.isSurrogate(leftChar) ? 1 : -1;
            }
        }
        return Integer.compare(left.length(), right.length());
    }

    public static int compareDoubles(double i1, double i2) {
        return NumberComparisonHelper.firestoreCompareDoubles(i1, i2);
    }

    public static int compareMixed(double doubleValue, long longValue) {
        return NumberComparisonHelper.firestoreCompareDoubleWithLong(doubleValue, longValue);
    }

    public static <T extends Comparable<T>> Comparator<T> comparator() {
        return new Util$$ExternalSyntheticLambda4();
    }

    public static FirebaseFirestoreException exceptionFromStatus(Status error) {
        StatusException statusException = error.asException();
        return new FirebaseFirestoreException(statusException.getMessage(), FirebaseFirestoreException.Code.fromValue(error.getCode().value()), statusException);
    }

    private static Exception convertStatusException(Exception e) {
        if (e instanceof StatusException) {
            return exceptionFromStatus(((StatusException) e).getStatus());
        }
        if (e instanceof StatusRuntimeException) {
            return exceptionFromStatus(((StatusRuntimeException) e).getStatus());
        }
        return e;
    }

    public static Exception convertThrowableToException(Throwable t) {
        if (t instanceof Exception) {
            return convertStatusException((Exception) t);
        }
        return new Exception(t);
    }

    static /* synthetic */ Void lambda$static$0(Task task) throws Exception {
        if (task.isSuccessful()) {
            return (Void) task.getResult();
        }
        Exception e = convertStatusException(task.getException());
        if (e instanceof FirebaseFirestoreException) {
            throw e;
        }
        throw new FirebaseFirestoreException(e.getMessage(), FirebaseFirestoreException.Code.UNKNOWN, e);
    }

    public static Continuation<Void, Void> voidErrorTransformer() {
        return VOID_ERROR_TRANSFORMER;
    }

    public static List<Object> collectUpdateArguments(int fieldPathOffset, Object field, Object val, Object... fieldsAndValues) {
        if (fieldsAndValues.length % 2 != 1) {
            List<Object> argumentList = new ArrayList<>();
            argumentList.add(field);
            argumentList.add(val);
            Collections.addAll(argumentList, fieldsAndValues);
            int i = 0;
            while (i < argumentList.size()) {
                Object fieldPath = argumentList.get(i);
                if ((fieldPath instanceof String) || (fieldPath instanceof FieldPath)) {
                    i += 2;
                } else {
                    throw new IllegalArgumentException("Excepted field name at argument position " + (i + fieldPathOffset + 1) + " but got " + fieldPath + " in call to update.  The arguments to update should alternate between field names and values");
                }
            }
            return argumentList;
        }
        throw new IllegalArgumentException("Missing value in call to update().  There must be an even number of arguments that alternate between field names and values");
    }

    public static String toDebugString(ByteString bytes) {
        int size = bytes.size();
        StringBuilder result = new StringBuilder(size * 2);
        for (int i = 0; i < size; i++) {
            int value = bytes.byteAt(i) & 255;
            result.append(Character.forDigit(value >>> 4, 16));
            result.append(Character.forDigit(value & 15, 16));
        }
        return result.toString();
    }

    public static String typeName(Object obj) {
        return obj == null ? "null" : obj.getClass().getName();
    }

    public static void crashMainThread(RuntimeException exception) {
        new Handler(Looper.getMainLooper()).post(new Util$$ExternalSyntheticLambda0(exception));
    }

    static /* synthetic */ void lambda$crashMainThread$1(RuntimeException exception) {
        throw exception;
    }

    public static int compareByteArrays(byte[] left, byte[] right) {
        int size = Math.min(left.length, right.length);
        for (int i = 0; i < size; i++) {
            int thisByte = left[i] & 255;
            int otherByte = right[i] & 255;
            if (thisByte < otherByte) {
                return -1;
            }
            if (thisByte > otherByte) {
                return 1;
            }
        }
        return Integer.compare(left.length, right.length);
    }

    public static int compareByteStrings(ByteString left, ByteString right) {
        int size = Math.min(left.size(), right.size());
        for (int i = 0; i < size; i++) {
            int thisByte = left.byteAt(i) & 255;
            int otherByte = right.byteAt(i) & 255;
            if (thisByte < otherByte) {
                return -1;
            }
            if (thisByte > otherByte) {
                return 1;
            }
        }
        return Integer.compare(left.size(), right.size());
    }

    public static StringBuilder repeatSequence(CharSequence sequence, int count, CharSequence delimiter) {
        StringBuilder sb = new StringBuilder();
        if (count != 0) {
            sb.append(sequence);
            for (int i = 1; i < count; i++) {
                sb.append(delimiter);
                sb.append(sequence);
            }
        }
        return sb;
    }

    public static <T> void diffCollections(Collection<T> before, Collection<T> after, Comparator<T> comparator, Consumer<T> onAdd, Consumer<T> onRemove) {
        List<T> beforeEntries = new ArrayList<>(before);
        Collections.sort(beforeEntries, comparator);
        List<T> afterEntries = new ArrayList<>(after);
        Collections.sort(afterEntries, comparator);
        diffCollections(beforeEntries.iterator(), afterEntries.iterator(), comparator, onAdd, onRemove);
    }

    public static <T extends Comparable<T>> void diffCollections(SortedSet<T> before, SortedSet<T> after, Consumer<T> onAdd, Consumer<T> onRemove) {
        Comparator<? super T> comparator;
        Iterator it = before.iterator();
        Iterator it2 = after.iterator();
        if (before.comparator() != null) {
            comparator = before.comparator();
        } else {
            new Util$$ExternalSyntheticLambda2
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0016: CONSTRUCTOR  (r2v2 ? I:com.google.firebase.firestore.util.Util$$ExternalSyntheticLambda2) =  call: com.google.firebase.firestore.util.Util$$ExternalSyntheticLambda2.<init>():void type: CONSTRUCTOR in method: com.google.firebase.firestore.util.Util.diffCollections(java.util.SortedSet, java.util.SortedSet, com.google.firebase.firestore.util.Consumer, com.google.firebase.firestore.util.Consumer):void, dex: classes4.dex
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:156)
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
                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r2v2 ?
                	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:189)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:620)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	... 34 more
                */
            /*
                java.util.Iterator r0 = r3.iterator()
                java.util.Iterator r1 = r4.iterator()
                java.util.Comparator r2 = r3.comparator()
                if (r2 == 0) goto L_0x0014
                java.util.Comparator r2 = r3.comparator()
                goto L_0x0019
            L_0x0014:
                com.google.firebase.firestore.util.Util$$ExternalSyntheticLambda2 r2 = new com.google.firebase.firestore.util.Util$$ExternalSyntheticLambda2
                r2.<init>()
            L_0x0019:
                diffCollections(r0, r1, r2, r5, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.util.Util.diffCollections(java.util.SortedSet, java.util.SortedSet, com.google.firebase.firestore.util.Consumer, com.google.firebase.firestore.util.Consumer):void");
        }

        private static <T> void diffCollections(Iterator<T> beforeSorted, Iterator<T> afterSorted, Comparator<? super T> comparator, Consumer<T> onAdd, Consumer<T> onRemove) {
            T beforeValue = advanceIterator(beforeSorted);
            T afterValue = advanceIterator(afterSorted);
            while (true) {
                if (beforeValue != null || afterValue != null) {
                    boolean added = false;
                    boolean removed = false;
                    if (beforeValue != null && afterValue != null) {
                        int cmp = comparator.compare(beforeValue, afterValue);
                        if (cmp < 0) {
                            removed = true;
                        } else if (cmp > 0) {
                            added = true;
                        }
                    } else if (beforeValue != null) {
                        removed = true;
                    } else {
                        added = true;
                    }
                    if (added) {
                        onAdd.accept(afterValue);
                        afterValue = advanceIterator(afterSorted);
                    } else if (removed) {
                        onRemove.accept(beforeValue);
                        beforeValue = advanceIterator(beforeSorted);
                    } else {
                        beforeValue = advanceIterator(beforeSorted);
                        afterValue = advanceIterator(afterSorted);
                    }
                } else {
                    return;
                }
            }
        }

        private static <T> T advanceIterator(Iterator<T> it) {
            if (it.hasNext()) {
                return it.next();
            }
            return null;
        }

        public static <K, V> Iterable<V> values(Iterable<Map.Entry<K, V>> map) {
            return new Util$$ExternalSyntheticLambda5(map);
        }

        public static <K, V> Map<K, V> firstNEntries(Map<K, V> data, int n, Comparator<V> comp) {
            if (data.size() <= n) {
                return data;
            }
            List<Map.Entry<K, V>> sortedValues = new ArrayList<>(data.entrySet());
            Collections.sort(sortedValues, new Util$$ExternalSyntheticLambda3(comp));
            Map<K, V> result = new HashMap<>();
            for (int i = 0; i < n; i++) {
                result.put(sortedValues.get(i).getKey(), sortedValues.get(i).getValue());
            }
            return result;
        }
    }
