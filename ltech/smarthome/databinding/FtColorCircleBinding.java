package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.view.ColorPickerPointView;
import com.ltech.smarthome.view.DeliverTouchLayout;
import com.ltech.smarthome.view.TextSeekBarView;

/* loaded from: classes3.dex */
public abstract class FtColorCircleBinding extends ViewDataBinding {
    public final TextSeekBarView brtSeekbar;
    public final ColorPickerPointView ccpv;
    public final ColorPickerPointView ccpv2;
    public final TextSeekBarView ct1Seekbar;
    public final RangeSeekBar ct2Seekbar;
    public final AppCompatImageView ivChangeBrt;
    public final AppCompatImageView ivChangePic;
    public final AppCompatImageView ivCwBrt;
    public final AppCompatImageView ivEmptyDevices;
    public final AppCompatImageView ivGradient;
    public final AppCompatImageView ivNormal;
    public final AppCompatImageView ivRgbBrt;
    public final AppCompatImageView ivSort;
    public final DeliverTouchLayout layoutCcpv;
    public final DeliverTouchLayout layoutCcpv2;
    public final RelativeLayout layoutCt;
    public final ConstraintLayout layoutGradient;
    public final ConstraintLayout layoutNormal;
    public final View line;
    public final View lineBottom;
    public final View lineBottom2;
    public final View lineTop;
    public final View lineTop2;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ActColorLightVM mViewmodel;
    public final RecyclerView rvColor;
    public final RecyclerView rvDevices;
    public final AppCompatTextView tvCt1;
    public final AppCompatTextView tvCt2;
    public final AppCompatTextView tvEmptyDevices;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvValue;
    public final View viewBottom;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setViewmodel(ActColorLightVM viewmodel);

    protected FtColorCircleBinding(Object _bindingComponent, View _root, int _localFieldCount, TextSeekBarView brtSeekbar, ColorPickerPointView ccpv, ColorPickerPointView ccpv2, TextSeekBarView ct1Seekbar, RangeSeekBar ct2Seekbar, AppCompatImageView ivChangeBrt, AppCompatImageView ivChangePic, AppCompatImageView ivCwBrt, AppCompatImageView ivEmptyDevices, AppCompatImageView ivGradient, AppCompatImageView ivNormal, AppCompatImageView ivRgbBrt, AppCompatImageView ivSort, DeliverTouchLayout layoutCcpv, DeliverTouchLayout layoutCcpv2, RelativeLayout layoutCt, ConstraintLayout layoutGradient, ConstraintLayout layoutNormal, View line, View lineBottom, View lineBottom2, View lineTop, View lineTop2, RecyclerView rvColor, RecyclerView rvDevices, AppCompatTextView tvCt1, AppCompatTextView tvCt2, AppCompatTextView tvEmptyDevices, AppCompatTextView tvLabel, AppCompatTextView tvTip, AppCompatTextView tvValue, View viewBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.brtSeekbar = brtSeekbar;
        this.ccpv = ccpv;
        this.ccpv2 = ccpv2;
        this.ct1Seekbar = ct1Seekbar;
        this.ct2Seekbar = ct2Seekbar;
        this.ivChangeBrt = ivChangeBrt;
        this.ivChangePic = ivChangePic;
        this.ivCwBrt = ivCwBrt;
        this.ivEmptyDevices = ivEmptyDevices;
        this.ivGradient = ivGradient;
        this.ivNormal = ivNormal;
        this.ivRgbBrt = ivRgbBrt;
        this.ivSort = ivSort;
        this.layoutCcpv = layoutCcpv;
        this.layoutCcpv2 = layoutCcpv2;
        this.layoutCt = layoutCt;
        this.layoutGradient = layoutGradient;
        this.layoutNormal = layoutNormal;
        this.line = line;
        this.lineBottom = lineBottom;
        this.lineBottom2 = lineBottom2;
        this.lineTop = lineTop;
        this.lineTop2 = lineTop2;
        this.rvColor = rvColor;
        this.rvDevices = rvDevices;
        this.tvCt1 = tvCt1;
        this.tvCt2 = tvCt2;
        this.tvEmptyDevices = tvEmptyDevices;
        this.tvLabel = tvLabel;
        this.tvTip = tvTip;
        this.tvValue = tvValue;
        this.viewBottom = viewBottom;
    }

    public ActColorLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtColorCircleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorCircleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtColorCircleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_circle, root, attachToRoot, component);
    }

    public static FtColorCircleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorCircleBinding inflate(LayoutInflater inflater, Object component) {
        return (FtColorCircleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_circle, null, false, component);
    }

    public static FtColorCircleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorCircleBinding bind(View view, Object component) {
        return (FtColorCircleBinding) bind(component, view, R.layout.ft_color_circle);
    }
}