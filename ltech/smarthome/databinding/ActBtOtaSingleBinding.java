package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.upgrade.ActBtOtaVM;
import com.smart.dialog.util.view.ProgressView;

/* loaded from: classes3.dex */
public abstract class ActBtOtaSingleBinding extends ViewDataBinding {
    public final AppCompatImageView ivUpgrade;
    public final ContentLoadingProgressBar ivUpgradeWaiting;
    public final LinearLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBtOtaVM mViewmodel;
    public final ProgressView progressBar;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvProgress;
    public final AppCompatTextView tvUpgrade;
    public final AppCompatTextView tvUpgradeTip1;
    public final AppCompatTextView tvUpgradeTip2;
    public final AppCompatTextView tvUpgradeTip3;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBtOtaVM viewmodel);

    protected ActBtOtaSingleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivUpgrade, ContentLoadingProgressBar ivUpgradeWaiting, LinearLayout layoutLoad, ProgressView progressBar, LayoutTitleDefaultBinding title, AppCompatTextView tvProgress, AppCompatTextView tvUpgrade, AppCompatTextView tvUpgradeTip1, AppCompatTextView tvUpgradeTip2, AppCompatTextView tvUpgradeTip3) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivUpgrade = ivUpgrade;
        this.ivUpgradeWaiting = ivUpgradeWaiting;
        this.layoutLoad = layoutLoad;
        this.progressBar = progressBar;
        this.title = title;
        this.tvProgress = tvProgress;
        this.tvUpgrade = tvUpgrade;
        this.tvUpgradeTip1 = tvUpgradeTip1;
        this.tvUpgradeTip2 = tvUpgradeTip2;
        this.tvUpgradeTip3 = tvUpgradeTip3;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActBtOtaVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActBtOtaSingleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaSingleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBtOtaSingleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_bt_ota_single, root, attachToRoot, component);
    }

    public static ActBtOtaSingleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaSingleBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBtOtaSingleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_bt_ota_single, null, false, component);
    }

    public static ActBtOtaSingleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaSingleBinding bind(View view, Object component) {
        return (ActBtOtaSingleBinding) bind(component, view, R.layout.act_bt_ota_single);
    }
}