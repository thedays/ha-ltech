package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ActE6PanelBindingImpl extends ActE6PanelBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final AppCompatImageView mboundView10;
    private final AppCompatImageView mboundView6;
    private final AppCompatImageView mboundView8;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(34);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{15}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_e6d, 16);
        sparseIntArray.put(R.id.rv_key, 17);
        sparseIntArray.put(R.id.layout_info, 18);
        sparseIntArray.put(R.id.layout_knob, 19);
        sparseIntArray.put(R.id.tv_object_title, 20);
        sparseIntArray.put(R.id.tv_object, 21);
        sparseIntArray.put(R.id.tabs, 22);
        sparseIntArray.put(R.id.tv_key_object, 23);
        sparseIntArray.put(R.id.tv_action_title, 24);
        sparseIntArray.put(R.id.civ_color, 25);
        sparseIntArray.put(R.id.tv_key_action, 26);
        sparseIntArray.put(R.id.layout_knob_use_tip, 27);
        sparseIntArray.put(R.id.tv_tip_message, 28);
        sparseIntArray.put(R.id.rv_knob_action, 29);
        sparseIntArray.put(R.id.view_tip, 30);
        sparseIntArray.put(R.id.iv_guide1, 31);
        sparseIntArray.put(R.id.iv_guide2, 32);
        sparseIntArray.put(R.id.group_guide, 33);
    }

    public ActE6PanelBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 34, sIncludes, sViewsWithIds));
    }

    private ActE6PanelBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CircleImageView) bindings[25], (Group) bindings[33], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[31], (AppCompatImageView) bindings[32], (RelativeLayout) bindings[9], (RelativeLayout) bindings[7], (RelativeLayout) bindings[5], (ConstraintLayout) bindings[0], (ScrollView) bindings[18], (LinearLayout) bindings[19], (ConstraintLayout) bindings[27], (RecyclerView) bindings[17], (RecyclerView) bindings[29], (TabLayout) bindings[22], (LayoutTitleDefaultBinding) bindings[15], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[26], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (View) bindings[30]);
        this.mDirtyFlags = -1L;
        this.ivControl.setTag(null);
        this.ivDoubt.setTag(null);
        this.keyAction.setTag(null);
        this.keyObject.setTag(null);
        this.knobObject.setTag(null);
        this.layoutBg.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[10];
        this.mboundView10 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[6];
        this.mboundView6 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        AppCompatImageView appCompatImageView3 = (AppCompatImageView) bindings[8];
        this.mboundView8 = appCompatImageView3;
        appCompatImageView3.setTag(null);
        setContainedBinding(this.title);
        this.tvGuide1.setTag(null);
        this.tvGuide2.setTag(null);
        this.tvInstruction.setTag(null);
        this.tvKnow.setTag(null);
        this.tvTitle.setTag(null);
        this.tvType.setTag(null);
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (43 == variableId) {
            setManagerOrOwner((Boolean) variable);
            return true;
        }
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActE6PanelBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActE6PanelBinding
    public void setManagerOrOwner(Boolean ManagerOrOwner) {
        this.mManagerOrOwner = ManagerOrOwner;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(43);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActE6PanelBinding
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0078  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> L9d
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L9d
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L9d
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r4 = r14.mClickCommand
            java.lang.Boolean r5 = r14.mManagerOrOwner
            com.ltech.smarthome.model.bean.TitleDefault r6 = r14.mTitle
            r7 = 10
            long r9 = r0 & r7
            r11 = 0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L2b
            boolean r5 = androidx.databinding.ViewDataBinding.safeUnbox(r5)
            if (r12 == 0) goto L25
            if (r5 == 0) goto L22
            r9 = 32
            goto L24
        L22:
            r9 = 16
        L24:
            long r0 = r0 | r9
        L25:
            if (r5 == 0) goto L28
            goto L2b
        L28:
            r5 = 8
            goto L2c
        L2b:
            r5 = 0
        L2c:
            r9 = 12
            long r9 = r9 & r0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            r9 = 9
            long r9 = r9 & r0
            int r13 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r13 == 0) goto L56
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivControl
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivDoubt
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r11)
            android.widget.RelativeLayout r9 = r14.keyAction
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r11)
            android.widget.RelativeLayout r9 = r14.keyObject
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r11)
            android.widget.RelativeLayout r9 = r14.knobObject
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvKnow
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r11)
        L56:
            long r7 = r7 & r0
            int r4 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r4 == 0) goto L6a
            androidx.appcompat.widget.AppCompatImageView r4 = r14.mboundView10
            r4.setVisibility(r5)
            androidx.appcompat.widget.AppCompatImageView r4 = r14.mboundView6
            r4.setVisibility(r5)
            androidx.appcompat.widget.AppCompatImageView r4 = r14.mboundView8
            r4.setVisibility(r5)
        L6a:
            if (r12 == 0) goto L71
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r4 = r14.title
            r4.setTitle(r6)
        L71:
            r4 = 8
            long r0 = r0 & r4
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L97
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvGuide1
            r1 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvGuide2
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvInstruction
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvKnow
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvTitle
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvType
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
        L97:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            executeBindingsOn(r0)
            return
        L9d:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L9d
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActE6PanelBindingImpl.executeBindings():void");
    }
}