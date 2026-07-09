package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class DialogAddCg485DeviceBinding extends ViewDataBinding {
    public final AppCompatEditText etName;
    public final Group groupAdd;
    public final AppCompatImageView ivClear;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final RecyclerView rvIcon;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvIcon;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTitle;
    public final View viewDivider;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogAddCg485DeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etName, Group groupAdd, AppCompatImageView ivClear, ConstraintLayout layoutBg, RecyclerView rvIcon, AppCompatTextView tvCancel, AppCompatTextView tvIcon, AppCompatTextView tvLabel, AppCompatTextView tvSave, AppCompatTextView tvTitle, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etName = etName;
        this.groupAdd = groupAdd;
        this.ivClear = ivClear;
        this.layoutBg = layoutBg;
        this.rvIcon = rvIcon;
        this.tvCancel = tvCancel;
        this.tvIcon = tvIcon;
        this.tvLabel = tvLabel;
        this.tvSave = tvSave;
        this.tvTitle = tvTitle;
        this.viewDivider = viewDivider;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogAddCg485DeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddCg485DeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogAddCg485DeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_cg485_device, root, attachToRoot, component);
    }

    public static DialogAddCg485DeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddCg485DeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogAddCg485DeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_cg485_device, null, false, component);
    }

    public static DialogAddCg485DeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddCg485DeviceBinding bind(View view, Object component) {
        return (DialogAddCg485DeviceBinding) bind(component, view, R.layout.dialog_add_cg485_device);
    }
}