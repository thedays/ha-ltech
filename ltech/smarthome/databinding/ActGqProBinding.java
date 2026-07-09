package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.gqpro.ActGqProVM;
import com.youth.banner.Banner;

/* loaded from: classes3.dex */
public abstract class ActGqProBinding extends ViewDataBinding {
    public final Banner banner;
    public final CardView cardview;
    public final Group groupNoData;
    public final AppCompatImageView ivBg;
    public final AppCompatImageView ivCover;
    public final AppCompatImageView ivMidBg;
    public final AppCompatImageView ivNoData;
    public final AppCompatImageView ivNoData1;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActGqProVM mViewmodel;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvNoData;
    public final AppCompatTextView tvNoDataTip;
    public final AppCompatTextView tvNoDataTip1;
    public final AppCompatTextView tvNum;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActGqProVM viewmodel);

    protected ActGqProBinding(Object _bindingComponent, View _root, int _localFieldCount, Banner banner, CardView cardview, Group groupNoData, AppCompatImageView ivBg, AppCompatImageView ivCover, AppCompatImageView ivMidBg, AppCompatImageView ivNoData, AppCompatImageView ivNoData1, RecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvNoData, AppCompatTextView tvNoDataTip, AppCompatTextView tvNoDataTip1, AppCompatTextView tvNum) {
        super(_bindingComponent, _root, _localFieldCount);
        this.banner = banner;
        this.cardview = cardview;
        this.groupNoData = groupNoData;
        this.ivBg = ivBg;
        this.ivCover = ivCover;
        this.ivMidBg = ivMidBg;
        this.ivNoData = ivNoData;
        this.ivNoData1 = ivNoData1;
        this.rv = rv;
        this.title = title;
        this.tvNoData = tvNoData;
        this.tvNoDataTip = tvNoDataTip;
        this.tvNoDataTip1 = tvNoDataTip1;
        this.tvNum = tvNum;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActGqProVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActGqProBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqProBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActGqProBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gq_pro, root, attachToRoot, component);
    }

    public static ActGqProBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqProBinding inflate(LayoutInflater inflater, Object component) {
        return (ActGqProBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_gq_pro, null, false, component);
    }

    public static ActGqProBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActGqProBinding bind(View view, Object component) {
        return (ActGqProBinding) bind(component, view, R.layout.act_gq_pro);
    }
}