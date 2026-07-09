package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.view.ColorPickerPointView;
import com.ltech.smarthome.view.DeliverTouchLayout;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ActColorLightBindingImpl extends ActColorLightBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(33);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{12}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view_bottom, 13);
        sparseIntArray.put(R.id.sb_brt, 14);
        sparseIntArray.put(R.id.tv_brt, 15);
        sparseIntArray.put(R.id.tv_brt_tip, 16);
        sparseIntArray.put(R.id.layout_ccpv, 17);
        sparseIntArray.put(R.id.ccpv, 18);
        sparseIntArray.put(R.id.rv_color, 19);
        sparseIntArray.put(R.id.line_bottom, 20);
        sparseIntArray.put(R.id.line_top, 21);
        sparseIntArray.put(R.id.group_wy, 22);
        sparseIntArray.put(R.id.layout_ccpv2, 23);
        sparseIntArray.put(R.id.ccpv2, 24);
        sparseIntArray.put(R.id.line_bottom2, 25);
        sparseIntArray.put(R.id.line_top2, 26);
        sparseIntArray.put(R.id.tv_tip, 27);
        sparseIntArray.put(R.id.tv_label, 28);
        sparseIntArray.put(R.id.rv_devices, 29);
        sparseIntArray.put(R.id.iv_empty_devices, 30);
        sparseIntArray.put(R.id.tv_empty_devices, 31);
        sparseIntArray.put(R.id.rv_action, 32);
    }

    public ActColorLightBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }

    private ActColorLightBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (ColorPickerPointView) bindings[18], (ColorPickerPointView) bindings[24], (Group) bindings[22], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[30], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[9], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[4], (DeliverTouchLayout) bindings[17], (DeliverTouchLayout) bindings[23], (ConstraintLayout) bindings[6], (ConstraintLayout) bindings[1], (View) bindings[20], (View) bindings[25], (View) bindings[21], (View) bindings[26], (RecyclerView) bindings[32], (RecyclerView) bindings[19], (RecyclerView) bindings[29], (LightBrtBar) bindings[14], (LayoutTitleDefaultBinding) bindings[12], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[27], (View) bindings[11], (View) bindings[13]);
        this.mDirtyFlags = -1L;
        this.ivCamera.setTag(null);
        this.ivChangeBrt.setTag(null);
        this.ivChangePic.setTag(null);
        this.ivCt.setTag(null);
        this.ivGradient.setTag(null);
        this.ivNormal.setTag(null);
        this.ivSort.setTag(null);
        this.ivWy.setTag(null);
        this.layoutGradient.setTag(null);
        this.layoutNormal.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.vOpen.setTag(null);
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
        setViewmodel((ActColorLightVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActColorLightBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActColorLightBinding
    public void setViewmodel(ActColorLightVM Viewmodel) {
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
            return onChangeViewmodelStateOn((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelGradientMode((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelStateOn(MutableLiveData<Boolean> ViewmodelStateOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelGradientMode(MutableLiveData<Boolean> ViewmodelGradientMode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0084  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActColorLightBindingImpl.executeBindings():void");
    }
}