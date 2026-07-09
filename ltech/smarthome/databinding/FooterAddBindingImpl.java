package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class FooterAddBindingImpl extends FooterAddBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public FooterAddBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds));
    }

    private FooterAddBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.tvRemoveScene.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
            setItem((String) variable);
            return true;
        }
        if (31 == variableId) {
            setGravity((Integer) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (50 == variableId) {
            setNoBackground((Boolean) variable);
            return true;
        }
        if (81 != variableId) {
            return false;
        }
        setTextColor((Integer) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FooterAddBinding
    public void setItem(String Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FooterAddBinding
    public void setGravity(Integer Gravity) {
        this.mGravity = Gravity;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(31);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FooterAddBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FooterAddBinding
    public void setNoBackground(Boolean NoBackground) {
        this.mNoBackground = NoBackground;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(50);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FooterAddBinding
    public void setTextColor(Integer TextColor) {
        this.mTextColor = TextColor;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(81);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        String str = this.mItem;
        Integer num = this.mGravity;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        Boolean bool = this.mNoBackground;
        Integer num2 = this.mTextColor;
        int safeUnbox = (j & 34) != 0 ? ViewDataBinding.safeUnbox(num) | 16 : 0;
        long j2 = j & 40;
        if (j2 != 0) {
            boolean safeUnbox2 = ViewDataBinding.safeUnbox(bool);
            if (j2 != 0) {
                j |= safeUnbox2 ? 128L : 64L;
            }
            i = safeUnbox2 ? R.color.transparent : R.drawable.selector_bg_pressed;
        } else {
            i = 0;
        }
        long j3 = 48 & j;
        int safeUnbox3 = j3 != 0 ? ViewDataBinding.safeUnbox(num2) : 0;
        if ((34 & j) != 0) {
            ViewAdapter.setTextGravity(this.tvRemoveScene, safeUnbox);
        }
        if ((j & 40) != 0) {
            ViewAdapter.setBackground(this.tvRemoveScene, i);
        }
        if ((32 & j) != 0) {
            ViewAdapter.setTextBold(this.tvRemoveScene, true);
        }
        if (j3 != 0) {
            this.tvRemoveScene.setTextColor(safeUnbox3);
        }
        if ((33 & j) != 0) {
            TextViewBindingAdapter.setText(this.tvRemoveScene, str);
        }
        if ((j & 36) != 0) {
            ViewAdapter.onClickCommand(this.tvRemoveScene, bindingCommand, false);
        }
    }
}