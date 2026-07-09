package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelect2Binding extends ViewDataBinding {
    public final RelativeLayout footerView;
    public final Group groupSync;
    public final Guideline guideline;
    public final AppCompatImageView ivSync;
    public final FrameLayout layoutSearch;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final ItemSearchBarBinding searchBar;
    public final MySpinner spinnerFloor;
    public final MySpinner spinnerRoom;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSync;
    public final View viewSync;

    public abstract void setTitle(TitleDefault title);

    protected ActSelect2Binding(Object _bindingComponent, View _root, int _localFieldCount, RelativeLayout footerView, Group groupSync, Guideline guideline, AppCompatImageView ivSync, FrameLayout layoutSearch, RecyclerView rvContent, ItemSearchBarBinding searchBar, MySpinner spinnerFloor, MySpinner spinnerRoom, LayoutTitleDefaultBinding title, AppCompatTextView tvSync, View viewSync) {
        super(_bindingComponent, _root, _localFieldCount);
        this.footerView = footerView;
        this.groupSync = groupSync;
        this.guideline = guideline;
        this.ivSync = ivSync;
        this.layoutSearch = layoutSearch;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
        this.spinnerFloor = spinnerFloor;
        this.spinnerRoom = spinnerRoom;
        this.title = title;
        this.tvSync = tvSync;
        this.viewSync = viewSync;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelect2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect2Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelect2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select2, root, attachToRoot, component);
    }

    public static ActSelect2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect2Binding inflate(LayoutInflater inflater, Object component) {
        return (ActSelect2Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select2, null, false, component);
    }

    public static ActSelect2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect2Binding bind(View view, Object component) {
        return (ActSelect2Binding) bind(component, view, R.layout.act_select2);
    }
}