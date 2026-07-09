package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActCreateHomeVM;

/* loaded from: classes3.dex */
public abstract class ActCreateHomeBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView3;
    public final AppCompatTextView appCompatTextView4;
    public final View layoutHomePosition;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCreateHomeVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvLocation;
    public final AppCompatTextView tvLocationGo;
    public final View view3;
    public final View view4;
    public final AppCompatTextView view5;
    public final AppCompatTextView view7;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCreateHomeVM viewmodel);

    protected ActCreateHomeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView3, AppCompatTextView appCompatTextView4, View layoutHomePosition, LayoutTitleDefaultBinding title, AppCompatTextView tvLocation, AppCompatTextView tvLocationGo, View view3, View view4, AppCompatTextView view5, AppCompatTextView view7) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView3 = appCompatTextView3;
        this.appCompatTextView4 = appCompatTextView4;
        this.layoutHomePosition = layoutHomePosition;
        this.title = title;
        this.tvLocation = tvLocation;
        this.tvLocationGo = tvLocationGo;
        this.view3 = view3;
        this.view4 = view4;
        this.view5 = view5;
        this.view7 = view7;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCreateHomeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCreateHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCreateHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCreateHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_create_home, root, attachToRoot, component);
    }

    public static ActCreateHomeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCreateHomeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCreateHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_create_home, null, false, component);
    }

    public static ActCreateHomeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCreateHomeBinding bind(View view, Object component) {
        return (ActCreateHomeBinding) bind(component, view, R.layout.act_create_home);
    }
}