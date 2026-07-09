package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.ui.control.FtAutomationVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class FtAutomationBindingImpl extends FtAutomationBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(5);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"item_search_bar_no_edit"}, new int[]{3}, new int[]{R.layout.item_search_bar_no_edit});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 4);
    }

    public FtAutomationBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private FtAutomationBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (FrameLayout) bindings[1], (SmartRefreshLayout) bindings[4], (RecyclerView) bindings[2], (ItemSearchBarNoEditBinding) bindings[3]);
        this.mDirtyFlags = -1L;
        this.layoutSearch.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.rvContent.setTag(null);
        setContainedBinding(this.searchBar);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
        }
        this.searchBar.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.searchBar.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (92 != variableId) {
            return false;
        }
        setViewmodel((FtAutomationVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtAutomationBinding
    public void setViewmodel(FtAutomationVM Viewmodel) {
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
        this.searchBar.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeSearchBar((ItemSearchBarNoEditBinding) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelAutomationList((ObservableList) object, fieldId);
    }

    private boolean onChangeSearchBar(ItemSearchBarNoEditBinding SearchBar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelAutomationList(ObservableList<Automation> ViewmodelAutomationList, int fieldId) {
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
        ItemBinding<Automation> itemBinding;
        ObservableList<Automation> observableList;
        BindingRecyclerViewAdapter.ItemIds<Automation> itemIds;
        ItemBinding<Automation> itemBinding2;
        ObservableList<Automation> observableList2;
        BindingRecyclerViewAdapter.ItemIds<Automation> itemIds2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        FtAutomationVM ftAutomationVM = this.mViewmodel;
        long j2 = 14 & j;
        RecyclerView.ItemDecoration itemDecoration = null;
        if (j2 != 0) {
            if (ftAutomationVM != null) {
                itemBinding2 = ftAutomationVM.itemBinding;
                observableList2 = ftAutomationVM.automationList;
                itemIds2 = ftAutomationVM.itemIds;
            } else {
                itemBinding2 = null;
                observableList2 = null;
                itemIds2 = null;
            }
            updateRegistration(1, observableList2);
            if ((j & 12) != 0 && ftAutomationVM != null) {
                itemDecoration = ftAutomationVM.decoration;
            }
            itemBinding = itemBinding2;
            observableList = observableList2;
            itemIds = itemIds2;
        } else {
            itemBinding = null;
            observableList = null;
            itemIds = null;
        }
        if ((j & 12) != 0) {
            ViewAdapter.addItemDecoration(this.rvContent, itemDecoration);
        }
        if (j2 != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.rvContent, itemBinding, observableList, null, itemIds, null, null);
        }
        executeBindingsOn(this.searchBar);
    }
}