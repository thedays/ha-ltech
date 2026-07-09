package com.ltech.smarthome.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.ActivityUtils;
import java.util.ArrayList;
import javax.annotation.Nonnull;

/* loaded from: classes4.dex */
public class NavUtils {
    private NavUtils() {
    }

    public static Builder destination(@Nonnull Class clz) {
        return new Builder(clz);
    }

    public static Builder destination(@Nonnull String classPath) {
        try {
            return new Builder(Class.forName(classPath));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new Builder();
        }
    }

    public static Builder result() {
        return new Builder();
    }

    public static final class Builder {
        private Class clz;
        private Bundle mBundle;
        private int requestCode;
        private int resultCode;

        private Builder() {
            this.requestCode = -1;
            this.resultCode = 0;
        }

        private Builder(@Nonnull Class clz) {
            this.requestCode = -1;
            this.resultCode = 0;
            this.clz = clz;
        }

        private Bundle getBundle() {
            if (this.mBundle == null) {
                this.mBundle = new Bundle();
            }
            return this.mBundle;
        }

        public Builder withBundle(Bundle bundle) {
            if (bundle != null) {
                this.mBundle = bundle;
            }
            return this;
        }

        public Builder withLong(String tag, long param) {
            getBundle().putLong(tag, param);
            return this;
        }

        public Builder withLongArray(String tag, long[] param) {
            getBundle().putLongArray(tag, param);
            return this;
        }

        public Builder withString(String tag, String param) {
            getBundle().putString(tag, param);
            return this;
        }

        public Builder withParcelable(String tag, Parcelable param) {
            getBundle().putParcelable(tag, param);
            return this;
        }

        public Builder withInt(String tag, int param) {
            getBundle().putInt(tag, param);
            return this;
        }

        public Builder withIntArray(String tag, int[] param) {
            getBundle().putIntArray(tag, param);
            return this;
        }

        public Builder withIntegerArrayList(String tag, ArrayList<Integer> param) {
            getBundle().putIntegerArrayList(tag, param);
            return this;
        }

        public Builder withArrayList(String tag, ArrayList<Integer> param) {
            getBundle().putIntegerArrayList(tag, param);
            return this;
        }

        public Builder withStringArray(String tag, String[] param) {
            getBundle().putStringArray(tag, param);
            return this;
        }

        public Builder withBoolean(String tag, boolean param) {
            getBundle().putBoolean(tag, param);
            return this;
        }

        public Builder withDouble(String tag, double param) {
            getBundle().putDouble(tag, param);
            return this;
        }

        public Builder withRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder withDefaultRequestCode() {
            this.requestCode = 0;
            return this;
        }

        public Builder withResultCode(int resultCode) {
            this.resultCode = resultCode;
            return this;
        }

        private Intent createIntent(Context context) {
            Intent intent = new Intent(context, (Class<?>) this.clz);
            Bundle bundle = this.mBundle;
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            return intent;
        }

        public void navigation(Activity activity) {
            if (this.requestCode == -1) {
                activity.startActivity(createIntent(activity));
            } else {
                activity.startActivityForResult(createIntent(activity), this.requestCode);
            }
        }

        public void navigation(Fragment fragment) {
            if (this.requestCode == -1) {
                fragment.startActivity(createIntent(ActivityUtils.getTopActivity()));
            } else {
                fragment.startActivityForResult(createIntent(ActivityUtils.getTopActivity()), this.requestCode);
            }
        }

        public void setResult(Activity activity) {
            if (this.mBundle != null) {
                activity.setResult(this.resultCode, new Intent().putExtras(this.mBundle));
            } else {
                activity.setResult(this.resultCode);
            }
        }
    }
}