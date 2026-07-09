package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.EditTextSeekBarView;
import com.ltech.smarthome.view.TextSeekBarView;

/* loaded from: classes3.dex */
public abstract class FtDaliPushrodBinding extends ViewDataBinding {
    public final TextSeekBarView ctSeekbar;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final EditTextSeekBarView seekbarB;
    public final TextSeekBarView seekbarBrt;
    public final EditTextSeekBarView seekbarG;
    public final EditTextSeekBarView seekbarR;
    public final AppCompatButton tvOff;
    public final AppCompatButton tvOn;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtDaliPushrodBinding(Object _bindingComponent, View _root, int _localFieldCount, TextSeekBarView ctSeekbar, EditTextSeekBarView seekbarB, TextSeekBarView seekbarBrt, EditTextSeekBarView seekbarG, EditTextSeekBarView seekbarR, AppCompatButton tvOff, AppCompatButton tvOn) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ctSeekbar = ctSeekbar;
        this.seekbarB = seekbarB;
        this.seekbarBrt = seekbarBrt;
        this.seekbarG = seekbarG;
        this.seekbarR = seekbarR;
        this.tvOff = tvOff;
        this.tvOn = tvOn;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtDaliPushrodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDaliPushrodBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtDaliPushrodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_dali_pushrod, root, attachToRoot, component);
    }

    public static FtDaliPushrodBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDaliPushrodBinding inflate(LayoutInflater inflater, Object component) {
        return (FtDaliPushrodBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_dali_pushrod, null, false, component);
    }

    public static FtDaliPushrodBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDaliPushrodBinding bind(View view, Object component) {
        return (FtDaliPushrodBinding) bind(component, view, R.layout.ft_dali_pushrod);
    }
}