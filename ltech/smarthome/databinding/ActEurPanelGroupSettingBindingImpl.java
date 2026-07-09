package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActEurPanelGroupSettingBindingImpl extends ActEurPanelGroupSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView24;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(47);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{28}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 29);
        sparseIntArray.put(R.id.tv_group_name, 30);
        sparseIntArray.put(R.id.tv_room_name, 31);
        sparseIntArray.put(R.id.tv_device_count, 32);
        sparseIntArray.put(R.id.iv_device_count_go, 33);
        sparseIntArray.put(R.id.tv_key_save_tip, 34);
        sparseIntArray.put(R.id.tv_state, 35);
        sparseIntArray.put(R.id.rv_light_setting, 36);
        sparseIntArray.put(R.id.tv_dmx_type, 37);
        sparseIntArray.put(R.id.iv_dmx_go, 38);
        sparseIntArray.put(R.id.tv_zone_control_state, 39);
        sparseIntArray.put(R.id.iv_zone_control_state_go, 40);
        sparseIntArray.put(R.id.tv_control_type_state, 41);
        sparseIntArray.put(R.id.iv_control_type_state_go, 42);
        sparseIntArray.put(R.id.layout_buzzer, 43);
        sparseIntArray.put(R.id.sb_buzzer, 44);
        sparseIntArray.put(R.id.tv_related_num, 45);
        sparseIntArray.put(R.id.iv_scene_go, 46);
    }

    public ActEurPanelGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 47, sIncludes, sViewsWithIds));
    }

    private ActEurPanelGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatButton) bindings[14], (AppCompatImageView) bindings[42], (AppCompatImageView) bindings[33], (AppCompatImageView) bindings[38], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[46], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[40], (RelativeLayout) bindings[43], (ConstraintLayout) bindings[4], (ConstraintLayout) bindings[22], (ConstraintLayout) bindings[7], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[9], (RelativeLayout) bindings[25], (RelativeLayout) bindings[18], (RelativeLayout) bindings[12], (ConstraintLayout) bindings[15], (ConstraintLayout) bindings[20], (SmartRefreshLayout) bindings[29], (RecyclerView) bindings[36], (SwitchButton) bindings[44], (SwitchButton) bindings[11], (LayoutTitleDefaultBinding) bindings[28], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[41], (AppCompatTextView) bindings[27], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[34], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[45], (AppCompatTextView) bindings[26], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[39]);
        this.mDirtyFlags = -1L;
        this.btnAdjust.setTag(null);
        this.ivGroupNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivStateGo.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutControlType.setTag(null);
        this.layoutEditGroup.setTag(null);
        this.layoutGroupName.setTag(null);
        this.layoutKeySave.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSetDmxType.setTag(null);
        this.layoutSetKRange.setTag(null);
        this.layoutSetOnState.setTag(null);
        this.layoutZoneControl.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        this.sbKeySave.setTag(null);
        setContainedBinding(this.title);
        this.tvControlType.setTag(null);
        this.tvDelete.setTag(null);
        this.tvGroupTip.setTag(null);
        this.tvLightOnState.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRelatedTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvZoneControl.setTag(null);
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
        setViewmodel((ActEurPanelGroupSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActEurPanelGroupSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEurPanelGroupSettingBinding
    public void setViewmodel(ActEurPanelGroupSettingVM Viewmodel) {
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
            return onChangeViewmodelKeySave((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelIsShowBindKRange((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelKeySave(MutableLiveData<Boolean> ViewmodelKeySave, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowBindKRange(MutableLiveData<Boolean> ViewmodelIsShowBindKRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x018f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActEurPanelGroupSettingBindingImpl.executeBindings():void");
    }
}