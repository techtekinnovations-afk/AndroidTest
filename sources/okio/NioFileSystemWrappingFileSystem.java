package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import okio.Path;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\nH\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00172\u0006\u0010\u0012\u001a\u00020\bH\u0016J \u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00172\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\nH\u0002J\u0018\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00172\u0006\u0010\u0012\u001a\u00020\bH\u0016J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\nH\u0016J\u0010\u0010\r\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010!\u001a\u00020\"H\u0016J\f\u0010#\u001a\u00020$*\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lokio/NioFileSystemWrappingFileSystem;", "Lokio/NioSystemFileSystem;", "nioFileSystem", "Ljava/nio/file/FileSystem;", "(Ljava/nio/file/FileSystem;)V", "appendingSink", "Lokio/Sink;", "file", "Lokio/Path;", "mustExist", "", "atomicMove", "", "source", "target", "canonicalize", "path", "createDirectory", "dir", "mustCreate", "createSymlink", "delete", "list", "", "throwOnFailure", "listOrNull", "metadataOrNull", "Lokio/FileMetadata;", "openReadOnly", "Lokio/FileHandle;", "openReadWrite", "sink", "Lokio/Source;", "toString", "", "resolve", "Ljava/nio/file/Path;", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: NioFileSystemWrappingFileSystem.kt */
public final class NioFileSystemWrappingFileSystem extends NioSystemFileSystem {
    private final FileSystem nioFileSystem;

    public NioFileSystemWrappingFileSystem(FileSystem nioFileSystem2) {
        Intrinsics.checkNotNullParameter(nioFileSystem2, "nioFileSystem");
        this.nioFileSystem = nioFileSystem2;
    }

    private final Path resolve(Path $this$resolve) {
        Path path = this.nioFileSystem.getPath($this$resolve.toString(), new String[0]);
        Intrinsics.checkNotNullExpressionValue(path, "nioFileSystem.getPath(toString())");
        return path;
    }

    public Path canonicalize(Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        try {
            Path.Companion companion = Path.Companion;
            java.nio.file.Path realPath = resolve(path).toRealPath(new LinkOption[0]);
            Intrinsics.checkNotNullExpressionValue(realPath, "path.resolve().toRealPath()");
            return Path.Companion.get$default(companion, realPath, false, 1, (Object) null);
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("no such file: " + path);
        }
    }

    public FileMetadata metadataOrNull(Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return metadataOrNull(resolve(path));
    }

    public List<Path> list(Path dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        List<Path> list = list(dir, true);
        Intrinsics.checkNotNull(list);
        return list;
    }

    public List<Path> listOrNull(Path dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        return list(dir, false);
    }

    private final List<Path> list(Path dir, boolean throwOnFailure) {
        java.nio.file.Path nioDir = resolve(dir);
        try {
            Collection destination$iv = new ArrayList();
            for (java.nio.file.Path entry : PathsKt.listDirectoryEntries$default(nioDir, (String) null, 1, (Object) null)) {
                destination$iv.add(dir.resolve(entry.toString()));
            }
            List result = (List) destination$iv;
            CollectionsKt.sort(result);
            return result;
        } catch (Exception e) {
            if (!throwOnFailure) {
                return null;
            }
            if (!Files.exists(nioDir, (LinkOption[]) Arrays.copyOf(new LinkOption[0], 0))) {
                throw new FileNotFoundException("no such file: " + dir);
            }
            throw new IOException("failed to list " + dir);
        }
    }

