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
import com.ltech.smarthome.model.bean.Room;

/* loaded from: classes3.dex */
public abstract class ItemRoomManageBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Room mItem;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setItem(Room item);

    protected ItemRoomManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
    }

    public Room getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemRoomManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRoomManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemRoomManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_room_manage, root, attachToRoot, component);
    }

    public static ItemRoomManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRoomManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemRoomManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_room_manage, null, false, component);
    }

    public static ItemRoomManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRoomManageBinding bind(View view, Object component) {
        return (ItemRoomManageBinding) bind(component, view, R.layout.item_room_manage);
    }
}