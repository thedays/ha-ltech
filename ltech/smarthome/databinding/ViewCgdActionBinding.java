package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ViewCgdActionBinding extends ViewDataBinding {
    public final CircleImageView civColor;
    public final ColorSeekBar colorSeekbar;
    public final Group groupColor;
    public final Group groupCt;
    public final LightBrtBar sbBrt;
    public final RangeSeekBar sbCt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvCtPercent;

    protected ViewCgdActionBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView civColor, ColorSeekBar colorSeekbar, Group groupColor, Group groupCt, LightBrtBar sbBrt, RangeSeekBar sbCt, AppCompatTextView tvBrt, AppCompatTextView tvCtPercent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.civColor = civColor;
        this.colorSeekbar = colorSeekbar;
        this.groupColor = groupColor;
        this.groupCt = groupCt;
        this.sbBrt = sbBrt;
        this.sbCt = sbCt;
        this.tvBrt = tvBrt;
        this.tvCtPercent = tvCtPercent;
    }

    public static ViewCgdActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewCgdActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_action, root, attachToRoot, component);
    }

    public static ViewCgdActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewCgdActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_cgd_action, null, false, component);
    }

    public static ViewCgdActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewCgdActionBinding bind(View view, Object component) {
        return (ViewCgdActionBinding) bind(component, view, R.layout.view_cgd_action);
    }
}