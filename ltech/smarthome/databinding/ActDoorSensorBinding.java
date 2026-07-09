package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActDoorSensorBinding extends ViewDataBinding {
    public final Group groupLog;
    public final AppCompatImageView ivAuto;
    public final AppCompatImageView ivBattery;
    public final AppCompatImageView ivDoorLeft;
    public final AppCompatImageView ivDoorRight;
    public final AppCompatImageView ivLog;
    public final RelativeLayout layoutAuto;
    public final ConstraintLayout layoutBattery;
    public final ConstraintLayout layoutContent;
    public final ConstraintLayout layoutLog;
    public final LinearLayout layoutPic;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvLog;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBattery;
    public final AppCompatTextView tvBatteryTip;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvDate;
    public final AppCompatTextView tvGatewayState;
    public final AppCompatTextView tvNoLog;
    public final AppCompatTextView tvState;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActDoorSensorBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupLog, AppCompatImageView ivAuto, AppCompatImageView ivBattery, AppCompatImageView ivDoorLeft, AppCompatImageView ivDoorRight, AppCompatImageView ivLog, RelativeLayout layoutAuto, ConstraintLayout layoutBattery, ConstraintLayout layoutContent, ConstraintLayout layoutLog, LinearLayout layoutPic, RecyclerView rvLog, LayoutTitleDefaultBinding title, AppCompatTextView tvBattery, AppCompatTextView tvBatteryTip, AppCompatTextView tvBottom, AppCompatTextView tvDate, AppCompatTextView tvGatewayState, AppCompatTextView tvNoLog, AppCompatTextView tvState) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupLog = groupLog;
        this.ivAuto = ivAuto;
        this.ivBattery = ivBattery;
        this.ivDoorLeft = ivDoorLeft;
        this.ivDoorRight = ivDoorRight;
        this.ivLog = ivLog;
        this.layoutAuto = layoutAuto;
        this.layoutBattery = layoutBattery;
        this.layoutContent = layoutContent;
        this.layoutLog = layoutLog;
        this.layoutPic = layoutPic;
        this.rvLog = rvLog;
        this.title = title;
        this.tvBattery = tvBattery;
        this.tvBatteryTip = tvBatteryTip;
        this.tvBottom = tvBottom;
        this.tvDate = tvDate;
        this.tvGatewayState = tvGatewayState;
        this.tvNoLog = tvNoLog;
        this.tvState = tvState;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActDoorSensorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDoorSensorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDoorSensorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_door_sensor, root, attachToRoot, component);
    }

    public static ActDoorSensorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDoorSensorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDoorSensorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_door_sensor, null, false, component);
    }

    public static ActDoorSensorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDoorSensorBinding bind(View view, Object component) {
        return (ActDoorSensorBinding) bind(component, view, R.layout.act_door_sensor);
    }
}