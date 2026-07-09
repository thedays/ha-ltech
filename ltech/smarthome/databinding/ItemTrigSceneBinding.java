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
public abstract class ItemTrigSceneBinding extends ViewDataBinding {
    public final AppCompatImageView ivBackground;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutBg;
    public final AppCompatTextView tvName;

    protected ItemTrigSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBackground, AppCompatImageView ivSelect, ConstraintLayout layoutBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBackground = ivBackground;
        this.ivSelect = ivSelect;
        this.layoutBg = layoutBg;
        this.tvName = tvName;
    }

    public static ItemTrigSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTrigSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTrigSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_trig_scene, root, attachToRoot, component);
    }

    public static ItemTrigSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTrigSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTrigSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_trig_scene, null, false, component);
    }

    public static ItemTrigSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTrigSceneBinding bind(View view, Object component) {
        return (ItemTrigSceneBinding) bind(component, view, R.layout.item_trig_scene);
    }
}