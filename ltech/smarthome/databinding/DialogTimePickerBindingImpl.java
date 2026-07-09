package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.MyTimePickerView;

/* loaded from: classes3.dex */
public class DialogTimePickerBindingImpl extends DialogTimePickerBinding {
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
        sparseIntArray.put(R.id.pick_view_min, 6);
        sparseIntArray.put(R.id.picker_view_sec, 7);
    }

    public DialogTimePickerBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private DialogTimePickerBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (MyTimePickerView) bindings[6], (MyTimePickerView) bindings[7], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCancel.setTag(null);
        this.tvFinish.setTag(null);
        this.tvMinTip.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogTimePickerBinding
    public void setSecUnit(String SecUnit) {
        this.mSecUnit = SecUnit;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(66);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogTimePickerBinding
    public void setWithUnit(Boolean WithUnit) {
        this.mWithUnit = WithUnit;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(96);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogTimePickerBinding
    public void setMinUnit(String MinUnit) {
        this.mMinUnit = MinUnit;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(44);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogTimePickerBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r17 = this;
            r1 = r17
            monitor-enter(r17)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> L81
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> L81
            monitor-exit(r17)     // Catch: java.lang.Throwable -> L81
            java.lang.String r0 = r1.mSecUnit
            java.lang.Boolean r6 = r1.mWithUnit
            java.lang.String r7 = r1.mMinUnit
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r8 = r1.mClickCommand
            r9 = 18
            long r11 = r2 & r9
            r13 = 0
            int r14 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r14 == 0) goto L2f
            boolean r6 = androidx.databinding.ViewDataBinding.safeUnbox(r6)
            if (r14 == 0) goto L29
            if (r6 == 0) goto L26
            r11 = 64
            goto L28
        L26:
            r11 = 32
        L28:
            long r2 = r2 | r11
        L29:
            if (r6 == 0) goto L2c
            goto L2f
        L2c:
            r6 = 8
            goto L30
        L2f:
            r6 = 0
        L30:
            r11 = 20
            long r11 = r11 & r2
            int r14 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            r11 = 24
            long r11 = r11 & r2
            int r15 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            r11 = 16
            long r11 = r11 & r2
            int r16 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r16 == 0) goto L51
            androidx.appcompat.widget.AppCompatTextView r11 = r1.tvCancel
            r12 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r11, r12)
            androidx.appcompat.widget.AppCompatTextView r11 = r1.tvFinish
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r11, r12)
            androidx.appcompat.widget.AppCompatTextView r11 = r1.tvTitle
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r11, r12)
        L51:
            if (r15 == 0) goto L5d
            androidx.appcompat.widget.AppCompatTextView r11 = r1.tvCancel
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r11, r8, r13)
            androidx.appcompat.widget.AppCompatTextView r11 = r1.tvFinish
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r11, r8, r13)
        L5d:
            if (r14 == 0) goto L64
            androidx.appcompat.widget.AppCompatTextView r8 = r1.tvMinTip
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r8, r7)
        L64:
            long r7 = r2 & r9
            int r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r9 == 0) goto L74
            androidx.appcompat.widget.AppCompatTextView r7 = r1.tvMinTip
            r7.setVisibility(r6)
            androidx.appcompat.widget.AppCompatTextView r7 = r1.tvSecTip
            r7.setVisibility(r6)
        L74:
            r6 = 17
            long r2 = r2 & r6
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L80
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvSecTip
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r2, r0)
        L80:
            return
        L81:
            r0 = move-exception
            monitor-exit(r17)     // Catch: java.lang.Throwable -> L81
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.DialogTimePickerBindingImpl.executeBindings():void");
    }
}