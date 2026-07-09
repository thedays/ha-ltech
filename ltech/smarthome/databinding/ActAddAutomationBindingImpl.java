package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.automation.ActAddAutomationVM;
import com.ltech.smarthome.ui.item.GoItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public class ActAddAutomationBindingImpl extends ActAddAutomationBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final LinearLayout mboundView1;
    private final AppCompatTextView mboundView10;
    private final ImageView mboundView13;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView4;
    private final AppCompatTextView mboundView6;
    private final AppCompatTextView mboundView8;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(39);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{21}, new int[]{R.layout.layout_title_default});
        includedLayouts.setIncludes(1, new String[]{"item_go_1"}, new int[]{22}, new int[]{R.layout.item_go_1});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 23);
        sparseIntArray.put(R.id.cardView2, 24);
        sparseIntArray.put(R.id.layout_bg, 25);
        sparseIntArray.put(R.id.radio_type, 26);
        sparseIntArray.put(R.id.radio_local, 27);
        sparseIntArray.put(R.id.radio_cloud, 28);
        sparseIntArray.put(R.id.tv_gateway, 29);
        sparseIntArray.put(R.id.tv_offline, 30);
        sparseIntArray.put(R.id.iv_go, 31);
        sparseIntArray.put(R.id.tv_times, 32);
        sparseIntArray.put(R.id.iv_go1, 33);
        sparseIntArray.put(R.id.iv_go2, 34);
        sparseIntArray.put(R.id.rv_exec_condition, 35);
        sparseIntArray.put(R.id.iv_status_condition_go, 36);
        sparseIntArray.put(R.id.rv_condition, 37);
        sparseIntArray.put(R.id.rv_action, 38);
    }

    public ActAddAutomationBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 39, sIncludes, sViewsWithIds));
    }

    private ActAddAutomationBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (CardView) bindings[24], (ItemGo1Binding) bindings[22], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[31], (AppCompatImageView) bindings[33], (AppCompatImageView) bindings[34], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[36], (RelativeLayout) bindings[25], (RelativeLayout) bindings[12], (RelativeLayout) bindings[9], (NestedScrollView) bindings[23], (RelativeLayout) bindings[5], (ConstraintLayout) bindings[15], (RelativeLayout) bindings[7], (RadioButton) bindings[28], (RadioButton) bindings[27], (RadioGroup) bindings[26], (SwipeRecyclerView) bindings[38], (SwipeRecyclerView) bindings[37], (SwipeRecyclerView) bindings[35], (LayoutTitleDefaultBinding) bindings[21], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[32]);
        this.mDirtyFlags = -1L;
        setContainedBinding(this.goItem);
        this.ivChangePic.setTag(null);
        this.ivStatusCondition.setTag(null);
        this.layoutExpand.setTag(null);
        this.layoutInterval.setTag(null);
        this.layoutSelectGateway.setTag(null);
        this.layoutStatusConditionBg.setTag(null);
        this.layoutTimes.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        LinearLayout linearLayout2 = (LinearLayout) bindings[1];
        this.mboundView1 = linearLayout2;
        linearLayout2.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        ImageView imageView = (ImageView) bindings[13];
        this.mboundView13 = imageView;
        imageView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[6];
        this.mboundView6 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[8];
        this.mboundView8 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        setContainedBinding(this.title);
        this.tvConditionExecType.setTag(null);
        this.tvConditionType.setTag(null);
        this.tvInterval.setTag(null);
        this.tvName.setTag(null);
        this.tvRemove.setTag(null);
        this.tvStatusCondition.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 256L;
        }
        this.title.invalidateAll();
        this.goItem.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings() || this.goItem.hasPendingBindings();
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
        setViewmodel((ActAddAutomationVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAddAutomationBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAddAutomationBinding
    public void setViewmodel(ActAddAutomationVM Viewmodel) {
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
        this.goItem.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelAutomationName((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeGoItem((ItemGo1Binding) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelEffectTimeLiveData((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelCycleIndex((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 4) {
            return onChangeViewmodelIsExpand((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 5) {
            return false;
        }
        return onChangeViewmodelIntervalTimeStr((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelAutomationName(MutableLiveData<String> ViewmodelAutomationName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeGoItem(ItemGo1Binding GoItem, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelEffectTimeLiveData(MutableLiveData<GoItem> ViewmodelEffectTimeLiveData, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelCycleIndex(MutableLiveData<Integer> ViewmodelCycleIndex, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelIsExpand(MutableLiveData<Boolean> ViewmodelIsExpand, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelIntervalTimeStr(MutableLiveData<String> ViewmodelIntervalTimeStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:139:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c6  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 717
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActAddAutomationBindingImpl.executeBindings():void");
    }
}