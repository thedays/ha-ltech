package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public abstract class ActMeshScanProxyBinding extends ViewDataBinding {
    public final ConstraintLayout constraintlayout;
    public final Guideline guideline;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMeshScanVM mViewmodel;
    public final ContentLoadingProgressBar progressBar;
    public final RecyclerView rvContent;
    public final MySpinner spinnerFloor;
    public final MySpinner spinnerRoom;
    public final SpreadView spreadviewScan;
    public final AppCompatTextView tvHelp;
    public final AppCompatTextView tvNoDevice;
    public final AppCompatTextView tvScanTip1;
    public final AppCompatTextView tvScanTip2;
    public final AppCompatTextView tvUpgrade;
    public final View vLine;
    public final View view24;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMeshScanVM viewmodel);

    protected ActMeshScanProxyBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout constraintlayout, Guideline guideline, ContentLoadingProgressBar progressBar, RecyclerView rvContent, MySpinner spinnerFloor, MySpinner spinnerRoom, SpreadView spreadviewScan, AppCompatTextView tvHelp, AppCompatTextView tvNoDevice, AppCompatTextView tvScanTip1, AppCompatTextView tvScanTip2, AppCompatTextView tvUpgrade, View vLine, View view24) {
        super(_bindingComponent, _root, _localFieldCount);
        this.constraintlayout = constraintlayout;
        this.guideline = guideline;
        this.progressBar = progressBar;
        this.rvContent = rvContent;
        this.spinnerFloor = spinnerFloor;
        this.spinnerRoom = spinnerRoom;
        this.spreadviewScan = spreadviewScan;
        this.tvHelp = tvHelp;
        this.tvNoDevice = tvNoDevice;
        this.tvScanTip1 = tvScanTip1;
        this.tvScanTip2 = tvScanTip2;
        this.tvUpgrade = tvUpgrade;
        this.vLine = vLine;
        this.view24 = view24;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActMeshScanVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActMeshScanProxyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScanProxyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMeshScanProxyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_scan_proxy, root, attachToRoot, component);
    }

    public static ActMeshScanProxyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScanProxyBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMeshScanProxyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_scan_proxy, null, false, component);
    }

    public static ActMeshScanProxyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScanProxyBinding bind(View view, Object component) {
        return (ActMeshScanProxyBinding) bind(component, view, R.layout.act_mesh_scan_proxy);
    }
}