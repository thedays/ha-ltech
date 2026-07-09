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
public abstract class ItemScreenInfoInputBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvName;

    protected ItemScreenInfoInputBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
    }

    public static ItemScreenInfoInputBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenInfoInputBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemScreenInfoInputBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_info_input, root, attachToRoot, component);
    }

    public static ItemScreenInfoInputBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenInfoInputBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemScreenInfoInputBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_info_input, null, false, component);
    }

    public static ItemScreenInfoInputBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenInfoInputBinding bind(View view, Object component) {
        return (ItemScreenInfoInputBinding) bind(component, view, R.layout.item_screen_info_input);
    }
}