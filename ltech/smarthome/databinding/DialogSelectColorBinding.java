package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.ColorPickerView;
import com.ltech.smarthome.view.HorizontalSeekBar;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class DialogSelectColorBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView26;
    public final ColorPickerView cpv;
    public final ColorEditText etColor1;
    public final ColorEditText etColor2;
    public final ColorEditText etColor3;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvColor;
    public final LightBrtBar sbBrt;
    public final HorizontalSeekBar sbW;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvColor1;
    public final AppCompatTextView tvColor2;
    public final AppCompatTextView tvColor3;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvWTip;
    public final View vColor;
    public final View view19;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectColorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView26, ColorPickerView cpv, ColorEditText etColor1, ColorEditText etColor2, ColorEditText etColor3, RecyclerView rvColor, LightBrtBar sbBrt, HorizontalSeekBar sbW, AppCompatTextView tvBrtTip, AppCompatTextView tvCancel, AppCompatTextView tvColor1, AppCompatTextView tvColor2, AppCompatTextView tvColor3, AppCompatTextView tvConfirm, AppCompatTextView tvWTip, View vColor, View view19) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView26 = appCompatTextView26;
        this.cpv = cpv;
        this.etColor1 = etColor1;
        this.etColor2 = etColor2;
        this.etColor3 = etColor3;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.sbW = sbW;
        this.tvBrtTip = tvBrtTip;
        this.tvCancel = tvCancel;
        this.tvColor1 = tvColor1;
        this.tvColor2 = tvColor2;
        this.tvColor3 = tvColor3;
        this.tvConfirm = tvConfirm;
        this.tvWTip = tvWTip;
        this.vColor = vColor;
        this.view19 = view19;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_color, root, attachToRoot, component);
    }

    public static DialogSelectColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectColorBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_color, null, false, component);
    }

    public static DialogSelectColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectColorBinding bind(View view, Object component) {
        return (DialogSelectColorBinding) bind(component, view, R.layout.dialog_select_color);
    }
}