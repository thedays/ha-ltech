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
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActDaliLightGroupSettingBinding extends ViewDataBinding {
    public final NestedScrollView actAddDeviceScroll;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivManagerGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneNameGo;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutManagerGroup;
    public final ConstraintLayout layoutSceneName;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDaliLightGroupSettingVM mViewmodel;
    public final SwitchButton sbAddToSmart;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvGatewayName;
    public final AppCompatTextView tvGatewayTip;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupNumber;
    public final AppCompatTextView tvManagerGroup;
    public final AppCompatTextView tvManagerTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSceneNumberTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDaliLightGroupSettingVM viewmodel);

    protected ActDaliLightGroupSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView actAddDeviceScroll, AppCompatImageView ivIcon, AppCompatImageView ivManagerGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneNameGo, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutManagerGroup, ConstraintLayout layoutSceneName, SwitchButton sbAddToSmart, LayoutTitleDefaultBinding title, AppCompatTextView tvGatewayName, AppCompatTextView tvGatewayTip, AppCompatTextView tvGroupName, AppCompatTextView tvGroupNumber, AppCompatTextView tvManagerGroup, AppCompatTextView tvManagerTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSceneNumberTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddDeviceScroll = actAddDeviceScroll;
        this.ivIcon = ivIcon;
        this.ivManagerGo = ivManagerGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneNameGo = ivSceneNameGo;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutManagerGroup = layoutManagerGroup;
        this.layoutSceneName = layoutSceneName;
        this.sbAddToSmart = sbAddToSmart;
        this.title = title;
        this.tvGatewayName = tvGatewayName;
        this.tvGatewayTip = tvGatewayTip;
        this.tvGroupName = tvGroupName;
        this.tvGroupNumber = tvGroupNumber;
        this.tvManagerGroup = tvManagerGroup;
        this.tvManagerTip = tvManagerTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSceneNumberTip = tvSceneNumberTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDaliLightGroupSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDaliLightGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDaliLightGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_light_group_setting, root, attachToRoot, component);
    }

    public static ActDaliLightGroupSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightGroupSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDaliLightGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_light_group_setting, null, false, component);
    }

    public static ActDaliLightGroupSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliLightGroupSettingBinding bind(View view, Object component) {
        return (ActDaliLightGroupSettingBinding) bind(component, view, R.layout.act_dali_light_group_setting);
    }
}