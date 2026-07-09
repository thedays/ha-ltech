package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActQrCodeScanBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleTranBinding title;
    public final ZXingView zxingview;

    public abstract void setTitle(TitleDefault title);

    protected ActQrCodeScanBinding(Object _bindingComponent, View _root, int _localFieldCount, LayoutTitleTranBinding title, ZXingView zxingview) {
        super(_bindingComponent, _root, _localFieldCount);
        this.title = title;
        this.zxingview = zxingview;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActQrCodeScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActQrCodeScanBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActQrCodeScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_qr_code_scan, root, attachToRoot, component);
    }

    public static ActQrCodeScanBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActQrCodeScanBinding inflate(LayoutInflater inflater, Object component) {
        return (ActQrCodeScanBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_qr_code_scan, null, false, component);
    }

    public static ActQrCodeScanBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActQrCodeScanBinding bind(View view, Object component) {
        return (ActQrCodeScanBinding) bind(component, view, R.layout.act_qr_code_scan);
    }
}