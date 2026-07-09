package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Floor;

/* loaded from: classes3.dex */
public abstract class ItemFloorManageBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Floor mItem;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setItem(Floor item);

    protected ItemFloorManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
    }

    public Floor getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemFloorManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFloorManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemFloorManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_floor_manage, root, attachToRoot, component);
    }

    public static ItemFloorManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFloorManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemFloorManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_floor_manage, null, false, component);
    }

    public static ItemFloorManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFloorManageBinding bind(View view, Object component) {
        return (ItemFloorManageBinding) bind(component, view, R.layout.item_floor_manage);
    }
}