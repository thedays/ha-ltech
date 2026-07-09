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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectColorCcBinding extends ViewDataBinding {
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
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCct;
    public final AppCompatTextView tvGeneral;
    public final AppCompatTextView tvGray;
    public final AppCompatTextView tvHsl;
    public final AppCompatTextView tvXyy;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectColorCcBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout fragmentContainer, LinearLayout layoutTab, View line1, View line2, View line3, View line4, LayoutTitleDefaultBinding title, AppCompatTextView tvCct, AppCompatTextView tvGeneral, AppCompatTextView tvGray, AppCompatTextView tvHsl, AppCompatTextView tvXyy) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fragmentContainer = fragmentContainer;
        this.layoutTab = layoutTab;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
        this.title = title;
        this.tvCct = tvCct;
        this.tvGeneral = tvGeneral;
        this.tvGray = tvGray;
        this.tvHsl = tvHsl;
        this.tvXyy = tvXyy;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectColorCcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectColorCcBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectColorCcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_color_cc, root, attachToRoot, component);
    }

    public static ActSelectColorCcBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectColorCcBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectColorCcBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_color_cc, null, false, component);
    }

    public static ActSelectColorCcBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectColorCcBinding bind(View view, Object component) {
        return (ActSelectColorCcBinding) bind(component, view, R.layout.act_select_color_cc);
    }
}