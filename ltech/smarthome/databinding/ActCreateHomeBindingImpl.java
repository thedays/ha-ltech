package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActCreateHomeVM;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;

/* loaded from: classes3.dex */
public class ActCreateHomeBindingImpl extends ActCreateHomeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final RecyclerView mboundView2;
    private final AppCompatEditText mboundView4;
    private InverseBindingListener mboundView4androidTextAttrChanged;
    private InverseBindingListener tvLocationandroidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{7}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view3, 8);
        sparseIntArray.put(R.id.view4, 9);
        sparseIntArray.put(R.id.view5, 10);
        sparseIntArray.put(R.id.view7, 11);
        sparseIntArray.put(R.id.tv_location_go, 12);
    }

    public ActCreateHomeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActCreateHomeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[5], (View) bindings[1], (LayoutTitleDefaultBinding) bindings[7], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[12], (View) bindings[8], (View) bindings[9], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[11]);
        this.mboundView4androidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActCreateHomeBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActCreateHomeBindingImpl.this.mboundView4);
                ActCreateHomeVM actCreateHomeVM = ActCreateHomeBindingImpl.this.mViewmodel;
                if (actCreateHomeVM == null || (observableField = actCreateHomeVM.homeName) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.tvLocationandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActCreateHomeBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActCreateHomeBindingImpl.this.tvLocation);
                ActCreateHomeVM actCreateHomeVM = ActCreateHomeBindingImpl.this.mViewmodel;
                if (actCreateHomeVM == null || (observableField = actCreateHomeVM.homeLocation) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.appCompatTextView3.setTag(null);
        this.appCompatTextView4.setTag(null);
        this.layoutHomePosition.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        RecyclerView recyclerView = (RecyclerView) bindings[2];
        this.mboundView2 = recyclerView;
        recyclerView.setTag(null);
        AppCompatEditText appCompatEditText = (AppCompatEditText) bindings[4];
        this.mboundView4 = appCompatEditText;
        appCompatEditText.setTag(null);
        setContainedBinding(this.title);
        this.tvLocation.setTag(null);
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
        setViewmodel((ActCreateHomeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActCreateHomeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCreateHomeBinding
    public void setViewmodel(ActCreateHomeVM Viewmodel) {
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
            return onChangeViewmodelHomeName((ObservableField) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelHomeLocation((ObservableField) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeViewmodelMMergeObservableList((MergeObservableList) object, fieldId);
    }

    private boolean onChangeViewmodelHomeName(ObservableField<String> ViewmodelHomeName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelHomeLocation(ObservableField<String> ViewmodelHomeLocation, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelMMergeObservableList(MergeObservableList<Object> ViewmodelMMergeObservableList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x007f  */
    /* JADX WARN: Type inference failed for: r27v0 */
    /* JADX WARN: Type inference failed for: r27v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r27v2 */
    /* JADX WARN: Type inference failed for: r32v0, types: [com.ltech.smarthome.databinding.ActCreateHomeBindingImpl] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActCreateHomeBindingImpl.executeBindings():void");
    }
}