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
import com.ltech.smarthome.ui.device.setting.ActDmx512SettingVM;

/* loaded from: classes3.dex */
public class ActDmx512SettingBindingImpl extends ActDmx512SettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView18;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView21;
    private final AppCompatTextView mboundView22;
    private final AppCompatTextView mboundView24;
    private final AppCompatTextView mboundView25;
    private final AppCompatTextView mboundView28;
    private final AppCompatImageView mboundView29;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(42);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{32}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 33);
        sparseIntArray.put(R.id.iv_icon, 34);
        sparseIntArray.put(R.id.tv_address, 35);
        sparseIntArray.put(R.id.iv_address_go, 36);
        sparseIntArray.put(R.id.tv_related_num, 37);
        sparseIntArray.put(R.id.iv_scene_go, 38);
        sparseIntArray.put(R.id.iv_device_id_go, 39);
        sparseIntArray.put(R.id.iv_mac_address, 40);
        sparseIntArray.put(R.id.iv_product_name, 41);
    }

    public ActDmx512SettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 42, sIncludes, sViewsWithIds));
    }

    private ActDmx512SettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7, (AppCompatImageView) bindings[30], (AppCompatImageView) bindings[36], (AppCompatImageView) bindings[39], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[34], (AppCompatImageView) bindings[40], (AppCompatImageView) bindings[41], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[38], (RelativeLayout) bindings[10], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[14], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[17], (RelativeLayout) bindings[23], (RelativeLayout) bindings[20], (RelativeLayout) bindings[12], (RelativeLayout) bindings[26], (LayoutTitleDefaultBinding) bindings[32], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[33], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[27]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutAddress.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutOutput.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[18];
        this.mboundView18 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[21];
        this.mboundView21 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[22];
        this.mboundView22 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[25];
        this.mboundView25 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[28];
        this.mboundView28 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[29];
        this.mboundView29 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView9;
        appCompatTextView9.setTag(null);
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
        setViewmodel((ActDmx512SettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDmx512SettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDmx512SettingBinding
    public void setViewmodel(ActDmx512SettingVM Viewmodel) {
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
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelHasOutputType((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelOutputType((MutableLiveData) object, fieldId);
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

    private boolean onChangeViewmodelHasOutputType(MutableLiveData<Boolean> ViewmodelHasOutputType, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelOutputType(MutableLiveData<String> ViewmodelOutputType, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:213:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x017b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x018e  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 956
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActDmx512SettingBindingImpl.executeBindings():void");
    }
}