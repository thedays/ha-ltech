package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.CountDownProgressBar;

/* loaded from: classes3.dex */
public abstract class FtStepsIntroductionBinding extends ViewDataBinding {
    public final CountDownProgressBar barCountdown;
    public final AppCompatButton btNext;
    public final AppCompatButton btPrevious;
    public final ImageView ivBg;
    public final ImageView ivSteps;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvSteps;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected FtStepsIntroductionBinding(Object _bindingComponent, View _root, int _localFieldCount, CountDownProgressBar barCountdown, AppCompatButton btNext, AppCompatButton btPrevious, ImageView ivBg, ImageView ivSteps, AppCompatTextView tvSteps) {
        super(_bindingComponent, _root, _localFieldCount);
        this.barCountdown = barCountdown;
        this.btNext = btNext;
        this.btPrevious = btPrevious;
        this.ivBg = ivBg;
        this.ivSteps = ivSteps;
        this.tvSteps = tvSteps;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static FtStepsIntroductionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtStepsIntroductionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtStepsIntroductionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_steps_introduction, root, attachToRoot, component);
    }

    public static FtStepsIntroductionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtStepsIntroductionBinding inflate(LayoutInflater inflater, Object component) {
        return (FtStepsIntroductionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_steps_introduction, null, false, component);
    }

    public static FtStepsIntroductionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtStepsIntroductionBinding bind(View view, Object component) {
        return (FtStepsIntroductionBinding) bind(component, view, R.layout.ft_steps_introduction);
    }
}