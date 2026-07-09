package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
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

/* loaded from: classes3.dex */
public class ItemPlaceUserBindingImpl extends ItemPlaceUserBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemPlaceUserBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ItemPlaceUserBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView6.setTag(null);
        this.appCompatTextView7.setTag(null);
        this.appCompatTextView8.setTag(null);
        this.ivGo.setTag(null);
        this.ivIcon.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
        if (64 == variableId) {
            setRole((String) variable);
            return true;
        }
        if (89 == variableId) {
            setUser((PlaceUser) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (2 == variableId) {
            setAccount((String) variable);
            return true;
        }
        if (57 != variableId) {
            return false;
        }
        setPlace((Place) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserBinding
    public void setRole(String Role) {
        this.mRole = Role;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(64);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserBinding
    public void setUser(PlaceUser User) {
        this.mUser = User;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(89);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserBinding
    public void setAccount(String Account) {
        this.mAccount = Account;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserBinding
    public void setPlace(Place Place) {
        this.mPlace = Place;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(57);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String str2;
        boolean z;
        int i;
        boolean z2;
        String str3;
        boolean z3;
        boolean z4;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        String str4 = this.mRole;
        PlaceUser placeUser = this.mUser;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        String str5 = this.mAccount;
        Place place = this.mPlace;
        long j2 = j & 34;
        if (j2 != 0) {
            if (placeUser != null) {
                str = placeUser.getHeadUrl();
                str2 = placeUser.getRemark();
            } else {
                str = null;
                str2 = null;
            }
            boolean isEmpty = TextUtils.isEmpty(str2);
            z = !isEmpty;
            if (j2 != 0) {
                j = !isEmpty ? j | PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED : j | PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
            }
        } else {
            str = null;
            str2 = null;
            z = false;
        }
        long j3 = j & 40;
        if (j3 != 0) {
            boolean isEmpty2 = TextUtils.isEmpty(str5);
            if (j3 != 0) {
                j |= isEmpty2 ? PlaybackStateCompat.ACTION_PREPARE_FROM_URI : 65536L;
            }
            i = isEmpty2 ? 8 : 0;
        } else {
            i = 0;
        }
        long j4 = j & 50;
        if (j4 != 0) {
            z2 = place != null ? place.isOwner() : false;
            if (j4 != 0) {
                j = z2 ? j | 128 : j | 64;
            }
        } else {
            z2 = false;
        }
        if ((j & 262272) != 0) {
            if ((j & 128) != 0) {
                z3 = !(placeUser != null ? placeUser.isOwner() : false);
            } else {
                z3 = false;
            }
            str3 = ((PlaybackStateCompat.ACTION_SET_REPEAT_MODE & j) == 0 || placeUser == null) ? null : placeUser.getUserName();
        } else {
            str3 = null;
            z3 = false;
        }
        long j5 = j & 50;
        if (j5 != 0) {
            if (!z2) {
                z3 = false;
            }
            if (j5 != 0) {
                j = z3 ? j | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : j | 1024;
            }
        } else {
            z3 = false;
        }
        String str6 = (j & 34) != 0 ? z ? str2 : str3 : null;
        long j6 = j & 1024;
        if (j6 != 0) {
            z4 = place != null ? place.isManager() : false;
            if (j6 != 0) {
                j = z4 ? j | 512 : j | 256;
            }
        } else {
            z4 = false;
        }
        boolean isMember = ((j & 512) == 0 || placeUser == null) ? false : placeUser.isMember();
        if ((j & 1024) == 0 || !z4) {
            isMember = false;
        }
        long j7 = j & 50;
        if (j7 != 0) {
            if (z3) {
                isMember = true;
            }
            if (j7 != 0) {
                j |= isMember ? 40960L : 20480L;
            }
            i2 = isMember ? 0 : 8;
        } else {
            isMember = false;
            i2 = 0;
        }
        if ((j & 34) != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView6, str6);
            ViewAdapter.onClickCommand(this.ivIcon, str, R.mipmap.ic_my_photo_default, R.mipmap.ic_my_photo_default, true);
        }
        if ((32 & j) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView6, true);
        }
        if ((j & 40) != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView7, str5);
            this.appCompatTextView7.setVisibility(i);
        }
        if ((33 & j) != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView8, str4);
        }
        if ((j & 50) != 0) {
            this.ivGo.setVisibility(i2);
            this.mboundView0.setEnabled(isMember);
        }
        if ((j & 36) != 0) {
            ViewAdapter.onClickCommand(this.mboundView0, bindingCommand, false);
        }
    }
}