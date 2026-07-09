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
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public abstract class ItemSelectOnOffTimeDiyBinding extends ViewDataBinding {
    public final AppCompatImageView ivElecSelect;
    public final AppCompatImageView ivOffSelect;
    public final AppCompatImageView ivOnSelect;
    public final AppCompatImageView ivSceneSelect;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutElec;
    public final ConstraintLayout layoutOff;
    public final ConstraintLayout layoutOn;
    public final ConstraintLayout layoutScene;

    @Bindable
    protected SelectItem mItem;
    public final AppCompatTextView tvElecName;
    public final AppCompatTextView tvElecSubName;
    public final AppCompatTextView tvElecTitleName;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvOffName;
    public final AppCompatTextView tvOffSubName;
    public final AppCompatTextView tvOffTitleName;
    public final AppCompatTextView tvOnName;
    public final AppCompatTextView tvOnSubName;
    public final AppCompatTextView tvOnTitleName;
    public final AppCompatTextView tvSceneName;
    public final AppCompatTextView tvSceneSubName;
    public final AppCompatTextView tvSceneTitleName;

    public abstract void setItem(SelectItem item);

    protected ItemSelectOnOffTimeDiyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivElecSelect, AppCompatImageView ivOffSelect, AppCompatImageView ivOnSelect, AppCompatImageView ivSceneSelect, AppCompatImageView ivSelect, ConstraintLayout layoutElec, ConstraintLayout layoutOff, ConstraintLayout layoutOn, ConstraintLayout layoutScene, AppCompatTextView tvElecName, AppCompatTextView tvElecSubName, AppCompatTextView tvElecTitleName, AppCompatTextView tvName, AppCompatTextView tvOffName, AppCompatTextView tvOffSubName, AppCompatTextView tvOffTitleName, AppCompatTextView tvOnName, AppCompatTextView tvOnSubName, AppCompatTextView tvOnTitleName, AppCompatTextView tvSceneName, AppCompatTextView tvSceneSubName, AppCompatTextView tvSceneTitleName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivElecSelect = ivElecSelect;
        this.ivOffSelect = ivOffSelect;
        this.ivOnSelect = ivOnSelect;
        this.ivSceneSelect = ivSceneSelect;
        this.ivSelect = ivSelect;
        this.layoutElec = layoutElec;
        this.layoutOff = layoutOff;
        this.layoutOn = layoutOn;
        this.layoutScene = layoutScene;
        this.tvElecName = tvElecName;
        this.tvElecSubName = tvElecSubName;
        this.tvElecTitleName = tvElecTitleName;
        this.tvName = tvName;
        this.tvOffName = tvOffName;
        this.tvOffSubName = tvOffSubName;
        this.tvOffTitleName = tvOffTitleName;
        this.tvOnName = tvOnName;
        this.tvOnSubName = tvOnSubName;
        this.tvOnTitleName = tvOnTitleName;
        this.tvSceneName = tvSceneName;
        this.tvSceneSubName = tvSceneSubName;
        this.tvSceneTitleName = tvSceneTitleName;
    }

    public SelectItem getItem() {
        return this.mItem;
    }

    public static ItemSelectOnOffTimeDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectOnOffTimeDiyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectOnOffTimeDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_on_off_time_diy, root, attachToRoot, component);
    }

    public static ItemSelectOnOffTimeDiyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectOnOffTimeDiyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectOnOffTimeDiyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_on_off_time_diy, null, false, component);
    }

    public static ItemSelectOnOffTimeDiyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectOnOffTimeDiyBinding bind(View view, Object component) {
        return (ItemSelectOnOffTimeDiyBinding) bind(component, view, R.layout.item_select_on_off_time_diy);
    }
}