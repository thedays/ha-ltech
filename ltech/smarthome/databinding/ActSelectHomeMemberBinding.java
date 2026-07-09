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
public abstract class ActSelectHomeMemberBinding extends ViewDataBinding {
    public final RecyclerView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectHomeMemberBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView layoutLoad, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectHomeMemberBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectHomeMemberBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectHomeMemberBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_home_member, root, attachToRoot, component);
    }

    public static ActSelectHomeMemberBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectHomeMemberBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectHomeMemberBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_home_member, null, false, component);
    }

    public static ActSelectHomeMemberBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectHomeMemberBinding bind(View view, Object component) {
        return (ActSelectHomeMemberBinding) bind(component, view, R.layout.act_select_home_member);
    }
}