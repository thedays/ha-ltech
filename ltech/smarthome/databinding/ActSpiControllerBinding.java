package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerVM;
import com.ltech.smarthome.view.HorizontalSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SmartSwitch;

/* loaded from: classes3.dex */
public abstract class ActSpiControllerBinding extends ViewDataBinding {
    public final AppCompatImageView ivConsole;
    public final ConstraintLayout layoutControl;
    public final LinearLayout layoutDirection;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mColorControl;

    @Bindable
    protected Boolean mExpand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSpiControllerVM mViewmodel;
    public final LightBrtBar sbBrt;
    public final HorizontalSeekBar sbSpeed;
    public final SmartSwitch spiSwitch;
    public final LinearLayout tabLayout;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvConsole;
    public final LinearLayout tvControl;
    public final AppCompatTextView tvDirection;
    public final AppCompatTextView tvSpeed;
    public final AppCompatTextView tvSpeedTip;
    public final View vOpen;
    public final View viewLine;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setColorControl(Boolean colorControl);

    public abstract void setExpand(Boolean expand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSpiControllerVM viewmodel);

    protected ActSpiControllerBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivConsole, ConstraintLayout layoutControl, LinearLayout layoutDirection, LightBrtBar sbBrt, HorizontalSeekBar sbSpeed, SmartSwitch spiSwitch, LinearLayout tabLayout, TabLayout tabs, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvConsole, LinearLayout tvControl, AppCompatTextView tvDirection, AppCompatTextView tvSpeed, AppCompatTextView tvSpeedTip, View vOpen, View viewLine, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivConsole = ivConsole;
        this.layoutControl = layoutControl;
        this.layoutDirection = layoutDirection;
        this.sbBrt = sbBrt;
        this.sbSpeed = sbSpeed;
        this.spiSwitch = spiSwitch;
        this.tabLayout = tabLayout;
        this.tabs = tabs;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvConsole = tvConsole;
        this.tvControl = tvControl;
        this.tvDirection = tvDirection;
        this.tvSpeed = tvSpeed;
        this.tvSpeedTip = tvSpeedTip;
        this.vOpen = vOpen;
        this.viewLine = viewLine;
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getExpand() {
        return this.mExpand;
    }

    public Boolean getColorControl() {
        return this.mColorControl;
    }

    public ActSpiControllerVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSpiControllerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiControllerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSpiControllerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_spi_controller, root, attachToRoot, component);
    }

    public static ActSpiControllerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiControllerBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSpiControllerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_spi_controller, null, false, component);
    }

    public static ActSpiControllerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiControllerBinding bind(View view, Object component) {
        return (ActSpiControllerBinding) bind(component, view, R.layout.act_spi_controller);
    }
}