package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.login.ActLoginVM;
import com.ltech.smarthome.view.ClearEditText;

/* loaded from: classes3.dex */
public abstract class ActLoginBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView;
    public final Button btLogin;
    public final ClearEditText etAccount;
    public final TextInputEditText etCountry;
    public final ClearEditText etPwd;
    public final AppCompatImageView ivFacebook;
    public final AppCompatImageView ivPassword;
    public final AppCompatImageView ivQq;
    public final AppCompatImageView ivRigth;
    public final AppCompatImageView ivTwitter;
    public final AppCompatImageView ivWechat;

    @Bindable
    protected ProtocolDefault mProtocol;

    @Bindable
    protected ActLoginVM mViewmodel;
    public final LayoutProtocolDefaultBinding protocol;
    public final TextView textView;
    public final TextView textView2;
    public final TextView textView4;
    public final TextInputLayout tilAccount;
    public final TextInputLayout tilCountry;
    public final TextInputLayout tilPwd;
    public final TextView tvFindBackPwd;
    public final TextView tvRegister;
    public final View view;
    public final View view2;

    public abstract void setProtocol(ProtocolDefault protocol);

    public abstract void setViewmodel(ActLoginVM viewmodel);

    protected ActLoginBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView, Button btLogin, ClearEditText etAccount, TextInputEditText etCountry, ClearEditText etPwd, AppCompatImageView ivFacebook, AppCompatImageView ivPassword, AppCompatImageView ivQq, AppCompatImageView ivRigth, AppCompatImageView ivTwitter, AppCompatImageView ivWechat, LayoutProtocolDefaultBinding protocol, TextView textView, TextView textView2, TextView textView4, TextInputLayout tilAccount, TextInputLayout tilCountry, TextInputLayout tilPwd, TextView tvFindBackPwd, TextView tvRegister, View view, View view2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView = appCompatTextView;
        this.btLogin = btLogin;
        this.etAccount = etAccount;
        this.etCountry = etCountry;
        this.etPwd = etPwd;
        this.ivFacebook = ivFacebook;
        this.ivPassword = ivPassword;
        this.ivQq = ivQq;
        this.ivRigth = ivRigth;
        this.ivTwitter = ivTwitter;
        this.ivWechat = ivWechat;
        this.protocol = protocol;
        this.textView = textView;
        this.textView2 = textView2;
        this.textView4 = textView4;
        this.tilAccount = tilAccount;
        this.tilCountry = tilCountry;
        this.tilPwd = tilPwd;
        this.tvFindBackPwd = tvFindBackPwd;
        this.tvRegister = tvRegister;
        this.view = view;
        this.view2 = view2;
    }

    public ActLoginVM getViewmodel() {
        return this.mViewmodel;
    }

    public ProtocolDefault getProtocol() {
        return this.mProtocol;
    }

    public static ActLoginBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLoginBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLoginBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_login, root, attachToRoot, component);
    }

    public static ActLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLoginBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLoginBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_login, null, false, component);
    }

    public static ActLoginBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLoginBinding bind(View view, Object component) {
        return (ActLoginBinding) bind(component, view, R.layout.act_login);
    }
}