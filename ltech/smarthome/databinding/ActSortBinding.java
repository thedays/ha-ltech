package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActSortBinding extends ViewDataBinding {
    public final SwipeRecyclerView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActSortBinding(Object _bindingComponent, View _root, int _localFieldCount, SwipeRecyclerView layoutLoad, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSortBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSortBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSortBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sort, root, attachToRoot, component);
    }

    public static ActSortBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSortBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSortBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sort, null, false, component);
    }

    public static ActSortBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSortBinding bind(View view, Object component) {
        return (ActSortBinding) bind(component, view, R.layout.act_sort);
    }
}