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
import com.ltech.smarthome.ui.home.ActAddFloorVM;

/* loaded from: classes3.dex */
public abstract class ActAddFloorBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView3;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddFloorVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final View view3;
    public final View view4;
    public final AppCompatTextView view7;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddFloorVM viewmodel);

    protected ActAddFloorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView3, LayoutTitleDefaultBinding title, View view3, View view4, AppCompatTextView view7) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView3 = appCompatTextView3;
        this.title = title;
        this.view3 = view3;
        this.view4 = view4;
        this.view7 = view7;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddFloorVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddFloorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddFloorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddFloorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_floor, root, attachToRoot, component);
    }

    public static ActAddFloorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddFloorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddFloorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_floor, null, false, component);
    }

    public static ActAddFloorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddFloorBinding bind(View view, Object component) {
        return (ActAddFloorBinding) bind(component, view, R.layout.act_add_floor);
    }
}