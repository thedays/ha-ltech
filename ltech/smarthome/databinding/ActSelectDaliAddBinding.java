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
public abstract class ActSelectDaliAddBinding extends ViewDataBinding {
    public final FrameLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;

    public abstract void setTitle(TitleDefault title);

    protected ActSelectDaliAddBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout layoutContent, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutContent = layoutContent;
        this.title = title;
        this.tvBottom = tvBottom;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSelectDaliAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDaliAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectDaliAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_dali_add, root, attachToRoot, component);
    }

    public static ActSelectDaliAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDaliAddBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectDaliAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_dali_add, null, false, component);
    }

    public static ActSelectDaliAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectDaliAddBinding bind(View view, Object component) {
        return (ActSelectDaliAddBinding) bind(component, view, R.layout.act_select_dali_add);
    }
}