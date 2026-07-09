package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActHomeBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView;
    public final AppCompatTextView appCompatTextView2;
    public final AppCompatButton btCreate;
    public final AppCompatButton btJoin;
    public final ConstraintLayout content;
    public final Group group;
    public final ConstraintLayout groupBg;
    public final Group groupContent;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final TabLayout tabs;
    public final AppCompatTextView tvLogout;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActHomeBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView, AppCompatTextView appCompatTextView2, AppCompatButton btCreate, AppCompatButton btJoin, ConstraintLayout content, Group group, ConstraintLayout groupBg, Group groupContent, TabLayout tabs, AppCompatTextView tvLogout, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView = appCompatImageView;
        this.appCompatTextView2 = appCompatTextView2;
        this.btCreate = btCreate;
        this.btJoin = btJoin;
        this.content = content;
        this.group = group;
        this.groupBg = groupBg;
        this.groupContent = groupContent;
        this.tabs = tabs;
        this.tvLogout = tvLogout;
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home, root, attachToRoot, component);
    }

    public static ActHomeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home, null, false, component);
    }

    public static ActHomeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomeBinding bind(View view, Object component) {
        return (ActHomeBinding) bind(component, view, R.layout.act_home);
    }
}