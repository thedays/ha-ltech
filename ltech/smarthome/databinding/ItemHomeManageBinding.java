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
import com.ltech.smarthome.model.bean.Place;

/* loaded from: classes3.dex */
public abstract class ItemHomeManageBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Place mItem;

    @Bindable
    protected String mSub;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setItem(Place item);

    public abstract void setSub(String sub);

    protected ItemHomeManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
    }

    public Place getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public String getSub() {
        return this.mSub;
    }

    public static ItemHomeManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemHomeManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemHomeManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_home_manage, root, attachToRoot, component);
    }

    public static ItemHomeManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemHomeManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemHomeManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_home_manage, null, false, component);
    }

    public static ItemHomeManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemHomeManageBinding bind(View view, Object component) {
        return (ItemHomeManageBinding) bind(component, view, R.layout.item_home_manage);
    }
}