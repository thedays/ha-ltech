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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.r8.ActR8SettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActR8SettingBindingImpl extends ActR8SettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView15;
    private final AppCompatTextView mboundView18;
    private final AppCompatTextView mboundView21;
    private final AppCompatTextView mboundView23;
    private final AppCompatTextView mboundView26;
    private final AppCompatTextView mboundView28;
    private final AppCompatTextView mboundView31;
    private final AppCompatTextView mboundView33;
    private final AppCompatTextView mboundView40;
    private final AppCompatTextView mboundView41;
    private final AppCompatTextView mboundView43;
    private final AppCompatTextView mboundView44;
    private final AppCompatTextView mboundView47;
    private final AppCompatImageView mboundView48;
    private final AppCompatTextView mboundView52;
    private final AppCompatImageView mboundView53;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(74);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{56}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 57);
        sparseIntArray.put(R.id.tv_room_name, 58);
        sparseIntArray.put(R.id.sensitivity_go, 59);
        sparseIntArray.put(R.id.key1_go, 60);
        sparseIntArray.put(R.id.key2_go, 61);
        sparseIntArray.put(R.id.key3_go, 62);
        sparseIntArray.put(R.id.key4_go, 63);
        sparseIntArray.put(R.id.layout_volume, 64);
        sparseIntArray.put(R.id.tv_battery_tip, 65);
        sparseIntArray.put(R.id.battery_go, 66);
        sparseIntArray.put(R.id.device_replace_go, 67);
        sparseIntArray.put(R.id.device_log_go, 68);
        sparseIntArray.put(R.id.tv_related_num, 69);
        sparseIntArray.put(R.id.iv_scene_go, 70);
        sparseIntArray.put(R.id.iv_device_id_go, 71);
        sparseIntArray.put(R.id.iv_mac_address, 72);
        sparseIntArray.put(R.id.iv_product_name, 73);
    }

    public ActR8SettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 74, sIncludes, sViewsWithIds));
    }

    private ActR8SettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 15, (AppCompatImageView) bindings[66], (AppCompatButton) bindings[29], (AppCompatImageView) bindings[68], (AppCompatImageView) bindings[67], (AppCompatImageView) bindings[49], (AppCompatImageView) bindings[71], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[54], (AppCompatImageView) bindings[72], (AppCompatImageView) bindings[73], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[70], (AppCompatImageView) bindings[60], (AppCompatImageView) bindings[61], (AppCompatImageView) bindings[62], (AppCompatImageView) bindings[63], (RelativeLayout) bindings[25], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[36], (RelativeLayout) bindings[32], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[30], (RelativeLayout) bindings[50], (RelativeLayout) bindings[11], (RelativeLayout) bindings[14], (RelativeLayout) bindings[17], (RelativeLayout) bindings[20], (RelativeLayout) bindings[39], (LinearLayout) bindings[8], (RelativeLayout) bindings[42], (RelativeLayout) bindings[34], (RelativeLayout) bindings[9], (RelativeLayout) bindings[27], (RelativeLayout) bindings[45], (RelativeLayout) bindings[64], (SmartRefreshLayout) bindings[57], (SwitchButton) bindings[24], (AppCompatImageView) bindings[59], (LayoutTitleDefaultBinding) bindings[56], (AppCompatTextView) bindings[65], (AppCompatTextView) bindings[55], (AppCompatTextView) bindings[38], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[51], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[69], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[58], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[46]);
        this.mDirtyFlags = -1L;
        this.btnAdjust.setTag(null);
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivIconGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutBattery.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutIconUpgrade.setTag(null);
        this.layoutKey1.setTag(null);
        this.layoutKey2.setTag(null);
        this.layoutKey3.setTag(null);
        this.layoutKey4.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutMore.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSensitivity.setTag(null);
        this.layoutSetKRange.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[15];
        this.mboundView15 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[18];
        this.mboundView18 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[21];
        this.mboundView21 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[23];
        this.mboundView23 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[28];
        this.mboundView28 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[31];
        this.mboundView31 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[33];
        this.mboundView33 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[40];
        this.mboundView40 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[41];
        this.mboundView41 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[43];
        this.mboundView43 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[44];
        this.mboundView44 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[47];
        this.mboundView47 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[48];
        this.mboundView48 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView16 = (AppCompatTextView) bindings[52];
        this.mboundView52 = appCompatTextView16;
        appCompatTextView16.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[53];
        this.mboundView53 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        this.sbVolumePowerOnTip.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvIconTip.setTag(null);
        this.tvKey1Tip.setTag(null);
        this.tvKey2Tip.setTag(null);
        this.tvKey3Tip.setTag(null);
        this.tvKey4Tip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRelatedTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
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
        setViewmodel((ActR8SettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActR8SettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActR8SettingBinding
    public void setViewmodel(ActR8SettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 65536;
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
                return onChangeViewmodelKey2Sensitivity((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelIsShowBindKRange((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelKey3Sensitivity((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelKey1Sensitivity((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelIsShowIconVersion((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelNewIconVersion((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelShowVoiceSetting((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelIconVersion((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 12:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 13:
                return onChangeViewmodelKey4Sensitivity((MutableLiveData) object, fieldId);
            case 14:
                return onChangeViewmodelShowLog((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelKey2Sensitivity(MutableLiveData<String> ViewmodelKey2Sensitivity, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowBindKRange(MutableLiveData<Boolean> ViewmodelIsShowBindKRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelKey3Sensitivity(MutableLiveData<String> ViewmodelKey3Sensitivity, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelKey1Sensitivity(MutableLiveData<String> ViewmodelKey1Sensitivity, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowIconVersion(MutableLiveData<Boolean> ViewmodelIsShowIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelNewIconVersion(MutableLiveData<Boolean> ViewmodelNewIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeViewmodelShowVoiceSetting(MutableLiveData<Boolean> ViewmodelShowVoiceSetting, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelIconVersion(MutableLiveData<String> ViewmodelIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    private boolean onChangeViewmodelKey4Sensitivity(MutableLiveData<String> ViewmodelKey4Sensitivity, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        return true;
    }

    private boolean onChangeViewmodelShowLog(MutableLiveData<Boolean> ViewmodelShowLog, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16384;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0192  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1603
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActR8SettingBindingImpl.executeBindings():void");
    }
}