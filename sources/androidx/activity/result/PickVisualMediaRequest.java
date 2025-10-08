package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContracts;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001*B\t\b\u0000¢\u0006\u0004\b\u0002\u0010\u0003R$\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@@X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR$\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000b@@X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0011@@X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R$\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u0016@@X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR$\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0011@@X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u0015R$\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u001e@@X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R(\u0010%\u001a\u0004\u0018\u00010$2\b\u0010\u0004\u001a\u0004\u0018\u00010$@@X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)¨\u0006+"}, d2 = {"Landroidx/activity/result/PickVisualMediaRequest;", "", "<init>", "()V", "value", "Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$VisualMediaType;", "mediaType", "getMediaType", "()Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$VisualMediaType;", "setMediaType$activity_release", "(Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$VisualMediaType;)V", "", "maxItems", "getMaxItems", "()I", "setMaxItems$activity_release", "(I)V", "", "isOrderedSelection", "()Z", "setOrderedSelection$activity_release", "(Z)V", "Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$DefaultTab;", "defaultTab", "getDefaultTab", "()Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$DefaultTab;", "setDefaultTab$activity_release", "(Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$DefaultTab;)V", "isCustomAccentColorApplied", "setCustomAccentColorApplied$activity_release", "", "accentColor", "getAccentColor", "()J", "setAccentColor$activity_release", "(J)V", "Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$MediaCapabilities;", "mediaCapabilitiesForTranscoding", "getMediaCapabilitiesForTranscoding", "()Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$MediaCapabilities;", "setMediaCapabilitiesForTranscoding$activity_release", "(Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$MediaCapabilities;)V", "Builder", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: PickVisualMediaRequest.kt */
public final class PickVisualMediaRequest {
    private long accentColor;
    private ActivityResultContracts.PickVisualMedia.DefaultTab defaultTab = ActivityResultContracts.PickVisualMedia.DefaultTab.PhotosTab.INSTANCE;
    private boolean isCustomAccentColorApplied;
    private boolean isOrderedSelection;
    private int maxItems = ActivityResultContracts.PickMultipleVisualMedia.Companion.getMaxItems$activity_release();
    private ActivityResultContracts.PickVisualMedia.MediaCapabilities mediaCapabilitiesForTranscoding;
    private ActivityResultContracts.PickVisualMedia.VisualMediaType mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE;

    public final ActivityResultContracts.PickVisualMedia.VisualMediaType getMediaType() {
        return this.mediaType;
    }

    public final void setMediaType$activity_release(ActivityResultContracts.PickVisualMedia.VisualMediaType visualMediaType) {
        Intrinsics.checkNotNullParameter(visualMediaType, "<set-?>");
        this.mediaType = visualMediaType;
    }

    public final int getMaxItems() {
        return this.maxItems;
    }

    public final void setMaxItems$activity_release(int i) {
        this.maxItems = i;
    }

    public final boolean isOrderedSelection() {
        return this.isOrderedSelection;
    }

    public final void setOrderedSelection$activity_release(boolean z) {
        this.isOrderedSelection = z;
    }

    public final ActivityResultContracts.PickVisualMedia.DefaultTab getDefaultTab() {
        return this.defaultTab;
    }

    public final void setDefaultTab$activity_release(ActivityResultContracts.PickVisualMedia.DefaultTab defaultTab2) {
        Intrinsics.checkNotNullParameter(defaultTab2, "<set-?>");
        this.defaultTab = defaultTab2;
    }

    public final boolean isCustomAccentColorApplied() {
        return this.isCustomAccentColorApplied;
    }

    public final void setCustomAccentColorApplied$activity_release(boolean z) {
        this.isCustomAccentColorApplied = z;
    }

    public final long getAccentColor() {
        return this.accentColor;
    }

    public final void setAccentColor$activity_release(long j) {
        this.accentColor = j;
    }

    public final ActivityResultContracts.PickVisualMedia.MediaCapabilities getMediaCapabilitiesForTranscoding() {
        return this.mediaCapabilitiesForTranscoding;
    }

