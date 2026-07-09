package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.ui.device.musicplayer.FtSongsVM;

/* loaded from: classes3.dex */
public class FtSongsBindingImpl extends FtSongsBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayoutCompat mboundView0;
    private final AppCompatTextView mboundView10;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.v_title_bg, 11);
        sparseIntArray.put(R.id.recycler_view, 12);
        sparseIntArray.put(R.id.layout_control, 13);
        sparseIntArray.put(R.id.layout_play, 14);
    }

    public FtSongsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private FtSongsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[6], (ConstraintLayout) bindings[13], (LinearLayoutCompat) bindings[14], (RecyclerView) bindings[12], (AppCompatSeekBar) bindings[9], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[1], (View) bindings[11]);
        this.mDirtyFlags = -1L;
        this.ivLast.setTag(null);
        this.ivMode.setTag(null);
        this.ivMute.setTag(null);
        this.ivNext.setTag(null);
        this.ivPlay.setTag(null);
        LinearLayoutCompat linearLayoutCompat = (LinearLayoutCompat) bindings[0];
        this.mboundView0 = linearLayoutCompat;
        linearLayoutCompat.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.sb.setTag(null);
        this.tvPlayAll.setTag(null);
        this.tvSongName.setTag(null);
        this.tvSync.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
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
        if (92 != variableId) {
            return false;
        }
        setViewmodel((FtSongsVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtSongsBinding
    public void setViewmodel(FtSongsVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0078  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.FtSongsBindingImpl.executeBindings():void");
    }
}