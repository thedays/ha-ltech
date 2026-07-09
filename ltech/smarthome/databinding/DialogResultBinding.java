package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class DialogResultBinding extends ViewDataBinding {
    public final AppCompatImageView ivResult;
    public final ConstraintLayout layoutBg;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvTitle;

    protected DialogResultBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivResult, ConstraintLayout layoutBg, AppCompatTextView tvTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivResult = ivResult;
        this.layoutBg = layoutBg;
        this.tvTip = tvTip;
        this.tvTitle = tvTitle;
    }

    public static DialogResultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogResultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogResultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_result, root, attachToRoot, component);
    }

    public static DialogResultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogResultBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogResultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_result, null, false, component);
    }

    public static DialogResultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogResultBinding bind(View view, Object component) {
        return (DialogResultBinding) bind(component, view, R.layout.dialog_result);
    }
}