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
import com.ltech.smarthome.view.MyTimePickerView;

/* loaded from: classes3.dex */
public abstract class DialogRoomPickerBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final MyTimePickerView pickViewFloor;
    public final MyTimePickerView pickerViewRoom;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvFinish;
    public final AppCompatTextView tvTitle;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected DialogRoomPickerBinding(Object _bindingComponent, View _root, int _localFieldCount, MyTimePickerView pickViewFloor, MyTimePickerView pickerViewRoom, AppCompatTextView tvCancel, AppCompatTextView tvFinish, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.pickViewFloor = pickViewFloor;
        this.pickerViewRoom = pickerViewRoom;
        this.tvCancel = tvCancel;
        this.tvFinish = tvFinish;
        this.tvTitle = tvTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogRoomPickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRoomPickerBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogRoomPickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_room_picker, root, attachToRoot, component);
    }

    public static DialogRoomPickerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRoomPickerBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogRoomPickerBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_room_picker, null, false, component);
    }

    public static DialogRoomPickerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRoomPickerBinding bind(View view, Object component) {
        return (DialogRoomPickerBinding) bind(component, view, R.layout.dialog_room_picker);
    }
}