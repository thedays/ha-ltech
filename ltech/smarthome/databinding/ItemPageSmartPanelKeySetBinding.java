package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemPageSmartPanelKeySetBinding extends ViewDataBinding {
    public final AppCompatImageView ivDisplay;
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivGo2;
    public final AppCompatImageView ivGo3;
    public final RelativeLayout layoutDisplay;
    public final RelativeLayout layoutTime;
    public final AppCompatTextView tv;
    public final AppCompatTextView tvDisplay;
    public final AppCompatTextView tvLongClickTip;
    public final AppCompatTextView tvMain;
    public final AppCompatTextView tvSubText;
    public final AppCompatTextView tvTime;

    protected ItemPageSmartPanelKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDisplay, AppCompatImageView ivGo, AppCompatImageView ivGo2, AppCompatImageView ivGo3, RelativeLayout layoutDisplay, RelativeLayout layoutTime, AppCompatTextView tv, AppCompatTextView tvDisplay, AppCompatTextView tvLongClickTip, AppCompatTextView tvMain, AppCompatTextView tvSubText, AppCompatTextView tvTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDisplay = ivDisplay;
        this.ivGo = ivGo;
        this.ivGo2 = ivGo2;
        this.ivGo3 = ivGo3;
        this.layoutDisplay = layoutDisplay;
        this.layoutTime = layoutTime;
        this.tv = tv;
        this.tvDisplay = tvDisplay;
        this.tvLongClickTip = tvLongClickTip;
        this.tvMain = tvMain;
        this.tvSubText = tvSubText;
        this.tvTime = tvTime;
    }

    public static ItemPageSmartPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPageSmartPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemPageSmartPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_page_smart_panel_key_set, root, attachToRoot, component);
    }

    public static ItemPageSmartPanelKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPageSmartPanelKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemPageSmartPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_page_smart_panel_key_set, null, false, component);
    }

    public static ItemPageSmartPanelKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemPageSmartPanelKeySetBinding bind(View view, Object component) {
        return (ItemPageSmartPanelKeySetBinding) bind(component, view, R.layout.item_page_smart_panel_key_set);
    }
}