package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActGroupCurtainSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceCountGo;
    public final AppCompatImageView ivGroupNameGo;
    public final AppCompatImageView ivMotorDirectionNameGo;
    public final AppCompatImageView ivRoomNameGo;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutEditGroup;
    public final ConstraintLayout layoutGroupName;
    public final ConstraintLayout layoutMotorOpenType;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvOpenType;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvSetCurtainMotorTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActGroupCurtainSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceCountGo, AppCompatImageView ivGroupNameGo, AppCompatImageView ivMotorDirectionNameGo, AppCompatImageView ivRoomNameGo, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutEditGroup, ConstraintLayout layoutGroupName, ConstraintLayout layoutMotorOpenType, LayoutTitleDefaultBinding title, AppCompatTextView tvDelete, AppCompatTextView tvDeviceCount, AppCompatTextView tvGroupName, AppCompatTextView tvGroupTip, AppCompatTextView tvNameTip, AppCompatTextView tvOpenType, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvSetCurtainMotorTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceCountGo = ivDeviceCountGo;
        this.ivGroupNameGo = ivGroupNameGo;
        this.ivMotorDirectionNameGo = ivMotorDirectionNameGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutEditGroup = layoutEditGroup;
        this.layoutGroupName = layoutGroupName;
        this.layoutMotorOpenType = layoutMotorOpenType;
        this.title = title;
        this.tvDelete = tvDelete;
        this.tvDeviceCount = tvDeviceCount;
        this.tvGroupName = tvGroupName;
        this.tvGroupTip = tvGroupTip;
        this.tvNameTip = tvNameTip;
        this.tvOpenType = tvOpenType;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvSetCurtainMotorTip = tvSetCurtainMotorTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActGroupCurtainSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGroupCurtainSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActGroupCurtainSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_group_curtain_setting, root, attachToRoot, component);
    }

    public static ActGroupCurtainSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGroupCurtainSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActGroupCurtainSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_group_curtain_setting, null, false, component);
    }

    public static ActGroupCurtainSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGroupCurtainSettingBinding bind(View view, Object component) {
        return (ActGroupCurtainSettingBinding) bind(component, view, R.layout.act_group_curtain_setting);
    }
}