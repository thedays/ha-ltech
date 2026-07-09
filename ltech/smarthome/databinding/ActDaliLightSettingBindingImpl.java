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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ActDaliLightSettingBindingImpl extends ActDaliLightSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView7;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(48);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{30}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 31);
        sparseIntArray.put(R.id.act_add_device_scroll, 32);
        sparseIntArray.put(R.id.iv_scene_name_go, 33);
        sparseIntArray.put(R.id.tv_room_name, 34);
        sparseIntArray.put(R.id.iv_room_name_go, 35);
        sparseIntArray.put(R.id.iv_icon, 36);
        sparseIntArray.put(R.id.tv_gateway_name, 37);
        sparseIntArray.put(R.id.tv_device_number, 38);
        sparseIntArray.put(R.id.tv_tip, 39);
        sparseIntArray.put(R.id.iv_dimming_curve_go, 40);
        sparseIntArray.put(R.id.iv_dimming_range_go, 41);
        sparseIntArray.put(R.id.iv_dimming_fade_time_go, 42);
        sparseIntArray.put(R.id.iv_ct_range_go, 43);
        sparseIntArray.put(R.id.civ_color_light_on, 44);
        sparseIntArray.put(R.id.iv_light_on_state_go, 45);
        sparseIntArray.put(R.id.civ_color_fail, 46);
        sparseIntArray.put(R.id.iv_failure_state_go, 47);
    }

    public ActDaliLightSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 48, sIncludes, sViewsWithIds));
    }

    private ActDaliLightSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 8, (NestedScrollView) bindings[32], (CircleImageView) bindings[46], (CircleImageView) bindings[44], (AppCompatImageView) bindings[43], (AppCompatImageView) bindings[40], (AppCompatImageView) bindings[42], (AppCompatImageView) bindings[41], (AppCompatImageView) bindings[47], (AppCompatImageView) bindings[36], (AppCompatImageView) bindings[45], (AppCompatImageView) bindings[35], (AppCompatImageView) bindings[33], (RelativeLayout) bindings[6], (ConstraintLayout) bindings[4], (ConstraintLayout) bindings[21], (ConstraintLayout) bindings[12], (ConstraintLayout) bindings[18], (ConstraintLayout) bindings[15], (ConstraintLayout) bindings[27], (ConstraintLayout) bindings[24], (ConstraintLayout) bindings[1], (SmartRefreshLayout) bindings[31], (SwitchButton) bindings[11], (LayoutTitleDefaultBinding) bindings[30], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[38], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[26], (AppCompatTextView) bindings[25], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[34], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[39]);
        this.mDirtyFlags = -1L;
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutCtRange.setTag(null);
        this.layoutDimmingCurve.setTag(null);
        this.layoutDimmingFadeTime.setTag(null);
        this.layoutDimmingRange.setTag(null);
        this.layoutFailureState.setTag(null);
        this.layoutLightOnState.setTag(null);
        this.layoutSceneName.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[7];
        this.mboundView7 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        this.sbAddToSmart.setTag(null);
        setContainedBinding(this.title);
        this.tvCtRange.setTag(null);
        this.tvCtRangeTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvDimmingCurve.setTag(null);
        this.tvDimmingCurveTip.setTag(null);
        this.tvDimmingFadeTime.setTag(null);
        this.tvDimmingFadeTimeTip.setTag(null);
        this.tvDimmingRange.setTag(null);
        this.tvDimmingRangeTip.setTag(null);
        this.tvFailureState.setTag(null);
        this.tvFailureStateTip.setTag(null);
        this.tvGatewayTip.setTag(null);
        this.tvLightOnState.setTag(null);
        this.tvLightOnStateTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvSceneNumberTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1024L;
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
        setViewmodel((ActDaliLightSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDaliLightSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDaliLightSettingBinding
    public void setViewmodel(ActDaliLightSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 512;
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
                return onChangeViewmodelDimmingRange((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelLightOnState((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelDimmingCurve((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelIsAddToRoom((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelCtRange((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelDeviceName((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelFailureState((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelDimmingFadeTime((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelDimmingRange(MutableLiveData<String> ViewmodelDimmingRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelLightOnState(MutableLiveData<String> ViewmodelLightOnState, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelDimmingCurve(MutableLiveData<String> ViewmodelDimmingCurve, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelIsAddToRoom(MutableLiveData<Boolean> ViewmodelIsAddToRoom, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelCtRange(MutableLiveData<String> ViewmodelCtRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelDeviceName(MutableLiveData<String> ViewmodelDeviceName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelFailureState(MutableLiveData<String> ViewmodelFailureState, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelDimmingFadeTime(MutableLiveData<String> ViewmodelDimmingFadeTime, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0115  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 541
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActDaliLightSettingBindingImpl.executeBindings():void");
    }
}