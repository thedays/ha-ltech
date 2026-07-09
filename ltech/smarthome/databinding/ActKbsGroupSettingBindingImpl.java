package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.kbs.ActKbsGroupSettingVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActKbsGroupSettingBindingImpl extends ActKbsGroupSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView8;
    private final AppCompatImageView mboundView9;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(26);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{17}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 18);
        sparseIntArray.put(R.id.tv_group_name, 19);
        sparseIntArray.put(R.id.tv_room_name, 20);
        sparseIntArray.put(R.id.iv_icon, 21);
        sparseIntArray.put(R.id.tv_tip, 22);
        sparseIntArray.put(R.id.rv_light_setting, 23);
        sparseIntArray.put(R.id.tv_device_count, 24);
        sparseIntArray.put(R.id.tv_state, 25);
    }

    public ActKbsGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }

    private ActKbsGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[15], (ConstraintLayout) bindings[7], (ConstraintLayout) bindings[4], (ConstraintLayout) bindings[10], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[13], (SmartRefreshLayout) bindings[18], (RecyclerView) bindings[23], (LayoutTitleDefaultBinding) bindings[17], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[25], (AppCompatTextView) bindings[22]);
        this.mDirtyFlags = -1L;
        this.ivDeviceCountGo.setTag(null);
        this.ivGroupNameGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivStateGo.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutEditGroup.setTag(null);
        this.layoutGroupName.setTag(null);
        this.layoutSetOnState.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[14];
        this.mboundView14 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[8];
        this.mboundView8 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[9];
        this.mboundView9 = appCompatImageView;
        appCompatImageView.setTag(null);
        setContainedBinding(this.title);
        this.tvDelete.setTag(null);
        this.tvGroupTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
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
        if (43 == variableId) {
            setManagerOrOwner((Boolean) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActKbsGroupSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActKbsGroupSettingBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActKbsGroupSettingBinding
    public void setManagerOrOwner(Boolean ManagerOrOwner) {
        this.mManagerOrOwner = ManagerOrOwner;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(43);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActKbsGroupSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActKbsGroupSettingBinding
    public void setViewmodel(ActKbsGroupSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b6  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> Lc1
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> Lc1
            monitor-exit(r14)     // Catch: java.lang.Throwable -> Lc1
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r4 = r14.mClickCommand
            java.lang.Boolean r5 = r14.mManagerOrOwner
            com.ltech.smarthome.model.bean.TitleDefault r6 = r14.mTitle
            r7 = 18
            long r9 = r0 & r7
            r11 = 0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L2b
            boolean r5 = androidx.databinding.ViewDataBinding.safeUnbox(r5)
            if (r12 == 0) goto L25
            if (r5 == 0) goto L22
            r9 = 320(0x140, double:1.58E-321)
            goto L24
        L22:
            r9 = 160(0xa0, double:7.9E-322)
        L24:
            long r0 = r0 | r9
        L25:
            if (r5 == 0) goto L28
            goto L2c
        L28:
            r9 = 8
            goto L2d
        L2b:
            r5 = 0
        L2c:
            r9 = 0
        L2d:
            r12 = 20
            long r12 = r12 & r0
            int r10 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            long r7 = r7 & r0
            int r12 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r12 == 0) goto L69
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivDeviceCountGo
            r7.setVisibility(r9)
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivGroupNameGo
            r7.setVisibility(r9)
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivRoomNameGo
            r7.setVisibility(r9)
            androidx.appcompat.widget.AppCompatImageView r7 = r14.ivStateGo
            r7.setVisibility(r9)
            androidx.constraintlayout.widget.ConstraintLayout r7 = r14.layoutChangeIcon
            r7.setEnabled(r5)
            androidx.constraintlayout.widget.ConstraintLayout r7 = r14.layoutChangeRoom
            r7.setEnabled(r5)
            androidx.constraintlayout.widget.ConstraintLayout r7 = r14.layoutEditGroup
            r7.setEnabled(r5)
            androidx.constraintlayout.widget.ConstraintLayout r7 = r14.layoutGroupName
            r7.setEnabled(r5)
            android.widget.RelativeLayout r7 = r14.layoutSetOnState
            r7.setEnabled(r5)
            androidx.appcompat.widget.AppCompatImageView r5 = r14.mboundView9
            r5.setVisibility(r9)
        L69:
            r7 = 17
            long r7 = r7 & r0
            int r5 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r5 == 0) goto L8e
            androidx.constraintlayout.widget.ConstraintLayout r5 = r14.layoutChangeIcon
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.constraintlayout.widget.ConstraintLayout r5 = r14.layoutChangeRoom
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.constraintlayout.widget.ConstraintLayout r5 = r14.layoutEditGroup
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.constraintlayout.widget.ConstraintLayout r5 = r14.layoutGroupName
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            android.widget.RelativeLayout r5 = r14.layoutSetOnState
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
            androidx.appcompat.widget.AppCompatTextView r5 = r14.tvDelete
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r4, r11)
        L8e:
            r4 = 16
            long r0 = r0 & r4
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto Lb4
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView14
            r1 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.mboundView8
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvDelete
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvGroupTip
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvNameTip
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvRoomTip
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
        Lb4:
            if (r10 == 0) goto Lbb
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            r0.setTitle(r6)
        Lbb:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            executeBindingsOn(r0)
            return
        Lc1:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> Lc1
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActKbsGroupSettingBindingImpl.executeBindings():void");
    }
}