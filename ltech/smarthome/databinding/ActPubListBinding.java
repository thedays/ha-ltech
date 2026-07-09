package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActPubListBinding extends ViewDataBinding {
    public final FrameLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final SmartRefreshLayout refreshLayout;
    public final SwipeRecyclerView rv;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActPubListBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutLoad, SmartRefreshLayout refreshLayout, SwipeRecyclerView rv, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.refreshLayout = refreshLayout;
        this.rv = rv;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActPubListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPubListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPubListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_pub_list, root, attachToRoot, component);
    }

    public static ActPubListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPubListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPubListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_pub_list, null, false, component);
    }

    public static ActPubListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPubListBinding bind(View view, Object component) {
        return (ActPubListBinding) bind(component, view, R.layout.act_pub_list);
    }
}