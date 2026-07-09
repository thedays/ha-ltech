package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.CircleColorPickerView;
import com.ltech.smarthome.view.DeliverTouchLayout;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class FtRgbLightBinding extends ViewDataBinding {
    public final CircleColorPickerView ccpv;
    public final AppCompatImageView ivCamera;
    public final AppCompatImageView ivCt;
    public final DeliverTouchLayout layoutCcpv;
    public final ConstraintLayout layoutRoot;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvColor;
    public final LightBrtBar sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtRgbLightBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleColorPickerView ccpv, AppCompatImageView ivCamera, AppCompatImageView ivCt, DeliverTouchLayout layoutCcpv, ConstraintLayout layoutRoot, RecyclerView rvColor, LightBrtBar sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ccpv = ccpv;
        this.ivCamera = ivCamera;
        this.ivCt = ivCt;
        this.layoutCcpv = layoutCcpv;
        this.layoutRoot = layoutRoot;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtRgbLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRgbLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtRgbLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_rgb_light, root, attachToRoot, component);
    }

    public static FtRgbLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRgbLightBinding inflate(LayoutInflater inflater, Object component) {
        return (FtRgbLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_rgb_light, null, false, component);
    }

    public static FtRgbLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtRgbLightBinding bind(View view, Object component) {
        return (FtRgbLightBinding) bind(component, view, R.layout.ft_rgb_light);
    }
}