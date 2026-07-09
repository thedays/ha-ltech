package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.BaseEditGeneralModeVM;
import com.ltech.smarthome.utils.selectedCountryLib.demo.RoundImageView;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.HorizontalSeekBar;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActEditGeneralModeBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView25;
    public final AppCompatTextView changeIcon;
    public final ColorSeekBar csbWyBar;
    public final Group groupW;
    public final Group groupWy;
    public final View guideline;
    public final AppCompatImageView iconMore;
    public final AppCompatImageView iconMore1;
    public final AppCompatImageView iconMore2;
    public final ConstraintLayout itemIcon;
    public final ConstraintLayout itemName;
    public final ConstraintLayout itemTimes;
    public final RoundImageView iv;
    public final ScrollView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected BaseEditGeneralModeVM mViewmodel;
    public final AppCompatTextView playBack;
    public final AppCompatTextView playBack1;
    public final AppCompatTextView reset;
    public final RecyclerView rvColor;
    public final LightBrtBar sbBrt;
    public final HorizontalSeekBar sbSpeed;
    public final LightBrtBar sbW;
    public final TabLayout tabMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvPlayTimes;
    public final AppCompatTextView tvSpeed;
    public final AppCompatTextView tvSpeedTip;
    public final AppCompatTextView tvTimesNumber;
    public final AppCompatTextView tvW;
    public final AppCompatTextView tvWTip;
    public final AppCompatTextView tvWy;
    public final AppCompatTextView tvWyTip;
    public final View vPreview1;
    public final View vPreview2;
    public final View vPreviewOff;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(BaseEditGeneralModeVM viewmodel);

    protected ActEditGeneralModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView25, AppCompatTextView changeIcon, ColorSeekBar csbWyBar, Group groupW, Group groupWy, View guideline, AppCompatImageView iconMore, AppCompatImageView iconMore1, AppCompatImageView iconMore2, ConstraintLayout itemIcon, ConstraintLayout itemName, ConstraintLayout itemTimes, RoundImageView iv, ScrollView layoutLoad, AppCompatTextView playBack, AppCompatTextView playBack1, AppCompatTextView reset, RecyclerView rvColor, LightBrtBar sbBrt, HorizontalSeekBar sbSpeed, LightBrtBar sbW, TabLayout tabMode, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvDeviceName, AppCompatTextView tvNameTip, AppCompatTextView tvPlayTimes, AppCompatTextView tvSpeed, AppCompatTextView tvSpeedTip, AppCompatTextView tvTimesNumber, AppCompatTextView tvW, AppCompatTextView tvWTip, AppCompatTextView tvWy, AppCompatTextView tvWyTip, View vPreview1, View vPreview2, View vPreviewOff) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView25 = appCompatTextView25;
        this.changeIcon = changeIcon;
        this.csbWyBar = csbWyBar;
        this.groupW = groupW;
        this.groupWy = groupWy;
        this.guideline = guideline;
        this.iconMore = iconMore;
        this.iconMore1 = iconMore1;
        this.iconMore2 = iconMore2;
        this.itemIcon = itemIcon;
        this.itemName = itemName;
        this.itemTimes = itemTimes;
        this.iv = iv;
        this.layoutLoad = layoutLoad;
        this.playBack = playBack;
        this.playBack1 = playBack1;
        this.reset = reset;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.sbSpeed = sbSpeed;
        this.sbW = sbW;
        this.tabMode = tabMode;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvDeviceName = tvDeviceName;
        this.tvNameTip = tvNameTip;
        this.tvPlayTimes = tvPlayTimes;
        this.tvSpeed = tvSpeed;
        this.tvSpeedTip = tvSpeedTip;
        this.tvTimesNumber = tvTimesNumber;
        this.tvW = tvW;
        this.tvWTip = tvWTip;
        this.tvWy = tvWy;
        this.tvWyTip = tvWyTip;
        this.vPreview1 = vPreview1;
        this.vPreview2 = vPreview2;
        this.vPreviewOff = vPreviewOff;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BaseEditGeneralModeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActEditGeneralModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditGeneralModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEditGeneralModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_general_mode, root, attachToRoot, component);
    }

    public static ActEditGeneralModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditGeneralModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEditGeneralModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_general_mode, null, false, component);
    }

    public static ActEditGeneralModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditGeneralModeBinding bind(View view, Object component) {
        return (ActEditGeneralModeBinding) bind(component, view, R.layout.act_edit_general_mode);
    }
}