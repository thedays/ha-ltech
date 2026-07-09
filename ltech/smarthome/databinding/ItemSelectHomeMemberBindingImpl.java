package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ItemSelectHomeMemberBindingImpl extends ItemSelectHomeMemberBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_icon, 3);
        sparseIntArray.put(R.id.iv_go, 4);
    }

    public ItemSelectHomeMemberBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ItemSelectHomeMemberBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (AppCompatImageView) bindings[4], (CircleImageView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView6.setTag(null);
        this.appCompatTextView7.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
        if (89 == variableId) {
            setUser((PlaceUser) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (57 == variableId) {
            setPlace((Place) variable);
            return true;
        }
        if (64 != variableId) {
            return false;
        }
        setRole((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectHomeMemberBinding
    public void setUser(PlaceUser User) {
        this.mUser = User;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(89);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectHomeMemberBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectHomeMemberBinding
    public void setPlace(Place Place) {
        this.mPlace = Place;
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectHomeMemberBinding
    public void setRole(String Role) {
        this.mRole = Role;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(64);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        PlaceUser placeUser = this.mUser;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        String str = this.mRole;
        long j2 = 17 & j;
        String userName = (j2 == 0 || placeUser == null) ? null : placeUser.getUserName();
        long j3 = 18 & j;
        long j4 = 24 & j;
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView6, userName);
        }
        if ((j & 16) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView6, true);
        }
        if (j4 != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView7, str);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.mboundView0, bindingCommand, false);
        }
    }
}