package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectDivideBinding extends ViewDataBinding {
    public final LinearLayout llContent;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvNotSelectContent;
    public final RecyclerView rvSelectContent;
    public final ScrollView scrollLayout;
    public final MySpinner spinnerFloor;
    public final MySpinner spinnerRoom;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectDivideBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout llContent, RecyclerView rvNotSelectContent, RecyclerView rvSelectContent, ScrollView scrollLayout, MySpinner spinnerFloor, MySpinner spinnerRoom, LayoutTitleDefaultBinding title, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.llContent = llContent;
        this.rvNotSelectContent = rvNotSelectContent;
        this.rvSelectContent = rvSelectContent;
        this.scrollLayout = scrollLayout;
        this.spinnerFloor = spinnerFloor;
        this.spinnerRoom = spinnerRoom;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectDivideBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDivideBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectDivideBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_divide, root, attachToRoot, component);
    }

    public static ActSelectDivideBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDivideBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectDivideBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_divide, null, false, component);
    }

    public static ActSelectDivideBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDivideBinding bind(View view, Object component) {
        return (ActSelectDivideBinding) bind(component, view, R.layout.act_select_divide);
    }
}