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

/* loaded from: classes3.dex */
public abstract class ActTrigCurtainGroupSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivCurtainOpenDirGo;
    public final AppCompatImageView ivDeviceCountGo;
    public final AppCompatImageView ivGroupNameGo;
    public final AppCompatImageView ivRoomNameGo;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutCurtainOpenDir;
    public final ConstraintLayout layoutEditGroup;
    public final ConstraintLayout layoutGroupName;
    public final RelativeLayout layoutOnOffTime;
    public final RelativeLayout layoutSetOnState;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCurtainOpenDirName;
    public final AppCompatTextView tvCurtainOpenDirTip;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActTrigCurtainGroupSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivCurtainOpenDirGo, AppCompatImageView ivDeviceCountGo, AppCompatImageView ivGroupNameGo, AppCompatImageView ivRoomNameGo, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutCurtainOpenDir, ConstraintLayout layoutEditGroup, ConstraintLayout layoutGroupName, RelativeLayout layoutOnOffTime, RelativeLayout layoutSetOnState, LayoutTitleDefaultBinding title, AppCompatTextView tvCurtainOpenDirName, AppCompatTextView tvCurtainOpenDirTip, AppCompatTextView tvDelete, AppCompatTextView tvDeviceCount, AppCompatTextView tvGroupName, AppCompatTextView tvGroupTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivCurtainOpenDirGo = ivCurtainOpenDirGo;
        this.ivDeviceCountGo = ivDeviceCountGo;
        this.ivGroupNameGo = ivGroupNameGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutCurtainOpenDir = layoutCurtainOpenDir;
        this.layoutEditGroup = layoutEditGroup;
        this.layoutGroupName = layoutGroupName;
        this.layoutOnOffTime = layoutOnOffTime;
        this.layoutSetOnState = layoutSetOnState;
        this.title = title;
        this.tvCurtainOpenDirName = tvCurtainOpenDirName;
        this.tvCurtainOpenDirTip = tvCurtainOpenDirTip;
        this.tvDelete = tvDelete;
        this.tvDeviceCount = tvDeviceCount;
        this.tvGroupName = tvGroupName;
        this.tvGroupTip = tvGroupTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActTrigCurtainGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTrigCurtainGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain_group_setting, root, attachToRoot, component);
    }

    public static ActTrigCurtainGroupSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainGroupSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTrigCurtainGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain_group_setting, null, false, component);
    }

    public static ActTrigCurtainGroupSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainGroupSettingBinding bind(View view, Object component) {
        return (ActTrigCurtainGroupSettingBinding) bind(component, view, R.layout.act_trig_curtain_group_setting);
    }
}