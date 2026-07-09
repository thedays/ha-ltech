package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.view.SignalView;

/* loaded from: classes3.dex */
public abstract class ItemMeshScanBinding extends ViewDataBinding {
    public final AppCompatImageView ivAdd;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivUpgradeFail;
    public final AppCompatImageView ivUpgradeSuccess;
    public final ContentLoadingProgressBar ivUpgradeWaiting;
    public final RelativeLayout layoutAdd;
    public final ConstraintLayout layoutBg;
    public final LinearLayout layoutEnd;
    public final RelativeLayout layoutUpgradeStatus;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected BindingCommand<View> mClicklocation;

    @Bindable
    protected String mCurrentVersion;

    @Bindable
    protected String mDeviceName;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected ActMeshScanVM.ScanItem mItem;

    @Bindable
    protected String mNewVersion;

    @Bindable
    protected Integer mSignal;

    @Bindable
    protected String mStrProgress;

    @Bindable
    protected String mUpgradeName;
    public final SignalView signalView;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvCurrentVersion;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvNewVersion;
    public final AppCompatTextView tvUpgradeItem;
    public final AppCompatTextView tvUpgradeProgress;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setClicklocation(BindingCommand<View> clicklocation);

    public abstract void setCurrentVersion(String currentVersion);

    public abstract void setDeviceName(String deviceName);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setItem(ActMeshScanVM.ScanItem item);

    public abstract void setNewVersion(String newVersion);

    public abstract void setSignal(Integer signal);

    public abstract void setStrProgress(String strProgress);

    public abstract void setUpgradeName(String upgradeName);

    protected ItemMeshScanBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivAdd, AppCompatImageView ivIcon, AppCompatImageView ivUpgradeFail, AppCompatImageView ivUpgradeSuccess, ContentLoadingProgressBar ivUpgradeWaiting, RelativeLayout layoutAdd, ConstraintLayout layoutBg, LinearLayout layoutEnd, RelativeLayout layoutUpgradeStatus, SignalView signalView, AppCompatTextView tvAdd, AppCompatTextView tvCurrentVersion, AppCompatTextView tvName, AppCompatTextView tvNewVersion, AppCompatTextView tvUpgradeItem, AppCompatTextView tvUpgradeProgress) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivAdd = ivAdd;
        this.ivIcon = ivIcon;
        this.ivUpgradeFail = ivUpgradeFail;
        this.ivUpgradeSuccess = ivUpgradeSuccess;
        this.ivUpgradeWaiting = ivUpgradeWaiting;
        this.layoutAdd = layoutAdd;
        this.layoutBg = layoutBg;
        this.layoutEnd = layoutEnd;
        this.layoutUpgradeStatus = layoutUpgradeStatus;
        this.signalView = signalView;
        this.tvAdd = tvAdd;
        this.tvCurrentVersion = tvCurrentVersion;
        this.tvName = tvName;
        this.tvNewVersion = tvNewVersion;
        this.tvUpgradeItem = tvUpgradeItem;
        this.tvUpgradeProgress = tvUpgradeProgress;
    }

    public ActMeshScanVM.ScanItem getItem() {
        return this.mItem;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public String getCurrentVersion() {
        return this.mCurrentVersion;
    }

    public String getNewVersion() {
        return this.mNewVersion;
    }

    public String getStrProgress() {
        return this.mStrProgress;
    }

    public String getUpgradeName() {
        return this.mUpgradeName;
    }

    public Integer getSignal() {
        return this.mSignal;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public BindingCommand<View> getClicklocation() {
        return this.mClicklocation;
    }

    public static ItemMeshScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMeshScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_scan, root, attachToRoot, component);
    }

    public static ItemMeshScanBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMeshScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_scan, null, false, component);
    }

    public static ItemMeshScanBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanBinding bind(View view, Object component) {
        return (ItemMeshScanBinding) bind(component, view, R.layout.item_mesh_scan);
    }
}