package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.ScrollRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActChannel512Binding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final ScrollRecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSub;

    public abstract void setTitle(TitleDefault title);

    protected ActChannel512Binding(Object _bindingComponent, View _root, int _localFieldCount, ScrollRecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rv = rv;
        this.title = title;
        this.tvSub = tvSub;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActChannel512Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChannel512Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActChannel512Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_channel_512, root, attachToRoot, component);
    }

    public static ActChannel512Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChannel512Binding inflate(LayoutInflater inflater, Object component) {
        return (ActChannel512Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_channel_512, null, false, component);
    }

    public static ActChannel512Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChannel512Binding bind(View view, Object component) {
        return (ActChannel512Binding) bind(component, view, R.layout.act_channel_512);
    }
}