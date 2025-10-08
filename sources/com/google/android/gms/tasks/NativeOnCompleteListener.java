package com.google.android.gms.tasks;

/* compiled from: com.google.android.gms:play-services-tasks@@18.1.0 */
public class NativeOnCompleteListener implements OnCompleteListener<Object> {
    private final long zza;

    public NativeOnCompleteListener(long j) {
        this.zza = j;
    }

    public static void createAndAddCallback(Task<Object> task, long aTaskPointer) {
        task.addOnCompleteListener(new NativeOnCompleteListener(aTaskPointer));
    }

    public native void nativeOnComplete(long j, Object obj, boolean z, boolean z2, String str);

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    public void onComplete(Task<Object> task) {
        String str;
        Object obj;
        Exception exception;
        if (task.isSuccessful()) {
            obj = task.getResult();
            str = null;
        } else if (task.isCanceled() || (exception = task.getException()) == null) {
            obj = null;
            str = null;
        } else {
            str = exception.getMessage();
            obj = null;
        }
        nativeOnComplete(this.zza, obj, task.isSuccessful(), task.isCanceled(), str);
    }
}
