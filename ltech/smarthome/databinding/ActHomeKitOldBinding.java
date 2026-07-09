package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActHomeKitOldBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView11;
    public final View bgTitle;
    public final AppCompatImageView ivEmpty;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEmptyTip;
    public final View vGuide;

    public abstract void setTitle(TitleDefault title);

    protected ActHomeKitOldBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView11, View bgTitle, AppCompatImageView ivEmpty, LayoutTitleDefaultBinding title, AppCompatTextView tvEmptyTip, View vGuide) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView11 = appCompatImageView11;
        this.bgTitle = bgTitle;
        this.ivEmpty = ivEmpty;
        this.title = title;
        this.tvEmptyTip = tvEmptyTip;
        this.vGuide = vGuide;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActHomeKitOldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeKitOldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomeKitOldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_kit_old, root, attachToRoot, component);
    }

    public static ActHomeKitOldBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeKitOldBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomeKitOldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_kit_old, null, false, component);
    }

    public static ActHomeKitOldBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeKitOldBinding bind(View view, Object component) {
        return (ActHomeKitOldBinding) bind(component, view, R.layout.act_home_kit_old);
    }
}