    public FileHandle openReadOnly(Path file) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            FileChannel channel = FileChannel.open(resolve(file), new OpenOption[]{StandardOpenOption.READ});
            Intrinsics.checkNotNullExpressionValue(channel, "channel");
            return new NioFileSystemFileHandle(false, channel);
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("no such file: " + file);
        }
    }

    public FileHandle openReadWrite(Path file, boolean mustCreate, boolean mustExist) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!mustCreate || !mustExist) {
            List createListBuilder = CollectionsKt.createListBuilder();
            List $this$openReadWrite_u24lambda_u242 = createListBuilder;
            $this$openReadWrite_u24lambda_u242.add(StandardOpenOption.READ);
            $this$openReadWrite_u24lambda_u242.add(StandardOpenOption.WRITE);
            if (mustCreate) {
                $this$openReadWrite_u24lambda_u242.add(StandardOpenOption.CREATE_NEW);
            } else if (!mustExist) {
                $this$openReadWrite_u24lambda_u242.add(StandardOpenOption.CREATE);
            }
            List openOptions = CollectionsKt.build(createListBuilder);
            try {
                java.nio.file.Path resolve = resolve(file);
                StandardOpenOption[] standardOpenOptionArr = (StandardOpenOption[]) openOptions.toArray(new StandardOpenOption[0]);
                FileChannel channel = FileChannel.open(resolve, (OpenOption[]) Arrays.copyOf(standardOpenOptionArr, standardOpenOptionArr.length));
                Intrinsics.checkNotNullExpressionValue(channel, "channel");
                return new NioFileSystemFileHandle(true, channel);
            } catch (NoSuchFileException e) {
                throw new FileNotFoundException("no such file: " + file);
            }
        } else {
            throw new IllegalArgumentException("Cannot require mustCreate and mustExist at the same time.".toString());
        }
    }

    public Source source(Path file) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            InputStream newInputStream = Files.newInputStream(resolve(file), (OpenOption[]) Arrays.copyOf(new OpenOption[0], 0));
            Intrinsics.checkNotNullExpressionValue(newInputStream, "newInputStream(this, *options)");
            return Okio.source(newInputStream);
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("no such file: " + file);
        }
    }

    public Sink sink(Path file, boolean mustCreate) {
        Intrinsics.checkNotNullParameter(file, "file");
        List createListBuilder = CollectionsKt.createListBuilder();
        List $this$sink_u24lambda_u243 = createListBuilder;
        if (mustCreate) {
            $this$sink_u24lambda_u243.add(StandardOpenOption.CREATE_NEW);
        }
        List openOptions = CollectionsKt.build(createListBuilder);
        try {
            java.nio.file.Path resolve = resolve(file);
            StandardOpenOption[] standardOpenOptionArr = (StandardOpenOption[]) openOptions.toArray(new StandardOpenOption[0]);
            OpenOption[] openOptionArr = (OpenOption[]) Arrays.copyOf(standardOpenOptionArr, standardOpenOptionArr.length);
            OutputStream newOutputStream = Files.newOutputStream(resolve, (OpenOption[]) Arrays.copyOf(openOptionArr, openOptionArr.length));
            Intrinsics.checkNotNullExpressionValue(newOutputStream, "newOutputStream(this, *options)");
            return Okio.sink(newOutputStream);
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException("no such file: " + file);
        }
    }

    public Sink appendingSink(Path file, boolean mustExist) {
        Intrinsics.checkNotNullParameter(file, "file");
        List createListBuilder = CollectionsKt.createListBuilder();
        List $this$appendingSink_u24lambda_u244 = createListBuilder;
        $this$appendingSink_u24lambda_u244.add(StandardOpenOption.APPEND);
        if (!mustExist) {
            $this$appendingSink_u24lambda_u244.add(StandardOpenOption.CREATE);
        }
        List openOptions = CollectionsKt.build(createListBuilder);
        java.nio.file.Path resolve = resolve(file);
        StandardOpenOption[] standardOpenOptionArr = (StandardOpenOption[]) openOptions.toArray(new StandardOpenOption[0]);
        OpenOption[] openOptionArr = (OpenOption[]) Arrays.copyOf(standardOpenOptionArr, standardOpenOptionArr.length);
        OutputStream newOutputStream = Files.newOutputStream(resolve, (OpenOption[]) Arrays.copyOf(openOptionArr, openOptionArr.length));
        Intrinsics.checkNotNullExpressionValue(newOutputStream, "newOutputStream(this, *options)");
        return Okio.sink(newOutputStream);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0011, code lost:
        if (r0.isDirectory() == true) goto L_0x0015;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void createDirectory(okio.Path r6, boolean r7) {
        /*
            r5 = this;
            java.lang.String r0 = "dir"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            okio.FileMetadata r0 = r5.metadataOrNull(r6)
            r1 = 0
            if (r0 == 0) goto L_0x0014
            boolean r0 = r0.isDirectory()
            r2 = 1
            if (r0 != r2) goto L_0x0014
            goto L_0x0015
        L_0x0014:
            r2 = r1
        L_0x0015:
            if (r2 == 0) goto L_0x0033
            if (r7 != 0) goto L_0x001a
            goto L_0x0033
        L_0x001a:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r3 = " already exist."
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0033:
            java.nio.file.Path r0 = r5.resolve(r6)     // Catch:{ IOException -> 0x004a }
            java.nio.file.attribute.FileAttribute[] r3 = new java.nio.file.attribute.FileAttribute[r1]     // Catch:{ IOException -> 0x004a }
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r3, r1)     // Catch:{ IOException -> 0x004a }
            java.nio.file.attribute.FileAttribute[] r1 = (java.nio.file.attribute.FileAttribute[]) r1     // Catch:{ IOException -> 0x004a }
            java.nio.file.Path r0 = java.nio.file.Files.createDirectory(r0, r1)     // Catch:{ IOException -> 0x004a }
            java.lang.String r1 = "createDirectory(this, *attributes)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)     // Catch:{ IOException -> 0x004a }
            return
        L_0x004a:
            r0 = move-exception
            if (r2 == 0) goto L_0x004e
            return
        L_0x004e:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "failed to create directory: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r3 = r3.toString()
            r4 = r0
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r1.<init>(r3, r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.NioFileSystemWrappingFileSystem.createDirectory(okio.Path, boolean):void");
    }

    public void atomicMove(Path source, Path target) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, TypedValues.AttributesType.S_TARGET);
        try {
            Intrinsics.checkNotNullExpressionValue(Files.move(resolve(source), resolve(target), (CopyOption[]) Arrays.copyOf(new CopyOption[]{StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING}, 2)), "move(this, target, *options)");
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (UnsupportedOperationException e2) {
            throw new IOException("atomic move not supported");
        }
    }

    public void delete(Path path, boolean mustExist) {
        Intrinsics.checkNotNullParameter(path, "path");
        if (!Thread.interrupted()) {
            java.nio.file.Path nioPath = resolve(path);
            try {
                Files.delete(nioPath);
            } catch (NoSuchFileException e) {
                if (mustExist) {
                    throw new FileNotFoundException("no such file: " + path);
                }
            } catch (IOException e2) {
                if (Files.exists(nioPath, (LinkOption[]) Arrays.copyOf(new LinkOption[0], 0))) {
                    throw new IOException("failed to delete " + path);
                }
            }
        } else {
            throw new InterruptedIOException("interrupted");
        }
    }

    public void createSymlink(Path source, Path target) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, TypedValues.AttributesType.S_TARGET);
        Intrinsics.checkNotNullExpressionValue(Files.createSymbolicLink(resolve(source), resolve(target), (FileAttribute[]) Arrays.copyOf(new FileAttribute[0], 0)), "createSymbolicLink(this, target, *attributes)");
    }

    public String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(this.nioFileSystem.getClass()).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        return simpleName;
    }
}
