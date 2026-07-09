package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.VerticalSeekBar;

/* loaded from: classes3.dex */
public abstract class DialogColorBrtControlBinding extends ViewDataBinding {
    public final VerticalSeekBar barBrt1;
    public final VerticalSeekBar barBrt2;
    public final VerticalSeekBar barCt;
    public final ConstraintLayout bg;
    public final AppCompatTextView tvBrt1;
    public final AppCompatTextView tvBrt2;
    public final AppCompatTextView tvCt;
    public final AppCompatTextView tvLabel1;
    public final AppCompatTextView tvLabel2;
    public final AppCompatTextView tvLabel3;

    protected DialogColorBrtControlBinding(Object _bindingComponent, View _root, int _localFieldCount, VerticalSeekBar barBrt1, VerticalSeekBar barBrt2, VerticalSeekBar barCt, ConstraintLayout bg, AppCompatTextView tvBrt1, AppCompatTextView tvBrt2, AppCompatTextView tvCt, AppCompatTextView tvLabel1, AppCompatTextView tvLabel2, AppCompatTextView tvLabel3) {
        super(_bindingComponent, _root, _localFieldCount);
        this.barBrt1 = barBrt1;
        this.barBrt2 = barBrt2;
        this.barCt = barCt;
        this.bg = bg;
        this.tvBrt1 = tvBrt1;
        this.tvBrt2 = tvBrt2;
        this.tvCt = tvCt;
        this.tvLabel1 = tvLabel1;
        this.tvLabel2 = tvLabel2;
        this.tvLabel3 = tvLabel3;
    }

    public static DialogColorBrtControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogColorBrtControlBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogColorBrtControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_color_brt_control, root, attachToRoot, component);
    }

    public static DialogColorBrtControlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogColorBrtControlBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogColorBrtControlBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_color_brt_control, null, false, component);
    }

    public static DialogColorBrtControlBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogColorBrtControlBinding bind(View view, Object component) {
        return (DialogColorBrtControlBinding) bind(component, view, R.layout.dialog_color_brt_control);
    }
}