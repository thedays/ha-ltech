package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSettingVM;

/* loaded from: classes3.dex */
public abstract class ActTrigCurtainChannelSetBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActTrigCurtainChannelSettingVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActTrigCurtainChannelSettingVM viewmodel);

    protected ActTrigCurtainChannelSetBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActTrigCurtainChannelSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActTrigCurtainChannelSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainChannelSetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTrigCurtainChannelSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain_channel_set, root, attachToRoot, component);
    }

    public static ActTrigCurtainChannelSetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainChannelSetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTrigCurtainChannelSetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_curtain_channel_set, null, false, component);
    }

    public static ActTrigCurtainChannelSetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigCurtainChannelSetBinding bind(View view, Object component) {
        return (ActTrigCurtainChannelSetBinding) bind(component, view, R.layout.act_trig_curtain_channel_set);
    }
}