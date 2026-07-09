package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActHomeManageVM;
import me.tatarka.bindingcollectionadapter2.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes3.dex */
public class ActHomeManageBindingImpl extends ActHomeManageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(3);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{2}, new int[]{R.layout.layout_title_default});
        sViewsWithIds = null;
    }

    public ActHomeManageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ActHomeManageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (RecyclerView) bindings[1], (LayoutTitleDefaultBinding) bindings[2]);
        this.mDirtyFlags = -1L;
        this.layoutLoad.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        setViewmodel((ActHomeManageVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActHomeManageBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActHomeManageBinding
    public void setViewmodel(ActHomeManageVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
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
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelMMergeObservableList((MergeObservableList) object, fieldId);
    }

    private boolean onChangeViewmodelMMergeObservableList(MergeObservableList<Object> ViewmodelMMergeObservableList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        MergeObservableList<Object> mergeObservableList;
        BindingRecyclerViewAdapter.ItemIds<Object> itemIds;
        OnItemBindClass<Object> onItemBindClass;
        BindingRecyclerViewAdapter.ItemIds<Object> itemIds2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActHomeManageVM actHomeManageVM = this.mViewmodel;
        long j2 = 10 & j;
        OnItemBindClass<Object> onItemBindClass2 = null;
        MergeObservableList<Object> mergeObservableList2 = null;
        long j3 = j & 13;
        if (j3 != 0) {
            if (actHomeManageVM != null) {
                onItemBindClass = actHomeManageVM.itemBinding;
                MergeObservableList<Object> mergeObservableList3 = actHomeManageVM.mMergeObservableList;
                itemIds2 = actHomeManageVM.itemIds;
                mergeObservableList2 = mergeObservableList3;
            } else {
                onItemBindClass = null;
                itemIds2 = null;
            }
            updateRegistration(0, mergeObservableList2);
            itemIds = itemIds2;
            mergeObservableList = mergeObservableList2;
            onItemBindClass2 = onItemBindClass;
        } else {
            mergeObservableList = null;
            itemIds = null;
        }
        if (j3 != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.layoutLoad, BindingCollectionAdapters.toItemBinding(onItemBindClass2), mergeObservableList, null, itemIds, null, null);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}