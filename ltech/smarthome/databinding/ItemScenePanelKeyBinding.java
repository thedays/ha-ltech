package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemScenePanelKeyBinding extends ViewDataBinding {
    public final LinearLayout layoutContent;
    public final ConstraintLayout layoutItemBg;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvSubText;

    protected ItemScenePanelKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutContent, ConstraintLayout layoutItemBg, AppCompatTextView tvDeviceName, AppCompatTextView tvSubText) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutContent = layoutContent;
        this.layoutItemBg = layoutItemBg;
        this.tvDeviceName = tvDeviceName;
        this.tvSubText = tvSubText;
    }

    public static ItemScenePanelKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScenePanelKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemScenePanelKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_scene_panel_key, root, attachToRoot, component);
    }

    public static ItemScenePanelKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScenePanelKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemScenePanelKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_scene_panel_key, null, false, component);
    }

    public static ItemScenePanelKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemScenePanelKeyBinding bind(View view, Object component) {
        return (ItemScenePanelKeyBinding) bind(component, view, R.layout.item_scene_panel_key);
    }
}