package io.grpc.protobuf.lite;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import io.grpc.Drainable;
import io.grpc.KnownLength;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;

final class ProtoInputStream extends InputStream implements Drainable, KnownLength {
    @Nullable
    private MessageLite message;
    private final Parser<?> parser;
    @Nullable
    private ByteArrayInputStream partial;

    ProtoInputStream(MessageLite message2, Parser<?> parser2) {
        this.message = message2;
        this.parser = parser2;
    }

    public int drainTo(OutputStream target) throws IOException {
        if (this.message != null) {
            int written = this.message.getSerializedSize();
            this.message.writeTo(target);
            this.message = null;
            return written;
        } else if (this.partial == null) {
            return 0;
        } else {
            int written2 = (int) ProtoLiteUtils.copy(this.partial, target);
            this.partial = null;
            return written2;
        }
    }

    public int read() {
        if (this.message != null) {
            this.partial = new ByteArrayInputStream(this.message.toByteArray());
            this.message = null;
        }
        if (this.partial != null) {
            return this.partial.read();
        }
        return -1;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        if (this.message != null) {
            int size = this.message.getSerializedSize();
            if (size == 0) {
                this.message = null;
                this.partial = null;
                return -1;
            } else if (len >= size) {
                CodedOutputStream stream = CodedOutputStream.newInstance(b, off, size);
                this.message.writeTo(stream);
                stream.flush();
                stream.checkNoSpaceLeft();
                this.message = null;
                this.partial = null;
                return size;
            } else {
                this.partial = new ByteArrayInputStream(this.message.toByteArray());
                this.message = null;
            }
        }
        if (this.partial != null) {
            return this.partial.read(b, off, len);
        }
        return -1;
    }

    public int available() {
        if (this.message != null) {
            return this.message.getSerializedSize();
        }
        if (this.partial != null) {
            return this.partial.available();
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public MessageLite message() {
        if (this.message != null) {
            return this.message;
        }
        throw new IllegalStateException("message not available");
    }

    /* access modifiers changed from: package-private */
    public Parser<?> parser() {
        return this.parser;
    }
}
