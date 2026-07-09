package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActDaliSceneSettingBinding extends ViewDataBinding {
    public final NestedScrollView actAddDeviceScroll;
    public final LinearLayout btnEdit;
    public final AppCompatImageView ivAction;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSceneNameGo;
    public final RelativeLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutSceneName;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDaliSceneSettingVM mViewmodel;
    public final RecyclerView rvDaliScene;
    public final SwitchButton sbAddToCommon;
    public final SwitchButton sbAddToSmart;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvGatewayName;
    public final AppCompatTextView tvGatewayTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSceneName;
    public final AppCompatTextView tvSceneNumber;
    public final AppCompatTextView tvSceneNumberTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDaliSceneSettingVM viewmodel);

    protected ActDaliSceneSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView actAddDeviceScroll, LinearLayout btnEdit, AppCompatImageView ivAction, AppCompatImageView ivIcon, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSceneNameGo, RelativeLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutSceneName, RecyclerView rvDaliScene, SwitchButton sbAddToCommon, SwitchButton sbAddToSmart, LayoutTitleDefaultBinding title, AppCompatTextView tvAction, AppCompatTextView tvGatewayName, AppCompatTextView tvGatewayTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSceneName, AppCompatTextView tvSceneNumber, AppCompatTextView tvSceneNumberTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddDeviceScroll = actAddDeviceScroll;
        this.btnEdit = btnEdit;
        this.ivAction = ivAction;
        this.ivIcon = ivIcon;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSceneNameGo = ivSceneNameGo;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutSceneName = layoutSceneName;
        this.rvDaliScene = rvDaliScene;
        this.sbAddToCommon = sbAddToCommon;
        this.sbAddToSmart = sbAddToSmart;
        this.title = title;
        this.tvAction = tvAction;
        this.tvGatewayName = tvGatewayName;
        this.tvGatewayTip = tvGatewayTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSceneName = tvSceneName;
        this.tvSceneNumber = tvSceneNumber;
        this.tvSceneNumberTip = tvSceneNumberTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDaliSceneSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDaliSceneSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliSceneSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDaliSceneSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_scene_setting, root, attachToRoot, component);
    }

    public static ActDaliSceneSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliSceneSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDaliSceneSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_scene_setting, null, false, component);
    }

    public static ActDaliSceneSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliSceneSettingBinding bind(View view, Object component) {
        return (ActDaliSceneSettingBinding) bind(component, view, R.layout.act_dali_scene_setting);
    }
}