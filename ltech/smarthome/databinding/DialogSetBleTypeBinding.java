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
public abstract class DialogSetBleTypeBinding extends ViewDataBinding {
    public final Group dryContactType;
    public final AppCompatEditText etDeviceName;
    public final Group groupAsPanelRgb;
    public final Group groupEurPanel;
    public final Group groupG4te;
    public final Group groupOutput;
    public final Group groupTvType;
    public final AppCompatImageView ivClear;
    public final AppCompatImageView ivLocation;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected ObservableField<String> mContent;
    public final RecyclerView pickViewFloor;
    public final RecyclerView pickerViewRoom;
    public final RadioImageTextView radio010V;
    public final RadioImageTextView radio512;
    public final RadioImageTextView radioAc;
    public final RadioImageTextView radioAcAndAir;
    public final RadioImageTextView radioAir;
    public final RadioImageTextView radioControlTypeRgb;
    public final RadioImageTextView radioControlTypeRgbw;
    public final RadioImageTextView radioControlTypeRgbwy;
    public final RadioImageTextView radioCt;
    public final RadioImageTextView radioCurtain;
    public final RadioImageTextView radioCurtainDream;
    public final RadioImageTextView radioDali;
    public final RadioImageTextView radioDim;
    public final RadioImageTextView radioDmx;
    public final RadioImageTextView radioRgb;
    public final RadioImageTextView radioRgbw;
    public final RadioImageTextView radioRgbwy;
    public final RadioImageTextView radioScene;
    public final RadioImageTextView radioScene8;
    public final RadioImageTextView radioTbType1;
    public final RadioImageTextView radioTbType2;
    public final RadioImageTextView radioTbType3;
    public final RadioImageTextView radioZoneMulti;
    public final RadioImageTextView radioZoneSingle;
    public final Group tbType;
    public final AppCompatTextView tvCancel;
    public final AppCompatTextView tvControlType;
    public final AppCompatTextView tvLabel;
    public final AppCompatTextView tvSave;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvTypeOutput;
    public final AppCompatTextView tvTypeTip;
    public final AppCompatTextView tvZoneControl;
    public final View viewDivider;
    public final View viewSecondRow;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(ObservableField<String> content);

    protected DialogSetBleTypeBinding(Object _bindingComponent, View _root, int _localFieldCount, Group dryContactType, AppCompatEditText etDeviceName, Group groupAsPanelRgb, Group groupEurPanel, Group groupG4te, Group groupOutput, Group groupTvType, AppCompatImageView ivClear, AppCompatImageView ivLocation, ConstraintLayout layoutBg, RecyclerView pickViewFloor, RecyclerView pickerViewRoom, RadioImageTextView radio010V, RadioImageTextView radio512, RadioImageTextView radioAc, RadioImageTextView radioAcAndAir, RadioImageTextView radioAir, RadioImageTextView radioControlTypeRgb, RadioImageTextView radioControlTypeRgbw, RadioImageTextView radioControlTypeRgbwy, RadioImageTextView radioCt, RadioImageTextView radioCurtain, RadioImageTextView radioCurtainDream, RadioImageTextView radioDali, RadioImageTextView radioDim, RadioImageTextView radioDmx, RadioImageTextView radioRgb, RadioImageTextView radioRgbw, RadioImageTextView radioRgbwy, RadioImageTextView radioScene, RadioImageTextView radioScene8, RadioImageTextView radioTbType1, RadioImageTextView radioTbType2, RadioImageTextView radioTbType3, RadioImageTextView radioZoneMulti, RadioImageTextView radioZoneSingle, Group tbType, AppCompatTextView tvCancel, AppCompatTextView tvControlType, AppCompatTextView tvLabel, AppCompatTextView tvSave, AppCompatTextView tvTitle, AppCompatTextView tvTypeOutput, AppCompatTextView tvTypeTip, AppCompatTextView tvZoneControl, View viewDivider, View viewSecondRow) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dryContactType = dryContactType;
        this.etDeviceName = etDeviceName;
        this.groupAsPanelRgb = groupAsPanelRgb;
        this.groupEurPanel = groupEurPanel;
        this.groupG4te = groupG4te;
        this.groupOutput = groupOutput;
        this.groupTvType = groupTvType;
        this.ivClear = ivClear;
        this.ivLocation = ivLocation;
        this.layoutBg = layoutBg;
        this.pickViewFloor = pickViewFloor;
        this.pickerViewRoom = pickerViewRoom;
        this.radio010V = radio010V;
        this.radio512 = radio512;
        this.radioAc = radioAc;
        this.radioAcAndAir = radioAcAndAir;
        this.radioAir = radioAir;
        this.radioControlTypeRgb = radioControlTypeRgb;
        this.radioControlTypeRgbw = radioControlTypeRgbw;
        this.radioControlTypeRgbwy = radioControlTypeRgbwy;
        this.radioCt = radioCt;
        this.radioCurtain = radioCurtain;
        this.radioCurtainDream = radioCurtainDream;
        this.radioDali = radioDali;
        this.radioDim = radioDim;
        this.radioDmx = radioDmx;
        this.radioRgb = radioRgb;
        this.radioRgbw = radioRgbw;
        this.radioRgbwy = radioRgbwy;
        this.radioScene = radioScene;
        this.radioScene8 = radioScene8;
        this.radioTbType1 = radioTbType1;
        this.radioTbType2 = radioTbType2;
        this.radioTbType3 = radioTbType3;
        this.radioZoneMulti = radioZoneMulti;
        this.radioZoneSingle = radioZoneSingle;
        this.tbType = tbType;
        this.tvCancel = tvCancel;
        this.tvControlType = tvControlType;
        this.tvLabel = tvLabel;
        this.tvSave = tvSave;
        this.tvTitle = tvTitle;
        this.tvTypeOutput = tvTypeOutput;
        this.tvTypeTip = tvTypeTip;
        this.tvZoneControl = tvZoneControl;
        this.viewDivider = viewDivider;
        this.viewSecondRow = viewSecondRow;
    }

    public ObservableField<String> getContent() {
        return this.mContent;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static DialogSetBleTypeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSetBleTypeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogSetBleTypeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_set_ble_type, root, attachToRoot, component);
    }

    public static DialogSetBleTypeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSetBleTypeBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogSetBleTypeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_set_ble_type, null, false, component);
    }

    public static DialogSetBleTypeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogSetBleTypeBinding bind(View view, Object component) {
        return (DialogSetBleTypeBinding) bind(component, view, R.layout.dialog_set_ble_type);
    }
}