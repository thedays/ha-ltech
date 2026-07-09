package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.view.CircleColorPickerView;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class FtSpiColorBinding extends ViewDataBinding {
    public final CircleColorPickerView ccpv;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActColorLightVM mViewmodel;
    public final RecyclerView rvColor;
    public final LightBrtBar sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final View vOpen;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActColorLightVM viewmodel);

    protected FtSpiColorBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleColorPickerView ccpv, RecyclerView rvColor, LightBrtBar sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, View vOpen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ccpv = ccpv;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.vOpen = vOpen;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActColorLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtSpiColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtSpiColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_spi_color, root, attachToRoot, component);
    }

    public static FtSpiColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiColorBinding inflate(LayoutInflater inflater, Object component) {
        return (FtSpiColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_spi_color, null, false, component);
    }

    public static FtSpiColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtSpiColorBinding bind(View view, Object component) {
        return (FtSpiColorBinding) bind(component, view, R.layout.ft_spi_color);
    }
}