package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.gqx.ActGqxVM;

/* loaded from: classes3.dex */
public abstract class FtGqxActionBinding extends ViewDataBinding {
    public final Group groupAction;
    public final Group groupIntro;
    public final AppCompatImageView iv1;
    public final AppCompatImageView iv2;
    public final AppCompatImageView iv3;
    public final AppCompatImageView ivBind1;
    public final AppCompatImageView ivBind2;
    public final AppCompatImageView ivBind3;
    public final LinearLayout layoutAction;
    public final RelativeLayout layoutBind1;
    public final RelativeLayout layoutBind2;
    public final RelativeLayout layoutBind3;

    @Bindable
    protected ActGqxVM mViewmodel;
    public final TextView tv1;
    public final TextView tv2;
    public final TextView tv3;
    public final TextView tvBind1;
    public final TextView tvBind2;
    public final TextView tvBind3;
    public final TextView tvBindAction1;
    public final TextView tvBindAction2;
    public final TextView tvBindAction3;
    public final ImageView tvBindActionMore1;
    public final ImageView tvBindActionMore2;
    public final ImageView tvBindActionMore3;
    public final TextView tvBindActionSub2;
    public final TextView tvDevName;
    public final TextView tvDevTip;
    public final TextView tvIntroTip1;
    public final TextView tvIntroTip2;
    public final AppCompatTextView tvIntroTitle;

    public abstract void setViewmodel(ActGqxVM viewmodel);

    protected FtGqxActionBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupAction, Group groupIntro, AppCompatImageView iv1, AppCompatImageView iv2, AppCompatImageView iv3, AppCompatImageView ivBind1, AppCompatImageView ivBind2, AppCompatImageView ivBind3, LinearLayout layoutAction, RelativeLayout layoutBind1, RelativeLayout layoutBind2, RelativeLayout layoutBind3, TextView tv1, TextView tv2, TextView tv3, TextView tvBind1, TextView tvBind2, TextView tvBind3, TextView tvBindAction1, TextView tvBindAction2, TextView tvBindAction3, ImageView tvBindActionMore1, ImageView tvBindActionMore2, ImageView tvBindActionMore3, TextView tvBindActionSub2, TextView tvDevName, TextView tvDevTip, TextView tvIntroTip1, TextView tvIntroTip2, AppCompatTextView tvIntroTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupAction = groupAction;
        this.groupIntro = groupIntro;
        this.iv1 = iv1;
        this.iv2 = iv2;
        this.iv3 = iv3;
        this.ivBind1 = ivBind1;
        this.ivBind2 = ivBind2;
        this.ivBind3 = ivBind3;
        this.layoutAction = layoutAction;
        this.layoutBind1 = layoutBind1;
        this.layoutBind2 = layoutBind2;
        this.layoutBind3 = layoutBind3;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tvBind1 = tvBind1;
        this.tvBind2 = tvBind2;
        this.tvBind3 = tvBind3;
        this.tvBindAction1 = tvBindAction1;
        this.tvBindAction2 = tvBindAction2;
        this.tvBindAction3 = tvBindAction3;
        this.tvBindActionMore1 = tvBindActionMore1;
        this.tvBindActionMore2 = tvBindActionMore2;
        this.tvBindActionMore3 = tvBindActionMore3;
        this.tvBindActionSub2 = tvBindActionSub2;
        this.tvDevName = tvDevName;
        this.tvDevTip = tvDevTip;
        this.tvIntroTip1 = tvIntroTip1;
        this.tvIntroTip2 = tvIntroTip2;
        this.tvIntroTitle = tvIntroTitle;
    }

    public ActGqxVM getViewmodel() {
        return this.mViewmodel;
    }

    public static FtGqxActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtGqxActionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtGqxActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_gqx_action, root, attachToRoot, component);
    }

    public static FtGqxActionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtGqxActionBinding inflate(LayoutInflater inflater, Object component) {
        return (FtGqxActionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_gqx_action, null, false, component);
    }

    public static FtGqxActionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtGqxActionBinding bind(View view, Object component) {
        return (FtGqxActionBinding) bind(component, view, R.layout.ft_gqx_action);
    }
}