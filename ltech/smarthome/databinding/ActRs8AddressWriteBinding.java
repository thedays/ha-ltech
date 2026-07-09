package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.rs8.ActRs8VM;

/* loaded from: classes3.dex */
public abstract class ActRs8AddressWriteBinding extends ViewDataBinding {
    public final View bg1;
    public final View bg2;
    public final View bg3;
    public final AppCompatEditText editAddress;
    public final AppCompatImageView iv;
    public final AppCompatImageView ivGo;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActRs8VM mViewmodel;
    public final AppCompatRadioButton rdbtnGroup;
    public final AppCompatRadioButton rdbtnSingle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAddress;
    public final TextView tvNext;
    public final AppCompatTextView tvTip;
    public final AppCompatTextView tvType;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActRs8VM viewmodel);

    protected ActRs8AddressWriteBinding(Object _bindingComponent, View _root, int _localFieldCount, View bg1, View bg2, View bg3, AppCompatEditText editAddress, AppCompatImageView iv, AppCompatImageView ivGo, AppCompatRadioButton rdbtnGroup, AppCompatRadioButton rdbtnSingle, LayoutTitleDefaultBinding title, AppCompatTextView tvAddress, TextView tvNext, AppCompatTextView tvTip, AppCompatTextView tvType) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg1 = bg1;
        this.bg2 = bg2;
        this.bg3 = bg3;
        this.editAddress = editAddress;
        this.iv = iv;
        this.ivGo = ivGo;
        this.rdbtnGroup = rdbtnGroup;
        this.rdbtnSingle = rdbtnSingle;
        this.title = title;
        this.tvAddress = tvAddress;
        this.tvNext = tvNext;
        this.tvTip = tvTip;
        this.tvType = tvType;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActRs8VM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActRs8AddressWriteBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8AddressWriteBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRs8AddressWriteBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_address_write, root, attachToRoot, component);
    }

    public static ActRs8AddressWriteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8AddressWriteBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRs8AddressWriteBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rs8_address_write, null, false, component);
    }

    public static ActRs8AddressWriteBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRs8AddressWriteBinding bind(View view, Object component) {
        return (ActRs8AddressWriteBinding) bind(component, view, R.layout.act_rs8_address_write);
    }
}