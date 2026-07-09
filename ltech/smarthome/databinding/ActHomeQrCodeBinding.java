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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActHomeQrCodeBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView17;
    public final AppCompatTextView appCompatTextView20;
    public final AppCompatImageView ivQrCode;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvHomeName;
    public final View view12;
    public final View view13;

    public abstract void setTitle(TitleDefault title);

    protected ActHomeQrCodeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView17, AppCompatTextView appCompatTextView20, AppCompatImageView ivQrCode, ConstraintLayout layoutLoad, LayoutTitleDefaultBinding title, AppCompatTextView tvHomeName, View view12, View view13) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView17 = appCompatTextView17;
        this.appCompatTextView20 = appCompatTextView20;
        this.ivQrCode = ivQrCode;
        this.layoutLoad = layoutLoad;
        this.title = title;
        this.tvHomeName = tvHomeName;
        this.view12 = view12;
        this.view13 = view13;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActHomeQrCodeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeQrCodeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomeQrCodeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_qr_code, root, attachToRoot, component);
    }

    public static ActHomeQrCodeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeQrCodeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomeQrCodeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_qr_code, null, false, component);
    }

    public static ActHomeQrCodeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeQrCodeBinding bind(View view, Object component) {
        return (ActHomeQrCodeBinding) bind(component, view, R.layout.act_home_qr_code);
    }
}