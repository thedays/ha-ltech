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
public abstract class ItemRelateInfoBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView8;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivNum;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvActionInfo;
    public final AppCompatTextView tvDeviceName;

    protected ItemRelateInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView8, AppCompatImageView ivIcon, AppCompatImageView ivNum, ConstraintLayout layoutItemBg, AppCompatTextView tvActionInfo, AppCompatTextView tvDeviceName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView8 = appCompatImageView8;
        this.ivIcon = ivIcon;
        this.ivNum = ivNum;
        this.layoutItemBg = layoutItemBg;
        this.tvActionInfo = tvActionInfo;
        this.tvDeviceName = tvDeviceName;
    }

    public static ItemRelateInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRelateInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemRelateInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_relate_info, root, attachToRoot, component);
    }

    public static ItemRelateInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRelateInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemRelateInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_relate_info, null, false, component);
    }

    public static ItemRelateInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemRelateInfoBinding bind(View view, Object component) {
        return (ItemRelateInfoBinding) bind(component, view, R.layout.item_relate_info);
    }
}