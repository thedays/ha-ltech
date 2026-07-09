package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectThemeModeBinding extends ViewDataBinding {
    public final FrameLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectThemeModeBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutContent, LayoutTitleDefaultBinding title, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutContent = layoutContent;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectThemeModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectThemeModeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectThemeModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_theme_mode, root, attachToRoot, component);
    }

    public static ActSelectThemeModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectThemeModeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectThemeModeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_theme_mode, null, false, component);
    }

    public static ActSelectThemeModeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectThemeModeBinding bind(View view, Object component) {
        return (ActSelectThemeModeBinding) bind(component, view, R.layout.act_select_theme_mode);
    }
}