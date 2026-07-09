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
import com.ltech.smarthome.ui.device.ir.ActDiyIrSettingVM;

/* loaded from: classes3.dex */
public class ActDiyIrSettingBindingImpl extends ActDiyIrSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView12;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(17);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{14}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_subordinate_device, 15);
        sparseIntArray.put(R.id.tv_room_name, 16);
    }

    public ActDiyIrSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }

    private ActDiyIrSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[7], (RelativeLayout) bindings[11], (ConstraintLayout) bindings[8], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[5], (LayoutTitleDefaultBinding) bindings[14], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[15]);
        this.mDirtyFlags = -1L;
        this.ivDeviceNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSubDeviceNameGo.setTag(null);
        this.layoutChangeKeyName.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutSubDevice.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView;
        appCompatTextView.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvDeviceTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        setViewmodel((ActDiyIrSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDiyIrSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDiyIrSettingBinding
    public void setViewmodel(ActDiyIrSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
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
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
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

    /* JADX WARN: Removed duplicated region for block: B:34:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b6  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActDiyIrSettingBindingImpl.executeBindings():void");
    }
}