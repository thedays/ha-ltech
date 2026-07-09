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
import com.ltech.smarthome.model.bean.Scene;

/* loaded from: classes3.dex */
public abstract class ItemBindEurSceneBinding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivIcon;
    public final LinearLayoutCompat layoutItem;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected Scene mItem;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvPosition;
    public final AppCompatTextView tvState;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setItem(Scene item);

    protected ItemBindEurSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, AppCompatImageView ivIcon, LinearLayoutCompat layoutItem, ConstraintLayout layoutItemBg, AppCompatTextView tvName, AppCompatTextView tvPosition, AppCompatTextView tvState) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.ivIcon = ivIcon;
        this.layoutItem = layoutItem;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
        this.tvPosition = tvPosition;
        this.tvState = tvState;
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

    public static ItemBindEurSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBindEurSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBindEurSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bind_eur_scene, root, attachToRoot, component);
    }

    public static ItemBindEurSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBindEurSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemBindEurSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_bind_eur_scene, null, false, component);
    }

    public static ItemBindEurSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBindEurSceneBinding bind(View view, Object component) {
        return (ItemBindEurSceneBinding) bind(component, view, R.layout.item_bind_eur_scene);
    }
}