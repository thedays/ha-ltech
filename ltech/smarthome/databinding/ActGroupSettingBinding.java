package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActGroupSettingBinding extends ViewDataBinding {
    public final LinearLayout editPlanTimeLayout;
    public final TextView endTimeTv;
    public final AppCompatImageView ivDeviceCountGo;
    public final AppCompatImageView ivGroupNameGo;
    public final AppCompatImageView ivIcon;
    public final VoisePlayingIcon ivPlayAnim;
    public final AppCompatImageView ivRhythmsGo;
    public final AppCompatImageView ivRoomNameGo;
    public final AppCompatImageView ivSelected1;
    public final AppCompatImageView ivSelected2;
    public final AppCompatImageView ivSelected3;
    public final ConstraintLayout layoutChangeIcon;
    public final ConstraintLayout layoutChangeRoom;
    public final ConstraintLayout layoutEditGroup;
    public final RelativeLayout layoutEndTime;
    public final ConstraintLayout layoutGroupName;
    public final RelativeLayout layoutOnOffTime;
    public final RelativeLayout layoutPlan;
    public final RelativeLayout layoutPlanTime;
    public final RelativeLayout layoutRepeatDate;
    public final RelativeLayout layoutRhythms;
    public final RelativeLayout layoutRhythmsState;
    public final LinearLayout layoutRhythmsSwitch;
    public final RelativeLayout layoutSetOnState;
    public final RelativeLayout layoutStartTime;
    public final RelativeLayout layoutSunset;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mManagerOrOwner;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActRhythmsSettingVM mViewmodel;
    public final AppCompatTextView planLabel;
    public final AppCompatTextView planTimeLabel;
    public final AppCompatImageView playIv;
    public final SmartRefreshLayout refreshLayout;
    public final AppCompatTextView repeatDateLabel;
    public final AppCompatTextView repeatWeekTv;
    public final AppCompatTextView rhythmsLabelTv;
    public final LinearLayout rhythmsSettingLayout;
    public final AppCompatTextView rhythmsStateLabel;
    public final SwitchButton sbRhythmsText;
    public final TextView startTimeTv;
    public final AppCompatTextView sunsetLabel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvGroupName;
    public final AppCompatTextView tvGroupTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvPlan;
    public final AppCompatTextView tvPlanTime;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setManagerOrOwner(Boolean managerOrOwner);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActRhythmsSettingVM viewmodel);

    protected ActGroupSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout editPlanTimeLayout, TextView endTimeTv, AppCompatImageView ivDeviceCountGo, AppCompatImageView ivGroupNameGo, AppCompatImageView ivIcon, VoisePlayingIcon ivPlayAnim, AppCompatImageView ivRhythmsGo, AppCompatImageView ivRoomNameGo, AppCompatImageView ivSelected1, AppCompatImageView ivSelected2, AppCompatImageView ivSelected3, ConstraintLayout layoutChangeIcon, ConstraintLayout layoutChangeRoom, ConstraintLayout layoutEditGroup, RelativeLayout layoutEndTime, ConstraintLayout layoutGroupName, RelativeLayout layoutOnOffTime, RelativeLayout layoutPlan, RelativeLayout layoutPlanTime, RelativeLayout layoutRepeatDate, RelativeLayout layoutRhythms, RelativeLayout layoutRhythmsState, LinearLayout layoutRhythmsSwitch, RelativeLayout layoutSetOnState, RelativeLayout layoutStartTime, RelativeLayout layoutSunset, AppCompatTextView planLabel, AppCompatTextView planTimeLabel, AppCompatImageView playIv, SmartRefreshLayout refreshLayout, AppCompatTextView repeatDateLabel, AppCompatTextView repeatWeekTv, AppCompatTextView rhythmsLabelTv, LinearLayout rhythmsSettingLayout, AppCompatTextView rhythmsStateLabel, SwitchButton sbRhythmsText, TextView startTimeTv, AppCompatTextView sunsetLabel, LayoutTitleDefaultBinding title, AppCompatTextView tvDelete, AppCompatTextView tvDeviceCount, AppCompatTextView tvGroupName, AppCompatTextView tvGroupTip, AppCompatTextView tvNameTip, AppCompatTextView tvPlan, AppCompatTextView tvPlanTime, AppCompatTextView tvRoomName, AppCompatTextView tvRoomTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.editPlanTimeLayout = editPlanTimeLayout;
        this.endTimeTv = endTimeTv;
        this.ivDeviceCountGo = ivDeviceCountGo;
        this.ivGroupNameGo = ivGroupNameGo;
        this.ivIcon = ivIcon;
        this.ivPlayAnim = ivPlayAnim;
        this.ivRhythmsGo = ivRhythmsGo;
        this.ivRoomNameGo = ivRoomNameGo;
        this.ivSelected1 = ivSelected1;
        this.ivSelected2 = ivSelected2;
        this.ivSelected3 = ivSelected3;
        this.layoutChangeIcon = layoutChangeIcon;
        this.layoutChangeRoom = layoutChangeRoom;
        this.layoutEditGroup = layoutEditGroup;
        this.layoutEndTime = layoutEndTime;
        this.layoutGroupName = layoutGroupName;
        this.layoutOnOffTime = layoutOnOffTime;
        this.layoutPlan = layoutPlan;
        this.layoutPlanTime = layoutPlanTime;
        this.layoutRepeatDate = layoutRepeatDate;
        this.layoutRhythms = layoutRhythms;
        this.layoutRhythmsState = layoutRhythmsState;
        this.layoutRhythmsSwitch = layoutRhythmsSwitch;
        this.layoutSetOnState = layoutSetOnState;
        this.layoutStartTime = layoutStartTime;
        this.layoutSunset = layoutSunset;
        this.planLabel = planLabel;
        this.planTimeLabel = planTimeLabel;
        this.playIv = playIv;
        this.refreshLayout = refreshLayout;
        this.repeatDateLabel = repeatDateLabel;
        this.repeatWeekTv = repeatWeekTv;
        this.rhythmsLabelTv = rhythmsLabelTv;
        this.rhythmsSettingLayout = rhythmsSettingLayout;
        this.rhythmsStateLabel = rhythmsStateLabel;
        this.sbRhythmsText = sbRhythmsText;
        this.startTimeTv = startTimeTv;
        this.sunsetLabel = sunsetLabel;
        this.title = title;
        this.tvDelete = tvDelete;
        this.tvDeviceCount = tvDeviceCount;
        this.tvGroupName = tvGroupName;
        this.tvGroupTip = tvGroupTip;
        this.tvNameTip = tvNameTip;
        this.tvPlan = tvPlan;
        this.tvPlanTime = tvPlanTime;
        this.tvRoomName = tvRoomName;
        this.tvRoomTip = tvRoomTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActRhythmsSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public Boolean getManagerOrOwner() {
        return this.mManagerOrOwner;
    }

    public static ActGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGroupSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_group_setting, root, attachToRoot, component);
    }

    public static ActGroupSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGroupSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActGroupSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_group_setting, null, false, component);
    }

    public static ActGroupSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGroupSettingBinding bind(View view, Object component) {
        return (ActGroupSettingBinding) bind(component, view, R.layout.act_group_setting);
    }
}