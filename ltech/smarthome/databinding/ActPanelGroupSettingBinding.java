package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActPanelGroupSettingBinding extends ViewDataBinding {
    public final AppCompatButton btnAdjust;
    public final AppCompatImageView elderlyModeGo;
    public final AppCompatImageView ivDeviceCountGo;
    public final AppCompatImageView ivDistanceGo;
    public final AppCompatImageView ivEndTimeGo;
    public final AppCompatImageView ivGroupNameGo;
    public final AppCompatImageView ivPatternGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivStartTimeGo;
    public final AppCompatImageView languageGo;
    public final RelativeLayout layoutBrtButton;
    public final RelativeLayout layoutChangeLanguage;
    public final RelativeLayout layoutChangePattern;
    public final ConstraintLayout layoutChangeRoom;
    public final RelativeLayout layoutDistance;
    public final ConstraintLayout layoutEditGroup;
    public final RelativeLayout layoutElderlyMode;
    public final ConstraintLayout layoutEndTime;
    public final RelativeLayout layoutEngraved;
    public final ConstraintLayout layoutGroupName;
    public final LinearLayout layoutMore;
    public final RelativeLayout layoutOnOffTime;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutSetKRange;
    public final RelativeLayout layoutSetOnState;
    public final ConstraintLayout layoutStartTime;
    public final RelativeLayout layoutSwitchPositionSetting;
    public final RelativeLayout layoutSwitchSetting;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mManagerOrOwner;

    @Bindable
    protected Boolean mSwitchPositionSetting;

    @Bindable
    protected Boolean mSwitchSettingShow;

    @Bindable
    protected TitleDefault mTitle;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvKeys;
    public final SwitchButton sbDistance;
    public final SwitchButton sbEngravedText;
    public final LinearLayout sbLayout;
    public final SwitchButton sbMemorizePowerOnTip;
    public final SwitchButton sbNightMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvDistance;
    public final AppCompatTextView tvElderlyModeTip;
    public final AppCompatTextView tvEndTime;
    public final AppCompatTextView tvEndTimeTip;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvLanguageTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvNightModeTip;
    public final AppCompatTextView tvPattern;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvStartTimeTip;
    public final AppCompatTextView tvTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setManagerOrOwner(Boolean managerOrOwner);

    public abstract void setSwitchPositionSetting(Boolean switchPositionSetting);

    public abstract void setSwitchSettingShow(Boolean switchSettingShow);

    public abstract void setTitle(TitleDefault title);

    protected ActPanelGroupSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btnAdjust, AppCompatImageView elderlyModeGo, AppCompatImageView ivDeviceCountGo, AppCompatImageView ivDistanceGo, AppCompatImageView ivEndTimeGo, AppCompatImageView ivGroupNameGo, AppCompatImageView ivPatternGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivStartTimeGo, AppCompatImageView languageGo, RelativeLayout layoutBrtButton, RelativeLayout layoutChangeLanguage, RelativeLayout layoutChangePattern, ConstraintLayout layoutChangeRoom, RelativeLayout layoutDistance, ConstraintLayout layoutEditGroup, RelativeLayout layoutElderlyMode, ConstraintLayout layoutEndTime, RelativeLayout layoutEngraved, ConstraintLayout layoutGroupName, LinearLayout layoutMore, RelativeLayout layoutOnOffTime, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutSetKRange, RelativeLayout layoutSetOnState, ConstraintLayout layoutStartTime, RelativeLayout layoutSwitchPositionSetting, RelativeLayout layoutSwitchSetting, SmartRefreshLayout refreshLayout, RecyclerView rvKeys, SwitchButton sbDistance, SwitchButton sbEngravedText, LinearLayout sbLayout, SwitchButton sbMemorizePowerOnTip, SwitchButton sbNightMode, LayoutTitleDefaultBinding title, AppCompatTextView tvDelete, AppCompatTextView tvDeviceCount, AppCompatTextView tvDistance, AppCompatTextView tvElderlyModeTip, AppCompatTextView tvEndTime, AppCompatTextView tvEndTimeTip, AppCompatTextView tvGroupName, AppCompatTextView tvGroupTip, AppCompatTextView tvLanguageTip, AppCompatTextView tvNameTip, AppCompatTextView tvNightModeTip, AppCompatTextView tvPattern, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvStartTime, AppCompatTextView tvStartTimeTip, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnAdjust = btnAdjust;
        this.elderlyModeGo = elderlyModeGo;
        this.ivDeviceCountGo = ivDeviceCountGo;
        this.ivDistanceGo = ivDistanceGo;
        this.ivEndTimeGo = ivEndTimeGo;
        this.ivGroupNameGo = ivGroupNameGo;
        this.ivPatternGo = ivPatternGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivStartTimeGo = ivStartTimeGo;
        this.languageGo = languageGo;
        this.layoutBrtButton = layoutBrtButton;
        this.layoutChangeLanguage = layoutChangeLanguage;
        this.layoutChangePattern = layoutChangePattern;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDistance = layoutDistance;
        this.layoutEditGroup = layoutEditGroup;
        this.layoutElderlyMode = layoutElderlyMode;
        this.layoutEndTime = layoutEndTime;
        this.layoutEngraved = layoutEngraved;
        this.layoutGroupName = layoutGroupName;
        this.layoutMore = layoutMore;
        this.layoutOnOffTime = layoutOnOffTime;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSetKRange = layoutSetKRange;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutStartTime = layoutStartTime;
        this.layoutSwitchPositionSetting = layoutSwitchPositionSetting;
        this.layoutSwitchSetting = layoutSwitchSetting;
        this.refreshLayout = refreshLayout;
        this.rvKeys = rvKeys;
        this.sbDistance = sbDistance;
        this.sbEngravedText = sbEngravedText;
        this.sbLayout = sbLayout;
        this.sbMemorizePowerOnTip = sbMemorizePowerOnTip;
        this.sbNightMode = sbNightMode;
        this.title = title;
        this.tvDelete = tvDelete;
        this.tvDeviceCount = tvDeviceCount;
        this.tvDistance = tvDistance;
        this.tvElderlyModeTip = tvElderlyModeTip;
        this.tvEndTime = tvEndTime;
        this.tvEndTimeTip = tvEndTimeTip;
        this.tvGroupName = tvGroupName;
        this.tvGroupTip = tvGroupTip;
        this.tvLanguageTip = tvLanguageTip;
        this.tvNameTip = tvNameTip;
        this.tvNightModeTip = tvNightModeTip;
        this.tvPattern = tvPattern;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvStartTime = tvStartTime;
        this.tvStartTimeTip = tvStartTimeTip;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Boolean getManagerOrOwner() {
        return this.mManagerOrOwner;
    }

    public Boolean getSwitchSettingShow() {
        return this.mSwitchSettingShow;
    }

    public Boolean getSwitchPositionSetting() {
        return this.mSwitchPositionSetting;
    }

    public static ActPanelGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPanelGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_group_setting, root, attachToRoot, component);
    }

    public static ActPanelGroupSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelGroupSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPanelGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_group_setting, null, false, component);
    }

    public static ActPanelGroupSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelGroupSettingBinding bind(View view, Object component) {
        return (ActPanelGroupSettingBinding) bind(component, view, R.layout.act_panel_group_setting);
    }
}