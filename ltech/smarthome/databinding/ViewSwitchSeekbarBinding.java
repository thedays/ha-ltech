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
public abstract class ViewSwitchSeekbarBinding extends ViewDataBinding {
    public final SwitchButton sbRgbOn;
    public final AppCompatSeekBar seekbar;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;

    protected ViewSwitchSeekbarBinding(Object _bindingComponent, View _root, int _localFieldCount, SwitchButton sbRgbOn, AppCompatSeekBar seekbar, AppCompatTextView tvTitle, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.sbRgbOn = sbRgbOn;
        this.seekbar = seekbar;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
    }

    public static ViewSwitchSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSwitchSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewSwitchSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_switch_seekbar, root, attachToRoot, component);
    }

    public static ViewSwitchSeekbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSwitchSeekbarBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewSwitchSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_switch_seekbar, null, false, component);
    }

    public static ViewSwitchSeekbarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewSwitchSeekbarBinding bind(View view, Object component) {
        return (ViewSwitchSeekbarBinding) bind(component, view, R.layout.view_switch_seekbar);
    }
}