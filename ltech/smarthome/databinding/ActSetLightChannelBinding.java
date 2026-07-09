package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSetLightChannelBinding extends ViewDataBinding {
    public final LinearLayout layoutRoot;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvOutput;
    public final AppCompatTextView tvPort;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActSetLightChannelBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutRoot, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvOutput, AppCompatTextView tvPort, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutRoot = layoutRoot;
        this.rvContent = rvContent;
        this.title = title;
        this.tvOutput = tvOutput;
        this.tvPort = tvPort;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSetLightChannelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSetLightChannelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSetLightChannelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_set_light_channel, root, attachToRoot, component);
    }

    public static ActSetLightChannelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSetLightChannelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSetLightChannelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_set_light_channel, null, false, component);
    }

    public static ActSetLightChannelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSetLightChannelBinding bind(View view, Object component) {
        return (ActSetLightChannelBinding) bind(component, view, R.layout.act_set_light_channel);
    }
}