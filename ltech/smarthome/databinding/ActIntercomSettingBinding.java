package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomSettingVM;

/* loaded from: classes3.dex */
public abstract class ActIntercomSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivFaceGo;
    public final AppCompatImageView ivUnlockGo;
    public final AppCompatImageView ivWriteAddressGo;
    public final ConstraintLayout layoutFace;
    public final ConstraintLayout layoutUnlock;
    public final ConstraintLayout layoutWriteAddress;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvFace;
    public final AppCompatTextView tvFaceTip;
    public final AppCompatTextView tvUnlock;
    public final AppCompatTextView tvUnlockTip;
    public final AppCompatTextView tvWriteAddress;
    public final AppCompatTextView tvWriteAddressStatus;
    public final AppCompatTextView tvWriteAddressTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomSettingVM viewmodel);

    protected ActIntercomSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivFaceGo, AppCompatImageView ivUnlockGo, AppCompatImageView ivWriteAddressGo, ConstraintLayout layoutFace, ConstraintLayout layoutUnlock, ConstraintLayout layoutWriteAddress, LayoutTitleDefaultBinding title, AppCompatTextView tvFace, AppCompatTextView tvFaceTip, AppCompatTextView tvUnlock, AppCompatTextView tvUnlockTip, AppCompatTextView tvWriteAddress, AppCompatTextView tvWriteAddressStatus, AppCompatTextView tvWriteAddressTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivFaceGo = ivFaceGo;
        this.ivUnlockGo = ivUnlockGo;
        this.ivWriteAddressGo = ivWriteAddressGo;
        this.layoutFace = layoutFace;
        this.layoutUnlock = layoutUnlock;
        this.layoutWriteAddress = layoutWriteAddress;
        this.title = title;
        this.tvFace = tvFace;
        this.tvFaceTip = tvFaceTip;
        this.tvUnlock = tvUnlock;
        this.tvUnlockTip = tvUnlockTip;
        this.tvWriteAddress = tvWriteAddress;
        this.tvWriteAddressStatus = tvWriteAddressStatus;
        this.tvWriteAddressTip = tvWriteAddressTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIntercomSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_setting, root, attachToRoot, component);
    }

    public static ActIntercomSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_setting, null, false, component);
    }

    public static ActIntercomSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSettingBinding bind(View view, Object component) {
        return (ActIntercomSettingBinding) bind(component, view, R.layout.act_intercom_setting);
    }
}