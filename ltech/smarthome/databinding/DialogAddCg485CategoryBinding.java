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
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public abstract class DialogAddCg485CategoryBinding extends ViewDataBinding {
    public final AppCompatEditText etName;
    public final Group groupAdd;
    public final AppCompatImageView ivClear;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final RadioImageTextView radio485ToBle;
    public final RadioImageTextView radioBleTo485;
    public final RecyclerView rvColor;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvColor;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvType;
    public final View viewDivider;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogAddCg485CategoryBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etName, Group groupAdd, AppCompatImageView ivClear, ConstraintLayout layoutBg, RadioImageTextView radio485ToBle, RadioImageTextView radioBleTo485, RecyclerView rvColor, AppCompatTextView tvCancel, AppCompatTextView tvColor, AppCompatTextView tvLabel, AppCompatTextView tvSave, AppCompatTextView tvTitle, AppCompatTextView tvType, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etName = etName;
        this.groupAdd = groupAdd;
        this.ivClear = ivClear;
        this.layoutBg = layoutBg;
        this.radio485ToBle = radio485ToBle;
        this.radioBleTo485 = radioBleTo485;
        this.rvColor = rvColor;
        this.tvCancel = tvCancel;
        this.tvColor = tvColor;
        this.tvLabel = tvLabel;
        this.tvSave = tvSave;
        this.tvTitle = tvTitle;
        this.tvType = tvType;
        this.viewDivider = viewDivider;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogAddCg485CategoryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddCg485CategoryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogAddCg485CategoryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_cg485_category, root, attachToRoot, component);
    }

    public static DialogAddCg485CategoryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddCg485CategoryBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogAddCg485CategoryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_cg485_category, null, false, component);
    }

    public static DialogAddCg485CategoryBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddCg485CategoryBinding bind(View view, Object component) {
        return (DialogAddCg485CategoryBinding) bind(component, view, R.layout.dialog_add_cg485_category);
    }
}