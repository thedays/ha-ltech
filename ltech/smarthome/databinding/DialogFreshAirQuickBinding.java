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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class DialogFreshAirQuickBinding extends ViewDataBinding {
    public final View dividerLine;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivPmIcon;
    public final AppCompatImageView ivSendTip;
    public final AppCompatImageView ivTempIcon;
    public final LinearLayout layoutInfo;
    public final LinearLayout layoutPmInfo;
    public final LinearLayout layoutTempInfo;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final SwitchButton sb;
    public final AppCompatTextView tvPmName;
    public final AppCompatTextView tvPmValue;
    public final AppCompatTextView tvTempName;
    public final AppCompatTextView tvTempValue;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogFreshAirQuickBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, AppCompatImageView ivDeviceMore, AppCompatImageView ivPmIcon, AppCompatImageView ivSendTip, AppCompatImageView ivTempIcon, LinearLayout layoutInfo, LinearLayout layoutPmInfo, LinearLayout layoutTempInfo, RecyclerView rvContent, SwitchButton sb, AppCompatTextView tvPmName, AppCompatTextView tvPmValue, AppCompatTextView tvTempName, AppCompatTextView tvTempValue, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.ivDeviceMore = ivDeviceMore;
        this.ivPmIcon = ivPmIcon;
        this.ivSendTip = ivSendTip;
        this.ivTempIcon = ivTempIcon;
        this.layoutInfo = layoutInfo;
        this.layoutPmInfo = layoutPmInfo;
        this.layoutTempInfo = layoutTempInfo;
        this.rvContent = rvContent;
        this.sb = sb;
        this.tvPmName = tvPmName;
        this.tvPmValue = tvPmValue;
        this.tvTempName = tvTempName;
        this.tvTempValue = tvTempValue;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogFreshAirQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFreshAirQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogFreshAirQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fresh_air_quick, root, attachToRoot, component);
    }

    public static DialogFreshAirQuickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFreshAirQuickBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogFreshAirQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_fresh_air_quick, null, false, component);
    }

    public static DialogFreshAirQuickBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFreshAirQuickBinding bind(View view, Object component) {
        return (DialogFreshAirQuickBinding) bind(component, view, R.layout.dialog_fresh_air_quick);
    }
}