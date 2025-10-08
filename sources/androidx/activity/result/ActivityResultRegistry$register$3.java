package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u00002\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0003H\u0016R\u001e\u0010\t\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\n8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"androidx/activity/result/ActivityResultRegistry$register$3", "Landroidx/activity/result/ActivityResultLauncher;", "launch", "", "input", "options", "Landroidx/core/app/ActivityOptionsCompat;", "(Ljava/lang/Object;Landroidx/core/app/ActivityOptionsCompat;)V", "unregister", "contract", "Landroidx/activity/result/contract/ActivityResultContract;", "getContract", "()Landroidx/activity/result/contract/ActivityResultContract;", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: ActivityResultRegistry.kt */
public final class ActivityResultRegistry$register$3 extends ActivityResultLauncher<I> {
    final /* synthetic */ ActivityResultContract<I, O> $contract;
    final /* synthetic */ String $key;
    final /* synthetic */ ActivityResultRegistry this$0;

    ActivityResultRegistry$register$3(ActivityResultRegistry $receiver, String $key2, ActivityResultContract<I, O> $contract2) {
        this.this$0 = $receiver;
        this.$key = $key2;
        this.$contract = $contract2;
    }

    public void launch(I input, ActivityOptionsCompat options) {
        Object obj = this.this$0.keyToRc.get(this.$key);
        ActivityResultContract<I, O> activityResultContract = this.$contract;
        if (obj != null) {
            int innerCode = ((Number) obj).intValue();
            this.this$0.launchedKeys.add(this.$key);
            try {
                this.this$0.onLaunch(innerCode, this.$contract, input, options);
            } catch (Exception e) {
                this.this$0.launchedKeys.remove(this.$key);
                throw e;
            }
        } else {
            throw new IllegalStateException(("Attempting to launch an unregistered ActivityResultLauncher with contract " + activityResultContract + " and input " + input + ". You must ensure the ActivityResultLauncher is registered before calling launch().").toString());
        }
    }

    public void unregister() {
        this.this$0.unregister$activity_release(this.$key);
    }

    public ActivityResultContract<I, ?> getContract() {
        return this.$contract;
    }
}
