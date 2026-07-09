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
import com.ltech.smarthome.ui.login.FtMailRegVM;

/* loaded from: classes3.dex */
public abstract class FtMailRegBinding extends ViewDataBinding {
    public final Button btRegister;
    public final TextInputEditText etAccount;
    public final TextInputEditText etPwd;
    public final TextInputEditText etVerification;
    public final AppCompatImageView ivPassword;

    @Bindable
    protected ProtocolDefault mProtocol;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected FtMailRegVM mViewmodel;
    public final LayoutProtocolDefaultBinding protocol;
    public final TextView textView;
    public final TextInputLayout tilAccount;
    public final TextInputLayout tilPwd;
    public final TextInputLayout tilVerification;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvChange;
    public final AppCompatTextView tvVerification;

    public abstract void setProtocol(ProtocolDefault protocol);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(FtMailRegVM viewmodel);

    protected FtMailRegBinding(Object _bindingComponent, View _root, int _localFieldCount, Button btRegister, TextInputEditText etAccount, TextInputEditText etPwd, TextInputEditText etVerification, AppCompatImageView ivPassword, LayoutProtocolDefaultBinding protocol, TextView textView, TextInputLayout tilAccount, TextInputLayout tilPwd, TextInputLayout tilVerification, LayoutTitleDefaultBinding title, AppCompatTextView tvChange, AppCompatTextView tvVerification) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btRegister = btRegister;
        this.etAccount = etAccount;
        this.etPwd = etPwd;
        this.etVerification = etVerification;
        this.ivPassword = ivPassword;
        this.protocol = protocol;
        this.textView = textView;
        this.tilAccount = tilAccount;
        this.tilPwd = tilPwd;
        this.tilVerification = tilVerification;
        this.title = title;
        this.tvChange = tvChange;
        this.tvVerification = tvVerification;
    }

    public ProtocolDefault getProtocol() {
        return this.mProtocol;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public FtMailRegVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtMailRegBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMailRegBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtMailRegBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_mail_reg, root, attachToRoot, component);
    }

    public static FtMailRegBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMailRegBinding inflate(LayoutInflater inflater, Object component) {
        return (FtMailRegBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_mail_reg, null, false, component);
    }

    public static FtMailRegBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtMailRegBinding bind(View view, Object component) {
        return (FtMailRegBinding) bind(component, view, R.layout.ft_mail_reg);
    }
}