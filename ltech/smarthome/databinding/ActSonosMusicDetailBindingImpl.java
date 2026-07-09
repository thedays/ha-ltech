package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.sonos.ActSonosPlayControlVM;
import com.ltech.smarthome.view.CircleMusicView;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActSonosMusicDetailBindingImpl extends ActSonosMusicDetailBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final NestedScrollView mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(30);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"layout_title_default"}, new int[]{24}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_music_cd, 25);
        sparseIntArray.put(R.id.layout_function, 26);
        sparseIntArray.put(R.id.line, 27);
        sparseIntArray.put(R.id.layout_play, 28);
        sparseIntArray.put(R.id.layout_cross_fade, 29);
    }

    public ActSonosMusicDetailBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 30, sIncludes, sViewsWithIds));
    }

    private ActSonosMusicDetailBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (TextView) bindings[15], (AppCompatImageView) bindings[17], (CircleMusicView) bindings[25], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[9], (AppCompatImageView) bindings[13], (AppCompatImageView) bindings[12], (RelativeLayout) bindings[29], (ConstraintLayout) bindings[26], (LinearLayout) bindings[22], (ConstraintLayout) bindings[1], (LinearLayout) bindings[28], (LinearLayout) bindings[18], (View) bindings[27], (SwitchButton) bindings[23], (LayoutTitleDefaultBinding) bindings[24], (TextView) bindings[16], (TextView) bindings[5], (TextView) bindings[4], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.cross.setTag(null);
        this.ivCross.setTag(null);
        this.ivNext.setTag(null);
        this.ivPlay.setTag(null);
        this.ivPrevious.setTag(null);
        this.ivRepeat.setTag(null);
        this.ivRepeatBg.setTag(null);
        this.ivRepeatOne.setTag(null);
        this.ivRepeatOneBg.setTag(null);
        this.ivShuffle.setTag(null);
        this.ivShuffleBg.setTag(null);
        this.layoutMenu.setTag(null);
        this.layoutMusicDetail.setTag(null);
        this.layoutVolume.setTag(null);
        NestedScrollView nestedScrollView = (NestedScrollView) bindings[0];
        this.mboundView0 = nestedScrollView;
        nestedScrollView.setTag(null);
        this.sbCrossFade.setTag(null);
        setContainedBinding(this.title);
        this.tvCross.setTag(null);
        this.tvModeFull.setTag(null);
        this.tvModeLabel.setTag(null);
        this.tvRepeat.setTag(null);
        this.tvRepeatOne.setTag(null);
        this.tvShuffle.setTag(null);
        this.tvSinger.setTag(null);
        this.tvSong.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128L;
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
        setViewmodel((ActSonosPlayControlVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSonosMusicDetailBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSonosMusicDetailBinding
    public void setViewmodel(ActSonosPlayControlVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 64;
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
            return onChangeViewmodelIsPlaying((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelSingerName((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelSongName((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelIsCrossFade((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelPlayModeEvent((MutableLiveData) object, fieldId);
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

    private boolean onChangeViewmodelSingerName(MutableLiveData<String> ViewmodelSingerName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelSongName(MutableLiveData<String> ViewmodelSongName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelIsCrossFade(MutableLiveData<Boolean> ViewmodelIsCrossFade, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelPlayModeEvent(MutableLiveData<Integer> ViewmodelPlayModeEvent, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e4  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 672
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSonosMusicDetailBindingImpl.executeBindings():void");
    }
}