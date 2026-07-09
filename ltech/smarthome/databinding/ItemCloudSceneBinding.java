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
public abstract class ItemCloudSceneBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView14;
    public final AppCompatImageView ivEdit;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected Scene mItem;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setItem(Scene item);

    protected ItemCloudSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView14, AppCompatImageView ivEdit, ConstraintLayout layoutItemBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView14 = appCompatImageView14;
        this.ivEdit = ivEdit;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
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

    public static ItemCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cloud_scene, root, attachToRoot, component);
    }

    public static ItemCloudSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCloudSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cloud_scene, null, false, component);
    }

    public static ItemCloudSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCloudSceneBinding bind(View view, Object component) {
        return (ItemCloudSceneBinding) bind(component, view, R.layout.item_cloud_scene);
    }
}