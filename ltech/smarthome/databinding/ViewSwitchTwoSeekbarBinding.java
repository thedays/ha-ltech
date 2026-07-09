package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ViewSwitchTwoSeekbarBinding extends ViewDataBinding {
    public final View line;
    public final SwitchButton sbRgbOn;
    public final AppCompatSeekBar seekbar;
    public final AppCompatSeekBar seekbarSecond;
    public final AppCompatTextView tvFirst;
    public final AppCompatTextView tvSecond;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;
    public final AppCompatTextView tvValueSecond;

    protected ViewSwitchTwoSeekbarBinding(Object _bindingComponent, View _root, int _localFieldCount, View line, SwitchButton sbRgbOn, AppCompatSeekBar seekbar, AppCompatSeekBar seekbarSecond, AppCompatTextView tvFirst, AppCompatTextView tvSecond, AppCompatTextView tvTitle, AppCompatTextView tvValue, AppCompatTextView tvValueSecond) {
        super(_bindingComponent, _root, _localFieldCount);
        this.line = line;
        this.sbRgbOn = sbRgbOn;
        this.seekbar = seekbar;
        this.seekbarSecond = seekbarSecond;
        this.tvFirst = tvFirst;
        this.tvSecond = tvSecond;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
        this.tvValueSecond = tvValueSecond;
    }

    public static ViewSwitchTwoSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSwitchTwoSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewSwitchTwoSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_switch_two_seekbar, root, attachToRoot, component);
    }

    public static ViewSwitchTwoSeekbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSwitchTwoSeekbarBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewSwitchTwoSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_switch_two_seekbar, null, false, component);
    }

    public static ViewSwitchTwoSeekbarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSwitchTwoSeekbarBinding bind(View view, Object component) {
        return (ViewSwitchTwoSeekbarBinding) bind(component, view, R.layout.view_switch_two_seekbar);
    }
}