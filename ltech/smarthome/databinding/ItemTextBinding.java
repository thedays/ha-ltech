package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemTextBinding extends ViewDataBinding {

    @Bindable
    protected String mItem;

    @Bindable
    protected Integer mTextColor;
    public final AppCompatTextView tvName;

    public abstract void setItem(String item);

    public abstract void setTextColor(Integer textColor);

    protected ItemTextBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvName = tvName;
    }

    public String getItem() {
        return this.mItem;
    }

    public Integer getTextColor() {
        return this.mTextColor;
    }

    public static ItemTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text, root, attachToRoot, component);
    }

    public static ItemTextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTextBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text, null, false, component);
    }

    public static ItemTextBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextBinding bind(View view, Object component) {
        return (ItemTextBinding) bind(component, view, R.layout.item_text);
    }
}