package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemSceneActionBinding extends ViewDataBinding {
    public final ConstraintLayout addSceneColayout;
    public final AppCompatImageView appCompatImageView13;
    public final CircleImageView civColor;
    public final AppCompatImageView ivDrag;
    public final AppCompatImageView ivIcon;
    public final LinearLayout layoutState;
    public final AppCompatTextView tvActionTip;
    public final AppCompatTextView tvDelayTime;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvLocation;
    public final AppCompatTextView tvState;
    public final AppCompatTextView tvVirtual;
    public final View vAction;
    public final View view20;

    protected ItemSceneActionBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout addSceneColayout, AppCompatImageView appCompatImageView13, CircleImageView civColor, AppCompatImageView ivDrag, AppCompatImageView ivIcon, LinearLayout layoutState, AppCompatTextView tvActionTip, AppCompatTextView tvDelayTime, AppCompatTextView tvDeviceName, AppCompatTextView tvLocation, AppCompatTextView tvState, AppCompatTextView tvVirtual, View vAction, View view20) {
        super(_bindingComponent, _root, _localFieldCount);
        this.addSceneColayout = addSceneColayout;
        this.appCompatImageView13 = appCompatImageView13;
        this.civColor = civColor;
        this.ivDrag = ivDrag;
        this.ivIcon = ivIcon;
        this.layoutState = layoutState;
        this.tvActionTip = tvActionTip;
        this.tvDelayTime = tvDelayTime;
        this.tvDeviceName = tvDeviceName;
        this.tvLocation = tvLocation;
        this.tvState = tvState;
        this.tvVirtual = tvVirtual;
        this.vAction = vAction;
        this.view20 = view20;
    }

    public static ItemSceneActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSceneActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSceneActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_scene_action, root, attachToRoot, component);
    }

    public static ItemSceneActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSceneActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSceneActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_scene_action, null, false, component);
    }

    public static ItemSceneActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSceneActionBinding bind(View view, Object component) {
        return (ItemSceneActionBinding) bind(component, view, R.layout.item_scene_action);
    }
}