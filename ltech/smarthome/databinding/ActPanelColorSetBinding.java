package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM;

/* loaded from: classes3.dex */
public abstract class ActPanelColorSetBinding extends ViewDataBinding {
    public final AppCompatImageView ivS4;
    public final AppCompatImageView ivScreen;
    public final AppCompatImageView ivShadowLeft;
    public final AppCompatImageView ivShadowRight;
    public final AppCompatImageView ivSwitch5;
    public final ConstraintLayout layoutContent;
    public final ConstraintLayout layoutIv;
    public final RelativeLayout layoutS4;
    public final LinearLayout llBottom;

    @Bindable
    protected ProductId mProductId;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartPanelSettingVM mViewmodel;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvColorName;

    public abstract void setProductId(ProductId productId);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartPanelSettingVM viewmodel);

    protected ActPanelColorSetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivS4, AppCompatImageView ivScreen, AppCompatImageView ivShadowLeft, AppCompatImageView ivShadowRight, AppCompatImageView ivSwitch5, ConstraintLayout layoutContent, ConstraintLayout layoutIv, RelativeLayout layoutS4, LinearLayout llBottom, RecyclerView rv, LayoutTitleDefaultBinding title, TextView tvColorName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivS4 = ivS4;
        this.ivScreen = ivScreen;
        this.ivShadowLeft = ivShadowLeft;
        this.ivShadowRight = ivShadowRight;
        this.ivSwitch5 = ivSwitch5;
        this.layoutContent = layoutContent;
        this.layoutIv = layoutIv;
        this.layoutS4 = layoutS4;
        this.llBottom = llBottom;
        this.rv = rv;
        this.title = title;
        this.tvColorName = tvColorName;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartPanelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public ProductId getProductId() {
        return this.mProductId;
    }

    public static ActPanelColorSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelColorSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPanelColorSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_color_set, root, attachToRoot, component);
    }

    public static ActPanelColorSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelColorSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPanelColorSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_color_set, null, false, component);
    }

    public static ActPanelColorSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelColorSetBinding bind(View view, Object component) {
        return (ActPanelColorSetBinding) bind(component, view, R.layout.act_panel_color_set);
    }
}