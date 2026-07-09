package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelSettingBinding extends ViewDataBinding {
    public final AppCompatImageView deviceMcuGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView firmwareIv;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivEndTimeGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSerialNumber;
    public final AppCompatImageView ivStartTimeGo;
    public final AppCompatImageView ivTalkStateGo;
    public final AppCompatImageView ivTvDirectVoiceGo;
    public final AppCompatImageView ivTvNearbyWakeupGo;
    public final AppCompatImageView ivVoiceControlGo;
    public final RelativeLayout layoutApplicationUpgrade;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceInfo;
    public final RelativeLayout layoutDeviceMcu;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final ConstraintLayout layoutDirectVoice;
    public final ConstraintLayout layoutEndTime;
    public final RelativeLayout layoutFirmwareUpgrade;
    public final RelativeLayout layoutLogUpload;
    public final RelativeLayout layoutMacAddress;
    public final RelativeLayout layoutMemorizePower;
    public final LinearLayout layoutMore;
    public final ConstraintLayout layoutNearbyWakeup;
    public final ConstraintLayout layoutStartTime;
    public final LinearLayout layoutSwitch;
    public final RelativeLayout layoutSwitch1;
    public final RelativeLayout layoutSwitch2;
    public final RelativeLayout layoutSwitch3;
    public final RelativeLayout layoutSwitch4;
    public final ConstraintLayout layoutTalk;
    public final LinearLayout layoutVoice;
    public final ConstraintLayout layoutVoiceControlRange;
    public final RelativeLayout layoutWakeUp;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSuperPanelSettingVM mViewmodel;
    public final RelativeLayout rlSerialNumber;
    public final SwitchButton sbAutoUpgrade;
    public final SwitchButton sbEngravedText;
    public final SwitchButton sbMemorizePowerOnTip;
    public final SwitchButton sbNightMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDirectVoice;
    public final AppCompatTextView tvDirectVoiceState;
    public final AppCompatTextView tvEndTime;
    public final AppCompatTextView tvFirmwareTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvNearbyWakeup;
    public final AppCompatTextView tvNearbyWakeupState;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSerialNumber;
    public final AppCompatTextView tvSerialNumberTip;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvSwitchName1;
    public final AppCompatTextView tvSwitchName2;
    public final AppCompatTextView tvSwitchName3;
    public final AppCompatTextView tvSwitchName4;
    public final AppCompatTextView tvTalkState;
    public final AppCompatTextView tvTalkTip;
    public final AppCompatTextView tvTalkVoiceControlState;
    public final AppCompatTextView tvUpgradeTip;
    public final AppCompatTextView tvVoiceControlRange;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSuperPanelSettingVM viewmodel);

    protected ActSuperPanelSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView deviceMcuGo, AppCompatImageView deviceReplaceGo, AppCompatImageView firmwareIv, AppCompatImageView iv, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivEndTimeGo, AppCompatImageView ivMacAddress, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSerialNumber, AppCompatImageView ivStartTimeGo, AppCompatImageView ivTalkStateGo, AppCompatImageView ivTvDirectVoiceGo, AppCompatImageView ivTvNearbyWakeupGo, AppCompatImageView ivVoiceControlGo, RelativeLayout layoutApplicationUpgrade, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceInfo, RelativeLayout layoutDeviceMcu, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, ConstraintLayout layoutDirectVoice, ConstraintLayout layoutEndTime, RelativeLayout layoutFirmwareUpgrade, RelativeLayout layoutLogUpload, RelativeLayout layoutMacAddress, RelativeLayout layoutMemorizePower, LinearLayout layoutMore, ConstraintLayout layoutNearbyWakeup, ConstraintLayout layoutStartTime, LinearLayout layoutSwitch, RelativeLayout layoutSwitch1, RelativeLayout layoutSwitch2, RelativeLayout layoutSwitch3, RelativeLayout layoutSwitch4, ConstraintLayout layoutTalk, LinearLayout layoutVoice, ConstraintLayout layoutVoiceControlRange, RelativeLayout layoutWakeUp, RelativeLayout rlSerialNumber, SwitchButton sbAutoUpgrade, SwitchButton sbEngravedText, SwitchButton sbMemorizePowerOnTip, SwitchButton sbNightMode, LayoutTitleDefaultBinding title, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvDirectVoice, AppCompatTextView tvDirectVoiceState, AppCompatTextView tvEndTime, AppCompatTextView tvFirmwareTip, AppCompatTextView tvNameTip, AppCompatTextView tvNearbyWakeup, AppCompatTextView tvNearbyWakeupState, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSerialNumber, AppCompatTextView tvSerialNumberTip, AppCompatTextView tvStartTime, AppCompatTextView tvSwitchName1, AppCompatTextView tvSwitchName2, AppCompatTextView tvSwitchName3, AppCompatTextView tvSwitchName4, AppCompatTextView tvTalkState, AppCompatTextView tvTalkTip, AppCompatTextView tvTalkVoiceControlState, AppCompatTextView tvUpgradeTip, AppCompatTextView tvVoiceControlRange) {
        super(_bindingComponent, _root, _localFieldCount);
        this.deviceMcuGo = deviceMcuGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.firmwareIv = firmwareIv;
        this.iv = iv;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivEndTimeGo = ivEndTimeGo;
        this.ivMacAddress = ivMacAddress;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSerialNumber = ivSerialNumber;
        this.ivStartTimeGo = ivStartTimeGo;
        this.ivTalkStateGo = ivTalkStateGo;
        this.ivTvDirectVoiceGo = ivTvDirectVoiceGo;
        this.ivTvNearbyWakeupGo = ivTvNearbyWakeupGo;
        this.ivVoiceControlGo = ivVoiceControlGo;
        this.layoutApplicationUpgrade = layoutApplicationUpgrade;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceInfo = layoutDeviceInfo;
        this.layoutDeviceMcu = layoutDeviceMcu;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutDirectVoice = layoutDirectVoice;
        this.layoutEndTime = layoutEndTime;
        this.layoutFirmwareUpgrade = layoutFirmwareUpgrade;
        this.layoutLogUpload = layoutLogUpload;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutMemorizePower = layoutMemorizePower;
        this.layoutMore = layoutMore;
        this.layoutNearbyWakeup = layoutNearbyWakeup;
        this.layoutStartTime = layoutStartTime;
        this.layoutSwitch = layoutSwitch;
        this.layoutSwitch1 = layoutSwitch1;
        this.layoutSwitch2 = layoutSwitch2;
        this.layoutSwitch3 = layoutSwitch3;
        this.layoutSwitch4 = layoutSwitch4;
        this.layoutTalk = layoutTalk;
        this.layoutVoice = layoutVoice;
        this.layoutVoiceControlRange = layoutVoiceControlRange;
        this.layoutWakeUp = layoutWakeUp;
        this.rlSerialNumber = rlSerialNumber;
        this.sbAutoUpgrade = sbAutoUpgrade;
        this.sbEngravedText = sbEngravedText;
        this.sbMemorizePowerOnTip = sbMemorizePowerOnTip;
        this.sbNightMode = sbNightMode;
        this.title = title;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvDirectVoice = tvDirectVoice;
        this.tvDirectVoiceState = tvDirectVoiceState;
        this.tvEndTime = tvEndTime;
        this.tvFirmwareTip = tvFirmwareTip;
        this.tvNameTip = tvNameTip;
        this.tvNearbyWakeup = tvNearbyWakeup;
        this.tvNearbyWakeupState = tvNearbyWakeupState;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSerialNumber = tvSerialNumber;
        this.tvSerialNumberTip = tvSerialNumberTip;
        this.tvStartTime = tvStartTime;
        this.tvSwitchName1 = tvSwitchName1;
        this.tvSwitchName2 = tvSwitchName2;
        this.tvSwitchName3 = tvSwitchName3;
        this.tvSwitchName4 = tvSwitchName4;
        this.tvTalkState = tvTalkState;
        this.tvTalkTip = tvTalkTip;
        this.tvTalkVoiceControlState = tvTalkVoiceControlState;
        this.tvUpgradeTip = tvUpgradeTip;
        this.tvVoiceControlRange = tvVoiceControlRange;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActSuperPanelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSuperPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_setting, root, attachToRoot, component);
    }

    public static ActSuperPanelSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_setting, null, false, component);
    }

    public static ActSuperPanelSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelSettingBinding bind(View view, Object component) {
        return (ActSuperPanelSettingBinding) bind(component, view, R.layout.act_super_panel_setting);
    }
}