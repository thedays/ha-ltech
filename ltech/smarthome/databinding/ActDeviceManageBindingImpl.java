package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseDeviceManageVM;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActDeviceManageBindingImpl extends ActDeviceManageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(9);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        includedLayouts.setIncludes(1, new String[]{"item_search_bar"}, new int[]{5}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.guideline, 6);
        sparseIntArray.put(R.id.spinner_floor, 7);
        sparseIntArray.put(R.id.spinner_room, 8);
    }

    public ActDeviceManageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ActDeviceManageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (Guideline) bindings[6], (FrameLayout) bindings[1], (RecyclerView) bindings[2], (ItemSearchBarBinding) bindings[5], (MySpinner) bindings[7], (MySpinner) bindings[8], (LayoutTitleDefaultBinding) bindings[4], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.layoutSearch.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.rvContent.setTag(null);
        setContainedBinding(this.searchBar);
        setContainedBinding(this.title);
        this.tvBottom.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
        }
        this.title.invalidateAll();
        this.searchBar.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings() || this.searchBar.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (6 == variableId) {
            setBottomTip((String) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((BaseDeviceManageVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceManageBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceManageBinding
    public void setBottomTip(String BottomTip) {
        this.mBottomTip = BottomTip;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceManageBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDeviceManageBinding
    public void setViewmodel(BaseDeviceManageVM Viewmodel) {
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
        this.searchBar.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeSearchBar((ItemSearchBarBinding) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelMDeviceList((ObservableList) object, fieldId);
    }

    private boolean onChangeSearchBar(ItemSearchBarBinding SearchBar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelMDeviceList(ObservableList<Device> ViewmodelMDeviceList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0056  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r26 = this;
            r1 = r26
            monitor-enter(r26)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lb1
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lb1
            monitor-exit(r26)     // Catch: java.lang.Throwable -> Lb1
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r0 = r1.mClickCommand
            java.lang.String r6 = r1.mBottomTip
            com.ltech.smarthome.model.bean.TitleDefault r7 = r1.mTitle
            com.ltech.smarthome.base.BaseDeviceManageVM r8 = r1.mViewmodel
            r9 = 72
            long r11 = r2 & r9
            r13 = 0
            int r14 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r14 == 0) goto L2e
            boolean r11 = android.text.TextUtils.isEmpty(r6)
            if (r14 == 0) goto L29
            if (r11 == 0) goto L26
            r14 = 256(0x100, double:1.265E-321)
            goto L28
        L26:
            r14 = 128(0x80, double:6.32E-322)
        L28:
            long r2 = r2 | r14
        L29:
            if (r11 == 0) goto L2e
            r11 = 8
            goto L2f
        L2e:
            r11 = 0
        L2f:
            r14 = 80
            long r14 = r14 & r2
            r12 = 1
            r16 = 0
            int r17 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            r14 = 98
            long r14 = r14 & r2
            int r18 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r18 == 0) goto L56
            if (r8 == 0) goto L49
            me.tatarka.bindingcollectionadapter2.ItemBinding<com.ltech.smarthome.model.bean.Device> r14 = r8.itemBinding
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter$ItemIds<com.ltech.smarthome.model.bean.Device> r15 = r8.itemIds
            androidx.databinding.ObservableList<com.ltech.smarthome.model.bean.Device> r8 = r8.mDeviceList
            r16 = r14
            goto L4c
        L49:
            r8 = r16
            r15 = r8
        L4c:
            r1.updateRegistration(r12, r8)
            r21 = r8
            r23 = r15
            r20 = r16
            goto L5c
        L56:
            r20 = r16
            r21 = r20
            r23 = r21
        L5c:
            if (r18 == 0) goto L77
            androidx.recyclerview.widget.RecyclerView r8 = r1.rvContent
            r22 = 0
            r14 = r22
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter r14 = (me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter) r14
            r24 = 0
            r14 = r24
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter$ViewHolderFactory r14 = (me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ViewHolderFactory) r14
            r25 = 0
            r14 = r25
            androidx.recyclerview.widget.AsyncDifferConfig r14 = (androidx.recyclerview.widget.AsyncDifferConfig) r14
            r19 = r8
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters.setAdapter(r19, r20, r21, r22, r23, r24, r25)
        L77:
            if (r17 == 0) goto L7e
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r8 = r1.title
            r8.setTitle(r7)
        L7e:
            long r7 = r2 & r9
            int r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r9 == 0) goto L8e
            androidx.appcompat.widget.AppCompatTextView r7 = r1.tvBottom
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r7, r6)
            androidx.appcompat.widget.AppCompatTextView r6 = r1.tvBottom
            r6.setVisibility(r11)
        L8e:
            r6 = 64
            long r6 = r6 & r2
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L9a
            androidx.appcompat.widget.AppCompatTextView r6 = r1.tvBottom
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r6, r12)
        L9a:
            r6 = 68
            long r2 = r2 & r6
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto La6
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvBottom
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r2, r0, r13)
        La6:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.title
            executeBindingsOn(r0)
            com.ltech.smarthome.databinding.ItemSearchBarBinding r0 = r1.searchBar
            executeBindingsOn(r0)
            return
        Lb1:
            r0 = move-exception
            monitor-exit(r26)     // Catch: java.lang.Throwable -> Lb1
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActDeviceManageBindingImpl.executeBindings():void");
    }
}