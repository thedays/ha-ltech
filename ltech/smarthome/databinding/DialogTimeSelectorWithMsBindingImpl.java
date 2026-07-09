package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public class DialogTimeSelectorWithMsBindingImpl extends DialogTimeSelectorWithMsBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.pick_view_min, 7);
        sparseIntArray.put(R.id.pick_view_sec, 8);
        sparseIntArray.put(R.id.pick_view_ms, 9);
    }

    public DialogTimeSelectorWithMsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }

    private DialogTimeSelectorWithMsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (RecyclerView) bindings[7], (RecyclerView) bindings[9], (RecyclerView) bindings[8], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCancel.setTag(null);
        this.tvFinish.setTag(null);
        this.tvMinTip.setTag(null);
        this.tvMsTip.setTag(null);
        this.tvSecTip.setTag(null);
        this.tvTitle.setTag(null);
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
        if (66 == variableId) {
            setSecUnit((String) variable);
            return true;
        }
        if (96 == variableId) {
            setWithUnit((Boolean) variable);
            return true;
        }
        if (44 == variableId) {
            setMinUnit((String) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogTimeSelectorWithMsBinding
    public void setSecUnit(String SecUnit) {
        this.mSecUnit = SecUnit;
    }

    @Override // com.ltech.smarthome.databinding.DialogTimeSelectorWithMsBinding
    public void setWithUnit(Boolean WithUnit) {
        this.mWithUnit = WithUnit;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(96);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogTimeSelectorWithMsBinding
    public void setMinUnit(String MinUnit) {
        this.mMinUnit = MinUnit;
    }

    @Override // com.ltech.smarthome.databinding.DialogTimeSelectorWithMsBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r13 = this;
            monitor-enter(r13)
            long r0 = r13.mDirtyFlags     // Catch: java.lang.Throwable -> L67
            r2 = 0
            r13.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L67
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L67
            java.lang.Boolean r4 = r13.mWithUnit
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r5 = r13.mClickCommand
            r6 = 18
            long r8 = r0 & r6
            r10 = 0
            int r11 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r11 == 0) goto L29
            boolean r4 = androidx.databinding.ViewDataBinding.safeUnbox(r4)
            if (r11 == 0) goto L23
            if (r4 == 0) goto L20
            r8 = 64
            goto L22
        L20:
            r8 = 32
        L22:
            long r0 = r0 | r8
        L23:
            if (r4 == 0) goto L26
            goto L29
        L26:
            r4 = 8
            goto L2a
        L29:
            r4 = 0
        L2a:
            r8 = 24
            long r8 = r8 & r0
            int r11 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            r8 = 16
            long r8 = r8 & r0
            int r12 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r12 == 0) goto L46
            androidx.appcompat.widget.AppCompatTextView r8 = r13.tvCancel
            r9 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r8, r9)
            androidx.appcompat.widget.AppCompatTextView r8 = r13.tvFinish
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r8, r9)
            androidx.appcompat.widget.AppCompatTextView r8 = r13.tvTitle
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r8, r9)
        L46:
            if (r11 == 0) goto L52
            androidx.appcompat.widget.AppCompatTextView r8 = r13.tvCancel
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r8, r5, r10)
            androidx.appcompat.widget.AppCompatTextView r8 = r13.tvFinish
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r8, r5, r10)
        L52:
            long r0 = r0 & r6
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 == 0) goto L66
            androidx.appcompat.widget.AppCompatTextView r0 = r13.tvMinTip
            r0.setVisibility(r4)
            androidx.appcompat.widget.AppCompatTextView r0 = r13.tvMsTip
            r0.setVisibility(r4)
            androidx.appcompat.widget.AppCompatTextView r0 = r13.tvSecTip
            r0.setVisibility(r4)
        L66:
            return
        L67:
            r0 = move-exception
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L67
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.DialogTimeSelectorWithMsBindingImpl.executeBindings():void");
    }
}