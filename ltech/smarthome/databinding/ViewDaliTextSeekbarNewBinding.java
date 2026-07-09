package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ViewDaliTextSeekbarNewBinding extends ViewDataBinding {
    public final RangeSeekBar seekbar;
    public final AppCompatTextView tvMax;
    public final AppCompatTextView tvMin;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;

    protected ViewDaliTextSeekbarNewBinding(Object _bindingComponent, View _root, int _localFieldCount, RangeSeekBar seekbar, AppCompatTextView tvMax, AppCompatTextView tvMin, AppCompatTextView tvTitle, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.seekbar = seekbar;
        this.tvMax = tvMax;
        this.tvMin = tvMin;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
    }

    public static ViewDaliTextSeekbarNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliTextSeekbarNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewDaliTextSeekbarNewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_dali_text_seekbar_new, root, attachToRoot, component);
    }

    public static ViewDaliTextSeekbarNewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliTextSeekbarNewBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewDaliTextSeekbarNewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_dali_text_seekbar_new, null, false, component);
    }

    public static ViewDaliTextSeekbarNewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewDaliTextSeekbarNewBinding bind(View view, Object component) {
        return (ViewDaliTextSeekbarNewBinding) bind(component, view, R.layout.view_dali_text_seekbar_new);
    }
}