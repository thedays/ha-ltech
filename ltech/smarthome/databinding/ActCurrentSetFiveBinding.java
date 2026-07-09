package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActCurrentSetFiveBinding extends ViewDataBinding {
    public final LinearLayout layoutCurrent;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvCurrent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvCurrentTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActCurrentSetFiveBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutCurrent, RecyclerView rvCurrent, LayoutTitleDefaultBinding title, AppCompatTextView tvCurrentTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutCurrent = layoutCurrent;
        this.rvCurrent = rvCurrent;
        this.title = title;
        this.tvCurrentTip = tvCurrentTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActCurrentSetFiveBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCurrentSetFiveBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCurrentSetFiveBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_current_set_five, root, attachToRoot, component);
    }

    public static ActCurrentSetFiveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCurrentSetFiveBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCurrentSetFiveBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_current_set_five, null, false, component);
    }

    public static ActCurrentSetFiveBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCurrentSetFiveBinding bind(View view, Object component) {
        return (ActCurrentSetFiveBinding) bind(component, view, R.layout.act_current_set_five);
    }
}