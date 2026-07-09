package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.push.AppNotice;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ItemMessageNoticeBindingImpl extends ItemMessageNoticeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.circleImageView2, 3);
        sparseIntArray.put(R.id.line_bottom, 4);
    }

    public ItemMessageNoticeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ItemMessageNoticeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CircleImageView) bindings[3], (ConstraintLayout) bindings[0], (View) bindings[4], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.layoutItemBg.setTag(null);
        this.tvContent.setTag(null);
        this.tvCreateTime.setTag(null);
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
        if (37 == variableId) {
            setIconRes((Integer) variable);
            return true;
        }
        if (15 == variableId) {
            setCreateTime((String) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (1 == variableId) {
            setContent((String) variable);
            return true;
        }
        if (3 == variableId) {
            setAppNotice((AppNotice) variable);
            return true;
        }
        if (81 != variableId) {
            return false;
        }
        setTextColor((Integer) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemMessageNoticeBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
    }

    @Override // com.ltech.smarthome.databinding.ItemMessageNoticeBinding
    public void setCreateTime(String CreateTime) {
        this.mCreateTime = CreateTime;
    }

    @Override // com.ltech.smarthome.databinding.ItemMessageNoticeBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMessageNoticeBinding
    public void setContent(String Content) {
        this.mContent = Content;
    }

    @Override // com.ltech.smarthome.databinding.ItemMessageNoticeBinding
    public void setAppNotice(AppNotice AppNotice) {
        this.mAppNotice = AppNotice;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(3);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMessageNoticeBinding
    public void setTextColor(Integer TextColor) {
        this.mTextColor = TextColor;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(81);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String str2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        AppNotice appNotice = this.mAppNotice;
        Integer num = this.mTextColor;
        long j2 = 68 & j;
        long j3 = 80 & j;
        if (j3 == 0 || appNotice == null) {
            str = null;
            str2 = null;
        } else {
            str = appNotice.getContent();
            str2 = appNotice.getCreateTime();
        }
        long j4 = 96 & j;
        int safeUnbox = j4 != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand, false);
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.tvContent, str);
            TextViewBindingAdapter.setText(this.tvCreateTime, str2);
        }
        if (j4 != 0) {
            this.tvContent.setTextColor(safeUnbox);
        }
        if ((j & 64) != 0) {
            ViewAdapter.setTextBold(this.tvCreateTime, true);
        }
    }
}