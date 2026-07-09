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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActKnobPanelBinding extends ViewDataBinding {
    public final AppCompatImageView ivDevicePic;
    public final ConstraintLayout layoutContent;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvKnobAction;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBindName;
    public final AppCompatTextView tvInstruction;
    public final AppCompatTextView tvTipMessage;
    public final View view6;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActKnobPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDevicePic, ConstraintLayout layoutContent, RecyclerView rvKnobAction, LayoutTitleDefaultBinding title, AppCompatTextView tvBindName, AppCompatTextView tvInstruction, AppCompatTextView tvTipMessage, View view6) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDevicePic = ivDevicePic;
        this.layoutContent = layoutContent;
        this.rvKnobAction = rvKnobAction;
        this.title = title;
        this.tvBindName = tvBindName;
        this.tvInstruction = tvInstruction;
        this.tvTipMessage = tvTipMessage;
        this.view6 = view6;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActKnobPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActKnobPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_knob_panel, root, attachToRoot, component);
    }

    public static ActKnobPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActKnobPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_knob_panel, null, false, component);
    }

    public static ActKnobPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobPanelBinding bind(View view, Object component) {
        return (ActKnobPanelBinding) bind(component, view, R.layout.act_knob_panel);
    }
}