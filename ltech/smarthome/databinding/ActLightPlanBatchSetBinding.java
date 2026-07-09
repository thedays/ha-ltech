package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchVM;

/* loaded from: classes3.dex */
public abstract class ActLightPlanBatchSetBinding extends ViewDataBinding {
    public final AppCompatTextView editLabel;
    public final LinearLayout editPlanTimeLayout;
    public final TextView endTimeTv;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivSelected1;
    public final AppCompatImageView ivSelected2;
    public final AppCompatImageView ivSelected3;
    public final RelativeLayout layoutEndTime;
    public final RelativeLayout layoutPlan;
    public final RelativeLayout layoutPlanEdit;
    public final RelativeLayout layoutPlanTime;
    public final RelativeLayout layoutRepeatDate;
    public final RelativeLayout layoutStartTime;
    public final RelativeLayout layoutSunset;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActLightPlanBatchVM mViewmodel;
    public final AppCompatTextView planLabel;
    public final AppCompatTextView planTimeLabel;
    public final AppCompatTextView repeatDateLabel;
    public final AppCompatTextView repeatWeekTv;
    public final LinearLayout rhythmsSettingLayout;
    public final TextView startTimeTv;
    public final AppCompatTextView sunsetLabel;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvNext;
    public final AppCompatTextView tvPlan;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActLightPlanBatchVM viewmodel);

    protected ActLightPlanBatchSetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView editLabel, LinearLayout editPlanTimeLayout, TextView endTimeTv, AppCompatImageView iv, AppCompatImageView ivSelected1, AppCompatImageView ivSelected2, AppCompatImageView ivSelected3, RelativeLayout layoutEndTime, RelativeLayout layoutPlan, RelativeLayout layoutPlanEdit, RelativeLayout layoutPlanTime, RelativeLayout layoutRepeatDate, RelativeLayout layoutStartTime, RelativeLayout layoutSunset, AppCompatTextView planLabel, AppCompatTextView planTimeLabel, AppCompatTextView repeatDateLabel, AppCompatTextView repeatWeekTv, LinearLayout rhythmsSettingLayout, TextView startTimeTv, AppCompatTextView sunsetLabel, LayoutTitleDefaultBinding title, TextView tvNext, AppCompatTextView tvPlan) {
        super(_bindingComponent, _root, _localFieldCount);
        this.editLabel = editLabel;
        this.editPlanTimeLayout = editPlanTimeLayout;
        this.endTimeTv = endTimeTv;
        this.iv = iv;
        this.ivSelected1 = ivSelected1;
        this.ivSelected2 = ivSelected2;
        this.ivSelected3 = ivSelected3;
        this.layoutEndTime = layoutEndTime;
        this.layoutPlan = layoutPlan;
        this.layoutPlanEdit = layoutPlanEdit;
        this.layoutPlanTime = layoutPlanTime;
        this.layoutRepeatDate = layoutRepeatDate;
        this.layoutStartTime = layoutStartTime;
        this.layoutSunset = layoutSunset;
        this.planLabel = planLabel;
        this.planTimeLabel = planTimeLabel;
        this.repeatDateLabel = repeatDateLabel;
        this.repeatWeekTv = repeatWeekTv;
        this.rhythmsSettingLayout = rhythmsSettingLayout;
        this.startTimeTv = startTimeTv;
        this.sunsetLabel = sunsetLabel;
        this.title = title;
        this.tvNext = tvNext;
        this.tvPlan = tvPlan;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActLightPlanBatchVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActLightPlanBatchSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanBatchSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLightPlanBatchSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_plan_batch_set, root, attachToRoot, component);
    }

    public static ActLightPlanBatchSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanBatchSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLightPlanBatchSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_plan_batch_set, null, false, component);
    }

    public static ActLightPlanBatchSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanBatchSetBinding bind(View view, Object component) {
        return (ActLightPlanBatchSetBinding) bind(component, view, R.layout.act_light_plan_batch_set);
    }
}