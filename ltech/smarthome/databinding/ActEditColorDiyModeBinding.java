package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActEditColorDiyModeVM;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public abstract class ActEditColorDiyModeBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView25;
    public final AppCompatTextView appCompatTextView29;
    public final RelativeLayout cardView;
    public final Group groupW;
    public final AppCompatImageView ivMode;
    public final AppCompatImageView ivPreview;
    public final ScrollView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActEditColorDiyModeVM mViewmodel;
    public final RecyclerView rvColor;
    public final HorizontalSeekBar sbBrt;
    public final HorizontalSeekBar sbSpeed;
    public final HorizontalSeekBar sbW;
    public final TabLayout tabMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvChooseIcon;
    public final AppCompatTextView tvModeName;
    public final AppCompatTextView tvModeNameTip;
    public final AppCompatTextView tvSpeed;
    public final AppCompatTextView tvSpeedTip;
    public final AppCompatTextView tvW;
    public final AppCompatTextView tvWTip;
    public final View vChangeIcon;
    public final View vPreview;
    public final View view17;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActEditColorDiyModeVM viewmodel);

    protected ActEditColorDiyModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView25, AppCompatTextView appCompatTextView29, RelativeLayout cardView, Group groupW, AppCompatImageView ivMode, AppCompatImageView ivPreview, ScrollView layoutLoad, RecyclerView rvColor, HorizontalSeekBar sbBrt, HorizontalSeekBar sbSpeed, HorizontalSeekBar sbW, TabLayout tabMode, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvChooseIcon, AppCompatTextView tvModeName, AppCompatTextView tvModeNameTip, AppCompatTextView tvSpeed, AppCompatTextView tvSpeedTip, AppCompatTextView tvW, AppCompatTextView tvWTip, View vChangeIcon, View vPreview, View view17) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView25 = appCompatTextView25;
        this.appCompatTextView29 = appCompatTextView29;
        this.cardView = cardView;
        this.groupW = groupW;
        this.ivMode = ivMode;
        this.ivPreview = ivPreview;
        this.layoutLoad = layoutLoad;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.sbSpeed = sbSpeed;
        this.sbW = sbW;
        this.tabMode = tabMode;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvChooseIcon = tvChooseIcon;
        this.tvModeName = tvModeName;
        this.tvModeNameTip = tvModeNameTip;
        this.tvSpeed = tvSpeed;
        this.tvSpeedTip = tvSpeedTip;
        this.tvW = tvW;
        this.tvWTip = tvWTip;
        this.vChangeIcon = vChangeIcon;
        this.vPreview = vPreview;
        this.view17 = view17;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActEditColorDiyModeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActEditColorDiyModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditColorDiyModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEditColorDiyModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_color_diy_mode, root, attachToRoot, component);
    }

    public static ActEditColorDiyModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditColorDiyModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEditColorDiyModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_color_diy_mode, null, false, component);
    }

    public static ActEditColorDiyModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditColorDiyModeBinding bind(View view, Object component) {
        return (ActEditColorDiyModeBinding) bind(component, view, R.layout.act_edit_color_diy_mode);
    }
}