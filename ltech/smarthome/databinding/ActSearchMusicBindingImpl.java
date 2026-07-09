package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicSearchVM;

/* loaded from: classes3.dex */
public class ActSearchMusicBindingImpl extends ActSearchMusicBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(11);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"item_search_bar"}, new int[]{6}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.rv_history, 7);
        sparseIntArray.put(R.id.tv_find_tips, 8);
        sparseIntArray.put(R.id.tv_music_number, 9);
        sparseIntArray.put(R.id.rv_result, 10);
    }

    public ActSearchMusicBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActSearchMusicBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (LinearLayout) bindings[2], (LinearLayout) bindings[4], (LinearLayout) bindings[5], (FrameLayout) bindings[1], (RecyclerView) bindings[7], (RecyclerView) bindings[10], (ItemSearchBarBinding) bindings[6], (ImageView) bindings[3], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[9]);
        this.mDirtyFlags = -1L;
        this.layoutHistory.setTag(null);
        this.layoutLoad.setTag(null);
        this.layoutPlayAll.setTag(null);
        this.layoutSearch.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.searchBar);
        this.searchDel.setTag(null);
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
        setViewmodel((ActDcaMusicSearchVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSearchMusicBinding
    public void setViewmodel(ActDcaMusicSearchVM Viewmodel) {
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
            return onChangeViewmodelShowHistoryLayout((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeSearchBar((ItemSearchBarBinding) object, fieldId);
    }

    private boolean onChangeViewmodelShowHistoryLayout(MutableLiveData<Boolean> ViewmodelShowHistoryLayout, int fieldId) {
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
        BindingCommand<View> bindingCommand;
        int i;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ActDcaMusicSearchVM actDcaMusicSearchVM = this.mViewmodel;
        long j2 = j & 13;
        if (j2 != 0) {
            bindingCommand = ((j & 12) == 0 || actDcaMusicSearchVM == null) ? null : actDcaMusicSearchVM.viewClick;
            MutableLiveData<Boolean> mutableLiveData = actDcaMusicSearchVM != null ? actDcaMusicSearchVM.showHistoryLayout : null;
            updateLiveDataRegistration(0, mutableLiveData);
            Boolean value = mutableLiveData != null ? mutableLiveData.getValue() : null;
            boolean booleanValue = value != null ? value.booleanValue() : false;
            if (j2 != 0) {
                j |= booleanValue ? 160L : 80L;
            }
            i = 8;
            i2 = booleanValue ? 8 : 0;
            if (booleanValue) {
                i = 0;
            }
        } else {
            bindingCommand = null;
            i = 0;
            i2 = 0;
        }
        if ((13 & j) != 0) {
            this.layoutHistory.setVisibility(i);
            this.layoutLoad.setVisibility(i2);
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.layoutPlayAll, bindingCommand, false);
            ViewAdapter.onClickCommand(this.searchDel, bindingCommand, false);
        }
        executeBindingsOn(this.searchBar);
    }
}