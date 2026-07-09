package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.kbs.ActKbsVM;

/* loaded from: classes3.dex */
public abstract class ActKbsBinding extends ViewDataBinding {
    public final AppCompatImageView ivBg;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActKbsVM mViewmodel;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvLabel;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActKbsVM viewmodel);

    protected ActKbsBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBg, RecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvLabel) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBg = ivBg;
        this.rv = rv;
        this.title = title;
        this.tvLabel = tvLabel;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActKbsVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActKbsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActKbsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_kbs, root, attachToRoot, component);
    }

    public static ActKbsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActKbsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_kbs, null, false, component);
    }

    public static ActKbsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKbsBinding bind(View view, Object component) {
        return (ActKbsBinding) bind(component, view, R.layout.act_kbs);
    }
}