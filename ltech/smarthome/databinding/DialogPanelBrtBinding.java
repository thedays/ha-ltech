package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class DialogPanelBrtBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final LightBrtBar sbBrt;
    public final SwitchButton sbOn;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvOnOff;
    public final View view33;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogPanelBrtBinding(Object _bindingComponent, View _root, int _localFieldCount, LightBrtBar sbBrt, SwitchButton sbOn, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvOnOff, View view33) {
        super(_bindingComponent, _root, _localFieldCount);
        this.sbBrt = sbBrt;
        this.sbOn = sbOn;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvOnOff = tvOnOff;
        this.view33 = view33;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogPanelBrtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPanelBrtBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogPanelBrtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_panel_brt, root, attachToRoot, component);
    }

    public static DialogPanelBrtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPanelBrtBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogPanelBrtBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_panel_brt, null, false, component);
    }

    public static DialogPanelBrtBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPanelBrtBinding bind(View view, Object component) {
        return (DialogPanelBrtBinding) bind(component, view, R.layout.dialog_panel_brt);
    }
}