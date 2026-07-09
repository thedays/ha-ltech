package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActEurPanelSettingBindingImpl extends ActEurPanelSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView17;
    private final AppCompatImageView mboundView18;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView20;
    private final AppCompatTextView mboundView23;
    private final AppCompatTextView mboundView26;
    private final AppCompatTextView mboundView31;
    private final AppCompatTextView mboundView39;
    private final AppCompatImageView mboundView40;
    private final AppCompatTextView mboundView41;
    private final AppCompatTextView mboundView43;
    private final AppCompatTextView mboundView45;
    private final AppCompatTextView mboundView52;
    private final AppCompatTextView mboundView53;
    private final AppCompatTextView mboundView55;
    private final AppCompatTextView mboundView56;
    private final AppCompatTextView mboundView59;
    private final AppCompatImageView mboundView60;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(92);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{63}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 64);
        sparseIntArray.put(R.id.tv_room_name, 65);
        sparseIntArray.put(R.id.tv_key_save_tip, 66);
        sparseIntArray.put(R.id.layout_knob_setting, 67);
        sparseIntArray.put(R.id.tv_sensitivity, 68);
        sparseIntArray.put(R.id.layout_mode_memorize, 69);
        sparseIntArray.put(R.id.tv_battery_tip, 70);
        sparseIntArray.put(R.id.battery_go, 71);
        sparseIntArray.put(R.id.tv_state, 72);
        sparseIntArray.put(R.id.rv_light_setting, 73);
        sparseIntArray.put(R.id.tv_dmx_type, 74);
        sparseIntArray.put(R.id.iv_dmx_go, 75);
        sparseIntArray.put(R.id.tv_zone_control_state, 76);
        sparseIntArray.put(R.id.iv_zone_control_state_go, 77);
        sparseIntArray.put(R.id.tv_control_type_state, 78);
        sparseIntArray.put(R.id.iv_control_type_state_go, 79);
        sparseIntArray.put(R.id.tv_function_state, 80);
        sparseIntArray.put(R.id.iv_function_state_go, 81);
        sparseIntArray.put(R.id.tv_eur_function_tips, 82);
        sparseIntArray.put(R.id.layout_buzzer, 83);
        sparseIntArray.put(R.id.sb_buzzer, 84);
        sparseIntArray.put(R.id.device_replace_go, 85);
        sparseIntArray.put(R.id.device_log_go, 86);
        sparseIntArray.put(R.id.tv_related_num, 87);
        sparseIntArray.put(R.id.iv_scene_go, 88);
        sparseIntArray.put(R.id.iv_device_id_go, 89);
        sparseIntArray.put(R.id.iv_mac_address, 90);
        sparseIntArray.put(R.id.iv_product_name, 91);
    }

    public ActEurPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 92, sIncludes, sViewsWithIds));
    }

    private ActEurPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 9, (AppCompatImageView) bindings[71], (AppCompatButton) bindings[24], (AppCompatImageView) bindings[86], (AppCompatImageView) bindings[85], (AppCompatImageView) bindings[61], (AppCompatImageView) bindings[79], (AppCompatImageView) bindings[89], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[75], (AppCompatImageView) bindings[81], (AppCompatImageView) bindings[90], (AppCompatImageView) bindings[91], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[88], (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[29], (AppCompatImageView) bindings[77], (RelativeLayout) bindings[25], (RelativeLayout) bindings[38], (RelativeLayout) bindings[83], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[34], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[48], (RelativeLayout) bindings[44], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[42], (ConstraintLayout) bindings[36], (RelativeLayout) bindings[10], (LinearLayout) bindings[67], (RelativeLayout) bindings[51], (RelativeLayout) bindings[69], (RelativeLayout) bindings[16], (RelativeLayout) bindings[54], (RelativeLayout) bindings[46], (ConstraintLayout) bindings[13], (RelativeLayout) bindings[30], (RelativeLayout) bindings[22], (ConstraintLayout) bindings[27], (RelativeLayout) bindings[57], (ConstraintLayout) bindings[32], (SmartRefreshLayout) bindings[64], (RecyclerView) bindings[73], (SwitchButton) bindings[84], (SwitchButton) bindings[12], (SwitchButton) bindings[21], (LayoutTitleDefaultBinding) bindings[63], (AppCompatTextView) bindings[70], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[78], (AppCompatTextView) bindings[62], (AppCompatTextView) bindings[50], (AppCompatTextView) bindings[49], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[74], (AppCompatTextView) bindings[82], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[80], (AppCompatTextView) bindings[66], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[87], (AppCompatTextView) bindings[47], (AppCompatTextView) bindings[65], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[68], (AppCompatTextView) bindings[72], (AppCompatTextView) bindings[58], (AppCompatTextView) bindings[33], (AppCompatTextView) bindings[76]);
        this.mDirtyFlags = -1L;
        this.btnAdjust.setTag(null);
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSensitivityGo.setTag(null);
        this.ivStateGo.setTag(null);
        this.layoutBattery.setTag(null);
        this.layoutBrtButton.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutControlType.setTag(null);
        this.layoutCreateGroup.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutFunction.setTag(null);
        this.layoutKeySave.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutModeOrder.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSensitivity.setTag(null);
        this.layoutSetDmxType.setTag(null);
        this.layoutSetKRange.setTag(null);
        this.layoutSetOnState.setTag(null);
        this.layoutUpgrade.setTag(null);
        this.layoutZoneControl.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[14];
        this.mboundView14 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[17];
        this.mboundView17 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[18];
        this.mboundView18 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[23];
        this.mboundView23 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[31];
        this.mboundView31 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[39];
        this.mboundView39 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[40];
        this.mboundView40 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[41];
        this.mboundView41 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[43];
        this.mboundView43 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[45];
        this.mboundView45 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[52];
        this.mboundView52 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[53];
        this.mboundView53 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[55];
        this.mboundView55 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        AppCompatTextView appCompatTextView16 = (AppCompatTextView) bindings[56];
        this.mboundView56 = appCompatTextView16;
        appCompatTextView16.setTag(null);
        AppCompatTextView appCompatTextView17 = (AppCompatTextView) bindings[59];
        this.mboundView59 = appCompatTextView17;
        appCompatTextView17.setTag(null);
        AppCompatImageView appCompatImageView3 = (AppCompatImageView) bindings[60];
        this.mboundView60 = appCompatImageView3;
        appCompatImageView3.setTag(null);
        AppCompatTextView appCompatTextView18 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView18;
        appCompatTextView18.setTag(null);
        this.sbKeySave.setTag(null);
        this.sbModeMemorize.setTag(null);
        setContainedBinding(this.title);
        this.tvControlType.setTag(null);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvFunction.setTag(null);
        this.tvLightOnState.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRelatedTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        this.tvZoneControl.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActEurPanelSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActEurPanelSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEurPanelSettingBinding
    public void setViewmodel(ActEurPanelSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 1024;
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
        switch (localFieldId) {
            case 0:
                return onChangeViewmodelKeySave((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelMemorizeMode((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelIsShowBindKRange((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelShowLog((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelKeySave(MutableLiveData<Boolean> ViewmodelKeySave, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelMemorizeMode(MutableLiveData<Boolean> ViewmodelMemorizeMode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowBindKRange(MutableLiveData<Boolean> ViewmodelIsShowBindKRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeViewmodelShowLog(MutableLiveData<Boolean> ViewmodelShowLog, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0180  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActEurPanelSettingBindingImpl.executeBindings():void");
    }
}