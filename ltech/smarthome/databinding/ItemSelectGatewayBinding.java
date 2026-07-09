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
public abstract class ItemSelectGatewayBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivIconBg;
    public final AppCompatImageView ivTick;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvContent;
    public final AppCompatTextView tvName;

    protected ItemSelectGatewayBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, AppCompatImageView ivIconBg, AppCompatImageView ivTick, ConstraintLayout layoutItemBg, AppCompatTextView tvContent, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.ivIconBg = ivIconBg;
        this.ivTick = ivTick;
        this.layoutItemBg = layoutItemBg;
        this.tvContent = tvContent;
        this.tvName = tvName;
    }

    public static ItemSelectGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectGatewayBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_gateway, root, attachToRoot, component);
    }

    public static ItemSelectGatewayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectGatewayBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectGatewayBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_gateway, null, false, component);
    }

    public static ItemSelectGatewayBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectGatewayBinding bind(View view, Object component) {
        return (ItemSelectGatewayBinding) bind(component, view, R.layout.item_select_gateway);
    }
}