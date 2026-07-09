package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.DonutProgress;

/* loaded from: classes3.dex */
public abstract class DialogLoadingProgressBinding extends ViewDataBinding {
    public final DonutProgress donutProgress;
    public final RelativeLayout layoutCancel;
    public final AppCompatTextView tvContent;

    protected DialogLoadingProgressBinding(Object _bindingComponent, View _root, int _localFieldCount, DonutProgress donutProgress, RelativeLayout layoutCancel, AppCompatTextView tvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.donutProgress = donutProgress;
        this.layoutCancel = layoutCancel;
        this.tvContent = tvContent;
    }

    public static DialogLoadingProgressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingProgressBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogLoadingProgressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_loading_progress, root, attachToRoot, component);
    }

    public static DialogLoadingProgressBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingProgressBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogLoadingProgressBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_loading_progress, null, false, component);
    }

    public static DialogLoadingProgressBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogLoadingProgressBinding bind(View view, Object component) {
        return (DialogLoadingProgressBinding) bind(component, view, R.layout.dialog_loading_progress);
    }
}