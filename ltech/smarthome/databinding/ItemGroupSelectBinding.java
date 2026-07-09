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
import com.ltech.smarthome.model.bean.Group;

/* loaded from: classes3.dex */
public abstract class ItemGroupSelectBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivIcon;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Group mGroup;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected String mPlaceInfo;

    @Bindable
    protected Boolean mSelect;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvPlaceInfo;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setGroup(Group group);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setPlaceInfo(String placeInfo);

    public abstract void setSelect(Boolean select);

    protected ItemGroupSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo, AppCompatImageView ivIcon, ConstraintLayout layoutItemBg, AppCompatTextView tvDeviceName, AppCompatTextView tvPlaceInfo) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
        this.layoutItemBg = layoutItemBg;
        this.tvDeviceName = tvDeviceName;
        this.tvPlaceInfo = tvPlaceInfo;
    }

    public Group getGroup() {
        return this.mGroup;
    }

    public String getPlaceInfo() {
        return this.mPlaceInfo;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public Boolean getSelect() {
        return this.mSelect;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemGroupSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemGroupSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_select, root, attachToRoot, component);
    }

    public static ItemGroupSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemGroupSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_group_select, null, false, component);
    }

    public static ItemGroupSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemGroupSelectBinding bind(View view, Object component) {
        return (ItemGroupSelectBinding) bind(component, view, R.layout.item_group_select);
    }
}