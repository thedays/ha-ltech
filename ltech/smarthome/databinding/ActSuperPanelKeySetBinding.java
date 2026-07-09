package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelKeySetBinding extends ViewDataBinding {
    public final LinearLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final SwipeRecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActSuperPanelKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutLoad, SwipeRecyclerView rvContent, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_key_set, root, attachToRoot, component);
    }

    public static ActSuperPanelKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_key_set, null, false, component);
    }

    public static ActSuperPanelKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelKeySetBinding bind(View view, Object component) {
        return (ActSuperPanelKeySetBinding) bind(component, view, R.layout.act_super_panel_key_set);
    }
}