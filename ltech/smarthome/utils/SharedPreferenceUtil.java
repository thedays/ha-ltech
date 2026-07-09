package com.ltech.smarthome.utils;

import android.content.Context;
import com.blankj.utilcode.util.GsonUtils;
import com.tencent.mmkv.MMKV;

/* loaded from: classes4.dex */
public class SharedPreferenceUtil {
    public static void init(Context context) {
        MMKV.initialize(context);
    }

    private SharedPreferenceUtil() {
    }

    private static MMKV getSharePreferences() {
        return MMKV.defaultMMKV();
    }

    public static <T> T getBean(String str, Class<T> cls) {
        return (T) GsonUtils.getGson().fromJson(queryValue(str + cls.getName()), (Class) cls);
    }

    public static byte[] queryByteArrayValue(String key) {
        return getSharePreferences().getBytes(key, null);
    }

    public static String queryValue(String key) {
        return getSharePreferences().getString(key, "");
    }

    public static int queryIntValue(String key) {
        return getSharePreferences().getInt(key, 0);
    }

    public static int queryIntValue(String key, int defaultValue) {
        return getSharePreferences().getInt(key, defaultValue);
    }

    public static boolean queryBooleanValue(String key) {
        return queryBooleanValue(key, false);
    }

    public static boolean queryBooleanValue(String key, boolean b2) {
        return getSharePreferences().getBoolean(key, b2);
    }

    public static long queryLongValue(String key) {
        return getSharePreferences().getLong(key, 0L);
    }

    public static EditBuilder edit() {
        return new EditBuilder(getSharePreferences());
    }

    public static final class EditBuilder {
        private MMKV editor;

        public EditBuilder(MMKV mmkv) {
            this.editor = mmkv;
        }

        public EditBuilder keepShared(String key, String value) {
            this.editor.putString(key, value);
            return this;
        }

        public EditBuilder keepShared(String key, long value) {
            this.editor.putLong(key, value);
            return this;
        }

        public EditBuilder keepShared(String key, int value) {
            this.editor.putInt(key, value);
            return this;
        }

        public EditBuilder keepShared(String key, boolean value) {
            this.editor.putBoolean(key, value);
            return this;
        }

        public EditBuilder keepShared(String key, byte[] value) {
            this.editor.putBytes(key, value);
            return this;
        }

        public EditBuilder deleteAllValue() {
            this.editor.clear();
            return this;
        }

        public EditBuilder deleteValue(String key) {
            this.editor.remove(key);
            return this;
        }

        public EditBuilder removeBean(String key, Class cls) {
            this.editor.remove(key + cls.getName());
            return this;
        }

        public EditBuilder putBean(String key, Object object) {
            this.editor.putString(key + object.getClass().getName(), GsonUtils.toJson(object));
            return this;
        }

        public void apply() {
            this.editor.apply();
        }

        public boolean commit() {
            return this.editor.commit();
        }
    }
}