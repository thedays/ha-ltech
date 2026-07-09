package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ItemDaliManageBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNum;
    public final AppCompatTextView tvPlaceInfo;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ItemDaliManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvDeviceName, AppCompatTextView tvNum, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvDeviceName = tvDeviceName;
        this.tvNum = tvNum;
        this.tvPlaceInfo = tvPlaceInfo;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemDaliManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemDaliManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_dali_manage, root, attachToRoot, component);
    }

    public static ItemDaliManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemDaliManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_dali_manage, null, false, component);
    }

    public static ItemDaliManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemDaliManageBinding bind(View view, Object component) {
        return (ItemDaliManageBinding) bind(component, view, R.layout.item_dali_manage);
    }
}