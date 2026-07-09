package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public class ActEditInstructCmdBindingImpl extends ActEditInstructCmdBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView4;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(21);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.et_name, 9);
        sparseIntArray.put(R.id.iv_name_go, 10);
        sparseIntArray.put(R.id.layout_choose, 11);
        sparseIntArray.put(R.id.radio_input, 12);
        sparseIntArray.put(R.id.radio_library, 13);
        sparseIntArray.put(R.id.radio_learn, 14);
        sparseIntArray.put(R.id.layout_data_format, 15);
        sparseIntArray.put(R.id.radio_hex, 16);
        sparseIntArray.put(R.id.radio_ascii, 17);
        sparseIntArray.put(R.id.tv_instruct, 18);
        sparseIntArray.put(R.id.iv_instruct_go, 19);
        sparseIntArray.put(R.id.layout_bottom, 20);
    }

    public ActEditInstructCmdBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }

    private ActEditInstructCmdBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[9], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[10], (LinearLayout) bindings[20], (RelativeLayout) bindings[11], (RelativeLayout) bindings[15], (RelativeLayout) bindings[5], (RelativeLayout) bindings[1], (RadioImageTextView) bindings[17], (RadioImageTextView) bindings[16], (RadioImageTextView) bindings[12], (RadioImageTextView) bindings[14], (RadioImageTextView) bindings[13], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.layoutInput.setTag(null);
        this.layoutName.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView;
        appCompatTextView.setTag(null);
        setContainedBinding(this.title);
        this.tvBottom.setTag(null);
        this.tvInput.setTag(null);
        this.tvInputType.setTag(null);
        this.tvName.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ActEditInstructCmdBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditInstructCmdBinding
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
            ViewAdapter.onClickCommand(this.layoutInput, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutName, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvBottom, bindingCommand, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView4, true);
            ViewAdapter.setTextBold(this.tvBottom, true);
            ViewAdapter.setTextBold(this.tvInput, true);
            ViewAdapter.setTextBold(this.tvInputType, true);
            ViewAdapter.setTextBold(this.tvName, true);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}