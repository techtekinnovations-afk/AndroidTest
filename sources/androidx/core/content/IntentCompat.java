package androidx.core.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import androidx.core.util.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;

public final class IntentCompat {
    public static final String ACTION_CREATE_REMINDER = "android.intent.action.CREATE_REMINDER";
    public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final String EXTRA_START_PLAYBACK = "android.intent.extra.START_PLAYBACK";
    public static final String EXTRA_TIME = "android.intent.extra.TIME";

    private IntentCompat() {
    }

    public static Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
        return Intent.makeMainSelectorActivity(selectorAction, selectorCategory);
    }

    public static Intent createManageUnusedAppRestrictionsIntent(Context context, String packageName) {
        if (!PackageManagerCompat.areUnusedAppRestrictionsAvailable(context.getPackageManager())) {
            throw new UnsupportedOperationException("Unused App Restriction features are not available on this device");
        } else if (Build.VERSION.SDK_INT >= 31) {
            return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", packageName, (String) null));
        } else {
            Intent permissionRevocationSettingsIntent = new Intent(PackageManagerCompat.ACTION_PERMISSION_REVOCATION_SETTINGS).setData(Uri.fromParts("package", packageName, (String) null));
            if (Build.VERSION.SDK_INT >= 30) {
                return permissionRevocationSettingsIntent;
            }
            return permissionRevocationSettingsIntent.setPackage((String) Preconditions.checkNotNull(PackageManagerCompat.getPermissionRevocationVerifierApp(context.getPackageManager())));
        }
    }

    public static <T> T getParcelableExtra(Intent in, String name, Class<T> clazz) {
        if (Build.VERSION.SDK_INT >= 34) {
            return Api33Impl.getParcelableExtra(in, name, clazz);
        }
        T extra = in.getParcelableExtra(name);
        if (clazz.isInstance(extra)) {
            return extra;
        }
        return null;
    }

    public static Parcelable[] getParcelableArrayExtra(Intent in, String name, Class<? extends Parcelable> clazz) {
        if (Build.VERSION.SDK_INT >= 34) {
            return (Parcelable[]) Api33Impl.getParcelableArrayExtra(in, name, clazz);
        }
        return in.getParcelableArrayExtra(name);
    }

    public static <T> ArrayList<T> getParcelableArrayListExtra(Intent in, String name, Class<? extends T> clazz) {
        if (Build.VERSION.SDK_INT >= 34) {
            return Api33Impl.getParcelableArrayListExtra(in, name, clazz);
        }
        return in.getParcelableArrayListExtra(name);
    }

    public static <T extends Serializable> T getSerializableExtra(Intent in, String key, Class<T> clazz) {
        if (Build.VERSION.SDK_INT >= 34) {
            return Api33Impl.getSerializableExtra(in, key, clazz);
        }
        Serializable serializable = in.getSerializableExtra(key);
        if (clazz.isInstance(serializable)) {
            return serializable;
        }
        return null;
    }

    static class Api33Impl {
        private Api33Impl() {
        }

        static <T> T getParcelableExtra(Intent in, String name, Class<T> clazz) {
            return in.getParcelableExtra(name, clazz);
        }

        static <T> T[] getParcelableArrayExtra(Intent in, String name, Class<T> clazz) {
            return in.getParcelableArrayExtra(name, clazz);
        }

        static <T> ArrayList<T> getParcelableArrayListExtra(Intent in, String name, Class<? extends T> clazz) {
            return in.getParcelableArrayListExtra(name, clazz);
        }

        static <T extends Serializable> T getSerializableExtra(Intent in, String name, Class<T> clazz) {
            return in.getSerializableExtra(name, clazz);
        }
    }
}
