package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActSuperPanelVoiceTalkBindingImpl extends ActSuperPanelVoiceTalkBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView1;
    private final AppCompatTextView mboundView2;
    private final AppCompatImageView mboundView3;
    private final AppCompatTextView mboundView4;
    private final AppCompatTextView mboundView5;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(11);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{6}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.img_tip_1, 7);
        sparseIntArray.put(R.id.img_tip_2, 8);
        sparseIntArray.put(R.id.tv_call, 9);
        sparseIntArray.put(R.id.tv_active, 10);
    }

    public ActSuperPanelVoiceTalkBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActSuperPanelVoiceTalkBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[8], (LayoutTitleDefaultBinding) bindings[6], (AppCompatButton) bindings[10], (AppCompatButton) bindings[9]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[1];
        this.mboundView1 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[3];
        this.mboundView3 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        setContainedBinding(this.title);
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
        if (94 != variableId) {
            return false;
        }
        setVoiceVisible((Boolean) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelVoiceTalkBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelVoiceTalkBinding
    public void setVoiceVisible(Boolean VoiceVisible) {
        this.mVoiceVisible = VoiceVisible;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(94);
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
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        Boolean bool = this.mVoiceVisible;
        long j2 = j & 6;
        int i = 0;
        if (j2 != 0) {
            boolean safeUnbox = ViewDataBinding.safeUnbox(bool);
            if (j2 != 0) {
                j |= safeUnbox ? 16L : 8L;
            }
            if (!safeUnbox) {
                i = 8;
            }
        }
        if ((4 & j) != 0) {
            ViewAdapter.setTextBold(this.mboundView1, true);
            ViewAdapter.setTextBold(this.mboundView2, true);
            ViewAdapter.setTextBold(this.mboundView4, true);
        }
        if ((6 & j) != 0) {
            this.mboundView3.setVisibility(i);
            this.mboundView4.setVisibility(i);
            this.mboundView5.setVisibility(i);
        }
        if ((j & 5) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}