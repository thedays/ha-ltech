package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.TimeProgressBar;

/* loaded from: classes3.dex */
public abstract class ActNetConnectBinding extends ViewDataBinding {
    public final AppCompatButton btNext;
    public final Group groupFail;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final TimeProgressBar timeBar;
    public final AppCompatTextView tvConfigTip;
    public final AppCompatTextView tvConfigTip2;
    public final AppCompatTextView tvConnectFailTip;
    public final AppCompatTextView tvPleaseCheck;
    public final AppCompatTextView tvProcessTip1;
    public final AppCompatTextView tvProcessTip2;
    public final View vViewDivider;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActNetConnectBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btNext, Group groupFail, TimeProgressBar timeBar, AppCompatTextView tvConfigTip, AppCompatTextView tvConfigTip2, AppCompatTextView tvConnectFailTip, AppCompatTextView tvPleaseCheck, AppCompatTextView tvProcessTip1, AppCompatTextView tvProcessTip2, View vViewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.groupFail = groupFail;
        this.timeBar = timeBar;
        this.tvConfigTip = tvConfigTip;
        this.tvConfigTip2 = tvConfigTip2;
        this.tvConnectFailTip = tvConnectFailTip;
        this.tvPleaseCheck = tvPleaseCheck;
        this.tvProcessTip1 = tvProcessTip1;
        this.tvProcessTip2 = tvProcessTip2;
        this.vViewDivider = vViewDivider;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActNetConnectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNetConnectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActNetConnectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_net_connect, root, attachToRoot, component);
    }

    public static ActNetConnectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNetConnectBinding inflate(LayoutInflater inflater, Object component) {
        return (ActNetConnectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_net_connect, null, false, component);
    }

    public static ActNetConnectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNetConnectBinding bind(View view, Object component) {
        return (ActNetConnectBinding) bind(component, view, R.layout.act_net_connect);
    }
}