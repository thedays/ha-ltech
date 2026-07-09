package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchVM;

/* loaded from: classes3.dex */
public class ActLightPlanBatchSetBindingImpl extends ActLightPlanBatchSetBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView18;
    private final AppCompatTextView mboundView3;
    private final TextView mboundView4;
    private final AppCompatTextView mboundView5;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(30);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{26}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv, 27);
        sparseIntArray.put(R.id.rhythms_setting_layout, 28);
        sparseIntArray.put(R.id.repeat_week_tv, 29);
    }

    public ActLightPlanBatchSetBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 30, sIncludes, sViewsWithIds));
    }

    private ActLightPlanBatchSetBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (AppCompatTextView) bindings[22], (LinearLayout) bindings[11], (TextView) bindings[15], (AppCompatImageView) bindings[27], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[20], (RelativeLayout) bindings[14], (RelativeLayout) bindings[16], (RelativeLayout) bindings[21], (RelativeLayout) bindings[7], (RelativeLayout) bindings[23], (RelativeLayout) bindings[12], (RelativeLayout) bindings[1], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[29], (LinearLayout) bindings[28], (TextView) bindings[13], (AppCompatTextView) bindings[2], (LayoutTitleDefaultBinding) bindings[26], (TextView) bindings[25], (AppCompatTextView) bindings[19]);
        this.mDirtyFlags = -1L;
        this.editLabel.setTag(null);
        this.editPlanTimeLayout.setTag(null);
        this.endTimeTv.setTag(null);
        this.ivSelected1.setTag(null);
        this.ivSelected2.setTag(null);
        this.ivSelected3.setTag(null);
        this.layoutEndTime.setTag(null);
        this.layoutPlan.setTag(null);
        this.layoutPlanEdit.setTag(null);
        this.layoutPlanTime.setTag(null);
        this.layoutRepeatDate.setTag(null);
        this.layoutStartTime.setTag(null);
        this.layoutSunset.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[18];
        this.mboundView18 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        TextView textView = (TextView) bindings[4];
        this.mboundView4 = textView;
        textView.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        this.planLabel.setTag(null);
        this.planTimeLabel.setTag(null);
        this.repeatDateLabel.setTag(null);
        this.startTimeTv.setTag(null);
        this.sunsetLabel.setTag(null);
        setContainedBinding(this.title);
        this.tvNext.setTag(null);
        this.tvPlan.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 256L;
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
        setViewmodel((ActLightPlanBatchVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActLightPlanBatchSetBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActLightPlanBatchSetBinding
    public void setViewmodel(ActLightPlanBatchVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 128;
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
            return onChangeViewmodelStarTimeText((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelSunRiseText((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelShowRhythmsModel((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 4) {
            return onChangeViewmodelPlanText((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 5) {
            return false;
        }
        return onChangeViewmodelSunSetText((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelStarTimeText(MutableLiveData<String> ViewmodelStarTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelSunRiseText(MutableLiveData<String> ViewmodelSunRiseText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythmsModel(MutableLiveData<Integer> ViewmodelShowRhythmsModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelPlanText(MutableLiveData<String> ViewmodelPlanText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelSunSetText(MutableLiveData<String> ViewmodelSunSetText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0129 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0135  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 570
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActLightPlanBatchSetBindingImpl.executeBindings():void");
    }
}