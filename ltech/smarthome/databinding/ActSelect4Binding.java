package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelect4Binding extends ViewDataBinding {
    public final ConstraintLayout layoutRoot;

    @Bindable
    protected String mTipText;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTip;

    public abstract void setTipText(String tipText);

    public abstract void setTitle(TitleDefault title);

    protected ActSelect4Binding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutRoot, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutRoot = layoutRoot;
        this.rvContent = rvContent;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getTipText() {
        return this.mTipText;
    }

    public static ActSelect4Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect4Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelect4Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select4, root, attachToRoot, component);
    }

    public static ActSelect4Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect4Binding inflate(LayoutInflater inflater, Object component) {
        return (ActSelect4Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select4, null, false, component);
    }

    public static ActSelect4Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect4Binding bind(View view, Object component) {
        return (ActSelect4Binding) bind(component, view, R.layout.act_select4);
    }
}