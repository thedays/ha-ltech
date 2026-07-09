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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActSenseSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivGo1;
    public final AppCompatImageView ivGo2;
    public final ConstraintLayout layoutSetMotion;
    public final ConstraintLayout layoutSetNoMotion;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final SwitchButton sbIndicator;
    public final SwitchButton sbSingleReport;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tv1;
    public final AppCompatTextView tv2;
    public final AppCompatTextView tv3;
    public final AppCompatTextView tv4;
    public final AppCompatTextView tvMotion;
    public final AppCompatTextView tvNoMotion;
    public final AppCompatTextView tvTip1;
    public final AppCompatTextView tvTip2;
    public final AppCompatTextView tvTip3;
    public final AppCompatTextView tvTip4;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSenseSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGo1, AppCompatImageView ivGo2, ConstraintLayout layoutSetMotion, ConstraintLayout layoutSetNoMotion, SwitchButton sbIndicator, SwitchButton sbSingleReport, LayoutTitleDefaultBinding title, AppCompatTextView tv1, AppCompatTextView tv2, AppCompatTextView tv3, AppCompatTextView tv4, AppCompatTextView tvMotion, AppCompatTextView tvNoMotion, AppCompatTextView tvTip1, AppCompatTextView tvTip2, AppCompatTextView tvTip3, AppCompatTextView tvTip4) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGo1 = ivGo1;
        this.ivGo2 = ivGo2;
        this.layoutSetMotion = layoutSetMotion;
        this.layoutSetNoMotion = layoutSetNoMotion;
        this.sbIndicator = sbIndicator;
        this.sbSingleReport = sbSingleReport;
        this.title = title;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
        this.tvMotion = tvMotion;
        this.tvNoMotion = tvNoMotion;
        this.tvTip1 = tvTip1;
        this.tvTip2 = tvTip2;
        this.tvTip3 = tvTip3;
        this.tvTip4 = tvTip4;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSenseSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSenseSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSenseSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sense_setting, root, attachToRoot, component);
    }

    public static ActSenseSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSenseSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSenseSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sense_setting, null, false, component);
    }

    public static ActSenseSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSenseSettingBinding bind(View view, Object component) {
        return (ActSenseSettingBinding) bind(component, view, R.layout.act_sense_setting);
    }
}