package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewBrtSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivMinus;
    public final AppCompatImageView ivPlus;
    public final AppCompatSeekBar seekbar;
    public final View topLine;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;

    protected ViewBrtSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMinus, AppCompatImageView ivPlus, AppCompatSeekBar seekbar, View topLine, AppCompatTextView tvTitle, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMinus = ivMinus;
        this.ivPlus = ivPlus;
        this.seekbar = seekbar;
        this.topLine = topLine;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
    }

    public static ViewBrtSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewBrtSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewBrtSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_brt_setting, root, attachToRoot, component);
    }

    public static ViewBrtSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewBrtSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewBrtSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_brt_setting, null, false, component);
    }

    public static ViewBrtSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewBrtSettingBinding bind(View view, Object component) {
        return (ViewBrtSettingBinding) bind(component, view, R.layout.view_brt_setting);
    }
}