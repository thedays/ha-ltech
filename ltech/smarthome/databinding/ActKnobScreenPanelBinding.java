package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
public abstract class ActKnobScreenPanelBinding extends ViewDataBinding {
    public final AppCompatImageView ivS4;
    public final AppCompatImageView ivScreen;
    public final AppCompatImageView ivShadowLeft;
    public final AppCompatImageView ivShadowRight;
    public final LinearLayout layoutContent;
    public final LinearLayout layoutS4;
    public final LinearLayout llBottom;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvKnobAction;
    public final RecyclerView rvScreenInfo;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBindName;
    public final AppCompatTextView tvInstruction;
    public final AppCompatTextView tvTipMessage;
    public final View viewGuide;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActKnobScreenPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivS4, AppCompatImageView ivScreen, AppCompatImageView ivShadowLeft, AppCompatImageView ivShadowRight, LinearLayout layoutContent, LinearLayout layoutS4, LinearLayout llBottom, RecyclerView rvKnobAction, RecyclerView rvScreenInfo, LayoutTitleDefaultBinding title, AppCompatTextView tvBindName, AppCompatTextView tvInstruction, AppCompatTextView tvTipMessage, View viewGuide) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivS4 = ivS4;
        this.ivScreen = ivScreen;
        this.ivShadowLeft = ivShadowLeft;
        this.ivShadowRight = ivShadowRight;
        this.layoutContent = layoutContent;
        this.layoutS4 = layoutS4;
        this.llBottom = llBottom;
        this.rvKnobAction = rvKnobAction;
        this.rvScreenInfo = rvScreenInfo;
        this.title = title;
        this.tvBindName = tvBindName;
        this.tvInstruction = tvInstruction;
        this.tvTipMessage = tvTipMessage;
        this.viewGuide = viewGuide;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActKnobScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobScreenPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActKnobScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_knob_screen_panel, root, attachToRoot, component);
    }

    public static ActKnobScreenPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobScreenPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActKnobScreenPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_knob_screen_panel, null, false, component);
    }

    public static ActKnobScreenPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActKnobScreenPanelBinding bind(View view, Object component) {
        return (ActKnobScreenPanelBinding) bind(component, view, R.layout.act_knob_screen_panel);
    }
}