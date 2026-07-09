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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.kbs.ActKbsSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActKbsSettingBindingImpl extends ActKbsSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView15;
    private final AppCompatTextView mboundView36;
    private final AppCompatTextView mboundView38;
    private final AppCompatTextView mboundView40;
    private final AppCompatTextView mboundView42;
    private final AppCompatTextView mboundView44;
    private final AppCompatTextView mboundView46;
    private final AppCompatTextView mboundView51;
    private final AppCompatTextView mboundView52;
    private final AppCompatTextView mboundView54;
    private final AppCompatTextView mboundView55;
    private final AppCompatTextView mboundView58;
    private final AppCompatImageView mboundView59;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(76);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{62}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 63);
        sparseIntArray.put(R.id.tv_room_name, 64);
        sparseIntArray.put(R.id.iv_icon, 65);
        sparseIntArray.put(R.id.tv_tip, 66);
        sparseIntArray.put(R.id.rv_light_setting, 67);
        sparseIntArray.put(R.id.tv_state, 68);
        sparseIntArray.put(R.id.tv_line, 69);
        sparseIntArray.put(R.id.iv_line_go, 70);
        sparseIntArray.put(R.id.device_replace_go, 71);
        sparseIntArray.put(R.id.device_log_go, 72);
        sparseIntArray.put(R.id.iv_device_id_go, 73);
        sparseIntArray.put(R.id.iv_mac_address, 74);
        sparseIntArray.put(R.id.iv_product_name, 75);
    }

    public ActKbsSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 76, sIncludes, sViewsWithIds));
    }

    private ActKbsSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 13, (AppCompatImageView) bindings[72], (AppCompatImageView) bindings[71], (AppCompatImageView) bindings[60], (AppCompatImageView) bindings[13], (AppCompatImageView) bindings[73], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[65], (AppCompatImageView) bindings[70], (AppCompatImageView) bindings[74], (AppCompatImageView) bindings[34], (AppCompatImageView) bindings[30], (AppCompatImageView) bindings[26], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[75], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[18], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[10], (RelativeLayout) bindings[14], (ConstraintLayout) bindings[47], (RelativeLayout) bindings[43], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[41], (RelativeLayout) bindings[39], (RelativeLayout) bindings[35], (RelativeLayout) bindings[50], (ConstraintLayout) bindings[27], (ConstraintLayout) bindings[31], (ConstraintLayout) bindings[19], (ConstraintLayout) bindings[23], (RelativeLayout) bindings[53], (RelativeLayout) bindings[45], (ConstraintLayout) bindings[16], (RelativeLayout) bindings[56], (RelativeLayout) bindings[37], (SmartRefreshLayout) bindings[63], (RecyclerView) bindings[67], (LayoutTitleDefaultBinding) bindings[62], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[61], (AppCompatTextView) bindings[49], (AppCompatTextView) bindings[48], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[69], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[33], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[25], (AppCompatTextView) bindings[64], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[68], (AppCompatTextView) bindings[66], (AppCompatTextView) bindings[57]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivControlTypeGo.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivPowerOffSceneDelayGo.setTag(null);
        this.ivPowerOffSceneGo.setTag(null);
        this.ivPowerOnSceneDelayGo.setTag(null);
        this.ivPowerOnSceneGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivStateGo.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutControlType.setTag(null);
        this.layoutCreateGroup.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutDuv.setTag(null);
        this.layoutLineSet.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutPowerOffScene.setTag(null);
        this.layoutPowerOffSceneDelay.setTag(null);
        this.layoutPowerOnScene.setTag(null);
        this.layoutPowerOnSceneDelay.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutRestoreFactory.setTag(null);
        this.layoutSetOnState.setTag(null);
        this.layoutUpgrade.setTag(null);
        this.layoutWhiteBalance.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[15];
        this.mboundView15 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[36];
        this.mboundView36 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[38];
        this.mboundView38 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[40];
        this.mboundView40 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[42];
        this.mboundView42 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[44];
        this.mboundView44 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[46];
        this.mboundView46 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[51];
        this.mboundView51 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[52];
        this.mboundView52 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[54];
        this.mboundView54 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[55];
        this.mboundView55 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[58];
        this.mboundView58 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[59];
        this.mboundView59 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        setContainedBinding(this.title);
        this.tvControlType.setTag(null);
        this.tvControlTypeState.setTag(null);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvLightOnState.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvPowerOffScene.setTag(null);
        this.tvPowerOffSceneDelay.setTag(null);
        this.tvPowerOffSceneName.setTag(null);
        this.tvPowerOffSceneNameDelay.setTag(null);
        this.tvPowerOnScene.setTag(null);
        this.tvPowerOnSceneDelay.setTag(null);
        this.tvPowerOnSceneName.setTag(null);
        this.tvPowerOnSceneNameDelay.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
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
        setViewmodel((ActKbsSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActKbsSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActKbsSettingBinding
    public void setViewmodel(ActKbsSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 16384;
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
                return onChangeViewmodelShowPowerOffSceneDelayStr((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelControlType((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelShowPowerOnOffScene((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelShowPowerOffScene((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelShowPowerOnSceneDelayStr((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelControlGroup((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewmodelShowLog((MutableLiveData) object, fieldId);
            case 12:
                return onChangeViewmodelShowPowerOnScene((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelShowPowerOffSceneDelayStr(MutableLiveData<String> ViewmodelShowPowerOffSceneDelayStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelControlType(MutableLiveData<Integer> ViewmodelControlType, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOnOffScene(MutableLiveData<Boolean> ViewmodelShowPowerOnOffScene, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOffScene(MutableLiveData<String> ViewmodelShowPowerOffScene, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOnSceneDelayStr(MutableLiveData<String> ViewmodelShowPowerOnSceneDelayStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelControlGroup(MutableLiveData<Group> ViewmodelControlGroup, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    private boolean onChangeViewmodelShowLog(MutableLiveData<Boolean> ViewmodelShowLog, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOnScene(MutableLiveData<String> ViewmodelShowPowerOnScene, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x026a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0429  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0470  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x047d  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0505  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0554  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x058e  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x05a3  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0670  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x067b  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x068b  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0696  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x06a1  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x06af  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0727  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0739  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0749  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0759  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x0766  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0773  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0783  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x0790  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x07a0  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x07b0  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0580  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x054b  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0523  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x04fc  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x0476  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01bc  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1984
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActKbsSettingBindingImpl.executeBindings():void");
    }
}