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
import com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSettingVM;

/* loaded from: classes3.dex */
public class ActBleTrigSceneSettingBindingImpl extends ActBleTrigSceneSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView20;
    private final AppCompatTextView mboundView22;
    private final AppCompatTextView mboundView23;
    private final AppCompatTextView mboundView26;
    private final AppCompatImageView mboundView27;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(38);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{30}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 31);
        sparseIntArray.put(R.id.tv_control_mode_name, 32);
        sparseIntArray.put(R.id.device_replace_go, 33);
        sparseIntArray.put(R.id.device_log_go, 34);
        sparseIntArray.put(R.id.iv_device_id_go, 35);
        sparseIntArray.put(R.id.iv_mac_address, 36);
        sparseIntArray.put(R.id.iv_product_name, 37);
    }

    public ActBleTrigSceneSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 38, sIncludes, sViewsWithIds));
    }

    private ActBleTrigSceneSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (AppCompatImageView) bindings[34], (AppCompatImageView) bindings[33], (AppCompatImageView) bindings[28], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[35], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[36], (AppCompatImageView) bindings[37], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[8], (ConstraintLayout) bindings[15], (RelativeLayout) bindings[13], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[11], (RelativeLayout) bindings[18], (RelativeLayout) bindings[21], (RelativeLayout) bindings[24], (LayoutTitleDefaultBinding) bindings[30], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[25]);
        this.mDirtyFlags = -1L;
        this.iv.setTag(null);
        this.ivControlModeGo.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutControlMode.setTag(null);
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
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[14];
        this.mboundView14 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[22];
        this.mboundView22 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[23];
        this.mboundView23 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[27];
        this.mboundView27 = appCompatImageView;
        appCompatImageView.setTag(null);
        setContainedBinding(this.title);
        this.tvControlModeTip.setTag(null);
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
        setViewmodel((ActBleTrigSceneSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActBleTrigSceneSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActBleTrigSceneSettingBinding
    public void setViewmodel(ActBleTrigSceneSettingVM Viewmodel) {
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

    /* JADX WARN: Removed duplicated region for block: B:116:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x038c  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0193  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1044
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActBleTrigSceneSettingBindingImpl.executeBindings():void");
    }
}