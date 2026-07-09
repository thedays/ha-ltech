package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemCategoryBinding extends ViewDataBinding {
    public final RelativeLayout layoutBg;
    public final AppCompatTextView tvContent;

    protected ItemCategoryBinding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout layoutBg, AppCompatTextView tvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBg = layoutBg;
        this.tvContent = tvContent;
    }

    public static ItemCategoryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCategoryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCategoryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_category, root, attachToRoot, component);
    }

    public static ItemCategoryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCategoryBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCategoryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_category, null, false, component);
    }

    public static ItemCategoryBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCategoryBinding bind(View view, Object component) {
        return (ItemCategoryBinding) bind(component, view, R.layout.item_category);
    }
}