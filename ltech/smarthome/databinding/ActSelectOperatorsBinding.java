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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectOperatorsBinding extends ViewDataBinding {
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvLocation;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectOperatorsBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout layoutLoad, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvLocation) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
        this.tvLocation = tvLocation;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectOperatorsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectOperatorsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectOperatorsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_operators, root, attachToRoot, component);
    }

    public static ActSelectOperatorsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectOperatorsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectOperatorsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_operators, null, false, component);
    }

    public static ActSelectOperatorsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectOperatorsBinding bind(View view, Object component) {
        return (ActSelectOperatorsBinding) bind(component, view, R.layout.act_select_operators);
    }
}