package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelChildSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActSmartPanelChildSettingBindingImpl extends ActSmartPanelChildSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(25);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{14}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 15);
        sparseIntArray.put(R.id.act_add_device_scroll, 16);
        sparseIntArray.put(R.id.iv_scene_name_go, 17);
        sparseIntArray.put(R.id.tv_subordinate_device, 18);
        sparseIntArray.put(R.id.iv_sub_device_name_go, 19);
        sparseIntArray.put(R.id.tv_room_name, 20);
        sparseIntArray.put(R.id.iv_room_name_go, 21);
        sparseIntArray.put(R.id.iv_icon, 22);
        sparseIntArray.put(R.id.tv_related_num, 23);
        sparseIntArray.put(R.id.iv_scene_go, 24);
    }

    public ActSmartPanelChildSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 25, sIncludes, sViewsWithIds));
    }

    private ActSmartPanelChildSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (NestedScrollView) bindings[16], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[24], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[19], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[6], (RelativeLayout) bindings[12], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[4], (SmartRefreshLayout) bindings[15], (SwitchButton) bindings[11], (LayoutTitleDefaultBinding) bindings[14], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[18]);
        this.mDirtyFlags = -1L;
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSceneName.setTag(null);
        this.layoutSubDevice.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        this.sbAddToSmart.setTag(null);
        setContainedBinding(this.title);
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
            this.mDirtyFlags = 16L;
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
        setViewmodel((ActSmartPanelChildSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelChildSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelChildSettingBinding
    public void setViewmodel(ActSmartPanelChildSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
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
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelIsAddToRoom((MutableLiveData) object, fieldId);
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

    private boolean onChangeViewmodelIsAddToRoom(MutableLiveData<Boolean> ViewmodelIsAddToRoom, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x006b  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSmartPanelChildSettingBindingImpl.executeBindings():void");
    }
}