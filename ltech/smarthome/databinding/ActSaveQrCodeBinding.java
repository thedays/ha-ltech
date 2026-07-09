package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSaveQrCodeBinding extends ViewDataBinding {
    public final AppCompatImageView ivQrCode;
    public final RelativeLayout layoutContent;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActSaveQrCodeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivQrCode, RelativeLayout layoutContent, LayoutTitleDefaultBinding title, AppCompatTextView tvSave, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivQrCode = ivQrCode;
        this.layoutContent = layoutContent;
        this.title = title;
        this.tvSave = tvSave;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSaveQrCodeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSaveQrCodeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSaveQrCodeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_save_qr_code, root, attachToRoot, component);
    }

    public static ActSaveQrCodeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSaveQrCodeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSaveQrCodeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_save_qr_code, null, false, component);
    }

    public static ActSaveQrCodeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSaveQrCodeBinding bind(View view, Object component) {
        return (ActSaveQrCodeBinding) bind(component, view, R.layout.act_save_qr_code);
    }
}