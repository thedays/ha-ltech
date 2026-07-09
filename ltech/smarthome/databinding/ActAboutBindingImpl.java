package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.item.SettingItem;
import com.ltech.smarthome.ui.my.ActAboutVM;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class ActAboutBindingImpl extends ActAboutBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{3}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_app_icon, 4);
        sparseIntArray.put(R.id.tv_app_version, 5);
        sparseIntArray.put(R.id.tv_app_no, 6);
        sparseIntArray.put(R.id.tv_app_copyright, 7);
    }

    public ActAboutBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActAboutBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[4], (RecyclerView) bindings[2], (LayoutTitleDefaultBinding) bindings[3], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[5]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.rvContent.setTag(null);
        setContainedBinding(this.title);
        this.tvAppName.setTag(null);
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
        setViewmodel((ActAboutVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAboutBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAboutBinding
    public void setViewmodel(ActAboutVM Viewmodel) {
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

    private boolean onChangeViewmodelMObservableList(ObservableList<SettingItem> ViewmodelMObservableList, int fieldId) {
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
        ItemBinding<SettingItem> itemBinding;
        ObservableList<SettingItem> observableList;
        ItemBinding<SettingItem> itemBinding2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActAboutVM actAboutVM = this.mViewmodel;
        long j2 = 10 & j;
        ObservableList<SettingItem> observableList2 = null;
        long j3 = 13 & j;
        if (j3 != 0) {
            if (actAboutVM != null) {
                ObservableList<SettingItem> observableList3 = actAboutVM.mObservableList;
                itemBinding2 = actAboutVM.itemBinding;
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
            BindingRecyclerViewAdapters.setAdapter(this.rvContent, itemBinding, observableList, null, null, null, null);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 8) != 0) {
            ViewAdapter.setTextBold(this.tvAppName, true);
        }
        executeBindingsOn(this.title);
    }
}