package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.FtLogVM;

/* loaded from: classes3.dex */
public abstract class ActOpenDoorLogDetailBinding extends ViewDataBinding {
    public final AppCompatImageView ivSipPic;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected FtLogVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAccessControl;
    public final AppCompatTextView tvOpenDoorPerson;
    public final AppCompatTextView tvOpenDoorTime;
    public final AppCompatTextView tvOpenDoorWay;
    public final AppCompatTextView tvStatus;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(FtLogVM viewmodel);

    protected ActOpenDoorLogDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivSipPic, LayoutTitleDefaultBinding title, AppCompatTextView tvAccessControl, AppCompatTextView tvOpenDoorPerson, AppCompatTextView tvOpenDoorTime, AppCompatTextView tvOpenDoorWay, AppCompatTextView tvStatus) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivSipPic = ivSipPic;
        this.title = title;
        this.tvAccessControl = tvAccessControl;
        this.tvOpenDoorPerson = tvOpenDoorPerson;
        this.tvOpenDoorTime = tvOpenDoorTime;
        this.tvOpenDoorWay = tvOpenDoorWay;
        this.tvStatus = tvStatus;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public FtLogVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActOpenDoorLogDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActOpenDoorLogDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActOpenDoorLogDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_open_door_log_detail, root, attachToRoot, component);
    }

    public static ActOpenDoorLogDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActOpenDoorLogDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (ActOpenDoorLogDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_open_door_log_detail, null, false, component);
    }

    public static ActOpenDoorLogDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActOpenDoorLogDetailBinding bind(View view, Object component) {
        return (ActOpenDoorLogDetailBinding) bind(component, view, R.layout.act_open_door_log_detail);
    }
}