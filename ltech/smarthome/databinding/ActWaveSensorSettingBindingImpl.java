package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActWaveSensorSettingBindingImpl extends ActWaveSensorSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView15;
    private final AppCompatTextView mboundView17;
    private final AppCompatTextView mboundView19;
    private final AppCompatImageView mboundView20;
    private final AppCompatTextView mboundView22;
    private final AppCompatTextView mboundView25;
    private final AppCompatTextView mboundView28;
    private final AppCompatTextView mboundView30;
    private final AppCompatTextView mboundView32;
    private final AppCompatTextView mboundView34;
    private final AppCompatTextView mboundView35;
    private final AppCompatTextView mboundView37;
    private final AppCompatTextView mboundView38;
    private final AppCompatTextView mboundView40;
    private final AppCompatTextView mboundView41;
    private final AppCompatTextView mboundView44;
    private final AppCompatImageView mboundView45;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(64);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{48}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 49);
        sparseIntArray.put(R.id.tv_room_name, 50);
        sparseIntArray.put(R.id.tv_illuminance, 51);
        sparseIntArray.put(R.id.tv_sensitivity, 52);
        sparseIntArray.put(R.id.tv_battery_tip, 53);
        sparseIntArray.put(R.id.battery_go, 54);
        sparseIntArray.put(R.id.tv_lux_value, 55);
        sparseIntArray.put(R.id.tv_ct_value, 56);
        sparseIntArray.put(R.id.tv_related_num, 57);
        sparseIntArray.put(R.id.iv_scene_go, 58);
        sparseIntArray.put(R.id.tv_delay, 59);
        sparseIntArray.put(R.id.iv_delay_go, 60);
        sparseIntArray.put(R.id.iv_device_id, 61);
        sparseIntArray.put(R.id.iv_mac_address, 62);
        sparseIntArray.put(R.id.iv_product_name, 63);
    }

    public ActWaveSensorSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 64, sIncludes, sViewsWithIds));
    }

    private ActWaveSensorSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (AppCompatImageView) bindings[54], (AppCompatImageView) bindings[46], (AppCompatImageView) bindings[26], (AppCompatImageView) bindings[60], (AppCompatImageView) bindings[61], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[62], (AppCompatImageView) bindings[63], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[58], (AppCompatImageView) bindings[13], (RelativeLayout) bindings[31], (RelativeLayout) bindings[16], (ConstraintLayout) bindings[5], (RelativeLayout) bindings[27], (RelativeLayout) bindings[33], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[36], (RelativeLayout) bindings[39], (RelativeLayout) bindings[29], (RelativeLayout) bindings[14], (ConstraintLayout) bindings[24], (ConstraintLayout) bindings[8], (ConstraintLayout) bindings[21], (ConstraintLayout) bindings[11], (ConstraintLayout) bindings[18], (RelativeLayout) bindings[42], (SmartRefreshLayout) bindings[49], (LayoutTitleDefaultBinding) bindings[48], (AppCompatTextView) bindings[53], (AppCompatTextView) bindings[56], (AppCompatTextView) bindings[59], (AppCompatTextView) bindings[47], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[51], (AppCompatTextView) bindings[55], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[57], (AppCompatTextView) bindings[50], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[52], (AppCompatTextView) bindings[43]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivCtGo.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivIlluminanceGo.setTag(null);
        this.ivLuxGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSensitivityGo.setTag(null);
        this.layoutAutomationDelay.setTag(null);
        this.layoutBattery.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutCreateGroup.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSenseSetting.setTag(null);
        this.layoutSetCt.setTag(null);
        this.layoutSetIlluminance.setTag(null);
        this.layoutSetLux.setTag(null);
        this.layoutSetSensitivity.setTag(null);
        this.layoutTestMode.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[15];
        this.mboundView15 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[17];
        this.mboundView17 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[20];
        this.mboundView20 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[22];
        this.mboundView22 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[25];
        this.mboundView25 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[28];
        this.mboundView28 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[30];
        this.mboundView30 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[32];
        this.mboundView32 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[34];
        this.mboundView34 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[35];
        this.mboundView35 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[37];
        this.mboundView37 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[38];
        this.mboundView38 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[40];
        this.mboundView40 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[41];
        this.mboundView41 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        AppCompatTextView appCompatTextView16 = (AppCompatTextView) bindings[44];
        this.mboundView44 = appCompatTextView16;
        appCompatTextView16.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[45];
        this.mboundView45 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        AppCompatTextView appCompatTextView17 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView17;
        appCompatTextView17.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 256L;
        }
        this.title.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActWaveSensorSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorSettingBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorSettingBinding
    public void setViewmodel(ActWaveSensorSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0180  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 847
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActWaveSensorSettingBindingImpl.executeBindings():void");
    }
}