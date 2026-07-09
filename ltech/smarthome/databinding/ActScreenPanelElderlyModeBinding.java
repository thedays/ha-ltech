package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActScreenPanelElderlyModeBinding extends ViewDataBinding {
    public final AppCompatButton btnEnableOrDisableElderlyMode;
    public final AppCompatImageView ivTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvConfigTip;
    public final AppCompatTextView tvConfigTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActScreenPanelElderlyModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btnEnableOrDisableElderlyMode, AppCompatImageView ivTip, AppCompatTextView tvConfigTip, AppCompatTextView tvConfigTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btnEnableOrDisableElderlyMode = btnEnableOrDisableElderlyMode;
        this.ivTip = ivTip;
        this.tvConfigTip = tvConfigTip;
        this.tvConfigTitle = tvConfigTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActScreenPanelElderlyModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActScreenPanelElderlyModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActScreenPanelElderlyModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_screen_panel_elderly_mode, root, attachToRoot, component);
    }

    public static ActScreenPanelElderlyModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActScreenPanelElderlyModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActScreenPanelElderlyModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_screen_panel_elderly_mode, null, false, component);
    }

    public static ActScreenPanelElderlyModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActScreenPanelElderlyModeBinding bind(View view, Object component) {
        return (ActScreenPanelElderlyModeBinding) bind(component, view, R.layout.act_screen_panel_elderly_mode);
    }
}