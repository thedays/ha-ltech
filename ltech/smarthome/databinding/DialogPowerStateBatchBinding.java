package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public abstract class DialogPowerStateBatchBinding extends ViewDataBinding {
    public final ViewPowerStateBleAllBinding layoutState;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RadioImageTextView radioLightOnCustom;
    public final RadioImageTextView radioLightOnDefault;
    public final RadioImageTextView radioLightOnMemory;
    public final RadioImageTextView radioLightOnNotLight;
    public final LinearLayout radioState;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogPowerStateBatchBinding(Object _bindingComponent, View _root, int _localFieldCount, ViewPowerStateBleAllBinding layoutState, RadioImageTextView radioLightOnCustom, RadioImageTextView radioLightOnDefault, RadioImageTextView radioLightOnMemory, RadioImageTextView radioLightOnNotLight, LinearLayout radioState, AppCompatTextView tvCancel, AppCompatTextView tvSave, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutState = layoutState;
        this.radioLightOnCustom = radioLightOnCustom;
        this.radioLightOnDefault = radioLightOnDefault;
        this.radioLightOnMemory = radioLightOnMemory;
        this.radioLightOnNotLight = radioLightOnNotLight;
        this.radioState = radioState;
        this.tvCancel = tvCancel;
        this.tvSave = tvSave;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogPowerStateBatchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPowerStateBatchBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogPowerStateBatchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_power_state_batch, root, attachToRoot, component);
    }

    public static DialogPowerStateBatchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPowerStateBatchBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogPowerStateBatchBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_power_state_batch, null, false, component);
    }

    public static DialogPowerStateBatchBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogPowerStateBatchBinding bind(View view, Object component) {
        return (DialogPowerStateBatchBinding) bind(component, view, R.layout.dialog_power_state_batch);
    }
}