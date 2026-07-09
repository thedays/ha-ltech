package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSelectCloudSceneBinding extends ViewDataBinding {
    public final AppCompatImageView ivSceneIcon;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvSceneName;

    protected ItemSelectCloudSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSceneIcon, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvSceneName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSceneIcon = ivSceneIcon;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvSceneName = tvSceneName;
    }

    public static ItemSelectCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_cloud_scene, root, attachToRoot, component);
    }

    public static ItemSelectCloudSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectCloudSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_cloud_scene, null, false, component);
    }

    public static ItemSelectCloudSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectCloudSceneBinding bind(View view, Object component) {
        return (ItemSelectCloudSceneBinding) bind(component, view, R.layout.item_select_cloud_scene);
    }
}