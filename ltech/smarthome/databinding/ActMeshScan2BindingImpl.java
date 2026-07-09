package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.view.SpreadView;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class ActMeshScan2BindingImpl extends ActMeshScan2Binding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LayoutTitleDefaultBinding mboundView0;
    private final LinearLayout mboundView01;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(12);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.constraintlayout, 5);
        sparseIntArray.put(R.id.tv_no_device, 6);
        sparseIntArray.put(R.id.tv_help, 7);
        sparseIntArray.put(R.id.view24, 8);
        sparseIntArray.put(R.id.spreadview_scan, 9);
        sparseIntArray.put(R.id.tv_scan_tip_2, 10);
        sparseIntArray.put(R.id.v_line, 11);
    }

    public ActMeshScan2BindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private ActMeshScan2BindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (ConstraintLayout) bindings[5], (RecyclerView) bindings[3], (SpreadView) bindings[9], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[2], (View) bindings[11], (View) bindings[8]);
        this.mDirtyFlags = -1L;
        LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) bindings[4];
        this.mboundView0 = layoutTitleDefaultBinding;
        setContainedBinding(layoutTitleDefaultBinding);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView01 = linearLayout;
        linearLayout.setTag(null);
        this.rvContent.setTag(null);
        this.tvScanTip1.setTag(null);
        this.tvTotal.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
        }
        this.mboundView0.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.mboundView0.hasPendingBindings();
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
        setViewmodel((ActMeshScanVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActMeshScan2Binding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
    }

    @Override // com.ltech.smarthome.databinding.ActMeshScan2Binding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActMeshScan2Binding
    public void setViewmodel(ActMeshScanVM Viewmodel) {
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
        this.mboundView0.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelDeviceNum((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelDeviceList((ObservableList) object, fieldId);
    }

    private boolean onChangeViewmodelDeviceNum(MutableLiveData<String> ViewmodelDeviceNum, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelDeviceList(ObservableList<ActMeshScanVM.ScanItem> ViewmodelDeviceList, int fieldId) {
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
        long j2;
        ItemBinding<ActMeshScanVM.ScanItem> itemBinding;
        ObservableList<ActMeshScanVM.ScanItem> observableList;
        BindingRecyclerViewAdapter.ItemIds<ActMeshScanVM.ScanItem> itemIds;
        ObservableList<ActMeshScanVM.ScanItem> observableList2;
        ItemBinding<ActMeshScanVM.ScanItem> itemBinding2;
        BindingRecyclerViewAdapter.ItemIds<ActMeshScanVM.ScanItem> itemIds2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActMeshScanVM actMeshScanVM = this.mViewmodel;
        long j3 = 40 & j;
        String str = null;
        if ((51 & j) != 0) {
            if ((j & 50) != 0) {
                if (actMeshScanVM != null) {
                    itemBinding2 = actMeshScanVM.itemBinding;
                    itemIds2 = actMeshScanVM.itemIds;
                    j2 = 0;
                    observableList2 = actMeshScanVM.deviceList;
                } else {
                    j2 = 0;
                    observableList2 = null;
                    itemBinding2 = null;
                    itemIds2 = null;
                }
                updateRegistration(1, observableList2);
            } else {
                j2 = 0;
                observableList2 = null;
                itemBinding2 = null;
                itemIds2 = null;
            }
            if ((j & 49) != j2) {
                MutableLiveData<String> mutableLiveData = actMeshScanVM != null ? actMeshScanVM.deviceNum : null;
                updateLiveDataRegistration(0, mutableLiveData);
                if (mutableLiveData != null) {
                    str = mutableLiveData.getValue();
                }
            }
            observableList = observableList2;
            itemBinding = itemBinding2;
            itemIds = itemIds2;
        } else {
            j2 = 0;
            itemBinding = null;
            observableList = null;
            itemIds = null;
        }
        if (j3 != 0) {
            this.mboundView0.setTitle(titleDefault);
        }
        if ((j & 50) != j2) {
            BindingRecyclerViewAdapters.setAdapter(this.rvContent, itemBinding, observableList, null, itemIds, null, null);
        }
        if ((32 & j) != j2) {
            ViewAdapter.setTextBold(this.tvScanTip1, true);
        }
        if ((j & 49) != j2) {
            TextViewBindingAdapter.setText(this.tvTotal, str);
        }
        executeBindingsOn(this.mboundView0);
    }
}