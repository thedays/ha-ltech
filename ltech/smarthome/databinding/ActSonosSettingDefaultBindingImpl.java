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
import com.ltech.smarthome.ui.device.setting.ActSonosSettingDefaultVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActSonosSettingDefaultBindingImpl extends ActSonosSettingDefaultBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView17;
    private final AppCompatTextView mboundView18;
    private final AppCompatTextView mboundView20;
    private final AppCompatTextView mboundView21;
    private final AppCompatTextView mboundView24;
    private final AppCompatImageView mboundView25;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(36);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{28}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 29);
        sparseIntArray.put(R.id.iv_distance_go, 30);
        sparseIntArray.put(R.id.tv_related_num, 31);
        sparseIntArray.put(R.id.iv_scene_go, 32);
        sparseIntArray.put(R.id.iv_device_id_go, 33);
        sparseIntArray.put(R.id.iv_mac_address, 34);
        sparseIntArray.put(R.id.iv_product_name, 35);
    }

    public ActSonosSettingDefaultBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 36, sIncludes, sViewsWithIds));
    }

    private ActSonosSettingDefaultBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (AppCompatImageView) bindings[26], (AppCompatImageView) bindings[33], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[30], (AppCompatImageView) bindings[34], (AppCompatImageView) bindings[35], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[32], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[13], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[16], (RelativeLayout) bindings[19], (RelativeLayout) bindings[11], (RelativeLayout) bindings[8], (RelativeLayout) bindings[22], (SwitchButton) bindings[10], (LayoutTitleDefaultBinding) bindings[28], (AppCompatTextView) bindings[27], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[23]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSensitivity.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[17];
        this.mboundView17 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[18];
        this.mboundView18 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[21];
        this.mboundView21 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[25];
        this.mboundView25 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        this.sbCrossFade.setTag(null);
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
        setViewmodel((ActSonosSettingDefaultVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSonosSettingDefaultBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSonosSettingDefaultBinding
    public void setViewmodel(ActSonosSettingDefaultVM Viewmodel) {
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
            return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelIsCrossFade((MutableLiveData) object, fieldId);
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

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelIsCrossFade(MutableLiveData<Boolean> ViewmodelIsCrossFade, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f3  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 639
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSonosSettingDefaultBindingImpl.executeBindings():void");
    }
}