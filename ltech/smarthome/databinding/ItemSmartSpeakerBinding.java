package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSmartSpeakerBinding extends ViewDataBinding {
    public final AppCompatImageView icSpeaker;
    public final AppCompatImageView ivGo;
    public final ConstraintLayout layoutNormal;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvTip;

    protected ItemSmartSpeakerBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView icSpeaker, AppCompatImageView ivGo, ConstraintLayout layoutNormal, AppCompatTextView tvMain, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.icSpeaker = icSpeaker;
        this.ivGo = ivGo;
        this.layoutNormal = layoutNormal;
        this.tvMain = tvMain;
        this.tvTip = tvTip;
    }

    public static ItemSmartSpeakerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartSpeakerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSmartSpeakerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_speaker, root, attachToRoot, component);
    }

    public static ItemSmartSpeakerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartSpeakerBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSmartSpeakerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_smart_speaker, null, false, component);
    }

    public static ItemSmartSpeakerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSmartSpeakerBinding bind(View view, Object component) {
        return (ItemSmartSpeakerBinding) bind(component, view, R.layout.item_smart_speaker);
    }
}