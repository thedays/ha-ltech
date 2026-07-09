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
public abstract class ItemScreenSpBtnKeyBinding extends ViewDataBinding {
    public final AppCompatImageView ivSwitch;
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvDeviceName;

    protected ItemScreenSpBtnKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSwitch, LinearLayout layoutBg, AppCompatTextView tvDeviceName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSwitch = ivSwitch;
        this.layoutBg = layoutBg;
        this.tvDeviceName = tvDeviceName;
    }

    public static ItemScreenSpBtnKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenSpBtnKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemScreenSpBtnKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_sp_btn_key, root, attachToRoot, component);
    }

    public static ItemScreenSpBtnKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenSpBtnKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemScreenSpBtnKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_sp_btn_key, null, false, component);
    }

    public static ItemScreenSpBtnKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenSpBtnKeyBinding bind(View view, Object component) {
        return (ItemScreenSpBtnKeyBinding) bind(component, view, R.layout.item_screen_sp_btn_key);
    }
}