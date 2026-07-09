package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemBatchSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivMore;
    public final AppCompatImageView ivSelect;
    public final AppCompatTextView tvSub;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;

    protected ItemBatchSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivMore, AppCompatImageView ivSelect, AppCompatTextView tvSub, AppCompatTextView tvTitle, AppCompatTextView tvValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivMore = ivMore;
        this.ivSelect = ivSelect;
        this.tvSub = tvSub;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
    }

    public static ItemBatchSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBatchSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBatchSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_batch_setting, root, attachToRoot, component);
    }

    public static ItemBatchSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBatchSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemBatchSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_batch_setting, null, false, component);
    }

    public static ItemBatchSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBatchSettingBinding bind(View view, Object component) {
        return (ItemBatchSettingBinding) bind(component, view, R.layout.item_batch_setting);
    }
}