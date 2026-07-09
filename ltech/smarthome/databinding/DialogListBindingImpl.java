package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.GoItem;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class DialogListBindingImpl extends DialogListBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final RelativeLayout mboundView2;
    private final AppCompatTextView mboundView4;

    public DialogListBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private DialogListBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[3], (RecyclerView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.ivIcon.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[2];
        this.mboundView2 = relativeLayout;
        relativeLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.rvContent.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
            setItem((GoItem) variable);
            return true;
        }
        if (41 == variableId) {
            setItemBinding((ItemBinding) variable);
            return true;
        }
        if (42 != variableId) {
            return false;
        }
        setList((ObservableList) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogListBinding
    public void setItem(GoItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogListBinding
    public void setItemBinding(ItemBinding<GoItem> ItemBinding) {
        this.mItemBinding = ItemBinding;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(41);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogListBinding
    public void setList(ObservableList<GoItem> List) {
        updateRegistration(0, List);
        this.mList = List;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(42);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeList((ObservableList) object, fieldId);
    }

    private boolean onChangeList(ObservableList<GoItem> List, int fieldId) {
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
        int i;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        GoItem goItem = this.mItem;
        ItemBinding<GoItem> itemBinding = this.mItemBinding;
        ObservableList<GoItem> observableList = this.mList;
        long j2 = j & 10;
        BindingCommand bindingCommand = null;
        if (j2 != 0) {
            if (goItem != null) {
                i2 = goItem.getImageRes();
                bindingCommand = goItem.getAction();
                str = goItem.getMainText();
            } else {
                str = null;
                i2 = 0;
            }
            boolean z = goItem != null;
            if (j2 != 0) {
                j |= z ? 32L : 16L;
            }
            i = z ? 0 : 8;
        } else {
            str = null;
            i = 0;
            i2 = 0;
        }
        long j3 = 13 & j;
        if ((10 & j) != 0) {
            ViewAdapter.setBackground(this.ivIcon, i2);
            this.mboundView2.setVisibility(i);
            ViewAdapter.onClickCommand(this.mboundView2, bindingCommand, false);
            TextViewBindingAdapter.setText(this.mboundView4, str);
        }
        if ((j & 8) != 0) {
            ViewAdapter.setTextBold(this.mboundView4, true);
        }
        if (j3 != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.rvContent, itemBinding, observableList, null, null, null, null);
        }
    }
}