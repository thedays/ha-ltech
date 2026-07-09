package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.GoItem;

/* loaded from: classes3.dex */
public class ItemPicBindingImpl extends ItemPicBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatImageView mboundView2;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemPicBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }

    private ItemPicBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.ivGo.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[2];
        this.mboundView2 = appCompatImageView;
        appCompatImageView.setTag(null);
        this.tvMain.setTag(null);
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
            setItem((GoItem) variable);
            return true;
        }
        if (9 == variableId) {
            setCircle((Boolean) variable);
            return true;
        }
        if (28 == variableId) {
            setErrorRes((Integer) variable);
            return true;
        }
        if (58 != variableId) {
            return false;
        }
        setPlaceHolderRes((Integer) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemPicBinding
    public void setItem(GoItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPicBinding
    public void setCircle(Boolean Circle) {
        this.mCircle = Circle;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(9);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPicBinding
    public void setErrorRes(Integer ErrorRes) {
        this.mErrorRes = ErrorRes;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPicBinding
    public void setPlaceHolderRes(Integer PlaceHolderRes) {
        this.mPlaceHolderRes = PlaceHolderRes;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(58);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        BindingCommand bindingCommand;
        String str;
        Object obj;
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        String str2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        GoItem goItem = this.mItem;
        Boolean bool = this.mCircle;
        Integer num = this.mErrorRes;
        Integer num2 = this.mPlaceHolderRes;
        BindingCommand bindingCommand2 = null;
        if ((j & 31) != 0) {
            Object imageUrl = goItem != null ? goItem.getImageUrl() : null;
            z = ViewDataBinding.safeUnbox(bool);
            i = ViewDataBinding.safeUnbox(num);
            i2 = ViewDataBinding.safeUnbox(num2);
            long j3 = j & 17;
            if (j3 != 0) {
                if (goItem != null) {
                    i3 = goItem.getGoRes();
                    bindingCommand2 = goItem.getAction();
                    str2 = goItem.getMainText();
                } else {
                    str2 = null;
                    i3 = 0;
                }
                boolean z2 = i3 != 0;
                if (j3 != 0) {
                    j |= z2 ? 64L : 32L;
                }
                i4 = z2 ? 0 : 8;
                j2 = 31;
                obj = imageUrl;
                str = str2;
                bindingCommand = bindingCommand2;
            } else {
                j2 = 31;
                bindingCommand = null;
                i3 = 0;
                i4 = 0;
                obj = imageUrl;
                str = null;
            }
        } else {
            j2 = 31;
            bindingCommand = null;
            str = null;
            obj = null;
            z = false;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        if ((17 & j) != 0) {
            this.ivGo.setVisibility(i4);
            ViewAdapter.setSrc(this.ivGo, i3);
            ViewAdapter.onClickCommand(this.mboundView0, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvMain, str);
        }
        if ((j & j2) != 0) {
            ViewAdapter.onClickCommand(this.mboundView2, obj, i2, i, z);
        }
        if ((j & 16) != 0) {
            ViewAdapter.setTextBold(this.tvMain, true);
        }
    }
}