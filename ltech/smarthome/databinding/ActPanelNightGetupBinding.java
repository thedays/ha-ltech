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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.setting.ActPanelNightGetUpVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActPanelNightGetupBinding extends ViewDataBinding {
    public final AppCompatImageView ivEndTimeGo;
    public final AppCompatImageView ivModeSceneGo;
    public final AppCompatImageView ivResetSceneGo;
    public final AppCompatImageView ivStartTimeGo;
    public final ConstraintLayout layoutEndTime;
    public final RelativeLayout layoutGetUpNightMode;
    public final RelativeLayout layoutGetUpNightModeScene;
    public final RelativeLayout layoutResetScene;
    public final ConstraintLayout layoutStartTime;

    @Bindable
    protected ProductId mProductId;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActPanelNightGetUpVM mViewmodel;
    public final SwitchButton sbGetUpNightMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEndTime;
    public final AppCompatTextView tvEndTimeTip;
    public final AppCompatTextView tvModeSceneTip;
    public final AppCompatTextView tvResetSceneTip;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvStartTimeTip;

    public abstract void setProductId(ProductId productId);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActPanelNightGetUpVM viewmodel);

    protected ActPanelNightGetupBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEndTimeGo, AppCompatImageView ivModeSceneGo, AppCompatImageView ivResetSceneGo, AppCompatImageView ivStartTimeGo, ConstraintLayout layoutEndTime, RelativeLayout layoutGetUpNightMode, RelativeLayout layoutGetUpNightModeScene, RelativeLayout layoutResetScene, ConstraintLayout layoutStartTime, SwitchButton sbGetUpNightMode, LayoutTitleDefaultBinding title, AppCompatTextView tvEndTime, AppCompatTextView tvEndTimeTip, AppCompatTextView tvModeSceneTip, AppCompatTextView tvResetSceneTip, AppCompatTextView tvStartTime, AppCompatTextView tvStartTimeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEndTimeGo = ivEndTimeGo;
        this.ivModeSceneGo = ivModeSceneGo;
        this.ivResetSceneGo = ivResetSceneGo;
        this.ivStartTimeGo = ivStartTimeGo;
        this.layoutEndTime = layoutEndTime;
        this.layoutGetUpNightMode = layoutGetUpNightMode;
        this.layoutGetUpNightModeScene = layoutGetUpNightModeScene;
        this.layoutResetScene = layoutResetScene;
        this.layoutStartTime = layoutStartTime;
        this.sbGetUpNightMode = sbGetUpNightMode;
        this.title = title;
        this.tvEndTime = tvEndTime;
        this.tvEndTimeTip = tvEndTimeTip;
        this.tvModeSceneTip = tvModeSceneTip;
        this.tvResetSceneTip = tvResetSceneTip;
        this.tvStartTime = tvStartTime;
        this.tvStartTimeTip = tvStartTimeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActPanelNightGetUpVM getViewmodel() {
        return this.mViewmodel;
    }

    public ProductId getProductId() {
        return this.mProductId;
    }

    public static ActPanelNightGetupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelNightGetupBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPanelNightGetupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_night_getup, root, attachToRoot, component);
    }

    public static ActPanelNightGetupBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelNightGetupBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPanelNightGetupBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_night_getup, null, false, component);
    }

    public static ActPanelNightGetupBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelNightGetupBinding bind(View view, Object component) {
        return (ActPanelNightGetupBinding) bind(component, view, R.layout.act_panel_night_getup);
    }
}