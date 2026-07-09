package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemColorPointBinding extends ViewDataBinding {
    public final CardView color;
    public final LinearLayout layoutItemBg;
    public final AppCompatTextView tvName;

    protected ItemColorPointBinding(Object _bindingComponent, View _root, int _localFieldCount, CardView color, LinearLayout layoutItemBg, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.color = color;
        this.layoutItemBg = layoutItemBg;
        this.tvName = tvName;
    }

    public static ItemColorPointBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemColorPointBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemColorPointBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_color_point, root, attachToRoot, component);
    }

    public static ItemColorPointBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemColorPointBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemColorPointBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_color_point, null, false, component);
    }

    public static ItemColorPointBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemColorPointBinding bind(View view, Object component) {
        return (ItemColorPointBinding) bind(component, view, R.layout.item_color_point);
    }
}