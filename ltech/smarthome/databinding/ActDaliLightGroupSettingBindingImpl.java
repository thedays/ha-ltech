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
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActDaliLightGroupSettingBindingImpl extends ActDaliLightGroupSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView7;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(24);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{14}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.act_add_device_scroll, 15);
        sparseIntArray.put(R.id.iv_scene_name_go, 16);
        sparseIntArray.put(R.id.tv_room_name, 17);
        sparseIntArray.put(R.id.iv_room_name_go, 18);
        sparseIntArray.put(R.id.iv_icon, 19);
        sparseIntArray.put(R.id.tv_gateway_name, 20);
        sparseIntArray.put(R.id.tv_group_number, 21);
        sparseIntArray.put(R.id.tv_manager_group, 22);
        sparseIntArray.put(R.id.iv_manager_go, 23);
    }

    public ActDaliLightGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }

    private ActDaliLightGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (NestedScrollView) bindings[15], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[16], (RelativeLayout) bindings[6], (ConstraintLayout) bindings[4], (ConstraintLayout) bindings[10], (ConstraintLayout) bindings[1], (SwitchButton) bindings[13], (LayoutTitleDefaultBinding) bindings[14], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[9]);
        this.mDirtyFlags = -1L;
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutManagerGroup.setTag(null);
        this.layoutSceneName.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[7];
        this.mboundView7 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        this.sbAddToSmart.setTag(null);
        setContainedBinding(this.title);
        this.tvGatewayTip.setTag(null);
        this.tvGroupName.setTag(null);
        this.tvManagerTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvSceneNumberTip.setTag(null);
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
        setViewmodel((ActDaliLightGroupSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDaliLightGroupSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDaliLightGroupSettingBinding
    public void setViewmodel(ActDaliLightGroupSettingVM Viewmodel) {
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
            return onChangeViewmodelGroupName((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelIsAddToRoom((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelGroupName(MutableLiveData<String> ViewmodelGroupName, int fieldId) {
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0068  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 212
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActDaliLightGroupSettingBindingImpl.executeBindings():void");
    }
}