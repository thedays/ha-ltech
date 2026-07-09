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
public abstract class LayoutTitleTranBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvEdit;
    public final AppCompatTextView tvTitle;

    public abstract void setTitle(TitleDefault title);

    protected LayoutTitleTranBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivEdit, AppCompatTextView tvEdit, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.tvEdit = tvEdit;
        this.tvTitle = tvTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static LayoutTitleTranBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleTranBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutTitleTranBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_title_tran, root, attachToRoot, component);
    }

    public static LayoutTitleTranBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleTranBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutTitleTranBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_title_tran, null, false, component);
    }

    public static LayoutTitleTranBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleTranBinding bind(View view, Object component) {
        return (LayoutTitleTranBinding) bind(component, view, R.layout.layout_title_tran);
    }
}