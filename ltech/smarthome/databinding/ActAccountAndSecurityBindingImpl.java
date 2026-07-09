package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.user.AccountAndSecurityVM;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class ActAccountAndSecurityBindingImpl extends ActAccountAndSecurityBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final RecyclerView mboundView1;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(3);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{2}, new int[]{R.layout.layout_title_default});
        sViewsWithIds = null;
    }

    public ActAccountAndSecurityBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ActAccountAndSecurityBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (LayoutTitleDefaultBinding) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        RecyclerView recyclerView = (RecyclerView) bindings[1];
        this.mboundView1 = recyclerView;
        recyclerView.setTag(null);
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
        setViewmodel((AccountAndSecurityVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAccountAndSecurityBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAccountAndSecurityBinding
    public void setViewmodel(AccountAndSecurityVM Viewmodel) {
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
        return onChangeViewmodelMObservableList((ObservableList) object, fieldId);
    }

    private boolean onChangeViewmodelMObservableList(ObservableList<GoItem> ViewmodelMObservableList, int fieldId) {
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
        ItemBinding<GoItem> itemBinding;
        ObservableList<GoItem> observableList;
        ItemBinding<GoItem> itemBinding2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        AccountAndSecurityVM accountAndSecurityVM = this.mViewmodel;
        long j2 = 10 & j;
        ObservableList<GoItem> observableList2 = null;
        long j3 = j & 13;
        if (j3 != 0) {
            if (accountAndSecurityVM != null) {
                ObservableList<GoItem> observableList3 = accountAndSecurityVM.mObservableList;
                itemBinding2 = accountAndSecurityVM.itemBinding;
                observableList2 = observableList3;
            } else {
                itemBinding2 = null;
            }
            updateRegistration(0, observableList2);
            itemBinding = itemBinding2;
            observableList = observableList2;
        } else {
            itemBinding = null;
            observableList = null;
        }
        if (j3 != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.mboundView1, itemBinding, observableList, null, null, null, null);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}