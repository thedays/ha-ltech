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
import com.ltech.smarthome.ui.my.ActAboutVM;

/* loaded from: classes3.dex */
public abstract class ActAboutBinding extends ViewDataBinding {
    public final AppCompatImageView ivAppIcon;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAboutVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAppCopyright;
    public final AppCompatTextView tvAppName;
    public final AppCompatTextView tvAppNo;
    public final AppCompatTextView tvAppVersion;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAboutVM viewmodel);

    protected ActAboutBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivAppIcon, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvAppCopyright, AppCompatTextView tvAppName, AppCompatTextView tvAppNo, AppCompatTextView tvAppVersion) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivAppIcon = ivAppIcon;
        this.rvContent = rvContent;
        this.title = title;
        this.tvAppCopyright = tvAppCopyright;
        this.tvAppName = tvAppName;
        this.tvAppNo = tvAppNo;
        this.tvAppVersion = tvAppVersion;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAboutVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAboutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAboutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAboutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_about, root, attachToRoot, component);
    }

    public static ActAboutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAboutBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAboutBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_about, null, false, component);
    }

    public static ActAboutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAboutBinding bind(View view, Object component) {
        return (ActAboutBinding) bind(component, view, R.layout.act_about);
    }
}