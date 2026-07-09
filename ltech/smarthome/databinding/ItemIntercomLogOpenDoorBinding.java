package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.TimeLineView;

/* loaded from: classes3.dex */
public abstract class ItemIntercomLogOpenDoorBinding extends ViewDataBinding {
    public final AppCompatImageView ivCapture;
    public final AppCompatImageView ivGo;
    public final TimeLineView line;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvStatus;
    public final AppCompatTextView tvTime;

    protected ItemIntercomLogOpenDoorBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivCapture, AppCompatImageView ivGo, TimeLineView line, AppCompatTextView tvName, AppCompatTextView tvStatus, AppCompatTextView tvTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivCapture = ivCapture;
        this.ivGo = ivGo;
        this.line = line;
        this.tvName = tvName;
        this.tvStatus = tvStatus;
        this.tvTime = tvTime;
    }

    public static ItemIntercomLogOpenDoorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogOpenDoorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemIntercomLogOpenDoorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_log_open_door, root, attachToRoot, component);
    }

    public static ItemIntercomLogOpenDoorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogOpenDoorBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemIntercomLogOpenDoorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_log_open_door, null, false, component);
    }

    public static ItemIntercomLogOpenDoorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomLogOpenDoorBinding bind(View view, Object component) {
        return (ItemIntercomLogOpenDoorBinding) bind(component, view, R.layout.item_intercom_log_open_door);
    }
}