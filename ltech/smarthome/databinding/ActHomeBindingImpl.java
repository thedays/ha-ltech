package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActHomeBindingImpl extends ActHomeBinding {
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
        sparseIntArray.put(R.id.tabs, 4);
        sparseIntArray.put(R.id.viewpager, 5);
        sparseIntArray.put(R.id.group_content, 6);
        sparseIntArray.put(R.id.group_bg, 7);
        sparseIntArray.put(R.id.appCompatImageView, 8);
        sparseIntArray.put(R.id.appCompatTextView2, 9);
        sparseIntArray.put(R.id.group, 10);
    }

    public ActHomeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActHomeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[8], (AppCompatTextView) bindings[9], (AppCompatButton) bindings[1], (AppCompatButton) bindings[2], (ConstraintLayout) bindings[0], (Group) bindings[10], (ConstraintLayout) bindings[7], (Group) bindings[6], (TabLayout) bindings[4], (AppCompatTextView) bindings[3], (ViewPager2) bindings[5]);
        this.mDirtyFlags = -1L;
        this.btCreate.setTag(null);
        this.btJoin.setTag(null);
        this.content.setTag(null);
        this.tvLogout.setTag(null);
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
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActHomeBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActHomeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        if ((5 & j) != 0) {
            ViewAdapter.onClickCommand(this.btCreate, bindingCommand, false);
            ViewAdapter.onClickCommand(this.btJoin, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvLogout, bindingCommand, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvLogout, true);
        }
    }
}