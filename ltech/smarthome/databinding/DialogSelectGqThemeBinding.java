package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogSelectGqThemeBinding extends ViewDataBinding {
    public final AppCompatImageView ivBg;
    public final AppCompatImageView ivMidBg;
    public final LinearLayout layoutPoint;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rv;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectGqThemeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBg, AppCompatImageView ivMidBg, LinearLayout layoutPoint, RecyclerView rv, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBg = ivBg;
        this.ivMidBg = ivMidBg;
        this.layoutPoint = layoutPoint;
        this.rv = rv;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectGqThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectGqThemeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectGqThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_gq_theme, root, attachToRoot, component);
    }

    public static DialogSelectGqThemeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectGqThemeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectGqThemeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_gq_theme, null, false, component);
    }

    public static DialogSelectGqThemeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectGqThemeBinding bind(View view, Object component) {
        return (DialogSelectGqThemeBinding) bind(component, view, R.layout.dialog_select_gq_theme);
    }
}