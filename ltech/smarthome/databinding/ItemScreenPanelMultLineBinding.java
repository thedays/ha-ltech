package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemScreenPanelMultLineBinding extends ViewDataBinding {
    public final LinearLayout imaginaryLine;
    public final AppCompatImageView ivScreen;
    public final LinearLayout layoutItemBg;
    public final AppCompatTextView tvScreen;

    protected ItemScreenPanelMultLineBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout imaginaryLine, AppCompatImageView ivScreen, LinearLayout layoutItemBg, AppCompatTextView tvScreen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.imaginaryLine = imaginaryLine;
        this.ivScreen = ivScreen;
        this.layoutItemBg = layoutItemBg;
        this.tvScreen = tvScreen;
    }

    public static ItemScreenPanelMultLineBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenPanelMultLineBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemScreenPanelMultLineBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_panel_mult_line, root, attachToRoot, component);
    }

    public static ItemScreenPanelMultLineBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenPanelMultLineBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemScreenPanelMultLineBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_panel_mult_line, null, false, component);
    }

    public static ItemScreenPanelMultLineBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenPanelMultLineBinding bind(View view, Object component) {
        return (ItemScreenPanelMultLineBinding) bind(component, view, R.layout.item_screen_panel_mult_line);
    }
}