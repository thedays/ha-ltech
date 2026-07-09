package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActSerialSettingBindingImpl extends ActSerialSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView2;
    private final AppCompatTextView mboundView5;
    private final AppCompatTextView mboundView8;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(18);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{13}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_baud_rate, 14);
        sparseIntArray.put(R.id.tv_parity, 15);
        sparseIntArray.put(R.id.tv_data_bits, 16);
        sparseIntArray.put(R.id.tv_stop_bits, 17);
    }

    public ActSerialSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }

    private ActSerialSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[9], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[12], (LinearLayout) bindings[1], (LinearLayout) bindings[7], (LinearLayout) bindings[4], (LinearLayout) bindings[10], (LayoutTitleDefaultBinding) bindings[13], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[17]);
        this.mDirtyFlags = -1L;
        this.ivBaudRate.setTag(null);
        this.ivDataBits.setTag(null);
        this.ivParity.setTag(null);
        this.ivStopBits.setTag(null);
        this.layoutBaudRate.setTag(null);
        this.layoutDataBits.setTag(null);
        this.layoutParity.setTag(null);
        this.layoutStopBits.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[8];
        this.mboundView8 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        setContainedBinding(this.title);
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

    @Override // com.ltech.smarthome.databinding.ActSerialSettingBinding
    public void setOwnerOrManager(Boolean OwnerOrManager) {
        this.mOwnerOrManager = OwnerOrManager;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(55);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSerialSettingBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSerialSettingBinding
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
    /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0083  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> L8e
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L8e
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L8e
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
            if (r9 == 0) goto L4f
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivBaudRate
            r7.setVisibility(r4)
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivDataBits
            r7.setVisibility(r4)
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivParity
            r7.setVisibility(r4)
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivStopBits
            r7.setVisibility(r4)
        L4f:
            if (r12 == 0) goto L65
            android.widget.LinearLayout r4 = r14.layoutBaudRate
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.LinearLayout r4 = r14.layoutDataBits
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.LinearLayout r4 = r14.layoutParity
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.LinearLayout r4 = r14.layoutStopBits
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
        L65:
            r4 = 8
            long r0 = r0 & r4
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L81
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView11
            r1 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView2
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView5
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView8
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
        L81:
            if (r13 == 0) goto L88
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            r0.setTitle(r6)
        L88:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            executeBindingsOn(r0)
            return
        L8e:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L8e
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSerialSettingBindingImpl.executeBindings():void");
    }
}