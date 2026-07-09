package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActAddDeviceVM;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public abstract class ActAddDeviceBinding extends ViewDataBinding {
    public final NestedScrollView actAddDeviceScroll;
    public final AppCompatTextView actAddSearchContent;
    public final AppCompatTextView actAddSearchHint;
    public final RelativeLayout actSearchRelayout;
    public final LinearLayout layoutFoundDevice;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddDeviceVM mViewmodel;
    public final RecyclerView rvCategory;
    public final RecyclerView rvDevice;
    public final RecyclerView rvSearchDevice;
    public final FrameLayout searchFrameLayout;
    public final AppCompatImageView searchImage;
    public final SpreadView searchSpreadView;
    public final LayoutTitleDefaultBinding title;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddDeviceVM viewmodel);

    protected ActAddDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView actAddDeviceScroll, AppCompatTextView actAddSearchContent, AppCompatTextView actAddSearchHint, RelativeLayout actSearchRelayout, LinearLayout layoutFoundDevice, ConstraintLayout layoutLoad, RecyclerView rvCategory, RecyclerView rvDevice, RecyclerView rvSearchDevice, FrameLayout searchFrameLayout, AppCompatImageView searchImage, SpreadView searchSpreadView, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddDeviceScroll = actAddDeviceScroll;
        this.actAddSearchContent = actAddSearchContent;
        this.actAddSearchHint = actAddSearchHint;
        this.actSearchRelayout = actSearchRelayout;
        this.layoutFoundDevice = layoutFoundDevice;
        this.layoutLoad = layoutLoad;
        this.rvCategory = rvCategory;
        this.rvDevice = rvDevice;
        this.rvSearchDevice = rvSearchDevice;
        this.searchFrameLayout = searchFrameLayout;
        this.searchImage = searchImage;
        this.searchSpreadView = searchSpreadView;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddDeviceVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActAddDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_device, root, attachToRoot, component);
    }

    public static ActAddDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_device, null, false, component);
    }

    public static ActAddDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddDeviceBinding bind(View view, Object component) {
        return (ActAddDeviceBinding) bind(component, view, R.layout.act_add_device);
    }
}