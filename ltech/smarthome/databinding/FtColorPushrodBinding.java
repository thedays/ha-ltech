package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.TextSeekBarView;

/* loaded from: classes3.dex */
public abstract class FtColorPushrodBinding extends ViewDataBinding {
    public final TextSeekBarView seekbarB;
    public final TextSeekBarView seekbarC;
    public final TextSeekBarView seekbarG;
    public final TextSeekBarView seekbarR;
    public final TextSeekBarView seekbarTotalBrt;
    public final TextSeekBarView seekbarW;

    protected FtColorPushrodBinding(Object _bindingComponent, View _root, int _localFieldCount, TextSeekBarView seekbarB, TextSeekBarView seekbarC, TextSeekBarView seekbarG, TextSeekBarView seekbarR, TextSeekBarView seekbarTotalBrt, TextSeekBarView seekbarW) {
        super(_bindingComponent, _root, _localFieldCount);
        this.seekbarB = seekbarB;
        this.seekbarC = seekbarC;
        this.seekbarG = seekbarG;
        this.seekbarR = seekbarR;
        this.seekbarTotalBrt = seekbarTotalBrt;
        this.seekbarW = seekbarW;
    }

    public static FtColorPushrodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorPushrodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtColorPushrodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_pushrod, root, attachToRoot, component);
    }

    public static FtColorPushrodBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorPushrodBinding inflate(LayoutInflater inflater, Object component) {
        return (FtColorPushrodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_pushrod, null, false, component);
    }

    public static FtColorPushrodBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorPushrodBinding bind(View view, Object component) {
        return (FtColorPushrodBinding) bind(component, view, R.layout.ft_color_pushrod);
    }
}