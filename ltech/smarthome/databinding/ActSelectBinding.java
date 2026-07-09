package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectBinding extends ViewDataBinding {
    public final Group groupSync;
    public final AppCompatImageView ivSync;
    public final ConstraintLayout layoutRoot;
    public final FrameLayout layoutSearch;

    @Bindable
    protected String mBottomText;

    @Bindable
    protected String mBottomTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final NestedScrollView scrollView;
    public final ItemSearchBarBinding searchBar;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvBottomTip;
    public final AppCompatTextView tvSync;
    public final AppCompatTextView tvTip;
    public final View viewSync;

    public abstract void setBottomText(String bottomText);

    public abstract void setBottomTip(String bottomTip);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupSync, AppCompatImageView ivSync, ConstraintLayout layoutRoot, FrameLayout layoutSearch, RecyclerView rvContent, NestedScrollView scrollView, ItemSearchBarBinding searchBar, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom, AppCompatTextView tvBottomTip, AppCompatTextView tvSync, AppCompatTextView tvTip, View viewSync) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupSync = groupSync;
        this.ivSync = ivSync;
        this.layoutRoot = layoutRoot;
        this.layoutSearch = layoutSearch;
        this.rvContent = rvContent;
        this.scrollView = scrollView;
        this.searchBar = searchBar;
        this.title = title;
        this.tvBottom = tvBottom;
        this.tvBottomTip = tvBottomTip;
        this.tvSync = tvSync;
        this.tvTip = tvTip;
        this.viewSync = viewSync;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getBottomText() {
        return this.mBottomText;
    }

    public String getBottomTip() {
        return this.mBottomTip;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select, root, attachToRoot, component);
    }

    public static ActSelectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select, null, false, component);
    }

    public static ActSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBinding bind(View view, Object component) {
        return (ActSelectBinding) bind(component, view, R.layout.act_select);
    }
}