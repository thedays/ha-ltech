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
import com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActBleMusicPlayerSettingBindingImpl extends ActBleMusicPlayerSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView22;
    private final AppCompatTextView mboundView23;
    private final AppCompatTextView mboundView25;
    private final AppCompatTextView mboundView26;
    private final AppCompatTextView mboundView29;
    private final AppCompatImageView mboundView30;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(41);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{33}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 34);
        sparseIntArray.put(R.id.tv_room_name, 35);
        sparseIntArray.put(R.id.iv_bluetooth_state_go, 36);
        sparseIntArray.put(R.id.iv_device_status_after_power_on_go, 37);
        sparseIntArray.put(R.id.iv_device_id_go, 38);
        sparseIntArray.put(R.id.iv_mac_address, 39);
        sparseIntArray.put(R.id.iv_product_name, 40);
    }

    public ActBleMusicPlayerSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 41, sIncludes, sViewsWithIds));
    }

    private ActBleMusicPlayerSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7, (AppCompatImageView) bindings[31], (AppCompatImageView) bindings[36], (AppCompatImageView) bindings[38], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[37], (AppCompatImageView) bindings[39], (AppCompatImageView) bindings[40], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[10], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[18], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[15], (ConstraintLayout) bindings[13], (RelativeLayout) bindings[21], (ConstraintLayout) bindings[8], (RelativeLayout) bindings[24], (RelativeLayout) bindings[27], (SmartRefreshLayout) bindings[34], (LayoutTitleDefaultBinding) bindings[33], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[28]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutBluetoothState.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceStatusAfterPowerOn.setTag(null);
        this.layoutIgnoreCurrentConnections.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutManagerPlaylist.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[22];
        this.mboundView22 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[23];
        this.mboundView23 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[25];
        this.mboundView25 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[29];
        this.mboundView29 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[30];
        this.mboundView30 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        setContainedBinding(this.title);
        this.tvBluetoothState.setTag(null);
        this.tvBluetoothStateTip.setTag(null);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvDeviceStatusAfterPowerOn.setTag(null);
        this.tvDeviceStatusAfterPowerOnTip.setTag(null);
        this.tvIgnoreCurrentConnectionsTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 512L;
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
        setViewmodel((ActBleMusicPlayerSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActBleMusicPlayerSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActBleMusicPlayerSettingBinding
    public void setViewmodel(ActBleMusicPlayerSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 256;
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
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelBleState((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelPowerState((MutableLiveData) object, fieldId);
            default:
                return false;
        }
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

    private boolean onChangeViewmodelBleState(MutableLiveData<String> ViewmodelBleState, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelPowerState(MutableLiveData<String> ViewmodelPowerState, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:157:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x013f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 809
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActBleMusicPlayerSettingBindingImpl.executeBindings():void");
    }
}