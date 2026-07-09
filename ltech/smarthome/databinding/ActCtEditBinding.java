package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.VerticalRangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActCtLightVM;

/* loaded from: classes3.dex */
public abstract class ActCtEditBinding extends ViewDataBinding {
    public final VerticalRangeSeekBar ctsb;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActCtLightVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvWy;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActCtLightVM viewmodel);

    protected ActCtEditBinding(Object _bindingComponent, View _root, int _localFieldCount, VerticalRangeSeekBar ctsb, LayoutTitleDefaultBinding title, AppCompatTextView tvWy) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ctsb = ctsb;
        this.title = title;
        this.tvWy = tvWy;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActCtLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActCtEditBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtEditBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCtEditBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ct_edit, root, attachToRoot, component);
    }

    public static ActCtEditBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtEditBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCtEditBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ct_edit, null, false, component);
    }

    public static ActCtEditBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtEditBinding bind(View view, Object component) {
        return (ActCtEditBinding) bind(component, view, R.layout.act_ct_edit);
    }
}