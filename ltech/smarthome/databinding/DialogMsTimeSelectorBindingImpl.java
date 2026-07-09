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
public class DialogMsTimeSelectorBindingImpl extends DialogMsTimeSelectorBinding {
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
        sparseIntArray.put(R.id.pick_view_hour, 8);
        sparseIntArray.put(R.id.pick_view_min, 9);
        sparseIntArray.put(R.id.picker_view_sec, 10);
        sparseIntArray.put(R.id.picker_view_ms, 11);
    }

    public DialogMsTimeSelectorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private DialogMsTimeSelectorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (RecyclerView) bindings[8], (RecyclerView) bindings[9], (RecyclerView) bindings[11], (RecyclerView) bindings[10], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCancel.setTag(null);
        this.tvFinish.setTag(null);
        this.tvMinHour.setTag(null);
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
        if (96 == variableId) {
            setWithUnit((Boolean) variable);
            return true;
        }
        if (66 == variableId) {
            setSecUnit((String) variable);
            return true;
        }
        if (45 == variableId) {
            setMsUnit((String) variable);
            return true;
        }
        if (44 == variableId) {
            setMinUnit((String) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (36 != variableId) {
            return false;
        }
        setHourUnit((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogMsTimeSelectorBinding
    public void setWithUnit(Boolean WithUnit) {
        this.mWithUnit = WithUnit;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(96);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogMsTimeSelectorBinding
    public void setSecUnit(String SecUnit) {
        this.mSecUnit = SecUnit;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(66);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogMsTimeSelectorBinding
    public void setMsUnit(String MsUnit) {
        this.mMsUnit = MsUnit;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(45);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogMsTimeSelectorBinding
    public void setMinUnit(String MinUnit) {
        this.mMinUnit = MinUnit;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(44);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogMsTimeSelectorBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogMsTimeSelectorBinding
    public void setHourUnit(String HourUnit) {
        this.mHourUnit = HourUnit;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(36);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r22 = this;
            r1 = r22
            monitor-enter(r22)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> La6
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> La6
            monitor-exit(r22)     // Catch: java.lang.Throwable -> La6
            java.lang.Boolean r0 = r1.mWithUnit
            java.lang.String r6 = r1.mSecUnit
            java.lang.String r7 = r1.mMsUnit
            java.lang.String r8 = r1.mMinUnit
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r9 = r1.mClickCommand
            java.lang.String r10 = r1.mHourUnit
            r11 = 65
            long r13 = r2 & r11
            r15 = 0
            int r16 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r16 == 0) goto L33
            boolean r0 = androidx.databinding.ViewDataBinding.safeUnbox(r0)
            if (r16 == 0) goto L2d
            if (r0 == 0) goto L2a
            r13 = 256(0x100, double:1.265E-321)
            goto L2c
        L2a:
            r13 = 128(0x80, double:6.32E-322)
        L2c:
            long r2 = r2 | r13
        L2d:
            if (r0 == 0) goto L30
            goto L33
        L30:
            r0 = 8
            goto L34
        L33:
            r0 = 0
        L34:
            r13 = 66
            long r13 = r13 & r2
            int r16 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            r13 = 68
            long r13 = r13 & r2
            int r17 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            r13 = 72
            long r13 = r13 & r2
            int r18 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            r13 = 80
            long r13 = r13 & r2
            int r19 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            r13 = 96
            long r13 = r13 & r2
            int r20 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            r13 = 64
            long r13 = r13 & r2
            int r21 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r21 == 0) goto L64
            androidx.appcompat.widget.AppCompatTextView r13 = r1.tvCancel
            r14 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r13, r14)
            androidx.appcompat.widget.AppCompatTextView r13 = r1.tvFinish
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r13, r14)
            androidx.appcompat.widget.AppCompatTextView r13 = r1.tvTitle
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r13, r14)
        L64:
            if (r19 == 0) goto L70
            androidx.appcompat.widget.AppCompatTextView r13 = r1.tvCancel
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r13, r9, r15)
            androidx.appcompat.widget.AppCompatTextView r13 = r1.tvFinish
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r13, r9, r15)
        L70:
            if (r20 == 0) goto L77
            androidx.appcompat.widget.AppCompatTextView r9 = r1.tvMinHour
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r9, r10)
        L77:
            long r2 = r2 & r11
            int r9 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r9 == 0) goto L90
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvMinHour
            r2.setVisibility(r0)
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvMinTip
            r2.setVisibility(r0)
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvMsTip
            r2.setVisibility(r0)
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvSecTip
            r2.setVisibility(r0)
        L90:
            if (r18 == 0) goto L97
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvMinTip
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r8)
        L97:
            if (r17 == 0) goto L9e
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvMsTip
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r7)
        L9e:
            if (r16 == 0) goto La5
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvSecTip
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r6)
        La5:
            return
        La6:
            r0 = move-exception
            monitor-exit(r22)     // Catch: java.lang.Throwable -> La6
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.DialogMsTimeSelectorBindingImpl.executeBindings():void");
    }
}