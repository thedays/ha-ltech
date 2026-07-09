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

/* loaded from: classes3.dex */
public abstract class ActEditKeyNameBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActEditKeyNameBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActEditKeyNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditKeyNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEditKeyNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_key_name, root, attachToRoot, component);
    }

    public static ActEditKeyNameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditKeyNameBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEditKeyNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_key_name, null, false, component);
    }

    public static ActEditKeyNameBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditKeyNameBinding bind(View view, Object component) {
        return (ActEditKeyNameBinding) bind(component, view, R.layout.act_edit_key_name);
    }
}