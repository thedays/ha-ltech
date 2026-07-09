package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSearchGooglePositionBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActSearchGooglePositionBinding(Object _bindingComponent, View _root, int _localFieldCount, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSearchGooglePositionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchGooglePositionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSearchGooglePositionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_google_position, root, attachToRoot, component);
    }

    public static ActSearchGooglePositionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchGooglePositionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSearchGooglePositionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_google_position, null, false, component);
    }

    public static ActSearchGooglePositionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchGooglePositionBinding bind(View view, Object component) {
        return (ActSearchGooglePositionBinding) bind(component, view, R.layout.act_search_google_position);
    }
}