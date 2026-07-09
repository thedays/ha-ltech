package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.my.FtMessageNoticeVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import me.tatarka.bindingcollectionadapter2.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;

/* loaded from: classes3.dex */
public class FtMessageNoticeBindingImpl extends FtMessageNoticeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final LinearLayout mboundView1;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(5);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"item_message_data_footer"}, new int[]{3}, new int[]{R.layout.item_message_data_footer});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 4);
    }

    public FtMessageNoticeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private FtMessageNoticeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (ItemMessageDataFooterBinding) bindings[3], (SmartRefreshLayout) bindings[4], (RecyclerView) bindings[2]);
        this.mDirtyFlags = -1L;
        setContainedBinding(this.footerView);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[1];
        this.mboundView1 = linearLayout;
        linearLayout.setTag(null);
        this.rvContent.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
        }
        this.footerView.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.footerView.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (92 != variableId) {
            return false;
        }
        setViewmodel((FtMessageNoticeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtMessageNoticeBinding
    public void setViewmodel(FtMessageNoticeVM Viewmodel) {
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
        this.footerView.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeFooterView((ItemMessageDataFooterBinding) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelDataList((MergeObservableList) object, fieldId);
    }

    private boolean onChangeFooterView(ItemMessageDataFooterBinding FooterView, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelDataList(MergeObservableList<Object> ViewmodelDataList, int fieldId) {
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
        MergeObservableList<Object> mergeObservableList;
        OnItemBind<Object> onItemBind;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        FtMessageNoticeVM ftMessageNoticeVM = this.mViewmodel;
        long j2 = j & 14;
        OnItemBind<Object> onItemBind2 = null;
        MergeObservableList<Object> mergeObservableList2 = null;
        if (j2 != 0) {
            if (ftMessageNoticeVM != null) {
                onItemBind = ftMessageNoticeVM.itemBinding;
                mergeObservableList2 = ftMessageNoticeVM.dataList;
            } else {
                onItemBind = null;
            }
            updateRegistration(1, mergeObservableList2);
            mergeObservableList = mergeObservableList2;
            onItemBind2 = onItemBind;
        } else {
            mergeObservableList = null;
        }
        if (j2 != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.rvContent, BindingCollectionAdapters.toItemBinding(onItemBind2), mergeObservableList, null, null, null, null);
        }
        executeBindingsOn(this.footerView);
    }
}