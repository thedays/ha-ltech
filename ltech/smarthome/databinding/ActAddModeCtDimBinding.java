package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.ActAddModeColorVM;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActAddModeCtDimBinding extends ViewDataBinding {
    public final ConstraintLayout constraintLayout2;
    public final RangeSeekBar csbCtBar;
    public final Group groupCt;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddModeColorVM mViewmodel;
    public final RecyclerView pickViewHour;
    public final RecyclerView pickViewMin;
    public final RecyclerView pickViewMs;
    public final RecyclerView pickViewSec;
    public final LightBrtBar sbBrt;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvCt;
    public final AppCompatTextView tvSetColorTip;
    public final AppCompatTextView tvSetTime;
    public final View view28;
    public final View view29;
    public final View view30;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddModeColorVM viewmodel);

    protected ActAddModeCtDimBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout constraintLayout2, RangeSeekBar csbCtBar, Group groupCt, RecyclerView pickViewHour, RecyclerView pickViewMin, RecyclerView pickViewMs, RecyclerView pickViewSec, LightBrtBar sbBrt, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvCt, AppCompatTextView tvSetColorTip, AppCompatTextView tvSetTime, View view28, View view29, View view30) {
        super(_bindingComponent, _root, _localFieldCount);
        this.constraintLayout2 = constraintLayout2;
        this.csbCtBar = csbCtBar;
        this.groupCt = groupCt;
        this.pickViewHour = pickViewHour;
        this.pickViewMin = pickViewMin;
        this.pickViewMs = pickViewMs;
        this.pickViewSec = pickViewSec;
        this.sbBrt = sbBrt;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvCt = tvCt;
        this.tvSetColorTip = tvSetColorTip;
        this.tvSetTime = tvSetTime;
        this.view28 = view28;
        this.view29 = view29;
        this.view30 = view30;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddModeColorVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddModeCtDimBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddModeCtDimBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddModeCtDimBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_mode_ct_dim, root, attachToRoot, component);
    }

    public static ActAddModeCtDimBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddModeCtDimBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddModeCtDimBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_mode_ct_dim, null, false, component);
    }

    public static ActAddModeCtDimBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddModeCtDimBinding bind(View view, Object component) {
        return (ActAddModeCtDimBinding) bind(component, view, R.layout.act_add_mode_ct_dim);
    }
}