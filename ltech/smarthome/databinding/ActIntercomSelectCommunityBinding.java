package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomLoginVM;

/* loaded from: classes3.dex */
public abstract class ActIntercomSelectCommunityBinding extends ViewDataBinding {
    public final LinearLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActIntercomLoginVM mViewmodel;
    public final RecyclerView rvCommunity;
    public final LayoutTitleDefaultBinding title;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActIntercomLoginVM viewmodel);

    protected ActIntercomSelectCommunityBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutLoad, RecyclerView rvCommunity, LayoutTitleDefaultBinding title) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.rvCommunity = rvCommunity;
        this.title = title;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public ActIntercomLoginVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActIntercomSelectCommunityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSelectCommunityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActIntercomSelectCommunityBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_select_community, root, attachToRoot, component);
    }

    public static ActIntercomSelectCommunityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSelectCommunityBinding inflate(LayoutInflater inflater, Object component) {
        return (ActIntercomSelectCommunityBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_intercom_select_community, null, false, component);
    }

    public static ActIntercomSelectCommunityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActIntercomSelectCommunityBinding bind(View view, Object component) {
        return (ActIntercomSelectCommunityBinding) bind(component, view, R.layout.act_intercom_select_community);
    }
}