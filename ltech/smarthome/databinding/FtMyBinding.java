package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.control.FtMyVM;

/* loaded from: classes3.dex */
public abstract class FtMyBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView3;
    public final CardView cardview;
    public final AppCompatTextView homeNews;
    public final AppCompatImageView ivPhoto;

    @Bindable
    protected FtMyVM mViewmodel;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSub;

    public abstract void setViewmodel(FtMyVM viewmodel);

    protected FtMyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView3, CardView cardview, AppCompatTextView homeNews, AppCompatImageView ivPhoto, AppCompatTextView tvName, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView3 = appCompatImageView3;
        this.cardview = cardview;
        this.homeNews = homeNews;
        this.ivPhoto = ivPhoto;
        this.tvName = tvName;
        this.tvSub = tvSub;
    }

    public FtMyVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtMyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtMyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_my, root, attachToRoot, component);
    }

    public static FtMyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMyBinding inflate(LayoutInflater inflater, Object component) {
        return (FtMyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_my, null, false, component);
    }

    public static FtMyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMyBinding bind(View view, Object component) {
        return (FtMyBinding) bind(component, view, R.layout.ft_my);
    }
}