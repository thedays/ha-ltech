package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemCommandBinding extends ViewDataBinding {
    public final CardView cardTitle;
    public final AppCompatImageView ivMore;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvName;

    protected ItemCommandBinding(Object _bindingComponent, View _root, int _localFieldCount, CardView cardTitle, AppCompatImageView ivMore, RecyclerView rvContent, AppCompatTextView tvAdd, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cardTitle = cardTitle;
        this.ivMore = ivMore;
        this.rvContent = rvContent;
        this.tvAdd = tvAdd;
        this.tvName = tvName;
    }

    public static ItemCommandBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCommandBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCommandBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_command, root, attachToRoot, component);
    }

    public static ItemCommandBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCommandBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCommandBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_command, null, false, component);
    }

    public static ItemCommandBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCommandBinding bind(View view, Object component) {
        return (ItemCommandBinding) bind(component, view, R.layout.item_command);
    }
}