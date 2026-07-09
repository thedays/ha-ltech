package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemIntercomLogTitleBinding extends ViewDataBinding {
    public final AppCompatTextView tvTitle;

    protected ItemIntercomLogTitleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvTitle = tvTitle;
    }

    public static ItemIntercomLogTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogTitleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemIntercomLogTitleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_log_title, root, attachToRoot, component);
    }

    public static ItemIntercomLogTitleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogTitleBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemIntercomLogTitleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_log_title, null, false, component);
    }

    public static ItemIntercomLogTitleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogTitleBinding bind(View view, Object component) {
        return (ItemIntercomLogTitleBinding) bind(component, view, R.layout.item_intercom_log_title);
    }
}