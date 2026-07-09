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
import com.ltech.smarthome.ui.device.light.ActDiyLightNameVM;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes3.dex */
public class ActDiyLightNameBindingImpl extends ActDiyLightNameBinding {
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

    public ActDiyLightNameBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActDiyLightNameBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatTextView) bindings[2], (LayoutTitleDefaultBinding) bindings[4], (View) bindings[5], (View) bindings[6], (AppCompatTextView) bindings[7]);
        this.mboundView3androidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActDiyLightNameBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActDiyLightNameBindingImpl.this.mboundView3);
                ActDiyLightNameVM actDiyLightNameVM = ActDiyLightNameBindingImpl.this.mViewmodel;
                if (actDiyLightNameVM == null || (observableField = actDiyLightNameVM.lightName) == null) {
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
            this.mDirtyFlags = 32L;
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
        if (47 == variableId) {
            setNameText((String) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActDiyLightNameVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDiyLightNameBinding
    public void setNameText(String NameText) {
        this.mNameText = NameText;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(47);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDiyLightNameBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDiyLightNameBinding
    public void setViewmodel(ActDiyLightNameVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 16;
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
            return onChangeViewmodelLightName((ObservableField) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelRecommendList((ObservableArrayList) object, fieldId);
    }

    private boolean onChangeViewmodelLightName(ObservableField<String> ViewmodelLightName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelRecommendList(ObservableArrayList<String> ViewmodelRecommendList, int fieldId) {
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
        String str2 = this.mNameText;
        TitleDefault titleDefault = this.mTitle;
        ActDiyLightNameVM actDiyLightNameVM = this.mViewmodel;
        long j3 = 36 & j;
        long j4 = 40 & j;
        if ((51 & j) != 0) {
            if ((j & 50) != 0) {
                if (actDiyLightNameVM != null) {
                    itemBinding2 = actDiyLightNameVM.itemBinding;
                    observableList2 = actDiyLightNameVM.recommendList;
                } else {
                    itemBinding2 = null;
                    observableList2 = null;
                }
                updateRegistration(1, observableList2);
            } else {
                itemBinding2 = null;
                observableList2 = null;
            }
            j2 = 48;
            if ((j & 49) != 0) {
                ObservableField<String> observableField = actDiyLightNameVM != null ? actDiyLightNameVM.lightName : null;
                updateRegistration(0, observableField);
                if (observableField != null) {
                    str = observableField.get();
                    flexboxLayoutManager = ((j & 48) != 0 || actDiyLightNameVM == null) ? null : actDiyLightNameVM.flexboxLayoutManager;
                    itemBinding = itemBinding2;
                    observableList = observableList2;
                }
            }
            str = null;
            if ((j & 48) != 0) {
            }
            itemBinding = itemBinding2;
            observableList = observableList2;
        } else {
            j2 = 48;
            flexboxLayoutManager = null;
            str = null;
            itemBinding = null;
            observableList = null;
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView3, str2);
        }
        if ((32 & j) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView3, true);
            ViewAdapter.setTextBold(this.mboundView3, true);
            TextViewBindingAdapter.setTextWatcher(this.mboundView3, null, null, null, this.mboundView3androidTextAttrChanged);
        }
        if ((j & j2) != 0) {
            this.mboundView1.setLayoutManager(flexboxLayoutManager);
        }
        if ((j & 50) != 0) {
            BindingRecyclerViewAdapters.setAdapter(this.mboundView1, itemBinding, observableList, null, null, null, null);
        }
        if ((j & 49) != 0) {
            TextViewBindingAdapter.setText(this.mboundView3, str);
        }
        if (j4 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}