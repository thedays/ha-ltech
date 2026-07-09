package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActReplaceBinding extends ViewDataBinding {
    public final AppCompatImageView ivReplace;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatSeekBar seekbar;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvReplace;
    public final AppCompatTextView tvReplaceTip;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActReplaceBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivReplace, AppCompatSeekBar seekbar, LayoutTitleDefaultBinding title, AppCompatTextView tvReplace, AppCompatTextView tvReplaceTip, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivReplace = ivReplace;
        this.seekbar = seekbar;
        this.title = title;
        this.tvReplace = tvReplace;
        this.tvReplaceTip = tvReplaceTip;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActReplaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActReplaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActReplaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_replace, root, attachToRoot, component);
    }

    public static ActReplaceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActReplaceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActReplaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_replace, null, false, component);
    }

    public static ActReplaceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActReplaceBinding bind(View view, Object component) {
        return (ActReplaceBinding) bind(component, view, R.layout.act_replace);
    }
}