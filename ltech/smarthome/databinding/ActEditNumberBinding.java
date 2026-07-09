package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActEditNumberBinding extends ViewDataBinding {
    public final AppCompatEditText etContent;
    public final AppCompatImageView ivDelete;
    public final LinearLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvError;
    public final AppCompatTextView tvTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActEditNumberBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etContent, AppCompatImageView ivDelete, LinearLayout layoutBg, LayoutTitleDefaultBinding title, AppCompatTextView tvError, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etContent = etContent;
        this.ivDelete = ivDelete;
        this.layoutBg = layoutBg;
        this.title = title;
        this.tvError = tvError;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActEditNumberBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditNumberBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEditNumberBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_number, root, attachToRoot, component);
    }

    public static ActEditNumberBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditNumberBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEditNumberBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_number, null, false, component);
    }

    public static ActEditNumberBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditNumberBinding bind(View view, Object component) {
        return (ActEditNumberBinding) bind(component, view, R.layout.act_edit_number);
    }
}