package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.BrightVerticalSeekBar;

/* loaded from: classes3.dex */
public abstract class ActDimSelectColorBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final BrightVerticalSeekBar sbBrt;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;

    public abstract void setTitle(TitleDefault title);

    protected ActDimSelectColorBinding(Object _bindingComponent, View _root, int _localFieldCount, BrightVerticalSeekBar sbBrt, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt) {
        super(_bindingComponent, _root, _localFieldCount);
        this.sbBrt = sbBrt;
        this.title = title;
        this.tvBrt = tvBrt;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActDimSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDimSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDimSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dim_select_color, root, attachToRoot, component);
    }

    public static ActDimSelectColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDimSelectColorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDimSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dim_select_color, null, false, component);
    }

    public static ActDimSelectColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDimSelectColorBinding bind(View view, Object component) {
        return (ActDimSelectColorBinding) bind(component, view, R.layout.act_dim_select_color);
    }
}