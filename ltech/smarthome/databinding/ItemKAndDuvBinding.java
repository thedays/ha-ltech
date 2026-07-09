package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemKAndDuvBinding extends ViewDataBinding {
    public final AppCompatImageView iv4;
    public final AppCompatTextView tv1;
    public final AppCompatTextView tv2;
    public final AppCompatTextView tv3;
    public final AppCompatTextView tv4;

    protected ItemKAndDuvBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView iv4, AppCompatTextView tv1, AppCompatTextView tv2, AppCompatTextView tv3, AppCompatTextView tv4) {
        super(_bindingComponent, _root, _localFieldCount);
        this.iv4 = iv4;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
    }

    public static ItemKAndDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemKAndDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemKAndDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_k_and_duv, root, attachToRoot, component);
    }

    public static ItemKAndDuvBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemKAndDuvBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemKAndDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_k_and_duv, null, false, component);
    }

    public static ItemKAndDuvBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemKAndDuvBinding bind(View view, Object component) {
        return (ItemKAndDuvBinding) bind(component, view, R.layout.item_k_and_duv);
    }
}