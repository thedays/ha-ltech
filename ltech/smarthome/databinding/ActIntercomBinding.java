package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public abstract class ActIntercomBinding extends ViewDataBinding {
    public final Guideline bluetoothGuideline;
    public final Guideline doorGuideline;
    public final View doorLine;
    public final Group groupClick;
    public final Group groupQrcode;
    public final Guideline guideline;
    public final AppCompatImageView intercomQrcode;
    public final AppCompatTextView intercomQrcodeTip;
    public final AppCompatImageView ivBluetooth;
    public final AppCompatImageView ivBluetoothGo;
    public final AppCompatImageView ivDoor;
    public final AppCompatImageView ivDoorGo;
    public final AppCompatImageView ivPassword;
    public final AppCompatImageView ivPasswordGo;
    public final AppCompatImageView ivQrcode;
    public final AppCompatImageView ivQrcodeGo;
    public final AppCompatImageView ivRecord;
    public final AppCompatImageView ivRecordGo;
    public final AppCompatImageView ivSearch;
    public final ConstraintLayout layoutBluetooth;
    public final LinearLayout layoutDoor;
    public final ConstraintLayout layoutQrcode;
    public final ConstraintLayout layoutRecord;
    public final ConstraintLayout layoutTempKey;
    public final LinearLayout llDoor;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomVM mViewmodel;
    public final Guideline passwordGuideline;
    public final Guideline recordGuideline;
    public final SmartRefreshLayout refreshLayout;
    public final RecyclerView rvDoor;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBluetooth;
    public final AppCompatTextView tvQrcode;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomVM viewmodel);

    protected ActIntercomBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline bluetoothGuideline, Guideline doorGuideline, View doorLine, Group groupClick, Group groupQrcode, Guideline guideline, AppCompatImageView intercomQrcode, AppCompatTextView intercomQrcodeTip, AppCompatImageView ivBluetooth, AppCompatImageView ivBluetoothGo, AppCompatImageView ivDoor, AppCompatImageView ivDoorGo, AppCompatImageView ivPassword, AppCompatImageView ivPasswordGo, AppCompatImageView ivQrcode, AppCompatImageView ivQrcodeGo, AppCompatImageView ivRecord, AppCompatImageView ivRecordGo, AppCompatImageView ivSearch, ConstraintLayout layoutBluetooth, LinearLayout layoutDoor, ConstraintLayout layoutQrcode, ConstraintLayout layoutRecord, ConstraintLayout layoutTempKey, LinearLayout llDoor, Guideline passwordGuideline, Guideline recordGuideline, SmartRefreshLayout refreshLayout, RecyclerView rvDoor, LayoutTitleDefaultBinding title, AppCompatTextView tvBluetooth, AppCompatTextView tvQrcode) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bluetoothGuideline = bluetoothGuideline;
        this.doorGuideline = doorGuideline;
        this.doorLine = doorLine;
        this.groupClick = groupClick;
        this.groupQrcode = groupQrcode;
        this.guideline = guideline;
        this.intercomQrcode = intercomQrcode;
        this.intercomQrcodeTip = intercomQrcodeTip;
        this.ivBluetooth = ivBluetooth;
        this.ivBluetoothGo = ivBluetoothGo;
        this.ivDoor = ivDoor;
        this.ivDoorGo = ivDoorGo;
        this.ivPassword = ivPassword;
        this.ivPasswordGo = ivPasswordGo;
        this.ivQrcode = ivQrcode;
        this.ivQrcodeGo = ivQrcodeGo;
        this.ivRecord = ivRecord;
        this.ivRecordGo = ivRecordGo;
        this.ivSearch = ivSearch;
        this.layoutBluetooth = layoutBluetooth;
        this.layoutDoor = layoutDoor;
        this.layoutQrcode = layoutQrcode;
        this.layoutRecord = layoutRecord;
        this.layoutTempKey = layoutTempKey;
        this.llDoor = llDoor;
        this.passwordGuideline = passwordGuideline;
        this.recordGuideline = recordGuideline;
        this.refreshLayout = refreshLayout;
        this.rvDoor = rvDoor;
        this.title = title;
        this.tvBluetooth = tvBluetooth;
        this.tvQrcode = tvQrcode;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActIntercomVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom, root, attachToRoot, component);
    }

    public static ActIntercomBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom, null, false, component);
    }

    public static ActIntercomBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomBinding bind(View view, Object component) {
        return (ActIntercomBinding) bind(component, view, R.layout.act_intercom);
    }
}