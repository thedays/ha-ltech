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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.login.FtBindMailVM;

/* loaded from: classes3.dex */
public abstract class FtBindMailBinding extends ViewDataBinding {
    public final Button btRegister;
    public final TextInputEditText etAccount;
    public final TextInputEditText etCountry;
    public final TextInputEditText etPwd;
    public final TextInputEditText etVerification;
    public final AppCompatImageView ivPassword;
    public final AppCompatImageView ivRigth;

    @Bindable
    protected ProtocolDefault mProtocol;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected FtBindMailVM mViewmodel;
    public final LayoutProtocolDefaultBinding protocol;
    public final TextView textView;
    public final TextInputLayout tilAccount;
    public final TextInputLayout tilCountry;
    public final TextInputLayout tilPwd;
    public final TextInputLayout tilVerification;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvChange;
    public final AppCompatTextView tvErrorTip;
    public final AppCompatTextView tvVerification;

    public abstract void setProtocol(ProtocolDefault protocol);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(FtBindMailVM viewmodel);

    protected FtBindMailBinding(Object _bindingComponent, View _root, int _localFieldCount, Button btRegister, TextInputEditText etAccount, TextInputEditText etCountry, TextInputEditText etPwd, TextInputEditText etVerification, AppCompatImageView ivPassword, AppCompatImageView ivRigth, LayoutProtocolDefaultBinding protocol, TextView textView, TextInputLayout tilAccount, TextInputLayout tilCountry, TextInputLayout tilPwd, TextInputLayout tilVerification, LayoutTitleDefaultBinding title, AppCompatTextView tvChange, AppCompatTextView tvErrorTip, AppCompatTextView tvVerification) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btRegister = btRegister;
        this.etAccount = etAccount;
        this.etCountry = etCountry;
        this.etPwd = etPwd;
        this.etVerification = etVerification;
        this.ivPassword = ivPassword;
        this.ivRigth = ivRigth;
        this.protocol = protocol;
        this.textView = textView;
        this.tilAccount = tilAccount;
        this.tilCountry = tilCountry;
        this.tilPwd = tilPwd;
        this.tilVerification = tilVerification;
        this.title = title;
        this.tvChange = tvChange;
        this.tvErrorTip = tvErrorTip;
        this.tvVerification = tvVerification;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public FtBindMailVM getViewmodel() {
        return this.mViewmodel;
    }

    public ProtocolDefault getProtocol() {
        return this.mProtocol;
    }

    public static FtBindMailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtBindMailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtBindMailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_bind_mail, root, attachToRoot, component);
    }

    public static FtBindMailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtBindMailBinding inflate(LayoutInflater inflater, Object component) {
        return (FtBindMailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_bind_mail, null, false, component);
    }

    public static FtBindMailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtBindMailBinding bind(View view, Object component) {
        return (FtBindMailBinding) bind(component, view, R.layout.ft_bind_mail);
    }
}