package kotlin.uuid;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0001\u001a\f\u0010\u0002\u001a\u00020\u0001*\u00020\u0003H\u0007\u001a\u0014\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u001c\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0001H\u0007\u001a\r\u0010\b\u001a\u00020\t*\u00020\tH\b\u001a\r\u0010\n\u001a\u00020\u000b*\u00020\u0001H\b\u001a\r\u0010\f\u001a\u00020\u0001*\u00020\u000bH\b¨\u0006\r"}, d2 = {"secureRandomUuid", "Lkotlin/uuid/Uuid;", "getUuid", "Ljava/nio/ByteBuffer;", "index", "", "putUuid", "uuid", "reverseBytes", "", "toJavaUuid", "Ljava/util/UUID;", "toKotlinUuid", "kotlin-stdlib"}, k = 5, mv = {1, 9, 0}, xi = 49, xs = "kotlin/uuid/UuidKt")
/* compiled from: UuidJVM.kt */
class UuidKt__UuidJVMKt {
    public static final Uuid secureRandomUuid() {
        byte[] randomBytes = new byte[16];
        SecureRandomHolder.INSTANCE.getInstance().nextBytes(randomBytes);
        return UuidKt.uuidFromRandomBytes(randomBytes);
    }

    public static final Uuid toKotlinUuid(UUID $this$toKotlinUuid) {
        Intrinsics.checkNotNullParameter($this$toKotlinUuid, "<this>");
        return Uuid.Companion.fromLongs($this$toKotlinUuid.getMostSignificantBits(), $this$toKotlinUuid.getLeastSignificantBits());
    }

    public static final UUID toJavaUuid(Uuid $this$toJavaUuid) {
        Intrinsics.checkNotNullParameter($this$toJavaUuid, "<this>");
        return new UUID($this$toJavaUuid.getMostSignificantBits(), $this$toJavaUuid.getLeastSignificantBits());
    }

    public static final Uuid getUuid(ByteBuffer $this$getUuid) {
        Intrinsics.checkNotNullParameter($this$getUuid, "<this>");
        if ($this$getUuid.position() + 15 < $this$getUuid.limit()) {
            long msb = $this$getUuid.getLong();
            long lsb = $this$getUuid.getLong();
            if (Intrinsics.areEqual((Object) $this$getUuid.order(), (Object) ByteOrder.LITTLE_ENDIAN)) {
                msb = Long.reverseBytes(msb);
                lsb = Long.reverseBytes(lsb);
            }
            return Uuid.Companion.fromLongs(msb, lsb);
        }
        throw new BufferUnderflowException();
    }

    public static final Uuid getUuid(ByteBuffer $this$getUuid, int index) {
        Intrinsics.checkNotNullParameter($this$getUuid, "<this>");
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index: " + index);
        } else if (index + 15 < $this$getUuid.limit()) {
            long msb = $this$getUuid.getLong(index);
            long lsb = $this$getUuid.getLong(index + 8);
            if (Intrinsics.areEqual((Object) $this$getUuid.order(), (Object) ByteOrder.LITTLE_ENDIAN)) {
                msb = Long.reverseBytes(msb);
                lsb = Long.reverseBytes(lsb);
            }
            return Uuid.Companion.fromLongs(msb, lsb);
        } else {
            throw new IndexOutOfBoundsException("Not enough bytes to read a uuid at index: " + index + ", with limit: " + $this$getUuid.limit() + ' ');
        }
    }

    public static final ByteBuffer putUuid(ByteBuffer $this$putUuid, Uuid uuid) {
        ByteBuffer byteBuffer;
        Intrinsics.checkNotNullParameter($this$putUuid, "<this>");
        Intrinsics.checkNotNullParameter(uuid, "uuid");
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        if ($this$putUuid.position() + 15 < $this$putUuid.limit()) {
            if (Intrinsics.areEqual((Object) $this$putUuid.order(), (Object) ByteOrder.BIG_ENDIAN)) {
                $this$putUuid.putLong(msb);
                byteBuffer = $this$putUuid.putLong(lsb);
            } else {
                $this$putUuid.putLong(Long.reverseBytes(msb));
                byteBuffer = $this$putUuid.putLong(Long.reverseBytes(lsb));
            }
            Intrinsics.checkNotNullExpressionValue(byteBuffer, "toLongs(...)");
            return byteBuffer;
        }
        throw new BufferOverflowException();
    }

    public static final ByteBuffer putUuid(ByteBuffer $this$putUuid, int index, Uuid uuid) {
        ByteBuffer byteBuffer;
        Intrinsics.checkNotNullParameter($this$putUuid, "<this>");
        Intrinsics.checkNotNullParameter(uuid, "uuid");
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index: " + index);
        } else if (index + 15 < $this$putUuid.limit()) {
            if (Intrinsics.areEqual((Object) $this$putUuid.order(), (Object) ByteOrder.BIG_ENDIAN)) {
                $this$putUuid.putLong(index, msb);
                byteBuffer = $this$putUuid.putLong(index + 8, lsb);
            } else {
                $this$putUuid.putLong(index, Long.reverseBytes(msb));
                byteBuffer = $this$putUuid.putLong(index + 8, Long.reverseBytes(lsb));
            }
            Intrinsics.checkNotNullExpressionValue(byteBuffer, "toLongs(...)");
            return byteBuffer;
        } else {
            throw new IndexOutOfBoundsException("Not enough capacity to write a uuid at index: " + index + ", with limit: " + $this$putUuid.limit() + ' ');
        }
    }

    public static final long reverseBytes(long $this$reverseBytes) {
        return Long.reverseBytes($this$reverseBytes);
    }
}
