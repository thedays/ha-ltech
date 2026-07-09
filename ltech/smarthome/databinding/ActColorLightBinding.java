package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.view.ColorPickerPointView;
import com.ltech.smarthome.view.DeliverTouchLayout;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActColorLightBinding extends ViewDataBinding {
    public final ColorPickerPointView ccpv;
    public final ColorPickerPointView ccpv2;
    public final Group groupWy;
    public final AppCompatImageView ivCamera;
    public final AppCompatImageView ivChangeBrt;
    public final AppCompatImageView ivChangePic;
    public final AppCompatImageView ivCt;
    public final AppCompatImageView ivEmptyDevices;
    public final AppCompatImageView ivGradient;
    public final AppCompatImageView ivNormal;
    public final AppCompatImageView ivSort;
    public final AppCompatImageView ivWy;
    public final DeliverTouchLayout layoutCcpv;
    public final DeliverTouchLayout layoutCcpv2;
    public final ConstraintLayout layoutGradient;
    public final ConstraintLayout layoutNormal;
    public final View lineBottom;
    public final View lineBottom2;
    public final View lineTop;
    public final View lineTop2;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActColorLightVM mViewmodel;
    public final RecyclerView rvAction;
    public final RecyclerView rvColor;
    public final RecyclerView rvDevices;
    public final LightBrtBar sbBrt;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvEmptyDevices;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvTip;
    public final View vOpen;
    public final View viewBottom;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActColorLightVM viewmodel);

    protected ActColorLightBinding(Object _bindingComponent, View _root, int _localFieldCount, ColorPickerPointView ccpv, ColorPickerPointView ccpv2, Group groupWy, AppCompatImageView ivCamera, AppCompatImageView ivChangeBrt, AppCompatImageView ivChangePic, AppCompatImageView ivCt, AppCompatImageView ivEmptyDevices, AppCompatImageView ivGradient, AppCompatImageView ivNormal, AppCompatImageView ivSort, AppCompatImageView ivWy, DeliverTouchLayout layoutCcpv, DeliverTouchLayout layoutCcpv2, ConstraintLayout layoutGradient, ConstraintLayout layoutNormal, View lineBottom, View lineBottom2, View lineTop, View lineTop2, RecyclerView rvAction, RecyclerView rvColor, RecyclerView rvDevices, LightBrtBar sbBrt, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvEmptyDevices, AppCompatTextView tvLabel, AppCompatTextView tvTip, View vOpen, View viewBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ccpv = ccpv;
        this.ccpv2 = ccpv2;
        this.groupWy = groupWy;
        this.ivCamera = ivCamera;
        this.ivChangeBrt = ivChangeBrt;
        this.ivChangePic = ivChangePic;
        this.ivCt = ivCt;
        this.ivEmptyDevices = ivEmptyDevices;
        this.ivGradient = ivGradient;
        this.ivNormal = ivNormal;
        this.ivSort = ivSort;
        this.ivWy = ivWy;
        this.layoutCcpv = layoutCcpv;
        this.layoutCcpv2 = layoutCcpv2;
        this.layoutGradient = layoutGradient;
        this.layoutNormal = layoutNormal;
        this.lineBottom = lineBottom;
        this.lineBottom2 = lineBottom2;
        this.lineTop = lineTop;
        this.lineTop2 = lineTop2;
        this.rvAction = rvAction;
        this.rvColor = rvColor;
        this.rvDevices = rvDevices;
        this.sbBrt = sbBrt;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvEmptyDevices = tvEmptyDevices;
        this.tvLabel = tvLabel;
        this.tvTip = tvTip;
        this.vOpen = vOpen;
        this.viewBottom = viewBottom;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActColorLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActColorLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActColorLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActColorLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_color_light, root, attachToRoot, component);
    }

    public static ActColorLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActColorLightBinding inflate(LayoutInflater inflater, Object component) {
        return (ActColorLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_color_light, null, false, component);
    }

    public static ActColorLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActColorLightBinding bind(View view, Object component) {
        return (ActColorLightBinding) bind(component, view, R.layout.act_color_light);
    }
}