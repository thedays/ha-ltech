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
public abstract class ItemEditDiyKeyNameBinding extends ViewDataBinding {
    public final AppCompatImageView ivEdit;
    public final ConstraintLayout layoutBg;
    public final AppCompatTextView tvName;

    protected ItemEditDiyKeyNameBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEdit, ConstraintLayout layoutBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEdit = ivEdit;
        this.layoutBg = layoutBg;
        this.tvName = tvName;
    }

    public static ItemEditDiyKeyNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEditDiyKeyNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemEditDiyKeyNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_edit_diy_key_name, root, attachToRoot, component);
    }

    public static ItemEditDiyKeyNameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEditDiyKeyNameBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemEditDiyKeyNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_edit_diy_key_name, null, false, component);
    }

    public static ItemEditDiyKeyNameBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemEditDiyKeyNameBinding bind(View view, Object component) {
        return (ItemEditDiyKeyNameBinding) bind(component, view, R.layout.item_edit_diy_key_name);
    }
}