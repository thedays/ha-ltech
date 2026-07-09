package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetailVM;
import com.ltech.smarthome.view.CircleMusicView;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public class ActDcaMusicDetailBindingImpl extends ActDcaMusicDetailBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.title, 15);
        sparseIntArray.put(R.id.iv_music_cd, 16);
        sparseIntArray.put(R.id.song_circle_pic, 17);
        sparseIntArray.put(R.id.layout_function, 18);
        sparseIntArray.put(R.id.layout_progress, 19);
        sparseIntArray.put(R.id.sb_music, 20);
        sparseIntArray.put(R.id.layout_play, 21);
    }

    public ActDcaMusicDetailBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }

    private ActDcaMusicDetailBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[13], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[7], (LinearLayout) bindings[18], (ConstraintLayout) bindings[0], (LinearLayout) bindings[21], (LinearLayout) bindings[19], (HorizontalSeekBar) bindings[20], (CircleMusicView) bindings[17], (Toolbar) bindings[15], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        this.ivMode.setTag(null);
        this.ivNext.setTag(null);
        this.ivPlay.setTag(null);
        this.ivPlaylist.setTag(null);
        this.ivPrevious.setTag(null);
        this.ivTime.setTag(null);
        this.ivVolume.setTag(null);
        this.layoutMusicDetail.setTag(null);
        this.tvSinger.setTag(null);
        this.tvTimeEnd.setTag(null);
        this.tvTimeStart.setTag(null);
        this.tvTips.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
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
        setViewmodel((ActDcaMusicDetailVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDcaMusicDetailBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDcaMusicDetailBinding
    public void setViewmodel(ActDcaMusicDetailVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
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
        long j2;
        BindingCommand bindingCommand;
        String str;
        BindingCommand bindingCommand2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        BindingCommand bindingCommand3;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActDcaMusicDetailVM actDcaMusicDetailVM = this.mViewmodel;
        long j3 = j & 10;
        BindingCommand<View> bindingCommand4 = null;
        if (j3 != 0) {
            if (titleDefault != null) {
                i3 = titleDefault.getBackImageRes();
                str = titleDefault.getTitle();
                bindingCommand2 = titleDefault.getBackAction();
                bindingCommand3 = titleDefault.getEditAction();
                i = titleDefault.getEditImageRes();
            } else {
                str = null;
                bindingCommand2 = null;
                bindingCommand3 = null;
                i = 0;
                i3 = 0;
            }
            boolean z = i3 != 0;
            boolean z2 = i != 0;
            if (j3 != 0) {
                j |= z ? 128L : 64L;
            }
            if ((j & 10) != 0) {
                j |= z2 ? 512L : 256L;
            }
            int i6 = z ? 0 : 8;
            i4 = z2 ? 0 : 8;
            bindingCommand = bindingCommand3;
            i2 = i6;
            j2 = 0;
        } else {
            j2 = 0;
            bindingCommand = null;
            str = null;
            bindingCommand2 = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        long j4 = j & 13;
        if (j4 != j2) {
            MutableLiveData<Boolean> mutableLiveData = actDcaMusicDetailVM != null ? actDcaMusicDetailVM.isPlaying : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j4 != j2) {
                j |= safeUnbox ? 32L : 16L;
            }
            i5 = safeUnbox ? R.mipmap.ic_music_detail_stop : R.mipmap.ic_music_detail_play;
            if ((j & 12) != j2 && actDcaMusicDetailVM != null) {
                bindingCommand4 = actDcaMusicDetailVM.viewClick;
            }
        } else {
            i5 = 0;
        }
        if ((j & 10) != j2) {
            ViewAdapter.setSrc(this.ivBack, i3);
            this.ivBack.setVisibility(i2);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand2, false);
            ViewAdapter.setSrc(this.ivEdit, i);
            this.ivEdit.setVisibility(i4);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvTitle, str);
        }
        if ((j & 12) != j2) {
            ViewAdapter.onClickCommand(this.ivMode, bindingCommand4, false);
            ViewAdapter.onClickCommand(this.ivNext, bindingCommand4, false);
            ViewAdapter.onClickCommand(this.ivPlay, bindingCommand4, false);
            ViewAdapter.onClickCommand(this.ivPlaylist, bindingCommand4, false);
            ViewAdapter.onClickCommand(this.ivPrevious, bindingCommand4, false);
            ViewAdapter.onClickCommand(this.ivTime, bindingCommand4, false);
            ViewAdapter.onClickCommand(this.ivVolume, bindingCommand4, false);
        }
        if ((j & 13) != j2) {
            ViewAdapter.setSrc(this.ivPlay, i5);
        }
        if ((j & 8) != j2) {
            ViewAdapter.setTextBold(this.tvSinger, true);
            ViewAdapter.setTextBold(this.tvTimeEnd, true);
            ViewAdapter.setTextBold(this.tvTimeStart, true);
            ViewAdapter.setTextBold(this.tvTips, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}