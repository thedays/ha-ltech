package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.RectProgressBar2;

/* loaded from: classes3.dex */
public abstract class FtDimLightBinding extends ViewDataBinding {
    public final ConstraintLayout layoutRoot;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RectProgressBar2 progressBar;
    public final AppCompatTextView tvBrt;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtDimLightBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutRoot, RectProgressBar2 progressBar, AppCompatTextView tvBrt) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutRoot = layoutRoot;
        this.progressBar = progressBar;
        this.tvBrt = tvBrt;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtDimLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDimLightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtDimLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_dim_light, root, attachToRoot, component);
    }

    public static FtDimLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDimLightBinding inflate(LayoutInflater inflater, Object component) {
        return (FtDimLightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_dim_light, null, false, component);
    }

    public static FtDimLightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtDimLightBinding bind(View view, Object component) {
        return (FtDimLightBinding) bind(component, view, R.layout.ft_dim_light);
    }
}