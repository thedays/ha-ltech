package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class DialogWyBinding extends ViewDataBinding {
    public final ColorSeekBar csbCtBar;
    public final Group groupWy;
    public final LightBrtBar sbBrt;
    public final SwitchButton sbRgbOn;
    public final SwitchButton sbWyOn;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvCt;
    public final AppCompatTextView tvCtTip;
    public final AppCompatTextView tvRgbOnOff;
    public final AppCompatTextView tvWyOnOff;
    public final View view33;

    protected DialogWyBinding(Object _bindingComponent, View _root, int _localFieldCount, ColorSeekBar csbCtBar, Group groupWy, LightBrtBar sbBrt, SwitchButton sbRgbOn, SwitchButton sbWyOn, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvCt, AppCompatTextView tvCtTip, AppCompatTextView tvRgbOnOff, AppCompatTextView tvWyOnOff, View view33) {
        super(_bindingComponent, _root, _localFieldCount);
        this.csbCtBar = csbCtBar;
        this.groupWy = groupWy;
        this.sbBrt = sbBrt;
        this.sbRgbOn = sbRgbOn;
        this.sbWyOn = sbWyOn;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvCt = tvCt;
        this.tvCtTip = tvCtTip;
        this.tvRgbOnOff = tvRgbOnOff;
        this.tvWyOnOff = tvWyOnOff;
        this.view33 = view33;
    }

    public static DialogWyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogWyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_wy, root, attachToRoot, component);
    }

    public static DialogWyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWyBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogWyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_wy, null, false, component);
    }

    public static DialogWyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogWyBinding bind(View view, Object component) {
        return (DialogWyBinding) bind(component, view, R.layout.dialog_wy);
    }
}