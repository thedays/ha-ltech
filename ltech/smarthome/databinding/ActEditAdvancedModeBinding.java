package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.BaseEditAdvancedModeVM;
import com.ltech.smarthome.utils.selectedCountryLib.demo.RoundImageView;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActEditAdvancedModeBinding extends ViewDataBinding {
    public final AppCompatTextView changeIcon;
    public final Guideline guideline;
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
    protected BaseEditAdvancedModeVM mViewmodel;
    public final AppCompatTextView reset;
    public final SwipeRecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvImportMode;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvPlayTimes;
    public final AppCompatTextView tvPreview;
    public final AppCompatTextView tvPreview1;
    public final AppCompatTextView tvTimesNumber;
    public final AppCompatTextView tvTotalTime;
    public final AppCompatTextView tvTotalTime1;
    public final View vPreview1;
    public final LinearLayout vPreview3;
    public final LinearLayout vPreview3Off;
    public final View view23;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(BaseEditAdvancedModeVM viewmodel);

    protected ActEditAdvancedModeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView changeIcon, Guideline guideline, AppCompatImageView iconMore, AppCompatImageView iconMore1, AppCompatImageView iconMore2, ConstraintLayout itemIcon, ConstraintLayout itemName, ConstraintLayout itemTimes, RoundImageView iv, ScrollView layoutLoad, AppCompatTextView reset, SwipeRecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvDeviceName, AppCompatTextView tvImportMode, AppCompatTextView tvNameTip, AppCompatTextView tvPlayTimes, AppCompatTextView tvPreview, AppCompatTextView tvPreview1, AppCompatTextView tvTimesNumber, AppCompatTextView tvTotalTime, AppCompatTextView tvTotalTime1, View vPreview1, LinearLayout vPreview3, LinearLayout vPreview3Off, View view23) {
        super(_bindingComponent, _root, _localFieldCount);
        this.changeIcon = changeIcon;
        this.guideline = guideline;
        this.iconMore = iconMore;
        this.iconMore1 = iconMore1;
        this.iconMore2 = iconMore2;
        this.itemIcon = itemIcon;
        this.itemName = itemName;
        this.itemTimes = itemTimes;
        this.iv = iv;
        this.layoutLoad = layoutLoad;
        this.reset = reset;
        this.rvContent = rvContent;
        this.title = title;
        this.tvDeviceName = tvDeviceName;
        this.tvImportMode = tvImportMode;
        this.tvNameTip = tvNameTip;
        this.tvPlayTimes = tvPlayTimes;
        this.tvPreview = tvPreview;
        this.tvPreview1 = tvPreview1;
        this.tvTimesNumber = tvTimesNumber;
        this.tvTotalTime = tvTotalTime;
        this.tvTotalTime1 = tvTotalTime1;
        this.vPreview1 = vPreview1;
        this.vPreview3 = vPreview3;
        this.vPreview3Off = vPreview3Off;
        this.view23 = view23;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BaseEditAdvancedModeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActEditAdvancedModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditAdvancedModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEditAdvancedModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_advanced_mode, root, attachToRoot, component);
    }

    public static ActEditAdvancedModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditAdvancedModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEditAdvancedModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_advanced_mode, null, false, component);
    }

    public static ActEditAdvancedModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditAdvancedModeBinding bind(View view, Object component) {
        return (ActEditAdvancedModeBinding) bind(component, view, R.layout.act_edit_advanced_mode);
    }
}