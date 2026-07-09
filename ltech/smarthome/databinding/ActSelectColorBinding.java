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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.ColorPickerView;
import com.ltech.smarthome.view.ColorSeekBar2;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActSelectColorBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView21;
    public final ColorPickerView cpv;
    public final ColorEditText etBlue;
    public final ColorEditText etGreen;
    public final ColorEditText etRed;
    public final Group groupRgb;
    public final Group groupRgbw;
    public final Group groupRgbwy;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvColor;
    public final LightBrtBar sbBrt;
    public final LightBrtBar sbW;
    public final ColorSeekBar2 seekBarWy;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBlue;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvGreen;
    public final AppCompatTextView tvRed;
    public final AppCompatTextView tvW;
    public final AppCompatTextView tvWTip;
    public final View vColor;
    public final ConstraintLayout vControl;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectColorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView21, ColorPickerView cpv, ColorEditText etBlue, ColorEditText etGreen, ColorEditText etRed, Group groupRgb, Group groupRgbw, Group groupRgbwy, RecyclerView rvColor, LightBrtBar sbBrt, LightBrtBar sbW, ColorSeekBar2 seekBarWy, LayoutTitleDefaultBinding title, AppCompatTextView tvBlue, AppCompatTextView tvBrt, AppCompatTextView tvGreen, AppCompatTextView tvRed, AppCompatTextView tvW, AppCompatTextView tvWTip, View vColor, ConstraintLayout vControl) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView21 = appCompatTextView21;
        this.cpv = cpv;
        this.etBlue = etBlue;
        this.etGreen = etGreen;
        this.etRed = etRed;
        this.groupRgb = groupRgb;
        this.groupRgbw = groupRgbw;
        this.groupRgbwy = groupRgbwy;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.sbW = sbW;
        this.seekBarWy = seekBarWy;
        this.title = title;
        this.tvBlue = tvBlue;
        this.tvBrt = tvBrt;
        this.tvGreen = tvGreen;
        this.tvRed = tvRed;
        this.tvW = tvW;
        this.tvWTip = tvWTip;
        this.vColor = vColor;
        this.vControl = vControl;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_color, root, attachToRoot, component);
    }

    public static ActSelectColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectColorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_color, null, false, component);
    }

    public static ActSelectColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectColorBinding bind(View view, Object component) {
        return (ActSelectColorBinding) bind(component, view, R.layout.act_select_color);
    }
}