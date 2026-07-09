package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.device.light.ActAddMusicVM;

/* loaded from: classes3.dex */
public abstract class ItemMusicBinding extends ViewDataBinding {
    public final AppCompatImageView ivDelete;
    public final AppCompatImageView ivSelect;
    public final ConstraintLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Integer mDefaultRes;

    @Bindable
    protected ActAddMusicVM.ItemMusic mItem;

    @Bindable
    protected Integer mSelectRes;
    public final AppCompatTextView tvArtist;
    public final AppCompatTextView tvMusicName;
    public final View vDelete;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setDefaultRes(Integer defaultRes);

    public abstract void setItem(ActAddMusicVM.ItemMusic item);

    public abstract void setSelectRes(Integer selectRes);

    protected ItemMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivDelete, AppCompatImageView ivSelect, ConstraintLayout layoutBg, AppCompatTextView tvArtist, AppCompatTextView tvMusicName, View vDelete) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivDelete = ivDelete;
        this.ivSelect = ivSelect;
        this.layoutBg = layoutBg;
        this.tvArtist = tvArtist;
        this.tvMusicName = tvMusicName;
        this.vDelete = vDelete;
    }

    public ActAddMusicVM.ItemMusic getItem() {
        return this.mItem;
    }

    public Integer getSelectRes() {
        return this.mSelectRes;
    }

    public Integer getDefaultRes() {
        return this.mDefaultRes;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music, root, attachToRoot, component);
    }

    public static ItemMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_music, null, false, component);
    }

    public static ItemMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMusicBinding bind(View view, Object component) {
        return (ItemMusicBinding) bind(component, view, R.layout.item_music);
    }
}