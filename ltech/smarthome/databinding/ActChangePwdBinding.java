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
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.login.ActChangePwdVM;
import com.ltech.smarthome.view.ClearEditText;

/* loaded from: classes3.dex */
public abstract class ActChangePwdBinding extends ViewDataBinding {
    public final Button btForget;
    public final Button btRegister;
    public final ClearEditText etNewPwd;
    public final ClearEditText etOldPwd;
    public final AppCompatImageView ivNewPassword;
    public final AppCompatImageView ivOldPassword;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActChangePwdVM mViewmodel;
    public final TextView textView;
    public final TextInputLayout tilNewPwd;
    public final TextInputLayout tilOldPwd;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvErrorTip;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActChangePwdVM viewmodel);

    protected ActChangePwdBinding(Object _bindingComponent, View _root, int _localFieldCount, Button btForget, Button btRegister, ClearEditText etNewPwd, ClearEditText etOldPwd, AppCompatImageView ivNewPassword, AppCompatImageView ivOldPassword, TextView textView, TextInputLayout tilNewPwd, TextInputLayout tilOldPwd, LayoutTitleDefaultBinding title, AppCompatTextView tvErrorTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btForget = btForget;
        this.btRegister = btRegister;
        this.etNewPwd = etNewPwd;
        this.etOldPwd = etOldPwd;
        this.ivNewPassword = ivNewPassword;
        this.ivOldPassword = ivOldPassword;
        this.textView = textView;
        this.tilNewPwd = tilNewPwd;
        this.tilOldPwd = tilOldPwd;
        this.title = title;
        this.tvErrorTip = tvErrorTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActChangePwdVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActChangePwdBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangePwdBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActChangePwdBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_change_pwd, root, attachToRoot, component);
    }

    public static ActChangePwdBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangePwdBinding inflate(LayoutInflater inflater, Object component) {
        return (ActChangePwdBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_change_pwd, null, false, component);
    }

    public static ActChangePwdBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangePwdBinding bind(View view, Object component) {
        return (ActChangePwdBinding) bind(component, view, R.layout.act_change_pwd);
    }
}