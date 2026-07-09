package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public abstract class ActDmxChannelSelectBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final StepSetView seekbarCh1;
    public final StepSetView seekbarCh2;
    public final StepSetView seekbarCh3;
    public final StepSetView seekbarCh4;
    public final StepSetView seekbarCh5;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActDmxChannelSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, StepSetView seekbarCh1, StepSetView seekbarCh2, StepSetView seekbarCh3, StepSetView seekbarCh4, StepSetView seekbarCh5, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.seekbarCh1 = seekbarCh1;
        this.seekbarCh2 = seekbarCh2;
        this.seekbarCh3 = seekbarCh3;
        this.seekbarCh4 = seekbarCh4;
        this.seekbarCh5 = seekbarCh5;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActDmxChannelSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDmxChannelSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDmxChannelSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dmx_channel_select, root, attachToRoot, component);
    }

    public static ActDmxChannelSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDmxChannelSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDmxChannelSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dmx_channel_select, null, false, component);
    }

    public static ActDmxChannelSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDmxChannelSelectBinding bind(View view, Object component) {
        return (ActDmxChannelSelectBinding) bind(component, view, R.layout.act_dmx_channel_select);
    }
}