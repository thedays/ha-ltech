package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM;
import com.ltech.smarthome.view.CircleMusicView;
import com.ltech.smarthome.view.StickyScrollView;

/* loaded from: classes3.dex */
public class ActDcaMusicListBindingImpl extends ActDcaMusicListBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(18);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{7}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_bg_music_list, 8);
        sparseIntArray.put(R.id.act_add_device_scroll, 9);
        sparseIntArray.put(R.id.music_number, 10);
        sparseIntArray.put(R.id.line, 11);
        sparseIntArray.put(R.id.iv, 12);
        sparseIntArray.put(R.id.layout_load, 13);
        sparseIntArray.put(R.id.rv, 14);
        sparseIntArray.put(R.id.song_circle_pic, 15);
        sparseIntArray.put(R.id.tv_song_name, 16);
        sparseIntArray.put(R.id.tv_singer, 17);
    }

    public ActDcaMusicListBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }

    private ActDcaMusicListBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (StickyScrollView) bindings[9], (ImageView) bindings[12], (ImageView) bindings[8], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[6], (ConstraintLayout) bindings[4], (FrameLayout) bindings[13], (LinearLayout) bindings[1], (RelativeLayout) bindings[2], (View) bindings[11], (TextView) bindings[10], (RecyclerView) bindings[14], (CircleMusicView) bindings[15], (LayoutTitleDefaultBinding) bindings[7], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[16], (TextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.ivPlay.setTag(null);
        this.ivPlaylist.setTag(null);
        this.layoutBottom.setTag(null);
        this.layoutPlayAll.setTag(null);
        this.layoutSort.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvWifi.setTag(null);
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
        setViewmodel((ActDcaMusicListVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDcaMusicListBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDcaMusicListBinding
    public void setViewmodel(ActDcaMusicListVM Viewmodel) {
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
        return onChangeViewmodelIsPlaying((MutableLiveData) object, fieldId);
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

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActDcaMusicListVM actDcaMusicListVM = this.mViewmodel;
        long j2 = j & 13;
        BindingCommand<View> bindingCommand = null;
        if (j2 != 0) {
            MutableLiveData<Boolean> mutableLiveData = actDcaMusicListVM != null ? actDcaMusicListVM.isPlaying : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 32L : 16L;
            }
            i = safeUnbox ? R.mipmap.ic_music_stop_black : R.mipmap.ic_music_play_black;
            if ((j & 12) != 0 && actDcaMusicListVM != null) {
                bindingCommand = actDcaMusicListVM.viewClick;
            }
        } else {
            i = 0;
        }
        if ((13 & j) != 0) {
            ViewAdapter.setSrc(this.ivPlay, i);
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.ivPlay, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivPlaylist, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBottom, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutPlayAll, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutSort, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvWifi, bindingCommand, false);
        }
        if ((j & 10) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}