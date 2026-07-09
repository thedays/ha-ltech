package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.upgrade.ActBtOtaVM;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class ActBtOtaBindingImpl extends ActBtOtaBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(9);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        includedLayouts.setIncludes(1, new String[]{"item_search_bar"}, new int[]{5}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.guideline, 6);
        sparseIntArray.put(R.id.spinner_floor, 7);
        sparseIntArray.put(R.id.spinner_room, 8);
    }

    public ActBtOtaBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ActBtOtaBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (Guideline) bindings[6], (FrameLayout) bindings[1], (RecyclerView) bindings[2], (ItemSearchBarBinding) bindings[5], (MySpinner) bindings[7], (MySpinner) bindings[8], (LayoutTitleDefaultBinding) bindings[4], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.layoutSearch.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.rvContent.setTag(null);
        setContainedBinding(this.searchBar);
        setContainedBinding(this.title);
        this.tvBottom.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActBtOtaVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActBtOtaBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
    }

    @Override // com.ltech.smarthome.databinding.ActBtOtaBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActBtOtaBinding
    public void setViewmodel(ActBtOtaVM Viewmodel) {
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
        this.searchBar.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeSearchBar((ItemSearchBarBinding) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelMDeviceList((ObservableList) object, fieldId);
    }

    private boolean onChangeSearchBar(ItemSearchBarBinding SearchBar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelMDeviceList(ObservableList<ActBtOtaVM.ScanItem> ViewmodelMDeviceList, int fieldId) {
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
        ItemBinding<ActBtOtaVM.ScanItem> itemBinding;
        ObservableList<ActBtOtaVM.ScanItem> observableList;
        BindingRecyclerViewAdapter.ItemIds<ActBtOtaVM.ScanItem> itemIds;
        ItemBinding<ActBtOtaVM.ScanItem> itemBinding2;
        BindingRecyclerViewAdapter.ItemIds<ActBtOtaVM.ScanItem> itemIds2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActBtOtaVM actBtOtaVM = this.mViewmodel;
        long j2 = 40 & j;
        ObservableList<ActBtOtaVM.ScanItem> observableList2 = null;
        long j3 = 50 & j;
        if (j3 != 0) {
            if (actBtOtaVM != null) {
                itemBinding2 = actBtOtaVM.itemBinding;
                itemIds2 = actBtOtaVM.itemIds;
                observableList2 = actBtOtaVM.mDeviceList;
            } else {
                itemBinding2 = null;
                itemIds2 = null;
            }
            updateRegistration(1, observableList2);
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
        if ((j & 32) != 0) {
            ViewAdapter.setTextBold(this.tvBottom, true);
        }
        executeBindingsOn(this.title);
        executeBindingsOn(this.searchBar);
    }
}