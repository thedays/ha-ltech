package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSearchBarBinding extends ViewDataBinding {
    public final AppCompatTextView cancelBtn;
    public final AppCompatImageView ivClean;
    public final LinearLayout layoutSearch;
    public final AppCompatEditText searchEdtTxt;

    protected ItemSearchBarBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView cancelBtn, AppCompatImageView ivClean, LinearLayout layoutSearch, AppCompatEditText searchEdtTxt) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cancelBtn = cancelBtn;
        this.ivClean = ivClean;
        this.layoutSearch = layoutSearch;
        this.searchEdtTxt = searchEdtTxt;
    }

    public static ItemSearchBarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSearchBarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_search_bar, root, attachToRoot, component);
    }

    public static ItemSearchBarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSearchBarBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_search_bar, null, false, component);
    }

    public static ItemSearchBarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarBinding bind(View view, Object component) {
        return (ItemSearchBarBinding) bind(component, view, R.layout.item_search_bar);
    }
}