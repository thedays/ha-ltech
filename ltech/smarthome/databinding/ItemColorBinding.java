package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemColorBinding extends ViewDataBinding {
    public final CircleImageView civColor;
    public final LinearLayout layoutItemBg;

    protected ItemColorBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView civColor, LinearLayout layoutItemBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.civColor = civColor;
        this.layoutItemBg = layoutItemBg;
    }

    public static ItemColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_color, root, attachToRoot, component);
    }

    public static ItemColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemColorBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_color, null, false, component);
    }

    public static ItemColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemColorBinding bind(View view, Object component) {
        return (ItemColorBinding) bind(component, view, R.layout.item_color);
    }
}