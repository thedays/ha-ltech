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
import com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefaultVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActHomeKitSettingBindingImpl extends ActHomeKitSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView17;
    private final AppCompatTextView mboundView20;
    private final AppCompatImageView mboundView21;
    private final AppCompatTextView mboundView8;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(32);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{24}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 25);
        sparseIntArray.put(R.id.tv_room_name, 26);
        sparseIntArray.put(R.id.layout_light, 27);
        sparseIntArray.put(R.id.sb_light, 28);
        sparseIntArray.put(R.id.iv_device_id_go, 29);
        sparseIntArray.put(R.id.iv_mac_address, 30);
        sparseIntArray.put(R.id.iv_product_name, 31);
    }

    public ActHomeKitSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 32, sIncludes, sViewsWithIds));
    }

    private ActHomeKitSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[29], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[30], (AppCompatImageView) bindings[31], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[9], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[27], (RelativeLayout) bindings[12], (RelativeLayout) bindings[15], (RelativeLayout) bindings[18], (SmartRefreshLayout) bindings[25], (SwitchButton) bindings[28], (LayoutTitleDefaultBinding) bindings[24], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[26], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[19]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[14];
        this.mboundView14 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[17];
        this.mboundView17 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[21];
        this.mboundView21 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[8];
        this.mboundView8 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        setContainedBinding(this.title);
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
            this.mDirtyFlags = 128L;
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
        setViewmodel((ActDeviceSettingDefaultVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActHomeKitSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActHomeKitSettingBinding
    public void setViewmodel(ActDeviceSettingDefaultVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 64;
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

    /* JADX WARN: Removed duplicated region for block: B:112:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0143 A[ADDED_TO_REGION] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 789
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActHomeKitSettingBindingImpl.executeBindings():void");
    }
}