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
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM;
import com.ltech.smarthome.view.AnnularColorPickView2;
import com.ltech.smarthome.view.TextSeekBarView;

/* loaded from: classes3.dex */
public class ActEurPanelBindingImpl extends ActEurPanelBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(34);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{15}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 16);
        sparseIntArray.put(R.id.annularColorPickView, 17);
        sparseIntArray.put(R.id.annularColorPickView_transparent_view, 18);
        sparseIntArray.put(R.id.layout_seekbar, 19);
        sparseIntArray.put(R.id.seekbar_brt, 20);
        sparseIntArray.put(R.id.seekbar_w, 21);
        sparseIntArray.put(R.id.seekbar_cw, 22);
        sparseIntArray.put(R.id.view6, 23);
        sparseIntArray.put(R.id.layout_bottom, 24);
        sparseIntArray.put(R.id.layout_single_zone, 25);
        sparseIntArray.put(R.id.iv_bind_end, 26);
        sparseIntArray.put(R.id.single_zone_line, 27);
        sparseIntArray.put(R.id.layout_multi_zone, 28);
        sparseIntArray.put(R.id.rv_multi_bind, 29);
        sparseIntArray.put(R.id.rv_multi_transparent_view, 30);
        sparseIntArray.put(R.id.layout_scene_bind, 31);
        sparseIntArray.put(R.id.rv_scene, 32);
        sparseIntArray.put(R.id.rv_scene_transparent_view, 33);
    }

    public ActEurPanelBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 34, sIncludes, sViewsWithIds));
    }

    private ActEurPanelBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AnnularColorPickView2) bindings[17], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[26], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[1], (LinearLayout) bindings[24], (LinearLayout) bindings[10], (LinearLayout) bindings[7], (LinearLayout) bindings[8], (LinearLayout) bindings[9], (ConstraintLayout) bindings[16], (ConstraintLayout) bindings[28], (ConstraintLayout) bindings[31], (LinearLayout) bindings[19], (ConstraintLayout) bindings[25], (RecyclerView) bindings[29], (View) bindings[30], (RecyclerView) bindings[32], (View) bindings[33], (TextSeekBarView) bindings[20], (TextSeekBarView) bindings[22], (TextSeekBarView) bindings[21], (View) bindings[27], (LayoutTitleDefaultBinding) bindings[15], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[5], (View) bindings[23]);
        this.mDirtyFlags = -1L;
        this.ivDoubt.setTag(null);
        this.ivFunctionDoubt.setTag(null);
        this.ivMode.setTag(null);
        this.ivRgb.setTag(null);
        this.ivSwitch.setTag(null);
        this.layoutBrt100.setTag(null);
        this.layoutBrt25.setTag(null);
        this.layoutBrt50.setTag(null);
        this.layoutBrt75.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvBindTitle.setTag(null);
        this.tvMultiBindTitle.setTag(null);
        this.tvQuickBrt.setTag(null);
        this.tvScene.setTag(null);
        this.tvWaitBind.setTag(null);
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
        setViewmodel((ActEurPanelVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActEurPanelBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEurPanelBinding
    public void setViewmodel(ActEurPanelVM Viewmodel) {
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
        ActEurPanelVM actEurPanelVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || actEurPanelVM == null) ? null : actEurPanelVM.viewClick;
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivDoubt, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivFunctionDoubt, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivMode, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivRgb, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSwitch, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBrt100, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBrt25, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBrt50, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBrt75, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvWaitBind, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvBindTitle, true);
            ViewAdapter.setTextBold(this.tvMultiBindTitle, true);
            ViewAdapter.setTextBold(this.tvQuickBrt, true);
            ViewAdapter.setTextBold(this.tvScene, true);
        }
        executeBindingsOn(this.title);
    }
}