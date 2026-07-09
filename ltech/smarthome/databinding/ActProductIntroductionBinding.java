package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActProductIntroductionBinding extends ViewDataBinding {
    public final AppCompatButton btNext;
    public final AppCompatImageView ivConfigTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvConfigTip;
    public final AppCompatTextView tvConfigTitle;
    public final AppCompatTextView tvFailTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActProductIntroductionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btNext, AppCompatImageView ivConfigTip, AppCompatTextView tvConfigTip, AppCompatTextView tvConfigTitle, AppCompatTextView tvFailTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.ivConfigTip = ivConfigTip;
        this.tvConfigTip = tvConfigTip;
        this.tvConfigTitle = tvConfigTitle;
        this.tvFailTip = tvFailTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActProductIntroductionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActProductIntroductionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActProductIntroductionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_product_introduction, root, attachToRoot, component);
    }

    public static ActProductIntroductionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActProductIntroductionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActProductIntroductionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_product_introduction, null, false, component);
    }

    public static ActProductIntroductionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActProductIntroductionBinding bind(View view, Object component) {
        return (ActProductIntroductionBinding) bind(component, view, R.layout.act_product_introduction);
    }
}