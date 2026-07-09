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
import com.ltech.smarthome.ui.device.ir.BaseIrVM;

/* loaded from: classes3.dex */
public abstract class ActFanBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView23;
    public final CardView cardviewAdd;
    public final AppCompatImageView ivDevice;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivUpper;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected BaseIrVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleIrBinding title;
    public final AppCompatTextView tvIndex;
    public final AppCompatTextView tvResponse;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(BaseIrVM viewmodel);

    protected ActFanBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView23, CardView cardviewAdd, AppCompatImageView ivDevice, AppCompatImageView ivNext, AppCompatImageView ivUpper, ConstraintLayout layoutLoad, RecyclerView rvContent, LayoutTitleIrBinding title, AppCompatTextView tvIndex, AppCompatTextView tvResponse) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView23 = appCompatTextView23;
        this.cardviewAdd = cardviewAdd;
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

    public BaseIrVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActFanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActFanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_fan, root, attachToRoot, component);
    }

    public static ActFanBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFanBinding inflate(LayoutInflater inflater, Object component) {
        return (ActFanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_fan, null, false, component);
    }

    public static ActFanBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActFanBinding bind(View view, Object component) {
        return (ActFanBinding) bind(component, view, R.layout.act_fan);
    }
}