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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6VM;

/* loaded from: classes3.dex */
public abstract class ActEurPanelEb6Binding extends ViewDataBinding {
    public final AppCompatImageView ivEb6;
    public final ConstraintLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActEurPanelEb6VM mViewmodel;
    public final RecyclerView rvKnobAction;
    public final RecyclerView rvScene;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBindName;
    public final AppCompatTextView tvInstruction;
    public final AppCompatTextView tvTipMessage;
    public final View view6;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActEurPanelEb6VM viewmodel);

    protected ActEurPanelEb6Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEb6, ConstraintLayout layoutContent, RecyclerView rvKnobAction, RecyclerView rvScene, LayoutTitleDefaultBinding title, AppCompatTextView tvBindName, AppCompatTextView tvInstruction, AppCompatTextView tvTipMessage, View view6) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEb6 = ivEb6;
        this.layoutContent = layoutContent;
        this.rvKnobAction = rvKnobAction;
        this.rvScene = rvScene;
        this.title = title;
        this.tvBindName = tvBindName;
        this.tvInstruction = tvInstruction;
        this.tvTipMessage = tvTipMessage;
        this.view6 = view6;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActEurPanelEb6VM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActEurPanelEb6Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelEb6Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEurPanelEb6Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_eur_panel_eb6, root, attachToRoot, component);
    }

    public static ActEurPanelEb6Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelEb6Binding inflate(LayoutInflater inflater, Object component) {
        return (ActEurPanelEb6Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_eur_panel_eb6, null, false, component);
    }

    public static ActEurPanelEb6Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEurPanelEb6Binding bind(View view, Object component) {
        return (ActEurPanelEb6Binding) bind(component, view, R.layout.act_eur_panel_eb6);
    }
}