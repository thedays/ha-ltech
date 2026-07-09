package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActSelectVoiceSpeakBinding extends ViewDataBinding {
    public final EditText etContent;
    public final LinearLayout layoutBar;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected String mVoiceContent;
    public final LightBrtBar sbVolume;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvInputWords;
    public final TextView tvPlayVoiceTips;
    public final AppCompatTextView tvVolume;

    public abstract void setTitle(TitleDefault title);

    public abstract void setVoiceContent(String voiceContent);

    protected ActSelectVoiceSpeakBinding(Object _bindingComponent, View _root, int _localFieldCount, EditText etContent, LinearLayout layoutBar, LightBrtBar sbVolume, LayoutTitleDefaultBinding title, TextView tvInputWords, TextView tvPlayVoiceTips, AppCompatTextView tvVolume) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etContent = etContent;
        this.layoutBar = layoutBar;
        this.sbVolume = sbVolume;
        this.title = title;
        this.tvInputWords = tvInputWords;
        this.tvPlayVoiceTips = tvPlayVoiceTips;
        this.tvVolume = tvVolume;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getVoiceContent() {
        return this.mVoiceContent;
    }

    public static ActSelectVoiceSpeakBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectVoiceSpeakBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectVoiceSpeakBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_voice_speak, root, attachToRoot, component);
    }

    public static ActSelectVoiceSpeakBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectVoiceSpeakBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectVoiceSpeakBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_voice_speak, null, false, component);
    }

    public static ActSelectVoiceSpeakBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectVoiceSpeakBinding bind(View view, Object component) {
        return (ActSelectVoiceSpeakBinding) bind(component, view, R.layout.act_select_voice_speak);
    }
}