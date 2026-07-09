package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.gqx.ActGqxVM;

/* loaded from: classes3.dex */
public class ActGqxBindingImpl extends ActGqxBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(9);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{5}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.bg, 6);
        sparseIntArray.put(R.id.tab_layout, 7);
        sparseIntArray.put(R.id.vp, 8);
    }

    public ActGqxBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ActGqxBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (ImageView) bindings[6], (TabLayout) bindings[7], (LayoutTitleDefaultBinding) bindings[5], (TextView) bindings[1], (TextView) bindings[2], (TextView) bindings[3], (TextView) bindings[4], (ViewPager2) bindings[8]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvBtn1.setTag(null);
        this.tvBtn2.setTag(null);
        this.tvBtn3.setTag(null);
        this.tvBtn4.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128L;
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
        setViewmodel((ActGqxVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActGqxBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGqxBinding
    public void setViewmodel(ActGqxVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 64;
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
            return onChangeViewmodelKeyName2((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelKeyName1((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelKeyName4((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelKeyName3((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelIndex((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelKeyName2(MutableLiveData<String> ViewmodelKeyName2, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelKeyName1(MutableLiveData<String> ViewmodelKeyName1, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelKeyName4(MutableLiveData<String> ViewmodelKeyName4, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelKeyName3(MutableLiveData<String> ViewmodelKeyName3, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelIndex(MutableLiveData<Integer> ViewmodelIndex, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0131 A[ADDED_TO_REGION] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActGqxBindingImpl.executeBindings():void");
    }
}