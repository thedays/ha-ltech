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
public abstract class ActAddInstructBinding extends ViewDataBinding {
    public final LinearLayout layoutBottom;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAddInstruct;
    public final AppCompatTextView tvSave;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActAddInstructBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBottom, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvAddInstruct, AppCompatTextView tvSave) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBottom = layoutBottom;
        this.rvContent = rvContent;
        this.title = title;
        this.tvAddInstruct = tvAddInstruct;
        this.tvSave = tvSave;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActAddInstructBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddInstructBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddInstructBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_instruct, root, attachToRoot, component);
    }

    public static ActAddInstructBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddInstructBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddInstructBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_instruct, null, false, component);
    }

    public static ActAddInstructBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddInstructBinding bind(View view, Object component) {
        return (ActAddInstructBinding) bind(component, view, R.layout.act_add_instruct);
    }
}