package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.login.ActSelectCountryVM;
import com.ltech.smarthome.view.SideBar;

/* loaded from: classes3.dex */
public abstract class ActSelectCountryBinding extends ViewDataBinding {
    public final ListView countryLvcountry;
    public final TextView dialog;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSelectCountryVM mViewmodel;
    public final RelativeLayout rlSearch;
    public final EditText searchBadge;
    public final SideBar sidrbar;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvSearch;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSelectCountryVM viewmodel);

    protected ActSelectCountryBinding(Object _bindingComponent, View _root, int _localFieldCount, ListView countryLvcountry, TextView dialog, RelativeLayout rlSearch, EditText searchBadge, SideBar sidrbar, LayoutTitleDefaultBinding title, TextView tvSearch) {
        super(_bindingComponent, _root, _localFieldCount);
        this.countryLvcountry = countryLvcountry;
        this.dialog = dialog;
        this.rlSearch = rlSearch;
        this.searchBadge = searchBadge;
        this.sidrbar = sidrbar;
        this.title = title;
        this.tvSearch = tvSearch;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSelectCountryVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSelectCountryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectCountryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectCountryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_country, root, attachToRoot, component);
    }

    public static ActSelectCountryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectCountryBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectCountryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_country, null, false, component);
    }

    public static ActSelectCountryBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectCountryBinding bind(View view, Object component) {
        return (ActSelectCountryBinding) bind(component, view, R.layout.act_select_country);
    }
}