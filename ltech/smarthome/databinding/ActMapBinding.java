package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.amap.api.maps2d.MapView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.StepSeekBar;

/* loaded from: classes3.dex */
public abstract class ActMapBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView31;
    public final AppCompatTextView appCompatTextView32;
    public final LinearLayout layoutSearch;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final MapView mapView;
    public final StepSeekBar stepSeekBar;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAddress;
    public final AppCompatTextView tvLocationName;
    public final AppCompatTextView tvRange;
    public final View view14;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActMapBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView31, AppCompatTextView appCompatTextView32, LinearLayout layoutSearch, MapView mapView, StepSeekBar stepSeekBar, LayoutTitleDefaultBinding title, AppCompatTextView tvAddress, AppCompatTextView tvLocationName, AppCompatTextView tvRange, View view14) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView31 = appCompatTextView31;
        this.appCompatTextView32 = appCompatTextView32;
        this.layoutSearch = layoutSearch;
        this.mapView = mapView;
        this.stepSeekBar = stepSeekBar;
        this.title = title;
        this.tvAddress = tvAddress;
        this.tvLocationName = tvLocationName;
        this.tvRange = tvRange;
        this.view14 = view14;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActMapBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMapBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMapBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_map, root, attachToRoot, component);
    }

    public static ActMapBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMapBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMapBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_map, null, false, component);
    }

    public static ActMapBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMapBinding bind(View view, Object component) {
        return (ActMapBinding) bind(component, view, R.layout.act_map);
    }
}