package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActDeviceConnectAndroid10Binding extends ViewDataBinding {
    public final View guidLayout;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActDeviceConnectAndroid10Binding(Object _bindingComponent, View _root, int _localFieldCount, View guidLayout) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guidLayout = guidLayout;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActDeviceConnectAndroid10Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConnectAndroid10Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDeviceConnectAndroid10Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_connect_android10, root, attachToRoot, component);
    }

    public static ActDeviceConnectAndroid10Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConnectAndroid10Binding inflate(LayoutInflater inflater, Object component) {
        return (ActDeviceConnectAndroid10Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_connect_android10, null, false, component);
    }

    public static ActDeviceConnectAndroid10Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceConnectAndroid10Binding bind(View view, Object component) {
        return (ActDeviceConnectAndroid10Binding) bind(component, view, R.layout.act_device_connect_android10);
    }
}