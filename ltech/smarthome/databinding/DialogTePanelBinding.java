package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class DialogTePanelBinding extends ViewDataBinding {
    public final View dividerLine;
    public final FrameLayout fragmentContainer;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivSendTip;
    public final LinearLayout layoutTab;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final SwitchButton sb;
    public final AppCompatTextView tvAc;
    public final AppCompatTextView tvAir;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogTePanelBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, FrameLayout fragmentContainer, AppCompatImageView ivDeviceMore, AppCompatImageView ivSendTip, LinearLayout layoutTab, SwitchButton sb, AppCompatTextView tvAc, AppCompatTextView tvAir, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.fragmentContainer = fragmentContainer;
        this.ivDeviceMore = ivDeviceMore;
        this.ivSendTip = ivSendTip;
        this.layoutTab = layoutTab;
        this.sb = sb;
        this.tvAc = tvAc;
        this.tvAir = tvAir;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogTePanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTePanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogTePanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_te_panel, root, attachToRoot, component);
    }

    public static DialogTePanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTePanelBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogTePanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_te_panel, null, false, component);
    }

    public static DialogTePanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogTePanelBinding bind(View view, Object component) {
        return (DialogTePanelBinding) bind(component, view, R.layout.dialog_te_panel);
    }
}