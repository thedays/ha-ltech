package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActCgdProLightSettingBinding extends ViewDataBinding {
    public final LinearLayout editPlanTimeLayout;
    public final TextView endTimeTv;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivBatchGo;
    public final AppCompatImageView ivBatchHideGo;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivMacAddress;
    public final VoisePlayingIcon ivPlayAnim;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRhythmsGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSearchAddressGo;
    public final AppCompatImageView ivSelected1;
    public final AppCompatImageView ivSelected2;
    public final AppCompatImageView ivSelected3;
    public final RelativeLayout layoutBatchHideManage;
    public final RelativeLayout layoutBatchSet;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceId;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutEndTime;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutPlan;
    public final RelativeLayout layoutPlanTime;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutRepeatDate;
    public final RelativeLayout layoutRhythms;
    public final RelativeLayout layoutRhythmsState;
    public final LinearLayout layoutRhythmsSwitch;
    public final RelativeLayout layoutSearchAddress;
    public final RelativeLayout layoutStartTime;
    public final RelativeLayout layoutSunset;
    public final RelativeLayout layoutUpgrade;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCgdProLightSettingVM mViewmodel;
    public final AppCompatTextView planLabel;
    public final AppCompatTextView planTimeLabel;
    public final AppCompatImageView playIv;
    public final SmartRefreshLayout refreshLayout;
    public final AppCompatTextView repeatDateLabel;
    public final AppCompatTextView repeatWeekTv;
    public final AppCompatTextView rhythmsLabelTv;
    public final LinearLayout rhythmsSettingLayout;
    public final AppCompatTextView rhythmsStateLabel;
    public final SwitchButton sbRhythmsText;
    public final TextView startTimeTv;
    public final AppCompatTextView sunsetLabel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDbatchSet;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvPlan;
    public final AppCompatTextView tvPlanTime;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSearchAddress;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCgdProLightSettingVM viewmodel);

    protected ActCgdProLightSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout editPlanTimeLayout, TextView endTimeTv, AppCompatImageView iv, AppCompatImageView ivBatchGo, AppCompatImageView ivBatchHideGo, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivMacAddress, VoisePlayingIcon ivPlayAnim, AppCompatImageView ivProductName, AppCompatImageView ivRhythmsGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSearchAddressGo, AppCompatImageView ivSelected1, AppCompatImageView ivSelected2, AppCompatImageView ivSelected3, RelativeLayout layoutBatchHideManage, RelativeLayout layoutBatchSet, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceId, ConstraintLayout layoutDeviceName, RelativeLayout layoutEndTime, RelativeLayout layoutMacAddress, RelativeLayout layoutPlan, RelativeLayout layoutPlanTime, RelativeLayout layoutProductName, RelativeLayout layoutRepeatDate, RelativeLayout layoutRhythms, RelativeLayout layoutRhythmsState, LinearLayout layoutRhythmsSwitch, RelativeLayout layoutSearchAddress, RelativeLayout layoutStartTime, RelativeLayout layoutSunset, RelativeLayout layoutUpgrade, AppCompatTextView planLabel, AppCompatTextView planTimeLabel, AppCompatImageView playIv, SmartRefreshLayout refreshLayout, AppCompatTextView repeatDateLabel, AppCompatTextView repeatWeekTv, AppCompatTextView rhythmsLabelTv, LinearLayout rhythmsSettingLayout, AppCompatTextView rhythmsStateLabel, SwitchButton sbRhythmsText, TextView startTimeTv, AppCompatTextView sunsetLabel, LayoutTitleDefaultBinding title, AppCompatTextView tvDbatchSet, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvPlan, AppCompatTextView tvPlanTime, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSearchAddress, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.editPlanTimeLayout = editPlanTimeLayout;
        this.endTimeTv = endTimeTv;
        this.iv = iv;
        this.ivBatchGo = ivBatchGo;
        this.ivBatchHideGo = ivBatchHideGo;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivMacAddress = ivMacAddress;
        this.ivPlayAnim = ivPlayAnim;
        this.ivProductName = ivProductName;
        this.ivRhythmsGo = ivRhythmsGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSearchAddressGo = ivSearchAddressGo;
        this.ivSelected1 = ivSelected1;
        this.ivSelected2 = ivSelected2;
        this.ivSelected3 = ivSelected3;
        this.layoutBatchHideManage = layoutBatchHideManage;
        this.layoutBatchSet = layoutBatchSet;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutEndTime = layoutEndTime;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutPlan = layoutPlan;
        this.layoutPlanTime = layoutPlanTime;
        this.layoutProductName = layoutProductName;
        this.layoutRepeatDate = layoutRepeatDate;
        this.layoutRhythms = layoutRhythms;
        this.layoutRhythmsState = layoutRhythmsState;
        this.layoutRhythmsSwitch = layoutRhythmsSwitch;
        this.layoutSearchAddress = layoutSearchAddress;
        this.layoutStartTime = layoutStartTime;
        this.layoutSunset = layoutSunset;
        this.layoutUpgrade = layoutUpgrade;
        this.planLabel = planLabel;
        this.planTimeLabel = planTimeLabel;
        this.playIv = playIv;
        this.refreshLayout = refreshLayout;
        this.repeatDateLabel = repeatDateLabel;
        this.repeatWeekTv = repeatWeekTv;
        this.rhythmsLabelTv = rhythmsLabelTv;
        this.rhythmsSettingLayout = rhythmsSettingLayout;
        this.rhythmsStateLabel = rhythmsStateLabel;
        this.sbRhythmsText = sbRhythmsText;
        this.startTimeTv = startTimeTv;
        this.sunsetLabel = sunsetLabel;
        this.title = title;
        this.tvDbatchSet = tvDbatchSet;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvNameTip = tvNameTip;
        this.tvPlan = tvPlan;
        this.tvPlanTime = tvPlanTime;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSearchAddress = tvSearchAddress;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCgdProLightSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCgdProLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCgdProLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCgdProLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cgd_pro_light_setting, root, attachToRoot, component);
    }

    public static ActCgdProLightSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCgdProLightSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCgdProLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cgd_pro_light_setting, null, false, component);
    }

    public static ActCgdProLightSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCgdProLightSettingBinding bind(View view, Object component) {
        return (ActCgdProLightSettingBinding) bind(component, view, R.layout.act_cgd_pro_light_setting);
    }
}