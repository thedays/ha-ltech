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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class FtPageScreenPanelDetailBindingImpl extends FtPageScreenPanelDetailBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final NestedScrollView mboundView0;
    private final AppCompatTextView mboundView1;
    private final TextView mboundView3;
    private final AppCompatTextView mboundView4;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout1, 5);
        sparseIntArray.put(R.id.layout_show, 6);
        sparseIntArray.put(R.id.sb, 7);
        sparseIntArray.put(R.id.layout_time, 8);
        sparseIntArray.put(R.id.tv_show_time, 9);
        sparseIntArray.put(R.id.iv_start_time_go, 10);
        sparseIntArray.put(R.id.layout_custom, 11);
        sparseIntArray.put(R.id.layout2, 12);
        sparseIntArray.put(R.id.rv, 13);
    }

    public FtPageScreenPanelDetailBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private FtPageScreenPanelDetailBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[10], (LinearLayout) bindings[5], (LinearLayout) bindings[12], (RelativeLayout) bindings[11], (RelativeLayout) bindings[6], (ConstraintLayout) bindings[8], (RecyclerView) bindings[13], (SwitchButton) bindings[7], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        NestedScrollView nestedScrollView = (NestedScrollView) bindings[0];
        this.mboundView0 = nestedScrollView;
        nestedScrollView.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[1];
        this.mboundView1 = appCompatTextView;
        appCompatTextView.setTag(null);
        TextView textView = (TextView) bindings[3];
        this.mboundView3 = textView;
        textView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        this.tvTimeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
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
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        if ((j & 1) != 0) {
            ViewAdapter.setTextBold(this.mboundView1, true);
            ViewAdapter.setTextBold(this.mboundView3, true);
            ViewAdapter.setTextBold(this.mboundView4, true);
            ViewAdapter.setTextBold(this.tvTimeTip, true);
        }
    }
}