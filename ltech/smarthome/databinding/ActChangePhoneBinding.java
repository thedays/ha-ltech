package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.login.ActChangePhoneVM;

/* loaded from: classes3.dex */
public abstract class ActChangePhoneBinding extends ViewDataBinding {
    public final Button btRegister;
    public final TextInputEditText etAccount;
    public final TextInputEditText etCountry;
    public final TextInputEditText etVerification;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActChangePhoneVM mViewmodel;
    public final TextView textView;
    public final TextInputLayout tilAccount;
    public final TextInputLayout tilCountry;
    public final TextInputLayout tilVerification;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvErrorTip;
    public final AppCompatTextView tvVerification;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActChangePhoneVM viewmodel);

    protected ActChangePhoneBinding(Object _bindingComponent, View _root, int _localFieldCount, Button btRegister, TextInputEditText etAccount, TextInputEditText etCountry, TextInputEditText etVerification, TextView textView, TextInputLayout tilAccount, TextInputLayout tilCountry, TextInputLayout tilVerification, LayoutTitleDefaultBinding title, AppCompatTextView tvErrorTip, AppCompatTextView tvVerification) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btRegister = btRegister;
        this.etAccount = etAccount;
        this.etCountry = etCountry;
        this.etVerification = etVerification;
        this.textView = textView;
        this.tilAccount = tilAccount;
        this.tilCountry = tilCountry;
        this.tilVerification = tilVerification;
        this.title = title;
        this.tvErrorTip = tvErrorTip;
        this.tvVerification = tvVerification;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActChangePhoneVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActChangePhoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangePhoneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActChangePhoneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_change_phone, root, attachToRoot, component);
    }

    public static ActChangePhoneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangePhoneBinding inflate(LayoutInflater inflater, Object component) {
        return (ActChangePhoneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_change_phone, null, false, component);
    }

    public static ActChangePhoneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangePhoneBinding bind(View view, Object component) {
        return (ActChangePhoneBinding) bind(component, view, R.layout.act_change_phone);
    }
}