package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemScreenPanelBinding extends ViewDataBinding {
    public final AppCompatImageView ivScreen;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvScreen;

    protected ItemScreenPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivScreen, ConstraintLayout layoutItemBg, AppCompatTextView tvScreen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivScreen = ivScreen;
        this.layoutItemBg = layoutItemBg;
        this.tvScreen = tvScreen;
    }

    public static ItemScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_panel, root, attachToRoot, component);
    }

    public static ItemScreenPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_screen_panel, null, false, component);
    }

    public static ItemScreenPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScreenPanelBinding bind(View view, Object component) {
        return (ItemScreenPanelBinding) bind(component, view, R.layout.item_screen_panel);
    }
}