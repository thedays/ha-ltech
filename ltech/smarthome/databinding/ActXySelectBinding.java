package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.XYCoordinateView;

/* loaded from: classes3.dex */
public abstract class ActXySelectBinding extends ViewDataBinding {
    public final StepSetView layoutBrt;
    public final StepSetView layoutX;
    public final StepSetView layoutY;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final XYCoordinateView xyCoordinateView;

    public abstract void setTitle(TitleDefault title);

    protected ActXySelectBinding(Object _bindingComponent, View _root, int _localFieldCount, StepSetView layoutBrt, StepSetView layoutX, StepSetView layoutY, LayoutTitleDefaultBinding title, XYCoordinateView xyCoordinateView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBrt = layoutBrt;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.title = title;
        this.xyCoordinateView = xyCoordinateView;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActXySelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActXySelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActXySelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_xy_select, root, attachToRoot, component);
    }

    public static ActXySelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActXySelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ActXySelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_xy_select, null, false, component);
    }

    public static ActXySelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActXySelectBinding bind(View view, Object component) {
        return (ActXySelectBinding) bind(component, view, R.layout.act_xy_select);
    }
}