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
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ItemSelectAutomationBinding extends ViewDataBinding {
    public final AppCompatImageView ivBg;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;
    public final SwitchButton sbState;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvType;
    public final View view21;

    protected ItemSelectAutomationBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBg, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, SwitchButton sbState, AppCompatTextView tvName, AppCompatTextView tvType, View view21) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBg = ivBg;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.sbState = sbState;
        this.tvName = tvName;
        this.tvType = tvType;
        this.view21 = view21;
    }

    public static ItemSelectAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_automation, root, attachToRoot, component);
    }

    public static ItemSelectAutomationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectAutomationBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_automation, null, false, component);
    }

    public static ItemSelectAutomationBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectAutomationBinding bind(View view, Object component) {
        return (ItemSelectAutomationBinding) bind(component, view, R.layout.item_select_automation);
    }
}