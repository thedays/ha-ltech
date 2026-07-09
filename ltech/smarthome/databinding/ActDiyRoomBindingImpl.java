package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.home.ActDiyRoomVM;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class ActDiyRoomBindingImpl extends ActDiyRoomBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final RecyclerView mboundView1;
    private final AppCompatEditText mboundView3;
    private InverseBindingListener mboundView3androidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view3, 5);
        sparseIntArray.put(R.id.view4, 6);
        sparseIntArray.put(R.id.view7, 7);
    }

    public ActDiyRoomBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActDiyRoomBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatTextView) bindings[2], (LayoutTitleDefaultBinding) bindings[4], (View) bindings[5], (View) bindings[6], (AppCompatTextView) bindings[7]);
        this.mboundView3androidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActDiyRoomBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActDiyRoomBindingImpl.this.mboundView3);
                ActDiyRoomVM actDiyRoomVM = ActDiyRoomBindingImpl.this.mViewmodel;
                if (actDiyRoomVM == null || (observableField = actDiyRoomVM.roomName) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.appCompatTextView3.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        RecyclerView recyclerView = (RecyclerView) bindings[1];
        this.mboundView1 = recyclerView;
        recyclerView.setTag(null);
        AppCompatEditText appCompatEditText = (AppCompatEditText) bindings[3];
        this.mboundView3 = appCompatEditText;
        appCompatEditText.setTag(null);
        setContainedBinding(this.title);
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActDiyRoomVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDiyRoomBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDiyRoomBinding
    public void setViewmodel(ActDiyRoomVM Viewmodel) {
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
        if (localFieldId == 0) {
            return onChangeViewmodelRecommendList((ObservableArrayList) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelRoomName((ObservableField) object, fieldId);
    }

    private boolean onChangeViewmodelRecommendList(ObservableArrayList<String> ViewmodelRecommendList, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelRoomName(ObservableField<String> ViewmodelRoomName, int fieldId) {
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
        FlexboxLayoutManager flexboxLayoutManager;
        String str;
        ItemBinding<String> itemBinding;
        ObservableList observableList;
        ItemBinding<String> itemBinding2;
        ObservableList observableList2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActDiyRoomVM actDiyRoomVM = this.mViewmodel;
        long j3 = 20 & j;
        if ((27 & j) != 0) {
            if ((j & 25) != 0) {
                if (actDiyRoomVM != null) {
                    itemBinding2 = actDiyRoomVM.itemBinding;
                    observableList2 = actDiyRoomVM.recommendList;
                } else {
                    itemBinding2 = null;
                    observableList2 = null;
                }
                j2 = 26;
                updateRegistration(0, observableList2);
            } else {
                j2 = 26;
                itemBinding2 = null;
                observableList2 = null;
            }
            if ((j & j2) != 0) {
                ObservableField<String> observableField = actDiyRoomVM != null ? actDiyRoomVM.roomName : null;
                updateRegistration(1, observableField);
                if (observableField != null) {
                    str = observableField.get();
                    flexboxLayoutManager = ((j & 24) != 0 || actDiyRoomVM == null) ? null : actDiyRoomVM.flexboxLayoutManager;
                    itemBinding = itemBinding2;
                    observableList = observableList2;
                }
            }
            str = null;
            if ((j & 24) != 0) {
            }
            itemBinding = itemBinding2;
            observableList = observableList2;
        } else {
            j2 = 26;
            flexboxLayoutManager = null;
            str = null;
            itemBinding = null;
            observableList = null;
        }
        if ((16 & j) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView3, true);
            ViewAdapter.setTextBold(this.mboundView3, true);
            TextViewBindingAdapter.setTextWatcher(this.mboundView3, null, null, null, this.mboundView3androidTextAttrChanged);
        }
        if ((j & 24) != 0) {
            this.mboundView1.setLayoutManager(flexboxLayoutManager);
        }
        if ((j & 25) != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.mboundView1, itemBinding, observableList, null, null, null, null);
        }
        if ((j & j2) != 0) {
            TextViewBindingAdapter.setText(this.mboundView3, str);
        }
        if (j3 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}