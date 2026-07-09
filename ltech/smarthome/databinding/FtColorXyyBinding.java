package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.XYCoordinateView;

/* loaded from: classes3.dex */
public abstract class FtColorXyyBinding extends ViewDataBinding {
    public final StepSetView layoutX;
    public final StepSetView layoutY;
    public final XYCoordinateView xyCoordinateView;

    protected FtColorXyyBinding(Object _bindingComponent, View _root, int _localFieldCount, StepSetView layoutX, StepSetView layoutY, XYCoordinateView xyCoordinateView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.xyCoordinateView = xyCoordinateView;
    }

    public static FtColorXyyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorXyyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtColorXyyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_xyy, root, attachToRoot, component);
    }

    public static FtColorXyyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorXyyBinding inflate(LayoutInflater inflater, Object component) {
        return (FtColorXyyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_xyy, null, false, component);
    }

    public static FtColorXyyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorXyyBinding bind(View view, Object component) {
        return (FtColorXyyBinding) bind(component, view, R.layout.ft_color_xyy);
    }
}