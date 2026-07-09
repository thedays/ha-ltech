package com.ltech.smarthome.utils;

import android.app.Activity;
import android.app.LocaleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.justalk.cloud.lemon.MtcUeConstants;
import com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0;
import java.util.Locale;

/* loaded from: classes4.dex */
public class LanguageUtils {
    public static void applySystemLanguage(Context context) {
        applyLanguage(context, Resources.getSystem().getConfiguration().locale, "", true, false);
    }

    public static void applyLanguage(Context context, final Locale locale) {
        applyLanguage(context, locale, "", false, false);
    }

    public static void applyLanguage(Context context, final Locale locale, final Class<? extends Activity> activityClz) {
        if (activityClz == null) {
            applyLanguage(context, locale, "", false, true);
        } else {
            applyLanguage(context, locale, activityClz.getName(), false, true);
        }
    }

    public static void applyLanguage(Context context, final Locale locale, final String activityClassName) {
        applyLanguage(context, locale, activityClassName, false, true);
    }

    private static void applyLanguage(Context context, final Locale locale, final String activityClassName, final boolean isFollowSystem, final boolean isNeedStartActivity) {
        updateLanguage(context, locale, isFollowSystem);
        if (isNeedStartActivity) {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(activityClassName)) {
                activityClassName = getLauncherActivity(context);
            }
            intent.setComponent(new ComponentName(context, activityClassName));
            intent.addFlags(335577088);
            context.startActivity(intent);
        }
    }

    private static void updateLanguage(Context context, Locale locale, boolean isFollowSystem) {
        Object systemService;
        LocaleList forLanguageTags;
        if (Build.VERSION.SDK_INT >= 34) {
            systemService = context.getSystemService((Class<Object>) GuideView$$ExternalSyntheticApiModelOutline0.m4240m());
            LocaleManager m2 = GuideView$$ExternalSyntheticApiModelOutline0.m(systemService);
            forLanguageTags = LocaleList.forLanguageTags(locale.toLanguageTag());
            m2.setApplicationLocales(forLanguageTags);
        } else {
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            Locale locale2 = configuration.locale;
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            configuration.setLocale(locale);
            context.createConfigurationContext(configuration);
            resources.updateConfiguration(configuration, displayMetrics);
        }
        if (isFollowSystem) {
            return;
        }
        LHomeSharedPreferencesUtil.putString(context, Constants.CURRENT_LANGUAGE, locale.toString());
    }

    public static String getCurrentLocaleLanguage(Context context) {
        return LHomeSharedPreferencesUtil.getString(context, Constants.CURRENT_LANGUAGE, "");
    }

    public static boolean isRussian(Context context) {
        String currentLocaleLanguage = getCurrentLocaleLanguage(context);
        if (!TextUtils.isEmpty(currentLocaleLanguage)) {
            return currentLocaleLanguage.contains("ru");
        }
        return com.blankj.utilcode.util.LanguageUtils.getAppContextLanguage().getLanguage().equals("ru");
    }

    public static boolean isChinese(Context context) {
        String currentLocaleLanguage = getCurrentLocaleLanguage(context);
        if (!TextUtils.isEmpty(currentLocaleLanguage)) {
            return currentLocaleLanguage.contains(MtcUeConstants.MTC_UE_AUTHCODE_IN_CHN);
        }
        return com.blankj.utilcode.util.LanguageUtils.getAppContextLanguage().getLanguage().contains(MtcUeConstants.MTC_UE_AUTHCODE_IN_CHN);
    }

    public static boolean isEnglish(Context context) {
        String currentLocaleLanguage = getCurrentLocaleLanguage(context);
        if (!TextUtils.isEmpty(currentLocaleLanguage)) {
            return currentLocaleLanguage.contains("en");
        }
        return com.blankj.utilcode.util.LanguageUtils.getAppContextLanguage().getLanguage().equals("en");
    }

    private static String getLauncherActivity(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(context.getPackageName());
        ResolveInfo next = context.getPackageManager().queryIntentActivities(intent, 0).iterator().next();
        if (next != null) {
            return next.activityInfo.name;
        }
        return "no launcher activity";
    }

    private static boolean equals(final CharSequence s1, final CharSequence s2) {
        int length;
        if (s1 == s2) {
            return true;
        }
        if (s1 == null || s2 == null || (length = s1.length()) != s2.length()) {
            return false;
        }
        if ((s1 instanceof String) && (s2 instanceof String)) {
            return s1.equals(s2);
        }
        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}