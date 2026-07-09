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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActKbsGroupSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceCountGo;
    public final AppCompatImageView ivGroupNameGo;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivStateGo;
    public final ConstraintLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutEditGroup;
    public final ConstraintLayout layoutGroupName;
    public final RelativeLayout layoutSetOnState;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mManagerOrOwner;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActKbsGroupSettingVM mViewmodel;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvLightSetting;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setManagerOrOwner(Boolean managerOrOwner);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActKbsGroupSettingVM viewmodel);

    protected ActKbsGroupSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceCountGo, AppCompatImageView ivGroupNameGo, AppCompatImageView ivIcon, AppCompatImageView ivRoomNameGo, AppCompatImageView ivStateGo, ConstraintLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutEditGroup, ConstraintLayout layoutGroupName, RelativeLayout layoutSetOnState, SmartRefreshLayout refreshLayout, RecyclerView rvLightSetting, LayoutTitleDefaultBinding title, AppCompatTextView tvDelete, AppCompatTextView tvDeviceCount, AppCompatTextView tvGroupName, AppCompatTextView tvGroupTip, AppCompatTextView tvNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip, AppCompatTextView tvState, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceCountGo = ivDeviceCountGo;
        this.ivGroupNameGo = ivGroupNameGo;
        this.ivIcon = ivIcon;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivStateGo = ivStateGo;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutEditGroup = layoutEditGroup;
        this.layoutGroupName = layoutGroupName;
        this.layoutSetOnState = layoutSetOnState;
        this.refreshLayout = refreshLayout;
        this.rvLightSetting = rvLightSetting;
        this.title = title;
        this.tvDelete = tvDelete;
        this.tvDeviceCount = tvDeviceCount;
        this.tvGroupName = tvGroupName;
        this.tvGroupTip = tvGroupTip;
        this.tvNameTip = tvNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
        this.tvState = tvState;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActKbsGroupSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public Boolean getManagerOrOwner() {
        return this.mManagerOrOwner;
    }

    public static ActKbsGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActKbsGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_kbs_group_setting, root, attachToRoot, component);
    }

    public static ActKbsGroupSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsGroupSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActKbsGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_kbs_group_setting, null, false, component);
    }

    public static ActKbsGroupSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsGroupSettingBinding bind(View view, Object component) {
        return (ActKbsGroupSettingBinding) bind(component, view, R.layout.act_kbs_group_setting);
    }
}