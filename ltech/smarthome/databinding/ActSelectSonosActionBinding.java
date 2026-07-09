package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectSonosActionBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvTitle;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectSonosActionBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvTitle = tvTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectSonosActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSonosActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectSonosActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_sonos_action, root, attachToRoot, component);
    }

    public static ActSelectSonosActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSonosActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectSonosActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_sonos_action, null, false, component);
    }

    public static ActSelectSonosActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSonosActionBinding bind(View view, Object component) {
        return (ActSelectSonosActionBinding) bind(component, view, R.layout.act_select_sonos_action);
    }
}