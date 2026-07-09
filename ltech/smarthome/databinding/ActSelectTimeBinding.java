package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectTimeBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView17;
    public final AppCompatTextView appCompatTextView24;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView pickViewHour;
    public final RecyclerView pickViewMin;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvRepeatTime;
    public final View vSelectTime;
    public final View view18;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectTimeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView17, AppCompatTextView appCompatTextView24, RecyclerView pickViewHour, RecyclerView pickViewMin, LayoutTitleDefaultBinding title, AppCompatTextView tvRepeatTime, View vSelectTime, View view18) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView17 = appCompatImageView17;
        this.appCompatTextView24 = appCompatTextView24;
        this.pickViewHour = pickViewHour;
        this.pickViewMin = pickViewMin;
        this.title = title;
        this.tvRepeatTime = tvRepeatTime;
        this.vSelectTime = vSelectTime;
        this.view18 = view18;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectTimeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_time, root, attachToRoot, component);
    }

    public static ActSelectTimeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectTimeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectTimeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_time, null, false, component);
    }

    public static ActSelectTimeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectTimeBinding bind(View view, Object component) {
        return (ActSelectTimeBinding) bind(component, view, R.layout.act_select_time);
    }
}