package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ltech.smarthome.ui.device.rs8.ActRs8VM;
import com.ltech.smarthome.view.CurtainBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.ReverseLightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActRs8CurtainBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView23;
    public final ConstraintLayout bg;
    public final CardView cardviewAdd;
    public final LightBrtBar curtainLeft;
    public final LightBrtBar curtainLeftOnly;
    public final ReverseLightBrtBar curtainRight;
    public final CurtainBar curtainUpDown;
    public final AppCompatImageView ivDevice;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivUpper;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActRs8VM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvIndex;
    public final AppCompatTextView tvResponse;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActRs8VM viewmodel);

    protected ActRs8CurtainBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView23, ConstraintLayout bg, CardView cardviewAdd, LightBrtBar curtainLeft, LightBrtBar curtainLeftOnly, ReverseLightBrtBar curtainRight, CurtainBar curtainUpDown, AppCompatImageView ivDevice, AppCompatImageView ivNext, AppCompatImageView ivUpper, ConstraintLayout layoutLoad, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvIndex, AppCompatTextView tvResponse) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView23 = appCompatTextView23;
        this.bg = bg;
        this.cardviewAdd = cardviewAdd;
        this.curtainLeft = curtainLeft;
        this.curtainLeftOnly = curtainLeftOnly;
        this.curtainRight = curtainRight;
        this.curtainUpDown = curtainUpDown;
        this.ivDevice = ivDevice;
        this.ivNext = ivNext;
        this.ivUpper = ivUpper;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
        this.tvIndex = tvIndex;
        this.tvResponse = tvResponse;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActRs8VM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActRs8CurtainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8CurtainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRs8CurtainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_curtain, root, attachToRoot, component);
    }

    public static ActRs8CurtainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8CurtainBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRs8CurtainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_curtain, null, false, component);
    }

    public static ActRs8CurtainBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8CurtainBinding bind(View view, Object component) {
        return (ActRs8CurtainBinding) bind(component, view, R.layout.act_rs8_curtain);
    }
}