package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public class DialogSetBleTypeBindingImpl extends DialogSetBleTypeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etDeviceNameandroidTextAttrChanged;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.radio_zone_multi, 12);
        sparseIntArray.put(R.id.radio_zone_single, 13);
        sparseIntArray.put(R.id.radio_control_type_rgb, 14);
        sparseIntArray.put(R.id.radio_control_type_rgbw, 15);
        sparseIntArray.put(R.id.radio_control_type_rgbwy, 16);
        sparseIntArray.put(R.id.radio_010V, 17);
        sparseIntArray.put(R.id.radio_dali, 18);
        sparseIntArray.put(R.id.radio_dmx, 19);
        sparseIntArray.put(R.id.radio_dim, 20);
        sparseIntArray.put(R.id.radio_ct, 21);
        sparseIntArray.put(R.id.radio_rgb, 22);
        sparseIntArray.put(R.id.view_second_row, 23);
        sparseIntArray.put(R.id.radio_rgbw, 24);
        sparseIntArray.put(R.id.radio_rgbwy, 25);
        sparseIntArray.put(R.id.radio_512, 26);
        sparseIntArray.put(R.id.radio_curtain, 27);
        sparseIntArray.put(R.id.radio_curtain_dream, 28);
        sparseIntArray.put(R.id.radio_scene8, 29);
        sparseIntArray.put(R.id.radio_scene, 30);
        sparseIntArray.put(R.id.radio_tb_type1, 31);
        sparseIntArray.put(R.id.radio_tb_type2, 32);
        sparseIntArray.put(R.id.radio_tb_type3, 33);
        sparseIntArray.put(R.id.radio_ac, 34);
        sparseIntArray.put(R.id.radio_air, 35);
        sparseIntArray.put(R.id.radio_ac_and_air, 36);
        sparseIntArray.put(R.id.view_divider, 37);
        sparseIntArray.put(R.id.pick_view_floor, 38);
        sparseIntArray.put(R.id.picker_view_room, 39);
        sparseIntArray.put(R.id.group_output, 40);
        sparseIntArray.put(R.id.dry_contact_type, 41);
        sparseIntArray.put(R.id.tb_type, 42);
        sparseIntArray.put(R.id.group_eur_panel, 43);
        sparseIntArray.put(R.id.group_as_panel_rgb, 44);
        sparseIntArray.put(R.id.group_g4te, 45);
        sparseIntArray.put(R.id.group_tv_type, 46);
    }

    public DialogSetBleTypeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 47, sIncludes, sViewsWithIds));
    }

    private DialogSetBleTypeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (Group) bindings[41], (AppCompatEditText) bindings[5], (Group) bindings[44], (Group) bindings[43], (Group) bindings[45], (Group) bindings[40], (Group) bindings[46], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[0], (RecyclerView) bindings[38], (RecyclerView) bindings[39], (RadioImageTextView) bindings[17], (RadioImageTextView) bindings[26], (RadioImageTextView) bindings[34], (RadioImageTextView) bindings[36], (RadioImageTextView) bindings[35], (RadioImageTextView) bindings[14], (RadioImageTextView) bindings[15], (RadioImageTextView) bindings[16], (RadioImageTextView) bindings[21], (RadioImageTextView) bindings[27], (RadioImageTextView) bindings[28], (RadioImageTextView) bindings[18], (RadioImageTextView) bindings[20], (RadioImageTextView) bindings[19], (RadioImageTextView) bindings[22], (RadioImageTextView) bindings[24], (RadioImageTextView) bindings[25], (RadioImageTextView) bindings[30], (RadioImageTextView) bindings[29], (RadioImageTextView) bindings[31], (RadioImageTextView) bindings[32], (RadioImageTextView) bindings[33], (RadioImageTextView) bindings[12], (RadioImageTextView) bindings[13], (Group) bindings[42], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[8], (View) bindings[37], (View) bindings[23]);
        this.etDeviceNameandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.DialogSetBleTypeBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(DialogSetBleTypeBindingImpl.this.etDeviceName);
                ObservableField<String> observableField = DialogSetBleTypeBindingImpl.this.mContent;
                if (observableField != null) {
                    observableField.set(textString);
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.etDeviceName.setTag(null);
        this.ivClear.setTag(null);
        this.ivLocation.setTag(null);
        this.layoutBg.setTag(null);
        this.tvCancel.setTag(null);
        this.tvControlType.setTag(null);
        this.tvLabel.setTag(null);
        this.tvSave.setTag(null);
        this.tvTitle.setTag(null);
        this.tvTypeOutput.setTag(null);
        this.tvTypeTip.setTag(null);
        this.tvZoneControl.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (13 == variableId) {
            setContent((ObservableField) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogSetBleTypeBinding
    public void setContent(ObservableField<String> Content) {
        updateRegistration(0, Content);
        this.mContent = Content;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(13);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogSetBleTypeBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeContent((ObservableField) object, fieldId);
    }

    private boolean onChangeContent(ObservableField<String> Content, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ObservableField<String> observableField = this.mContent;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        long j2 = 5 & j;
        String str = (j2 == 0 || observableField == null) ? null : observableField.get();
        long j3 = 6 & j;
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.etDeviceName, str);
        }
        if ((j & 4) != 0) {
            TextViewBindingAdapter.setTextWatcher(this.etDeviceName, null, null, null, this.etDeviceNameandroidTextAttrChanged);
            ViewAdapter.setTextBold(this.tvControlType, true);
            ViewAdapter.setTextBold(this.tvLabel, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
            ViewAdapter.setTextBold(this.tvTypeOutput, true);
            ViewAdapter.setTextBold(this.tvTypeTip, true);
            ViewAdapter.setTextBold(this.tvZoneControl, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivClear, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivLocation, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvSave, bindingCommand, false);
        }
    }
}