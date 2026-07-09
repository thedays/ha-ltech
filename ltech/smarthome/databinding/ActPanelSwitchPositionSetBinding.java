package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActPanelSwitchPositionSetBinding extends ViewDataBinding {
    public final Group groupPage1;
    public final Group groupPage2;
    public final Group groupPage3;
    public final Group groupTab;
    public final AppCompatImageView ivS4;
    public final AppCompatImageView ivScreen;
    public final AppCompatImageView ivShadowLeft;
    public final AppCompatImageView ivShadowRight;
    public final AppCompatImageView ivSwitch5;
    public final ConstraintLayout layoutContent;
    public final LinearLayout layoutS4;
    public final RelativeLayout layoutScreen;
    public final View line;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvDisplay;
    public final RecyclerView rvDisplayKey;
    public final RecyclerView rvKeyInfo;
    public final RecyclerView rvKeyInfo2;
    public final RecyclerView rvScreenInfo;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvTab1;
    public final TextView tvTab2;
    public final TextView tvTab3;
    public final View viewGuide;
    public final View viewTab1;
    public final View viewTab2;
    public final View viewTab3;

    public abstract void setTitle(TitleDefault title);

    protected ActPanelSwitchPositionSetBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupPage1, Group groupPage2, Group groupPage3, Group groupTab, AppCompatImageView ivS4, AppCompatImageView ivScreen, AppCompatImageView ivShadowLeft, AppCompatImageView ivShadowRight, AppCompatImageView ivSwitch5, ConstraintLayout layoutContent, LinearLayout layoutS4, RelativeLayout layoutScreen, View line, RecyclerView rvDisplay, RecyclerView rvDisplayKey, RecyclerView rvKeyInfo, RecyclerView rvKeyInfo2, RecyclerView rvScreenInfo, LayoutTitleDefaultBinding title, TextView tvTab1, TextView tvTab2, TextView tvTab3, View viewGuide, View viewTab1, View viewTab2, View viewTab3) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupPage1 = groupPage1;
        this.groupPage2 = groupPage2;
        this.groupPage3 = groupPage3;
        this.groupTab = groupTab;
        this.ivS4 = ivS4;
        this.ivScreen = ivScreen;
        this.ivShadowLeft = ivShadowLeft;
        this.ivShadowRight = ivShadowRight;
        this.ivSwitch5 = ivSwitch5;
        this.layoutContent = layoutContent;
        this.layoutS4 = layoutS4;
        this.layoutScreen = layoutScreen;
        this.line = line;
        this.rvDisplay = rvDisplay;
        this.rvDisplayKey = rvDisplayKey;
        this.rvKeyInfo = rvKeyInfo;
        this.rvKeyInfo2 = rvKeyInfo2;
        this.rvScreenInfo = rvScreenInfo;
        this.title = title;
        this.tvTab1 = tvTab1;
        this.tvTab2 = tvTab2;
        this.tvTab3 = tvTab3;
        this.viewGuide = viewGuide;
        this.viewTab1 = viewTab1;
        this.viewTab2 = viewTab2;
        this.viewTab3 = viewTab3;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPanelSwitchPositionSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_switch_position_set, root, attachToRoot, component);
    }

    public static ActPanelSwitchPositionSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelSwitchPositionSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPanelSwitchPositionSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_panel_switch_position_set, null, false, component);
    }

    public static ActPanelSwitchPositionSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPanelSwitchPositionSetBinding bind(View view, Object component) {
        return (ActPanelSwitchPositionSetBinding) bind(component, view, R.layout.act_panel_switch_position_set);
    }
}