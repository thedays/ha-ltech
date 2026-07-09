package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectConditionDeviceBinding extends ViewDataBinding {
    public final Guideline guideline;
    public final AppCompatImageView iv;
    public final AppCompatImageView iv2;
    public final AppCompatImageView ivDevice;
    public final LinearLayout layoutPic;
    public final LinearLayout layoutRoot;
    public final ConstraintLayout layoutS6;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvFirst;
    public final AppCompatTextView tvLogoTip;
    public final AppCompatTextView tvSecond;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectConditionDeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline, AppCompatImageView iv, AppCompatImageView iv2, AppCompatImageView ivDevice, LinearLayout layoutPic, LinearLayout layoutRoot, ConstraintLayout layoutS6, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvFirst, AppCompatTextView tvLogoTip, AppCompatTextView tvSecond, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline = guideline;
        this.iv = iv;
        this.iv2 = iv2;
        this.ivDevice = ivDevice;
        this.layoutPic = layoutPic;
        this.layoutRoot = layoutRoot;
        this.layoutS6 = layoutS6;
        this.rvContent = rvContent;
        this.title = title;
        this.tvFirst = tvFirst;
        this.tvLogoTip = tvLogoTip;
        this.tvSecond = tvSecond;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectConditionDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectConditionDeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectConditionDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_condition_device, root, attachToRoot, component);
    }

    public static ActSelectConditionDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectConditionDeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectConditionDeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_condition_device, null, false, component);
    }

    public static ActSelectConditionDeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectConditionDeviceBinding bind(View view, Object component) {
        return (ActSelectConditionDeviceBinding) bind(component, view, R.layout.act_select_condition_device);
    }
}