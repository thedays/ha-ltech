package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActMatterSubListBindingImpl extends ActMatterSubListBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(24);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{5}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_top_tip, 6);
        sparseIntArray.put(R.id.layout_normal, 7);
        sparseIntArray.put(R.id.layout_qrcode, 8);
        sparseIntArray.put(R.id.layout_platform, 9);
        sparseIntArray.put(R.id.line, 10);
        sparseIntArray.put(R.id.iv_go, 11);
        sparseIntArray.put(R.id.tv_platform_num, 12);
        sparseIntArray.put(R.id.iv_go1, 13);
        sparseIntArray.put(R.id.tv_device_num, 14);
        sparseIntArray.put(R.id.layout_load, 15);
        sparseIntArray.put(R.id.refreshLayout, 16);
        sparseIntArray.put(R.id.rv, 17);
        sparseIntArray.put(R.id.layout_tab, 18);
        sparseIntArray.put(R.id.layout_num, 19);
        sparseIntArray.put(R.id.tv_sync_num, 20);
        sparseIntArray.put(R.id.iv_sync, 21);
        sparseIntArray.put(R.id.fragment_container, 22);
        sparseIntArray.put(R.id.tv_sync, 23);
    }

    public ActMatterSubListBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }

    private ActMatterSubListBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (FrameLayout) bindings[22], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[13], (ImageView) bindings[21], (FrameLayout) bindings[15], (View) bindings[7], (RelativeLayout) bindings[19], (View) bindings[9], (View) bindings[8], (LinearLayout) bindings[18], (View) bindings[10], (SmartRefreshLayout) bindings[16], (RecyclerView) bindings[17], (LayoutTitleDefaultBinding) bindings[5], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[23], (TextView) bindings[20], (AppCompatTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvDevice.setTag(null);
        this.tvMain.setTag(null);
        this.tvPlatform.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ActMatterSubListBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActMatterSubListBinding
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
        long j2 = 5 & j;
        if ((6 & j) != 0) {
            this.title.setTitle(titleDefault);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.tvDevice, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvScene, bindingCommand, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvMain, true);
            ViewAdapter.setTextBold(this.tvPlatform, true);
        }
        executeBindingsOn(this.title);
    }
}