    public final void setMediaCapabilitiesForTranscoding$activity_release(ActivityResultContracts.PickVisualMedia.MediaCapabilities mediaCapabilities) {
        this.mediaCapabilitiesForTranscoding = mediaCapabilities;
    }

    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005J\u0010\u0010\u0012\u001a\u00020\u00002\b\b\u0001\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u000eJ\u0012\u0010\u0016\u001a\u00020\u00002\b\u0010\u0017\u001a\u0004\u0018\u00010\u0010H\u0007J\u0006\u0010\u0018\u001a\u00020\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Landroidx/activity/result/PickVisualMediaRequest$Builder;", "", "<init>", "()V", "mediaType", "Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$VisualMediaType;", "maxItems", "", "isOrderedSelection", "", "defaultTab", "Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$DefaultTab;", "isCustomAccentColorApplied", "accentColor", "", "mediaCapabilitiesForTranscoding", "Landroidx/activity/result/contract/ActivityResultContracts$PickVisualMedia$MediaCapabilities;", "setMediaType", "setMaxItems", "setOrderedSelection", "setDefaultTab", "setAccentColor", "setMediaCapabilitiesForTranscoding", "mediaCapabilities", "build", "Landroidx/activity/result/PickVisualMediaRequest;", "activity_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: PickVisualMediaRequest.kt */
    public static final class Builder {
        private long accentColor;
        private ActivityResultContracts.PickVisualMedia.DefaultTab defaultTab = ActivityResultContracts.PickVisualMedia.DefaultTab.PhotosTab.INSTANCE;
        private boolean isCustomAccentColorApplied;
        private boolean isOrderedSelection;
        private int maxItems = ActivityResultContracts.PickMultipleVisualMedia.Companion.getMaxItems$activity_release();
        private ActivityResultContracts.PickVisualMedia.MediaCapabilities mediaCapabilitiesForTranscoding;
        private ActivityResultContracts.PickVisualMedia.VisualMediaType mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE;

        public final Builder setMediaType(ActivityResultContracts.PickVisualMedia.VisualMediaType mediaType2) {
            Intrinsics.checkNotNullParameter(mediaType2, "mediaType");
            this.mediaType = mediaType2;
            return this;
        }

        public final Builder setMaxItems(int maxItems2) {
            this.maxItems = maxItems2;
            return this;
        }

        public final Builder setOrderedSelection(boolean isOrderedSelection2) {
            this.isOrderedSelection = isOrderedSelection2;
            return this;
        }

        public final Builder setDefaultTab(ActivityResultContracts.PickVisualMedia.DefaultTab defaultTab2) {
            Intrinsics.checkNotNullParameter(defaultTab2, "defaultTab");
            this.defaultTab = defaultTab2;
            return this;
        }

        public final Builder setAccentColor(long accentColor2) {
            this.accentColor = accentColor2;
            this.isCustomAccentColorApplied = true;
            return this;
        }

        public final Builder setMediaCapabilitiesForTranscoding(ActivityResultContracts.PickVisualMedia.MediaCapabilities mediaCapabilities) {
            this.mediaCapabilitiesForTranscoding = mediaCapabilities;
            return this;
        }

        public final PickVisualMediaRequest build() {
            PickVisualMediaRequest pickVisualMediaRequest = new PickVisualMediaRequest();
            PickVisualMediaRequest $this$build_u24lambda_u240 = pickVisualMediaRequest;
            $this$build_u24lambda_u240.setMediaType$activity_release(this.mediaType);
            $this$build_u24lambda_u240.setMaxItems$activity_release(this.maxItems);
            $this$build_u24lambda_u240.setOrderedSelection$activity_release(this.isOrderedSelection);
            $this$build_u24lambda_u240.setDefaultTab$activity_release(this.defaultTab);
            $this$build_u24lambda_u240.setCustomAccentColorApplied$activity_release(this.isCustomAccentColorApplied);
            $this$build_u24lambda_u240.setAccentColor$activity_release(this.accentColor);
            $this$build_u24lambda_u240.setMediaCapabilitiesForTranscoding$activity_release(this.mediaCapabilitiesForTranscoding);
            return pickVisualMediaRequest;
        }
    }
}
