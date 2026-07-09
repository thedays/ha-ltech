package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectSceneAllBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final FrameLayout layoutSearch;

    @Bindable
    protected String mBottomTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final ItemSearchBarBinding searchBar;
    public final TabLayout tabs;
    public final AppCompatTextView title;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvEdit;
    public final View vTitleBg;

    public abstract void setBottomTip(String bottomTip);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectSceneAllBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, FrameLayout layoutSearch, RecyclerView rvContent, ItemSearchBarBinding searchBar, TabLayout tabs, AppCompatTextView title, AppCompatTextView tvBottom, AppCompatTextView tvEdit, View vTitleBg) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.layoutSearch = layoutSearch;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
        this.tabs = tabs;
        this.title = title;
        this.tvBottom = tvBottom;
        this.tvEdit = tvEdit;
        this.vTitleBg = vTitleBg;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getBottomTip() {
        return this.mBottomTip;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectSceneAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSceneAllBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectSceneAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_scene_all, root, attachToRoot, component);
    }

    public static ActSelectSceneAllBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSceneAllBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectSceneAllBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_scene_all, null, false, component);
    }

    public static ActSelectSceneAllBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSceneAllBinding bind(View view, Object component) {
        return (ActSelectSceneAllBinding) bind(component, view, R.layout.act_select_scene_all);
    }
}