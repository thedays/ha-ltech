package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActEurPanelGroupSettingBinding extends ViewDataBinding {
    public final AppCompatButton btnAdjust;
    public final AppCompatImageView ivControlTypeStateGo;
    public final AppCompatImageView ivDeviceCountGo;
    public final AppCompatImageView ivDmxGo;
    public final AppCompatImageView ivGroupNameGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivStateGo;
    public final AppCompatImageView ivZoneControlStateGo;
    public final RelativeLayout layoutBuzzer;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutControlType;
    public final ConstraintLayout layoutEditGroup;
    public final ConstraintLayout layoutGroupName;
    public final RelativeLayout layoutKeySave;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutSetDmxType;
    public final RelativeLayout layoutSetKRange;
    public final ConstraintLayout layoutSetOnState;
    public final ConstraintLayout layoutZoneControl;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActEurPanelGroupSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvLightSetting;
    public final SwitchButton sbBuzzer;
    public final SwitchButton sbKeySave;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvControlType;
    public final AppCompatTextView tvControlTypeState;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvDmxType;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvKeySaveTip;
    public final AppCompatTextView tvLightOnState;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRelatedTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvZoneControl;
    public final AppCompatTextView tvZoneControlState;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActEurPanelGroupSettingVM viewmodel);

    protected ActEurPanelGroupSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btnAdjust, AppCompatImageView ivControlTypeStateGo, AppCompatImageView ivDeviceCountGo, AppCompatImageView ivDmxGo, AppCompatImageView ivGroupNameGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivStateGo, AppCompatImageView ivZoneControlStateGo, RelativeLayout layoutBuzzer, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutControlType, ConstraintLayout layoutEditGroup, ConstraintLayout layoutGroupName, RelativeLayout layoutKeySave, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutSetDmxType, RelativeLayout layoutSetKRange, ConstraintLayout layoutSetOnState, ConstraintLayout layoutZoneControl, SmartRefreshLayout refreshLayout, RecyclerView rvLightSetting, SwitchButton sbBuzzer, SwitchButton sbKeySave, LayoutTitleDefaultBinding title, AppCompatTextView tvControlType, AppCompatTextView tvControlTypeState, AppCompatTextView tvDelete, AppCompatTextView tvDeviceCount, AppCompatTextView tvDmxType, AppCompatTextView tvGroupName, AppCompatTextView tvGroupTip, AppCompatTextView tvKeySaveTip, AppCompatTextView tvLightOnState, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRelatedTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvState, AppCompatTextView tvZoneControl, AppCompatTextView tvZoneControlState) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnAdjust = btnAdjust;
        this.ivControlTypeStateGo = ivControlTypeStateGo;
        this.ivDeviceCountGo = ivDeviceCountGo;
        this.ivDmxGo = ivDmxGo;
        this.ivGroupNameGo = ivGroupNameGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivStateGo = ivStateGo;
        this.ivZoneControlStateGo = ivZoneControlStateGo;
        this.layoutBuzzer = layoutBuzzer;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutControlType = layoutControlType;
        this.layoutEditGroup = layoutEditGroup;
        this.layoutGroupName = layoutGroupName;
        this.layoutKeySave = layoutKeySave;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSetDmxType = layoutSetDmxType;
        this.layoutSetKRange = layoutSetKRange;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutZoneControl = layoutZoneControl;
        this.refreshLayout = refreshLayout;
        this.rvLightSetting = rvLightSetting;
        this.sbBuzzer = sbBuzzer;
        this.sbKeySave = sbKeySave;
        this.title = title;
        this.tvControlType = tvControlType;
        this.tvControlTypeState = tvControlTypeState;
        this.tvDelete = tvDelete;
        this.tvDeviceCount = tvDeviceCount;
        this.tvDmxType = tvDmxType;
        this.tvGroupName = tvGroupName;
        this.tvGroupTip = tvGroupTip;
        this.tvKeySaveTip = tvKeySaveTip;
        this.tvLightOnState = tvLightOnState;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRelatedTip = tvRelatedTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvState = tvState;
        this.tvZoneControl = tvZoneControl;
        this.tvZoneControlState = tvZoneControlState;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActEurPanelGroupSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActEurPanelGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEurPanelGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_eur_panel_group_setting, root, attachToRoot, component);
    }

    public static ActEurPanelGroupSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelGroupSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEurPanelGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_eur_panel_group_setting, null, false, component);
    }

    public static ActEurPanelGroupSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelGroupSettingBinding bind(View view, Object component) {
        return (ActEurPanelGroupSettingBinding) bind(component, view, R.layout.act_eur_panel_group_setting);
    }
}