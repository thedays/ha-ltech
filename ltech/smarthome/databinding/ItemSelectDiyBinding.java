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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM;
import com.ltech.smarthome.ui.item.SelectItem;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ItemSelectDiyBinding extends ViewDataBinding {
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutChangeCurtainOpenPercent;

    @Bindable
    protected SelectItem mItem;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBleMotorModeSetVM mViewmodel;
    public final LightBrtBar sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvName;

    public abstract void setItem(SelectItem item);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBleMotorModeSetVM viewmodel);

    protected ItemSelectDiyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSelect, ConstraintLayout layoutChangeCurtainOpenPercent, LightBrtBar sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSelect = ivSelect;
        this.layoutChangeCurtainOpenPercent = layoutChangeCurtainOpenPercent;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvName = tvName;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public ActBleMotorModeSetVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ItemSelectDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_diy, root, attachToRoot, component);
    }

    public static ItemSelectDiyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDiyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_diy, null, false, component);
    }

    public static ItemSelectDiyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectDiyBinding bind(View view, Object component) {
        return (ItemSelectDiyBinding) bind(component, view, R.layout.item_select_diy);
    }
}