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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.control.ActSmartSpeakerVM;

/* loaded from: classes3.dex */
public abstract class ActSmartSpeakerDetailBinding extends ViewDataBinding {
    public final AppCompatImageView ivSmartSpeaker;
    public final ConstraintLayout layoutLoad;
    public final LinearLayout layoutSave;
    public final LinearLayout layoutSmartSpeaker;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartSpeakerVM mViewmodel;
    public final RecyclerView rvSmartSpeaker;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvSmartSpeaker;
    public final AppCompatTextView tvSmartSpeakerTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartSpeakerVM viewmodel);

    protected ActSmartSpeakerDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSmartSpeaker, ConstraintLayout layoutLoad, LinearLayout layoutSave, LinearLayout layoutSmartSpeaker, RecyclerView rvSmartSpeaker, AppCompatTextView tvBottom, AppCompatTextView tvSmartSpeaker, AppCompatTextView tvSmartSpeakerTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSmartSpeaker = ivSmartSpeaker;
        this.layoutLoad = layoutLoad;
        this.layoutSave = layoutSave;
        this.layoutSmartSpeaker = layoutSmartSpeaker;
        this.rvSmartSpeaker = rvSmartSpeaker;
        this.tvBottom = tvBottom;
        this.tvSmartSpeaker = tvSmartSpeaker;
        this.tvSmartSpeakerTip = tvSmartSpeakerTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartSpeakerVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSmartSpeakerDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartSpeakerDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSmartSpeakerDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_speaker_detail, root, attachToRoot, component);
    }

    public static ActSmartSpeakerDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartSpeakerDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSmartSpeakerDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_speaker_detail, null, false, component);
    }

    public static ActSmartSpeakerDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartSpeakerDetailBinding bind(View view, Object component) {
        return (ActSmartSpeakerDetailBinding) bind(component, view, R.layout.act_smart_speaker_detail);
    }
}