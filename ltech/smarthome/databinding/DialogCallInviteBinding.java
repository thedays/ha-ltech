package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogCallInviteBinding extends ViewDataBinding {
    public final AppCompatImageView callAnswer;
    public final AppCompatImageView callHangup;
    public final AppCompatImageView callLogo;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvCallName;
    public final AppCompatTextView tvInvite;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogCallInviteBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView callAnswer, AppCompatImageView callHangup, AppCompatImageView callLogo, AppCompatTextView tvCallName, AppCompatTextView tvInvite) {
        super(_bindingComponent, _root, _localFieldCount);
        this.callAnswer = callAnswer;
        this.callHangup = callHangup;
        this.callLogo = callLogo;
        this.tvCallName = tvCallName;
        this.tvInvite = tvInvite;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogCallInviteBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCallInviteBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogCallInviteBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_call_invite, root, attachToRoot, component);
    }

    public static DialogCallInviteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCallInviteBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogCallInviteBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_call_invite, null, false, component);
    }

    public static DialogCallInviteBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogCallInviteBinding bind(View view, Object component) {
        return (DialogCallInviteBinding) bind(component, view, R.layout.dialog_call_invite);
    }
}