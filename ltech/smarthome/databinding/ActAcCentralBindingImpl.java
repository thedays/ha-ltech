package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.air.ActAcCentralVM;
import com.ltech.smarthome.view.CircleBar;

/* loaded from: classes3.dex */
public class ActAcCentralBindingImpl extends ActAcCentralBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView4;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(19);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_ir"}, new int[]{15}, new int[]{R.layout.layout_title_ir});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 16);
        sparseIntArray.put(R.id.rv_content, 17);
        sparseIntArray.put(R.id.cardview_add, 18);
    }

    public ActAcCentralBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }

    private ActAcCentralBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[10], (CardView) bindings[18], (CircleBar) bindings[1], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[7], (AppCompatTextView) bindings[6], (AppCompatImageView) bindings[13], (ConstraintLayout) bindings[16], (RecyclerView) bindings[17], (LayoutTitleIrBinding) bindings[15], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[3], (View) bindings[9]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView19.setTag(null);
        this.appCompatTextView23.setTag(null);
        this.circleBar.setTag(null);
        this.ivMinus.setTag(null);
        this.ivNext.setTag(null);
        this.ivPlus.setTag(null);
        this.ivUnit.setTag(null);
        this.ivUpper.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView;
        appCompatTextView.setTag(null);
        setContainedBinding(this.title);
        this.tvIndex.setTag(null);
        this.tvResponse.setTag(null);
        this.tvState.setTag(null);
        this.tvTemp.setTag(null);
        this.viewOnOff.setTag(null);
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
        setViewmodel((ActAcCentralVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAcCentralBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAcCentralBinding
    public void setViewmodel(ActAcCentralVM Viewmodel) {
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
            return onChangeViewmodelTempControl((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelPowerOn((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelTempControl(MutableLiveData<Boolean> ViewmodelTempControl, int fieldId) {
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x009a  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActAcCentralBindingImpl.executeBindings():void");
    }
}