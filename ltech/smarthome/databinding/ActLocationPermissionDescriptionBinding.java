package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActLocationPermissionDescriptionBinding extends ViewDataBinding {
    public final ImageView ivTipPic;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvNoThanks;
    public final AppCompatButton tvOpen;
    public final TextView tvTips;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActLocationPermissionDescriptionBinding(Object _bindingComponent, View _root, int _localFieldCount, ImageView ivTipPic, LayoutTitleDefaultBinding title, AppCompatTextView tvNoThanks, AppCompatButton tvOpen, TextView tvTips) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivTipPic = ivTipPic;
        this.title = title;
        this.tvNoThanks = tvNoThanks;
        this.tvOpen = tvOpen;
        this.tvTips = tvTips;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActLocationPermissionDescriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLocationPermissionDescriptionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLocationPermissionDescriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_location_permission_description, root, attachToRoot, component);
    }

    public static ActLocationPermissionDescriptionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLocationPermissionDescriptionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLocationPermissionDescriptionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_location_permission_description, null, false, component);
    }

    public static ActLocationPermissionDescriptionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLocationPermissionDescriptionBinding bind(View view, Object component) {
        return (ActLocationPermissionDescriptionBinding) bind(component, view, R.layout.act_location_permission_description);
    }
}