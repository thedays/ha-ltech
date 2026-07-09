package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.VerticalRangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.BrightVerticalSeekBar;

/* loaded from: classes3.dex */
public abstract class ActCtSelectColorBinding extends ViewDataBinding {
    public final VerticalRangeSeekBar ctsb;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvColor;
    public final BrightVerticalSeekBar sbBrt;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvWy;

    public abstract void setTitle(TitleDefault title);

    protected ActCtSelectColorBinding(Object _bindingComponent, View _root, int _localFieldCount, VerticalRangeSeekBar ctsb, RecyclerView rvColor, BrightVerticalSeekBar sbBrt, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvWy) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ctsb = ctsb;
        this.rvColor = rvColor;
        this.sbBrt = sbBrt;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvWy = tvWy;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActCtSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtSelectColorBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCtSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ct_select_color, root, attachToRoot, component);
    }

    public static ActCtSelectColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtSelectColorBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCtSelectColorBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_ct_select_color, null, false, component);
    }

    public static ActCtSelectColorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCtSelectColorBinding bind(View view, Object component) {
        return (ActCtSelectColorBinding) bind(component, view, R.layout.act_ct_select_color);
    }
}