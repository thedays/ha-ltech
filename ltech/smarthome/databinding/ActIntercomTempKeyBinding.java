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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM;

/* loaded from: classes3.dex */
public abstract class ActIntercomTempKeyBinding extends ViewDataBinding {
    public final AppCompatImageView ivEffectiveTimeGo;
    public final AppCompatImageView ivExpirationTimeGo;
    public final AppCompatImageView ivKeyCountGo;
    public final AppCompatImageView ivMessage;
    public final AppCompatImageView ivWechat;
    public final ConstraintLayout layoutEffectiveTime;
    public final ConstraintLayout layoutExpirationTime;
    public final ConstraintLayout layoutKeyCount;
    public final LinearLayout layoutSetKey;
    public final ConstraintLayout layoutShowKey;
    public final View line;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomTempKeyVM mViewmodel;
    public final AppCompatTextView shareLine;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEffectiveTime;
    public final AppCompatTextView tvEffectiveTimeTip;
    public final AppCompatTextView tvExpirationTime;
    public final AppCompatTextView tvExpirationTimeTip;
    public final AppCompatTextView tvKeyCount;
    public final AppCompatTextView tvKeyCountTip;
    public final AppCompatTextView tvTempEffectiveTime;
    public final AppCompatTextView tvTempEffectiveTimeTip;
    public final AppCompatTextView tvTempExpirationTime;
    public final AppCompatTextView tvTempExpirationTimeTip;
    public final AppCompatTextView tvTempKey;
    public final AppCompatTextView tvTempKeyCount;
    public final AppCompatTextView tvTempKeyTip;
    public final AppCompatTextView tvWechat;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomTempKeyVM viewmodel);

    protected ActIntercomTempKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivEffectiveTimeGo, AppCompatImageView ivExpirationTimeGo, AppCompatImageView ivKeyCountGo, AppCompatImageView ivMessage, AppCompatImageView ivWechat, ConstraintLayout layoutEffectiveTime, ConstraintLayout layoutExpirationTime, ConstraintLayout layoutKeyCount, LinearLayout layoutSetKey, ConstraintLayout layoutShowKey, View line, AppCompatTextView shareLine, LayoutTitleDefaultBinding title, AppCompatTextView tvEffectiveTime, AppCompatTextView tvEffectiveTimeTip, AppCompatTextView tvExpirationTime, AppCompatTextView tvExpirationTimeTip, AppCompatTextView tvKeyCount, AppCompatTextView tvKeyCountTip, AppCompatTextView tvTempEffectiveTime, AppCompatTextView tvTempEffectiveTimeTip, AppCompatTextView tvTempExpirationTime, AppCompatTextView tvTempExpirationTimeTip, AppCompatTextView tvTempKey, AppCompatTextView tvTempKeyCount, AppCompatTextView tvTempKeyTip, AppCompatTextView tvWechat) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivEffectiveTimeGo = ivEffectiveTimeGo;
        this.ivExpirationTimeGo = ivExpirationTimeGo;
        this.ivKeyCountGo = ivKeyCountGo;
        this.ivMessage = ivMessage;
        this.ivWechat = ivWechat;
        this.layoutEffectiveTime = layoutEffectiveTime;
        this.layoutExpirationTime = layoutExpirationTime;
        this.layoutKeyCount = layoutKeyCount;
        this.layoutSetKey = layoutSetKey;
        this.layoutShowKey = layoutShowKey;
        this.line = line;
        this.shareLine = shareLine;
        this.title = title;
        this.tvEffectiveTime = tvEffectiveTime;
        this.tvEffectiveTimeTip = tvEffectiveTimeTip;
        this.tvExpirationTime = tvExpirationTime;
        this.tvExpirationTimeTip = tvExpirationTimeTip;
        this.tvKeyCount = tvKeyCount;
        this.tvKeyCountTip = tvKeyCountTip;
        this.tvTempEffectiveTime = tvTempEffectiveTime;
        this.tvTempEffectiveTimeTip = tvTempEffectiveTimeTip;
        this.tvTempExpirationTime = tvTempExpirationTime;
        this.tvTempExpirationTimeTip = tvTempExpirationTimeTip;
        this.tvTempKey = tvTempKey;
        this.tvTempKeyCount = tvTempKeyCount;
        this.tvTempKeyTip = tvTempKeyTip;
        this.tvWechat = tvWechat;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIntercomTempKeyVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomTempKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomTempKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomTempKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_temp_key, root, attachToRoot, component);
    }

    public static ActIntercomTempKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomTempKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomTempKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_temp_key, null, false, component);
    }

    public static ActIntercomTempKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomTempKeyBinding bind(View view, Object component) {
        return (ActIntercomTempKeyBinding) bind(component, view, R.layout.act_intercom_temp_key);
    }
}