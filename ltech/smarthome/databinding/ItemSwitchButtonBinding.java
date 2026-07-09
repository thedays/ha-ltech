package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ItemSwitchButtonBinding extends ViewDataBinding {
    public final SwitchButton sbFunction;
    public final AppCompatTextView title;
    public final AppCompatTextView tvSub;

    protected ItemSwitchButtonBinding(Object _bindingComponent, View _root, int _localFieldCount, SwitchButton sbFunction, AppCompatTextView title, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.sbFunction = sbFunction;
        this.title = title;
        this.tvSub = tvSub;
    }

    public static ItemSwitchButtonBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSwitchButtonBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSwitchButtonBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_switch_button, root, attachToRoot, component);
    }

    public static ItemSwitchButtonBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSwitchButtonBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSwitchButtonBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_switch_button, null, false, component);
    }

    public static ItemSwitchButtonBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSwitchButtonBinding bind(View view, Object component) {
        return (ItemSwitchButtonBinding) bind(component, view, R.layout.item_switch_button);
    }
}