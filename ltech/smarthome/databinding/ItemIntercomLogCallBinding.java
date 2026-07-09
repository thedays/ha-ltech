package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.TimeLineView;

/* loaded from: classes3.dex */
public abstract class ItemIntercomLogCallBinding extends ViewDataBinding {
    public final TimeLineView line;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvStatus;
    public final AppCompatTextView tvTime;

    protected ItemIntercomLogCallBinding(Object _bindingComponent, View _root, int _localFieldCount, TimeLineView line, AppCompatTextView tvName, AppCompatTextView tvStatus, AppCompatTextView tvTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.line = line;
        this.tvName = tvName;
        this.tvStatus = tvStatus;
        this.tvTime = tvTime;
    }

    public static ItemIntercomLogCallBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogCallBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemIntercomLogCallBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_log_call, root, attachToRoot, component);
    }

    public static ItemIntercomLogCallBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogCallBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemIntercomLogCallBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_log_call, null, false, component);
    }

    public static ItemIntercomLogCallBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogCallBinding bind(View view, Object component) {
        return (ItemIntercomLogCallBinding) bind(component, view, R.layout.item_intercom_log_call);
    }
}