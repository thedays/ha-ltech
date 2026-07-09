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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActVoiceCallSettingBinding extends ViewDataBinding {
    public final LinearLayout editPlanTimeLayout;
    public final TextView endTimeTv;
    public final AppCompatImageView iv1Go;
    public final AppCompatImageView iv2Go;
    public final AppCompatImageView iv3Go;
    public final ConstraintLayout layoutAuto;
    public final ConstraintLayout layoutDisturb;
    public final RelativeLayout layoutEndTime;
    public final ConstraintLayout layoutGroup;
    public final ConstraintLayout layoutRepeat;
    public final RelativeLayout layoutStartTime;
    public final ConstraintLayout layoutWhiteList;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActVoiceCallSettingVM mViewmodel;
    public final SwitchButton sbAuto;
    public final SwitchButton sbDisturb;
    public final TextView startTimeTv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tv1Tip;
    public final AppCompatTextView tv2Tip;
    public final AppCompatTextView tv3Tip;
    public final AppCompatTextView tvMain;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActVoiceCallSettingVM viewmodel);

    protected ActVoiceCallSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout editPlanTimeLayout, TextView endTimeTv, AppCompatImageView iv1Go, AppCompatImageView iv2Go, AppCompatImageView iv3Go, ConstraintLayout layoutAuto, ConstraintLayout layoutDisturb, RelativeLayout layoutEndTime, ConstraintLayout layoutGroup, ConstraintLayout layoutRepeat, RelativeLayout layoutStartTime, ConstraintLayout layoutWhiteList, SwitchButton sbAuto, SwitchButton sbDisturb, TextView startTimeTv, LayoutTitleDefaultBinding title, AppCompatTextView tv1Tip, AppCompatTextView tv2Tip, AppCompatTextView tv3Tip, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.editPlanTimeLayout = editPlanTimeLayout;
        this.endTimeTv = endTimeTv;
        this.iv1Go = iv1Go;
        this.iv2Go = iv2Go;
        this.iv3Go = iv3Go;
        this.layoutAuto = layoutAuto;
        this.layoutDisturb = layoutDisturb;
        this.layoutEndTime = layoutEndTime;
        this.layoutGroup = layoutGroup;
        this.layoutRepeat = layoutRepeat;
        this.layoutStartTime = layoutStartTime;
        this.layoutWhiteList = layoutWhiteList;
        this.sbAuto = sbAuto;
        this.sbDisturb = sbDisturb;
        this.startTimeTv = startTimeTv;
        this.title = title;
        this.tv1Tip = tv1Tip;
        this.tv2Tip = tv2Tip;
        this.tv3Tip = tv3Tip;
        this.tvMain = tvMain;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActVoiceCallSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActVoiceCallSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActVoiceCallSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_setting, root, attachToRoot, component);
    }

    public static ActVoiceCallSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActVoiceCallSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_setting, null, false, component);
    }

    public static ActVoiceCallSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallSettingBinding bind(View view, Object component) {
        return (ActVoiceCallSettingBinding) bind(component, view, R.layout.act_voice_call_setting);
    }
}