package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MediatorLiveData;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class FtRoomBindingImpl extends FtRoomBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view8, 9);
        sparseIntArray.put(R.id.layout_load, 10);
        sparseIntArray.put(R.id.refreshLayout, 11);
        sparseIntArray.put(R.id.tabs, 12);
        sparseIntArray.put(R.id.viewpager, 13);
    }

    public FtRoomBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private FtRoomBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[1], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[6], (LinearLayout) bindings[10], (SmartRefreshLayout) bindings[11], (AppCompatImageView) bindings[2], (AppCompatTextView) bindings[8], (TabLayout) bindings[12], (AppCompatTextView) bindings[4], (View) bindings[9], (ViewPager2) bindings[13]);
        this.mDirtyFlags = -1L;
        this.addDevice.setTag(null);
        this.appCompatImageView2.setTag(null);
        this.appCompatTextView4.setTag(null);
        this.appCompatTextView5.setTag(null);
        this.centerSelect.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.searchDevice.setTag(null);
        this.selectAll.setTag(null);
        this.tvSelectDone.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (92 != variableId) {
            return false;
        }
        setViewmodel((FtRoomVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtRoomBinding
    public void setViewmodel(FtRoomVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelIsShowSelectAll((SingleLiveEvent) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelSelectNumber((SingleLiveEvent) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelSelectAllText((SingleLiveEvent) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelPlaceResult((MediatorLiveData) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelFloorResult((MediatorLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelIsShowSelectAll(SingleLiveEvent<Boolean> ViewmodelIsShowSelectAll, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelSelectNumber(SingleLiveEvent<String> ViewmodelSelectNumber, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelSelectAllText(SingleLiveEvent<String> ViewmodelSelectAllText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelPlaceResult(MediatorLiveData<Place> ViewmodelPlaceResult, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelFloorResult(MediatorLiveData<Floor> ViewmodelFloorResult, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0111  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 443
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.FtRoomBindingImpl.executeBindings():void");
    }
}