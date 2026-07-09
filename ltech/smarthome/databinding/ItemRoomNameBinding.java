package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ItemRoomNameBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mItem;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setItem(String item);

    protected ItemRoomNameBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvName = tvName;
    }

    public String getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemRoomNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRoomNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemRoomNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_room_name, root, attachToRoot, component);
    }

    public static ItemRoomNameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRoomNameBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemRoomNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_room_name, null, false, component);
    }

    public static ItemRoomNameBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRoomNameBinding bind(View view, Object component) {
        return (ItemRoomNameBinding) bind(component, view, R.layout.item_room_name);
    }
}