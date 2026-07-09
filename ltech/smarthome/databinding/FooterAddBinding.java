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

/* loaded from: classes3.dex */
public abstract class FooterAddBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Integer mGravity;

    @Bindable
    protected String mItem;

    @Bindable
    protected Boolean mNoBackground;

    @Bindable
    protected Integer mTextColor;
    public final AppCompatTextView tvRemoveScene;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setGravity(Integer gravity);

    public abstract void setItem(String item);

    public abstract void setNoBackground(Boolean noBackground);

    public abstract void setTextColor(Integer textColor);

    protected FooterAddBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvRemoveScene) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvRemoveScene = tvRemoveScene;
    }

    public String getItem() {
        return this.mItem;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Integer getGravity() {
        return this.mGravity;
    }

    public Integer getTextColor() {
        return this.mTextColor;
    }

    public Boolean getNoBackground() {
        return this.mNoBackground;
    }

    public static FooterAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FooterAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.footer_add, root, attachToRoot, component);
    }

    public static FooterAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterAddBinding inflate(LayoutInflater inflater, Object component) {
        return (FooterAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.footer_add, null, false, component);
    }

    public static FooterAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FooterAddBinding bind(View view, Object component) {
        return (FooterAddBinding) bind(component, view, R.layout.footer_add);
    }
}