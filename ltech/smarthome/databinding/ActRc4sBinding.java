package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActRc4sBinding extends ViewDataBinding {
    public final AppCompatImageView ivClick;
    public final AppCompatImageView ivDevicePic;
    public final AppCompatImageView ivDoubt;
    public final AppCompatImageView ivMode;
    public final ConstraintLayout layoutContent;
    public final ConstraintLayout layoutScene;
    public final ConstraintLayout layoutZone;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvMultiBind;
    public final RecyclerView rvScene;
    public final NestedScrollView scrollView;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvMultiBindTitle;
    public final AppCompatTextView tvScene;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActRc4sBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivClick, AppCompatImageView ivDevicePic, AppCompatImageView ivDoubt, AppCompatImageView ivMode, ConstraintLayout layoutContent, ConstraintLayout layoutScene, ConstraintLayout layoutZone, RecyclerView rvMultiBind, RecyclerView rvScene, NestedScrollView scrollView, LayoutTitleDefaultBinding title, AppCompatTextView tvMultiBindTitle, AppCompatTextView tvScene) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivClick = ivClick;
        this.ivDevicePic = ivDevicePic;
        this.ivDoubt = ivDoubt;
        this.ivMode = ivMode;
        this.layoutContent = layoutContent;
        this.layoutScene = layoutScene;
        this.layoutZone = layoutZone;
        this.rvMultiBind = rvMultiBind;
        this.rvScene = rvScene;
        this.scrollView = scrollView;
        this.title = title;
        this.tvMultiBindTitle = tvMultiBindTitle;
        this.tvScene = tvScene;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActRc4sBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRc4sBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRc4sBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rc4s, root, attachToRoot, component);
    }

    public static ActRc4sBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRc4sBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRc4sBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_rc4s, null, false, component);
    }

    public static ActRc4sBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRc4sBinding bind(View view, Object component) {
        return (ActRc4sBinding) bind(component, view, R.layout.act_rc4s);
    }
}