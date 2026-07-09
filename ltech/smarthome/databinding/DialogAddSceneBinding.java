package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public abstract class DialogAddSceneBinding extends ViewDataBinding {
    public final AppCompatEditText etName;
    public final AppCompatImageView ivClear;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView pickViewFloor;
    public final RecyclerView pickerViewRoom;
    public final RadioImageTextView radioCloud;
    public final RadioImageTextView radioLocal;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvType;
    public final View viewDivider;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogAddSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etName, AppCompatImageView ivClear, ConstraintLayout layoutBg, RecyclerView pickViewFloor, RecyclerView pickerViewRoom, RadioImageTextView radioCloud, RadioImageTextView radioLocal, AppCompatTextView tvCancel, AppCompatTextView tvLabel, AppCompatTextView tvSave, AppCompatTextView tvTitle, AppCompatTextView tvType, View viewDivider) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etName = etName;
        this.ivClear = ivClear;
        this.layoutBg = layoutBg;
        this.pickViewFloor = pickViewFloor;
        this.pickerViewRoom = pickerViewRoom;
        this.radioCloud = radioCloud;
        this.radioLocal = radioLocal;
        this.tvCancel = tvCancel;
        this.tvLabel = tvLabel;
        this.tvSave = tvSave;
        this.tvTitle = tvTitle;
        this.tvType = tvType;
        this.viewDivider = viewDivider;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogAddSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogAddSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_scene, root, attachToRoot, component);
    }

    public static DialogAddSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogAddSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_add_scene, null, false, component);
    }

    public static DialogAddSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogAddSceneBinding bind(View view, Object component) {
        return (DialogAddSceneBinding) bind(component, view, R.layout.dialog_add_scene);
    }
}