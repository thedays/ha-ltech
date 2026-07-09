package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.NestedScrollParentView;

/* loaded from: classes3.dex */
public class FtRoomDaliAddBindingImpl extends FtRoomDaliAddBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final LinearLayout mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.scroll_layout, 7);
        sparseIntArray.put(R.id.rv_group, 8);
        sparseIntArray.put(R.id.layout_light, 9);
        sparseIntArray.put(R.id.radio_position, 10);
        sparseIntArray.put(R.id.radio_0, 11);
        sparseIntArray.put(R.id.radio_20, 12);
        sparseIntArray.put(R.id.radio_40, 13);
        sparseIntArray.put(R.id.rv_light, 14);
    }

    public FtRoomDaliAddBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private FtRoomDaliAddBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (LinearLayout) bindings[9], (RadioButton) bindings[11], (RadioButton) bindings[12], (RadioButton) bindings[13], (RadioGroup) bindings[10], (RecyclerView) bindings[8], (RecyclerView) bindings[14], (RecyclerView) bindings[4], (NestedScrollParentView) bindings[7], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        LinearLayout linearLayout2 = (LinearLayout) bindings[1];
        this.mboundView1 = linearLayout2;
        linearLayout2.setTag(null);
        this.rvScene.setTag(null);
        this.tvGroupTip.setTag(null);
        this.tvLightTip.setTag(null);
        this.tvSceneTip.setTag(null);
        this.tvShow.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (65 != variableId) {
            return false;
        }
        setSceneEdit((Boolean) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtRoomDaliAddBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtRoomDaliAddBinding
    public void setSceneEdit(Boolean SceneEdit) {
        this.mSceneEdit = SceneEdit;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(65);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r12 = this;
            monitor-enter(r12)
            long r0 = r12.mDirtyFlags     // Catch: java.lang.Throwable -> L5d
            r2 = 0
            r12.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L5d
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L5d
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r4 = r12.mClickCommand
            java.lang.Boolean r5 = r12.mSceneEdit
            r6 = 6
            long r8 = r0 & r6
            r10 = 0
            int r11 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r11 == 0) goto L29
            boolean r5 = androidx.databinding.ViewDataBinding.safeUnbox(r5)
            if (r11 == 0) goto L23
            if (r5 == 0) goto L20
            r8 = 16
            goto L22
        L20:
            r8 = 8
        L22:
            long r0 = r0 | r8
        L23:
            if (r5 == 0) goto L26
            goto L29
        L26:
            r5 = 8
            goto L2a
        L29:
            r5 = 0
        L2a:
            long r6 = r6 & r0
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 == 0) goto L39
            android.widget.LinearLayout r6 = r12.mboundView1
            r6.setVisibility(r5)
            androidx.recyclerview.widget.RecyclerView r6 = r12.rvScene
            r6.setVisibility(r5)
        L39:
            r5 = 4
            long r5 = r5 & r0
            int r7 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r7 == 0) goto L50
            androidx.appcompat.widget.AppCompatTextView r5 = r12.tvGroupTip
            r6 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r5, r6)
            androidx.appcompat.widget.AppCompatTextView r5 = r12.tvLightTip
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r5, r6)
            androidx.appcompat.widget.AppCompatTextView r5 = r12.tvSceneTip
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r5, r6)
        L50:
            r5 = 5
            long r0 = r0 & r5
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 == 0) goto L5c
            androidx.appcompat.widget.AppCompatTextView r0 = r12.tvShow
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r4, r10)
        L5c:
            return
        L5d:
            r0 = move-exception
            monitor-exit(r12)     // Catch: java.lang.Throwable -> L5d
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.FtRoomDaliAddBindingImpl.executeBindings():void");
    }
}