package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemIrDiyKeyBinding extends ViewDataBinding {
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvName;

    protected ItemIrDiyKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBg = layoutBg;
        this.tvName = tvName;
    }

    public static ItemIrDiyKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrDiyKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemIrDiyKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ir_diy_key, root, attachToRoot, component);
    }

    public static ItemIrDiyKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrDiyKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemIrDiyKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_ir_diy_key, null, false, component);
    }

    public static ItemIrDiyKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemIrDiyKeyBinding bind(View view, Object component) {
        return (ItemIrDiyKeyBinding) bind(component, view, R.layout.item_ir_diy_key);
    }
}