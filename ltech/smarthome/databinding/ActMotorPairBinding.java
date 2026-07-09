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
public abstract class ActMotorPairBinding extends ViewDataBinding {
    public final AppCompatButton btNext;
    public final AppCompatImageView ivConfigTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvConfigTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActMotorPairBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btNext, AppCompatImageView ivConfigTip, AppCompatTextView tvConfigTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.ivConfigTip = ivConfigTip;
        this.tvConfigTitle = tvConfigTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActMotorPairBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMotorPairBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMotorPairBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_motor_pair, root, attachToRoot, component);
    }

    public static ActMotorPairBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMotorPairBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMotorPairBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_motor_pair, null, false, component);
    }

    public static ActMotorPairBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMotorPairBinding bind(View view, Object component) {
        return (ActMotorPairBinding) bind(component, view, R.layout.act_motor_pair);
    }
}