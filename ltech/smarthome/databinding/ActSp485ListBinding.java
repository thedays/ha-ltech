package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActSp485ListBinding extends ViewDataBinding {
    public final View dividerLine;
    public final LinearLayout layoutBottom;
    public final FrameLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final SmartRefreshLayout refreshLayout;
    public final SwipeRecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;

    public abstract void setTitle(TitleDefault title);

    protected ActSp485ListBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, LinearLayout layoutBottom, FrameLayout layoutLoad, SmartRefreshLayout refreshLayout, SwipeRecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.layoutBottom = layoutBottom;
        this.layoutLoad = layoutLoad;
        this.refreshLayout = refreshLayout;
        this.rv = rv;
        this.title = title;
        this.tvBottom = tvBottom;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSp485ListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSp485ListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSp485ListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sp485_list, root, attachToRoot, component);
    }

    public static ActSp485ListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSp485ListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSp485ListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sp485_list, null, false, component);
    }

    public static ActSp485ListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSp485ListBinding bind(View view, Object component) {
        return (ActSp485ListBinding) bind(component, view, R.layout.act_sp485_list);
    }
}