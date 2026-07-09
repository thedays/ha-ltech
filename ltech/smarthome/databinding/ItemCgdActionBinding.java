package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Scene;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemCgdActionBinding extends ViewDataBinding {
    public final CircleImageView civColor;
    public final Group groupColor;
    public final AppCompatImageView ivIcon;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutItemBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Scene mItem;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvAdd;
    public final AppCompatTextView tvColor;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setItem(Scene item);

    protected ItemCgdActionBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView civColor, Group groupColor, AppCompatImageView ivIcon, AppCompatImageView ivSelect, ConstraintLayout layoutItemBg, AppCompatTextView tvAction, AppCompatTextView tvAdd, AppCompatTextView tvColor, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.civColor = civColor;
        this.groupColor = groupColor;
        this.ivIcon = ivIcon;
        this.ivSelect = ivSelect;
        this.layoutItemBg = layoutItemBg;
        this.tvAction = tvAction;
        this.tvAdd = tvAdd;
        this.tvColor = tvColor;
        this.tvName = tvName;
    }

    public Scene getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemCgdActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCgdActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemCgdActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cgd_action, root, attachToRoot, component);
    }

    public static ItemCgdActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCgdActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemCgdActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_cgd_action, null, false, component);
    }

    public static ItemCgdActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemCgdActionBinding bind(View view, Object component) {
        return (ItemCgdActionBinding) bind(component, view, R.layout.item_cgd_action);
    }
}