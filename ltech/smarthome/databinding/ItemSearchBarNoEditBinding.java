package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSearchBarNoEditBinding extends ViewDataBinding {
    public final AppCompatTextView cancelBtn;
    public final AppCompatImageView ivClean;
    public final AppCompatTextView searchEdtTxt;
    public final LinearLayout searchLayout;

    protected ItemSearchBarNoEditBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView cancelBtn, AppCompatImageView ivClean, AppCompatTextView searchEdtTxt, LinearLayout searchLayout) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cancelBtn = cancelBtn;
        this.ivClean = ivClean;
        this.searchEdtTxt = searchEdtTxt;
        this.searchLayout = searchLayout;
    }

    public static ItemSearchBarNoEditBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarNoEditBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSearchBarNoEditBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_search_bar_no_edit, root, attachToRoot, component);
    }

    public static ItemSearchBarNoEditBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarNoEditBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSearchBarNoEditBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_search_bar_no_edit, null, false, component);
    }

    public static ItemSearchBarNoEditBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarNoEditBinding bind(View view, Object component) {
        return (ItemSearchBarNoEditBinding) bind(component, view, R.layout.item_search_bar_no_edit);
    }
}