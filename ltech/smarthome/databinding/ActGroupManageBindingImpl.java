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
import com.ltech.smarthome.base.BaseGroupManageVM;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActGroupManageBindingImpl extends ActGroupManageBinding {
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

    public ActGroupManageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ActGroupManageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
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
        setViewmodel((BaseGroupManageVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActGroupManageBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGroupManageBinding
    public void setBottomTip(String BottomTip) {
        this.mBottomTip = BottomTip;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGroupManageBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGroupManageBinding
    public void setViewmodel(BaseGroupManageVM Viewmodel) {
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
            return onChangeViewmodelMGroupList((ObservableList) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeSearchBar((ItemSearchBarBinding) object, fieldId);
    }

    private boolean onChangeViewmodelMGroupList(ObservableList<Group> ViewmodelMGroupList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeSearchBar(ItemSearchBarBinding SearchBar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0051  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r25 = this;
            r1 = r25
            monitor-enter(r25)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lad
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lad
            monitor-exit(r25)     // Catch: java.lang.Throwable -> Lad
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r0 = r1.mClickCommand
            java.lang.String r6 = r1.mBottomTip
            com.ltech.smarthome.model.bean.TitleDefault r7 = r1.mTitle
            com.ltech.smarthome.base.BaseGroupManageVM r8 = r1.mViewmodel
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
            r12 = 0
            int r16 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            r14 = 97
            long r14 = r14 & r2
            int r17 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r17 == 0) goto L51
            if (r8 == 0) goto L45
            androidx.databinding.ObservableList<com.ltech.smarthome.model.bean.Group> r12 = r8.mGroupList
            me.tatarka.bindingcollectionadapter2.ItemBinding<com.ltech.smarthome.model.bean.Group> r14 = r8.itemBinding
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter$ItemIds<com.ltech.smarthome.model.bean.Group> r8 = r8.itemIds
            goto L47
        L45:
            r8 = r12
            r14 = r8
        L47:
            r1.updateRegistration(r13, r12)
            r22 = r8
            r20 = r12
            r19 = r14
            goto L57
        L51:
            r19 = r12
            r20 = r19
            r22 = r20
        L57:
            if (r17 == 0) goto L72
            androidx.recyclerview.widget.RecyclerView r8 = r1.rvContent
            r21 = 0
            r12 = r21
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter r12 = (me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter) r12
            r23 = 0
            r12 = r23
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter$ViewHolderFactory r12 = (me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ViewHolderFactory) r12
            r24 = 0
            r12 = r24
            androidx.recyclerview.widget.AsyncDifferConfig r12 = (androidx.recyclerview.widget.AsyncDifferConfig) r12
            r18 = r8
            me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters.setAdapter(r18, r19, r20, r21, r22, r23, r24)
        L72:
            if (r16 == 0) goto L79
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r8 = r1.title
            r8.setTitle(r7)
        L79:
            long r7 = r2 & r9
            int r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r9 == 0) goto L89
            androidx.appcompat.widget.AppCompatTextView r7 = r1.tvBottom
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r7, r6)
            androidx.appcompat.widget.AppCompatTextView r6 = r1.tvBottom
            r6.setVisibility(r11)
        L89:
            r6 = 64
            long r6 = r6 & r2
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L96
            androidx.appcompat.widget.AppCompatTextView r6 = r1.tvBottom
            r7 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r6, r7)
        L96:
            r6 = 68
            long r2 = r2 & r6
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto La2
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvBottom
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r2, r0, r13)
        La2:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.title
            executeBindingsOn(r0)
            com.ltech.smarthome.databinding.ItemSearchBarBinding r0 = r1.searchBar
            executeBindingsOn(r0)
            return
        Lad:
            r0 = move-exception
            monitor-exit(r25)     // Catch: java.lang.Throwable -> Lad
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActGroupManageBindingImpl.executeBindings():void");
    }
}