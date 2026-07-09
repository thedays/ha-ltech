package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public class ActMeshScanBindingImpl extends ActMeshScanBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;
    private final LinearLayout mboundView1;
    private final LayoutTitleDefaultBinding mboundView11;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(15);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(1, new String[]{"layout_title_default"}, new int[]{9}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.constraintlayout, 10);
        sparseIntArray.put(R.id.view24, 11);
        sparseIntArray.put(R.id.spreadview_scan, 12);
        sparseIntArray.put(R.id.tv_scan_tip_2, 13);
        sparseIntArray.put(R.id.v_line, 14);
    }

    public ActMeshScanBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActMeshScanBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (ConstraintLayout) bindings[10], (ContentLoadingProgressBar) bindings[8], (RecyclerView) bindings[6], (SpreadView) bindings[12], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[7], (View) bindings[14], (View) bindings[11]);
        this.mDirtyFlags = -1L;
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[1];
        this.mboundView1 = linearLayout;
        linearLayout.setTag(null);
        LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) bindings[9];
        this.mboundView11 = layoutTitleDefaultBinding;
        setContainedBinding(layoutTitleDefaultBinding);
        this.progressBar.setTag(null);
        this.rvContent.setTag(null);
        this.tvHelp.setTag(null);
        this.tvNoDevice.setTag(null);
        this.tvScanTip1.setTag(null);
        this.tvTotal.setTag(null);
        this.tvUpgrade.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128L;
        }
        this.mboundView11.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.mboundView11.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (88 == variableId) {
            setUpgradeTip((String) variable);
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
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActMeshScanVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActMeshScanBinding
    public void setUpgradeTip(String UpgradeTip) {
        this.mUpgradeTip = UpgradeTip;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(88);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActMeshScanBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActMeshScanBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActMeshScanBinding
    public void setViewmodel(ActMeshScanVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.mboundView11.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelDeviceList((ObservableList) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelShowprogressBar((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeViewmodelDeviceNum((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelDeviceList(ObservableList<ActMeshScanVM.ScanItem> ViewmodelDeviceList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelShowprogressBar(MutableLiveData<Boolean> ViewmodelShowprogressBar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelDeviceNum(MutableLiveData<String> ViewmodelDeviceNum, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00d9  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActMeshScanBindingImpl.executeBindings():void");
    }
}