package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.ReverseLightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActMotorBinding extends ViewDataBinding {
    public final LightBrtBar curtainLeft;
    public final ReverseLightBrtBar curtainRight;
    public final AppCompatImageView ivDevice;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleIrBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActMotorBinding(Object _bindingComponent, View _root, int _localFieldCount, LightBrtBar curtainLeft, ReverseLightBrtBar curtainRight, AppCompatImageView ivDevice, ConstraintLayout layoutLoad, RecyclerView rvContent, LayoutTitleIrBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.curtainLeft = curtainLeft;
        this.curtainRight = curtainRight;
        this.ivDevice = ivDevice;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActMotorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMotorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMotorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_motor, root, attachToRoot, component);
    }

    public static ActMotorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMotorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMotorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_motor, null, false, component);
    }

    public static ActMotorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMotorBinding bind(View view, Object component) {
        return (ActMotorBinding) bind(component, view, R.layout.act_motor);
    }
}