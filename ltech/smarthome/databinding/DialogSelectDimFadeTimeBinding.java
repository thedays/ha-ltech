package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.DaliTextSeekBarViewNew;

/* loaded from: classes3.dex */
public abstract class DialogSelectDimFadeTimeBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView33;
    public final ConstraintLayout layoutBg;
    public final LinearLayout layoutExtendFadeTime;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final DaliTextSeekBarViewNew seekbarFadeTime;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvExtendFadeTime;
    public final AppCompatTextView tvFadeTimeEqual;
    public final AppCompatTextView tvFadeTimeTimes;
    public final AppCompatTextView tvFadeTimeTotal;
    public final AppCompatTextView tvFadeTimeValue;
    public final AppCompatTextView tvFadeTimeX;
    public final AppCompatTextView tvSave;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogSelectDimFadeTimeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView33, ConstraintLayout layoutBg, LinearLayout layoutExtendFadeTime, DaliTextSeekBarViewNew seekbarFadeTime, AppCompatTextView tvCancel, AppCompatTextView tvExtendFadeTime, AppCompatTextView tvFadeTimeEqual, AppCompatTextView tvFadeTimeTimes, AppCompatTextView tvFadeTimeTotal, AppCompatTextView tvFadeTimeValue, AppCompatTextView tvFadeTimeX, AppCompatTextView tvSave) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView33 = appCompatTextView33;
        this.layoutBg = layoutBg;
        this.layoutExtendFadeTime = layoutExtendFadeTime;
        this.seekbarFadeTime = seekbarFadeTime;
        this.tvCancel = tvCancel;
        this.tvExtendFadeTime = tvExtendFadeTime;
        this.tvFadeTimeEqual = tvFadeTimeEqual;
        this.tvFadeTimeTimes = tvFadeTimeTimes;
        this.tvFadeTimeTotal = tvFadeTimeTotal;
        this.tvFadeTimeValue = tvFadeTimeValue;
        this.tvFadeTimeX = tvFadeTimeX;
        this.tvSave = tvSave;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectDimFadeTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimFadeTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectDimFadeTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_dim_fade_time, root, attachToRoot, component);
    }

    public static DialogSelectDimFadeTimeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimFadeTimeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectDimFadeTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_dim_fade_time, null, false, component);
    }

    public static DialogSelectDimFadeTimeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectDimFadeTimeBinding bind(View view, Object component) {
        return (DialogSelectDimFadeTimeBinding) bind(component, view, R.layout.dialog_select_dim_fade_time);
    }
}