package com.google.firebase.firestore.bundle;

import com.google.firebase.firestore.util.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleReader {
    protected static final int BUFFER_CAPACITY = 1024;
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private final InputStream bundleInputStream;
    long bytesRead;
    private final InputStreamReader dataReader = new InputStreamReader(this.bundleInputStream);
    BundleMetadata metadata;
    private final BundleSerializer serializer;

    public BundleReader(BundleSerializer serializer2, InputStream bundleInputStream2) {
        this.serializer = serializer2;
        this.bundleInputStream = bundleInputStream2;
        this.buffer.flip();
    }

    public BundleMetadata getBundleMetadata() throws IOException, JSONException {
        if (this.metadata != null) {
            return this.metadata;
        }
        BundleElement element = readNextElement();
        if (element instanceof BundleMetadata) {
            this.metadata = (BundleMetadata) element;
            this.bytesRead = 0;
            return this.metadata;
        }
        throw abort("Expected first element in bundle to be a metadata object");
    }

    public BundleElement getNextElement() throws IOException, JSONException {
        getBundleMetadata();
        return readNextElement();
    }

    public long getBytesRead() {
        return this.bytesRead;
    }

    public void close() throws IOException {
        this.bundleInputStream.close();
    }

    private BundleElement readNextElement() throws IOException, JSONException {
        String lengthPrefix = readLengthPrefix();
        if (lengthPrefix == null) {
            return null;
        }
        int jsonStringByteCount = Integer.parseInt(lengthPrefix);
        String json = readJsonString(jsonStringByteCount);
        this.bytesRead += (long) (lengthPrefix.getBytes(UTF8_CHARSET).length + jsonStringByteCount);
        return decodeBundleElement(json);
    }

    private String readLengthPrefix() throws IOException {
        int nextOpenBracket;
        do {
            int indexOfOpenBracket = indexOfOpenBracket();
            nextOpenBracket = indexOfOpenBracket;
            if (indexOfOpenBracket != -1 || !pullMoreData()) {
            }
            int indexOfOpenBracket2 = indexOfOpenBracket();
            nextOpenBracket = indexOfOpenBracket2;
            break;
        } while (!pullMoreData());
        if (this.buffer.remaining() == 0) {
            return null;
        }
        if (nextOpenBracket != -1) {
            byte[] b = new byte[nextOpenBracket];
            this.buffer.get(b);
            return UTF8_CHARSET.decode(ByteBuffer.wrap(b)).toString();
        }
        throw abort("Reached the end of bundle when a length string is expected.");
    }

    private int indexOfOpenBracket() {
        this.buffer.mark();
        int i = 0;
        while (i < this.buffer.remaining()) {
            try {
                if (this.buffer.get() == 123) {
                    return i;
                }
                i++;
            } finally {
                this.buffer.reset();
            }
        }
        this.buffer.reset();
        return -1;
    }

    private String readJsonString(int bytesToRead) throws IOException {
        ByteArrayOutputStream jsonBytes = new ByteArrayOutputStream();
        int remaining = bytesToRead;
        while (remaining > 0) {
            if (this.buffer.remaining() != 0 || pullMoreData()) {
                int read = Math.min(remaining, this.buffer.remaining());
                jsonBytes.write(this.buffer.array(), this.buffer.arrayOffset() + this.buffer.position(), read);
                this.buffer.position(this.buffer.position() + read);
                remaining -= read;
            } else {
                throw abort("Reached the end of bundle when more data was expected.");
            }
        }
        return jsonBytes.toString(UTF8_CHARSET.name());
    }

    private boolean pullMoreData() throws IOException {
        this.buffer.compact();
        int bytesRead2 = this.bundleInputStream.read(this.buffer.array(), this.buffer.arrayOffset() + this.buffer.position(), this.buffer.remaining());
        boolean readSuccess = bytesRead2 > 0;
        if (readSuccess) {
            this.buffer.position(this.buffer.position() + bytesRead2);
        }
        this.buffer.flip();
        return readSuccess;
    }

    private BundleElement decodeBundleElement(String json) throws JSONException, IOException {
        JSONObject object = new JSONObject(json);
        if (object.has("metadata")) {
            BundleMetadata metadata2 = this.serializer.decodeBundleMetadata(object.getJSONObject("metadata"));
            Logger.debug("BundleElement", "BundleMetadata element loaded", new Object[0]);
            return metadata2;
        } else if (object.has("namedQuery")) {
            NamedQuery namedQuery = this.serializer.decodeNamedQuery(object.getJSONObject("namedQuery"));
            Logger.debug("BundleElement", "Query loaded: " + namedQuery.getName(), new Object[0]);
            return namedQuery;
        } else if (object.has("documentMetadata")) {
            BundledDocumentMetadata documentMetadata = this.serializer.decodeBundledDocumentMetadata(object.getJSONObject("documentMetadata"));
            Logger.debug("BundleElement", "Document metadata loaded: " + documentMetadata.getKey(), new Object[0]);
            return documentMetadata;
        } else if (object.has("document")) {
            BundleDocument document = this.serializer.decodeDocument(object.getJSONObject("document"));
            Logger.debug("BundleElement", "Document loaded: " + document.getKey(), new Object[0]);
            return document;
        } else {
            throw abort("Cannot decode unknown Bundle element: " + json);
        }
    }

    private IllegalArgumentException abort(String message) throws IOException {
        close();
        throw new IllegalArgumentException("Invalid bundle: " + message);
    }
}
