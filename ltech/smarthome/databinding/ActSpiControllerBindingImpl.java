package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerVM;
import com.ltech.smarthome.view.HorizontalSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SmartSwitch;

/* loaded from: classes3.dex */
public class ActSpiControllerBindingImpl extends ActSpiControllerBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView7;
    private final Group mboundView8;
    private final Group mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(23);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{11}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tab_layout, 12);
        sparseIntArray.put(R.id.tabs, 13);
        sparseIntArray.put(R.id.viewpager, 14);
        sparseIntArray.put(R.id.layout_control, 15);
        sparseIntArray.put(R.id.layout_direction, 16);
        sparseIntArray.put(R.id.sb_brt, 17);
        sparseIntArray.put(R.id.tv_brt, 18);
        sparseIntArray.put(R.id.sb_speed, 19);
        sparseIntArray.put(R.id.tv_speed, 20);
        sparseIntArray.put(R.id.view_line, 21);
        sparseIntArray.put(R.id.spi_switch, 22);
    }

    public ActSpiControllerBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }

    private ActSpiControllerBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[2], (ConstraintLayout) bindings[15], (LinearLayout) bindings[16], (LightBrtBar) bindings[17], (HorizontalSeekBar) bindings[19], (SmartSwitch) bindings[22], (LinearLayout) bindings[12], (TabLayout) bindings[13], (LayoutTitleDefaultBinding) bindings[11], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[1], (LinearLayout) bindings[6], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[5], (View) bindings[10], (View) bindings[21], (ViewPager2) bindings[14]);
        this.mDirtyFlags = -1L;
        this.ivConsole.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[7];
        this.mboundView7 = appCompatTextView;
        appCompatTextView.setTag(null);
        Group group = (Group) bindings[8];
        this.mboundView8 = group;
        group.setTag(null);
        Group group2 = (Group) bindings[9];
        this.mboundView9 = group2;
        group2.setTag(null);
        setContainedBinding(this.title);
        this.tvBrtTip.setTag(null);
        this.tvConsole.setTag(null);
        this.tvControl.setTag(null);
        this.tvDirection.setTag(null);
        this.tvSpeedTip.setTag(null);
        this.vOpen.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
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
        if (12 == variableId) {
            setColorControl((Boolean) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (29 == variableId) {
            setExpand((Boolean) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActSpiControllerVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSpiControllerBinding
    public void setColorControl(Boolean ColorControl) {
        this.mColorControl = ColorControl;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(12);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSpiControllerBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSpiControllerBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSpiControllerBinding
    public void setExpand(Boolean Expand) {
        this.mExpand = Expand;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(29);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSpiControllerBinding
    public void setViewmodel(ActSpiControllerVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 32;
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

    /* JADX WARN: Code restructure failed: missing block: B:40:0x007e, code lost:
    
        if (r9 != false) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00e7  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSpiControllerBindingImpl.executeBindings():void");
    }
}