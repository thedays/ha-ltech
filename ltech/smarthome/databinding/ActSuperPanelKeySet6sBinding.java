package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelKeySet6sBinding extends ViewDataBinding {
    public final AppCompatImageView ivS4;
    public final AppCompatImageView ivShadowLeft;
    public final AppCompatImageView ivShadowRight;
    public final AppCompatImageView ivSwitch5;
    public final ConstraintLayout layoutContent;
    public final RelativeLayout layoutS4;
    public final LinearLayout llBottom;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvKeyInfo;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvTipMessage;
    public final TextView tvTipTitle;

    public abstract void setTitle(TitleDefault title);

    protected ActSuperPanelKeySet6sBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivS4, AppCompatImageView ivShadowLeft, AppCompatImageView ivShadowRight, AppCompatImageView ivSwitch5, ConstraintLayout layoutContent, RelativeLayout layoutS4, LinearLayout llBottom, RecyclerView rvKeyInfo, LayoutTitleDefaultBinding title, TextView tvTipMessage, TextView tvTipTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivS4 = ivS4;
        this.ivShadowLeft = ivShadowLeft;
        this.ivShadowRight = ivShadowRight;
        this.ivSwitch5 = ivSwitch5;
        this.layoutContent = layoutContent;
        this.layoutS4 = layoutS4;
        this.llBottom = llBottom;
        this.rvKeyInfo = rvKeyInfo;
        this.title = title;
        this.tvTipMessage = tvTipMessage;
        this.tvTipTitle = tvTipTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSuperPanelKeySet6sBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelKeySet6sBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelKeySet6sBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_key_set_6s, root, attachToRoot, component);
    }

    public static ActSuperPanelKeySet6sBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelKeySet6sBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelKeySet6sBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_key_set_6s, null, false, component);
    }

    public static ActSuperPanelKeySet6sBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelKeySet6sBinding bind(View view, Object component) {
        return (ActSuperPanelKeySet6sBinding) bind(component, view, R.layout.act_super_panel_key_set_6s);
    }
}