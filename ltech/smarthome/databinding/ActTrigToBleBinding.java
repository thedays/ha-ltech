package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActTrigToBleBinding extends ViewDataBinding {
    public final AppCompatImageView ivBg;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvMode;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBindTitle;

    public abstract void setTitle(TitleDefault title);

    protected ActTrigToBleBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBg, RecyclerView rvMode, LayoutTitleDefaultBinding title, AppCompatTextView tvBindTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBg = ivBg;
        this.rvMode = rvMode;
        this.title = title;
        this.tvBindTitle = tvBindTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActTrigToBleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigToBleBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTrigToBleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_to_ble, root, attachToRoot, component);
    }

    public static ActTrigToBleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigToBleBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTrigToBleBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_trig_to_ble, null, false, component);
    }

    public static ActTrigToBleBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTrigToBleBinding bind(View view, Object component) {
        return (ActTrigToBleBinding) bind(component, view, R.layout.act_trig_to_ble);
    }
}