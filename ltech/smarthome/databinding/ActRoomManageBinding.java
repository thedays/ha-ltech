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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActRoomManageVM;

/* loaded from: classes3.dex */
public abstract class ActRoomManageBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView5;
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;
    public final RecyclerView layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActRoomManageVM mViewmodel;
    public final ConstraintLayout title;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActRoomManageVM viewmodel);

    protected ActRoomManageBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView5, AppCompatImageView ivBack, AppCompatImageView ivEdit, RecyclerView layoutLoad, ConstraintLayout title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView5 = appCompatTextView5;
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.layoutLoad = layoutLoad;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActRoomManageVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActRoomManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRoomManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRoomManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_room_manage, root, attachToRoot, component);
    }

    public static ActRoomManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRoomManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRoomManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_room_manage, null, false, component);
    }

    public static ActRoomManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRoomManageBinding bind(View view, Object component) {
        return (ActRoomManageBinding) bind(component, view, R.layout.act_room_manage);
    }
}