package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ActSearchSceneBinding extends ViewDataBinding {
    public final FrameLayout layoutSearch;
    public final RecyclerView rvDevice;
    public final ItemSearchBarBinding searchBar;

    protected ActSearchSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutSearch, RecyclerView rvDevice, ItemSearchBarBinding searchBar) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutSearch = layoutSearch;
        this.rvDevice = rvDevice;
        this.searchBar = searchBar;
    }

    public static ActSearchSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSearchSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_scene, root, attachToRoot, component);
    }

    public static ActSearchSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSearchSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_scene, null, false, component);
    }

    public static ActSearchSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchSceneBinding bind(View view, Object component) {
        return (ActSearchSceneBinding) bind(component, view, R.layout.act_search_scene);
    }
}