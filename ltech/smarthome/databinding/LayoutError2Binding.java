package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class LayoutError2Binding extends ViewDataBinding {
    public final AppCompatButton errorRetryView;
    public final ConstraintLayout errorView;
    public final AppCompatTextView tvErrorTip;

    protected LayoutError2Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton errorRetryView, ConstraintLayout errorView, AppCompatTextView tvErrorTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.errorRetryView = errorRetryView;
        this.errorView = errorView;
        this.tvErrorTip = tvErrorTip;
    }

    public static LayoutError2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutError2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutError2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_error_2, root, attachToRoot, component);
    }

    public static LayoutError2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutError2Binding inflate(LayoutInflater inflater, Object component) {
        return (LayoutError2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_error_2, null, false, component);
    }

    public static LayoutError2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutError2Binding bind(View view, Object component) {
        return (LayoutError2Binding) bind(component, view, R.layout.layout_error_2);
    }
}