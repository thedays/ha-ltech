package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemText36Binding extends ViewDataBinding {
    public final AppCompatTextView tvName;

    protected ItemText36Binding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvName = tvName;
    }

    public static ItemText36Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemText36Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemText36Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_36, root, attachToRoot, component);
    }

    public static ItemText36Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemText36Binding inflate(LayoutInflater inflater, Object component) {
        return (ItemText36Binding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_36, null, false, component);
    }

    public static ItemText36Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemText36Binding bind(View view, Object component) {
        return (ItemText36Binding) bind(component, view, R.layout.item_text_36);
    }
}