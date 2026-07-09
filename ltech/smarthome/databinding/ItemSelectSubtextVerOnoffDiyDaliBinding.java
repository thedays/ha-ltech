package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView;
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public abstract class ItemSelectSubtextVerOnoffDiyDaliBinding extends ViewDataBinding {
    public final CgdActionSelectView cgdActionView;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItem;

    @Bindable
    protected SelectItem mItem;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;

    public abstract void setItem(SelectItem item);

    protected ItemSelectSubtextVerOnoffDiyDaliBinding(Object _bindingComponent, View _root, int _localFieldCount, CgdActionSelectView cgdActionView, AppCompatImageView ivSelect, ConstraintLayout layoutItem, AppCompatTextView tvName, AppCompatTextView tvSubName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cgdActionView = cgdActionView;
        this.ivSelect = ivSelect;
        this.layoutItem = layoutItem;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelectSubtextVerOnoffDiyDaliBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyDaliBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectSubtextVerOnoffDiyDaliBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver_onoff_diy_dali, root, attachToRoot, component);
    }

    public static ItemSelectSubtextVerOnoffDiyDaliBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyDaliBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectSubtextVerOnoffDiyDaliBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver_onoff_diy_dali, null, false, component);
    }

    public static ItemSelectSubtextVerOnoffDiyDaliBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyDaliBinding bind(View view, Object component) {
        return (ItemSelectSubtextVerOnoffDiyDaliBinding) bind(component, view, R.layout.item_select_subtext_ver_onoff_diy_dali);
    }
}