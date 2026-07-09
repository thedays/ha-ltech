package com.ltech.smarthome.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.dialog.DelFailDialog;

/* loaded from: classes4.dex */
public class ViewHelpUtil {
    public static void createTabCustomView(Context context, TabLayout.Tab tab, String text, boolean select) {
        View customView = tab.getCustomView();
        if (customView == null) {
            customView = LayoutInflater.from(context).inflate(R.layout.tab_text, (ViewGroup) null);
            tab.setCustomView(customView);
        }
        AppCompatTextView appCompatTextView = (AppCompatTextView) customView.findViewById(R.id.tv_tab);
        appCompatTextView.setText(text);
        if (select) {
            appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_black));
            appCompatTextView.getPaint().setFakeBoldText(true);
            if (LanguageUtils.isRussian(context)) {
                appCompatTextView.setTextSize(15.0f);
                return;
            } else {
                appCompatTextView.setTextSize(16.0f);
                return;
            }
        }
        appCompatTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_gray));
        appCompatTextView.getPaint().setFakeBoldText(false);
        appCompatTextView.setTextSize(14.0f);
    }

    public static void showBindFailDialog(FragmentActivity activity) {
        DelFailDialog.asDefault().setTitle(activity.getString(R.string.bind_ble_device_fail)).setContent(activity.getString(R.string.bind_ble_device_fail_reason)).setCancelString(activity.getString(R.string.ok)).showDialog(activity);
    }

    public static void showUnBindFailDialog(FragmentActivity activity) {
        DelFailDialog.asDefault().setTitle(activity.getString(R.string.unbind_ble_device_fail)).setContent(activity.getString(R.string.unbind_ble_device_fail_reason)).setCancelString(activity.getString(R.string.ok)).showDialog(activity);
    }

    public static void zoomInZoomOut(View view, Animation.AnimationListener listener) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(200L);
        animationSet.addAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(listener);
        view.startAnimation(animationSet);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(200L);
        alphaAnimation.setAnimationListener(listener);
        animationSet.addAnimation(alphaAnimation);
        view.startAnimation(animationSet);
    }
}