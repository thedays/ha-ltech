package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public class LayoutTitleTransparentBindingImpl extends LayoutTitleTransparentBinding {
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
        sparseIntArray.put(R.id.layout_toolbar, 7);
        sparseIntArray.put(R.id.iv_doubt, 8);
        sparseIntArray.put(R.id.iv_search, 9);
        sparseIntArray.put(R.id.layout_search_view, 10);
        sparseIntArray.put(R.id.search_SpreadView, 11);
        sparseIntArray.put(R.id.search_image, 12);
        sparseIntArray.put(R.id.tv_num, 13);
        sparseIntArray.put(R.id.gray_line, 14);
    }

    public LayoutTitleTransparentBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private LayoutTitleTransparentBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[4], (View) bindings[14], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[9], (RelativeLayout) bindings[10], (ConstraintLayout) bindings[7], (AppCompatImageView) bindings[12], (SpreadView) bindings[11], (Toolbar) bindings[0], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView8.setTag(null);
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        this.toolbar.setTag(null);
        this.tvEdit.setTag(null);
        this.tvSubTitle.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.LayoutTitleTransparentBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        long j3;
        BindingCommand bindingCommand;
        String str;
        BindingCommand bindingCommand2;
        BindingCommand bindingCommand3;
        String str2;
        String str3;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        String str4;
        String str5;
        String str6;
        BindingCommand bindingCommand4;
        BindingCommand bindingCommand5;
        BindingCommand bindingCommand6;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        long j4 = j & 3;
        String str7 = null;
        if (j4 != 0) {
            if (titleDefault != null) {
                String editString = titleDefault.getEditString();
                i4 = titleDefault.getBackImageRes();
                String subTitle = titleDefault.getSubTitle();
                str5 = titleDefault.getTitle();
                str6 = titleDefault.getBackString();
                bindingCommand4 = titleDefault.getTitleAction();
                bindingCommand5 = titleDefault.getBackAction();
                bindingCommand6 = titleDefault.getEditAction();
                i = titleDefault.getEditImageRes();
                str7 = subTitle;
                str4 = editString;
            } else {
                str4 = null;
                str5 = null;
                str6 = null;
                bindingCommand4 = null;
                bindingCommand5 = null;
                bindingCommand6 = null;
                i = 0;
                i4 = 0;
            }
            boolean z = i4 != 0;
            boolean isEmpty = StringUtils.isEmpty(str7);
            boolean z2 = i != 0;
            if (j4 != 0) {
                j |= z ? 8L : 4L;
            }
            if ((j & 3) != 0) {
                j |= isEmpty ? 32L : 16L;
            }
            if ((j & 3) != 0) {
                j |= z2 ? 128L : 64L;
            }
            int i6 = z ? 0 : 8;
            String str8 = str6;
            str3 = str7;
            str7 = str8;
            i5 = isEmpty ? 8 : 0;
            String str9 = str5;
            str2 = str4;
            bindingCommand2 = bindingCommand5;
            i2 = z2 ? 0 : 8;
            bindingCommand3 = bindingCommand6;
            i3 = i6;
            j3 = 3;
            str = str9;
            bindingCommand = bindingCommand4;
            j2 = 0;
        } else {
            j2 = 0;
            j3 = 3;
            bindingCommand = null;
            str = null;
            bindingCommand2 = null;
            bindingCommand3 = null;
            str2 = null;
            str3 = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
        }
        if ((j & j3) != j2) {
            TextViewBindingAdapter.setText(this.appCompatTextView8, str7);
            ViewAdapter.onClickCommand(this.appCompatTextView8, bindingCommand2, false);
            ViewAdapter.setSrc(this.ivBack, i4);
            this.ivBack.setVisibility(i3);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand2, false);
            ViewAdapter.setSrc(this.ivEdit, i);
            this.ivEdit.setVisibility(i2);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand3, false);
            TextViewBindingAdapter.setText(this.tvEdit, str2);
            ViewAdapter.onClickCommand(this.tvEdit, bindingCommand3, false);
            TextViewBindingAdapter.setText(this.tvSubTitle, str3);
            this.tvSubTitle.setVisibility(i5);
            ViewAdapter.onClickCommand(this.tvSubTitle, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvTitle, str);
            ViewAdapter.onClickCommand(this.tvTitle, bindingCommand, false);
        }
        if ((j & 2) != j2) {
            ViewAdapter.setTextBold(this.appCompatTextView8, true);
            ViewAdapter.setTextBold(this.tvEdit, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}