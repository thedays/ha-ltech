package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public class ActRemoteBatteryBindingImpl extends ActRemoteBatteryBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView4;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(22);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{10}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 11);
        sparseIntArray.put(R.id.iv_device_pic, 12);
        sparseIntArray.put(R.id.scrollView, 13);
        sparseIntArray.put(R.id.layout_panel, 14);
        sparseIntArray.put(R.id.iv_add_icon, 15);
        sparseIntArray.put(R.id.rv_panel, 16);
        sparseIntArray.put(R.id.layout_scene, 17);
        sparseIntArray.put(R.id.iv_action, 18);
        sparseIntArray.put(R.id.rv_scene, 19);
        sparseIntArray.put(R.id.layout_zone, 20);
        sparseIntArray.put(R.id.rv_multi_bind, 21);
    }

    public ActRemoteBatteryBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }

    private ActRemoteBatteryBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (RelativeLayout) bindings[3], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[1], (ConstraintLayout) bindings[11], (LinearLayout) bindings[6], (ConstraintLayout) bindings[14], (ConstraintLayout) bindings[17], (ConstraintLayout) bindings[20], (RecyclerView) bindings[21], (SwipeRecyclerView) bindings[16], (RecyclerView) bindings[19], (NestedScrollView) bindings[13], (LayoutTitleDefaultBinding) bindings[10], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[5]);
        this.mDirtyFlags = -1L;
        this.addPanel.setTag(null);
        this.ivDoubt.setTag(null);
        this.ivMode.setTag(null);
        this.layoutEditAction.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView;
        appCompatTextView.setTag(null);
        setContainedBinding(this.title);
        this.tvAction.setTag(null);
        this.tvMultiBindTitle.setTag(null);
        this.tvPanel.setTag(null);
        this.tvScene.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ActRemoteBatteryBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActRemoteBatteryBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
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
        BindingCommand<View> bindingCommand = this.mClickCommand;
        TitleDefault titleDefault = this.mTitle;
        long j2 = 6 & j;
        if ((5 & j) != 0) {
            ViewAdapter.onClickCommand(this.addPanel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivDoubt, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivMode, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutEditAction, bindingCommand, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView4, true);
            ViewAdapter.setTextBold(this.tvAction, true);
            ViewAdapter.setTextBold(this.tvMultiBindTitle, true);
            ViewAdapter.setTextBold(this.tvPanel, true);
            ViewAdapter.setTextBold(this.tvScene, true);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}