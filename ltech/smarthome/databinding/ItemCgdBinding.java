package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Scene;

/* loaded from: classes3.dex */
public abstract class ItemCgdBinding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Scene mItem;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvOffline;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setItem(Scene item);

    protected ItemCgdBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, AppCompatImageView ivIcon, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvAdd, AppCompatTextView tvName, AppCompatTextView tvOffline) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvAdd = tvAdd;
        this.tvName = tvName;
        this.tvOffline = tvOffline;
    }

    public Scene getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemCgdBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCgdBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCgdBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cgd, root, attachToRoot, component);
    }

    public static ItemCgdBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCgdBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCgdBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cgd, null, false, component);
    }

    public static ItemCgdBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCgdBinding bind(View view, Object component) {
        return (ItemCgdBinding) bind(component, view, R.layout.item_cgd);
    }
}