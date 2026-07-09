package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class TabTextBinding extends ViewDataBinding {
    public final AppCompatTextView tvTab;

    protected TabTextBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvTab) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvTab = tvTab;
    }

    public static TabTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TabTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (TabTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.tab_text, root, attachToRoot, component);
    }

    public static TabTextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TabTextBinding inflate(LayoutInflater inflater, Object component) {
        return (TabTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.tab_text, null, false, component);
    }

    public static TabTextBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TabTextBinding bind(View view, Object component) {
        return (TabTextBinding) bind(component, view, R.layout.tab_text);
    }
}