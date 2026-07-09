package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActRoomManageVM;
import me.tatarka.bindingcollectionadapter2.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

/* loaded from: classes3.dex */
public class ActRoomManageBindingImpl extends ActRoomManageBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView4;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.title, 6);
    }

    public ActRoomManageBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ActRoomManageBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatTextView) bindings[3], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[1], (RecyclerView) bindings[5], (ConstraintLayout) bindings[6]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView5.setTag(null);
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        this.layoutLoad.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView;
        appCompatTextView.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActRoomManageVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActRoomManageBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActRoomManageBinding
    public void setViewmodel(ActRoomManageVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelMMergeObservableList((MergeObservableList) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelFloorResult((MediatorLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelMMergeObservableList(MergeObservableList<Object> ViewmodelMMergeObservableList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelFloorResult(MediatorLiveData<Floor> ViewmodelFloorResult, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        long j3;
        int i;
        BindingCommand bindingCommand;
        BindingCommand bindingCommand2;
        int i2;
        int i3;
        String str;
        int i4;
        BindingCommand bindingCommand3;
        int i5;
        OnItemBindClass<Object> onItemBindClass;
        String str2;
        int i6;
        MergeObservableList<Object> mergeObservableList;
        BindingRecyclerViewAdapter.ItemIds<Object> itemIds;
        boolean z;
        BindingRecyclerViewAdapter.ItemIds<Object> itemIds2;
        MergeObservableList<Object> mergeObservableList2;
        int i7;
        String str3;
        int i8;
        BindingCommand bindingCommand4;
        BindingCommand bindingCommand5;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActRoomManageVM actRoomManageVM = this.mViewmodel;
        long j4 = j & 20;
        if (j4 != 0) {
            if (titleDefault != null) {
                i3 = titleDefault.getBackImageRes();
                str = titleDefault.getTitle();
                bindingCommand4 = titleDefault.getBackAction();
                bindingCommand5 = titleDefault.getEditAction();
                i = titleDefault.getEditImageRes();
            } else {
                i = 0;
                i3 = 0;
                str = null;
                bindingCommand4 = null;
                bindingCommand5 = null;
            }
            boolean z2 = i3 != 0;
            boolean z3 = i != 0;
            if (j4 != 0) {
                j |= z2 ? 256L : 128L;
            }
            if ((j & 20) != 0) {
                j |= z3 ? PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
            }
            i4 = z2 ? 0 : 8;
            i2 = z3 ? 0 : 8;
            j3 = 20;
            bindingCommand = bindingCommand4;
            bindingCommand2 = bindingCommand5;
            j2 = 0;
        } else {
            j2 = 0;
            j3 = 20;
            i = 0;
            bindingCommand = null;
            bindingCommand2 = null;
            i2 = 0;
            i3 = 0;
            str = null;
            i4 = 0;
        }
        if ((j & 27) != j2) {
            if ((j & 25) != j2) {
                if (actRoomManageVM != null) {
                    itemIds2 = actRoomManageVM.itemIds;
                    onItemBindClass = actRoomManageVM.itemBinding;
                    mergeObservableList2 = actRoomManageVM.mMergeObservableList;
                } else {
                    itemIds2 = null;
                    onItemBindClass = null;
                    mergeObservableList2 = null;
                }
                updateRegistration(0, mergeObservableList2);
            } else {
                itemIds2 = null;
                onItemBindClass = null;
                mergeObservableList2 = null;
            }
            long j5 = j & 26;
            if (j5 != j2) {
                MediatorLiveData<Floor> mediatorLiveData = actRoomManageVM != null ? actRoomManageVM.floorResult : null;
                updateLiveDataRegistration(1, mediatorLiveData);
                Floor value = mediatorLiveData != null ? mediatorLiveData.getValue() : null;
                str3 = value != null ? value.getFloorName() : null;
                boolean z4 = value != null;
                boolean z5 = value == null;
                if (j5 != j2) {
                    j |= z4 ? 1024L : 512L;
                }
                if ((j & 26) != j2) {
                    j |= z5 ? 64L : 32L;
                }
                i8 = z4 ? 0 : 8;
                i7 = z5 ? 0 : 8;
            } else {
                i7 = 0;
                str3 = null;
                i8 = 0;
            }
            if ((j & 24) == j2 || actRoomManageVM == null) {
                itemIds = itemIds2;
                mergeObservableList = mergeObservableList2;
                i5 = i7;
                str2 = str3;
                i6 = i8;
                bindingCommand3 = null;
            } else {
                bindingCommand3 = actRoomManageVM.changeFloor;
                itemIds = itemIds2;
                mergeObservableList = mergeObservableList2;
                i5 = i7;
                str2 = str3;
                i6 = i8;
            }
        } else {
            bindingCommand3 = null;
            i5 = 0;
            onItemBindClass = null;
            str2 = null;
            i6 = 0;
            mergeObservableList = null;
            itemIds = null;
        }
        if ((j & 26) != j2) {
            this.appCompatTextView5.setVisibility(i6);
            TextViewBindingAdapter.setText(this.appCompatTextView5, str2);
            this.mboundView4.setVisibility(i5);
        }
        if ((16 & j) != j2) {
            ViewAdapter.setTextBold(this.appCompatTextView5, true);
            ViewAdapter.setTextBold(this.mboundView4, true);
        }
        if ((j & 24) != j2) {
            z = false;
            ViewAdapter.onClickCommand(this.appCompatTextView5, bindingCommand3, false);
        } else {
            z = false;
        }
        if ((j & j3) != j2) {
            this.ivBack.setVisibility(i4);
            ViewAdapter.setSrc(this.ivBack, i3);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand, z);
            this.ivEdit.setVisibility(i2);
            ViewAdapter.setSrc(this.ivEdit, i);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand2, z);
            TextViewBindingAdapter.setText(this.mboundView4, str);
        }
        if ((j & 25) != j2) {
            BindingRecyclerViewAdapters.setAdapter(this.layoutLoad, BindingCollectionAdapters.toItemBinding(onItemBindClass), mergeObservableList, null, itemIds, null, null);
        }
    }
}