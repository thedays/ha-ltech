package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class Dialog512ChannelSettingBinding extends ViewDataBinding {
    public final AppCompatEditText edit;
    public final AppCompatImageView iv;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RangeSeekBar seekBar;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvLabel1;
    public final AppCompatTextView tvLabel2;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvValue;
    public final View view1;
    public final View view2;
    public final View viewEdit;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected Dialog512ChannelSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText edit, AppCompatImageView iv, RangeSeekBar seekBar, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvLabel1, AppCompatTextView tvLabel2, AppCompatTextView tvTitle, AppCompatTextView tvValue, View view1, View view2, View viewEdit) {
        super(_bindingComponent, _root, _localFieldCount);
        this.edit = edit;
        this.iv = iv;
        this.seekBar = seekBar;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvLabel1 = tvLabel1;
        this.tvLabel2 = tvLabel2;
        this.tvTitle = tvTitle;
        this.tvValue = tvValue;
        this.view1 = view1;
        this.view2 = view2;
        this.viewEdit = viewEdit;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static Dialog512ChannelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Dialog512ChannelSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (Dialog512ChannelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_512_channel_setting, root, attachToRoot, component);
    }

    public static Dialog512ChannelSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Dialog512ChannelSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (Dialog512ChannelSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_512_channel_setting, null, false, component);
    }

    public static Dialog512ChannelSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static Dialog512ChannelSettingBinding bind(View view, Object component) {
        return (Dialog512ChannelSettingBinding) bind(component, view, R.layout.dialog_512_channel_setting);
    }
}