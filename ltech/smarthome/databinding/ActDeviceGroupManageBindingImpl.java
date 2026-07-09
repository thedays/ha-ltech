package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseManageVM;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.TitleDefault;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class ActDeviceGroupManageBindingImpl extends ActDeviceGroupManageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{3}, new int[]{R.layout.layout_title_default});
        includedLayouts.setIncludes(1, new String[]{"item_search_bar"}, new int[]{4}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.guideline, 5);
        sparseIntArray.put(R.id.spinner_floor, 6);
        sparseIntArray.put(R.id.spinner_room, 7);
    }

    public ActDeviceGroupManageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActDeviceGroupManageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (Guideline) bindings[5], (FrameLayout) bindings[1], (RecyclerView) bindings[2], (ItemSearchBarBinding) bindings[4], (MySpinner) bindings[6], (MySpinner) bindings[7], (LayoutTitleDefaultBinding) bindings[3]);
        this.mDirtyFlags = -1L;
        this.layoutSearch.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.rvContent.setTag(null);
        setContainedBinding(this.searchBar);
        setContainedBinding(this.title);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
        }
        this.title.invalidateAll();
        this.searchBar.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings() || this.searchBar.hasPendingBindings();
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
        setViewmodel((BaseManageVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceGroupManageBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceGroupManageBinding
    public void setViewmodel(BaseManageVM Viewmodel) {
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
        this.searchBar.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelMRoleList((ObservableList) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeSearchBar((ItemSearchBarBinding) object, fieldId);
    }

    private boolean onChangeViewmodelMRoleList(ObservableList<Role> ViewmodelMRoleList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeSearchBar(ItemSearchBarBinding SearchBar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        ItemBinding<Role> itemBinding;
        ObservableList<Role> observableList;
        BindingRecyclerViewAdapter.ItemIds<Role> itemIds;
        ItemBinding<Role> itemBinding2;
        BindingRecyclerViewAdapter.ItemIds<Role> itemIds2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        BaseManageVM baseManageVM = this.mViewmodel;
        long j2 = 20 & j;
        ObservableList<Role> observableList2 = null;
        long j3 = j & 25;
        if (j3 != 0) {
            if (baseManageVM != null) {
                ObservableList<Role> observableList3 = baseManageVM.mRoleList;
                itemBinding2 = baseManageVM.itemBinding;
                itemIds2 = baseManageVM.itemIds;
                observableList2 = observableList3;
            } else {
                itemBinding2 = null;
                itemIds2 = null;
            }
            updateRegistration(0, observableList2);
            itemBinding = itemBinding2;
            itemIds = itemIds2;
            observableList = observableList2;
        } else {
            itemBinding = null;
            observableList = null;
            itemIds = null;
        }
        if (j3 != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.rvContent, itemBinding, observableList, null, itemIds, null, null);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
        executeBindingsOn(this.searchBar);
    }
}