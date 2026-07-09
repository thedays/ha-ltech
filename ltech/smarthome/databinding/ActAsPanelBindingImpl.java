package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM;
import com.ltech.smarthome.view.AnnularColorPickView;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ActAsPanelBindingImpl extends ActAsPanelBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(27);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{9}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 10);
        sparseIntArray.put(R.id.annularColorPickView, 11);
        sparseIntArray.put(R.id.sb_brt, 12);
        sparseIntArray.put(R.id.tv_brt, 13);
        sparseIntArray.put(R.id.tv_brt_tip, 14);
        sparseIntArray.put(R.id.tv_color_tip, 15);
        sparseIntArray.put(R.id.csb_color_bar, 16);
        sparseIntArray.put(R.id.tv_color, 17);
        sparseIntArray.put(R.id.civ_rgb, 18);
        sparseIntArray.put(R.id.civ_w, 19);
        sparseIntArray.put(R.id.group_brt, 20);
        sparseIntArray.put(R.id.group_wy, 21);
        sparseIntArray.put(R.id.group_color_top, 22);
        sparseIntArray.put(R.id.constraintLayout, 23);
        sparseIntArray.put(R.id.rv_scene, 24);
        sparseIntArray.put(R.id.rv_related_info, 25);
        sparseIntArray.put(R.id.v_open, 26);
    }

    public ActAsPanelBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }

    private ActAsPanelBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AnnularColorPickView) bindings[11], (AppCompatTextView) bindings[5], (CircleImageView) bindings[18], (CircleImageView) bindings[19], (ConstraintLayout) bindings[23], (ColorSeekBar) bindings[16], (Group) bindings[20], (Group) bindings[22], (Group) bindings[8], (Group) bindings[21], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[10], (RecyclerView) bindings[25], (RecyclerView) bindings[24], (LightBrtBar) bindings[12], (LayoutTitleDefaultBinding) bindings[9], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[15], (View) bindings[26]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView36.setTag(null);
        this.groupOpen.setTag(null);
        this.ivMode.setTag(null);
        this.ivOnOff.setTag(null);
        this.ivOpen.setTag(null);
        this.ivRgb.setTag(null);
        this.ivScene.setTag(null);
        this.ivW.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        setViewmodel((ActAsPanelVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAsPanelBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAsPanelBinding
    public void setViewmodel(ActAsPanelVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
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
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelStateOn((SingleLiveEvent) object, fieldId);
    }

    private boolean onChangeViewmodelStateOn(SingleLiveEvent<Boolean> ViewmodelStateOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
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
        ActAsPanelVM actAsPanelVM = this.mViewmodel;
        long j2 = j & 13;
        BindingCommand<View> bindingCommand = null;
        if (j2 != 0) {
            BindingCommand<View> bindingCommand2 = ((j & 12) == 0 || actAsPanelVM == null) ? null : actAsPanelVM.viewClick;
            SingleLiveEvent<Boolean> singleLiveEvent = actAsPanelVM != null ? actAsPanelVM.stateOn : null;
            updateLiveDataRegistration(0, singleLiveEvent);
            boolean safeUnbox = ViewDataBinding.safeUnbox(singleLiveEvent != null ? singleLiveEvent.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 32L : 16L;
            }
            i = safeUnbox ? 8 : 0;
            bindingCommand = bindingCommand2;
        } else {
            i = 0;
        }
        if ((8 & j) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView36, true);
        }
        if ((13 & j) != 0) {
            this.groupOpen.setVisibility(i);
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.ivMode, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivOnOff, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivOpen, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivRgb, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivScene, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivW, bindingCommand, false);
        }
        if ((j & 10) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}