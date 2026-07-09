package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class FtDaliAddBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final TabLayout tabs;
    public final AppCompatTextView tvFloorContent;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtDaliAddBinding(Object _bindingComponent, View _root, int _localFieldCount, TabLayout tabs, AppCompatTextView tvFloorContent, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tabs = tabs;
        this.tvFloorContent = tvFloorContent;
        this.viewpager = viewpager;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtDaliAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDaliAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtDaliAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_dali_add, root, attachToRoot, component);
    }

    public static FtDaliAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDaliAddBinding inflate(LayoutInflater inflater, Object component) {
        return (FtDaliAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_dali_add, null, false, component);
    }

    public static FtDaliAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDaliAddBinding bind(View view, Object component) {
        return (FtDaliAddBinding) bind(component, view, R.layout.ft_dali_add);
    }
}