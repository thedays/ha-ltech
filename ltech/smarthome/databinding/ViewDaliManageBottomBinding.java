package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ViewDaliManageBottomBinding extends ViewDataBinding {
    public final LinearLayout btnChangeRoom;
    public final LinearLayout btnFindDevice;
    public final LinearLayout btnModifyParam;
    public final AppCompatImageView ivChangeRoom;
    public final AppCompatImageView ivModifyParam;
    public final ConstraintLayout layoutDeviceManage;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvChangeRoom;
    public final AppCompatTextView tvStartLocate;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ViewDaliManageBottomBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout btnChangeRoom, LinearLayout btnFindDevice, LinearLayout btnModifyParam, AppCompatImageView ivChangeRoom, AppCompatImageView ivModifyParam, ConstraintLayout layoutDeviceManage, AppCompatTextView tvChangeRoom, AppCompatTextView tvStartLocate) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnChangeRoom = btnChangeRoom;
        this.btnFindDevice = btnFindDevice;
        this.btnModifyParam = btnModifyParam;
        this.ivChangeRoom = ivChangeRoom;
        this.ivModifyParam = ivModifyParam;
        this.layoutDeviceManage = layoutDeviceManage;
        this.tvChangeRoom = tvChangeRoom;
        this.tvStartLocate = tvStartLocate;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ViewDaliManageBottomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliManageBottomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewDaliManageBottomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_dali_manage_bottom, root, attachToRoot, component);
    }

    public static ViewDaliManageBottomBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliManageBottomBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewDaliManageBottomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_dali_manage_bottom, null, false, component);
    }

    public static ViewDaliManageBottomBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliManageBottomBinding bind(View view, Object component) {
        return (ViewDaliManageBottomBinding) bind(component, view, R.layout.view_dali_manage_bottom);
    }
}