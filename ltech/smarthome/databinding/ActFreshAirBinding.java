package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.air.ActAcCentralVM;

/* loaded from: classes3.dex */
public abstract class ActFreshAirBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView19;
    public final AppCompatTextView appCompatTextView23;
    public final CardView cardviewAdd;
    public final AppCompatImageView ivCircle;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivUpper;
    public final LinearLayout layoutCircleBar;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAcCentralVM mViewmodel;
    public final RecyclerView rvContent;
    public final RecyclerView rvInfo;
    public final LayoutTitleIrBinding title;
    public final AppCompatTextView tvIndex;
    public final AppCompatTextView tvResponse;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTemp;
    public final View viewOnOff;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAcCentralVM viewmodel);

    protected ActFreshAirBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView19, AppCompatTextView appCompatTextView23, CardView cardviewAdd, AppCompatImageView ivCircle, AppCompatImageView ivNext, AppCompatImageView ivUpper, LinearLayout layoutCircleBar, ConstraintLayout layoutLoad, RecyclerView rvContent, RecyclerView rvInfo, LayoutTitleIrBinding title, AppCompatTextView tvIndex, AppCompatTextView tvResponse, AppCompatTextView tvState, AppCompatTextView tvTemp, View viewOnOff) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView19 = appCompatTextView19;
        this.appCompatTextView23 = appCompatTextView23;
        this.cardviewAdd = cardviewAdd;
        this.ivCircle = ivCircle;
        this.ivNext = ivNext;
        this.ivUpper = ivUpper;
        this.layoutCircleBar = layoutCircleBar;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.rvInfo = rvInfo;
        this.title = title;
        this.tvIndex = tvIndex;
        this.tvResponse = tvResponse;
        this.tvState = tvState;
        this.tvTemp = tvTemp;
        this.viewOnOff = viewOnOff;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAcCentralVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActFreshAirBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFreshAirBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActFreshAirBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_fresh_air, root, attachToRoot, component);
    }

    public static ActFreshAirBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFreshAirBinding inflate(LayoutInflater inflater, Object component) {
        return (ActFreshAirBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_fresh_air, null, false, component);
    }

    public static ActFreshAirBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFreshAirBinding bind(View view, Object component) {
        return (ActFreshAirBinding) bind(component, view, R.layout.act_fresh_air);
    }
}