package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.ir.BaseTvVM;

/* loaded from: classes3.dex */
public abstract class ActTvBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView23;
    public final CardView cardviewAdd;
    public final FrameLayout frameLayout;
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivHome;
    public final AppCompatImageView ivNext;
    public final AppCompatImageView ivPower;
    public final AppCompatImageView ivUpper;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected BaseTvVM mViewmodel;
    public final LayoutTitleIrBinding title;
    public final AppCompatTextView tvIndex;
    public final AppCompatTextView tvMore;
    public final AppCompatTextView tvNum;
    public final AppCompatTextView tvResponse;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(BaseTvVM viewmodel);

    protected ActTvBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView23, CardView cardviewAdd, FrameLayout frameLayout, AppCompatImageView ivBack, AppCompatImageView ivHome, AppCompatImageView ivNext, AppCompatImageView ivPower, AppCompatImageView ivUpper, ConstraintLayout layoutLoad, LayoutTitleIrBinding title, AppCompatTextView tvIndex, AppCompatTextView tvMore, AppCompatTextView tvNum, AppCompatTextView tvResponse) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView23 = appCompatTextView23;
        this.cardviewAdd = cardviewAdd;
        this.frameLayout = frameLayout;
        this.ivBack = ivBack;
        this.ivHome = ivHome;
        this.ivNext = ivNext;
        this.ivPower = ivPower;
        this.ivUpper = ivUpper;
        this.layoutLoad = layoutLoad;
        this.title = title;
        this.tvIndex = tvIndex;
        this.tvMore = tvMore;
        this.tvNum = tvNum;
        this.tvResponse = tvResponse;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BaseTvVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActTvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTvBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_tv, root, attachToRoot, component);
    }

    public static ActTvBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTvBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTvBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_tv, null, false, component);
    }

    public static ActTvBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTvBinding bind(View view, Object component) {
        return (ActTvBinding) bind(component, view, R.layout.act_tv);
    }
}