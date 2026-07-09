package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSpBtnKeyBinding extends ViewDataBinding {
    public final AppCompatImageView ivSwitch;
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvDeviceName;

    protected ItemSpBtnKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSwitch, LinearLayout layoutBg, AppCompatTextView tvDeviceName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSwitch = ivSwitch;
        this.layoutBg = layoutBg;
        this.tvDeviceName = tvDeviceName;
    }

    public static ItemSpBtnKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpBtnKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSpBtnKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sp_btn_key, root, attachToRoot, component);
    }

    public static ItemSpBtnKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpBtnKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSpBtnKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_sp_btn_key, null, false, component);
    }

    public static ItemSpBtnKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSpBtnKeyBinding bind(View view, Object component) {
        return (ItemSpBtnKeyBinding) bind(component, view, R.layout.item_sp_btn_key);
    }
}