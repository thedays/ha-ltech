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
import com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM;

/* loaded from: classes3.dex */
public abstract class FtAirDialogBinding extends ViewDataBinding {
    public final AppCompatImageView ivPmIcon;
    public final AppCompatImageView ivTempIcon;
    public final LinearLayout layoutInfo;
    public final LinearLayout layoutPmInfo;
    public final LinearLayout layoutTempInfo;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ActTePanelVM mViewmodel;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvPmName;
    public final AppCompatTextView tvPmValue;
    public final AppCompatTextView tvTempName;
    public final AppCompatTextView tvTempValue;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setViewmodel(ActTePanelVM viewmodel);

    protected FtAirDialogBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPmIcon, AppCompatImageView ivTempIcon, LinearLayout layoutInfo, LinearLayout layoutPmInfo, LinearLayout layoutTempInfo, RecyclerView rvContent, AppCompatTextView tvPmName, AppCompatTextView tvPmValue, AppCompatTextView tvTempName, AppCompatTextView tvTempValue) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPmIcon = ivPmIcon;
        this.ivTempIcon = ivTempIcon;
        this.layoutInfo = layoutInfo;
        this.layoutPmInfo = layoutPmInfo;
        this.layoutTempInfo = layoutTempInfo;
        this.rvContent = rvContent;
        this.tvPmName = tvPmName;
        this.tvPmValue = tvPmValue;
        this.tvTempName = tvTempName;
        this.tvTempValue = tvTempValue;
    }

    public ActTePanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtAirDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAirDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtAirDialogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_air_dialog, root, attachToRoot, component);
    }

    public static FtAirDialogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAirDialogBinding inflate(LayoutInflater inflater, Object component) {
        return (FtAirDialogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_air_dialog, null, false, component);
    }

    public static FtAirDialogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAirDialogBinding bind(View view, Object component) {
        return (FtAirDialogBinding) bind(component, view, R.layout.ft_air_dialog);
    }
}