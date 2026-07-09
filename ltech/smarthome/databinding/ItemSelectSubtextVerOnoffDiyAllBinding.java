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
public abstract class ItemSelectSubtextVerOnoffDiyAllBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItem;
    public final ViewPowerStateBleAllBinding layoutState;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvSubName;

    protected ItemSelectSubtextVerOnoffDiyAllBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, ConstraintLayout layoutItem, ViewPowerStateBleAllBinding layoutState, AppCompatTextView tvName, AppCompatTextView tvSubName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.layoutItem = layoutItem;
        this.layoutState = layoutState;
        this.tvName = tvName;
        this.tvSubName = tvSubName;
    }

    public static ItemSelectSubtextVerOnoffDiyAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectSubtextVerOnoffDiyAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver_onoff_diy_all, root, attachToRoot, component);
    }

    public static ItemSelectSubtextVerOnoffDiyAllBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyAllBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectSubtextVerOnoffDiyAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_subtext_ver_onoff_diy_all, null, false, component);
    }

    public static ItemSelectSubtextVerOnoffDiyAllBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectSubtextVerOnoffDiyAllBinding bind(View view, Object component) {
        return (ItemSelectSubtextVerOnoffDiyAllBinding) bind(component, view, R.layout.item_select_subtext_ver_onoff_diy_all);
    }
}