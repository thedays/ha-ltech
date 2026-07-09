package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.upgrade.ActBtOtaVM;

/* loaded from: classes3.dex */
public abstract class ItemBtOtaBinding extends ViewDataBinding {
    public final AppCompatImageView ivUpgradeFail;
    public final AppCompatImageView ivUpgradeSuccess;
    public final ContentLoadingProgressBar ivUpgradeWaiting;
    public final ConstraintLayout layoutBg;
    public final RelativeLayout layoutUpgradeStatus;

    @Bindable
    protected String mBtnName;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mDeviceName;

    @Bindable
    protected ActBtOtaVM.ScanItem mItem;

    @Bindable
    protected String mStrProgress;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvUpgradeProgress;

    public abstract void setBtnName(String btnName);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDeviceName(String deviceName);

    public abstract void setItem(ActBtOtaVM.ScanItem item);

    public abstract void setStrProgress(String strProgress);

    protected ItemBtOtaBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivUpgradeFail, AppCompatImageView ivUpgradeSuccess, ContentLoadingProgressBar ivUpgradeWaiting, ConstraintLayout layoutBg, RelativeLayout layoutUpgradeStatus, AppCompatTextView tvName, AppCompatTextView tvUpgradeProgress) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivUpgradeFail = ivUpgradeFail;
        this.ivUpgradeSuccess = ivUpgradeSuccess;
        this.ivUpgradeWaiting = ivUpgradeWaiting;
        this.layoutBg = layoutBg;
        this.layoutUpgradeStatus = layoutUpgradeStatus;
        this.tvName = tvName;
        this.tvUpgradeProgress = tvUpgradeProgress;
    }

    public ActBtOtaVM.ScanItem getItem() {
        return this.mItem;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getStrProgress() {
        return this.mStrProgress;
    }

    public String getBtnName() {
        return this.mBtnName;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemBtOtaBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBtOtaBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBtOtaBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bt_ota, root, attachToRoot, component);
    }

    public static ItemBtOtaBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBtOtaBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemBtOtaBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bt_ota, null, false, component);
    }

    public static ItemBtOtaBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBtOtaBinding bind(View view, Object component) {
        return (ItemBtOtaBinding) bind(component, view, R.layout.item_bt_ota);
    }
}