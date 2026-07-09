package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ItemTextAddBinding extends ViewDataBinding {
    public final AppCompatImageView ivIcon;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Integer mImageRes;

    @Bindable
    protected String mItem;

    @Bindable
    protected Integer mTextColor;
    public final RelativeLayout rlAdd;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setImageRes(Integer imageRes);

    public abstract void setItem(String item);

    public abstract void setTextColor(Integer textColor);

    protected ItemTextAddBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivIcon, RelativeLayout rlAdd) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivIcon = ivIcon;
        this.rlAdd = rlAdd;
    }

    public String getItem() {
        return this.mItem;
    }

    public Integer getImageRes() {
        return this.mImageRes;
    }

    public Integer getTextColor() {
        return this.mTextColor;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemTextAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemTextAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_add, root, attachToRoot, component);
    }

    public static ItemTextAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextAddBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemTextAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_text_add, null, false, component);
    }

    public static ItemTextAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTextAddBinding bind(View view, Object component) {
        return (ItemTextAddBinding) bind(component, view, R.layout.item_text_add);
    }
}