package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanListVM;

/* loaded from: classes3.dex */
public abstract class ActLightPlanListBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActLightPlanListVM mViewmodel;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActLightPlanListVM viewmodel);

    protected ActLightPlanListBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rv, LayoutTitleDefaultBinding title, TextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rv = rv;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActLightPlanListVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActLightPlanListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLightPlanListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_plan_list, root, attachToRoot, component);
    }

    public static ActLightPlanListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLightPlanListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_light_plan_list, null, false, component);
    }

    public static ActLightPlanListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLightPlanListBinding bind(View view, Object component) {
        return (ActLightPlanListBinding) bind(component, view, R.layout.act_light_plan_list);
    }
}