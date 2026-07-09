package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHomeVM;
import com.ltech.smarthome.view.CircleMusicView;

/* loaded from: classes3.dex */
public class ActDcaMusicHomeBindingImpl extends ActDcaMusicHomeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final LinearLayout mboundView1;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(12);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"layout_title_default"}, new int[]{6}, new int[]{R.layout.layout_title_default});
        includedLayouts.setIncludes(2, new String[]{"item_search_bar_no_edit_music"}, new int[]{7}, new int[]{R.layout.item_search_bar_no_edit_music});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.rv, 8);
        sparseIntArray.put(R.id.song_circle_pic, 9);
        sparseIntArray.put(R.id.tv_song_name, 10);
        sparseIntArray.put(R.id.tv_singer, 11);
    }

    public ActDcaMusicHomeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private ActDcaMusicHomeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[5], (ConstraintLayout) bindings[3], (FrameLayout) bindings[2], (RecyclerView) bindings[8], (ItemSearchBarNoEditMusicBinding) bindings[7], (CircleMusicView) bindings[9], (LayoutTitleDefaultBinding) bindings[6], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[10]);
        this.mDirtyFlags = -1L;
        this.ivPlay.setTag(null);
        this.ivPlaylist.setTag(null);
        this.layoutBottom.setTag(null);
        this.layoutSearch.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[1];
        this.mboundView1 = linearLayout;
        linearLayout.setTag(null);
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
        setViewmodel((ActDcaMusicHomeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDcaMusicHomeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDcaMusicHomeBinding
    public void setViewmodel(ActDcaMusicHomeVM Viewmodel) {
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
            return onChangeViewmodelIsPlaying((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeSearchBar((ItemSearchBarNoEditMusicBinding) object, fieldId);
    }

    private boolean onChangeViewmodelIsPlaying(MutableLiveData<Boolean> ViewmodelIsPlaying, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeSearchBar(ItemSearchBarNoEditMusicBinding SearchBar, int fieldId) {
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
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActDcaMusicHomeVM actDcaMusicHomeVM = this.mViewmodel;
        long j2 = j & 25;
        BindingCommand<View> bindingCommand = null;
        if (j2 != 0) {
            MutableLiveData<Boolean> mutableLiveData = actDcaMusicHomeVM != null ? actDcaMusicHomeVM.isPlaying : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 64L : 32L;
            }
            i = safeUnbox ? R.mipmap.ic_music_stop_black : R.mipmap.ic_music_play_black;
            if ((j & 24) != 0 && actDcaMusicHomeVM != null) {
                bindingCommand = actDcaMusicHomeVM.viewClick;
            }
        } else {
            i = 0;
        }
        if ((25 & j) != 0) {
            ViewAdapter.setSrc(this.ivPlay, i);
        }
        if ((j & 24) != 0) {
            ViewAdapter.onClickCommand(this.ivPlay, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivPlaylist, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBottom, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutSearch, bindingCommand, false);
        }
        if ((j & 20) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
        executeBindingsOn(this.searchBar);
    }
}