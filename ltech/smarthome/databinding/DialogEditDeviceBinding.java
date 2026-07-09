package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogEditDeviceBinding extends ViewDataBinding {
    public final AppCompatEditText etDeviceName;
    public final AppCompatImageView ivClear;
    public final AppCompatImageView ivLocation;
    public final ConstraintLayout layoutBg;
    public final LinearLayout layoutIcon;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView pickViewFloor;
    public final RecyclerView pickerViewRoom;
    public final RecyclerView rvIcon;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvIcon;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTitle;
    public final View viewDivider;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogEditDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etDeviceName, AppCompatImageView ivClear, AppCompatImageView ivLocation, ConstraintLayout layoutBg, LinearLayout layoutIcon, RecyclerView pickViewFloor, RecyclerView pickerViewRoom, RecyclerView rvIcon, AppCompatTextView tvCancel, AppCompatTextView tvIcon, AppCompatTextView tvLabel, AppCompatTextView tvSave, AppCompatTextView tvTitle, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etDeviceName = etDeviceName;
        this.ivClear = ivClear;
        this.ivLocation = ivLocation;
        this.layoutBg = layoutBg;
        this.layoutIcon = layoutIcon;
        this.pickViewFloor = pickViewFloor;
        this.pickerViewRoom = pickerViewRoom;
        this.rvIcon = rvIcon;
        this.tvCancel = tvCancel;
        this.tvIcon = tvIcon;
        this.tvLabel = tvLabel;
        this.tvSave = tvSave;
        this.tvTitle = tvTitle;
        this.viewDivider = viewDivider;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogEditDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogEditDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_edit_device, root, attachToRoot, component);
    }

    public static DialogEditDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogEditDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_edit_device, null, false, component);
    }

    public static DialogEditDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditDeviceBinding bind(View view, Object component) {
        return (DialogEditDeviceBinding) bind(component, view, R.layout.dialog_edit_device);
    }
}