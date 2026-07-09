package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSelectCoverBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;
    public final View vPic;
    public final View vSelect;

    protected ItemSelectCoverBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg, View vPic, View vSelect) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
        this.vPic = vPic;
        this.vSelect = vSelect;
    }

    public static ItemSelectCoverBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectCoverBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectCoverBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_cover, root, attachToRoot, component);
    }

    public static ItemSelectCoverBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectCoverBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectCoverBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_cover, null, false, component);
    }

    public static ItemSelectCoverBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectCoverBinding bind(View view, Object component) {
        return (ItemSelectCoverBinding) bind(component, view, R.layout.item_select_cover);
    }
}