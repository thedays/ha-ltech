package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActSmartPanelThemeBinding extends ViewDataBinding {
    public final AppCompatImageView ivStartTimeGo;
    public final TextView label;
    public final RelativeLayout layoutShow;
    public final ConstraintLayout layoutTime;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSmartPanelThemeVM mViewmodel;
    public final RecyclerView rv;
    public final SwitchButton sb;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvStartTime;
    public final AppCompatTextView tvTimeTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSmartPanelThemeVM viewmodel);

    protected ActSmartPanelThemeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivStartTimeGo, TextView label, RelativeLayout layoutShow, ConstraintLayout layoutTime, RecyclerView rv, SwitchButton sb, LayoutTitleDefaultBinding title, AppCompatTextView tvStartTime, AppCompatTextView tvTimeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivStartTimeGo = ivStartTimeGo;
        this.label = label;
        this.layoutShow = layoutShow;
        this.layoutTime = layoutTime;
        this.rv = rv;
        this.sb = sb;
        this.title = title;
        this.tvStartTime = tvStartTime;
        this.tvTimeTip = tvTimeTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSmartPanelThemeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSmartPanelThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSmartPanelThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_theme, root, attachToRoot, component);
    }

    public static ActSmartPanelThemeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelThemeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSmartPanelThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_smart_panel_theme, null, false, component);
    }

    public static ActSmartPanelThemeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSmartPanelThemeBinding bind(View view, Object component) {
        return (ActSmartPanelThemeBinding) bind(component, view, R.layout.act_smart_panel_theme);
    }
}