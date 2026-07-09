package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.imageclip.ClipViewLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomSettingVM;

/* loaded from: classes3.dex */
public abstract class ActIntercomSetFaceBinding extends ViewDataBinding {
    public final ClipViewLayout clipViewLayout;
    public final FrameLayout container;
    public final AppCompatImageView ivFace;
    public final AppCompatImageView ivFaceBg;
    public final AppCompatImageView ivFaceSuccess;
    public final ConstraintLayout layoutRecordStart;
    public final ConstraintLayout layoutRecordSuccess;
    public final ConstraintLayout layoutRecording;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomSettingVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeleteFace;
    public final AppCompatTextView tvFaceSuccess;
    public final AppCompatTextView tvFaceSuccessTip;
    public final AppCompatTextView tvFaceTip;
    public final AppCompatTextView tvStartRecord;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomSettingVM viewmodel);

    protected ActIntercomSetFaceBinding(Object _bindingComponent, View _root, int _localFieldCount, ClipViewLayout clipViewLayout, FrameLayout container, AppCompatImageView ivFace, AppCompatImageView ivFaceBg, AppCompatImageView ivFaceSuccess, ConstraintLayout layoutRecordStart, ConstraintLayout layoutRecordSuccess, ConstraintLayout layoutRecording, LayoutTitleDefaultBinding title, AppCompatTextView tvDeleteFace, AppCompatTextView tvFaceSuccess, AppCompatTextView tvFaceSuccessTip, AppCompatTextView tvFaceTip, AppCompatTextView tvStartRecord) {
        super(_bindingComponent, _root, _localFieldCount);
        this.clipViewLayout = clipViewLayout;
        this.container = container;
        this.ivFace = ivFace;
        this.ivFaceBg = ivFaceBg;
        this.ivFaceSuccess = ivFaceSuccess;
        this.layoutRecordStart = layoutRecordStart;
        this.layoutRecordSuccess = layoutRecordSuccess;
        this.layoutRecording = layoutRecording;
        this.title = title;
        this.tvDeleteFace = tvDeleteFace;
        this.tvFaceSuccess = tvFaceSuccess;
        this.tvFaceSuccessTip = tvFaceSuccessTip;
        this.tvFaceTip = tvFaceTip;
        this.tvStartRecord = tvStartRecord;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIntercomSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomSetFaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSetFaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomSetFaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_set_face, root, attachToRoot, component);
    }

    public static ActIntercomSetFaceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSetFaceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomSetFaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_set_face, null, false, component);
    }

    public static ActIntercomSetFaceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSetFaceBinding bind(View view, Object component) {
        return (ActIntercomSetFaceBinding) bind(component, view, R.layout.act_intercom_set_face);
    }
}