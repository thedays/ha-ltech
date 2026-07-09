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
public abstract class ItemSceneNewBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView14;
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected Scene mItem;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvType;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setItem(Scene item);

    protected ItemSceneNewBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView14, AppCompatImageView ivEdit, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvName, AppCompatTextView tvType) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView14 = appCompatImageView14;
        this.ivEdit = ivEdit;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
        this.tvType = tvType;
    }

    public Scene getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public static ItemSceneNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSceneNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSceneNewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_scene_new, root, attachToRoot, component);
    }

    public static ItemSceneNewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSceneNewBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSceneNewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_scene_new, null, false, component);
    }

    public static ItemSceneNewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSceneNewBinding bind(View view, Object component) {
        return (ItemSceneNewBinding) bind(component, view, R.layout.item_scene_new);
    }
}