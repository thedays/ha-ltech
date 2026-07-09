package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSpiLightSettingBinding extends ViewDataBinding {
    public final AppCompatEditText editPixel;
    public final ConstraintLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView pickViewIc;
    public final RecyclerView pickerViewOrder;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvError;
    public final AppCompatTextView tvTip;
    public final View viewCenter;

    public abstract void setTitle(TitleDefault title);

    protected ActSpiLightSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText editPixel, ConstraintLayout layoutContent, RecyclerView pickViewIc, RecyclerView pickerViewOrder, LayoutTitleDefaultBinding title, AppCompatTextView tvError, AppCompatTextView tvTip, View viewCenter) {
        super(_bindingComponent, _root, _localFieldCount);
        this.editPixel = editPixel;
        this.layoutContent = layoutContent;
        this.pickViewIc = pickViewIc;
        this.pickerViewOrder = pickerViewOrder;
        this.title = title;
        this.tvError = tvError;
        this.tvTip = tvTip;
        this.viewCenter = viewCenter;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSpiLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiLightSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSpiLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_spi_light_setting, root, attachToRoot, component);
    }

    public static ActSpiLightSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiLightSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSpiLightSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_spi_light_setting, null, false, component);
    }

    public static ActSpiLightSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiLightSettingBinding bind(View view, Object component) {
        return (ActSpiLightSettingBinding) bind(component, view, R.layout.act_spi_light_setting);
    }
}