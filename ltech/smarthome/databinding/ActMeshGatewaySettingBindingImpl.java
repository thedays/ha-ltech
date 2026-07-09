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
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewaySettingVM;

/* loaded from: classes3.dex */
public class ActMeshGatewaySettingBindingImpl extends ActMeshGatewaySettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(23);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{18}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 19);
        sparseIntArray.put(R.id.iv_product_name, 20);
        sparseIntArray.put(R.id.iv_tip_dot, 21);
        sparseIntArray.put(R.id.iv_go_upgrade, 22);
    }

    public ActMeshGatewaySettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }

    private ActMeshGatewaySettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[21], (RelativeLayout) bindings[10], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[12], (RelativeLayout) bindings[8], (RelativeLayout) bindings[15], (LayoutTitleDefaultBinding) bindings[18], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[16]);
        this.mDirtyFlags = -1L;
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutAtmosphereLampSetting.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSetNetwork.setTag(null);
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
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        setContainedBinding(this.title);
        this.tvAtmosphereLampTip.setTag(null);
        this.tvDeleteDevice.setTag(null);
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
            this.mDirtyFlags = 32L;
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
        setViewmodel((ActMeshGatewaySettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActMeshGatewaySettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActMeshGatewaySettingBinding
    public void setViewmodel(ActMeshGatewaySettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 16;
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
            return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
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

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0110, code lost:
    
        if (r6 != false) goto L94;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01bb  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 457
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActMeshGatewaySettingBindingImpl.executeBindings():void");
    }
}