package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActVoiceCallBindBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActVoiceCallBindBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rv, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rv = rv;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActVoiceCallBindBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallBindBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActVoiceCallBindBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_bind, root, attachToRoot, component);
    }

    public static ActVoiceCallBindBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallBindBinding inflate(LayoutInflater inflater, Object component) {
        return (ActVoiceCallBindBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_bind, null, false, component);
    }

    public static ActVoiceCallBindBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallBindBinding bind(View view, Object component) {
        return (ActVoiceCallBindBinding) bind(component, view, R.layout.act_voice_call_bind);
    }
}