package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class FtAdvancedModeBinding extends ViewDataBinding {
    public final AppCompatImageView circleImageView1;
    public final AppCompatImageView ivListTimes;
    public final AppCompatImageView ivPlayList;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvMode;
    public final AppCompatTextView tvPlayList;
    public final AppCompatTextView tvTip;
    public final RelativeLayout vPlayAll;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtAdvancedModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView circleImageView1, AppCompatImageView ivListTimes, AppCompatImageView ivPlayList, SmartRefreshLayout refreshLayout, RecyclerView rvMode, AppCompatTextView tvPlayList, AppCompatTextView tvTip, RelativeLayout vPlayAll) {
        super(_bindingComponent, _root, _localFieldCount);
        this.circleImageView1 = circleImageView1;
        this.ivListTimes = ivListTimes;
        this.ivPlayList = ivPlayList;
        this.refreshLayout = refreshLayout;
        this.rvMode = rvMode;
        this.tvPlayList = tvPlayList;
        this.tvTip = tvTip;
        this.vPlayAll = vPlayAll;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtAdvancedModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAdvancedModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtAdvancedModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_advanced_mode, root, attachToRoot, component);
    }

    public static FtAdvancedModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAdvancedModeBinding inflate(LayoutInflater inflater, Object component) {
        return (FtAdvancedModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_advanced_mode, null, false, component);
    }

    public static FtAdvancedModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtAdvancedModeBinding bind(View view, Object component) {
        return (FtAdvancedModeBinding) bind(component, view, R.layout.ft_advanced_mode);
    }
}