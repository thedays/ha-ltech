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
public abstract class ActBleCurtainMotorTypeSettingBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActBleCurtainMotorTypeSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActBleCurtainMotorTypeSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorTypeSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBleCurtainMotorTypeSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_curtain_motor_type_setting, root, attachToRoot, component);
    }

    public static ActBleCurtainMotorTypeSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorTypeSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBleCurtainMotorTypeSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ble_curtain_motor_type_setting, null, false, component);
    }

    public static ActBleCurtainMotorTypeSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBleCurtainMotorTypeSettingBinding bind(View view, Object component) {
        return (ActBleCurtainMotorTypeSettingBinding) bind(component, view, R.layout.act_ble_curtain_motor_type_setting);
    }
}