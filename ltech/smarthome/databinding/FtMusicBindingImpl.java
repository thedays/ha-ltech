package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.FtMusicVM;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public class FtMusicBindingImpl extends FtMusicBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar, 9);
        sparseIntArray.put(R.id.tv_music_name, 10);
        sparseIntArray.put(R.id.tv_music_artist, 11);
        sparseIntArray.put(R.id.iv_bg, 12);
        sparseIntArray.put(R.id.iv_music, 13);
        sparseIntArray.put(R.id.sb_music, 14);
        sparseIntArray.put(R.id.tv_music_current, 15);
        sparseIntArray.put(R.id.tv_music_total, 16);
    }

    public FtMusicBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }

    private FtMusicBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ConstraintLayout) bindings[0], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[13], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[5], (HorizontalSeekBar) bindings[14], (Toolbar) bindings[9], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.constraintlayout.setTag(null);
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        this.ivNext.setTag(null);
        this.ivPlay.setTag(null);
        this.ivPlayList.setTag(null);
        this.ivPlayMode.setTag(null);
        this.ivPrevious.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        setViewmodel((FtMusicVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtMusicBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtMusicBinding
    public void setViewmodel(FtMusicVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        BindingCommand bindingCommand;
        BindingCommand bindingCommand2;
        int i;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        FtMusicVM ftMusicVM = this.mViewmodel;
        long j2 = 5 & j;
        BindingCommand<View> bindingCommand3 = null;
        if (j2 == 0 || titleDefault == null) {
            str = null;
            bindingCommand = null;
            bindingCommand2 = null;
            i = 0;
            i2 = 0;
        } else {
            i2 = titleDefault.getBackImageRes();
            str = titleDefault.getTitle();
            bindingCommand = titleDefault.getBackAction();
            bindingCommand2 = titleDefault.getEditAction();
            i = titleDefault.getEditImageRes();
        }
        long j3 = 6 & j;
        if (j3 != 0 && ftMusicVM != null) {
            bindingCommand3 = ftMusicVM.viewClick;
        }
        if (j2 != 0) {
            ViewAdapter.setSrc(this.ivBack, i2);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand, false);
            ViewAdapter.setSrc(this.ivEdit, i);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand2, false);
            TextViewBindingAdapter.setText(this.tvTitle, str);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivNext, bindingCommand3, false);
            ViewAdapter.onClickCommand(this.ivPlay, bindingCommand3, false);
            ViewAdapter.onClickCommand(this.ivPlayList, bindingCommand3, false);
            ViewAdapter.onClickCommand(this.ivPlayMode, bindingCommand3, false);
            ViewAdapter.onClickCommand(this.ivPrevious, bindingCommand3, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}