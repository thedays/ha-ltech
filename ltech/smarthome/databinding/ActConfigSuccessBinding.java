package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActConfigSuccessVM;

/* loaded from: classes3.dex */
public abstract class ActConfigSuccessBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView4;
    public final AppCompatImageView appCompatImageView5;
    public final AppCompatImageView appCompatImageView6;
    public final AppCompatTextView appCompatTextView10;
    public final AppCompatTextView appCompatTextView13;
    public final AppCompatTextView appCompatTextView14;
    public final AppCompatTextView appCompatTextView9;
    public final AppCompatButton btNext;
    public final Group groupSetRoom;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActConfigSuccessVM mViewmodel;
    public final AppCompatTextView tvDeviceName;
    public final AppCompatTextView tvRoom;
    public final View vDeviceName;
    public final View vOwnRoom;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActConfigSuccessVM viewmodel);

    protected ActConfigSuccessBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView4, AppCompatImageView appCompatImageView5, AppCompatImageView appCompatImageView6, AppCompatTextView appCompatTextView10, AppCompatTextView appCompatTextView13, AppCompatTextView appCompatTextView14, AppCompatTextView appCompatTextView9, AppCompatButton btNext, Group groupSetRoom, AppCompatTextView tvDeviceName, AppCompatTextView tvRoom, View vDeviceName, View vOwnRoom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView4 = appCompatImageView4;
        this.appCompatImageView5 = appCompatImageView5;
        this.appCompatImageView6 = appCompatImageView6;
        this.appCompatTextView10 = appCompatTextView10;
        this.appCompatTextView13 = appCompatTextView13;
        this.appCompatTextView14 = appCompatTextView14;
        this.appCompatTextView9 = appCompatTextView9;
        this.btNext = btNext;
        this.groupSetRoom = groupSetRoom;
        this.tvDeviceName = tvDeviceName;
        this.tvRoom = tvRoom;
        this.vDeviceName = vDeviceName;
        this.vOwnRoom = vOwnRoom;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActConfigSuccessVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActConfigSuccessBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActConfigSuccessBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActConfigSuccessBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_config_success, root, attachToRoot, component);
    }

    public static ActConfigSuccessBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActConfigSuccessBinding inflate(LayoutInflater inflater, Object component) {
        return (ActConfigSuccessBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_config_success, null, false, component);
    }

    public static ActConfigSuccessBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActConfigSuccessBinding bind(View view, Object component) {
        return (ActConfigSuccessBinding) bind(component, view, R.layout.act_config_success);
    }
}