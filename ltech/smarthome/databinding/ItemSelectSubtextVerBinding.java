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
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public abstract class ItemSelectSubtextVerBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;

    @Bindable
    protected SelectItem mItem;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;

    public abstract void setItem(SelectItem item);

    protected ItemSelectSubtextVerBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, AppCompatTextView tvName, AppCompatTextView tvSubName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelectSubtextVerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectSubtextVerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver, root, attachToRoot, component);
    }

    public static ItemSelectSubtextVerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectSubtextVerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver, null, false, component);
    }

    public static ItemSelectSubtextVerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerBinding bind(View view, Object component) {
        return (ItemSelectSubtextVerBinding) bind(component, view, R.layout.item_select_subtext_ver);
    }
}