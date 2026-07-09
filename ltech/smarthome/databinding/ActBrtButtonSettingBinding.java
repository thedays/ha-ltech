package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActBrtButtonSettingBinding extends ViewDataBinding {
    public final RangeSeekBar brtStep;
    public final AppCompatImageView ivBrt1;
    public final AppCompatImageView ivBrt2;
    public final ConstraintLayout layoutDiff;
    public final ConstraintLayout layoutSame;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt1;
    public final AppCompatTextView tvBrt2;
    public final AppCompatTextView tvBrtTip1;
    public final AppCompatTextView tvBrtTip2;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActBrtButtonSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, RangeSeekBar brtStep, AppCompatImageView ivBrt1, AppCompatImageView ivBrt2, ConstraintLayout layoutDiff, ConstraintLayout layoutSame, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt1, AppCompatTextView tvBrt2, AppCompatTextView tvBrtTip1, AppCompatTextView tvBrtTip2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.brtStep = brtStep;
        this.ivBrt1 = ivBrt1;
        this.ivBrt2 = ivBrt2;
        this.layoutDiff = layoutDiff;
        this.layoutSame = layoutSame;
        this.title = title;
        this.tvBrt1 = tvBrt1;
        this.tvBrt2 = tvBrt2;
        this.tvBrtTip1 = tvBrtTip1;
        this.tvBrtTip2 = tvBrtTip2;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActBrtButtonSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBrtButtonSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBrtButtonSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_brt_button_setting, root, attachToRoot, component);
    }

    public static ActBrtButtonSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBrtButtonSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBrtButtonSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_brt_button_setting, null, false, component);
    }

    public static ActBrtButtonSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBrtButtonSettingBinding bind(View view, Object component) {
        return (ActBrtButtonSettingBinding) bind(component, view, R.layout.act_brt_button_setting);
    }
}