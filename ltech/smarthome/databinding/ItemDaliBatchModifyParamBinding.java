package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.CircularProgressView;

/* loaded from: classes3.dex */
public abstract class ItemDaliBatchModifyParamBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final CircularProgressView ivUpgradeWaiting;
    public final ContentLoadingProgressBar sendLoading;
    public final AppCompatTextView syncTv;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNum;
    public final AppCompatTextView tvPlaceInfo;

    protected ItemDaliBatchModifyParamBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, CircularProgressView ivUpgradeWaiting, ContentLoadingProgressBar sendLoading, AppCompatTextView syncTv, AppCompatTextView tvDeviceName, AppCompatTextView tvNum, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.ivUpgradeWaiting = ivUpgradeWaiting;
        this.sendLoading = sendLoading;
        this.syncTv = syncTv;
        this.tvDeviceName = tvDeviceName;
        this.tvNum = tvNum;
        this.tvPlaceInfo = tvPlaceInfo;
    }

    public static ItemDaliBatchModifyParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliBatchModifyParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDaliBatchModifyParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_dali_batch_modify_param, root, attachToRoot, component);
    }

    public static ItemDaliBatchModifyParamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliBatchModifyParamBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDaliBatchModifyParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_dali_batch_modify_param, null, false, component);
    }

    public static ItemDaliBatchModifyParamBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliBatchModifyParamBinding bind(View view, Object component) {
        return (ItemDaliBatchModifyParamBinding) bind(component, view, R.layout.item_dali_batch_modify_param);
    }
}