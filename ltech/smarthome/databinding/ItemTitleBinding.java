package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemTitleBinding extends ViewDataBinding {
    public final AppCompatTextView tvMain;

    protected ItemTitleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvMain = tvMain;
    }

    public static ItemTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTitleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_title, root, attachToRoot, component);
    }

    public static ItemTitleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTitleBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTitleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_title, null, false, component);
    }

    public static ItemTitleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTitleBinding bind(View view, Object component) {
        return (ItemTitleBinding) bind(component, view, R.layout.item_title);
    }
}