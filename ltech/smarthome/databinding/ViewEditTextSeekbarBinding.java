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
import com.ltech.smarthome.view.ColorEditText;

/* loaded from: classes3.dex */
public abstract class ViewEditTextSeekbarBinding extends ViewDataBinding {
    public final ColorEditText etValue;
    public final AppCompatImageView ivEdit;
    public final AppCompatSeekBar seekbar;
    public final AppCompatTextView tvMax;
    public final AppCompatTextView tvMin;
    public final AppCompatTextView tvTitle;

    protected ViewEditTextSeekbarBinding(Object _bindingComponent, View _root, int _localFieldCount, ColorEditText etValue, AppCompatImageView ivEdit, AppCompatSeekBar seekbar, AppCompatTextView tvMax, AppCompatTextView tvMin, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etValue = etValue;
        this.ivEdit = ivEdit;
        this.seekbar = seekbar;
        this.tvMax = tvMax;
        this.tvMin = tvMin;
        this.tvTitle = tvTitle;
    }

    public static ViewEditTextSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEditTextSeekbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewEditTextSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_edit_text_seekbar, root, attachToRoot, component);
    }

    public static ViewEditTextSeekbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEditTextSeekbarBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewEditTextSeekbarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_edit_text_seekbar, null, false, component);
    }

    public static ViewEditTextSeekbarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewEditTextSeekbarBinding bind(View view, Object component) {
        return (ViewEditTextSeekbarBinding) bind(component, view, R.layout.view_edit_text_seekbar);
    }
}