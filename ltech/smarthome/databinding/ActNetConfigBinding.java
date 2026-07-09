package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActNetConfigBinding extends ViewDataBinding {
    public final AppCompatButton btNext;
    public final AppCompatEditText etPwd;
    public final AppCompatEditText etRouterName;
    public final AppCompatImageView ivEye;
    public final LinearLayout layoutPic;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected MutableLiveData<String> mPassword;

    @Bindable
    protected MutableLiveData<String> mSsid;

    @Bindable
    protected TitleDefault mTitle;
    public final TextInputLayout tilPwd;
    public final TextInputLayout tilRouterName;
    public final AppCompatTextView tvChangeNetwork;
    public final AppCompatTextView tvQuestion;
    public final View vFocus;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setPassword(MutableLiveData<String> password);

    public abstract void setSsid(MutableLiveData<String> ssid);

    public abstract void setTitle(TitleDefault title);

    protected ActNetConfigBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btNext, AppCompatEditText etPwd, AppCompatEditText etRouterName, AppCompatImageView ivEye, LinearLayout layoutPic, TextInputLayout tilPwd, TextInputLayout tilRouterName, AppCompatTextView tvChangeNetwork, AppCompatTextView tvQuestion, View vFocus) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.etPwd = etPwd;
        this.etRouterName = etRouterName;
        this.ivEye = ivEye;
        this.layoutPic = layoutPic;
        this.tilPwd = tilPwd;
        this.tilRouterName = tilRouterName;
        this.tvChangeNetwork = tvChangeNetwork;
        this.tvQuestion = tvQuestion;
        this.vFocus = vFocus;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public MutableLiveData<String> getSsid() {
        return this.mSsid;
    }

    public MutableLiveData<String> getPassword() {
        return this.mPassword;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActNetConfigBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNetConfigBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActNetConfigBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_net_config, root, attachToRoot, component);
    }

    public static ActNetConfigBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNetConfigBinding inflate(LayoutInflater inflater, Object component) {
        return (ActNetConfigBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_net_config, null, false, component);
    }

    public static ActNetConfigBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActNetConfigBinding bind(View view, Object component) {
        return (ActNetConfigBinding) bind(component, view, R.layout.act_net_config);
    }
}