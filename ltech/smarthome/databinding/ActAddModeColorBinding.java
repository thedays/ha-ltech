package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.ActAddModeColorVM;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.VerticalSeekBar;

/* loaded from: classes3.dex */
public abstract class ActAddModeColorBinding extends ViewDataBinding {
    public final ColorEditText etBlue;
    public final ColorEditText etGreen;
    public final ColorEditText etRed;
    public final ColorEditText etWy;
    public final Group groupW;
    public final Group groupWy;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddModeColorVM mViewmodel;
    public final RecyclerView pickViewHour;
    public final RecyclerView pickViewMin;
    public final RecyclerView pickViewMs;
    public final RecyclerView pickViewSec;
    public final RecyclerView rvColor;
    public final LightBrtBar sbBrt;
    public final LightBrtBar sbW;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvB;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvG;
    public final AppCompatTextView tvR;
    public final AppCompatTextView tvSetColorTip;
    public final AppCompatTextView tvSetTime;
    public final AppCompatTextView tvW;
    public final AppCompatTextView tvWTip;
    public final AppCompatTextView tvWy;
    public final View vColor;
    public final View view28;
    public final View view29;
    public final View view30;
    public final VerticalSeekBar vsbBlue;
    public final VerticalSeekBar vsbGreen;
    public final VerticalSeekBar vsbRed;
    public final VerticalSeekBar vsbWy;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddModeColorVM viewmodel);

    protected ActAddModeColorBinding(Object _bindingComponent, View _root, int _localFieldCount, ColorEditText etBlue, ColorEditText etGreen, ColorEditText etRed, ColorEditText etWy, Group groupW, Group groupWy, RecyclerView pickViewHour, RecyclerView pickViewMin, RecyclerView pickViewMs, RecyclerView pickViewSec, RecyclerView rvColor, LightBrtBar sbBrt, LightBrtBar sbW, LayoutTitleDefaultBinding title, AppCompatTextView tvB, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvG, AppCompatTextView tvR, AppCompatTextView tvSetColorTip, AppCompatTextView tvSetTime, AppCompatTextView tvW, AppCompatTextView tvWTip, AppCompatTextView tvWy, View vColor, View view28, View view29, View view30, VerticalSeekBar vsbBlue, VerticalSeekBar vsbGreen, VerticalSeekBar vsbRed, VerticalSeekBar vsbWy) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etBlue = etBlue;
        this.etGreen = etGreen;
        this.etRed = etRed;
        this.etWy = etWy;
        this.groupW = groupW;
        this.groupWy = groupWy;
        this.pickViewHour = pickViewHour;
        this.pickViewMin = pickViewMin;
        this.pickViewMs = pickViewMs;
        this.pickViewSec = pickViewSec;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.sbW = sbW;
        this.title = title;
        this.tvB = tvB;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvG = tvG;
        this.tvR = tvR;
        this.tvSetColorTip = tvSetColorTip;
        this.tvSetTime = tvSetTime;
        this.tvW = tvW;
        this.tvWTip = tvWTip;
        this.tvWy = tvWy;
        this.vColor = vColor;
        this.view28 = view28;
        this.view29 = view29;
        this.view30 = view30;
        this.vsbBlue = vsbBlue;
        this.vsbGreen = vsbGreen;
        this.vsbRed = vsbRed;
        this.vsbWy = vsbWy;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddModeColorVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddModeColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddModeColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddModeColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_mode_color, root, attachToRoot, component);
    }

    public static ActAddModeColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddModeColorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddModeColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_mode_color, null, false, component);
    }

    public static ActAddModeColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddModeColorBinding bind(View view, Object component) {
        return (ActAddModeColorBinding) bind(component, view, R.layout.act_add_mode_color);
    }
}