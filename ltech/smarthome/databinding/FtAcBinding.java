package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM;
import com.ltech.smarthome.view.CircleBar;

/* loaded from: classes3.dex */
public abstract class FtAcBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView19;
    public final CircleBar circleBar;
    public final Group groupOn;
    public final AppCompatImageView ivMinus;
    public final AppCompatImageView ivPlus;
    public final AppCompatTextView ivUnit;
    public final LinearLayout layoutErrorTip;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected ActTePanelVM mViewmodel;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvErrorTip;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTemp;
    public final View viewOnOff;

    public abstract void setViewmodel(ActTePanelVM viewmodel);

    protected FtAcBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView19, CircleBar circleBar, Group groupOn, AppCompatImageView ivMinus, AppCompatImageView ivPlus, AppCompatTextView ivUnit, LinearLayout layoutErrorTip, ConstraintLayout layoutLoad, RecyclerView rvContent, AppCompatTextView tvErrorTip, AppCompatTextView tvState, AppCompatTextView tvTemp, View viewOnOff) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView19 = appCompatTextView19;
        this.circleBar = circleBar;
        this.groupOn = groupOn;
        this.ivMinus = ivMinus;
        this.ivPlus = ivPlus;
        this.ivUnit = ivUnit;
        this.layoutErrorTip = layoutErrorTip;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.tvErrorTip = tvErrorTip;
        this.tvState = tvState;
        this.tvTemp = tvTemp;
        this.viewOnOff = viewOnOff;
    }

    public ActTePanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtAcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtAcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_ac, root, attachToRoot, component);
    }

    public static FtAcBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAcBinding inflate(LayoutInflater inflater, Object component) {
        return (FtAcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_ac, null, false, component);
    }

    public static FtAcBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAcBinding bind(View view, Object component) {
        return (FtAcBinding) bind(component, view, R.layout.ft_ac);
    }
}