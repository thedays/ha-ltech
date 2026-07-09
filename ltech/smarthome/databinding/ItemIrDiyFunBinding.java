package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemIrDiyFunBinding extends ViewDataBinding {
    public final AppCompatImageView ivAdd;
    public final RelativeLayout layoutBg;
    public final AppCompatTextView tvName;

    protected ItemIrDiyFunBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivAdd, RelativeLayout layoutBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivAdd = ivAdd;
        this.layoutBg = layoutBg;
        this.tvName = tvName;
    }

    public static ItemIrDiyFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrDiyFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemIrDiyFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ir_diy_fun, root, attachToRoot, component);
    }

    public static ItemIrDiyFunBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrDiyFunBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemIrDiyFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ir_diy_fun, null, false, component);
    }

    public static ItemIrDiyFunBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrDiyFunBinding bind(View view, Object component) {
        return (ItemIrDiyFunBinding) bind(component, view, R.layout.item_ir_diy_fun);
    }
}