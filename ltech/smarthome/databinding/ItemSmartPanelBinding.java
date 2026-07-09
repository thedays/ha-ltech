package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.view.SmartSwitch;

/* loaded from: classes3.dex */
public abstract class ItemSmartPanelBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView9;
    public final AppCompatTextView appCompatTextView15;
    public final AppCompatTextView appCompatTextView16;
    public final AppCompatTextView appCompatTextView17;
    public final LinearLayoutCompat bg;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivFavorite;
    public final SmartSwitch ivSwitch;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected SmartSwitch.OnUserCheckedChangeListener mCheckedChangeListener;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Device mDevice;

    @Bindable
    protected Boolean mHideMore;

    @Bindable
    protected Boolean mHideSwitch;

    @Bindable
    protected Integer mIconRes;
    public final AppCompatTextView tvVirtual;
    public final AppCompatTextView tvWrite;
    public final View vFavorite;

    public abstract void setCheckedChangeListener(SmartSwitch.OnUserCheckedChangeListener checkedChangeListener);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDevice(Device device);

    public abstract void setHideMore(Boolean hideMore);

    public abstract void setHideSwitch(Boolean hideSwitch);

    public abstract void setIconRes(Integer iconRes);

    protected ItemSmartPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView9, AppCompatTextView appCompatTextView15, AppCompatTextView appCompatTextView16, AppCompatTextView appCompatTextView17, LinearLayoutCompat bg, AppCompatImageView ivDeviceMore, AppCompatImageView ivFavorite, SmartSwitch ivSwitch, ConstraintLayout layoutItemBg, AppCompatTextView tvVirtual, AppCompatTextView tvWrite, View vFavorite) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView9 = appCompatImageView9;
        this.appCompatTextView15 = appCompatTextView15;
        this.appCompatTextView16 = appCompatTextView16;
        this.appCompatTextView17 = appCompatTextView17;
        this.bg = bg;
        this.ivDeviceMore = ivDeviceMore;
        this.ivFavorite = ivFavorite;
        this.ivSwitch = ivSwitch;
        this.layoutItemBg = layoutItemBg;
        this.tvVirtual = tvVirtual;
        this.tvWrite = tvWrite;
        this.vFavorite = vFavorite;
    }

    public Device getDevice() {
        return this.mDevice;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public SmartSwitch.OnUserCheckedChangeListener getCheckedChangeListener() {
        return this.mCheckedChangeListener;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public Boolean getHideMore() {
        return this.mHideMore;
    }

    public Boolean getHideSwitch() {
        return this.mHideSwitch;
    }

    public static ItemSmartPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSmartPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_panel, root, attachToRoot, component);
    }

    public static ItemSmartPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSmartPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_panel, null, false, component);
    }

    public static ItemSmartPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartPanelBinding bind(View view, Object component) {
        return (ItemSmartPanelBinding) bind(component, view, R.layout.item_smart_panel);
    }
}