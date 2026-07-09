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
public abstract class ItemGroupSmartPanelBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView9;
    public final AppCompatTextView appCompatTextView15;
    public final AppCompatTextView appCompatTextView17;
    public final LinearLayoutCompat bg;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivFavorite;
    public final SmartSwitch ivSwitch;
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
    protected Boolean mHideSwitch;

    @Bindable
    protected Integer mIconRes;
    public final View vFavorite;
    public final View view10;

    public abstract void setCheckedChangeListener(SmartSwitch.OnUserCheckedChangeListener checkedChangeListener);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setGroup(Group group);

    public abstract void setHideMore(Boolean hideMore);

    public abstract void setHideSwitch(Boolean hideSwitch);

    public abstract void setIconRes(Integer iconRes);

    protected ItemGroupSmartPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView9, AppCompatTextView appCompatTextView15, AppCompatTextView appCompatTextView17, LinearLayoutCompat bg, AppCompatImageView ivDeviceMore, AppCompatImageView ivFavorite, SmartSwitch ivSwitch, ConstraintLayout layoutItemBg, View vFavorite, View view10) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView9 = appCompatImageView9;
        this.appCompatTextView15 = appCompatTextView15;
        this.appCompatTextView17 = appCompatTextView17;
        this.bg = bg;
        this.ivDeviceMore = ivDeviceMore;
        this.ivFavorite = ivFavorite;
        this.ivSwitch = ivSwitch;
        this.layoutItemBg = layoutItemBg;
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

    public Boolean getHideSwitch() {
        return this.mHideSwitch;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public Boolean getHideMore() {
        return this.mHideMore;
    }

    public static ItemGroupSmartPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSmartPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGroupSmartPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_smart_panel, root, attachToRoot, component);
    }

    public static ItemGroupSmartPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSmartPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemGroupSmartPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_smart_panel, null, false, component);
    }

    public static ItemGroupSmartPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSmartPanelBinding bind(View view, Object component) {
        return (ItemGroupSmartPanelBinding) bind(component, view, R.layout.item_group_smart_panel);
    }
}