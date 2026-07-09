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
import com.ltech.smarthome.ui.login.ActChangeEmailVM;
import com.ltech.smarthome.view.ClearEditText;

/* loaded from: classes3.dex */
public abstract class ActChangeEmailBinding extends ViewDataBinding {
    public final Button btRegister;
    public final ClearEditText etAccount;
    public final TextInputEditText etVerification;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActChangeEmailVM mViewmodel;
    public final TextView textView;
    public final TextInputLayout tilAccount;
    public final TextInputLayout tilVerification;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvErrorTip;
    public final AppCompatTextView tvVerification;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActChangeEmailVM viewmodel);

    protected ActChangeEmailBinding(Object _bindingComponent, View _root, int _localFieldCount, Button btRegister, ClearEditText etAccount, TextInputEditText etVerification, TextView textView, TextInputLayout tilAccount, TextInputLayout tilVerification, LayoutTitleDefaultBinding title, AppCompatTextView tvErrorTip, AppCompatTextView tvVerification) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btRegister = btRegister;
        this.etAccount = etAccount;
        this.etVerification = etVerification;
        this.textView = textView;
        this.tilAccount = tilAccount;
        this.tilVerification = tilVerification;
        this.title = title;
        this.tvErrorTip = tvErrorTip;
        this.tvVerification = tvVerification;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActChangeEmailVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActChangeEmailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangeEmailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActChangeEmailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_change_email, root, attachToRoot, component);
    }

    public static ActChangeEmailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangeEmailBinding inflate(LayoutInflater inflater, Object component) {
        return (ActChangeEmailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_change_email, null, false, component);
    }

    public static ActChangeEmailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChangeEmailBinding bind(View view, Object component) {
        return (ActChangeEmailBinding) bind(component, view, R.layout.act_change_email);
    }
}