package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.SwitchSeekBarView;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ViewPowerStateBleAllBinding extends ViewDataBinding {
    public final CircleImageView civColor;
    public final ConstraintLayout layoutPowerState;

    @Bindable
    protected Boolean mCtGone;

    @Bindable
    protected Boolean mCwGone;

    @Bindable
    protected Boolean mRgbGone;

    @Bindable
    protected Boolean mWGone;
    public final SwitchButton sbRgb;
    public final SwitchSeekBarView seekbarBrt;
    public final RangeSeekBar seekbarCt;
    public final SwitchSeekBarView seekbarCwBrt;
    public final ColorSeekBar seekbarRgb;
    public final SwitchSeekBarView switchSeekbarCw;
    public final SwitchSeekBarView switchSeekbarW;
    public final AppCompatTextView tvColorTip;
    public final AppCompatTextView tvCtTip;
    public final AppCompatTextView tvCtValue;
    public final View viewDivider;

    public abstract void setCtGone(Boolean ctGone);

    public abstract void setCwGone(Boolean cwGone);

    public abstract void setRgbGone(Boolean rgbGone);

    public abstract void setWGone(Boolean wGone);

    protected ViewPowerStateBleAllBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView civColor, ConstraintLayout layoutPowerState, SwitchButton sbRgb, SwitchSeekBarView seekbarBrt, RangeSeekBar seekbarCt, SwitchSeekBarView seekbarCwBrt, ColorSeekBar seekbarRgb, SwitchSeekBarView switchSeekbarCw, SwitchSeekBarView switchSeekbarW, AppCompatTextView tvColorTip, AppCompatTextView tvCtTip, AppCompatTextView tvCtValue, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.civColor = civColor;
        this.layoutPowerState = layoutPowerState;
        this.sbRgb = sbRgb;
        this.seekbarBrt = seekbarBrt;
        this.seekbarCt = seekbarCt;
        this.seekbarCwBrt = seekbarCwBrt;
        this.seekbarRgb = seekbarRgb;
        this.switchSeekbarCw = switchSeekbarCw;
        this.switchSeekbarW = switchSeekbarW;
        this.tvColorTip = tvColorTip;
        this.tvCtTip = tvCtTip;
        this.tvCtValue = tvCtValue;
        this.viewDivider = viewDivider;
    }

    public Boolean getCtGone() {
        return this.mCtGone;
    }

    public Boolean getRgbGone() {
        return this.mRgbGone;
    }

    public Boolean getWGone() {
        return this.mWGone;
    }

    public Boolean getCwGone() {
        return this.mCwGone;
    }

    public static ViewPowerStateBleAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewPowerStateBleAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ViewPowerStateBleAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_power_state_ble_all, root, attachToRoot, component);
    }

    public static ViewPowerStateBleAllBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewPowerStateBleAllBinding inflate(LayoutInflater inflater, Object component) {
        return (ViewPowerStateBleAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.view_power_state_ble_all, null, false, component);
    }

    public static ViewPowerStateBleAllBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewPowerStateBleAllBinding bind(View view, Object component) {
        return (ViewPowerStateBleAllBinding) bind(component, view, R.layout.view_power_state_ble_all);
    }
}