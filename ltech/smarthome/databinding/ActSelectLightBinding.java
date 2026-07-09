package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActSelectLightBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvAction;
    public final SwipeRecyclerView rvKey;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectLightBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvAction, SwipeRecyclerView rvKey, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvAction = rvAction;
        this.rvKey = rvKey;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_light, root, attachToRoot, component);
    }

    public static ActSelectLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectLightBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_light, null, false, component);
    }

    public static ActSelectLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectLightBinding bind(View view, Object component) {
        return (ActSelectLightBinding) bind(component, view, R.layout.act_select_light);
    }
}