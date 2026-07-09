package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class TabText2Binding extends ViewDataBinding {
    public final AppCompatTextView tvTab;

    protected TabText2Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvTab) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvTab = tvTab;
    }

    public static TabText2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TabText2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (TabText2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.tab_text2, root, attachToRoot, component);
    }

    public static TabText2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TabText2Binding inflate(LayoutInflater inflater, Object component) {
        return (TabText2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.tab_text2, null, false, component);
    }

    public static TabText2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static TabText2Binding bind(View view, Object component) {
        return (TabText2Binding) bind(component, view, R.layout.tab_text2);
    }
}