package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.GoItem;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public abstract class DialogListBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;

    @Bindable
    protected GoItem mItem;

    @Bindable
    protected ItemBinding<GoItem> mItemBinding;

    @Bindable
    protected ObservableList<GoItem> mList;
    public final RecyclerView rvContent;

    public abstract void setItem(GoItem item);

    public abstract void setItemBinding(ItemBinding<GoItem> itemBinding);

    public abstract void setList(ObservableList<GoItem> list);

    protected DialogListBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.rvContent = rvContent;
    }

    public ObservableList<GoItem> getList() {
        return this.mList;
    }

    public ItemBinding<GoItem> getItemBinding() {
        return this.mItemBinding;
    }

    public GoItem getItem() {
        return this.mItem;
    }

    public static DialogListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_list, root, attachToRoot, component);
    }

    public static DialogListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogListBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_list, null, false, component);
    }

    public static DialogListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogListBinding bind(View view, Object component) {
        return (DialogListBinding) bind(component, view, R.layout.dialog_list);
    }
}