package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActMatterPlatformListBinding extends ViewDataBinding {
    public final FrameLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSync;

    public abstract void setTitle(TitleDefault title);

    protected ActMatterPlatformListBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutLoad, SmartRefreshLayout refreshLayout, RecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvSync) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.refreshLayout = refreshLayout;
        this.rv = rv;
        this.title = title;
        this.tvSync = tvSync;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActMatterPlatformListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMatterPlatformListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActMatterPlatformListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_matter_platform_list, root, attachToRoot, component);
    }

    public static ActMatterPlatformListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMatterPlatformListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActMatterPlatformListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_matter_platform_list, null, false, component);
    }

    public static ActMatterPlatformListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActMatterPlatformListBinding bind(View view, Object component) {
        return (ActMatterPlatformListBinding) bind(component, view, R.layout.act_matter_platform_list);
    }
}