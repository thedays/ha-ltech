package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.share.ActAddPlaceUserVM;

/* loaded from: classes3.dex */
public abstract class ActAddPlaceUserBinding extends ViewDataBinding {
    public final AppCompatImageView ivGoDevice;
    public final AppCompatImageView ivGoRoom;
    public final AppCompatImageView ivGoScene;
    public final AppCompatImageView ivGoSetPermission;
    public final AppCompatImageView ivGoTime;
    public final AppCompatImageView ivMangerTick;
    public final AppCompatImageView ivMemberTick;
    public final RelativeLayout layoutDevice;
    public final ScrollView layoutLoad;
    public final RelativeLayout layoutManager;
    public final RelativeLayout layoutMember;
    public final RelativeLayout layoutRoom;
    public final RelativeLayout layoutScene;
    public final RelativeLayout layoutTime;
    public final RelativeLayout lightGroupPermission;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddPlaceUserVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAccountTip;
    public final AppCompatTextView tvManager;
    public final AppCompatTextView tvMember;
    public final AppCompatTextView tvTime;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddPlaceUserVM viewmodel);

    protected ActAddPlaceUserBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivGoDevice, AppCompatImageView ivGoRoom, AppCompatImageView ivGoScene, AppCompatImageView ivGoSetPermission, AppCompatImageView ivGoTime, AppCompatImageView ivMangerTick, AppCompatImageView ivMemberTick, RelativeLayout layoutDevice, ScrollView layoutLoad, RelativeLayout layoutManager, RelativeLayout layoutMember, RelativeLayout layoutRoom, RelativeLayout layoutScene, RelativeLayout layoutTime, RelativeLayout lightGroupPermission, LayoutTitleDefaultBinding title, AppCompatTextView tvAccountTip, AppCompatTextView tvManager, AppCompatTextView tvMember, AppCompatTextView tvTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivGoDevice = ivGoDevice;
        this.ivGoRoom = ivGoRoom;
        this.ivGoScene = ivGoScene;
        this.ivGoSetPermission = ivGoSetPermission;
        this.ivGoTime = ivGoTime;
        this.ivMangerTick = ivMangerTick;
        this.ivMemberTick = ivMemberTick;
        this.layoutDevice = layoutDevice;
        this.layoutLoad = layoutLoad;
        this.layoutManager = layoutManager;
        this.layoutMember = layoutMember;
        this.layoutRoom = layoutRoom;
        this.layoutScene = layoutScene;
        this.layoutTime = layoutTime;
        this.lightGroupPermission = lightGroupPermission;
        this.title = title;
        this.tvAccountTip = tvAccountTip;
        this.tvManager = tvManager;
        this.tvMember = tvMember;
        this.tvTime = tvTime;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddPlaceUserVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddPlaceUserBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddPlaceUserBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddPlaceUserBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_place_user, root, attachToRoot, component);
    }

    public static ActAddPlaceUserBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddPlaceUserBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddPlaceUserBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_place_user, null, false, component);
    }

    public static ActAddPlaceUserBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddPlaceUserBinding bind(View view, Object component) {
        return (ActAddPlaceUserBinding) bind(component, view, R.layout.act_add_place_user);
    }
}