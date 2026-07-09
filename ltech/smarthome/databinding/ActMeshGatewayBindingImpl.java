package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewayVM;

/* loaded from: classes3.dex */
public class ActMeshGatewayBindingImpl extends ActMeshGatewayBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{3}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.v_top, 4);
        sparseIntArray.put(R.id.tv_num, 5);
        sparseIntArray.put(R.id.layout_load, 6);
        sparseIntArray.put(R.id.rv_content, 7);
    }

    public ActMeshGatewayBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActMeshGatewayBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[1], (AppCompatImageView) bindings[2], (LinearLayout) bindings[6], (RecyclerView) bindings[7], (LayoutTitleDefaultBinding) bindings[3], (AppCompatTextView) bindings[5], (AppCompatImageView) bindings[4]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView38.setTag(null);
        this.ivAdd.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActMeshGatewayVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActMeshGatewayBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActMeshGatewayBinding
    public void setViewmodel(ActMeshGatewayVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
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
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActMeshGatewayVM actMeshGatewayVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || actMeshGatewayVM == null) ? null : actMeshGatewayVM.clickCommand;
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView38, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivAdd, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}