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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class DialogFloorHeatQuickBinding extends ViewDataBinding {
    public final View dividerLine;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivSendTip;
    public final AppCompatImageView ivTempLeft;
    public final AppCompatImageView ivTempRight;
    public final LinearLayout layoutTempLeft;
    public final LinearLayout layoutTempRight;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvTemp;
    public final SwitchButton sb;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogFloorHeatQuickBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, AppCompatImageView ivDeviceMore, AppCompatImageView ivSendTip, AppCompatImageView ivTempLeft, AppCompatImageView ivTempRight, LinearLayout layoutTempLeft, LinearLayout layoutTempRight, RecyclerView rvTemp, SwitchButton sb, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.ivDeviceMore = ivDeviceMore;
        this.ivSendTip = ivSendTip;
        this.ivTempLeft = ivTempLeft;
        this.ivTempRight = ivTempRight;
        this.layoutTempLeft = layoutTempLeft;
        this.layoutTempRight = layoutTempRight;
        this.rvTemp = rvTemp;
        this.sb = sb;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogFloorHeatQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFloorHeatQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogFloorHeatQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_floor_heat_quick, root, attachToRoot, component);
    }

    public static DialogFloorHeatQuickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFloorHeatQuickBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogFloorHeatQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_floor_heat_quick, null, false, component);
    }

    public static DialogFloorHeatQuickBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFloorHeatQuickBinding bind(View view, Object component) {
        return (DialogFloorHeatQuickBinding) bind(component, view, R.layout.dialog_floor_heat_quick);
    }
}