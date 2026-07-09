package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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
public abstract class ActBleCurtainMotorBinding extends ViewDataBinding {
    public final ConstraintLayout bottomLayout;
    public final LightBrtBar curtainLeft;
    public final ReverseLightBrtBar curtainRight;
    public final AppCompatImageView ivDevice;
    public final LinearLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final RecyclerView rvMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvMode;

    public abstract void setTitle(TitleDefault title);

    protected ActBleCurtainMotorBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout bottomLayout, LightBrtBar curtainLeft, ReverseLightBrtBar curtainRight, AppCompatImageView ivDevice, LinearLayout layoutLoad, RecyclerView rvContent, RecyclerView rvMode, LayoutTitleDefaultBinding title, AppCompatTextView tvMode) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bottomLayout = bottomLayout;
        this.curtainLeft = curtainLeft;
        this.curtainRight = curtainRight;
        this.ivDevice = ivDevice;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.rvMode = rvMode;
        this.title = title;
        this.tvMode = tvMode;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActBleCurtainMotorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleCurtainMotorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_curtain_motor, root, attachToRoot, component);
    }

    public static ActBleCurtainMotorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleCurtainMotorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_curtain_motor, null, false, component);
    }

    public static ActBleCurtainMotorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorBinding bind(View view, Object component) {
        return (ActBleCurtainMotorBinding) bind(component, view, R.layout.act_ble_curtain_motor);
    }
}