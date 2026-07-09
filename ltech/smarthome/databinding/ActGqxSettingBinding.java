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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.gqx.ActGqxSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActGqxSettingBinding extends ViewDataBinding {
    public final AppCompatImageView batteryGo;
    public final AppCompatButton btnAdjust;
    public final AppCompatImageView deviceLogGo;
    public final AppCompatImageView deviceReplaceGo;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivDeviceIdGo;
    public final AppCompatImageView ivDeviceNameGo;
    public final AppCompatImageView ivIconGo;
    public final AppCompatImageView ivMacAddress;
    public final AppCompatImageView ivProductName;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView key1Go;
    public final AppCompatImageView key2Go;
    public final AppCompatImageView key3Go;
    public final AppCompatImageView key4Go;
    public final RelativeLayout layoutBattery;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutDeviceId;
    public final RelativeLayout layoutDeviceLog;
    public final ConstraintLayout layoutDeviceName;
    public final RelativeLayout layoutDeviceReplace;
    public final RelativeLayout layoutIconUpgrade;
    public final RelativeLayout layoutKey1;
    public final RelativeLayout layoutKey2;
    public final RelativeLayout layoutKey3;
    public final RelativeLayout layoutKey4;
    public final RelativeLayout layoutMacAddress;
    public final LinearLayout layoutMore;
    public final RelativeLayout layoutProductName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final RelativeLayout layoutSensitivity;
    public final RelativeLayout layoutSetKRange;
    public final RelativeLayout layoutUpgrade;
    public final RelativeLayout layoutVolume;

    @Bindable
    protected ProductId mProductId;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActGqxSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final SwitchButton sbVolumePowerOnTip;
    public final AppCompatImageView sensitivityGo;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvDeleteDevice;
    public final AppCompatTextView tvDeviceIdName;
    public final AppCompatTextView tvDeviceIdTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvIconTip;
    public final AppCompatTextView tvKey1Tip;
    public final AppCompatTextView tvKey2Tip;
    public final AppCompatTextView tvKey3Tip;
    public final AppCompatTextView tvKey4Tip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRelatedTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvUpgradeTip;

    public abstract void setProductId(ProductId productId);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActGqxSettingVM viewmodel);

    protected ActGqxSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView batteryGo, AppCompatButton btnAdjust, AppCompatImageView deviceLogGo, AppCompatImageView deviceReplaceGo, AppCompatImageView iv, AppCompatImageView ivDeviceIdGo, AppCompatImageView ivDeviceNameGo, AppCompatImageView ivIconGo, AppCompatImageView ivMacAddress, AppCompatImageView ivProductName, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView key1Go, AppCompatImageView key2Go, AppCompatImageView key3Go, AppCompatImageView key4Go, RelativeLayout layoutBattery, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutDeviceId, RelativeLayout layoutDeviceLog, ConstraintLayout layoutDeviceName, RelativeLayout layoutDeviceReplace, RelativeLayout layoutIconUpgrade, RelativeLayout layoutKey1, RelativeLayout layoutKey2, RelativeLayout layoutKey3, RelativeLayout layoutKey4, RelativeLayout layoutMacAddress, LinearLayout layoutMore, RelativeLayout layoutProductName, RelativeLayout layoutSceneAndAutomation, RelativeLayout layoutSensitivity, RelativeLayout layoutSetKRange, RelativeLayout layoutUpgrade, RelativeLayout layoutVolume, SmartRefreshLayout refreshLayout, SwitchButton sbVolumePowerOnTip, AppCompatImageView sensitivityGo, LayoutTitleDefaultBinding title, AppCompatTextView tvBatteryTip, AppCompatTextView tvDeleteDevice, AppCompatTextView tvDeviceIdName, AppCompatTextView tvDeviceIdTip, AppCompatTextView tvDeviceName, AppCompatTextView tvIconTip, AppCompatTextView tvKey1Tip, AppCompatTextView tvKey2Tip, AppCompatTextView tvKey3Tip, AppCompatTextView tvKey4Tip, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRelatedTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvUpgradeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.batteryGo = batteryGo;
        this.btnAdjust = btnAdjust;
        this.deviceLogGo = deviceLogGo;
        this.deviceReplaceGo = deviceReplaceGo;
        this.iv = iv;
        this.ivDeviceIdGo = ivDeviceIdGo;
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.ivIconGo = ivIconGo;
        this.ivMacAddress = ivMacAddress;
        this.ivProductName = ivProductName;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.key1Go = key1Go;
        this.key2Go = key2Go;
        this.key3Go = key3Go;
        this.key4Go = key4Go;
        this.layoutBattery = layoutBattery;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutDeviceId = layoutDeviceId;
        this.layoutDeviceLog = layoutDeviceLog;
        this.layoutDeviceName = layoutDeviceName;
        this.layoutDeviceReplace = layoutDeviceReplace;
        this.layoutIconUpgrade = layoutIconUpgrade;
        this.layoutKey1 = layoutKey1;
        this.layoutKey2 = layoutKey2;
        this.layoutKey3 = layoutKey3;
        this.layoutKey4 = layoutKey4;
        this.layoutMacAddress = layoutMacAddress;
        this.layoutMore = layoutMore;
        this.layoutProductName = layoutProductName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSensitivity = layoutSensitivity;
        this.layoutSetKRange = layoutSetKRange;
        this.layoutUpgrade = layoutUpgrade;
        this.layoutVolume = layoutVolume;
        this.refreshLayout = refreshLayout;
        this.sbVolumePowerOnTip = sbVolumePowerOnTip;
        this.sensitivityGo = sensitivityGo;
        this.title = title;
        this.tvBatteryTip = tvBatteryTip;
        this.tvDeleteDevice = tvDeleteDevice;
        this.tvDeviceIdName = tvDeviceIdName;
        this.tvDeviceIdTip = tvDeviceIdTip;
        this.tvDeviceName = tvDeviceName;
        this.tvIconTip = tvIconTip;
        this.tvKey1Tip = tvKey1Tip;
        this.tvKey2Tip = tvKey2Tip;
        this.tvKey3Tip = tvKey3Tip;
        this.tvKey4Tip = tvKey4Tip;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRelatedTip = tvRelatedTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvUpgradeTip = tvUpgradeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActGqxSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public ProductId getProductId() {
        return this.mProductId;
    }

    public static ActGqxSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqxSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActGqxSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gqx_setting, root, attachToRoot, component);
    }

    public static ActGqxSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqxSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActGqxSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gqx_setting, null, false, component);
    }

    public static ActGqxSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqxSettingBinding bind(View view, Object component) {
        return (ActGqxSettingBinding) bind(component, view, R.layout.act_gqx_setting);
    }
}