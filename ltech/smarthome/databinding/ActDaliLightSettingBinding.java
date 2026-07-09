package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ActDaliLightSettingBinding extends ViewDataBinding {
    public final NestedScrollView actAddDeviceScroll;
    public final CircleImageView civColorFail;
    public final CircleImageView civColorLightOn;
    public final AppCompatImageView ivCtRangeGo;
    public final AppCompatImageView ivDimmingCurveGo;
    public final AppCompatImageView ivDimmingFadeTimeGo;
    public final AppCompatImageView ivDimmingRangeGo;
    public final AppCompatImageView ivFailureStateGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivLightOnStateGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneNameGo;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutCtRange;
    public final ConstraintLayout layoutDimmingCurve;
    public final ConstraintLayout layoutDimmingFadeTime;
    public final ConstraintLayout layoutDimmingRange;
    public final ConstraintLayout layoutFailureState;
    public final ConstraintLayout layoutLightOnState;
    public final ConstraintLayout layoutSceneName;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDaliLightSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final SwitchButton sbAddToSmart;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCtRange;
    public final AppCompatTextView tvCtRangeTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDeviceNumber;
    public final AppCompatTextView tvDimmingCurve;
    public final AppCompatTextView tvDimmingCurveTip;
    public final AppCompatTextView tvDimmingFadeTime;
    public final AppCompatTextView tvDimmingFadeTimeTip;
    public final AppCompatTextView tvDimmingRange;
    public final AppCompatTextView tvDimmingRangeTip;
    public final AppCompatTextView tvFailureState;
    public final AppCompatTextView tvFailureStateTip;
    public final AppCompatTextView tvGatewayName;
    public final AppCompatTextView tvGatewayTip;
    public final AppCompatTextView tvLightOnState;
    public final AppCompatTextView tvLightOnStateTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSceneNumberTip;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDaliLightSettingVM viewmodel);

    protected ActDaliLightSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView actAddDeviceScroll, CircleImageView civColorFail, CircleImageView civColorLightOn, AppCompatImageView ivCtRangeGo, AppCompatImageView ivDimmingCurveGo, AppCompatImageView ivDimmingFadeTimeGo, AppCompatImageView ivDimmingRangeGo, AppCompatImageView ivFailureStateGo, AppCompatImageView ivIcon, AppCompatImageView ivLightOnStateGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneNameGo, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutCtRange, ConstraintLayout layoutDimmingCurve, ConstraintLayout layoutDimmingFadeTime, ConstraintLayout layoutDimmingRange, ConstraintLayout layoutFailureState, ConstraintLayout layoutLightOnState, ConstraintLayout layoutSceneName, SmartRefreshLayout refreshLayout, SwitchButton sbAddToSmart, LayoutTitleDefaultBinding title, AppCompatTextView tvCtRange, AppCompatTextView tvCtRangeTip, AppCompatTextView tvDeviceName, AppCompatTextView tvDeviceNumber, AppCompatTextView tvDimmingCurve, AppCompatTextView tvDimmingCurveTip, AppCompatTextView tvDimmingFadeTime, AppCompatTextView tvDimmingFadeTimeTip, AppCompatTextView tvDimmingRange, AppCompatTextView tvDimmingRangeTip, AppCompatTextView tvFailureState, AppCompatTextView tvFailureStateTip, AppCompatTextView tvGatewayName, AppCompatTextView tvGatewayTip, AppCompatTextView tvLightOnState, AppCompatTextView tvLightOnStateTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSceneNumberTip, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddDeviceScroll = actAddDeviceScroll;
        this.civColorFail = civColorFail;
        this.civColorLightOn = civColorLightOn;
        this.ivCtRangeGo = ivCtRangeGo;
        this.ivDimmingCurveGo = ivDimmingCurveGo;
        this.ivDimmingFadeTimeGo = ivDimmingFadeTimeGo;
        this.ivDimmingRangeGo = ivDimmingRangeGo;
        this.ivFailureStateGo = ivFailureStateGo;
        this.ivIcon = ivIcon;
        this.ivLightOnStateGo = ivLightOnStateGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneNameGo = ivSceneNameGo;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutCtRange = layoutCtRange;
        this.layoutDimmingCurve = layoutDimmingCurve;
        this.layoutDimmingFadeTime = layoutDimmingFadeTime;
        this.layoutDimmingRange = layoutDimmingRange;
        this.layoutFailureState = layoutFailureState;
        this.layoutLightOnState = layoutLightOnState;
        this.layoutSceneName = layoutSceneName;
        this.refreshLayout = refreshLayout;
        this.sbAddToSmart = sbAddToSmart;
        this.title = title;
        this.tvCtRange = tvCtRange;
        this.tvCtRangeTip = tvCtRangeTip;
        this.tvDeviceName = tvDeviceName;
        this.tvDeviceNumber = tvDeviceNumber;
        this.tvDimmingCurve = tvDimmingCurve;
        this.tvDimmingCurveTip = tvDimmingCurveTip;
        this.tvDimmingFadeTime = tvDimmingFadeTime;
        this.tvDimmingFadeTimeTip = tvDimmingFadeTimeTip;
        this.tvDimmingRange = tvDimmingRange;
        this.tvDimmingRangeTip = tvDimmingRangeTip;
        this.tvFailureState = tvFailureState;
        this.tvFailureStateTip = tvFailureStateTip;
        this.tvGatewayName = tvGatewayName;
        this.tvGatewayTip = tvGatewayTip;
        this.tvLightOnState = tvLightOnState;
        this.tvLightOnStateTip = tvLightOnStateTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSceneNumberTip = tvSceneNumberTip;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDaliLightSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDaliLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDaliLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_light_setting, root, attachToRoot, component);
    }

    public static ActDaliLightSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDaliLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_light_setting, null, false, component);
    }

    public static ActDaliLightSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightSettingBinding bind(View view, Object component) {
        return (ActDaliLightSettingBinding) bind(component, view, R.layout.act_dali_light_setting);
    }
}