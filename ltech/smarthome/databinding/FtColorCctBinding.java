package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.CCTCoordinateView;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class FtColorCctBinding extends ViewDataBinding {
    public final CCTCoordinateView cctCoordinateView;
    public final StepSetView layoutBrt;
    public final StepSetView layoutCt;
    public final StepSetView layoutDuv;
    public final ConstraintLayout layoutPoint;
    public final FrameLayout layoutView;
    public final RecyclerView rvColorPoint;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvColorTitle;
    public final AppCompatTextView tvDeleteTip;

    protected FtColorCctBinding(Object _bindingComponent, View _root, int _localFieldCount, CCTCoordinateView cctCoordinateView, StepSetView layoutBrt, StepSetView layoutCt, StepSetView layoutDuv, ConstraintLayout layoutPoint, FrameLayout layoutView, RecyclerView rvColorPoint, AppCompatTextView tvAdd, AppCompatTextView tvColorTitle, AppCompatTextView tvDeleteTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cctCoordinateView = cctCoordinateView;
        this.layoutBrt = layoutBrt;
        this.layoutCt = layoutCt;
        this.layoutDuv = layoutDuv;
        this.layoutPoint = layoutPoint;
        this.layoutView = layoutView;
        this.rvColorPoint = rvColorPoint;
        this.tvAdd = tvAdd;
        this.tvColorTitle = tvColorTitle;
        this.tvDeleteTip = tvDeleteTip;
    }

    public static FtColorCctBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorCctBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtColorCctBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_cct, root, attachToRoot, component);
    }

    public static FtColorCctBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorCctBinding inflate(LayoutInflater inflater, Object component) {
        return (FtColorCctBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_color_cct, null, false, component);
    }

    public static FtColorCctBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtColorCctBinding bind(View view, Object component) {
        return (FtColorCctBinding) bind(component, view, R.layout.ft_color_cct);
    }
}