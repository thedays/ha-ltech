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
import com.ltech.smarthome.ui.device.light.ActAutoNetTimeSettingVM;

/* loaded from: classes3.dex */
public abstract class ActAutoNetTimeSettingBinding extends ViewDataBinding {

    @Bindable
    protected String mNameText;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAutoNetTimeSettingVM mViewmodel;
    public final RecyclerView pickViewMin;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSecTip;

    public abstract void setNameText(String nameText);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAutoNetTimeSettingVM viewmodel);

    protected ActAutoNetTimeSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView pickViewMin, LayoutTitleDefaultBinding title, AppCompatTextView tvSecTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.pickViewMin = pickViewMin;
        this.title = title;
        this.tvSecTip = tvSecTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getNameText() {
        return this.mNameText;
    }

    public ActAutoNetTimeSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAutoNetTimeSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAutoNetTimeSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAutoNetTimeSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_auto_net_time_setting, root, attachToRoot, component);
    }

    public static ActAutoNetTimeSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAutoNetTimeSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAutoNetTimeSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_auto_net_time_setting, null, false, component);
    }

    public static ActAutoNetTimeSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAutoNetTimeSettingBinding bind(View view, Object component) {
        return (ActAutoNetTimeSettingBinding) bind(component, view, R.layout.act_auto_net_time_setting);
    }
}