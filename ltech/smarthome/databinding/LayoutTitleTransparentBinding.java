package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public abstract class LayoutTitleTransparentBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView8;
    public final View grayLine;
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivDoubt;
    public final AppCompatImageView ivEdit;
    public final AppCompatImageView ivSearch;
    public final RelativeLayout layoutSearchView;
    public final ConstraintLayout layoutToolbar;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatImageView searchImage;
    public final SpreadView searchSpreadView;
    public final Toolbar toolbar;
    public final AppCompatTextView tvEdit;
    public final AppCompatTextView tvNum;
    public final AppCompatTextView tvSubTitle;
    public final AppCompatTextView tvTitle;

    public abstract void setTitle(TitleDefault title);

    protected LayoutTitleTransparentBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView8, View grayLine, AppCompatImageView ivBack, AppCompatImageView ivDoubt, AppCompatImageView ivEdit, AppCompatImageView ivSearch, RelativeLayout layoutSearchView, ConstraintLayout layoutToolbar, AppCompatImageView searchImage, SpreadView searchSpreadView, Toolbar toolbar, AppCompatTextView tvEdit, AppCompatTextView tvNum, AppCompatTextView tvSubTitle, AppCompatTextView tvTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView8 = appCompatTextView8;
        this.grayLine = grayLine;
        this.ivBack = ivBack;
        this.ivDoubt = ivDoubt;
        this.ivEdit = ivEdit;
        this.ivSearch = ivSearch;
        this.layoutSearchView = layoutSearchView;
        this.layoutToolbar = layoutToolbar;
        this.searchImage = searchImage;
        this.searchSpreadView = searchSpreadView;
        this.toolbar = toolbar;
        this.tvEdit = tvEdit;
        this.tvNum = tvNum;
        this.tvSubTitle = tvSubTitle;
        this.tvTitle = tvTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static LayoutTitleTransparentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleTransparentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutTitleTransparentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_title_transparent, root, attachToRoot, component);
    }

    public static LayoutTitleTransparentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleTransparentBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutTitleTransparentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_title_transparent, null, false, component);
    }

    public static LayoutTitleTransparentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutTitleTransparentBinding bind(View view, Object component) {
        return (LayoutTitleTransparentBinding) bind(component, view, R.layout.layout_title_transparent);
    }
}