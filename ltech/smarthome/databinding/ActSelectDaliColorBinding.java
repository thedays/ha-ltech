package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectDaliColorBinding extends ViewDataBinding {
    public final TabLayout bottomTabLayout;
    public final FrameLayout fragmentContainer;
    public final LinearLayout layoutTab;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectDaliColorBinding(Object _bindingComponent, View _root, int _localFieldCount, TabLayout bottomTabLayout, FrameLayout fragmentContainer, LinearLayout layoutTab, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bottomTabLayout = bottomTabLayout;
        this.fragmentContainer = fragmentContainer;
        this.layoutTab = layoutTab;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectDaliColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDaliColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectDaliColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_dali_color, root, attachToRoot, component);
    }

    public static ActSelectDaliColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDaliColorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectDaliColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_dali_color, null, false, component);
    }

    public static ActSelectDaliColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDaliColorBinding bind(View view, Object component) {
        return (ActSelectDaliColorBinding) bind(component, view, R.layout.act_select_dali_color);
    }
}