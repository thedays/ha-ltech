package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class DialogLightQuickBinding extends ViewDataBinding {
    public final CircleImageView civColor;
    public final ColorSeekBar csbColorBar;
    public final Group groupColor;
    public final Group groupCt;
    public final AppCompatImageView ivDeviceMore;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final SwitchButton sb;
    public final LightBrtBar sbBrt;
    public final RangeSeekBar sbCt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvColorTip;
    public final AppCompatTextView tvCtPercent;
    public final AppCompatTextView tvCtTip;
    public final AppCompatTextView tvName;
    public final View view16;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogLightQuickBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView civColor, ColorSeekBar csbColorBar, Group groupColor, Group groupCt, AppCompatImageView ivDeviceMore, SwitchButton sb, LightBrtBar sbBrt, RangeSeekBar sbCt, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvColorTip, AppCompatTextView tvCtPercent, AppCompatTextView tvCtTip, AppCompatTextView tvName, View view16) {
        super(_bindingComponent, _root, _localFieldCount);
        this.civColor = civColor;
        this.csbColorBar = csbColorBar;
        this.groupColor = groupColor;
        this.groupCt = groupCt;
        this.ivDeviceMore = ivDeviceMore;
        this.sb = sb;
        this.sbBrt = sbBrt;
        this.sbCt = sbCt;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvColorTip = tvColorTip;
        this.tvCtPercent = tvCtPercent;
        this.tvCtTip = tvCtTip;
        this.tvName = tvName;
        this.view16 = view16;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogLightQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLightQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogLightQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_light_quick, root, attachToRoot, component);
    }

    public static DialogLightQuickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLightQuickBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogLightQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_light_quick, null, false, component);
    }

    public static DialogLightQuickBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLightQuickBinding bind(View view, Object component) {
        return (DialogLightQuickBinding) bind(component, view, R.layout.dialog_light_quick);
    }
}