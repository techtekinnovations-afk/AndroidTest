package kotlin.io;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0007\u001a*\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0007\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002¢\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002¢\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002¢\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004¨\u0006+"}, d2 = {"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, k = 5, mv = {1, 9, 0}, xi = 49, xs = "kotlin/io/FilesKt")
/* compiled from: Utils.kt */
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
    public static /* synthetic */ File createTempDir$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            file = null;
        }
        return FilesKt.createTempDir(str, str2, file);
    }

    @Deprecated(message = "Avoid creating temporary directories in the default temp location with this function due to too wide permissions on the newly created directory. Use kotlin.io.path.createTempDirectory instead.")
    public static final File createTempDir(String prefix, String suffix, File directory) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        File dir = File.createTempFile(prefix, suffix, directory);
        dir.delete();
        if (dir.mkdir()) {
            Intrinsics.checkNotNull(dir);
            return dir;
        }
        throw new IOException("Unable to create temporary directory " + dir + '.');
    }

    public static /* synthetic */ File createTempFile$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            file = null;
        }
        return FilesKt.createTempFile(str, str2, file);
    }

    @Deprecated(message = "Avoid creating temporary files in the default temp location with this function due to too wide permissions on the newly created file. Use kotlin.io.path.createTempFile instead or resort to java.io.File.createTempFile.")
    public static final File createTempFile(String prefix, String suffix, File directory) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        File createTempFile = File.createTempFile(prefix, suffix, directory);
        Intrinsics.checkNotNullExpressionValue(createTempFile, "createTempFile(...)");
        return createTempFile;
    }

    public static final String getExtension(File $this$extension) {
        Intrinsics.checkNotNullParameter($this$extension, "<this>");
        String name = $this$extension.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.substringAfterLast(name, '.', "");
    }

    public static final String getInvariantSeparatorsPath(File $this$invariantSeparatorsPath) {
        Intrinsics.checkNotNullParameter($this$invariantSeparatorsPath, "<this>");
        if (File.separatorChar != '/') {
            String path = $this$invariantSeparatorsPath.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            return StringsKt.replace$default(path, File.separatorChar, '/', false, 4, (Object) null);
        }
        String path2 = $this$invariantSeparatorsPath.getPath();
        Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
        return path2;
    }

    public static final String getNameWithoutExtension(File $this$nameWithoutExtension) {
        Intrinsics.checkNotNullParameter($this$nameWithoutExtension, "<this>");
        String name = $this$nameWithoutExtension.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.substringBeforeLast$default(name, ".", (String) null, 2, (Object) null);
    }

    public static final String toRelativeString(File $this$toRelativeString, File base) {
        Intrinsics.checkNotNullParameter($this$toRelativeString, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt($this$toRelativeString, base);
        if (relativeStringOrNull$FilesKt__UtilsKt != null) {
            return relativeStringOrNull$FilesKt__UtilsKt;
        }
        throw new IllegalArgumentException("this and base files have different roots: " + $this$toRelativeString + " and " + base + '.');
    }

    public static final File relativeTo(File $this$relativeTo, File base) {
        Intrinsics.checkNotNullParameter($this$relativeTo, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        return new File(FilesKt.toRelativeString($this$relativeTo, base));
    }

    public static final File relativeToOrSelf(File $this$relativeToOrSelf, File base) {
        Intrinsics.checkNotNullParameter($this$relativeToOrSelf, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        String p0 = toRelativeStringOrNull$FilesKt__UtilsKt($this$relativeToOrSelf, base);
        return p0 != null ? new File(p0) : $this$relativeToOrSelf;
    }

    public static final File relativeToOrNull(File $this$relativeToOrNull, File base) {
        Intrinsics.checkNotNullParameter($this$relativeToOrNull, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        String p0 = toRelativeStringOrNull$FilesKt__UtilsKt($this$relativeToOrNull, base);
        if (p0 != null) {
            return new File(p0);
        }
        return null;
    }

    private static final String toRelativeStringOrNull$FilesKt__UtilsKt(File $this$toRelativeStringOrNull, File base) {
        FilePathComponents thisComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents($this$toRelativeStringOrNull));
        FilePathComponents baseComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents(base));
        if (!Intrinsics.areEqual((Object) thisComponents.getRoot(), (Object) baseComponents.getRoot())) {
            return null;
        }
        int baseCount = baseComponents.getSize();
        int thisCount = thisComponents.getSize();
        File file = $this$toRelativeStringOrNull;
        int i = 0;
        int maxSameCount = Math.min(thisCount, baseCount);
        while (i < maxSameCount && Intrinsics.areEqual((Object) thisComponents.getSegments().get(i), (Object) baseComponents.getSegments().get(i))) {
            i++;
        }
        StringBuilder res = new StringBuilder();
        int i2 = baseCount - 1;
        if (i <= i2) {
            while (!Intrinsics.areEqual((Object) baseComponents.getSegments().get(i2).getName(), (Object) "..")) {
                res.append("..");
                if (i2 != i) {
                    res.append(File.separatorChar);
                }
                if (i2 != i) {
                    i2--;
                }
            }
            return null;
        }
        if (i < thisCount) {
            if (i < baseCount) {
                res.append(File.separatorChar);
            }
            String str = File.separator;
            Intrinsics.checkNotNullExpressionValue(str, "separator");
            CollectionsKt.joinTo$default(CollectionsKt.drop(thisComponents.getSegments(), i), res, str, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 124, (Object) null);
        }
        return res.toString();
    }

    public static /* synthetic */ File copyTo$default(File file, File file2, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 8192;
        }
        return FilesKt.copyTo(file, file2, z, i);
    }

    /* JADX INFO: finally extract failed */
    public static final File copyTo(File $this$copyTo, File target, boolean overwrite, int bufferSize) {
        Throwable th;
        Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
        Intrinsics.checkNotNullParameter(target, TypedValues.AttributesType.S_TARGET);
        if ($this$copyTo.exists()) {
            if (target.exists()) {
                if (!overwrite) {
                    throw new FileAlreadyExistsException($this$copyTo, target, "The destination file already exists.");
                } else if (!target.delete()) {
                    throw new FileAlreadyExistsException($this$copyTo, target, "Tried to overwrite the destination, but failed to delete it.");
                }
            }
            if (!$this$copyTo.isDirectory()) {
                File parentFile = target.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                Closeable fileInputStream = new FileInputStream($this$copyTo);
                try {
                    FileInputStream input = (FileInputStream) fileInputStream;
                    Closeable fileOutputStream = new FileOutputStream(target);
                    try {
                        ByteStreamsKt.copyTo(input, (FileOutputStream) fileOutputStream, bufferSize);
                        CloseableKt.closeFinally(fileOutputStream, (Throwable) null);
                        CloseableKt.closeFinally(fileInputStream, (Throwable) null);
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    try {
                        throw th4;
                    } catch (Throwable th5) {
                        CloseableKt.closeFinally(fileInputStream, th4);
                        throw th5;
                    }
                }
            } else if (!target.mkdirs()) {
                throw new FileSystemException($this$copyTo, target, "Failed to create target directory.");
            }
            return target;
        }
        throw new NoSuchFileException($this$copyTo, (File) null, "The source file doesn't exist.", 2, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ boolean copyRecursively$default(File file, File file2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            function2 = FilesKt__UtilsKt$copyRecursively$1.INSTANCE;
        }
        return FilesKt.copyRecursively(file, file2, z, function2);
    }

    public static final boolean copyRecursively(File $this$copyRecursively, File target, boolean overwrite, Function2<? super File, ? super IOException, ? extends OnErrorAction> onError) {
        boolean stillExists;
        Intrinsics.checkNotNullParameter($this$copyRecursively, "<this>");
        Intrinsics.checkNotNullParameter(target, TypedValues.AttributesType.S_TARGET);
        Intrinsics.checkNotNullParameter(onError, "onError");
        if (!$this$copyRecursively.exists()) {
            File $this$copyRecursively2 = $this$copyRecursively;
            if (onError.invoke($this$copyRecursively2, new NoSuchFileException($this$copyRecursively2, (File) null, "The source file doesn't exist.", 2, (DefaultConstructorMarker) null)) != OnErrorAction.TERMINATE) {
                return true;
            }
            return false;
        }
        File $this$copyRecursively3 = $this$copyRecursively;
        try {
            Iterator<File> it = FilesKt.walkTopDown($this$copyRecursively3).onFail(new FilesKt__UtilsKt$copyRecursively$2(onError)).iterator();
            while (it.hasNext()) {
                File src = it.next();
                if (!src.exists()) {
                    File src2 = src;
                    try {
                        if (onError.invoke(src2, new NoSuchFileException(src2, (File) null, "The source file doesn't exist.", 2, (DefaultConstructorMarker) null)) == OnErrorAction.TERMINATE) {
                            return false;
                        }
                    } catch (TerminateException e) {
                        TerminateException terminateException = e;
                        boolean z = overwrite;
                    }
                } else {
                    File src3 = src;
                    File dstFile = new File(target, FilesKt.toRelativeString(src3, $this$copyRecursively3));
                    if (dstFile.exists()) {
                        if (!src3.isDirectory() || !dstFile.isDirectory()) {
                            if (!overwrite) {
                                stillExists = true;
                            } else if (dstFile.isDirectory()) {
                                stillExists = !FilesKt.deleteRecursively(dstFile);
                            } else {
                                stillExists = !dstFile.delete();
                            }
                            if (stillExists) {
                                if (onError.invoke(dstFile, new FileAlreadyExistsException(src3, dstFile, "The destination file already exists.")) == OnErrorAction.TERMINATE) {
                                    return false;
                                }
                            }
                        }
                    }
                    if (src3.isDirectory()) {
                        dstFile.mkdirs();
                    } else {
                        boolean overwrite2 = overwrite;
                        File src4 = src3;
                        try {
                            File src5 = src4;
                            if (FilesKt.copyTo$default(src4, dstFile, overwrite2, 0, 4, (Object) null).length() == src5.length()) {
                                overwrite = overwrite2;
                            } else if (onError.invoke(src5, new IOException("Source file wasn't copied completely, length of destination file differs.")) == OnErrorAction.TERMINATE) {
                                return false;
                            } else {
                                overwrite = overwrite2;
                            }
                        } catch (TerminateException e2) {
                            TerminateException terminateException2 = e2;
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (TerminateException e3) {
            boolean z2 = overwrite;
            TerminateException terminateException3 = e3;
            return false;
        }
    }

    public static final boolean deleteRecursively(File $this$deleteRecursively) {
        Intrinsics.checkNotNullParameter($this$deleteRecursively, "<this>");
        boolean accumulator$iv = true;
        for (File it : FilesKt.walkBottomUp($this$deleteRecursively)) {
            accumulator$iv = (it.delete() || !it.exists()) && accumulator$iv;
        }
        return accumulator$iv;
    }

    public static final boolean startsWith(File $this$startsWith, File other) {
        Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        FilePathComponents components = FilesKt.toComponents($this$startsWith);
        FilePathComponents otherComponents = FilesKt.toComponents(other);
        if (Intrinsics.areEqual((Object) components.getRoot(), (Object) otherComponents.getRoot()) && components.getSize() >= otherComponents.getSize()) {
            return components.getSegments().subList(0, otherComponents.getSize()).equals(otherComponents.getSegments());
        }
        return false;
    }

    public static final boolean startsWith(File $this$startsWith, String other) {
        Intrinsics.checkNotNullParameter($this$startsWith, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return FilesKt.startsWith($this$startsWith, new File(other));
    }

    public static final boolean endsWith(File $this$endsWith, File other) {
        Intrinsics.checkNotNullParameter($this$endsWith, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        FilePathComponents components = FilesKt.toComponents($this$endsWith);
        FilePathComponents otherComponents = FilesKt.toComponents(other);
        if (otherComponents.isRooted()) {
            return Intrinsics.areEqual((Object) $this$endsWith, (Object) other);
        }
        int shift = components.getSize() - otherComponents.getSize();
        if (shift < 0) {
            return false;
        }
        return components.getSegments().subList(shift, components.getSize()).equals(otherComponents.getSegments());
    }

    public static final boolean endsWith(File $this$endsWith, String other) {
        Intrinsics.checkNotNullParameter($this$endsWith, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return FilesKt.endsWith($this$endsWith, new File(other));
    }

    public static final File normalize(File $this$normalize) {
        Intrinsics.checkNotNullParameter($this$normalize, "<this>");
        FilePathComponents $this$normalize_u24lambda_u245 = FilesKt.toComponents($this$normalize);
        File root = $this$normalize_u24lambda_u245.getRoot();
        String str = File.separator;
        Intrinsics.checkNotNullExpressionValue(str, "separator");
        return FilesKt.resolve(root, CollectionsKt.joinToString$default(normalize$FilesKt__UtilsKt((List<? extends File>) $this$normalize_u24lambda_u245.getSegments()), str, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
    }

    private static final FilePathComponents normalize$FilesKt__UtilsKt(FilePathComponents $this$normalize) {
        return new FilePathComponents($this$normalize.getRoot(), normalize$FilesKt__UtilsKt((List<? extends File>) $this$normalize.getSegments()));
    }

    private static final List<File> normalize$FilesKt__UtilsKt(List<? extends File> $this$normalize) {
        List list = new ArrayList($this$normalize.size());
        for (File file : $this$normalize) {
            String name = file.getName();
            if (!Intrinsics.areEqual((Object) name, (Object) ".")) {
                if (!Intrinsics.areEqual((Object) name, (Object) "..")) {
                    list.add(file);
                } else if (list.isEmpty() || Intrinsics.areEqual((Object) ((File) CollectionsKt.last(list)).getName(), (Object) "..")) {
                    list.add(file);
                } else {
                    list.remove(list.size() - 1);
                }
            }
        }
        return list;
    }

    public static final File resolve(File $this$resolve, File relative) {
        Intrinsics.checkNotNullParameter($this$resolve, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        if (FilesKt.isRooted(relative)) {
            return relative;
        }
        String baseName = $this$resolve.toString();
        Intrinsics.checkNotNullExpressionValue(baseName, "toString(...)");
        return ((baseName.length() == 0) || StringsKt.endsWith$default((CharSequence) baseName, File.separatorChar, false, 2, (Object) null)) ? new File(baseName + relative) : new File(baseName + File.separatorChar + relative);
    }

    public static final File resolve(File $this$resolve, String relative) {
        Intrinsics.checkNotNullParameter($this$resolve, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        return FilesKt.resolve($this$resolve, new File(relative));
    }

    public static final File resolveSibling(File $this$resolveSibling, File relative) {
        Intrinsics.checkNotNullParameter($this$resolveSibling, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        FilePathComponents components = FilesKt.toComponents($this$resolveSibling);
        return FilesKt.resolve(FilesKt.resolve(components.getRoot(), components.getSize() == 0 ? new File("..") : components.subPath(0, components.getSize() - 1)), relative);
    }

    public static final File resolveSibling(File $this$resolveSibling, String relative) {
        Intrinsics.checkNotNullParameter($this$resolveSibling, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        return FilesKt.resolveSibling($this$resolveSibling, new File(relative));
    }
}
