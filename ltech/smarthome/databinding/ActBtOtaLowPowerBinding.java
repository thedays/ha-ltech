package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ActBtOtaLowPowerBinding extends ViewDataBinding {
    public final AppCompatImageView bg;
    public final ImageView ivBg;
    public final TextView tvOk;
    public final TextView tvTitle;

    protected ActBtOtaLowPowerBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView bg, ImageView ivBg, TextView tvOk, TextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg = bg;
        this.ivBg = ivBg;
        this.tvOk = tvOk;
        this.tvTitle = tvTitle;
    }

    public static ActBtOtaLowPowerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaLowPowerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBtOtaLowPowerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_bt_ota_low_power, root, attachToRoot, component);
    }

    public static ActBtOtaLowPowerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaLowPowerBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBtOtaLowPowerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_bt_ota_low_power, null, false, component);
    }

    public static ActBtOtaLowPowerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaLowPowerBinding bind(View view, Object component) {
        return (ActBtOtaLowPowerBinding) bind(component, view, R.layout.act_bt_ota_low_power);
    }
}