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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomTempKeyVM;

/* loaded from: classes3.dex */
public class ActIntercomTempKeyBindingImpl extends ActIntercomTempKeyBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(28);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{9}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_set_key, 10);
        sparseIntArray.put(R.id.tv_key_count, 11);
        sparseIntArray.put(R.id.iv_key_count_go, 12);
        sparseIntArray.put(R.id.tv_effective_time, 13);
        sparseIntArray.put(R.id.iv_effective_time_go, 14);
        sparseIntArray.put(R.id.tv_expiration_time, 15);
        sparseIntArray.put(R.id.iv_expiration_time_go, 16);
        sparseIntArray.put(R.id.layout_show_key, 17);
        sparseIntArray.put(R.id.tv_temp_key_tip, 18);
        sparseIntArray.put(R.id.tv_temp_key, 19);
        sparseIntArray.put(R.id.tv_temp_key_count, 20);
        sparseIntArray.put(R.id.line, 21);
        sparseIntArray.put(R.id.tv_temp_effective_time_tip, 22);
        sparseIntArray.put(R.id.tv_temp_effective_time, 23);
        sparseIntArray.put(R.id.tv_temp_expiration_time_tip, 24);
        sparseIntArray.put(R.id.tv_temp_expiration_time, 25);
        sparseIntArray.put(R.id.share_line, 26);
        sparseIntArray.put(R.id.tv_wechat, 27);
    }

    public ActIntercomTempKeyBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }

    private ActIntercomTempKeyBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[3], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[1], (LinearLayout) bindings[10], (ConstraintLayout) bindings[17], (View) bindings[21], (AppCompatTextView) bindings[26], (LayoutTitleDefaultBinding) bindings[9], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[25], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[27]);
        this.mDirtyFlags = -1L;
        this.ivMessage.setTag(null);
        this.ivWechat.setTag(null);
        this.layoutEffectiveTime.setTag(null);
        this.layoutExpirationTime.setTag(null);
        this.layoutKeyCount.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvEffectiveTimeTip.setTag(null);
        this.tvExpirationTimeTip.setTag(null);
        this.tvKeyCountTip.setTag(null);
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
        setViewmodel((ActIntercomTempKeyVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActIntercomTempKeyBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActIntercomTempKeyBinding
    public void setViewmodel(ActIntercomTempKeyVM Viewmodel) {
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
        ActIntercomTempKeyVM actIntercomTempKeyVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || actIntercomTempKeyVM == null) ? null : actIntercomTempKeyVM.viewClick;
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivMessage, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivWechat, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutEffectiveTime, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutExpirationTime, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutKeyCount, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvEffectiveTimeTip, true);
            ViewAdapter.setTextBold(this.tvExpirationTimeTip, true);
            ViewAdapter.setTextBold(this.tvKeyCountTip, true);
        }
        executeBindingsOn(this.title);
    }
}