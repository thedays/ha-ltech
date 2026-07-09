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
import com.ltech.smarthome.ui.scene.ActAddCloudSceneVM;
import com.ltech.smarthome.view.SwitchButton;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActAddCloudSceneBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView28;
    public final LinearLayout footerView;
    public final AppCompatImageView ivAction;
    public final AppCompatImageView ivImport;
    public final AppCompatImageView ivModeIcon;
    public final LinearLayout layoutEditAction;
    public final ConstraintLayout layoutLoad;
    public final LinearLayout layoutSort;
    public final ConstraintLayout layoutTip;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddCloudSceneVM mViewmodel;
    public final SwipeRecyclerView rvAction;
    public final SwitchButton sbAddToCommon;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAction;
    public final AppCompatTextView tvChooseIcon;
    public final AppCompatTextView tvModeName;
    public final AppCompatTextView tvModeNameTip;
    public final AppCompatTextView tvRoomName;
    public final AppCompatTextView tvRoomNameTip;
    public final AppCompatTextView tvTotalTime;
    public final View vChangeIcon;
    public final View view17;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddCloudSceneVM viewmodel);

    protected ActAddCloudSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView28, LinearLayout footerView, AppCompatImageView ivAction, AppCompatImageView ivImport, AppCompatImageView ivModeIcon, LinearLayout layoutEditAction, ConstraintLayout layoutLoad, LinearLayout layoutSort, ConstraintLayout layoutTip, SwipeRecyclerView rvAction, SwitchButton sbAddToCommon, LayoutTitleDefaultBinding title, AppCompatTextView tvAction, AppCompatTextView tvChooseIcon, AppCompatTextView tvModeName, AppCompatTextView tvModeNameTip, AppCompatTextView tvRoomName, AppCompatTextView tvRoomNameTip, AppCompatTextView tvTotalTime, View vChangeIcon, View view17) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView28 = appCompatTextView28;
        this.footerView = footerView;
        this.ivAction = ivAction;
        this.ivImport = ivImport;
        this.ivModeIcon = ivModeIcon;
        this.layoutEditAction = layoutEditAction;
        this.layoutLoad = layoutLoad;
        this.layoutSort = layoutSort;
        this.layoutTip = layoutTip;
        this.rvAction = rvAction;
        this.sbAddToCommon = sbAddToCommon;
        this.title = title;
        this.tvAction = tvAction;
        this.tvChooseIcon = tvChooseIcon;
        this.tvModeName = tvModeName;
        this.tvModeNameTip = tvModeNameTip;
        this.tvRoomName = tvRoomName;
        this.tvRoomNameTip = tvRoomNameTip;
        this.tvTotalTime = tvTotalTime;
        this.vChangeIcon = vChangeIcon;
        this.view17 = view17;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddCloudSceneVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddCloudSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_cloud_scene, root, attachToRoot, component);
    }

    public static ActAddCloudSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddCloudSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddCloudSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_cloud_scene, null, false, component);
    }

    public static ActAddCloudSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddCloudSceneBinding bind(View view, Object component) {
        return (ActAddCloudSceneBinding) bind(component, view, R.layout.act_add_cloud_scene);
    }
}