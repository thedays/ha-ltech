package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.musicplayer.FtSongsVM;

/* loaded from: classes3.dex */
public class ActSongsBindingImpl extends ActSongsBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayoutCompat mboundView0;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(16);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{10}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 11);
        sparseIntArray.put(R.id.v_title_bg, 12);
        sparseIntArray.put(R.id.recycler_view, 13);
        sparseIntArray.put(R.id.layout_control, 14);
        sparseIntArray.put(R.id.layout_play, 15);
    }

    public ActSongsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private ActSongsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[5], (ConstraintLayout) bindings[14], (FrameLayout) bindings[11], (LinearLayoutCompat) bindings[15], (RecyclerView) bindings[13], (AppCompatSeekBar) bindings[8], (LayoutTitleDefaultBinding) bindings[10], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (View) bindings[12]);
        this.mDirtyFlags = -1L;
        this.ivLast.setTag(null);
        this.ivMode.setTag(null);
        this.ivMute.setTag(null);
        this.ivNext.setTag(null);
        this.ivPlay.setTag(null);
        LinearLayoutCompat linearLayoutCompat = (LinearLayoutCompat) bindings[0];
        this.mboundView0 = linearLayoutCompat;
        linearLayoutCompat.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.sb.setTag(null);
        setContainedBinding(this.title);
        this.tvPlayAll.setTag(null);
        this.tvSongName.setTag(null);
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
        setViewmodel((FtSongsVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSongsBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSongsBinding
    public void setViewmodel(FtSongsVM Viewmodel) {
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
            return onChangeViewmodelCurPlaySound((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelTotolCount((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelPlayModeRes((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelPlayActionRes((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelSoundValue((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelCurPlaySound(MutableLiveData<SongInfo> ViewmodelCurPlaySound, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelTotolCount(MutableLiveData<Integer> ViewmodelTotolCount, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelPlayModeRes(MutableLiveData<Integer> ViewmodelPlayModeRes, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelPlayActionRes(MutableLiveData<Integer> ViewmodelPlayActionRes, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelSoundValue(MutableLiveData<Integer> ViewmodelSoundValue, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x007f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSongsBindingImpl.executeBindings():void");
    }
}