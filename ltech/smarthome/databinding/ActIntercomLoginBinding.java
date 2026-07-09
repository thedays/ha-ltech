package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomLoginVM;
import com.ltech.smarthome.view.ClearEditText;

/* loaded from: classes3.dex */
public abstract class ActIntercomLoginBinding extends ViewDataBinding {
    public final ClearEditText etAccount;
    public final ClearEditText etPassword;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomLoginVM mViewmodel;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvLogin;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomLoginVM viewmodel);

    protected ActIntercomLoginBinding(Object _bindingComponent, View _root, int _localFieldCount, ClearEditText etAccount, ClearEditText etPassword, LayoutTitleDefaultBinding title, AppCompatTextView tvLogin) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etAccount = etAccount;
        this.etPassword = etPassword;
        this.title = title;
        this.tvLogin = tvLogin;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActIntercomLoginVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomLoginBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomLoginBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomLoginBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_login, root, attachToRoot, component);
    }

    public static ActIntercomLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomLoginBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomLoginBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_login, null, false, component);
    }

    public static ActIntercomLoginBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomLoginBinding bind(View view, Object component) {
        return (ActIntercomLoginBinding) bind(component, view, R.layout.act_intercom_login);
    }
}