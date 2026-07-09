package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.TimeProgressBar;

/* loaded from: classes3.dex */
public abstract class ActDeviceConfigFailBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView10;
    public final AppCompatButton btConfigAgain;
    public final ConstraintLayout clLayout;
    public final View guidLayout;
    public final AppCompatImageView ivConfigTimeOut;
    public final AppCompatImageView ivFail;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final TimeProgressBar timeBar;
    public final AppCompatTextView tvConfigTip;
    public final AppCompatTextView tvFailTip1;
    public final AppCompatTextView tvFailTip2;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActDeviceConfigFailBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView10, AppCompatButton btConfigAgain, ConstraintLayout clLayout, View guidLayout, AppCompatImageView ivConfigTimeOut, AppCompatImageView ivFail, TimeProgressBar timeBar, AppCompatTextView tvConfigTip, AppCompatTextView tvFailTip1, AppCompatTextView tvFailTip2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView10 = appCompatTextView10;
        this.btConfigAgain = btConfigAgain;
        this.clLayout = clLayout;
        this.guidLayout = guidLayout;
        this.ivConfigTimeOut = ivConfigTimeOut;
        this.ivFail = ivFail;
        this.timeBar = timeBar;
        this.tvConfigTip = tvConfigTip;
        this.tvFailTip1 = tvFailTip1;
        this.tvFailTip2 = tvFailTip2;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActDeviceConfigFailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConfigFailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDeviceConfigFailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_config_fail, root, attachToRoot, component);
    }

    public static ActDeviceConfigFailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConfigFailBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDeviceConfigFailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_config_fail, null, false, component);
    }

    public static ActDeviceConfigFailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConfigFailBinding bind(View view, Object component) {
        return (ActDeviceConfigFailBinding) bind(component, view, R.layout.act_device_config_fail);
    }
}