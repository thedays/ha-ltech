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
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSetVM;
import com.ltech.smarthome.ui.item.SelectItem;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ItemSelectDiyBindingImpl extends ItemSelectDiyBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final RelativeLayout mboundView1;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_brt, 5);
        sparseIntArray.put(R.id.sb_brt, 6);
    }

    public ItemSelectDiyBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ItemSelectDiyBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[4], (LightBrtBar) bindings[6], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivSelect.setTag(null);
        this.layoutChangeCurtainOpenPercent.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[1];
        this.mboundView1 = relativeLayout;
        relativeLayout.setTag(null);
        this.tvName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (40 == variableId) {
            setItem((SelectItem) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActBleMotorModeSetVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectDiyBinding
    public void setItem(SelectItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectDiyBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectDiyBinding
    public void setViewmodel(ActBleMotorModeSetVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeItemSelect((LiveData) object, fieldId);
    }

    private boolean onChangeItemSelect(LiveData<Boolean> ItemSelect, int fieldId) {
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
        String str;
        BindingCommand bindingCommand;
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SelectItem selectItem = this.mItem;
        ActBleMotorModeSetVM actBleMotorModeSetVM = this.mViewmodel;
        long j2 = j & 19;
        BindingCommand<View> bindingCommand2 = null;
        if (j2 != 0) {
            LiveData<Boolean> isSelect = selectItem != null ? selectItem.isSelect() : null;
            updateLiveDataRegistration(0, isSelect);
            boolean safeUnbox = ViewDataBinding.safeUnbox(isSelect != null ? isSelect.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 64L : 32L;
            }
            i = safeUnbox ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default;
            if ((j & 18) == 0 || selectItem == null) {
                str = null;
                bindingCommand = null;
            } else {
                bindingCommand = selectItem.getAction();
                str = selectItem.getName();
            }
        } else {
            str = null;
            bindingCommand = null;
            i = 0;
        }
        long j3 = 24 & j;
        if (j3 != 0 && actBleMotorModeSetVM != null) {
            bindingCommand2 = actBleMotorModeSetVM.viewClick;
        }
        if ((19 & j) != 0) {
            ViewAdapter.setBackground(this.ivSelect, i);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.layoutChangeCurtainOpenPercent, bindingCommand2, false);
        }
        if ((j & 18) != 0) {
            ViewAdapter.onClickCommand(this.mboundView1, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvName, str);
        }
    }
}