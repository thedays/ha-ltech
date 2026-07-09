package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelContinousTalkBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final ItemSwitchBinding talkSwitch;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActSuperPanelContinousTalkBinding(Object _bindingComponent, View _root, int _localFieldCount, ItemSwitchBinding talkSwitch, LayoutTitleDefaultBinding title, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.talkSwitch = talkSwitch;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSuperPanelContinousTalkBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelContinousTalkBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelContinousTalkBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_continous_talk, root, attachToRoot, component);
    }

    public static ActSuperPanelContinousTalkBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelContinousTalkBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelContinousTalkBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_continous_talk, null, false, component);
    }

    public static ActSuperPanelContinousTalkBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelContinousTalkBinding bind(View view, Object component) {
        return (ActSuperPanelContinousTalkBinding) bind(component, view, R.layout.act_super_panel_continous_talk);
    }
}