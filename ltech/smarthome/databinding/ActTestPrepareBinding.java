package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActTestPrepareBinding extends ViewDataBinding {
    public final AppCompatImageView ivTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvConfigTip;
    public final AppCompatTextView tvConfigTitle;
    public final AppCompatTextView tvInstall;
    public final AppCompatTextView tvStartTest;
    public final AppCompatTextView tvUnit;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActTestPrepareBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivTip, RecyclerView rvContent, AppCompatTextView tvConfigTip, AppCompatTextView tvConfigTitle, AppCompatTextView tvInstall, AppCompatTextView tvStartTest, AppCompatTextView tvUnit) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivTip = ivTip;
        this.rvContent = rvContent;
        this.tvConfigTip = tvConfigTip;
        this.tvConfigTitle = tvConfigTitle;
        this.tvInstall = tvInstall;
        this.tvStartTest = tvStartTest;
        this.tvUnit = tvUnit;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActTestPrepareBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestPrepareBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTestPrepareBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_test_prepare, root, attachToRoot, component);
    }

    public static ActTestPrepareBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestPrepareBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTestPrepareBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_test_prepare, null, false, component);
    }

    public static ActTestPrepareBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestPrepareBinding bind(View view, Object component) {
        return (ActTestPrepareBinding) bind(component, view, R.layout.act_test_prepare);
    }
}