package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.SelectItem;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ItemSelectSubtextVerOnoffDiyBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final LinearLayout layoutBrt;
    public final ConstraintLayout layoutItem;

    @Bindable
    protected SelectItem mItem;
    public final LightBrtBar sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;

    public abstract void setItem(SelectItem item);

    protected ItemSelectSubtextVerOnoffDiyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, LinearLayout layoutBrt, ConstraintLayout layoutItem, LightBrtBar sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvName, AppCompatTextView tvSubName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.layoutBrt = layoutBrt;
        this.layoutItem = layoutItem;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelectSubtextVerOnoffDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectSubtextVerOnoffDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver_onoff_diy, root, attachToRoot, component);
    }

    public static ItemSelectSubtextVerOnoffDiyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectSubtextVerOnoffDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver_onoff_diy, null, false, component);
    }

    public static ItemSelectSubtextVerOnoffDiyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyBinding bind(View view, Object component) {
        return (ItemSelectSubtextVerOnoffDiyBinding) bind(component, view, R.layout.item_select_subtext_ver_onoff_diy);
    }
}