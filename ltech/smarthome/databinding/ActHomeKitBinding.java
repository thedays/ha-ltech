package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActHomeKitBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView11;
    public final View bgTitle;
    public final AppCompatImageView ivHomekit;
    public final AppCompatImageView ivMatter;

    @Bindable
    protected TitleDefault mTitle;
    public final SmartRefreshLayout refreshLayout;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEmptyTip;
    public final AppCompatTextView tvEnableMatter;
    public final AppCompatTextView tvOffline;
    public final AppCompatTextView tvSyncNum;
    public final AppCompatTextView tvSyncSceneNum;
    public final View vGuide;
    public final View viewOffline;

    public abstract void setTitle(TitleDefault title);

    protected ActHomeKitBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView11, View bgTitle, AppCompatImageView ivHomekit, AppCompatImageView ivMatter, SmartRefreshLayout refreshLayout, LayoutTitleDefaultBinding title, AppCompatTextView tvEmptyTip, AppCompatTextView tvEnableMatter, AppCompatTextView tvOffline, AppCompatTextView tvSyncNum, AppCompatTextView tvSyncSceneNum, View vGuide, View viewOffline) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView11 = appCompatImageView11;
        this.bgTitle = bgTitle;
        this.ivHomekit = ivHomekit;
        this.ivMatter = ivMatter;
        this.refreshLayout = refreshLayout;
        this.title = title;
        this.tvEmptyTip = tvEmptyTip;
        this.tvEnableMatter = tvEnableMatter;
        this.tvOffline = tvOffline;
        this.tvSyncNum = tvSyncNum;
        this.tvSyncSceneNum = tvSyncSceneNum;
        this.vGuide = vGuide;
        this.viewOffline = viewOffline;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActHomeKitBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeKitBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomeKitBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_kit, root, attachToRoot, component);
    }

    public static ActHomeKitBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeKitBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomeKitBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_kit, null, false, component);
    }

    public static ActHomeKitBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeKitBinding bind(View view, Object component) {
        return (ActHomeKitBinding) bind(component, view, R.layout.act_home_kit);
    }
}