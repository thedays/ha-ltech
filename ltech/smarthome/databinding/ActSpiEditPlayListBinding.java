package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSpiEditPlayListBinding extends ViewDataBinding {
    public final LinearLayout llContent;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvNotSelectContent;
    public final RecyclerView rvSelectContent;
    public final NestedScrollView scrollLayout;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvNotSelect;
    public final AppCompatTextView tvSelect;

    public abstract void setTitle(TitleDefault title);

    protected ActSpiEditPlayListBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout llContent, RecyclerView rvNotSelectContent, RecyclerView rvSelectContent, NestedScrollView scrollLayout, LayoutTitleDefaultBinding title, AppCompatTextView tvNotSelect, AppCompatTextView tvSelect) {
        super(_bindingComponent, _root, _localFieldCount);
        this.llContent = llContent;
        this.rvNotSelectContent = rvNotSelectContent;
        this.rvSelectContent = rvSelectContent;
        this.scrollLayout = scrollLayout;
        this.title = title;
        this.tvNotSelect = tvNotSelect;
        this.tvSelect = tvSelect;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSpiEditPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiEditPlayListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSpiEditPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_spi_edit_play_list, root, attachToRoot, component);
    }

    public static ActSpiEditPlayListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiEditPlayListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSpiEditPlayListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_spi_edit_play_list, null, false, component);
    }

    public static ActSpiEditPlayListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSpiEditPlayListBinding bind(View view, Object component) {
        return (ActSpiEditPlayListBinding) bind(component, view, R.layout.act_spi_edit_play_list);
    }
}