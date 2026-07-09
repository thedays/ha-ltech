package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActLightPlanDetailBinding extends ViewDataBinding {
    public final RangeSeekBar csbColorBar;
    public final RelativeLayout curNodeLayout;
    public final AppCompatTextView deleteTv;
    public final AppCompatTextView factoryResetTv;
    public final RelativeLayout gradientLayout;
    public final AppCompatTextView gradientTv;
    public final AppCompatImageView ivStateGo;
    public final AppCompatImageView ivStateGo1;
    public final AppCompatImageView ivStateGo2;
    public final AppCompatTextView kTv;
    public final AppCompatTextView label;
    public final AppCompatTextView label1;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActLightPlanDetailVM mViewmodel;
    public final RelativeLayout nameLayout;
    public final AppCompatTextView nameTv;
    public final LinearLayout nodeLayout;
    public final AppCompatTextView pTv;
    public final RecyclerView quickRv;
    public final AppCompatTextView reviewTv;
    public final RecyclerView rv;
    public final LightBrtBar sbBrt;
    public final RelativeLayout timeLayout;
    public final AppCompatTextView timeTv;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActLightPlanDetailVM viewmodel);

    protected ActLightPlanDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, RangeSeekBar csbColorBar, RelativeLayout curNodeLayout, AppCompatTextView deleteTv, AppCompatTextView factoryResetTv, RelativeLayout gradientLayout, AppCompatTextView gradientTv, AppCompatImageView ivStateGo, AppCompatImageView ivStateGo1, AppCompatImageView ivStateGo2, AppCompatTextView kTv, AppCompatTextView label, AppCompatTextView label1, RelativeLayout nameLayout, AppCompatTextView nameTv, LinearLayout nodeLayout, AppCompatTextView pTv, RecyclerView quickRv, AppCompatTextView reviewTv, RecyclerView rv, LightBrtBar sbBrt, RelativeLayout timeLayout, AppCompatTextView timeTv, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.csbColorBar = csbColorBar;
        this.curNodeLayout = curNodeLayout;
        this.deleteTv = deleteTv;
        this.factoryResetTv = factoryResetTv;
        this.gradientLayout = gradientLayout;
        this.gradientTv = gradientTv;
        this.ivStateGo = ivStateGo;
        this.ivStateGo1 = ivStateGo1;
        this.ivStateGo2 = ivStateGo2;
        this.kTv = kTv;
        this.label = label;
        this.label1 = label1;
        this.nameLayout = nameLayout;
        this.nameTv = nameTv;
        this.nodeLayout = nodeLayout;
        this.pTv = pTv;
        this.quickRv = quickRv;
        this.reviewTv = reviewTv;
        this.rv = rv;
        this.sbBrt = sbBrt;
        this.timeLayout = timeLayout;
        this.timeTv = timeTv;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActLightPlanDetailVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActLightPlanDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLightPlanDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_plan_detail, root, attachToRoot, component);
    }

    public static ActLightPlanDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLightPlanDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_plan_detail, null, false, component);
    }

    public static ActLightPlanDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanDetailBinding bind(View view, Object component) {
        return (ActLightPlanDetailBinding) bind(component, view, R.layout.act_light_plan_detail);
    }
}