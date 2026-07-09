package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
public abstract class ActMeshScanBinding extends ViewDataBinding {
    public final ConstraintLayout constraintlayout;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected String mUpgradeTip;

    @Bindable
    protected ActMeshScanVM mViewmodel;
    public final ContentLoadingProgressBar progressBar;
    public final RecyclerView rvContent;
    public final SpreadView spreadviewScan;
    public final AppCompatTextView tvHelp;
    public final AppCompatTextView tvNoDevice;
    public final AppCompatTextView tvScanTip1;
    public final AppCompatTextView tvScanTip2;
    public final AppCompatTextView tvTotal;
    public final AppCompatTextView tvUpgrade;
    public final View vLine;
    public final View view24;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setUpgradeTip(String upgradeTip);

    public abstract void setViewmodel(ActMeshScanVM viewmodel);

    protected ActMeshScanBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout constraintlayout, ContentLoadingProgressBar progressBar, RecyclerView rvContent, SpreadView spreadviewScan, AppCompatTextView tvHelp, AppCompatTextView tvNoDevice, AppCompatTextView tvScanTip1, AppCompatTextView tvScanTip2, AppCompatTextView tvTotal, AppCompatTextView tvUpgrade, View vLine, View view24) {
        super(_bindingComponent, _root, _localFieldCount);
        this.constraintlayout = constraintlayout;
        this.progressBar = progressBar;
        this.rvContent = rvContent;
        this.spreadviewScan = spreadviewScan;
        this.tvHelp = tvHelp;
        this.tvNoDevice = tvNoDevice;
        this.tvScanTip1 = tvScanTip1;
        this.tvScanTip2 = tvScanTip2;
        this.tvTotal = tvTotal;
        this.tvUpgrade = tvUpgrade;
        this.vLine = vLine;
        this.view24 = view24;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getUpgradeTip() {
        return this.mUpgradeTip;
    }

    public ActMeshScanVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActMeshScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMeshScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_scan, root, attachToRoot, component);
    }

    public static ActMeshScanBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScanBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMeshScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_scan, null, false, component);
    }

    public static ActMeshScanBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshScanBinding bind(View view, Object component) {
        return (ActMeshScanBinding) bind(component, view, R.layout.act_mesh_scan);
    }
}