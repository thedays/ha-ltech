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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.setting.ActPanelNightGetUpVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActPanelNightGetupBindingImpl extends ActPanelNightGetupBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView1;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView13;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(21);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{15}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_get_up_night_mode, 16);
        sparseIntArray.put(R.id.iv_start_time_go, 17);
        sparseIntArray.put(R.id.iv_end_time_go, 18);
        sparseIntArray.put(R.id.iv_mode_scene_go, 19);
        sparseIntArray.put(R.id.iv_reset_scene_go, 20);
    }

    public ActPanelNightGetupBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }

    private ActPanelNightGetupBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[17], (ConstraintLayout) bindings[6], (RelativeLayout) bindings[16], (RelativeLayout) bindings[9], (RelativeLayout) bindings[12], (ConstraintLayout) bindings[3], (SwitchButton) bindings[2], (LayoutTitleDefaultBinding) bindings[15], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[4]);
        this.mDirtyFlags = -1L;
        this.layoutEndTime.setTag(null);
        this.layoutGetUpNightModeScene.setTag(null);
        this.layoutResetScene.setTag(null);
        this.layoutStartTime.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[1];
        this.mboundView1 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        this.sbGetUpNightMode.setTag(null);
        setContainedBinding(this.title);
        this.tvEndTime.setTag(null);
        this.tvEndTimeTip.setTag(null);
        this.tvModeSceneTip.setTag(null);
        this.tvResetSceneTip.setTag(null);
        this.tvStartTime.setTag(null);
        this.tvStartTimeTip.setTag(null);
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
        if (61 == variableId) {
            setProductId((ProductId) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActPanelNightGetUpVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActPanelNightGetupBinding
    public void setProductId(ProductId ProductId) {
        this.mProductId = ProductId;
    }

    @Override // com.ltech.smarthome.databinding.ActPanelNightGetupBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActPanelNightGetupBinding
    public void setViewmodel(ActPanelNightGetUpVM Viewmodel) {
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
            return onChangeViewmodelRestartSceneText((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelSwitchOn((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelStarTimeText((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelExcSceneText((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelRestartSceneText(MutableLiveData<String> ViewmodelRestartSceneText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelSwitchOn(MutableLiveData<Boolean> ViewmodelSwitchOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelStarTimeText(MutableLiveData<String> ViewmodelStarTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelExcSceneText(MutableLiveData<String> ViewmodelExcSceneText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0099 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0071  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActPanelNightGetupBindingImpl.executeBindings():void");
    }
}