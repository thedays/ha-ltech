package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.view.SmartSwitch;

/* loaded from: classes3.dex */
public abstract class ItemGroupLightBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView9;
    public final AppCompatTextView appCompatTextView15;
    public final AppCompatTextView appCompatTextView17;
    public final AppCompatTextView appCompatTextView18;
    public final LinearLayoutCompat bg;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivFavorite;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected SmartSwitch.OnUserCheckedChangeListener mCheckedChangeListener;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Group mGroup;

    @Bindable
    protected Boolean mHideMore;

    @Bindable
    protected Integer mIconRes;
    public final SmartSwitch sb;
    public final AppCompatTextView tvVirtual;
    public final View vFavorite;
    public final View view10;

    public abstract void setCheckedChangeListener(SmartSwitch.OnUserCheckedChangeListener checkedChangeListener);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setGroup(Group group);

    public abstract void setHideMore(Boolean hideMore);

    public abstract void setIconRes(Integer iconRes);

    protected ItemGroupLightBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView9, AppCompatTextView appCompatTextView15, AppCompatTextView appCompatTextView17, AppCompatTextView appCompatTextView18, LinearLayoutCompat bg, AppCompatImageView ivDeviceMore, AppCompatImageView ivFavorite, ConstraintLayout layoutItemBg, SmartSwitch sb, AppCompatTextView tvVirtual, View vFavorite, View view10) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView9 = appCompatImageView9;
        this.appCompatTextView15 = appCompatTextView15;
        this.appCompatTextView17 = appCompatTextView17;
        this.appCompatTextView18 = appCompatTextView18;
        this.bg = bg;
        this.ivDeviceMore = ivDeviceMore;
        this.ivFavorite = ivFavorite;
        this.layoutItemBg = layoutItemBg;
        this.sb = sb;
        this.tvVirtual = tvVirtual;
        this.vFavorite = vFavorite;
        this.view10 = view10;
    }

    public Group getGroup() {
        return this.mGroup;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public SmartSwitch.OnUserCheckedChangeListener getCheckedChangeListener() {
        return this.mCheckedChangeListener;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public Boolean getHideMore() {
        return this.mHideMore;
    }

    public static ItemGroupLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGroupLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_light, root, attachToRoot, component);
    }

    public static ItemGroupLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupLightBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemGroupLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_light, null, false, component);
    }

    public static ItemGroupLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupLightBinding bind(View view, Object component) {
        return (ItemGroupLightBinding) bind(component, view, R.layout.item_group_light);
    }
}