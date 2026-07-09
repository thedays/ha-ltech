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

/* loaded from: classes3.dex */
public abstract class FtAirBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView19;
    public final Group groupOn;
    public final AppCompatImageView ivCircle;
    public final LinearLayout layoutCircleBar;
    public final LinearLayout layoutErrorTip;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected ActTePanelVM mViewmodel;
    public final RecyclerView rvContent;
    public final RecyclerView rvInfo;
    public final AppCompatTextView tvErrorTip;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvTemp;
    public final View viewAirOnOff;

    public abstract void setViewmodel(ActTePanelVM viewmodel);

    protected FtAirBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView19, Group groupOn, AppCompatImageView ivCircle, LinearLayout layoutCircleBar, LinearLayout layoutErrorTip, ConstraintLayout layoutLoad, RecyclerView rvContent, RecyclerView rvInfo, AppCompatTextView tvErrorTip, AppCompatTextView tvState, AppCompatTextView tvTemp, View viewAirOnOff) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView19 = appCompatTextView19;
        this.groupOn = groupOn;
        this.ivCircle = ivCircle;
        this.layoutCircleBar = layoutCircleBar;
        this.layoutErrorTip = layoutErrorTip;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.rvInfo = rvInfo;
        this.tvErrorTip = tvErrorTip;
        this.tvState = tvState;
        this.tvTemp = tvTemp;
        this.viewAirOnOff = viewAirOnOff;
    }

    public ActTePanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtAirBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAirBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtAirBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_air, root, attachToRoot, component);
    }

    public static FtAirBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAirBinding inflate(LayoutInflater inflater, Object component) {
        return (FtAirBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_air, null, false, component);
    }

    public static FtAirBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAirBinding bind(View view, Object component) {
        return (FtAirBinding) bind(component, view, R.layout.ft_air);
    }
}