package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM;
import com.ltech.smarthome.view.AnnularColorPickView;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ActAsPanelBinding extends ViewDataBinding {
    public final AnnularColorPickView annularColorPickView;
    public final AppCompatTextView appCompatTextView36;
    public final CircleImageView civRgb;
    public final CircleImageView civW;
    public final ConstraintLayout constraintLayout;
    public final ColorSeekBar csbColorBar;
    public final Group groupBrt;
    public final Group groupColorTop;
    public final Group groupOpen;
    public final Group groupWy;
    public final AppCompatImageView ivMode;
    public final AppCompatImageView ivOnOff;
    public final AppCompatImageView ivOpen;
    public final AppCompatImageView ivRgb;
    public final AppCompatImageView ivScene;
    public final AppCompatImageView ivW;
    public final ConstraintLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAsPanelVM mViewmodel;
    public final RecyclerView rvRelatedInfo;
    public final RecyclerView rvScene;
    public final LightBrtBar sbBrt;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBrt;
    public final AppCompatTextView tvBrtTip;
    public final AppCompatTextView tvColor;
    public final AppCompatTextView tvColorTip;
    public final View vOpen;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAsPanelVM viewmodel);

    protected ActAsPanelBinding(Object _bindingComponent, View _root, int _localFieldCount, AnnularColorPickView annularColorPickView, AppCompatTextView appCompatTextView36, CircleImageView civRgb, CircleImageView civW, ConstraintLayout constraintLayout, ColorSeekBar csbColorBar, Group groupBrt, Group groupColorTop, Group groupOpen, Group groupWy, AppCompatImageView ivMode, AppCompatImageView ivOnOff, AppCompatImageView ivOpen, AppCompatImageView ivRgb, AppCompatImageView ivScene, AppCompatImageView ivW, ConstraintLayout layoutContent, RecyclerView rvRelatedInfo, RecyclerView rvScene, LightBrtBar sbBrt, LayoutTitleDefaultBinding title, AppCompatTextView tvBrt, AppCompatTextView tvBrtTip, AppCompatTextView tvColor, AppCompatTextView tvColorTip, View vOpen) {
        super(_bindingComponent, _root, _localFieldCount);
        this.annularColorPickView = annularColorPickView;
        this.appCompatTextView36 = appCompatTextView36;
        this.civRgb = civRgb;
        this.civW = civW;
        this.constraintLayout = constraintLayout;
        this.csbColorBar = csbColorBar;
        this.groupBrt = groupBrt;
        this.groupColorTop = groupColorTop;
        this.groupOpen = groupOpen;
        this.groupWy = groupWy;
        this.ivMode = ivMode;
        this.ivOnOff = ivOnOff;
        this.ivOpen = ivOpen;
        this.ivRgb = ivRgb;
        this.ivScene = ivScene;
        this.ivW = ivW;
        this.layoutContent = layoutContent;
        this.rvRelatedInfo = rvRelatedInfo;
        this.rvScene = rvScene;
        this.sbBrt = sbBrt;
        this.title = title;
        this.tvBrt = tvBrt;
        this.tvBrtTip = tvBrtTip;
        this.tvColor = tvColor;
        this.tvColorTip = tvColorTip;
        this.vOpen = vOpen;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAsPanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAsPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAsPanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAsPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_as_panel, root, attachToRoot, component);
    }

    public static ActAsPanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAsPanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAsPanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_as_panel, null, false, component);
    }

    public static ActAsPanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAsPanelBinding bind(View view, Object component) {
        return (ActAsPanelBinding) bind(component, view, R.layout.act_as_panel);
    }
}