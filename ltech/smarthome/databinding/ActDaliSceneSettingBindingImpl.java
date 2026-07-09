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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActDaliSceneSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActDaliSceneSettingBindingImpl extends ActDaliSceneSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final RelativeLayout mboundView12;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView7;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(28);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{17}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.act_add_device_scroll, 18);
        sparseIntArray.put(R.id.iv_scene_name_go, 19);
        sparseIntArray.put(R.id.tv_room_name, 20);
        sparseIntArray.put(R.id.iv_room_name_go, 21);
        sparseIntArray.put(R.id.iv_icon, 22);
        sparseIntArray.put(R.id.tv_gateway_name, 23);
        sparseIntArray.put(R.id.tv_scene_number, 24);
        sparseIntArray.put(R.id.sb_add_to_common, 25);
        sparseIntArray.put(R.id.iv_action, 26);
        sparseIntArray.put(R.id.rv_dali_scene, 27);
    }

    public ActDaliSceneSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }

    private ActDaliSceneSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (NestedScrollView) bindings[18], (LinearLayout) bindings[15], (AppCompatImageView) bindings[26], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[19], (RelativeLayout) bindings[6], (ConstraintLayout) bindings[4], (ConstraintLayout) bindings[1], (RecyclerView) bindings[27], (SwitchButton) bindings[25], (SwitchButton) bindings[11], (LayoutTitleDefaultBinding) bindings[17], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[9]);
        this.mDirtyFlags = -1L;
        this.btnEdit.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutSceneName.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[12];
        this.mboundView12 = relativeLayout;
        relativeLayout.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[14];
        this.mboundView14 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[7];
        this.mboundView7 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        this.sbAddToSmart.setTag(null);
        setContainedBinding(this.title);
        this.tvAction.setTag(null);
        this.tvGatewayTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvSceneName.setTag(null);
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
        setViewmodel((ActDaliSceneSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDaliSceneSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDaliSceneSettingBinding
    public void setViewmodel(ActDaliSceneSettingVM Viewmodel) {
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
            return onChangeViewmodelSceneName((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelIsAddToSmart((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelSceneName(MutableLiveData<String> ViewmodelSceneName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelIsAddToSmart(MutableLiveData<Boolean> ViewmodelIsAddToSmart, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0079  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActDaliSceneSettingBindingImpl.executeBindings():void");
    }
}