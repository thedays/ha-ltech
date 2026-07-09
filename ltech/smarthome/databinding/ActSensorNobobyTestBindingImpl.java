package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public class ActSensorNobobyTestBindingImpl extends ActSensorNobobyTestBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView3;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{5}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 6);
        sparseIntArray.put(R.id.wave_view, 7);
        sparseIntArray.put(R.id.tv_state, 8);
        sparseIntArray.put(R.id.tv_test_tip, 9);
        sparseIntArray.put(R.id.layout_progress, 10);
        sparseIntArray.put(R.id.seekbar, 11);
        sparseIntArray.put(R.id.tv_finish, 12);
    }

    public ActSensorNobobyTestBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActSensorNobobyTestBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[2], (LinearLayout) bindings[6], (FrameLayout) bindings[10], (AppCompatSeekBar) bindings[11], (SpreadView) bindings[1], (LayoutTitleDefaultBinding) bindings[5], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[4], (RelativeLayout) bindings[7]);
        this.mDirtyFlags = -1L;
        this.ivSensorClose.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.spreadviewSensor.setTag(null);
        setContainedBinding(this.title);
        this.tvTestTipTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (76 != variableId) {
            return false;
        }
        setStateOn((Boolean) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSensorNobobyTestBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSensorNobobyTestBinding
    public void setStateOn(Boolean StateOn) {
        this.mStateOn = StateOn;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(76);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
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
        Boolean bool = this.mStateOn;
        long j2 = j & 6;
        int i2 = 0;
        if (j2 != 0) {
            boolean safeUnbox = ViewDataBinding.safeUnbox(bool);
            if (j2 != 0) {
                j |= safeUnbox ? 64L : 32L;
            }
            i = safeUnbox ? 0 : 8;
            if ((j & 6) != 0) {
                j |= !safeUnbox ? 16L : 8L;
            }
            if (safeUnbox) {
                i2 = 8;
            }
        } else {
            i = 0;
        }
        if ((6 & j) != 0) {
            this.ivSensorClose.setVisibility(i2);
            this.spreadviewSensor.setVisibility(i);
        }
        if ((4 & j) != 0) {
            ViewAdapter.setTextBold(this.mboundView3, true);
            ViewAdapter.setTextBold(this.tvTestTipTitle, true);
        }
        if ((j & 5) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}