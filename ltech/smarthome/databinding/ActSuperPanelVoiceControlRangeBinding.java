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
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeVM;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelVoiceControlRangeBinding extends ViewDataBinding {
    public final AppCompatImageView allIcon;
    public final AppCompatTextView allMainText;
    public final AppCompatTextView allSubText;
    public final AppCompatTextView diyCountText;
    public final AppCompatImageView diyGoIcon;
    public final AppCompatImageView diyIcon;
    public final AppCompatTextView diyMainText;
    public final AppCompatTextView diySubText;
    public final ConstraintLayout layoutAll;
    public final ConstraintLayout layoutDiy;
    public final ConstraintLayout layoutPart;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSuperPanelVoiceControlRangeVM mViewmodel;
    public final AppCompatImageView partIcon;
    public final AppCompatTextView partMainText;
    public final AppCompatTextView partSubText;
    public final LayoutTitleDefaultBinding title;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSuperPanelVoiceControlRangeVM viewmodel);

    protected ActSuperPanelVoiceControlRangeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView allIcon, AppCompatTextView allMainText, AppCompatTextView allSubText, AppCompatTextView diyCountText, AppCompatImageView diyGoIcon, AppCompatImageView diyIcon, AppCompatTextView diyMainText, AppCompatTextView diySubText, ConstraintLayout layoutAll, ConstraintLayout layoutDiy, ConstraintLayout layoutPart, AppCompatImageView partIcon, AppCompatTextView partMainText, AppCompatTextView partSubText, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.allIcon = allIcon;
        this.allMainText = allMainText;
        this.allSubText = allSubText;
        this.diyCountText = diyCountText;
        this.diyGoIcon = diyGoIcon;
        this.diyIcon = diyIcon;
        this.diyMainText = diyMainText;
        this.diySubText = diySubText;
        this.layoutAll = layoutAll;
        this.layoutDiy = layoutDiy;
        this.layoutPart = layoutPart;
        this.partIcon = partIcon;
        this.partMainText = partMainText;
        this.partSubText = partSubText;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActSuperPanelVoiceControlRangeVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActSuperPanelVoiceControlRangeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceControlRangeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelVoiceControlRangeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_voice_control_range, root, attachToRoot, component);
    }

    public static ActSuperPanelVoiceControlRangeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceControlRangeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelVoiceControlRangeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_voice_control_range, null, false, component);
    }

    public static ActSuperPanelVoiceControlRangeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelVoiceControlRangeBinding bind(View view, Object component) {
        return (ActSuperPanelVoiceControlRangeBinding) bind(component, view, R.layout.act_super_panel_voice_control_range);
    }
}