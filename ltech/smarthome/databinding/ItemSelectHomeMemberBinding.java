package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemSelectHomeMemberBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView6;
    public final AppCompatTextView appCompatTextView7;
    public final AppCompatImageView ivGo;
    public final CircleImageView ivIcon;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Place mPlace;

    @Bindable
    protected String mRole;

    @Bindable
    protected PlaceUser mUser;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setPlace(Place place);

    public abstract void setRole(String role);

    public abstract void setUser(PlaceUser user);

    protected ItemSelectHomeMemberBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView6, AppCompatTextView appCompatTextView7, AppCompatImageView ivGo, CircleImageView ivIcon) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView6 = appCompatTextView6;
        this.appCompatTextView7 = appCompatTextView7;
        this.ivGo = ivGo;
        this.ivIcon = ivIcon;
    }

    public PlaceUser getUser() {
        return this.mUser;
    }

    public String getRole() {
        return this.mRole;
    }

    public Place getPlace() {
        return this.mPlace;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemSelectHomeMemberBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectHomeMemberBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSelectHomeMemberBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_home_member, root, attachToRoot, component);
    }

    public static ItemSelectHomeMemberBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectHomeMemberBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSelectHomeMemberBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_select_home_member, null, false, component);
    }

    public static ItemSelectHomeMemberBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSelectHomeMemberBinding bind(View view, Object component) {
        return (ItemSelectHomeMemberBinding) bind(component, view, R.layout.item_select_home_member);
    }
}