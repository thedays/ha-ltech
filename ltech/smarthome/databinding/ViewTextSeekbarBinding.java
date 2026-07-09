package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewTextSeekbarBinding extends ViewDataBinding {
    public final AppCompatSeekBar seekbar;
    public final AppCompatTextView tvMax;
    public final AppCompatTextView tvMin;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;

    protected ViewTextSeekbarBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatSeekBar seekbar, AppCompatTextView tvMax, AppCompatTextView tvMin, AppCompatTextView tvTitle, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.seekbar = seekbar;
        this.tvMax = tvMax;
        this.tvMin = tvMin;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
    }

    public static ViewTextSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTextSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewTextSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_text_seekbar, root, attachToRoot, component);
    }

    public static ViewTextSeekbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTextSeekbarBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewTextSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_text_seekbar, null, false, component);
    }

    public static ViewTextSeekbarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewTextSeekbarBinding bind(View view, Object component) {
        return (ViewTextSeekbarBinding) bind(component, view, R.layout.view_text_seekbar);
    }
}