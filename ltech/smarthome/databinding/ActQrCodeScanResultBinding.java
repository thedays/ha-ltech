package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActQrCodeScanResultVM;

/* loaded from: classes3.dex */
public abstract class ActQrCodeScanResultBinding extends ViewDataBinding {
    public final AppCompatButton btRequestJoin;
    public final AppCompatTextView homeJoinTip;
    public final AppCompatTextView homeName;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActQrCodeScanResultVM mViewmodel;
    public final AppCompatImageView resultRes;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActQrCodeScanResultVM viewmodel);

    protected ActQrCodeScanResultBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btRequestJoin, AppCompatTextView homeJoinTip, AppCompatTextView homeName, AppCompatImageView resultRes) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btRequestJoin = btRequestJoin;
        this.homeJoinTip = homeJoinTip;
        this.homeName = homeName;
        this.resultRes = resultRes;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActQrCodeScanResultVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActQrCodeScanResultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActQrCodeScanResultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActQrCodeScanResultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_qr_code_scan_result, root, attachToRoot, component);
    }

    public static ActQrCodeScanResultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActQrCodeScanResultBinding inflate(LayoutInflater inflater, Object component) {
        return (ActQrCodeScanResultBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_qr_code_scan_result, null, false, component);
    }

    public static ActQrCodeScanResultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActQrCodeScanResultBinding bind(View view, Object component) {
        return (ActQrCodeScanResultBinding) bind(component, view, R.layout.act_qr_code_scan_result);
    }
}