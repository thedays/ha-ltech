package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemIntercomDoorBinding extends ViewDataBinding {
    public final Guideline guideline;
    public final AppCompatTextView intercomName;
    public final LinearLayout layoutMonitor;
    public final ConstraintLayout layoutNormal;
    public final LinearLayout layoutOpen;

    protected ItemIntercomDoorBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline, AppCompatTextView intercomName, LinearLayout layoutMonitor, ConstraintLayout layoutNormal, LinearLayout layoutOpen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline = guideline;
        this.intercomName = intercomName;
        this.layoutMonitor = layoutMonitor;
        this.layoutNormal = layoutNormal;
        this.layoutOpen = layoutOpen;
    }

    public static ItemIntercomDoorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomDoorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemIntercomDoorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_door, root, attachToRoot, component);
    }

    public static ItemIntercomDoorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomDoorBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemIntercomDoorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_intercom_door, null, false, component);
    }

    public static ItemIntercomDoorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIntercomDoorBinding bind(View view, Object component) {
        return (ItemIntercomDoorBinding) bind(component, view, R.layout.item_intercom_door);
    }
}