package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParamVM;
import com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView;
import com.ltech.smarthome.view.DaliTextSeekBarView;
import com.ltech.smarthome.view.DaliTextSeekBarViewNew;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public abstract class ActDaliBatchModifyParamBinding extends ViewDataBinding {
    public final NestedScrollView actAddDeviceScroll;
    public final CgdActionSelectView failureActionView;
    public final AppCompatImageView ivLinearLog;
    public final AppCompatImageView ivSceneNameGo;
    public final ConstraintLayout layoutBrt;
    public final LinearLayout layoutCtRange;
    public final ConstraintLayout layoutDimCurve;
    public final LinearLayout layoutDimRange;
    public final LinearLayout layoutExtendFadeTime;
    public final LinearLayout layoutFadeTime;
    public final LinearLayout layoutFailureState;
    public final LinearLayout layoutLightOnState;
    public final LinearLayout layoutSave;
    public final ConstraintLayout layoutSelectModifyParam;
    public final CgdActionSelectView lightOnActionView;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActDaliBatchModifyParamVM mViewmodel;
    public final RadioImageTextView radioCtCustom;
    public final RadioImageTextView radioCtDefault;
    public final RadioImageTextView radioFailureCustom;
    public final RadioImageTextView radioFailureDefault;
    public final RadioImageTextView radioFailureMemory;
    public final RadioImageTextView radioFailureNotLight;
    public final RadioImageTextView radioLightOnCustom;
    public final RadioImageTextView radioLightOnDefault;
    public final RadioImageTextView radioLightOnMemory;
    public final RadioImageTextView radioLightOnNotLight;
    public final RadioImageTextView radioLinear;
    public final RadioImageTextView radioLog;
    public final RangeSeekBar sbBrt;
    public final DaliTextSeekBarViewNew seekbarFadeTime;
    public final DaliTextSeekBarView seekbarMaxBtr;
    public final DaliTextSeekBarView seekbarMinBtr;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvDimCurve;
    public final AppCompatTextView tvExtendFadeTime;
    public final AppCompatTextView tvFadeTimeEqual;
    public final AppCompatTextView tvFadeTimeTimes;
    public final AppCompatTextView tvFadeTimeTotal;
    public final AppCompatTextView tvFadeTimeValue;
    public final AppCompatTextView tvFadeTimeX;
    public final AppCompatTextView tvKLeft;
    public final AppCompatTextView tvKRight;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActDaliBatchModifyParamVM viewmodel);

    protected ActDaliBatchModifyParamBinding(Object _bindingComponent, View _root, int _localFieldCount, NestedScrollView actAddDeviceScroll, CgdActionSelectView failureActionView, AppCompatImageView ivLinearLog, AppCompatImageView ivSceneNameGo, ConstraintLayout layoutBrt, LinearLayout layoutCtRange, ConstraintLayout layoutDimCurve, LinearLayout layoutDimRange, LinearLayout layoutExtendFadeTime, LinearLayout layoutFadeTime, LinearLayout layoutFailureState, LinearLayout layoutLightOnState, LinearLayout layoutSave, ConstraintLayout layoutSelectModifyParam, CgdActionSelectView lightOnActionView, RadioImageTextView radioCtCustom, RadioImageTextView radioCtDefault, RadioImageTextView radioFailureCustom, RadioImageTextView radioFailureDefault, RadioImageTextView radioFailureMemory, RadioImageTextView radioFailureNotLight, RadioImageTextView radioLightOnCustom, RadioImageTextView radioLightOnDefault, RadioImageTextView radioLightOnMemory, RadioImageTextView radioLightOnNotLight, RadioImageTextView radioLinear, RadioImageTextView radioLog, RangeSeekBar sbBrt, DaliTextSeekBarViewNew seekbarFadeTime, DaliTextSeekBarView seekbarMaxBtr, DaliTextSeekBarView seekbarMinBtr, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom, AppCompatTextView tvDimCurve, AppCompatTextView tvExtendFadeTime, AppCompatTextView tvFadeTimeEqual, AppCompatTextView tvFadeTimeTimes, AppCompatTextView tvFadeTimeTotal, AppCompatTextView tvFadeTimeValue, AppCompatTextView tvFadeTimeX, AppCompatTextView tvKLeft, AppCompatTextView tvKRight) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actAddDeviceScroll = actAddDeviceScroll;
        this.failureActionView = failureActionView;
        this.ivLinearLog = ivLinearLog;
        this.ivSceneNameGo = ivSceneNameGo;
        this.layoutBrt = layoutBrt;
        this.layoutCtRange = layoutCtRange;
        this.layoutDimCurve = layoutDimCurve;
        this.layoutDimRange = layoutDimRange;
        this.layoutExtendFadeTime = layoutExtendFadeTime;
        this.layoutFadeTime = layoutFadeTime;
        this.layoutFailureState = layoutFailureState;
        this.layoutLightOnState = layoutLightOnState;
        this.layoutSave = layoutSave;
        this.layoutSelectModifyParam = layoutSelectModifyParam;
        this.lightOnActionView = lightOnActionView;
        this.radioCtCustom = radioCtCustom;
        this.radioCtDefault = radioCtDefault;
        this.radioFailureCustom = radioFailureCustom;
        this.radioFailureDefault = radioFailureDefault;
        this.radioFailureMemory = radioFailureMemory;
        this.radioFailureNotLight = radioFailureNotLight;
        this.radioLightOnCustom = radioLightOnCustom;
        this.radioLightOnDefault = radioLightOnDefault;
        this.radioLightOnMemory = radioLightOnMemory;
        this.radioLightOnNotLight = radioLightOnNotLight;
        this.radioLinear = radioLinear;
        this.radioLog = radioLog;
        this.sbBrt = sbBrt;
        this.seekbarFadeTime = seekbarFadeTime;
        this.seekbarMaxBtr = seekbarMaxBtr;
        this.seekbarMinBtr = seekbarMinBtr;
        this.title = title;
        this.tvBottom = tvBottom;
        this.tvDimCurve = tvDimCurve;
        this.tvExtendFadeTime = tvExtendFadeTime;
        this.tvFadeTimeEqual = tvFadeTimeEqual;
        this.tvFadeTimeTimes = tvFadeTimeTimes;
        this.tvFadeTimeTotal = tvFadeTimeTotal;
        this.tvFadeTimeValue = tvFadeTimeValue;
        this.tvFadeTimeX = tvFadeTimeX;
        this.tvKLeft = tvKLeft;
        this.tvKRight = tvKRight;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActDaliBatchModifyParamVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActDaliBatchModifyParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliBatchModifyParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDaliBatchModifyParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_batch_modify_param, root, attachToRoot, component);
    }

    public static ActDaliBatchModifyParamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliBatchModifyParamBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDaliBatchModifyParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dali_batch_modify_param, null, false, component);
    }

    public static ActDaliBatchModifyParamBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDaliBatchModifyParamBinding bind(View view, Object component) {
        return (ActDaliBatchModifyParamBinding) bind(component, view, R.layout.act_dali_batch_modify_param);
    }
}