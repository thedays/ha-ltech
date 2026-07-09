package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.videogo.widget.HandleView;

/* loaded from: classes3.dex */
public class ActCameraPlayBackBindingImpl extends ActCameraPlayBackBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.linearLayout2, 14);
        sparseIntArray.put(R.id.surface_view, 15);
        sparseIntArray.put(R.id.tv_record_time, 16);
        sparseIntArray.put(R.id.tv_speed, 17);
        sparseIntArray.put(R.id.tv_loading, 18);
        sparseIntArray.put(R.id.layout_controller, 19);
        sparseIntArray.put(R.id.layout_controller2, 20);
        sparseIntArray.put(R.id.tv_talk_tip, 21);
        sparseIntArray.put(R.id.handle_view, 22);
        sparseIntArray.put(R.id.tv_full_record_time, 23);
        sparseIntArray.put(R.id.handle_view_full, 24);
        sparseIntArray.put(R.id.guideline4, 25);
        sparseIntArray.put(R.id.guideline3, 26);
    }

    public ActCameraPlayBackBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }

    private ActCameraPlayBackBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Guideline) bindings[26], (Guideline) bindings[25], (HandleView) bindings[22], (HandleView) bindings[24], (ImageView) bindings[2], (ImageView) bindings[12], (ImageView) bindings[10], (ImageView) bindings[9], (ImageView) bindings[11], (ImageView) bindings[6], (ImageView) bindings[4], (ImageView) bindings[13], (ImageView) bindings[3], (ImageView) bindings[5], (ImageView) bindings[7], (ConstraintLayout) bindings[19], (ConstraintLayout) bindings[20], (ConstraintLayout) bindings[8], (LinearLayout) bindings[14], (SurfaceView) bindings[15], (TextView) bindings[23], (TextView) bindings[18], (TextView) bindings[1], (TextView) bindings[16], (TextView) bindings[17], (TextView) bindings[21]);
        this.mDirtyFlags = -1L;
        this.ivFull.setTag(null);
        this.ivFullRecord.setTag(null);
        this.ivFullShot.setTag(null);
        this.ivFullSound.setTag(null);
        this.ivFullSpeak.setTag(null);
        this.ivRecord.setTag(null);
        this.ivShot.setTag(null);
        this.ivSmall.setTag(null);
        this.ivSound.setTag(null);
        this.ivSpeak.setTag(null);
        this.ivVideo.setTag(null);
        this.layoutController3.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvQuality.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActCameraPlayBackBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        if ((j & 3) != 0) {
            ViewAdapter.onClickCommand(this.ivFull, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivFullRecord, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivFullShot, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivFullSound, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivFullSpeak, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivRecord, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivShot, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSmall, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSound, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSpeak, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivVideo, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutController3, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvQuality, bindingCommand, false);
        }
    }
}