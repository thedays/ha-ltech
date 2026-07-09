package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemNameValueBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView8;
    public final LinearLayout layoutItemBg;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvValue;

    protected ItemNameValueBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView8, LinearLayout layoutItemBg, AppCompatTextView tvName, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView8 = appCompatImageView8;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
        this.tvValue = tvValue;
    }

    public static ItemNameValueBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNameValueBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemNameValueBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_name_value, root, attachToRoot, component);
    }

    public static ItemNameValueBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNameValueBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemNameValueBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_name_value, null, false, component);
    }

    public static ItemNameValueBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemNameValueBinding bind(View view, Object component) {
        return (ItemNameValueBinding) bind(component, view, R.layout.item_name_value);
    }
}