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
import com.jaygoo.widget.VerticalRangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.RectProgressBar2;

/* loaded from: classes3.dex */
public abstract class FtCtLightBinding extends ViewDataBinding {
    public final VerticalRangeSeekBar ctsb;
    public final ConstraintLayout layoutRoot;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvCt;
    public final RectProgressBar2 sbBrt;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvWy;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtCtLightBinding(Object _bindingComponent, View _root, int _localFieldCount, VerticalRangeSeekBar ctsb, ConstraintLayout layoutRoot, RecyclerView rvCt, RectProgressBar2 sbBrt, AppCompatTextView tvBrt, AppCompatTextView tvWy) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ctsb = ctsb;
        this.layoutRoot = layoutRoot;
        this.rvCt = rvCt;
        this.sbBrt = sbBrt;
        this.tvBrt = tvBrt;
        this.tvWy = tvWy;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtCtLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCtLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtCtLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_ct_light, root, attachToRoot, component);
    }

    public static FtCtLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCtLightBinding inflate(LayoutInflater inflater, Object component) {
        return (FtCtLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_ct_light, null, false, component);
    }

    public static FtCtLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtCtLightBinding bind(View view, Object component) {
        return (FtCtLightBinding) bind(component, view, R.layout.ft_ct_light);
    }
}