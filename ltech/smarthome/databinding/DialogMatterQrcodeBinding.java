package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public abstract class DialogMatterQrcodeBinding extends ViewDataBinding {
    public final View bg;
    public final Group groupFail;
    public final Group groupLoading;
    public final Group groupSuccess;
    public final AppCompatImageView ivQrCode;
    public final AppCompatImageView ivQrCodeBg;
    public final AppCompatImageView ivRefresh;
    public final WaveView progress;
    public final AppCompatTextView tvCopy;
    public final AppCompatTextView tvNum;
    public final TextView tvQrcodeTip;
    public final AppCompatTextView tvRefresh;
    public final AppCompatTextView tvRefresh2;
    public final View viewClose;

    protected DialogMatterQrcodeBinding(Object _bindingComponent, View _root, int _localFieldCount, View bg, Group groupFail, Group groupLoading, Group groupSuccess, AppCompatImageView ivQrCode, AppCompatImageView ivQrCodeBg, AppCompatImageView ivRefresh, WaveView progress, AppCompatTextView tvCopy, AppCompatTextView tvNum, TextView tvQrcodeTip, AppCompatTextView tvRefresh, AppCompatTextView tvRefresh2, View viewClose) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg = bg;
        this.groupFail = groupFail;
        this.groupLoading = groupLoading;
        this.groupSuccess = groupSuccess;
        this.ivQrCode = ivQrCode;
        this.ivQrCodeBg = ivQrCodeBg;
        this.ivRefresh = ivRefresh;
        this.progress = progress;
        this.tvCopy = tvCopy;
        this.tvNum = tvNum;
        this.tvQrcodeTip = tvQrcodeTip;
        this.tvRefresh = tvRefresh;
        this.tvRefresh2 = tvRefresh2;
        this.viewClose = viewClose;
    }

    public static DialogMatterQrcodeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMatterQrcodeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogMatterQrcodeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_matter_qrcode, root, attachToRoot, component);
    }

    public static DialogMatterQrcodeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMatterQrcodeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogMatterQrcodeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_matter_qrcode, null, false, component);
    }

    public static DialogMatterQrcodeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogMatterQrcodeBinding bind(View view, Object component) {
        return (DialogMatterQrcodeBinding) bind(component, view, R.layout.dialog_matter_qrcode);
    }
}