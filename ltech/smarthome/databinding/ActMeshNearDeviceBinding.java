package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
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
public abstract class ActMeshNearDeviceBinding extends ViewDataBinding {
    public final AppCompatTextView actAddSearchContent;
    public final AppCompatTextView actAddSearchHint;
    public final RelativeLayout actSearchRelayout;
    public final ConstraintLayout constraintlayout;
    public final Guideline guideline;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActMeshScanVM mViewmodel;
    public final RecyclerView rvContent;
    public final FrameLayout searchFrameLayout;
    public final AppCompatImageView searchImage;
    public final SpreadView searchSpreadView;
    public final AppCompatTextView tvAddAll;
    public final AppCompatTextView tvHelp;
    public final AppCompatTextView tvNoDevice;
    public final AppCompatTextView tvScanTip1;
    public final AppCompatTextView tvUpgrade;
    public final View vLine;
    public final View view24;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActMeshScanVM viewmodel);

    protected ActMeshNearDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView actAddSearchContent, AppCompatTextView actAddSearchHint, RelativeLayout actSearchRelayout, ConstraintLayout constraintlayout, Guideline guideline, RecyclerView rvContent, FrameLayout searchFrameLayout, AppCompatImageView searchImage, SpreadView searchSpreadView, AppCompatTextView tvAddAll, AppCompatTextView tvHelp, AppCompatTextView tvNoDevice, AppCompatTextView tvScanTip1, AppCompatTextView tvUpgrade, View vLine, View view24) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddSearchContent = actAddSearchContent;
        this.actAddSearchHint = actAddSearchHint;
        this.actSearchRelayout = actSearchRelayout;
        this.constraintlayout = constraintlayout;
        this.guideline = guideline;
        this.rvContent = rvContent;
        this.searchFrameLayout = searchFrameLayout;
        this.searchImage = searchImage;
        this.searchSpreadView = searchSpreadView;
        this.tvAddAll = tvAddAll;
        this.tvHelp = tvHelp;
        this.tvNoDevice = tvNoDevice;
        this.tvScanTip1 = tvScanTip1;
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

    public static ActMeshNearDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshNearDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMeshNearDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_near_device, root, attachToRoot, component);
    }

    public static ActMeshNearDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshNearDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMeshNearDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_mesh_near_device, null, false, component);
    }

    public static ActMeshNearDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMeshNearDeviceBinding bind(View view, Object component) {
        return (ActMeshNearDeviceBinding) bind(component, view, R.layout.act_mesh_near_device);
    }
}