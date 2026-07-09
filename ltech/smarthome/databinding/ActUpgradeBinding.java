package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.smart.dialog.util.view.ProgressView;

/* loaded from: classes3.dex */
public abstract class ActUpgradeBinding extends ViewDataBinding {
    public final AppCompatImageView ivUpgrade;
    public final LinearLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final ProgressView progressBar;
    public final AppCompatTextView tvProgress;
    public final AppCompatTextView tvUpgrade;
    public final AppCompatTextView tvUpgradeTip1;
    public final AppCompatTextView tvUpgradeTip2;
    public final AppCompatTextView tvUpgradeTip3;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActUpgradeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivUpgrade, LinearLayout layoutLoad, ProgressView progressBar, AppCompatTextView tvProgress, AppCompatTextView tvUpgrade, AppCompatTextView tvUpgradeTip1, AppCompatTextView tvUpgradeTip2, AppCompatTextView tvUpgradeTip3) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivUpgrade = ivUpgrade;
        this.layoutLoad = layoutLoad;
        this.progressBar = progressBar;
        this.tvProgress = tvProgress;
        this.tvUpgrade = tvUpgrade;
        this.tvUpgradeTip1 = tvUpgradeTip1;
        this.tvUpgradeTip2 = tvUpgradeTip2;
        this.tvUpgradeTip3 = tvUpgradeTip3;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActUpgradeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActUpgradeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActUpgradeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_upgrade, root, attachToRoot, component);
    }

    public static ActUpgradeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActUpgradeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActUpgradeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_upgrade, null, false, component);
    }

    public static ActUpgradeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActUpgradeBinding bind(View view, Object component) {
        return (ActUpgradeBinding) bind(component, view, R.layout.act_upgrade);
    }
}