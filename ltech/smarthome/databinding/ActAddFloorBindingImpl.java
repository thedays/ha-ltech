package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActAddFloorVM;
import me.tatarka.bindingcollectionadapter2.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes3.dex */
public class ActAddFloorBindingImpl extends ActAddFloorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final RecyclerView mboundView1;
    private final AppCompatEditText mboundView3;
    private InverseBindingListener mboundView3androidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view3, 5);
        sparseIntArray.put(R.id.view4, 6);
        sparseIntArray.put(R.id.view7, 7);
    }

    public ActAddFloorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActAddFloorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatTextView) bindings[2], (LayoutTitleDefaultBinding) bindings[4], (View) bindings[5], (View) bindings[6], (AppCompatTextView) bindings[7]);
        this.mboundView3androidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActAddFloorBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActAddFloorBindingImpl.this.mboundView3);
                ActAddFloorVM actAddFloorVM = ActAddFloorBindingImpl.this.mViewmodel;
                if (actAddFloorVM == null || (observableField = actAddFloorVM.floorName) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.appCompatTextView3.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        RecyclerView recyclerView = (RecyclerView) bindings[1];
        this.mboundView1 = recyclerView;
        recyclerView.setTag(null);
        AppCompatEditText appCompatEditText = (AppCompatEditText) bindings[3];
        this.mboundView3 = appCompatEditText;
        appCompatEditText.setTag(null);
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
        setViewmodel((ActAddFloorVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAddFloorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAddFloorBinding
    public void setViewmodel(ActAddFloorVM Viewmodel) {
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
            return onChangeViewmodelMergeObservableList((MergeObservableList) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelFloorName((ObservableField) object, fieldId);
    }

    private boolean onChangeViewmodelMergeObservableList(MergeObservableList<Object> ViewmodelMergeObservableList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelFloorName(ObservableField<String> ViewmodelFloorName, int fieldId) {
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
        String str;
        OnItemBindClass<Object> onItemBindClass;
        ObservableList observableList;
        ObservableList observableList2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActAddFloorVM actAddFloorVM = this.mViewmodel;
        long j3 = 20 & j;
        if ((27 & j) != 0) {
            if ((j & 25) != 0) {
                if (actAddFloorVM != null) {
                    onItemBindClass = actAddFloorVM.itemBinding;
                    observableList2 = actAddFloorVM.mergeObservableList;
                } else {
                    onItemBindClass = null;
                    observableList2 = null;
                }
                j2 = 0;
                updateRegistration(0, observableList2);
            } else {
                j2 = 0;
                onItemBindClass = null;
                observableList2 = null;
            }
            if ((j & 26) != j2) {
                ObservableField<String> observableField = actAddFloorVM != null ? actAddFloorVM.floorName : null;
                updateRegistration(1, observableField);
                if (observableField != null) {
                    str = observableField.get();
                    observableList = observableList2;
                }
            }
            observableList = observableList2;
            str = null;
        } else {
            j2 = 0;
            str = null;
            onItemBindClass = null;
            observableList = null;
        }
        if ((16 & j) != j2) {
            ViewAdapter.setTextBold(this.appCompatTextView3, true);
            ViewAdapter.setTextBold(this.mboundView3, true);
            TextViewBindingAdapter.setTextWatcher(this.mboundView3, null, null, null, this.mboundView3androidTextAttrChanged);
        }
        if ((j & 25) != j2) {
            BindingRecyclerViewAdapters.setAdapter(this.mboundView1, BindingCollectionAdapters.toItemBinding(onItemBindClass), observableList, null, null, null, null);
        }
        if ((j & 26) != j2) {
            TextViewBindingAdapter.setText(this.mboundView3, str);
        }
        if (j3 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}