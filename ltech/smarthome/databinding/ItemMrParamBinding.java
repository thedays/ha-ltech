package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemMrParamBinding extends ViewDataBinding {
    public final CircleImageView civColorMain;
    public final CircleImageView civColorSub;
    public final RelativeLayout firstGo;
    public final AppCompatImageView ivGo1;
    public final AppCompatImageView ivGo2;
    public final AppCompatImageView ivIcon1;
    public final AppCompatImageView ivIcon2;
    public final RelativeLayout secondGo;
    public final AppCompatTextView title;
    public final AppCompatTextView tvMain1;
    public final AppCompatTextView tvMain2;
    public final AppCompatTextView tvSub1;
    public final AppCompatTextView tvSub2;
    public final View view1;

    protected ItemMrParamBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView civColorMain, CircleImageView civColorSub, RelativeLayout firstGo, AppCompatImageView ivGo1, AppCompatImageView ivGo2, AppCompatImageView ivIcon1, AppCompatImageView ivIcon2, RelativeLayout secondGo, AppCompatTextView title, AppCompatTextView tvMain1, AppCompatTextView tvMain2, AppCompatTextView tvSub1, AppCompatTextView tvSub2, View view1) {
        super(_bindingComponent, _root, _localFieldCount);
        this.civColorMain = civColorMain;
        this.civColorSub = civColorSub;
        this.firstGo = firstGo;
        this.ivGo1 = ivGo1;
        this.ivGo2 = ivGo2;
        this.ivIcon1 = ivIcon1;
        this.ivIcon2 = ivIcon2;
        this.secondGo = secondGo;
        this.title = title;
        this.tvMain1 = tvMain1;
        this.tvMain2 = tvMain2;
        this.tvSub1 = tvSub1;
        this.tvSub2 = tvSub2;
        this.view1 = view1;
    }

    public static ItemMrParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMrParamBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMrParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mr_param, root, attachToRoot, component);
    }

    public static ItemMrParamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMrParamBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMrParamBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_mr_param, null, false, component);
    }

    public static ItemMrParamBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMrParamBinding bind(View view, Object component) {
        return (ItemMrParamBinding) bind(component, view, R.layout.item_mr_param);
    }
}