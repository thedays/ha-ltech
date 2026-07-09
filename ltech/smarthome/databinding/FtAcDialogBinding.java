package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM;

/* loaded from: classes3.dex */
public abstract class FtAcDialogBinding extends ViewDataBinding {
    public final AppCompatImageView ivTempLeft;
    public final AppCompatImageView ivTempRight;
    public final LinearLayout layoutTempLeft;
    public final LinearLayout layoutTempRight;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ActTePanelVM mViewmodel;
    public final RecyclerView rvContent;
    public final RecyclerView rvTemp;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setViewmodel(ActTePanelVM viewmodel);

    protected FtAcDialogBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivTempLeft, AppCompatImageView ivTempRight, LinearLayout layoutTempLeft, LinearLayout layoutTempRight, RecyclerView rvContent, RecyclerView rvTemp) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivTempLeft = ivTempLeft;
        this.ivTempRight = ivTempRight;
        this.layoutTempLeft = layoutTempLeft;
        this.layoutTempRight = layoutTempRight;
        this.rvContent = rvContent;
        this.rvTemp = rvTemp;
    }

    public ActTePanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtAcDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAcDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtAcDialogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_ac_dialog, root, attachToRoot, component);
    }

    public static FtAcDialogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAcDialogBinding inflate(LayoutInflater inflater, Object component) {
        return (FtAcDialogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_ac_dialog, null, false, component);
    }

    public static FtAcDialogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAcDialogBinding bind(View view, Object component) {
        return (FtAcDialogBinding) bind(component, view, R.layout.ft_ac_dialog);
    }
}