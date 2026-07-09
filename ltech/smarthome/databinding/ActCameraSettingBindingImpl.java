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
import com.ltech.smarthome.ui.camera.config.ActEzCameraSettingVM;

/* loaded from: classes3.dex */
public class ActCameraSettingBindingImpl extends ActCameraSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView17;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView20;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(28);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{22}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_room_name, 23);
        sparseIntArray.put(R.id.flip_image_go, 24);
        sparseIntArray.put(R.id.iv_device_id_go, 25);
        sparseIntArray.put(R.id.iv_product_name, 26);
        sparseIntArray.put(R.id.iv_mac_address, 27);
    }

    public ActCameraSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }

    private ActCameraSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (AppCompatImageView) bindings[24], (AppCompatImageView) bindings[25], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[27], (AppCompatImageView) bindings[26], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[12], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[8], (RelativeLayout) bindings[18], (RelativeLayout) bindings[15], (RelativeLayout) bindings[10], (LayoutTitleDefaultBinding) bindings[22], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutFlipImage.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSetNetwork.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[17];
        this.mboundView17 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
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
        setViewmodel((ActEzCameraSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActCameraSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCameraSettingBinding
    public void setViewmodel(ActEzCameraSettingVM Viewmodel) {
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

    /* JADX WARN: Code restructure failed: missing block: B:77:0x013f, code lost:
    
        if (r9 != false) goto L97;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ae  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 513
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActCameraSettingBindingImpl.executeBindings():void");
    }
}