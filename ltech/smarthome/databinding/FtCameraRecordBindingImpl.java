package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.videogo.widget.PinnedHeaderListView;

/* loaded from: classes3.dex */
public class FtCameraRecordBindingImpl extends FtCameraRecordBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView4;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.title, 5);
        sparseIntArray.put(R.id.layout_load, 6);
        sparseIntArray.put(R.id.listview_line, 7);
        sparseIntArray.put(R.id.list_record, 8);
        sparseIntArray.put(R.id.layout_empty, 9);
        sparseIntArray.put(R.id.iv_no_sdcard, 10);
    }

    public FtCameraRecordBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private FtCameraRecordBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[3], (ScrollView) bindings[9], (ConstraintLayout) bindings[6], (PinnedHeaderListView) bindings[8], (RelativeLayout) bindings[7], (LinearLayout) bindings[5], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivLeft.setTag(null);
        this.ivRight.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.tvDate.setTag(null);
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (20 != variableId) {
            return false;
        }
        setDate((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtCameraRecordBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtCameraRecordBinding
    public void setDate(String Date) {
        this.mDate = Date;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(20);
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
        String str = this.mDate;
        long j2 = 6 & j;
        if ((5 & j) != 0) {
            ViewAdapter.onClickCommand(this.ivLeft, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivRight, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvDate, bindingCommand, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView4, true);
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.tvDate, str);
        }
    }
}