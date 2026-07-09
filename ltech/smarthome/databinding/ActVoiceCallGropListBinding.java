package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActVoiceCallGropListBinding extends ViewDataBinding {
    public final FrameLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActVoiceCallGropListBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutLoad, RecyclerView rv, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rv = rv;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActVoiceCallGropListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallGropListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActVoiceCallGropListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_grop_list, root, attachToRoot, component);
    }

    public static ActVoiceCallGropListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallGropListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActVoiceCallGropListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_voice_call_grop_list, null, false, component);
    }

    public static ActVoiceCallGropListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActVoiceCallGropListBinding bind(View view, Object component) {
        return (ActVoiceCallGropListBinding) bind(component, view, R.layout.act_voice_call_grop_list);
    }
}