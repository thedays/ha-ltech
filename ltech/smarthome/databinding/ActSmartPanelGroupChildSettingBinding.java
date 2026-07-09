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
import com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActSmartPanelGroupChildSettingBinding extends ViewDataBinding {
    public final NestedScrollView actAddDeviceScroll;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneGo;
    public final AppCompatImageView ivSceneNameGo;
    public final AppCompatImageView ivSubDeviceNameGo;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutGroupName;
    public final RelativeLayout layoutSceneAndAutomation;
    public final ConstraintLayout layoutSubDevice;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartPanelGroupChildSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final SwitchButton sbAddToSmart;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvDeviceTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRelatedNum;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSubordinateDevice;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartPanelGroupChildSettingVM viewmodel);

    protected ActSmartPanelGroupChildSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView actAddDeviceScroll, AppCompatImageView ivIcon, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneGo, AppCompatImageView ivSceneNameGo, AppCompatImageView ivSubDeviceNameGo, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutGroupName, RelativeLayout layoutSceneAndAutomation, ConstraintLayout layoutSubDevice, SmartRefreshLayout refreshLayout, SwitchButton sbAddToSmart, LayoutTitleDefaultBinding title, AppCompatTextView tvDeviceName, AppCompatTextView tvDeviceTip, AppCompatTextView tvNameTip, AppCompatTextView tvRelatedNum, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSubordinateDevice) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddDeviceScroll = actAddDeviceScroll;
        this.ivIcon = ivIcon;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneGo = ivSceneGo;
        this.ivSceneNameGo = ivSceneNameGo;
        this.ivSubDeviceNameGo = ivSubDeviceNameGo;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutGroupName = layoutGroupName;
        this.layoutSceneAndAutomation = layoutSceneAndAutomation;
        this.layoutSubDevice = layoutSubDevice;
        this.refreshLayout = refreshLayout;
        this.sbAddToSmart = sbAddToSmart;
        this.title = title;
        this.tvDeviceName = tvDeviceName;
        this.tvDeviceTip = tvDeviceTip;
        this.tvNameTip = tvNameTip;
        this.tvRelatedNum = tvRelatedNum;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSubordinateDevice = tvSubordinateDevice;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartPanelGroupChildSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSmartPanelGroupChildSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelGroupChildSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSmartPanelGroupChildSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_group_child_setting, root, attachToRoot, component);
    }

    public static ActSmartPanelGroupChildSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelGroupChildSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSmartPanelGroupChildSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_group_child_setting, null, false, component);
    }

    public static ActSmartPanelGroupChildSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelGroupChildSettingBinding bind(View view, Object component) {
        return (ActSmartPanelGroupChildSettingBinding) bind(component, view, R.layout.act_smart_panel_group_child_setting);
    }
}