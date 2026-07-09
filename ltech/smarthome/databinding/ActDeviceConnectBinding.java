package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActDeviceConnectBinding extends ViewDataBinding {
    public final AppCompatButton btNext;
    public final AppCompatImageView ivConfigTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvConfigTip1;
    public final AppCompatTextView tvConfigTip2;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActDeviceConnectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btNext, AppCompatImageView ivConfigTip, AppCompatTextView tvConfigTip1, AppCompatTextView tvConfigTip2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.ivConfigTip = ivConfigTip;
        this.tvConfigTip1 = tvConfigTip1;
        this.tvConfigTip2 = tvConfigTip2;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActDeviceConnectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConnectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDeviceConnectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_connect, root, attachToRoot, component);
    }

    public static ActDeviceConnectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConnectBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDeviceConnectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_connect, null, false, component);
    }

    public static ActDeviceConnectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConnectBinding bind(View view, Object component) {
        return (ActDeviceConnectBinding) bind(component, view, R.layout.act_device_connect);
    }
}