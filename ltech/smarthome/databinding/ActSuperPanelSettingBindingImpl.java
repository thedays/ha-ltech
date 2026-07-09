package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
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
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActSuperPanelSettingBindingImpl extends ActSuperPanelSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView24;
    private final AppCompatTextView mboundView33;
    private final AppCompatTextView mboundView35;
    private final AppCompatTextView mboundView38;
    private final AppCompatTextView mboundView40;
    private final AppCompatTextView mboundView41;
    private final AppCompatTextView mboundView44;
    private final AppCompatTextView mboundView46;
    private final AppCompatTextView mboundView51;
    private final AppCompatTextView mboundView52;
    private final AppCompatTextView mboundView57;
    private final AppCompatImageView mboundView58;
    private final AppCompatTextView mboundView62;
    private final AppCompatImageView mboundView63;
    private final RelativeLayout mboundView65;
    private final AppCompatTextView mboundView66;
    private final AppCompatTextView mboundView69;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(90);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{71}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 72);
        sparseIntArray.put(R.id.tv_talk_voice_control_state, 73);
        sparseIntArray.put(R.id.tv_talk_state, 74);
        sparseIntArray.put(R.id.tv_direct_voice_state, 75);
        sparseIntArray.put(R.id.tv_nearby_wakeup_state, 76);
        sparseIntArray.put(R.id.layout_switch, 77);
        sparseIntArray.put(R.id.layout_more, 78);
        sparseIntArray.put(R.id.tv_start_time, 79);
        sparseIntArray.put(R.id.iv_start_time_go, 80);
        sparseIntArray.put(R.id.tv_end_time, 81);
        sparseIntArray.put(R.id.iv_end_time_go, 82);
        sparseIntArray.put(R.id.layout_memorize_power, 83);
        sparseIntArray.put(R.id.device_replace_go, 84);
        sparseIntArray.put(R.id.device_mcu_go, 85);
        sparseIntArray.put(R.id.iv_device_id_go, 86);
        sparseIntArray.put(R.id.iv_mac_address, 87);
        sparseIntArray.put(R.id.tv_serial_number, 88);
        sparseIntArray.put(R.id.iv_serial_number, 89);
    }

    public ActSuperPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 90, sIncludes, sViewsWithIds));
    }

    private ActSuperPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 11, (AppCompatImageView) bindings[85], (AppCompatImageView) bindings[84], (AppCompatImageView) bindings[59], (AppCompatImageView) bindings[64], (AppCompatImageView) bindings[86], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[82], (AppCompatImageView) bindings[87], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[89], (AppCompatImageView) bindings[80], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[10], (RelativeLayout) bindings[60], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[47], (RelativeLayout) bindings[23], (RelativeLayout) bindings[45], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[43], (ConstraintLayout) bindings[17], (ConstraintLayout) bindings[39], (RelativeLayout) bindings[55], (RelativeLayout) bindings[68], (RelativeLayout) bindings[50], (RelativeLayout) bindings[83], (LinearLayout) bindings[78], (ConstraintLayout) bindings[20], (ConstraintLayout) bindings[37], (LinearLayout) bindings[77], (RelativeLayout) bindings[25], (RelativeLayout) bindings[27], (RelativeLayout) bindings[29], (RelativeLayout) bindings[31], (ConstraintLayout) bindings[14], (LinearLayout) bindings[11], (ConstraintLayout) bindings[8], (RelativeLayout) bindings[12], (RelativeLayout) bindings[53], (SwitchButton) bindings[67], (SwitchButton) bindings[34], (SwitchButton) bindings[42], (SwitchButton) bindings[36], (LayoutTitleDefaultBinding) bindings[71], (AppCompatTextView) bindings[70], (AppCompatTextView) bindings[49], (AppCompatTextView) bindings[48], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[75], (AppCompatTextView) bindings[81], (AppCompatTextView) bindings[56], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[76], (AppCompatTextView) bindings[72], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[88], (AppCompatTextView) bindings[54], (AppCompatTextView) bindings[79], (AppCompatTextView) bindings[26], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[74], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[73], (AppCompatTextView) bindings[61], (AppCompatTextView) bindings[9]);
        this.mDirtyFlags = -1L;
        this.firmwareIv.setTag(null);
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivTalkStateGo.setTag(null);
        this.ivTvDirectVoiceGo.setTag(null);
        this.ivTvNearbyWakeupGo.setTag(null);
        this.ivVoiceControlGo.setTag(null);
        this.layoutApplicationUpgrade.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceInfo.setTag(null);
        this.layoutDeviceMcu.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutDirectVoice.setTag(null);
        this.layoutEndTime.setTag(null);
        this.layoutFirmwareUpgrade.setTag(null);
        this.layoutLogUpload.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutNearbyWakeup.setTag(null);
        this.layoutStartTime.setTag(null);
        this.layoutSwitch1.setTag(null);
        this.layoutSwitch2.setTag(null);
        this.layoutSwitch3.setTag(null);
        this.layoutSwitch4.setTag(null);
        this.layoutTalk.setTag(null);
        this.layoutVoice.setTag(null);
        this.layoutVoiceControlRange.setTag(null);
        this.layoutWakeUp.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[33];
        this.mboundView33 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[35];
        this.mboundView35 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[38];
        this.mboundView38 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[40];
        this.mboundView40 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[41];
        this.mboundView41 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[44];
        this.mboundView44 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[46];
        this.mboundView46 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[51];
        this.mboundView51 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[52];
        this.mboundView52 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[57];
        this.mboundView57 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[58];
        this.mboundView58 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[62];
        this.mboundView62 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[63];
        this.mboundView63 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[65];
        this.mboundView65 = relativeLayout;
        relativeLayout.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[66];
        this.mboundView66 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[69];
        this.mboundView69 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        this.rlSerialNumber.setTag(null);
        this.sbAutoUpgrade.setTag(null);
        this.sbEngravedText.setTag(null);
        this.sbMemorizePowerOnTip.setTag(null);
        this.sbNightMode.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvDirectVoice.setTag(null);
        this.tvFirmwareTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvNearbyWakeup.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvSerialNumberTip.setTag(null);
        this.tvSwitchName1.setTag(null);
        this.tvSwitchName2.setTag(null);
        this.tvSwitchName3.setTag(null);
        this.tvSwitchName4.setTag(null);
        this.tvTalkTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        this.tvVoiceControlRange.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16384L;
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
        setViewmodel((ActSuperPanelSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelSettingBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelSettingBinding
    public void setViewmodel(ActSuperPanelSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 8192;
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
                return onChangeViewmodelNewMucVersion((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelMcuversion((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelFirmwareversion((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelIsChinaNode((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelNightMode((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelMemorizePowerOff((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelNextIcon((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelEngravedText((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelAutoUpgrade((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelNewMucVersion(MutableLiveData<Boolean> ViewmodelNewMucVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelMcuversion(MutableLiveData<String> ViewmodelMcuversion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelFirmwareversion(MutableLiveData<String> ViewmodelFirmwareversion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelIsChinaNode(MutableLiveData<Boolean> ViewmodelIsChinaNode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelNightMode(MutableLiveData<Boolean> ViewmodelNightMode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelMemorizePowerOff(MutableLiveData<Boolean> ViewmodelMemorizePowerOff, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelNextIcon(MutableLiveData<Boolean> ViewmodelNextIcon, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelEngravedText(MutableLiveData<Boolean> ViewmodelEngravedText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelAutoUpgrade(MutableLiveData<Boolean> ViewmodelAutoUpgrade, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x015d  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSuperPanelSettingBindingImpl.executeBindings():void");
    }
}