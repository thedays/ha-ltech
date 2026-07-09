package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.automation.ActSelectEffectPeriodVM;

/* loaded from: classes3.dex */
public abstract class ActSelectEffectPeriodBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView17;
    public final AppCompatTextView appCompatTextView24;
    public final AppCompatTextView date;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSelectEffectPeriodVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEndTime;
    public final AppCompatTextView tvEndTimeTip;
    public final AppCompatTextView tvRepeatTime;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvStartTimeTip;
    public final View vSelectTime;
    public final View view22;
    public final View view3;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSelectEffectPeriodVM viewmodel);

    protected ActSelectEffectPeriodBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView17, AppCompatTextView appCompatTextView24, AppCompatTextView date, LayoutTitleDefaultBinding title, AppCompatTextView tvEndTime, AppCompatTextView tvEndTimeTip, AppCompatTextView tvRepeatTime, AppCompatTextView tvStartTime, AppCompatTextView tvStartTimeTip, View vSelectTime, View view22, View view3) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView17 = appCompatImageView17;
        this.appCompatTextView24 = appCompatTextView24;
        this.date = date;
        this.title = title;
        this.tvEndTime = tvEndTime;
        this.tvEndTimeTip = tvEndTimeTip;
        this.tvRepeatTime = tvRepeatTime;
        this.tvStartTime = tvStartTime;
        this.tvStartTimeTip = tvStartTimeTip;
        this.vSelectTime = vSelectTime;
        this.view22 = view22;
        this.view3 = view3;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSelectEffectPeriodVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSelectEffectPeriodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectEffectPeriodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectEffectPeriodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_effect_period, root, attachToRoot, component);
    }

    public static ActSelectEffectPeriodBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectEffectPeriodBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectEffectPeriodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_effect_period, null, false, component);
    }

    public static ActSelectEffectPeriodBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectEffectPeriodBinding bind(View view, Object component) {
        return (ActSelectEffectPeriodBinding) bind(component, view, R.layout.act_select_effect_period);
    }
}