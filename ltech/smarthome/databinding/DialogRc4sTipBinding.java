package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class DialogRc4sTipBinding extends ViewDataBinding {
    public final View bg;
    public final AppCompatImageView iv1;
    public final AppCompatImageView iv2;
    public final AppCompatImageView iv3;
    public final AppCompatImageView iv4;
    public final View line;
    public final RecyclerView rv;

    protected DialogRc4sTipBinding(Object _bindingComponent, View _root, int _localFieldCount, View bg, AppCompatImageView iv1, AppCompatImageView iv2, AppCompatImageView iv3, AppCompatImageView iv4, View line, RecyclerView rv) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg = bg;
        this.iv1 = iv1;
        this.iv2 = iv2;
        this.iv3 = iv3;
        this.iv4 = iv4;
        this.line = line;
        this.rv = rv;
    }

    public static DialogRc4sTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRc4sTipBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (DialogRc4sTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_rc4s_tip, root, attachToRoot, component);
    }

    public static DialogRc4sTipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRc4sTipBinding inflate(LayoutInflater inflater, Object component) {
        return (DialogRc4sTipBinding) ViewDataBinding.inflateInternal(inflater, R.layout.dialog_rc4s_tip, null, false, component);
    }

    public static DialogRc4sTipBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogRc4sTipBinding bind(View view, Object component) {
        return (DialogRc4sTipBinding) bind(component, view, R.layout.dialog_rc4s_tip);
    }
}