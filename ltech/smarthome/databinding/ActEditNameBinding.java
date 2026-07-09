package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActEditNameBinding extends ViewDataBinding {
    public final AppCompatEditText etName;
    public final LayoutTitleDefaultBinding include;
    public final AppCompatImageView ivClear;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mDeleteTip;

    @Bindable
    protected MutableLiveData<String> mName;

    @Bindable
    protected String mNameTip;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvEditTip;
    public final View view9;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDeleteTip(String deleteTip);

    public abstract void setName(MutableLiveData<String> name);

    public abstract void setNameTip(String nameTip);

    public abstract void setTitle(TitleDefault title);

    protected ActEditNameBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etName, LayoutTitleDefaultBinding include, AppCompatImageView ivClear, AppCompatTextView tvDelete, AppCompatTextView tvEditTip, View view9) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etName = etName;
        this.include = include;
        this.ivClear = ivClear;
        this.tvDelete = tvDelete;
        this.tvEditTip = tvEditTip;
        this.view9 = view9;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getNameTip() {
        return this.mNameTip;
    }

    public String getDeleteTip() {
        return this.mDeleteTip;
    }

    public MutableLiveData<String> getName() {
        return this.mName;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActEditNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditNameBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEditNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_name, root, attachToRoot, component);
    }

    public static ActEditNameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditNameBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEditNameBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_name, null, false, component);
    }

    public static ActEditNameBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditNameBinding bind(View view, Object component) {
        return (ActEditNameBinding) bind(component, view, R.layout.act_edit_name);
    }
}