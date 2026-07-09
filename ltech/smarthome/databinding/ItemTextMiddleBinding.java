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
public abstract class ItemTextMiddleBinding extends ViewDataBinding {

    @Bindable
    protected String mItem;

    @Bindable
    protected Integer mTextColor;
    public final AppCompatTextView tvName;

    public abstract void setItem(String item);

    public abstract void setTextColor(Integer textColor);

    protected ItemTextMiddleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvName = tvName;
    }

    public String getItem() {
        return this.mItem;
    }

    public Integer getTextColor() {
        return this.mTextColor;
    }

    public static ItemTextMiddleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextMiddleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTextMiddleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_middle, root, attachToRoot, component);
    }

    public static ItemTextMiddleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextMiddleBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTextMiddleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_middle, null, false, component);
    }

    public static ItemTextMiddleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextMiddleBinding bind(View view, Object component) {
        return (ItemTextMiddleBinding) bind(component, view, R.layout.item_text_middle);
    }
}