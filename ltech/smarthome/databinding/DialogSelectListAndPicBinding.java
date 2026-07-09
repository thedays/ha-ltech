package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogSelectListAndPicBinding extends ViewDataBinding {
    public final AppCompatImageView ivPic;
    public final LinearLayout layoutPic;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvConfirm;
    public final AppCompatTextView tvLogoTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogSelectListAndPicBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivPic, LinearLayout layoutPic, RecyclerView rvContent, AppCompatTextView tvCancel, AppCompatTextView tvConfirm, AppCompatTextView tvLogoTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivPic = ivPic;
        this.layoutPic = layoutPic;
        this.rvContent = rvContent;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvLogoTip = tvLogoTip;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSelectListAndPicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectListAndPicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSelectListAndPicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_list_and_pic, root, attachToRoot, component);
    }

    public static DialogSelectListAndPicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectListAndPicBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSelectListAndPicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_select_list_and_pic, null, false, component);
    }

    public static DialogSelectListAndPicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSelectListAndPicBinding bind(View view, Object component) {
        return (DialogSelectListAndPicBinding) bind(component, view, R.layout.dialog_select_list_and_pic);
    }
}