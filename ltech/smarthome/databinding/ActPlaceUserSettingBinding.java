package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.share.ActPlaceUserSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActPlaceUserSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivGoDevice;
    public final AppCompatImageView ivGoGroup;
    public final AppCompatImageView ivGoName;
    public final AppCompatImageView ivGoRoom;
    public final AppCompatImageView ivGoScene;
    public final RelativeLayout layoutDevice;
    public final RelativeLayout layoutGroup;
    public final LinearLayout layoutLoad;
    public final RelativeLayout layoutName;
    public final RelativeLayout layoutRoom;
    public final RelativeLayout layoutScene;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActPlaceUserSettingVM mViewmodel;
    public final SwitchButton sb;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAccountTip;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvRemove;
    public final AppCompatTextView tvSetManagerTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActPlaceUserSettingVM viewmodel);

    protected ActPlaceUserSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGoDevice, AppCompatImageView ivGoGroup, AppCompatImageView ivGoName, AppCompatImageView ivGoRoom, AppCompatImageView ivGoScene, RelativeLayout layoutDevice, RelativeLayout layoutGroup, LinearLayout layoutLoad, RelativeLayout layoutName, RelativeLayout layoutRoom, RelativeLayout layoutScene, SwitchButton sb, LayoutTitleDefaultBinding title, AppCompatTextView tvAccountTip, AppCompatTextView tvNameTip, AppCompatTextView tvRemove, AppCompatTextView tvSetManagerTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGoDevice = ivGoDevice;
        this.ivGoGroup = ivGoGroup;
        this.ivGoName = ivGoName;
        this.ivGoRoom = ivGoRoom;
        this.ivGoScene = ivGoScene;
        this.layoutDevice = layoutDevice;
        this.layoutGroup = layoutGroup;
        this.layoutLoad = layoutLoad;
        this.layoutName = layoutName;
        this.layoutRoom = layoutRoom;
        this.layoutScene = layoutScene;
        this.sb = sb;
        this.title = title;
        this.tvAccountTip = tvAccountTip;
        this.tvNameTip = tvNameTip;
        this.tvRemove = tvRemove;
        this.tvSetManagerTip = tvSetManagerTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActPlaceUserSettingVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActPlaceUserSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPlaceUserSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPlaceUserSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_place_user_setting, root, attachToRoot, component);
    }

    public static ActPlaceUserSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPlaceUserSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPlaceUserSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_place_user_setting, null, false, component);
    }

    public static ActPlaceUserSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPlaceUserSettingBinding bind(View view, Object component) {
        return (ActPlaceUserSettingBinding) bind(component, view, R.layout.act_place_user_setting);
    }
}