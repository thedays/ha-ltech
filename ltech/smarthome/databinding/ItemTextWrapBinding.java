package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemTextWrapBinding extends ViewDataBinding {
    public final AppCompatTextView tvContent;

    protected ItemTextWrapBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvContent = tvContent;
    }

    public static ItemTextWrapBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextWrapBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTextWrapBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_wrap, root, attachToRoot, component);
    }

    public static ItemTextWrapBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextWrapBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTextWrapBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_wrap, null, false, component);
    }

    public static ItemTextWrapBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextWrapBinding bind(View view, Object component) {
        return (ItemTextWrapBinding) bind(component, view, R.layout.item_text_wrap);
    }
}