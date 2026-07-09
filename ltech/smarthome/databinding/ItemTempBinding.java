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
public abstract class ItemTempBinding extends ViewDataBinding {
    public final LinearLayout layoutBg;
    public final AppCompatTextView tvTemp;

    protected ItemTempBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBg, AppCompatTextView tvTemp) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBg = layoutBg;
        this.tvTemp = tvTemp;
    }

    public static ItemTempBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTempBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTempBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_temp, root, attachToRoot, component);
    }

    public static ItemTempBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTempBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTempBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_temp, null, false, component);
    }

    public static ItemTempBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTempBinding bind(View view, Object component) {
        return (ItemTempBinding) bind(component, view, R.layout.item_temp);
    }
}