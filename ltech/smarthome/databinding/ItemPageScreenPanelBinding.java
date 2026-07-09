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
public abstract class ItemPageScreenPanelBinding extends ViewDataBinding {
    public final AppCompatImageView ivScreen;
    public final AppCompatImageView ivScreen2;
    public final LinearLayout layoutItemBg;
    public final AppCompatTextView tvScreen;

    protected ItemPageScreenPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivScreen, AppCompatImageView ivScreen2, LinearLayout layoutItemBg, AppCompatTextView tvScreen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivScreen = ivScreen;
        this.ivScreen2 = ivScreen2;
        this.layoutItemBg = layoutItemBg;
        this.tvScreen = tvScreen;
    }

    public static ItemPageScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPageScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPageScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_page_screen_panel, root, attachToRoot, component);
    }

    public static ItemPageScreenPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPageScreenPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPageScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_page_screen_panel, null, false, component);
    }

    public static ItemPageScreenPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPageScreenPanelBinding bind(View view, Object component) {
        return (ItemPageScreenPanelBinding) bind(component, view, R.layout.item_page_screen_panel);
    }
}