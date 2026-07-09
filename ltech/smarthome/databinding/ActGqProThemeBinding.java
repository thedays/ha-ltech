package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.gqpro.ActGqProVM;

/* loaded from: classes3.dex */
public abstract class ActGqProThemeBinding extends ViewDataBinding {
    public final Group groupSync;
    public final AppCompatImageView ivSync;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActGqProVM mViewmodel;
    public final RecyclerView rv;
    public final LayoutTitleTransparentBinding title;
    public final AppCompatTextView tvDel;
    public final AppCompatTextView tvSync;
    public final View viewSync;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActGqProVM viewmodel);

    protected ActGqProThemeBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupSync, AppCompatImageView ivSync, RecyclerView rv, LayoutTitleTransparentBinding title, AppCompatTextView tvDel, AppCompatTextView tvSync, View viewSync) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupSync = groupSync;
        this.ivSync = ivSync;
        this.rv = rv;
        this.title = title;
        this.tvDel = tvDel;
        this.tvSync = tvSync;
        this.viewSync = viewSync;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActGqProVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActGqProThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqProThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActGqProThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gq_pro_theme, root, attachToRoot, component);
    }

    public static ActGqProThemeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqProThemeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActGqProThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gq_pro_theme, null, false, component);
    }

    public static ActGqProThemeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqProThemeBinding bind(View view, Object component) {
        return (ActGqProThemeBinding) bind(component, view, R.layout.act_gq_pro_theme);
    }
}