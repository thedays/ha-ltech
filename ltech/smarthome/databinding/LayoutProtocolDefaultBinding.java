package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.extra.ProtocolDefault;

/* loaded from: classes3.dex */
public abstract class LayoutProtocolDefaultBinding extends ViewDataBinding {
    public final CheckBox checkBox;

    @Bindable
    protected ProtocolDefault mProtocol;
    public final TextView tvProtocolText;

    public abstract void setProtocol(ProtocolDefault protocol);

    protected LayoutProtocolDefaultBinding(Object _bindingComponent, View _root, int _localFieldCount, CheckBox checkBox, TextView tvProtocolText) {
        super(_bindingComponent, _root, _localFieldCount);
        this.checkBox = checkBox;
        this.tvProtocolText = tvProtocolText;
    }

    public ProtocolDefault getProtocol() {
        return this.mProtocol;
    }

    public static LayoutProtocolDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutProtocolDefaultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutProtocolDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_protocol_default, root, attachToRoot, component);
    }

    public static LayoutProtocolDefaultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutProtocolDefaultBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutProtocolDefaultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_protocol_default, null, false, component);
    }

    public static LayoutProtocolDefaultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutProtocolDefaultBinding bind(View view, Object component) {
        return (LayoutProtocolDefaultBinding) bind(component, view, R.layout.layout_protocol_default);
    }
}