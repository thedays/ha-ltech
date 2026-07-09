package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActColorCCLightVM;

/* loaded from: classes3.dex */
public abstract class ActColorLightCcBinding extends ViewDataBinding {
    public final FrameLayout fragmentContainer;
    public final LinearLayout layoutTab;
    public final View line1;
    public final View line2;
    public final View line3;
    public final View line4;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActColorCCLightVM mViewmodel;
    public final RecyclerView rvAction;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCct;
    public final AppCompatTextView tvGeneral;
    public final AppCompatTextView tvGray;
    public final AppCompatTextView tvHsl;
    public final AppCompatTextView tvXyy;
    public final View vOpen;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActColorCCLightVM viewmodel);

    protected ActColorLightCcBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout fragmentContainer, LinearLayout layoutTab, View line1, View line2, View line3, View line4, RecyclerView rvAction, LayoutTitleDefaultBinding title, AppCompatTextView tvCct, AppCompatTextView tvGeneral, AppCompatTextView tvGray, AppCompatTextView tvHsl, AppCompatTextView tvXyy, View vOpen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.layoutTab = layoutTab;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
        this.rvAction = rvAction;
        this.title = title;
        this.tvCct = tvCct;
        this.tvGeneral = tvGeneral;
        this.tvGray = tvGray;
        this.tvHsl = tvHsl;
        this.tvXyy = tvXyy;
        this.vOpen = vOpen;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActColorCCLightVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActColorLightCcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActColorLightCcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActColorLightCcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_color_light_cc, root, attachToRoot, component);
    }

    public static ActColorLightCcBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActColorLightCcBinding inflate(LayoutInflater inflater, Object component) {
        return (ActColorLightCcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_color_light_cc, null, false, component);
    }

    public static ActColorLightCcBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActColorLightCcBinding bind(View view, Object component) {
        return (ActColorLightCcBinding) bind(component, view, R.layout.act_color_light_cc);
    }
}