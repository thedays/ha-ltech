package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.DeliverTouchLayout;
import com.ltech.smarthome.view.HslColorPickerView;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class FtColorHslBinding extends ViewDataBinding {
    public final HslColorPickerView ccpv;
    public final DeliverTouchLayout layoutCcpv;
    public final StepSetView layoutH;
    public final StepSetView layoutL;
    public final StepSetView layoutS;

    protected FtColorHslBinding(Object _bindingComponent, View _root, int _localFieldCount, HslColorPickerView ccpv, DeliverTouchLayout layoutCcpv, StepSetView layoutH, StepSetView layoutL, StepSetView layoutS) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ccpv = ccpv;
        this.layoutCcpv = layoutCcpv;
        this.layoutH = layoutH;
        this.layoutL = layoutL;
        this.layoutS = layoutS;
    }

    public static FtColorHslBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorHslBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtColorHslBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_hsl, root, attachToRoot, component);
    }

    public static FtColorHslBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorHslBinding inflate(LayoutInflater inflater, Object component) {
        return (FtColorHslBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_hsl, null, false, component);
    }

    public static FtColorHslBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorHslBinding bind(View view, Object component) {
        return (FtColorHslBinding) bind(component, view, R.layout.ft_color_hsl);
    }
}