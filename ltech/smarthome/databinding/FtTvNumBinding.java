package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class FtTvNumBinding extends ViewDataBinding {
    public final Guideline guideline3;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tv0;
    public final AppCompatTextView tv1;
    public final AppCompatTextView tv2;
    public final AppCompatTextView tv3;
    public final AppCompatTextView tv4;
    public final AppCompatTextView tv5;
    public final AppCompatTextView tv6;
    public final AppCompatTextView tv7;
    public final AppCompatTextView tv8;
    public final AppCompatTextView tv9;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtTvNumBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline3, AppCompatTextView tv0, AppCompatTextView tv1, AppCompatTextView tv2, AppCompatTextView tv3, AppCompatTextView tv4, AppCompatTextView tv5, AppCompatTextView tv6, AppCompatTextView tv7, AppCompatTextView tv8, AppCompatTextView tv9) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline3 = guideline3;
        this.tv0 = tv0;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
        this.tv5 = tv5;
        this.tv6 = tv6;
        this.tv7 = tv7;
        this.tv8 = tv8;
        this.tv9 = tv9;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtTvNumBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvNumBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtTvNumBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_tv_num, root, attachToRoot, component);
    }

    public static FtTvNumBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvNumBinding inflate(LayoutInflater inflater, Object component) {
        return (FtTvNumBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_tv_num, null, false, component);
    }

    public static FtTvNumBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvNumBinding bind(View view, Object component) {
        return (FtTvNumBinding) bind(component, view, R.layout.ft_tv_num);
    }
}