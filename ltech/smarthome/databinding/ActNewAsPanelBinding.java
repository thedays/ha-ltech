package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM;
import com.ltech.smarthome.view.AnnularColorPickView2;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public abstract class ActNewAsPanelBinding extends ViewDataBinding {
    public final AnnularColorPickView2 annularColorPickView;
    public final AppCompatImageView annularColorPickViewTransparentView;
    public final Group groupBrt;
    public final AppCompatImageView ivBindEnd;
    public final AppCompatImageView ivDoubt;
    public final AppCompatImageView ivFunctionDoubt;
    public final AppCompatImageView ivMode;
    public final AppCompatImageView ivRgb;
    public final AppCompatImageView ivSwitch;
    public final AppCompatImageView ivW;
    public final LinearLayout layoutBottom;
    public final LinearLayout layoutBrt100;
    public final LinearLayout layoutBrt25;
    public final LinearLayout layoutBrt50;
    public final LinearLayout layoutBrt75;
    public final ConstraintLayout layoutContent;
    public final ConstraintLayout layoutMultiZone;
    public final ConstraintLayout layoutSceneBind;
    public final ConstraintLayout layoutSingleZone;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActEurPanelVM mViewmodel;
    public final RecyclerView rvMultiBind;
    public final View rvMultiTransparentView;
    public final RecyclerView rvScene;
    public final View rvSceneTransparentView;
    public final LightBrtBar sbBrt;
    public final View singleZoneLine;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBindTitle;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvMultiBindTitle;
    public final AppCompatTextView tvQuickBrt;
    public final AppCompatTextView tvScene;
    public final AppCompatTextView tvWaitBind;
    public final View view6;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActEurPanelVM viewmodel);

    protected ActNewAsPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AnnularColorPickView2 annularColorPickView, AppCompatImageView annularColorPickViewTransparentView, Group groupBrt, AppCompatImageView ivBindEnd, AppCompatImageView ivDoubt, AppCompatImageView ivFunctionDoubt, AppCompatImageView ivMode, AppCompatImageView ivRgb, AppCompatImageView ivSwitch, AppCompatImageView ivW, LinearLayout layoutBottom, LinearLayout layoutBrt100, LinearLayout layoutBrt25, LinearLayout layoutBrt50, LinearLayout layoutBrt75, ConstraintLayout layoutContent, ConstraintLayout layoutMultiZone, ConstraintLayout layoutSceneBind, ConstraintLayout layoutSingleZone, RecyclerView rvMultiBind, View rvMultiTransparentView, RecyclerView rvScene, View rvSceneTransparentView, LightBrtBar sbBrt, View singleZoneLine, LayoutTitleDefaultBinding title, AppCompatTextView tvBindTitle, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvMultiBindTitle, AppCompatTextView tvQuickBrt, AppCompatTextView tvScene, AppCompatTextView tvWaitBind, View view6) {
        super(_bindingComponent, _root, _localFieldCount);
        this.annularColorPickView = annularColorPickView;
        this.annularColorPickViewTransparentView = annularColorPickViewTransparentView;
        this.groupBrt = groupBrt;
        this.ivBindEnd = ivBindEnd;
        this.ivDoubt = ivDoubt;
        this.ivFunctionDoubt = ivFunctionDoubt;
        this.ivMode = ivMode;
        this.ivRgb = ivRgb;
        this.ivSwitch = ivSwitch;
        this.ivW = ivW;
        this.layoutBottom = layoutBottom;
        this.layoutBrt100 = layoutBrt100;
        this.layoutBrt25 = layoutBrt25;
        this.layoutBrt50 = layoutBrt50;
        this.layoutBrt75 = layoutBrt75;
        this.layoutContent = layoutContent;
        this.layoutMultiZone = layoutMultiZone;
        this.layoutSceneBind = layoutSceneBind;
        this.layoutSingleZone = layoutSingleZone;
        this.rvMultiBind = rvMultiBind;
        this.rvMultiTransparentView = rvMultiTransparentView;
        this.rvScene = rvScene;
        this.rvSceneTransparentView = rvSceneTransparentView;
        this.sbBrt = sbBrt;
        this.singleZoneLine = singleZoneLine;
        this.title = title;
        this.tvBindTitle = tvBindTitle;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvMultiBindTitle = tvMultiBindTitle;
        this.tvQuickBrt = tvQuickBrt;
        this.tvScene = tvScene;
        this.tvWaitBind = tvWaitBind;
        this.view6 = view6;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActEurPanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActNewAsPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNewAsPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActNewAsPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_new_as_panel, root, attachToRoot, component);
    }

    public static ActNewAsPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNewAsPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActNewAsPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_new_as_panel, null, false, component);
    }

    public static ActNewAsPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNewAsPanelBinding bind(View view, Object component) {
        return (ActNewAsPanelBinding) bind(component, view, R.layout.act_new_as_panel);
    }
}