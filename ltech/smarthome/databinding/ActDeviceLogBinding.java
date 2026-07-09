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
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActDeviceLogBinding extends ViewDataBinding {
    public final AppCompatImageView ivLeft;
    public final AppCompatImageView ivRight;
    public final LinearLayout layoutDate;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDate;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActDeviceLogBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivLeft, AppCompatImageView ivRight, LinearLayout layoutDate, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvDate) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivLeft = ivLeft;
        this.ivRight = ivRight;
        this.layoutDate = layoutDate;
        this.rvContent = rvContent;
        this.title = title;
        this.tvDate = tvDate;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActDeviceLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDeviceLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_log, root, attachToRoot, component);
    }

    public static ActDeviceLogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceLogBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDeviceLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_device_log, null, false, component);
    }

    public static ActDeviceLogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDeviceLogBinding bind(View view, Object component) {
        return (ActDeviceLogBinding) bind(component, view, R.layout.act_device_log);
    }
}