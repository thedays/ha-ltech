package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActColorCCLightVM;

/* loaded from: classes3.dex */
public class ActColorLightCcBindingImpl extends ActColorLightCcBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(15);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{7}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_tab, 8);
        sparseIntArray.put(R.id.line_1, 9);
        sparseIntArray.put(R.id.line_2, 10);
        sparseIntArray.put(R.id.line_3, 11);
        sparseIntArray.put(R.id.line_4, 12);
        sparseIntArray.put(R.id.fragment_container, 13);
        sparseIntArray.put(R.id.rv_action, 14);
    }

    public ActColorLightCcBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActColorLightCcBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (FrameLayout) bindings[13], (LinearLayout) bindings[8], (View) bindings[9], (View) bindings[10], (View) bindings[11], (View) bindings[12], (RecyclerView) bindings[14], (LayoutTitleDefaultBinding) bindings[7], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (View) bindings[6]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvCct.setTag(null);
        this.tvGeneral.setTag(null);
        this.tvGray.setTag(null);
        this.tvHsl.setTag(null);
        this.tvXyy.setTag(null);
        this.vOpen.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActColorCCLightVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActColorLightCcBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActColorLightCcBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActColorLightCcBinding
    public void setViewmodel(ActColorCCLightVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelStateOn((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelStateOn(MutableLiveData<Boolean> ViewmodelStateOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r13 = this;
            monitor-enter(r13)
            long r0 = r13.mDirtyFlags     // Catch: java.lang.Throwable -> L7a
            r2 = 0
            r13.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L7a
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L7a
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r4 = r13.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r5 = r13.mTitle
            com.ltech.smarthome.ui.device.light.ActColorCCLightVM r6 = r13.mViewmodel
            r7 = 25
            long r9 = r0 & r7
            r11 = 0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L3d
            r9 = 0
            if (r6 == 0) goto L1d
            androidx.lifecycle.MutableLiveData<java.lang.Boolean> r6 = r6.stateOn
            goto L1e
        L1d:
            r6 = r9
        L1e:
            r13.updateLiveDataRegistration(r11, r6)
            if (r6 == 0) goto L2a
            java.lang.Object r6 = r6.getValue()
            r9 = r6
            java.lang.Boolean r9 = (java.lang.Boolean) r9
        L2a:
            boolean r6 = androidx.databinding.ViewDataBinding.safeUnbox(r9)
            if (r12 == 0) goto L38
            if (r6 == 0) goto L35
            r9 = 64
            goto L37
        L35:
            r9 = 32
        L37:
            long r0 = r0 | r9
        L38:
            if (r6 == 0) goto L3d
            r6 = 8
            goto L3e
        L3d:
            r6 = 0
        L3e:
            r9 = 20
            long r9 = r9 & r0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L4a
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r9 = r13.title
            r9.setTitle(r5)
        L4a:
            r9 = 18
            long r9 = r9 & r0
            int r5 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r5 == 0) goto L6a
            androidx.appcompat.widget.AppCompatTextView r5 = r13.tvCct
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.appcompat.widget.AppCompatTextView r5 = r13.tvGeneral
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.appcompat.widget.AppCompatTextView r5 = r13.tvGray
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.appcompat.widget.AppCompatTextView r5 = r13.tvHsl
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.appcompat.widget.AppCompatTextView r5 = r13.tvXyy
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
        L6a:
            long r0 = r0 & r7
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L74
            android.view.View r0 = r13.vOpen
            r0.setVisibility(r6)
        L74:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r13.title
            executeBindingsOn(r0)
            return
        L7a:
            r0 = move-exception
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L7a
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActColorLightCcBindingImpl.executeBindings():void");
    }
}