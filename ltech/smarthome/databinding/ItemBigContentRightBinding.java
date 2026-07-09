package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.MusicItem;
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public abstract class ItemBigContentRightBinding extends ViewDataBinding {
    public final View centerLine;
    public final WaveView icPlayingMain;
    public final WaveView icPlayingSub;
    public final ImageView ivMain;
    public final ImageView ivPlay;
    public final ImageView ivSub;
    public final ImageView ivSubPlay;
    public final LinearLayout layoutItemBg;
    public final ConstraintLayout layoutMain;
    public final ConstraintLayout layoutSub;

    @Bindable
    protected MusicItem mItem;
    public final TextView tvMain;
    public final TextView tvMorning;
    public final TextView tvMorningSub;
    public final TextView tvSub;

    public abstract void setItem(MusicItem item);

    protected ItemBigContentRightBinding(Object _bindingComponent, View _root, int _localFieldCount, View centerLine, WaveView icPlayingMain, WaveView icPlayingSub, ImageView ivMain, ImageView ivPlay, ImageView ivSub, ImageView ivSubPlay, LinearLayout layoutItemBg, ConstraintLayout layoutMain, ConstraintLayout layoutSub, TextView tvMain, TextView tvMorning, TextView tvMorningSub, TextView tvSub) {
        super(_bindingComponent, _root, _localFieldCount);
        this.centerLine = centerLine;
        this.icPlayingMain = icPlayingMain;
        this.icPlayingSub = icPlayingSub;
        this.ivMain = ivMain;
        this.ivPlay = ivPlay;
        this.ivSub = ivSub;
        this.ivSubPlay = ivSubPlay;
        this.layoutItemBg = layoutItemBg;
        this.layoutMain = layoutMain;
        this.layoutSub = layoutSub;
        this.tvMain = tvMain;
        this.tvMorning = tvMorning;
        this.tvMorningSub = tvMorningSub;
        this.tvSub = tvSub;
    }

    public MusicItem getItem() {
        return this.mItem;
    }

    public static ItemBigContentRightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBigContentRightBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemBigContentRightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_big_content_right, root, attachToRoot, component);
    }

    public static ItemBigContentRightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBigContentRightBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemBigContentRightBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_big_content_right, null, false, component);
    }

    public static ItemBigContentRightBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemBigContentRightBinding bind(View view, Object component) {
        return (ItemBigContentRightBinding) bind(component, view, R.layout.item_big_content_right);
    }
}