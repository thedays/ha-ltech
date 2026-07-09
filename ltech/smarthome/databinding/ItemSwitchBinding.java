package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.SwitchItem;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ItemSwitchBinding extends ViewDataBinding {
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected SwitchButton.OnCheckedChangeListener mCheckedChangeListener;

    @Bindable
    protected SwitchItem mItem;
    public final SwitchButton sbTalk;
    public final AppCompatTextView tvMain;

    public abstract void setCheckedChangeListener(SwitchButton.OnCheckedChangeListener checkedChangeListener);

    public abstract void setItem(SwitchItem item);

    protected ItemSwitchBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutItemBg, SwitchButton sbTalk, AppCompatTextView tvMain) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutItemBg = layoutItemBg;
        this.sbTalk = sbTalk;
        this.tvMain = tvMain;
    }

    public SwitchItem getItem() {
        return this.mItem;
    }

    public SwitchButton.OnCheckedChangeListener getCheckedChangeListener() {
        return this.mCheckedChangeListener;
    }

    public static ItemSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSwitchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_switch, root, attachToRoot, component);
    }

    public static ItemSwitchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSwitchBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSwitchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_switch, null, false, component);
    }

    public static ItemSwitchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSwitchBinding bind(View view, Object component) {
        return (ItemSwitchBinding) bind(component, view, R.layout.item_switch);
    }
}