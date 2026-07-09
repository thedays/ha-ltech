package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class LayoutTitleIrBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView8;
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivTip;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvEdit;
    public final AppCompatTextView tvTitle;

    public abstract void setTitle(TitleDefault title);

    protected LayoutTitleIrBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView8, AppCompatImageView ivBack, AppCompatImageView ivEdit, AppCompatImageView ivTip, AppCompatTextView tvEdit, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView8 = appCompatTextView8;
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.ivTip = ivTip;
        this.tvEdit = tvEdit;
        this.tvTitle = tvTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static LayoutTitleIrBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleIrBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutTitleIrBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_title_ir, root, attachToRoot, component);
    }

    public static LayoutTitleIrBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleIrBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutTitleIrBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_title_ir, null, false, component);
    }

    public static LayoutTitleIrBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleIrBinding bind(View view, Object component) {
        return (LayoutTitleIrBinding) bind(component, view, R.layout.layout_title_ir);
    }
}