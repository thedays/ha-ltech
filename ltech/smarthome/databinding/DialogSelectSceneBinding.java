package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class DialogSelectSceneBinding extends ViewDataBinding {
    public final AppCompatImageView ivArrow;
    public final RecyclerView rvScene;

    protected DialogSelectSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivArrow, RecyclerView rvScene) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivArrow = ivArrow;
        this.rvScene = rvScene;
    }

    public static DialogSelectSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_scene, root, attachToRoot, component);
    }

    public static DialogSelectSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_scene, null, false, component);
    }

    public static DialogSelectSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectSceneBinding bind(View view, Object component) {
        return (DialogSelectSceneBinding) bind(component, view, R.layout.dialog_select_scene);
    }
}