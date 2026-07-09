package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ActSelectVoiceSpeakBindingImpl extends ActSelectVoiceSpeakBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etContentandroidTextAttrChanged;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{3}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_play_voice_tips, 4);
        sparseIntArray.put(R.id.layout_bar, 5);
        sparseIntArray.put(R.id.sb_volume, 6);
        sparseIntArray.put(R.id.tv_volume, 7);
    }

    public ActSelectVoiceSpeakBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActSelectVoiceSpeakBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (EditText) bindings[1], (LinearLayout) bindings[5], (LightBrtBar) bindings[6], (LayoutTitleDefaultBinding) bindings[3], (TextView) bindings[2], (TextView) bindings[4], (AppCompatTextView) bindings[7]);
        this.etContentandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActSelectVoiceSpeakBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActSelectVoiceSpeakBindingImpl.this.etContent);
                String str = ActSelectVoiceSpeakBindingImpl.this.mVoiceContent;
                ActSelectVoiceSpeakBindingImpl actSelectVoiceSpeakBindingImpl = ActSelectVoiceSpeakBindingImpl.this;
                if (actSelectVoiceSpeakBindingImpl != null) {
                    actSelectVoiceSpeakBindingImpl.setVoiceContent(textString);
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.etContent.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvInputWords.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (93 == variableId) {
            setVoiceContent((String) variable);
            return true;
        }
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSelectVoiceSpeakBinding
    public void setVoiceContent(String VoiceContent) {
        this.mVoiceContent = VoiceContent;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(93);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectVoiceSpeakBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        String str2 = this.mVoiceContent;
        TitleDefault titleDefault = this.mTitle;
        long j2 = 5 & j;
        if (j2 != 0) {
            str = (str2 != null ? str2.length() : 0) + "/300";
        } else {
            str = null;
        }
        long j3 = 6 & j;
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.etContent, str2);
            TextViewBindingAdapter.setText(this.tvInputWords, str);
        }
        if ((j & 4) != 0) {
            TextViewBindingAdapter.setTextWatcher(this.etContent, null, null, null, this.etContentandroidTextAttrChanged);
        }
        if (j3 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}