package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemDiyModeBinding extends ViewDataBinding {
    public final CardView cardView;
    public final AppCompatImageView ivCover;
    public final AppCompatTextView tvEdit;
    public final AppCompatTextView tvModeName;

    protected ItemDiyModeBinding(Object _bindingComponent, View _root, int _localFieldCount, CardView cardView, AppCompatImageView ivCover, AppCompatTextView tvEdit, AppCompatTextView tvModeName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cardView = cardView;
        this.ivCover = ivCover;
        this.tvEdit = tvEdit;
        this.tvModeName = tvModeName;
    }

    public static ItemDiyModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDiyModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDiyModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_diy_mode, root, attachToRoot, component);
    }

    public static ItemDiyModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDiyModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDiyModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_diy_mode, null, false, component);
    }

    public static ItemDiyModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDiyModeBinding bind(View view, Object component) {
        return (ItemDiyModeBinding) bind(component, view, R.layout.item_diy_mode);
    }
}