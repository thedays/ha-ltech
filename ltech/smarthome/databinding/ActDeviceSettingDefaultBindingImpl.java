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

/* loaded from: classes3.dex */
public class ActDeviceSettingDefaultBindingImpl extends ActDeviceSettingDefaultBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView15;
    private final AppCompatTextView mboundView20;
    private final AppCompatTextView mboundView21;
    private final AppCompatTextView mboundView23;
    private final AppCompatTextView mboundView24;
    private final AppCompatTextView mboundView27;
    private final AppCompatImageView mboundView28;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(42);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{31}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 32);
        sparseIntArray.put(R.id.tv_battery_tip, 33);
        sparseIntArray.put(R.id.battery_go, 34);
        sparseIntArray.put(R.id.tv_related_num, 35);
        sparseIntArray.put(R.id.iv_scene_go, 36);
        sparseIntArray.put(R.id.device_replace_go, 37);
        sparseIntArray.put(R.id.device_log_go, 38);
        sparseIntArray.put(R.id.iv_device_id_go, 39);
        sparseIntArray.put(R.id.iv_mac_address, 40);
        sparseIntArray.put(R.id.iv_product_name, 41);
    }

    public ActDeviceSettingDefaultBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 42, sIncludes, sViewsWithIds));
    }

    private ActDeviceSettingDefaultBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (AppCompatImageView) bindings[34], (AppCompatImageView) bindings[38], (AppCompatImageView) bindings[37], (AppCompatImageView) bindings[29], (AppCompatImageView) bindings[39], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[40], (AppCompatImageView) bindings[41], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[36], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[16], (RelativeLayout) bindings[14], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[12], (RelativeLayout) bindings[19], (RelativeLayout) bindings[22], (RelativeLayout) bindings[10], (RelativeLayout) bindings[25], (LayoutTitleDefaultBinding) bindings[31], (AppCompatTextView) bindings[33], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[26]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutBattery.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[15];
        this.mboundView15 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[21];
        this.mboundView21 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[23];
        this.mboundView23 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[27];
        this.mboundView27 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[28];
        this.mboundView28 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
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
        setViewmodel((ActDeviceSettingDefaultVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceSettingDefaultBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceSettingDefaultBinding
    public void setViewmodel(ActDeviceSettingDefaultVM Viewmodel) {
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

    /* JADX WARN: Removed duplicated region for block: B:108:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x03ab  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x03ba  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0149 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x015e  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 970
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActDeviceSettingDefaultBindingImpl.executeBindings():void");
    }
}