package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActImportSceneAllBinding extends ViewDataBinding {
    public final FrameLayout layoutSearch;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected Boolean mTitleGone;
    public final RecyclerView rvContent;
    public final ItemSearchBarBinding searchBar;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setTitleGone(Boolean titleGone);

    protected ActImportSceneAllBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutSearch, RecyclerView rvContent, ItemSearchBarBinding searchBar, TabLayout tabs, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutSearch = layoutSearch;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
        this.tabs = tabs;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getTitleGone() {
        return this.mTitleGone;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActImportSceneAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActImportSceneAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActImportSceneAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_import_scene_all, root, attachToRoot, component);
    }

    public static ActImportSceneAllBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActImportSceneAllBinding inflate(LayoutInflater inflater, Object component) {
        return (ActImportSceneAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_import_scene_all, null, false, component);
    }

    public static ActImportSceneAllBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActImportSceneAllBinding bind(View view, Object component) {
        return (ActImportSceneAllBinding) bind(component, view, R.layout.act_import_scene_all);
    }
}