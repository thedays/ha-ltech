package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.ir.ActIrDiyVM;

/* loaded from: classes3.dex */
public abstract class ActIrDiyBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView23;
    public final AppCompatTextView appCompatTextView39;
    public final CardView cardviewAdd;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIrDiyVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleIrBinding title;
    public final AppCompatTextView tvAdd;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIrDiyVM viewmodel);

    protected ActIrDiyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView23, AppCompatTextView appCompatTextView39, CardView cardviewAdd, RecyclerView rvContent, LayoutTitleIrBinding title, AppCompatTextView tvAdd) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView23 = appCompatTextView23;
        this.appCompatTextView39 = appCompatTextView39;
        this.cardviewAdd = cardviewAdd;
        this.rvContent = rvContent;
        this.title = title;
        this.tvAdd = tvAdd;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIrDiyVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIrDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIrDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIrDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ir_diy, root, attachToRoot, component);
    }

    public static ActIrDiyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIrDiyBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIrDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ir_diy, null, false, component);
    }

    public static ActIrDiyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIrDiyBinding bind(View view, Object component) {
        return (ActIrDiyBinding) bind(component, view, R.layout.act_ir_diy);
    }
}