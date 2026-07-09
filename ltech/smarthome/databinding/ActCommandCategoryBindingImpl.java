package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActCommandCategoryBindingImpl extends ActCommandCategoryBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView2;
    private final AppCompatTextView mboundView5;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(11);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_name, 9);
        sparseIntArray.put(R.id.card_color, 10);
    }

    public ActCommandCategoryBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActCommandCategoryBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CardView) bindings[10], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[3], (LinearLayout) bindings[4], (LinearLayout) bindings[1], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[9]);
        this.mDirtyFlags = -1L;
        this.ivColorGo.setTag(null);
        this.ivNameGo.setTag(null);
        this.layoutSetColor.setTag(null);
        this.layoutSetName.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        setContainedBinding(this.title);
        this.tvDelete.setTag(null);
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
        if (55 == variableId) {
            setOwnerOrManager((Boolean) variable);
            return true;
        }
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

    @Override // com.ltech.smarthome.databinding.ActCommandCategoryBinding
    public void setOwnerOrManager(Boolean OwnerOrManager) {
        this.mOwnerOrManager = OwnerOrManager;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(55);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCommandCategoryBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCommandCategoryBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> L7f
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L7f
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L7f
            java.lang.Boolean r4 = r14.mOwnerOrManager
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r5 = r14.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r6 = r14.mTitle
            r7 = 9
            long r9 = r0 & r7
            r11 = 0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L2b
            boolean r4 = androidx.databinding.ViewDataBinding.safeUnbox(r4)
            if (r12 == 0) goto L25
            if (r4 == 0) goto L22
            r9 = 32
            goto L24
        L22:
            r9 = 16
        L24:
            long r0 = r0 | r9
        L25:
            if (r4 == 0) goto L28
            goto L2b
        L28:
            r4 = 8
            goto L2c
        L2b:
            r4 = 0
        L2c:
            r9 = 10
            long r9 = r9 & r0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            r9 = 12
            long r9 = r9 & r0
            int r13 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            long r7 = r7 & r0
            int r9 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r9 == 0) goto L4a
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivColorGo
            r7.setVisibility(r4)
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivNameGo
            r7.setVisibility(r4)
            androidx.appcompat.widget.AppCompatTextView r7 = r14.tvDelete
            r7.setVisibility(r4)
        L4a:
            if (r12 == 0) goto L5b
            android.widget.LinearLayout r4 = r14.layoutSetColor
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.LinearLayout r4 = r14.layoutSetName
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r4 = r14.tvDelete
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
        L5b:
            r4 = 8
            long r0 = r0 & r4
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L72
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView2
            r1 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView5
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvDelete
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
        L72:
            if (r13 == 0) goto L79
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            r0.setTitle(r6)
        L79:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            executeBindingsOn(r0)
            return
        L7f:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L7f
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActCommandCategoryBindingImpl.executeBindings():void");
    }
}