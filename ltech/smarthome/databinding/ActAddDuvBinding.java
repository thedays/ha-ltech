package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActAddDuvBinding extends ViewDataBinding {
    public final AppCompatImageView ivDeviceNameGo;
    public final LinearLayout layoutBottom;
    public final ConstraintLayout layoutDuv;
    public final ConstraintLayout layoutName;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAddK;
    public final AppCompatTextView tvApply;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvNameTip;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActAddDuvBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDeviceNameGo, LinearLayout layoutBottom, ConstraintLayout layoutDuv, ConstraintLayout layoutName, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvAddK, AppCompatTextView tvApply, AppCompatTextView tvName, AppCompatTextView tvNameTip, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDeviceNameGo = ivDeviceNameGo;
        this.layoutBottom = layoutBottom;
        this.layoutDuv = layoutDuv;
        this.layoutName = layoutName;
        this.rvContent = rvContent;
        this.title = title;
        this.tvAddK = tvAddK;
        this.tvApply = tvApply;
        this.tvName = tvName;
        this.tvNameTip = tvNameTip;
        this.tvTitle = tvTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActAddDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddDuvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_duv, root, attachToRoot, component);
    }

    public static ActAddDuvBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddDuvBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddDuvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_duv, null, false, component);
    }

    public static ActAddDuvBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddDuvBinding bind(View view, Object component) {
        return (ActAddDuvBinding) bind(component, view, R.layout.act_add_duv);
    }
}