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
public abstract class ViewDaliTextSeekbarBinding extends ViewDataBinding {
    public final AppCompatImageView ivPlus;
    public final AppCompatImageView ivReduce;
    public final AppCompatSeekBar seekbar;
    public final AppCompatTextView tvMax;
    public final AppCompatTextView tvMin;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;

    protected ViewDaliTextSeekbarBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPlus, AppCompatImageView ivReduce, AppCompatSeekBar seekbar, AppCompatTextView tvMax, AppCompatTextView tvMin, AppCompatTextView tvTitle, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPlus = ivPlus;
        this.ivReduce = ivReduce;
        this.seekbar = seekbar;
        this.tvMax = tvMax;
        this.tvMin = tvMin;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
    }

    public static ViewDaliTextSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliTextSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewDaliTextSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_dali_text_seekbar, root, attachToRoot, component);
    }

    public static ViewDaliTextSeekbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliTextSeekbarBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewDaliTextSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_dali_text_seekbar, null, false, component);
    }

    public static ViewDaliTextSeekbarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliTextSeekbarBinding bind(View view, Object component) {
        return (ViewDaliTextSeekbarBinding) bind(component, view, R.layout.view_dali_text_seekbar);
    }
}