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
public abstract class ActSelectBleCurtainActionBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectBleCurtainActionBinding(Object _bindingComponent, View _root, int _localFieldCount, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectBleCurtainActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBleCurtainActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectBleCurtainActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_ble_curtain_action, root, attachToRoot, component);
    }

    public static ActSelectBleCurtainActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBleCurtainActionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectBleCurtainActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_ble_curtain_action, null, false, component);
    }

    public static ActSelectBleCurtainActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBleCurtainActionBinding bind(View view, Object component) {
        return (ActSelectBleCurtainActionBinding) bind(component, view, R.layout.act_select_ble_curtain_action);
    }
}