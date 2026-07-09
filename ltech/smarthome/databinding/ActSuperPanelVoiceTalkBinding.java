package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelVoiceTalkBinding extends ViewDataBinding {
    public final AppCompatImageView imgTip1;
    public final AppCompatImageView imgTip2;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected Boolean mVoiceVisible;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatButton tvActive;
    public final AppCompatButton tvCall;

    public abstract void setTitle(TitleDefault title);

    public abstract void setVoiceVisible(Boolean voiceVisible);

    protected ActSuperPanelVoiceTalkBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView imgTip1, AppCompatImageView imgTip2, LayoutTitleDefaultBinding title, AppCompatButton tvActive, AppCompatButton tvCall) {
        super(_bindingComponent, _root, _localFieldCount);
        this.imgTip1 = imgTip1;
        this.imgTip2 = imgTip2;
        this.title = title;
        this.tvActive = tvActive;
        this.tvCall = tvCall;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getVoiceVisible() {
        return this.mVoiceVisible;
    }

    public static ActSuperPanelVoiceTalkBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceTalkBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelVoiceTalkBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_voice_talk, root, attachToRoot, component);
    }

    public static ActSuperPanelVoiceTalkBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceTalkBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelVoiceTalkBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_voice_talk, null, false, component);
    }

    public static ActSuperPanelVoiceTalkBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceTalkBinding bind(View view, Object component) {
        return (ActSuperPanelVoiceTalkBinding) bind(component, view, R.layout.act_super_panel_voice_talk);
    }
}