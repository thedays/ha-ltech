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

/* loaded from: classes3.dex */
public abstract class DialogAcQuickBinding extends ViewDataBinding {
    public final View dividerLine;
    public final AppCompatImageView ivDeviceMore;
    public final AppCompatImageView ivSendTip;
    public final AppCompatImageView ivTempLeft;
    public final AppCompatImageView ivTempRight;
    public final LinearLayout layoutTempLeft;
    public final LinearLayout layoutTempRight;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final RecyclerView rvTemp;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogAcQuickBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, AppCompatImageView ivDeviceMore, AppCompatImageView ivSendTip, AppCompatImageView ivTempLeft, AppCompatImageView ivTempRight, LinearLayout layoutTempLeft, LinearLayout layoutTempRight, RecyclerView rvContent, RecyclerView rvTemp, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.ivDeviceMore = ivDeviceMore;
        this.ivSendTip = ivSendTip;
        this.ivTempLeft = ivTempLeft;
        this.ivTempRight = ivTempRight;
        this.layoutTempLeft = layoutTempLeft;
        this.layoutTempRight = layoutTempRight;
        this.rvContent = rvContent;
        this.rvTemp = rvTemp;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogAcQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAcQuickBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogAcQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ac_quick, root, attachToRoot, component);
    }

    public static DialogAcQuickBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAcQuickBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogAcQuickBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_ac_quick, null, false, component);
    }

    public static DialogAcQuickBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAcQuickBinding bind(View view, Object component) {
        return (DialogAcQuickBinding) bind(component, view, R.layout.dialog_ac_quick);
    }
}