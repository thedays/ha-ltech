package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ItemAutomationBindingImpl extends ItemAutomationBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final CardView mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemAutomationBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ItemAutomationBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ConstraintLayout) bindings[1], (SwitchButton) bindings[4], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.layoutItemBg.setTag(null);
        CardView cardView = (CardView) bindings[0];
        this.mboundView0 = cardView;
        cardView.setTag(null);
        this.sb.setTag(null);
        this.tvModeName.setTag(null);
        this.tvModeType.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
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
            setItem((Automation) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (4 == variableId) {
            setBgRes((Integer) variable);
            return true;
        }
        if (86 == variableId) {
            setTypeBg((Integer) variable);
            return true;
        }
        if (85 == variableId) {
            setType((String) variable);
            return true;
        }
        if (8 != variableId) {
            return false;
        }
        setCheckedChangeListener((SwitchButton.OnCheckedChangeListener) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemAutomationBinding
    public void setItem(Automation Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemAutomationBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemAutomationBinding
    public void setBgRes(Integer BgRes) {
        this.mBgRes = BgRes;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemAutomationBinding
    public void setTypeBg(Integer TypeBg) {
        this.mTypeBg = TypeBg;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(86);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemAutomationBinding
    public void setType(String Type) {
        this.mType = Type;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(85);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemAutomationBinding
    public void setCheckedChangeListener(SwitchButton.OnCheckedChangeListener CheckedChangeListener) {
        this.mCheckedChangeListener = CheckedChangeListener;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        boolean z;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Automation automation = this.mItem;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        Integer num = this.mBgRes;
        Integer num2 = this.mTypeBg;
        String str2 = this.mType;
        SwitchButton.OnCheckedChangeListener onCheckedChangeListener = this.mCheckedChangeListener;
        long j2 = 65 & j;
        if (j2 == 0 || automation == null) {
            str = null;
            z = false;
        } else {
            z = automation.isEnable();
            str = automation.getName();
        }
        long j3 = 66 & j;
        long j4 = 68 & j;
        int safeUnbox = j4 != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        long j5 = 72 & j;
        int safeUnbox2 = j5 != 0 ? ViewDataBinding.safeUnbox(num2) : 0;
        long j6 = 80 & j;
        long j7 = 96 & j;
        if (j4 != 0) {
            ViewAdapter.setBackground(this.layoutItemBg, safeUnbox);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand, false);
        }
        if (j2 != 0) {
            ViewAdapter.setChecked(this.sb, z);
            TextViewBindingAdapter.setText(this.tvModeName, str);
        }
        if (j7 != 0) {
            ViewAdapter.setCheckdChangeListener(this.sb, onCheckedChangeListener);
        }
        if ((j & 64) != 0) {
            ViewAdapter.setTextBold(this.tvModeName, true);
            ViewAdapter.setTextBold(this.tvModeType, true);
        }
        if (j5 != 0) {
            ViewAdapter.setBackground(this.tvModeType, safeUnbox2);
        }
        if (j6 != 0) {
            TextViewBindingAdapter.setText(this.tvModeType, str2);
        }
    }
}