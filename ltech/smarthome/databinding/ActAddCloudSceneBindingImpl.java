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
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.scene.ActAddCloudSceneVM;
import com.ltech.smarthome.view.SwitchButton;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public class ActAddCloudSceneBindingImpl extends ActAddCloudSceneBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView7;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(24);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{15}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 16);
        sparseIntArray.put(R.id.view17, 17);
        sparseIntArray.put(R.id.iv_mode_icon, 18);
        sparseIntArray.put(R.id.sb_add_to_common, 19);
        sparseIntArray.put(R.id.layout_tip, 20);
        sparseIntArray.put(R.id.tv_total_time, 21);
        sparseIntArray.put(R.id.iv_action, 22);
        sparseIntArray.put(R.id.rv_action, 23);
    }

    public ActAddCloudSceneBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }

    private ActAddCloudSceneBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (AppCompatTextView) bindings[8], (LinearLayout) bindings[14], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[9], (AppCompatImageView) bindings[18], (LinearLayout) bindings[10], (ConstraintLayout) bindings[16], (LinearLayout) bindings[12], (ConstraintLayout) bindings[20], (SwipeRecyclerView) bindings[23], (SwitchButton) bindings[19], (LayoutTitleDefaultBinding) bindings[15], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[21], (View) bindings[6], (View) bindings[17]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView28.setTag(null);
        this.footerView.setTag(null);
        this.ivImport.setTag(null);
        this.layoutEditAction.setTag(null);
        this.layoutSort.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[7];
        this.mboundView7 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        setContainedBinding(this.title);
        this.tvAction.setTag(null);
        this.tvChooseIcon.setTag(null);
        this.tvModeName.setTag(null);
        this.tvModeNameTip.setTag(null);
        this.tvRoomName.setTag(null);
        this.tvRoomNameTip.setTag(null);
        this.vChangeIcon.setTag(null);
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
        setViewmodel((ActAddCloudSceneVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAddCloudSceneBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAddCloudSceneBinding
    public void setViewmodel(ActAddCloudSceneVM Viewmodel) {
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
            return onChangeViewmodelSceneName((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelIsShowEditLayout((SingleLiveEvent) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeViewmodelIsShowSortLayout((SingleLiveEvent) object, fieldId);
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

    private boolean onChangeViewmodelIsShowEditLayout(SingleLiveEvent<Boolean> ViewmodelIsShowEditLayout, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowSortLayout(SingleLiveEvent<Boolean> ViewmodelIsShowSortLayout, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0073 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00b3  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActAddCloudSceneBindingImpl.executeBindings():void");
    }
}