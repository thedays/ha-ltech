package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM;
import com.ltech.smarthome.view.CircleBar;

/* loaded from: classes3.dex */
public class FtAcBindingImpl extends FtAcBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView3;
    private final AppCompatTextView mboundView4;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_error_tip, 9);
        sparseIntArray.put(R.id.tv_error_tip, 10);
        sparseIntArray.put(R.id.layout_load, 11);
        sparseIntArray.put(R.id.appCompatTextView19, 12);
        sparseIntArray.put(R.id.tv_state, 13);
        sparseIntArray.put(R.id.rv_content, 14);
        sparseIntArray.put(R.id.group_on, 15);
    }

    public FtAcBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private FtAcBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatTextView) bindings[12], (CircleBar) bindings[1], (Group) bindings[15], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[6], (AppCompatTextView) bindings[5], (LinearLayout) bindings[9], (ConstraintLayout) bindings[11], (RecyclerView) bindings[14], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[2], (View) bindings[8]);
        this.mDirtyFlags = -1L;
        this.circleBar.setTag(null);
        this.ivMinus.setTag(null);
        this.ivPlus.setTag(null);
        this.ivUnit.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        this.tvTemp.setTag(null);
        this.viewOnOff.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        setViewmodel((ActTePanelVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtAcBinding
    public void setViewmodel(ActTePanelVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelOffLine((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelPowerOn((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelOffLine(MutableLiveData<Boolean> ViewmodelOffLine, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelPowerOn(MutableLiveData<Boolean> ViewmodelPowerOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x00c8, code lost:
    
        if (r0 != false) goto L72;
     */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.FtAcBindingImpl.executeBindings():void");
    }
}