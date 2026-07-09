package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.MyTimePickerView;

/* loaded from: classes3.dex */
public abstract class ActSelectTemperatureWeatherBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView19;
    public final AppCompatTextView appCompatTextView18;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final MyTimePickerView pickViewSymbol;
    public final MyTimePickerView pickViewTem;
    public final LayoutTitleDefaultBinding title;
    public final View view30;
    public final View view32;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectTemperatureWeatherBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView19, AppCompatTextView appCompatTextView18, MyTimePickerView pickViewSymbol, MyTimePickerView pickViewTem, LayoutTitleDefaultBinding title, View view30, View view32) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView19 = appCompatImageView19;
        this.appCompatTextView18 = appCompatTextView18;
        this.pickViewSymbol = pickViewSymbol;
        this.pickViewTem = pickViewTem;
        this.title = title;
        this.view30 = view30;
        this.view32 = view32;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectTemperatureWeatherBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectTemperatureWeatherBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectTemperatureWeatherBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_temperature_weather, root, attachToRoot, component);
    }

    public static ActSelectTemperatureWeatherBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectTemperatureWeatherBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectTemperatureWeatherBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_temperature_weather, null, false, component);
    }

    public static ActSelectTemperatureWeatherBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectTemperatureWeatherBinding bind(View view, Object component) {
        return (ActSelectTemperatureWeatherBinding) bind(component, view, R.layout.act_select_temperature_weather);
    }
}