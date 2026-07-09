package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.view.CircularProgressView;
import com.ltech.smarthome.view.SignalView;

/* loaded from: classes3.dex */
public abstract class ItemMeshScanAllDeviceBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivUpgradeFail;
    public final AppCompatImageView ivUpgradeSuccess;
    public final CircularProgressView ivUpgradeWaiting;
    public final RelativeLayout layoutBg;

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
    public final SignalView signalView;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setClicklocation(BindingCommand<View> clicklocation);

    public abstract void setCurrentVersion(String currentVersion);

    public abstract void setDeviceName(String deviceName);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setItem(ActMeshScanVM.ScanItem item);

    public abstract void setNewVersion(String newVersion);

    public abstract void setSignal(Integer signal);

    public abstract void setStrProgress(String strProgress);

    protected ItemMeshScanAllDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivUpgradeFail, AppCompatImageView ivUpgradeSuccess, CircularProgressView ivUpgradeWaiting, RelativeLayout layoutBg, SignalView signalView, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivUpgradeFail = ivUpgradeFail;
        this.ivUpgradeSuccess = ivUpgradeSuccess;
        this.ivUpgradeWaiting = ivUpgradeWaiting;
        this.layoutBg = layoutBg;
        this.signalView = signalView;
        this.tvName = tvName;
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

    public Integer getSignal() {
        return this.mSignal;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public BindingCommand<View> getClicklocation() {
        return this.mClicklocation;
    }

    public static ItemMeshScanAllDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanAllDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMeshScanAllDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_scan_all_device, root, attachToRoot, component);
    }

    public static ItemMeshScanAllDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanAllDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMeshScanAllDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mesh_scan_all_device, null, false, component);
    }

    public static ItemMeshScanAllDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMeshScanAllDeviceBinding bind(View view, Object component) {
        return (ItemMeshScanAllDeviceBinding) bind(component, view, R.layout.item_mesh_scan_all_device);
    }
}