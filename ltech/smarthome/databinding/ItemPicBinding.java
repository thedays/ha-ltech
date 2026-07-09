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
import com.ltech.smarthome.ui.item.GoItem;

/* loaded from: classes3.dex */
public abstract class ItemPicBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;

    @Bindable
    protected Boolean mCircle;

    @Bindable
    protected Integer mErrorRes;

    @Bindable
    protected GoItem mItem;

    @Bindable
    protected Integer mPlaceHolderRes;
    public final AppCompatTextView tvMain;

    public abstract void setCircle(Boolean circle);

    public abstract void setErrorRes(Integer errorRes);

    public abstract void setItem(GoItem item);

    public abstract void setPlaceHolderRes(Integer placeHolderRes);

    protected ItemPicBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.tvMain = tvMain;
    }

    public GoItem getItem() {
        return this.mItem;
    }

    public Integer getErrorRes() {
        return this.mErrorRes;
    }

    public Integer getPlaceHolderRes() {
        return this.mPlaceHolderRes;
    }

    public Boolean getCircle() {
        return this.mCircle;
    }

    public static ItemPicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_pic, root, attachToRoot, component);
    }

    public static ItemPicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPicBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_pic, null, false, component);
    }

    public static ItemPicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPicBinding bind(View view, Object component) {
        return (ItemPicBinding) bind(component, view, R.layout.item_pic);
    }
}