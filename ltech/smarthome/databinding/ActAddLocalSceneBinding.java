package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.scene.local.ActAddLocalSceneVM;
import com.ltech.smarthome.view.SwitchButton;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActAddLocalSceneBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView28;
    public final LinearLayout footerView;
    public final AppCompatImageView ivAction;
    public final AppCompatImageView ivDynamic;
    public final AppCompatImageView ivImport;
    public final AppCompatImageView ivModeIcon;
    public final LinearLayout layoutEditAction;
    public final ConstraintLayout layoutLoad;
    public final LinearLayout layoutSort;
    public final ConstraintLayout layoutTip;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddLocalSceneVM mViewmodel;
    public final SwipeRecyclerView rvAction;
    public final SwitchButton sbAddToCommon;
    public final SwitchButton sbSetDynamic;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvChooseIcon;
    public final AppCompatTextView tvDynamicTip;
    public final AppCompatTextView tvModeName;
    public final AppCompatTextView tvModeNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomNameTip;
    public final AppCompatTextView tvTotalTime;
    public final View vChangeIcon;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddLocalSceneVM viewmodel);

    protected ActAddLocalSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView28, LinearLayout footerView, AppCompatImageView ivAction, AppCompatImageView ivDynamic, AppCompatImageView ivImport, AppCompatImageView ivModeIcon, LinearLayout layoutEditAction, ConstraintLayout layoutLoad, LinearLayout layoutSort, ConstraintLayout layoutTip, SwipeRecyclerView rvAction, SwitchButton sbAddToCommon, SwitchButton sbSetDynamic, LayoutTitleDefaultBinding title, AppCompatTextView tvAction, AppCompatTextView tvChooseIcon, AppCompatTextView tvDynamicTip, AppCompatTextView tvModeName, AppCompatTextView tvModeNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomNameTip, AppCompatTextView tvTotalTime, View vChangeIcon) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView28 = appCompatTextView28;
        this.footerView = footerView;
        this.ivAction = ivAction;
        this.ivDynamic = ivDynamic;
        this.ivImport = ivImport;
        this.ivModeIcon = ivModeIcon;
        this.layoutEditAction = layoutEditAction;
        this.layoutLoad = layoutLoad;
        this.layoutSort = layoutSort;
        this.layoutTip = layoutTip;
        this.rvAction = rvAction;
        this.sbAddToCommon = sbAddToCommon;
        this.sbSetDynamic = sbSetDynamic;
        this.title = title;
        this.tvAction = tvAction;
        this.tvChooseIcon = tvChooseIcon;
        this.tvDynamicTip = tvDynamicTip;
        this.tvModeName = tvModeName;
        this.tvModeNameTip = tvModeNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomNameTip = tvRoomNameTip;
        this.tvTotalTime = tvTotalTime;
        this.vChangeIcon = vChangeIcon;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddLocalSceneVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddLocalSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddLocalSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddLocalSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_local_scene, root, attachToRoot, component);
    }

    public static ActAddLocalSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddLocalSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddLocalSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_local_scene, null, false, component);
    }

    public static ActAddLocalSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddLocalSceneBinding bind(View view, Object component) {
        return (ActAddLocalSceneBinding) bind(component, view, R.layout.act_add_local_scene);
    }
}