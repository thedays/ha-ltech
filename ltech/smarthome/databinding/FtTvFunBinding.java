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
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.DirectionView;

/* loaded from: classes3.dex */
public abstract class FtTvFunBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView40;
    public final DirectionView directionView;
    public final AppCompatImageView ivChPlus;
    public final AppCompatImageView ivChReduce;
    public final AppCompatImageView ivHome;
    public final AppCompatImageView ivMenu;
    public final AppCompatImageView ivVolPlus;
    public final AppCompatImageView ivVolReduce;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvChannel;
    public final AppCompatTextView tvSignal;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtTvFunBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView40, DirectionView directionView, AppCompatImageView ivChPlus, AppCompatImageView ivChReduce, AppCompatImageView ivHome, AppCompatImageView ivMenu, AppCompatImageView ivVolPlus, AppCompatImageView ivVolReduce, AppCompatTextView tvChannel, AppCompatTextView tvSignal) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView40 = appCompatTextView40;
        this.directionView = directionView;
        this.ivChPlus = ivChPlus;
        this.ivChReduce = ivChReduce;
        this.ivHome = ivHome;
        this.ivMenu = ivMenu;
        this.ivVolPlus = ivVolPlus;
        this.ivVolReduce = ivVolReduce;
        this.tvChannel = tvChannel;
        this.tvSignal = tvSignal;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtTvFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvFunBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtTvFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_tv_fun, root, attachToRoot, component);
    }

    public static FtTvFunBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvFunBinding inflate(LayoutInflater inflater, Object component) {
        return (FtTvFunBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_tv_fun, null, false, component);
    }

    public static FtTvFunBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtTvFunBinding bind(View view, Object component) {
        return (FtTvFunBinding) bind(component, view, R.layout.ft_tv_fun);
    }
}