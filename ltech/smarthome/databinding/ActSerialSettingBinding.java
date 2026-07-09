package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSerialSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivBaudRate;
    public final AppCompatImageView ivDataBits;
    public final AppCompatImageView ivParity;
    public final AppCompatImageView ivStopBits;
    public final LinearLayout layoutBaudRate;
    public final LinearLayout layoutDataBits;
    public final LinearLayout layoutParity;
    public final LinearLayout layoutStopBits;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mOwnerOrManager;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBaudRate;
    public final AppCompatTextView tvDataBits;
    public final AppCompatTextView tvParity;
    public final AppCompatTextView tvStopBits;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setOwnerOrManager(Boolean ownerOrManager);

    public abstract void setTitle(TitleDefault title);

    protected ActSerialSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBaudRate, AppCompatImageView ivDataBits, AppCompatImageView ivParity, AppCompatImageView ivStopBits, LinearLayout layoutBaudRate, LinearLayout layoutDataBits, LinearLayout layoutParity, LinearLayout layoutStopBits, LayoutTitleDefaultBinding title, AppCompatTextView tvBaudRate, AppCompatTextView tvDataBits, AppCompatTextView tvParity, AppCompatTextView tvStopBits) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBaudRate = ivBaudRate;
        this.ivDataBits = ivDataBits;
        this.ivParity = ivParity;
        this.ivStopBits = ivStopBits;
        this.layoutBaudRate = layoutBaudRate;
        this.layoutDataBits = layoutDataBits;
        this.layoutParity = layoutParity;
        this.layoutStopBits = layoutStopBits;
        this.title = title;
        this.tvBaudRate = tvBaudRate;
        this.tvDataBits = tvDataBits;
        this.tvParity = tvParity;
        this.tvStopBits = tvStopBits;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Boolean getOwnerOrManager() {
        return this.mOwnerOrManager;
    }

    public static ActSerialSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSerialSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSerialSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_serial_setting, root, attachToRoot, component);
    }

    public static ActSerialSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSerialSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSerialSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_serial_setting, null, false, component);
    }

    public static ActSerialSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSerialSettingBinding bind(View view, Object component) {
        return (ActSerialSettingBinding) bind(component, view, R.layout.act_serial_setting);
    }
}