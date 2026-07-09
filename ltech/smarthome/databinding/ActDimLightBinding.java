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
import com.ltech.smarthome.ui.device.light.ActDimLightVM;
import com.ltech.smarthome.view.RectProgressBar2;

/* loaded from: classes3.dex */
public abstract class ActDimLightBinding extends ViewDataBinding {
    public final AppCompatImageView ivOpen;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDimLightVM mViewmodel;
    public final RectProgressBar2 progressBar;
    public final RecyclerView rvMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final View view15;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDimLightVM viewmodel);

    protected ActDimLightBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivOpen, RectProgressBar2 progressBar, RecyclerView rvMode, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, View view15) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivOpen = ivOpen;
        this.progressBar = progressBar;
        this.rvMode = rvMode;
        this.title = title;
        this.tvBrt = tvBrt;
        this.view15 = view15;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDimLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDimLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDimLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDimLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dim_light, root, attachToRoot, component);
    }

    public static ActDimLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDimLightBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDimLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dim_light, null, false, component);
    }

    public static ActDimLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDimLightBinding bind(View view, Object component) {
        return (ActDimLightBinding) bind(component, view, R.layout.act_dim_light);
    }
}