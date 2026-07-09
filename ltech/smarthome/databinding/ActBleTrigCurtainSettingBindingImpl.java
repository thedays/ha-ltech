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
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSettingVM;

/* loaded from: classes3.dex */
public class ActBleTrigCurtainSettingBindingImpl extends ActBleTrigCurtainSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView23;
    private final AppCompatTextView mboundView25;
    private final AppCompatTextView mboundView30;
    private final AppCompatTextView mboundView31;
    private final AppCompatTextView mboundView33;
    private final AppCompatTextView mboundView34;
    private final AppCompatTextView mboundView37;
    private final AppCompatImageView mboundView38;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(52);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{41}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 42);
        sparseIntArray.put(R.id.tv_curtain_open_dir_name, 43);
        sparseIntArray.put(R.id.tv_curtain_control_setting_name, 44);
        sparseIntArray.put(R.id.tv_curtain_channel_setting_name, 45);
        sparseIntArray.put(R.id.tv_control_mode_name, 46);
        sparseIntArray.put(R.id.device_replace_go, 47);
        sparseIntArray.put(R.id.device_log_go, 48);
        sparseIntArray.put(R.id.iv_device_id_go, 49);
        sparseIntArray.put(R.id.iv_mac_address, 50);
        sparseIntArray.put(R.id.iv_product_name, 51);
    }

    public ActBleTrigCurtainSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 52, sIncludes, sViewsWithIds));
    }

    private ActBleTrigCurtainSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (AppCompatImageView) bindings[48], (AppCompatImageView) bindings[47], (AppCompatImageView) bindings[39], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[49], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[50], (AppCompatImageView) bindings[51], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[19], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[16], (ConstraintLayout) bindings[13], (ConstraintLayout) bindings[10], (ConstraintLayout) bindings[26], (RelativeLayout) bindings[24], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[22], (RelativeLayout) bindings[29], (RelativeLayout) bindings[32], (RelativeLayout) bindings[35], (LayoutTitleDefaultBinding) bindings[41], (AppCompatTextView) bindings[46], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[45], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[44], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[43], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[40], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[27], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[42], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[36]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivControlModeGo.setTag(null);
        this.ivCurtainChannelSettingGo.setTag(null);
        this.ivCurtainControlSettingGo.setTag(null);
        this.ivCurtainOpenDirGo.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutControlMode.setTag(null);
        this.layoutCreateGroup.setTag(null);
        this.layoutCurtainChannelSetting.setTag(null);
        this.layoutCurtainControlSetting.setTag(null);
        this.layoutCurtainOpenDir.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[23];
        this.mboundView23 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[25];
        this.mboundView25 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[30];
        this.mboundView30 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[31];
        this.mboundView31 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[33];
        this.mboundView33 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[34];
        this.mboundView34 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[37];
        this.mboundView37 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[38];
        this.mboundView38 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        setContainedBinding(this.title);
        this.tvControlModeTip.setTag(null);
        this.tvCurtainChannelSettingTip.setTag(null);
        this.tvCurtainControlSettingTip.setTag(null);
        this.tvCurtainOpenDirTip.setTag(null);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActBleTrigCurtainSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActBleTrigCurtainSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActBleTrigCurtainSettingBinding
    public void setViewmodel(ActBleTrigCurtainSettingVM Viewmodel) {
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
        if (localFieldId == 4) {
            return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 5) {
            return false;
        }
        return onChangeViewmodelShowLog((MutableLiveData) object, fieldId);
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

    private boolean onChangeViewmodelShowLog(MutableLiveData<Boolean> ViewmodelShowLog, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:182:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x016e  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 997
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActBleTrigCurtainSettingBindingImpl.executeBindings():void");
    }
}