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
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActWaveSensorGroupSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivCtGo;
    public final AppCompatImageView ivDelayGo;
    public final AppCompatImageView ivDeviceCountGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivIlluminanceGo;
    public final AppCompatImageView ivLuxGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivSensitivityGo;
    public final RelativeLayout layoutAutomationDelay;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutEditGroup;
    public final ConstraintLayout layoutGroupName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final ConstraintLayout layoutSetCt;
    public final ConstraintLayout layoutSetIlluminance;
    public final ConstraintLayout layoutSetLux;
    public final ConstraintLayout layoutSetSensitivity;
    public final RelativeLayout layoutTestMode;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mManagerOrOwner;

    @Bindable
    protected TitleDefault mTitle;
    public final SmartRefreshLayout refreshLayout;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCtValue;
    public final AppCompatTextView tvDelay;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvIlluminance;
    public final AppCompatTextView tvLuxValue;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSensitivity;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setManagerOrOwner(Boolean managerOrOwner);

    public abstract void setTitle(TitleDefault title);

    protected ActWaveSensorGroupSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivCtGo, AppCompatImageView ivDelayGo, AppCompatImageView ivDeviceCountGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivIlluminanceGo, AppCompatImageView ivLuxGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivSensitivityGo, RelativeLayout layoutAutomationDelay, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutEditGroup, ConstraintLayout layoutGroupName, RelativeLayout layoutSceneAndAutomation, ConstraintLayout layoutSetCt, ConstraintLayout layoutSetIlluminance, ConstraintLayout layoutSetLux, ConstraintLayout layoutSetSensitivity, RelativeLayout layoutTestMode, SmartRefreshLayout refreshLayout, LayoutTitleDefaultBinding title, AppCompatTextView tvCtValue, AppCompatTextView tvDelay, AppCompatTextView tvDelete, AppCompatTextView tvDeviceCount, AppCompatTextView tvGroupName, AppCompatTextView tvGroupTip, AppCompatTextView tvIlluminance, AppCompatTextView tvLuxValue, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSensitivity) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivCtGo = ivCtGo;
        this.ivDelayGo = ivDelayGo;
        this.ivDeviceCountGo = ivDeviceCountGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivIlluminanceGo = ivIlluminanceGo;
        this.ivLuxGo = ivLuxGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivSensitivityGo = ivSensitivityGo;
        this.layoutAutomationDelay = layoutAutomationDelay;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutEditGroup = layoutEditGroup;
        this.layoutGroupName = layoutGroupName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSetCt = layoutSetCt;
        this.layoutSetIlluminance = layoutSetIlluminance;
        this.layoutSetLux = layoutSetLux;
        this.layoutSetSensitivity = layoutSetSensitivity;
        this.layoutTestMode = layoutTestMode;
        this.refreshLayout = refreshLayout;
        this.title = title;
        this.tvCtValue = tvCtValue;
        this.tvDelay = tvDelay;
        this.tvDelete = tvDelete;
        this.tvDeviceCount = tvDeviceCount;
        this.tvGroupName = tvGroupName;
        this.tvGroupTip = tvGroupTip;
        this.tvIlluminance = tvIlluminance;
        this.tvLuxValue = tvLuxValue;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSensitivity = tvSensitivity;
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

    public static ActWaveSensorGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActWaveSensorGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_group_setting, root, attachToRoot, component);
    }

    public static ActWaveSensorGroupSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorGroupSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActWaveSensorGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_wave_sensor_group_setting, null, false, component);
    }

    public static ActWaveSensorGroupSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWaveSensorGroupSettingBinding bind(View view, Object component) {
        return (ActWaveSensorGroupSettingBinding) bind(component, view, R.layout.act_wave_sensor_group_setting);
    }
}